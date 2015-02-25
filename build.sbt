import sbtprotobuf.ProtobufPlugin

name := """defend7-spark-protobuf"""

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark"              %% "spark-core"                % "1.2.1"          % "provided",
  "org.apache.spark"              %% "spark-graphx"              % "1.2.1"          % "provided",
  "org.apache.spark"              %% "spark-streaming"           % "1.2.1"          % "provided",
  "com.google.protobuf"            % "protobuf-java"             % "2.6.1"
)

// sbt-protobuf settings to follow:
Seq(ProtobufPlugin.protobufSettings: _*)

// Version of the protobuf library to be used.
version in ProtobufPlugin.protobufConfig := "2.6.1"

// Path for the generated *.java files.
javaSource in ProtobufPlugin.protobufConfig <<= baseDirectory(_ / "src" / "main" / "java")
