// Comment to get more information during initialization
logLevel := Level.Warn

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.20")

update / evictionWarningOptions := EvictionWarningOptions.empty

// scala-xml breaking compatibility but not really (https://eed3si9n.com/sbt-1.8.0)
ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
