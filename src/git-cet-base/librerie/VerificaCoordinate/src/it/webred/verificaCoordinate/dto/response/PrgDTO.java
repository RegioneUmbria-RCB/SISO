package it.webred.verificaCoordinate.dto.response;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;

import java.io.Serializable;

public class PrgDTO extends VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Long dal;
    protected Long al;
    protected DestFunzionaleDTO destFunzionale;
    protected ZonaOmogeneaDTO zonaOmogenea;
    protected LinksDTO links;
    protected String id;
    
    public Long getDal() {
        return dal;
    }

    public void setDal(Long value) {
        this.dal = value;
    }

    public boolean isSetDal() {
        return (this.dal != null);
    }

    public Long getAl() {
        return al;
    }
    
    public void setAl(Long value) {
        this.al = value;
    }

    public boolean isSetAl() {
        return (this.al != null);
    }

    public DestFunzionaleDTO getDestFunzionale() {
        return destFunzionale;
    }
    
    public void setDestFunzionale(DestFunzionaleDTO value) {
        this.destFunzionale = value;
    }

    public boolean isSetDestFunzionale() {
        return (this.destFunzionale != null);
    }

    public ZonaOmogeneaDTO getZonaOmogenea() {
        return zonaOmogenea;
    }

    public void setZonaOmogenea(ZonaOmogeneaDTO value) {
        this.zonaOmogenea = value;
    }

    public boolean isSetZonaOmogenea() {
        return (this.zonaOmogenea != null);
    }

    public LinksDTO getLinks() {
        return links;
    }

    public void setLinks(LinksDTO value) {
        this.links = value;
    }

    public boolean isSetLinks() {
        return (this.links != null);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id != null);
    }

}

