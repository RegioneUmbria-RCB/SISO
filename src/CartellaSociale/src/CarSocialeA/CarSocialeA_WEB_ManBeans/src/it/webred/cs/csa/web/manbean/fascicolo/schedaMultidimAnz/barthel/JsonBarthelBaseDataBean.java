package it.webred.cs.csa.web.manbean.fascicolo.schedaMultidimAnz.barthel;

import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.dto.utility.StringIntPairBean;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class JsonBarthelBaseDataBean extends JsonBaseBean{

	@JsonIgnore protected HashMap<String, Integer> mapPunteggio = new HashMap<String, Integer>();
	@JsonIgnore protected HashMap<String, String>  mapLabel = new HashMap<String,String>();
	@JsonIgnore protected Integer punteggioTotale;
	@JsonIgnore protected String  punteggioTotaleLegenda;
	
	public JsonBarthelBaseDataBean(){
	
		mapPunteggio = new HashMap<String, Integer>();
		mapLabel = new HashMap<String, String>();
		
	}

	protected List<SelectItem> CreateOptionList( String baseClassName, StringIntPairBean... args )
	{
		List<SelectItem> retList = new LinkedList<SelectItem>();
		
		if( args != null )
		{
			for( int i = 0; i < args.length; i++)
			{
				StringIntPairBean pair = args[i];
				String className = baseClassName + i;
				retList.add( new SelectItem( className, pair.getKey() + " [ " + pair.getValue() + " ] " ));
				mapPunteggio.put( className, pair.getValue() );
				mapLabel.put(className, pair.getKey() + " [ " + pair.getValue() + " ] ");
			}
		}
		return retList;
	}
	
	
	public abstract String getPunteggioTotaleLegenda();
	
	
	public HashMap<String, Integer> getMapPunteggio() {
		return mapPunteggio;
	}

	public Integer getPunteggioTotale() {
		return punteggioTotale;
	}
	

	public String getDescrizione(String classname){
		String s = this.mapLabel.get(classname);
		return s!=null ? s : "-";
	}
	
	public Integer getPunteggio (String classname){
		return this.mapPunteggio.get(classname);
	}
	
}
