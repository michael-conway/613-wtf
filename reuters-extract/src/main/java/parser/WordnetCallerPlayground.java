package parser;

import java.io.IOException;

public class WordnetCallerPlayground {
		
		public static void main(String[] args) throws IOException {			
			testDictionary();
		}

		public static void testDictionary() throws IOException {
			// construct the URL to the Wordnet dictionary directory
            // have to give it a valid path to the wordnet library download
			String wnhome = "/Applications/WordNet-3.0/";
			WordnetCaller wnCaller = new WordnetCaller(wnhome);
			wnCaller.getSynonyms("explosion");
		}
}
