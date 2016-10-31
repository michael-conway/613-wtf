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
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(line.substring(reutersIndex));
		int last = line.indexOf("</REUTERS>", reutersIndex + 9);
		String nextLine;
		while (last == -1) {
			nextLine = br.readLine();
			if (nextLine == null) {
				throw new IllegalStateException("cannot find end </reuters> tag");
			}
			last = nextLine.indexOf("</REUTERS>");
			if (last == -1) {
				log.debug("more data for this story:{}", nextLine);
				sb.append(nextLine);
			} else {
				log.debug("at end of data for this story:{}", nextLine);
				sb.append(nextLine.substring(0, last));
			}
		}
		String allData = sb.toString();
		ReutersInfo reutersInfo = new ReutersInfo();
		reutersInfo.setTopics(parseDDelimited("<TOPICS>", "</TOPICS>", allData));
		
		
		
	
		// I now have the full reuters article, scan for the various subparts
		
		
		
		
		
		
		return "";
	}
	
	private List<String> parseDDelimited(String startDelim, String endDelim, String data) {
		List<String> parsed = new ArrayList<String>();
		log.debug("data:{}", data);
		log.debug("delim:{}", startDelim);
		// find delimited data
		
		int i= data.indexOf(startDelim);
		int j = -1;
		if (i == -1) {
			log.debug("didn't find delim, returning with empty");
			return parsed;
		} else {
			log.debug("found delim");
			j = data.indexOf(endDelim);
			

			log.debug("end of delim:{}", j);
			if (j == -1) {
				log.error("didn't find end delim in data:{}", endDelim);
				throw new RuntimeException("cannot find end delim");
			}
		}
		
		String tagBody = data.substring(i + startDelim.length(), j);
		return parsed;
		
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
 