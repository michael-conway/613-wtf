/**
 * 
 */
package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mconway - take the synsets and unify with the input csv file
 *
 */
public class TrainingDocUnfier {

	private final String extractCsvPath;
	private final String synsetPath;
	private final String outputFilePath;
	private PrintWriter bw;
	private BufferedReader csvReader;
	private BufferedReader synsetReader;
	private List<String> csvLines = new ArrayList<String>();
	private String synsetCarryoverLine;

	public static final Logger log = LoggerFactory
			.getLogger(TrainingDocUnfier.class);

	public TrainingDocUnfier(String extractCsvPath, String synsetPath,
			String outputFilePath) {
		super();
		this.extractCsvPath = extractCsvPath;
		this.synsetPath = synsetPath;
		this.outputFilePath = outputFilePath;
		File outputFile = new File(outputFilePath);
		outputFile.delete();
		try {
			outputFile.getParentFile().mkdirs();
			outputFile.createNewFile();
			bw = new PrintWriter(new FileWriter(outputFile));
			csvReader = new BufferedReader(new FileReader(extractCsvPath));
			synsetReader = new BufferedReader(new FileReader(synsetPath));
		} catch (IOException e) {
			throw new RuntimeException("unable to create outputFile", e);
		}

		try {
			synsetCarryoverLine = synsetReader.readLine();
		} catch (IOException e) {
			log.error("cannot read synset line", e);
			throw new RuntimeException("unable to read synset", e);

		}

	}

	public void process() {

		// read csv into a list
		log.info("build csv list in mem");
		String header = "topic,title,text,docname";
		bw.println(header);
		String line;
		try {
			line = csvReader.readLine();

			while (line != null) {
				csvLines.add(line);
				line = csvReader.readLine();
			}

		} catch (IOException e) {
			log.error(
					">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>error reading csvline",
					e);
			throw new RuntimeException("unable to read csv lines");
		}

		FileAndSynset fileAndSynset = parseNextFileAndSynset();

		while (fileAndSynset != null) {
			outputLine(fileAndSynset);
			fileAndSynset = parseNextFileAndSynset();
		}

		log.info("fileAndSynset:{}", fileAndSynset);
		bw.close();

	}

	private void outputLine(FileAndSynset fileAndSynset) {
		log.info("finding doc for:{}", fileAndSynset.getFileName());
		String foundLine = null;
		for (String line : csvLines) {
			if (line.endsWith(fileAndSynset.getFileName())) {
				foundLine = line;
				break;
			}
		}

		if (foundLine == null) {
			log.error("cannot find csvLine for file:{}",
					fileAndSynset.getFileName());
			return;
		}

		log.info("found csv line:{}", foundLine);

		String[] t = foundLine.split(",");
		if (t.length != 4) {
			log.error("unknown number of parameters in csv line:{} t");
			throw new RuntimeException("unknown number of params in csv line");
		}

		StringBuilder sb = new StringBuilder();
		sb.append(t[0]); // topic
		sb.append(",");
		sb.append(t[1]); // title
		sb.append(",");
		sb.append(t[2]); // text
		sb.append(" ");

		// insert wordnet data

		for (String wnline : fileAndSynset.getSynsetData()) {
			sb.append(wnline);
			sb.append(" ");
		}
		sb.append(',');
		sb.append(t[3]); // filename
		log.info("new line:{}", sb.toString());
		bw.println(sb.toString());

	}

	private FileAndSynset parseNextFileAndSynset() {
		try {
			String line = synsetCarryoverLine;

			if (line == null) {
				log.info("all done with synsets!");
				return null;
			}

			FileAndSynset fileAndSynset = new FileAndSynset();

			// line should start with 'doc'
			if (line.startsWith("doc")) {
				log.info("have a new doc");
				String[] t = line.split(",");
				if (t.length < 2) {
					log.error("cannot parse synset line:{}", line);
					throw new RuntimeException("exception parsing synset line");
				}
				fileAndSynset.setFileName(t[0]);
			} else {
				log.error("first line of synset data isn't the doc:{}", line);
				throw new RuntimeException(
						"first line of synset data isn't the doc");
			}

			// now read until the next 'doc' line

			line = synsetReader.readLine();
			if (line == null) {
				log.info("done!");
				return null;
			}
			while (line != null) {

				if (line.startsWith("doc")) {
					log.info("got next doc line, so save it for next time");
					synsetCarryoverLine = line;
					return fileAndSynset;
				}

				// have synset lines to process
				String[] t = line.split(",");
				if (t.length < 2) {
					log.warn("cannot parse synset line:{}", line);
				} else {

					fileAndSynset.getSynsetData().add(t[1]);
				}
				line = synsetReader.readLine();

			}

			return fileAndSynset;

		} catch (IOException e) {
			log.error("cannot read from synset file");
			throw new RuntimeException("error reading synset data", e);
		}
	}

}
