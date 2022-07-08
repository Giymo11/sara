
from: https://www.youtube.com/watch?v=dv7fPmgFTNA



# Setup Dev Environment

- install scala https://docs.scala-lang.org/getting-started/index.html
- install vs code
- install metals


# getting started


## scala build setup

// project/build.properties
sbt.version = 1.6.2

// project/plugins.sbt
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.10.0")


# Frontend

`sbt ~frontend/fastLinkJS`

at the same time

`cd frontend`
`npm install`
`npm run dev`

this compiles and displays your frontend project on every file save


