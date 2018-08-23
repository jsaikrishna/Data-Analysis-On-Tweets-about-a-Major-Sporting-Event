import sbt._


object Dependencies {

  val mapreduceLibraryDependencies = Seq("org.apache.hadoop" % "hadoop-core" % "1.2.1")

  val stormLibraryDependencies = Seq("org.apache.storm" % "storm-core" % "1.2.1" % Provided exclude("ring-cors", "ring-cors") ,

    "org.twitter4j" % "twitter4j-core" % "3.0.3" exclude("org.slf4j", "slf4j-log4j12"),
    "org.twitter4j" % "twitter4j-stream" % "3.0.3" exclude("org.slf4j", "slf4j-log4j12"),
    "com.googlecode.json-simple" % "json-simple" % "1.1" exclude("org.slf4j", "slf4j-log4j12"),
    "org.apache.storm" % "storm-kafka" % "1.2.1" exclude("org.apache.zookeeper", "zookeeper") exclude("org.slf4j", "slf4j-log4j12"),
    "org.apache.kafka" % "kafka_2.11" % "1.0.0" exclude("org.slf4j", "slf4j-log4j12") exclude("org.apache.zookeeper", "zookeeper")exclude("org.slf4j", "slf4j-log4j12") ,
    "org.apache.zookeeper" % "zookeeper" % "3.4.6" exclude("org.slf4j", "slf4j-log4j12"),
    "edu.stanford.nlp" % "stanford-corenlp" % "3.9.1"  ,
    "edu.stanford.nlp" % "stanford-corenlp" % "3.9.1"  % Provided classifier "models",
    "org.scalaj" %% "scalaj-http" % "2.3.0").map(_.exclude("org.slf4j", "*"))

}



