package it.webred.cs.data;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

public class DataModelCostanti {

	public static final String NOME_APPLICAZIONE = "CarSociale";
	public static final Date END_DATE = new GregorianCalendar(9999, 11, 31).getTime();
	public static final String END_DATE_STRING = "31/12/9999";
	public static final int INTESTATARIO_SCHEDA_REL_ID = 999;
	public static final String CITTADINANZA_ITA = "ITALIANA";
	public static final String SENZA_FISSA_DIMORA= "Senza fissa dimora";
	public static final String ORG_CONFIGURAZIONE = "ORG AMMINISTRATORI CARTELLA";
	
	public static class Segnalibri implements Serializable {

		private static final long serialVersionUID = 1L;
		
		public static final String CASO_ID = ":CASO_ID";
		public static final String CASO_NOME = ":CASO_NOME";
		public static final String CASO_DATA_CREAZIONE = ":CASO_DATA_CREAZIONE";
		public static final String CASO_DATA_MODIFICA = ":CASO_DATA_MODIFICA";
		public static final String CASO_USERNAME_UTENTE_CREAZIONE = ":CASO_USERNAME_UTENTE_CREAZIONE";
		public static final String CASO_USERNAME_UTENTE_MODIFICA = ":CASO_USERNAME_UTENTE_MODIFICA";

		public static final String ITERSTEP_USERNAME_SEGNALANTE = ":ITERSTEP_USERNAME_SEGNALANTE";
		public static final String ITERSTEP_NOME_SEGNALANTE = ":ITERSTEP_NOME_SEGNALANTE";
		public static final String ITERSTEP_COGNOME_SEGNALANTE = ":ITERSTEP_COGNOME_SEGNALANTE";
		public static final String ITERSTEP_STATO_SEZIONE_ATTRIBUTI = ":ITERSTEP_STATO_SEZIONE_ATTRIBUTI";

		public static final String ITERSTEP_USERNAME_SEGNALATO = ":ITERSTEP_USERNAME_SEGNALATO";
		public static final String ITERSTEP_NOME_SEGNALATO = ":ITERSTEP_NOME_SEGNALATO";
		public static final String ITERSTEP_COGNOME_SEGNALATO = ":ITERSTEP_COGNOME_SEGNALATO";

		public static final String ITERSTEP_DATA_CREAZIONE = ":ITERSTEP_DATA_CREAZIONE";
		public static final String SETTORE_TO = ":SETTORE_TO";
	}
	public static class TipoStatoErogazione
	{
		public static final String PRELIMINARE = "P";
		public static final String EROGATIVO = "E";
		
	}
	public static class TipoIndirizzo
	{
		public static final String RESIDENZA = "residenza";
		public static final String DOMICILIO = "domicilio";
		
	}
	public static class ListaErogazioni
	{
		public static final Integer TUTTI = 1;
		public static final Integer CON_RICHIESTA = 2;
		public static final Integer SENZA_RICHIESTA = 3;	
	}
	public static class ListaBeneficiari{
		public static final String INDIVIDUALE="INDIVIDUALE";
		public static final String NUCLEO="NUCLEO";
		public static final String GRUPPO="GRUPPO";
	}
	
	public static class IterStatoInfo
	{
		public static final Long APERTO = 1L;
		public static final Long CHIUSO = 2L;
		public static final Long PRESO_IN_CARICO = 3L;
		public static final Long SEGNALATO = 4L;
		public static final Long SEGNALATO_OP = 11L;
		public static final Long SEGNALATO_ENTE = 10L;
		public static final Long RIAPERTO = 6L;

		public static final Long DEFAULT_ITER_STEP_ID = APERTO;
	}
	
	public static class TipoParamSchedaMultidime
	{
		
		public static final String MOM_VAL_TIPO = "MOM_VAL_TIPO";
		public static final String VAL_SALUTE_PERS = "VAL_SALUTE_PERS";
		public static final String VAL_SALUTE_PSI = "VAL_SALUTE_PSI";
		public static final String VAL_AUTO_PERS = "VAL_AUTO_PERS";
		public static final String VAL_AUTO_DOM = "VAL_AUTO_DOM";
		public static final String VAL_AUTO_EDOM = "VAL_AUTO_EDOM";
		public static final String VAL_RETE_SOC = "VAL_RETE_SOC";
		public static final String VAL_RETE_FAM = "VAL_RETE_FAM";
		public static final String VAL_ECONOMICA = "VAL_ECONOMICA";
		
