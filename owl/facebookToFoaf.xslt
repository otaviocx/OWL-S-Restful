<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:foaf="http://xmlns.com/foaf/0.1/">

	<xsl:strip-space elements="*" />
	<xsl:output indent="yes" />
	<xsl:template match="json">
		<rdf:RDF>
			<foaf:Person>
				<xsl:attribute name="rdf:ID">
					<xsl:value-of select="link" />
				</xsl:attribute>
				<foaf:firstName>
					<xsl:value-of select="first_name" />
				</foaf:firstName>
				<foaf:lastName>
					<xsl:value-of select="last_name" />
				</foaf:lastName>
				<foaf:gender>
					<xsl:value-of select="gender" />
				</foaf:gender>
				<xsl:if test="email">
					<foaf:mbox>
						<xsl:value-of select="email" />
					</foaf:mbox>
				</xsl:if>
				<xsl:if test="username">
					<foaf:account>
						<xsl:value-of select="username" />
					</foaf:account>
				</xsl:if>
				<xsl:if test="birthday">
					<foaf:birthday>
						<xsl:value-of select="birthday" />
					</foaf:birthday>
				</xsl:if>
			</foaf:Person>
		</rdf:RDF>
	</xsl:template>

</xsl:stylesheet>