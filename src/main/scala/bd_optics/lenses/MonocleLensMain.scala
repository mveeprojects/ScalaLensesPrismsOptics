package bd_optics.lenses

import bd_optics.ExampleNestedCaseClassInstance.user
import bd_optics._
import monocle.macros.GenLens

object MonocleLensMain extends App {

  updateStockQuantity(user, 1)

  def updateStockQuantity(user: User, qty: Int): Unit = {
    val cartLense        = GenLens[User](_.cart)
    val qtyLense         = GenLens[Cart](_.quantity)
    val itemLense        = GenLens[Cart](_.item)
    val leftInStockLense = GenLens[Item](_.leftInStock)

    val leftInStockComposedLense = cartLense.composeLens(itemLense).composeLens(leftInStockLense)
    val userCartQtyComposedLense = cartLense.composeLens(qtyLense)

    val operations = Seq(
      leftInStockComposedLense.modify(_ - qty),
      userCartQtyComposedLense.modify(_ + qty)
    )

    val modifyAll: User => User = Function.chain(operations)
    val newUser: User = modifyAll(user)

    println(s"-- original user -- $user")
    println(s"-- updated user -- $newUser")
  }
}
