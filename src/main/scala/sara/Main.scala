package sara

import scala.scalajs.js
import org.scalajs.dom

@main
def Sara(): Unit = {
    dom.document.querySelector("#app").innerHTML = """
        <h1>Hello Scala.js</h1>
        <div>lol</div>
    """
}

