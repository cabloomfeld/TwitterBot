package twitterbot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Carly Bloomfeld
 */
public class Generator {
    
    BufferedReader reader;
    private HashMap chains = new HashMap();
    final int KEYLENGTH = 4;
    
    public Generator(String fileName) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(fileName));
    }
    
    private void makeChain() throws IOException {
        String key = "";
        while (!key.equals("****")) {
            char val = (char) reader.read();
            if (val != '@') {
                ArrayList suff = new ArrayList();
                if (chains.containsKey(key)) {
                    suff = (ArrayList) chains.get(key);
                }
                suff.add(val);
                chains.put(key, suff);
                if (key.length() < KEYLENGTH) {
                    key +=val;
                }
                else {
                    key = key.substring(1) + val;
                }
            }
        }
    }
    
    private char getNextChar(String key) {
        ArrayList suff = (ArrayList) chains.get(key);
        Random rand = new Random();
        int r = rand.nextInt(suff.size());
        char c = (char) suff.get(r);
        return c;
    }
    
    public String generate(int limit) throws IOException {
        makeChain();
        StringBuilder tweet = new StringBuilder();
        String key = "";
        char nextChar;
        for (int i = 0; i < limit; i++) {
            nextChar = getNextChar(key);
            tweet.append(nextChar);
            if (key.length() < KEYLENGTH) {
                key +=nextChar;
            }
            else {
                key = key.substring(1) + nextChar;
            }
        }
        return tweet.toString();
    }
}
