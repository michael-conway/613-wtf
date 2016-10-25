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
public class ReutersReader extends DefaultHandler {
	
	
	
	private String sourceFile;
	private String targetFile;
	StringBuilder sb;
	
	
	public static final Logger log = LoggerFactory
			.getLogger(ReutersReader.class);
	
	public ReutersReader(final String sourceFile, final String targetFile) {
		log.info("constructor");
		log.info("source file:{}", sourceFile);
		log.info("target file:}", targetFile);
		this.sourceFile = sourceFile;
		this.targetFile = targetFile;
		
	}
	
	public void parse() {
		log.info("parse()");
		File sourceAsFile = new File(sourceFile);
		File outputAsFile = new File(targetFile);
		outputAsFile.delete();
		log.info("source exists, target file deleted and ready to write");
		
		if (!sourceAsFile.exists()) {
			log.error("source file does not exist");
			throw new IllegalArgumentException("source file does not exist");
		}
		
		
	}
	
	  public void startDocument() throws SAXException {
	        log.info("start document");
	  }
	  
	  public void endDocument() throws SAXException {
	       	log.info("end document");
	       	
	    }
	 
	  

}
