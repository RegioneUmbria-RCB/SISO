package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.RDemanioBeneDao;
import it.webred.rulengine.dwh.Dao.RDemanioBeneInfoDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.RDemanioBeneInfo;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import oracle.sql.CLOB;

import org.apache.log4j.Logger;

public class LoadDemanioBeneInfo extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadDemanioBeneInfo(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadDemanioBeneInfo.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = RDemanioBeneInfo.class;
	}

	private List<Object> caricaParametri(int maxIndex, Context ctx){
		
		List<Object> lst = new ArrayList<Object>();
		List parametriIn = this.getParametersIn(_jrulecfg);
		for(int i=0; i<= maxIndex; i++){
			Object o = ctx.get(((RRuleParamIn) parametriIn.get(i)).getDescr());
			lst.add(o);
		}
		
		return lst;
		
	}

	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		
		try {
			List<Object> params = this.caricaParametri(36, ctx);
			Integer fk_ente_sorgente = (Integer)params.get(34);
			RDemanioBeneInfo tab = (RDemanioBeneInfo)getTabellaDwhInstance(ctx);
			
			String chiaveBene = (String)params.get(36);
			String provenienza = (String)params.get(32);
			
			tab.setQualita((String)params.get(0));
			tab.setQuotaProprieta((String)params.get(1));
		
			tab.setFlCongelato((String)params.get(2));
			tab.setFlAnticoPossesso((String)params.get(3));
		
			tab.setValInventariale((BigDecimal)params.get(4));
			tab.setTotAbitativa((BigDecimal)params.get(5));
			tab.setTotUsiDiversi((BigDecimal)params.get(6));
			tab.setTotUnita((BigDecimal)params.get(7));
			tab.setTotUnitaComunali((BigDecimal)params.get(8));
			tab.setNumBox((BigDecimal)params.get(9));
			tab.setDataCensimento(DwhUtils.getDataDwh(new DataDwh(),(Timestamp)params.get(10)));
			tab.setStatoCensimento((BigDecimal)params.get(11));
			tab.setDataRil(DwhUtils.getDataDwh(new DataDwh(),(Timestamp)params.get(12)));
			tab.setDataIns(DwhUtils.getDataDwh(new DataDwh(),(Timestamp)params.get(13)));
			tab.setDataAgg(DwhUtils.getDataDwh(new DataDwh(),(Timestamp)params.get(14)));
			
			tab.setVolumeTot((BigDecimal)params.get(15));
			tab.setSupCop((BigDecimal)params.get(16));
			tab.setSupTot((BigDecimal)params.get(17));
			tab.setSupTotSlp((BigDecimal)params.get(18));
			tab.setSupLoc((BigDecimal)params.get(19));
			tab.setSupOper((BigDecimal)params.get(20));
			tab.setSupCoSe((BigDecimal)params.get(21));
			tab.setNumPianiF((BigDecimal)params.get(22));
			tab.setNumPianiI((String)params.get(23));
			tab.setRendCatas((BigDecimal)params.get(24));
			tab.setVolumeI((BigDecimal)params.get(25));
			tab.setVolumeF((BigDecimal)params.get(26));
			tab.setSupCommerciale((BigDecimal)params.get(27));
			tab.setValAcquisto((BigDecimal)params.get(28));
			tab.setValCatastale((BigDecimal)params.get(29));
			tab.setNumVani((BigDecimal)params.get(30));
			tab.setMetriQ((BigDecimal)params.get(31));
			tab.setProvenienza(provenienza);
			tab.setTipo((String)params.get(33));

			
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),(Timestamp)params.get(35)));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),null));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),null));
			tab.setFlagDtValDato(new BigDecimal(0));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
		
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore(chiaveBene);
			tab.setIdOrig(co);
				
			RDemanioBeneInfoDao dao = (RDemanioBeneInfoDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadDemanioBeneInfo",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record DEMANIO_BENE_INFO inserito";
		
		return(new ApplicationAck(msg));

	}
	

}
