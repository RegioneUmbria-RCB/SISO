package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InfoExportSinbaDTO;
import it.webred.cs.csa.ejb.dto.SinbaMinoriSearchResultDTO;
import it.webred.cs.csa.ejb.dto.SinbaSearchCriteria;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDClob;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDExportSinba;
import it.webred.cs.data.model.CsDSinba;
import it.webred.cs.data.model.CsIPsExportMast;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

@Named
public class ExportValutazioneSinbaDAO extends CarSocialeBaseDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SinbaDAO sinbaDao;
	
	@Autowired
	private SoggettoDAO soggettoDao;
	
	/**
	 * Ritorna la list dei codici di prestazione del soggetto TODO: 
	 * @param cf
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findCodiciPrestazione(String cf, String dtIniRiferimento, String dtFineRiferimento, String tipo) {
		Query q = em.createNamedQuery("CsIPs.findCodiciPrestazione");
		q.setParameter("cf", cf);
		q.setParameter("dtIniRiferimento", dtIniRiferimento);
		q.setParameter("dtFineRiferimento", dtFineRiferimento);
		q.setParameter("tipo", tipo);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CsIPsExportMast> findFlussiInviatiInPeriodo(long idOperSettore,String dataInizio, String dataFine) {
		Query q = em.createNamedQuery("CsIPsExportMast.findPsExportMastInPeriodo");
		q.setParameter("dataDA", dataInizio);
		q.setParameter("dataA", dataFine);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SinbaMinoriSearchResultDTO> findSchedeSinbaPerMinore(
			BaseDTO bDto) {
		List<Object[]> result = new ArrayList<Object[]>();

		List<SinbaMinoriSearchResultDTO> listMinoriDto = new ArrayList<SinbaMinoriSearchResultDTO>();

		ExportValutazioneSinbaQueryBuilder qb = new ExportValutazioneSinbaQueryBuilder(
				bDto);
		try {
			// RICERCA PER CF O NOME O COGNOME
			// RICERCA PER PERIODO
			String sql = qb.createQueryMinoriPerSinbaDaEportareByCasoId();

			logger.debug("findMinoriNelPeriodo SQL[" + sql + "]");

			Query q = em.createNativeQuery(sql);

			result = q.getResultList();
			listMinoriDto = createListMinoriSinbaDTO(result);

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return listMinoriDto;

	}

	@SuppressWarnings("unchecked")
	public List<SinbaMinoriSearchResultDTO> findMinoriNelPeriodo(SinbaSearchCriteria bDto) throws CarSocialeServiceException {
		List<Object[]> result = new ArrayList<Object[]>();

		List<SinbaMinoriSearchResultDTO> listMinoriDto = new ArrayList<SinbaMinoriSearchResultDTO>();
		List<SinbaMinoriSearchResultDTO> listMinoriResultDto = new ArrayList<SinbaMinoriSearchResultDTO>();

		ExportValutazioneSinbaQueryBuilder qb = new ExportValutazioneSinbaQueryBuilder(bDto);
		try {
			// RICERCA PER CF O NOME O COGNOME
			// RICERCA PER PERIODO
			String sql = qb.createQueryMinoriPerSinbaDaEportare();

			logger.debug("findMinoriNelPeriodo SQL[" + sql + "]");

			Query q = em.createNativeQuery(sql);

//		if ((bDto.getCasoId() == null || bDto.getCasoId() == 0)
//					&& (bDto.getCodiceFiscale() == null || bDto
//							.getCodiceFiscale().isEmpty())
//
//			) {
			//SISO-777 a ricerca deve essere fatta secondo ogni singolo criterio inseribile
			if ( bDto.getDataInizio()!=null && ! bDto.getDataInizio().isEmpty() &&  bDto.getDataFine()!=null && !bDto.getDataFine().isEmpty()){
				q.setParameter("dataInizio", bDto.getDataInizio());
				q.setParameter("dataFine", bDto.getDataFine());}

				// q.setParameter("operatoreId", bDto.getOperatoreId());
//		}

			q.setParameter("belfioreOperatore", bDto.getOrganizzazioneBelfiore());

			result = q.getResultList();
			listMinoriDto = createListMinoriSinbaDTO(result);
			
			//filtro per esportati/non Esportati/tutti
			for (SinbaMinoriSearchResultDTO sinbaMinoriSearchResultDTO : listMinoriDto) {
				if (bDto.getStatoEsportazione().equals("e")) {
					// voglio solo gli esportati

					if (sinbaMinoriSearchResultDTO.isMinoreEsportatoSinba()) {
						listMinoriResultDto.add(sinbaMinoriSearchResultDTO);
					}

				} else if (bDto.getStatoEsportazione().equals("ne")) {

					if (!sinbaMinoriSearchResultDTO.isMinoreEsportatoSinba()) {
						listMinoriResultDto.add(sinbaMinoriSearchResultDTO);
					}
				} else {
					listMinoriResultDto.add(sinbaMinoriSearchResultDTO);
				}
			}

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return listMinoriResultDto;
	}

	private List<SinbaMinoriSearchResultDTO> createListMinoriSinbaDTO(List<Object[]> result) {

		List<SinbaMinoriSearchResultDTO> listMinoriDto = new ArrayList<SinbaMinoriSearchResultDTO>();
		HashMap<BigDecimal, SinbaMinoriSearchResultDTO> mapMinoriDto = new HashMap<BigDecimal, SinbaMinoriSearchResultDTO>();
		for (Object[] o : result) {
			SinbaMinoriSearchResultDTO e = new SinbaMinoriSearchResultDTO();
			CsDSinba sinba = null;
			InfoExportSinbaDTO info = new InfoExportSinbaDTO();
			/*CsDDiario diario = new CsDDiario();
			CsDClob clob = new CsDClob();
			CsDExportSinba fileExport = new CsDExportSinba();*/
			
			BigDecimal idAnagrafica = (BigDecimal) o[0];
			BigDecimal idCaso = (BigDecimal) o[6];
			
			CsASoggettoLAZY soggetto = soggettoDao.getSoggettoById(idAnagrafica.longValue());
			e.setSoggettoBeneficiario(soggetto);
			
			//	e.setIdAnagrafica(idAnagrafica);
			//	e.setCognome(o[1] != null ? ((String) o[1]) : null);
			//	e.setNome(o[2] != null ? ((String) o[2]) : null);
				
			if (o[3] != null) {
				Date dataNascita = (Date)o[3];
				//e.setDt_nascita(dataNascita);
				Calendar c = new GregorianCalendar();
				long etaMilli = c.getTimeInMillis()- dataNascita.getTime();
				int days = (int) (etaMilli / 86400000);
				int anni = Math.round(days / 365);
				e.setEta(anni);
			}
			
			//e.setIdCaso(idCaso.longValue());
			//e.setcFbeneficiario(o[7] != null ? (String) o[7] : null);
			
			

			// popola la lista di CsDSinba attraverso un hashMap
			if (o[8] != null) {
			    sinba = sinbaDao.getSchedaSinbaById(((BigDecimal) o[8]).longValue());
			    info.setCsDSinba(sinba);
/*				diario.setId(((BigDecimal) o[8]).longValue());

				if (o[13] != null) {
					// popolo il CLOB
					clob.setId(((BigDecimal) o[13]).longValue());
					if (o[14] != null)
						try {
							clob.setDatiClob(((Clob) o[14]).getSubString(1,(int) ((Clob) o[14]).length()));
						} catch (SQLException e1) {
							clob.setDatiClob("");
						}
					diario.setCsDClob(clob);
				}

				if (o[12] != null)
					diario.setDtIns((Date)o[12]);

				sinba.setDiarioId(diario.getId());
				sinba.setCsDDiario(diario);

				if (o[9] != null)
					sinba.setDescrizioneScheda((String) o[9]);
				if (o[10] != null)
					sinba.setDataExport(convertTimestampToDate(o[10]));
				if (o[11] != null) {
					fileExport.setId(((BigDecimal) o[11]).longValue());
					if(o[15]!=null){
						fileExport.setIdentificazioneFlusso((String)o[15]);
					}
					sinba.setCsDExportSinba(fileExport);
						
				}
				

			} else {
				// il record non aveva un Sinba quindi annullo CsDSinba
				sinba = null;*/
			}
			
			if (!mapMinoriDto.containsKey(idAnagrafica)) {
				if (sinba != null) {
					if (e.getCsDSinbas() == null) {
						e.setCsDSinbas(new ArrayList<InfoExportSinbaDTO>());
						info.setEsportabile(true); // essendo la prima è la
													// sola ad essere
													// esportabile
						// se la prima sinba risulta anche esportata allora il
						// minore è da considerarsi esportato
						if (sinba.getDataExport() != null
								&& sinba.getCsDExportSinba() != null
								&& sinba.getCsDExportSinba().getId() > 0) {
							e.setMinoreEsportatoSinba(true);
						} else {
							e.setMinoreEsportatoSinba(false);
						}

					} else {
						info.setEsportabile(false);
					}

					e.getCsDSinbas().add(info);
				}
				mapMinoriDto.put(idAnagrafica, e);
			} else {
				if (sinba != null) {
					if (mapMinoriDto.get(idAnagrafica).getCsDSinbas() == null) {
						mapMinoriDto.get(idAnagrafica).setCsDSinbas(new ArrayList<InfoExportSinbaDTO>());
						info.setEsportabile(true); // essendo la prima è la
													// sola ad essere
													// esportabile
						// se la prima sinba risulta anche esportata allora il
						// minore è da considerarsi esportato
						if (sinba.getDataExport() != null
								&& sinba.getCsDExportSinba() != null
								&& sinba.getCsDExportSinba().getId() > 0) {
						//	e.setMinoreEsportatoSinba(true);
							mapMinoriDto.get(idAnagrafica).setMinoreEsportatoSinba(true);
						} else {
						//	e.setMinoreEsportatoSinba(false);
							mapMinoriDto.get(idAnagrafica).setMinoreEsportatoSinba(false);
						}

					} else {
						info.setEsportabile(false);
					}

					mapMinoriDto.get(idAnagrafica).getCsDSinbas().add(info);
				
				}
			
			}

		}

		listMinoriDto = new ArrayList<SinbaMinoriSearchResultDTO>(mapMinoriDto.values());
		return listMinoriDto;
	}

	public long getProgressivoCsDExportSinba(String enteErogatore,
			String flusso, Date dtInsStart, Date dtInsEnd) {

		Query q = em
				.createNamedQuery("CsDExportSinba.countProgressivoCsDExportSinba");

		q.setParameter("enteErogatore", enteErogatore);
		q.setParameter("flusso", flusso);

		q.setParameter("dtInsStart", dtInsStart);
		q.setParameter("dtInsEnd", dtInsEnd);

		int count = ((Number) q.getSingleResult()).intValue();

		return count;
	}

	public CsDExportSinba saveCsDExportSinba(CsDExportSinba nuovoCsDExportSinba) {
		try {
			em.persist(nuovoCsDExportSinba);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new CarSocialeServiceException(t);
		}
		return nuovoCsDExportSinba;
	}

	// // TASK777 come esempio per esportazione?!
	// public static List<EsportazioneDTOView> createListEsportazioni(
	// List<Object[]> queryResult) { // SISO-538 cambiato il tipo dato in
	// // EsportazioneDTOView
	// List<Object[]> result = queryResult;
	// List<EsportazioneDTOView> listEsportazioni = new
	// ArrayList<EsportazioneDTOView>(); // SISO-538
	// // cambiato
	// // il
	// // tipo
	// // dato
	// // in
	// // EsportazioneDTOView
	//
	// for (Object[] o : result) {
	// EsportazioneDTOView e = new EsportazioneDTOView(); // SISO-538
	// e.setInterventoEsegId(((BigDecimal) o[0]).longValue());
	// if (o[1] != null)
	// e.setBenefAnnoNascita(((BigDecimal) o[1]).intValue());
	// e.setBenefLuogoNascita((String) o[2]);
	// if (o[3] != null)
	// e.setBenefSesso(((BigDecimal) o[3]).intValue());
	// if (o[4] != null)
	// e.setBenefCittadinanza(((BigDecimal) o[4]).intValue());
	//
	// // if(o[5]!=null)
	// // e.setBenefSecCittadinanza(((BigDecimal)o[5]).intValue());
	// e.setBenefRegione((String) o[5]);
	// e.setBenefComune((String) o[6]);
	// e.setBenefNazione((String) o[7]);
	// e.setNumProtDSU((String) o[8]);
	// if (o[9] != null)
	// e.setAnnoProtDSU(((BigDecimal) o[9]).intValue());
	// e.setDataDSU(convertTimestampToDate(o[10]));
	// e.setCodPrestazione((String) o[11]);
	// e.setDenomPrestazione((String) o[12]);
	// e.setPrestazioneProtocEnte((String) o[13]);
	// // e.setSogliaISEE((BigDecimal)o[15]);
	//
	// BigDecimal m_spesa = (BigDecimal) o[28];
	// BigDecimal m_compart_altre = (BigDecimal) o[29];
	// BigDecimal m_compart_ssn = (BigDecimal) o[30];
	// BigDecimal m_compart_utenti = (BigDecimal) o[31];
	// BigDecimal m_perc_gestita_ente = (BigDecimal) o[32];
	// BigDecimal m_valore_gestita_ente = (BigDecimal) o[33];
	// String m_note_altre_compart = (String) o[34];
	// String m_soggetto_cf = (String) o[36];
	// String m_soggetto_cognome = (String) o[37];
	// String m_soggetto_nome = (String) o[38];
	//
	// // inizio SISO-538
	// e.setSpesaDettaglio((BigDecimal) o[17]);
	// e.setSpesaTestata(m_spesa);
	// // fine SISO-538
	//
	// // if(m_compart_altre!=null)
	// // e.setCompartAltre(m_compart_altre);
	// // else
	// // e.setCompartAltre((BigDecimal)o[18]);
	// e.setCompartAltre((BigDecimal) o[18]);
	//
	// // if(m_compart_ssn!=null)
	// // e.setCompartSsn(m_compart_ssn);
	// // else
	// // e.setCompartSsn((BigDecimal)o[19]);
	// e.setCompartSsn((BigDecimal) o[19]);
	//
	// // if(m_compart_utenti!=null)
	// // e.setCompartUtenti(m_compart_utenti);
	// // else
	// // e.setCompartUtenti((BigDecimal)o[20]);
	// e.setCompartUtenti((BigDecimal) o[20]);
	//
	// if ((BigDecimal) o[21] != null)
	// e.setPercGestitaEnte((BigDecimal) o[21]);
	// else
	// e.setPercGestitaEnte(m_perc_gestita_ente);
	//
	// if ((BigDecimal) o[22] != null)
	// e.setValoreGestitaEnte((BigDecimal) o[22]);
	// else
	// e.setValoreGestitaEnte(m_valore_gestita_ente);
	//
	// if ((String) o[27] != null)
	// e.setNoteAltreCompart((String) o[27]);
	// else
	// e.setNoteAltreCompart(m_note_altre_compart);
	//
	// if ((String) o[14] != null)
	// e.setSoggettoCodiceFiscale((String) o[14]);
	// else
	// e.setSoggettoCodiceFiscale(m_soggetto_cf);
	//
	// if ((String) o[15] != null)
	// e.setSoggettoCognome((String) o[15]);
	// else
	// e.setSoggettoCognome(m_soggetto_cognome);
	//
	// if ((String) o[16] != null)
	// e.setSoggettoNome((String) o[16]);
	// else
	// e.setSoggettoNome(m_soggetto_nome);
	//
	// e.setDataEsecuzione(convertTimestampToDate(o[23]));
	// e.setEnteOperatoreErogante((String) o[24]);
	// e.setNomeOperatoreErog((String) o[25]);
	// e.setNote((String) o[26]);
	// e.setCarattere((String) o[35]);
	// e.setInterventoEsegMastId(((BigDecimal) o[39]).longValue());
	// e.setInterventoId((BigDecimal) o[40]);
	// e.setDataEsecuzioneA(convertTimestampToDate(o[41])); // SISO-538
	//
	// // INIZIO SISO-657 - Nuovo tracciato casellario PSA - PS - SINA
	// e.setPresenzaProvaMezzi((BigDecimal) o[42]);
	// e.setPresaInCarico((BigDecimal) o[43]);
	// e.setCategoriaSocialeId((BigDecimal) o[44]);
	// // FINE SISO-657 - Nuovo tracciato casellario PSA - PS - SINA
	//
	// /* SISO-719 */
	// e.setCategoriaSocialeDescrizione((String) o[45]);
	// if (o[46] != null) {
	// e.setIdFlusso((String) o[46]);
	// }
	// if (o[47] != null) {
	// e.setDataEsportazione(convertTimestampToDate(o[47]));
	// }
	// if (o[48] != null) {
	// e.setRevocabile(((BigDecimal) o[48]).intValue() == 1 ? true
	// : false);
	// }
	//
	// listEsportazioni.add(e);
	// }
	//
	// return listEsportazioni;
	// }

	// inizio SISO-538
	private static Date convertTimestampToDate(Object object) {
		if (object != null) {
			Calendar c = new GregorianCalendar();
			c.setTimeInMillis(((Timestamp) object).getTime());
			return c.getTime();
		} else {
			return null;
		}

	}

}
