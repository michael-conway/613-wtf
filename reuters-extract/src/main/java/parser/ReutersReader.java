package parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

/**
 * Class to read a reuters SGML file and output a CSV file suitable for Lightside
 * @author mconway
 *
 */
public class ReutersReader  {
	private BufferedReader br;
	private BufferedWriter bw;
	
	public static final Logger log = LoggerFactory
			.getLogger(ReutersReader.class);
	
	public ReutersReader(String sourceFile, String targetFile) throws IOException {
		
		File sourceAsFile = new File(sourceFile);
		
		if (!sourceAsFile.exists()) {
			log.error("source file does not exist");
			throw new IllegalArgumentException("source file does not exist");
		}
		File targetAsFile = new File(targetFile);
		log.info("deleting targetAsFile:{}", targetAsFile);
		targetAsFile.delete();
		targetAsFile.createNewFile();
		targetAsFile.getParentFile().mkdirs();
		 br = new BufferedReader(new FileReader(sourceAsFile));
		 bw = new BufferedWriter(new FileWriter(targetAsFile));
	}
	
	
	
	public void parse() throws IOException {
		log.info("parse()");
		
	    String line = br.readLine();
	    int count = 0;

	    int start = 0;
		while ( line != null) {
			if (line.startsWith("<!DOCTYPE")) {
				log.info("ignore doctype");
				line = br.readLine();
				continue;
			} 
			
			start = line.indexOf("<REUTERS");
			
			if (start > -1) {
				log.info("found a <REUTERS>");
				
				count++;
				line = processReuters(line, start); // process until a </REUTERS> is encountered, will return the remainder here
				if (line.length() == 0) {
					line = br.readLine();
				} // otherwise, loop back up and consume rest of the remaining line
			} else { 
				log.warn("skipping:{}", line);
				line = br.readLine();
				
			}
		}
		
		bw.flush();
		bw.close();
		br.close();
		log.info("parsed {} reuters", count);
		
		
	}
	
	private String processReuters(String line, int reutersIndex) throws IOException {
		/* have a line that starts with <REUTERS> so process it, and subsequent lines, until </RETUERS>
		 * then return any remainder (allow wrapping)
		 */
	
		
		List<String> topics = new ArrayList<String>();
		List<String> places = new ArrayList<String>();
		List<String> people = new ArrayList<String>();
		String title;
		String body;
		
		log.debug("line:{}", line);
		// find topics
		
		int idx = line.indexOf("<TOPICS>");
		
		while (idx == -1) {
			log.debug("no topics, seek to next line");
			line = readNonNullLine();
			idx = line.indexOf("<TOPICS>");
		} 
		
		log.info("found topic:{}", line);
		
		
		return "";
	}



	private String readNonNullLine() throws IOException {
		String line;
		line = br.readLine();
		if (line == null) {
			throw new RuntimeException("invalid file, no topic found");
		}
		return line;
	}
	
	

}
 