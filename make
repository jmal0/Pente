#!/usr/bin/env bash

compile() {
	mkdir -p build;
	javac -d build src/pente/*.java src/pente/Players;
}

makeJar() {
	compile;
	mkdir -p build;
	jar cvf pente.jar -C build/pente pente;
}

if [ "$1" == "clean" ]; then
	rm -rf build;
fi

if [ "$1" == "compile" ]; then
	compile;
fi

if [ "$1" == "jar" ]; then
	makeJar;
fi