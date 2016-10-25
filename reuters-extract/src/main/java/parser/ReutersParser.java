/**
 * 
 */
package parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mconway
 *
 */
public class ReutersParser {
	
	public static final Logger log = LoggerFactory
			.getLogger(ReutersParser.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* params
		 * 0 - src path to target file
		 * 2 - path to output file
		 */
		
		if (args.length != 2) {
			throw new IllegalArgumentException("needs 2 args [source path, target path]");
		}
		
		log.info("args:{}", args);
		
		ReutersReader reader = new ReutersReader(args[0], args[1]);
		reader.parse();
	}

}
