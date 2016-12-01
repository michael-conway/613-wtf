/**
 * 
 */
package wordnet.emitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mconway parse output line of file that is the result of getting the best tfidf words
 *
 */
public class GoodWordsParser {
	
	public static final Logger log = LoggerFactory
			.getLogger(GoodWordsParser.class);
	
	public GoodWordsFromTfidf parseGoodWordsLine(final String line) {
		String[] columns = line.split(",");
		if (columns.length < 2) {
			log.error("cannot process line:{}", line);
			throw new RuntimeException("cannot parse good words");
		}
		
		GoodWordsFromTfidf goodWords = new GoodWordsFromTfidf();
		goodWords.setDocName(columns[0]);
		String[] words = columns[1].split("\\|");
		for(String word : words) {
			goodWords.getGoodWords().add(word);
		}
		return goodWords;
	}

}
