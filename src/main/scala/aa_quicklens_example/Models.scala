package aa_quicklens_example

case class User(id: Int, name: Name, billingInfo: BillingInfo, phone: String, email: String)
case class Name(firstName: String, lastName: String)
case class Address(street1: String, street2: String, city: String, state: String, zip: String)
case class CreditCard(name: Name, number: String, month: Int, year: Int, cvv: String)
case class BillingInfo(creditCards: Seq[CreditCard])

object ExampleNestedCaseClassInstance {
  val user: User = User(
    id = 1,
    name = Name(
      firstName = "Al",
      lastName = "Alexander"
    ),
    billingInfo = BillingInfo(
      creditCards = Seq(
        CreditCard(
          name = Name("Al", "Alexander"),
          number = "1111111111111111",
          month = 3,
          year = 2020,
          cvv = ""
        )
      )
    ),
    phone = "907-555-1212",
    email = "al@al.com"
  )
}
