import cv2
import numpy as np
from matplotlib import pyplot as plt

# cam = cv2.VideoCapture(1)
# _, img0 = cam.read()
img = cv2.imread('cube.jpg')
img0 = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
mask = cv2.inRange(hsv, (25, 70, 85), (33, 255, 255))
kernel = np.ones((3, 3), dtype=np.uint8)
mask = cv2.erode(mask, kernel=kernel, iterations=3)
mask = cv2.dilate(mask, kernel=kernel, iterations=3)
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
img1 = img.copy()
laplacian = cv2.Laplacian(mask,cv2.CV_64F,ksize=25)
_, thresh = cv2.threshold(laplacian,127,255, cv2.THRESH_BINARY)
img2 = cv2.bitwise_and(thresh, 255)
img2 = np.array(img2, dtype=np.uint8)
_, contours, _ = cv2.findContours(img2, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)
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
            cv2.circle(img1, far, 5, [0, 0, 255], -1)
    except AttributeError:
        cv2.drawContours(img1,c,-1,(255,0,0),5)

# sobelx = cv2.Sobel(img,cv2.CV_64F,1,0,ksize=5)  # x
# sobely = cv2.Sobel(img,cv2.CV_64F,0,1,ksize=5)  # y
# canny = cv2.Canny(mask,100,200)

plt.subplot(2,2,1),plt.imshow(img0)
plt.title('Original'), plt.xticks([]), plt.yticks([])
plt.subplot(2,2,2),plt.imshow(mask, cmap = 'gray')
plt.title('Mask'), plt.xticks([]), plt.yticks([])
plt.subplot(2,2,3),plt.imshow(thresh, cmap = 'gray')
plt.title('Laplacian'), plt.xticks([]), plt.yticks([])
plt.subplot(2,2,4),plt.imshow(img1)
plt.title('Contours'), plt.xticks([]), plt.yticks([])

plt.show()