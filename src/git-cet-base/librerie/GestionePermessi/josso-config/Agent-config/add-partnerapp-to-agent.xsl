<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="nomeapplicazioneAgent"></xsl:param>
	<xsl:output method="xml" indent="yes" omit-xml-declaration="no"/>




	<xsl:template match="/ | @* | node()">
		<xsl:choose>
			<xsl:when
				test="local-name(.)='partner-apps'">
				<xsl:call-template name="addPartnerapp" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy>
					<xsl:apply-templates select="@* | node()" />
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>



	<xsl:template name="addPartnerapp">
			<partner-apps>
				<xsl:for-each select="partner-app">
					<xsl:copy-of select="."/>
				</xsl:for-each>
			<partner-app>
            			<context>
            			<xsl:text>/</xsl:text>
            			<xsl:value-of select="$nomeapplicazioneAgent"/>
            			</context>
        	</partner-app>

			</partner-apps>
	</xsl:template>


</xsl:stylesheet>