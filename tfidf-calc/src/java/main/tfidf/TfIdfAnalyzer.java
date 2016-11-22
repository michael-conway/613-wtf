/**
 *
 */
package tfidf;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.vectorizer.DictionaryVectorizer;
import org.apache.mahout.vectorizer.DocumentProcessor;
import org.apache.mahout.vectorizer.common.PartialVectorMerger;
import org.apache.mahout.vectorizer.tfidf.TFIDFConverter;

/**
 * @author mconway
 *
 */
public class TfIdfAnalyzer {

	String outputFolder;
	Configuration configuration;
	FileSystem fileSystem;
	String inputFileLocation;
	Path documentsSequencePath;
	Path tokenizedDocumentsPath;
	Path tfidfPath;
	Path termFrequencyVectorsPath;

	public static void main(String args[]) throws Exception {

		TfIdfAnalyzer tester = new TfIdfAnalyzer("/home/mconway/temp/tfidf/", new Configuration(), "/home/mconway/temp/doc");

		tester.loadDocumentsInParentDir();
		tester.calculateTfIdf();
		tester.printSequenceFile(tester.documentsSequencePath);

		System.out.println("\n Step 1: Word count ");
		tester.printSequenceFile(new Path(tester.outputFolder
				+ "wordcount/part-r-00000"));

		System.out.println("\n Step 2: Word dictionary ");
		tester.printSequenceFile(new Path(tester.outputFolder,
				"dictionary.file-0"));

		System.out.println("\n Step 3: Term Frequency Vectors ");
		tester.printSequenceFile(new Path(tester.outputFolder
				+ "tf-vectors/part-r-00000"));

		System.out.println("\n Step 4: Document Frequency ");
		tester.printSequenceFile(new Path(tester.outputFolder
				+ "tfidf/df-count/part-r-00000"));

		System.out.println("\n Step 5: TFIDF ");
		tester.printSequenceFile(new Path(tester.outputFolder
				+ "tfidf/tfidf-vectors/part-r-00000"));

	}
	
	public TfIdfAnalyzer(String outputFolder, Configuration configuration, String inputPath) {
		this.configuration = configuration;
		try {
			fileSystem = FileSystem.get(configuration);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		documentsSequencePath = new Path(outputFolder, "sequence");
		tokenizedDocumentsPath = new Path(outputFolder,
				DocumentProcessor.TOKENIZED_DOCUMENT_OUTPUT_FOLDER);
		tfidfPath = new Path(outputFolder + "tfidf");
		termFrequencyVectorsPath = new Path(outputFolder
				+ DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER);
		this.outputFolder = outputFolder;
		this.inputFileLocation = inputPath;
		File outputFile = new File(outputFolder);
		outputFile.delete();
		outputFile.mkdirs();
	}

	public TfIdfAnalyzer() throws IOException {

		configuration = new Configuration();
		fileSystem = FileSystem.get(configuration);
		documentsSequencePath = new Path(outputFolder, "sequence");
		tokenizedDocumentsPath = new Path(outputFolder,
				DocumentProcessor.TOKENIZED_DOCUMENT_OUTPUT_FOLDER);
		tfidfPath = new Path(outputFolder + "tfidf");
		termFrequencyVectorsPath = new Path(outputFolder
				+ DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER);
	}

	public void loadDocumentsInParentDir() throws IOException {
		SequenceFile.Writer writer = new SequenceFile.Writer(fileSystem,
				configuration, documentsSequencePath, Text.class, Text.class);
		
		File inputDir = new File(inputFileLocation);
		if (!inputDir.exists()) {
			throw new RuntimeException("no input file");
		}
		Text data;
		
		for (File file : inputDir.listFiles()) {
			data = new Text(FileUtils.readFileToString(file));
			writer.append(new Text(file.getName()), data);
		}
		

		writer.close();
	}

	public void calculateTfIdf() throws ClassNotFoundException, IOException,
			InterruptedException {

		// Tokenize the documents using Apache Lucene StandardAnalyzer
		DocumentProcessor.tokenizeDocuments(documentsSequencePath,
				StandardAnalyzer.class, tokenizedDocumentsPath, configuration);

		DictionaryVectorizer.createTermFrequencyVectors(tokenizedDocumentsPath,
				new Path(outputFolder),
				DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER,
				configuration, 1, 1, 0.0f, PartialVectorMerger.NO_NORMALIZING,
				true, 1, 100, false, false);

		Pair<Long[], List<Path>> documentFrequencies = TFIDFConverter
				.calculateDF(termFrequencyVectorsPath, tfidfPath,
						configuration, 100);

		TFIDFConverter.processTfIdf(termFrequencyVectorsPath, tfidfPath,
				configuration, documentFrequencies, 1, 100,
				PartialVectorMerger.NO_NORMALIZING, false, false, false, 1);
	}

	void printSequenceFile(Path path) {
		SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<Writable, Writable>(
				path, configuration);
		for (Pair<Writable, Writable> pair : iterable) {
			System.out
					.format("%10s -> %s\n", pair.getFirst(), pair.getSecond());
		}
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public FileSystem getFileSystem() {
		return fileSystem;
	}

	public void setFileSystem(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

	public String getInputFileLocation() {
		return inputFileLocation;
	}

	public void setInputFileLocation(String inputFileLocation) {
		this.inputFileLocation = inputFileLocation;
	}

	public Path getDocumentsSequencePath() {
		return documentsSequencePath;
	}

	public void setDocumentsSequencePath(Path documentsSequencePath) {
		this.documentsSequencePath = documentsSequencePath;
	}

	public Path getTokenizedDocumentsPath() {
		return tokenizedDocumentsPath;
	}

	public void setTokenizedDocumentsPath(Path tokenizedDocumentsPath) {
		this.tokenizedDocumentsPath = tokenizedDocumentsPath;
	}

	public Path getTfidfPath() {
		return tfidfPath;
	}

	public void setTfidfPath(Path tfidfPath) {
		this.tfidfPath = tfidfPath;
	}

	public Path getTermFrequencyVectorsPath() {
		return termFrequencyVectorsPath;
	}

	public void setTermFrequencyVectorsPath(Path termFrequencyVectorsPath) {
		this.termFrequencyVectorsPath = termFrequencyVectorsPath;
	}

}
