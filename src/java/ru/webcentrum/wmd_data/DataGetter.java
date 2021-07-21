/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_data;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author TitarX
 */
public class DataGetter
{

    private String pathData="F:/Work/Projects/webcentrum.ru/wmd/Project/WMD/web/data/xml/data.xml";

    public String getData(String path)
    {
        try
        {
            Document document=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(pathData);
            XPath xPath=XPathFactory.newInstance().newXPath();
            XPathExpression xpathExpression=xPath.compile(path);
            NodeList nodeList=(NodeList)xpathExpression.evaluate(document,XPathConstants.NODESET);
            return nodeList.item(0).getTextContent().trim();
        }
        catch(Exception ex)
        {
            Logger.getLogger(DataGetter.class.getName()).log(Level.SEVERE,null,ex);
            return null;
        }
    }
}
