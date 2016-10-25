package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReutersReaderTest {

	@Test
	public void testParse() {
		String sourcePath = "/home/mconway/Documents/text-mining/reuters/reut2-000.sgm";
		String targetPath = "/home/mconway/tmp/reut2-000.csv";
		ReutersReader reader = new ReutersReader(sourcePath, targetPath);
		reader.parse();
	}

}
