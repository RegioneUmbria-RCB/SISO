package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.brick.loadDwh.load.util.Util;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.def.CtrHash;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.CtrHash;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.def.Identificativo;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;
import it.webred.rulengine.dwh.table.IdExtFromSequence;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;
import it.webred.utils.GenericTuples;
import it.webred.utils.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;



public abstract class AbstractTabellaDwhDao  implements GenericDao {

	
	protected static LinkedHashMap<Class<? extends TabellaDwh>,ArrayList<Method>> tableWithMasterTables =  new LinkedHashMap<Class<? extends TabellaDwh>, ArrayList<Method>>();
	protected static ArrayList<String> campiSqlStandard;
	protected LinkedHashMap<String,Object> valoriSqlStandard;
	private static final Logger log = Logger.getLogger(AbstractTabellaDwhDao.class.getName());
	protected  TabellaDwh tabella;
	protected String nomeTabella;
	protected String nomeTabellaTMP;


	
	protected boolean disattivaStorico = false;
	protected boolean inReplace = false;
	
	static {
		campiSqlStandard = new ArrayList<String>();
		campiSqlStandard.add("ID_ORIG");
		campiSqlStandard.add("ID_EXT");
		campiSqlStandard.add("ID");
		campiSqlStandard.add("CTR_HASH");
		campiSqlStandard.add("DT_INIZIO_VAL");
		campiSqlStandard.add("DT_FINE_VAL");
		campiSqlStandard.add("FK_ENTE_SORGENTE");
		campiSqlStandard.add("DT_INS");
		campiSqlStandard.add("DT_MOD");
		campiSqlStandard.add("FLAG_DT_VAL_DATO");
		campiSqlStandard.add("DT_EXP_DATO");
		campiSqlStandard.add("DT_INIZIO_DATO");
		campiSqlStandard.add("DT_FINE_DATO");
		campiSqlStandard.add("PROCESSID");
		

	}

	public TabellaDwh getTabella()
	{
		return tabella;
	}
	
	public AbstractTabellaDwhDao(TabellaDwh tab, BeanEnteSorgente bes) 
	{
		tabella = tab;
		this.disattivaStorico = bes.isDisabilitaStorico();
		this.inReplace = bes.isInReplace();
		abstractInit();
	}

	public AbstractTabellaDwhDao(TabellaDwh tab) 
	{
		tabella = tab; 	
		abstractInit();
	}
	
	
	
	public void abstractInit() {
		// VERIFICO LA PRESENZA DI RELAZIONI VERSO LE MASTER TABLE
		try {
			manageTableWithMasterTables();
		} catch (Exception e1) {
			log.error("Problemi gestione relazioni master",e1);
		}
		

		CtrHash c;
		if (this.getTabella().getCtrHash()==null ||  this.getTabella().getCtrHash().getValore()==null)
				this.getTabella().setCtrHash();

		// se l'id originale non è stato fornito allora 
		// bisogna avere pazienza e settarlo con l'hash appena calcolato
		// ma in questo caso sorge il problema della storicizzazione!!!
		// infati se la storicizzazione e' attiva allora bisogna storicizzare tutto 
		// se non attiva si deve andare in sostituzione e cancellare i record precedenti sulla stessa tabella
		// (se la fornitura e' completa allora tutti altrimenti solo quelli relativi alle informazioni giunte)
		if (!(this.getTabella() instanceof IdExtFromSequence) && this.getTabella().getIdOrig().getValore()==null) {
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore(this.getTabella().getCtrHash().getValore());
			this.getTabella().setIdOrig(co);
		}
		// imposto la data inizio con la giusta data di validità: quella del dato o quella di esportazione oppure 
		try
		{
			if (this.getTabella().getDtInizioVal().getValore()==null) {
				if (this.getTabella().getFlagDtValDato().compareTo(BigDecimal.ONE)==0)
						this.getTabella().setDtInizioVal((DtIniVal)DwhUtils.getDataDwh(new DtIniVal(),this.getTabella().getDtInizioDato().getValore()));
				else 
					this.getTabella().setDtInizioVal((DtIniVal)DwhUtils.getDataDwh(new DtIniVal(),this.getTabella().getDtExpDato().getValore()));
			}
		}
		catch (Exception e)
		{
			log.error("Problemi impostazione data inizio validita",e);
		}
			

		nomeTabella = StringUtils.JavaName2NomeCampo(this.tabella.getClass().getSimpleName());
		nomeTabellaTMP = Util.prefissoTmpTabelle + nomeTabella;

		
		valoriSqlStandard = new LinkedHashMap<String,Object>();
		valoriSqlStandard.put(campiSqlStandard.get(0),tabella.getIdOrig());
		valoriSqlStandard.put(campiSqlStandard.get(1),tabella.getIdExt());
		valoriSqlStandard.put(campiSqlStandard.get(2),tabella.getId());
		valoriSqlStandard.put(campiSqlStandard.get(3),tabella.getCtrHash());
		valoriSqlStandard.put(campiSqlStandard.get(4),tabella.getDtInizioVal());
		valoriSqlStandard.put(campiSqlStandard.get(5),tabella.getDtFineVal());

		Relazione es = tabella.getFkEnteSorgente();

		if (es!=null)
			valoriSqlStandard.put(campiSqlStandard.get(6),((Identificativo)es.getRelazione()).getValore());
		else
			valoriSqlStandard.put(campiSqlStandard.get(6),null);
		valoriSqlStandard.put(campiSqlStandard.get(7),tabella.getDtIns());
		valoriSqlStandard.put(campiSqlStandard.get(8),tabella.getDtMod());
		valoriSqlStandard.put(campiSqlStandard.get(9),tabella.getFlagDtValDato());
		valoriSqlStandard.put(campiSqlStandard.get(10),tabella.getDtExpDato());
		valoriSqlStandard.put(campiSqlStandard.get(11),tabella.getDtInizioDato());
		valoriSqlStandard.put(campiSqlStandard.get(12),tabella.getDtFineDato());
		
		valoriSqlStandard.put(campiSqlStandard.get(13),tabella.getProcessid());
		
	}
	


