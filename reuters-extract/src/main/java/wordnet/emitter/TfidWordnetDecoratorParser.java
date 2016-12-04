/**
 * 
 */
package wordnet.emitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import parser.WordnetCaller;

/**
 * @author mconway Read in a tfidf doc file and amend each line with the wordnet
 *         synsets
 */
public class TfidWordnetDecoratorParser {

	public static final Logger log = LoggerFactory
			.getLogger(TfidWordnetDecoratorParser.class);

	private String tfidfInputFile;
	private String decoratedTfidfOutputFile;
	private String wordnetBasePath;
	private BufferedReader br;
	private PrintWriter bw;
	private WordnetCaller wnCaller;
	private boolean includeGlossary = false;

	public TfidWordnetDecoratorParser(String tfidfInputFile,
			String decoratedTfidfOutputFile, String wordnetBasePath) {
		super();
		this.tfidfInputFile = tfidfInputFile;
		this.decoratedTfidfOutputFile = decoratedTfidfOutputFile;
		this.wordnetBasePath = wordnetBasePath;

		File sourceAsFile = new File(tfidfInputFile);

		if (!sourceAsFile.exists()) {
			log.error("source file does not exist");
			throw new IllegalArgumentException("source file does not exist");
		}

		try {
			br = new BufferedReader(new FileReader(sourceAsFile));
			File targetAsFile = new File(decoratedTfidfOutputFile);
			log.info("deleting targetAsFile:{}", targetAsFile);
			targetAsFile.delete();
			targetAsFile.getParentFile().mkdirs();
			targetAsFile.createNewFile();
			bw = new PrintWriter(new FileWriter(targetAsFile));
		} catch (IOException e) {
			throw new RuntimeException("unable to create reader", e);
		}
		
		try {
			wnCaller = new WordnetCaller(wordnetBasePath);
		} catch (IOException e) {
			log.error("cannot create wordnet caller", e);
		}

	}
	
	public void process() {
		// read each line from the csv file
		GoodWordsParser goodWordsParser = new GoodWordsParser();
		try {
			//String line = br.readLine();
			//bw.println(line);  //header
			String line= br.readLine();

			GoodWordsFromTfidf goodWordsFromTfidf;
			while (line != null) {
				bw.println(line);
				goodWordsFromTfidf = goodWordsParser.parseGoodWordsLine(line);
				for (String word : goodWordsFromTfidf.getGoodWords()) {
					String[] synsetLines = getSynsetLines(word);
					
					for(String synsetLine : synsetLines) {
						putOutSynsetLine(synsetLine);
					}
				}
				line = br.readLine();
				
				
			}
			
			bw.close();
			
			
		} catch (IOException e) {
			throw new RuntimeException("exception processing data", e);

		} 
		
		
	}

	private void putOutSynsetLine(String synsetLine) {

		log.debug("synsetLine:{}", synsetLine);
		bw.println(synsetLine);
	}

	private String[] getSynsetLines(String word) {
		return wnCaller.stringify(word, includeGlossary);
	}

	public boolean isIncludeGlossary() {
		return includeGlossary;
	}

	public void setIncludeGlossary(boolean includeGlossary) {
		this.includeGlossary = includeGlossary;
	}

}
