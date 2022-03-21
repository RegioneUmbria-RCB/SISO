package it.webred.ct.rulengine.web.bean.cataloghi;

import it.webred.ct.data.access.basic.pgt.dto.CataloghiDataIn;
import it.webred.ct.data.access.basic.pgt.dto.RicercaPgtDTO;
import it.webred.ct.data.access.basic.pgt.dto.VisLayerSqlDTO;
import it.webred.ct.data.model.pgt.PgtSqlLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayerPK;
import it.webred.ct.data.model.pgt.PgtSqlVisLayer;
import it.webred.ct.data.model.pgt.PgtSqlVisLayerPK;
import it.webred.ct.rulengine.controller.model.RConnection;
import it.webred.ct.rulengine.web.bean.util.UploadBean;
import it.webred.ct.rulengine.web.bean.util.ZipUtility;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import oracle.spatial.util.SampleShapefileToJGeomFeature;

import org.apache.log4j.Logger;

public class ShapeBean extends CataloghiBaseBean {

	private static Logger logger = Logger.getLogger(ShapeBean.class.getName());

	private String enteSelezionato;
	private List<SelectItem> listaConnessioni = new ArrayList<SelectItem>();
	private String conn;
	private String table;
	private String descrizione;
	private String tipoLayer;
	private String file;
	private String idColumn;
	private String sdoColumn;
	private String srid;
	private String boundX;
	private String boundY;
	private String sqlDeco;
	private String sqlLayer;
	private String opacity;
	private String plEncode;
	private String plDecode;
	private String plDecodeDescr;
	private String shapeType;
	private boolean flDownload;
	private boolean flPublish;
	private boolean flStandard;
	private boolean flHideInfo;
	private String idLayerSel;
	private String idLayerStandard;
	private boolean modalityUpdate;
	private String nomeColonnaCodice;
	private String nomeColonnaDescr;
	private String nomeColonnaInfo;
	private String descrColonnaInfo;
	private boolean flConvDaFile;
	
	private List<VisLayerSqlDTO> listaSqlVis = new ArrayList<VisLayerSqlDTO>();;
	PgtSqlLayer layer;

	private File fAllegato;
	private String nomeFileZip;

