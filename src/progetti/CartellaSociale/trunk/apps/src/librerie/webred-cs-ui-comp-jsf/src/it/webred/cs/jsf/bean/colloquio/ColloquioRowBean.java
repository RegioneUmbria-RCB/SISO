package it.webred.cs.jsf.bean.colloquio;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsDColloquioBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class ColloquioRowBean extends CsUiCompBaseBean {

	private static final long serialVersionUID = 1L;
	private CsDColloquioBASIC collBASIC;
	private AmAnagrafica opAnagrafica;
	private Long diarioId;
	
	private String nome;
	private String cognome;
	private boolean riservato;
	private boolean abilitato4riservato;
	private String campoTestoRid;
	private String descrizioneTipoColloquio;
	private String descrizioneDiarioDove;
	private String descrizioneDiarioConChi;
	private String diarioConChiAltro;
	private Date dtModifica;
	private String opModifica;
	private Date dtAmministrativa;
	private String nomeDaChi;
	private String cognomeDaChi;
	private String usernameMod;
	private String usernameOp;

	protected AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote)getCarSocialeEjb ("AccessTableCasoSessionBean");

	protected boolean isResponsabileCaso(){
		CsOOperatoreSettore currentOpSettore = getCurrentOpSettore();

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);

		boolean bIsResponsabileCaso = false;
		if( collBASIC.getCsDDiario() != null ){
			CsACaso caso = collBASIC.getCsDDiario().getCsACaso();
			dto.setObj(caso.getId());
			CsACasoOpeTipoOpe opResponsabile = casoService.findCasoOpeResponsabile(dto);
			
			if( opResponsabile != null ){
				bIsResponsabileCaso &= opResponsabile.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore().getId().equals(currentOpSettore.getCsOOperatore().getId());
				bIsResponsabileCaso &= opResponsabile.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOSettore().getId().equals(currentOpSettore.getCsOSettore().getId());
			}
			else
				bIsResponsabileCaso = false;
		}
		return bIsResponsabileCaso;
	
	}
	
	protected boolean isResponsabileUfficio(){
		String belfiore = getUser().getCurrentEnte();
		CsOOperatoreSettore currentOpSettore = getCurrentOpSettore();
		
		boolean is = currentOpSettore.getAmGroup().contains("CSOCIALE_RESPO_SETTORE_" + belfiore);
		return is;
		
	}
	
	protected boolean isGestoreDiario(){
		CsOOperatoreSettore currentOpSettore = getCurrentOpSettore();
		boolean isInserimento = false;
		boolean isModifica = false;
		
		isInserimento = currentOpSettore.getCsOOperatore().getUsername().equals(collBASIC.getCsDDiario().getUserIns());
		isModifica = currentOpSettore.getCsOOperatore().getUsername().equals(collBASIC.getCsDDiario().getUsrMod());
		
		return isInserimento||isModifica;
	}

	protected String getCampoTestoRid(CsDColloquioBASIC collBasic) {
		String campoTesto = collBasic.getTestoDiario();
		campoTesto = StringEscapeUtils.unescapeHtml(campoTesto);
		campoTesto = campoTesto.replaceAll("\\<[^>]*>",""); //("\\<.*?>", "");
		if( campoTesto != null && campoTesto.length() > 20 ) 
			return campoTesto.substring(0, 20) + "...";
		else return campoTesto;
	}

	public void Initialize( CsDColloquioBASIC collBasic, String opUsername ) {
		collBASIC = collBasic;
		opAnagrafica = getAnagraficaByUsername(opUsername);
		
		campoTestoRid = getCampoTestoRid(collBasic);
		diarioId = collBasic.getDiarioId();
		dtAmministrativa = collBasic.getCsDDiario().getDtAmministrativa();
		cognome = collBasic.getCsDDiario().getCsACaso().getCsASoggetto().getCsAAnagrafica().getCognome();
		nome = collBasic.getCsDDiario().getCsACaso().getCsASoggetto().getCsAAnagrafica().getNome();
		descrizioneTipoColloquio = collBasic.getCsCTipoColloquio()!=null ? collBasic.getCsCTipoColloquio().getDescrizione() : null;
		descrizioneDiarioDove = collBasic.getCsCDiarioDove()!=null ? collBasic.getCsCDiarioDove().getDescrizione() : null;
		diarioConChiAltro = collBasic.getDiarioConChiAltro();
		descrizioneDiarioConChi = collBasic.getCsCDiarioConchi()!=null ? collBasic.getCsCDiarioConchi().getDescrizione() : null;
		Date dtMod = collBasic.getCsDDiario().getDtMod();
		Date dtIns = collBasic.getCsDDiario().getDtIns();
		dtModifica = dtMod != null ? dtMod : dtIns;
		
		String usrMod = collBasic.getCsDDiario().getUsrMod()!=null ?  super.getCognomeNomeUtente(collBasic.getCsDDiario().getUsrMod()) : null;
		opModifica = usrMod!=null ? usrMod : (collBasic.getCsDDiario().getUserIns()!=null ? super.getCognomeNomeUtente(collBasic.getCsDDiario().getUserIns()) : null); 
		
		usernameMod = collBasic.getCsDDiario().getUsrMod();
		usernameOp = usernameMod!=null? usernameMod : collBasic.getCsDDiario().getUserIns();
		if( StringUtils.isEmpty(usernameOp) )
			usernameOp = collBasic.getCsDDiario().getUserIns();		
        
		if(opAnagrafica!=null){
        	nomeDaChi = opAnagrafica.getNome();
        	cognomeDaChi = opAnagrafica.getCognome();
		}
		
		riservato = collBASIC!=null && "1".equals( collBASIC.getRiservato() ) ? true : false;
		abilitato4riservato = true;
		if( collBASIC!=null && collBASIC.getDiarioId() != null ) { 
			if(riservato)
				abilitato4riservato = isResponsabileCaso()||isResponsabileUfficio()||isGestoreDiario();
		}
	}

	public CsDColloquioBASIC getColloquioBASIC() {
		return collBASIC;
	}
	public Long getDiarioId() {
		return diarioId;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCampoTestoRid() {
		return campoTestoRid;
	}

	public String getDescrizioneTipoColloquio() {
		return descrizioneTipoColloquio;
	}

	public String getDescrizioneDiarioDove() {
		return descrizioneDiarioDove;
	}

	public String getDescrizioneDiarioConChi() {
		return descrizioneDiarioConChi;
	}

	public String getDiarioConChiAltro() {
		return diarioConChiAltro;
	}

	public Date getDtModifica() {
		return dtModifica;
	}

	public Date getDtAmministrativa() {
		return dtAmministrativa;
	}

	public String getUsernameMod() {
		return usernameMod;
	}

	public String getUsernameOp() {
		return usernameOp;
	}

	public String getNomeDaChi() {
		return nomeDaChi;
	}

	public String getCognomeDaChi() {
		return cognomeDaChi;
	}
	
	public boolean isRiservato(){
		return riservato;
	}
	
	public boolean isAbilitato4riservato() {
		return abilitato4riservato;
	}

	public String getOpModifica() {
		return opModifica;
	}
	
}
