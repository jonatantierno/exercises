# exercises
This folder contains a set of programming exercises and puzzles.

* Random line in file: bash script

	./random_line/random_line.sh FILE

* Rand7 function from rand5: python script

	python ./rand7/rand7.py

* Counting cards Puzzle (http://www.puzzlenode.com/puzzles/5-counting-cards): Java project. Build with Gradle, wrapper included. Compile with :

	cd CountingCards

	./gradlew build

The jar file will be generated in ./CountingCards/build/libs/CountingCards-1.0.jar

Then place it in any directory along with the file INPUT.txt (in ./CountingCards/src/test/resources), and run with

	java -jar CountingCards-1.0.jar
