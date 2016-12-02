/**
 * 
 */
package wordnet.emitter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mconway  file name and associated synset data
 *
 */
public class FileAndSynset {
	private String fileName;
	private List<String> synsetData = new ArrayList<String>();
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<String> getSynsetData() {
		return synsetData;
	}
	public void setSynsetData(List<String> synsetData) {
		this.synsetData = synsetData;
	}

}
