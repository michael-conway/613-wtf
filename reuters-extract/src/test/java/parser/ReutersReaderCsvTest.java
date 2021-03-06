package parser;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ReutersReaderCsvTest {

	@Test
	public void testParse() throws IOException {
		String sourcePath = "/home/mconway/Documents/text-mining/reuters/reutconcat.sgm";
		String targetPath = "/home/mconway/temp/csv/reut2-000.csv";
		ReutersReader reader = new ReutersReader(sourcePath, targetPath);
		Emitter csvEmitter = new CsvEmitter(targetPath);
		reader.setEmitter(csvEmitter);
		reader.parse();
	}

	
	@Test
	public void testParseTestSet() throws IOException {
		String sourcePath = "/home/mconway/Documents/text-mining/reuters/reutconcattest.sgm";
		String targetPath = "/home/mconway/temp/csv/reut-test.csv";
		ReutersReader reader = new ReutersReader(sourcePath, targetPath);
		CsvEmitter csvEmitter = new CsvEmitter(targetPath);
		reader.setEmitter(csvEmitter);
		csvEmitter.addHeader();
		reader.parse();
	}
}
