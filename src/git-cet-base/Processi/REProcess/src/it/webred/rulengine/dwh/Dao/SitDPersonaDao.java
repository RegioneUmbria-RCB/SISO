package it.webred.rulengine.dwh.Dao;

import java.sql.ResultSet;

import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.table.SitDPersona;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitDPersonaDao extends TabellaDwhDao
{

	public SitDPersonaDao(SitDPersona tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}
	public SitDPersonaDao(SitDPersona tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
	/*
	 * 
	 */
	@Override
	protected void gestioneDatoStessoIdExt(ResultSet rs) {
		//Aggiungo alla persona eventuali dati storici non ripetuti nell'ultimo tracciato
		
		SitDPersona persona = (SitDPersona) this.getTabella();
		boolean change = false;
		
		try{
		
			if(persona.getPosizAna() == null && rs.getString("POSIZ_ANA") != null){
				persona.setPosizAna(rs.getString("POSIZ_ANA"));
				change = true;
			}
			
			//aggiorno lo stato civile anche se l'oggetto persona deriva dal coniuge (quindi comune nascita nullo)
			if((persona.getStatoCivile() == null && rs.getString("STATO_CIVILE") != null)
					|| (persona.getIdExtComuneNascita().getRelazione().getValore() == null && rs.getString("STATO_CIVILE") != null)){
				persona.setStatoCivile(rs.getString("STATO_CIVILE"));
				change = true;
			}
			
			if(persona.getDataInizioResidenza().getValore() == null && rs.getString("DATA_INIZIO_RESIDENZA") != null){
				persona.setDataInizioResidenza(new DataDwh(rs.getDate("DATA_INIZIO_RESIDENZA")));
				change = true;
			}
			
			if(persona.getIdExtStato().getRelazione().getValore() == null && rs.getString("ID_EXT_STATO") != null){
				persona.setIdExtStato(new ChiaveEsterna(rs.getString("ID_EXT_STATO")));
				change = true;
			}
			
			if(persona.getIdExtComuneImm().getRelazione().getValore() == null && rs.getString("ID_EXT_COMUNE_IMM") != null){
				persona.setIdExtComuneImm(new ChiaveEsterna(rs.getString("ID_EXT_COMUNE_IMM")));
				change = true;
			}
			
			//se c'è la provincia voglio mantenere anche il comune
			if(persona.getIdExtProvinciaImm().getRelazione().getValore() == null && rs.getString("ID_EXT_PROVINCIA_IMM") != null){
				persona.setIdExtProvinciaImm(new ChiaveEsterna(rs.getString("ID_EXT_PROVINCIA_IMM")));
				persona.setIdExtComuneImm(new ChiaveEsterna(rs.getString("ID_EXT_COMUNE_IMM")));
				change = true;
			}
			
			if(persona.getDataImm().getValore() == null && rs.getString("DATA_IMM") != null){
				persona.setDataImm(new DataDwh(rs.getDate("DATA_IMM")));
				change = true;
			}
			
			if(persona.getIdExtComuneEmi().getRelazione().getValore() == null && rs.getString("ID_EXT_COMUNE_EMI") != null){
				persona.setIdExtComuneEmi(new ChiaveEsterna(rs.getString("ID_EXT_COMUNE_EMI")));
				change = true;
			}
			
			if(persona.getIdExtProvinciaEmi().getRelazione().getValore() == null && rs.getString("ID_EXT_PROVINCIA_EMI") != null){
				persona.setIdExtProvinciaEmi(new ChiaveEsterna(rs.getString("ID_EXT_PROVINCIA_EMI")));
				persona.setIdExtComuneEmi(new ChiaveEsterna(rs.getString("ID_EXT_COMUNE_EMI")));
				change = true;
			}
			
			if(persona.getDataEmi().getValore() == null && rs.getString("DATA_EMI") != null){
				persona.setDataEmi(new DataDwh(rs.getDate("DATA_EMI")));
				change = true;
			}
			
			if(persona.getIdExtProvinciaMor().getRelazione().getValore() == null && rs.getString("ID_EXT_PROVINCIA_MOR") != null){
				persona.setIdExtProvinciaMor(new ChiaveEsterna(rs.getString("ID_EXT_PROVINCIA_MOR")));
				persona.setIdExtComuneMor(new ChiaveEsterna(rs.getString("ID_EXT_COMUNE_MOR")));
				change = true;
			}
			
			if(persona.getDataMor().getValore() == null && rs.getString("DATA_MOR") != null){
				persona.setDataMor(new DataDwh(rs.getDate("DATA_MOR")));
				change = true;
			}
			
			if(persona.getIdExtProvinciaNascita().getRelazione().getValore() == null && rs.getString("ID_EXT_PROVINCIA_NASCITA") != null){
				persona.setIdExtProvinciaNascita(new ChiaveEsterna(rs.getString("ID_EXT_PROVINCIA_NASCITA")));
				change = true;
			}
			
			if(persona.getIdExtComuneNascita().getRelazione().getValore() == null && rs.getString("ID_EXT_COMUNE_NASCITA") != null){
				persona.setIdExtComuneNascita(new ChiaveEsterna(rs.getString("ID_EXT_COMUNE_NASCITA")));
				change = true;
			}
			
			if(persona.getDataNascita().getValore() == null && rs.getString("DATA_NASCITA") != null){
				persona.setDataNascita(new DataDwh(rs.getDate("DATA_NASCITA")));
				change = true;
			}
			
			if(persona.getIdExtDPersonaMadre().getRelazione().getValore() == null && rs.getString("ID_EXT_D_PERSONA_MADRE") != null){
				persona.setIdExtDPersonaMadre(new ChiaveEsterna(rs.getString("ID_EXT_D_PERSONA_MADRE")));
				change = true;
			}
			
			if(persona.getIdExtDPersonaPadre().getRelazione().getValore() == null && rs.getString("ID_EXT_D_PERSONA_PADRE") != null){
				persona.setIdExtDPersonaPadre(new ChiaveEsterna(rs.getString("ID_EXT_D_PERSONA_PADRE")));
				change = true;
			}
			
			if(persona.getFlagCodiceFiscale() == null && rs.getString("FLAG_CODICE_FISCALE") != null){
				persona.setFlagCodiceFiscale(rs.getString("FLAG_CODICE_FISCALE"));
				change = true;
			}
			
			if(persona.getIndirizzoEmi() == null && rs.getString("INDIRIZZO_EMI") != null){
				persona.setIndirizzoEmi(rs.getString("INDIRIZZO_EMI"));
				change = true;
			}
			
			try{
				if(persona.getMotivoCancellazioneApr() == null && rs.getString("MOTIVO_CANCELLAZIONE_APR") != null){
					persona.setMotivoCancellazioneApr(rs.getString("MOTIVO_CANCELLAZIONE_APR"));
					change = true;
				}
				
				if(persona.getDataCancellazioneApr().getValore() == null && rs.getString("DATA_CANCELLAZIONE_APR") != null){
					persona.setDataCancellazioneApr(new DataDwh(rs.getDate("DATA_CANCELLAZIONE_APR")));
					change = true;
				}
				
				if(persona.getMotivoIscrizioneApr() == null && rs.getString("MOTIVO_ISCRIZIONE_APR") != null){
					persona.setMotivoIscrizioneApr(rs.getString("MOTIVO_ISCRIZIONE_APR"));
					change = true;
				}
				
				if(persona.getDataIscrizioneApr().getValore() == null && rs.getString("DATA_ISCRIZIONE_APR") != null){
					persona.setDataIscrizioneApr(new DataDwh(rs.getDate("DATA_ISCRIZIONE_APR")));
					change = true;
				}
			} catch (Exception e){
				//questi campi esistono solo nel saia
			}
		
			if(change){
				persona.setCtrHash();
			}
			
		} catch (Exception e){
			log.warn("Errore in GestioneDatoStessoId per tabella: " + tabella,e);
		}
	}
}