		public static final String ABIT_ADEGUATEZZA = "ABIT_ADEGUATEZZA";
		public static final String ABIT_UBICAZIONE = "ABIT_UBICAZIONE";
		public static final String ABIT_BAGNO = "ABIT_BAGNO";
		public static final String ABIT_FORNITO= "ABIT_FORNITO";
		public static final String ABIT_ELETTRODOMESTICI= "ABIT_ELETTRODOMESTICI";
		public static final String ABIT_APROBLEMI= "ABIT_APROBLEMI";
		public static final String ABIT_TIPO="ABIT_TIPO";
		public static final String ABIT_TITOLO="ABIT_TITOLO";
		public static final String ABIT_COMPOSIZIONE="ABIT_COMPOSIZIONE";
	
	}

	public static class TipoAttributo
	{
		public enum Enum 
		{
			LIST,
			BOOLEAN,
			STRING,
			DATE,
			INTEGER,
			DOUBLE,
			TIME,
		}
		
		public static Enum ToEnum( String value ) {

			value = StringUtils.trimToEmpty( value );
			
			if( "LIST".compareToIgnoreCase(value) == 0 )
				return Enum.LIST;
			if( "BOOL".compareToIgnoreCase(value) == 0 )
				return Enum.BOOLEAN;
			if( "STRING".compareToIgnoreCase(value) == 0 )
				return Enum.STRING;
			if( "DATE".compareToIgnoreCase(value) == 0 )
				return Enum.DATE;
			if( "INTEGER".compareToIgnoreCase(value) == 0 )
				return Enum.INTEGER;
			if( "DOUBLE".compareToIgnoreCase(value) == 0 )
				return Enum.DOUBLE;
			if( "TIME".compareToIgnoreCase(value) == 0 )
				return Enum.TIME;
			
			throw new Error("TipoAttributo \"" +  value + "\" non gestito" );
		}
	}

	public static class StatoTransizione
	{
		public enum Enum 
		{
			IGNORA,
			NON_OBBLIGATORIO,
			OBBLIGATORIO,
			
		}

		public static Enum ToEnum( String value ) {
			
			value = StringUtils.trimToEmpty( value );
			
			if( "IGN".compareToIgnoreCase(value) == 0 )
				return Enum.IGNORA;
			if( "NOBB".compareToIgnoreCase(value) == 0 )
				return Enum.NON_OBBLIGATORIO;
			if( "OBB".compareToIgnoreCase(value) == 0 )
				return Enum.OBBLIGATORIO;

			throw new Error("StatoTransizione \"" +  value + "\" non gestito" );
		}
	}
	
	public static class PermessiGenerali {
		public static final String ITEM = "CarSociale";
		
		public static final String AMMINISTRAZIONE_ORG_SETT_OP = "Amministra Organizzazioni/Settori/Operatori";
		public static final String GESTIONE_CONFIG = "Gestisci Configurazione";
	}
	
	public static class PermessiErogazioneInterventi {
		public static final String ITEM = "CarSociale";
		
		public static final String AUTORIZZA="Autorizza Erogazione Interventi";
		public static final String EROGA="Eroga Servizi e Interventi";
		public static final String ABILITA="Abilita Funzioni di Erogazione Interventi";
		public static final String RICHIEDI_INTERVENTI = "Richiedi Interventi";
	}
	
	public static class PermessiCasellario{
		public static final String ITEM = "CarSociale";
		
		public static final String ESPORTA_CASELLARIO = "Esporta Casellario";
	}
	
	public static class PermessiInterscambio{
		public static final String ITEM = "CarSociale";
		
		public static final String INTERSCAMBIO_EXPORT = "Interscambio export";
	}
	
	public static class PermessiCaso {
		public static final String ITEM = "CarSociale"; //Application_ITEM
		
