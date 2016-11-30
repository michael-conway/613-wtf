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
			targetAsFile.createNewFile();
			targetAsFile.getParentFile().mkdirs();
			bw = new PrintWriter(new FileWriter(targetAsFile));
		} catch (IOException e) {
			throw new RuntimeException("unable to create reader", e);
		}

	}
	
	public void process() {
		// read each line from the csv file
		try {
			String line = br.readLine();
			bw.println(line);  //header
			line= br.readLine();

			while (line != null) {
				
			}
			
			
		} catch (IOException e) {
			throw new RuntimeException("exception processing data", e);

		} 
		
		
	}

}
