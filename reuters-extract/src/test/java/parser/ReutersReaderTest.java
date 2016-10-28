package parser;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ReutersReaderTest {

	@Test
	public void testParse() throws IOException {
		String sourcePath = "/home/mconway/Documents/text-mining/reuters/reut2-000.sgm";
		String targetPath = "/home/mconway/temp/csv/reut2-000.csv";
		ReutersReader reader = new ReutersReader(sourcePath, targetPath);
		reader.parse();
	}

}