		public static final String MODIFICA_CASI_SETTORE = "Modifica tutti i casi del mio Settore";
		public static final String VISUALIZZAZIONE_DATI_RISERV = "Accedi ai Dati Riservati";
		public static final String VISUALIZZAZIONE_LISTA_CASI = "Visualizza Elenco Casi";
		public static final String CREAZIONE_CASO = "Crea un caso";
		public static final String VISUALIZZAZIONE_CASI_SETTORE = "Visualizza Tutti i Casi del mio Settore";
		public static final String VISUALIZZAZIONE_CASI_ORG = "Visualizza Tutti i Casi della mia Organizzazione";
		public static final String VISUALIZZAZIONE_CARICO_LAVORO = "Visualizza Carico di Lavoro";
	}
	
	public static class PermessiFascicolo {
		public static final String ITEM = "Fascicolo cartella sociale"; //APPLICATION_ITEM
		
		//public static final String VISUALIZZAZIONE_FASCICOLO = "Visualizza Fascicolo";
		public static final String GESTIONE_ELEM_FASCICOLO = "Inserisci / Modifica Elementi del Fascicolo";
		
		public static final String TAB_PIC = "01. Accesso Dati presa in carico";
		public static final String TAB_INTERVENTI = "02. Accesso Interventi richiesti";
		public static final String TAB_DIARIO = "03. Accesso Diario";
		public static final String TAB_ATTIVITA_PROFESSIONALI = "04. Accesso Attività professionali";
		public static final String TAB_ISEE = "05. Accesso ISEE";
		public static final String TAB_PAI = "06. Accesso PAI";
		public static final String TAB_SCHEDE_UDC = "07. Accesso Schede UDC";
		public static final String TAB_SCHEDA_MULTIDIMENSIONALE = "08. Accesso Scheda Multidimensionale";
		public static final String TAB_DATI_SCUOLA = "09. Accesso Dati scuola";
		public static final String TAB_PROVVEDIMENTI_MINORI = "10. Accesso Provvedimenti minori";
		public static final String TAB_SCHEDA_SINBA = "11. Accesso Valutazione SINBA";
		public static final String TAB_DOC_INDIVIDUALI_UP = "Upload Documenti Individuali";
		public static final String TAB_DOC_INDIVIDUALI_DOWN = "Download Documenti Individuali";
	}
	
	public static class PermessiNotifiche {
		public static final String ITEM = "Notifiche Cartella"; //APPLICATION_ITEM
		
		public static final String VISUALIZZA_NOTIFICHE_ORGANIZZAZIONE = "VISUALIZZA-NOTIFICHE-ORGANIZZAZIONE";
		public static final String VISUALIZZA_NOTIFICHE_SETTORE = "VISUALIZZA-NOTIFICHE-SETTORE";
		public static final String VISUALIZZA_NOTIFICHE_TIPO = "VISUALIZZA-NOTIFICHE-TIPO-";
	}
	
	public static class PermessiIterDialog {
		public static final String ITER_DIALOG_PERMESSO_NOTIFICA_SETTORE_SEGNALANTE = "ITER_DIALOG_PERMESSO_NOTIFICA_SETTORE_SEGNALANTE";
	}
	
	public static class PermessiSchedeSegr {
		public static final String ITEM = "CarSociale";
		
		public static final String VISUALIZZA_SCHEDE_SEGR = "Visualizza Schede Segretariato";
	}
	
