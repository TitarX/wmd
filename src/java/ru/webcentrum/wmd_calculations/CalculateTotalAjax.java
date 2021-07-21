/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_calculations;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.webcentrum.wmd_data.ConnectionFactory;
import ru.webcentrum.wmd_data.SimpleCalendar;

/**
 *
 * @author TitarX
 */
public class CalculateTotalAjax extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        resp.setHeader("Cache-Control","no-cache");
        resp.setHeader("Pragma","no-cache");
        resp.setHeader("Content-Type","text/xml; charset=UTF-8");

        PrintWriter out=resp.getWriter();

        HttpSession session=req.getSession(true);

        if(session.getAttribute("access")!=null)
        {
            Connection connection=null;

            try
            {
                String wmid=session.getAttribute("access").toString();
                connection=new ConnectionFactory().getConnection();
                Statement statement=connection.createStatement();
                String sqlQuery=null;

                String from=req.getParameter("from");
                String to=req.getParameter("to");

                double issued=0;
                double toReturn=0;
                double returned=0;
                double delayed=0;
                double commissionWM=0;
                double commissionCM=0;
                double refund=0;
                double profit=0;

                double partialReturn=0;

                if((from!=null)&&(to!=null))
                {
                    if((!from.equals(""))&&(!to.equals("")))
                    {
                        if(to.equals("0"))
                        {
                            Date date=new Date();
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");
                            to=simpleDateFormat.format(date);
                        }

                        SimpleCalendar calendar=new SimpleCalendar();
                        from=calendar.YMDtoMDY(from);
                        to=calendar.YMDtoMDY(to);

                        sqlQuery="SELECT \"Creditsumm\" FROM \"Credits\""
                                +"WHERE \"WMID\"='"+wmid+"' AND"
                                + "((\"Status\"='2' AND (\"Factdate\" BETWEEN '"+from+"' AND '"+to+"')) OR (\"Status\"='3' AND (\"Enddate\" BETWEEN '"+from+"' AND '"+to+"')));";
                        ResultSet results=statement.executeQuery(sqlQuery);
                        while(results.next())
                        {
                            issued+=results.getDouble("Creditsumm");
                        }
                        
                        System.out.println(issued);

                        //

                        sqlQuery="SELECT \"Returnsumm\" FROM \"Credits\""
                                +"WHERE \"WMID\"='"+wmid+"' AND"
                                + "((\"Status\"='2' AND (\"Factdate\" BETWEEN '"+from+"' AND '"+to+"')) OR (\"Status\"='3' AND (\"Enddate\" BETWEEN '"+from+"' AND '"+to+"')));";
                        results=statement.executeQuery(sqlQuery);
                        while(results.next())
                        {
                            toReturn+=results.getDouble("Returnsumm");
                        }

                        //

                        sqlQuery="SELECT \"Returnsumm\" FROM \"Credits\""
                                +"WHERE \"WMID\"='"+wmid+"' AND \"Status\"='2' AND (\"Factdate\" BETWEEN '"+from+"' AND '"+to+"');";
                        results=statement.executeQuery(sqlQuery);
                        while(results.next())
                        {
                            returned+=results.getDouble("Returnsumm");
                        }

                        //

                        sqlQuery="SELECT \"Returnsumm\" FROM \"Credits\""
                                +"WHERE \"WMID\"='"+wmid+"' AND \"Status\"='3' AND (\"Enddate\" BETWEEN '"+from+"' AND '"+to+"');";
                        results=statement.executeQuery(sqlQuery);
                        while(results.next())
                        {
                            delayed+=results.getDouble("Returnsumm");
                        }

                        //

                        sqlQuery="SELECT \"Refund\" FROM \"Credits\""
                                +"WHERE \"WMID\"='"+wmid+"' AND \"Status\"='2' AND (\"Factdate\" BETWEEN '"+from+"' AND '"+to+"');";
                        results=statement.executeQuery(sqlQuery);
                        while(results.next())
                        {
                            refund+=results.getDouble("Refund");
                        }

                        //

                        sqlQuery="SELECT \"Return\" FROM \"Credits\""
                                +"WHERE \"WMID\"='"+wmid+"' AND \"Status\"='3' AND (\"Enddate\" BETWEEN '"+from+"' AND '"+to+"');";
                        results=statement.executeQuery(sqlQuery);
                        while(results.next())
                        {
                            partialReturn+=results.getDouble("Return");
                        }

                        //

                        returned+=partialReturn;
                        delayed-=partialReturn;

                        commissionWM=issued/100*0.8;
                        commissionCM=returned/100*0.1;
                        profit=returned-issued+partialReturn+refund-commissionWM-commissionCM;

                        //

                        NumberFormat resultFormat=NumberFormat.getInstance();
                        resultFormat.setMaximumFractionDigits(2);

                        out.println("<?xml version='1.0' encoding='UTF-8'?>");
                        out.println("<root>");

                        out.println("<issued>"+resultFormat.format(issued)+"</issued>");
                        out.println("<toReturn>"+resultFormat.format(toReturn)+"</toReturn>");
                        out.println("<returned>"+resultFormat.format(returned)+"</returned>");
                        out.println("<delayed>"+resultFormat.format(delayed)+"</delayed>");
                        out.println("<commissionWM>"+resultFormat.format(commissionWM)+"</commissionWM>");
                        out.println("<commissionCM>"+resultFormat.format(commissionCM)+"</commissionCM>");
                        out.println("<refund>"+resultFormat.format(refund)+"</refund>");
                        out.println("<profit>"+resultFormat.format(profit)+"</profit>");

                        out.println("</root>");
                    }
                }
            }
            catch(Exception ex)
            {
                Logger.getLogger(CalculateTotalAjax.class.getName()).log(Level.SEVERE,null,ex);
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
                    Logger.getLogger(CalculateTotalAjax.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        }
    }
}