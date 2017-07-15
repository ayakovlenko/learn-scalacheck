import com.google.common.base.{CaseFormat, Converter}
import org.scalacheck.Gen
import org.scalacheck.Gen._
import org.scalatest.prop.PropertyChecks
import org.scalatest.{MustMatchers, PropSpec}

class CaseFormatProperties
  extends PropSpec
    with PropertyChecks
    with MustMatchers {

  val capitalizedStringGen: Gen[String] = for {
    n <- choose(0, 10)
    upper <- alphaUpperChar
    lowers <- listOfN(n, alphaLowerChar)
  } yield (upper :: lowers) mkString ""

  val upperCamelCaseStringGen: Int => Gen[String] = n => for {
    strings <- listOfN(n, capitalizedStringGen)
  } yield strings mkString ""

  val caseConverter: Converter[String, String] = {
    CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_HYPHEN)
  }

  property("contains (n - 1) dashes") {
    forAll(posNum[Int]) { n =>
      forAll(upperCamelCaseStringGen(n)) { x =>
        val y = caseConverter.convert(x)

        y.count(_ == '-') mustBe n - 1
      }
    }
  }
}
