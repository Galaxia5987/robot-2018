import cv2

LINE_COLORS = (
    (0, 0, 0),
    (255, 255, 255),
    (0, 0, 255),
    (0, 255, 0),
    (255, 0, 0)
)
line_color_counter = 0


def next_color():
    global line_color_counter
    if line_color_counter == len(LINE_COLORS) - 1:
        line_color_counter = 0
    else:
        line_color_counter += 1


LINE_WIDTH = 1

cam = cv2.VideoCapture(0)
while True:
    _, frame = cam.read()
    h, w = frame.shape[:2]
    # kav anachi
    top_center = (int(w/2.0), 0)
    bottom_center = (int(w/2.0), int(h))
    cv2.line(frame, top_center, bottom_center, LINE_COLORS[line_color_counter], LINE_WIDTH)

    cv2.imshow('frame', frame)

    if cv2.waitKey(1) & 0xFF == ord('c'):
        next_color()

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cam.release()
cv2.destroyAllWindows()