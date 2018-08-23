package storm.tweetkafka

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.{IRichBolt, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple, Values}
import storm.util.HashTagUtil

class PlayersEmitBolt extends IRichBolt{

  var hashtag_util: HashTagUtil = _
  var collector: OutputCollector = _

  override def getComponentConfiguration: util.Map[String, AnyRef] = {
    return null
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields( "player", "count"))

  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    this.hashtag_util = new HashTagUtil()
    this.collector = collector

  }

  override def execute(input: Tuple): Unit = {
    val text: String = input.getStringByField("tweet_text")
    val list: util.List[String] = hashtag_util.getUsers(text)

    for (i <- 0 to list.size() - 1){
      val player:String = list.get(i)
      if(hashtag_util.containsPlayer(player))
      collector.emit(new Values(player,"1"))
    }

  }

  override def cleanup(): Unit = {

  }
}
