import cv2
import numpy as np

def callback(x):
    pass

cap = cv2.VideoCapture(0)
cap.set(cv2.CAP_PROP_SETTINGS,1)
cv2.namedWindow('image')

ilowH = 0
ihighH = 255

ilowS = 0
ihighS = 255
ilowV = 0
ihighV = 255

# create trackbars for color change
cv2.createTrackbar('lowH','image',0,255,callback)
cv2.createTrackbar('highH','image',255,255,callback)

cv2.createTrackbar('lowS','image',0,255,callback)
cv2.createTrackbar('highS','image',255,255,callback)

cv2.createTrackbar('lowV','image',0,255,callback)
cv2.createTrackbar('highV','image',255,255,callback)



while(1):
    ret, frame = cap.read()
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    lower_hsv = (cv2.getTrackbarPos("lowH","image"), cv2.getTrackbarPos("lowS","image"), cv2.getTrackbarPos("lowV","image"))
    higher_hsv = (cv2.getTrackbarPos("highH","image"), cv2.getTrackbarPos("highS","image"), cv2.getTrackbarPos("highV","image"))
    mask = cv2.inRange(hsv, lower_hsv, higher_hsv)
    cv2.imshow('mask', mask)
    cv2.imshow('frame', frame)
    if cv2.waitKey(1) is ord('q'):
        break
cv2.destroyAllWindows()
cap.release()
