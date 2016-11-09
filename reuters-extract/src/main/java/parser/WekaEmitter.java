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
public class WekaEmitter extends Emitter {
	
	private PrintWriter bw;
	public static final Logger log = LoggerFactory
			.getLogger(WekaEmitter.class);
	


	public WekaEmitter(final String outputFilePath) throws IOException {
		File targetAsFile = new File(outputFilePath);
		log.info("deleting targetAsFile:{}", targetAsFile);
		targetAsFile.delete();
		targetAsFile.createNewFile();
		targetAsFile.getParentFile().mkdirs();
		bw = new PrintWriter(new FileWriter(targetAsFile));

		
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
		if (reutersInfo.getTopics().size() == 0) {
			sb.append("?,");
		} else {
			sb.append('\'');
			sb.append(reutersInfo.getTopics().get(0));
			sb.append('\'');
			sb.append(",");
		}
		 
		if (reutersInfo.getPeople().size() == 0) {
			sb.append("?,");
		} else {
			sb.append('\'');
			sb.append(reutersInfo.getPeople().get(0));
			sb.append('\'');
			sb.append(",");
		}
		
		if (reutersInfo.getPlaces().size() == 0) {
			sb.append("?,");
		} else {
			sb.append('\'');
			sb.append(reutersInfo.getPlaces().get(0));
			sb.append('\'');
			sb.append(",");
		}
		sb.append('\'');
		sb.append(reutersInfo.getTitle().replaceAll(",", ".").replaceAll("\'", " ").replaceAll("\"", " "));
		sb.append('\'');

		sb.append(",");
		sb.append('\'');
		sb.append(reutersInfo.getBody().replaceAll(",", ".").replaceAll("\'", " ").replaceAll("\"", " "));
		sb.append('\'');

		bw.println(sb.toString());
		
	}


	@Override
	public void shutdown() {
		bw.close();
	}


	@Override
	public void generateHeaders() {
		bw.println("@relation wekaEmitter" + System.currentTimeMillis());
		bw.println("@attribute topic string");
		bw.println("@attribute person string");
		bw.println("@attribute place string");
		bw.println("@attribute title string");
		bw.println("@attribute body string");
		bw.println("@data");
	}
	
	
	


}
