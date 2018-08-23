package storm.util
import storm.util.SentimentAnalysisUtils._

import scala.collection.mutable.{ListBuffer, Map}

object test {
  def main(args: Array[String]) {
    println("Hello, world!")
    var states: Map[String, ListBuffer[Int]] = Map()

    states += ("Andhra Pradesh" -> ListBuffer(0, 0))
    states += ("Arunachal Pradesh" -> ListBuffer(0, 0))
    states += ("Assam" -> ListBuffer(0, 0))
    states += ("Bihar" -> ListBuffer(0, 0))
    states += ("Chhattisgarh" -> ListBuffer(0, 0))
    states += ("Goa" -> ListBuffer(0, 0))
    states += ("Gujarat" -> ListBuffer(0, 0))
    states += ("Haryana" -> ListBuffer(0, 0))
    states += ("Himachal Pradesh" -> ListBuffer(0, 0))
    states += ("Jharkhand" -> ListBuffer(0, 0))
    states += ("Jammu and Kashmir" -> ListBuffer(0, 0))
    states += ("Karnataka" -> ListBuffer(0, 0))
    states += ("Kerala" -> ListBuffer(0, 0))
    states += ("Madhya Pradesh" -> ListBuffer(0, 0))
    states += ("Maharashtra" -> ListBuffer(0, 0))
    states += ("Manipur" -> ListBuffer(0, 0))
    states += ("Meghalaya" -> ListBuffer(0, 0))
    states += ("Mizoram" -> ListBuffer(0, 0))
    states += ("Nagaland" -> ListBuffer(0, 0))
    states += ("Odisha" -> ListBuffer(0, 0))
    states += ("Punjab" -> ListBuffer(0, 0))
    states += ("Rajasthan" -> ListBuffer(0, 0))
    states += ("Sikkim" -> ListBuffer(0, 0))
    states += ("Tamil Nadu" -> ListBuffer(0, 0))
    states += ("Telangana" -> ListBuffer(0, 0))
    states += ("Tripura" -> ListBuffer(0, 0))
    states += ("Uttar Pradesh" -> ListBuffer(0, 0))
    states += ("Uttarakhand" -> ListBuffer(0, 0))
    states += ("West Bengal" -> ListBuffer(0, 0))
    states += ("Chandigarh" -> ListBuffer(0, 0))
    states += ("Delhi" -> ListBuffer(0, 0))
    states += ("Pondicherry" -> ListBuffer(0, 0))
    states += ("Lakshadweep" -> ListBuffer(0, 0))
    states += ("Daman and Diu" -> ListBuffer(0, 0))
    states += ("Dadra and Nagar Haveli" -> ListBuffer(0, 0))
    states += ("Andaman and Nicobar Islands" -> ListBuffer(0, 0))

    for ((k, v) <- states) {
      printf("key: %s, value: %s\n", k, v(0))

      //println("THE SENTIMENT IS:" + sentiment)
    }
  }
}


