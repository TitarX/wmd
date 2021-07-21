/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_credits;

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

/**
 *
 * @author TitarX
 */
public class DeleteCredit extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session=req.getSession(true);

        if(session.getAttribute("access")!=null)
        {
            String referer="/cabinet/portfolio/credits/all.jsp";
            Connection connection=null;
            try
            {
                String wmid=session.getAttribute("access").toString();

                if((session.getAttribute("referer")!=null)&&(!session.getAttribute("referer").equals("")))
                {
                    referer=session.getAttribute("referer").toString();
                }

                connection=new ConnectionFactory().getConnection();
                Statement statement=connection.createStatement();
                String sqlQuery=null;

                String id=req.getParameter("idd");
                if((id!=null)&&(!id.equals("")))
                {
                    sqlQuery="DELETE FROM \"Credits\" WHERE \"ID\"='"+id+"' AND \"WMID\"='"+wmid+"';";
                    int r=statement.executeUpdate(sqlQuery);
                    if(r>0)
                    {
                        req.setAttribute("addCreditInfo","<span class='info'>Кредит успешно удалён</span>");
                        req.getRequestDispatcher(referer).forward(req,resp);
                    }
                    else
                    {
                        req.setAttribute("addCreditInfo","<span class='error'>Кредит не найден</span>");
                        req.getRequestDispatcher(referer).forward(req,resp);
                    }
                }
            }
            catch(SQLException ex)
            {
                req.setAttribute("addCreditInfo","<span class='error'>Ошибка при запросе к базе данных</span>");
                req.getRequestDispatcher(referer).forward(req,resp);
            }
            catch(Exception ex)
            {
                req.setAttribute("addCreditInfo","<span class='error'>Неизвестная ошибка</span>");
                req.getRequestDispatcher(referer).forward(req,resp);
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
                    req.setAttribute("addCreditInfo","<span class='error'>Ошибка при закрытии соединения с базой данных</span>");
                    req.getRequestDispatcher(referer).forward(req,resp);
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