	public static class Operatori implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final Long ASSISTENTI_SOCIALI_ID = new Long(1);
		public static final Long EDUCATORE_UOL_ID = new Long(2);
		public static final Long PSICOLOGA_ID = new Long(3);
		public static final Long EDUCATORE_ID = new Long(4);
		public static final Long EDUCATORE_SPR_ID = new Long(6);
		public static final Long COP_ID = new Long(5);
	
	}
	
	public static class TipiIndirizzi implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final Long RESIDENZA_ID = new Long(1);
	
	}
	
	public static class TipiFamiglia implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String CONVIVENTI = "NUCLEO FAMILIARE CONVIVENTE";
		public static final String AMICI = "GRUPPO AMICALE";
		public static final String ALTRI = "ALTRI FAMILIARI";
	
	}
	
	public enum CategoriaSociale {
		MINORI, MINORI_DISABILI, ADULTI, ADULTI_DISABILI, ANZIANI, ORIENTAMENTO_LAVORO, ALL;
	}
	
	public static class TipiAlertCod implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String FAMIGLIA_GIT = "FAMGIT";
		public static final String ITER_STEP = "IT";
		public static final String DOC_IND = "DOCIND";
		public static final String RELAZIONE = "REL";
		public static final String IMPORT_EXPORT = "IMPEXP";
		public static final String EROGAZIONE="EROGAZIONE";
		public static final String UDC="UDC";
	}
	
	public static class TipiIngMercato implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String CERCA_PRIMA_OCCUPAZIONE = "1";
		public static final String OCCUPATO = "2";
		public static final String DISOCCUPATO = "3";
		public static final String INATTIVO = "5";
	}
	
	public static class TipoDiario implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final int CAMBIO_STATO_ID = 1;
		public static final int EROGAZIONE_INT_ID = 2;
		public static final int FOGLIO_AMM_ID = 3;
		public static final int NOTE_ID = 4;
		public static final int COLLOQUIO_ID = 5;
		public static final int RELAZIONE_ID = 6;
		public static final int VALUTAZIONE_MDS_ID = 7;
		public static final int PAI_ID = 8;
		public static final int BARTHEL_ID = 9;
		public static final int DOC_INDIVIDUALE_ID = 10;
		public static final int DATI_SCUOLA_ID = 11;
		public static final int ISEE_ID = 12;
		public static final int STRANIERI_ID = 13;
		public static final int FAMIGLIA_ID = 14;
		public static final int ABITAZIONE_ID = 15;
		public static final int RECAPITI_ID = 16;
		public static final int INTERMEDIAZIONE_AB_ID=17;
		public static final int ORIENTAMENTO_LAVORO_ID=18;
		public static final int PROVVEDIMENTI_TRIBUNALE = 19;
		public static final int ORIENTAMENTO_ISTRUZIONE_ID = 20;
		public static final int MEDIAZIONE_CULT_ID = 21;
		public static final int RICHIESTA_SERVIZIO_ID = 22;
		public static final int VALUTAZIONE_SINBA = 23;
	}
	
	public static class SchedaSegr implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String STATO_INS = "I";
		public static final String STATO_MOD = "M";
		
		public static final String STATO_CREATA = "CREATA";
		public static final String STATO_RESPINTA = "RESPINTA";
		public static final String STATO_VISTA = "VISTA";
	}
	
	public static class FoglioAmministrativo implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String STATO_VALUTAZIONE = "V"; //aggiunto
		public static final String STATO_ATTIVAZIONE = "A";
		public static final String STATO_SOSPENSIONE = "S";
		public static final String STATO_CHIUSURA = "C";
	}
	
	public static class TabFascicolo implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String ITER = "ITER";
		public static final String FOGLI_AMMINISTRATIVI = "FOGLI AMMINISTRATIVI";
		public static final String COLLOQUIO = "COLLOQUIO";
		public static final String RELAZIONI = "RELAZIONI";
		public static final String PAI = "PAI";
		public static final String DOC_INDIVIDUALI = "DOCUMENTI INDIVIDUALI";
		public static final String SCHEDA_MULTIDIMENSIONALE = "SCHEDA MULTIDIMENSIONALE";
		public static final String SCHEDA_SINBA = "SCHEDA SINBA";
		public static final String DATI_SCUOLA = "SCUOLA";
		public static final String SCHEDE_SEGRETARIATO = "SCHEDE SEGRETARIATO";
		public static final String DATI_ISEE = "DATI ISEE";
		public static final String PROVVEDIMENTI_MINORI = "PROVVEDIMENTI MINORI";
		
	}
	
	public static class ParamReport implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String SEZIONE = "report";
		
		public static final String PIEDIPAGINA = "piedipagina";
	
	}
	
	public static class ErogazioneInterventoConst implements Serializable{
		private static final long serialVersionUID = 1L;
		public static final String TESTATA_SPESA_PERCENTUALE="testaPercentuale";
		public static final String DETTAGLIO_SPESA_PERCENTUALE="dettaPercentuale";
		
		public static final String SCEGLI_OPERATORE="scegli";
		public static final String INSERISCI_OPERATORE="inserisci";
	}
	
	
	//INIZIO MOD-RL
		// 
		public static class CSIPs { 
			
			public static final String PREFIX_PROT_DSU="INPS-ISEE-";
			
			public static final String CARATTERE_PRESTAZIONE_DI_TIPO_OCCASIONALE= "2";
			public static final String CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO = "1";
			
			// SISO-657 - rif id BX202 nuovo schema flusso INPS
			/* javadoc aggiunto per chiarezza */
			/**
			 * prestazione soggetta a ISEE
			 */
			public static final int PRESENZA_PROVA_MEZZI_ISEE_RICHIESTO = 1;
			/**
			 * prestazione soggetta alla prova dei mezzi ma non attraverso ISEE
			 */
			public static final int PRESENZA_PROVA_MEZZI_NON_ISEE = 2;
			/**
			 * prestazione soggetta a ISEE ma prova dei mezzi non richiesta
			 */
			public static final int PRESENZA_PROVA_MEZZI_ISEE_RICHIESTO_NO_PROVA = 3;
			/**
			 * prestazione non soggetta alla prova dei mezzi
			 */
			public static final int PRESENZA_PROVA_MEZZI_NESSUNA = 4;
			

			
			public static final int ID_FLAG_PROVA_MEZZI_VUOTO = -1;
			public static final int ID_FLAG_PROVA_MEZZI_ISEE_E_PROVA_MEZZI = 1;
			public static final int ID_FLAG_PROVA_MEZZI_NON_ISEE_PROVA_MEZZI = 2;
			public static final int ID_FLAG_PROVA_MEZZI_ISEE_NON_PROVA_MEZZI = 3;
			public static final int ID_FLAG_PROVA_MEZZI_NON_ISEE_NON_PROVA_MEZZI = 4;
			

			public static final String DESCRIZIONE_FLAG_PROVA_MEZZI_VUOTO = " - seleziona - ";
			public static final String DESCRIZIONE_FLAG_PROVA_MEZZI_ISEE_E_PROVA_MEZZI = "Prestazione soggetta a ISEE";
			public static final String DESCRIZIONE_FLAG_PROVA_MEZZI_NON_ISEE_PROVA_MEZZI = "Prestazione soggetta a prova dei mezzi";
			public static final String DESCRIZIONE_FLAG_PROVA_MEZZI_ISEE_NON_PROVA_MEZZI = "Prestazione in generale soggetta a ISEE, ma sottratta alla prova dei mezzi";
			public static final String DESCRIZIONE_FLAG_PROVA_MEZZI_NON_ISEE_NON_PROVA_MEZZI = "Prestazione non soggetta a prova dei mezzi"; 

			
			
			//public static final int ID_FLAG_IN_CARICO_VUOTO = -1;
			public static final boolean ID_FLAG_IN_CARICO_SI = Boolean.TRUE;
			public static final boolean ID_FLAG_IN_CARICO_NO = Boolean.FALSE;

			public static final String DESCRIZIONE_FLAG_IN_CARICO_VUOTO = " - ";
			public static final String DESCRIZIONE_FLAG_IN_CARICO_SI = "Sì";
			public static final String DESCRIZIONE_FLAG_IN_CARICO_NO = "No";

			public static final String AREA_UTENZA_FAMIGLIA_E_MINORI = "1";
			public static final String AREA_UTENZA_DISABILI = "2";
			public static final String AREA_UTENZA_GENERICA = "3";
			// ~-- SISO-657
			
		} 
		
		public static class AmTabNazioni { 
			public static final String CODICE_ITALIA= "380"; 
		}

		public static class CsIPsExportMast { 
			public static final String FLUSSO_PSA= "PSA"; 
		}
	//FINE MOD-RL

	//INIZIO SISO-391
	public static class DatiInterventoConst implements Serializable{ 
		public static final String TIPO_DATA_PRESUNTA ="1";
		public static final String TIPO_DATA_CERTA ="2"; 
	}
	//FINE SISO-391
		

	//INIZIO SISO-538  
	
	
	public static class VErogExportHelp implements Serializable{ 
		public static final String TIPO_EXPORT_MASTER ="EXPORT_MASTER";
		public static final String TIPO_EXPORT_RIGHE ="EXPORT_RIGHE"; 
		public static final String TIPO_EXPORT_TO_CLOSE ="TO_CLOSE"; 
		public static final String FLAG_CARATTERE_PRESTAZIONE_PERIODICA ="PERIODICA"; 
		public static final String FLAG_CARATTERE_PRESTAZIONE_OCCASIONALE ="OCCASIONALE"; 
		public static final String FLAG_EROGAZIONE_CHIUSA ="1"; 
		public static final String FLAG_EROGAZIONE_NON_CHIUSA ="0";  
	}
	//FINE SISO-538
	
	
	//INIZIO SISO-556    
	public static class CsIQuotaRipartiz implements Serializable {
		public static final String FLAG_RENDICONTO_PERIODICO ="P";
		public static final String FLAG_RENDICONTO_EVENTO ="E";

		public static final String FLAG_PERIODO_RIPARTIZ_GIORNALIERO ="G";
		public static final String FLAG_PERIODO_RIPARTIZ_SETTIMANALE ="S";
		public static final String FLAG_PERIODO_RIPARTIZ_MENSILE ="M";
		public static final String FLAG_PERIODO_RIPARTIZ_ANNUALE ="A"; 

	} 

	public static class CsCfgIntEsegStato implements Serializable {
		public static final boolean FLAG_CHIUDI_APERTO = false; 
		public static final boolean FLAG_CHIUDI_CHIUSO = true; 
	}
	

	public static class CsTbUnitaMisura implements Serializable {
		public static final int  ID_EURO = 17;  
	}
	//FINE SISO-556
	
	

	//INIZIO SISO-346

	public static class Scheda implements Serializable {
		
		public static class Accessi implements Serializable {
			public static final String MOTIVO_DECRETO = "Decreto";
			public static final String MOTIVO_INVIATO_DA = "Inviato da";
			public static final String MOTIVO_SPONTANEO = "Spontaneo"; 
			public static final String MOTIVO_CONVOCATO = "Convocato"; 
			

			public static class Interlocutori implements Serializable { 
				public static final String ATTIVITA_GIUDIZIARIA = "Autorità giudiziaria";
				public static final String UTENTE = "Utente";
				public static final String PARENTE_O_CONOSCENTE= "Parente o Conoscente";
				public static final String ORGANIZZAZIONE = "Organizzazione";
				public static final String ISTITUZIONALE = "Istituzionale";
				public static final String ALTRO_SOGGETTO = "Altro soggetto"; 
			}
			
		}
		
 
		
	}
	
	//FINE SISO-346
	

	public static class CsTbSottocartellaDoc implements Serializable { 
			public static final long ID_ALTRO = 9; 
			public static final long ID_SOSTEGNO_PSICOLOGICO = 8; 
			public static final long ID_CALENDARI_E_ACCORDI_PRESI = 7; 
			public static final long ID_DOCUMENTI_SANITARI = 6; 
			public static final long ID_CONTRIBUTI_ECONOMICI = 5; 
			public static final long ID_DIARIO = 4; 
			public static final long ID_DOCUMENTI_IN_USCITA_PER_ALTRI_SERVIZI = 3; 
			public static final long ID_DOCUMENTI_IN_ENTRATA_DA_ALTRI_SERVIZI = 2; 
			public static final long ID_TRIBUNALE_DECRETI_RELAZIONI = 1;  
		
	}
	
	public static class TipoDocumentoMobile implements Serializable {
		public static final int DIARIO_TESTO = 110;
		public static final int DIARIO_AUDIO = 120;
		public static final int DIARIO_FOTO = 130;
	}
	
	public static class TipoDatoMobile implements Serializable {
		public static final String MOBILE_DIARIO = "DIARIO";
		public static final String MOBILE_SCANNER = "SCANNER";
		public static final String MOBILE_EROGAZIONE = "EROGAZIONE";
	}
	
	public static class FiltroCasi implements Serializable{
		public static String STATO="filtroCasi_STATO";
		public static String OPERATORE="filtroCasi_OPERATORE";
		public static String TIPO_OPERATORE="filtroCasi_TIPO_OPERATORE";
		public static String STUDIO="filtroCasi_STUDIO";
		public static String LAVORO="filtroCasi_LAVORO";
		public static String TUTELA="filtroCasi_TUTELA";
		public static String TRIBUNALE="filtroCasi_TRIBUNALE";
	}
	
	public static class GestioneTipoFamiglia implements Serializable{
		public static String DETTAGLIO = "dettaglio";
		public static String COMPLETA = "completa";
		public static String SINTETICA = "sintetica";
	}
}
