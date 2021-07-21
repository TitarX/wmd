/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_credits;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
import javax.servlet.http.HttpSession;
import ru.webcentrum.wmd_data.ConnectionFactory;
import ru.webcentrum.wmd_data.DataConvertor;
import ru.webcentrum.wmd_data.SimpleCalendar;

/**
 *
 * @author TitarX
 */
public class UpdateCredit extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //PrintWriter out=resp.getWriter();
        HttpSession session=req.getSession(true);

        if(session.getAttribute("access")!=null)
        {
            String wmid=session.getAttribute("access").toString();

            String referer="/cabinet/portfolio/credits/all.jsp";
            if((session.getAttribute("referer")!=null)&&(!session.getAttribute("referer").equals("")))
            {
                referer=session.getAttribute("referer").toString();
            }

            String beginDateParameter=req.getParameter("begin");
            String compensationMoneyParameter=req.getParameter("compensation");
            String endDateParameter=req.getParameter("end");
            String borrowerParameter=req.getParameter("borrower");
            String returnMoneyParameter=req.getParameter("sreturn");
            String requistionParameter=req.getParameter("nrequistion");
            String commentParameter=req.getParameter("comment");
            String creditMoneyParameter=req.getParameter("scredit");
            //String creditParameter=req.getParameter("ncredit");
            String factMoneyParameter=req.getParameter("return");
            String factDateParameter=req.getParameter("fact");
            String importantlyParameter=req.getParameter("importantly");
            if(beginDateParameter!=null&&compensationMoneyParameter!=null&&endDateParameter!=null&&borrowerParameter!=null&&returnMoneyParameter!=null&&requistionParameter!=null
                    &&commentParameter!=null&&creditMoneyParameter!=null&&factMoneyParameter!=null&&factDateParameter!=null)
            {
                beginDateParameter=beginDateParameter.trim();
                compensationMoneyParameter=compensationMoneyParameter.trim();
                endDateParameter=endDateParameter.trim();
                borrowerParameter=borrowerParameter.trim();
                returnMoneyParameter=returnMoneyParameter.trim();
                requistionParameter=requistionParameter.trim();
                commentParameter=commentParameter.trim();
                creditMoneyParameter=creditMoneyParameter.trim();
                factMoneyParameter=factMoneyParameter.trim();
                factDateParameter=factDateParameter.trim();

                if((!borrowerParameter.equals(""))&&(!requistionParameter.equals(""))&&(!creditMoneyParameter.equals(""))
                        &&(!returnMoneyParameter.equals(""))&&(!beginDateParameter.equals(""))&&(!endDateParameter.equals("")))
                {
                    if(commentParameter.equals(""))
                    {
                        commentParameter="Комментарий";
                    }

                    boolean isCorrect=true;
                    double creditMoney=0;
                    double returnMoney=0;
                    double compensationMoney=0;
                    double factMoney=0;
                    double deltaMoney=0;
                    int deltaDateBeginEnd=0;
                    int deltaDateBeginFact=0;
                    int deltaDateEndCurrent=0;

                    int importantly=0;
                    if(importantlyParameter!=null)
                    {
                        importantly=1;
                    }

                    SimpleCalendar calendar=new SimpleCalendar();

                    try
                    {
                        creditMoney=Double.parseDouble(creditMoneyParameter);
                    }
                    catch(NumberFormatException nfex)
                    {
                        req.setAttribute("addCreditInfo","<span class='error'>Указана некорректная сумма кредита</span>");
                        req.getRequestDispatcher(referer).forward(req,resp);
                        isCorrect=false;
                    }

                    try
                    {
                        returnMoney=Double.parseDouble(returnMoneyParameter);
                    }
                    catch(NumberFormatException nfex)
                    {
                        req.setAttribute("addCreditInfo","<span class='error'>Указана некорректная сумма возврата</span>");
                        req.getRequestDispatcher(referer).forward(req,resp);
                        isCorrect=false;
                    }

                    if(!beginDateParameter.matches("([0-9]{4})\\.([0-9]{2})\\.([0-9]{2})"))
                    {
                        req.setAttribute("addCreditInfo","<span class='error'>Указана некорректная дата начала кредита</span>");
                        req.getRequestDispatcher(referer).forward(req,resp);
                        isCorrect=false;
                    }

                    if(!endDateParameter.matches("([0-9]{4})\\.([0-9]{2})\\.([0-9]{2})"))
                    {
                        req.setAttribute("addCreditInfo","<span class='error'>Указана некорректная дата завершения кредита</span>");
                        req.getRequestDispatcher(referer).forward(req,resp);
                        isCorrect=false;
                    }

                    deltaMoney=returnMoney-creditMoney;

                    if(deltaMoney<=0)
                    {
                        req.setAttribute("addCreditInfo","<span class='error'>Сумма возврата должна быть больше суммы кредита</span>");
                        req.getRequestDispatcher(referer).forward(req,resp);
                        isCorrect=false;
                    }

                    try
                    {
                        deltaDateBeginEnd=calendar.getBetweenDate(beginDateParameter,endDateParameter);
                    }
                    catch(Exception ex)
                    {
                        Logger.getLogger(AddCredit.class.getName()).log(Level.SEVERE,null,ex);
                        req.setAttribute("addCreditInfo","<span class='error'>Произошла неизвестная ошибка при вычислении разницы дат</span>");
                        req.getRequestDispatcher(referer).forward(req,resp);
                        isCorrect=false;
                    }

                    if(deltaDateBeginEnd<=0)
                    {
                        req.setAttribute("addCreditInfo","<span class='error'>Дата завершения кредита должна быть больше даты начала кредита</span>");
                        req.getRequestDispatcher(referer).forward(req,resp);
                        isCorrect=false;
                    }

                    if(!factMoneyParameter.equals(""))
                    {
                        try
                        {
                            factMoney=Double.parseDouble(factMoneyParameter);
                        }
                        catch(NumberFormatException nfex)
                        {
                            req.setAttribute("addCreditInfo","<span class='error'>Указана некорректная фактическая сумма возврата</span>");
                            req.getRequestDispatcher(referer).forward(req,resp);
                            isCorrect=false;
                        }

                        if((returnMoney-factMoney)<0)
                        {
                            req.setAttribute("addCreditInfo","<span class='error'>Фактическая сумма возврата не может превышать сумму возврата</span>");
                            req.getRequestDispatcher(referer).forward(req,resp);
                            isCorrect=false;
                        }
                    }

                    if(!compensationMoneyParameter.equals(""))
                    {
                        try
                        {
                            compensationMoney=Double.parseDouble(compensationMoneyParameter);
                        }
                        catch(NumberFormatException nfex)
                        {
                            req.setAttribute("addCreditInfo","<span class='error'>Указана некорректная сумма компенсации</span>");
                            req.getRequestDispatcher(referer).forward(req,resp);
                            isCorrect=false;
                        }
                    }

                    if(!factDateParameter.equals(""))
                    {
                        if(!factDateParameter.matches("([0-9]{4})\\.([0-9]{2})\\.([0-9]{2})"))
                        {
                            req.setAttribute("addCreditInfo","<span class='error'>Указана некорректная фактическая дата возврата</span>");
                            req.getRequestDispatcher(referer).forward(req,resp);
                            isCorrect=false;
                        }

                        try
                        {
                            deltaDateBeginFact=calendar.getBetweenDate(beginDateParameter,factDateParameter);
                        }
                        catch(Exception ex)
                        {
                            Logger.getLogger(AddCredit.class.getName()).log(Level.SEVERE,null,ex);
                            req.setAttribute("addCreditInfo","<span class='error'>Произошла неизвестная ошибка при вычислении разницы дат</span>");
                            req.getRequestDispatcher(referer).forward(req,resp);
                            isCorrect=false;
                        }

                        if(deltaDateBeginFact<=0)
                        {
                            req.setAttribute("addCreditInfo","<span class='error'>Фактическая дата возврата должна быть больше даты начала кредита</span>");
                            req.getRequestDispatcher(referer).forward(req,resp);
                            isCorrect=false;
                        }
                    }

                    Date date=new Date();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");
                    String currentDate=simpleDateFormat.format(date);

                    int status=1;
                    if((returnMoney==factMoney)&&(factDateParameter.equals("")))
                    {
                        factDateParameter=currentDate;
                    }
                    if(!factDateParameter.equals(""))
                    {
                        factMoneyParameter=returnMoneyParameter;
                        status=2;
                    }
                    else
                    {
                        try
                        {
                            deltaDateEndCurrent=calendar.getBetweenDate(endDateParameter,currentDate);
                        }
                        catch(Exception ex)
                        {
                            Logger.getLogger(AddCredit.class.getName()).log(Level.SEVERE,null,ex);
                            req.setAttribute("addCreditInfo","<span class='error'>Произошла неизвестная ошибка при вычислении разницы дат</span>");
                            req.getRequestDispatcher(referer).forward(req,resp);
                            isCorrect=false;
                        }

                        if(deltaDateEndCurrent>0)
                        {
                            status=3;
                        }
                    }

                    double percentInDay=(deltaMoney/deltaDateBeginEnd)/(creditMoney/100);

                    //

                    Connection connection=null;
                    try
                    {
                        connection=new ConnectionFactory().getConnection();
                        Statement statement=connection.createStatement();
                        String sqlQuery=null;

                        if(isCorrect)
                        {
                            String id=req.getParameter("ida");
                            if((id!=null)||(!id.equals("")))
                            {
                                String sqlQueryPart1=
                                        "UPDATE \"Credits\" SET "
                                        +"\"Nrequest\"='"+DataConvertor.fixApostrophe(requistionParameter)+"'"
                                        +",\"Creditsumm\"='"+creditMoneyParameter+"'"
                                        +",\"Returnsumm\"='"+returnMoneyParameter+"'"
                                        +",\"Begindate\"='"+calendar.YMDtoMDY(beginDateParameter)+"'"
                                        +",\"Enddate\"='"+calendar.YMDtoMDY(endDateParameter)+"'"
                                        +",\"Comment\"='"+DataConvertor.fixApostrophe(commentParameter)+"'"
                                        +",\"Creditterm\"='"+deltaDateBeginEnd+"'"
                                        +",\"Percentcredit\"='"+percentInDay+"'"
                                        +",\"Status\"='"+status+"'"
                                        +",\"Borrower\"='"+DataConvertor.fixApostrophe(borrowerParameter)+"'"
                                        +",\"Importantly\"='"+importantly+"'";

                                String sqlQueryPart2="";
                                if(!factDateParameter.equals(""))
                                {
                                    sqlQueryPart2=",\"Factdate\"='"+calendar.YMDtoMDY(factDateParameter)+"'";
                                }
                                String sqlQueryPart3="";
                                if(!factMoneyParameter.equals(""))
                                {
                                    sqlQueryPart3=",\"Return\"='"+factMoneyParameter+"'";
                                }
                                String sqlQueryPart4="";
                                if(!compensationMoneyParameter.equals(""))
                                {
                                    sqlQueryPart4=",\"Refund\"='"+compensationMoneyParameter+"'";
                                }
                                String sqlQueryPart5="WHERE \"ID\"='"+id+"' AND \"WMID\"='"+wmid+"';";

                                StringBuilder sqlQueryBuilder=new StringBuilder();
                                sqlQueryBuilder.append(sqlQueryPart1);
                                sqlQueryBuilder.append(sqlQueryPart2);
                                sqlQueryBuilder.append(sqlQueryPart3);
                                sqlQueryBuilder.append(sqlQueryPart4);
                                sqlQueryBuilder.append(sqlQueryPart5);
                                sqlQuery=sqlQueryBuilder.toString();

                                int r=statement.executeUpdate(sqlQuery);
                                if(r>0)
                                {
                                    switch(status)
                                    {
                                        case 1:
                                            req.setAttribute("addCreditInfo","<span class='info'>Кредит успешно обновлён</span>");
                                            req.getRequestDispatcher("/cabinet/portfolio/credits/yielded.jsp").forward(req,resp);
                                            break;
                                        case 2:
                                            req.setAttribute("addCreditInfo","<span class='info'>Кредит успешно обновлён</span>");
                                            req.getRequestDispatcher("/cabinet/portfolio/credits/returned.jsp").forward(req,resp);
                                            break;
                                        case 3:
                                            req.setAttribute("addCreditInfo","<span class='info'>Кредит успешно обновлён</span>");
                                            req.getRequestDispatcher("/cabinet/portfolio/credits/delay.jsp").forward(req,resp);
                                            break;
                                        default:
                                            req.setAttribute("addCreditInfo","<span class='info'>Кредит успешно обновлён</span>");
                                            req.getRequestDispatcher("/cabinet/portfolio/credits/all.jsp").forward(req,resp);
                                    }
                                }
                                else
                                {
                                    req.setAttribute("addCreditInfo","<span class='error'>Не удалось сохранить данные кредита</span>");
                                    req.getRequestDispatcher(referer).forward(req,resp);
                                }
                            }
                            else
                            {
                                req.setAttribute("addCreditInfo","<span class='error'>Отсутствует идентификатор кредита</span>");
                                req.getRequestDispatcher(referer).forward(req,resp);
                            }
                        }
                    }
                    catch(Exception ex)
                    {
                        Logger.getLogger(AddCredit.class.getName()).log(Level.SEVERE,null,ex);
                        req.setAttribute("addCreditInfo","<span class='error'>Не удалось сохранить данные кредита</span>");
                        req.getRequestDispatcher(referer).forward(req,resp);
                    }
                    if(connection!=null)
                    {
                        try
                        {
                            connection.close();
                        }
                        catch(SQLException ex)
                        {
                            Logger.getLogger(AddCredit.class.getName()).log(Level.SEVERE,null,ex);
                        }
                    }
                }
                else
                {
                    req.setAttribute("addCreditInfo","<span class='error'>Не все необходимые поля были заполнены</span>");
                    req.getRequestDispatcher(referer).forward(req,resp);
                }
            }
            else
            {
                req.setAttribute("addCreditInfo","<span class='error'>Недостаточно параметров</span>");
                req.getRequestDispatcher(referer).forward(req,resp);
            }
        }
        else
        {
            session.invalidate();
            req.setAttribute("errorLogin","23");
            req.getRequestDispatcher("/errorLogin.jsp").forward(req,resp);
        }
    }

    private void writeResponse(PrintWriter out)
    {
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"refresh\" content=\"0; url=/WMD/cabinet/portfolio/notebook.jsp\"/>");
        out.println("<title></title>");
        out.println("</head>");
        out.println("<body>");
        out.println("</body>");
        out.println("</html>");
    }
}
