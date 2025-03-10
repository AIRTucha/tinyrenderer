// Turn this project into a Scala.js project by importing these settings
enablePlugins(ScalaJSPlugin)

name := "TineRender"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.1"

scalaJSUseMainModuleInitializer := true
artifactPath in(Compile, fastOptJS) := baseDirectory.value / "dist" / "lib.js"
// testFrameworks += new TestFramework("utest.runner.Framework")

libraryDependencies ++= Seq(
    "fr.hmil" %%% "roshttp" % "2.0.2",
    "org.scala-js" %%% "scalajs-dom" % "0.9.1",
    "com.lihaoyi" %%% "utest" % "0.4.5" % "test"
)
