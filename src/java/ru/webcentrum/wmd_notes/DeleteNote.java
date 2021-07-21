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
public class DeleteNote extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(true);
        //PrintWriter out=resp.getWriter();

        if (session.getAttribute("access") != null)
        {
            String tableName = req.getParameter("tablename").trim();
            if (tableName != null && (tableName.equals("Notes") || (tableName.equals("Blacklist"))))
            {

                if (session.getAttribute("titlePre".concat(tableName)) != null)
                {
                    session.removeAttribute("titlePre".concat(tableName));
                }
                if (session.getAttribute("bodyPre".concat(tableName)) != null)
                {
                    session.removeAttribute("bodyPre".concat(tableName));
                }

                Connection connection = null;

                try
                {
                    String wmid = session.getAttribute("access").toString();
                    connection = new ConnectionFactory().getConnection();
                    Statement statement = connection.createStatement();
                    String sqlQuery = null;

                    String id = req.getParameter("idd").trim();
                    if ((id != null) && (!id.equals("")))
                    {
                        String temp = "";
                        sqlQuery = "SELECT \"WMID\" FROM \"" + tableName + "\" WHERE \"ID\"='" + id + "';";
                        ResultSet results = statement.executeQuery(sqlQuery);
                        if (results.next())
                        {
                            temp = results.getString("WMID").trim();
                        }
                        if (wmid.equals(temp))
                        {
                            sqlQuery = "DELETE FROM \"" + tableName + "\" WHERE \"ID\"='" + id + "';";
                            int r = statement.executeUpdate(sqlQuery);
                            if (r > 0)
                            {
                                req.setAttribute("notesInfo", "<span class='info'>Записка успешно удалена</span>");
                                req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                                //writeResponse(out);
                            }
                            else
                            {
                                req.setAttribute("notesInfo", "<span class='error'>Не удалось удалить запись</span>");
                                req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                                //writeResponse(out);
                            }
                        }
                        else
                        {
                            req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                            //writeResponse(out);
                        }
                    }
                    else
                    {
                        req.setAttribute("notesInfo", "<span class='error'>Недостаточно параметров</span>");
                        req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                    }
                }
                catch (SQLException ex)
                {
                    req.setAttribute("notesInfo", "<span class='error'>Ошибка при запросе к базе данных</span>");
                    req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                }
                catch (Exception ex)
                {
                    req.setAttribute("notesInfo", "<span class='error'>Неизвестная ошибка</span>");
                    req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                }
                finally
                {
                    try
                    {
                        if (connection != null && !connection.isClosed())
                        {
                            connection.close();
                        }
                    }
                    catch (SQLException ex)
                    {
                        req.setAttribute("notesInfo", "<span class='error'>Ошибка при закрытии соединения с базой данных</span>");
                        req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                    }
                }
            }
            else
            {
                req.setAttribute("errorLogin", "26");
                req.getRequestDispatcher("/errorLogin.jsp").forward(req, resp);
            }
        }
        else
        {
            session.invalidate();
            req.setAttribute("errorLogin", "23");
            req.getRequestDispatcher("/errorLogin.jsp").forward(req, resp);
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
