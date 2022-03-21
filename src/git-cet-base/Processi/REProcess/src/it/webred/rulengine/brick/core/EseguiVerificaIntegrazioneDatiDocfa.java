package it.webred.rulengine.brick.core;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.WarningAck;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;


/*
 * 
 * @author DAN
 * @version $Revision: 1.4 $ $Date: 2008/05/15 10:51:49 $
 */
@Deprecated
public class EseguiVerificaIntegrazioneDatiDocfa extends Command  implements Rule
{
	private static final Logger log = Logger.getLogger(EseguiVerificaIntegrazioneDatiDocfa.class.getName());
	
	/**
	 * @param bc
	 */
	public EseguiVerificaIntegrazioneDatiDocfa(BeanCommand bc)
	{
		super(bc);
	}
	

	public EseguiVerificaIntegrazioneDatiDocfa(BeanCommand bc,Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}


	@SuppressWarnings("unchecked")
	public CommandAck run(Context ctx)
	{
		Connection conn = null;
		
		try
		{
			log.debug("Recupero parametro da contesto");
			conn = ctx.getConnection((String)ctx.get("connessione"));
			
			//String elencoForniture = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			String elencoForniture = null;
			ComplexParam paramSql = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			HashMap<String,ComplexParamP> pars2 = paramSql.getParams();
			Set set2 = pars2.entrySet();
			Iterator it2 = set2.iterator();
			int i2 =1;
			while (it2.hasNext()) {
				Entry entry = (Entry)it2.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				elencoForniture = o.toString();
			}
			
			log.debug("Elenco forniture: "+elencoForniture);
			
			String whereSqlDocfa = null;
			if(elencoForniture!=null && !(elencoForniture.trim()).equals(""))
			{
				whereSqlDocfa = " where fornitura in(";
				String forniture[] = elencoForniture.split(";");
				for (int i = 0; i < forniture.length; i++)
				{
					if (i>0)
						whereSqlDocfa = whereSqlDocfa + ",";
					whereSqlDocfa = whereSqlDocfa + "TO_DATE('"+ forniture[i]+"','dd/mm/yyyy')";
				}
				whereSqlDocfa = whereSqlDocfa + ")";
			}
			else
			{
				whereSqlDocfa = " where TO_CHAR(FORNITURA,'dd/mm/yyyy') not in( select distinct c.iid_fornitura from DOCFA_COMUNICAZIONE c)";
			}
			
			//inserisco qui tutte query che elaborano i dati appena trasferiti per prearare le Comunicazioni
			List<RAbNormal>  listaAnomalie = this.eseguiQueryRecuperoDatiVerticaleDocfa(conn, whereSqlDocfa,(String)ctx.getDeclarativeType("RULENGINE.CODENTE").getValue());
			CommandAck ca = null;
			if (listaAnomalie == null || listaAnomalie.size()<1) {
				ApplicationAck aa =  new ApplicationAck("Generate le comunicazioni DOCFA senza anomalie");
				ca = aa;
			} else {
				WarningAck wa = new WarningAck("Comunicazioni DOCFA generate con "+ listaAnomalie.size() + " anomalie");
				wa.setAbn(listaAnomalie);
				ca = wa;
			}
			
			return ca;			
		}
		catch(Exception ex)
		{
			try
			{
				conn.rollback();
			}
			catch (SQLException e)
			{
			}
			log.error("EseguiVerificaIntegrazioneDatiDocfa ", ex);
			ErrorAck ea = new ErrorAck(ex.getMessage());
			return (ea);
		}
				
	}

