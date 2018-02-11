import cv2
import numpy as np
#from matplotlib import pyplot as plt

cam = cv2.VideoCapture(0)
while True:
    _, img = cam.read()
    #img = cv2.imread('cube.jpg')
    #img0 = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    mask = cv2.inRange(hsv, (0, 63, 58), (60, 239, 255))
    #kernel = np.ones((3, 3), dtype=np.uint8)
    kernel = (
        (0,1,0),
        (1,1,1),
        (0,1,0)
    )
    kernel=np.array(kernel,dtype=np.uint8)
    mask = cv2.dilate(mask, kernel=kernel, iterations=5)
    mask = cv2.erode(mask, kernel=kernel, iterations=10)
    mask = cv2.dilate(mask, kernel=kernel, iterations=5)
    img1 = img.copy()
    _, contours, _ = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    for c in contours:
        hull=cv2.convexHull(c)
        epsilon = 0.015*cv2.arcLength(hull,True)
        approx = cv2.approxPolyDP(hull,epsilon,True)
        hullpoints = cv2.convexHull(approx,returnPoints=True)
        if len(hullpoints) == 6 or len(hullpoints) == 5:
            for i in range(0,len(hullpoints)):
                cv2.line(img1, tuple(hullpoints[i-1][0]), tuple(hullpoints[i][0]), [0, 255, 0], 2)
                cv2.circle(img1, tuple(hullpoints[i][0]), 5, [0, 0, 255], -1)

    cv2.imshow("Frame",img1)
    cv2.imshow("mask",mask)
    key = cv2.waitKey(1)
    if key is ord('q'):
        break


