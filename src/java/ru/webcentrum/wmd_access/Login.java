/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_access;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.webcentrum.wmd_data.DataGetter;
import ru.webcentrum.wmd_data.ConnectionFactory;
import ru.webcentrum.wmd_user.User;

/**
 *
 * @author TitarX
 */
public class Login extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        Connection connection=null;
        try
        {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            HttpSession session=req.getSession(true);

            String urlid=new DataGetter().getData("/data/urlid/id/text()");

            String WmLogin_AuthType=req.getParameter("WmLogin_AuthType");
            String WmLogin_Created=req.getParameter("WmLogin_Created");
            String WmLogin_Expires=req.getParameter("WmLogin_Expires");
            String WmLogin_LastAccess=req.getParameter("WmLogin_LastAccess");
            String WmLogin_Ticket=req.getParameter("WmLogin_Ticket");
            String WmLogin_UrlID=req.getParameter("WmLogin_UrlID");
            String WmLogin_UserAddress=req.getParameter("WmLogin_UserAddress");
            String WmLogin_WMID=req.getParameter("WmLogin_WMID");
            //
            String WmLogin_KeeperRetStr=req.getParameter("WmLogin_KeeperRetStr");
            String WmLogin_KeeperRetVal=req.getParameter("WmLogin_KeeperRetVal");

            if(WmLogin_KeeperRetStr==null&&WmLogin_KeeperRetVal==null)
            {
                if(urlid.equals(WmLogin_UrlID)&&WmLogin_Ticket.matches("[a-zA-Z0-9\\$\\!\\/]{32,48}"))
                {
                    Verification verification=new Verification("517533138006",WmLogin_WMID,WmLogin_Ticket,WmLogin_UrlID,WmLogin_AuthType,WmLogin_UserAddress);
                    String retval=verification.verify();
                    if(retval.equals("0"))
                    {
                        String sqlQuery="SELECT * FROM \"Users\" WHERE \"WMID\"='"+WmLogin_WMID+"';";
                        connection=new ConnectionFactory().getConnection();
                        Statement statement=connection.createStatement();
                        ResultSet results=statement.executeQuery(sqlQuery);
                        User user=null;
                        if(results.next())
                        {
                            user=new User(results.getString("WMID"),results.getString("Email"),results.getString("Firstname"),results.getString("Lastname"),results.getString("Patronymic"));
                            session.setAttribute("access",WmLogin_WMID);
                            session.setAttribute("user",user);
                            req.getRequestDispatcher("/cabinet/portfolio/credits/all.jsp").forward(req,resp);
                        }
                        else
                        {
                            sqlQuery="INSERT INTO \"Users\"(\"WMID\",\"Email\",\"Firstname\",\"Lastname\",\"Patronymic\")"
                                    +"VALUES ('"+WmLogin_WMID+"','','','','');";
                            statement.execute(sqlQuery);

                            user=new User(WmLogin_WMID,"","","","");
                            session.setAttribute("access",WmLogin_WMID);
                            session.setAttribute("user",user);
                            req.getRequestDispatcher("/cabinet/profile.jsp").forward(req,resp);
                        }
                    }
                    else
                    {
                        req.setAttribute("errorLogin",retval);
                        req.getRequestDispatcher("/errorLogin.jsp").forward(req,resp);
                    }
                }
                else
                {
                    req.setAttribute("errorLogin","21");
                    req.getRequestDispatcher("/errorLogin.jsp").forward(req,resp);
                }
            }
            else
            {
                if(WmLogin_KeeperRetStr.equalsIgnoreCase("Canceled")&&WmLogin_KeeperRetVal.equals("4"))
                {
                    req.setAttribute("errorLogin","22");
                    req.getRequestDispatcher("/errorLogin.jsp").forward(req,resp);
                }
                else
                {
                    req.setAttribute("errorLogin","n");
                    req.getRequestDispatcher("/errorLogin.jsp").forward(req,resp);
                }
            }
        }
        catch(SQLException ex)
        {
            req.setAttribute("errorLogin","24");
            req.getRequestDispatcher("/errorLogin.jsp").forward(req,resp);
        }
        catch(Exception ex)
        {
            req.setAttribute("errorLogin","n");
            req.getRequestDispatcher("/errorLogin.jsp").forward(req,resp);
        }
        finally
        {
            try
            {
                if(connection!=null&&!connection.isClosed())
                {
                    connection.close();
                }
            }
            catch(SQLException ex)
            {
                req.setAttribute("errorLogin","25");
                req.getRequestDispatcher("/errorLogin.jsp").forward(req,resp);
            }
        }
    }
}
