name := "ScalaOptics"

version := "0.1"

scalaVersion := "2.13.6"

val quickLensVersion = "1.7.5"
val monocleVersion = "2.1.0"

libraryDependencies ++= Seq(
  "com.softwaremill.quicklens" %% "quicklens"     % quickLensVersion,
  "com.github.julien-truffaut" %% "monocle-core"  % monocleVersion,
  "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion
)
