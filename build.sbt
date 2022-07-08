ThisBuild / scalaVersion := "3.1.3"

val Versions =
  new {
    val scalajs_dom = "2.0.0"
    val munit = "0.7.29"
  }

lazy val frontend = project
  .in(file("frontend"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % Versions.scalajs_dom,
      "org.scalameta" %%% "munit" % Versions.munit % Test,
    ),
  )
  .dependsOn(shared.js)

lazy val backend = project
  .in(file("backend"))
  .settings(
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % Versions.munit % Test
    ),
    Compile / resourceGenerators += Def.task {
      val source = (frontend / Compile / scalaJSLinkedFile).value.data
      val dest = (Compile / resourceManaged).value / "assets" / "main.js"
      IO.copy(Seq(source -> dest))
      Seq(dest)
    },
    run / fork := true,
  )
  .dependsOn(shared.jvm)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % Versions.munit % Test
    )
  )
