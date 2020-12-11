L = [int(input()) % 42 for i in range(0,10)]
cnt = 0
for i in range(0, 42):
    for j in range(0, 10):
        if(i == L[j]):
            cnt += 1
            break
print(cnt)