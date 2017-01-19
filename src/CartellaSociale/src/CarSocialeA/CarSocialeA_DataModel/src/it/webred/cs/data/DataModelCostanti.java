package it.webred.cs.data;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

public class DataModelCostanti {

	public static final String NOME_APPLICAZIONE = "CarSociale";
	public static final Date END_DATE = new GregorianCalendar(9999, 11, 31).getTime();
	public static final String END_DATE_STRING = "31/12/9999";
	public static final int INTESTATARIO_SCHEDA_REL_ID = 999;
	
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
	}
	public static class TipoStatoErogazione
	{
		public static final String PRELIMINARE = "P";
		public static final String EROGATIVO = "E";
		
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
		public static final String AMMINISTRAZIONE_ORG_SETT_OP = "Amministra Organizzazioni/Settori/Operatori";
		public static final String GESTIONE_CONFIG = "Gestisci Configurazione";
	}
	
	public static class PermessiErogazioneInterventi {
		public static final String AUTORIZZA="Autorizza Erogazione Interventi";
		public static final String EROGA="Eroga Servizi e Interventi";
		public static final String ABILITA="Abilita Funzioni di Erogazione Interventi";
		public static final String RICHIEDI_INTERVENTI = "Richiedi Interventi";
	}
	
	public static class PermessiCasellario{
		public static final String ESPORTA_CASELLARIO = "Esporta Casellario";
	}
	
	public static class PermessiCaso {
		public static final String MODIFICA_CASI_SETTORE = "Modifica tutti i casi del mio Settore";
		public static final String VISUALIZZAZIONE_DATI_RISERV = "Accedi ai Dati Riservati";
		public static final String VISUALIZZAZIONE_LISTA_CASI = "Visualizza Elenco Casi";
		public static final String CREAZIONE_CASO = "Crea un caso";
		public static final String VISUALIZZAZIONE_CASI_SETTORE = "Visualizza Tutti i Casi del mio Settore";
		public static final String VISUALIZZAZIONE_CASI_ORG = "Visualizza Tutti i Casi della mia Organizzazione";
		public static final String VISUALIZZAZIONE_CARICO_LAVORO = "Visualizza Carico di Lavoro";
	}
	
	public static class PermessiFascicolo {
		public static final String VISUALIZZAZIONE_FASCICOLO = "Visualizza Fascicolo";
		public static final String GESTIONE_ELEM_FASCICOLO = "Inserisci / Modifica Elementi del Fascicolo";
		public static final String UPLOAD_DOC_INDIVIDUALI = "Upload Documenti Individuali";
	}
	
	public static class PermessiNotifiche {
		public static final String NOTIFICHE_ITEM = "Notifiche Cartella";
		
		public static final String VISUALIZZA_NOTIFICHE_ORGANIZZAZIONE = "VISUALIZZA-NOTIFICHE-ORGANIZZAZIONE";
		public static final String VISUALIZZA_NOTIFICHE_SETTORE = "VISUALIZZA-NOTIFICHE-SETTORE";
		public static final String VISUALIZZA_NOTIFICHE_TIPO = "VISUALIZZA-NOTIFICHE-TIPO-";
	}
	
	public static class PermessiIterDialog {
		public static final String ITER_DIALOG_PERMESSO_NOTIFICA_SETTORE_SEGNALANTE = "ITER_DIALOG_PERMESSO_NOTIFICA_SETTORE_SEGNALANTE";
	}
	
	public static class PermessiSchedeSegr {
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
	
	public static class TipiAlert implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String FAMIGLIA_GIT = "FAMGIT";
		public static final String FAMIGLIA_GIT_DESC = "Famiglia Anagrafe";
		public static final String ITER_STEP = "IT";
		public static final String ITER_STEP_DESC = "Iter Step";
		public static final String DOC_IND = "DOCIND";
		public static final String DOC_IND_DESC = "Doc Individuale";
		public static final String RELAZIONE = "REL";
		public static final String RELAZIONE_DESC = "Relazione";
	
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
		public static final int RICHIESTA_SERVIZIO_ID = 22; // FIXME
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
			public static final String CARATTERE_PRESTAZIONE_DI_TIPO_OCCASIONALE= "2";
			public static final String CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO = "1";
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
		
		
}
