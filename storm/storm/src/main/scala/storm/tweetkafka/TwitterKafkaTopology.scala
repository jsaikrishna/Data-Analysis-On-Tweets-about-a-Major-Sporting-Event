package storm.tweetkafka

import org.apache.storm.kafka._
import org.apache.storm.spout.SchemeAsMultiScheme
import org.apache.storm.{Config, LocalCluster, StormSubmitter}
import org.apache.storm.topology.TopologyBuilder
import org.apache.storm.tuple.Fields

object TwitterKafkaTopology {

  def main(args: Array[String]): Unit = {

    val zkConnString: String = "34.217.111.247:2181"
    val hosts: BrokerHosts = new ZkHosts(zkConnString,"/brokers")
    val topic: String = "ipl-tweets"
    val kafkaSpoutConfig: SpoutConfig = new SpoutConfig(hosts, topic, "/ipltweet", "ipltweet11")

    kafkaSpoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme())
    val topologyBuilder: TopologyBuilder = new TopologyBuilder()

    topologyBuilder.setSpout("kafkaspout", new KafkaSpout(kafkaSpoutConfig))

    topologyBuilder.setBolt("GeolocationAndFilter",
      new GeoLocationAndFilterBolt(), 1).shuffleGrouping("kafkaspout")

    topologyBuilder.setBolt("match-followers",
      new MatchFollowersBolt(), 1).fieldsGrouping("GeolocationAndFilter",new Fields("state"))

    topologyBuilder.setBolt("team-poll",
      new SentimentAnalysisBolt(), 1).fieldsGrouping("GeolocationAndFilter", new Fields("state"))

    topologyBuilder.setBolt("playerEmit",
      new PlayersEmitBolt(), 1).shuffleGrouping("GeolocationAndFilter")

    topologyBuilder.setBolt("top-player",
      new TopPlayerCount(), 1).fieldsGrouping("playerEmit",new Fields("player"))

    val conf = new Config()
    conf.setDebug(false)

    conf.setNumWorkers(5)
    StormSubmitter.submitTopology("twitter",
      conf, topologyBuilder.createTopology())

//    val cluster: LocalCluster = new LocalCluster()
//    conf.setMaxTaskParallelism(6)
//    cluster.submitTopology("wordCounter", conf, topologyBuilder.createTopology())
//    Thread.sleep(100 * 1000)
//    cluster.killTopology("wordCounter")

  }
}
