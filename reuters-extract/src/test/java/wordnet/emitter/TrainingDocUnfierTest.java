package wordnet.emitter;

import static org.junit.Assert.*;

import org.junit.Test;

import parser.TrainingDocUnfier;

public class TrainingDocUnfierTest {

	@Test
	public void testProcessWithGloss() {
		String csvFile = "/home/mconway/temp/csv/reut2-000.csv";
		String synsetFile = "/home/mconway/temp/goodwords/synsetswithgloss.csv";
		String outputFile = "/home/mconway/temp/final/trainingwithgloss.csv";
		TrainingDocUnfier unifier = new TrainingDocUnfier(csvFile, synsetFile, outputFile);
		unifier.process();
		
	}
	
	@Test
	public void testProcessWithoutGloss() {
		String csvFile = "/home/mconway/temp/csv/reut2-000.csv";
		String synsetFile = "/home/mconway/temp/goodwords/synsetsnogloss.csv";
		String outputFile = "/home/mconway/temp/final/trainingwithoutgloss.csv";
		TrainingDocUnfier unifier = new TrainingDocUnfier(csvFile, synsetFile, outputFile);
		unifier.process();
		
	}


}
