package it.webred.WsSitClient;

public class TopoServiceEnumStatoCivico {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TopoServiceEnumStatoCivico(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Applicato = "Applicato";
    public static final java.lang.String _IterInCorso = "IterInCorso";
    public static final java.lang.String _Provvisorio = "Provvisorio";
    public static final java.lang.String _Soppresso = "Soppresso";
    public static final java.lang.String _Fittizio = "Fittizio";
    public static final java.lang.String _Centroide = "Centroide";
    public static final java.lang.String _Tutti = "Tutti";
    public static final TopoServiceEnumStatoCivico Applicato = new TopoServiceEnumStatoCivico(_Applicato);
    public static final TopoServiceEnumStatoCivico IterInCorso = new TopoServiceEnumStatoCivico(_IterInCorso);
    public static final TopoServiceEnumStatoCivico Provvisorio = new TopoServiceEnumStatoCivico(_Provvisorio);
    public static final TopoServiceEnumStatoCivico Soppresso = new TopoServiceEnumStatoCivico(_Soppresso);
    public static final TopoServiceEnumStatoCivico Fittizio = new TopoServiceEnumStatoCivico(_Fittizio);
    public static final TopoServiceEnumStatoCivico Centroide = new TopoServiceEnumStatoCivico(_Centroide);
    public static final TopoServiceEnumStatoCivico Tutti = new TopoServiceEnumStatoCivico(_Tutti);
    public java.lang.String getValue() { return _value_;}
    public static TopoServiceEnumStatoCivico fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TopoServiceEnumStatoCivico enumeration = (TopoServiceEnumStatoCivico)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TopoServiceEnumStatoCivico fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
}
