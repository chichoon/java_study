#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <time.h>
#include <Windows.h>

char line1[16];
char line2[16];
int speed = 400;
int count = 0;
int flag = 2;
int score = 0;

void shiftleft(char arr[]) {
	//첫번째 자리 제외 shift 필요
	//rotate가 아닌 shift이기 때문에 arr[1]은 밀려 사라진다
	for (int i = 1; i < 15; i++) {
		arr[i] = arr[i + 1];
	}
	arr[15] = ' ';
}

int main(void) {
	srand(time(NULL));
	int p_obs;

	for (int i = 0; i < 16; i++) {
		line1[i] = ' ';
		line2[i] = ' ';
	}

	while (1) {
		p_obs = rand() % 5;
		//점프

		if (flag == 1 && _kbhit()) {
			flag = 2; //방향: 2번째 줄로
		}
		else if (flag == 2 && _kbhit()) {
			flag = 1; //방향: 1번째 줄로
		}

		if (flag == 1) { //A를 1번째 줄로 이동
			line1[0] = 'A';
			line2[0] = ' ';
		}
		else if (flag == 2){ //A를 2번째 줄로 이동
			line1[0] = ' ';
			line2[0] = 'A';
		}
		
		if (count % 2 == 0) { //2턴에 한번씩
			if (p_obs == 1 || p_obs == 3) { //1번째 줄에 장애물 등장
				line1[15] = 'o';
				line2[15] = ' ';
			}
			else if(p_obs == 2 || p_obs == 4) { //2번째 줄에 장애물 등장
				line1[15] = ' ';
				line2[15] = 'o';
			}
			else {
				line1[15] = ' ';
				line2[15] = ' ';
			}
		}

		//화면에 표시
		printf("%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", 
			line1[0], line1[1], line1[2], line1[3], line1[4], line1[5], line1[6], line1[7], 
			line1[8], line1[9], line1[10], line1[11], line1[12], line1[13], line1[14], line1[15],
			line2[0], line2[1], line2[2], line2[3], line2[4], line2[5],line2[6], line2[7], 
			line2[8], line2[9], line2[10], line2[11], line2[12], line2[13], line2[14], line2[15]);

		printf("\n%d\n", score);
		while (_kbhit()) _getch(); //_kbhit flush
		count++; //장애물 등장 count를 ++함

		//shift 전에 장애물에 닿는지 미리 체크
		if (line1[0] == 'A' && line1[1] == 'o') { 
			printf("\nGame Over!!");
			return 0;
		}
		else if (line2[0] == 'A' && line2[1] == 'o') {
			printf("\nGame Over!!");
			return 0;
		}

		//배열을 left shift - 장애물이 이동
		shiftleft(line1);
		shiftleft(line2);
		Sleep(speed); //delay를 주고
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
		system("cls"); //화면을 업데이트함
	}
}