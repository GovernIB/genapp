#!/bin/bash

echo off
cat help.txt

env mvn -DskipTests $@ clean install 

if [ $? == 0 ]; then
  if [ "GENAPPSQLTUTORIAL_DEPLOY_DIR" == "" ];  then

    echo  =================================================================
    echo    Definex la variable d\'entorn GENAPPSQLTUTORIAL_DEPLOY_DIR apuntant al
    echo    directori de deploy del JBOSS  i automaticament s\'hi copiara
    echo    l\'ear generat.
    echo  =================================================================
  
  else
  
    echo on
    echo --------- COPIANT EAR ---------
    cp ./genappsqltutorial-ear/target/genappsqltutorial.ear $GENAPPSQLTUTORIAL_DEPLOY_DIR

  fi
fi


