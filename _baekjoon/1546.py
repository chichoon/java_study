N = int(input())
L = [int(i)  for i in input().split(" ")]
print(sum(L)/N/max(L)*100)