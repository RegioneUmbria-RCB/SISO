package it.webred.cs.csa.ejb.dto.retvalue;

import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.OrganizzazioneDTO;
import it.webred.cs.csa.ejb.dto.SettoreDTO;
import it.webred.cs.data.model.CsDDiarioBASIC;
import it.webred.cs.data.model.CsItStepAttrValueBASIC;
import it.webred.cs.data.model.CsItStepLAZY;
import it.webred.cs.data.model.CsOOperatoreBASIC;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CsIterStepByCasoDTO implements Serializable {

	private Date dataAmministrativa;
	private String resposabileDenominazione;
	
	private Long idCaso;
	private List<KeyValueDTO> listaAttributi;
	
	private Long idStatoIter;
	private String nomeStatoIter;
	
	private OrganizzazioneDTO enteSegnalante;
	private SettoreDTO settoreSegnalante;
	private KeyValueDTO operatoreSegnalante;
	
	private OrganizzazioneDTO enteSegnalato;
	private SettoreDTO settoreSegnalato;
	private KeyValueDTO operatoreSegnalato;

	private String nota;
	private String cssClassStato;
	private String segnalatoDaLabel;
	private String segnalatoALabel;
	private String sezioneAttributiLabel;
	
	public CsIterStepByCasoDTO(){}
	
	public CsIterStepByCasoDTO(CsItStepLAZY csItStep, CsOOperatoreBASIC operatoreResponsabile)  throws Exception {
		this();

		if (operatoreResponsabile!=null)
			setResposabileDenominazione(operatoreResponsabile.getDenominazione());
		
		// carico le liste
		if(csItStep!=null){
			
			setIdCaso(csItStep.getCsACasoId());
			setIdStatoIter(csItStep.getCsCfgItStato().getId());
			setNomeStatoIter(csItStep.getCsCfgItStato().getNome());
			
			LinkedList<KeyValueDTO> listaAttributi = new LinkedList<KeyValueDTO>();
			List<CsItStepAttrValueBASIC> attrValues = csItStep.getCsItStepAttrValues();
			if(attrValues!=null){
				for (CsItStepAttrValueBASIC itStepValue : attrValues)
					listaAttributi.add( new KeyValueDTO(itStepValue.getCsCfgAttr().getLabel(), itStepValue.getValore() ) );
			}
			setListaAttributi(listaAttributi);
			
			/*Dati segnalante*/
			OrganizzazioneDTO enteSegnalante = null;
			if(csItStep.getCsOOrganizzazione1()!=null){
				enteSegnalante = new OrganizzazioneDTO(csItStep.getCsOOrganizzazione1());
				setEnteSegnalante(enteSegnalante);
			}
			
			if(csItStep.getCsOSettore1()!=null){
				SettoreDTO settoreSegnalante = new SettoreDTO();
				
				settoreSegnalante.setId(csItStep.getCsOSettore1().getId());
				settoreSegnalante.setNome(csItStep.getCsOSettore1().getNome());			
				settoreSegnalante.setOrganizzazione(enteSegnalante);
				
				setSettoreSegnalante(settoreSegnalante);
			}
			
			if(csItStep.getCsOOperatore1()!=null)
				setOperatoreSegnalante(new KeyValueDTO(csItStep.getCsOOperatore1().getId(), csItStep.getCsOOperatore1().getDenominazione()));
			
			/*Dati segnalato*/
			OrganizzazioneDTO enteSegnalato = null;
			if(csItStep.getCsOOrganizzazione2()!=null){
				enteSegnalato = new OrganizzazioneDTO(csItStep.getCsOOrganizzazione2());
				setEnteSegnalato(enteSegnalato);
			}
			
			if(csItStep.getCsOSettore2()!=null){
				SettoreDTO settoreSegnalato = new SettoreDTO();
				
				settoreSegnalato.setId(csItStep.getCsOSettore2().getId());
				settoreSegnalato.setNome(csItStep.getCsOSettore2().getNome());			
				settoreSegnalato.setOrganizzazione(enteSegnalato);
				
				setSettoreSegnalato(settoreSegnalato);
			}
			
			if(csItStep.getCsOOperatore2()!=null)
				setOperatoreSegnalato(new KeyValueDTO(csItStep.getCsOOperatore2().getId(), csItStep.getCsOOperatore2().getDenominazione()));
			
			setNota(csItStep.getNota());
			setCssClassStato(csItStep.getCsCfgItStato().getCssClassStato());
			setSegnalatoDaLabel(csItStep.getCsCfgItStato().getSegnalatoDaLabel());
			setSegnalatoALabel(csItStep.getCsCfgItStato().getSegnalatoALabel());
			setSezioneAttributiLabel(csItStep.getCsCfgItStato().getSezioneAttributiLabel());
			
			CsDDiarioBASIC diario = csItStep.getCsDDiario();
			Date dataAmministrativa = diario.getDtAmministrativa();
			setDataAmministrativa(dataAmministrativa);
			
		}
	}
	
	public Date getDataAmministrativa() {
		return dataAmministrativa;
	}
	public void setDataAmministrativa(Date dataAmministrativa) {
		this.dataAmministrativa = dataAmministrativa;
	}
	public String getResposabileDenominazione() {
		return resposabileDenominazione;
	}
	public void setResposabileDenominazione(String resposabileDenominazione) {
		this.resposabileDenominazione = resposabileDenominazione;
	}
	public Long getIdCaso() {
		return idCaso;
	}
	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}
	public List<KeyValueDTO> getListaAttributi() {
		return listaAttributi;
	}
	public void setListaAttributi(List<KeyValueDTO> listaAttributi) {
		this.listaAttributi = listaAttributi;
	}
	public Long getIdStatoIter() {
		return idStatoIter;
	}
	public void setIdStatoIter(Long idStatoIter) {
		this.idStatoIter = idStatoIter;
	}
	public String getNomeStatoIter() {
		return nomeStatoIter;
	}
	public void setNomeStatoIter(String nomeStatoIter) {
		this.nomeStatoIter = nomeStatoIter;
	}
	public OrganizzazioneDTO getEnteSegnalante() {
		return enteSegnalante;
	}
	public void setEnteSegnalante(OrganizzazioneDTO enteSegnalante) {
		this.enteSegnalante = enteSegnalante;
	}
	public SettoreDTO getSettoreSegnalante() {
		return settoreSegnalante;
	}
	public void setSettoreSegnalante(SettoreDTO settoreSegnalante) {
		this.settoreSegnalante = settoreSegnalante;
	}
	public OrganizzazioneDTO getEnteSegnalato() {
		return enteSegnalato;
	}
	public void setEnteSegnalato(OrganizzazioneDTO enteSegnalato) {
		this.enteSegnalato = enteSegnalato;
	}
	public SettoreDTO getSettoreSegnalato() {
		return settoreSegnalato;
	}
	public void setSettoreSegnalato(SettoreDTO settoreSegnalato) {
		this.settoreSegnalato = settoreSegnalato;
	}
	public KeyValueDTO getOperatoreSegnalante() {
		return operatoreSegnalante;
	}

	public void setOperatoreSegnalante(KeyValueDTO operatoreSegnalante) {
		this.operatoreSegnalante = operatoreSegnalante;
	}

	public KeyValueDTO getOperatoreSegnalato() {
		return operatoreSegnalato;
	}

	public void setOperatoreSegnalato(KeyValueDTO operatoreSegnalato) {
		this.operatoreSegnalato = operatoreSegnalato;
	}

	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getCssClassStato() {
		return cssClassStato;
	}
	public void setCssClassStato(String cssClassStato) {
		this.cssClassStato = cssClassStato;
	}
	public String getSegnalatoDaLabel() {
		return segnalatoDaLabel;
	}
	public void setSegnalatoDaLabel(String segnalatoDaLabel) {
		this.segnalatoDaLabel = segnalatoDaLabel;
	}
	public String getSegnalatoALabel() {
		return segnalatoALabel;
	}
	public void setSegnalatoALabel(String segnalatoALabel) {
		this.segnalatoALabel = segnalatoALabel;
	}
	public String getSezioneAttributiLabel() {
		return sezioneAttributiLabel;
	}
	public void setSezioneAttributiLabel(String sezioneAttributiLabel) {
		this.sezioneAttributiLabel = sezioneAttributiLabel;
	}
}
