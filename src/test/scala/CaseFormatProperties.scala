import com.google.common.base.CaseFormat
import org.scalacheck.Gen._
import org.scalatest.PropSpec
import org.scalacheck.{Gen, Prop}
import org.scalatest.prop.{Checkers, GeneratorDrivenPropertyChecks}

class CaseFormatProperties
  extends PropSpec
  with Checkers
  with GeneratorDrivenPropertyChecks {

  val capitalizedStringGen: Gen[String] = for {
    n <- choose(0, 10)
    upper <- alphaUpperChar
    lowers <- listOfN(n, alphaLowerChar)
  } yield (upper :: lowers) mkString ""

  val upperCamelCaseStringGen: Int => Gen[String] = n => for {
    strings <- listOfN(n, capitalizedStringGen)
  } yield strings mkString ""

  check {
    val caseConverter = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_HYPHEN)

    Prop.forAll(posNum[Int]) { n =>
      Prop.forAll(upperCamelCaseStringGen(n)) { x =>
        val y = caseConverter.convert(x)

        y.count(_ == '-') === n - 1
      }
    }
  }
}
