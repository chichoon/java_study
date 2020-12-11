N = int(input())
I = [int(i)  for i in input().split(" ")]
min = 1000000
max = -1000000

for i in range(0,N):
    if min > I[i] :
        min = I[i]
    if max < I[i] :
        max = I[i]

print(min, max)