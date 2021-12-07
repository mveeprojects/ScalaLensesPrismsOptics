package aa_quicklens_example

import aa_quicklens_example.ExampleNestedCaseClassInstance.user

object RawCaseClassCopyMain extends App {

  println(s"original user:\n$user\n")

  val newBillingInfo = user.billingInfo.copy(creditCards = List.empty)
  val userCopiedWithNoCreditCards = user.copy(billingInfo = newBillingInfo)

  println(s"userCopiedWithNoCreditCards:\n$userCopiedWithNoCreditCards\n")

  val newName = user.name.copy(lastName = "Vee")
  val newPhone = "6789"
  val userCopiedAndChangedTwoFields = user.copy(name = newName, phone = newPhone)

  println(s"userCopiedAndChangedTwoFields:\n$userCopiedAndChangedTwoFields \n")
}
