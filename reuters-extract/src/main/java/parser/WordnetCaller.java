package parser;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import edu.mit.jwi.morph.WordnetStemmer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Taylor Moore on 11/7/16.
 */
public class WordnetCaller {
    IDictionary dict;
    WordnetStemmer stemmer;

    public WordnetCaller(String wordnetPath) throws IOException {
        String path = wordnetPath + File.separator + "dict";
        URL url = new URL("file", null, path);

        // construct the dictionary object and open it
        this.dict = new Dictionary(url);
        dict.open();

        // construct the stemmer to stem words before using Wordnet for
        this.stemmer = new WordnetStemmer(this.dict);
    }

    public void getSynonyms(String wordStr) {
        // look up first sense of whatever word is passed in
        String stemmedStr = stemWord(wordStr);
        IIndexWord idxWord = dict.getIndexWord(stemmedStr, POS.NOUN);
        IWordID wordID = idxWord.getWordIDs().get(0);	// 1st meaning
        IWord word = dict.getWord(wordID);
        ISynset synset = word.getSynset();

        // iterate over words associated with the synset of the first meaning
        for (IWord w : synset.getWords()) {
            System.out.println(w.getLemma());
        }
    }

    public ISynset[] getSynsets(String wordStr) {
        String stemmedStr = stemWord(wordStr);
        IIndexWord idxWord = dict.getIndexWord(stemmedStr, POS.NOUN);
        int senseCount = idxWord.getTagSenseCount();
        ISynset[] synsets = new ISynset[senseCount];

        for (int i = 0; i < senseCount; i++) {
            IWordID wordID = idxWord.getWordIDs().get(i);
            IWord word = dict.getWord(wordID);
            synsets[i] = word.getSynset();
        }
        return synsets;
    }

    public String stemWord(String wordStr) {
        List<String> stemmedWords = stemmer.findStems(wordStr, POS.NOUN);
        return stemmedWords.get(0);
    }

    public String[] synsetWordsToStringArray(ISynset syn) {
        // make an array of Strings to store each word in the synset in
        String[] wordsString = new String[syn.getWords().size()];
        List<IWord> words = syn.getWords();
        int i  = 0;

        for (IWord word : words) {
            wordsString[i] = word.getLemma();
            i++;
        }
        return wordsString;
    }
}
