def remdup(lis):
	out=[]
	for el in lis:
		boolean isP=False
		for ch2 in out:
			if ch2==el:
				isP=True
				break
		if !isP:
			out.append(el)
	return out
size=int(input())
lst=[input() for i in range(size)]
lst=remdup(lst)
print(lst)