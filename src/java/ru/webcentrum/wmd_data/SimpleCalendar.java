/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_data;

import java.util.GregorianCalendar;
import java.util.regex.Pattern;

//Для формата yyyy.MM.dd
/**
 *
 * @author TitarX
 */
public class SimpleCalendar
{

    public int getBetweenDate(String beginDate,String endDate) throws Exception
    {
        int beginYear=getYear(beginDate);
        int beginMonth=getMonth(beginDate);
        int beginDay=getDay(beginDate);

        int endYear=getYear(endDate);
        int endMonth=getMonth(endDate);
        int endDay=getDay(endDate);

        if(endYear>=beginYear)
        {
            GregorianCalendar calendar=new GregorianCalendar();
            int difference=0;
            int differenceYear=endYear-beginYear;

            if(differenceYear>0)
            {
                difference=differenceYear*365;
                for(int i=0;i<differenceYear;i++)
                {
                    if(calendar.isLeapYear(beginYear+i))
                    {
                        ++difference;
                    }
                }

                if(beginMonth>1)
                {
                    for(int i=1;i<beginMonth;i++)
                    {
                        difference-=getCountDayOfMonth(i,beginYear);
                    }
                }

                difference-=beginDay;

                if(endMonth>1)
                {
                    for(int i=1;i<endMonth;i++)
                    {
                        difference+=getCountDayOfMonth(i,endYear);
                    }
                }

                difference+=endDay;
            }
            else
            {
                if(endMonth>=beginMonth)
                {
                    int differenceMonth=endMonth-beginMonth;
                    if(differenceMonth>0)
                    {
                        for(int i=beginMonth;i<endMonth;i++)
                        {
                            difference+=getCountDayOfMonth(i,beginYear);
                        }
                        difference-=beginDay;
                        difference+=endDay;
                    }
                    else
                    {
                        if(endDay>beginDay)
                        {
                            difference-=beginDay;
                            difference+=endDay;
                        }
                        else
                        {
                            return 0;
                        }
                    }
                }
                else
                {
                    return 0;
                }
            }

            if(difference>0)
            {
                return difference;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return 0;
        }
    }

    public int getCountDayOfMonth(int month,int year) throws Exception
    {
        GregorianCalendar calendar=new GregorianCalendar();
        switch(month)
        {
            case 1:
                return 31;
            case 2:
                if(calendar.isLeapYear(year))
                {
                    return 29;
                }
                else
                {
                    return 28;
                }
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
            default:
                return 30;
        }
    }

    public int getDay(String date) throws Exception
    {
        Pattern pattern=Pattern.compile("[ ,./]");
        String[] temp=pattern.split(date);
        return Integer.parseInt(temp[2]);
    }

    public int getMonth(String date) throws Exception
    {
        Pattern pattern=Pattern.compile("[ ,./]");
        String[] temp=pattern.split(date);
        return Integer.parseInt(temp[1]);
    }

    public int getYear(String date) throws Exception
    {
        Pattern pattern=Pattern.compile("[ ,./]");
        String[] temp=pattern.split(date);
        return Integer.parseInt(temp[0]);
    }

    public String YMDtoMDY(String ymd) throws Exception
    {
        int day=getDay(ymd);
        int month=getMonth(ymd);
        int year=getYear(ymd);
        StringBuilder mdy=new StringBuilder();
        mdy.append(month);
        mdy.append(".");
        mdy.append(day);
        mdy.append(".");
        mdy.append(year);
        return mdy.toString();
    }
}
