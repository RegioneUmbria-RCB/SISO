/*
 * An XML document type.
 * Localname: Request
 * Namespace: http://www.finmatica.it/schema/HL7
 * Java type: it.finmatica.www.schema.hl7.RequestDocument
 *
 * Automatically generated - do not modify.
 */
package it.finmatica.www.schema.hl7.impl;
/**
 * A document containing one Request(@http://www.finmatica.it/schema/HL7) element.
 *
 * This is a complex type.
 */
public class RequestDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.finmatica.www.schema.hl7.RequestDocument
{
    
    public RequestDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REQUEST$0 = 
        new javax.xml.namespace.QName("http://www.finmatica.it/schema/HL7", "Request");
    
    
    /**
     * Gets the "Request" element
     */
    public it.finmatica.www.schema.hl7.RequestDocument.Request getRequest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.finmatica.www.schema.hl7.RequestDocument.Request target = null;
            target = (it.finmatica.www.schema.hl7.RequestDocument.Request)get_store().find_element_user(REQUEST$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Request" element
     */
    public void setRequest(it.finmatica.www.schema.hl7.RequestDocument.Request request)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.finmatica.www.schema.hl7.RequestDocument.Request target = null;
            target = (it.finmatica.www.schema.hl7.RequestDocument.Request)get_store().find_element_user(REQUEST$0, 0);
            if (target == null)
            {
                target = (it.finmatica.www.schema.hl7.RequestDocument.Request)get_store().add_element_user(REQUEST$0);
            }
            target.set(request);
        }
    }
    
    /**
     * Appends and returns a new empty "Request" element
     */
    public it.finmatica.www.schema.hl7.RequestDocument.Request addNewRequest()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.finmatica.www.schema.hl7.RequestDocument.Request target = null;
            target = (it.finmatica.www.schema.hl7.RequestDocument.Request)get_store().add_element_user(REQUEST$0);
            return target;
        }
    }
    /**
     * An XML Request(@http://www.finmatica.it/schema/HL7).
     *
     * This is a complex type.
     */
    public static class RequestImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.finmatica.www.schema.hl7.RequestDocument.Request
    {
        
        public RequestImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName MESSAGE$0 = 
            new javax.xml.namespace.QName("http://www.finmatica.it/schema/HL7", "Message");
        private static final javax.xml.namespace.QName CREATOR$2 = 
            new javax.xml.namespace.QName("http://www.finmatica.it/schema/HL7", "Creator");
        
        
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
        
        /**
         * Gets the "Creator" element
         */
        public java.lang.String getCreator()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CREATOR$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "Creator" element
         */
        public org.apache.xmlbeans.XmlString xgetCreator()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CREATOR$2, 0);
                return target;
            }
        }
        
        /**
         * Sets the "Creator" element
         */
        public void setCreator(java.lang.String creator)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CREATOR$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CREATOR$2);
                }
                target.setStringValue(creator);
            }
        }
        
        /**
         * Sets (as xml) the "Creator" element
         */
        public void xsetCreator(org.apache.xmlbeans.XmlString creator)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CREATOR$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CREATOR$2);
                }
                target.set(creator);
            }
        }
    }
}
