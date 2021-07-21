/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_post;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.webcentrum.wmd_data.DataGetter;

/**
 *
 * @author TitarX
 */
public class SendEmail extends HttpServlet implements SingleThreadModel
{

    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        DiskFileItemFactory diskFileItemFactory=null;
        ServletFileUpload servletFileUpload=null;
        List listParameters=null;
        List attachments=null;
        Map simpleParameters=null;

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session=req.getSession(true);

        if(session.getAttribute("user")!=null)
        {
            String pop="";
            String smtp="";
            String login="";
            String password="";
            String attachmentFolder="";
            String port="25";
            String sparePort="465";

            String to="";
            String from="";
            String subject="";
            String message="";

            try
            {
                DataGetter dataGetter=new DataGetter();
                pop=dataGetter.getData("/data/email/pop/text()");
                smtp=dataGetter.getData("/data/email/smtp/text()");
                login=dataGetter.getData("/data/email/login/text()");
                password=dataGetter.getData("/data/email/password/text()");
                attachmentFolder=dataGetter.getData("/data/email/attachmentfolder/text()");

                diskFileItemFactory=new DiskFileItemFactory();
                diskFileItemFactory.setRepository(new File(attachmentFolder));
                servletFileUpload=new ServletFileUpload(diskFileItemFactory);

                attachments=new ArrayList();
                listParameters=servletFileUpload.parseRequest(req);
                simpleParameters=new HashMap();
                Iterator iteratorParameters=listParameters.iterator();
                while(iteratorParameters.hasNext())
                {
                    FileItem fileItem=(FileItem)iteratorParameters.next();
                    if(fileItem.isFormField())
                    {
                        simpleParameters.put(fileItem.getFieldName(),fileItem.getString());
                    }
                    else
                    {
                        if((fileItem.getSize()<=0)||(fileItem.getSize()>10485760L))
                        {
                            continue;
                        }
                        AttachmentDataSource dataSource=new AttachmentDataSource(fileItem);
                        attachments.add(dataSource);
                    }
                }

                to=new String(String.valueOf(simpleParameters.get("to")).getBytes("ISO-8859-1"),"UTF-8");
                from=new String(String.valueOf(simpleParameters.get("from")).getBytes("ISO-8859-1"),"UTF-8");
                subject=new String(String.valueOf(simpleParameters.get("subject")).getBytes("ISO-8859-1"),"UTF-8");
                message=new String(String.valueOf(simpleParameters.get("message")).getBytes("ISO-8859-1"),"UTF-8");

                String emailRegex="^[_0-9a-zA-Zа-яА-Я][-._0-9a-zA-Zа-яА-Я]{0,29}[_0-9a-zA-Zа-яА-Я]@([0-9a-zA-Zа-яА-Я][-0-9a-zA-Zа-яА-Я]*[0-9a-zA-Zа-яА-Я]\\.)+[a-zA-Zа-яА-Я]{2,8}$";
                if(to.matches(emailRegex))
                {
                    if(from.matches(emailRegex))
                    {
                        if(attachments.size()>0)
                        {
                            new EmailAttachmentSender2().send(from,to,subject,message,smtp,port,login,password,attachments);
                        }
                        else
                        {
                            new EmailSender().send(from,to,subject,message,smtp,port,login,password);
                        }

                        req.setAttribute("sendEmailInfo","<span class='info'>Сообщение удачно отправлено</span>");
                        req.getRequestDispatcher("/cabinet/contacts/sendemail.jsp").forward(req,resp);
                    }
                    else
                    {
                        req.setAttribute("sendEmailInfo","<span class='error'>Указан некорректный email отправителя</span>");
                        req.getRequestDispatcher("/cabinet/contacts/sendemail.jsp").forward(req,resp);
                    }
                }
                else
                {
                    req.setAttribute("sendEmailInfo","<span class='error'>Указан некорректный email получателя</span>");
                    req.getRequestDispatcher("/cabinet/contacts/sendemail.jsp").forward(req,resp);
                }
            }
            catch(MessagingException ex)
            {
                try
                {
                    if(attachments.size()>0)
                    {
                        new EmailAttachmentSender2().send(from,to,subject,message,smtp,sparePort,login,password,attachments);
                    }
                    else
                    {
                        new EmailSender().send(from,to,subject,message,smtp,sparePort,login,password);
                    }

                    req.setAttribute("sendEmailInfo","<span class='info'>Сообщение успешно отправлено</span>");
                    req.getRequestDispatcher("/cabinet/contacts/sendemail.jsp").forward(req,resp);
                }
                catch(Exception ex2)
                {
                    req.setAttribute("sendEmailInfo","<span class='error'>Не удалось отправить сообщение</span>");
                    req.getRequestDispatcher("/cabinet/contacts/sendemail.jsp").forward(req,resp);
                }
            }
            catch(Exception ex)
            {
                req.setAttribute("sendEmailInfo","<span class='error'>Не удалось отправить сообщение</span>");
                req.getRequestDispatcher("/cabinet/contacts/sendemail.jsp").forward(req,resp);
            }
        }
        else
        {
            session.invalidate();
            req.setAttribute("errorLogin","23");
            req.getRequestDispatcher("/errorLogin.jsp").forward(req,resp);
        }

    }
}
