/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_post;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 *
 * @author TitarX
 */
class EmailAttachmentSender2
{

    void send(String from,String to,String subject,String content,String smtpHost,String smtpPort,List attachments) throws MessagingException,UnsupportedEncodingException
    {
        Properties properties=System.getProperties();
        properties.put("mail.smtp.port",smtpPort);
        properties.put("mail.smtp.host",smtpHost);
        properties.put("mail.smtp.auth","false");
        properties.put("mail.mime.charset","UTF-8");
        Session session=Session.getDefaultInstance(properties,null);

        Message msg=new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
        msg.setSubject(subject);

        BodyPart messageBodyPart=new MimeBodyPart();
        messageBodyPart.setContent(content,"text/plain; charset=UTF-8");
        Multipart multipart=new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        Iterator attachmentsIterator=attachments.iterator();
        while(attachmentsIterator.hasNext())
        {
            messageBodyPart=new MimeBodyPart();
            final DataSource dataSource=(DataSource)attachmentsIterator.next();
            messageBodyPart.setDataHandler(new DataHandler(dataSource));
            messageBodyPart.setFileName(MimeUtility.encodeText(dataSource.getName()));
            multipart.addBodyPart(messageBodyPart);
        }

        msg.setContent(multipart);
        Transport.send(msg);
    }

    void send(String from,String to,String subject,String content,String smtpHost,String smtpPort,String login,String password,List attachments) throws MessagingException,UnsupportedEncodingException
    {
        Authenticator authenticator=new PostAuthenticator(login,password);

        Properties properties=System.getProperties();
        properties.put("mail.smtp.port",smtpPort);
        properties.put("mail.smtp.host",smtpHost);
        properties.put("mail.smtp.auth","true");
        properties.put("mail.mime.charset","UTF-8");
        Session session=Session.getDefaultInstance(properties,authenticator);

        Message msg=new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
        msg.setSubject(subject);

        BodyPart messageBodyPart=new MimeBodyPart();
        messageBodyPart.setContent(content,"text/plain; charset=UTF-8");
        Multipart multipart=new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        Iterator attachmentsIterator=attachments.iterator();
        while(attachmentsIterator.hasNext())
        {
            messageBodyPart=new MimeBodyPart();
            final DataSource dataSource=(DataSource)attachmentsIterator.next();
            messageBodyPart.setDataHandler(new DataHandler(dataSource));
            messageBodyPart.setFileName(MimeUtility.encodeText(dataSource.getName()));
            multipart.addBodyPart(messageBodyPart);
        }

        msg.setContent(multipart);
        Transport.send(msg);
    }

    void sendMass(String from,ArrayList<String> to,String subject,String content,String smtpHost,String smtpPort,List attachments) throws MessagingException,UnsupportedEncodingException
    {
        Properties properties=System.getProperties();
        properties.put("mail.smtp.port",smtpPort);
        properties.put("mail.smtp.host",smtpHost);
        properties.put("mail.smtp.auth","false");
        properties.put("mail.mime.charset","UTF-8");
        Session session=Session.getDefaultInstance(properties,null);

        ArrayList<InternetAddress> internetAddresses=new ArrayList<InternetAddress>();
        for(String string:to)
        {
            internetAddresses.add(new InternetAddress(string));
        }
        InternetAddress[] internetAddressesArray=new InternetAddress[internetAddresses.size()];
        internetAddressesArray=internetAddresses.toArray(internetAddressesArray);

        Message msg=new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO,internetAddressesArray);
        msg.setSubject(subject);

        BodyPart messageBodyPart=new MimeBodyPart();
        messageBodyPart.setContent(content,"text/plain; charset=UTF-8");
        Multipart multipart=new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        Iterator attachmentsIterator=attachments.iterator();
        while(attachmentsIterator.hasNext())
        {
            messageBodyPart=new MimeBodyPart();
            final DataSource dataSource=(DataSource)attachmentsIterator.next();
            messageBodyPart.setDataHandler(new DataHandler(dataSource));
            messageBodyPart.setFileName(MimeUtility.encodeText(dataSource.getName()));
            multipart.addBodyPart(messageBodyPart);
        }

        msg.setContent(multipart);
        Transport.send(msg);
    }

    void sendMass(String from,ArrayList<String> to,String subject,String content,String smtpHost,String smtpPort,String login,String password,List attachments) throws MessagingException,UnsupportedEncodingException
    {
        Authenticator authenticator=new PostAuthenticator(login,password);

        Properties properties=System.getProperties();
        properties.put("mail.smtp.port",smtpPort);
        properties.put("mail.smtp.host",smtpHost);
        properties.put("mail.smtp.auth","true");
        properties.put("mail.mime.charset","UTF-8");
        Session session=Session.getDefaultInstance(properties,authenticator);

        ArrayList<InternetAddress> internetAddresses=new ArrayList<InternetAddress>();
        for(String string:to)
        {
            internetAddresses.add(new InternetAddress(string));
        }
        InternetAddress[] internetAddressesArray=new InternetAddress[internetAddresses.size()];
        internetAddressesArray=internetAddresses.toArray(internetAddressesArray);

        Message msg=new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO,internetAddressesArray);
        msg.setSubject(subject);

        BodyPart messageBodyPart=new MimeBodyPart();
        messageBodyPart.setContent(content,"text/plain; charset=UTF-8");
        Multipart multipart=new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        Iterator attachmentsIterator=attachments.iterator();
        while(attachmentsIterator.hasNext())
        {
            messageBodyPart=new MimeBodyPart();
            final DataSource dataSource=(DataSource)attachmentsIterator.next();
            messageBodyPart.setDataHandler(new DataHandler(dataSource));
            messageBodyPart.setFileName(MimeUtility.encodeText(dataSource.getName()));
            multipart.addBodyPart(messageBodyPart);
        }

        msg.setContent(multipart);
        Transport.send(msg);
    }
}
