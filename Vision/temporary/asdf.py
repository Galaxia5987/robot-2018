import cv2
import numpy as np

cam = cv2.VideoCapture('q33.avi')
while True:
    _, img = cam.read()
    #img = cv2.imread("")
    hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

    img1 = img.copy()
    img2 = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

    # _, contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    # cv2.drawContours(img1, contours, -1, [255, 0, 0], 3)
    #
    # for c in contours:
    #     hull=cv2.convexHull(c)
    #     epsilon = 0.015*cv2.arcLength(hull,True)
    #     approx = cv2.approxPolyDP(hull,epsilon,True)
    #     hullpoints = cv2.convexHull(approx,returnPoints=True)
    #     #if len(hullpoints) == 6 or len(hullpoints) == 5:
    #     for i in range(0,len(hullpoints)):
    #         cv2.line(img1, tuple(hullpoints[i-1][0]), tuple(hullpoints[i][0]), [0, 255, 0], 2)
    #         cv2.circle(img1, tuple(hullpoints[i][0]), 5, [0, 0, 255], -1)
    ranges = [((14, 0, 0), (38, 255, 255))]
    masks = []

    kernel = (
        (0,1,0),
        (1,1,1),
        (0,1,0)
    )
    kernel=np.array(kernel,dtype=np.uint8)

    for r in ranges:
        masks.append(cv2.inRange(hsv, r[0], r[1]))

    mask = np.zeros((img.shape[0], img.shape[1]), dtype=np.uint8)

    for m in masks:
        mask = cv2.bitwise_or(mask, m)
    [hue,saturation,value]=cv2.split(img2)
    hue=cv2.Canny(hue,100,200)
    value=cv2.Laplacian(value,8)
    saturation=cv2.Laplacian(saturation,8)
    _, thresh=cv2.threshold(value,10,255,cv2.THRESH_TRIANGLE)
    _, thresh2=cv2.threshold(saturation,10,255,cv2.THRESH_TRIANGLE)

    tresh=cv2.bitwise_and(thresh,thresh2)
    mask_thresh=cv2.bitwise_and(mask,thresh)
    val_max=np.array(thresh,dtype=np.uint8)

    satval=cv2.bitwise_and(value,saturation)
    maskval=cv2.bitwise_and(mask,value)

    cv2.imshow("hue", hue)
    cv2.imshow("value",value)
    cv2.imshow("mask_thresh", mask_thresh)
    cv2.imshow("sat_thresh",val_max)
    cv2.imshow("image", img1)
    key = cv2.waitKey(0)
    if key is ord('q'):
        break
