import sbt._
import  Keys._

object Common {

  val commonSettings = Seq(
    organization := "com.ds",
    version := "0.1.0",
    scalaVersion := "2.11.12",
    scalacOptions += "-target:jvm-1.7",
    javacOptions ++= Seq("-source", "1.7", "-target", "1.7"),
      scalacOptions += "-Yresolve-term-conflict:package"
  )

    def importSubProject(name: String) : Project = (
    Project(name, file(name))
    settings(commonSettings:_*)
  )
}
