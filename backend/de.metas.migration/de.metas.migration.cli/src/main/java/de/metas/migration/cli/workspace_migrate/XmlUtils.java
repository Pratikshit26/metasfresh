package de.metas.migration.cli.workspace_migrate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.migration.cli
     
 * #L%
 */

@UtilityClass
final class XmlUtils
{
	private static XPath xPath = XPathFactory.newInstance().newXPath();

	public static org.w3c.dom.Document loadDocument(final File file)
	{
		try (final InputStream in = new FileInputStream(file))
		{
			final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			return documentBuilder.parse(in);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Failed loading XML document: " + file, e);
		}
	}

	public static String getString(final String xPathExpression, final Node node) throws XPathExpressionException
	{
		return (String)xPath.evaluate(xPathExpression, node, XPathConstants.STRING);
	}

}
