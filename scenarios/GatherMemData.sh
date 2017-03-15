#!/bin/bash
rm mem.txt
package="com.attenhome"
if [[ $1 = "android" ]]; then
  package="se.attentec.attenhome"
fi
n=0
while [[ $n -lt $2 ]]; do
  adb shell dumpsys meminfo $package >> mem.txt
  n=$((n+1))
  sleep 1

done
