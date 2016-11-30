package parser;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ReutersReaderDocTest {

	@Test
	public void testParse() throws IOException {
		String sourcePath = "/home/mconway/Documents/text-mining/reuters/reut2-000.sgm";
		String targetPath = "/home/mconway/temp/doc";
		ReutersReader reader = new ReutersReader(sourcePath, targetPath);
		Emitter emitter = new DocEmitter(targetPath, Constants.DOC_PREFIX, Constants.DOC_SUFFIX);
		reader.setEmitter(emitter);
		reader.parse();
	}

}
