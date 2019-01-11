package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsTbSinaDomanda;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsDSinaEseg;
import it.webred.cs.data.model.CsTbSinaRisposta;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SinaEsegDTO extends CeTBaseObject {
	private static final long serialVersionUID = 1L;
	protected CsDSina csDSina;
	protected HashMap<Long, CsTbSinaDomanda> domandas = new HashMap<Long, CsTbSinaDomanda>();
	protected HashMap<Long, String> rispostas = new HashMap<Long, String>();
	//protected HashMap<Long, CsTbSinaDomanda> domandaInvalidita = new HashMap<Long, CsTbSinaDomanda>();
	//protected HashMap<Long, String> rispostaInvalidita = new HashMap<Long, String>();
	private List<CsTbSinaDomanda> lstSinaParams;
	private List<Long> lstSinaParamInvalidita = new ArrayList<Long>();
	private List<String> lstPrestazioniInpsScelte = new ArrayList<String>();
	
	
	public static SinaEsegDTO create(CsDSina csDSina, List<CsTbSinaDomanda> params) {
		return new SinaEsegDTO().init(csDSina, params);
	}
	
	protected SinaEsegDTO init(CsDSina csDSina, List<CsTbSinaDomanda> params) {
		lstSinaParams = new ArrayList<CsTbSinaDomanda>();
		lstSinaParams=params;
		
		this.csDSina = csDSina!=null ? csDSina : new CsDSina();

		for (Iterator<CsDSinaEseg> i = csDSina.getCsDSinaEseg().iterator(); i.hasNext();) {
			CsDSinaEseg csRelSinaEseg = i.next(); 
			CsTbSinaDomanda csTbSinaDomanda = csRelSinaEseg.getCsTbSinaDomanda();
			
		    if(csTbSinaDomanda.getId() == Long.parseLong("10")){
		    	this.lstSinaParamInvalidita.add(csRelSinaEseg.getCsTbSinaRisposta().getId());
		    	
		    }else{
		    	
			this.domandas.put(csTbSinaDomanda.getId(), csRelSinaEseg.getCsTbSinaDomanda());
			this.rispostas.put(csTbSinaDomanda.getId(), String.valueOf(csRelSinaEseg.getCsTbSinaRisposta().getId()) );
		    }
		}
		
		for(CsTbSinaDomanda d: params){
			if(!this.rispostas.containsKey(d.getId()) && d.getCsTbSinaRispostas().size() > 0 && d.getId() != Long.parseLong("10"))
				this.rispostas.put(d.getId(), null);
				//this.rispostas.put(d.getId(), d.getCsTbSinaRispostas().get(0).getId());
		}
//         this.domandaInvalidita = (HashMap<Long, CsTbSinaDomanda>) domandas.clone();
//         this.rispostaInvalidita = (HashMap<Long, String>) rispostas.clone();
		return this;
	}

	public CsDDiario getDiario() {
		return this.csDSina.getCsDDiario();
	}
	
	public void associaDiario(Long idDiario){
		if(this.csDSina == null){
			this.csDSina = new CsDSina();
			CsDDiario d = new CsDDiario();
			d.setId(idDiario);
			this.csDSina.setCsDDiario(d);
		}
		if(this.csDSina != null && this.csDSina.getCsDDiario()==null) {
			CsDDiario d = new CsDDiario();
			d.setId(idDiario);
			csDSina.setCsDDiario(d);
		}
	}
	
	public void prestazioniScelte(List<ArTbPrestazioniInps> lstPrestazioniInps){
		List<ArTbPrestazioniInps> prest = new ArrayList<ArTbPrestazioniInps>();
		this.csDSina.getArTbPrestazioniInps().clear();
		for(ArTbPrestazioniInps p : lstPrestazioniInps){
			for(String s : lstPrestazioniInpsScelte)
			if (s.contentEquals(p.getCodice())){
				prest.add(p);
				this.csDSina.addArTbPrestazioniInps(p);
			}
		}
		
		//this.csDSina.setArTbPrestazioniInps(prest);
	}

	
	
	public CsIInterventoEsegMast getInterventoEsegMast() {
		return this.csDSina.getCsIInterventoEsegMast();
	}

	public CsTbSinaDomanda getDomandaEsegById(Long id) {
		return this.domandas.get(id);
	}

	public CsDSina getCsDSina() {
		return csDSina;
	}

	public void setCsDSina(CsDSina csDSina) {
		this.csDSina = csDSina;
	}

	public HashMap<Long, CsTbSinaDomanda> getDomandas() {
		return domandas;
	}

	public void setDomandas(HashMap<Long, CsTbSinaDomanda> domandas) {
		this.domandas = domandas;
	}

	public HashMap<Long, String> getRispostas() {
		return rispostas;
	}

	public void setRispostas(HashMap<Long, String> rispostas) {
		this.rispostas = rispostas;
	}

//	public HashMap<Long, CsTbSinaDomanda> getDomandaInvalidita() {
//		return domandaInvalidita;
//	}
//
//	public void setDomandaInvalidita(
//			HashMap<Long, CsTbSinaDomanda> domandaInvalidita) {
//		this.domandaInvalidita = domandaInvalidita;
//	}
//
//	public HashMap<Long, String> getRispostaInvalidita() {
//		return rispostaInvalidita;
//	}
//
//	public void setRispostaInvalidita(HashMap<Long, String> rispostaInvalidita) {
//		this.rispostaInvalidita = rispostaInvalidita;
//	}

	public List<CsTbSinaDomanda> getLstSinaParams() {
		return lstSinaParams;
	}

	public void setLstSinaParams(List<CsTbSinaDomanda> lstSinaParams) {
		this.lstSinaParams = lstSinaParams;
	}

	public List<Long> getLstSinaParamInvalidita() {
		return lstSinaParamInvalidita;
	}

	public void setLstSinaParamInvalidita(List<Long> lstSinaParamInvalidita) {
		this.lstSinaParamInvalidita = lstSinaParamInvalidita;
	}

	public List<String> getLstPrestazioniInpsScelte() {
		return lstPrestazioniInpsScelte;
	}

	public void setLstPrestazioniInpsScelte(List<String> lstPrestazioniInpsScelte) {
		this.lstPrestazioniInpsScelte = lstPrestazioniInpsScelte;
	}

}
