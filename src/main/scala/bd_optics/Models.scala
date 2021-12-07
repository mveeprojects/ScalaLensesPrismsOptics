package bd_optics

case class User(name: String, cart: Cart) {
  override def toString: String = s"\nname: $name" +
    s"\ncart: [" +
    s"\n\tid: ${cart.id}" +
    s"\n\titem: [" +
    s"\n\t\tsku: ${cart.item.sku}" +
    s"\n\t\tprice: ${cart.item.price}" +
    s"\n\t\tleftInStock: ${cart.item.leftInStock}" +
    s"]" +
    s"\n\tquantity: ${cart.quantity}" +
    s"\n]\n"
}
case class Cart(id: String, item: Item, quantity: Int)
case class Item(sku: String, price: Double, leftInStock: Int, discount: Discount)

trait Discount
case class NoDiscount()                 extends Discount
case class PercentageOff(value: Double) extends Discount
case class FixPriceOff(value: Double)   extends Discount

object ExampleNestedCaseClassInstance {

  val user: User = User(
    "M_Vee",
    Cart(
      "123",
      Item(
        "abc",
        12.0,
        3,
        NoDiscount()
      ),
      0
    )
  )
}
