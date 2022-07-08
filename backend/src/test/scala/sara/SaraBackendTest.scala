package sara

class MySuite extends munit.FunSuite {
  test("greeting") {
    assert(greeting().contains("backend"))
  }
}
