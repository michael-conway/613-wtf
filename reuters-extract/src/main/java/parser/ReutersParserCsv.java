/**
 * 
 */
package parser;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parse into a csv file
 * @author mconway
 *
 */
public class ReutersParserCsv {
	
	public static final Logger log = LoggerFactory
			.getLogger(ReutersParserCsv.class);

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		/* params
		 * 0 - src path to target file
		 * 2 - path to output file
		 */
		
		if (args.length != 2) {
			throw new IllegalArgumentException("needs 2 args [source path, target path]");
		}
		
		log.info("args:{}", args);
		
		ReutersReader reader = new ReutersReader(args[0], args[1]);
		CsvEmitter emitter = new CsvEmitter(args[1]);
		reader.parse();
	}

}
