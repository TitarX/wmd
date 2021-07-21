/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_access;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TitarX
 */
public class Access implements Filter
{

    FilterConfig config=null;

    public void init(FilterConfig filterConfig) throws ServletException
    {
        config=filterConfig;
    }

    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException,ServletException
    {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpSession session=req.getSession(true);
        if(session.getAttribute("access")==null)
        {
            req.setAttribute("errorLogin","23");
            req.getRequestDispatcher("/errorLogin.jsp").forward(request,response);
        }
        else
        {
            chain.doFilter(request,response);
        }
    }

    public void destroy()
    {
        config=null;
    }
}
