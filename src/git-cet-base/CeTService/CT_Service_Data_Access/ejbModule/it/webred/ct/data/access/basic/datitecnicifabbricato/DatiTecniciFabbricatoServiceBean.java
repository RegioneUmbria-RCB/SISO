package it.webred.ct.data.access.basic.datitecnicifabbricato;

import java.util.ArrayList;
import java.util.List;

import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.datitecnicifabbricato.dao.DatiTecniciFabbricatoDAO;
import it.webred.ct.data.access.basic.datitecnicifabbricato.dto.RicercaDatiTecniciDTO;
import it.webred.ct.data.model.datitecnicifabbricato.CertificazioneEnergetica;
import it.webred.ct.data.model.datitecnicifabbricato.CollaudoStatico;
import it.webred.ct.data.model.datitecnicifabbricato.DichiarazioneConformita;
import it.webred.ct.data.model.datitecnicifabbricato.DocumentiTecniciFabbricato;


import javax.ejb.Stateless;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class DatiTecniciFabbricatoServiceBean implements DatiTecniciFabbricatoService {
	@Autowired
	private DatiTecniciFabbricatoDAO datiTecniciFabbricatoDAO;

	@Override
	public List<CertificazioneEnergetica> getListaCertificazioniEnergeticheByFP(RicercaOggettoCatDTO ro) throws DatiTecniciFabbricatoException {
		List<CertificazioneEnergetica> lista =new ArrayList<CertificazioneEnergetica>();
		List<CertificazioneEnergetica> listaTemp = datiTecniciFabbricatoDAO.getListaCertificazioniEnergeticheByFP(ro);
		if (listaTemp != null && listaTemp.size() > 0) {
			if (ro.getDtVal()!=null) {
				for(CertificazioneEnergetica cert: listaTemp) {
					if (cert.getDataProt() !=null) {
						if (!cert.getDataProt().after(ro.getDtVal()) )
							lista.add(cert);
					}else
						lista.add(cert);						
				}	
			}else
				lista=listaTemp;
		}
		return lista;
	}
	
	@Override
	public CertificazioneEnergetica getCertificazioneEnergeticaById(Long uid) throws DatiTecniciFabbricatoException {
		CertificazioneEnergetica ce = datiTecniciFabbricatoDAO.getCertificazioneEnergeticaById(uid);
		return ce;
	}//-------------------------------------------------------------------------

	@Override
	public List<CollaudoStatico> getListaCollaudoStaticoByFP(RicercaOggettoCatDTO ro) throws DatiTecniciFabbricatoException {
		List<CollaudoStatico> lista = new ArrayList<CollaudoStatico>();
		List<CollaudoStatico> listaTemp = datiTecniciFabbricatoDAO.getListaCollaudoStaticoByFP(ro);
		if (listaTemp != null && listaTemp.size() > 0) {
			if (ro.getDtVal()!=null) {
				for(CollaudoStatico coll: listaTemp) {
					if (coll.getDtProtDocColl() !=null) {
						if (!coll.getDtProtDocColl().after(ro.getDtVal()) )
							lista.add(coll);
					}else
						lista.add(coll);						
				}	
			}else
				lista=listaTemp;
		}
		return lista;

	}

	@Override
	public List<DichiarazioneConformita> getListaDichiarazioneConformitaByFP(RicercaOggettoCatDTO ro) throws DatiTecniciFabbricatoException {
		List<DichiarazioneConformita> lista = new ArrayList<DichiarazioneConformita>();
		List<DichiarazioneConformita> listaTemp = datiTecniciFabbricatoDAO.getListaDichiarazioneConformitaByFP(ro);
		if (listaTemp != null && listaTemp.size() > 0) {
			if (ro.getDtVal()!=null) {
				for(DichiarazioneConformita dic: listaTemp) {
					if (dic.getDtProt() !=null) {
						if (!dic.getDtProt().after(ro.getDtVal()) )
							lista.add(dic);
					}else
						lista.add(dic);						
				}	
			}else
				lista=listaTemp;
		}
		return lista;

	}

	@Override
	public List<DocumentiTecniciFabbricato> getListaDocumentiByIdDati(RicercaDatiTecniciDTO rd) throws DatiTecniciFabbricatoException {
		List<DocumentiTecniciFabbricato> lista = datiTecniciFabbricatoDAO.getListaDocumentiByIdDati(rd); 
		return lista;
	}
	
	
}
