/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_notes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class GetterBodyNoteAjax extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session=req.getSession(true);

        resp.setHeader("Cache-Control","no-cache");
        resp.setHeader("Pragma","no-cache");
        resp.setHeader("Content-Type","text/plain; charset=UTF-8");
        PrintWriter out=resp.getWriter();

        if(session.getAttribute("access")!=null)
        {
            String wmid=session.getAttribute("access").toString();

            String id=req.getParameter("id").trim();
            String tableName=req.getParameter("tablename").trim();
            //out.println("<?xml version='1.0' encoding='UTF-8'?>");
            if((id!=null)&&(!id.equals(""))&&(tableName!=null)&&(!tableName.equals("")))
            {
                Connection connection=null;

                try
                {
                    connection=new ConnectionFactory().getConnection();
                    Statement statement=connection.createStatement();

                    String temp="";
                    String sqlQuery="SELECT \"WMID\" FROM \""+tableName+"\" WHERE \"ID\"='"+id+"';";
                    ResultSet results=statement.executeQuery(sqlQuery);
                    if(results.next())
                    {
                        temp=results.getString("WMID").trim();
                    }
                    else
                    {
                        out.println("Запись не найдена");
                    }
                    if(wmid.equals(temp))
                    {
                        sqlQuery="SELECT \"Body\" FROM \""+tableName+"\" WHERE \"ID\"='"+id+"';";
                        results=statement.executeQuery(sqlQuery);
                        if(results.next())
                        {
                            out.println(results.getString("Body").trim());
                        }
                        else
                        {
                            out.println("Запись не найдена");
                        }
                    }
                    else
                    {
                        out.println("Запрашиваемая запись принадлежит другому пользователю");
                    }
                }
                catch(Exception ex)
                {
                    Logger.getLogger(GetterBodyNoteAjax.class.getName()).log(Level.SEVERE,null,ex);
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
                        Logger.getLogger(GetterBodyNoteAjax.class.getName()).log(Level.SEVERE,null,ex);
                    }
                }
            }
            else
            {
                out.println("");
            }
        }
    }
}