	private List<RAbNormal>  eseguiQueryRecuperoDatiVerticaleDocfa(Connection conn, String whereSqlDocfa, String codEnte) throws Exception
	{
		//String sqlDocfa = "select distinct protocollo_reg,TO_CHAR(FORNITURA,'dd/mm/yyyy')FORNITURA from docfa_dati_generali ";
		String sqlDocfa = _jrulecfg.getProperty("rengine.rule.sql.docfa.value");
		
		if (whereSqlDocfa != null )
			sqlDocfa = sqlDocfa + whereSqlDocfa;
		
		String sqlDocfaDic = _jrulecfg.getProperty("rengine.rule.sql.docfadic.value");
		String sqlUIUDocfa = _jrulecfg.getProperty("rengine.rule.sql.uiudocfa.value");	
		String sqlUIUCensDocfa = _jrulecfg.getProperty("rengine.rule.sql.uiucensdocfa.value");
		String sqlUIUCatastoS = (_jrulecfg.getProperty("rengine.rule.sql.uiucatastos.value")).replace("$COD_ENTE", codEnte);		
		String sqlUIUCatastoC = (_jrulecfg.getProperty("rengine.rule.sql.uiucatastoc.value")).replace("$COD_ENTE", codEnte);
		
		//TODO millucci per scatenare indici visto che installazioni solo monoente ha tolto il belfiore
		//TODO millucci ha aggiunto and rownum = 1 per risolvere il problema della distinct
		String sqlUIUDGraf = _jrulecfg.getProperty("rengine.rule.sql.uiudgraf.value");
		String sqlUIUTitoS = (_jrulecfg.getProperty("rengine.rule.sql.uiutitos.value")).replace("$COD_ENTE", codEnte);		
		String sqlUIUTitoC = (_jrulecfg.getProperty("rengine.rule.sql.uiutitoc.value")).replace("$COD_ENTE", codEnte);
		String sqlUIUTitoResAna = _jrulecfg.getProperty("rengine.rule.sql.uiutitoresana.value");
		String sqlUIUTitoResTributi = _jrulecfg.getProperty("rengine.rule.sql.uiutitorestributi.value");
		String sqlUIUTitoGTributi = _jrulecfg.getProperty("rengine.rule.sql.uiutitogtributi.value");
		String sqlUIURLTitoG = _jrulecfg.getProperty("rengine.rule.sql.uiurltitog.value");
		
		long kkk=0;		
		
		try		{
			//recupero tutti i docfa elaborati (protocollo_reg+data_fornitura)
			Statement stat = conn.createStatement();
			ResultSet rsDocfa = stat.executeQuery(sqlDocfa);
			List<RAbNormal> abnormals = new ArrayList();
			String protocolloReg = "";

			while (rsDocfa.next())
			{
				kkk++;
				try
				{
				DocfaBean db = new DocfaBean();
				db.setProtocolloReg(rsDocfa.getString("protocollo_reg"));
				protocolloReg = db.getProtocolloReg();//per banormal
				db.setDataFornitura(rsDocfa.getString("FORNITURA"));
				
				//per ogni docfa recupero il dichiarante
				PreparedStatement pstsDic = conn.prepareStatement(sqlDocfaDic);
				pstsDic.setString(1, rsDocfa.getString("protocollo_reg"));
				pstsDic.setString(2, rsDocfa.getString("FORNITURA"));
				ResultSet rsDic = pstsDic.executeQuery();
				if (rsDic.next())
				{
					DocfaUIUTitolareBean dich = new DocfaUIUTitolareBean();
					dich.setDenominazione(rsDic.getString("dic_cognome"));
					dich.setNome(rsDic.getString("dic_nome"));
					dich.setIndirizzoResidenza(rsDic.getString("dic_res_indir"));
					dich.setCivicoResidenza(rsDic.getString("dic_res_numciv"));
					dich.setCapResidenza(rsDic.getString("dic_res_cap"));
					dich.setComuneResidenza(rsDic.getString("dic_res_com"));
					dich.setProvinciaResidenza(rsDic.getString("dic_res_prov"));
					db.setDichiarante(dich);
				}
				rsDic.close();
				pstsDic.close();
				
				//per ogni docfa cerco le uiu coinvolte
				ArrayList elencoUIU = new ArrayList();
				PreparedStatement psts = conn.prepareStatement(sqlUIUDocfa);
				psts.setString(1, rsDocfa.getString("protocollo_reg"));
				psts.setString(2, rsDocfa.getString("FORNITURA"));
				ResultSet rsUIU = psts.executeQuery();
				
				
				//per ogni UIU recupero tutte le info da catasto proprie e dei proprietari
				while (rsUIU.next())
				{
					DocfaUIUBean uiu = new DocfaUIUBean();
					uiu.setProtocolloReg(rsDocfa.getString("protocollo_reg"));
					uiu.setDataFornitura(rsDocfa.getString("FORNITURA"));
					uiu.setFoglio(rsUIU.getString("FOGLIO"));
					uiu.setParticella(rsUIU.getString("NUMERO"));
					uiu.setSubalterno(rsUIU.getString("SUBALTERNO"));
					uiu.setTipoOperazione(rsUIU.getString("TIPO_OPERAZIONE"));
					uiu.setCodCategoriaDocfaProp(rsUIU.getString("PROP_CATEGORIA"));
					uiu.setClasseDocfaProp(rsUIU.getString("PROP_CLASSE"));
					uiu.setRenditaDocfaProp(rsUIU.getString("PROP_RENDITA_EURO_CATA"));
					uiu.setSuperficieDocfaProp(rsUIU.getString("PROP_SUPERFICIE_CATA"));
					uiu.setZonaDocfaProp(rsUIU.getString("PROP_ZONA_CENSUARIA"));
					uiu.setIndirizzoDocfa(rsUIU.getString("INDIR_TOPONIMO"));
					uiu.setCivicoDocfa(rsUIU.getString("INDIR_NCIV_UNO"));
					
					//recupero i dati censuari del docfa 
					PreparedStatement pstsUIUCens = conn.prepareStatement(sqlUIUCensDocfa);
					pstsUIUCens.setString(1, rsDocfa.getString("protocollo_reg"));
					pstsUIUCens.setString(2, rsDocfa.getString("FORNITURA"));
					pstsUIUCens.setString(3, rsUIU.getString("FOGLIO"));
					pstsUIUCens.setString(4, rsUIU.getString("NUMERO"));
					pstsUIUCens.setString(5, rsUIU.getString("SUBALTERNO"));
					
					ResultSet rsUIUCens = pstsUIUCens.executeQuery();
					if (rsUIUCens.next())
					{
						uiu.setCodCategoriaDocfa(rsUIUCens.getString("categoria"));
						uiu.setClasseDocfa(rsUIUCens.getString("classe"));
						uiu.setRenditaDocfa(rsUIUCens.getString("rendita_euro"));
						uiu.setSuperficieDocfa(rsUIUCens.getString("superficie"));
						uiu.setZonaDocfa(rsUIUCens.getString("zona"));
					}
					rsUIUCens.close();
					pstsUIUCens.close();
										
					//info FPS catasto
					PreparedStatement pstsUIUCata = null;
					// millucci ha detto  di trattare i V come S 
					if(uiu.getTipoOperazione().equals("V"))
					{
						uiu.setTipoOperazione("S");
						uiu.setTipoOperazioneOld("V");
					}
					if (uiu.getTipoOperazione().equals("S")){
						pstsUIUCata = conn.prepareStatement(sqlUIUCatastoS); //cerco proprietari attivi al momento di chiusura della UIU
					}
					if (uiu.getTipoOperazione().equals("C")){
						pstsUIUCata = conn.prepareStatement(sqlUIUCatastoC);//cerco proprietari attivi dal momento di creazione della UIU
					}
					try
					{
						//controllo che il valore del foglio sia numerico (a catasto posso accedere
						//solo per valori numerici di foglio!)
						Integer.parseInt(rsUIU.getString("FOGLIO")); 
					}catch (NumberFormatException e)
					{
					
						ExceptionApplicativa ea = new ExceptionApplicativa("UIU FOGLIO: dato non numerico");
						throw ea;
						
					}
					pstsUIUCata.setString(1, rsUIU.getString("FOGLIO"));
					
					pstsUIUCata.setString(2, rsUIU.getString("NUMERO"));
					if (rsUIU.getString("SUBALTERNO") == null || (rsUIU.getString("SUBALTERNO").trim()).equals(""))
					{
						pstsUIUCata.setString(3, "0");
					}
					else
					{
						pstsUIUCata.setString(3, rsUIU.getString("SUBALTERNO"));
					}
					pstsUIUCata.setString(4, rsDocfa.getString("FORNITURA"));
					if (uiu.getTipoOperazione().equals("S")){
						pstsUIUCata.setString(5, rsDocfa.getString("FORNITURA"));
					}
					ResultSet rsUIUCata = pstsUIUCata.executeQuery();
					
					ArrayList elencoCivici = new ArrayList();
					while (rsUIUCata.next())
					{
						//memorizzo le info del catasto
						uiu.setCodCategoria(rsUIUCata.getString("categoria"));
						uiu.setDescrCategoria(rsUIUCata.getString("descategoria"));
						uiu.setZona(rsUIUCata.getString("zona"));
						uiu.setMicrozona(rsUIUCata.getString("microzona"));
						uiu.setSuperficie(rsUIUCata.getString("superficie"));
						uiu.setVani(rsUIUCata.getString("vani"));
						uiu.setRendita(rsUIUCata.getString("rendita"));
						uiu.setClasse(rsUIUCata.getString("classe"));
						uiu.setPartita(rsUIUCata.getString("partita"));
						uiu.setPiano(rsUIUCata.getString("piano"));
						BeanCivico bc = new BeanCivico();
						bc.setCivico(rsUIUCata.getString("civico"));
						bc.setFlagPrincipale(rsUIUCata.getString("PRINCIPALE"));
						bc.setIndirizzo(rsUIUCata.getString("indirizzo"));
						elencoCivici.add(bc);
					}
					rsUIUCata.close();
					pstsUIUCata.close();
					uiu.setElencoIndirizzi(elencoCivici);
					
					//ricerca graffati
					PreparedStatement pstsUIUGra = conn.prepareStatement(sqlUIUDGraf);
					pstsUIUGra.setString(1, rsUIU.getString("FOGLIO"));
					pstsUIUGra.setString(2, rsUIU.getString("NUMERO"));
					pstsUIUGra.setString(3, rsUIU.getString("SUBALTERNO"));
					ResultSet rsUIUGra = pstsUIUGra.executeQuery();
					
					ArrayList elencoGraffati = new ArrayList();
					while (rsUIUGra.next())
					{
						DocfaUIUBean uiuGraf = new DocfaUIUBean();
						uiuGraf.setFoglio(rsUIUGra.getString("foglio"));
						uiuGraf.setParticella(rsUIUGra.getString("mappale"));
						uiuGraf.setSubalterno(rsUIUGra.getString("sub"));
						elencoGraffati.add(uiuGraf);
					}
					rsUIUGra.close();
					pstsUIUGra.close();
					uiu.setElencoGraffati(elencoGraffati);					
					
					//info FPS Titolari
					PreparedStatement pstsUIUTito = null;
					if (uiu.getTipoOperazione().equals("S")){
						pstsUIUTito = conn.prepareStatement(sqlUIUTitoS);
					}
					if (uiu.getTipoOperazione().equals("C")){
						pstsUIUTito = conn.prepareStatement(sqlUIUTitoC);
					}
					
					pstsUIUTito.setString(1, rsUIU.getString("FOGLIO"));
					pstsUIUTito.setString(2, rsUIU.getString("NUMERO"));
					if (rsUIU.getString("SUBALTERNO") == null || (rsUIU.getString("SUBALTERNO").trim()).equals(""))
					{
						pstsUIUTito.setString(3, "0");
					}
					else
					{
						pstsUIUTito.setString(3, rsUIU.getString("SUBALTERNO"));
					}
					pstsUIUTito.setString(4, rsDocfa.getString("FORNITURA"));
					//pstsUIUTito.setString(5, rsDocfa.getString("FORNITURA"));
					//pstsUIUTito.setString(6, rsDocfa.getString("FORNITURA"));
					if (uiu.getTipoOperazione().equals("S")){
						pstsUIUTito.setString(5, rsDocfa.getString("FORNITURA"));//messo idx 5 invece che 7 (meno parametri cons_sogg_tab non ha storico)
					}
					
					ResultSet rsUIUTito = pstsUIUTito.executeQuery();
					
					ArrayList elencoTitolari = new ArrayList();
					while (rsUIUTito.next())
					{
						//memorizzo le info dei Titolari
						DocfaUIUTitolareBean tito = new DocfaUIUTitolareBean();
						tito.setProtocolloReg(rsDocfa.getString("protocollo_reg"));
						tito.setDataFornitura(rsDocfa.getString("fornitura"));
						tito.setFoglio(rsUIU.getString("FOGLIO"));
						tito.setParticella(rsUIU.getString("NUMERO"));
						tito.setSubalterno(rsUIU.getString("SUBALTERNO"));
						
						tito.setDenominazione(rsUIUTito.getString("denominazione"));
						tito.setNome(rsUIUTito.getString("nome"));
						tito.setDataNascita(rsUIUTito.getString("data_nasc"));
						tito.setComuneNascita(rsUIUTito.getString("comune_nascita"));
						tito.setFlagPersona(rsUIUTito.getString("flag_pers_fisica"));
						tito.setCodiceFiscale(rsUIUTito.getString("codi_fisc"));
						tito.setPartitaIva(rsUIUTito.getString("partita_iva"));
						tito.setSesso(rsUIUTito.getString("sesso"));
						tito.setFlagProvenienzaInfo("C"); // dati trovati in Catasto
						if (tito.getCodiceFiscale()!= null && tito.getCodiceFiscale().length() == 16)
						{
							//recupero residenza del titolare da Anagrafe
							PreparedStatement pstsUIUTitoRes = conn.prepareStatement(sqlUIUTitoResAna);
							pstsUIUTitoRes.setString(1, tito.getCodiceFiscale());
							
							
							ResultSet rsUIUTitoRes = pstsUIUTitoRes.executeQuery();
							if (rsUIUTitoRes.next())
							{
								tito.setIndirizzoResidenza(rsUIUTitoRes.getString("descr_via"));
								tito.setCivicoResidenza(rsUIUTitoRes.getString("NUMERO_CIV")+" "+rsUIUTitoRes.getString("ESP_CIV"));
								//?????????? SOLO MILANO!!!!!!!	
								tito.setComuneResidenza("MILANO");
								tito.setFlagProvenienzaInfo("A"); // dati trovati in Anagrafe
							}
							else
							{
								//cerco nei tributi
								PreparedStatement pstsUIUTitoResTri = conn.prepareStatement(sqlUIUTitoResTributi);
								pstsUIUTitoResTri.setString(1, tito.getCodiceFiscale());
								
								ResultSet rsUIUTitoResTri = pstsUIUTitoResTri.executeQuery();
								if (rsUIUTitoResTri.next())
								{
									tito.setIndirizzoResidenza(rsUIUTitoResTri.getString("descr_indirizzo"));
									tito.setComuneResidenza(rsUIUTitoResTri.getString("comune_residenza"));
									tito.setFlagProvenienzaInfo("T"); // dati trovati in Tributi
								}
								rsUIUTitoResTri.close();
								pstsUIUTitoResTri.close();
								
							}
							rsUIUTitoRes.close();
							pstsUIUTitoRes.close();							
						}
						else
						{
							//cerco dati giuridici in tributi
							PreparedStatement pstsUIUTitoGTri = conn.prepareStatement(sqlUIUTitoGTributi);
							if (tito.getCodiceFiscale()!= null && tito.getCodiceFiscale().length() == 11)
								pstsUIUTitoGTri.setString(1, tito.getCodiceFiscale());
							else if (tito.getPartitaIva()!= null && tito.getPartitaIva().length() == 11)
								pstsUIUTitoGTri.setString(1, tito.getPartitaIva());
							else
								pstsUIUTitoGTri.setString(1, "");
							
							ResultSet rsUIUTitoGTri = pstsUIUTitoGTri.executeQuery();
							if (rsUIUTitoGTri.next())
							{
								tito.setIndirizzoResidenza(rsUIUTitoGTri.getString("descr_indirizzo"));
								tito.setComuneResidenza(rsUIUTitoGTri.getString("comune_residenza"));
								tito.setFlagProvenienzaInfo("T"); // dati trovati in Tributi
							}
							rsUIUTitoGTri.close();
							pstsUIUTitoGTri.close();
							
							
							//cerco dati del rappresentante legale
							PreparedStatement pstsUIUTitoGRL = conn.prepareStatement(sqlUIURLTitoG);
							
							try {
								if (tito.getCodiceFiscale()!= null && tito.getCodiceFiscale().length() == 11)
									pstsUIUTitoGRL.setString(1, tito.getCodiceFiscale());
								else if (tito.getPartitaIva()!= null && tito.getPartitaIva().length() == 11)
									pstsUIUTitoGRL.setString(1, tito.getPartitaIva());
								else
									pstsUIUTitoGRL.setString(1, "");
								
								ResultSet rsUIUTitoGRL = pstsUIUTitoGRL.executeQuery();
								if (rsUIUTitoGRL.next())
								{
									tito.setCodiceFiscaleRL(rsUIUTitoGRL.getString("COD_FISC_RAP_LEG_PFIS"));
									tito.setDenominazioneRL(rsUIUTitoGRL.getString("COGNOME_RAP_LEG_PFIS"));
									tito.setNomeRL(rsUIUTitoGRL.getString("NOME_RAP_LEG_PFIS"));
									tito.setIndirizzoResidenzaRL(rsUIUTitoGRL.getString("IND_CIV_RAP_LEG_PFIS"));
									tito.setComuneResidenzaRL(rsUIUTitoGRL.getString("COMUNE_RES_RAP_LEG_PFIS"));
									tito.setCapResidenzaRL(rsUIUTitoGRL.getString("CAP_RAP_LEG_PFIS"));
									tito.setProvinciaResidenzaRL(rsUIUTitoGRL.getString("PROVIN_RES_RAP_LEG"));
								}
								rsUIUTitoGRL.close();
							}catch(Exception e) {
								ByteArrayOutputStream ba = new ByteArrayOutputStream();
								e.printStackTrace(new PrintStream(ba));
								RAbNormal rabn = new RAbNormal();
								rabn.setEntity("VerificaIntegrazioneDocfa");
								rabn.setMessage(ba.toString());
								rabn.setKey(protocolloReg);
								rabn.setLivelloAnomalia(1);					
								rabn.setMessageDate(new Timestamp(new java.util.Date().getTime()));
								abnormals.add(rabn);
								log.warn("Anomalia VerificaIntegrazioneDocfa line "+kkk+" key "+protocolloReg+" message: "+e.getMessage());
							}finally {
								pstsUIUTitoGRL.close();	
							}
							
							
							
						}
						
						tito.setPercentualePossesso(rsUIUTito.getString("QUOTA"));
						tito.setTipoPossesso("P");
						elencoTitolari.add(tito);
						
					}
					
					rsUIUTito.close();
					pstsUIUTito.close();
					uiu.setElencoTitolari(elencoTitolari);
					elencoUIU.add(uiu);
					
				}
				
				rsUIU.close();
				psts.close();
				
				//memorizzo tutte le uiu coinvolte nel DOCFA
				db.setElencoUiu(elencoUIU);		
				//verifico i dati recuperati
				this.eseguiVerificaDatiDocfa(conn, db);
				//inserisco i dati nel DB
				this.eseguiInserimentoDatiComunicazioni(conn, db);
				}
				catch(ExceptionApplicativa eaperab)
				{
					RAbNormal rabn = new RAbNormal();
					rabn.setEntity("VerificaIntegrazioneDocfa");
					rabn.setMessage(eaperab.getMessage());
					rabn.setKey(protocolloReg);
					rabn.setLivelloAnomalia(1);					
					rabn.setMessageDate(new Timestamp(new java.util.Date().getTime()));
					abnormals.add(rabn);
					log.debug("Anomalia VerificaIntegrazioneDocfa line "+kkk+" key "+protocolloReg+" message: "+eaperab.getMessage()); 
				}
				catch(Exception perab)
				{
					ByteArrayOutputStream ba = new ByteArrayOutputStream();
					perab.printStackTrace(new PrintStream(ba));
					RAbNormal rabn = new RAbNormal();
					rabn.setEntity("VerificaIntegrazioneDocfa");
					rabn.setMessage(ba.toString());
					rabn.setKey(protocolloReg);
					rabn.setLivelloAnomalia(1);					
					rabn.setMessageDate(new Timestamp(new java.util.Date().getTime()));
					abnormals.add(rabn);
					log.warn("Anomalia VerificaIntegrazioneDocfa line "+kkk+" key "+protocolloReg+" message: "+perab.getMessage(),perab); 
				}
			}
			rsDocfa.close();
			stat.close();
			
			return abnormals;
			
		}
		catch(Exception e)
		{
			throw e;
		}
		
	}
	
	
	//private void eseguiVerificaDatiDocfa(Connection conn,ArrayList elencoDocfa) throws Exception
	private void eseguiVerificaDatiDocfa(Connection conn,DocfaBean docfa) throws Exception
	{
		String sqlInsertImportLog = _jrulecfg.getProperty("rengine.rule.sql.insertimportlog.value");
		
		try
		{
			//controllo i dati fondamentali del docfa
			//DATA_FORNITURA
			if ( docfa.getDataFornitura()== null || (docfa.getDataFornitura()).equals("") )
			{
				PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
				pstsImportLog.setString(1,null );
				pstsImportLog.setString(2, docfa.getProtocolloReg());
				pstsImportLog.setString(3, "DOCFA_DATI_GENERALI");
				pstsImportLog.setString(4, "FORNITURA");
				pstsImportLog.setString(5, "DATO MANCANTE");
				pstsImportLog.setString(6, null);
				pstsImportLog.setString(7, null);
				pstsImportLog.setString(8, null);
				pstsImportLog.executeUpdate();
				pstsImportLog.close();
			}
			
			//PROTOCOLLO_REG
			if ( docfa.getProtocolloReg()== null || (docfa.getProtocolloReg()).equals("") )
			{
				PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
				pstsImportLog.setString(1,docfa.getDataFornitura());
				pstsImportLog.setString(2, null);
				pstsImportLog.setString(3, "DOCFA_DATI_GENERALI");
				pstsImportLog.setString(4, "PROTOCOLLO_REG");
				pstsImportLog.setString(5, "DATO MANCANTE");
				pstsImportLog.setString(6, null);
				pstsImportLog.setString(7, null);
				pstsImportLog.setString(8, null);
				pstsImportLog.executeUpdate();
				pstsImportLog.close();
			}
				
			//controllo i dati delle UIU
			ArrayList elencoUIU = docfa.getElencoUiu();
			for(int j=0;j<elencoUIU.size();j++)
			{
				DocfaUIUBean uiu = (DocfaUIUBean)elencoUIU.get(j);
				//foglio
				if ( uiu.getFoglio()== null || (uiu.getFoglio()).equals("") )
				{
					PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
					pstsImportLog.setString(1,docfa.getDataFornitura() );
					pstsImportLog.setString(2, docfa.getProtocolloReg());
					pstsImportLog.setString(3, "DOCFA_UIU");
					pstsImportLog.setString(4, "FOGLIO");
					pstsImportLog.setString(5, "DATO MANCANTE");
					pstsImportLog.setString(6, null);
					pstsImportLog.setString(7, uiu.getParticella());
					pstsImportLog.setString(8, uiu.getSubalterno());
					pstsImportLog.executeUpdate();
					pstsImportLog.close();
				}
				else 
				{
					//foglio valore numerico
					try
					{
						Integer.parseInt(uiu.getFoglio());
					}catch (NumberFormatException e)
					{
						PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
						pstsImportLog.setString(1,docfa.getDataFornitura() );
						pstsImportLog.setString(2, docfa.getProtocolloReg());
						pstsImportLog.setString(3, "DOCFA_UIU");
						pstsImportLog.setString(4, "FOGLIO");
						pstsImportLog.setString(5, "DATO NON NUMERICO");
						pstsImportLog.setString(6, uiu.getFoglio());
						pstsImportLog.setString(7, uiu.getParticella());
						pstsImportLog.setString(8, uiu.getSubalterno());
						pstsImportLog.executeUpdate();
						pstsImportLog.close();
						
					}
				}
					
				//particella(numero)
				if ( uiu.getParticella()== null || (uiu.getParticella()).equals("") )
				{
					PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
					pstsImportLog.setString(1,docfa.getDataFornitura() );
					pstsImportLog.setString(2, docfa.getProtocolloReg());
					pstsImportLog.setString(3, "DOCFA_UIU");
					pstsImportLog.setString(4, "NUMERO");
					pstsImportLog.setString(5, "DATO MANCANTE");
					pstsImportLog.setString(6, uiu.getFoglio());
					pstsImportLog.setString(7, null);
					pstsImportLog.setString(8, uiu.getSubalterno());
					pstsImportLog.executeUpdate();
					pstsImportLog.close();
				}
				//subalterno
				if ( uiu.getSubalterno()== null || (uiu.getSubalterno()).equals("") )
				{
					PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
					pstsImportLog.setString(1,docfa.getDataFornitura() );
					pstsImportLog.setString(2, docfa.getProtocolloReg());
					pstsImportLog.setString(3, "DOCFA_UIU");
					pstsImportLog.setString(4, "SUBALTERNO");
					pstsImportLog.setString(5, "DATO MANCANTE");
					pstsImportLog.setString(6, uiu.getFoglio());
					pstsImportLog.setString(7, uiu.getSubalterno());
					pstsImportLog.setString(8, null);
					pstsImportLog.executeUpdate();
					pstsImportLog.close();
				}
				
				//tipo operazione
				if ( !valoreValidoStringa(uiu.getTipoOperazione()))
				{
					PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
					pstsImportLog.setString(1,docfa.getDataFornitura() );
					pstsImportLog.setString(2, docfa.getProtocolloReg());
					pstsImportLog.setString(3, "DOCFA_UIU");
					pstsImportLog.setString(4, "TIPO_OPERAZIONE");
					pstsImportLog.setString(5, "DATO MANCANTE");
					pstsImportLog.setString(6, uiu.getFoglio());
					pstsImportLog.setString(7, uiu.getParticella());
					pstsImportLog.setString(8, uiu.getSubalterno());
					pstsImportLog.executeUpdate();
					pstsImportLog.close();
				}

				//per le uiu che sono state create/variate verifico presenza info recuperati dai DATI_CENSUARI e PROPOSTE DI ASSEGNAZIONE SU DOCFA_UIU
				//if (uiu.getTipoOperazione() != null && !uiu.getTipoOperazione().equals("S"))
				if ( uiu.getTipoOperazione() != null 
					&& (uiu.getTipoOperazione().equals("C")|| uiu.getTipoOperazioneOld().equals("V"))
					)
				{
					//rendita
					if (!valoreValidoNumerico(uiu.getRenditaDocfa()))
					{
						PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
						pstsImportLog.setString(1,docfa.getDataFornitura() );
						pstsImportLog.setString(2, docfa.getProtocolloReg());
						pstsImportLog.setString(3, "DOCFA_DATI_CENSUARI");
						pstsImportLog.setString(4, "RENDITA_EURO");
						pstsImportLog.setString(5, "DATO MANCANTE");
						pstsImportLog.setString(6, uiu.getFoglio());
						pstsImportLog.setString(7, uiu.getParticella());
						pstsImportLog.setString(8, uiu.getSubalterno());
						pstsImportLog.executeUpdate();
						pstsImportLog.close();
					}
					//superficie
					if (!valoreValidoNumerico(uiu.getSuperficieDocfa()))
					{
						PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
						pstsImportLog.setString(1,docfa.getDataFornitura() );
						pstsImportLog.setString(2, docfa.getProtocolloReg());
						pstsImportLog.setString(3, "DOCFA_DATI_CENSUARI");
						pstsImportLog.setString(4, "SUPERFICIE");
						pstsImportLog.setString(5, "DATO MANCANTE");
						pstsImportLog.setString(6, uiu.getFoglio());
						pstsImportLog.setString(7, uiu.getParticella());
						pstsImportLog.setString(8, uiu.getSubalterno());
						pstsImportLog.executeUpdate();
						pstsImportLog.close();
					}
					//classe	
					if (!valoreValidoNumerico(uiu.getClasseDocfa()))
					{
						PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
						pstsImportLog.setString(1,docfa.getDataFornitura() );
						pstsImportLog.setString(2, docfa.getProtocolloReg());
						pstsImportLog.setString(3, "DOCFA_DATI_CENSUARI");
						pstsImportLog.setString(4, "CLASSE");
						pstsImportLog.setString(5, "DATO MANCANTE");
						pstsImportLog.setString(6, uiu.getFoglio());
						pstsImportLog.setString(7, uiu.getParticella());
						pstsImportLog.setString(8, uiu.getSubalterno());
						pstsImportLog.executeUpdate();
						pstsImportLog.close();
					}
					//codice categoria
					if (!valoreValidoStringa(uiu.getCodCategoriaDocfa()))
					{
						PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
						pstsImportLog.setString(1,docfa.getDataFornitura() );
						pstsImportLog.setString(2, docfa.getProtocolloReg());
						pstsImportLog.setString(3, "DOCFA_DATI_CENSUARI");
						pstsImportLog.setString(4, "CATEGORIA");
						pstsImportLog.setString(5, "DATO MANCANTE");
						pstsImportLog.setString(6, uiu.getFoglio());
						pstsImportLog.setString(7, uiu.getParticella());
						pstsImportLog.setString(8, uiu.getSubalterno());
						pstsImportLog.executeUpdate();
						pstsImportLog.close();
					}
					
					//controllo le proposte di categoria,classe,superficie,rendita
					if (!valoreValidoStringa(uiu.getCodCategoriaDocfaProp()) )
					{
						PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
						pstsImportLog.setString(1,docfa.getDataFornitura() );
						pstsImportLog.setString(2, docfa.getProtocolloReg());
						pstsImportLog.setString(3, "DOCFA_UIU");
						pstsImportLog.setString(4, "PROP_CATEGORIA");
						pstsImportLog.setString(5, "DATO MANCANTE per UIU variata o creata");
						pstsImportLog.setString(6, uiu.getFoglio());
						pstsImportLog.setString(7, uiu.getParticella());
						pstsImportLog.setString(8, uiu.getSubalterno());
						pstsImportLog.executeUpdate();
						pstsImportLog.close();
					}
					
					if (!valoreValidoStringa(uiu.getClasseDocfaProp()) )
					{
						PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
						pstsImportLog.setString(1,docfa.getDataFornitura() );
						pstsImportLog.setString(2, docfa.getProtocolloReg());
						pstsImportLog.setString(3, "DOCFA_UIU");
						pstsImportLog.setString(4, "PROP_CLASSE");
						pstsImportLog.setString(5, "DATO MANCANTE per UIU variata o creata");
						pstsImportLog.setString(6, uiu.getFoglio());
						pstsImportLog.setString(7, uiu.getParticella());
						pstsImportLog.setString(8, uiu.getSubalterno());
						pstsImportLog.executeUpdate();
						pstsImportLog.close();
					}
					
					if (!valoreValidoNumerico(uiu.getSuperficieDocfaProp()))
					{
						PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
						pstsImportLog.setString(1,docfa.getDataFornitura() );
						pstsImportLog.setString(2, docfa.getProtocolloReg());
						pstsImportLog.setString(3, "DOCFA_UIU");
						pstsImportLog.setString(4, "PROP_SUPERFICIE_CATA");
						pstsImportLog.setString(5, "DATO MANCANTE per UIU variata o creata");
						pstsImportLog.setString(6, uiu.getFoglio());
						pstsImportLog.setString(7, uiu.getParticella());
						pstsImportLog.setString(8, uiu.getSubalterno());
						pstsImportLog.executeUpdate();
						pstsImportLog.close();
					}
					
					if (!valoreValidoNumerico(uiu.getRenditaDocfaProp()))
					{
						PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
						pstsImportLog.setString(1,docfa.getDataFornitura() );
						pstsImportLog.setString(2, docfa.getProtocolloReg());
						pstsImportLog.setString(3, "DOCFA_UIU");
						pstsImportLog.setString(4, "PROP_RENDITA_EURO_CATA");
						pstsImportLog.setString(5, "DATO MANCANTE per UIU variata o creata");
						pstsImportLog.setString(6, uiu.getFoglio());
						pstsImportLog.setString(7, uiu.getParticella());
						pstsImportLog.setString(8, uiu.getSubalterno());
						pstsImportLog.executeUpdate();
						pstsImportLog.close();
					}
					
				}
									
			}
			//verifico consistenza dei dati integrati per il docfa corrente
			this.eseguiVerificaDatiIntegrati(conn, docfa);
			
		}
		catch(ExceptionApplicativa ea)
		{
			throw ea;
		}
		catch(Exception e)
		{
			throw e;
		}
		
	}
	
