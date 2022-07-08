ThisBuild / scalaVersion := "3.1.3"

lazy val frontend = project
  .in(file("frontend"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "2.0.0"
    )
  )
  .dependsOn(shared.js)

lazy val backend = project
  .in(file("backend"))
  .settings(
    libraryDependencies ++= Seq(
    ),
    Compile / resourceGenerators += Def.task {
      val source = (frontend / Compile / scalaJSLinkedFile).value.data
      val dest = (Compile / resourceManaged).value / "assets" / "main.js"
      IO.copy(Seq(source -> dest))
      Seq(dest)
    },
    run / fork := true
  )
  .dependsOn(shared.jvm)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(
    libraryDependencies ++= Seq()
  )


