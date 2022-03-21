package it.webred.mui.input;

public class ParserLogFactory {

	private static ParserLogFactory _plf = null;
	private ParserLog _pl = null;

	private ParserLogFactory() {

	}

	public static ParserLogFactory getParserLogFactory() {
		if (_plf == null) {
			synchronized (ParserLogFactory.class) {
				if (_plf == null) {
					_plf = new ParserLogFactory();
				}

			}
		}
		return _plf;
	}
	
	public ParserLog getParserLog(){
		return _pl;
	}
}
