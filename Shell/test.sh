#!/bin/bash
#  changing default delimiter from space to comma

IFS=","
# f1 store city and f2 store state
while read f1 f2
do
# we want output only city to new file
        echo $f1
done < cityState.csv > newcityState.csv
# replace new line with semicolon
tr '\n' ';' < newcityState.csv > finalcity.csv

