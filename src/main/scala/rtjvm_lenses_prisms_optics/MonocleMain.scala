package rtjvm_lenses_prisms_optics

import monocle.macros.GenLens
import rtjvm_lenses_prisms_optics.ExampleNestedCaseClassInstance.metallica

object MonocleMain extends App {

  val leadGuitaristLens   = GenLens[RockBand](_.leadGuitarist)
  val favouriteGuitarLens = GenLens[Guitarist](_.favouriteGuitar)
  val guitarModelLens     = GenLens[Guitar](_.model)
  val guitarMakeLens      = GenLens[Guitar](_.make)

  val composedLensToReturnNestedGuitarModel =
    leadGuitaristLens.composeLens(favouriteGuitarLens).composeLens(guitarModelLens)

  val guitarModel: String = composedLensToReturnNestedGuitarModel.get(metallica)

  println(s"original guitar model: $guitarModel")

  val formattedRockBand: RockBand = composedLensToReturnNestedGuitarModel.modify(_.replace(" ", "-"))(metallica)

  println(s"modified guitar model: ${formattedRockBand.leadGuitarist.favouriteGuitar.model}")

  val composedLensToReturnNestedGuitarMake = leadGuitaristLens.composeLens(favouriteGuitarLens).composeLens(guitarMakeLens)

  println(s"original guitar make: ${formattedRockBand.leadGuitarist.favouriteGuitar.make}")

  val anotherFormattedRockBand = composedLensToReturnNestedGuitarMake.modify(_.toLowerCase)(formattedRockBand)

  println(s"modified guitar make: ${anotherFormattedRockBand.leadGuitarist.favouriteGuitar.make}")
}
