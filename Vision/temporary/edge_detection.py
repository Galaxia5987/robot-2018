import cv2
import numpy as np

cam = cv2.VideoCapture('practice12.avi')
while True:
    _, img = cam.read()
    #img = cv2.imread("")
    #img = np.ndarray(img)
    hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

    ranges = [((21, 147, 65), (38, 255, 255)), ((14, 0, 227), (129, 108, 255)), ((16, 103, 52), (94, 255, 255))]
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

    mask = cv2.dilate(mask, kernel=kernel, iterations=5)
    mask = cv2.erode(mask, kernel=kernel, iterations=10)
    mask = cv2.dilate(mask, kernel=kernel, iterations=5)

    img1 = img.copy()
    img2 = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    # img2 = cv2.GaussianBlur(img2, ksize=(7, 7), sigmaX=0)
    # img2 = cv2.cvtColor(img2, cv2.COLOR_HSV2BGR)
    # img2 = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY)
    # img3 = cv2.bitwise_and(mask, img2)

    # canny = cv2.Canny(img3, 50, 75)
    # _, thresh = cv2.threshold(canny, 10, 255, cv2.THRESH_BINARY)
    # thresh = np.array(thresh, dtype=np.uint8)
    # thresh = cv2.dilate(thresh, kernel=kernel, iterations=5)
    # thresh = cv2.erode(thresh, kernel=kernel, iterations=3)
    # mask = cv2.bitwise_and(cv2.bitwise_not(thresh), mask)


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

    [hue,saturation,value]=cv2.split(img2)
    value=cv2.Laplacian(value,8)
    saturation=cv2.Laplacian(saturation,8)
    valsat=cv2.bitwise_or(value,saturation)
    img3 = cv2.bitwise_and(mask, valsat)
    _, img3 = cv2.threshold(img3, 32, 255, cv2.THRESH_BINARY)
    img3 = cv2.dilate(img3, kernel=kernel, iterations=3)
    img3 = cv2.erode(img3, kernel=kernel, iterations=3)

    cv2.imshow("valsat", valsat)
    cv2.imshow("processed image", img3)
    cv2.imshow("image", img1)
    key = cv2.waitKey(0)
    if key is ord('q'):
        break


