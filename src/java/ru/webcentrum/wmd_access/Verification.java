/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_access;

import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lv.flancer.wmt.xml.HttpRequester;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author TitarX
 */
public class Verification
{

    private String SiteHolder=null;
    private String WmLogin_WMID=null;
    private String WmLogin_Ticket=null;
    private String WmLogin_UrlID=null;
    private String WmLogin_AuthType=null;
    private String WmLogin_UserAddress=null;

    public Verification(String SiteHolder,String WmLogin_WMID,String WmLogin_Ticket,String WmLogin_UrlID,String WmLogin_AuthType,String WmLogin_UserAddress)
    {
        this.SiteHolder=SiteHolder;
        this.WmLogin_AuthType=WmLogin_AuthType;
        this.WmLogin_Ticket=WmLogin_Ticket;
        this.WmLogin_UrlID=WmLogin_UrlID;
        this.WmLogin_UserAddress=WmLogin_UserAddress;
        this.WmLogin_WMID=WmLogin_WMID;
    }

    public String verify() throws Exception
    {
        String requestCheckTicket=
                "<request>"
                +"<siteHolder>"+SiteHolder+"</siteHolder>"
                +"<user>"+WmLogin_WMID+"</user>"
                +"<ticket>"+WmLogin_Ticket+"</ticket>"
                +"<urlId>"+WmLogin_UrlID+"</urlId>"
                +"<authType>"+WmLogin_AuthType+"</authType>"
                +"<userAddress>"+WmLogin_UserAddress+"</userAddress>"
                +"</request>";
        String urlCheckTicket="https://login.wmtransfer.com/ws/authorize.xiface";

        HttpRequester httpRequester=new HttpRequester("login.wmtransfer.com",443);
        httpRequester.setSecuredResuest(true);
        String responseCheckTicket=httpRequester.doPost(urlCheckTicket,requestCheckTicket);

        /*
        String sample="<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        int firstIndex=responseCheckTicket.indexOf(sample);
        if(firstIndex>-1)
        {
        String xmlResponse=responseCheckTicket.substring(firstIndex).trim();
        }
        else
        {
        out.println("<p>Ошибка при подтверждении авторизации:<br/>Неверный XML-ответ</p><p>Вы не авторизованы</p>");
        }
         */

        Matcher matcher=Pattern.compile("<response.+/>").matcher(responseCheckTicket);
        if(matcher.find())
        {
            String xmlHead="<?xml version=\"1.0\" encoding=\"utf-8\"?>";
            String xmlBody=matcher.group();
            String xmlResponse=xmlHead.concat(xmlBody);

            SAXBuilder saxBuilder=new SAXBuilder();
            Document xmlResponseDocument=saxBuilder.build(new StringReader(xmlResponse));
            Element responseElement=xmlResponseDocument.getRootElement();
            
            return responseElement.getAttributeValue("retval");
        }
        else
        {
            return "20";
        }
    }
}
