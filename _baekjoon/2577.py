
I = 1
cnt = 0
for i in range(0, 3):
    I = I*int(input())
for j in range(0, 10):
    for o in str(I):
        if o == str(j):
            cnt = cnt + 1
    print(cnt)
    cnt = 0