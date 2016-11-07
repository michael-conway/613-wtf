package parser;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Taylor Moore on 11/7/16.
 */
public class WordnetCaller {
    IDictionary dict;

    public WordnetCaller(String wordnetPath) throws IOException {
        String path = wordnetPath + File.separator + "dict";
        URL url = new URL("file", null, path);

        // construct the dictionary object and open it
        this.dict = new Dictionary(url);
        dict.open();
    }

    public void getSynonyms(String wordStr) {
        // look up first sense of whatever word is passed in
        IIndexWord idxWord = dict.getIndexWord(wordStr, POS.NOUN);
        IWordID wordID = idxWord.getWordIDs().get(0);	// 1st meaning
        IWord word = dict.getWord(wordID);
        ISynset synset = word.getSynset();

        // iterate over words associated with the synset of the first meaning
        for (IWord w : synset.getWords()) {
            System.out.println(w.getLemma());
        }
    }

    public ISynset[] getSynsets(String wordStr) {
        IIndexWord idxWord = dict.getIndexWord(wordStr, POS.NOUN);
        int senseCount = idxWord.getTagSenseCount();
        ISynset[] synsets = new ISynset[senseCount];

        for (int i = 0; i < senseCount; i++) {
            IWordID wordID = idxWord.getWordIDs().get(i);
            IWord word = dict.getWord(wordID);
            synsets[i] = word.getSynset();
        }
        return synsets;
    }
}
