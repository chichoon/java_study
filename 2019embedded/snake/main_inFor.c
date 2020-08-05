#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <fcntl.h>
#include <sys/ioctl.h> // ioctl
#include <sys/mman.h> // mmap PROT_
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <linux/input.h>
#include <linux/fb.h>

#define EVENT_BUF_NUM 64
#define FBDEV_FILE "/dev/fb0"

//키패드
#define LEFT 4
#define RIGHT 6
#define UP 2
#define DOWN 8
#define GIVEUP 26

//모눈칸 (한블럭은 4*4)
#define MAP_X 200
#define MAP_Y 120

//뱀게임 변수
int x[100], y[100];
int food_x, food_y; //food x, y
int length; //body
int speed; //속도
int score; //점수
int dir; //방향
int button; //keypad button
int ggflag = 0; //게임오버 플래그
int i; //전체 for문
int a; //버튼 눌림 체크용 for문
int fd = -1; //file descriptor
size_t read_bytes;
struct input_event event_buf[EVENT_BUF_NUM];

//GraphicLCD 변수
int screen_width;
int screen_height;
int bits_per_pixel;
int line_length;

int fb_fd;
struct fb_var_screeninfo fbvar;
struct fb_fix_screeninfo fbfix;  
int mem_size;
unsigned short *ptr;

//좌표값만큼 for문 돌리기 위한 변수
int coor_y;
int coor_x;
int i;

void init(void); //GraphicLCD 값 initialize
void reset(void); //게임 & 화면을 초기화
void move(int dir); //뱀머리를 이동
void food(void); // 음식 생성

void gotoxy(int x, int y, unsigned short color) {
    int j, k;
    for(j = 4*y - 3; j < 4*y + 1; j++){
        ptr = (unsigned short)*fb_mapped + screen_width * j;
        for(k = 4*x - 3; k < 4*x + 1; k++){
            *ptr++ = color;
        }
    }
}

int main(int argc, char **argv){
    printf("SNAKE GAME\nEAT SOME APPLES!!\n");
	printf("Press 2, 4, 6, 8 key to move\nPress 26 key to give up");
    
	if((fd == open("/dev/input/event3", O_RDONLY)) < 0){
		printf("KEYPAD DRIVER OPEN FAIL\n");
		exit(1);
	}

    init();
    reset();
    while (1) {
		read_bytes = read(fd, event_buf, (sizeof(struct input_event)*EVENT_BUF_NUM));
		if(read_bytes)<sizeof(struct input_event)){
			printf("BUTTON READ ERROR!");
			exit(1);
		}

		for(a=0; a<(read_bytes/sizeof(struct input_event)); a++){
			if((event_buf[i].type == EV_KEY) && (event_buf[i].value == 0)){
				printf("\n Button %d Pressed\n", event_buf[i].code);
				button = event_buf[i].code;
			}

			switch (button) {
				case LEFT: //left, right, up, down 모두
				case RIGHT: //down의 실행문이 수행됨
				case UP:
				case DOWN:
					if ((dir == LEFT && button != RIGHT) || (dir == RIGHT && button != LEFT) ||
						(dir == DOWN && button != UP) || (dir == UP && button != DOWN))
						dir = button; //180도 방향전환 방지용
					button = 0; //키값 초기화
					break;
					//Pause, ESC 없음
				case GIVEUP:
					printf("GAVE UP\n");
					ggflag = 1;
			}
			move(dir);

			if (ggflag == 1) {
				printf("GAME OVER!!\n");
        	    munmap(fb_mapped, mem_size);
        	    close(fb_fd);
				close(fd);
				return 0;
			}
		}
		//키를 입력받으면 button에 방향 저장
		sleep(speed);
	}
}

