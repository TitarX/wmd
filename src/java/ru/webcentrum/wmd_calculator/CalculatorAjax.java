/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_calculator;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.webcentrum.wmd_data.SimpleCalendar;

/**
 *
 * @author TitarX
 */
public class CalculatorAjax extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        resp.setHeader("Cache-Control","no-cache");
        resp.setHeader("Pragma","no-cache");
        resp.setHeader("Content-Type","text/plain; charset=UTF-8");
        PrintWriter out=resp.getWriter();

        try
        {
            String typeCalculationParameter=req.getParameter("typeCalculation");
            String exSummParameter=req.getParameter("exSumm");
            String inSummParameter=req.getParameter("inSumm");
            String exDateParameter=req.getParameter("exDate");
            String inDateParameter=req.getParameter("inDate");
            String inFactNumberParameter=req.getParameter("inFactNumber");

//            System.out.println("typeCalculation="+typeCalculationParameter);
//            System.out.println("exSumm="+exSummParameter);
//            System.out.println("inSumm="+inSummParameter);
//            System.out.println("exDate="+exDateParameter);
//            System.out.println("inDate="+inDateParameter);
//            System.out.println("inFactNumber="+inFactNumberParameter);

            if((typeCalculationParameter!=null&&exSummParameter!=null&&inSummParameter!=null&&exDateParameter!=null&&inDateParameter!=null&&inFactNumberParameter!=null)
                    &&(!typeCalculationParameter.equals("")&&!exSummParameter.equals("")&&!inSummParameter.equals("")&&!exDateParameter.equals("")&&!inDateParameter.equals("")&&!inFactNumberParameter.equals("")))
            {
                NumberFormat resultFormat=NumberFormat.getInstance();
                resultFormat.setMaximumFractionDigits(2);

                int inFactNumber=Integer.parseInt(inFactNumberParameter);
                ArrayList<Double> inFactSumms=new ArrayList<Double>();
                ArrayList<Integer> returnsPeriods=new ArrayList<Integer>();

                double inFactSummsTotal=0;
                for(int i=1;i<=inFactNumber;i++)
                {
                    String inFactSummTemp=req.getParameter("inFactSumm"+i);
                    String inFactDateTemp=req.getParameter("inFactDate"+i);
                    if((inFactSummTemp!=null&&inFactDateTemp!=null)&&(!inFactSummTemp.equals("")&&!inFactDateTemp.equals("")))
                    {
                        double temp=Double.parseDouble(inFactSummTemp);
                        inFactSummsTotal+=temp;
                        inFactSumms.add(temp);
                        returnsPeriods.add(new SimpleCalendar().getBetweenDate(inDateParameter,inFactDateTemp));
                    }
                }

                int typeCalculation=Integer.parseInt(typeCalculationParameter);
                double exSumm=Double.parseDouble(exSummParameter);
                double inSumm=Double.parseDouble(inSummParameter);

                double delta=inSumm-exSumm;
                int period=new SimpleCalendar().getBetweenDate(exDateParameter,inDateParameter);
                if(period>0)
                {
                    if(delta>0)
                    {
                        if(inSumm>=inFactSummsTotal)
                        {
                            double percentInDay=(delta/period)/(exSumm/100);
                            double result=0;
                            if(typeCalculation==2)
                            {
                                for(int i=0;i<inFactSumms.size();i++)
                                {
                                    double inFactSumm=inFactSumms.get(i);
                                    int returnPeriod=returnsPeriods.get(i);
                                    result+=inFactSumm/100*percentInDay*returnPeriod;
                                }
                                out.println(resultFormat.format(result));
                            }
                            else if(typeCalculation==1)
                            {
                                for(int i=0;i<inFactSumms.size();i++)
                                {
                                    double inFactSumm=inFactSumms.get(i);
                                    double returnPeriod=returnsPeriods.get(i);

                                    double percentOfInSumm=inFactSumm/(inSumm/100);
                                    inFactSumm=exSumm/100*percentOfInSumm;

                                    result+=inFactSumm/100*percentInDay*returnPeriod;
                                }
                                out.println(resultFormat.format(result));
                            }
                            else
                            {
                                out.println("ошибка: не вено указан тип вычисления");
                            }
                        }
                        else
                        {
                            out.println("ошибка: возвращённая сумма превышает сумму возврата");
                        }
                    }
                    else
                    {
                        out.println("ошибка: сумма выдачи превышает сумму возврата, либо суммы равны");
                    }
                }
                else
                {
                    out.println("ошибка: дата выдачи превышает дату возврата, либо даты равны");
                }
            }
            else
            {
                out.println("ошибка: недостаточно данных для вычисления");
            }
        }
        catch(Exception ex)
        {
            Logger.getLogger(CalculatorAjax.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    private int getDay(String date)
    {
        Pattern pattern=Pattern.compile("[ ,./]");
        String[] temp=pattern.split(date);
        return Integer.parseInt(temp[0]);
    }

    private int getMonth(String date)
    {
        Pattern pattern=Pattern.compile("[ ,./]");
        String[] temp=pattern.split(date);
        return Integer.parseInt(temp[1]);
    }

    private int getYear(String date)
    {
        Pattern pattern=Pattern.compile("[ ,./]");
        String[] temp=pattern.split(date);
        return Integer.parseInt(temp[2]);
    }
}
