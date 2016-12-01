package wordnet.emitter;

import static org.junit.Assert.*;

import org.junit.Test;

public class TfidWordnetDecoratorParserTest {

	@Test
	public void test() {
		String tfidfInput = "/home/mconway/temp/tfidfextract/extract.csv";
		String outputPath = "/home/mconway/temp/goodwords/synsets.csv";
		String wordnetPath = "/home/mconway/Documents/text-mining/WordNet-3.0";
		
		TfidWordnetDecoratorParser parser = new TfidWordnetDecoratorParser(tfidfInput, outputPath, wordnetPath);
		parser.process();
	}

}
