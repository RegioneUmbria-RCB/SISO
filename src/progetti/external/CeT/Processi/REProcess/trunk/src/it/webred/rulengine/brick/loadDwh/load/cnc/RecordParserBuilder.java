package it.webred.rulengine.brick.loadDwh.load.cnc;

import java.util.HashMap;
import java.util.Properties;


/**
 * @author Francesco
 * 
 */
public class RecordParserBuilder {

	private static RecordParserBuilder me;
	private static Properties configProps = new Properties();
	private HashMap<String, RecordParser> recordParserMap = new HashMap<String, RecordParser>();
	
	private RecordParserBuilder() {
		try {
			configProps.load(getClass().getResourceAsStream("parser.properties"));
		}
		catch(Throwable t) {}
		
	}
	
	public static RecordParserBuilder getInstance() {
		if (me == null)
			me = new RecordParserBuilder();
		
		return me;
	}
	
	
	public RecordParser getRecordParser(String recordKey) {
		RecordParser rec = recordParserMap.get(recordKey);
		
		if (rec == null) {
			rec = getClass(configProps.getProperty(recordKey));
		}
		
		return rec;
	}
	
	
	private RecordParser getClass(String className) {
		RecordParser r = null;
		try {
			Class c = Class.forName(className);
			r = (RecordParser) c.newInstance();
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
		
		return r;
	}
	
}

	