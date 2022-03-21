package it.webred.rulengine.brick.elab.cartellaSociale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

class ArgoDbClient {

	private static final Logger log = Logger.getLogger(ArgoDbClient.class);

	private final Connection connection;

	//@formatter:off
	private static final String SQL_SELECT = 
			"SELECT " + 
			"	ZONA_SOC, " + 
			"	INDIRIZZO_HASH, " + 
			"	INDIRIZZO_ESTESO, " +
			"	INDIRIZZO, "+
			"	CIVICO_NUMERO, "+
			"	COMUNE_DES, "+
			"	PROV_SIGLA "+
			"FROM " + 
			"	AR_V_INDIRIZZI_ZONE " + 
			"WHERE " + 
			"	(GM_STATUS IS NULL OR GM_STATUS = '" + GeolocalizationResult.UNDEFINED.name() + "')" +	
			"	AND ROWNUM < 2500 " ;
	
	private static final String SQL_UPDATE_SUCCESS =
			"UPDATE " + 
			"	AR_V_INDIRIZZI_ZONE " + 
			"SET " + 
			"	GM_LAT = ?, " + 
			"	GM_LON = ?, " + 
			"	GM_FORMATTED_ADDRESS = ?, " + 
			"  	GM_STATUS = ?, " + 
			"  	GM_STATUS_DETAIL = ? " + 
			"WHERE " + 
			"	ZONA_SOC = ? " + 
			"	AND INDIRIZZO_HASH = ? ";
	
	private static final String SQL_UPDATE_ERROR =
			"UPDATE " + 
			"	AR_V_INDIRIZZI_ZONE " + 
			"SET " + 
			"  	GM_STATUS = ?, " + 
			"  	GM_STATUS_DETAIL = ? " + 
			"WHERE " + 
			"	ZONA_SOC = ? " + 
			"	AND INDIRIZZO_HASH = ? ";
	//@formatter:on
		
	private static final String SQL_UPDATE_SDO_GEOMETRY = 
			"UPDATE AR_V_INDIRIZZI_ZONE "+
			"SET SHAPE = " + 
			"	SDO_GEOMETRY(" + 
			"		2001," + 
			"		4326," + 
			"		SDO_POINT_TYPE(GM_LON, GM_LAT, NULL),NULL,NULL)" + 
			"WHERE GM_LAT IS NOT NULL" + 
			"	AND SHAPE IS NULL";
	
	private static final String SQL_UPDATE_ZONA_CENSUARIA =
			"UPDATE AR_V_INDIRIZZI_ZONE ZU " + 
			"SET ZU.ZONA_CENSUARIA = " +
			"	(SELECT B.ZU_DESC " + 
			"		FROM AR_V_INDIRIZZI_ZONE A, GIS_ZONE_URBANE B" + 
			"		WHERE SDO_INSIDE(A.SHAPE, B.SHAPE) = 'TRUE'" + 
			"			AND A.SHAPE IS NOT NULL" + 
			"			AND ZONA_CENSUARIA IS NULL" + 
			"			AND ZU.INDIRIZZO_HASH = A.INDIRIZZO_HASH)" +  
			"WHERE ZU.SHAPE IS NOT NULL" + 
			"	AND ZU.ZONA_CENSUARIA IS NULL";

	public ArgoDbClient(Connection connection) {
		this.connection = connection;
	}

