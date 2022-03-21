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
import it.webred.rulengine.dwh.Dao.RDemanioBeneInvDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.RDemanioBeneInv;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadDemanioBeneInv extends AbstractLoaderCommand implements Rule{

	public LoadDemanioBeneInv(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	private static final Logger log = Logger.getLogger(LoadDemanioBeneInv.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = RDemanioBeneInv.class;
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
			List<Object> params = this.caricaParametri(21, ctx);
			
			RDemanioBeneInv tab = (RDemanioBeneInv)getTabellaDwhInstance(ctx);
			
			String chiaveBene = (String)params.get(0);
			
			tab.setCodInventario((BigDecimal)params.get(1));
			tab.setCodTipo((String)params.get(2));
			tab.setDesTipo((String)params.get(3));
			tab.setCodTipoProprieta((BigDecimal)params.get(4));
			tab.setDesTipoProprieta((String)params.get(5));
			tab.setCodCatInventariale((BigDecimal)params.get(6));
			tab.setDesCatInventariale((String)params.get(7));
			tab.setCodCategoria((String)params.get(8));
			tab.setDesCategoria((String)params.get(9));
			tab.setCodSottoCategoria((String)params.get(10));
			tab.setDesSottoCategoria((String)params.get(11));
			tab.setMefTipologia((String)params.get(12));
			tab.setMefUtilizzo((String)params.get(13));
			tab.setMefFinalita((String)params.get(14));
			tab.setMefNaturaGiuridica((String)params.get(15));
			String provenienza = (String)params.get(16);
			tab.setProvenienza(provenienza);
			tab.setTipo((String)params.get(17));

			Integer fk_ente_sorgente = (Integer)params.get(18);
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),(Timestamp)params.get(19)));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),null));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),null));
			tab.setFlagDtValDato(new BigDecimal(0));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			tab.setCodCartella((String)params.get(20));
			tab.setDesCartella((String)params.get(21));
		
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore(chiaveBene);
			tab.setIdOrig(co);
				
			RDemanioBeneInvDao dao = (RDemanioBeneInvDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadDemanioBeneInv",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record DEMANIO_BENE_INV inserito";
		
		return(new ApplicationAck(msg));

	}
	

}
