package aa_quicklens_example

import aa_quicklens_example.ExampleNestedCaseClassInstance.user

object QuickLenseMain extends App {

  import com.softwaremill.quicklens._

  println(s"original user:\n$user\n")

  val userCopiedWithNoCreditCards = user
    .modify(_.billingInfo.creditCards)
    .setTo(List.empty)

  println(s"userCopiedWithNoCreditCards:\n$userCopiedWithNoCreditCards\n")

  val userCopiedAndChangedTwoFields = user
    .modify(_.name.lastName)
    .setTo("Vee")
    .modify(_.phone)
    .setTo("1234")

  println(s"userCopiedAndChangedTwoFields:\n$userCopiedAndChangedTwoFields \n")
}
