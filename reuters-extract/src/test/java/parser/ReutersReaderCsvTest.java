package parser;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ReutersReaderCsvTest {

	@Test
	public void testParse() throws IOException {
		String sourcePath = "/Users/henryn/Documents/reut2-000.sgm";
		String targetPath = "/Users/henryn/Documents/reut2-000.csv";
		ReutersReader reader = new ReutersReader(sourcePath, targetPath);
		Emitter csvEmitter = new CsvEmitter(targetPath);
		reader.setEmitter(csvEmitter);
		reader.parse();
	}

}
