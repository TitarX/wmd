/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_credits;

import java.util.Date;
import java.util.Timer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author TitarX
 */
public class TimerServlet extends HttpServlet
{

    @Override
    public void init() throws ServletException
    {
        Date date=new Date();
        date.setHours(0);
        date.setMinutes(5);

        StatusRecalculation sr=new StatusRecalculation();
        Timer timer=new Timer();
        timer.schedule(sr,date,86400000L);
    }
}
