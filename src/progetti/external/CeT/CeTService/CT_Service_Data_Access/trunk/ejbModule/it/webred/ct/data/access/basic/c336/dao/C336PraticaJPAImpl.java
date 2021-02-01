package it.webred.ct.data.access.basic.c336.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.c336.C336PraticaServiceException;
import it.webred.ct.data.access.basic.c336.dto.C336CommonDTO;
import it.webred.ct.data.access.basic.c336.dto.C336PraticaDTO;
import it.webred.ct.data.access.basic.c336.dto.C336StatOperatoreDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.model.c336.C336Allegato;
import it.webred.ct.data.model.c336.C336DecodIrreg;
import it.webred.ct.data.model.c336.C336DecodTipDoc;
import it.webred.ct.data.model.c336.C336GesPratica;
import it.webred.ct.data.model.c336.C336GridAttribCatA2;
import it.webred.ct.data.model.c336.C336Pratica;
import it.webred.ct.data.model.c336.C336SkCarGenFabbricato;
import it.webred.ct.data.model.c336.C336SkCarGenUiu;
import it.webred.ct.data.model.c336.C336TabValIncrClsA4A3;
import it.webred.ct.data.model.c336.C336TabValIncrClsA5A6;
import it.webred.ct.data.model.catasto.Siticomu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class C336PraticaJPAImpl extends CTServiceBaseDAO implements C336PraticaDAO {
	
	private static final long serialVersionUID = 1L;
	public static final Map<String, String> FILE_IMAGES = new HashMap<String, String>() {
		{
			put("doc", "doc.png");
			put("pdf", "pdf.png");
			put("xls", "xls.png");
			put("jpeg", "jpg.png");
			put("jpg", "jpg.png");
			put("png", "png.png");
			put("bmp", "bmp.png");
			put("gif", "gif.png");
			put("doc", "doc.png");
			put("tiff", "tiff.png");
			put("tif", "tiff.png");
			put("txt", "txt.png");
		
		}
	};
		
	@Override
	public List<C336GesPratica> getListaOperatoriPratica(C336PraticaDTO praDto) throws C336PraticaServiceException {
		List<C336GesPratica> result = new ArrayList<C336GesPratica>();
		
		try {
			logger.debug("getListaOperatoriPratica[praticaID: "+praDto.getPratica().getIdPratica()+"");
			Query q = manager_diogene.createNamedQuery("C336GesPratica.getStoricoGestionePratica");
			q.setParameter("idPratica", praDto.getPratica().getIdPratica());
			result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
			
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}

	public C336GesPratica getGesAttualePratica(C336PraticaDTO praDto) throws C336PraticaServiceException {
		C336GesPratica result = new C336GesPratica();
		
		try {
			logger.debug("getGesAttualePratica[praticaID: "+praDto.getPratica().getIdPratica()+"");
			Query q = manager_diogene.createNamedQuery("C336GesPratica.getGestioneAttualePratica");
			q.setParameter("idPratica", praDto.getPratica().getIdPratica());
			result = (C336GesPratica)q.getSingleResult();
			
		} catch (NoResultException nf) {
			logger.debug("getGesAttualePratica()-NON TROVATO");
	
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}
	@Override
	public C336GridAttribCatA2 getGridAttribCat2(C336PraticaDTO praDto)	throws C336PraticaServiceException {
		C336GridAttribCatA2	result = new C336GridAttribCatA2();
		
		try {
			logger.debug("getGridAttribCat2[praticaID: "+praDto.getPratica().getIdPratica()+"");
			Query q = manager_diogene.createNamedQuery("C336GridAttribA2.getGridAttribA2");
			q.setParameter("idPratica", praDto.getPratica().getIdPratica());
			result = (C336GridAttribCatA2)q.getSingleResult();
			
		} catch (NoResultException nf) {
			logger.debug("NON TROVATO");
	
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}

	@Override
	public List<C336Allegato> getListaAllegatiPratica(C336PraticaDTO praDto) throws C336PraticaServiceException {
		List<C336Allegato> result = new ArrayList<C336Allegato>();
		
		try {
			logger.debug("getListaAllegatiPratica[praticaID: "+praDto.getPratica().getIdPratica()+"");
			Query q = manager_diogene.createNamedQuery("C336Allegato.getAllegatiPratica");
			q.setParameter("idPratica", praDto.getPratica().getIdPratica());
			List<Object[]> listObj = q.getResultList();
			if (listObj!=null) {
				for (Object[] obj : listObj) {
					C336Allegato all = (C336Allegato) obj[0];
					all.setDesTipDoc((String)obj[1]);
					all.setFileImg(FILE_IMAGES.get(all.getExtFile()));
					result.add(all);
				}	
			}
			logger.debug("Result size ["+result.size()+"]");
			
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}

	@Override
	public C336SkCarGenFabbricato getSkGeneraleFabbricato(C336PraticaDTO praDto) throws C336PraticaServiceException {
		C336SkCarGenFabbricato	result = new C336SkCarGenFabbricato();
		try {
			logger.debug("getSkGeneraleFabbricato[praticaID: "+praDto.getPratica().getIdPratica()+"");
			Query q = manager_diogene.createNamedQuery("C336SkFabbricato.getSkFabbricato");
			q.setParameter("idPratica", praDto.getPratica().getIdPratica());
			result = (C336SkCarGenFabbricato)q.getSingleResult();
			
		} catch (NoResultException nf) {
			logger.debug("SCHEDA GEN FABBR NON TROVATO");
			
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}

	@Override
	public C336SkCarGenUiu getSkGeneraleUiu(C336PraticaDTO praDto) throws C336PraticaServiceException {
		C336SkCarGenUiu	result = new C336SkCarGenUiu();
		try {
			logger.debug("getSkGeneraleUiu[praticaID: "+praDto.getPratica().getIdPratica()+"");
			Query q = manager_diogene.createNamedQuery("C336SkUiu.getSkUiu");
			q.setParameter("idPratica", praDto.getPratica().getIdPratica());
			result = (C336SkCarGenUiu)q.getSingleResult();
			
		} catch (NoResultException nf) {
			logger.debug("SCHEDA GEN UIU NON TROVATO");
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}

	@Override
	public C336TabValIncrClsA4A3 getTabValutIncrClasseA4A3(C336PraticaDTO praDto) throws C336PraticaServiceException {
		C336TabValIncrClsA4A3	result = new C336TabValIncrClsA4A3();
		try {
			logger.debug("getTabValutIncrClasseA4A3[praticaID: "+praDto.getPratica().getIdPratica()+"");
			Query q = manager_diogene.createNamedQuery("C336TabIncrA4A3.getTabIncrA4A3");
			q.setParameter("idPratica", praDto.getPratica().getIdPratica());
			result = (C336TabValIncrClsA4A3)q.getSingleResult();
			
		} catch (NoResultException nf) {
			logger.debug("NON TROVATO");
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}

	@Override
	public C336TabValIncrClsA5A6 getTabValutIncrClasseA5A6(C336PraticaDTO praDto) throws C336PraticaServiceException {
		C336TabValIncrClsA5A6	result = new C336TabValIncrClsA5A6();
		try {
			logger.debug("getTabValutIncrClasseA5A6[praticaID: "+praDto.getPratica().getIdPratica()+"");
			Query q = manager_diogene.createNamedQuery("C336TabIncrA5A6.getTabIncrA5A6");
			q.setParameter("idPratica", praDto.getPratica().getIdPratica());
			result = (C336TabValIncrClsA5A6)q.getSingleResult();
			
		} catch (NoResultException nf) {
			logger.debug("NON TROVATO");
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}

	@Override
	public C336Pratica getPraticaApertaByOggetto(RicercaOggettoDTO ro) throws C336PraticaServiceException {
		logger.debug("getPraticaApertaByOggetto - foglio ["+ro.getFoglio() +"];particella: [" + ro.getParticella() + "];sub: [" + ro.getSub() + "]");
		C336Pratica	result = null;
		Query q=null;
		if (ro.getSub() == null || ro.getSub().trim().equals("") )
			q = manager_diogene.createNamedQuery("C336Pratica.getPraticaApertaByFP");
		else
			q = manager_diogene.createNamedQuery("C336Pratica.getPraticaApertaByFPS");
		try {
			q.setParameter("foglio", ro.getFoglio());
			q.setParameter("particella", ro.getParticella());
			if (ro.getSub() != null && !ro.getSub().trim().equals("") )
				q.setParameter("sub", ro.getSub()  );
			result = (C336Pratica)q.getSingleResult();
		} catch (NoResultException nf) {
			logger.debug("getPraticaApertaByOggetto: NON TROVATO");
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		logger.debug("getPraticaApertaByOggetto -- return: " + result);
		return result;
	}

	@Override
	public C336Pratica getPraticaChiusaByOggetto(RicercaOggettoDTO ro) throws C336PraticaServiceException {
		logger.debug("getPraticaChiusaByOggetto - foglio ["+ro.getFoglio() +"];particella: [" + ro.getParticella() + "];sub: [" + ro.getSub() + "]");
		C336Pratica	result = null;
		Query q=null;
		if (ro.getSub() == null || ro.getSub().trim().equals("") )
			q = manager_diogene.createNamedQuery("C336Pratica.getPraticaChiusaByFP");
		else
			q = manager_diogene.createNamedQuery("C336Pratica.getPraticaChiusaByFPS");
		try {
			q.setParameter("foglio", ro.getFoglio());
			q.setParameter("particella", ro.getParticella());
			if (ro.getSub() != null && !ro.getSub().trim().equals("") )
				q.setParameter("sub", ro.getSub()  );
			result = (C336Pratica)q.getSingleResult();
		} catch (NoResultException nf) {
			logger.debug("getPraticaChiusaByOggetto: NON TROVATO");
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}
	@Override
	public String getDesIrregolarita(String codIrregolarita)	throws C336PraticaServiceException {
		logger.debug("getDesIrregolarita - cod: "+codIrregolarita);
		String 	result = "";
		Query q=  manager_diogene.createNamedQuery("C336DecodIrreg.getDesIrreg");
		try {
			q.setParameter("codIrreg", codIrregolarita);
			C336DecodIrreg decodIrreg = (C336DecodIrreg)q.getSingleResult();
			result = decodIrreg.getDesIrreg().toString(); 
			logger.debug("getDesIrregolarita - des: " + result);
		} catch (NoResultException nf) {
			logger.debug("NON TROVATO");
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}

	@Override
	public List<C336DecodIrreg> getListaIrregolarita()		throws C336PraticaServiceException {
		List<C336DecodIrreg> result = new ArrayList<C336DecodIrreg>();
		
		try {
			logger.debug("getListaIrregolarita");
			Query q = manager_diogene.createNamedQuery("C336DecodIrreg.getListaIrreg");
			q.setParameter("flAttivo", "S");
			result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
			
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}

	@Override
	public String getDesTipDoc(String codTipDoc ) throws C336PraticaServiceException {
		logger.debug("getDesTipDoc - cod: "+ codTipDoc);
		String 	result = "";
		Query q=  manager_diogene.createNamedQuery("C336DecodTipDoc.getDesTipDoc");
		try {
			q.setParameter("codTipDoc", codTipDoc);
			C336DecodTipDoc decodTipDoc = (C336DecodTipDoc)q.getSingleResult();
			result = decodTipDoc.getDesTipDoc().toString(); 
			logger.debug("getDesIrregolarita - des: " + result);
		} catch (NoResultException nf) {
			logger.debug("NON TROVATO");
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}

	@Override
	public List<C336DecodTipDoc> getListaTipiDoc() throws C336PraticaServiceException {
		List<C336DecodTipDoc> result = new ArrayList<C336DecodTipDoc>();
		
		try {
			logger.debug("getListaTipiDoc");
			Query q = manager_diogene.createNamedQuery("C336DecodTipDoc.getListaTipDoc");
			result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
			
		} catch (Throwable t) {
			logger.error("",t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}

	@Override
	public C336Allegato getAllegato(C336CommonDTO praDto)		throws C336PraticaServiceException {
		logger.debug("C336PraticaJpaImpl-getAllegato" );
		try {
			C336Allegato all= manager_diogene.find(C336Allegato.class, praDto.getIdAllegato());
			return all;
		}
		catch (Throwable t) {
			logger.error("", t);
			throw new C336PraticaServiceException(t);
		}
	}
	
	public List<String> getOperatoriIni(){
		try{
			Query q = manager_diogene.createNamedQuery("C336Pratica.getListaUserNameIni");
			return q.getResultList();
		}catch (Throwable t) {
			logger.error("", t);
			throw new C336PraticaServiceException(t);
		}
	}
	
	public List<String> getOperatoriFin(){
		try{
			Query q = manager_diogene.createNamedQuery("C336Pratica.getListaUserNameFin");
			return q.getResultList();
		}catch (Throwable t) {
			logger.error("", t);
			throw new C336PraticaServiceException(t);
		}
	}
	

	@Override
	public List<String> getOperatori() {
		List<String> lista = new ArrayList<String>();
		logger.debug("C336PraticaJpaImpl-getOperatori()" );
		try{
		Query q = manager_diogene.createNamedQuery("C336GesPratica.getListaOperatori");
		lista= q.getResultList();
		logger.debug("C336PraticaJpaImpl-listaOperatori. N.ele:" + lista.size());
		return lista;
		}catch (Throwable t) {
			logger.error("", t);
			throw new C336PraticaServiceException(t);
		}
	}
	

	@Override
	public C336StatOperatoreDTO getStatistiche(String userId) {
		logger.debug("C336PraticaJpaImpl-getStatistiche(). Operatore: " + userId );
		C336StatOperatoreDTO stat = new C336StatOperatoreDTO();
		stat.setOperatore(userId);
		
		//Ricerca pratiche aperte dall'operatore
		Query qa = manager_diogene.createNamedQuery("C336Pratica.getListaAperteOperatore");
		qa.setParameter("codStato", C336PraticaDTO.COD_STATO_ISTRUTTORIA_CONCLUSA);
		qa.setParameter("operatore", userId);
		int numAperte = qa.getResultList().size();
		logger.debug("C336PraticaJpaImpl-getStatistiche().numAperte:" + numAperte );
		stat.setNumPraAperte(numAperte);
		
		//Ricerca pratiche chiuse dall'operatore
		Query qc = manager_diogene.createNamedQuery("C336Pratica.getListaChiuseOperatore");
		qc.setParameter("codStato", C336PraticaDTO.COD_STATO_ISTRUTTORIA_CONCLUSA);
		qc.setParameter("operatore", userId);
		List<C336Pratica> chiuse = qc.getResultList();
		logger.debug("C336PraticaJpaImpl-getStatistiche().numAChiuse:" + chiuse );
		stat.setNumPraChiuse(chiuse.size());
		
		int numPraChiuseRegolari = 0;      //numero pratiche chiuse senza alcuna irregolarità riscontrata
		int numPraChiuseFabbMaiDich = 0;   //numero pratiche chiuse con riscontro di fabbricato/u.i. mai dichiarato in catasto
		int numPraChiuseClassCat = 0;      //numero pratiche chiuse in cui è stata riscontrata classificazione catastale incongruente
		int numPraChiuseExRurale = 0;      //numero di pratiche chiuse relative a  fabbricati che  hanno perso i requisiti di ruralità
		int numPraChiuseNonEsenti = 0;     //numero di pratiche chiuse relative a u.i. prima esenti da imposte ed ora soggette a tassazione
		int numPraChiuseNonCodificato = 0;
		
		//Calcolo delle statistiche per pratiche chiuse
		for(C336Pratica p: chiuse){
			String codice = p.getCodIrregAccertata();
			if(codice!=null){
				 int cod = Integer.parseInt(codice);
		
				 switch (cod) {
				 	case 0:  
				 		numPraChiuseRegolari++;      break;
		            case 1: 
		            case 9:
		            	numPraChiuseFabbMaiDich++;   break;
		            case 2:  
		            case 3: 
		            case 4:  
		            case 5:  
		            case 6: 
		            case 7:  
		            case 8:  
		            	numPraChiuseClassCat++; break;
		            case 10: 
		            	numPraChiuseExRurale++;       break;
		            case 11: 
		            	numPraChiuseNonEsenti++;      break;
		            default:  numPraChiuseNonCodificato++; break;
		        }
			}else
				numPraChiuseNonCodificato++;
		}
		
		stat.setNumPraChiuseRegolari(numPraChiuseRegolari);
		stat.setNumPraChiuseFabbMaiDich(numPraChiuseFabbMaiDich);
		stat.setNumPraChiuseClassCat(numPraChiuseClassCat);
		stat.setNumPraChiuseExRurale(numPraChiuseExRurale);
		stat.setNumPraChiuseNonEsenti(numPraChiuseNonEsenti);
		stat.setNumPraChiuseNonCodificato(numPraChiuseNonCodificato);
		
		logger.debug("C336PraticaJpaImpl-getStatistiche().fine");
		return stat;
	}

	
}