	@PostConstruct
	public void init() {
		if (listaConnessioni.size() == 0)
			doCaricaConnessioni();
		
	/*	if(listaSqlVis.size()==0)
			doCaricaListaSqlVisibilita();*/
	}

	
	public void initPanel() {
		if (listaConnessioni.size() == 0)
			doCaricaConnessioni();
		
		layer = new PgtSqlLayer();
		if (idLayerSel != null && !idLayerSel.equals("")) {
			modalityUpdate = true;
			// "seleziono" il layer sul cataloghiBean
			CataloghiBean catBean = (CataloghiBean) getBeanReference("cataloghiBean");
			catBean.setIdLayerSel(idLayerSel);
			catBean.setIdLayerStandard(idLayerStandard);
			catBean.setColonnaTemaSelezionata("");
			catBean.setColonnaTemaDescrSelezionata("");
			// recupero dati layer
			RicercaPgtDTO rp = new RicercaPgtDTO();
			rp.setEnteId(enteSelezionato);
			rp.setId(new Long(idLayerSel).longValue());
			rp.setStandard(idLayerStandard);
			layer = pgtService.getLayerByPK(rp);
			// imposto il layer selezionato su cataloghi Beam
			catBean.setLayerSelezionato(layer.getNameTable());
			catBean.doCaricaColonne();
			//
			table = layer.getNameTable();
			descrizione = layer.getDesLayer();
			nomeColonnaCodice = layer.getNameColTema();
			nomeColonnaDescr = layer.getNameColTemaDescr();
			tipoLayer = layer.getTipoLayer();
			idColumn = layer.getNameColId();
			sdoColumn = layer.getNameColShape();
			shapeType = layer.getShapeType();
			sqlDeco = layer.getSqlDeco();
			sqlLayer = layer.getSqlLayer();
			plEncode = layer.getPlencode();
			plDecode = layer.getPldecode();
			opacity = layer.getOpacity();
			nomeColonnaInfo = layer.getNameColInfo();
			descrColonnaInfo = layer.getDescrColInfo();
			plDecodeDescr = layer.getPldecodeDescr();
			if (layer.getFlgDownload() != null
					&& layer.getFlgDownload().equals("Y"))
				flDownload = true;
			else
				flDownload = false;
			if (layer.getFlgPublish() != null
					&& layer.getFlgPublish().equals("Y"))
				flPublish = true;
			else
				flPublish = false;
			if (layer.getId().getStandard() != null
					&& layer.getId().getStandard().equals("Y"))
				flStandard = true;
			else
				flStandard = false;
			if (layer.getFlgHideInfo() != null
					&& layer.getFlgHideInfo().equals("Y"))
				flHideInfo = true;
			else
				flHideInfo = false;
			
			this.doCaricaListaSqlVisibilita(idLayerSel,idLayerStandard);
			
		} else {
			modalityUpdate = false;

			for (SelectItem item : listaConnessioni) {
				if (item.getLabel().equalsIgnoreCase("DWH_" + enteSelezionato))
					conn = (String) item.getValue();
			}
			logger.debug("conn :" + conn);
			table = "";
			descrizione = "";
			tipoLayer = "";
			idColumn = "SE_ROW_ID";
			sdoColumn = "SHAPE";
			srid = "";
			boundX = "";
			boundY = "";
			sqlDeco = "SELECT #IDLAYER AS ID_LAYER,ID,codut || ' - ' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = #IDLAYER";
			sqlLayer = "";
			plEncode = "LAYER_TOOLS.SETCODEPRGC";
			plDecode = "LAYER_TOOLS.GETCODEPRGC";
			plDecodeDescr = "LAYER_TOOLS.GETCODEPRGC_DESCR";
			shapeType = "";
			nomeColonnaInfo = "";
			descrColonnaInfo = "";
			flDownload = false;
			flPublish = false;
			flStandard = false;
			flHideInfo = false;
			UploadBean u = (UploadBean) getBeanReference("uploadBean");
			u.setAllegato(null);
			u.setFileName("");
			
			this.doCaricaListaSqlVisibilita(null,null);
		}
		
		flConvDaFile=false;

	}

	public void doCaricaConnessioni() {
		List<RConnection> lista = utilService.getConnessioni();
		listaConnessioni = new ArrayList<SelectItem>();
		for (RConnection conn : lista) {
			int idx = conn.getConnString().indexOf("@");
			String value = conn.getConnString().substring(++idx) + ":"
					+ conn.getUserName() + ":" + conn.getPassword();
			listaConnessioni.add(new SelectItem(value, conn.getName()));
			logger.debug("connessione: " + conn.getName());
		}
	}
	
	

	public void doCaricaListaSqlVisibilita(String idLayer, String standard) {
		PgtSqlLayerPK pk = new PgtSqlLayerPK();
		pk.setId(idLayer!=null && !"".equals(idLayer) ? new Long(idLayer):null);
		pk.setStandard(standard);
		
		CataloghiDataIn dataIn  = new CataloghiDataIn();
		dataIn.setUserId(this.getUsername());
		dataIn.setEnteId(this.getEnteSelezionato());
		dataIn.setPkLayer(pk);
		
		listaSqlVis = pgtService.getListaSqlTemplate(dataIn);
	}
	
