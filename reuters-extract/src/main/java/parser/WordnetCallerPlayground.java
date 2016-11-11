package parser;

import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;

import java.io.IOException;
import java.util.List;

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
            ISynset[] synsets = wnCaller.getSynsets("explosion");
            String[] synsetWords = wnCaller.synsetWordsToStringArray(synsets[0]);
		}
}
