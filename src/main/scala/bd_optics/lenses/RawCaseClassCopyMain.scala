package bd_optics.lenses

import bd_optics.User

object RawCaseClassCopyMain extends App {
  def updateStockQuantity(user: User): User =
    user.copy(
      cart = user.cart.copy(
        item = user.cart.item.copy(leftInStock = user.cart.item.leftInStock - 1)
      )
    )
}