	public void doShapeToSdo() {
		if (enteSelezionato == null || enteSelezionato.equals("9999")) {
			super.addErrorMessage("shapetosdo.ente.error", "");
			return;
		}
		if (idLayerSel != null && !idLayerSel.equals("")) {
			try {
				modifica();
			} catch (Exception e) {
				String mes = e.getMessage();
				super.addErrorMessage("cataloghi.info.save.error", mes);
			}
			return;
		}
		if (table == null || table.equals("")) {
			super.addErrorMessage("shapetosdo.table.error", "");
			return;
		}
		
		if(this.flConvDaFile){
		
			// controlla che il nome di tabella specificato non sia già esistente
			ArrayList<String> arg = new ArrayList<String>();
			String[] c = conn.split(":");
			// logger.debug("user: " + c[3]);
			String owner = c[3];
			RicercaPgtDTO rp = new RicercaPgtDTO();
			rp.setEnteId(enteSelezionato);
			rp.setNameTable(table);
			rp.setOwner(owner);
			boolean exists = pgtService.isTableExisting(rp);
			if (exists) {
				super.addErrorMessage("shapetosdo.tableExists.error", "");
				return;
			}
			// presenza file .zip
			UploadBean updBean = (UploadBean) getBeanReference("uploadBean");
			String parameterPath = getRootPathDatiShape();
			updBean.setParameterPath(parameterPath);
			if (updBean.getAllegato() == null) {
				super.addErrorMessage("shapetosdo.filezip.error", "");
				return;
			}
			String msg = "shapetosdo";
			// upload file zip shape e decompressione dello zip
			logger.debug("PATH PER UPLOAD FILE ZIP: " + parameterPath);
			File fUpd = updBean.doUpload();
			String pathFileUnZipped = parameterPath + "temp_unzip/";
			File fUnZipped = new File(pathFileUnZipped);
			fUnZipped.mkdir();
			File percorsoFiles = null;
			try {
				percorsoFiles = ZipUtility.decompress(fUpd, fUnZipped);
			} catch (IOException ioe) {
				logger.error(ioe.getMessage(), ioe);
			}
			// conversione dei files shape ed insert catalogo
			logger.debug("path files decompressi: "
					+ percorsoFiles.getAbsolutePath());
			String[] listaFiles = percorsoFiles.list();
			// pulisco la lista dai file non shape
			List<String> listaFilesShp = new ArrayList<String>();
			logger.debug("lista file to elab.size(): " + listaFiles.length);
			for (int i = 0; i < listaFiles.length; i++) {
				logger.debug("entra nel ciclo for listaFiles.length");
				String nomefile = listaFiles[i];
				if (nomefile.endsWith(".shp") || nomefile.endsWith(".SHP"))
					listaFilesShp.add(nomefile);
			}
			// conversione
			if (listaFilesShp.size() == 1) {
				String nomefile = listaFilesShp.get(0);
				String nomefileNoExt = nomefile.substring(0, nomefile.length()-4); 
			//	String nomefileNoExt = nomefile.replace(".shp", "");
			//	nomefileNoExt = nomefile.replace(".SHP", "");
				conversione(table, descrizione, nomefile, percorsoFiles + "/"
						+ nomefileNoExt);
			} else {
				int j = 1;
				for (String nomefile : listaFilesShp) {
					String nomefileNoExt = nomefile.substring(0, nomefile.length()-4);
					//String nomefileNoExt = nomefile.replace(".shp", "");
					//nomefileNoExt = nomefile.replace(".SHP", "");
					logger.debug("elab file: " + nomefile);
					conversione(table + "_" + j, descrizione + " " + j, nomefile,
							percorsoFiles + "/" + nomefileNoExt);
					j++;
				}
			}
			// cancellazione dei files temporanei utilizzati per caricare lo shape
			cancellaDirTemp(percorsoFiles, fUpd);
			fUnZipped.delete();
		
		}else{
			this.salvaCatalogo(table, descrizione);
		}

		// update cataloghi
		CataloghiBean catBean = (CataloghiBean) getBeanReference("cataloghiBean");
		catBean.doCaricaLayer();
	}

