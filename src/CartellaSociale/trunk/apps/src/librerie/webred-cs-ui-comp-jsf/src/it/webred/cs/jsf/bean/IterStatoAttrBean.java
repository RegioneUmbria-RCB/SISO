package it.webred.cs.jsf.bean;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoAttributo;
import it.webred.cs.data.model.CsCfgAttr;
import it.webred.cs.data.model.CsCfgAttrOption;
import it.webred.utilities.CommonUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

public class IterStatoAttrBean implements Serializable{

	private static final long serialVersionUID = 1L;
	protected TipoAttributo.Enum eTipoAttributo;

	protected boolean required;
	protected Long idAttr;
	protected String label;

	protected Object valore;
	protected String tooltip;
	protected String requiredMessage;
 	protected List<SelectItem> listaCombo;
	
	public IterStatoAttrBean( CsCfgAttr itStatoAttr )
	{
		eTipoAttributo = DataModelCostanti.TipoAttributo.ToEnum(itStatoAttr.getTipoAttr()); 
		
		idAttr = itStatoAttr.getId();
		label = itStatoAttr.getLabel();
		requiredMessage = itStatoAttr.getMessaggioObbligatorio();
		required = !StringUtils.isEmpty(requiredMessage); 
		valore = itStatoAttr.getValoreDefault();
		
		listaCombo = new LinkedList<SelectItem>();
		if( isList() )
			for( CsCfgAttrOption itOption : itStatoAttr.getCsCfgAttrOptions() )
				listaCombo.add( new SelectItem(itOption.getId(), itOption.getValore()) );

		createTooltip( itStatoAttr );
	}

	protected void createTooltip( CsCfgAttr itStatoAttr ) {
		tooltip = itStatoAttr.getTooltip();
		if( isList() )
		{
			List<String> list = new LinkedList<String>();
			for( CsCfgAttrOption itOption : itStatoAttr.getCsCfgAttrOptions() ) {
				if(itOption.getTooltip() != null && !"".equals(itOption.getTooltip()))
					list.add( "<strong>" + itOption.getValore() + "</strong> " + itOption.getTooltip() );
			}
			if(!list.isEmpty())
				tooltip = CommonUtils.Join("<br/>", list.toArray());
			
		}
		
	}
	
	public boolean isBool() {
		return eTipoAttributo == TipoAttributo.Enum.BOOLEAN;
	}

	public boolean isString() {
		return eTipoAttributo == TipoAttributo.Enum.STRING;
	}

	public boolean isInteger() {
		return eTipoAttributo == TipoAttributo.Enum.INTEGER;
	}

	public boolean isDate() {
		return eTipoAttributo == TipoAttributo.Enum.DATE;
	}

	public boolean isList() {
		return eTipoAttributo == TipoAttributo.Enum.LIST;
	}

	public String getLabel() {
		return label;
	}

	public String getTooltip() {
		return tooltip;
	}

	public String getRequiredMessage() {
		return requiredMessage;
	}

	public List<SelectItem> getListaCombo() {
		return listaCombo;
	}
	
	public Object getValore() {
		return valore;
	}

	public void setValore(Object valore) {
		this.valore = valore;
	}

	public boolean isRequired() {
		return required;
	}

	public Long getIdAttr() {
		return idAttr;
	}
	
}