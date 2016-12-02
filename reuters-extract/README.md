create training file

cat reut2-000.sgm reut2-001.sgm reut2-002.sgm reut2-003.sgm reut2-004.sgm reut2-005.sgm reut2-006.sgm reut2-007.sgm reut2-008.sgm reut2-009.sgm > reutconcat.sgm


Run parser.ReutersReaderCsvTest to create the csv file, collapsing to topic

Run ReutersReaderDocTest to create text only files for tfidf

Run TfidfAnalyzer to come up with top words for each document

Run TopWordsPerDocAnalyzerTest to generate word lists based on tfidf stats

Run TfidWordnetDecoratorParserTest to generate 'good words' from wordnet