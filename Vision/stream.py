from flask import Flask, render_template, Response
import numpy as np
import cv2
app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

def gen():
    cam = cv2.VideoCapture(0)
    while True:
        _,frame = cam.read()
        jpg=cv2.imencode('.jpg',frame)[1].tostring()
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + jpg + b'\r\n')
        key=cv2.waitKey(1)

@app.route('/video_feed')
def video_feed():
    return Response(gen(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

if __name__ == '__main__':
        app.run(host='localhost', debug=True)
