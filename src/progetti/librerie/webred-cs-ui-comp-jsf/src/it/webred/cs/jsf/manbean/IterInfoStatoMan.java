package it.webred.cs.jsf.manbean;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.model.CsItStepAttrValueBASIC;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.jsf.interfaces.IIterInfoStato;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.dto.utility.KeyValuePairBean;
import it.webred.utilities.DateTimeUtils;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class IterInfoStatoMan extends CsUiCompBaseBean implements IIterInfoStato {

	private long idCaso;
	
	private String nomeStato;
	private String dataCreazione;
	private String responsabile;
	private String codEnteSegnalante;
	private String enteSegnalante;
	private String ufficioSegnalante;
	private String operatoreSegnalante;
	private String enteSegnalato;
	private String ufficioSegnalato;
	private String operatoreSegnalato;
	private String nota;
	private String cssClassStato;
	private String segnalatoDaLabel;
	private String segnalatoALabel;
	private String sezioneAttributiLabel;
	
	public List<KeyValuePairBean<String,String>> listaAttrValues;

	@Override
	public String getNomeStato() {
		return nomeStato;
	}
	
	@Override
	public String getDataCreazione() {
		return dataCreazione;
	}

	@Override
	public String getResponsabile() {
		return responsabile;
	}
	
	@Override
	public String getEnteSegnalante() {
		return enteSegnalante;
	}
	
	@Override
	public String getUfficioSegnalante() {
		return ufficioSegnalante;
	}
	
	@Override
	public String getOperatoreSegnalante() {
		return operatoreSegnalante;
	}
	
	@Override
	public String getEnteSegnalato() {
		return enteSegnalato;
	}

	@Override
	public String getUfficioSegnalato() {
		return ufficioSegnalato;
	}

	@Override
	public String getOperatoreSegnalato() {
		return operatoreSegnalato;
	}

	@Override
	public String getNota() {
		return nota;
	}
	
	@Override
	public String getCssClassStato() {
		return cssClassStato;
	}
	
	@Override
	public String getSegnalatoDaLabel() {
		return segnalatoDaLabel;
	}
	
	@Override
	public String getSegnalatoALabel() {
		return segnalatoALabel;
	}

	@Override
	public List<KeyValuePairBean<String,String>> getListaAttrValues() {
		return listaAttrValues;
	}

	@Override
	public String getSezioneAttributiLabel() {
		return sezioneAttributiLabel;
	}
	
	@Override
	public boolean isEnteARendered() {
		if (enteSegnalato == null)
			return false;
		else
			return true;
	}
	
	@Override
	public boolean isUfficioARendered() {
		if (ufficioSegnalato == null)
			return false;
		else
			return true;
	}
	
	@Override
	public boolean isOperatoreARendered() {
		if (operatoreSegnalato == null)
			return false;
		else
			return true;
	}
	
	@Override
	public boolean isNotaRendered() {
		if (nota == null)
			return false;
		else
			return true;
	}
	
	@Override
	public boolean isResponsabileRendered(){
		if(responsabile == null)
			return false;
		else
			return true;
	}
	
	@Override
	public boolean isSezioneAttributiRendered() {
		if (listaAttrValues != null) {
			if (listaAttrValues.isEmpty())
				return false;
			else
				return true;
		}
		return false;
	}
	
	@Override
	public boolean isOpPanelARendered() {
		if (operatoreSegnalato == null && ufficioSegnalato == null && enteSegnalato == null)
			return false;
		else
			return true;
	}
	
	@Override
	public void initialize(CsIterStepByCasoDTO itStepDTO) {	
		
		try {
			if(itStepDTO!=null && itStepDTO.getCsItStep()!=null){
				this.idCaso = itStepDTO.getCsItStep().getCsACasoId();
				this.responsabile = itStepDTO.getResposabileDenominazione();
				this.listaAttrValues = new LinkedList<KeyValuePairBean<String,String>>();
				for(CsItStepAttrValueBASIC itStepValue : itStepDTO.getCsItStep().getCsItStepAttrValues() )
					this.listaAttrValues.add( new KeyValuePairBean<String,String>(itStepValue.getCsCfgAttr().getLabel(), itStepValue.getValore() ) );
				
				this.nomeStato= itStepDTO.getCsItStep().getCsCfgItStato().getNome();
				
				this.dataCreazione = DateTimeUtils.dateToString(itStepDTO.getDataAmministrativa(), "dd/MM/yy - HH:mm");
				
				this.codEnteSegnalante = itStepDTO.getCsItStep().getCsOOrganizzazione1().getCodRouting();
				this.enteSegnalante = itStepDTO.getCsItStep().getCsOOrganizzazione1().getNome();
				this.ufficioSegnalante= itStepDTO.getCsItStep().getCsOSettore1().getNome();
				this.operatoreSegnalante = getCognomeNomeUtente(itStepDTO.getCsItStep().getCsOOperatore1().getUsername());
				
				getSegnalato(itStepDTO.getCsItStep().getCsOOrganizzazione2(),
								itStepDTO.getCsItStep().getCsOSettore2()!=null? itStepDTO.getCsItStep().getCsOSettore2().getNome():null,
								itStepDTO.getCsItStep().getCsOOperatore2()!=null?itStepDTO.getCsItStep().getCsOOperatore2().getUsername():null);
				
				this.nota = itStepDTO.getCsItStep().getNota();
				this.cssClassStato = itStepDTO.getCsItStep().getCsCfgItStato().getCssClassStato();
				this.segnalatoDaLabel = itStepDTO.getCsItStep().getCsCfgItStato().getSegnalatoDaLabel();
				this.segnalatoALabel = itStepDTO.getCsItStep().getCsCfgItStato().getSegnalatoALabel();
				this.sezioneAttributiLabel = itStepDTO.getCsItStep().getCsCfgItStato().getSezioneAttributiLabel();
			}else
				logger.warn(itStepDTO==null ? "itStepDTO è NULL" : (itStepDTO.getCsItStep()==null ? "itStep è NULL" : ""));
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorFromProperties("caricamento.error");
			
		}
	}

	private void getSegnalato(CsOOrganizzazione ente, String ufficio, String operatore2Username) {
		
		try {
		
			if (ente != null)
				this.enteSegnalato = ente.getNome();
			
			if (ufficio != null)
				this.ufficioSegnalato = ufficio;
			
			if (operatore2Username != null)
				this.operatoreSegnalato = getCognomeNomeUtente(operatore2Username);
		
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public long getIdCaso() {
		return idCaso;
	}

	public String getCodEnteSegnalante() {
		return codEnteSegnalante;
	}

	public void setCodEnteSegnalante(String codEnteSegnalante) {
		this.codEnteSegnalante = codEnteSegnalante;
	}
	
}
