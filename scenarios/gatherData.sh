#!/bin/bash

onCancel() {
  pkill -P $command1PID
  kill $command1PID
  pkill -P $command2PID
  kill $command2PID
  pkill -P $command3PID
  kill $command3PID
  exit
}

declare -a testTimes=(21 31 33 6 11 11 11 11)

trap "onCancel" SIGINT
arg=$1
if [ $arg -gt 8 -o $arg -lt 1 ]; then
  printf "ERROR: Test number needs to be in [1..8]\n"
  printf "Usage: ./gatherData.sh <TestNumber> <Platform>\n"
  exit
fi
arg2=$2
if [ $arg2 = "android" ] || [ $arg2 = "rn" ]; then

  ./GatherCPUData.sh ${testTimes[$arg-1]}&
  command1PID="$!"
  ./GatherMemData.sh $arg2 ${testTimes[$arg-1]} &
  command2PID="$!"
  ./systrace.sh $arg $arg2 ${testTimes[$arg-1]}&
  command3PID="$!"
  ./androidScenarios.py -t $arg -$arg2
  wait
  ./grepCPUUsage.sh $arg2
  ./grepMemUsage.sh
else
  printf "ERROR: Platform needs to be 'android' or 'rn'\n"
  printf "Usage: ./gatherData.sh <TestNumber> <Platform>\n"
  exit
fi
