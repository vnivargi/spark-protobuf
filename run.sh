#!/bin/bash

# Fail fast.
set -e

SPARK_BIN_PATH=`which spark-submit`
DRIVER_CLASS="com.defend7.processors.MessageProcessor"
DRIVER_JAR="defend7-spark-protobuf-assembly-1.0.jar"

sbt assembly

${SPARK_BIN_PATH} \
    --class ${DRIVER_CLASS} \
    --driver-class-path target/scala-2.10/${DRIVER_JAR} \
    target/scala-2.10/${DRIVER_JAR}
