/**
 * 
 */
package tfidf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Writable;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;

/**
 * @author mconway - prints a set of top words per document
 *
 */
public class TopWordsPerDocAnalyzer {

	private String dictionaryPath;
	private String tfidfPath;
	private String outputFilePath;
	private PrintWriter bw;
	Map<String,String> dict = new HashMap<String,String>();
	Configuration configuration;
	FileSystem fileSystem;
	
	// DICT /home/mconway/temp/tfidf/dictionary.file-0
	
	// TFIDF /home/mconway/temp/tfidf/tfidf/tfidf-vectors/part-r-00000
	
	public TopWordsPerDocAnalyzer(String dictionaryPath, String tfidfPath, String outputFilePath) {
		this.dictionaryPath = dictionaryPath;
		this.tfidfPath = tfidfPath;
		this.outputFilePath = outputFilePath;
		File outputFile = new File(outputFilePath);
		outputFile.delete();
		try {
			outputFile.getParentFile().mkdirs();
			outputFile.createNewFile();
			bw = new PrintWriter(new FileWriter(outputFile));
		} catch (IOException e) {
			throw new RuntimeException("unable to create outputFile",e);
		}
		this.configuration = new Configuration();
		try {
			fileSystem = FileSystem.get(configuration);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Map<String,String> generateDictMap() {
		SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<Writable, Writable>(
				new Path(dictionaryPath), configuration);
		for (Pair<Writable, Writable> pair : iterable) {
			dict.put(pair.getSecond().toString(), pair.getFirst().toString());
		}
		
		return dict;
		
	}
	
	public Map<String, Map<Float,String>> generateTranslatedTfidf() {
		Map<String, Map<Float,String>> tfidf = new HashMap<String, Map<Float,String>>();
		generateDictMap();
		SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<Writable, Writable>(
				new Path(tfidfPath), configuration);
		String realWord;
		Float freq;
		for (Pair<Writable, Writable> pair : iterable) {
			System.out.println(pair.getFirst().toString() + "---" +  pair.getSecond().toString());
			//String dictName = dict.get(pair.getFirst());
			//System.out.println("real name:" + dictName);
			String[] tokens= pair.getSecond().toString().split(",");
			Map<Float,String> entries = new HashMap<Float,String>();
			Map<Float,String> filteredEntries = new HashMap<Float,String>();

			for(String token : tokens) {
				String[] wordFreq = token.replace('{', ' ').replace('}', ' ').split(":");
				realWord = dict.get(wordFreq[0].trim());
				if (isNumeric(realWord)) {
					continue;
				}
				freq = new Float(wordFreq[1]);
				entries.put(freq, realWord);
				System.out.println("......." + realWord + " - " + freq);
			}
			List<Float> keys = new ArrayList<Float>(entries.keySet());
			Collections.sort(keys);
			int added = 0;
			for (int i = keys.size() - 1; i >= 0; i--) {
				if (added > 5) {
					break;
				}
				filteredEntries.put(keys.get(i), entries.get(keys.get(i)));
				added++;
			}
			
			tfidf.put(pair.getFirst().toString(), filteredEntries);
		}
		
		return tfidf;
		
		
	}
	
	public boolean isNumeric(String s) {  
	   // return s.matches("[-+]?\\d*\\.?\\d+");  
		return NumberUtils.isDigits(s.substring(0,1));
	}  
	
	public void output() {
		Map<String, Map<Float,String>> data = generateTranslatedTfidf();
		StringBuilder line;

		for(String file : data.keySet()) {
			line = new StringBuilder();
			line.append(file);
			line.append(",");
			Map<Float,String> wordVals = data.get(file);
			boolean first = true;
			for(String word : wordVals.values()) {
				if (!first) {
					line.append('|');
				} else {
					first = false;
				}
				
				line.append(word);
			}
				
			bw.println(line.toString());
		}
	}
	
}