	private void conversione(String tabella, String descr, String nomefile, String percorsofile) {

		logger.info("------------->Percorso file passato a parametro: " + percorsofile);
		
		try {

			CataloghiDataIn dataIn = new CataloghiDataIn();
			dataIn.setEnteId(enteSelezionato);
			dataIn.setTable(tabella.toUpperCase());

			/*
			 * Explanation of usage parameters: è -h: Host machine with existing
			 * Oracle database è -p: Host machine's port (e.g. 1521) è -s: Host
			 * machine's SID è -u: Database user è -d: Database user's password
			 * è -t: Table name for the converted Shapefile è -f: File name of
			 * an input Shapefile (without extension) è -i: Column name for
			 * unique numeric ID; if required è -r: Valid Oracle SRID for
			 * coordinate system; use 0 if unknown è -g: Preferred SDO_GEOMETRY
			 * column name è -x: Bounds for the X dimension; use -180,180 if
			 * unknown è -y: Bounds for the Y dimension; use -90,90 if unknown 
			 * -o: Load tolerance fields (x and y) in metadata, if not
			 * specified, tolerance fields are 0.05 è -a: Append Shapefile data
			 * to an existing table è -n: Start ID for column specified in -i
			 * parameter è -c: Commit interval. Default, commits every 1000
			 * conversions and at the end è -v: Println interval. Default,
			 * displays every 10 conversions
			 */

			ArrayList<String> arg = new ArrayList<String>();
			String[] c = conn.split(":");
			arg.add("-h");
			arg.add(c[0]);
			arg.add("-p");
			arg.add(c[1]);
			arg.add("-s");
			arg.add(c[2]);
			arg.add("-u");
			arg.add(c[3]);
			arg.add("-d");
			arg.add(c[4]);
			arg.add("-t");
			arg.add(tabella);
			arg.add("-f");
			arg.add(percorsofile);

			if (idColumn != null && !"".equals(idColumn)) {
				arg.add("-i");
				arg.add(idColumn);
			}
			if (sdoColumn != null && !"".equals(sdoColumn)) {
				arg.add("-g");
				arg.add(sdoColumn);
			}
			if (srid != null && !"".equals(srid)) {
				arg.add("-r");
				arg.add(srid);
			}
			if (boundX != null && !"".equals(boundX)) {
				arg.add("-x");
				arg.add(boundX);
			}
			if (boundY != null && !"".equals(boundY)) {
				arg.add("-y");
				arg.add(boundY);
			}

			logger.debug("eseguo conversione SampleShapefileToJGeomFeature " + tabella);
			String[] sa = (String[]) arg.toArray(new String[0]);
			SampleShapefileToJGeomFeature.main(sa);
			logger.debug("OK conversione SampleShapefileToJGeomFeature "
					+ tabella);

			layer = pgtService.getLayerByTable(dataIn);
			boolean exist = true;
			if (layer == null) {
				exist = false;
				layer = new PgtSqlLayer();
				PgtSqlLayerPK layerPK = new PgtSqlLayerPK();
				layerPK.setId(pgtService.getMaxIdLayer(dataIn) + 1);
				if (flStandard)
					layerPK.setStandard("Y");
				else
					layerPK.setStandard("N");
				layer.setId(layerPK);
			}
			if (descrizione == null || descrizione.equals(""))
				descrizione = new String(tabella.toUpperCase());
			layer.setDesLayer(descrizione);
			if (tipoLayer == null || tipoLayer.equals(""))
				tipoLayer = "GENERICO";
			layer.setTipoLayer(tipoLayer);
			layer.setNameTable(tabella.toUpperCase());
			layer.setNameColShape(sdoColumn);
			layer.setNameColId(idColumn);
			layer.setNomeFile(nomefile);
			sqlDeco = sqlDeco.replaceAll("#IDLAYER", layer.getId().getId()
					.toString());
			layer.setSqlDeco(sqlDeco);
			layer.setSqlLayer(sqlLayer);
			layer.setPlencode(plEncode);
			layer.setPldecode(plDecode);
			layer.setPldecodeDescr(plDecodeDescr);
			layer.setShapeType(shapeType);
			layer.setOpacity(opacity);
			layer.setNameColInfo(nomeColonnaInfo);
			layer.setDescrColInfo(descrColonnaInfo);
			if (flDownload)
				layer.setFlgDownload("Y");
			else
				layer.setFlgDownload("N");
			if (flPublish)
				layer.setFlgPublish("Y");
			else
				layer.setFlgPublish("N");
			if (flHideInfo)
				layer.setFlgHideInfo("Y");
			else
				layer.setFlgHideInfo("N");

			dataIn.setPgtSqlLayer(layer);

			if (exist)
				pgtService.mergeLayer(dataIn);
			else
				pgtService.persistLayer(dataIn);
			
			/*Salva la lista di sql per accedere da app.esterne*/
			this.salvaListaSqlVisibilita();

			super.addInfoMessage("shapetosdo");

			// creazione indice spaziale

			String sql = "{call sdo_diogene.crea_idx_spatial(?,?,?,?)}";
			RicercaPgtDTO rp = new RicercaPgtDTO();
			rp.setEnteId(enteSelezionato);
			rp.setStatementSql(sql);
			rp.setNameTable(tabella);
			rp.setSdoColumn(sdoColumn);
			rp.setCreateIndexSDO(true);
			pgtService.executeStatement(rp);

		} catch (Throwable t) {
			super.addErrorMessage("shapetosdo.error", t.getMessage());
			logger.error("Errore conversione file shape " + nomefile + " --> "
					+ tabella, t);
		}
	}
	
