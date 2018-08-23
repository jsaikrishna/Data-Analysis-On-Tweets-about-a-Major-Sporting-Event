package storm.tweetkafka

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.Tuple
import scalaj.http.Http
import storm.util.JsonUtil

class TopPlayerCount extends IRichBolt{

  val map : util.Map[String,Int] = new util.HashMap[String,Int]();

  override def getComponentConfiguration: util.Map[String, AnyRef] = {

    return null
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {

  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector): Unit = {

  }

  override def execute(input: Tuple): Unit = {

    val player:String = input.getStringByField("player")
    if(map.containsKey(player)){
      var count :Int = map.get(player)
      count  =count +1
      map.put(player,count)

      val url:String = "http://54.201.254.50:9200/top/doc/"
      var json :String = JsonUtil.getTopJson(player,count)
      val resp :String = Http(url+player).put(json).header("content-type", "application/json").asString.body
    }
    else{
      map.put(player,1)
      val url:String = "http://54.201.254.50:9200/top/doc/"
      var json :String = JsonUtil.getTopJson(player,1)
      val resp :String = Http(url+player).put(json).header("content-type", "application/json").asString.body
    }

  }

  override def cleanup(): Unit = {

  }
}
