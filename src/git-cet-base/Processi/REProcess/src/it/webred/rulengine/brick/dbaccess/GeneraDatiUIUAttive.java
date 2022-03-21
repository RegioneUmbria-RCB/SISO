package it.webred.rulengine.brick.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;


public class GeneraDatiUIUAttive extends Command implements Rule {

	
	private static final org.apache.log4j.Logger log = Logger.getLogger(GeneraDatiUIUAttive.class.getName());

	public GeneraDatiUIUAttive(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	@Override
	public CommandAck run(Context ctx) throws CommandException {
		
		Connection conn = ctx.getConnection((String)ctx.get("connessione"));
		
		if (conn==null)
			return new RejectAck("Connessione non fornita");
		
		Connection connI = null;
		try {
			connI = RulesConnection.getConnection("DWH_"+ctx.getBelfiore());
		} catch (Exception e) {
			log.warn("NON PRESENTE LA CONNESSIONE DWH_"+ctx.getBelfiore()+" IN RE");
			ErrorAck ea = new ErrorAck(e);
			return ea;
		}
		
		
		String comune =null;
		
		try {
			
		List parametriIn = this.getParametersIn(_jrulecfg);
		comune = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
		
	 
		String codCommand = (String) ctx.getDeclarativeType("RULENGINE.COD_COMMAND").getValue();
		
		//elenco UIU attive
		String sqlUiuAttive = "insert into sit_acc_ogg " +
				"select seq_acc_ogg.nextval,foglio,particella,UNIMM,categoria,pr_id,cd_com,rendita,pkid_uiu  " +
				"from ( " +
					"select distinct pkid_uiu,foglio,particella,UNIMM,categoria,'"+ctx.getProcessID() +"' as pr_id,'" +
					""+ codCommand+"' as cd_com"+ ",rendita " +
					" from siti.sitiuiu u" +
					" WHERE u.cod_nazionale = ? " +
					" AND u.data_fine_val = TO_DATE ('99991231', 'yyyymmdd')" +
					" and rownum < 10 " +  /// solo per i test!!!!  RIMUOVERE!!!!!
				")";
		
		
		PreparedStatement psSiti = conn.prepareStatement(sqlUiuAttive);
			psSiti.setString(1, comune);
			int numRec1 = psSiti.executeUpdate();
		
		psSiti.close();
		
			
		//indirizzi da siti
		/*String sql2= "SELECT DISTINCT c1.*, s.prefisso as Sedime ,s.nome AS indirizzo, s.numero AS NUM_STRA " +
					 "FROM ( SELECT DISTINCT c2.*, c.pkid_stra, c.civico " +
						"FROM (select distinct c1.ID as ID_UIU ,c1.ID_ORIG as ID_ORIG_UIU, uiu.PKID_CIVI " +
						      "from sit_acc_ogg c1,siti.siticivi_uiu uiu " +
						      "where c1.ID_orig =  uiu.PKID_UIU " +
						      "and uiu.data_fine_val = TO_DATE ('99991231', 'yyyymmdd')" +
						      ") c2 " +
						", siti.siticivi c " +
						"where c.pkid_civi = c2.pkid_civi " +
						"AND c.data_fine_val = TO_DATE ('99991231', 'yyyymmdd') " +
						")c1 " +
					 ",siti.sitidstr s " +
					 "where c1.pkid_stra = s.pkid_stra " +
					 "AND s.data_fine_val = TO_DATE ('99991231', 'yyyymmdd')";
		*/
		
		String sqlInsert2 = "INSERT into sit_acc_ogg_ind " +
					 "SELECT seq_acc_ogg_ind.nextval," +
					 "num_stra, " +
					 "'<civicocomposto><att tipo=\"numero\" valore=\"'||civico||'\"/></civicocomposto>'," +
					 "'"+ctx.getProcessID()+"'," +
					 "'"+codCommand+"'," +
					 "id_uiu," +
					 "sedime," +
					 "indirizzo," +
					 "0 " +
			 		 "FROM " +
			 		 "(SELECT DISTINCT c1.*, s.prefisso  as Sedime ,s.nome AS indirizzo, s.numero AS NUM_STRA " +
			 		 "FROM ( SELECT DISTINCT c2.*, c.pkid_stra, c.civico " +
			 		 		"FROM (SELECT distinct c1.ID as ID_UIU ,c1.ID_ORIG as ID_ORIG_UIU, uiu.PKID_CIVI " +
			 		 			   "FROM sit_acc_ogg c1,siti.siticivi_uiu uiu " +
			 		 			   "WHERE c1.ID_orig =  uiu.PKID_UIU " +
			 		 			   "AND uiu.data_fine_val = TO_DATE ('99991231', 'yyyymmdd')) c2 " +
			 		 		", siti.siticivi c " +
			 		 		"WHERE c.pkid_civi = c2.pkid_civi " +
			 		 		"AND c.data_fine_val = TO_DATE ('99991231', 'yyyymmdd') )c1" +
			 		 ",siti.sitidstr s " +
			 		 "WHERE c1.pkid_stra = s.pkid_stra " +
			 		 "AND s.data_fine_val = TO_DATE ('99991231', 'yyyymmdd')" +
			 		 ")";
		
		/*Statement stuiu = conn.createStatement();
		ResultSet rsu = stuiu.executeQuery(sql2);
		while (rsu.next()){
			System.out.println("strada="+rsu.getString("num_stra"));
			System.out.println("processId="+ctx.getProcessID());
			System.out.println("cod_comm="+codCommand);
			System.out.println("fk_id_uiu="+rsu.getString("id_uiu"));
			System.out.println("strada="+rsu.getString("num_stra"));
			System.out.println("strada="+rsu.getString("num_stra"));
			
			String sqlInsert1 ="insert into sit_acc_ogg_ind values( " +
								"seq_acc_ogg_ind.nextval," +
								"'"+rsu.getString("num_stra")+"', " +
								//"'<civicocomposto><att tipo=\"numero\" valore=\""+rsu.getString("civico")+"\"/></civicocomposto>'" +
								"?," +
							    "'"+ctx.getProcessID()+"'," +
							    "'"+codCommand+"'," +
							    "" +rsu.getInt("id_uiu")+"," +
							    "'"+rsu.getString("sedime")+"', " +
							    "'"+rsu.getString("indirizzo")+"', " +
							    "0" +
							    ")"; 
			PreparedStatement psSiti = conn.prepareStatement(sqlInsert1);
			TipoXml tx = new TipoXml ();
			tx.setValore("<civicocomposto><att tipo=\"numero\" valore=\""+rsu.getString("civico")+"\"/></civicocomposto>");
			psSiti.setObject(1, tx.getValore());
			
			int numRec1 = psSiti.executeUpdate();
			psSiti.close();
		}
		rsu.close();
		stuiu.close();
		*/
		
		psSiti = conn.prepareStatement(sqlInsert2);
		int numRec2 = psSiti.executeUpdate();
		
		psSiti.close();
		
		//indirizzi da catasto -- query da rivedere non ce la fa a girare!!  non termina! forse servono indici? bho!
		String sqlInsert3= "" +
					"INSERT INTO sit_acc_ogg_ind " +
					"SELECT seq_acc_ogg_ind.nextval," +
							"progressivo, " +
							"'<civicocomposto><att tipo=\"numero\" valore=\"'||civ||'\"/></civicocomposto>'," +
							"'"+ctx.getProcessID()+"'," +
							"'"+codCommand+"'," +
							"id_uiu," +
							"sedime," +
							"ind," +
							"1 " +
					"FROM " +
					 	 	"(SELECT DISTINCT  CV.ID as id_uiu, CV.FOGLIO, CV.PARTICELLA, CV.SUBALTERNO, " +
					 	 			"ind.progressivo, t.descr as sedime,ind.ind AS ind, ind.civ1 AS civ " +
					 	 	"FROM SITI.load_cat_uiu_id IDU," +
					 	 		 "SITI.siticomu c," +
					 	 		 "SITI.load_cat_uiu_ind ind," +
					 	 		 "SITI.cat_d_toponimi t," +
					 	 		 "sit_acc_ogg CV " +
					 	 	"WHERE NOT EXISTS (SELECT 1 FROM SIT_ACC_OGG_IND AOI WHERE AOI.FK_ACC_OGG = CV.ID) " +
					 	 	"AND c.cod_nazionale = '"+comune+"' " +
					 	 	"AND IDU.codi_fisc_luna = c.codi_fisc_luna " +
					 	 	"AND IDU.sez = c.sezione_censuaria " +
					 	 	"AND IDU.foglio = LPAD (CV.FOGLIO, 4, '0') " +
							"AND IDU.mappale = CV.PARTICELLA " +
							"AND IDU.sub = LPAD (CV.SUBALTERNO, 4, '0') " +
							"AND ind.codi_fisc_luna = c.codi_fisc_luna " +
							"AND ind.sezione = IDU.sezione " +
							"AND ind.id_imm = IDU.id_imm " +
							"AND ind.progressivo = IDU.progressivo " +
							"AND t.pk_id = ind.toponimo " +
							")";
		
		psSiti = conn.prepareStatement(sqlInsert3);
		int numRec3 = psSiti.executeUpdate();
		
		psSiti.close();
		
		
		return new ApplicationAck("GeneraDatiUIUAttive eseguito");
				
		} catch (Exception e)
		{
			log.error("Errore nell'esecuzione di GeneraDatiUIUAttive",e);
			ErrorAck ea = new ErrorAck(e);
			return ea;
		} finally {
			
		}
		
	}


		
		
}
 
		






