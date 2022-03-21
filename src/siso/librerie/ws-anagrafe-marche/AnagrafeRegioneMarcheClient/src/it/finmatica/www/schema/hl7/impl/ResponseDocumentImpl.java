/*
 * An XML document type.
 * Localname: Response
 * Namespace: http://www.finmatica.it/schema/HL7
 * Java type: it.finmatica.www.schema.hl7.ResponseDocument
 *
 * Automatically generated - do not modify.
 */
package it.finmatica.www.schema.hl7.impl;
/**
 * A document containing one Response(@http://www.finmatica.it/schema/HL7) element.
 *
 * This is a complex type.
 */
public class ResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.finmatica.www.schema.hl7.ResponseDocument
{
    
    public ResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName RESPONSE$0 = 
        new javax.xml.namespace.QName("http://www.finmatica.it/schema/HL7", "Response");
    
    
    /**
     * Gets the "Response" element
     */
    public it.finmatica.www.schema.hl7.ResponseDocument.Response getResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.finmatica.www.schema.hl7.ResponseDocument.Response target = null;
            target = (it.finmatica.www.schema.hl7.ResponseDocument.Response)get_store().find_element_user(RESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Response" element
     */
    public void setResponse(it.finmatica.www.schema.hl7.ResponseDocument.Response response)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.finmatica.www.schema.hl7.ResponseDocument.Response target = null;
            target = (it.finmatica.www.schema.hl7.ResponseDocument.Response)get_store().find_element_user(RESPONSE$0, 0);
            if (target == null)
            {
                target = (it.finmatica.www.schema.hl7.ResponseDocument.Response)get_store().add_element_user(RESPONSE$0);
            }
            target.set(response);
        }
    }
    
    /**
     * Appends and returns a new empty "Response" element
     */
    public it.finmatica.www.schema.hl7.ResponseDocument.Response addNewResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.finmatica.www.schema.hl7.ResponseDocument.Response target = null;
            target = (it.finmatica.www.schema.hl7.ResponseDocument.Response)get_store().add_element_user(RESPONSE$0);
            return target;
        }
    }
    /**
     * An XML Response(@http://www.finmatica.it/schema/HL7).
     *
     * This is a complex type.
     */
    public static class ResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.finmatica.www.schema.hl7.ResponseDocument.Response
    {
        
        public ResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName MESSAGE$0 = 
            new javax.xml.namespace.QName("http://www.finmatica.it/schema/HL7", "Message");
        
        
        /**
         * Gets the "Message" element
         */
        public java.lang.String getMessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "Message" element
         */
        public org.apache.xmlbeans.XmlString xgetMessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$0, 0);
                return target;
            }
        }
        
        /**
         * Sets the "Message" element
         */
        public void setMessage(java.lang.String message)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MESSAGE$0);
                }
                target.setStringValue(message);
            }
        }
        
        /**
         * Sets (as xml) the "Message" element
         */
        public void xsetMessage(org.apache.xmlbeans.XmlString message)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MESSAGE$0);
                }
                target.set(message);
            }
        }
    }
}
