package org.compiere.print;

 


import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.xml.transform.stream.StreamResult;

import org.junit.Test;

public class PrintDataTest
{
	/**
	 * Lagacy test to make sure the XML is correcty generated and parsed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void test_parseXml() throws IOException
	{
		PrintData pd = new PrintData(new Properties(), "test1");
		pd.addNode(new PrintDataElement("test1element1", "testvalue<1>", 0, null));
		pd.addNode(new PrintDataElement("test1element2", "testvalue&2&", 0, null));

		PrintData pdx = new PrintData(new Properties(), "test2");
		pdx.addNode(new PrintDataElement("test2element1-1", "testvalue11", 0, null));
		pdx.addNode(new PrintDataElement("test2element1-2", "testvalue12", 0, null));
		pdx.addRow(false, 0);
		pdx.addNode(new PrintDataElement("test2element2-1", "testvalue21", 0, null));
		pdx.addNode(new PrintDataElement("test2element2-2", "testvalue22", 0, null));

		pd.addNode(pdx);
		pd.addNode(new PrintDataElement("test1element3", "testvalue/3/", 0, null));

		final File file = File.createTempFile("printDataTest", ".xml");
		pd.createXML(file.getAbsolutePath());
		pd.createXML(new StreamResult(System.out));
		System.out.println("");
		pd.dump();

		// parse
		System.out.println("");
		PrintData pd1 = PrintData.parseXML(new Properties(), file);
		pd1.createXML(new StreamResult(System.out));
		System.out.println("");
		pd1.dump();
	}	// main

}
