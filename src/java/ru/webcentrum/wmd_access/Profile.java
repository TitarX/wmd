/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_access;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.webcentrum.wmd_data.ConnectionFactory;
import ru.webcentrum.wmd_data.DataConvertor;
import ru.webcentrum.wmd_user.User;

/**
 *
 * @author TitarX
 */
public class Profile extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session=req.getSession(true);

        if(session.getAttribute("access")!=null)
        {
            String firstname=req.getParameter("firstname");
            String lastname=req.getParameter("lastname");
            String patronymic=req.getParameter("patronymic");
            String email=req.getParameter("email");

            if(firstname==null||lastname==null||patronymic==null||email==null)
            {
                req.setAttribute("profileInfo","<span class='error'>Недостаточно параметров</span>");
                req.getRequestDispatcher("/cabinet/profile.jsp").forward(req,resp);
            }

            Connection connection=null;

            try
            {
                String WMID=session.getAttribute("access").toString();
                connection=new ConnectionFactory().getConnection();
                Statement statement=connection.createStatement();
                String sqlQuery=null;

                sqlQuery="UPDATE \"Users\""
                        +"SET "
                        +"\"Email\"='"+DataConvertor.fixApostrophe(email)
                        +"',\"Firstname\"='"+DataConvertor.fixApostrophe(firstname)
                        +"',\"Lastname\"='"+DataConvertor.fixApostrophe(lastname)
                        +"',\"Patronymic\"='"+DataConvertor.fixApostrophe(patronymic)
                        +"'"
                        +"WHERE \"WMID\"='"+WMID+"';";
                int r=statement.executeUpdate(sqlQuery);

                if(r>0)
                {
                    User user=new User(WMID,email,firstname,lastname,patronymic);
                    session.removeAttribute("user");
                    session.setAttribute("user",user);
                    req.setAttribute("profileInfo","<span class='info'>Данные успешно изменены</span>");
                    req.getRequestDispatcher("/cabinet/profile.jsp").forward(req,resp);
                }
                else
                {
                    req.setAttribute("profileInfo","<span class='error'>Не удалось обновить данные</span>");
                    req.getRequestDispatcher("/cabinet/profile.jsp").forward(req,resp);
                }
            }
            catch(SQLException ex)
            {
                req.setAttribute("profileInfo","<span class='error'>Ошибка при запросе к базе данных</span>");
                req.getRequestDispatcher("/cabinet/profile.jsp").forward(req,resp);
            }
            catch(Exception ex)
            {
                req.setAttribute("profileInfo","<span class='error'>Неизвестная ошибка</span>");
                req.getRequestDispatcher("/cabinet/profile.jsp").forward(req,resp);
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
                    req.setAttribute("profileInfo","<span class='error'>Ошибка при закрытии соединения с базой данных</span>");
                    req.getRequestDispatcher("/cabinet/profile.jsp").forward(req,resp);
                }
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
