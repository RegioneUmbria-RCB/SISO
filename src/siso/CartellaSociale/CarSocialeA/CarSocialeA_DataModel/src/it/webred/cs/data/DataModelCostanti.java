package it.webred.cs.data;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class DataModelCostanti {

	public static final String NOME_APPLICAZIONE = "CarSociale";
	public static final Date END_DATE = new GregorianCalendar(9999, 11, 31).getTime();
	public static final Date START_DATE = new GregorianCalendar(0001, 01, 01).getTime();
	public static final String END_DATE_STRING = "31/12/9999";
	public static final int INTESTATARIO_SCHEDA_REL_ID = 999;
	public static final String CITTADINANZA_ITA = "ITALIANA";
	public static final String STATO_ITA = "ITALIA";
	public static final String SENZA_FISSA_DIMORA= "Senza fissa dimora";
	public static final String ORG_CONFIGURAZIONE = "ORG AMMINISTRATORI CARTELLA";
	public static final String SEGNALATO_CF_ANONIMO = "ANONIMO";
	public static final int MAX_PARAMS_QUERY_IN_CLAUSE = 990;
	public static final String patternFSE = "FSE: ";
	public static final String SEPARATORE_AM_GROUP = ",";
	public static Pattern patternNumTelFisso = Pattern.compile("(0{1}[1-9]{1,3})[\\s]?(\\d{4,})");
	public static Pattern patternNumTelMobile = Pattern.compile("(\\d{1,3})[\\s]?(\\d{4,})");
	public static final String NON_RILEVATO = "Non rilevato";
	
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
	
	public static class TipoPOR
	{
		public static final long PRESA_IN_CARICO = 1l;
		public static final long SCHEDA_ACCESSO = 2l;
		
	}
	
	public static class GrVulnerabile{
		public static final String ALTRO = "07";
		public static final String VITTIMA_VIOLENZA = "13";
		public static final String NON_COMUNICA_VULNERABILITA = "00";
		
		public static final String[] stampaAltro = {"01","02","03","04","14","17","18","19","20"};
		public static final String[] stampaVittimaViolenza = {"13","15","16"};
		
	}
	
	public static class CampiFse
	{
		 public static final String AZIENDA_RAGIONE_SOCIALE="AZIENDA_RAGIONE_SOCIALE";
		 public static final String AZIENDA_PI="AZIENDA_PI";                     
		 public static final String AZIENDA_CF="AZIENDA_CF";            
		 public static final String AZIENDA_VIA="AZIENDA_VIA";                        
		 public static final String AZIENDA_COMUNE="AZIENDA_COMUNE";      
		 public static final String AZIENDA_COD_ATECO="AZIENDA_COD_ATECO";      
		 public static final String AZIENDA_FORMA_GIURIDICA="AZIENDA_FORMA_GIURIDICA";       
		 public static final String AZIENDA_DIMENSIONE="AZIENDA_DIMENSIONE";  
		 
		 public static final String LAVORO_TIPO="LAVORO_TIPO";  
		 public static final String LAVORO_ORARIO="LAVORO_ORARIO";  
		 
		 public static final String ANNO_TITOLO_STUDIO="ANNO_TITOLO_STUDIO";  
		 
		 public static final String INATTIVO_ALTRO_CORSO="INATTIVO_ALTRO_CORSO";  
		 public static final String DURATA_RICERCA_LAVORO="DURATA_RICERCA_LAVORO";  
		 
		 public static final String PAG_IBAN="PAG_IBAN";   
		 public static final String PAG_RES_DOM="PAG_RES_DOM";  
		 
		 public static final String DATA_SOTTOSCRIZIONE = "DATA_SOTTOSCRIZIONE";
		 public static final String SOGGETTO_ATTUATORE  = "SOGGETTO_ATTUATORE";
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
		
		public static final Long RESIDENZA_ID = new Long(1);
		public static final Long DOMICILIO_ID = new Long(3);
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
	public static class TipoProgetto{
		public static final String FSE = "FSE";
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
	
	public static class TipoIsee{
		public static final Long ORDINARIO = 1L;
		public static final Long UNIVERSITARIO = 2L;
		public static final Long SOCIOSAN = 3L;
		public static final Long SOCIOSANRES = 4L;
		public static final Long MINORENNI = 5L;
		public static final Long CORRENTE = 6L;
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
	
	public static class PermessiDatiSinba{
		public static final String ITEM = "CarSociale";
		
		public static final String ESPORTA_DATI_SINBA = "Esporta Valutazione Sinba";
	}
	
	public static class PermessiInterscambio{
		public static final String ITEM = "CarSociale";
		
		public static final String INTERSCAMBIO_EXPORT = "Interscambio export";
	}
	
	public static class PermessiCartella {
		public static final String ITEM = "CarSociale"; //Application_ITEM
		
		public static final String MODIFICA_CASI_SETTORE = "Modifica tutti i casi del mio Settore";
		public static final String VISUALIZZAZIONE_DATI_RISERV = "Accedi ai Dati Riservati";
		public static final String VISUALIZZAZIONE_LISTA_CASI = "Visualizza Elenco Casi";
		public static final String CREAZIONE_CASO = "Crea un caso";
		public static final String VISUALIZZAZIONE_CASI_SETTORE = "Visualizza Tutti i Casi del mio Settore";
		public static final String VISUALIZZAZIONE_CASI_ORG = "Visualizza Tutti i Casi della mia Organizzazione";
		public static final String VISUALIZZAZIONE_CARICO_LAVORO = "Visualizza Carico di Lavoro";
		public static final String VISUALIZZAZIONE_LISTA_RDC = "Visualizza Reddito di Cittadinanza";
		public static final String VISUALIZZAZIONE_LISTA_FSE = "Visualizza elenco FSE";
		public static final String VISUALIZZAZIONE_LISTA_FSE_ZS = "Visualizza elenco FSE della zona sociale";
		
		public static final String ACCESSO_ESTERNO_DATI_CARTELLA = "Accesso esterno ai dati della cartella";
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
		public static final String ACCESSO_ESTERNO_DATI_FASCICOLO = "Accesso esterno ai dati del fascicolo";
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
	
	public static class TipiFamiglia implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String CONVIVENTI = "NUCLEO FAMILIARE CONVIVENTE";
		public static final String AMICI = "GRUPPO AMICALE";
		public static final String ALTRI = "ALTRI FAMILIARI";
	
	}
	
	// SISO-897 - mappatura ID di CS_C_CATEGORIA_SOCIALE
	public static class TipiCategoriaSociale implements Serializable {
		private static final long serialVersionUID = 1L;
		
		// per ora mettiamo solo quelli effettivamente utilizzati dall'applicazione
		public static final Long FAMIGLIA_MINORI_ID = new Long(1);
		public static final Long DISABILI_ID = new Long(2);
		public static final Long ADULTI_ID = new Long(3);
		public static final Long ANZIANI_ID = new Long(4);
	}
	
	public static class TipiAlertCod implements Serializable {
		private static final long serialVersionUID = 1L;
		
		//SISO-1127
		public static final String ANAGRAFICA_CASO_GIT = "ANAGGIT";
		public static final String FAMIGLIA_GIT = "FAMGIT";
		public static final String IMPORT_EXPORT = "IMPEXP";
		public static final String EROGAZIONE="EROGAZIONE";
		public static final String UDC="UDC";
		public static final String MODUDC = "MODUDC";
		
		//Alert associati a TIPI DIARIO
		public static final String ITER_STEP = "IT";
		public static final String DOC_IND = "DOCIND";
		public static final String RELAZIONE = "REL";
		public static final String PROTRIB="PROTRIB";
		public static final String PAI = "PAI";
		public static final String COLLOQUIO = "COLLOQUIO";
		public static final String SCUOLA = "SCUOLA";
		public static final String ISEE = "ISEE";
		public static final String MULTIDIM = "MULTIDIM"; 
		public static final String SINBA = "SINBA";
		public static final String FGL_AMM = "FGLAMM";
	}
	
	public static class TipiIngMercato implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String CERCA_PRIMA_OCCUPAZIONE = "1";
		public static final String OCCUPATO = "2";
		public static final String DISOCCUPATO = "3";
		public static final String STUDENTE = "4";
		public static final String INATTIVO = "5";
	}
	
	public static class TipoDiario implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final int CAMBIO_STATO_ID = 1;
		//public static final int EROGAZIONE_INT_ID = 2;
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
		
		public static final String PROVENIENZA_SS = "SS";
		public static final String PROVENIENZA_SERENA = "SERENA";
		
		public static final String TIPO_PROPOSTA_PRESA_IN_CARICO = "Proposta di presa in carico";
		
		public static final String STATO_INS = "I";
		public static final String STATO_MOD = "M";
		
		public static final String STATO_CREATA = "CREATA";
		public static final String STATO_RESPINTA = "RESPINTA";
		public static final String STATO_VISTA = "VISTA";
	}
	
	public static class FoglioAmministrativo implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static enum STATO{
			VALUTAZIONE("Valutazione", "V"),
			GRADUATORIA("Inserito in graduatoria", "G"),
			ATTIVAZIONE("Attivazione", "A"),
			SOSPENSIONE("Sospensione","S"),
			CHIUSURA("Chiusura","C");
			
			String descrizione;
	        String codice;
			
	        private STATO(String descrizione, String codice) {
				this.descrizione = descrizione;
				this.codice = codice;
			}

			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
			public String getCodice() {return codice;}
			public void setCodice(String codice) {this.codice = codice;}
		}
		
		
	}
	
	public static class EsportazioneSIUSS implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static enum STATO{
			TUTTO("Tutto", "a"),
			ESPORTATE("Esportata", "e"),
			DA_ESPORTARE("Da Esportare", "ne");
			
			String descrizione;
	        String codice;
			
	        private STATO(String descrizione, String codice) {
				this.descrizione = descrizione;
				this.codice = codice;
			}

			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
			public String getCodice() {return codice;}
			public void setCodice(String codice) {this.codice = codice;}
		}
		
		public static enum FREQUENZA{
			REGOLARE("Regolare", "REGOLARE"),
			IRREGOLARE("Irregolare", "IRREGOLARE"),
			UNA_TANTUM("Una tantum", "UNATANTUM");
			
			String descrizione;
	        String codice;
			
	        private FREQUENZA(String descrizione, String codice) {
				this.descrizione = descrizione;
				this.codice = codice;
			}

			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
			public String getCodice() {return codice;}
			public void setCodice(String codice) {this.codice = codice;}
		}
		
		
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
			
			public static final String AREA_UTENZA_FAMIGLIA_E_MINORI = "1";
			public static final String AREA_UTENZA_DISABILI = "2";
			public static final String AREA_UTENZA_GENERICA = "3";
			// ~-- SISO-657
			
			public enum FLAG_IN_CARICO {
				SI("Si", 1),
				NO("No", 0),
				NON_SO("Non so",2);
				
				String descrizione;
		        int codice;
				
		        private FLAG_IN_CARICO(String descrizione, int codice) {
					this.descrizione = descrizione;
					this.codice = codice;
				}

				public String getDescrizione() {return descrizione;}
				public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
				public int getCodice() {return codice;}
				public void setCodice(int codice) {this.codice = codice;}
			}
			
			public enum PROVA_MEZZI {
				VUOTO (-1," - seleziona - ",""),
				ISEE_E_PROVA_MEZZI (1,"Prestazione soggetta a ISEE","Prestazione soggetta a ISEE"),
				NON_ISEE_PROVA_MEZZI (2,"Prestazione soggetta a prova dei mezzi","Prestazione soggetta a prova dei mezzi, ma non attraverso ISEE (es. prestaz. inps, agevolaz. tribut.)"),
				ISEE_NON_PROVA_MEZZI (3,"Prestazione in generale soggetta a ISEE, ma sottratta alla prova dei mezzi","Prestazione in generale soggetta a ISEE, ma sottratta alla prova dei mezzi per lo specifico beneficiario in virtu' di altri criteri di bisogno (es. asilo nido per bambino con disabilità  o in famiglie numerose, ecc.)"),
				NON_ISEE_NON_PROVA_MEZZI (4,"Prestazione non soggetta a prova dei mezzi","Prestazione non soggetta a prova dei mezzi (assenza di criteri economici nella disciplina dell'erogazione)"); 

				
				String descrizione;
		        int codice;
		        String tooltip;
				
		        private PROVA_MEZZI(int codice, String descrizione, String tooltip) {
					this.descrizione = descrizione;
					this.codice = codice;
				}

				public String getDescrizione() {return descrizione;}
				public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
				public int getCodice() {return codice;}
				public void setCodice(int codice) {this.codice = codice;}
				public String getTooltip(){return tooltip;}
				public void setTooltip(String tooltip){this.tooltip=tooltip;}
			}
		} 
		
		public static class AmTabNazioni { 
			public static final String CODICE_ISO3166_ITALIA= "380"; 
			public static final String CODICE_ISO3166_NON_DISPONIBILE= "990"; 
		}

		public static class CsIPsExportMast { 
			public static final String FLUSSO_PSA= "PSA"; 
		}
		
		public static class CsSinbasExportMast {
			public static final String FLUSSO_SINBA = "SINBA";
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
	
	public static class Pai{
		
		public static final int RAGGIUNTI_NO = 1;
		public static final int RAGGIUNTI_PARZIALMENTE = 2;
		public static final int RAGGIUNTI_SI = 3;
		
		public enum PERIODO_TEMPORALE {
			GIORNI("Giorni", "Giorni"),
			SETTIMANE("Settimane", "Settimane"),
			MESI("Mesi", "Mesi"),
			ANNI("Anni", "Anni");
			
			String descrizione;
	        String codice;
			
	        private PERIODO_TEMPORALE (String descrizione, String codice) {
				this.descrizione = descrizione;
				this.codice = codice;
			}

			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
			public String getCodice() {return codice;}
			public void setCodice(String codice) {this.codice = codice;}
		}
		
	}
	
	//INIZIO SISO-556 
	public static class ProgettoErogazioni{
		public enum FLAG_RES_DOM{
			RESIDENZA("Residenza", "RES"),
			DOMICILIO("Domicilio", "DOM");
			
			String descrizione;
			String codice;
			
			 private FLAG_RES_DOM (String descrizione, String codice) {
					this.descrizione = descrizione;
					this.codice = codice;
			 }
		
			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
			public String getCodice() {return codice;}
			public void setCodice(String codice) {this.codice = codice;}
		}
	}
	
	public static class TariffeErogazioni { 
		public enum TIPO_RENDICONTO {
			PERIODICO("Periodico", "P"),
			ADEVENTO("Ad evento", "E");
			
			String descrizione;
	        String codice;
			
	        private TIPO_RENDICONTO (String descrizione, String codice) {
				this.descrizione = descrizione;
				this.codice = codice;
			}
	
			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
			public String getCodice() {return codice;}
			public void setCodice(String codice) {this.codice = codice;}
		}
		
		public enum TIPO_FREQUENZA_SERVIZIO_CADENZA {
			ANNUALE("annuale", "A"),
			SEMESTRALE("semestrale","6M"),
			TRIMESTRALE("trimestrale","3M"),
			BIMESTRALE("bimestrale","2M"),
			MENSILE("mensile", "M"),
			SETTIMANALE("settimanale", "S"),
			GIORNALIERA("giornaliera", "G");
			
			String descrizione;
	        String codice;
			
	        private TIPO_FREQUENZA_SERVIZIO_CADENZA (String descrizione, String codice) {
				this.descrizione = descrizione;
				this.codice = codice;
			}
	
			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
			public String getCodice() {return codice;}
			public void setCodice(String codice) {this.codice = codice;}
		}
		
		public enum TIPO_QUOTA {
	        ASSEGNAZIONE("Assegnazione", "A"), 
	        DIMINUZIONE("Diminuzione", "D");
	        	   
	        String descrizione;
	        String codice;

	        TIPO_QUOTA(String descrizione, String codice) {
	            this.descrizione = descrizione;
	            this.codice = codice;
	        }

			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
			public String getCodice() {return codice;}
			public void setCodice(String codice) {this.codice = codice;}
	    }
			
		public enum TIPO_CADENZA_QUOTA {
			ANNUALE("annuale", "A"),
			MENSILE("mensile", "M"),
			SETTIMANALE("settimanale", "S"),
			GIORNALIERA("giornaliera", "G"),
			OCCASIONALE("occasionale", "O"),
			UNATANTUM("una tantum", "U");
			
			String descrizione;
	        String codice;
			
	        private TIPO_CADENZA_QUOTA(String descrizione, String codice) {
				this.descrizione = descrizione;
				this.codice = codice;
			}

			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
			public String getCodice() {return codice;}
			public void setCodice(String codice) {this.codice = codice;}
		}
		
		public enum TIPO_FREQUENZA_SERVIZIO {
			UNATANTUM("Una tantum", "U"),
			IRREGOLARE("Irregolare", "O"),	//SISO-556
			PERIODICO("Regolare", "P");		//SISO-556
			
			String descrizione;
	        String codice;
			
	        private TIPO_FREQUENZA_SERVIZIO (String descrizione, String codice) {
				this.descrizione = descrizione;
				this.codice = codice;
			}

			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
			public String getCodice() {return codice;}
			public void setCodice(String codice) {this.codice = codice;}
		}
	}

	public static class CsCfgIntEsegStato implements Serializable {
		public static final boolean FLAG_CHIUDI_APERTO = false; 
		public static final boolean FLAG_CHIUDI_CHIUSO = true; 
	}
	

	public static class CsTbUnitaMisura implements Serializable {
		public static final int  ID_EURO = 17;  
		public static final int  ID_ORE = 6; //SISO-806
		public static final int  ID_ORE_MINUTI = 18; //SISO-806
	}
	//FINE SISO-556
	

	//INIZIO SISO-346

	public static class Scheda implements Serializable {
		
		public static class Accessi implements Serializable {
			public static final String MOTIVO_DECRETO = "Decreto";
			public static final String MOTIVO_INVIATO_DA = "Inviato da";
			public static final String MOTIVO_SPONTANEO = "Spontaneo"; 
			public static final String MOTIVO_CONVOCATO = "Convocato"; 
		}

		public static class Interlocutori implements Serializable { 
			public static final String ATTIVITA_GIUDIZIARIA = "Autorità giudiziaria";
			public static final String UTENTE = "Utente";
			public static final String PARENTE_O_CONOSCENTE= "Parente o Conoscente";
			public static final String ORGANIZZAZIONE = "Organizzazione";
			public static final String ISTITUZIONALE = "Istituzionale";
			public static final String ALTRO_SOGGETTO = "Altro soggetto"; 
		}		
		
		public enum ModalitaAccesso {
			PERSONA("Di Persona"),
			EMAIL("e-mail"),
			DOMICILIO("Rilevato a domicilio"),
			SEGN_SCRITTA("Segnalazione scritta"),
			SEGN_TELEFONIVA("Segnalazione telefonica"),
			ALTRA_DISTANZA("Altra modalità a distanza");
			
			String descrizione;
			
	        private ModalitaAccesso(String descrizione) {
				this.descrizione = descrizione;
			}

			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
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
	
	public static class TipoVisibilitaDocumento implements Serializable {
		public static final boolean PRIVATO = true;
		public static final boolean PUBBLICO = false;
	}
	
	public static class TipoDatoMobile implements Serializable {
		public static final String MOBILE_DIARIO = "DIARIO";
		public static final String MOBILE_SCANNER = "SCANNER";
		public static final String MOBILE_EROGAZIONE = "EROGAZIONE";
		public static final String MOBILE_ATTIVITA_PROFESSIONALE = "ATT_PROFESSIONALE";
		
	}
	public static class PermessiIter{
		public static final String ITEM = "CarSociale";
		
		public static final String ITER_AMMINISTRAZIONE = "Amministrazione Iter";
		public static final String GESTIONE_ITER_BATCH = "Gestione casi batch";
	}
	public static class FiltroCasi implements Serializable{
		public static String STATO="filtroCasi_STATO";
		public static String STATO_OPERATORE = "filtroCasi_STATO_OPERATORE";
		public static String OPERATORE="filtroCasi_OPERATORE";
		public static String TIPO_OPERATORE="filtroCasi_TIPO_OPERATORE";
		public static String STUDIO="filtroCasi_STUDIO";
		public static String LAVORO="filtroCasi_LAVORO";
		public static String TUTELA="filtroCasi_TUTELA";
		public static String TRIBUNALE="filtroCasi_TRIBUNALE";
		public static String RESIDENZA_COMUNE = "filtroCasi_RESIDENZA_COMUNE";
		public static String RESIDENZA_NAZIONE = "filtroCasi_RESIDENZA_NAZIONE";
		public static String RESIDENZA_TIPO = "filtroCasi_RESIDENZA_TIPO";
	}
	
	public static class FiltroFse implements Serializable{
		public static final String EXTRACT_FIRST = "filtroFse_EXTRACT_FIRST";
		public static String TIPO="filtroFse_TIPO";
		public static String DATA_DA = "filtroFse_DATA_DA";
		public static String DATA_A="filtroFse_DATA_A";
		public static String RESIDENZA_COMUNE = "filtroFse_RESIDENZA_COMUNE";
	}
	
	public static class GestioneTipoFamiglia implements Serializable{
		public static String DETTAGLIO = "dettaglio";
		public static String COMPLETA = "completa";
		public static String SINTETICA = "sintetica";
	}
	
	public static class StrutturaTribunale implements Serializable{ //ID di CS_TB_TRIBSTRUTTURA
		public static String TM_CIVILE = "TMCivile";
		public static String TM_AMMINISTRATIVO = "TMAmministrativo";
		public static String PENALE_MINORILE = "penaleMinorile";
		public static String TO = "TO";
		public static String NIS = "NIS";
		public static String PROCURA_MINORILE = "procuraMinorile";
		public static String PROCURA_ORDINARIA = "procuraOrdinaria";
		public static String CORTE_APPELLO = "corteAppello";
	}

	//SISO-784
	public static class TipoSinaDomanda implements Serializable {
			public static long MOBILITA = 1;
			public static long ATTIVITA_VITA_QUOTIDIANA = 2;
			public static long DISTURBI_AREA_COGNITIVA = 3;
			public static long DISTURBI_COMPORTAMENTALI = 4;
			public static long NECESSITA_CURE_SANITARIE = 5;
			public static long AREA_REDDITUALE = 6;
			public static long AREA_SUPPORTO = 7;
			public static long FONTE_DERIVAZIONE_VALUTAZIONE = 8;
			public static long STRUMENTO_VALUTAZIONE = 9;
			public static long INVALIDITA_CIVILE = 10;
			public static long FONTE_DERIVAZIONE_INVALIDITA = 11;
		}
	
	//SISO-1278
	public static class TipoInvaliditaTotale implements Serializable {
		public static String INVALIDITA_INFERIORE_75 = "32";
		public static String INVALIDITA_TOTALE_CON_ACCOMPAGNAMENTO = "33";
		public static String INVALIDITA_TOTALE_SENZA_ACCOMPAGNAMENTO = "34";
		
	}	
	public static class TipoRicercaSoggetto implements Serializable{
		public static String ANAG_SANITARIA_PREFIX = "ANASAN"; 
		public static String SIGESS = "SIGESS";
		public static String ANAG_RILEVAZIONE_PRESENZE = "ANA_RP";
		public static String ANAG_SANITARIA_UMBRIA = ANAG_SANITARIA_PREFIX+"_UMBRIA";
		public static String ANAG_SANITARIA_MARCHE = ANAG_SANITARIA_PREFIX+"_MARCHE";
		public static String DEFAULT = "DIOGENE";
		
		public static String CF = "CF";
		public static String DATI_ANAGRAFICI="ANAG";
	}
	
	public static class SigessFlagStatoAnagrafe implements Serializable{
		public static String RESIDENTE = "0";
		public static String NON_RESIDENTE = "3";
		public static String[] DECEDUTO = {"1","2","4","7","8"};
		public static String EMIGRATO = "2";
		public static String IMMIGRATO = "9";
		public static String AIRE = "6";
		
/*		0	vivo residente                                    	Sempre
		1	morto residente                                   	Sempre
		2	nato morto residente                              	Sempre
		3	vivo non residente                                	MAI
		4	morto non residente                               	MAI
		5	nato morto non residente                          	MAI
		6	vivo a.i.r.e          *                            	Sempre
		7	morto a.i.r.e      *                               	Sempre
		8	nato morto a.i.r.e                 *               	Sempre
		9	immigrato in attesa di definizione                	Sempre*/

	}
	
	public static class AmParameterKey implements Serializable{
		public static String WS_RICERCA_URL = "smartwelfare.ricercaSoggetto.ws.url";
		public static String WS_RICERCA_URL_BRIDGE = "smartwelfare.ricercaSoggetto.ws.bridge.url";
		public static String WS_RICERCA_URL_BRIDGE_TOKEN = "smartwelfare.ricercaSoggetto.ws.bridge.token";
		
		public static String WS_RICERCA_USR = "smartwelfare.ricercaSoggetto.ws.username";
		public static String WS_RICERCA_PWD = "smartwelfare.ricercaSoggetto.ws.password";
		
		public static String WS_RICERCA_JKS_PATH="smartwelfare.ricercaSoggetto.jks.path"; //Anagrafe regionale SIRPS
		public static String WS_RICERCA_JKS_PWD="smartwelfare.ricercaSoggetto.jks.pwd";   //Anagrafe regionale SIRPS
		
		public static String WS_MEDICI_URL = "smartwelfare.mediciws.url";
		
		public static String WS_ATLANTE_USR = "smartwelfare.atlantews.username";
		public static String WS_ATLANTE_PWD = "smartwelfare.atlantews.password";
		
		
		public static String WS_ISEE_URL = "smartwelfare.isee.ws.url";
		public static String WS_ISEE_USERNAME = "smartwelfare.isee.ws.username";
		public static String WS_ISEE_UFFICIO = "smartwelfare.isee.ws.codiceUfficio";
		public static String WS_ISEE_ENTE = "smartwelfare.isee.ws.codiceEnte";
		public static String WS_ISEE_ABILITA = "smartwelfare.isee.ws.abilita";
		
		
		public static String REQUIRED_FONTE_FINANZIAMENTO = "smartwelfare.obbligatorio.fonteFinanziamento";

		public static String suffix_NIS = "NIS";
		public static String suffix_PDF = "PDF";
		public static String suffix_DF = "DF";
		public static String suffix_COP = "COP";

		public static String TIPO_INTERVENTO_VIEW = "smartwelfare.erogazioni.tipoInterventoView";//SISO_1110
		public static String NOMENCLATORE_TIPO_INTERVENTO_CUSTOM = "smartwelfare.label.erogazioni.tipoIntNomenclatore";//SISO_1110
		public static String LIVELLI_NOMENCLATORE_INTERVENTO_CUSTOM = "smartwelfare.label.erogazioni.livelliNomenclatore";//SISO_1110
	
		public static String RILEVAZIONE_PRESENZE_ABILITA = "smartwelfare.rilevazionepresenze.abilita";//#ROMACAPITALE
		public static String MAX_VARIAZIONI_ANAGRAFICHE = "smartwelfare.timertask.maxVariazioniAnagrafiche";

		public static String RDC_ABILITA = "smartwelfare.rdc.abilita";
		
		public static String POR_UDC_ABILITA = "smartwelfare.por.udc.abilita";
		public static String POR_CS_ABILITA = "smartwelfare.por.cs.datiSociali.abilita";
		public static String POR_MODELLO_STAMPA = "smartwelfare.stampa.por012019.nome";
		public static String POR_RESIDENZA_DOMICILIO_REGIONE = "smartwelfare.resi.domi.por.regione";
		public static String GESTIONE_CAPOFILA_PIC = "smartwelfare.udc.gestionePicCapofila.abilita";
		public static String DOC_INDIVIDUALI_PROTOCOLLO_VIS = "smartwelfare.docIndividuali.colonnaProtocollo.visibile";
		
	/*	public static String KEY_AUTHENTICATION = "smartwelfare.umbria.api.authentication";
		public static String KEY_URL_TOKEN = "smartwelfare.umbria.api.token.url";
		public static String KEY_URL_SSOCIALI = "smartwelfare.umbria.api.serviziSociali.url";
		public static String KEY_JKS_PATH = "smartwelfare.umbria.api.jks.path";
		public static String KEY_JKS_PASSWORD = "smartwelfare.umbria.api.jks.password"; */

	}
	
	public static class TabUDC implements Serializable{
		public static String ACCESSO_TAB = "accesso";
		public static String SEGNALANTE_TAB = "segnalante";
		public static String SEGNALATO_TAB = "segnalato";
		public static String RIFERIMENTO_TAB = "riferimento";
		public static String MOTIVAZIONE_TAB = "motivazione";
		public static String INTERVENTI_TAB = "interventi";
		public static String CHIUSURA_TAB = "chiusura";
	}
	
	//SISO-1127 
	public static class TipoAggiornamentoAnagrafica
	{
		public static final String ASSENTE = "-ASSENTE -";
		
		public static final String RESIDENZA = "RESIDENZA";
		public static final String DOMICILIO = "DOMICILIO";
		public static final String MEDICO_REVOCATO = "MEDICO REVOCATO";
		public static final String NUOVO_MEDICO = "NUOVO MEDICO";
		public static final String ANAGRAFICA = "ANAGRAFICA";
		public static final String MEDICO = "MEDICO";
		
		public static final String ANAGRAFICA_COGNOME = "ANAGRAFICA COGNOME";
		public static final String ANAGRAFICA_NOME = "ANAGRAFICA NOME";
		public static final String ANAGRAFICA_CODICE_FISCALE = "ANAGRAFICA CODICE FISCALE";
		public static final String ANAGRAFICA_TELEFONO = "ANAGRAFICA NUM. TELEFONO";
		public static final String ANAGRAFICA_CELLULARE = "ANAGRAFICA NUM. CELLULARE";
		public static final String ANAGRAFICA_STATO_CIVILE = "ANAGRAFICA STATO CIVILE";
		public static final String ANAGRAFICA_DATA_DECESSO = "ANAGRAFICA DATA DECESSO";
		public static final String INDIRIZZO_RESIDENZA = "INDIRIZZO RESIDENZA";
		public static final String NUM_CIVICO_RESIDENZA = "NUM. CIVICO RESIDENZA";
		public static final String COD_COM_RESIDENZA = "COD COMUNE RESIDENZA";
		public static final String COMUNE_DI_RESIDENZA = "COMUNE RESIDENZA";
		public static final String PROVINCIA_DI_RESIDENZA = "PROVINCIA RESIDENZA";
		
		public static final String ANAGRAFICA_SESSO = "ANAGRAFICA SESSO";
		public static final String ANAGRAFICA_CITTADINANZA = "ANAGRAFICA CITTADINANZA";
		public static final String CODICE_COMUNE_DOMICILIO = "COD. COMUNE DOMICILIO";
		public static final String COMUNE_DOMICILIO = "COMUNE DOMICILIO";
		public static final String NUM_CIVICO_DOMICILIO= "NUM. CIVICO DOMICILIO";
		public static final String INDIRIZZO_DOMICILIO = "INDIRIZZO DOMICILIO";
		public static final String PROVINCIA_DI_DOMICILIO = "PROVINCIA DOMICILIO";
		
		public static final String COGNOME_NUOVO_MEDICO = "COGNOME NUOVO MEDICO";
		public static final String NOME_NUOVO_MEDICO ="NOME NUOVO MEDICO";
		public static final String COD_REGIONALE_NUOVO_MEDICO ="COD. REGIONALE NUOVO MEDICO";
		public static final String CODICE_FISCALE_NUOVO_MEDICO ="CODICE FISCALE NUOVO MEDICO";
		public static final String DATA_SCELTA_NUOVO_MEDICO ="DATA SCELTA NUOVO MEDICO";
		public static final String DATA_REVOCA_NUOVO_MEDICO ="DATA REVOCA NUOVO MEDICO";
		
		public static final String DENOMINAZIONE_MEDICO_REVOCATO = "DENOMINAZIONE MEDICO REVOCATO";
		public static final String COGNOME_MEDICO_REVOCATO = "COGNOME MEDICO REVOCATO";
		public static final String NOME_MEDICO_REVOCATO ="NOME MEDICO REVOCATO";
		public static final String COD_REGIONALE_MEDICO_REVOCATO ="COD. REGIONALE MEDICO REVOCATO";
		public static final String CODICE_FISCALE_MEDICO_REVOCATO ="CODICE FISCALE MEDICO REVOCATO";
		public static final String DATA_SCELTA_MEDICO_REVOCATO ="DATA SCELTA MEDICO REVOCATO";
		public static final String DATA_REVOCA_MEDICO_REVOCATO ="DATA REVOCA MEDICO REVOCATO";
 	
	}
	
	public static class TipiInterventoCustom{
		
		public static final int UDC_INTERMEDIAZIONE_ABITATIVA		 = 79;
		public static final int UDC_ORIENTAMENTO_INSERIMENTO_LAVORO	 = 80;
		public static final int UDC_MEDIAZIONE_CULTURALE		 	 = 81;
		public static final int UDC_ORIENTAMENTO_ALL_ISTRUZIONE_FORMAZIONE = 82;
		public static final int UDC_INVIO_A_ORGANIZZAZIONE_ESTERNA	 = 176;
		public static final int UDC_INVIO_ALTRO_UFFICIO	 	 = 177;
		
		
	}
	
	public static class PermessiProgettiIndividuali{
		public static final String ITEM = "CarSociale";
		public static final String ABILITA="Visualizza Lista PAI";
	}
	
	public static class TipoFunzioneStruttura{
		public static final long VILLAGGIO = 1;
		public static final long STRUTTURE_MINORI = 2;
	}
	
	public static class TipoSecondoLivello{
		public static final String MINORI = "Equipe Minori";
	}
	
	public static class StatiRichiesta {
		public enum STATO_RICHIESTA {
			INVIATA("INVIATA","Inviata"),
			ACCETTATA_STRUTTURA("ACCETTATA_STRUTTURA","Accettata"),
			ACCETTATA("ACCETTATA","Accettata da Ente"),
			RIFIUTATA_STRUTTURA("RIFIUTATA_STRUTTURA","Rifiutata"),
			RIFIUTATA("RIFIUTATA","Rifiutata da Ente");
			
			private String codice;
			private String descrizione;
			private STATO_RICHIESTA(String codice, String descrizione) {
				this.codice = codice;
				this.descrizione = descrizione;
			}
			public String getCodice() {
				return codice;
			}
			public void setCodice(String codice) {
				this.codice = codice;
			}
			public String getDescrizione() {
				return descrizione;
			}
			public void setDescrizione(String descrizione) {
				this.descrizione = descrizione;
			}
						
			
		}
		
	}
}
