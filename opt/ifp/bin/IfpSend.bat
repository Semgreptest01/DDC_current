@echo off
setlocal ENABLEDELAYEDEXPANSION
if "%IFP_HOME%" == "" (
echo "環境変数：IFP_HOMEを設定してください。"
exit 101
)

rem ------------------------------------------------------------
rem クラスパスの設定
rem ------------------------------------------------------------
set classpath=%IFP_HOME%;%IFP_HOME%\conf;
for %%i in (%IFP_HOME%\lib\*.*) do set classpath=!classpath!;%%i

java -DIFP_HOME=%IFP_HOME% IfpSend %1 %2 %3 %4 %5 %6 %7 %8 %9
pause
exit %errorlevel%
