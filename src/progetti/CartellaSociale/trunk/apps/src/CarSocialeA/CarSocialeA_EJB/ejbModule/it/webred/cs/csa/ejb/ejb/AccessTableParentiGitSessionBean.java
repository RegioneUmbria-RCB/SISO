package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableParentiGitSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.IndirizzoDAO;
import it.webred.cs.csa.ejb.dao.ParentiDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InfoRecapitiDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAComponenteGit;
import it.webred.cs.data.model.CsAFamigliaGruppoGit;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;
import it.webred.siso.ws.ricerca.dto.FamiliareDettaglio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class AccessTableDataMediciSessionBean
 */
@Stateless
public class AccessTableParentiGitSessionBean extends CarSocialeBaseSessionBean implements AccessTableParentiGitSessionBeanRemote {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ParentiDAO parentiDAO;
	
	@Autowired
	private SoggettoDAO soggettoDAO;
	
	@Autowired
	private IndirizzoDAO indirizzoDAO;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDAO;
	
    public AccessTableParentiGitSessionBean() {
        // TODO Auto-generated constructor stub
    }
    
    public CsAFamigliaGruppoGit getFamigliaGruppoGit(BaseDTO dto) {
    	return parentiDAO.getFamigliaGruppoGit((Long) dto.getObj());
    }
    
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    public List<CsAFamigliaGruppoGit> getFamigliaGruppoGitAggiornate(BaseDTO dto) {
    	return parentiDAO.getFamiglieGruppoGitAggiornate();
    }
    
    public void createFamigliaGruppoGit(BaseDTO dto) {
    	
    	CsASoggettoLAZY csASoggetto = (CsASoggettoLAZY) dto.getObj();
    	CsAFamigliaGruppoGit famigliaGit = new CsAFamigliaGruppoGit();
    	famigliaGit.setCsASoggetto(csASoggetto);
    	famigliaGit.setDataFineApp(DataModelCostanti.END_DATE);
    	famigliaGit.setDataInizioApp(new Date());
    	famigliaGit.setDataInizioSys(new Date());
    	famigliaGit.setUserIns(dto.getUserId());
    	famigliaGit.setDtIns(new Date());
    	    	
    	famigliaGit.setCsAComponenteGits(null);
    	famigliaGit = parentiDAO.saveFamigliaGruppoGit(famigliaGit);
    
    	List<FamiliareDettaglio> lista = (List<FamiliareDettaglio>)dto.getObj2();	
		/* Valorizzo i familiari*/
    	if(lista!=null){
	    	for(FamiliareDettaglio c: lista) {
	    		CsAComponenteGit componenteGit = this.initFamiliare(c, famigliaGit, dto.getUserId());
	    		parentiDAO.saveComponenteGit(componenteGit);	
	    	}
    	}

    }
    
    private CsAComponenteGit initFamiliare(FamiliareDettaglio c, CsAFamigliaGruppoGit famigliaGit, String userId){
		CsAComponenteGit componenteGit = new CsAComponenteGit();
		
		componenteGit.setCsAFamigliaGruppoGit(famigliaGit);
		componenteGit.setUserIns(userId);
		componenteGit.setDtIns(new Date());
		componenteGit.setFlgSegnalazione(true);
		
		componenteGit.setCognome(c.getCognome());
		componenteGit.setNome(c.getNome());
		componenteGit.setSesso(c.getSesso());
		componenteGit.setCf(c.getCodfisc());
		componenteGit.setDataNascita(c.getDataNascita());
		componenteGit.setDataDecesso(c.getDataMorte());
		
		// Cittadinanza
		componenteGit.setCittadinanza(c.getCittadinanza());
		
		if(c.getComuneNascita()!=null){
			componenteGit.setComuneNascitaCod(c.getComuneNascita().getCodIstatComune());
			componenteGit.setComuneNascitaDes(c.getComuneNascita().getDenominazione());
			componenteGit.setProvNascitaCod(c.getComuneNascita().getSiglaProv());
		}
		
		if(c.getNazioneNascita()!=null){
			componenteGit.setStatoNascitaCod(c.getNazioneNascita().getCodIstatNazione());
			componenteGit.setStatoNascitaDes(c.getNazioneNascita().getNazione());
		}
		
		componenteGit.setIndirizzoRes(c.getIndirizzoResidenza());
		componenteGit.setNumCivRes(c.getCivicoResidenza());
		
		if(c.getComuneResidenza()!=null){
			componenteGit.setComResCod(c.getComuneResidenza().getCodIstatComune());
			componenteGit.setComResDes(c.getComuneResidenza().getDenominazione());
			componenteGit.setProvResCod(c.getComuneResidenza().getSiglaProv());
		}
		componenteGit.setCsTbTipoRapportoCon(c.getParentela());
		componenteGit.setParentelaValida(c.isParentelaValida());
		
		return componenteGit;
    }
    
