package twitter.TwitterKafka;

/**
 * Hello world!
 *
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.twitter.hbc.core.endpoint.Location;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterKafka {

	private static final String topic = "ipl-tweets";

	public static void run(String consumerKey, String consumerSecret,
			String token, String secret) throws InterruptedException {

		Properties properties = new Properties();
		properties.put("metadata.broker.list", "localhost:9092");
		properties.put("serializer.class", "kafka.serializer.StringEncoder");
		properties.put("client.id","camus");
		ProducerConfig producerConfig = new ProducerConfig(properties);
		kafka.javaapi.producer.Producer<String, String> producer = new kafka.javaapi.producer.Producer<String, String>(
				producerConfig);

		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.trackTerms(Lists.newArrayList("yellove",
                				"whistlepodu", "chennaisuperkings", "csk ", "rcb ", "playbold", "viratkohli",
                				"srh","orangearmy","sunrisers","kkr","korbolorbojeetbo","kkrhaitaiyaar","kkrfan",
                                 "cricketmerijaan","mumbaiindians","shreyasiyer","hallabol","rajasthanroyals",
                                "jazbajeetka","kingsxipunjab","livepunjabiplaypunjab","kxip","@ChennaiIPL","@msdhoni","@ImRaina",
								"@RayuduAmbati","@DJBravo47","@ShaneRWatson33","@RCBTweets","@ImKohli18","@ABdeVilliers17",
								"@yuzi_chahal","@y_umesh","@SunRisers","@KanyWilliamson","@rashidkhan_19","@BhuviOfficial",
								"@iamyusufpathan","@KKRiders","@DineshKarthik","@SunilPNarine74","@Russell12A","@mipaltan",
								"@MarkandeMayank","@surya_14kumar","@ImRo45","@KieronPollard55","@DelhiDaredevils",
								"@ShreyasIyer8","@trent_boult","@RishabPant777","@rajasthanroyals","@ajinkyarahane88","@IamSanjuSamson",
								"@benstokes38","@JUnadkat","@lionsdenkxip","@ashwinravi99","@henrygayle","@klrahul11","@Mujeeb_Zadran",
                                 "vivoipl","vivoipl2018","vivoiplonstar","bestvsbest","@ChennaiIPL","@mipaltan","@RCBTweets","@lionsdenkxip","@rajasthanroyals",
                                 "@SunRisers","@KKRiders","@DelhiDaredevils"));

//        endpoint.locations(Arrays.asList(
//				new Location(
//						new Location.Coordinate(68.1,8.06),
//						new Location.Coordinate(97.41,37.10))));

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token,
				secret);
		// Authentication auth = new BasicAuth(username, password);

		// Create a new BasicClient. By default gzip is enabled.
		Client client = new ClientBuilder().hosts(Constants.STREAM_HOST)
				.endpoint(endpoint).authentication(auth)
				.processor(new StringDelimitedProcessor(queue)).build();

		// Establish a connection
		client.connect();

		// Do whatever needs to be done with messages
		for (int msgRead = 0; msgRead < 100000; msgRead++) {
			KeyedMessage<String, String> message = null;
			try {
				message = new KeyedMessage<String, String>(topic, queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			producer.send(message);
			System.out.print(message);
		}
		producer.close();
		client.stop();

	}

	public static void main(String[] args) {
		try {
			TwitterKafka.run("ibKVFwn6prLt6uHlOmvs1cTBu", "6fujwa7Q3nAJdhEjH20NSMdqdRWzzQWXPAGsbYkIZ3qIvnMiOT", "1327782752-CnVRtLcEOLuOBlYhhFowfjli2bvjWqrSPbWxVgx", "7KAva5NHSc1ExAf3Ci3cmJFxwsQU4nlkoIIyHl9nCUeUc");
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}

