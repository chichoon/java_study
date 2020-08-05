#include<stdio.h>
#include<windows.h>
#include<conio.h>
#include<stdlib.h>
#include<time.h>

//키패드
#define LEFT 75
#define RIGHT 77

//모눈칸 (도트매트릭스 10*7)
#define MAP_X 30
#define MAP_Y 30

int x[100], y[100];
int ball_x, ball_y;
int ball_dir; //0, 1, 2, 3 ↖↗↙↘

int length = 7;
int score;
int dir;
int button; //keypad button
int ggflag = 0;

void gotoxy(int x, int y, char* s) {
	COORD pos = { 2 * x, y }; //ASCII 문자열은 2바이트
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), pos);
	printf("%s", s);
}

void reset(void); //게임을 초기화
void move(int dir); //발판 이동
void ball_draw(void); //공 그리기
void ball_changedir(void);

int main() {
	reset();
	while (1) {
		if (_kbhit()) do { button = _getch(); } while (button == 224);
		//키를 입력받으면 button에 방향 저장
		Sleep(200);

		switch (button) {
		case LEFT: 
		case RIGHT:
			dir = button;
			button = 0; //키값 초기화
			break;
			//Pause, ESC 없음
		}
		if (x[0] == 0) {
			dir = RIGHT;
		}
		if (x[length - 1] == MAP_X - 1) {
			dir = LEFT;
		}
		move(dir);
		ball_draw();
		ball_changedir();
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
	score = 0; //점수 초기화
	ball_x = MAP_X / 2 + 2;
	ball_y = MAP_Y / 2;
	ball_dir = 3;

	for (int i = 0; i < length; i++) {
		x[i] = MAP_X / 2 + i; //맵의 중심에서 시작
		y[i] = MAP_Y - 10;
		gotoxy(x[i], y[i], "▩");
	}
	ball_draw();
}

void move(int dir) {
	int i;

	if (dir == LEFT) {
		gotoxy(x[length - 1], y[length - 1], "　");
		for (i = 0; i < length; i++) {
			x[i]--;
		}
		gotoxy(x[0], y[0], "▩");
	}
	else if (dir == RIGHT) {
		gotoxy(x[0], y[0], " ");
		for (i = 0; i < length; i++) {
			x[i]++;
		}
		gotoxy(x[length - 1], y[length - 1], "▩");
	}
	//if (dir == LEFT) --x[0];
	//if (dir == RIGHT) ++x[0];
	//gotoxy(x[0], y[0], "▩");
}

void ball_draw(void) {
	gotoxy(0, MAP_Y + 1, " YOUR SCORE: "); //점수표시
	printf("%3d", score);
	gotoxy(ball_x, ball_y, "　");

	switch (ball_dir) { //0, 1, 2, 3 ↖↗↙↘
	case 0:
		gotoxy(--ball_x, --ball_y, "●");
		break;
	case 1:
		gotoxy(++ball_x, --ball_y, "●");
		break;
	case 2:
		gotoxy(--ball_x, ++ball_y, "●");
		break;
	case 3:
		gotoxy(++ball_x, ++ball_y, "●");
		break;
	}
}

void ball_changedir(void) {
	int i;
	for (i = 0; i < length; i++) {
		if (ball_x == x[i] && ball_y == y[i] - 1) {
			switch (ball_dir) {
			case 2:
				ball_dir = 0;
				break;
			case 3:
				ball_dir = 1;
				break;
			}
		}
		if (ball_x == x[i] && ball_y == y[i] + 1) {
			switch (ball_dir) {
			case 1:
				ball_dir = 3;
				break;
			case 0:
				ball_dir = 2;
				break;
			}
		}
		if (ball_x == x[length - 1] + 1 && ball_y == y[i]) {
			switch (ball_dir) {
			case 2:
				ball_dir = 3;
				break;
			case 0:
				ball_dir = 1;
				break;
			}
		}
		if (ball_x == x[0] - 1 && ball_y == y[i]) {
			switch (ball_dir) {
			case 3:
				ball_dir = 2;
				break;
			case 1:
				ball_dir = 0;
				break;
			}
		}
	}
	if (ball_x == 1) {
		switch (ball_dir) {
		case 0:
			ball_dir = 1;
			break;
		case 2:
			ball_dir = 3;
			break;
		}
	}
	if (ball_x == MAP_X - 1) {
		switch (ball_dir) {
		case 1:
			ball_dir = 0;
			break;
		case 3:
			ball_dir = 2;
			break;
		}
	}
	if (ball_y == 1) {
		switch (ball_dir) {
		case 0:
			ball_dir = 2;
			break;
		case 1:
			ball_dir = 3;
			break;
		}
	}
	if (ball_y == MAP_Y - 1) {
		switch (ball_dir) {
		case 2:
			ball_dir = 0;
			break;
		case 3:
			ball_dir = 1;
			break;
		}
	}
}

