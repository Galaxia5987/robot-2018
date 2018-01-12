import numpy as np
import cv2

cam = cv2.VideoCapture(0)
cam.set(cv2.CAP_PROP_SETTINGS,1)
minH=255
minS=255
minV=255
maxH=0
maxS=0
maxV=0
_,frame=cam.read()
height, width,_=frame.shape
target=np.zeros((height,width,1),dtype=np.uint8)
target[int(height/3):int(2*height/3),int(2*width/6):int(4*width/6)]=255
target1=target
target=np.zeros((height,width,1),dtype=np.uint8)
target[int(2*height/5):int(3*height/5),int(2*width/5):int(3*width/5)]=255
target2=target

while True:
    _, frame=cam.read()
    frame=cv2.bitwise_and(frame,frame,mask=target1)
    key= cv2.waitKey(1) & 0xFF
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    if key == ord('q'):
        for row in hsv:
            for pixel in row:
                # Goes through every pixel in the frame and finds the lowest and highest values
                if not pixel.all()==0:
                    if pixel[0] < minH:
                        minH=pixel[0]
                    if pixel[1] < minS:
                        minS=pixel[1]
                    if pixel[2] < minV:
                        minV=pixel[2]
                    if pixel[0] > maxH:
                        maxH=pixel[0]
                    if pixel[1] > maxS:
                        maxS=pixel[1]
                    if pixel[2] > maxV:
                        maxV=pixel[2]
        file=open("Ace.acpf",'w')
        file.write("self.lower_range,self.upper_range = ({},{},{}),({},{},{})".format(minH,minS,minV,maxH,maxS,maxV))
        file.close()
        break
    cv2.imshow("frame", frame)
print("self.lower_range,self.upper_range = ({},{},{}),({},{},{})".format(minH,minS,minV,maxH,maxS,maxV))
