package it.umbriadigitale.argo.ejb.cs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.umbriadigitale.argo.data.cs.data.ImportSiruProgettiAttivita;
import it.umbriadigitale.argo.data.cs.data.ImportSiruProgettiAttivitaPK;
import it.umbriadigitale.argo.ejb.base.dao.ArSiruDAO;
import it.umbriadigitale.argo.ejb.client.base.ejbclient.ArgoServiceException;
import it.umbriadigitale.argo.ejb.client.cs.bean.ArSiruService;
import it.umbriadigitale.argo.ejb.client.cs.dto.siru.SiruJsonProgettiDTO;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class ArSiruServiceBean implements ArSiruService {

	@Autowired
	private ArSiruDAO arSiruDAO;
	
	@Override
	public int save(List<SiruJsonProgettiDTO> elenco) throws ArgoServiceException {
		int numInsert = 0;
		HashMap<String,String> mappaCodiciCapofila = new HashMap<String, String>();
		if(elenco!=null && !elenco.isEmpty()){
			List<ImportSiruProgettiAttivita> lst = new ArrayList<ImportSiruProgettiAttivita>();
			for(SiruJsonProgettiDTO json : elenco){
				
				ImportSiruProgettiAttivitaPK id = new ImportSiruProgettiAttivitaPK();
				id.setCodLocaleProgetto(json.getCodice_locale_progetto());
				id.setAttivitaId(json.getId_attivita());
				
				ImportSiruProgettiAttivita prec = arSiruDAO.findSiruProgettiAttivita(id);
				
				if(prec==null){
					ImportSiruProgettiAttivita jpa = new ImportSiruProgettiAttivita();
					jpa.setId(id);
					jpa.setCf(json.getCodice_fiscale());
					jpa.setCodCorso(json.getCodice_corso());
					jpa.setDenominazioneSog(json.getDenominazione_soggetto());
					jpa.setIntDescrizione(json.getDescrizione_intervento_specifico());
					jpa.setIntId(json.getId_intervento_specifico());
					jpa.setPartecipantiPrevisti(json.getPartecipanti_previsti());
					jpa.setPiva(json.getPartita_iva());
					jpa.setProgettoId(json.getId_progetto());
					jpa.setTitoloCorso(json.getTitolo_corso());
					
					String key = json.getDenominazione_soggetto();
					String belfiore = null;
					if(mappaCodiciCapofila.containsKey(key)){
					  belfiore = mappaCodiciCapofila.get(key);
					}else{
						belfiore = arSiruDAO.getBelfioreCapofilaByDenomSiru(json.getDenominazione_soggetto());
						mappaCodiciCapofila.put(key, belfiore);
					}
					jpa.setBelfiore(belfiore);
					jpa.setDtIns(new Date());
					lst.add(jpa);
				}
			}
			
			if(!lst.isEmpty())
				numInsert = arSiruDAO.save(lst);
		}
		return numInsert;
	}
	
	@Override
	public void aggiornaTabelleFinali(){
	
		//Se va a buon fine il caricamento della tabella di staging, aggiorno i progetti.
		arSiruDAO.callArProgettiRefresh();
	}

}
