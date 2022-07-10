import org.scalajs.linker.interface.ModuleSplitStyle

val scala3 = "3.1.3"

val Versions =
  new {
    val scalajs_dom = "2.2.0"
    val munit = "0.7.29"
    val laminar = "0.14.2"
  }

ThisBuild / scalaVersion := scala3

val publicDev = taskKey[String]("output directory for `npm run dev`")
val publicProd = taskKey[String]("output directory for `npm run build`")

lazy val frontend = project
  .in(file("frontend"))
  .enablePlugins(ScalaJSPlugin)
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(
          ModuleSplitStyle.SmallModulesFor(List("frontend"))
        )
    },
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % Versions.scalajs_dom,
      "com.raquo" %%% "laminar" % Versions.laminar,
      "org.scalameta" %%% "munit" % Versions.munit % Test,
    ),
    externalNpm := {
      // scala.sys.process.Process(List("npm", "install", "--silent", "--no-audit", "--no-fund"), baseDirectory.value).!
      baseDirectory.value
    },
    publicDev := linkerOutputDirectory((Compile / fastLinkJS).value)
      .getAbsolutePath(),
    publicProd := linkerOutputDirectory((Compile / fullLinkJS).value)
      .getAbsolutePath(),
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

def linkerOutputDirectory(
  v: Attributed[org.scalajs.linker.interface.Report]
): File = v.get(scalaJSLinkerOutputDirectory.key).getOrElse {
  throw new MessageOnlyException(
    "Linking report was not attributed with output directory. " +
      "Please report this as a Scala.js bug."
  )
}
