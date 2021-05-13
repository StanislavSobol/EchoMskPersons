@echo off

set title=Project gradlew command panel
set buildVariant=fullCoroutinesDebug

set cmd1=gradlew assemble%buildVariant% --offline
set cmd2=gradlew assemble%buildVariant% --offline install%buildVariant%
set cmd3=gradlew assemble%buildVariant%
set cmd4=gradlew clean assemble%buildVariant% --offline
set cmd5=gradlew clean
set cmd6=gradlew clean cleanupDeadModules cleanBuildCache assemble%buildVariant% --rerun-tasks
set cmd7=gradlew clean cleanBuildCache assemble%buildVariant% --rerun-tasks

echo %title%

if  [%1%] neq [] (
	echo Applying the param %1%...
	set cmdInputInd=%1%
	goto case
)

echo Select commands to run:
echo 1. %cmd1%
echo 2. %cmd2%
echo 3. %cmd3%
echo 4. %cmd4%
echo 5. %cmd5%
echo 6. %cmd6%
echo 7. %cmd7%
echo 0. exit

set /p cmdInputInd=Choose the number:

echo You pressed "%cmdInputInd%"

:case

if %cmdInputInd%==0 (
	goto finish
)

if %cmdInputInd%==1 (
	set currCmd=%cmd1%
	goto job
)

if %cmdInputInd%==2 (
	set currCmd=%cmd2%
	goto job
)

if %cmdInputInd%==3 (
	set currCmd=%cmd3%
	goto job
)

if %cmdInputInd%==4 (
	set currCmd=%cmd4%
	goto job
)

if %cmdInputInd%==5 (
	set currCmd=%cmd5%
	goto job
)

if %cmdInputInd%==6 (
	set currCmd=%cmd6%
	goto job
)

if %cmdInputInd%==7 (
	set currCmd=%cmd7%
	goto job
)

@echo Unknown choise "%cmdInputInd%"
goto finish

:job
echo Starting "%currCmd%"...
%currCmd%
echo "%currCmd%" is finished

:finish
echo %title% is finished