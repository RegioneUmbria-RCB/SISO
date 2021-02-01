package it.webred.rulengine.brick.loadDwh.load.tributi.v2;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.tributi.TributiTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.tributi.v3.EnvSitTIciRiduzioni;
import it.webred.rulengine.brick.loadDwh.load.tributi.v3.EnvSitTIciSogg;
import it.webred.rulengine.brick.loadDwh.load.tributi.v3.EnvSitTIciUltSogg;
import it.webred.rulengine.brick.loadDwh.load.tributi.v3.EnvSitTIciVersamenti;
import it.webred.rulengine.brick.loadDwh.load.tributi.v3.EnvSitTIciVia;
import it.webred.rulengine.brick.loadDwh.load.tributi.v3.EnvSitTTarOggetto;
import it.webred.rulengine.brick.loadDwh.load.tributi.v3.EnvSitTTarRiduzioni;
import it.webred.rulengine.brick.loadDwh.load.tributi.v3.EnvSitTTarSogg;
import it.webred.rulengine.brick.loadDwh.load.tributi.v3.EnvSitTTarUltSogg;
import it.webred.rulengine.brick.loadDwh.load.tributi.v3.EnvSitTTarVia;


public class Env<T extends TributiTipoRecordEnv> extends it.webred.rulengine.brick.loadDwh.load.tributi.v3.Env<T> {

	private String SQL_RE_TRIBUTI_TRTRIBTARSUSOGG = getProperty("sql.RE_TRIBUTI_TRTRIBTARSUSOGG");
	private String SQL_RE_TRIBUTI_TRTRIBTARSUDICH = getProperty("sql.RE_TRIBUTI_TRTRIBTARSUDICH");
	private String SQL_RE_TRIBUTI_TRTRIBTARSUSOGGULT = getProperty("sql.RE_TRIBUTI_TRTRIBTARSUSOGGULT");
	private String SQL_RE_TRIBUTI_TRTRIBTARSUVIA = getProperty("sql.RE_TRIBUTI_TRTRIBTARSUVIA");
	private String SQL_RE_TRIBUTI_TRTRIBTARRIDUZ = getProperty("sql.RE_TRIBUTI_TRTRIBTARRIDUZ");
	private String SQL_RE_TRIBUTI_TRTRIBICISOGG = getProperty("sql.RE_TRIBUTI_TRTRIBICISOGG");
	private String SQL_RE_TRIBUTI_TRTRIBICIDICH = getProperty("sql.RE_TRIBUTI_TRTRIBICIDICH");
	private String SQL_RE_TRIBUTI_TRTRIBICISOGGULT = getProperty("sql.RE_TRIBUTI_TRTRIBICISOGGULT");
	private String SQL_RE_TRIBUTI_TRTRIBICIVIA = getProperty("sql.RE_TRIBUTI_TRTRIBICIVIA");
	private String SQL_RE_TRIBUTI_TRTRIBICIRIDUZ = getProperty("sql.RE_TRIBUTI_TRTRIBICIRIDUZ");
	private String SQL_RE_TRIBUTI_TRTRIBVICI = getProperty("sql.RE_TRIBUTI_TRTRIBVICI");	

	private EnvSitTTarSogg envSitTTarSogg = new EnvSitTTarSogg(envImport.RE_TRIBUTI_TRTRIBTARSUSOGG, envImport.getTestata().getProvenienza(), new String[] {"ID_ORIG_SOGG"});
	private EnvSitTTarOggetto envSitTTarOggetto = new EnvSitTTarOggetto(envImport.RE_TRIBUTI_TRTRIBTARSUDICH, envImport.getTestata().getProvenienza(), new String[] {"ID_ORIG_OGG_RSU"});
	private EnvSitTTarUltSogg envSitTTarUltSogg = new EnvSitTTarUltSogg(envImport.RE_TRIBUTI_TRTRIBTARSUSOGGULT, envImport.getTestata().getProvenienza(), new String[] {});
	private EnvSitTTarVia envSitTTarVia = new EnvSitTTarVia(envImport.RE_TRIBUTI_TRTRIBTARSUVIA, envImport.getTestata().getProvenienza(), new String[] {"ID_ORIG_VIA"});
	private EnvSitTTarRiduzioni envSitTTarRiduzioni = new EnvSitTTarRiduzioni(envImport.RE_TRIBUTI_TRTRIBTARRIDUZ, envImport.getTestata().getProvenienza(), new String[] {});
	private EnvSitTIciSogg envSitTIciSogg = new EnvSitTIciSogg(envImport.RE_TRIBUTI_TRTRIBICISOGG, envImport.getTestata().getProvenienza(), new String[] {"ID_ORIG_SOGG"});	
	private EnvSitTIciOggetto envSitTIciOggetto = new EnvSitTIciOggetto(envImport.RE_TRIBUTI_TRTRIBICIDICH, envImport.getTestata().getProvenienza(), new String[] {"ID_ORIG_OGG_ICI"});
	private EnvSitTIciUltSogg envSitTIciUltSogg = new EnvSitTIciUltSogg(envImport.RE_TRIBUTI_TRTRIBICISOGGULT, envImport.getTestata().getProvenienza(), new String[] {});
	private EnvSitTIciVia envSitTIciVia = new EnvSitTIciVia(envImport.RE_TRIBUTI_TRTRIBICIVIA, envImport.getTestata().getProvenienza(), new String[] {"ID_ORIG_VIA"});
	private EnvSitTIciRiduzioni envSitTIciRiduzioni = new EnvSitTIciRiduzioni(envImport.RE_TRIBUTI_TRTRIBICIRIDUZ, envImport.getTestata().getProvenienza(), new String[] {});
	private EnvSitTIciVersamenti envSitTIciVersamenti = new EnvSitTIciVersamenti(envImport.RE_TRIBUTI_TRTRIBVICI, envImport.getTestata().getProvenienza(), new String[] {"ID_ORIG_VICI"});
	
	
	public Env(T envImport) {
		super(envImport);
		
		//TODO
	}
	
	
	public String getSQL_RE_TRIBUTI_TRTRIBTARSUSOGG() {
		return SQL_RE_TRIBUTI_TRTRIBTARSUSOGG;
	}

