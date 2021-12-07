package rtjvm_optics.optic

import monocle.Prism
import monocle.macros.GenLens
import rtjvm_optics.prisms.{Circle, Rectangle, Shape}

object MonocleOpticMain extends App {

  val circlePrism = Prism[Shape, Double] {
    case Circle(r) => Some(r)
    case _         => None
  }(r => Circle(r))

  val iconLense  = GenLens[BrandIdentity](_.icon)
  val shapeLense = GenLens[Icon](_.shape)

  val brandCircleR = iconLense.composeLens(shapeLense).composePrism(circlePrism)

  val aBrand                   = BrandIdentity(Logo("Orange"), Icon("Black", Circle(12)))
  val abrandWithEnlargedRadius = brandCircleR.modify(_ + 12)(aBrand)

  val anotherBrand                   = BrandIdentity(Logo("Orange"), Icon("Black", Rectangle(4, 12)))
  val anotherBrandWithEnlargedRadius = brandCircleR.modify(_ + 12)(anotherBrand)

  println(abrandWithEnlargedRadius)
  println(anotherBrandWithEnlargedRadius)
}
