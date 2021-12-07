package aa_quicklens

import aa_quicklens.ExampleNestedCaseClassInstance.user

object RawCaseClassCopyMain extends App {

  println(s"original user:\n$user\n")

  val newBillingInfo              = user.billingInfo.copy(creditCards = List.empty)
  val userCopiedWithNoCreditCards = user.copy(billingInfo = newBillingInfo)

  println(s"userCopiedWithNoCreditCards:\n$userCopiedWithNoCreditCards\n")

  val newName                       = user.name.copy(lastName = "Vee")
  val userCopiedAndChangedTwoFields = user.copy(name = newName, phone = "6789")

  println(s"userCopiedAndChangedTwoFields:\n$userCopiedAndChangedTwoFields \n")
}
