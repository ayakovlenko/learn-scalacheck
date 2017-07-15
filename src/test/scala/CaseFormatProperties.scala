import com.google.common.base.{CaseFormat, Converter}
import org.scalacheck.Gen._
import org.scalacheck.Prop.{AnyOperators, _}
import org.scalacheck.{Gen, Properties}

class CaseFormatProperties
  extends Properties("CaseFormatter") {

  val genCapitalizedString: Gen[String] = for {
    n <- choose(0, 10)
    upper <- alphaUpperChar
    lowers <- listOfN(n, alphaLowerChar)
  } yield (upper :: lowers) mkString ""

  val genUpperCamelCaseString: Int => Gen[String] = n => for {
    strings <- listOfN(n, genCapitalizedString)
  } yield strings mkString ""

  val camelToHyphen: Converter[String, String] = {
    CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_HYPHEN)
  }

  val hyphenToCamel: Converter[String, String] = {
    CaseFormat.LOWER_HYPHEN.converterTo(CaseFormat.UPPER_CAMEL)
  }

  property("a bunch of CaseFormatter properties") = forAll(posNum[Int]) { n =>
    forAll(genUpperCamelCaseString(n) :| "word") { x =>
      val y = camelToHyphen.convert(x)

      all(
        "number of hyphens" |: {
          y.count(_ == '-') ?= n - 1
        },
        "round-trip" |: {
          hyphenToCamel.convert(y) ?= x
        }
      )
    }
  }
}
