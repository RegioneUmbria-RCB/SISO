package it.webred.cs.csa.ejb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.ArCsPaiInfoSinteticheDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.ArCsPaiPTIDocumentoDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoConsuntivazioneDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoMinoreStrutturaEnum;
import it.webred.cs.csa.ejb.dto.pai.pti.RichiestaDisponibilitaPaiPtiDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.StrutturaDisponibilitaDTO;
import it.webred.cs.data.model.VStrutturaArea;
import it.webred.cs.data.model.pti.CsPaiPTI;
import it.webred.cs.data.model.pti.CsPaiPTIRevisioni;
import it.webred.cs.data.model.pti.CsPaiPtiDocumento;
import it.webred.cs.data.model.pti.InserimentoConsuntivazione;
import it.webred.cs.data.model.pti.InserimentoMinoreDaStruttura;
import it.webred.cs.data.model.pti.RichiestaDisponibilitaPaiPti;

@Named
public class PaiPTIDAO extends CarSocialeBaseDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6500693281257803341L;

	public CsPaiPTI findPTIByDiarioPaiId(Long diarioPaiId) throws Exception {
		try {
			Query query = em.createQuery("SELECT d FROM CsPaiPTI d WHERE d.diarioPaiId = :diarioPaiId");
			query.setParameter("diarioPaiId", diarioPaiId);
			CsPaiPTI csPaiPTI = (CsPaiPTI) query.getSingleResult();
			csPaiPTI.getDocumenti();
			csPaiPTI.getRichiesteDisponibilita();
			return csPaiPTI;
		} catch (Exception e) {
			logger.error("Errore salvataggio PTI "+ e.getMessage(), e);
			return null;
		}
	}

	public CsPaiPTI savePTI(CsPaiPTI entity) throws Exception {
		try {
			CsPaiPTI toReturn = em.merge(entity);
			// TODO Auto-generated method stub
			em.flush();
			return toReturn;

		} catch (Exception e) {

			logger.error("Errore salvataggio PTI", e);
			return null;
		}

	}

	// Query per l'estrazione delle strutture quando si entra nel nuovo PTI
	@SuppressWarnings("unchecked")
	public List<VStrutturaArea> findElencoStrutture(Long tipoStruttura, Integer eta) throws Exception {
		List<VStrutturaArea> lstOut = new ArrayList<VStrutturaArea>();
		try {
			String sql = "select s.* from V_STRUTTURA_AREA s, AR_RP_TIPOLOGIA_SERVIZIO t "+
						 "where ID_TIPOLOGIA_SERVIZIO = t.id "+
					     "AND tipo_Struttura = :tipoStruttura and :eta between eta_min and eta_max "+
					     "order by nome_struttura";
			logger.debug("findElencoStrutture SQL["+sql+"]");
			logger.debug("findElencoStrutture PARAMS tipoStruttura["+tipoStruttura+"] età["+eta+"]");
			Query query = em.createNativeQuery(sql, VStrutturaArea.class);
			query.setParameter("tipoStruttura", tipoStruttura);
			query.setParameter("eta", eta);

			lstOut = (List<VStrutturaArea>) query.getResultList();
			logger.debug("findElencoStrutture RES["+lstOut.size()+"]");
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return lstOut;
	}

	@SuppressWarnings("unchecked")
	public List<StrutturaDisponibilitaDTO> findElencoStruttureDisponibilita(Long idProgettoIndividuale)
			throws Exception {
		try {
			List<StrutturaDisponibilitaDTO> elencoStruttDisponibilita = new ArrayList<StrutturaDisponibilitaDTO>();

			String qq = "SELECT s.*, NVL(disp.ID,0) as ID_DISP, disp.DATA_RICHESTA, disp.DATA_ULT_MOD, disp.STATO_RICH,"
					+ " disp.MOTIVO_RIFIUTO, disp.DOC_PTI, disp.DATA_INIZIO_PERM, disp.DATA_FINE_PERM, disp.DETTAGLIO_PTI,"
					+ " disp.DETTAGLIO_MINORE, disp.NOME_DOC, disp.DATA_ACC_STRUTT "
					+ " FROM V_Struttura_Area  s left join AR_CS_PAI_PTI_RICH_DISP disp  "
					+ " on s.ID_STRUTTURA =disp.ID_STRUTTURA and disp.ID_PROGETTO_INDIVIDUALE = :idProgettoIndividuale "
					+ " WHERE s.TIPO_STRUTTURA IN (1,2) ";
			logger.debug("findElencoStruttureDisponibilita SQL["+qq+"]");
			logger.debug("findElencoStruttureDisponibilita PARAMS idProgettoIndividuale["+idProgettoIndividuale+"]");
			Query query = em.createNativeQuery(qq);
			query.setParameter("idProgettoIndividuale", idProgettoIndividuale);

			List<Object[]> ret = query.getResultList();
			logger.debug("findElencoStruttureDisponibilita RES["+ret.size()+"]");
			if (ret != null && !ret.isEmpty()) {
			for (Object[] o : ret) {
					StrutturaDisponibilitaDTO struttTemp = new StrutturaDisponibilitaDTO();
					long idStruttura = ((BigDecimal) o[0]).longValue();
					struttTemp.setIdStruttura(idStruttura); // idstruttura
					struttTemp.setNomeStruttura((String) o[1]); // NOME_STRUTTURA
					struttTemp.setIndirizzo((String) o[2]);// INDIRIZZO
					String flgAttrezzato = o[3] != null ? "" : (String) o[3];
					struttTemp.setFlagAttrezzato(flgAttrezzato);// FLAG_ATTREZZATO
					long tipoStruttura = ((BigDecimal) o[4]).longValue();
					struttTemp.setTipoStruttura(tipoStruttura);// TIPO_STRUTTURA
					String abilitato = o[5] != null ? "" : (String) o[5];
					struttTemp.setAbilitato(abilitato);// ABILITATO
					struttTemp.setTipologiaServizio((String) o[6]); // TIPOLOGIA_SERVIZIO
					struttTemp.setGestoreServizio((String) o[7]);// GESTORE_SERVIZIO
					struttTemp.setTelefonoFissoGestore((String) o[8]);// TELEFONO_FISSO_GESTORE
					struttTemp.setMailGestore((String) o[9]);// MAIL_GESTORE
					struttTemp.setPecGestore((String) o[10]);// PEC_GESTORE
					struttTemp.setCoordinatore((String) o[11]);// COORDINATORE
					struttTemp.setTelefonoFissoCoordinatore((String) o[12]);// TELEFONO_FISSO_COORDINATORE
					struttTemp.setMailCoordinatore((String) o[13]);// MAIL_COORDINATORE
					struttTemp.setCodComune((String) o[14]);// CODICE_COMUNE
					struttTemp.setDescrizioneComune((String) o[15]);// DESCRIZIONE_COMUNE

					long idDisponibilita = ((BigDecimal) o[21]).longValue();

					if (idDisponibilita != 0) {

						RichiestaDisponibilitaPaiPtiDTO richiestaDispoDTO = new RichiestaDisponibilitaPaiPtiDTO();
						richiestaDispoDTO.setId(idDisponibilita);
						richiestaDispoDTO.setDataRichiesta((Date) o[22]);
						richiestaDispoDTO.setDataUltimaModifica((Date) o[23]);
						richiestaDispoDTO.setStatoRichiesta((String) o[24]);
						String motivoRifiuto = o[24] != null ? (String) o[25] : "";
						richiestaDispoDTO.setMotivoRifiuto(motivoRifiuto);
						if (o[26] != null) {
							Blob blob = (Blob) o[26];
							int blobLength = (int) blob.length();
							byte[] blobAsBytes = blob.getBytes(1, blobLength);
							richiestaDispoDTO.setDocumento(blobAsBytes);
						}

						richiestaDispoDTO.setDataInizioPermamenza((Date) o[27]);
						richiestaDispoDTO.setDataFinePermanenza((Date) o[28]);
						richiestaDispoDTO.setDettaglioPTI((String) o[29]);
						richiestaDispoDTO.setDettaglioMinore((String) o[30]);
						richiestaDispoDTO.setNomeDocumento((String) o[31]);
						richiestaDispoDTO.setIdStruttura(idStruttura);
						richiestaDispoDTO.setDataAccStruttura((Date) o[32]);
						richiestaDispoDTO.setIdProgettoIndividuale(idProgettoIndividuale);
						struttTemp.setRichiesteDisponibilita(richiestaDispoDTO);
					}

					elencoStruttDisponibilita.add(struttTemp);
				}

			}
			return elencoStruttDisponibilita;
		} catch (Exception e) {
			logger.error("Errore salvataggio PTI", e);
			throw e;
		}
	}

	public RichiestaDisponibilitaPaiPti findDisponibilitaById(Long idRichiestaDisponibilita) throws Exception {
		try {
			Query query = em.createQuery(
					"SELECT s FROM RichiestaDisponibilitaPaiPti s where s.id  = :idRichiestaDisponibilita ");
			query.setParameter("idRichiestaDisponibilita", idRichiestaDisponibilita);

			return (RichiestaDisponibilitaPaiPti) query.getSingleResult();

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * @param codRouting
	 * @return
	 */
	public List<InserimentoMinoreDaStruttura> findInsMinoriDaStruttura() {
		try {
			Query query = em.createNamedQuery("InserimentoMinoreDaStruttura.findInsMinoriInZsDaStruttura");
			query.setParameter("statoI", InserimentoMinoreStrutturaEnum.INS.toString());
			return (List<InserimentoMinoreDaStruttura>) query.getResultList();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public InserimentoMinoreDaStruttura save(InserimentoMinoreDaStruttura entity) throws Exception {
		try {
			InserimentoMinoreDaStruttura toReturn = em.merge(entity);
			em.flush();
			return toReturn;

		} catch (Exception e) {

			logger.error("Errore salvataggio ", e);
			return null;
		}

	}

	public CsPaiPtiDocumento inserisciDocumento(CsPaiPtiDocumento entity) {
		try {

			em.persist(entity);
			em.flush();
			return entity;
		} catch (Exception e) {

			logger.error("Errore salvataggio ", e);
			return null;
		}
	}

	public CsPaiPtiDocumento saveDocumento(CsPaiPtiDocumento entity) {
		try {
			CsPaiPtiDocumento toReturn = em.merge(entity);
			em.flush();
			return toReturn;
		} catch (Exception e) {

			logger.error("Errore salvataggio ", e);
			return null;
		}
	}

	public List<InserimentoConsuntivazione> findConsuntivazioneDaStruttura(Long idPaiPti, String codRouting) {
		try {
			Query query = em.createQuery(
					"SELECT c FROM InserimentoConsuntivazione c where c.idPaiPTI = :idPaiPTI and c.codRouting = :codRouting and c.inviatoEnte=1 ");
			query.setParameter("idPaiPTI", idPaiPti);
			query.setParameter("codRouting", codRouting);
			return (List<InserimentoConsuntivazione>) query.getResultList();

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public List<InserimentoConsuntivazione> findConsuntivazioneErogate(Long idPaiPti, String codRouting) {
		try {
			Query query = em.createQuery(
					"SELECT c FROM InserimentoConsuntivazione c where c.idPaiPTI = :idPaiPTI and c.codRouting = :codRouting and c.flagErogato=1 order by c.dataA asc");
			query.setParameter("idPaiPTI", idPaiPti);
			query.setParameter("codRouting", codRouting);
			return (List<InserimentoConsuntivazione>) query.getResultList();

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public InserimentoConsuntivazione saveConsuntivazione(InserimentoConsuntivazioneDTO consuntivazioneDTO) {
		try {
			InserimentoConsuntivazione toReturn = new InserimentoConsuntivazione();
			BeanUtils.copyProperties(consuntivazioneDTO, toReturn);
			toReturn = em.merge(toReturn);
			em.flush();

			return toReturn;
		} catch (Exception e) {
			logger.error("Errore saveConsuntivazione " + e.getMessage(), e);
		}
		return null;
	}

	public CsPaiPTIRevisioni savePTIRevisione(CsPaiPTIRevisioni entity) throws Exception {
		try {
			CsPaiPTIRevisioni toReturn = em.merge(entity);
			// TODO Auto-generated method stub
			em.flush();
			return toReturn;

		} catch (Exception e) {

			logger.error("Errore salvataggio Revisione PTI", e);
			return null;
		}

	}

	// Query per l'estrazione delle strutture quando si entra nel nuovo PTI
	@SuppressWarnings("unchecked")
	public List<CsPaiPTIRevisioni> findRevisioni(Long idPaiPTI) throws Exception {
		try {
			Query query = em.createQuery("SELECT s FROM CsPaiPTIRevisioni s where s.idPaiPTI  = :idPaiPTI ");
			query.setParameter("idPaiPTI", idPaiPTI);

			return (List<CsPaiPTIRevisioni>) query.getResultList();

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public List<ArCsPaiPTIDocumentoDTO> findDocumentiRichiestaSelezionata(BaseDTO dto) {
		List<ArCsPaiPTIDocumentoDTO> resDTO = new ArrayList<ArCsPaiPTIDocumentoDTO>();
		Query query = em.createQuery(
				"SELECT d FROM CsPaiPtiDocumento d where d.idPaiPTI = :idPaiPTI and d.codRouting = :codRouting order by d.validoDa desc ");
		query.setParameter("idPaiPTI", (Long) dto.getObj());
		query.setParameter("codRouting", String.valueOf(dto.getObj2()));
		ArCsPaiPTIDocumentoDTO tmpDTO;
		ArCsPaiInfoSinteticheDTO tmpInfoDTO;
		List<CsPaiPtiDocumento> resultList = query.getResultList();
		for (CsPaiPtiDocumento docEntity : resultList) {
			tmpDTO = new ArCsPaiPTIDocumentoDTO();
			BeanUtils.copyProperties(docEntity, tmpDTO);

			if (docEntity.getArCsPaiInfoSintetiche() != null) {
				// QUI DEVO TROVARE SOURCE E tARGET
				tmpInfoDTO = new ArCsPaiInfoSinteticheDTO();
				BeanUtils.copyProperties(docEntity.getArCsPaiInfoSintetiche(), tmpInfoDTO);
				tmpDTO.setArCsPaiInfoSinteticheDTO(tmpInfoDTO);
			}

			resDTO.add(tmpDTO);
		}

		return resDTO;
	}

	public List<RichiestaDisponibilitaPaiPtiDTO> findProgettiAltriEnti(String codRouting) throws Exception {
		try {
			List<RichiestaDisponibilitaPaiPti> lstRichieste = new ArrayList<RichiestaDisponibilitaPaiPti>();
			Query query = em.createQuery(
					"SELECT d FROM RichiestaDisponibilitaPaiPti d  WHERE d.codRouting <> :codRouting  AND d.idStruttura IN (SELECT S.idStruttura FROM VStrutturaArea S WHERE S.codBelfioreComune = :codRouting)");
			query.setParameter("codRouting", codRouting);
			lstRichieste = (List<RichiestaDisponibilitaPaiPti>) query.getResultList();

			List<RichiestaDisponibilitaPaiPti> result = query.getResultList();
			RichiestaDisponibilitaPaiPtiDTO tmpDTO;
			List<RichiestaDisponibilitaPaiPtiDTO> resDTO = new ArrayList<RichiestaDisponibilitaPaiPtiDTO>();
			for (RichiestaDisponibilitaPaiPti richiestaEntity : lstRichieste) {
				tmpDTO = new RichiestaDisponibilitaPaiPtiDTO();
				String[] ignore = { "DettaglioMinore" };

				BeanUtils.copyProperties(richiestaEntity, tmpDTO, ignore);

				resDTO.add(tmpDTO);
			}
			return resDTO;

		} catch (Exception e) {
			logger.error("Errore ricerca richieste da altri entis"+ e.getMessage(), e);
			return null;
		}
	}

	// Query per l'estrazione della struttura per id
	@SuppressWarnings("unchecked")
	public VStrutturaArea findStrutturaById(Long idStruttura) throws Exception {
		try {
			Query query = em.createQuery("SELECT s FROM VStrutturaArea s where s.id  = :idStruttura ");
			query.setParameter("idStruttura", idStruttura);
			return (VStrutturaArea) query.getSingleResult();

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public List<CsPaiPtiDocumento> findDocumentiDaProcessare(String enteId) {
		try {
			Query query = em.createQuery(
					"SELECT d FROM CsPaiPtiDocumento d where d.flgNotifica = 0 AND d.codRouting = :enteId");
			query.setParameter("enteId", enteId);
			return (List<CsPaiPtiDocumento>) query.getResultList();

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsPaiPTI findPTIById(Long idPTI) throws Exception {
		try {
			CsPaiPTI cs = em.find(CsPaiPTI.class, idPTI);
			return cs;

		} catch (Exception e) {
			logger.error("Errore salvataggio PTI"+ e.getMessage(), e);
			return null;
		}
	}

	public List<InserimentoConsuntivazione> findConsuntiviDaProcessare(String enteId) {
		try {
			Query query = em.createQuery(
					"SELECT d FROM InserimentoConsuntivazione d where d.inviatoEnte = 1 AND d.flagNotifica = 0 AND d.codRouting = :enteId");
			query.setParameter("enteId", enteId);
			return (List<InserimentoConsuntivazione>) query.getResultList();

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