	/**
	 * Verifica la presenza nella tabella corrente di Relazioni verso tabelle master
	 * e di conseguenza imposta la storicizzazioone 
	 * @throws Exception 
	 */
	private void manageTableWithMasterTables() throws Exception {
		ArrayList<Method> masterClasses = new ArrayList<Method>();
		if (this.tableWithMasterTables.containsKey((tabella.getClass())))
			return; // gia' passato e verificato
	 	
		// se non ancora verificato verifico
		PropertyDescriptor[] pd = DwhUtils.getBeanFields(tabella);
		for (int i = 0; i < pd.length; i++) {
			PropertyDescriptor p = pd[i];
			Class c = p.getPropertyType();
			if (Relazione.class.getName().equals(c.getName())) {
				Method m = p.getReadMethod();
				if (m==null)
					continue;
				Object o;
				try {
					o = m.invoke(tabella);
					if (o instanceof RelazioneToMasterTable) {
						masterClasses.add(m);
					}
				} catch (Exception e) {
					log.error("Errore nella ricerca dei metofi get per le tabelle Master",e);
					throw e;
				}
					
			}
		}

		// metto i metodi per l'accesso ai valori delle relazioni per le tabelle master
		this.tableWithMasterTables.put(this.getTabella().getClass(), masterClasses);
	}

	public static String getPlainSelect(Class<? extends TabellaDwh> tab) throws Exception {
		 ArrayList<String> a = getNomeCampiTabella(tab);
		 String selectPlain = "SELECT ";
	    for (int i = 0; i < a.size(); i++) {
	    	boolean sequenceConn = tab.newInstance() instanceof IdExtFromSequence && a.get(i).equalsIgnoreCase("SEQUENCE_CONN");
			if (!sequenceConn) {
				selectPlain += a.get(i) + ", ";
			}	    	
		}
	    selectPlain = selectPlain.substring(0,selectPlain.lastIndexOf(","));
	    selectPlain += " FROM " +StringUtils.JavaName2NomeCampo(tab.getSimpleName());
	    return selectPlain;
		
	}
	
	/**
	 * RESTITUISCE LA STRINGA DI UPDATE DEL CAMPO CTR_HASH
	 * CON LA CONDIZIONE ID=? PRONTA PER LA PREPARESTATEMENT
	 * @param tab
	 * @return
	 * @throws Exception
	 */
	public static String getUpdateCtrHash(Class<? extends TabellaDwh> tab) throws Exception {
		 String sqlUpd = "UPDATE " + StringUtils.JavaName2NomeCampo(tab.getSimpleName()) ;
		 sqlUpd += " SET CTR_HASH=? WHERE ID=?";
	    return sqlUpd;
		
	}
	
	public static ArrayList<String> getNomeCampiTabella(Class<? extends TabellaDwh> tabellaClass) throws Exception {
		try {
			ArrayList<String>  nomi = new ArrayList<String>();
			TabellaDwh t = tabellaClass.newInstance();
			GenericTuples.T2<DynaBean,HashMap<String,Object>> dyn = DwhUtils.getDynaInfoTable(t,true);
			Set sets = dyn.secondObj.entrySet();
			Iterator it = sets.iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String setMethod = (String) entry.getKey();
				String campo =  setMethod.substring(3).substring(0, 1).toLowerCase() +  setMethod.substring(3).substring(1,  setMethod.substring(3).length());
				String campoDB = StringUtils.JavaName2NomeCampo(campo);
				nomi.add(campoDB);
			}
			nomi.addAll(campiSqlStandard);
			return nomi;
		} catch (Exception e) {
			log.error("Impossibile recuperare i nomi e i valori dei campi della tabella custom ",e);
			throw e;
		}
	}
	

	
}