	private void salvaCatalogo(String tabella, String descr) {

		
		
		try {

			CataloghiDataIn dataIn = new CataloghiDataIn();
			dataIn.setEnteId(enteSelezionato);
			dataIn.setTable(tabella.toUpperCase());

			layer = pgtService.getLayerByTable(dataIn);
			boolean exist = true;
			if (layer == null) {
				exist = false;
				layer = new PgtSqlLayer();
				PgtSqlLayerPK layerPK = new PgtSqlLayerPK();
				layerPK.setId(pgtService.getMaxIdLayer(dataIn) + 1);
				if (flStandard)
					layerPK.setStandard("Y");
				else
					layerPK.setStandard("N");
				layer.setId(layerPK);
			}
			if (descrizione == null || descrizione.equals(""))
				descrizione = new String(tabella.toUpperCase());
			layer.setDesLayer(descrizione);
			if (tipoLayer == null || tipoLayer.equals(""))
				tipoLayer = "GENERICO";
			layer.setTipoLayer(tipoLayer);
			layer.setNameTable(tabella.toUpperCase());
			layer.setNameColShape(sdoColumn);
			layer.setNameColId(idColumn);
			sqlDeco = sqlDeco.replaceAll("#IDLAYER", layer.getId().getId()
					.toString());
			layer.setSqlDeco(sqlDeco);
			layer.setSqlLayer(sqlLayer);
			layer.setPlencode(plEncode);
			layer.setPldecode(plDecode);
			layer.setPldecodeDescr(plDecodeDescr);
			layer.setShapeType(shapeType);
			layer.setOpacity(opacity);
			layer.setNameColInfo(nomeColonnaInfo);
			layer.setDescrColInfo(descrColonnaInfo);
			layer.setDataAcquisizione(getCurrentDate());
			if (flDownload)
				layer.setFlgDownload("Y");
			else
				layer.setFlgDownload("N");
			if (flPublish)
				layer.setFlgPublish("Y");
			else
				layer.setFlgPublish("N");
			if (flHideInfo)
				layer.setFlgHideInfo("Y");
			else
				layer.setFlgHideInfo("N");

			dataIn.setPgtSqlLayer(layer);

			if (exist)
				pgtService.mergeLayer(dataIn);
			else
				pgtService.persistLayer(dataIn);
			
			/*Salva la lista di sql per accedere da app.esterne*/
			this.salvaListaSqlVisibilita();
			
		
			super.addInfoMessage("shapetosdo");
		

		} catch (Throwable t) {
			super.addErrorMessage("shapetosdo.error", t.getMessage());
			logger.error("Errore creazione catalogo " + tabella, t);
		}
	}
	
	private String salvaListaSqlVisibilita() throws Exception{
		List<PgtSqlVisLayer> lista = new ArrayList<PgtSqlVisLayer>();
		boolean verificaOk=true;
		String message="";
		for(VisLayerSqlDTO vis : this.listaSqlVis){
			//Aggiorno
			PgtSqlVisLayer v = new PgtSqlVisLayer();
			
			PgtSqlVisLayerPK vId = new PgtSqlVisLayerPK();
			vId.setIdLayer(layer.getId().getId());
			vId.setStndLayer(layer.getId().getStandard());
			vId.setIdTmpl(vis.getIdTemplate());
			
			v.setId(vId);
			v.setModInterrogazione(vis.getModalita());
			v.setVisualizza(vis.getVisualizza() ? "Y" : "N");
			
			String sqlLayer = vis.getSqlLayer();
				
			if( "C".equals(vis.getModalita())||
			   ("A".equals(vis.getModalita()) && sqlLayer!=null && !"".equals(sqlLayer.trim()) && !(sqlLayer.trim()).equals(vis.getSqlTemplate()))){
				//Va bene, può essere salvata
				
				if("C".equals(vis.getModalita()))
					sqlLayer=null;
				
				v.setSqlLayer(sqlLayer);
				
				
				}else{
					verificaOk=false;
					message="\n è stata selezionata la modalità di interrogazione alfanumerica per l'applicazione "+vis.getApplicazione()+", inserire la query nell'apposita form.";
				}
			
			lista.add(v);
		}
		
		if(verificaOk){
			CataloghiDataIn dataIn = new CataloghiDataIn();
			dataIn.setEnteId(this.getEnteSelezionato());
			dataIn.setUserId(this.getUsername());
			dataIn.setListPgtSqlVisLayer(lista);
			pgtService.salvaSqlVisLayer(dataIn);
		}else{
		    throw new Exception(message);
		}
		
		return message;
	}
	
	

