package it.webred.rulengine.brick.loadDwh.load.cnc;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public abstract class RecordParser {
	
	private String lineRule = "";
	private List<String> results = new ArrayList<String>();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private String line;
	
	public RecordParser(String lineRule) {
		this.lineRule = lineRule;
	}
	
	public String getLine() {
		return this.line;
	}
	
	public void parseLine(String line) throws CNCParseException {
		this.line = line;
	    List<String> rules = parseRule();
	    
	    
	    for (int i=0; i < rules.size(); i++) {
	    	int p1 = Integer.parseInt(rules.get(i)) - 1;
	    	int p2 = 0;
	    	if (i != rules.size() - 1)
	    	  p2 = Integer.parseInt(rules.get(i + 1)) - 1;
	    	else
	    	  p2 = line.length();
	    	
	    	
	    	String fragment = line.substring(p1, p2);
	    	System.out.println("Fragment ["+fragment+"]");
	    	results.add(fragment.trim());
	    }
	    
	   
	}
	
	public abstract void finishParseJob() throws CNCParseException ;
	
	public List<String> getCNCRecord() {
		results.remove(0);
		results.remove(results.size() - 1); // Rimuovo il filler
		System.out.println("Results ["+results+"]");
		return results;
	}
	
	public void removeElement(int pos) {
		results.remove(pos - 1);
	}

	protected List<String>  parseRule() {
	    
		List<String> rules = new ArrayList<String>();
		
		for (int i=0; i < lineRule.length(); i+= 3) {
	    	String delim = lineRule.substring(i, i + 3);
	    	rules.add(delim);
	    }
	    System.out.println("Line rule parsed");
	    
	    return rules;
	}
	
	protected Date getDate(String sDate) {
		try {
			return sdf.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected String getElemenet(int pos) {
		return results.get(pos - 1);
	}


	public String getLineRule() {
		return lineRule;
	}


	public void setLineRule(String lineRule) {
		this.lineRule = lineRule;
	}
	
	protected void addEmptyElements(String emptyRule) {
		List<String> emptyList = new ArrayList<String>();
		for (int i=0; i < emptyRule.length(); i+=2) {
			String cod = emptyRule.substring(i, i+2);
			if (cod.equals("AN")) 
				emptyList.add("");
			else if (cod.equals("NN"))
				emptyList.add("0");
				
		}
		
		results.addAll(emptyList);
	}
	
	public void removeLastElement() {
		results.remove(results.size() - 1); 
	}

	protected List<String> getElements() {
		return this.results;
	}
}