	public void setSQL_RE_TRIBUTI_TRTRIBTARSUSOGG(
			String sql_re_tributi_trtribtarsusogg) {
		SQL_RE_TRIBUTI_TRTRIBTARSUSOGG = sql_re_tributi_trtribtarsusogg;
	}

	public String getSQL_RE_TRIBUTI_TRTRIBTARSUDICH() {
		return SQL_RE_TRIBUTI_TRTRIBTARSUDICH;
	}

	public void setSQL_RE_TRIBUTI_TRTRIBTARSUDICH(
			String sql_re_tributi_trtribtarsudich) {
		SQL_RE_TRIBUTI_TRTRIBTARSUDICH = sql_re_tributi_trtribtarsudich;
	}

	public String getSQL_RE_TRIBUTI_TRTRIBTARSUSOGGULT() {
		return SQL_RE_TRIBUTI_TRTRIBTARSUSOGGULT;
	}

	public void setSQL_RE_TRIBUTI_TRTRIBTARSUSOGGULT(
			String sql_re_tributi_trtribtarsusoggult) {
		SQL_RE_TRIBUTI_TRTRIBTARSUSOGGULT = sql_re_tributi_trtribtarsusoggult;
	}

	public String getSQL_RE_TRIBUTI_TRTRIBTARSUVIA() {
		return SQL_RE_TRIBUTI_TRTRIBTARSUVIA;
	}

	public void setSQL_RE_TRIBUTI_TRTRIBTARSUVIA(
			String sql_re_tributi_trtribtarsuvia) {
		SQL_RE_TRIBUTI_TRTRIBTARSUVIA = sql_re_tributi_trtribtarsuvia;
	}

	public String getSQL_RE_TRIBUTI_TRTRIBTARRIDUZ() {
		return SQL_RE_TRIBUTI_TRTRIBTARRIDUZ;
	}

	public void setSQL_RE_TRIBUTI_TRTRIBTARRIDUZ(
			String sql_re_tributi_trtribtarriduz) {
		SQL_RE_TRIBUTI_TRTRIBTARRIDUZ = sql_re_tributi_trtribtarriduz;
	}

	public String getSQL_RE_TRIBUTI_TRTRIBICISOGG() {
		return SQL_RE_TRIBUTI_TRTRIBICISOGG;
	}

	public void setSQL_RE_TRIBUTI_TRTRIBICISOGG(String sql_re_tributi_trtribicisogg) {
		SQL_RE_TRIBUTI_TRTRIBICISOGG = sql_re_tributi_trtribicisogg;
	}

	public String getSQL_RE_TRIBUTI_TRTRIBICIDICH() {
		return SQL_RE_TRIBUTI_TRTRIBICIDICH;
	}

	public void setSQL_RE_TRIBUTI_TRTRIBICIDICH(String sql_re_tributi_trtribicidich) {
		SQL_RE_TRIBUTI_TRTRIBICIDICH = sql_re_tributi_trtribicidich;
	}

	public String getSQL_RE_TRIBUTI_TRTRIBICISOGGULT() {
		return SQL_RE_TRIBUTI_TRTRIBICISOGGULT;
	}

	public void setSQL_RE_TRIBUTI_TRTRIBICISOGGULT(
			String sql_re_tributi_trtribicisoggult) {
		SQL_RE_TRIBUTI_TRTRIBICISOGGULT = sql_re_tributi_trtribicisoggult;
	}

