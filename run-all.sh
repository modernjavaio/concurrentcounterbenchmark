#!/bin/bash

set -e
set -u

extraArgs=
if [ $1 == "-q" ]; then
   extraArgs="-f 1 -wi 1 -i 1 "
fi

for i in $(seq 8); do
   java -jar target/benchmark.jar -t $i -rff jmh-result.$i.csv $extraArgs
done
