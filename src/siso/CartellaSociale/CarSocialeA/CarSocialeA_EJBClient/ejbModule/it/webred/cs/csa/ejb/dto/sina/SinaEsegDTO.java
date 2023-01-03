package it.webred.cs.csa.ejb.dto.sina;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.DataModelCostanti.TipoSinaDomanda;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsDSinaEseg;
import it.webred.cs.data.model.CsTbSinaDomanda;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class SinaEsegDTO extends CeTBaseObject {
	private static final long serialVersionUID = 1L;
	
	private Long sinaId;
	private Long diarioMultidimId;
	private Long interventoEsegMastId;
	private Boolean flagValutaDopo;
	private Date data;
	
	protected HashMap<Long, CsTbSinaDomanda> domandas;
	protected HashMap<Long, String> rispostas;

	private List<CsTbSinaDomanda> lstSinaParams;
	private List<Long> lstSinaParamInvalidita = new ArrayList<Long>();
	private List<String> lstPrestazioniInpsScelte = new ArrayList<String>();	
	private List<KeyValueDTO> riepilogo;
	private Date dataUltimaModifica;
	private String operatoreUltimaModifica;
	
	public SinaEsegDTO() {
		lstPrestazioniInpsScelte = new ArrayList<String>();	
	}
	
	public static SinaEsegDTO nuovo(List<CsTbSinaDomanda> params){
		return create(new CsDSina(), params, new ArrayList<CsDSinaEseg>(), new ArrayList<String>());
	}
	
	public static SinaEsegDTO create(CsDSina csDSina, List<CsTbSinaDomanda> params, List<CsDSinaEseg> lstSinaEseg, List<String> prestazioniInps) {
		try{
		
			return new SinaEsegDTO().init(csDSina, params, lstSinaEseg, prestazioniInps);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static SinaEsegDTO clone(CsDSina csDSina, List<CsTbSinaDomanda> params, List<CsDSinaEseg> lstSinaEseg, List<String> prestazioniInps) {
		CsDSina  sina = new CsDSina();
		sina.setData(csDSina.getData());
		sina.setFlagValutaDopo(csDSina.getFlagValutaDopo());
		//sina.setArTbPrestazioniInps(csDSina.getArTbPrestazioniInps());
		
		List<CsDSinaEseg> lstSinaEsegClone = new ArrayList<CsDSinaEseg>();
		for(CsDSinaEseg es : lstSinaEseg) {
			CsDSinaEseg es2 = new CsDSinaEseg();
			es2.setCsTbSinaDomanda(es.getCsTbSinaDomanda());
			es2.setCsTbSinaRisposta(es.getCsTbSinaRisposta());
			lstSinaEsegClone.add(es2);
		}
		
		return new SinaEsegDTO().init(sina, params, lstSinaEsegClone, prestazioniInps);
		
	}
	
	protected SinaEsegDTO init(CsDSina csDSina, List<CsTbSinaDomanda> params, List<CsDSinaEseg> lstSinaEseg, List<String> prestazioniInps) {
		lstSinaParams = new ArrayList<CsTbSinaDomanda>();
		lstSinaParams.addAll(params);
		
		domandas = new HashMap<Long, CsTbSinaDomanda>();
		rispostas = new HashMap<Long, String>();
		
		if(csDSina!=null) {
			sinaId = csDSina.getId();
			data = csDSina.getData();
			flagValutaDopo = csDSina.getFlagValutaDopo();
			diarioMultidimId = csDSina.getDiarioId();
			interventoEsegMastId = csDSina.getIntEsegMastId();
		}
		lstPrestazioniInpsScelte.addAll(prestazioniInps);
		
		for (CsDSinaEseg csRelSinaEseg: lstSinaEseg) {
			CsTbSinaDomanda csTbSinaDomanda = csRelSinaEseg.getCsTbSinaDomanda();
			
		    if(csTbSinaDomanda.getId() == TipoSinaDomanda.INVALIDITA_CIVILE){
		    	this.lstSinaParamInvalidita.add(csRelSinaEseg.getCsTbSinaRisposta().getId());
		    }else{
		    	this.domandas.put(csTbSinaDomanda.getId(), csRelSinaEseg.getCsTbSinaDomanda());
		    	this.rispostas.put(csTbSinaDomanda.getId(), String.valueOf(csRelSinaEseg.getCsTbSinaRisposta().getId()) );
		    }
		}
		
		for(CsTbSinaDomanda d: params){
			if(!this.rispostas.containsKey(d.getId()) && d.getCsTbSinaRispostas().size() > 0 && d.getId() != TipoSinaDomanda.INVALIDITA_CIVILE)
				this.rispostas.put(d.getId(), null);
		}

		this.loadRiepilogo();
		
		this.dataUltimaModifica = csDSina.getDtMod()!=null ? csDSina.getDtMod() : csDSina.getDtIns();
		this.operatoreUltimaModifica  = !StringUtils.isBlank(csDSina.getUserMod()) ? csDSina.getUserMod() : csDSina.getUserIns();
		
		return this;
	}
	
	

	/*
	 * public CsDDiario getDiario() { return diario; }
	 * 
	 * public void associaDiario(Long idDiario){ if(idDiario!=null){
	 * if(diario==null){ diario = new CsDDiario(); diario.setId(idDiario); } } }
	 */
	
	/*
	 * public void prestazioniScelte(List<ArTbPrestazioniInps> lstPrestazioniInps){
	 * //this.csDSina.getArTbPrestazioniInps().clear(); for(ArTbPrestazioniInps p :
	 * lstPrestazioniInps){ for(String s : lstPrestazioniInpsScelte) if
	 * (s.contentEquals(p.getCodice())){ this.prestazioniSina.add(p);
	 * //this.csDSina.addArTbPrestazioniInps(p); } }
	 * 
	 * //this.csDSina.setArTbPrestazioniInps(prest); }
	 */
	
	public HashMap<Long, CsTbSinaDomanda> getDomandas() {
		return domandas;
	}

	public HashMap<Long, String> getRispostas() {
		return rispostas;
	}

	public void setRispostas(HashMap<Long, String> rispostas) {
		this.rispostas = rispostas;
	}

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

	public boolean validaRispostas(){
		boolean valida = true;
		Iterator<Long> set = this.rispostas.keySet().iterator();
		while(valida && set.hasNext()){
			Long id = set.next();
			if(id!=3 && id!=5 && id!=11 && this.rispostas.get(id)==null)
				valida = false;
		}
		
		//** risposte invalidità non obbligatorie SISO-976 **//
		//** risposte prestazioni inps non obbligatorie SISO-976 **//
		//if(this.getLstSinaParamInvalidita().isEmpty() || this.getLstPrestazioniInpsScelte().isEmpty())
		//	valida = false;

		return valida;
	}

	public Long getSinaId() {
		return sinaId;
	}

	public void setSinaId(Long sinaId) {
		this.sinaId = sinaId;
	}

	public Long getInterventoEsegMastId() {
		return interventoEsegMastId;
	}

	public void setInterventoEsegMastId(Long interventoEsegMastId) {
		this.interventoEsegMastId = interventoEsegMastId;
	}

	public Boolean getFlagValutaDopo() {
		return flagValutaDopo;
	}

	public void setFlagValutaDopo(Boolean flagValutaDopo) {
		this.flagValutaDopo = flagValutaDopo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getDiarioMultidimId() {
		return diarioMultidimId;
	}

	public void setDiarioMultidimId(Long diarioMultidimId) {
		this.diarioMultidimId = diarioMultidimId;
	}
	
	public List<KeyValueDTO> getRiepilogo(){
		return riepilogo;
	}
	
	public void loadRiepilogo() {
		riepilogo = new ArrayList<KeyValueDTO>();
		for(CsTbSinaDomanda dom : lstSinaParams){
			Long domId = dom.getId();
			KeyValueDTO kv = new KeyValueDTO();
			kv.setCodice(domId);
			kv.setDescrizione(dom.getTesto());
			if(domId == TipoSinaDomanda.INVALIDITA_CIVILE)
				kv.setAbilitato(!this.lstSinaParamInvalidita.isEmpty());
			else {
				String ris = this.rispostas.get(domId);
				kv.setAbilitato(StringUtils.isNotBlank(ris));
			}
			
			riepilogo.add(kv);
		}
		
		KeyValueDTO kvinps = new KeyValueDTO();
		kvinps.setDescrizione("Necessità di prestazioni (opzionale)");
		kvinps.setAbilitato(!this.lstPrestazioniInpsScelte.isEmpty());
		riepilogo.add(kvinps);
	}

	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getOperatoreUltimaModifica() {
		return operatoreUltimaModifica;
	}

	public void setOperatoreUltimaModifica(String operatoreUltimaModifica) {
		this.operatoreUltimaModifica = operatoreUltimaModifica;
	}
	
	
	
}
