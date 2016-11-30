package parser;

public class Constants {
	
	public static final  String DOC_PREFIX = "doc";
	public static final String DOC_SUFFIX = ".txt";
	
	public static final String genDocName(int ctr) {
		StringBuilder sb = new StringBuilder();
		sb.append(DOC_PREFIX);
		String formatted = String.format("%07d", ctr);
		sb.append(formatted);
		sb.append(DOC_SUFFIX);
		return sb.toString();
	}

}
