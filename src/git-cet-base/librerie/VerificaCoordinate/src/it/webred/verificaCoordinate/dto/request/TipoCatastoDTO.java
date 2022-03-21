package it.webred.verificaCoordinate.dto.request;

import javax.xml.bind.annotation.XmlEnumValue;

public enum TipoCatastoDTO {
	
	@XmlEnumValue("Fabbricati")
    FABBRICATI("Fabbricati"),
    @XmlEnumValue("Terreni")
    TERRENI("Terreni");
	private final String value;

    TipoCatastoDTO(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoCatastoDTO fromValue(String v) {
        for (TipoCatastoDTO c: TipoCatastoDTO.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
