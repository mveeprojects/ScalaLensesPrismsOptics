# Scala Optics Notes

Introduce case class vs class, focussing on the copy function in particular.

- Alvins scalac, javap example demonstrates this well

For this example we are going to user the following case classes detailing the relationship between an `Employee` and
the information HR has on file for them.

```scala
case class Employee(id: Int, workInfo: WorkInfo, personalInfo: PersonalInfo)

case class WorkInfo(department: String, team: String, role: String, contactInfo: ContactInfo)

case class PersonalInfo(name: Name, dob: String)

case class Name(firstName: String, lastName: String)

case class ContactInfo(phone: String, email: String)

val employee = Employee(
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
```

This is easily modifiable, can use `.copy(...)` to update values where necessary.

Let's imagine that Sally in the example above married and decided to take her partners surname.

We can update this using the case classes copy function like so.

```scala
val updatedEmployee = employee.copy(
  personalInfo = employee.personalInfo.copy(
    name = employee.personalInfo.name.copy(
      lastName = "McSalad"
    )
  )
)
println(employee)
println(updatedEmployee)

// println output:
// Employee(1,WorkInfo(TDT,Plutus,Scala Developer,ContactInfo(1234,mv@blah.com)),PersonalInfo(Name(Sally,McChickendipper),01/01/1990))
// Employee(1,WorkInfo(TDT,Plutus,Scala Developer,ContactInfo(1234,mv@blah.com)),PersonalInfo(Name(Sally,McSalad),01/01/1990))
```

You might agree that this code is pretty clunky and not overly easy to read. It's also only got one purpose, to update
the lastname value.

Let's approach this using Monocle instead.

First we create a series of Lenses. Lenses allow us to "look inside" case classes, e.g. with a Lens on Employee, we can
look into `id`, `workInfo` or `personalInfo`.

In our case we will need to do the following:

1. look into the nested case class `personalInfo`
2. look into the again nested case class `Name`
3. look into the the `lastName` value and modify it

We will then compose our lenses together to create a specific view straight to the `lastName` value when provided an
instance of `Employee`.

```scala
val personalInfoLens = GenLens[Employee](_.personalInfo)
val nameLens = GenLens[PersonalInfo](_.name)
val lastNameLens = GenLens[Name](_.lastName)
val composedLastNameLense = personalInfoLens.composeLens(nameLens).composeLens(lastNameLens)
```

Once the lenses have been created and composed together, all we need to do is call the `modify` function on
our `composedLastNameLense`, providing an instance of `Employee` in order to manipulate the value of the `lastName`
field.

```scala
val updatedEmployee = composedLastNameLense.modify(_ => "McSalad")(employee)
println(updatedEmployee)
```

You might be thinking, this is a lot of fuss to get to the same result as the `.copy` function, but the real power of
using lenses is in their reuse for different compositions.

If you had to use the `.copy` function to modify many fields it would soon become a messy lump of difficult to
understand code. Instead we could just define each `Lens` we need and the composition of lenses to reach each field we
want to work with.

E.g. We've accidentally set the `dob` of this employee to sometime 2000 years ago, let's fix this with an
additional `Lens` that we can compose with our existing Lenses.

```scala
val dobLens = GenLens[PersonalInfo](_.dob)
val composeDobLens = personalInfoLens.composeLens(dobLens)
```

This can now be applied to an instance of `Employee` by simply calling it in the manner below.

```scala
val updatedDoBEmployee = composeDobLens.modify(_ => "01/01/1990")(updatedEmployee)
```

You might've noticed above that I applied this to the `updatedEmployee` instance (`Employee` instance with lastName
already altered), the code to modify both the employees `lastName` and `dob` in this case is shown below, it's not very
elegant.

```scala
val updatedEmployee = composedLastNameLense.modify(_ => "McSalad")(employee)
val updatedDobEmployee = composeDobLens.modify(_ => "01/01/1990")(updatedEmployee)
```

We can improve on this by chaining these modifications together like so.

```scala
val operations = Seq(
  composedLastNameLense.modify(_ => "McSalad"),
  composeDobLens.modify(_ => "01/01/1990")
)
val modifyAll = Function.chain(operations)
```

`andThen` is a bit more readable though, so we'll use that instead.

```scala
val modifyAll: Employee => Employee =
  composedLastNameLense.modify(_ => "McSalad").andThen(composedDobLens.modify(_ => "01/01/1990"))
```

The final code in its complete form for modifying both fields looks like this

```scala
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
  private val personalInfoLens = GenLens[Employee](_.personalInfo)
  private val nameLens = GenLens[PersonalInfo](_.name)
  private val lastNameLens = GenLens[Name](_.lastName)
  private val composedLastNameLense = personalInfoLens.composeLens(nameLens).composeLens(lastNameLens)

  private val dobLens = GenLens[PersonalInfo](_.dob)
  private val composedDobLens = personalInfoLens.composeLens(dobLens)

  val modifyAll: Employee => Employee =
    composedLastNameLense.modify(_ => "McSalad").andThen(composedDobLens.modify(_ => "01/01/1990"))
}
```
