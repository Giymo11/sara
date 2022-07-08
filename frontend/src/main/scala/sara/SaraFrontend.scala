package sara

import org.scalajs.dom

def greeting(): String = s"Hello ${Hello.name} from frontend!"

@main
def main(): Unit =
  dom.document.querySelector("#app").innerHTML =
    s"<h1>Hello Frog</h1><div>${greeting()}</div>"
