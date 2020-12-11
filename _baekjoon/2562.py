max = -100000
n = 0

for i in range(0, 9):
    I = int(input())
    if max < I :
        max = I
        n = i

print(max)
print(n+1)
