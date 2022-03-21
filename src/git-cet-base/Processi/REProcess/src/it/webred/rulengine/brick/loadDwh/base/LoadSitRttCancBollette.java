package it.webred.rulengine.brick.loadDwh.base;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.swing.text.TabSet;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.AbstractTabellaDwhDao;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.SitComuneDao;
import it.webred.rulengine.dwh.Dao.SitDCivicoDao;
import it.webred.rulengine.dwh.Dao.SitDPersonaDao;
import it.webred.rulengine.dwh.Dao.SitDUnioneDao;
import it.webred.rulengine.dwh.Dao.SitRttBolletteDao;
import it.webred.rulengine.dwh.Dao.SitRttDettBolletteDao;
import it.webred.rulengine.dwh.Dao.SitRttRateBolletteDao;
import it.webred.rulengine.dwh.Dao.SitTrffMulteDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.def.TipoXml;
import it.webred.rulengine.dwh.table.SitRttBollette;
import it.webred.rulengine.dwh.table.SitDPersona;
import it.webred.rulengine.dwh.table.SitDUnione;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.utils.StringUtils;

public class LoadSitRttCancBollette extends AbstractLoaderCommand implements Rule
{
	
	public LoadSitRttCancBollette(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitRttCancBollette.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitRttBollette.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			
			String id_servizio = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			String cod_bolletta = (String) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			
			boolean salvato = false;
			String sql = "UPDATE SIT_RTT_BOLLETTE";
			sql = sql + " SET DT_FINE_VAL = SYSDATE, DT_MOD = SYSDATE";
			sql = sql + " WHERE COD_BOLLETTA = ? AND ID_SERVIZIO = ?";
			// effettuo l'update
			PreparedStatement upd =  null;
			try {
				upd = conn.prepareStatement(sql);
				upd.setString(1, cod_bolletta);
				upd.setString(2, id_servizio);
				upd.executeUpdate();
				
			} catch (Exception e) {
				log.error("Errore update invalidazione:" +tabellaDwhClass.getName() );
				throw e;
			} finally {
				DbUtils.close(upd);
			}
			
			salvato = true;
			log.info(salvato?"Salvataggio avvenuto correttamente": "Salvataggio non avvenuto");
			
		} catch (Exception e)
		{
			log.error("CancSitRttBollette",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record SIT_RTT_BOLLETTE invalidato";
		return(new ApplicationAck(msg));

	}

}
