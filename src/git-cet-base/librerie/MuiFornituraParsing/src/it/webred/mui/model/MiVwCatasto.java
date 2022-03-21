package it.webred.mui.model;

public class MiVwCatasto extends MiVwCatastoBase {
    public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof MiVwCatasto) ) return false;

        final MiVwCatasto cat = (MiVwCatasto) other;

        if ( !cat.getFoglio().equals( getFoglio() ) ) return false;
        if ( !cat.getParticella().equals( getParticella() ) ) return false;
        if ( !cat.getSubalterno().equals( getSubalterno() ) ) return false;
        if ( !cat.getDataFine().equals( getDataFine() ) ) return false;
        if ( !cat.getDataInizio().equals( getDataInizio() ) ) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = getFoglio().hashCode();
        result = 4 * result + getParticella().hashCode();
        result = 4 * result + getSubalterno().hashCode();
        result = 4 * result + getDataInizio().hashCode();
        result = 4 * result + getDataFine().hashCode();
        return result;
    }
}
