#!/bin/bash
if [ $1 = "rn" ]; then
  package="com.attenhome"
else
  package="se.attentec.attenhome"
fi
cat topData.txt | grep $package | grep -o "[0-9]\+%" > results/CPUResults.txt
printf "Parsed CPU results for package: $package \nResults can be found in results/CPUResults.txt\n"
