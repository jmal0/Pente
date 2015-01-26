#!/usr/bin/env bash

compile() {
	mkdir -p build/classes
	javac -d build/classes src/pente/*.java src/pente/Players/*.java
}

makeJar() {
	compile
	mkdir -p build/jars
	jar cvf pente.jar -C build/classes/ pente
	mv pente.jar build/jars
}

run() {
	java -cp build/classes/pente:build/jars/pente.jar pente.Pente
}

if [ "$1" == "clean" ]; then
	rm -rf build
fi

if [ "$1" == "compile" ]; then
	compile
fi

if [ "$1" == "jar" ]; then
	makeJar
fi

if [ "$1" == "run" ]; then
	run
fi

if [ "$1" == "test" ]; then
	compile
	makeJar
	run
fi
