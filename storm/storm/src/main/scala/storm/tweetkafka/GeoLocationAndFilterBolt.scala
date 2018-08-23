package storm.tweetkafka

import java.util
import java.util.Date

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple, Values}
import org.json.simple._

import scala.collection.immutable.HashSet
import scala.collection.mutable.Map
import scalaj.http._
import storm.util.{DateUtil, HashTagUtil, MatchUtil, StateUtil}

class GeoLocationAndFilterBolt extends IRichBolt{

  var collector: OutputCollector = _
  var hashtag_util: HashTagUtil = _
  var state_util: StateUtil = _
  var match_util: MatchUtil = _

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    this.collector = collector
    this.hashtag_util = new HashTagUtil()
    this.state_util = new StateUtil()
    this.match_util = new MatchUtil()
  }

  override def execute(input: Tuple): Unit = {

    try {
      val obj: JSONObject = JSONValue.parseWithException(input.getString(0)).asInstanceOf[JSONObject]

      if (obj.containsKey("lang") && "en".equals(obj.get("lang"))) {

        val text: String = obj.get("text").asInstanceOf[String]

        val entities: JSONObject = obj.get("entities").asInstanceOf[JSONObject]
        val hashtags: JSONArray = entities.get("hashtags").asInstanceOf[JSONArray]

        val hashTagsInTweet: util.List[String] = new util.ArrayList[String]()
        var hashTagsString : String = new String
        for (i <- 0 to hashtags.size() - 1) {
          val hashtag: JSONObject = hashtags.get(i).asInstanceOf[JSONObject]
          hashTagsInTweet.add(hashtag.get("text").asInstanceOf[String].toLowerCase())
          if(i!=hashtags.size() - 1)
          hashTagsString = hashTagsString + hashtag.get("text").asInstanceOf[String].toLowerCase() + ","
          else
            hashTagsString = hashTagsString + hashtag.get("text").asInstanceOf[String].toLowerCase()
        }

        val createdAt: String = obj.get("created_at").asInstanceOf[String]
        val dateTime: Date = DateUtil.getDate(createdAt)
        val currentDateTime: Date = DateUtil.getCurrentTime()

        // need to be removed
        val hours: Int = dateTime.getHours()
        val minutes: Int = dateTime.getMinutes()
        val month: Int = dateTime.getMonth() + 1
        val date: Int = dateTime.getDate()
        val curMonth :Int = currentDateTime.getMonth + 1

        val teamsString: String = match_util.getTeams(currentDateTime.getDate + "_" +curMonth )
        println("teams "+teamsString)
        //        val isDuringMatch: Boolean = DateUtil.isDuringMatch(hours,minutes)

        val teams: Array[String] = teamsString.split(",")

        val hashtagsFortheMatch: util.List[String] = new util.ArrayList[String]()
        hashtagsFortheMatch.addAll(hashtag_util.getTagList(teams(0)))
        hashtagsFortheMatch.addAll(hashtag_util.getTagList(teams(1)))

        val handlesFortheMatch: util.List[String] = new util.ArrayList[String]()
        handlesFortheMatch.addAll(hashtag_util.getHandlesForTeam(teams(0)))
        handlesFortheMatch.addAll(hashtag_util.getHandlesForTeam(teams(1)))


        val tweetRelavent:Boolean = match_util.tweetRelaventToMatch(handlesFortheMatch, hashtagsFortheMatch, text, hashTagsInTweet)
        if ((currentDateTime.getMonth()== dateTime.getMonth && currentDateTime.getDate()== dateTime.getDate() && tweetRelavent)){

        val place: JSONObject = obj.get("place").asInstanceOf[JSONObject]
        if (place != null) {
          val bounding_box: JSONObject = place.get("bounding_box").asInstanceOf[JSONObject]
          if (bounding_box != null) {

            val coordinates: JSONArray = bounding_box.get("coordinates").asInstanceOf[JSONArray]
            val coordinates_nested_1: JSONArray = coordinates.get(0).asInstanceOf[JSONArray]
            val coordinates_nested_2: JSONArray = coordinates_nested_1.get(0).asInstanceOf[JSONArray]

            val latitude: Double = coordinates_nested_2.get(1).asInstanceOf[Double]
            val longitude: Double = coordinates_nested_2.get(0).asInstanceOf[Double]

            val url = "http://54.201.181.84:8081/state?lat=" + latitude + "&lon=" + longitude
            println(url)
            val responsehttp: HttpResponse[String] = Http(url).asString

            val responseBody: String = responsehttp.body
            val state: String = responseBody
            collector.emit(new Values(text,createdAt, state, hashTagsString, teamsString))

          }
        }
      }
      }
      else {

      }

    } catch {
      case e: Exception =>
        println("Error parsing tweet: " + e.getMessage)
    }
  }

  override def cleanup(): Unit = {

  }

  override def getComponentConfiguration: util.Map[String, AnyRef] = {
    return null
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields( "tweet_text", "tweet_created_at","state", "hashtags", "teams"))
  }
}