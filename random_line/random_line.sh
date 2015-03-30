#!/bin/bash
#
# A program that selects a random line from a text file.
#
# This one is a one-liner in a shell script
if [ -z $1  ] ; then
	echo "______________________________"
	echo "Usage: random_line.sh FILE_NAME"
	echo "______________________________"
else
	shuf -n 1 $1 
fi
