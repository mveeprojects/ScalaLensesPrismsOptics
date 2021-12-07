package rtjvm_lenses_prisms_optics

import rtjvm_lenses_prisms_optics.ExampleNestedCaseClassInstance.metallica

object RawCasClassCopyMain extends App {

  val metallicaGuitarModelNamesWithDashes = metallica.copy(
    leadGuitarist = metallica.leadGuitarist.copy(
      favouriteGuitar = metallica.leadGuitarist.favouriteGuitar.copy(
        model = metallica.leadGuitarist.favouriteGuitar.model.replace(" ", "-")
      )
    )
  )
}
