package it.webred.rulengine.brick.reperimento.executor.logic;




import it.webred.rulengine.brick.reperimento.executor.ListaProcessiExecutor;
import it.webred.rulengine.brick.reperimento.executor.bean.GenericValueText;
import it.webred.rulengine.brick.reperimento.executor.bean.Listap;
import it.webred.rulengine.brick.reperimento.executor.bean.Processo;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;

import javax.servlet.http.HttpSession;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;

public class ListapProcessoLogic extends BasicLogic{

	private static final Logger log = Logger.getLogger(ListapProcessoLogic.class.getName());
	
	public static final String LISTA = "lista";
	public static final String DETTAGLIO = "dettaglio";
	public static final String NUOVO_PROCESSO = "nuovoProcesso";
	public static final String SEL_TIPO_PROCESSO = "selTipoProcesso";
	public static final String SALVA = "salva";
	public static final String CANCELLA = "cancella";
	public static final String MUOVI_SU = "muoviSu";
	public static final String MUOVI_GIU = "muoviGiu";
	public static final String EXEC = "exec";
	public static final String SALVA_EXEC = "salvaExec";
	public static final String SALVA_EXEC_PROC = "salvaExecProc";
	public static final String IDX_PROCESSO_SEL = "idxProcessoSel";
	public static final String ID_PROCESSO_SEL = "idProcessoSel";
	public static final String LISTA_NUO = "LISTA_NUO";
	public static final String LISTA_MOD = "LISTA_MOD";
	public static final String PROCESSI_NUO = "PROCESSI_NUO";
	public static final String PROCESSI_MOD = "PROCESSI_MOD";
	public static final String NUM_PROCESSI = "numProcessi";
	public static final String ACQUISIZIONE = "REPERIMENTO";
	public static final String TRATTAMENTO = "TRATTAMENTO";
	public static final String ALERT_MSG = "alertMsg";
	
	public static final String LISTA_LISTE_PROCESSI = "LISTA_LISTE_PROCESSI";
	public static final String LISTA_PROCESSI_ACQUISIZIONE = "LISTA_PROCESSI_ACQUISIZIONE";
	public static final String LISTA_PROCESSI_TRATTAMENTO = "LISTA_PROCESSI_TRATTAMENTO";
	
	public static final String STATO_NON_AVVIATO = "Non Avviato";
	public static final String STATO_IN_ESECUZIONE = "In esecuzione";
	public static final String STATO_CONCLUSO = "Concluso";
	public static final String ESITO_POSITIVO = "Positivo";
	public static final String ESITO_TIMEOUT = "Timeout";
	public static final String ESITO_ERRORI_ESECUZIONE = "Errori di esecuzione";
	public static final String ESITO_CHIAMATA_WS_CARONTE_FALLITA = "Call WS Fallito";
	public static final String ESITO_INTERRUZIONE_INATTESA = "Interruzione server";
	
	
	public ListapProcessoLogic() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ListapProcessoLogic(HashMap<String, Object> configurazione) {
		super(configurazione);
		// TODO Auto-generated constructor stub
	}


	public ListapProcessoLogic(HttpSession sessione) {
		super(sessione);
		// TODO Auto-generated constructor stub
	}
	
	
		
}