	public void updateComponenteGit(BaseDTO dto) {
		parentiDAO.updateComponenteGit((CsAComponenteGit) dto.getObj());
	}
	
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public void updateFamigliaGruppoGit(BaseDTO dto) {
		parentiDAO.updateFamigliaGruppoGit((CsAFamigliaGruppoGit) dto.getObj());
	}
	
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public void elaboraVariazioniFamigliaGruppoGit(BaseDTO dto) {
		CsAFamigliaGruppoGit famiglia = (CsAFamigliaGruppoGit)dto.getObj();
		List<FamiliareDettaglio> lista = (List<FamiliareDettaglio>)dto.getObj2();
		String cfIntestatario = famiglia.getCsASoggetto().getCsAAnagrafica().getCf();
		HashMap<String, CsAComponenteGit> mappaNew = new HashMap<String, CsAComponenteGit>();
		for(FamiliareDettaglio f : lista){
			//salto il soggetto da cui provengo
			if(cfIntestatario.equalsIgnoreCase(f.getCodfisc()))
				continue;
			
			CsAComponenteGit c = this.initFamiliare(f, famiglia, dto.getUserId());
			mappaNew.put(f.getCodfisc().toUpperCase(), c);
		}
			
		boolean segnalazioneFamiglia = false;
		List<CsAComponenteGit> componentiNew = new ArrayList<CsAComponenteGit>();
		List<CsAComponenteGit> componentiOld = famiglia.getCsAComponenteGits();
		List<BigDecimal> toRemove = new ArrayList<BigDecimal>();
		for(CsAComponenteGit o : componentiOld){
			String key = o.getCf().toUpperCase();
			CsAComponenteGit f = mappaNew.get(key);
			boolean segnalazione = false;
			
			if(f!=null){ //IN CASO CONTRARIO DEVE ESSERE ELIMINATO
				if(!compare(f.getCognome(), o.getCognome())){
					segnalazione=true;
					o.setCognome(f.getCognome());
				}
				if(!compare(f.getNome(), o.getNome())){
					segnalazione=true;
					o.setNome(f.getNome());
				}
				if(!compare(f.getCf(), o.getCf())){
					segnalazione=true;
					o.setCf(f.getCf());
				}
				if(!compare(f.getDataNascita(), o.getDataNascita())){
					segnalazione=true;
					o.setDataNascita(f.getDataNascita());
				}
				if(!compare(f.getDataDecesso(), o.getDataDecesso())){
					segnalazione=true;
					o.setDataDecesso(f.getDataDecesso());
				}
				if(!compare(f.getComuneNascitaCod(), o.getComuneNascitaCod())){
					segnalazione=true;
					o.setComuneNascitaCod(f.getComuneNascitaCod());
				}
				if(!compare(f.getComuneNascitaDes(), o.getComuneNascitaDes())){
					segnalazione=true;
					o.setComuneNascitaDes(f.getComuneNascitaDes());
				}
				if(!compare(f.getProvNascitaCod(), o.getProvNascitaCod())){
					segnalazione=true;
					o.setProvNascitaCod(f.getProvNascitaCod());
				}
				if(!compare(f.getStatoNascitaCod(), o.getStatoNascitaCod())){
					segnalazione=true;
					o.setStatoNascitaCod(f.getStatoNascitaCod());
				}
				if(!compare(f.getStatoNascitaDes(), o.getStatoNascitaDes())){
					segnalazione=true;
					o.setStatoNascitaDes(f.getStatoNascitaDes());
				}
				if(!compare(f.getIndirizzoRes(), o.getIndirizzoRes())){
					segnalazione=true;
					o.setIndirizzoRes(f.getIndirizzoRes());
				}
				if(!compare(f.getNumCivRes(), o.getNumCivRes())){
					segnalazione=true;
					o.setNumCivRes(f.getNumCivRes());
				}
				if(!compare(f.getComResCod(), o.getComResCod())){
					segnalazione=true;
					o.setComResCod(f.getComResCod());
				}
				if(!compare(f.getComResDes(), o.getComResDes())){
					segnalazione=true;
					o.setComResDes(f.getComResDes());
				}
				if(!compare(f.getProvResCod(), o.getProvResCod())){
					segnalazione=true;
					o.setProvResCod(f.getProvResCod());
				}
				if(!compare(f.getCsTbTipoRapportoCon()!=null ? f.getCsTbTipoRapportoCon().getId().toString() : null, o.getCsTbTipoRapportoCon()!=null ? o.getCsTbTipoRapportoCon().getId().toString() : null)){
					segnalazione=true;
					o.setCsTbTipoRapportoCon(f.getCsTbTipoRapportoCon());
				}
				if(!f.getParentelaValida().equals(o.getParentelaValida())){
					segnalazione=true;
					o.setParentelaValida(f.getParentelaValida());
				}
				if(!compare(f.getCittadinanza(), o.getCittadinanza())){
					segnalazione=true;
					o.setCittadinanza(f.getCittadinanza());
				}
				
				if(segnalazione){
					segnalazioneFamiglia = true;
					o.setUsrMod(dto.getUserId());
					o.setDtMod(new Date());
					o.setFlgSegnalazione(segnalazione);
				}
			
				mappaNew.remove(key);
				componentiNew.add(o);
			}else{
				//Se non lo trovo lo aggiungo ai componenti da rimuovere
				toRemove.add(o.getId());
			}
		}
		
		/*Elimino la lista di componenti non più presenti*/
		int rimossi = parentiDAO.deleteComponenteGit(toRemove);
		if(rimossi>0) segnalazioneFamiglia=true; 
		
		/*Aggiungo i nuovi componenti familiari*/	
		if(!mappaNew.isEmpty()){
			segnalazioneFamiglia=true;
			Iterator it = mappaNew.keySet().iterator();
			while(it.hasNext())
				componentiNew.add(mappaNew.get(it.next()));
		}
				
		famiglia.setCsAComponenteGits(componentiNew);
				
		famiglia.setFlgSegnalazione(segnalazioneFamiglia);
		famiglia.setUsrMod(dto.getUserId());
		famiglia.setDtMod(new Date());
		parentiDAO.updateFamigliaGruppoGit(famiglia);
			
	}

