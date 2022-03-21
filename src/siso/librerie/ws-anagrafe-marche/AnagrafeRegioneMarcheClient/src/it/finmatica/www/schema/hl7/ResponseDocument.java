/*
 * An XML document type.
 * Localname: Response
 * Namespace: http://www.finmatica.it/schema/HL7
 * Java type: it.finmatica.www.schema.hl7.ResponseDocument
 *
 * Automatically generated - do not modify.
 */
package it.finmatica.www.schema.hl7;


/**
 * A document containing one Response(@http://www.finmatica.it/schema/HL7) element.
 *
 * This is a complex type.
 */
public interface ResponseDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ResponseDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9AECBC69EF59D857941D2CDEDAD8B7C0").resolveHandle("responseaf2adoctype");
    
    /**
     * Gets the "Response" element
     */
    it.finmatica.www.schema.hl7.ResponseDocument.Response getResponse();
    
    /**
     * Sets the "Response" element
     */
    void setResponse(it.finmatica.www.schema.hl7.ResponseDocument.Response response);
    
    /**
     * Appends and returns a new empty "Response" element
     */
    it.finmatica.www.schema.hl7.ResponseDocument.Response addNewResponse();
    
    /**
     * An XML Response(@http://www.finmatica.it/schema/HL7).
     *
     * This is a complex type.
     */
    public interface Response extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Response.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9AECBC69EF59D857941D2CDEDAD8B7C0").resolveHandle("responsee567elemtype");
        
        /**
         * Gets the "Message" element
         */
        java.lang.String getMessage();
        
        /**
         * Gets (as xml) the "Message" element
         */
        org.apache.xmlbeans.XmlString xgetMessage();
        
        /**
         * Sets the "Message" element
         */
        void setMessage(java.lang.String message);
        
        /**
         * Sets (as xml) the "Message" element
         */
        void xsetMessage(org.apache.xmlbeans.XmlString message);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static it.finmatica.www.schema.hl7.ResponseDocument.Response newInstance() {
              return (it.finmatica.www.schema.hl7.ResponseDocument.Response) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static it.finmatica.www.schema.hl7.ResponseDocument.Response newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (it.finmatica.www.schema.hl7.ResponseDocument.Response) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.finmatica.www.schema.hl7.ResponseDocument newInstance() {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.finmatica.www.schema.hl7.ResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.finmatica.www.schema.hl7.ResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
