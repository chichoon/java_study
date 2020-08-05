#include<stdio.h>
#include<windows.h>
#include<conio.h>
#include<stdlib.h>
#include<time.h>

//키패드
#define LEFT 75
#define RIGHT 77
#define UP 72
#define DOWN 80

//모눈칸 (도트매트릭스 10*7)
#define MAP_X 20
#define MAP_Y 20

int x[100], y[100];
int food_x, food_y; //food x, y
int length; //body
int speed;
int score;
int dir;
int button; //keypad button
int ggflag = 0;

void gotoxy(int x, int y, char* s) {
	COORD pos = { x, y }; //ASCII 문자열은 2바이트
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), pos);
	printf("%s", s);
}

void reset(void); //게임을 초기화
void move(int dir); //뱀머리를 이동
void food(void); // 음식 생성

int main() {
	reset();
	while (1) {
		if (_kbhit()) do { button = _getch(); } while (button == 224);
		//키를 입력받으면 button에 방향 저장
		Sleep(speed);

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
		}
		move(dir);
		if (ggflag == 1) {
			printf("GAME OVER!!\n");
			return 0;
		}
	}
}

void reset(void) {
	system("cls");
	while (_kbhit()) _getch(); //키보드 버퍼 비우기

	dir = LEFT; //방향 초기화
	speed = 200; //속도 초기화
	length = 2; //뱀 길이 초기화
	score = 0; //점수 초기화
	for (int i = 0; i < length; i++) {
		x[i] = MAP_X / 2 + i; //맵의 중심에서 시작
		y[i] = MAP_Y / 2;
		gotoxy(x[i], y[i], "o");
	}
	gotoxy(x[0], y[0], "O");
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
	for (int i = 1; i < length; i++) {
		if (x[0] == x[i] && y[0] == y[i]) {
			//지 몸과 충돌했을 경우
			ggflag = 1;
			return;
		}
	}

	gotoxy(x[length - 1], y[length - 1], " ");
	for (int i = length - 1; i > 0; i--) {
		//좌표 한칸씩 이동
		x[i] = x[i - 1];
		y[i] = y[i - 1];
	}
	gotoxy(x[0], y[0], "o");
	//움직일 방향에 따라 머리의 좌표 변경
	if (dir == LEFT) --x[0];
	if (dir == RIGHT) ++x[0];
	if (dir == UP) --y[0];
	if (dir == DOWN) ++y[0];
	gotoxy(x[0], y[0], "O");
}


void food(void) {
	int i;

	int food_crush_on = 0;//food가 뱀 몸통좌표에 생길 경우 on
	int r = 0; //난수 생성에 사동되는 변수
	gotoxy(0, MAP_Y + 1, " YOUR SCORE: "); //점수표시
	printf("%3d", score);

	while (1) {
		food_crush_on = 0;
		srand((unsigned)time(NULL) + r); //난수표생성
		food_x = (rand() % (MAP_X - 2)) + 1;    //난수를 좌표값에 넣음
		food_y = (rand() % (MAP_Y - 2)) + 1;

		for (i = 0; i < length; i++) { //food가 뱀 몸통과 겹치는지 확인
			if (food_x == x[i] && food_y == y[i]) {
				food_crush_on = 1; //겹치면 food_crush_on 를 on
				r++;
				break;
			}
		}

		if (food_crush_on == 1) continue; //겹쳤을 경우 while문을 다시 시작

		gotoxy(food_x, food_y, "v"); //안겹쳤을 경우 좌표값에 food를 찍고
		speed -= 10; //속도 증가
		break;

	}
}