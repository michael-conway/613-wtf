/**
 * 
 */
package parser;

/**
 * Superclass for an emitter that puts out reuters info in some format
 * 
 * @author mconway
 *
 */
public class Emitter {
	
	/**
	 * Generate any necessary header, invoked at the start of each document (for example, for weka headers)
	 */
	public void generateHeaders() {
		
	}
	
	public void emitDocument(final ReutersInfo reutersInfo) {
		
	}
	
	/**
	 * Close and terminate any resources
	 */
	public void shutdown() {
		
	}

}
