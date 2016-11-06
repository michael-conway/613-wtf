package parser;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class WordnetCallerPlayground {
		
		public static void main(String[] args) throws IOException {			
			testDictionary();
		}
	
		public static void testDictionary() throws IOException {
			// construct the URL to the Wordnet dictionary directory
			String wnhome = "/Applications/WordNet-3.0/";
			String path = wnhome + File.separator + "dict";
			URL url = new URL("file", null, path);
			
			// construct the dictionary object and open it
			IDictionary dict = new Dictionary(url);
			dict.open();
	
			getSynonyms(dict);
			getSynsets(dict.getIndexWord("sleep", POS.NOUN), dict);
		}
		
		public static void getSynonyms(IDictionary dict) {
			// look up first sense of the word "dog"
			IIndexWord idxWord = dict.getIndexWord("sleep", POS.NOUN);
			IWordID wordID = idxWord.getWordIDs().get(0);	// 1st meaning
			IWord word = dict.getWord(wordID);
			ISynset synset = word.getSynset();
			
			// iterate over words associated with the synset
			for (IWord w : synset.getWords()) {
				System.out.println(w.getLemma());
			}
		}
		
		public static void getSynsets(IIndexWord idxWord, IDictionary dict) {
			int senseCount = idxWord.getTagSenseCount();
			ISynset synsets[] = new ISynset[senseCount];
			
			for (int i = 0; i < senseCount; i++) {
				IWordID wordID = idxWord.getWordIDs().get(i);
				IWord word = dict.getWord(wordID);
				synsets[i] = word.getSynset();
			}
		}
}
