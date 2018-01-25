import numpy as np
input=[
    [15,14],
    [12,15],
    [14,1],
    [20,2]
       ]
input=np.array(input)
def order(box):
    box=list(box)
    bottom=[]

    def sortbyindex1(arr):
        return arr[1]

    def sortbyindex0(arr):
        return arr[0]

    box.sort(key=sortbyindex1)
    bottom.append(box[2])
    bottom.append(box[3])

    box.pop(3)
    box.pop(2)
    top=box

    top.sort(key=sortbyindex0)
    bottom.sort(key=sortbyindex0)

    top_left=top[0]
    top_right=top[1]
    bottom_left=bottom[0]
    bottom_right=bottom[1]

    return top_left,top_right,bottom_left,bottom_right

print(order(input))