	private boolean valoreValidoNumerico(String s)
	{
		if(s == null || s.trim().equals(""))
			return false;
		try
		{
			s = s.replaceAll(",", ".");
			if(Double.parseDouble(s) <=0)
				return false;
			else 
				return true;
		}
		catch(Exception www)
		{
			return false;
		}
		
	}
	private boolean valoreValidoStringa(String s)
	{
		if(s == null || s.trim().equals(""))
			return false;
			if(s.trim().equals("-"))
				return false;
			else 
				return true;		
	}	
	/*
	 * Verifico se sono stati recuperati tutti i dati necessari per le comunicazioni ricercati nei vari DB
	 */
	private void eseguiVerificaDatiIntegrati(Connection conn,DocfaBean docfa) throws Exception
	{
		
		try
		{
			//controllo i dati delle UIU
				ArrayList elencoUIU = docfa.getElencoUiu();
				for(int j=0;j<elencoUIU.size();j++)
				{
					DocfaUIUBean uiu = (DocfaUIUBean)elencoUIU.get(j);
					
					String sqlInsertImportLog = _jrulecfg.getProperty("rengine.rule.sql.insertimportlog.param.value");
					sqlInsertImportLog = sqlInsertImportLog.replace("$DOCFA_GETDATAFORNITURA", docfa.getDataFornitura());
					sqlInsertImportLog = sqlInsertImportLog.replace("$DOCFA_GETPROTOCOLLOREG", docfa.getProtocolloReg());
					sqlInsertImportLog = sqlInsertImportLog.replace("$UIU_GETFOGLIO", uiu.getFoglio());
					sqlInsertImportLog = sqlInsertImportLog.replace("$UIU_GETPARTICELLA", uiu.getParticella());
					sqlInsertImportLog = sqlInsertImportLog.replace("$UIU_GETSUBALTERNO", uiu.getSubalterno());
					
					//controllo se ho trovato la UIU a catasto
					if ( uiu.getCodCategoria()== null || (uiu.getCodCategoria()).equals("") )
					{
						PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
						pstsImportLog.setString(1, "CATASTO");
						pstsImportLog.setString(2, "UIU");
						pstsImportLog.setString(3, "UIU non trovata a catasto");
						pstsImportLog.executeUpdate();
						pstsImportLog.close();
					}
					else
					{
						//verifico i dati categoria,classe,rendita,superficie
						if (uiu.getTipoOperazione().equals("S"))
						{
							if (!valoreValidoStringa(uiu.getCodCategoria()))
							{
								PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
								pstsImportLog.setString(1, "CATASTO");
								pstsImportLog.setString(2, "UIU");
								pstsImportLog.setString(3, "CODICE CATEGORIA non trovata");
								pstsImportLog.executeUpdate();
								pstsImportLog.close();
							}
							
							if(!valoreValidoNumerico(uiu.getClasse()))
							{
								PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
								pstsImportLog.setString(1, "CATASTO");
								pstsImportLog.setString(2, "UIU");
								pstsImportLog.setString(3, "CLASSE non trovata");
								pstsImportLog.executeUpdate();
								pstsImportLog.close();
							}
							
							if(!valoreValidoNumerico(uiu.getRendita()))
							{
								PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
								pstsImportLog.setString(1, "CATASTO");
								pstsImportLog.setString(2, "UIU");
								pstsImportLog.setString(3, "RENDITA non trovata");
								pstsImportLog.executeUpdate();
								pstsImportLog.close();
							}
							
							if(!valoreValidoNumerico(uiu.getSuperficie()))
							{
								PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
								pstsImportLog.setString(1, "CATASTO");
								pstsImportLog.setString(2, "UIU");
								pstsImportLog.setString(3, "SUPERFICIE non trovata");
								pstsImportLog.executeUpdate();
								pstsImportLog.close();
							}
							
							if (uiu.getElencoIndirizzi() == null || uiu.getElencoIndirizzi().size() == 0)
							{
								PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
								pstsImportLog.setString(1, "CATASTO");
								pstsImportLog.setString(2, "UIU");
								pstsImportLog.setString(3, "INDIRIZZO non trovato");
								pstsImportLog.executeUpdate();
								pstsImportLog.close();
							}
							
						}
					}
					
					//controllo se ho trovato i titolari
					if (uiu.getElencoTitolari() == null || uiu.getElencoTitolari().size() == 0)
					{
						PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
						pstsImportLog.setString(1, "CATASTO");
						pstsImportLog.setString(2, "INTESTATARI UIU");
						pstsImportLog.setString(3, "INTESTATARI non trovati");
						pstsImportLog.executeUpdate();
						pstsImportLog.close();
					}
					else
					{
						ArrayList elencoTito = uiu.getElencoTitolari();
						for(int i =0;i<elencoTito.size();i++)
						{
							DocfaUIUTitolareBean tito = (DocfaUIUTitolareBean)elencoTito.get(i);
							
							//loggo la provenienza dei dati del titolare
							if (tito.getFlagProvenienzaInfo().equals("A"))
							{
								PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
								pstsImportLog.setString(1, "ANAGRAFE");
								pstsImportLog.setString(2, "INTESTATARI UIU");
								pstsImportLog.setString(3, "DATI INTESTATARIO RECUPERATI IN ANAGRAFE");
								pstsImportLog.executeUpdate();
								pstsImportLog.close();
							}
							else if(tito.getFlagProvenienzaInfo().equals("T"))
							{
								PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
								pstsImportLog.setString(1, "TRIBUTI");
								pstsImportLog.setString(2, "INTESTATARI UIU");
								pstsImportLog.setString(3, "DATI INTESTATARIO RECUPERATI IN TRIBUTI");
								pstsImportLog.executeUpdate();
								pstsImportLog.close();
							} 
							else if(tito.getFlagProvenienzaInfo().equals("C"))
							{
								PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
								pstsImportLog.setString(1, "CATASTO");
								pstsImportLog.setString(2, "INTESTATARI UIU");
								pstsImportLog.setString(3, "DATI INTESTATARIO RECUPERATI DA CATASTO");
								pstsImportLog.executeUpdate();
								pstsImportLog.close();
							} 									
							
							if (tito.getFlagPersona().equals("P"))
							{ // controllo i dati per persone fisiche
								
								if (!valoreValidoStringa(tito.getCodiceFiscale()))
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "CATASTO");
									pstsImportLog.setString(2, "INTESTATARI FISICI UIU");
									pstsImportLog.setString(3, "CF INTESTATARIO non trovato");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getDenominazione()) )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "CATASTO");
									pstsImportLog.setString(2, "INTESTATARI FISICI UIU");
									pstsImportLog.setString(3, "COGNOME INTESTATARIO non trovato");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getNome()) )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "CATASTO");
									pstsImportLog.setString(2, "INTESTATARI FISICI UIU");
									pstsImportLog.setString(3, "NOME INTESTATARIO non trovato");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getDataNascita()) )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "CATASTO");
									pstsImportLog.setString(2, "INTESTATARI FISICI UIU");
									pstsImportLog.setString(3, "DATA NASCITA INTESTATARIO non trovata");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getComuneNascita()) )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "CATASTO");
									pstsImportLog.setString(2, "INTESTATARI FISICI UIU");
									pstsImportLog.setString(3, "COMUNE NASCITA INTESTATARIO non trovata");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getSesso()) )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "CATASTO");
									pstsImportLog.setString(2, "INTESTATARI FISICI UIU");
									pstsImportLog.setString(3, "SESSO INTESTATARIO non trovato");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getIndirizzoResidenza()) )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "ANAGRAFE - TRIBUTI");
									pstsImportLog.setString(2, "INTESTATARI FISICI UIU");
									pstsImportLog.setString(3, "INDIRIZZO RESIDENZA INTESTATARIO non trovata");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getCivicoResidenza()))
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "ANAGRAFE - TRIBUTI");
									pstsImportLog.setString(2, "INTESTATARI FISICI UIU");
									pstsImportLog.setString(3, "CIVICO RESIDENZA INTESTATARIO non trovata");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getCapResidenza()) )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "ANAGRAFE - TRIBUTI");
									pstsImportLog.setString(2, "INTESTATARI FISICI UIU");
									pstsImportLog.setString(3, "CAP RESIDENZA INTESTATARIO non trovata");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getComuneResidenza()) )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "ANAGRAFE - TRIBUTI");
									pstsImportLog.setString(2, "INTESTATARI FISICI UIU");
									pstsImportLog.setString(3, "COMUNE RESIDENZA INTESTATARIO non trovata");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
							}
							else //"G"
							{// controllo i dati per persone giuridiche
								if (!valoreValidoStringa(tito.getPartitaIva()) )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "CATASTO");
									pstsImportLog.setString(2, "INTESTATARI GIURIDICI UIU");
									pstsImportLog.setString(3, "PARTITA IVA INTESTATARIO non trovata");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getDenominazione()) )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "CATASTO");
									pstsImportLog.setString(2, "INTESTATARI GIURIDICI UIU");
									pstsImportLog.setString(3, "DENOMINAZIONE INTESTATARIO non trovato");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getDataNascita())  )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "CATASTO - TRIBUTI");
									pstsImportLog.setString(2, "INTESTATARI GIURIDICI UIU");
									pstsImportLog.setString(3, "DATA INIZIO ATTIVITA' INTESTATARIO non trovata");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
								
								if (!valoreValidoStringa(tito.getComuneNascita()) && !valoreValidoStringa(tito.getComuneResidenza()) )
								{
									PreparedStatement pstsImportLog = conn.prepareStatement(sqlInsertImportLog);
									pstsImportLog.setString(1, "CATASTO - TRIBUTI");
									pstsImportLog.setString(2, "INTESTATARI GIURIDICI UIU");
									pstsImportLog.setString(3, "COMUNE SEDE INTESTATARIO non trovato");
									pstsImportLog.executeUpdate();
									pstsImportLog.close();
								}
							}
						}
					}
					
					
					
				}
				
		}
		catch(Exception e)
		{
			throw e;
		}
		
	}
	
	private void eseguiInserimentoDatiComunicazioni(Connection conn,DocfaBean docfa) throws Exception	{
		
		String sqlSeqComunicazione = _jrulecfg.getProperty("rengine.rule.sql.seqcomunicazione.value");
		String sqlSeqComOgg = _jrulecfg.getProperty("rengine.rule.sql.seqcomogg.value");
		String sqlInsertComm = _jrulecfg.getProperty("rengine.rule.sql.insertcomm.value");
		String sqlInsertOggComm = _jrulecfg.getProperty("rengine.rule.sql.insertoggcomm.value");
		
		Hashtable mappaUIU = new Hashtable();
		Hashtable mappaTito = new Hashtable();
		
		try	{
			
			String dataFornitura = docfa.getDataFornitura();
			String protocolloReg = docfa.getProtocolloReg();
			
			ArrayList elencouiu = docfa.getElencoUiu();
			for (int i=0;i<elencouiu.size();i++)
			{
				DocfaUIUBean uiu = (DocfaUIUBean)elencouiu.get(i);
				
				//recupero intestatari (e quindi numero comunicazioni)
				ArrayList elencoTito= uiu.getElencoTitolari();
				for (int j=0;j<elencoTito.size();j++)
				{
					DocfaUIUTitolareBean tito= (DocfaUIUTitolareBean)elencoTito.get(j);
					//String keyTito = Integer.toString(j);
					String keyTito = "";
					
					if (tito.getFlagPersona().equals("P"))
					{
						keyTito = tito.getCodiceFiscale();
					}
					else if (tito.getCodiceFiscale()!= null && tito.getCodiceFiscale().length()==11)
					{
						keyTito = tito.getCodiceFiscale();
					}
					else if (tito.getPartitaIva()!= null && tito.getPartitaIva().length()==11)
					{
						keyTito = tito.getPartitaIva();
					}
					
					DocfaUIUBean uiuT = uiu;
					
					//memorizzo percentuale possesso in UIU
					uiuT.setPossesso(tito.getPercentualePossesso());
					
					//controllo la presenza dei graffati
					ArrayList eleGr = uiuT.getElencoGraffati();
					if (eleGr!= null && eleGr.size()>1)
						uiuT.setPresenzaGraffati("S");
					else
						uiuT.setPresenzaGraffati("N");
					
					if (!keyTito.equals("")) //genero la comunicazione solo per titolari che riesco ad identificare per CF o PI!
					{
						Object titoMap = mappaUIU.get(keyTito);
						if (titoMap == null)
						{
							//aggiungo il titolare in hashT
							mappaTito.put(keyTito, tito);
							
							//aggiungo la UIU  su lista in Hast
							ArrayList listaUIUT = new ArrayList();
							listaUIUT.add(uiuT);
							mappaUIU.put(keyTito, listaUIUT);
						}
						else
						{// titolare già presente per questo docfa:
							//aggiungo la uiu alla lista sulla hasht
							ArrayList listaUIUT = (ArrayList)mappaUIU.get(keyTito);
							listaUIUT.add(uiuT);
							mappaUIU.put(keyTito, listaUIUT);
							
						}
					}
					
				}
				
			}
			
			//creo i dati per le comunicazioni
			 for (Enumeration e = mappaTito.keys() ; e.hasMoreElements() ;) {
				 String numCom = "";
		         String chiave = (String)e.nextElement();
		         //recupero le info del titolare 
		         DocfaUIUTitolareBean tito= (DocfaUIUTitolareBean)mappaTito.get(chiave);
		         //recupero le info delle UIU ad esso collegate
		         ArrayList listaUIUT = (ArrayList)mappaUIU.get(chiave);
		         
		         //PRENDO UNA CHIAVE PER LA COMUNICAZIONE
		         Statement st = conn.createStatement();
		         ResultSet rsCom = st.executeQuery(sqlSeqComunicazione);
		         if (rsCom.next())
		         {
		        	 numCom = rsCom.getString("PROG_COM");
		         }
		         rsCom.close();
		         st.close();
		         
		         PreparedStatement pstInsCom = conn.prepareStatement(sqlInsertComm);
		         pstInsCom.setInt(1, Integer.parseInt(numCom) );
		         pstInsCom.setString(2, protocolloReg);
		         pstInsCom.setString(3, dataFornitura);
		         pstInsCom.setString(4, chiave);
		         pstInsCom.setString(5, null); //prefisso
		         pstInsCom.setString(6, null); //telefono
		         pstInsCom.setString(7, tito.getDenominazione());
		         pstInsCom.setString(8, tito.getNome());
		         pstInsCom.setString(9, tito.getDataNascita());
		         pstInsCom.setString(10, tito.getComuneNascita());
		         
		         pstInsCom.setString(11, tito.getProvinciaNascita() );
		         pstInsCom.setString(12, tito.getSesso());
		         pstInsCom.setString(13, tito.getIndirizzoResidenza()+" "+tito.getCivicoResidenza());
		         pstInsCom.setString(14, tito.getCapResidenza());
		         pstInsCom.setString(15, tito.getComuneResidenza()); 
		         pstInsCom.setString(16, tito.getProvinciaResidenza()); 
		         pstInsCom.setString(17, null); //indirizzo domic.
		         pstInsCom.setString(18, null); //cap domic.
		         pstInsCom.setString(19, null); //comune domic.
		         pstInsCom.setString(20, null); //provincia domic.
		         
		         pstInsCom.setString(21, tito.getCodiceFiscaleRL()); //CF RL
		         pstInsCom.setString(22, null); //professo RL
		         pstInsCom.setString(23, null); //tel RL
		         pstInsCom.setString(24, tito.getDenominazioneRL()); //cognome RL
		         pstInsCom.setString(25, tito.getNomeRL()); //nome RL
		         pstInsCom.setString(26, tito.getDataNascitaRL()); //data nascita RL
		         pstInsCom.setString(27, tito.getComuneNascitaRL()); //comune RL
		         pstInsCom.setString(28, tito.getProvinciaNascitaRL()); //prov RL
		         pstInsCom.setString(29, tito.getSessoRL()); //sesso RL
		         pstInsCom.setString(30, tito.getIndirizzoResidenzaRL()); //indirizzo RL
		         
		         pstInsCom.setString(31, tito.getCapResidenzaRL()); //cap RL
		         pstInsCom.setString(32, tito.getComuneResidenzaRL()); //comune RL
		         pstInsCom.setString(33, tito.getProvinciaResidenzaRL()); //provincia RL
		         pstInsCom.setString(34, null); //indirizzo domic RL
		         pstInsCom.setString(35, null); //cap domic RL
		         pstInsCom.setString(36, null); //comune domic RL
		         pstInsCom.setString(37, null); //provincia domic RL
		         if (tito.getCodiceFiscaleRL()!= null && !tito.getCodiceFiscaleRL().trim().equals(""))
		        	 pstInsCom.setString(38, "Y"); //flag RL
		         else
		        	 pstInsCom.setString(38, null); //flag RL
		         pstInsCom.setString(39, null); //flag curatore fall
		         pstInsCom.setString(40, null); //flag tutore
		         
		         pstInsCom.setString(41, null); //flag erede
		         pstInsCom.setString(42, null); //altra natura
		         pstInsCom.setString(43, tito.getFlagPersona()); //flag persona
		         pstInsCom.setString(44, null); //data comunicazione
		         pstInsCom.setString(45, null); //stato
		         pstInsCom.setString(46, Integer.toString(1+listaUIUT.size())); //pagine
		         pstInsCom.setInt(47, 0); //flag elab
		         
		         pstInsCom.executeUpdate();
		         pstInsCom.close();
		         
		         //genero gli oggetti delle comunicazioni
		         for(int k=0;k<listaUIUT.size();k++)
		         {
		        	 String numOggCom = "";
		        	 DocfaUIUBean uiu = (DocfaUIUBean)listaUIUT.get(k);
		        	 //PRENDO UNA CHIAVE PER L'OGGETTO DELLA COMUNICAZIONE
			         Statement stO= conn.createStatement();
			         ResultSet rsOggCom = stO.executeQuery(sqlSeqComOgg);
			         if (rsOggCom.next())
			         {
			        	 numOggCom = rsOggCom.getString("PROG_COM_OGG");
			         }
			         rsOggCom.close();
			         stO.close();
			         
			         PreparedStatement pstInsOgg = conn.prepareStatement(sqlInsertOggComm);
			         pstInsOgg.setString(1, numOggCom );
			         pstInsOgg.setString(2, numCom);
			         pstInsOgg.setString(3, protocolloReg);
			         pstInsOgg.setString(4, dataFornitura);
			         if (uiu.getTipoOperazione().equals("C"))
			         {
			        	pstInsOgg.setString(5, "A1"); 
			           	pstInsOgg.setString(6, "Acquisto Possesso");
			         }
			         else if (uiu.getTipoOperazione().equals("S"))
			         {
			        	pstInsOgg.setString(5, "C1"); 
			           	pstInsOgg.setString(6, "Cessione Possesso");
			         }
			         else //V
			         {
			        	pstInsOgg.setString(5, "V"); 
			           	pstInsOgg.setString(6, "Variazione");
			         }
			         pstInsOgg.setString(7, null); // decorrenza
			         pstInsOgg.setString(8, "Y"); //flag fabbricato
			         pstInsOgg.setString(9, null); //flag area fabbricabile
			         pstInsOgg.setString(10,null); //flag fabbricato D
			         
			         pstInsOgg.setString(11, null ); //flag terreno agricolo
			         //pstInsOgg.setString(12, tornaIndirizzoUIU(uiu.getElencoIndirizzi()));
			         pstInsOgg.setString(12, tornaIndirizzoUIU(uiu));
			         pstInsOgg.setString(13, null); //sezione
			         pstInsOgg.setString(14, uiu.getFoglio());
			         pstInsOgg.setString(15, uiu.getParticella()); 
			         pstInsOgg.setString(16, uiu.getSubalterno());
			         pstInsOgg.setString(17, null); //protocollo scheda
			         pstInsOgg.setString(18, null); //anno
			         if (uiu.getCodCategoriaDocfa()!= null && !uiu.getCodCategoriaDocfa().equals(""))
			        	 pstInsOgg.setString(19, uiu.getCodCategoriaDocfa());
			         else 
			        	 pstInsOgg.setString(19, uiu.getCodCategoria());
			         if (uiu.getClasseDocfa()!= null && !uiu.getClasseDocfa().equals(""))
			        	 pstInsOgg.setString(20, uiu.getClasseDocfa());
			         else
			        	 pstInsOgg.setString(20, uiu.getClasse());
			         pstInsOgg.setString(21, null); //flag rend. presunta
			         pstInsOgg.setString(22, null); //flag rend. definitiva
			         pstInsOgg.setString(23, null); //flag valore venale
			         pstInsOgg.setString(24, null); //flag costi contabili
			         pstInsOgg.setString(25, null); //flag reddito dominicale
			         if (uiu.getRenditaDocfa()!= null && !uiu.getRenditaDocfa().equals(""))
			        	 pstInsOgg.setString(26, uiu.getRenditaDocfa());
			         else
			        	 pstInsOgg.setString(26, uiu.getRendita());
			         pstInsOgg.setString(27, null); // sezione prop
			         pstInsOgg.setString(28, null); //fg. prop
			         pstInsOgg.setString(29, null); //part. prop
			         pstInsOgg.setString(30, null); //sub prop
			         
			         pstInsOgg.setString(31, null); //protocollo scheda prop
			         pstInsOgg.setString(32, null); //anno prop
			         pstInsOgg.setString(33, uiu.getCodCategoriaDocfaProp());
			         pstInsOgg.setString(34, uiu.getClasseDocfaProp());
			         pstInsOgg.setString(35, uiu.getPossesso()); 
			         pstInsOgg.setString(36, null); //flag poss. proprieta
			         pstInsOgg.setString(37, null); //flag poss. usufrutto
			         pstInsOgg.setString(38, null); //flag poss. uso
			         pstInsOgg.setString(39, null); //flag poss. diritto abitazione
			         pstInsOgg.setString(40, null); //flag poss. superficie
			         
			         pstInsOgg.setString(41, null); //flag poss. leasing
			         pstInsOgg.setString(42, null); //altro poss.
			         pstInsOgg.setString(43, null); //flag abitaz. princ. 
			         pstInsOgg.setString(44, null); //flag abitaz princ. nm
			         pstInsOgg.setString(45, null); //contitolari abitaz princ. 
			         pstInsOgg.setString(46, null); //flag inagibile
			         pstInsOgg.setString(47, null); //flag agricoltura
			         pstInsOgg.setString(48, null); //flag immobile escluso
			         pstInsOgg.setString(49, null); //flag riduz.locazione
			         pstInsOgg.setString(50, null); //flag storico
			         
			         pstInsOgg.setString(51, null); //flag detraz.delib
			         pstInsOgg.setString(52, null); //flag detraz delib nm
			         pstInsOgg.setString(53, null); //membri nucleo fam.
			         pstInsOgg.setString(54, null); //flag pensionato
			         pstInsOgg.setString(55, null); //flag coniuge pens.
			         pstInsOgg.setString(56, null); //flag invalido
			         pstInsOgg.setString(57, null); //flag disoccupato
			         pstInsOgg.setString(58, null); //flag mobilita
			         pstInsOgg.setString(59, null); //flag interinale
			         pstInsOgg.setString(60, null); //flag cococo
			         pstInsOgg.setString(61, uiu.getPresenzaGraffati()); //flag presenza graffati
			         pstInsOgg.executeUpdate();
			         pstInsOgg.close();
			         
		         }

		     }
			
		}
		catch(Exception e)
		{
			throw e;
		}
		
	}
	
	//private String tornaIndirizzoUIU(ArrayList elencoCivici)
	private String tornaIndirizzoUIU(DocfaUIUBean uiu)
	{
		ArrayList elencoCivici = uiu.getElencoIndirizzi();
		String indirizzo = "";
		
		for(int i =0;i<elencoCivici.size();i++)
		{
			BeanCivico civico= (BeanCivico)elencoCivici.get(i);
			if (civico.getFlagPrincipale() != null && civico.getFlagPrincipale().equals("Y"))
			{
				indirizzo = civico.getIndirizzo()+" "+civico.getCivico();
			}
			else if (indirizzo.equals("") && civico.getIndirizzo()!= null && !(civico.getIndirizzo().trim()).equals("") )
			{
				indirizzo = civico.getIndirizzo()+" "+civico.getCivico();
			}
		}
		
		//se non ho trovato indirizzi a casto
		if (indirizzo.equals(""))
		{
			//vedo se c'era sul docfa
			if ( uiu.getIndirizzoDocfa()!= null && !uiu.getIndirizzoDocfa().equals(""))
			{
				indirizzo = uiu.getIndirizzoDocfa()+" "+uiu.getCivicoDocfa();
			}
		}
		
		return indirizzo;
	}
	
	private class ExceptionApplicativa extends Exception{
		
		public ExceptionApplicativa(String messaggio)
		{
			super(messaggio);
		}
	}
	


}
