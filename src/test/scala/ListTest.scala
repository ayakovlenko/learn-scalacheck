import org.scalatest.PropSpec
import org.scalatest.prop.Checkers

class ListTest extends PropSpec
  // with GeneratorDrivenPropertyChecks
  with Checkers {

  // TODO: http://www.scalatest.org/user_guide/property_based_testing
  // TODO: http://www.scalatest.org/user_guide/table_driven_property_checks 
  // TODO: http://www.scalatest.org/user_guide/generator_driven_property_checks
  // TODO: http://www.scalatest.org/user_guide/writing_scalacheck_style_properties

  property("a.size + b.size should be equal to (a `concat` b).size") {
    check { (a: List[Int], b: List[Int]) =>
      a.size + b.size == (a ::: b).size
    }
  }

  property("reversing a list twice should be equal to the original list") {
    check { ls: List[Int] =>
      ls.reverse.reverse == ls
    }
  }

  property("the first element should become the last element after reversal") {
    check { ls: List[Int] =>
      ls.headOption == ls.reverse.lastOption
    }
  }

  property("reversing a list has no effect on summing the list") {
    check { ls: List[Int] =>
      ls.sum == ls.reverse.sum
    }
  }
}
