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
public class CsvEmitter extends Emitter {
	
	private PrintWriter bw;
	public static final Logger log = LoggerFactory
			.getLogger(CsvEmitter.class);
	private int ctr = 0;
	


	public CsvEmitter(final String outputFilePath) throws IOException {
		File targetAsFile = new File(outputFilePath);
		log.info("deleting targetAsFile:{}", targetAsFile);
		targetAsFile.delete();
		targetAsFile.createNewFile();
		targetAsFile.getParentFile().mkdirs();
		bw = new PrintWriter(new FileWriter(targetAsFile));

		
	}
	
	public void addHeader() {
		String header = "topic,title,text,docname";
		bw.println(header);
	}
	
	
	@Override
	public void emitDocument(ReutersInfo reutersInfo) {
		/*
		 * topic (first topic)
		 * people (first person)
		 * place (first place)
		 * title
		 * body
		 */
		
		StringBuilder sb = new StringBuilder();
		
		
		if (reutersInfo.getTopics().size() > 0) {
			sb.append(reutersInfo.getTopics().get(0));
		} else if (reutersInfo.getPeople().size() > 0) {
			sb.append(reutersInfo.getPeople().get(0));
		} else if (reutersInfo.getPlaces().size() > 0) {
			sb.append(reutersInfo.getPlaces().get(0));
		} else {
			return;
		}
		
		sb.append(",");
		
		sb.append(reutersInfo.getTitle().replaceAll(",", "."));
		sb.append(",");
		sb.append(reutersInfo.getBody().replaceAll(",", "."));
		sb.append(",");
		sb.append(Constants.genDocName(ctr++));
		bw.println(sb.toString());
		
	}


	@Override
	public void shutdown() {
		bw.close();
	}
	
	
	


}
