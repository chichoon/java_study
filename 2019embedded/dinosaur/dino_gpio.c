#include <stdio.h>
#include <stdlib.h>
#include <time.h>
//gpio_test에서 사용하는 헤더들
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include <signal.h>  


void shiftleft(char arr[]) {
	//첫번째 자리 제외 shift 필요
	//rotate가 아닌 shift이기 때문에 arr[1]은 밀려 사라진다
	for (int i = 2; i < 16; i++) {
		arr[i] = arr[i + 1];
	}
	arr[16] = ' ';
}

void my_signal_fun(int signum)  
{  
	read(gpio_dev,&button,2); //드라이버 측에서 button 값 받아옴
	printf("%d\n",button); //button값 minicom쪽에서 출력 (확인차 냅둠)
	
	if((button == 1) && (flag == 1)){
        flag = 0;
        button = 0;
    }
	else if((button == 1) && (flag == 0)){
        flag = 1;
        button = 0;
    }
} 

int flag = 1;
unsigned short button = 0;

int dino(void){
	//전역변수들
	char line1[17];
	char line2[17];
	int speed = 400;
	int count = 0;
	int score = 0;
	flag = 1;

	/*
    dev = open("/dev/gpiobutton",O_RDWR);
    dev1 = open("/dev/textlcd", O_WRONLY);
	if(dev < 0) {
		printf( "GPIO Device Driver Error\n");
		return -1;
	}
    if (dev1 < 0) {
        printf("TextLCD Device Driver Error\n");
        return -1;
    } //디바이스 드라이버 오류이면 프로그램 종료
	*/
    int Oflags;
    srand(time(NULL));
	int p_obs;
    line1[0] = '0';
    line2[0] = '1';

	signal(SIGIO, my_signal_fun); //원본 gpio_test 에서도 while(1) 밖에 있었으니까
	//밑의 while문에 굳이 넣어줄 필요 없을듯

    printf("Press GPIO button to change Direction\n");
    fcntl(gpio_dev, F_SETOWN, getpid());  
    Oflags = fcntl(gpio_dev, F_GETFL);  
    fcntl(gpio_dev, F_SETFL, Oflags | FASYNC); 
	
	int i;

	for (i = 1; i < 17; i++) {
		line1[i] = ' ';
		line2[i] = ' ';
	}

	while (1) {
		if( button==1 ){
			button = 0;
		}
		else if(button == 0) {
			p_obs = rand() % 5;
			button = 0;
			if (flag == 0) { //A를 1번째 줄로 이동
				line1[1] = 'A';
				line2[1] = ' ';
			}
			else if (flag == 1){ //A를 2번째 줄로 이동
				line1[1] = ' ';
				line2[1] = 'A';
			}

			if (count % 2 == 0) { //2턴에 한번씩
				if (p_obs == 1 || p_obs == 3) { //1번째 줄에 장애물 등장
					line1[16] = 'o';
					line2[16] = ' ';
				}
				else if(p_obs == 2 || p_obs == 4) { //2번째 줄에 장애물 등장
					line1[16] = ' ';
					line2[16] = 'o';
				}
				else {
					line1[16] = ' ';
					line2[16] = ' ';
				}
			}

			//화면에 표시 - LCD
	        write(lcd_dev,&line1,17);
	        write(lcd_dev,&line2,17);
	        //close(dev1);
			count++; //장애물 등장 count를 ++함

			//shift 전에 장애물에 닿는지 미리 체크
			if (line1[1] == 'A' && line1[2] == 'o') { 
				printf("\nGame Over!!");
				return 0;
			}
			else if (line2[1] == 'A' && line2[2] == 'o') {
				printf("\nGame Over!!");
				return 0;
			}

			//배열을 left shift - 장애물이 이동
			shiftleft(line1);
			shiftleft(line2);
			sleep(speed * 0.001); //delay를 주고
			if (score >= 10 && score < 30) {
				speed = 300;
			}
			else if (score >= 30 && score < 50) {
				speed = 250;
			}
			else if (score >= 50 && score < 80) {
				speed = 200;
			}
			else if (score >= 80) {
				speed = 100;
			}
			score++;
			//system("cls"); //화면을 업데이트함
		}
	}
}