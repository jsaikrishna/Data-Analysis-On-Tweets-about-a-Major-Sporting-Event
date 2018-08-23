package storm.tweetkafka

import java.util
import java.util.Date

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.Tuple
import scalaj.http.Http
import storm.util._

import scala.collection.mutable.Map

class MatchFollowersBolt extends IRichBolt{

  var states: Map[String,Int] = Map()

  states += ("Andhra Pradesh" -> 0)
  states += ("Arunachal Pradesh" -> 0)
  states += ("Assam" -> 0)
  states += ("Bihar" -> 0)
  states += ("Chhattisgarh" -> 0)
  states += ("Goa" -> 0)
  states += ("Gujarat" -> 0)
  states += ("Haryana" -> 0)
  states += ("Himachal Pradesh" -> 0)
  states += ("Jharkhand" -> 0)
  states+= ("Jammu and Kashmir"->0)
  states += ("Karnataka" -> 0)
  states += ("Kerala" -> 0)
  states += ("Madhya Pradesh" -> 0)
  states += ("Maharashtra" -> 0)
  states += ("Manipur" -> 0)
  states += ("Meghalaya" -> 0)
  states += ("Mizoram" -> 0)
  states += ("Nagaland" -> 0)
  states += ("Odisha" -> 0)
  states += ("Punjab" -> 0)
  states += ("Rajasthan" -> 0)
  states += ("Sikkim" -> 0)
  states += ("Tamil Nadu" -> 0)
  states += ("Telangana" -> 0)
  states += ("Tripura" -> 0)
  states += ("Uttar Pradesh" -> 0)
  states += ("Uttarakhand" -> 0)
  states += ("West Bengal" -> 0)
  states += ("Chandigarh" -> 0)
  states += ("Delhi" -> 0)
  states += ("Pondicherry" -> 0)
  states +=("Lakshadweep"->0)
  states +=("Daman and Diu"->0)
  states+=("Dadra and Nagar Haveli"->0)
  states+=("Andaman and Nicobar Islands"->0)

  var collector: OutputCollector = _
  var hashtag_util: HashTagUtil = _
  var state_util: StateUtil = _
  var match_util: MatchUtil = _
  var completed: Int = 0

  override def getComponentConfiguration: util.Map[String, AnyRef] = {
    return null
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {

  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    this.collector = collector
    this.hashtag_util = new HashTagUtil()
    this.state_util = new StateUtil()
    this.match_util = new MatchUtil()
  }

  override def execute(input: Tuple): Unit = {
    try {

      val createdAt: String = input.getStringByField("tweet_created_at")
      val state:String = input.getStringByField("state")
      val dateTime: Date = DateUtil.getDate(createdAt)

      // need to be removed
      val hours: Int = dateTime.getHours()
      val minutes: Int = dateTime.getMinutes()
      val month: Int = dateTime.getMonth() + 1
      val date: Int = dateTime.getDate()

      if (DateUtil.isDuringMatch(hours,minutes)){

        completed =0
        if (state != null) {
          var counter: Int = states(state)
          counter = counter + 1
          states.put(state, counter)
          if(state_util.containsState(state)){

            val code:String = state_util.getCodeForState(state)
            val url:String = "http://54.201.254.50:9200/followers/doc/"
            var json :String = JsonUtil.getJson(state,states.get(state).get)
            val resp :String = Http(url+code).put(json).header("content-type", "application/json").asString.body

          }

        }
      }
      else
      {
        if(DateUtil.isAfterMatch(hours,minutes) && completed==0)
        {
          for((k,v) <- states){
            states.put(k,0)
          }
        }

        else
        {
          println("Not during match")
        }
      }

    } catch {
      case e: Exception =>
        println("Error parsing tweet: " + e.getMessage)
    }
  }

  override def cleanup(): Unit = {

  }
}
