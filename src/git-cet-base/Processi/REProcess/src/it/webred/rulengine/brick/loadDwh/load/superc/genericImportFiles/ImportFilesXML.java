package it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles;


import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportFactory;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImportFilesWithTipoRecord;
import it.webred.rulengine.brick.loadDwh.load.util.Util;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.RulEngineException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import org.dom4j.Node;
import org.dom4j.io.DOMWriter;
import org.dom4j.io.SAXReader;


public abstract class ImportFilesXML<T extends EnvImport> extends
		ImportFiles<T> implements ElementHandler {

	public ImportFilesXML(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	boolean lettoqualcosa = false;
	List<String> errori = new ArrayList<String>();
	String tempTable;
	Timestamp dataExport;
	
	//Nome del nodo principale dove sono inseriti i campi da mappare
	public abstract String getNodeName() throws RulEngineException;
	//Nome del nodo secondario dove sono inseriti i sottocampi che ripeteranno quelli principali 
	public abstract String getSubNode() throws RulEngineException;
	//Valore che descrive la natura del nodo secondario: composto da più valori o solo da 1
	public abstract boolean isSubNodeOneValue() throws RulEngineException;
	//Nomi dei nodi da escludere dal mappaggio
	public abstract List<String> getNodeToExclude() throws RulEngineException;
	//Metodo per eseguire azioni particolari sul nodo corrente 
	public abstract void elabCurrentNode(org.dom4j.Element element, HashMap<String, String> hmCampi, 
			String tempTable, Timestamp dataExport, boolean lettoQualcosa, List<String> errori) throws RulEngineException;
	//decido se lasciare tutta l'elaborazione del nodo alla regola stessa  
	public abstract boolean elabPersonalizzata() throws RulEngineException;
	//Esegue un azione dopo l'elaborazione del file
	public abstract void  postElaborazione(String file, List<String> fileDaElaborare, String cartellaFiles);
	// restituisce una mappa con la lista dei namespaces dichiarati nell'xml (es. nel SAIA Ancitel)
	protected abstract Map<String, String> getNamespaces();
	
    public  void selectNode(Document document) throws Exception {
    	org.dom4j.Element  root = document.getRootElement();
    	
    	int y = 0;
    	 for ( Iterator i = root.elementIterator( getNodeName() ); i.hasNext(); ) {
    		 selectAllSubnodes((org.dom4j.Element) i.next()); 
    		 y = y + 1;
         }


    }

    public void  treeWalk(org.dom4j.Element  element, List<org.dom4j.Element> attributeList) {
        for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
            Node node = element.node(i);
            if ( node instanceof org.dom4j.Element ) {
                attributeList.add((org.dom4j.Element ) node);
                treeWalk( (Element ) node,attributeList );
            }
            else {
                // do nothing
            }
        }
    }
    
    public  void selectAllSubnodes(org.dom4j.Element element) throws Exception {
//        for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
//            Node node = element.node(i);
//            if ( node instanceof org.dom4j.Element ) {
         	     

            		List<org.dom4j.Element> attributeList = new ArrayList<org.dom4j.Element>();
            		treeWalk(element, attributeList); // element.selectNodes("*");
					//for ( Iterator ii = element.elementIterator(); ii.hasNext(); ) {
					//	org.dom4j.Element attribute = (org.dom4j.Element) ii.next();
			        //    attributeList.add(attribute);
			        //}
					
					String subNode = getSubNode();
					List<String> nodeToExclude = getNodeToExclude();
					boolean isSubNodeOneValue = isSubNodeOneValue();
					boolean isElabPersonalizzata = elabPersonalizzata();
					
					HashMap<String, String> hmCampi = new HashMap<String, String>();
					boolean toSave = false;
					
					//do la possibilità alla regola chiamata di lavorare il nodo
					//(gestisco anche il salvataggio in tabella) per eventuali elementi speciali
					elabCurrentNode(element, hmCampi, tempTable, dataExport, lettoqualcosa, errori);
					
					if(!isElabPersonalizzata){
						for (int k = 0; k < attributeList.size(); k++) {
		
							org.dom4j.Element attributeNode = attributeList.get(k);
							
							if(nodeToExclude != null && nodeToExclude.contains(attributeNode.getName()))
								continue;
							
							if(subNode != null && subNode.equals(attributeNode.getName())){
								
								if(isSubNodeOneValue){
									
									//poiché è solo 1 valore, lo inserisco nella hashmap e salvo se k non è a fine lista (se fine lista salvo dopo)
									String key = attributeNode.getName();
									String value = attributeNode.getText() != null? attributeNode.getText().trim(): "";
									String keyFormatted = underscoreFormat(key);
									hmCampi.put(keyFormatted, value);
									if(k != attributeList.size() -1){
										boolean saved = insertInTmp(hmCampi, tempTable, dataExport, errori);
										lettoqualcosa = lettoqualcosa || saved;
									}
									
								}else if(toSave){
									boolean saved = insertInTmp(hmCampi, tempTable, dataExport, errori);
									lettoqualcosa = lettoqualcosa || saved;
								}else toSave = true;	
								
							}else{
								
								String key = attributeNode.getName();
								String value = attributeNode.getText() != null? attributeNode.getText().trim(): "";
								String keyFormatted = underscoreFormat(key);
								hmCampi.put(keyFormatted, value);
							
							}
		
							if(k == attributeList.size() -1){
								boolean saved = insertInTmp(hmCampi, tempTable, dataExport, errori);
								lettoqualcosa = lettoqualcosa || saved;
							}
			                
							//treeWalk(  attributeNode );
							
						}
					}
					
					
				

//            }
//            else {
//                // do something....
//            }
//        }
    }
    
	 public  org.w3c.dom.Element convertDom4jElToW3CEl(org.dom4j.Element element) throws DocumentException {
		 org.dom4j.Element elementCopy = element.createCopy();
    	   
           org.dom4j.Document doc1 = DocumentHelper.createDocument();
           doc1.setRootElement(elementCopy);

           // Convert dom4j document to w3c document
           DOMWriter writer = new DOMWriter();
           org.w3c.dom.Document doc2 = writer.write(doc1);

           return doc2.getDocumentElement();
     }
	 
    public void onStart(ElementPath path) {
		// do nothing
		
	}
	
	@Override
	public void onEnd(ElementPath path) {
		org.dom4j.Element row = path.getCurrent();
		   
		try {
				 selectAllSubnodes(row);
		} catch (Exception e) {
			   IllegalStateException ise = new IllegalStateException("Problemi in endTag durante treeWalk!");
			   ise.initCause(e);
			   throw ise;
		}
		
	  org.dom4j.Element rowSet = row.getParent();
      Document document = row.getDocument();
      row.detach();
		
	}
	
	@Override
	protected boolean leggiFile(String file, String cartellaDati,
			String tempTable, String tipoRecord, Timestamp dataExport)
			throws Exception {

		this.tempTable = tempTable;
		this.dataExport = dataExport;



		try {

			if (new File(cartellaDati + "ELABORATI/" + file).exists()) {
				log.debug("Scartato file perche già elaborato " + file);
				RAbNormal abn = new RAbNormal();
				abn.setMessage("Scartato file perche già elaborato " + file);
				abn.setMessageDate(new Timestamp(new Date().getTime()));
				env.getAbnormals().add(abn);
				new File(cartellaDati + file).delete();
				return false;
			}

			// traccia file forniture
			tracciaFornitura(file, cartellaDati, new String());
			
			File fileXML = new File(cartellaDati + file);
			
			
			Map<String, String> uris = this.getNamespaces();
		    if (uris!=null)
		    	uris.putAll(uris);

		     DocumentFactory factory = new DocumentFactory();
		     factory.setXPathNamespaceURIs( uris );

		     SAXReader reader = new SAXReader();
		     reader.setDocumentFactory( factory );
		     
	    	   reader.addHandler( getNodeName(), this);

	    	   
	           Document document = null;
				try {
					document = reader.read(fileXML);
				} catch (DocumentException e) {
					log.error("ERRORE PARSING XML " + file,e);
					throw new RulEngineException("ERRORE PARSING XML " + file, e);

				}
				
	
/*
			javax.xml.parsers.DocumentBuilder builder = null;
			javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(true);
			builder = factory.newDocumentBuilder();
			org.w3c.dom.Document doc = builder.parse(fileXML);
			org.w3c.dom.Node root = doc.getFirstChild();
			NodeList nodeList = ((org.w3c.dom.Element) root)
					.getElementsByTagName(getNodeName());
			String subNode = getSubNode();
			List<String> nodeToExclude = getNodeToExclude();
			boolean isSubNodeOneValue = isSubNodeOneValue();
			boolean isElabPersonalizzata = elabPersonalizzata();
*/
	    	   

			
			/*
			 * Si vanno a mappare tutti i campi sotto il nodo principale
			 * Nel caso sia presente anche quello secondario i campi sotto esso 
			 * andranno a salvare più righe mentre quelli principali manterrano i valori
			*/

			int y = 0;
			/* da qui
			for (int j = 0; j < nodeList.getLength(); j++) {

				org.w3c.dom.Element element = (org.w3c.dom.Element) nodeList.item(j);
				NodeList attributeList = ((org.w3c.dom.Element) element)
						.getElementsByTagName("*");
				
				y = y + 1;
				
				HashMap<String, String> hmCampi = new HashMap<String, String>();
				boolean toSave = false;
				
				//do la possibilità alla regola chiamata di lavorare il nodo
				//(gestisco anche il salvataggio in tabella) per eventuali elementi speciali
				elabCurrentNode(element, hmCampi, tempTable, dataExport, lettoqualcosa, errori);
				
				if(!isElabPersonalizzata){
					for (int k = 0; k < attributeList.getLength(); k++) {
	
						org.w3c.dom.Node attributeNode = attributeList.item(k);
						
						if(nodeToExclude != null && nodeToExclude.contains(attributeNode.getLocalName()))
							continue;
						
						if(subNode != null && subNode.equals(attributeNode.getLocalName())){
							
							if(isSubNodeOneValue){
								
								//poiché è solo 1 valore, lo inserisco nella hashmap e salvo se k non è a fine lista (se fine lista salvo dopo)
								String key = attributeNode.getLocalName();
								String value = attributeNode.getFirstChild() != null? attributeNode.getFirstChild().getNodeValue().trim(): "";
								String keyFormatted = underscoreFormat(key);
								hmCampi.put(keyFormatted, value);
								if(k != attributeList.getLength() -1){
									boolean saved = insertInTmp(hmCampi, tempTable, dataExport, errori);
									lettoqualcosa = lettoqualcosa || saved;
								}
								
							}else if(toSave){
								boolean saved = insertInTmp(hmCampi, tempTable, dataExport, errori);
								lettoqualcosa = lettoqualcosa || saved;
							}else toSave = true;	
							
						}else{
							
							String key = attributeNode.getLocalName();
							String value = attributeNode.getFirstChild() != null? attributeNode.getFirstChild().getNodeValue().trim(): "";
							String keyFormatted = underscoreFormat(key);
							hmCampi.put(keyFormatted, value);
						
						}
	
						if(k == attributeList.getLength() -1){
							boolean saved = insertInTmp(hmCampi, tempTable, dataExport, errori);
							lettoqualcosa = lettoqualcosa || saved;
						}
						
					}
				}
				

			}
				a qui */
			System.out.println("fatti cicli " + y);
			return lettoqualcosa;

		} finally {
			if (errori.size() > 0) {
				PrintWriter pw = new PrintWriter(cartellaDati + "ELABORATI/"
						+ file + ".err");
				for (int ii = 0; ii < errori.size(); ii++) {
					pw.println(errori.get(ii));
				}
				pw.close();
				throw new RulEngineException(
						"Errore di inserimento ! Prodotto file " + file
								+ ".err");

			}
		}
	}
	
	

	protected boolean insertInTmp(HashMap<String, String> hmCampi,
			String tempTable, Timestamp dataExport, List<String> errori)
			throws Exception {
		
		//Nella tabella temporanea nel caso di import XML i campi devono essere uguali agli elementi XML
		
		String insertSql = null;
		boolean lettoqualcosa = false;
		java.sql.PreparedStatement ps = null;
		
			StringBuffer s = new StringBuffer();
			String campi = "";
			String valori = "";
			Iterator it = hmCampi.entrySet().iterator();

			while(it.hasNext()){
				Map.Entry pairs = (Map.Entry)it.next();
				campi += pairs.getKey() + ",";
				valori += "?,";
			}
			s.append("INSERT INTO ");
			s.append(tempTable + " (");
			s.append(campi + "processid,re_flag_elaborato,dt_exp_dato)");
			s.append(" VALUES(");
			s.append(valori);

			s.append("?,"); // processid
			s.append("?,"); // re_flag_elaborato
			s.append("?)"); // dt_exp_dato
			insertSql = s.toString();

		try {
			ps = con.prepareStatement(insertSql);

			int ii = 0;
			it = hmCampi.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pairs = (Map.Entry)it.next();
				if (pairs.getValue() != null && !pairs.getValue().equals(""))
					ps.setString(++ii, (String)pairs.getValue());
				else
					ps.setNull(++ii, Types.VARCHAR);
			}
			
			ps.setString(++ii, processId);
			ps.setString(++ii, "0"); // re_flag_elaborato
			ps.setTimestamp(++ii, dataExport); // dt_exp_dato

			ps.executeUpdate();
			lettoqualcosa = true;
		} catch (Exception e) {
			log.error("Errore di inserimento record", e);
			log.error(insertSql);
			errori.add(insertSql + " _____ECCEZIONE: " + e);
		} finally {
			if (ps != null)
				ps.close();
		}
		
		return lettoqualcosa;
	}
	
	protected String underscoreFormat(String campo){
		
		String campoFormattato = String.valueOf(campo.charAt(0));
		//ad ogni uppercase (65 to 90 ASCII) aggiungo un underscore per i campi della tabella
		for (int i=1; i<campo.length(); i++)
		{
		  int charPoint = (char)campo.charAt(i);
		  char charValue = (char)campo.charAt(i);
		  if (charPoint >= 65 && charPoint <= 90)
			  campoFormattato += "_" + charValue;
		  else campoFormattato += charValue;
		}
		return campoFormattato;
	}

	protected void procesingFile(String file, String cartellaDati)
			throws RulEngineException {
		boolean gestisciTmp = false;
		boolean disabilitaStorico = false;
		if (env.getEnteSorgente().isInReplace())
			gestisciTmp = true;

		if (env.getEnteSorgente().isDisabilitaStorico())
			disabilitaStorico = true;


		LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
		ConcreteImport ci = ConcreteImportFactory.getConcreteImport(this);
		tabs = ci.getTabelleAndTipiRecord();

		// METTO IL FILE NELLA TABELLA TEMPORANEA
		// ogni file lo tratto per tutti i tipi record che ha
		for (String key : tabs.keySet()) {
			String tr = tabs.get(key);
			log.info("CARICO " + file);
			try {
				leggiFile(file, cartellaDati, key, tr, getDataExport());
			} catch (Exception e) {
				throw new RulEngineException("Problema in lettura del file "
						+ file + " tr=" + tr, e);
			}
		}
		ci.postLetturaFileAndFilter(cartellaDati, file, gestisciTmp);
		
		log.info("AVVIO NORMALIZZAZIONE " + file);

		boolean norm = ci.normalizza(super.ctx.getBelfiore());
		
		log.info("Aggiornamento ocntesto con info per eventuale normalizzazione e reverse dati");
		//mettere su ctx le tabelle DWH
		Map m = new HashMap();
		m.put("reverse.tabelleDWH", ci.getTabelleFinaliDWH());
		m.put("reverse.tabs", tabs);
		ctx.addReverseObjects(m);

		/*
		 * se la normalizzazione non avviene (false) allora non faccio neanche
		 * tutto il giochino di riversamento!!
		 */
		if (norm) {
			if (gestisciTmp) {
				// RIVERSO DA TABELLA TMP A PRODUZIONE
				try {
					ArrayList<String> tabelleDWH = ci.getTabelleFinaliDWH();
					Connection conn = ci.getConnection();
					Util.riversaSetDatiDaTmpADwh(tabelleDWH, conn,disabilitaStorico,env.getEnteSorgente().getInReplaceValue(),ci.getGestoreCorrelazioneVariazioni());
				} catch (Exception e) {
					throw new RulEngineException(e.getMessage(), e);
				}
			}

			// dopo la normalizzazione setto ad elaborati tutti i record che
			// sono rimasti con flag a zero per via del fatto che
			// non avevano chiave
			if (!gestisciTmp) {
				log.info("setReFlagElaboratoConChiaveNullaONoChiave");
				ci.setReFlagElaboratoConChiaveNullaONoChiave();
			} else {
				Connection conn = ci.getConnection();
				for (String key : tabs.keySet()) {
					try {
						log.info("TRUNCATE TABELLA " + key);
						Util.truncateTable(key, conn);
					} catch (Exception e) {
						log.error("ERRORE IN TRUNCATE TABELLA " + key);
						throw new RulEngineException(
								"ERRORE IN TRUNCATE TABELLA " + key, e);
					}

				}
			}
		}

	}
	
	@Override
	protected void postElaborazioneAction(String file, List<String> fileDaElaborare, String cartellaFiles){
		postElaborazione(file, fileDaElaborare, cartellaFiles);
	}

}