	public String getSQL_RE_TRIBUTI_TRTRIBICIVIA() {
		return SQL_RE_TRIBUTI_TRTRIBICIVIA;
	}

	public void setSQL_RE_TRIBUTI_TRTRIBICIVIA(String sql_re_tributi_trtribicivia) {
		SQL_RE_TRIBUTI_TRTRIBICIVIA = sql_re_tributi_trtribicivia;
	}

	public String getSQL_RE_TRIBUTI_TRTRIBICIRIDUZ() {
		return SQL_RE_TRIBUTI_TRTRIBICIRIDUZ;
	}

	public void setSQL_RE_TRIBUTI_TRTRIBICIRIDUZ(
			String sql_re_tributi_trtribiciriduz) {
		SQL_RE_TRIBUTI_TRTRIBICIRIDUZ = sql_re_tributi_trtribiciriduz;
	}

	public String getSQL_RE_TRIBUTI_TRTRIBVICI() {
		return SQL_RE_TRIBUTI_TRTRIBVICI;
	}

	public void setSQL_RE_TRIBUTI_TRTRIBVICI(String sql_re_tributi_trtribvici) {
		SQL_RE_TRIBUTI_TRTRIBVICI = sql_re_tributi_trtribvici;
	}

	public EnvSitTTarSogg getEnvSitTTarSogg() {
		return envSitTTarSogg;
	}

	public void setEnvSitTTarSogg(EnvSitTTarSogg envSitTTarSogg) {
		this.envSitTTarSogg = envSitTTarSogg;
	}

	public EnvSitTTarOggetto getEnvSitTTarOggetto() {
		return envSitTTarOggetto;
	}

	public void setEnvSitTTarOggetto(EnvSitTTarOggetto envSitTTarOggetto) {
		this.envSitTTarOggetto = envSitTTarOggetto;
	}

	public EnvSitTTarUltSogg getEnvSitTTarUltSogg() {
		return envSitTTarUltSogg;
	}

	public void setEnvSitTTarUltSogg(EnvSitTTarUltSogg envSitTTarUltSogg) {
		this.envSitTTarUltSogg = envSitTTarUltSogg;
	}

	public EnvSitTTarVia getEnvSitTTarVia() {
		return envSitTTarVia;
	}

	public void setEnvSitTTarVia(EnvSitTTarVia envSitTTarVia) {
		this.envSitTTarVia = envSitTTarVia;
	}

	public EnvSitTTarRiduzioni getEnvSitTTarRiduzioni() {
		return envSitTTarRiduzioni;
	}

	public void setEnvSitTTarRiduzioni(EnvSitTTarRiduzioni envSitTTarRiduzioni) {
		this.envSitTTarRiduzioni = envSitTTarRiduzioni;
	}

	public EnvSitTIciSogg getEnvSitTIciSogg() {
		return envSitTIciSogg;
	}

	public void setEnvSitTIciSogg(EnvSitTIciSogg envSitTIciSogg) {
		this.envSitTIciSogg = envSitTIciSogg;
	}

	public EnvSitTIciOggetto getEnvSitTIciOggetto() {
		return envSitTIciOggetto;
	}

	public void setEnvSitTIciOggetto(EnvSitTIciOggetto envSitTIciOggetto) {
		this.envSitTIciOggetto = envSitTIciOggetto;
	}

	public EnvSitTIciUltSogg getEnvSitTIciUltSogg() {
		return envSitTIciUltSogg;
	}

	public void setEnvSitTIciUltSogg(EnvSitTIciUltSogg envSitTIciUltSogg) {
		this.envSitTIciUltSogg = envSitTIciUltSogg;
	}

	public EnvSitTIciVia getEnvSitTIciVia() {
		return envSitTIciVia;
	}

	public void setEnvSitTIciVia(EnvSitTIciVia envSitTIciVia) {
		this.envSitTIciVia = envSitTIciVia;
	}

	public EnvSitTIciRiduzioni getEnvSitTIciRiduzioni() {
		return envSitTIciRiduzioni;
	}

	public void setEnvSitTIciRiduzioni(EnvSitTIciRiduzioni envSitTIciRiduzioni) {
		this.envSitTIciRiduzioni = envSitTIciRiduzioni;
	}

	public EnvSitTIciVersamenti getEnvSitTIciVersamenti() {
		return envSitTIciVersamenti;
	}

	public void setEnvSitTIciVersamenti(EnvSitTIciVersamenti envSitTIciVersamenti) {
		this.envSitTIciVersamenti = envSitTIciVersamenti;
	}

	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecordSpec() {
		return super.getTabelleAndTipiRecordSpec();
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		return super.getTabelleAndChiavi();
	}
	
}
