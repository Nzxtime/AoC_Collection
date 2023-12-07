#!/bin/bash

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "specify year and day"
    exit
fi

year=$1
day=$(printf "%02d" $2)

echo $year
echo $day

./aocdl -year $year -day $day -output "resources/${year}/${year}_${day}.txt"
# git add "resources/${year}/${year}_${day}.txt"
