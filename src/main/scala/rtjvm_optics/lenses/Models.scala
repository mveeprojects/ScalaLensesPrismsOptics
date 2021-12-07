package rtjvm_optics.lenses

case class Guitar(make: String, model: String)
case class Guitarist(name: String, favouriteGuitar: Guitar)
case class RockBand(name: String, yearFormed: Int, leadGuitarist: Guitarist)

object ExampleNestedCaseClassInstance {
  val metallica: RockBand = RockBand("Metallica", 1981, Guitarist("Kirk Hammett", Guitar("ESP", "M II")))
}
