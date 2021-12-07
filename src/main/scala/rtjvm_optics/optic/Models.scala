package rtjvm_optics.optic

import rtjvm_optics.prisms.Shape

case class Icon(background: String, shape: Shape)
case class Logo(color: String)
case class BrandIdentity(logo: Logo, icon: Icon)
