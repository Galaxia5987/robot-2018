import cv2
import numpy as np

cam = cv2.VideoCapture(1)
while True:
    kernel = (
        (0, 1, 0),
        (1, 1, 1),
        (0, 1, 0)
    )
    _, img = cam.read()
    img = cv2.blur(img, ksize=(15,15))
    hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    mask = cv2.inRange(hsv, (20, 216, 23), (46, 255, 202))
    kernel=np.array(kernel,dtype=np.uint8)
    mask = cv2.erode(mask, kernel=kernel, iterations=3)
    mask = cv2.dilate(mask, kernel=kernel, iterations=3)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    img1 = img.copy()
    laplacian = cv2.Laplacian(mask,cv2.CV_64F,ksize=25)
    _, thresh = cv2.threshold(laplacian,127,255, cv2.THRESH_BINARY)
    img2 = cv2.bitwise_and(thresh, 255)
    img2 = np.array(img2, dtype=np.uint8)
    _, contours, _ = cv2.findContours(img2, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    cv2.drawContours(img1, contours, -1, (255, 0, 0), 3)
    for c in contours:
        hull = cv2.convexHull(c, returnPoints=False)
        defects = cv2.convexityDefects(c, hull)
        try:
            for i in range(defects.shape[0]):
                s, e, f, _ = defects[i, 0]
                start = tuple(c[s][0])
                end = tuple(c[e][0])
                far = tuple(c[f][0])
                cv2.line(img1, start, end, [0, 255, 0], 2)
                #cv2.circle(img1, far, 5, [0, 0, 255], -1)
        except AttributeError:
            pass
    cv2.imshow("Frame",img1)
    key = cv2.waitKey(1)
    if key is ord('q'):
        break



