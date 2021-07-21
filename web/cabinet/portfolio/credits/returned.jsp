<%-- 
    Document   : returned
    Created on : Jul 3, 2011, 6:06:57 PM
    Author     : TitarX
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="ru.webcentrum.wmd_data.ConnectionFactory"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! private String wmid = "";%>
<%! private String typePeriod = "", from = "", to = "";%>
<%
    if (session.getAttribute("access") != null)
    {
        wmid = session.getAttribute("access").toString();
    }
    else
    {
        wmid = "";
    }

    session.setAttribute("referer", "/cabinet/portfolio/credits/returned.jsp");
%>
<%
    if (request.getParameter("typePeriod") != null)
    {
        typePeriod = request.getParameter("typePeriod");
    }
    if (request.getParameter("from") != null)
    {
        from = request.getParameter("from");
    }
    if (request.getParameter("to") != null)
    {
        to = request.getParameter("to");
    }
%>
<!DOCTYPE html>
<html>
    <head>

        <noscript>
        <meta http-equiv="refresh" content="0; url=/WMD/noscript.jsp"/>
        </noscript>

        <meta name="autor" content="Dmitriy Ignatenko" />
        <meta name="keywords" content="возвращённые кредиты" />
        <meta name="description" content="Возвращённые кредиты" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="Pragma" content="no-cache"/>
        <link rel="icon" type="image/x-icon" href="/WMD/images/favicon.ico"/>
        <link rel="shortcut icon" type="image/x-icon" href="/WMD/images/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/panels.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/menu.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/credits/credits.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/credits/add.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/jquery/tables/demo_table_jui.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/jquery/ui/excitebike/jquery-ui-1.8.14.custom.css"/>
        <script type="text/javascript" src="/WMD/js/jquery/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="/WMD/js/jquery/ui/excitebike/datepicker.js"></script>
        <script type="text/javascript" src="/WMD/js/jquery/ui/excitebike/datepicker-ru.js"></script>
        <script type="text/javascript" src="/WMD/js/jquery/tables/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="/WMD/js/jquery/simplemodal/jquery.simplemodal-1.4.2.js"></script>
        <script type="text/javascript" src="/WMD/js/main.js"></script>
        <script type="text/javascript" src="/WMD/js/panels.js"></script>
        <script type="text/javascript" src="/WMD/js/credits/credits.js"></script>
        <script type="text/javascript" src="/WMD/js/credits/add.js"></script>
        <title>Возвращённые кредиты<%= " " + wmid%></title>
    </head>
    <body>
        <div id="wrapper">
            <div id="top-lable">
                <div id="menu-show">
                    <img id="menu-show-image" title="Открыть" src="/WMD/images/menu/down.png" alt="&dArr;"/>
                    <img id="menu-image" title="Меню" src="/WMD/images/menu/menu.png" alt="Меню"/>
                </div>
            </div>
            <div id="bottom-lable">
                <div id="panel-show">
                    <img id="panel-show-image" title="Открыть" src="/WMD/images/menu/up.png" alt="&uArr;"/>
                </div>
            </div>
            <div id="top">
                <div class="panel-content">
                    <ul class="empties-list-login">
                        <li></li>
                    </ul>
                    <ul id="cabinet-menu-list" class="menu-list">
                        <li class="header-menu-list">Кабинет</li>
                        <li class="item"><a href="/WMD/cabinet/profile.jsp">Профиль</a></li>
                        <li class="item"><a href="/WMD/exit">Выход</a></li>
                    </ul>
                    <ul id="portfolio-menu-list" class="menu-list">
                        <li class="header-menu-list">Портфель</li>
                        <li class="item"><a href="/WMD/cabinet/portfolio/credits/all.jsp">Кредиты</a></li>
                        <li class="item"><a href="/WMD/cabinet/portfolio/calculations.jsp">Расчёты</a></li>
                        <li class="item"><a href="/WMD/calculator.jsp">Калькулятор компенсации</a></li>
                    </ul>
                    <ul id="portfolio-menu-list" class="menu-list">
                        <li class="header-menu-list">Записи</li>
                        <li class="item"><a href="/WMD/cabinet/records/notes.jsp">Записная книжка</a></li>
                        <li class="item"><a href="/WMD/cabinet/records/blacklist.jsp">Чёрный список</a></li>
                    </ul>
                    <ul id="contacts-menu-list" class="menu-list">
                        <li class="header-menu-list">Контакты</li>
                        <li class="item"><a href="/WMD/cabinet/contacts/sendemail.jsp">Отправить email</a></li;>
                        <li class="item"><a href="/WMD/feedback.jsp">Обратная связь</a></li>
                    </ul>
                </div>
            </div>
            <div id="content">
                <div id="filler"></div>
                <div>
                    |<a href="/WMD/cabinet/portfolio/credits/add.jsp">Добавить кредит</a>|
                    |<a href="/WMD/cabinet/portfolio/credits/yielded.jsp">Выданные</a>|
                    |<a href="/WMD/cabinet/portfolio/credits/returned.jsp"><span class="thispage">Возвращённые</span></a>|
                    |<a href="/WMD/cabinet/portfolio/credits/delay.jsp">Просроченные</a>|
                    |<a href="/WMD/cabinet/portfolio/credits/all.jsp">Все</a>|
                </div>
                <p>
                    <span class='warning'>
                        <%
                            Date date = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd zzz");
                            out.print("Ткущая дата: " + simpleDateFormat.format(date));
                        %>
                    </span>
                </p>
                <%
                    if (request.getAttribute("addCreditInfo") != null)
                    {
                        out.println("<p>" + request.getAttribute("addCreditInfo").toString() + "</p>");
                    }
                %>
                <center>
                    <form name="periodForm" method="post" action="/WMD/cabinet/portfolio/credits/returned.jsp"
                          onkeyup="copyFields();"
                          onmousemove="copyFields();">
                        <table class="whiteTableWrapper" align="center">
                            <tr>
                                <th class="wrapHeader">
                                    Период выдачи кредитов
                                </th>
                            </tr>
                            <tr>
                                <td>
                                    <table id="periodTable" align="center">
                                        <tr>
                                            <td class="toCenter" colspan="2">
                                                <small>(Показаны кредиты, дата начала которых<br/>входить в выбранный период)</small>
                                            </td>
                                        </tr>
                                        <tr><td colspan="2">&nbsp;</td></tr>
                                        <%
                                            if ((typePeriod.equals("")) || (typePeriod.equals("1")))
                                            {
                                                out.println(
                                                        "<tr>"
                                                        + "<td class=\"toRight\">"
                                                        + "Кредиты:&nbsp;"
                                                        + "</td>"
                                                        + "<td class=\"toLeft\">"
                                                        + "<select name=\"typePeriod\" onchange=\"setFields(this.value);\">"
                                                        + "<option selected value=\"1\">"
                                                        + "За весь период"
                                                        + "</option>"
                                                        + "<option value=\"2\">"
                                                        + "До даты"
                                                        + "</option>"
                                                        + "<option value=\"3\">"
                                                        + "Выбрать период"
                                                        + "</option>"
                                                        + "</select>"
                                                        + "</td>"
                                                        + "</tr>"
                                                        + "<tr class=\"dateFields\">"
                                                        + "<td colspan=\"2\">"
                                                        + "<input id=\"from\" type=\"hidden\" name=\"from\" value=\"2000.01.01\"/>"
                                                        + "</td>"
                                                        + "</tr>"
                                                        + "<tr class=\"dateFields\">"
                                                        + "<td colspan=\"2\">"
                                                        + "<input id=\"to\" type=\"hidden\" name=\"to\" value=\"0\"/>"
                                                        + "</td>"
                                                        + "</tr>");
                                            }
                                            else
                                            {
                                                if (typePeriod.equals("2"))
                                                {
                                                    out.println(
                                                            "<tr>"
                                                            + "<td class=\"toRight\">"
                                                            + "Кредиты:&nbsp;"
                                                            + "</td>"
                                                            + "<td class=\"toLeft\">"
                                                            + "<select name=\"typePeriod\" onchange=\"setFields(this.value);\">"
                                                            + "<option value=\"1\">"
                                                            + "За весь период"
                                                            + "</option>"
                                                            + "<option selected value=\"2\">"
                                                            + "До даты"
                                                            + "</option>"
                                                            + "<option value=\"3\">"
                                                            + "Выбрать период"
                                                            + "</option>"
                                                            + "</select>"
                                                            + "</td>"
                                                            + "</tr>"
                                                            + "<tr class=\"dateFields\">"
                                                            + "<td colspan=\"2\">"
                                                            + "<input id=\"from\" type=\"hidden\" name=\"from\" value=\"2000.01.01\"/>"
                                                            + "</td>"
                                                            + "</tr>"
                                                            + "<tr class=\"dateFields\">"
                                                            + "<td class=\"toRight\">"
                                                            + "До:&nbsp;"
                                                            + "</td>"
                                                            + "<td class=\"toLeft\">"
                                                            + "<input id=\"to\" readonly class=\"datePeriod\" type=\"text\" name=\"to\" value=\"" + to + "\""
                                                            + "onfocus=\"setBackgroundColor(this);\" onblur=\"removeBackgroundColor(this);\""
                                                            + "oninput=\"copyFields();\" onpropertychange=\"copyFields();\"/>"
                                                            + "</td>"
                                                            + "</tr>");
                                                }
                                                else
                                                {
                                                    if (typePeriod.equals("3"))
                                                    {
                                                        out.println(
                                                                "<tr>"
                                                                + "<td class=\"toRight\">"
                                                                + "Кредиты:&nbsp;"
                                                                + "</td>"
                                                                + "<td class=\"toLeft\">"
                                                                + "<select name=\"typePeriod\" onchange=\"setFields(this.value);\">"
                                                                + "<option value=\"1\">"
                                                                + "За весь период"
                                                                + "</option>"
                                                                + "<option value=\"2\">"
                                                                + "До даты"
                                                                + "</option>"
                                                                + "<option selected value=\"3\">"
                                                                + "Выбрать период"
                                                                + "</option>"
                                                                + "</select>"
                                                                + "</td>"
                                                                + "</tr>"
                                                                + "<tr class=\"dateFields\">"
                                                                + "<td class=\"toRight\">"
                                                                + "От:&nbsp;"
                                                                + "</td>"
                                                                + "<td class=\"toLeft\">"
                                                                + "<input id=\"from\" readonly class=\"datePeriod\" type=\"text\" name=\"from\" value=\"" + from + "\""
                                                                + "onfocus=\"setBackgroundColor(this);\" onblur=\"removeBackgroundColor(this);\""
                                                                + "oninput=\"copyFields();\" onpropertychange=\"copyFields();\"/>"
                                                                + "</td>"
                                                                + "</tr>"
                                                                + "<tr class=\"dateFields\">"
                                                                + "<td class=\"toRight\">"
                                                                + "До:&nbsp;"
                                                                + "</td>"
                                                                + "<td class=\"toLeft\">"
                                                                + "<input id=\"to\" readonly class=\"datePeriod\" type=\"text\" name=\"to\" value=\"" + to + "\""
                                                                + "onfocus=\"setBackgroundColor(this);\" onblur=\"removeBackgroundColor(this);\""
                                                                + "oninput=\"copyFields();\" onpropertychange=\"copyFields();\"/>"
                                                                + "</td>"
                                                                + "</tr>");
                                                    }
                                                }
                                            }
                                        %>
                                    </table>
                                    <table align="center">
                                        <tr>
                                            <td class="toLeft">
                                                <input type="submit" name="show" value="Показать"/>
                                            </td>
                                            <td class="toRight">
                                                <input type="button" name="export" value="Экспортировать в Excel" onclick="document.exportForm.submit();"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <form name="exportForm" method="post" action="/WMD/servlets/exportCredits">
                        <input type="hidden" name="page" value="2"/>
                        <%
                            if ((typePeriod.equals("")) || (typePeriod.equals("1")))
                            {
                                out.println(
                                        "<input type=\"hidden\" name=\"typePeriod\" value=\"1\"/>"
                                        + "<input type=\"hidden\" name=\"from\" value=\"2000.01.01\"/>"
                                        + "<input type=\"hidden\" name=\"to\" value=\"0\"/>");
                            }
                            else
                            {
                                if (typePeriod.equals("2"))
                                {
                                    out.println(
                                            "<input type=\"hidden\" name=\"typePeriod\" value=\"2\"/>"
                                            + "<input type=\"hidden\" name=\"from\" value=\"2000.01.01\"/>"
                                            + "<input type=\"hidden\" name=\"to\" value=\"" + to + "\"/>");
                                }
                                else
                                {
                                    if (typePeriod.equals("3"))
                                    {
                                        out.println(
                                                "<input type=\"hidden\" name=\"typePeriod\" value=\"3\"/>"
                                                + "<input type=\"hidden\" name=\"from\" value=\"" + from + "\"/>"
                                                + "<input type=\"hidden\" name=\"to\" value=\"" + to + "\"/>");
                                    }
                                }
                            }
                        %>
                    </form>
                </center>
                <div>&nbsp;</div>
                <div>
                    <table id="credits" width="100%">
                        <thead>
                            <tr align="center">
                                <th>
                                    !
                                </th>
                                <th>
                                    № кредита
                                </th>
                                <th>
                                    Заёмщик
                                </th>
                                <th>
                                    Сумма кредита
                                </th>
                                <th>
                                    Сумма возврата
                                </th>
                                <th>
                                    Возвращено
                                </th>
                                <th>
                                    % кредита<br/>(в день)
                                </th>
                                <th>
                                    Дата начала<br/>(гмд)
                                </th>
                                <th>
                                    Дата завершения<br/>(гмд)
                                </th>
                                <th>
                                    Срок кредита<br/>(дней)
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                String betweenDate = "";
                                if ((!from.equals("") && !to.equals("")) && (from.matches("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2}") && to.matches("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2}")))
                                {
                                    if (to.equals("0"))
                                    {
                                        to = simpleDateFormat.format(date);
                                    }
                                    betweenDate = "AND (\"Begindate\" BETWEEN '" + from + "' AND '" + to + "') ";
                                }

                                Connection connection = new ConnectionFactory().getConnection();
                                Statement statement = connection.createStatement();
                                String sqlQuery = "SELECT \"Borrower\",\"Ncredit\",\"Creditsumm\",\"Returnsumm\",\"Return\""
                                        + ",\"Percentcredit\",\"Begindate\",\"Enddate\",\"Creditterm\",\"Status\",\"ID\",\"Importantly\""
                                        + "FROM \"Credits\" WHERE \"WMID\"='" + wmid + "' AND \"Status\"='2' " + betweenDate + "ORDER BY \"Begindate\" DESC;";
                                ResultSet results = statement.executeQuery(sqlQuery);

                                String borrower = null;
                                String ncredit = null;
                                String creditsumm = null;
                                String returnsumm = null;
                                String percentcredit = null;
                                String begindate = null;
                                String enddate = null;
                                String creditterm = null;
                                String returN = null;
                                String id = null;
                                int status = 0;
                                int importantly = 0;

                                NumberFormat resultFormat = NumberFormat.getInstance();
                                resultFormat.setMaximumFractionDigits(2);

                                SimpleDateFormat simpleDateFormatResults = new SimpleDateFormat("yyyy.MM.dd");

                                while (results.next())
                                {
                                    borrower = results.getString("Borrower");
                                    ncredit = results.getString("Ncredit");
                                    creditsumm = resultFormat.format(results.getDouble("Creditsumm"));
                                    returnsumm = resultFormat.format(results.getDouble("Returnsumm"));
                                    percentcredit = resultFormat.format(results.getDouble("Percentcredit"));
                                    begindate = simpleDateFormatResults.format(results.getDate("Begindate"));
                                    enddate = simpleDateFormatResults.format(results.getDate("Enddate"));
                                    creditterm = String.valueOf(results.getInt("Creditterm"));
                                    returN = resultFormat.format(results.getDouble("Return"));
                                    status = results.getInt("Status");
                                    id = String.valueOf(results.getLong("ID"));
                                    importantly = results.getInt("Importantly");

                                    String colorStatus = "";
                                    switch (status)
                                    {
                                        case 1:
                                            colorStatus = "class='yielded' ";
                                            break;
                                        case 2:
                                            colorStatus = "class='returned' ";
                                            break;
                                        case 3:
                                            colorStatus = "class='expired' ";
                                            break;
                                    }

                                    if (importantly == 0)
                                    {
                                        out.println("<tr href=\"#dialog\" name=\"modal\" " + colorStatus + "onmouseover=\"setBackgroundColor(this);\" onmouseout=\"removeBackgroundColor(this);\" onclick=\"setFormValues(" + id + ");\">");
                                        //out.println("<a href=\"#dialog\" name=\"modal\" onclick=\"setFormValues("+id+");\">");

                                        out.println("<td>&#160;</td>");
                                        out.println("<td>" + ncredit + "</td>");
                                        out.println("<td>" + borrower + "</td>");
                                        out.println("<td>" + creditsumm + "</td>");
                                        out.println("<td>" + returnsumm + "</td>");
                                        out.println("<td>" + returN + "</td>");
                                        out.println("<td>" + percentcredit + "</td>");
                                        out.println("<td>" + begindate + "</td>");
                                        out.println("<td>" + enddate + "</td>");
                                        out.println("<td>" + creditterm + "</td>");

                                        //out.println("</a>");
                                        out.println("</tr>");
                                    }
                                    else
                                    {
                                        out.println("<tr href=\"#dialog\" name=\"modal\" " + colorStatus + "onmouseover=\"setBackgroundColor(this);\" onmouseout=\"removeBackgroundColor(this);\" onclick=\"setFormValues(" + id + ");\">");
                                        //out.println("<a href=\"#dialog\" name=\"modal\" onclick=\"setFormValues("+id+");\">");

                                        out.println("<td class='importantly'>&#33;</td>");
                                        out.println("<td>" + ncredit + "</td>");
                                        out.println("<td>" + borrower + "</td>");
                                        out.println("<td>" + creditsumm + "</td>");
                                        out.println("<td>" + returnsumm + "</td>");
                                        out.println("<td>" + returN + "</td>");
                                        out.println("<td>" + percentcredit + "</td>");
                                        out.println("<td>" + begindate + "</td>");
                                        out.println("<td>" + enddate + "</td>");
                                        out.println("<td>" + creditterm + "</td>");

                                        //out.println("</a>");
                                        out.println("</tr>");
                                    }
                                }
                                connection.close();
                            %>
                        </tbody>
                    </table>
                </div>

                <!--Модальное окно-->
                <div id="boxes">
                    <div id="dialog">

                        <table>
                            <tr>
                                <td>
                                    <form action="/WMD/servlets/deleteCredit" method="post" name="deleteCreditForm" class="forCreditForm">
                                        <input type="hidden" name="idd" value=""/>
                                        <table class="forCreditForm">
                                            <tr>
                                                <td align="right">
                                                    <input type="button" name="confirmDeleteButton" value="Удалить" onclick="deleteNote();"/>
                                                    <div style="display: none">
                                                        <input type="submit" id="deleteNoteButton" name="deleteNoteButton" value="Удалить"/>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form name="addCreditForm" action="/WMD/servlets/updateCredit" method="post"
                                          onkeyup="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                          onmousemove="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);">
                                        <input type="hidden" name="ida" value=""/>
                                        <table class="forCreditForm">
                                            <tr>
                                                <td>
                                                    <table>
                                                        <tr class="wrapFormElement">
                                                            <td>
                                                                Заёмщик
                                                            </td>
                                                            <td>
                                                                <input title="Идентификатор заёмщика"
                                                                       class="text" type="text" name="borrower" value=""
                                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onfocus="setBackgroundColor(this);"
                                                                       onblur="removeBackgroundColor(this);"/>
                                                                <span id="borrowerValidIndicator" class="error">*</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2"></td>
                                                        </tr>
                                                        <tr class="wrapFormElement">
                                                            <td>
                                                                Номер кредита
                                                            </td>
                                                            <td>
                                                                <input disabled title="Уникальный номер кредита"
                                                                       class="text" type="text" name="ncredit" value=""
                                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onfocus="setBackgroundColor(this);"
                                                                       onblur="removeBackgroundColor(this);"/>
                                                                <span id="ncreditValidIndicator" class="error">*</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2"></td>
                                                        </tr>
                                                        <tr class="wrapFormElement">
                                                            <td>
                                                                Номер заявки
                                                            </td>
                                                            <td>
                                                                <input title="Номер заявки на кредит"
                                                                       class="text" type="text" name="nrequistion" value=""
                                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onfocus="setBackgroundColor(this);"
                                                                       onblur="removeBackgroundColor(this);"/>
                                                                <span id="nrequistionValidIndicator" class="error">*</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2"></td>
                                                        </tr>
                                                        <tr class="wrapFormElement">
                                                            <td>
                                                                Сумма кредита
                                                            </td>
                                                            <td>
                                                                <input title="Выдаваемая сумма"
                                                                       class="summ" type="text" name="scredit" value=""
                                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onfocus="setBackgroundColor(this);"
                                                                       onblur="removeBackgroundColor(this);"/>
                                                                <span id="screditValidIndicator" class="error">*</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2"></td>
                                                        </tr>
                                                        <tr class="wrapFormElement">
                                                            <td>
                                                                Сумма возврата
                                                            </td>
                                                            <td>
                                                                <input title="Возвращаемая сумма"
                                                                       id="sreturnId" class="summ" type="text" name="sreturn" value=""
                                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onfocus="setBackgroundColor(this);"
                                                                       onblur="removeBackgroundColor(this);"/>
                                                                <span id="sreturnValidIndicator" class="error">*</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2"></td>
                                                        </tr>
                                                        <tr class="wrapFormElement">
                                                            <td>
                                                                Дата начала кредита (гмд)
                                                            </td>
                                                            <td>
                                                                <input title="Дата начала кредита"
                                                                       readonly class="date" type="text" name="begin" value=""
                                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onfocus="setBackgroundColor(this);"
                                                                       onblur="removeBackgroundColor(this);"/>
                                                                <span id="beginValidIndicator" class="error">*</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2"></td>
                                                        </tr>
                                                        <tr class="wrapFormElement">
                                                            <td>
                                                                Дата завешения кредита (гмд)
                                                            </td>
                                                            <td>
                                                                <input title="Дата завешения кредита"
                                                                       readonly id="endId" class="date" type="text" name="end" value=""
                                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                                       onfocus="setBackgroundColor(this);"
                                                                       onblur="removeBackgroundColor(this);"/>
                                                                <span id="endValidIndicator" class="error">*</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2"></td>
                                                        </tr>
                                                        <tr class="wrapFormElement">
                                                            <td>
                                                                Фактическая дата возврата (гмд)
                                                            </td>
                                                            <td>
                                                                <input readonly title="Дата полного погашения кредита" id="factId" class="date" type="text" name="fact" value=""
                                                                       onfocus="setBackgroundColor(this);"
                                                                       onblur="removeBackgroundColor(this);"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2"></td>
                                                        </tr>
                                                        <tr class="wrapFormElement">
                                                            <td>
                                                                Фактическая сумма возврата
                                                            </td>
                                                            <td>
                                                                <input title="Сколько возвращено на текущий момент" id="returnId" class="summ" type="text" name="return" value=""
                                                                       onfocus="setBackgroundColor(this);"
                                                                       onblur="removeBackgroundColor(this);"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2"></td>
                                                        </tr>
                                                        <tr class="wrapFormElement">
                                                            <td>
                                                                Сумма компенсации
                                                            </td>
                                                            <td>
                                                                <input title="Сумма компенсации за задержку возврата кредита"
                                                                       class="summ" type="text" name="compensation" value=""
                                                                       onfocus="setBackgroundColor(this);"
                                                                       onblur="removeBackgroundColor(this);"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2"></td>
                                                        </tr>
                                                        <tr class="wrapFormElement">
                                                            <td>
                                                                Важный
                                                            </td>
                                                            <td>
                                                                <input title="Пометить как важный" type="checkbox" name="importantly" value=""/>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td>
                                                    <textarea name="comment" cols="50" rows="20"
                                                              onfocus="setBackgroundColor(this);"
                                                              onblur="removeBackgroundColor(this);"
                                                              ></textarea>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="toLeft">
                                                    <a href="#" id="closeModal"><input type="button" id="closeModalButton" name="closeModalButton" value="Закрыть"/></a>
                                                </td>
                                                <td class="toRight">
                                                    <input disabled type="submit" name="send" value="Сохранить"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                </td>
                            </tr>
                        </table>

                    </div>
                    <div id="mask"></div>
                </div>
                <!--Модальное окно-->

                <!--Confirm окно-->
                <div id='confirm'>
                    <!--
                    <div class='header'>
                        <span>Подтвердите удаление записи</span>
                    </div>
                    -->
                    <div class='message'>
                    </div>
                    <div class='buttons'>
                        <div class='no simplemodal-close'>
                            Нет</div>
                        <div class='yes'>
                            Да</div>
                    </div>
                </div>
                <!--Confirm окно-->

                <div id="filler"></div>
            </div>
            <div id="bottom">
                <div class="panel-content">
                    <!--WWW-->
                    <a href="http://www.webcentrum.ru/" target="_blank"><img class="content" src="/WMD/images/panel/allservices.gif" alt="Все сервисы" title="Все сервисы" border="0"/></a>
                    <!--/WWW-->

                    <!-- webmoney attestation label#6AC4F5E2-5898-41CB-A3D6-791E560F587D begin -->
                    <a href="http://passport.webmoney.ru/asp/certview.asp?wmid=227429553045" target="_blank">
                        <img src="/WMD/images/panel/wmatt.gif" border="0">
                    </a>
                    <!-- webmoney attestation label#6AC4F5E2-5898-41CB-A3D6-791E560F587D end -->

                    <!--RBC-->
                    <script>
                        document.write( '<a href="http://www.informer.ru/cgi-bin/redirect.cgi?id=1_1_1_48_2_2-0&url=http://www.rbc.ru&src_url=usd_nal_8831_7.gif" target="_blank"><img src="http://pics.rbc.ru/img/grinf/usd_nal_8831_7.gif?'+ Math.floor( 100000*Math.random() ) + '" WIDTH=88 HEIGHT="31" border=0></a>');
                    </script>
                    <!--/RBC-->

                    <!-- begin of Top100 code -->
                    <script id="top100Counter" type="text/javascript" src="http://counter.rambler.ru/top100.jcn?2797913"></script>
                    <noscript>
                    <a href="http://top100.rambler.ru/navi/2797913/">
                        <img src="http://counter.rambler.ru/top100.cnt?2797913" alt="Rambler's Top100" border="0" />
                    </a>
                    </noscript>
                    <!-- end of Top100 code -->

                    <!-- HotLog -->
                    <script type="text/javascript" language="javascript">
                        hotlog_js="1.0"; hotlog_r=""+Math.random()+"&amp;s=2260312&amp;im=501&amp;r="+
                            escape(document.referrer)+"&amp;pg="+escape(window.location.href);
                    </script>
                    <script type="text/javascript" language="javascript1.1">
                        hotlog_js="1.1"; hotlog_r+="&amp;j="+(navigator.javaEnabled()?"Y":"N");
                    </script>
                    <script type="text/javascript" language="javascript1.2">
                        hotlog_js="1.2"; hotlog_r+="&amp;wh="+screen.width+"x"+screen.height+"&amp;px="+
                            (((navigator.appName.substring(0,3)=="Mic"))?screen.colorDepth:screen.pixelDepth);
                    </script>
                    <script type="text/javascript" language="javascript1.3">
                        hotlog_js="1.3";
                    </script>
                    <script type="text/javascript" language="javascript">
                        hotlog_r+="&amp;js="+hotlog_js;
                        document.write('<a href="http://click.hotlog.ru/?2260312" target="_blank"><img '+
                            'src="http://hit41.hotlog.ru/cgi-bin/hotlog/count?'+
                            hotlog_r+'" border="0" width="88" height="31" alt="HotLog"><\/a>');
                    </script>
                    <noscript>
                    <a href="http://click.hotlog.ru/?2260312" target="_blank"><img
                            src="http://hit41.hotlog.ru/cgi-bin/hotlog/count?s=2260312&amp;im=501" border="0"
                            width="88" height="31" alt="HotLog"></a>
                    </noscript>
                    <!-- /HotLog -->
                </div>
            </div>
        </div>
    </body>
</html>
