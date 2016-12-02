/**
 * 
 */
package parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Emit data as a csv file
 * @author mconway
 *
 */
public class DocEmitter extends Emitter {
	
	public static final Logger log = LoggerFactory
			.getLogger(WekaEmitter.class);
	private File parent;
	private String outputFilePrefix;
	private String outputFileSuffix;
	private int ctr = 0;
	


	public DocEmitter(final String outputFileDirectory, final String outputFilePrefix, final String outputFileSuffix) throws IOException {
		 parent = new File(outputFileDirectory);
		log.info("deleting and recreating parent:{}", outputFileDirectory);
		parent.delete();
		parent.mkdirs();
		this.outputFilePrefix = outputFilePrefix;
		this.outputFileSuffix = outputFileSuffix;
	}
	
	
	@Override
	public void emitDocument(ReutersInfo reutersInfo)  {
		
		if (reutersInfo.getTopics().size() > 0) {
		} else if (reutersInfo.getPeople().size() > 0) {
		} else if (reutersInfo.getPlaces().size() > 0) {
		} else {
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.genDocName(ctr++));
		File outputFile = new File(parent, sb.toString());
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
			pw.println(reutersInfo.getBody());
			pw.close();
		} catch (IOException e) {
			log.error("error writing output file", e);
			throw new RuntimeException("error writing output file", e);
		}

		
	}


	@Override
	public void shutdown() {
	}


	@Override
	public void generateHeaders() {
		
	}
	
	

}
