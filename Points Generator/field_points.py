import cv2
import numpy
import random

START_X = 0.4
START_Y = 1.25
ROBOT_RADIUS = 0.75 # meter
mouseX, mouseY = (0, 0)
y_direction_var_name = "Y_DIRECTION"
def m_to_px(point_m):
    x, y = point_m
    return (x * (x_px / x_m), y * (y_px / y_m))

def px_to_m(point_px):
    x, y = point_px
    return (x * (x_m / x_px), y * (y_m / y_px))

# mouse callback function
points = []
line_ofki = ((0,0), (0,0))
line_anachi = ((0,0), (0,0))
def draw_points():
    for _, _, x, y in points:
        cv2.circle(field,(x,y),4,(255,0,0),-1)
    cv2.line(field, *line_anachi, (0,0,0), 1)
    cv2.line(field, *line_ofki, (0,0,0), 1)
    cv2.circle(field,(mouseX, mouseY), int(m_to_px((ROBOT_RADIUS, ROBOT_RADIUS))[0]),(0,0,255),1)
def print_points():
    print("addSequential(new PathPointsCommand(new Point[]{")
    for i, (pos_x, pos_y, x, y) in enumerate(points):
        text = "    new Point({}, {} * {})".format(pos_x + random.random() / 1000, pos_y, y_direction_var_name)
        if i!= len(points) -1:
            text += ","
        print(text)
    print("});")

def draw_circle(event,x,y,flags,param):
    global mouseX, mouseY
    mouseX, mouseY = (x, y)
    global line_anachi, line_ofki
    line_ofki = ((x,0), (x,y_px))
    line_anachi = ((0,y), (x_px,y))
    if event == cv2.EVENT_LBUTTONDBLCLK:
        y_fix = y_px - y
        x_fix = x
        pos_x = x_fix * (x_m / x_px) - START_X
        pos_y = y_fix * (y_m / y_px) - START_Y
        # print("new Point({}, {}),".format(pos_x, pos_y))
        points.append((pos_x, pos_y, x, y))


field_blank = cv2.imread('field.png',cv2.IMREAD_COLOR)
y_px, x_px = field_blank.shape[:2]
y_m, x_m = 8.23, 16.46 # (8.23, 16.46)
cv2.circle(field_blank, (int(START_X * (x_px / x_m)), int((y_m - START_Y) * (y_px / y_m))), 4, (255,0,0), -1)
print("png: {}, field: {}".format(x_px / y_px, x_m / y_m))
cv2.namedWindow('field')
cv2.setMouseCallback('field',draw_circle)
while True:
    field = field_blank.copy()
    if cv2.waitKey(1) & 0xFF == ord('r'):
        print("removed")
        print(points)
        try:
            points.pop()
        except:
            pass

    draw_points()
    cv2.imshow('field', field)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
# When everything done, release the capture
cv2.destroyAllWindows()
print_points()
