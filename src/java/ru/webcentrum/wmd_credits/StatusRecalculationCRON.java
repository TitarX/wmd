/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_credits;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.webcentrum.wmd_data.ConnectionFactory;
import ru.webcentrum.wmd_data.SimpleCalendar;

/**
 *
 * @author TitarX
 */
public class StatusRecalculationCRON extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        Connection connection=null;
        try
        {
            connection=new ConnectionFactory().getConnection();
            Statement statement=connection.createStatement();
            String sqlQuery=null;

            SimpleCalendar calendar=new SimpleCalendar();
            Date date=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");
            String currentDate=simpleDateFormat.format(date);

            String endDate=null;
            int id=0;
            int differenceDate=0;

            sqlQuery="SELECT \"ID\",\"Enddate\" FROM \"Credits\" WHERE \"Status\"='1';";
            ResultSet results=statement.executeQuery(sqlQuery);
            while(results.next())
            {
                id=results.getInt("ID");
                endDate=simpleDateFormat.format(results.getDate("Enddate"));

                differenceDate=calendar.getBetweenDate(endDate,currentDate);
                if(differenceDate>0)
                {
                    sqlQuery="UPDATE \"Credits\" SET \"Status\"='3' WHERE \"ID\"='"+id+"';";
                    statement.executeUpdate(sqlQuery);
                }
            }
        }
        catch(Exception ex)
        {
            Logger.getLogger(StatusRecalculation.class.getName()).log(Level.SEVERE,null,ex);
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
