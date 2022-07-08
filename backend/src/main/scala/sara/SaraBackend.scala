package sara

def greeting(): String = s"Hello ${Hello.name} from backend"

@main
def main(): Unit = println(greeting())
