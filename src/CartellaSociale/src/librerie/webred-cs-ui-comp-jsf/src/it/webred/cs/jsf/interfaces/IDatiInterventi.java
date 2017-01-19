package it.webred.cs.jsf.interfaces;

import it.webred.cs.jsf.bean.DatiInterventoBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public interface IDatiInterventi {

	public List<DatiInterventoBean> getListaInterventi();
}
