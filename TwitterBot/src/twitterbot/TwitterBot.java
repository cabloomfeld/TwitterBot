package twitterbot;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Carly Bloomfeld
 */
public class TwitterBot {

    public static void main(String[] args) throws FileNotFoundException, IOException, TwitterException {
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("***")
                .setOAuthConsumerSecret("***")
                .setOAuthAccessToken("***")
                .setOAuthAccessTokenSecret("***");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        
        PrintWriter writer = new PrintWriter(new FileWriter("feed.txt"), true);
        ResponseList<Status> timeline = twitter.getHomeTimeline();
        for (int i = 2; i < 13; i++) {
            Paging p = new Paging(i);
            ResponseList<Status> page = twitter.getHomeTimeline(p);
            timeline.addAll(page);
        }
        ListIterator<Status> it = timeline.listIterator();
        while (it.hasNext()) {
            Status st = it.next();
            String status = st.getText();
            writer.println(status);
            //System.out.println(status);
        }
        writer.println("****");
        writer.close(); 
        
        //Generator gen = new Generator("bios.txt");
        //Generator gen = new Generator("tweets.txt");
        Generator gen = new Generator("feed.txt");
        String tweet = gen.generate(140);
        //System.out.println(tweet);
        twitter.updateStatus(tweet);
    }
    
}
