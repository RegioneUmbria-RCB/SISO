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
			logger.error(t.getMessage(), t);
			throw new CarSocialeServiceException(t);
		}
		return nuovoCsDExportSinba;
	}

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