	private void cancellaDirTemp(File dirFilesUnZipped, File zipTemp) {
		if (zipTemp != null) {
			logger.debug("files to delete: " + zipTemp.getAbsolutePath());
			if (zipTemp.exists())
				zipTemp.delete();
		}
		logger.debug("dir to delete: " + dirFilesUnZipped.getAbsolutePath());
		if (dirFilesUnZipped == null)
			return;
		if (dirFilesUnZipped.exists()) {
			File[] listaFiles = dirFilesUnZipped.listFiles();
			for (int i = 0; i < listaFiles.length; i++) {
				File f = listaFiles[i];
				f.delete();
			}
			if (dirFilesUnZipped.exists())
				dirFilesUnZipped.delete();
		}

	}

	private void modifica() throws Exception {
		
		CataloghiDataIn dataIn = new CataloghiDataIn();
		dataIn.setEnteId(enteSelezionato);
		if (descrizione == null || descrizione.equals(""))
			descrizione = new String(table);
		layer.setDesLayer(descrizione);
		if (tipoLayer == null || tipoLayer.equals(""))
			tipoLayer = "GENERICO";
		layer.setTipoLayer(tipoLayer);
		layer.setNameTable(table);
		layer.setNameColShape(sdoColumn);
		layer.setNameColId(idColumn);
		sqlDeco = sqlDeco.replaceAll("#IDLAYER", layer.getId().getId()
				.toString());
		layer.setSqlDeco(sqlDeco);
		layer.setSqlLayer(sqlLayer);
		layer.setPlencode(plEncode);
		layer.setPldecode(plDecode);
		layer.setPldecodeDescr(plDecodeDescr);
		layer.setShapeType(shapeType);
		layer.setOpacity(opacity);
		layer.setNameColInfo(nomeColonnaInfo);
		layer.setDescrColInfo(descrColonnaInfo);
		if (flDownload)
			layer.setFlgDownload("Y");
		else
			layer.setFlgDownload("N");
		if (flPublish)
			layer.setFlgPublish("Y");
		else
			layer.setFlgPublish("N");
		if (flHideInfo)
			layer.setFlgHideInfo("Y");
		else
			layer.setFlgHideInfo("N");

		dataIn.setPgtSqlLayer(layer);
		pgtService.mergeLayer(dataIn);
		
		/*Salva la lista di sql per accedere da app.esterne*/
		this.salvaListaSqlVisibilita();
		
		// update lista cataloghi
		CataloghiBean catBean = (CataloghiBean) getBeanReference("cataloghiBean");
		catBean.doCaricaLayer();
		
		super.addInfoMessage("cataloghi.info.save");
		
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getIdColumn() {
		return idColumn;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public String getSdoColumn() {
		return sdoColumn;
	}

	public void setSdoColumn(String sdoColumn) {
		this.sdoColumn = sdoColumn;
	}

	public String getSrid() {
		return srid;
	}

	public void setSrid(String srid) {
		this.srid = srid;
	}

	public String getBoundX() {
		return boundX;
	}

	public void setBoundX(String boundX) {
		this.boundX = boundX;
	}

	public String getBoundY() {
		return boundY;
	}

	public void setBoundY(String boundY) {
		this.boundY = boundY;
	}

	public List<SelectItem> getListaConnessioni() {
		return listaConnessioni;
	}

	public void setListaConnessioni(List<SelectItem> listaConnessioni) {
		this.listaConnessioni = listaConnessioni;
	}

	public String getConn() {
		return conn;
	}

	public void setConn(String conn) {
		this.conn = conn;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getEnteSelezionato() {
		return enteSelezionato;
	}

	public void setEnteSelezionato(String enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}

	public String getSqlDeco() {
		return sqlDeco;
	}

	public void setSqlDeco(String sqlDeco) {
		this.sqlDeco = sqlDeco;
	}

	public String getPlEncode() {
		return plEncode;
	}

	public void setPlEncode(String plEncode) {
		this.plEncode = plEncode;
	}

	public String getPlDecode() {
		return plDecode;
	}

	public void setPlDecode(String plDecode) {
		this.plDecode = plDecode;
	}

	public String getShapeType() {
		return shapeType;
	}

	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}

	public String getPlDecodeDescr() {
		return plDecodeDescr;
	}

	public void setPlDecodeDescr(String plDecodeDescr) {
		this.plDecodeDescr = plDecodeDescr;
	}

	public boolean isFlDownload() {
		return flDownload;
	}

	public void setFlDownload(boolean flDownload) {
		this.flDownload = flDownload;
	}

	public boolean isFlPublish() {
		return flPublish;
	}

	public void setFlPublish(boolean flPublish) {
		this.flPublish = flPublish;
	}

	public String getIdLayerSel() {
		return idLayerSel;
	}

	public void setIdLayerSel(String idLayerSel) {
		this.idLayerSel = idLayerSel;
	}

	public String getIdLayerStandard() {
		return idLayerStandard;
	}

	public void setIdLayerStandard(String idLayerStandard) {
		this.idLayerStandard = idLayerStandard;
	}

	public boolean isModalityUpdate() {
		return modalityUpdate;
	}

	public void setModalityUpdate(boolean modalityUpdate) {
		this.modalityUpdate = modalityUpdate;
	}

	public String getSqlLayer() {
		return sqlLayer;
	}

	public void setSqlLayer(String sqlLayer) {
		this.sqlLayer = sqlLayer;
	}

	public boolean isFlStandard() {
		return flStandard;
	}

	public void setFlStandard(boolean flStandard) {
		this.flStandard = flStandard;
	}

	public PgtSqlLayer getLayer() {
		return layer;
	}

	public void setLayer(PgtSqlLayer layer) {
		this.layer = layer;
	}

	public String getTipoLayer() {
		return tipoLayer;
	}

	public void setTipoLayer(String tipoLayer) {
		this.tipoLayer = tipoLayer;
	}

	public String getNomeColonnaCodice() {
		return nomeColonnaCodice;
	}

	public void setNomeColonnaCodice(String nomeColonnaCodice) {
		this.nomeColonnaCodice = nomeColonnaCodice;
	}

	public String getNomeColonnaDescr() {
		return nomeColonnaDescr;
	}

	public void setNomeColonnaDescr(String nomeColonnaDescr) {
		this.nomeColonnaDescr = nomeColonnaDescr;
	}

	public File getfAllegato() {
		return fAllegato;
	}

	public void setfAllegato(File fAllegato) {
		this.fAllegato = fAllegato;
	}

	public String getNomeFileZip() {
		return nomeFileZip;
	}

	public void setNomeFileZip(String nomeFileZip) {
		this.nomeFileZip = nomeFileZip;
	}

	public String getOpacity() {
		return opacity;
	}

	public void setOpacity(String opacity) {
		this.opacity = opacity;
	}

	public String getNomeColonnaInfo() {
		return nomeColonnaInfo;
	}

	public void setNomeColonnaInfo(String nomeColonnaInfo) {
		this.nomeColonnaInfo = nomeColonnaInfo;
	}

	public String getDescrColonnaInfo() {
		return descrColonnaInfo;
	}

	public void setDescrColonnaInfo(String descrColonnaInfo) {
		this.descrColonnaInfo = descrColonnaInfo;
	}

	public boolean isFlHideInfo() {
		return flHideInfo;
	}

	public void setFlHideInfo(boolean flHideInfo) {
		this.flHideInfo = flHideInfo;
	}

	public boolean isFlConvDaFile() {
		return flConvDaFile;
	}

	public void setFlConvDaFile(boolean flConvDaFile) {
		this.flConvDaFile = flConvDaFile;
	}


	public List<VisLayerSqlDTO> getListaSqlVis() {
		return listaSqlVis;
	}


	public void setListaSqlVis(List<VisLayerSqlDTO> listaSqlVis) {
		this.listaSqlVis = listaSqlVis;
	}

	

}
