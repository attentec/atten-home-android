cat mem.txt |  awk '$1 == "TOTAL:" { print $2 }' > results/MemResults.txt
printf "Saved memory results in results/MemResults.txt"
