/**
 * 
 */
package wordnet.emitter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mconway - 
 *
 */
public class GoodWordsFromTfidf {
	
	private String docName;
	private List<String> goodWords = new ArrayList<String>();
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public List<String> getGoodWords() {
		return goodWords;
	}
	public void setGoodWords(List<String> goodWords) {
		this.goodWords = goodWords;
	}

}
