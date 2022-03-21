package it.webred.cs.jsf.interfaces;

import java.util.List;

import it.webred.cs.jsf.bean.ValiditaCompBaseBean;

public interface IDatiValiditaList {
	
	public Object getTypeClass();
	
	public String getTypeComponent();
	
	public String getParam();
	
	public void setParam(String param);
	
	public String getNuovoAttiva();
	
	public void nuovo();
	
	public boolean salva();

	public void salvaCs(Long id);
	
	public void elimina();
	
	public void copia();
	
	public Object getCsFromComponente(Object obj);
	
	public ValiditaCompBaseBean getComponenteFromCs(Object obj);
	
	public List<ValiditaCompBaseBean> getListaComponenti();
	
	public Long getSoggettoId();
	
	public int getCurrentIndex();
	
	public void setCurrentIndex(int currentIndex);
	
	public boolean validaCs(ValiditaCompBaseBean comp);
}
