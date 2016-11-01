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
	private PrintWriter bw;
	
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
		 bw = new PrintWriter(new FileWriter(targetAsFile));
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
				processReuters(line, start); // process until a </REUTERS> is encountered, will return the remainder here
				line = br.readLine();
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
	
	private void processReuters(String line, int reutersIndex) throws IOException {
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
		// I now have the full reuters article, scan for the various subparts

		String allData = sb.toString();
		ReutersInfo reutersInfo = new ReutersInfo();
		reutersInfo.setTopics(parseDDelimited("<TOPICS>", "</TOPICS>", allData));
		reutersInfo.setPeople(parseDDelimited("<PEOPLE>", "</PEOPLE>", allData));
		reutersInfo.setPlaces(parseDDelimited("<PLACES>", "</PLACES>", allData));
		reutersInfo.setTitle(parseTitle(allData));
		
		int i = allData.indexOf("<BODY>");
		int j = allData.indexOf("</BODY>", i);
		
		if (i == -1 || j == -1){
			log.warn("cannot find BODY, discarding:{}", allData);
			return;
		}
		
		reutersInfo.setBody(allData.substring(i + 6, j));
		writeCsv(reutersInfo);
		
		log.info("reutersInfo:{}", reutersInfo);
		
	}
	
	private String parseTitle(String data) {
		
		int i= data.indexOf("<TITLE>");
		int j = -1;
		if (i == -1) {
			log.debug("didn't find title, returning with empty");
			return "";
		} else {
			log.debug("found title");
			j = data.indexOf("</TITLE>");
			

			log.debug("end of title:{}", j);
			if (j == -1) {
				log.error("didn't find end title in data");
				throw new RuntimeException("cannot find end delim for title");
			}
		}
		
		return data.substring(i + 7, j);
		
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
		
		i = tagBody.indexOf("<D>");
		j = tagBody.indexOf("</D>");
		int ilim = 0;
		int jlim = 0;
		
		/*
		 * march along the <D></D> tags and pick out their innards		 */
		while(i != -1) {
			if (j == -1) {
				log.error("cannot find end tag for delim <D>");
				throw new RuntimeException("cannot parse for <D></D>");
			}
			parsed.add(tagBody.substring(i + 3, j));
			jlim = j;
			i = tagBody.indexOf("<D>", jlim);
			j = tagBody.indexOf("</D>", jlim + 3);
			
		}
			
		return parsed;
		
	}
	
	private void writeCsv(ReutersInfo reutersInfo) {
		/*
		 * topic (first topic)
		 * people (first person)
		 * place (first place)
		 * title
		 * body
		 */
		
		StringBuilder sb = new StringBuilder();
		if (reutersInfo.getTopics().size() == 0) {
			sb.append(",");
		} else {
			sb.append(reutersInfo.getTopics().get(0));
			sb.append(",");
		}
		 
		if (reutersInfo.getPeople().size() == 0) {
			sb.append(",");
		} else {
			sb.append(reutersInfo.getPeople().get(0));
			sb.append(",");
		}
		
		if (reutersInfo.getPlaces().size() == 0) {
			sb.append(",");
		} else {
			sb.append(reutersInfo.getPlaces().get(0));
			sb.append(",");
		}
		
		sb.append(reutersInfo.getTitle().replaceAll(",", "."));
		sb.append(",");
		sb.append(reutersInfo.getBody().replaceAll(",", "."));
		bw.println(sb.toString());
		
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
 