void init(void){
    if(access(FBDEV_FILE, F_OK))
    {
        printf("%s: access error\n", FBDEV_FILE);
        exit(1);
    }
    
    if((fb_fd = open( FBDEV_FILE, O_RDWR)) <0)
    {
        printf("%s: open error\n", FBDEV_FILE);
        exit(1);
    }
    
    if( ioctl(fb_fd, FBIOGET_VSCREENINFO, &fbvar))
    {
        printf("%s: ioctl error - FBIOGET_VSCREENINFO \n", FBDEV_FILE);
        exit(1);
    }
    
    if( ioctl(fb_fd, FBIOGET_FSCREENINFO, &fbfix))
    {
        printf("%s: ioctl error - FBIOGET_FSCREENINFO \n", FBDEV_FILE);
        exit(1);
    }

    screen_width = fbvar.xres; // 스크린의 픽셀 폭
    screen_height = fbvar.yres; // 스크린의 픽셀 높이
    bits_per_pixel = fbvar.bits_per_pixel; // 픽셀 당 비트 개수
    line_length = fbfix.line_length; // 한개 라인 당 바이트 개수
    
    mem_size = screen_width * screen_height*2;

    fb_mapped = (unsigned char *)mmap(0, mem_size, PROT_READ|PROT_WRITE, MAP_SHARED, fb_fd, 0);
}

void reset(void) {
    for(coor_y = 0; coor_y<480; coor_y++){
        ptr = (unsigned short*)fb_mapped + screen_width * coor_y;
        for(coor_x = 0; coor_x < 800; coor_x++){
            *ptr++ = 0x0000;
        }
    }
	while (_kbhit()) _getch(); //키보드 버퍼 비우기

	dir = LEFT; //방향 초기화
	speed = 200; //속도 초기화
	length = 4; //뱀 길이 초기화
	score = 0; //점수 초기화
	for (i = 0; i < length; i++) {
		x[i] = MAP_X / 2 + i; //맵의 중심에서 시작
		y[i] = MAP_Y / 2;
		gotoxy(x[i], y[i], 0xffff);
	}
	gotoxy(x[0], y[0], 0x07e0);
	food();
}

void move(int dir) {
	if (x[0] == food_x && y[0] == food_y) {
		//음식 먹으면
		score += 10;
		food(); //랜덤하게 음식 다시 생성
		length++; //길이 증가
		x[length - 1] = x[length - 2];
		y[length - 1] = y[length - 2];
	}
	if (x[0] == 0 || x[0] == MAP_X - 1 || y[0] == 0 || y[0] == MAP_Y - 1) {
		//벽에 충돌했을 경우
		ggflag = 1;
		return;
	}
	for (i = 1; i < length; i++) {
		if (x[0] == x[i] && y[0] == y[i]) {
			//지 몸과 충돌했을 경우
			ggflag = 1;
			return;
		}
	}

	gotoxy(x[length - 1], y[length - 1], 0x0000);
	for (i = length - 1; i > 0; i--) {
		//좌표 한칸씩 이동
		x[i] = x[i - 1];
		y[i] = y[i - 1];
	}
	gotoxy(x[0], y[0], 0xffff);
	//움직일 방향에 따라 머리의 좌표 변경
	if (dir == LEFT) --x[0];
	if (dir == RIGHT) ++x[0];
	if (dir == UP) --y[0];
	if (dir == DOWN) ++y[0];
	gotoxy(x[0], y[0], 0x07e0);
}

void food(void) {
	int j;

	int food_crush_on = 0;//food가 뱀 몸통좌표에 생길 경우 on
	int r = 0; //난수 생성에 사동되는 변수
	//gotoxy(0, MAP_Y + 1, " YOUR SCORE: "); //점수표시
	printf("YOUR SCORE : %3d", score);

	while (1) {
		food_crush_on = 0;
		srand((unsigned)time(NULL) + r); //난수표생성
		food_x = (rand() % (MAP_X - 2)) + 1;    //난수를 좌표값에 넣음
		food_y = (rand() % (MAP_Y - 2)) + 1;

		for (j = 0; j < length; j++) { //food가 뱀 몸통과 겹치는지 확인
			if (food_x == x[j] && food_y == y[j]) {
				food_crush_on = 1; //겹치면 food_crush_on 를 on
				r++;
				break;
			}
		}

		if (food_crush_on == 1) continue; //겹쳤을 경우 while문을 다시 시작

		gotoxy(food_x, food_y, 0xf800); //안겹쳤을 경우 좌표값에 food를 찍고
		if(speed >= 50) speed -= 10; //속도 증가
		break;

	}
}