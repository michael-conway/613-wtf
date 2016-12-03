package wordnet.emitter;

import static org.junit.Assert.*;

import org.junit.Test;

import parser.TrainingDocUnfier;

public class TrainingDocUnfierTest {

	@Test
	public void testProcess() {
		String csvFile = "/home/mconway/temp/csv/reut2-000.csv";
		String synsetFile = "/home/mconway/temp/goodwords/synsets.csv";
		String outputFile = "/home/mconway/temp/final/training.csv";
		TrainingDocUnfier unifier = new TrainingDocUnfier(csvFile, synsetFile, outputFile);
		unifier.process();
		
	}

}
