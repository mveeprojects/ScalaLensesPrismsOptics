package mv_lenses

import monocle.macros.GenLens

case class Employee(id: Int, workInfo: WorkInfo, personalInfo: PersonalInfo)
case class WorkInfo(department: String, team: String, role: String, contactInfo: ContactInfo)
case class PersonalInfo(name: Name, dob: String)
case class Name(firstName: String, lastName: String)
case class ContactInfo(phone: String, email: String)

object Main extends App {

  import LensUtils._

  private val employee = Employee(
    1,
    WorkInfo(
      "TDT",
      "Plutus",
      "Scala Developer",
      ContactInfo(
        "1234",
        "mv@blah.com"
      )
    ),
    PersonalInfo(Name("Sally", "McChickendipper"), "01/01/0001")
  )

  def updateEmployee(employee: Employee): Employee = modifyAll(employee)

  println(updateEmployee(employee))
}

object LensUtils {
  private val personalInfoLens      = GenLens[Employee](_.personalInfo)
  private val nameLens              = GenLens[PersonalInfo](_.name)
  private val lastNameLens          = GenLens[Name](_.lastName)
  private val composedLastNameLense = personalInfoLens.composeLens(nameLens).composeLens(lastNameLens)

  private val dobLens         = GenLens[PersonalInfo](_.dob)
  private val composedDobLens = personalInfoLens.composeLens(dobLens)

  val modifyAll: Employee => Employee =
    composedLastNameLense.modify(_ => "McSalad").andThen(composedDobLens.modify(_ => "01/01/1990"))
}
