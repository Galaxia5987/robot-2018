import cv2
import numpy as np

while True:
    frame = np.zeros((480,640,3),dtype=np.uint8)
    key=cv2.waitKeyEx(0)
    if not key is -1:
        cv2.putText(frame,"Key:"+str(key),(20,200),cv2.FONT_HERSHEY_SIMPLEX,5,[255,255,255],5)
    else:
        cv2.putText(frame,"Key:",(20,200),cv2.FONT_HERSHEY_SIMPLEX,5,[255,255,255],5)
    cv2.imshow('frame',frame)
    if key is ord("\\"):
        break
