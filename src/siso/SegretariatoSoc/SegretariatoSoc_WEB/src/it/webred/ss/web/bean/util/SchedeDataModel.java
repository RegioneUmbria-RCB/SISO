package it.webred.ss.web.bean.util;

import it.webred.ss.web.bean.lista.Scheda;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class SchedeDataModel extends ListDataModel<Scheda> implements SelectableDataModel<Scheda> {
	
	public SchedeDataModel(List<Scheda> schede){
		super(schede);
	}

	@Override
	public Scheda getRowData(String arg) {
		List<Scheda> schede = (List<Scheda>) getWrappedData();
		for(Scheda s: schede)
			if(arg.equals(getRowKey(s)))
				return s;
		
		return null;
	}

	@Override
	public Object getRowKey(Scheda arg) {
		return arg.getCognome() + "-" + arg.getNome();
	}

}
