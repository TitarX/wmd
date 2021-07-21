/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_credits;

import java.io.IOException;
import java.io.OutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import ru.webcentrum.wmd_data.ConnectionFactory;

/**
 *
 * @author TitarX
 */
public class ExportCredits extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session=req.getSession(true);

        if(session.getAttribute("access")!=null)
        {
            Connection connection=null;

            try
            {
                String wmid=session.getAttribute("access").toString();
                String from=req.getParameter("from");
                String to=req.getParameter("to");
                String typePeriod=req.getParameter("typePeriod");
                String page=req.getParameter("page");

                Date date=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");

                String betweenDate="";
                if((from!=null&&to!=null&&typePeriod!=null&&page!=null)
                        &&(!from.equals("")&&!to.equals("")&&!typePeriod.equals("")&&!page.equals(""))
                        &&(from.matches("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2}")&&to.matches("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2}")&&typePeriod.matches("[1-3]")))
                {
                    if(to.equals("0"))
                    {
                        to=simpleDateFormat.format(date);
                    }
                    betweenDate="AND (\"Begindate\" BETWEEN '"+from+"' AND '"+to+"') ";
                }
                else
                {
                    typePeriod="1";
                }

                String status="";
                int pageInt=Integer.parseInt(page);
                switch(pageInt)
                {
                    case 1:
                        status="AND \"Status\"='1' ";
                        break;
                    case 2:
                        status="AND \"Status\"='2' ";
                        break;
                    case 3:
                        status="AND \"Status\"='3' ";
                }

                connection=new ConnectionFactory().getConnection();
                Statement statement=connection.createStatement();
                String sqlQuery="SELECT * FROM \"Credits\" WHERE \"WMID\"='"+wmid+"' "+status+betweenDate+"ORDER BY \"Importantly\" DESC,\"Begindate\" DESC;";
                ResultSet results=statement.executeQuery(sqlQuery);

                int typePeriodInt=Integer.parseInt(typePeriod);
                switch(typePeriodInt)
                {
                    case 1:
                        typePeriod="За весь период";
                        break;
                    case 2:
                        typePeriod="До "+to;
                        break;
                    case 3:
                        typePeriod="От "+from+" до "+to;
                }

                HSSFWorkbook workbook=new HSSFWorkbook();
                HSSFSheet sheet=workbook.createSheet(typePeriod);

                HSSFRow row=null;
                HSSFCell cell=null;
                HSSFRichTextString text=null;
                HSSFFont font=null;
                HSSFCellStyle style=null;

                style=workbook.createCellStyle();
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setFillForegroundColor(HSSFColor.BLUE.index);
                style.setFillBackgroundColor(HSSFColor.BLUE.index);
                style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

                font=workbook.createFont();
                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                font.setColor(HSSFColor.WHITE.index);
                style.setFont(font);

                String[] strings=
                {
                    "!","№ кредита","№ заявки","Заёмщик","Сумма кредита","Сумма возврата","Возвращено","Компенсация",
                    "% кредита","Дата начала","Дата завершения","Дата возврата","Срок кредита","Комментарий"
                };
                row=sheet.createRow(0);
                for(int i=0;i<strings.length;i++)
                {
                    if(i==0)
                    {
                        sheet.setColumnWidth(i,800);
                    }
                    else
                    {
                        sheet.setColumnWidth(i,4500);
                    }
                    cell=row.createCell(i);
                    text=new HSSFRichTextString(strings[i]);
                    cell.setCellValue(text);
                    cell.setCellStyle(style);
                }

                NumberFormat resultFormat=NumberFormat.getInstance();
                resultFormat.setMaximumFractionDigits(2);

                int i=0;
                while(results.next())
                {
                    short colorStatus=HSSFColor.BLACK.index;;
                    switch(results.getInt("Status"))
                    {
                        case 1:
                            colorStatus=HSSFColor.BLACK.index;
                            break;
                        case 2:
                            colorStatus=HSSFColor.GREEN.index;
                            break;
                        case 3:
                            colorStatus=HSSFColor.RED.index;
                    }
                    style=workbook.createCellStyle();
                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    font=workbook.createFont();
                    font.setColor(colorStatus);
                    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                    style.setFont(font);

                    row=sheet.createRow(++i);

                    cell=row.createCell(0);
                    if(results.getInt("Importantly")==0)
                    {
                        text=new HSSFRichTextString("");
                    }
                    else
                    {
                        text=new HSSFRichTextString("!");
                    }
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    style=workbook.createCellStyle();
                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    font=workbook.createFont();
                    font.setColor(colorStatus);
                    style.setFont(font);

                    cell=row.createCell(1);
                    text=new HSSFRichTextString(results.getString("Ncredit"));
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(2);
                    text=new HSSFRichTextString(results.getString("Nrequest"));
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(3);
                    text=new HSSFRichTextString(results.getString("Borrower"));
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(4);
                    text=new HSSFRichTextString(resultFormat.format(results.getDouble("Creditsumm")));
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(5);
                    text=new HSSFRichTextString(resultFormat.format(results.getDouble("Returnsumm")));
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(6);
                    if(results.getDouble("Return")!=0)
                    {
                        text=new HSSFRichTextString(resultFormat.format(results.getDouble("Return")));
                    }
                    else
                    {
                        text=new HSSFRichTextString("");
                    }
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(7);
                    if(results.getDouble("Refund")!=0)
                    {
                        text=new HSSFRichTextString(resultFormat.format(results.getDouble("Refund")));
                    }
                    else
                    {
                        text=new HSSFRichTextString("");
                    }
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(8);
                    text=new HSSFRichTextString(resultFormat.format(results.getDouble("Percentcredit")));
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(9);
                    text=new HSSFRichTextString(simpleDateFormat.format(results.getDate("Begindate")));
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(10);
                    text=new HSSFRichTextString(simpleDateFormat.format(results.getDate("Enddate")));
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(11);
                    if(results.getDate("Factdate")!=null)
                    {
                        text=new HSSFRichTextString(simpleDateFormat.format(results.getDate("Factdate")));
                    }
                    else
                    {
                        text=new HSSFRichTextString("");
                    }
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(12);
                    text=new HSSFRichTextString(String.valueOf(results.getInt("Creditterm")));
                    cell.setCellValue(text);
                    cell.setCellStyle(style);

                    cell=row.createCell(13);
                    if((results.getString("Comment")!=null)&&(!results.getString("Comment").equals("Комментарий")))
                    {
                        text=new HSSFRichTextString(results.getString("Comment"));
                    }
                    else
                    {
                        text=new HSSFRichTextString("");
                    }
                    cell.setCellValue(text);
                    cell.setCellStyle(style);
                }
                connection.close();

                switch(pageInt)
                {
                    case 1:
                        page="Issued_credits_";
                        break;
                    case 2:
                        page="Returned_credits_";
                        break;
                    case 3:
                        page="Delayed_credits_";
                        break;
                    default:
                        page="All_credits_";
                }
                String fileName=page+wmid+"_("+simpleDateFormat.format(date)+")";
                resp.setContentType("application/vnd.ms-excel");
                resp.setHeader("Content-Disposition","attachment;filename="+fileName+".xls");
                OutputStream out=resp.getOutputStream();
                workbook.write(out);
                out.close();
            }
            catch(Exception ex)
            {
                Logger.getLogger(ExportCredits.class.getName()).log(Level.SEVERE,null,ex);
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
