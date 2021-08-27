#!/bin/bash
cd src
# Compile
javac main/java/com/banheiro/unisex/java/pc27s/Pc27sApplication/*.java main/java/banheiro/Banheiro/*.java main/java/pessoa/Pessoa/*.java
# Generate jar
jar cfm ../Pc27sApplication.jar ../manifest.mf Pc27sApplication/Pc27sApplication/*.class Pc27sApplication/bathroom/*.class Pc27sApplication/person/*.class
