/*
 * An XML document type.
 * Localname: Fault
 * Namespace: http://www.finmatica.it/schema/HL7
 * Java type: it.finmatica.www.schema.hl7.FaultDocument
 *
 * Automatically generated - do not modify.
 */
package it.finmatica.www.schema.hl7;


/**
 * A document containing one Fault(@http://www.finmatica.it/schema/HL7) element.
 *
 * This is a complex type.
 */
public interface FaultDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(FaultDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9AECBC69EF59D857941D2CDEDAD8B7C0").resolveHandle("fault477bdoctype");
    
    /**
     * Gets the "Fault" element
     */
    it.finmatica.www.schema.hl7.FaultDocument.Fault getFault();
    
    /**
     * Sets the "Fault" element
     */
    void setFault(it.finmatica.www.schema.hl7.FaultDocument.Fault fault);
    
    /**
     * Appends and returns a new empty "Fault" element
     */
    it.finmatica.www.schema.hl7.FaultDocument.Fault addNewFault();
    
    /**
     * An XML Fault(@http://www.finmatica.it/schema/HL7).
     *
     * This is a complex type.
     */
    public interface Fault extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Fault.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9AECBC69EF59D857941D2CDEDAD8B7C0").resolveHandle("faultae4delemtype");
        
        /**
         * Gets the "FaultCode" element
         */
        java.lang.String getFaultCode();
        
        /**
         * Gets (as xml) the "FaultCode" element
         */
        org.apache.xmlbeans.XmlString xgetFaultCode();
        
        /**
         * Sets the "FaultCode" element
         */
        void setFaultCode(java.lang.String faultCode);
        
        /**
         * Sets (as xml) the "FaultCode" element
         */
        void xsetFaultCode(org.apache.xmlbeans.XmlString faultCode);
        
        /**
         * Gets the "FaultString" element
         */
        java.lang.String getFaultString();
        
        /**
         * Gets (as xml) the "FaultString" element
         */
        org.apache.xmlbeans.XmlString xgetFaultString();
        
        /**
         * Sets the "FaultString" element
         */
        void setFaultString(java.lang.String faultString);
        
        /**
         * Sets (as xml) the "FaultString" element
         */
        void xsetFaultString(org.apache.xmlbeans.XmlString faultString);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.finmatica.www.schema.hl7.FaultDocument.Fault newInstance() {
              return (it.finmatica.www.schema.hl7.FaultDocument.Fault) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.finmatica.www.schema.hl7.FaultDocument.Fault newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.finmatica.www.schema.hl7.FaultDocument.Fault) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.finmatica.www.schema.hl7.FaultDocument newInstance() {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.finmatica.www.schema.hl7.FaultDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.finmatica.www.schema.hl7.FaultDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.finmatica.www.schema.hl7.FaultDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.finmatica.www.schema.hl7.FaultDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.finmatica.www.schema.hl7.FaultDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.finmatica.www.schema.hl7.FaultDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
