create training file

cat reut2-000.sgm reut2-001.sgm reut2-002.sgm reut2-003.sgm reut2-004.sgm reut2-005.sgm reut2-006.sgm reut2-007.sgm reut2-008.sgm reut2-009.sgm > reutconcat.sgm

cat reut2-010.sgm reut2-011.sgm reut2-012.sgm reut2-013.sgm reut2-014.sgm reut2-015.sgm reut2-016.sgm reut2-017.sgm reut2-018.sgm reut2-019.sgm > reutconcattest.sgm



Run parser.ReutersReaderCsvTest to create the csv file, collapsing to topic

Run ReutersReaderDocTest to create text only files for tfidf

Run TfidfAnalyzer to come up with top words for each document

Run TopWordsPerDocAnalyzerTest to generate word lists based on tfidf stats

Run TfidWordnetDecoratorParserTest to generate 'good words' from wordnet

Run TrainingDocUnifierTest to pull wordnet data back into the text documents for the final csv file