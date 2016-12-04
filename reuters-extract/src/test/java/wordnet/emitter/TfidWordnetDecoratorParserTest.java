package wordnet.emitter;

import static org.junit.Assert.*;

import org.junit.Test;

public class TfidWordnetDecoratorParserTest {

	@Test
	public void testWithGloss() {
		String tfidfInput = "/home/mconway/temp/tfidfextract/extract.csv";
		String outputPath = "/home/mconway/temp/goodwords/synsetswithgloss.csv";
		String wordnetPath = "/home/mconway/Documents/text-mining/WordNet-3.0";
		
		TfidWordnetDecoratorParser parser = new TfidWordnetDecoratorParser(tfidfInput, outputPath, wordnetPath);
		parser.setIncludeGlossary(true);
		parser.process();
	}
	
	@Test
	public void testWithoutGloss() {
		String tfidfInput = "/home/mconway/temp/tfidfextract/extract.csv";
		String outputPath = "/home/mconway/temp/goodwords/synsetsnogloss.csv";
		String wordnetPath = "/home/mconway/Documents/text-mining/WordNet-3.0";
		
		TfidWordnetDecoratorParser parser = new TfidWordnetDecoratorParser(tfidfInput, outputPath, wordnetPath);
		parser.setIncludeGlossary(false);
		parser.process();
	}

}
