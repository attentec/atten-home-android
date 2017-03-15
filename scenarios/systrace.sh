#!/bin/bash
#"Input" "View System" "Activity Manager"
DATE=`date +%Y-%m-%d-%H:%M:%S`
TEST=$1
PLATFORM=$2
TIME=$3
if [[ $PLATFORM = 'android' ]]; then
  package="se.attentec.attenhome"
else
  package="com.attenhome"
fi
python $ANDROID_HOME/platform-tools/systrace/systrace.py -o traces/test$TEST-$PLATFORM-$DATE.html -a $package -t $TIME input view wm am
