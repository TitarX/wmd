/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_credits;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.webcentrum.wmd_data.ConnectionFactory;
import ru.webcentrum.wmd_data.DataConvertor;

/**
 *
 * @author TitarX
 */
public class GetterValueCreditAjax extends HttpServlet
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

                String id=req.getParameter("id");
                if((id!=null)&&(!id.equals("")))
                {
                    out.println("<?xml version='1.0' encoding='UTF-8'?>");
                    out.println("<root>");

                    sqlQuery="SELECT \"Borrower\",\"Ncredit\",\"Nrequest\",\"Creditsumm\",\"Returnsumm\""
                            +",\"Begindate\",\"Enddate\",\"Factdate\",\"Return\",\"Refund\",\"Comment\",\"Importantly\""
                            +"FROM \"Credits\" WHERE \"ID\"='"+id+"' AND \"WMID\"='"+wmid+"'";
                    ResultSet results=statement.executeQuery(sqlQuery);
                    if(results.next())
                    {
                        String borrower=null;
                        String ncredit=null;
                        String nrequest=null;
                        String creditsumm=null;
                        String returnsumm=null;
                        String begindate=null;
                        String enddate=null;

                        String factdate=null;
                        String returN=null;
                        String refund=null;
                        String comment=null;

                        int importantly=0;

                        NumberFormat resultFormat=NumberFormat.getInstance();
                        resultFormat.setMaximumFractionDigits(2);

                        SimpleDateFormat simpleDateFormatResults=new SimpleDateFormat("yyyy.MM.dd");

                        borrower=results.getString("Borrower");
                        ncredit=results.getString("Ncredit");
                        nrequest=results.getString("Nrequest");
                        creditsumm=resultFormat.format(results.getDouble("Creditsumm"));
                        creditsumm=creditsumm.replaceAll(",",".");
                        creditsumm=DataConvertor.fixReal(creditsumm);
                        returnsumm=resultFormat.format(results.getDouble("Returnsumm"));
                        returnsumm=returnsumm.replaceAll(",",".");
                        returnsumm=DataConvertor.fixReal(returnsumm);
                        begindate=simpleDateFormatResults.format(results.getDate("Begindate"));
                        enddate=simpleDateFormatResults.format(results.getDate("Enddate"));

                        if(results.getDate("Factdate")!=null)
                        {
                            factdate=simpleDateFormatResults.format(results.getDate("Factdate"));
                        }
                        else
                        {
                            factdate="0";
                        }
                        if(results.getDouble("Return")!=0)
                        {
                            returN=resultFormat.format(results.getDouble("Return"));
                            returN=returN.replaceAll(",",".");
                            returN=DataConvertor.fixReal(returN);
                        }
                        else
                        {
                            returN="0";
                        }
                        if(results.getDouble("Refund")!=0)
                        {
                            refund=resultFormat.format(results.getDouble("Refund"));
                            refund=refund.replaceAll(",",".");
                            refund=DataConvertor.fixReal(refund);
                        }
                        else
                        {
                            refund="0";
                        }
                        if(results.getString("Comment")!=null)
                        {
                            comment=results.getString("Comment");
                        }
                        else
                        {
                            comment="0";
                        }

                        importantly=results.getInt("Importantly");

                        out.println("<borrower>"+borrower+"</borrower>");
                        out.println("<ncredit>"+ncredit+"</ncredit>");
                        out.println("<nrequest>"+nrequest+"</nrequest>");
                        out.println("<creditsumm>"+creditsumm+"</creditsumm>");
                        out.println("<returnsumm>"+returnsumm+"</returnsumm>");
                        out.println("<begindate>"+begindate+"</begindate>");
                        out.println("<enddate>"+enddate+"</enddate>");
                        out.println("<factdate>"+factdate+"</factdate>");
                        out.println("<returN>"+returN+"</returN>");
                        out.println("<refund>"+refund+"</refund>");
                        out.println("<comment>"+comment+"</comment>");
                        out.println("<importantly>"+importantly+"</importantly>");
                    }

                    out.println("</root>");
                }
            }
            catch(Exception ex)
            {
                Logger.getLogger(GetterValueCreditAjax.class.getName()).log(Level.SEVERE,null,ex);
            }
            finally
            {
                if(connection!=null)
                {
                    try
                    {
                        connection.close();
                    }
                    catch(SQLException ex)
                    {
                        Logger.getLogger(StatusRecalculation.class.getName()).log(Level.SEVERE,null,ex);
                    }
                }
            }
        }
    }
}
