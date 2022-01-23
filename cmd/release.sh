#!/bin/bash

cd ../
if [ "$1" == "version" ]; then
  mvn clean install
  mvn deploy
  git checkout -b release/"$2"
  git add .
  git commit -m "release $2"
  git tag v"$2"
  git push --set-upstream origin release/"$2"
  git push origin v"$2"
  git checkout master
fi

if [ "$1" == "patch" ]; then
  mvn clean install
  mvn deploy
  git add .
  git commit -m "release $2"
  git tag v"$2"
  git push
  git push origin v"$2"
fi


if [ "$1" == "snapshot" ]; then
  mvn clean install
  mvn deploy
fi