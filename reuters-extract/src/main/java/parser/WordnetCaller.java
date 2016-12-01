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
        IIndexWord idxWord = this.makeIndexWord(wordStr);


        IWordID wordID = idxWord.getWordIDs().get(0);	// 1st meaning
        IWord word = dict.getWord(wordID);
        ISynset synset = word.getSynset();

        // iterate over words associated with the synset of the first meaning
        for (IWord w : synset.getWords()) {
            System.out.println(w.getLemma());
        }
    }

    public ISynset[] getSynsets(String wordStr) {
        IIndexWord idxWord = this.makeIndexWord(wordStr);
        if (idxWord == null) {
        	return new ISynset[0];
        }

        int senseCount = idxWord.getTagSenseCount();
        ISynset[] synsets = new ISynset[senseCount];

        for (int i = 0; i < senseCount; i++) {
            IWordID wordID = idxWord.getWordIDs().get(i);
            IWord word = dict.getWord(wordID);
            synsets[i] = word.getSynset();
        }
        return synsets;
    }

    public String getSynsetDefinition(ISynset syn) {
        return syn.getGloss();
    }

    public String stemWord(String wordStr) {
        List<String> stemmedWords = stemmer.findStems(wordStr, POS.NOUN);
        if (stemmedWords.size() == 0) {
        	return null;
        }
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
    
    public String[] stringify(String wordStr) {
    	 ISynset[] synsets = this.getSynsets(wordStr);
    	 if (synsets.length == 0) {
    		 return new String[0];
    	 }
    	 
    	 StringBuilder sb;

         // loop through every synset and print definition plus words
         int i = 0;
         String[] synsetData = new String[synsets.length];
         for (ISynset synset : synsets) {
        	 sb = new StringBuilder();
        	 sb.append("Synset");
        	 sb.append(i);
        	 sb.append(",");
             sb.append(synset.getGloss().replaceAll(",", " "));
             sb.append(" ");
             
             for (IWord w : synset.getWords()) {
                if (!w.getLemma().equals(wordStr)) {
                     sb.append(w.getLemma());
                     sb.append(" ");
                 }
             }
             
             synsetData[i] = sb.toString();
             i++;
         }
         return synsetData;
    }

    public void printInfo(String wordStr) {

        ISynset[] synsets = this.getSynsets(wordStr);

        // loop through every synset and print definition plus words
        int i = 0;
        for (ISynset synset : synsets) {
            System.out.println("Synset " + i + ": " + synset.getGloss());
            printUniqueWordsInSynset(synset, wordStr);
            i++;
        }
    }

    public IIndexWord makeIndexWord(String wordString) {
        String stemmedStr = stemWord(wordString);
        if (stemmedStr == null) {
        	return null;
        }
        IIndexWord idxWord = dict.getIndexWord(stemmedStr, POS.NOUN);
        return idxWord;
    }

    public void printWordsInSynset(ISynset syn) {
        for (IWord w : syn.getWords()) {
                System.out.print(w.getLemma() + " | ");
        }
    }

    public void printUniqueWordsInSynset(ISynset syn, String wordStr) {
        int i = 0;
        for (IWord w : syn.getWords()) {
            if (!w.getLemma().equals(wordStr) && i < syn.getWords().size() - 2) {
                System.out.print(w.getLemma() + " | ");
                i++;
            } else if (!w.getLemma().equals(wordStr)) {
                System.out.println(w.getLemma());
            }
        }
    }
}
