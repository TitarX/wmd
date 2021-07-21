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
import ru.webcentrum.wmd_user.User;

/**
 *
 * @author TitarX
 */
public class FeedBack extends HttpServlet implements SingleThreadModel
{

    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        DiskFileItemFactory diskFileItemFactory=null;
        ServletFileUpload servletFileUpload=null;
        List listParameters=null;
        List attachments=null;
        Map simpleParameters=null;
        StringBuilder content=null;

        String subject="";
        String pop="";
        String smtp="";
        String login="";
        String password="";
        String to="";
        String from="";
        String attachmentFolder="";
        String port="25";
        String sparePort="465";

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session=req.getSession(true);

        try
        {
            User user=null;
            String wmid="";
            String email="";
            String firstname="";
            String lastname="";
            String patronymic="";
            if(session.getAttribute("user")!=null)
            {
                user=(User)session.getAttribute("user");
                wmid=user.getWMID();
                email=user.getEmail();
                firstname=user.getFirstname();
                lastname=user.getLastname();
                patronymic=user.getPatronymic();
            }

            DataGetter dataGetter=new DataGetter();
            pop=dataGetter.getData("/data/email/pop/text()");
            smtp=dataGetter.getData("/data/email/smtp/text()");
            login=dataGetter.getData("/data/email/login/text()");
            password=dataGetter.getData("/data/email/password/text()");
            to=dataGetter.getData("/data/email/to/text()");
            from=dataGetter.getData("/data/email/from/text()");
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

            subject=new String(String.valueOf(simpleParameters.get("subject")).getBytes("ISO-8859-1"),"UTF-8");
            String message=new String(String.valueOf(simpleParameters.get("message")).getBytes("ISO-8859-1"),"UTF-8");

            content=new StringBuilder();
            content.append(wmid);
            content.append("\n");
            content.append(email);
            content.append("\n");
            content.append(firstname);
            content.append("\n");
            content.append(lastname);
            content.append("\n");
            content.append(patronymic);
            content.append("\n");
            content.append("----------------------------------------------");
            content.append("\n");
            content.append(message);

            if(attachments.size()>0)
            {
                new EmailAttachmentSender2().send(from,to,subject,content.toString(),smtp,port,login,password,attachments);
            }
            else
            {
                new EmailSender().send(from,to,subject,content.toString(),smtp,port,login,password);
            }

            req.setAttribute("feedBackInfo","<span class='info'>Сообщение удачно отправлено</span>");
            req.getRequestDispatcher("/feedback.jsp").forward(req,resp);
        }
        catch(MessagingException ex)
        {
            try
            {
                if(attachments.size()>0)
                {
                    new EmailAttachmentSender2().send(from,to,subject,content.toString(),smtp,sparePort,login,password,attachments);
                }
                else
                {
                    new EmailSender().send(from,to,subject,content.toString(),smtp,sparePort,login,password);
                }

                req.setAttribute("feedBackInfo","<span class='info'>Сообщение успешно отправлено</span>");
                req.getRequestDispatcher("/feedback.jsp").forward(req,resp);
            }
            catch(Exception ex2)
            {
                req.setAttribute("feedBackInfo","<span class='error'>Не удалось отправить сообщение</span>");
                req.getRequestDispatcher("/feedback.jsp").forward(req,resp);
            }
        }
        catch(Exception ex)
        {
            req.setAttribute("feedBackInfo","<span class='error'>Не удалось отправить сообщение</span>");
            req.getRequestDispatcher("/feedback.jsp").forward(req,resp);
        }
    }
}
