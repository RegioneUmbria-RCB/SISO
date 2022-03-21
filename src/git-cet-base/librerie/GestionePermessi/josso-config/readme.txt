------------------
Introduzione
------------------
Josso è un framework per il SSO centralizzato completamente integrato con i meccanismi di autenticazione
di Tomcat basati su JAAS.

La configurazione di sso di josso è composta da
1 gateway
n partner applications

Il gateway e le partner applications possono anche risiedere su due o più application servers distinti.

-------------------
Configurazione implementata
-------------------
La configurazione scelta è quella di avere un gateway josso in ascolto su un tomcat dedicato
e n partner application su 1 o più application severs.
Di seguito gli step per configurare l'ambiente di sso.

--------------------
Configurazione di tomcat 5.5 per l'utilizzo con josso (sia gateway che agent)
--------------------
Si esegue dal build presente nella cartella "josso-config"
- tomcat.configuring.with.josso
Copia le librerie necessarie al funzionamento di josso nella struttura delle directory di tomcat
--------------------
Configurazione dell'applicazione gateway
--------------------
E' necessario istallare la webapp presente all'interno dello zip in un tomcat già preparato con le librerie di josso.
Eventualmnte prima vanno modificati il file di configurazione del gateway: josso-gateway-config.xml
--------------------
Configurazione di un agent del gateway
--------------------
Dopo aver preparato tomcat attraverso il target ant tomcat.configuring.with.josso
E' necessario aggiungere i files di configurazione dell'agent alla cartella
CATALINA_HOME\bin. Questo può essere fatto attraverso il target "add.agent.conf.to.tomcat"

Successivamente per rendere una applicazione connessa all'agent di sso si deve lanciare 
il target "add.application.to.agent".
n.b. i files di configurazione dell'agent vengono aggiunti anche alla cartella di eclipse 
per un problema di josso nell'individuare la working dir quando si avvia tomcat dal wtp.

Creare una pagina login-redirect.jsp nell'applicazione agent:
<%@page contentType="text/html; charset=iso-8859-1" language="java"
        session="true"%>
<%
    response.sendRedirect(request.getContextPath() + "/josso_login/");
%>
E' possibile anche creare una pagina logout-redirect.jsp:
<%@page contentType="text/html; charset=iso-8859-1" language="java"
            session="true" %>
    <%
        response.sendRedirect(request.getContextPath() + "/josso_logout");
    %>

Aggiungere il file jaas.conf in CATALINA_HOME\conf con il seguente contenuto:
josso {
      org.josso.tc55.agent.jaas.SSOGatewayLoginModule required debug=true;
};
conseguentemente a questo , aggiungere come apzione all'avvio di tomcat il parametro
-Djava.security.auth.login.config="..path....tomcat....\conf\jaas.conf" 

Se Tomcat parte come servizio potrebbe non trovare il jaas.conf a meno di mettere nel file
JVM/jre/lib/security/java.security il riferimento al file jaas.conf, come da istruzioni:
An alternate approach to specifying the location of the login configuration file is to indicate its URL as the value of a login.config.url.n property in the security properties file. The security properties file is the java.security file located in the lib/security directory of the JRE. 
Here, n indicates a consecutively-numbered integer starting with 1. Thus, if desired, you can specify more than one login configuration file by indicating one file's URL for the login.config.url.1 property, a second file's URL for the login.config.url.2 property, and so on. If more than one login configuration file is specified (that is, if n > 1), then the files are read and concatenated into a single configuration. 
Here is an example of what would need to be added to the security properties file in order to indicate the jaas.conf login configuration file used by this tutorial. This example assumes the file is in the C:\AcnTest directory on a Microsoft Windows system: 
login.config.url.1=file:C:/path tomcat/conf/jaas.conf
(Note that URLs always use forward slashes, regardless of what operating system the user is running.) 






Aggiungere il tag Valve al server.xml del tomcat dove verrà fatto il deploy della partner application:
      <Host name="localhost" appBase="webapps" unpackWARs="true" 
      autoDeploy="true" xmlValidation="false" xmlNamespaceAware="false">
		......
		<Valve className="org.josso.tc55.agent.SSOAgentValve" debug="1"/>
		........
	  <Host>
		
Sempre nel server.xml remmare la riga

	     <!-- Realm className="org.apache.catalina.realm.UserDatabaseRealm" resourceName="UserDatabase"/-->
e aggiungere al suo posto il seguente tag:
		<Realm className="org.apache.catalina.realm.JAASRealm" 
		appName="josso" 
		userClassNames="org.josso.gateway.identity.service.BaseUserImpl" 
		roleClassNames="org.josso.gateway.identity.service.BaseRoleImpl" 
		debug="1"/>
		

Nel web.xml si può specificare quali risorse devono essere protette e richiedono autenticazione:

<security-constraint>
        <!-- Sample Security Constraint -->
        <web-resource-collection>

            <web-resource-name>protected-resources</web-resource-name>

            <url-pattern>/*</url-pattern>

            <http-method>HEAD</http-method>
            <http-method>GET</http-method>
            <http-method>POST</http-method>
            <http-method>PUT</http-method>
            <http-method>DELETE</http-method>

        </web-resource-collection>

        <auth-constraint>
            <role-name>role1</role-name>
        </auth-constraint>

        <user-data-constraint>
            <transport-guarantee>NONE</transport-guarantee>
        </user-data-constraint>
    </security-constraint>

    <login-config>
        <auth-method>FORM</auth-method>

        <form-login-config>
            <form-login-page>/login-redirect.jsp</form-login-page>
            <form-error-page>/login-redirect.jsp</form-error-page>
        </form-login-config>
    </login-config>

    <security-role >
        <description>The Role1</description>
        <role-name>role1</role-name>
    </security-role>
    
		
N.B. Se Tomcat parte come servizio allora i files josso-agent-config.xml e josso-config.xml
potrebbero non essere trovati da josso che li cercherebbe non su bin ma sul path del s.o.

Si deve eseguire dentro CATALINA_HOME/bin la seguente istruzione:

tomcat5 //US/Tomcat5 --StartPath "il path di bin di tomcat"



 	

--------------------
Reverse Proxy
--------------------
Josso fornisce anche un meccanismo di reverse proxy, solitamente da istallare sul tomcat del gateway
La sua configurazione è semplice, basta copiare josso-config.xml e josso-reverseproxy.xml opportunamente configurati
nella bin di tomcat
e aggiungere al server.xml del tomcat la riga
	<Valve className="org.josso.tc55.gateway.reverseproxy.ReverseProxyValve"/>

Comunque questo meccanismo sembra non funzionare pienamente, si ritiene dunque preferibile utilizzare un'altra
implementazione di un reverse proxy, magari quella di apache httpd.
		
		
		







    







<!-- il reverse proxy intercetta le chiamate
	<Valve className="org.josso.tc55.gateway.reverseproxy.ReverseProxyValve"/>
-->

