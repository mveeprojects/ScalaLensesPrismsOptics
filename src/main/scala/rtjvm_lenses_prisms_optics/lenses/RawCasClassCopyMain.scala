package rtjvm_lenses_prisms_optics.lenses

import rtjvm_lenses_prisms_optics.lenses.ExampleNestedCaseClassInstance.metallica

object RawCasClassCopyMain extends App {

  val metallicaGuitarModelNamesWithDashes = metallica.copy(
    leadGuitarist = metallica.leadGuitarist.copy(
      favouriteGuitar = metallica.leadGuitarist.favouriteGuitar.copy(
        model = metallica.leadGuitarist.favouriteGuitar.model.replace(" ", "-")
      )
    )
  )

  println(metallicaGuitarModelNamesWithDashes)
}
