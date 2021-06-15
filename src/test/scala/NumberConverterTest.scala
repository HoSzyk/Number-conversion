import Services.NumberConversionService
import org.scalatest.funsuite.AsyncFunSuite

class NumberConverterTest extends AsyncFunSuite {
  test("should convert number to hexadecimal and roman") {
    for {
      romanResult <- NumberConversionService.convert("roman", 1)
      hexResult <- NumberConversionService.convert("hexadecimal", 1)
    } yield {
      assert(romanResult == Left("I"))
      assert(hexResult == Left("1"))
    }
  }

  test("should return \"Illegal number lower than 0\" when number is lower than 0") {
    for {
      romanResult <- NumberConversionService.convert("roman", -1)
      hexResult <- NumberConversionService.convert("hexadecimal", -1)
    } yield {
      assert(romanResult == Right("Illegal number lower than 0"))
      assert(hexResult == Right("Illegal number lower than 0"))
    }
  }

  test("should convert number to roman correctly") {
    NumberConversionService.convert("roman", 11) map {
      result => assert(result == Left("XI"))
    }
  }

  test("should convert number to hexadecimal correctly") {
    NumberConversionService.convert("hexadecimal", 11) map {
      result => assert(result == Left("B"))
    }
  }

  test("should return \"Illegal number lower than 0\" when specifying wrong conversion type ") {
    NumberConversionService.convert("wrongconversion", 11) map {
      result => assert(result == Right("No such conversion"))
    }
  }
}
