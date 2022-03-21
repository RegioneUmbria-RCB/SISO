package it.webred.ct.data.access.basic.fonti.dao;

import it.webred.ct.data.access.basic.fonti.FontiDataIn;
import it.webred.ct.data.access.basic.fonti.FontiServiceException;
import it.webred.ct.data.access.basic.fonti.dto.FontiDTO;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class FontiJPAImpl extends FontiBaseDAO implements FontiDAO {

	@Override
	public FontiDTO getDateRifFonteTracciaDate(FontiDataIn in) {
		
		FontiDTO dto = new FontiDTO();
		
		String sql = "select rtd.datamin as DATAMIN, rtd.datamax as DATAMAX from R_TRACCIA_DATE rtd " +
					 "where rtd.belfiore = '"+in.getEnteId()+"'	and rtd.id_fonte = "+in.getIdFonte();
		try{
			List<Object[]> result = executeNativeQuery(sql);
			if (result != null && result.size()>0) {
				Object[] o = result.get(0);
				Date diIni = (Date) o[0];
				Date diAgg = (Date) o[1];
				dto.setDataRifInizio(diIni);
				dto.setDataRifAggiornamento(diAgg);
			}
			
		}catch(Exception e){
			logger.error("Errore esecuzione query SQL["+sql+"]", e);
		}
		
		return dto;
		
	}
	
	
	@Override
	public FontiDTO getDateRifFonteProps(FontiDataIn in) {

		FontiDTO dto = new FontiDTO();
		if (fontiProps == null)
			initProps();
	
		String data = fontiProps.getProperty("sql.data.riferimento." + in.getIdFonte());
		
		try{
		/*String dataAgg = fontiProps
				.getProperty("sql.data.riferimento.aggiornamento."
						+ in.getIdFonte());*/
		if (data != null) {
			List<Object[]> result = executeNativeQuery(data);
			if (result != null && result.size()>0) {
				Object[] o = result.get(0);
				Date diIni = (Date) o[0];
				Date diAgg = (Date) o[1];
				dto.setDataRifInizio(diIni);
				dto.setDataRifAggiornamento(diAgg);
			}
		}
		/*if (dataAgg != null) {
			List<Object[]> result = executeNativeQuery(dataAgg);
			if (result != null && result.get(0) != null) {
				Object o = result.get(0);
				Date di = (Date) o;
				dto.setDataRifAggiornamento(di);
			}
		}*/
		}catch(Exception e){
			logger.error("Errore esecuzione query sql.data.riferimento."+ in.getIdFonte(), e);
		}
		
		return dto;
	}

	private List<Object[]> executeNativeQuery(String query) {

		try {

			logger.debug("ESECUZIONE QUERY: " + query);
			Query q = manager_diogene.createNativeQuery(query);
			return q.getResultList();

		} catch (NoResultException nre) {
			logger.error("Nessun risultato.");
		} catch (Throwable t) {
			throw new FontiServiceException(t);
		}

		return null;

	}

	private void initProps() {

		try {
			fontiProps = new Properties();
			fontiProps.load(this.getClass().getResourceAsStream("/fonti.properties"));
		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
		}
	}
}
