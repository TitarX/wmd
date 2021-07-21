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
import ru.webcentrum.wmd_data.DataConvertor;

/**
 *
 * @author TitarX
 */
public class WriteNote extends HttpServlet
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

                String title = req.getParameter("title").trim();
                String body = req.getParameter("body").trim();
                String id = req.getParameter("idw").trim();

                if (title != null && body != null && id != null)
                {
                    if (!title.equals(""))
                    {
                        Connection connection = null;

                        try
                        {
                            String wmid = session.getAttribute("access").toString();
                            connection = new ConnectionFactory().getConnection();
                            Statement statement = connection.createStatement();

                            String idThisTitle = "";
                            String sqlQuery = "SELECT \"ID\" FROM \"" + tableName + "\" WHERE \"WMID\"='" + wmid + "' AND \"Title\" LIKE '" + title + "';";
                            ResultSet results = statement.executeQuery(sqlQuery);
                            if (results.next())
                            {
                                idThisTitle = results.getString("ID").trim();
                            }

                            if (id.equals(""))
                            {
                                String titlePre = "";
                                String bodyPre = "";

                                if (session.getAttribute("titlePre".concat(tableName)) != null)
                                {
                                    titlePre = session.getAttribute("titlePre".concat(tableName)).toString();
                                }
                                if (session.getAttribute("bodyPre".concat(tableName)) != null)
                                {
                                    bodyPre = session.getAttribute("bodyPre".concat(tableName)).toString();
                                }

                                if ((!titlePre.equals(title)) || (!bodyPre.equals(body)))
                                {
                                    if (idThisTitle.equals(""))
                                    {
                                        sqlQuery =
                                                "INSERT INTO \"" + tableName + "\"(\"WMID\", \"Title\", \"Body\")"
                                                + "VALUES ('" + wmid + "','"
                                                + DataConvertor.fixApostrophe(title) + "','"
                                                + DataConvertor.fixApostrophe(body) + "');";
                                        int r = statement.executeUpdate(sqlQuery);
                                        if (r > 0)
                                        {
                                            session.setAttribute("titlePre".concat(tableName), title);
                                            session.setAttribute("bodyPre".concat(tableName), body);
                                            req.setAttribute("notesInfo", "<span class='info'>Запись успешно добавлена</span>");
                                            req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                                            //writeResponse(out);
                                        }
                                        else
                                        {
                                            req.setAttribute("notesInfo", "<span class='error'>Не удалось добавить запись</span>");
                                            req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                                            //writeResponse(out);
                                        }
                                    }
                                    else
                                    {
                                        if (tableName.equals("Notes"))
                                        {
                                            req.setAttribute("notesInfo", "<span class='error'>В записной книжке уже имеется записка с таким заголовком</span>");
                                        }
                                        else if (tableName.equals("Blacklist"))
                                        {
                                            req.setAttribute("notesInfo", "<span class='error'>Чёрный список уже содержит данного заёмщика</span>");
                                        }
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
                                if (session.getAttribute("titlePre".concat(tableName)) != null)
                                {
                                    session.removeAttribute("titlePre".concat(tableName));
                                }
                                if (session.getAttribute("bodyPre".concat(tableName)) != null)
                                {
                                    session.removeAttribute("bodyPre".concat(tableName));
                                }

                                if (idThisTitle.equals("") || idThisTitle.equals(id))
                                {
                                    String temp = "";
                                    sqlQuery = "SELECT \"WMID\" FROM \"" + tableName + "\" WHERE \"ID\"='" + id + "';";
                                    results = statement.executeQuery(sqlQuery);
                                    if (results.next())
                                    {
                                        temp = results.getString("WMID").trim();
                                    }
                                    if (wmid.equals(temp))
                                    {
                                        sqlQuery = "UPDATE \"" + tableName + "\""
                                                + "SET \"Title\"='" + DataConvertor.fixApostrophe(title)
                                                + "',\"Body\"='" + DataConvertor.fixApostrophe(body)
                                                + "'"
                                                + "WHERE \"ID\"='" + id + "';";
                                        int r = statement.executeUpdate(sqlQuery);
                                        if (r > 0)
                                        {
                                            req.setAttribute("notesInfo", "<span class='info'>Запись успешно обновлена</span>");
                                            req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                                            //writeResponse(out);
                                        }
                                        else
                                        {
                                            req.setAttribute("notesInfo", "<span class='error'>Не удалось обновить запись</span>");
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
                                    if (tableName.equals("Notes"))
                                    {
                                        req.setAttribute("notesInfo", "<span class='error'>В записной книжке уже имеется записка с таким заголовком</span>");
                                    }
                                    else if (tableName.equals("Blacklist"))
                                    {
                                        req.setAttribute("notesInfo", "<span class='error'>Чёрный список уже содержит данного заёмщика</span>");
                                    }
                                    req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                                    //writeResponse(out);
                                }
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
                        if (tableName.equals("Notes"))
                        {
                            req.setAttribute("notesInfo", "<span class='error'>Запись не имеет заголовка</span>");
                        }
                        else if (tableName.equals("Blacklist"))
                        {
                            req.setAttribute("notesInfo", "<span class='error'>Не указан заёмщик</span>");
                        }
                        req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                        //writeResponse(out);
                    }
                }
                else
                {
                    req.setAttribute("notesInfo", "<span class='error'>Недостаточно параметров</span>");
                    req.getRequestDispatcher("/cabinet/records/" + tableName.toLowerCase() + ".jsp").forward(req, resp);
                    //writeResponse(out);
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
