package it.webred.cs.json.OrientamentoLavoro;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.model.SelectItem;

import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.OrientamentoLavoro.ver1.OrientamentoLavoroController;

public interface IOrientamentoLavoro extends ISchedaValutazione{
	public OrientamentoLavoroController getController();
	public List<SelectItem> getLstProfessioniAltri();
	public List<SelectItem> getLstProfessioniAttuale();
	public List<SelectItem> getLstCondLavorativa();
	public List<SelectItem> getLstLipoPatente();
	public List<SelectItem> getLstDisponiblita();
	public List<SelectItem> getLstYesNoRadio();
	public List<SelectItem> getDisponibilitaSpostamento();
	public List<SelectItem> getMotivoRicorso();
	public List<SelectItem> getLstProfessioniIta();
	public List<SelectItem> getLstProfessioniEstero();
	
	public void onChangeBpatente();
	public void onChangeNessunaRic();
	public void onChangeAltro();
	public void onChangeOnly();
	public void onChangeRivoltoAqualcuno();

	public boolean isNew();
	public void preValorizzaLavoro(BigDecimal idLavoro);
	
	
	
}
