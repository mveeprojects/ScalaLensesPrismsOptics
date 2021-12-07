package rtjvm_optics.prisms

sealed trait Shape
case class Circle(radius: Double)                    extends Shape
case class Oval(widestRadius: Double)                extends Shape
case class Rectangle(w: Double, h: Double)           extends Shape
case class Triangle(a: Double, b: Double, c: Double) extends Shape

object MonoclePrismMain extends App {

  val aRectangle: Rectangle = Rectangle(10, 20)
  val aTriangle: Triangle   = Triangle(3, 4, 5)

  import monocle.Prism

  val circlePrism = Prism[Shape, Double] {
    case Circle(r) => Some(r)
    case _         => None
  }(r => Circle(r))

  val aCircle: Shape = circlePrism(12)
  val anOval: Shape  = circlePrism(20)

  println(circlePrism.getOption(aCircle))
  println(circlePrism.getOption(anOval))
  println(circlePrism.getOption(aRectangle))
  println(circlePrism.getOption(aTriangle))
}
