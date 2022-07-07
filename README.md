
from: https://www.youtube.com/watch?v=dv7fPmgFTNA



# Setup Dev Environment

- install scala https://docs.scala-lang.org/getting-started/index.html
- install vs code
- install metals


# getting started

`choco install sbt` or `scoop install sbt`

`npm create vite@latest`
`cd xxx`
`npm install`
`npm run dev`

## scala build setup

// project/build.properties
sbt.version = 1.6.2

// project/plugins.sbt
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.10.0")

`sbt ~fastLinkJS`

also
`npm run build`
`cd dist`
`npx http-server`



