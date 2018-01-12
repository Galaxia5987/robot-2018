@echo off
set /p a="Local or Stream? (l/s): "
python vision_class.py -%a%
pause 