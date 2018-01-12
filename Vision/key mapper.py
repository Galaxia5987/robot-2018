import cv2
import numpy as np
grid=np.zeros((500,500,3),dtype=np.uint8)
while True:
    cv2.imshow('grid',grid)
    key=cv2.waitKeyEx(1)
    if key is not -1:
        print(key)
    if key is ord('q'):
        break
