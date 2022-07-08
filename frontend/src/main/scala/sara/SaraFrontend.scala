package sara

import org.scalajs.dom

def greeting(): String = s"Hello ${Hello.name} from frontend"

@main
def main(): Unit =
  dom.document.querySelector("#app").innerHTML =
    "<h1>" + greeting() + "</h1><div>lol</div>"
