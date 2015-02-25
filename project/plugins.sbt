// Comment to get more information during initialization
logLevel := Level.Warn

// So we can use snapshot versions
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

// The assembly plugin (for fat spark jars)
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.12.0")

// An sbt plugin to compile protobuf files.
addSbtPlugin("com.github.gseitz" % "sbt-protobuf" % "0.3.3")
