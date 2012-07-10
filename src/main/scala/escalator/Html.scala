package escalator

object Html {
  def a(href: String, children: Any*): Tag = {
    new Tag("a", children).attr("href" -> href)
  }

  def button(inputType: String, children: Any*): Tag = {
    new Tag("button", children).attr("type" -> inputType)
  }

  def div(children: Any*): Tag = {
    new Tag("div", children)
  }

  def em(children: Any*): Tag = {
    new Tag("em", children)
  }

  def form(method: String, action: String, children: Any*): Tag = {
    new Tag("form", children).attr("method" -> method, "action" -> action)
  }

  def input(inputType: String, name: String, children: Any*): Tag = {
    new Tag("input").attr("type" -> inputType, "name" -> name)
  }

  def h1(children: Any*): Tag = {
    new Tag("h1", children)
  }

  def h2(children: Any*): Tag = {
    new Tag("h2", children)
  }

  def h3(children: Any*): Tag = {
    new Tag("h3", children)
  }

  def h4(children: Any*): Tag = {
    new Tag("h4", children)
  }

  def h5(children: Any*): Tag = {
    new Tag("h5", children)
  }

  def h6(children: Any*): Tag = {
    new Tag("h6", children)
  }

  def header(children: Any*): Tag = {
    new Tag("header", children)
  }

  def i(): Tag = {
    new Tag("i");
  }

  def img(src: String): Tag = {
    new Tag("img").attr("src" -> src)
  }

  def li(children: Any*): Tag = {
    new Tag("li", children)
  }

  def nbsp(): Writable = {
    new UnsecureText("&nbsp;")
  }

  def small(children: Any*): Tag = {
    new Tag("small", children)
  }

  def span(children: Any*): Tag = {
    new Tag("span", children)
  }

  def table(children: Any*): Tag = {
    new Tag("table", children)
  }

  def tbody(children: Any*): Tag = {
    new Tag("tbody", children)
  }

  def td(children: Any*): Tag = {
    new Tag("td", children)
  }

  def thead(children: Any*): Tag = {
    new Tag("thead", children)
  }

  def th(children: Any*): Tag = {
    new Tag("th", children)
  }

  def tr(children: Any*): Tag = {
    new Tag("tr", children)
  }

  def ul(children: Any*): Tag = {
    new Tag("ul", children)
  }
}