	public static boolean compare(String str1, String str2) {
	    return (str1 == null ? str2 == null : str1.equals(str2));
	}
	
	public static boolean compare(Date str1, Date str2) {
	    return (str1 == null ? str2 == null : str1.equals(str2));
	}
	
	@Override
	public InfoRecapitiDTO findInfoRecapiti(BaseDTO dto){
		InfoRecapitiDTO i = null;
		CsASoggettoLAZY s = soggettoDAO.getSoggettoByCF((String) dto.getObj());
		if(s!=null){
			i = new InfoRecapitiDTO();
			i.setCartellaSociale(true);
			i.setCellulare(s.getCsAAnagrafica().getCell());
			i.setTelefono(s.getCsAAnagrafica().getTel());
			i.setEmail(s.getCsAAnagrafica().getEmail());
			
			List<CsAIndirizzo> inds = indirizzoDAO.getIndirizziBySoggetto(s.getAnagraficaId());
			for(CsAIndirizzo ind : inds){
				if(ind.getCsTbTipoIndirizzo().getId() == DataModelCostanti.TipiIndirizzi.RESIDENZA_ID && ind.getDataFineApp() == null){
					i.setResIndirizzo(ind.getCsAAnaIndirizzo().getIndirizzo());
					i.setResCivico(ind.getCsAAnaIndirizzo().getCivicoNumero());
					i.setResComune(new KeyValueDTO(ind.getCsAAnaIndirizzo().getComCod(), ind.getCsAAnaIndirizzo().getComDes()));
					i.setResStato( new KeyValueDTO(ind.getCsAAnaIndirizzo().getStatoCod(), ind.getCsAAnaIndirizzo().getStatoDes()));
				} 
			}
		}else
			i = parentiDAO.findInfoRecapiti((String) dto.getObj());
		
		return i;
	}

	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public List<CsAFamigliaGruppoGit> getFamigliaGruppoProvenienza(BaseDTO bDto) {
		return parentiDAO.getFamiglieGruppoGit((String)bDto.getObj());
	}
	
	
}
