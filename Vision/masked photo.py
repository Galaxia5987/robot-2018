import cv2
import numpy as np
cap = cv2.VideoCapture(0)
cv2.namedWindow('image')

while(True):
    # grab the frame
    ret, frame = cap.read()
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    file=open('Ace.acpf','r')
    exec(file.read())
    file.close()
    mask = cv2.inRange(hsv, lower_range, upper_range)
    kernel=np.array([
        [0,1,0],
        [1,1,1],
        [0,1,0]
    ])
    mask=cv2.erode(mask,kernel,iterations=3)
    mask=cv2.dilate(mask,kernel,iterations=3)
    frame = cv2.bitwise_and(frame, frame, mask=mask)
    # show thresholded image
    cv2.imshow('image', frame)
    k = cv2.waitKey(1) & 0xFF # large wait time to remove freezing
    if k == 113 or k == 27:
        break
