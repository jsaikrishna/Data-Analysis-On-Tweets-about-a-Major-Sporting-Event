package storm.tweetkafka

import java.util
import java.util.Date

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple, Values}
import org.json.simple._

import scala.collection.immutable.HashSet
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import scalaj.http._
import storm.util._
import storm.util.SentimentAnalysisUtils._


class SentimentAnalysisBolt extends IRichBolt{

  var states: Map [String,ListBuffer[Int]] = Map()

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
  states+= ("Jammu and Kashmir"->ListBuffer(0, 0))
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
  states +=("Lakshadweep"->ListBuffer(0, 0))
  states +=("Daman and Diu"->ListBuffer(0, 0))
  states+=("Dadra and Nagar Haveli"->ListBuffer(0, 0))
  states+=("Andaman and Nicobar Islands"->ListBuffer(0, 0))

  var collector: OutputCollector = _
  var hashtag_util: HashTagUtil = _
  var state_util: StateUtil = _
  var match_util: MatchUtil = _
  var poll0: Int = 0
  var poll1: Int = 0

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    this.collector = collector
    this.hashtag_util = new HashTagUtil()
    this.state_util = new StateUtil()
    this.match_util = new MatchUtil()
  }

  override def execute(input: Tuple): Unit = {

    val createdAt: String = input.getStringByField("tweet_created_at")
    val state: String = input.getStringByField("state")
    val dateTime: Date = DateUtil.getDate(createdAt)

    // need to be removed
    val hours: Int = dateTime.getHours()
    val minutes: Int = dateTime.getMinutes()
    val month: Int = dateTime.getMonth() + 1
    val date: Int = dateTime.getDate()

    if (DateUtil.beforeMatch(hours, minutes)) {
      val text: String = input.getStringByField("tweet_text")
      val textcreatedat: String = input.getStringByField("tweet_created_at")
      val iplteams: String = input.getStringByField("teams")
      val hash: String = input.getStringByField("hashtags")

      val teams: Array[String] = iplteams.split(",")
      val hashTagsInTweet: util.List[String] = new util.ArrayList[String]()
      val hashtags: Array[String] = hash.split(",")

      var handle: String = new String
      for (handle <- hashtags) {
        hashTagsInTweet.add(handle)
      }

      val hashtagsFortheMatchTeam0: util.List[String] = new util.ArrayList[String]()
      hashtagsFortheMatchTeam0.addAll(hashtag_util.getTagList(teams(0)))
      val hashtagsFortheMatchTeam1: util.List[String] = new util.ArrayList[String]()
      hashtagsFortheMatchTeam1.addAll(hashtag_util.getTagList(teams(1)))

      val handlesFortheMatchTeam0: util.List[String] = new util.ArrayList[String]()
      handlesFortheMatchTeam0.addAll(hashtag_util.getHandlesForTeam(teams(0)))
      val handlesFortheMatchTeam1: util.List[String] = new util.ArrayList[String]()
      handlesFortheMatchTeam1.addAll(hashtag_util.getHandlesForTeam(teams(1)))


      val finalpoll: Boolean = match_util.getPoll(handlesFortheMatchTeam0, handlesFortheMatchTeam1, hashtagsFortheMatchTeam0, hashtagsFortheMatchTeam1, text, hashTagsInTweet)
      //val count :List[Int] = states.get("dd").get
      //val value :Int = count(0)
      var count = new ListBuffer[Int]()

      if (finalpoll == true) {
        println(teams(1)+"Tweet:"+text)
        if (states.contains(state)) {
          count = states.get(state).get
          count(1) = count(1) + 1
          states.put(state, count)
          if(state_util.containsState(state)){

          val code:String = state_util.getCodeForState(state)
          val url:String = "http://54.201.254.50:9200/support/doc/"
            val percent = count(1)/(count(0)+count(1))
          var json :String = JsonUtil.getSupportJson(state,percent)
          val resp :String = Http(url+code).put(json).header("content-type", "application/json").asString.body

        }
        }
      }


      else {
        if (states.contains(state)) {
          count = states.get(state).get
          count(0) = count(0) + 1
          states.put(state, count)
          if(state_util.containsState(state)){

          val code:String = state_util.getCodeForState(state)
          val url:String = "http://54.201.254.50:9200/support/doc/"
          val percent = count(0)/(count(0)+count(1))
          var json :String = JsonUtil.getSupportJson(state,percent)
          val resp :String = Http(url+code).put(json).header("content-type", "application/json").asString.body

        }
        }
      }
      var teampoll0 : Double = 0
      teampoll0 =0
      var teampoll1 : Double = 0
      teampoll1 = 0
      for ((k,v) <- states) {
        teampoll0 = teampoll0 + v(0)
        teampoll1 = teampoll1 + v(1)
      }
      var overallpoll0 : Double = 0
      overallpoll0 = (teampoll0/(teampoll0+teampoll1))*100
      if(overallpoll0>0 || overallpoll0 == 0) {
        val code: String = teams(0)
        val url: String = "http://54.201.254.50:9200/piechart/doc/"
        val json: String = JsonUtil.getPollJson(teams(0), overallpoll0)
        val resp: String = Http(url + code).put(json).header("content-type", "application/json").asString.body
      }
      var overallpoll1 : Double = 0
      overallpoll1 = (teampoll1 / (teampoll0 + teampoll1)) * 100
      if(overallpoll1>0 || overallpoll1 == 0) {
        val code: String = teams(1)
        val url: String = "http://54.201.254.50:9200/piechart/doc/"
        val json: String = JsonUtil.getPollJson(teams(1), overallpoll1)
        val resp: String = Http(url + code).put(json).header("content-type", "application/json").asString.body
      }
    }
  }

  override def cleanup(): Unit = {

  }

  override def getComponentConfiguration: util.Map[String, AnyRef] = {
    return null
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {

  }

}