	public List<IndirizziZoneDto> findUnmappedAddresses() throws DataAccessException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			try {
				log.info(" * chiamo findUnmappedAddresses() ");
				pst = connection.prepareStatement(SQL_SELECT);
				log.info("* Query SQL --> "+SQL_SELECT);
				rs = pst.executeQuery();
				return mapResultSet(rs);
			}
			catch (SQLException e) {
				log.error("db access error", e);
				throw new DataAccessException("", e);
			}
		}
		finally {
			//@formatter:off
			try { if (rs != null) { rs.close(); } if (pst != null) { pst.close(); } }
			catch (SQLException e) {/* fregagnente */}
			}			
			//@formatter:on
	}

	private List<IndirizziZoneDto> mapResultSet(ResultSet rs) throws SQLException {
		List<IndirizziZoneDto> results = new ArrayList<IndirizziZoneDto>();
		IndirizziZoneDto dto;
		while (rs.next()) {
			dto = new IndirizziZoneDto();
			dto.setZonaSoc(rs.getString(1));
			dto.setIndirizzoHash(rs.getString(2));
			dto.setIndirizzoEsteso(rs.getString(3));
			dto.setToponimo(rs.getString(4));
			dto.setNumeroCivico(rs.getString(5));
			dto.setComune(rs.getString(6));
			dto.setSiglaProvincia(rs.getString(7));
			//log.info(" * Lista dto: "+ dto.getZonaSoc()+" "+dto.getIndirizzoHash()+ " "+dto.getIndirizzoEsteso());
			results.add(dto);
		}
		log.info(" * results.size: "+ results.size());
		return results;
	}

	public void updateMappedAddresses(List<IndirizziZoneDto> updatedAddresses) throws DataAccessException {
		PreparedStatement pstUpdateOk = null;
		PreparedStatement pstUpdateError = null;
		try {
			pstUpdateOk = connection.prepareStatement(SQL_UPDATE_SUCCESS);
			pstUpdateError = connection.prepareStatement(SQL_UPDATE_ERROR);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Unable to prepare statements for batch update", e);
		}
		log.info(" * sono in updateMappedAddresses() ");
		for (IndirizziZoneDto dto : updatedAddresses) {
			try {
				log.info(" Valori "+ dto.getGmFormattedAddress() + " " + dto.getIndirizzoHash() + " " +dto.getZonaSoc() + " " + dto.getLatitudine() + " "+dto.getGeocodeStatus());
				if (dto.getGeocodeStatus().equals(GeolocalizationResult.OK.name())) {
					log.info(" * If status OK ");
					pstUpdateOk.setDouble(1, dto.getLatitudine());
					pstUpdateOk.setDouble(2, dto.getLongitudine());
					pstUpdateOk.setString(3, dto.getGmFormattedAddress());
					pstUpdateOk.setString(4, dto.getGeocodeStatus());
					pstUpdateOk.setString(5, dto.getGeocodeStatusDetail());
					pstUpdateOk.setString(6, dto.getZonaSoc());
					pstUpdateOk.setString(7, dto.getIndirizzoHash());

					pstUpdateOk.executeUpdate();
				}
				else {
					log.info(" * Else ");
					pstUpdateError.setString(1, dto.getGeocodeStatus());
					pstUpdateError.setString(2, dto.getGeocodeStatusDetail());
					pstUpdateError.setString(3, dto.getZonaSoc());
					pstUpdateError.setString(4, dto.getIndirizzoHash());

					pstUpdateError.executeUpdate();
				} // ~ if/else
			} // ~ try
			catch (SQLException e) {
				log.error("Error while updating geolocation information for " + dto, e);
			} // ~ catch
		} // ~ for
		//@formatter:off
		try { pstUpdateOk.close(); pstUpdateError.close(); }  catch (Exception e) {/*fregagnente*/}
		//@formatter:on
	}
	
	public void updateZonaCensuaria() throws DataAccessException {
		PreparedStatement pstUpdateSdoGeometry = null;
		PreparedStatement pstUpdateZonaCensuaria = null;
		try {
			pstUpdateSdoGeometry = connection.prepareStatement(SQL_UPDATE_SDO_GEOMETRY);
			pstUpdateZonaCensuaria = connection.prepareStatement(SQL_UPDATE_ZONA_CENSUARIA);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Unable to prepare statements for batch update", e);
		}
		log.info(" * updateZonaCensuaria() ");
			try {
				log.info(" ** UpdateZonaSdoGeometry START");
				pstUpdateSdoGeometry.executeUpdate();
				log.info(" ** UpdateZonaSdoGeometry STOP");
				log.info(" ** UpdateZonaSdoGeometry Record Aggiornati : " + pstUpdateSdoGeometry.getUpdateCount());
				log.info(" ** UpdateZonaCensuaria START");
				pstUpdateZonaCensuaria.executeUpdate();
				log.info(" ** UpdateZonaCensuaria STOP");
				log.info(" ** UpdateZonaCensuaria Record Aggiornati : " + pstUpdateZonaCensuaria.getUpdateCount());
			} // ~ try
			catch (SQLException e) {
				log.error("Error while updating geolocation information for ", e);
			} // ~ catch
		//@formatter:off
		try { pstUpdateSdoGeometry.close(); pstUpdateZonaCensuaria.close(); }  catch (Exception e) {/*fregagnente*/}
		//@formatter:on
	}
}
