package tfidf;

import static org.junit.Assert.*;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class TopWordsPerDocAnalyzerTest {
	
	public static  final String DICT = "/home/mconway/temp/tfidf/dictionary.file-0";
	
	public static final String TFIDF = "/home/mconway/temp/tfidf/tfidf/tfidf-vectors/part-r-00000";
	
	public static  final String OUTPUT = "/home/mconway/temp/tfidfextract/extract.csv";
	

	@Test
	public void testDict() {
		
		TopWordsPerDocAnalyzer topWordsPerDocAnalyzer = new TopWordsPerDocAnalyzer(DICT, TFIDF, OUTPUT);
		Map<String, String> map = topWordsPerDocAnalyzer.generateDictMap();
		Assert.assertNotNull("null map", map);
		
	}
	
	@Test 
	public void testTfidf() {
		TopWordsPerDocAnalyzer topWordsPerDocAnalyzer = new TopWordsPerDocAnalyzer(DICT, TFIDF, OUTPUT);
		Map<String, Map<Float, String>> actual = topWordsPerDocAnalyzer.generateTranslatedTfidf();
		Assert.assertNotNull("null map", actual);

	}

	@Test 
	public void testOutput() {
		TopWordsPerDocAnalyzer topWordsPerDocAnalyzer = new TopWordsPerDocAnalyzer(DICT, TFIDF, OUTPUT);
		topWordsPerDocAnalyzer.output();


	}

}
