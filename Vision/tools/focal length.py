import cv2
import numpy as np

kernel=np.array((
    (0,1,0),
    (1,1,1),
    (0,1,0)
))

def numbers_input(k,num):
    if k is 8:
        num=int(num/10)
    elif 48<=k<=57:
        num*=10
        num+=k-48
    else:
        pass
    return num

camera=cv2.VideoCapture(0)
distance=0
real_width=23
while True:
    _, frame = camera.read()
    hsv = cv2.cvtColor(frame,cv2.COLOR_BGR2HSV)
    range=(1,168,65),(10,255,96)

    mask=cv2.inRange(hsv,range[0],range[1])
    mask=cv2.erode(mask,kernel,iterations=3)
    mask=cv2.dilate(mask,kernel,iterations=3)

    _,contours,_=cv2.findContours(mask,cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)
    if len(contours) is not 0:
        contours=list(contours)
        area=[]
        for contour in contours:
                area.append(cv2.contourArea(contour))

        largest_contour=contours[area.index(max(area))]
        x,y,width,height = cv2.boundingRect(largest_contour)
        cv2.rectangle(frame,(x,y),(x+width,y+height),(0,255,0),2)
    cv2.putText(frame,"distance: "+str(distance), (10,25), cv2.FONT_HERSHEY_SIMPLEX, 1, color=(255,0,0))
    cv2.imshow('frame',frame)
    key=cv2.waitKey(1)
    if key==ord('q'):
        print('focal length= '+str(width*distance/real_width))
        break
    elif key is not -1:
        distance=numbers_input(key,distance)
