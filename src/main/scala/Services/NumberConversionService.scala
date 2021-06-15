package Services

import scala.annotation.tailrec
import scala.collection.immutable.{HashMap, TreeMap}
import scala.concurrent.{ExecutionContext, Future}

object NumberConversionService {

  private val romanNumberMap = TreeMap(
    1000 -> "M",
    900 -> "CM",
    500 -> "D",
    400 -> "CD",
    100 -> "C",
    90 -> "XC",
    50 -> "L",
    40 -> "XL",
    10 -> "X",
    9 -> "IX",
    5 -> "V",
    4 -> "IV",
    1 -> "I"
  )

  private def romanNumberConversion(number: Int): String = {
    @tailrec
    def helper(number: Int, acc: String = ""): String = {
      romanNumberMap.rangeTo(number).lastOption match {
        case Some((value, numeral)) => helper(number - value, acc + numeral)
        case _ => acc
      }
    }

    helper(number)
  }


  private def hexadecimalConversion(number: Int): String = {
    def mapNumberToHex(numeral: Int): String = {
      if (numeral < 10) {
        numeral.toString
      }
      else {
        (55 + numeral).toChar.toString
      }
    }

    @tailrec
    def helper(number: Int, acc: String = ""): String = {
      number match {
        case 0 => acc
        case _ =>
          val quotient = number / 16
          val reminder = number % 16
          helper(
            quotient,
            mapNumberToHex(reminder) + acc
          )
      }
    }

    helper(number)
  }


  private val numberConvertersMap: Map[String, Int => String] = HashMap(
    "roman" -> romanNumberConversion,
    "hexadecimal" -> hexadecimalConversion
  )

  def convert(conversionType: String, number: Int)(implicit ec: ExecutionContext): Future[Either[String, String]] = {
    Future(
      if (number < 0)
        Right("Illegal number lower than 0")
      else {
        numberConvertersMap.get(conversionType) match {
          case Some(converter) => Left(converter(number))
          case None => Right("No such conversion")
        }
      }
    )
  }
}
