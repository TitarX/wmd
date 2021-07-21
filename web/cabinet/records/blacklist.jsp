<%-- 
    Document   : blacklist
    Created on : Aug 1, 2012, 8:07:51 PM
    Author     : TitarX
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="ru.webcentrum.wmd_data.ConnectionFactory"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! private String wmid = "";%>
<%
    if (session.getAttribute("access") != null)
    {
        wmid = session.getAttribute("access").toString();
    }
    else
    {
        wmid = "";
    }
%>
<!DOCTYPE html>
<html>
    <head>

        <noscript>
        <meta http-equiv="refresh" content="0; url=/WMD/noscript.jsp"/>
        </noscript>

        <meta name="autor" content="Dmitriy Ignatenko" />
        <meta name="keywords" content="Чёрный список заёмщиков" />
        <meta name="description" content="Чёрный список заёмщиков" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="Pragma" content="no-cache"/>
        <link rel="icon" type="image/x-icon" href="/WMD/images/favicon.ico"/>
        <link rel="shortcut icon" type="image/x-icon" href="/WMD/images/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/panels.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/menu.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/notebook.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/jquery/tables/demo_table_jui.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/jquery/ui/excitebike/jquery-ui-1.8.14.custom.css"/>
        <script type="text/javascript" src="/WMD/js/jquery/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="/WMD/js/jquery/tables/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="/WMD/js/jquery/simplemodal/jquery.simplemodal-1.4.2.js"></script>
        <script type="text/javascript" src="/WMD/js/main.js"></script>
        <script type="text/javascript" src="/WMD/js/panels.js"></script>
        <script type="text/javascript" src="/WMD/js/notebook.js"></script>
        <title>Чёрный список заёмщиков<%= " " + wmid%></title>
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
                        <li class="item"><a href="/WMD/cabinet/contacts/sendemail.jsp">Отправить email</a></li>
                        <li class="item"><a href="/WMD/feedback.jsp">Обратная связь</a></li>
                    </ul>
                </div>
            </div>
            <div id="content">
                <center>
                    <div id="filler"></div>

                    <%
                        if (request.getAttribute("notesInfo") != null)
                        {
                            out.println("<p>" + request.getAttribute("notesInfo").toString() + "</p>");
                        }
                    %>

                    <table id="notes" width="100%">
                        <thead>
                            <tr class='titleNotes' href="#dialog" name="modal" onclick="setFormValues();">
                                <th id="addNote" onmouseover="setBackgroundColorForTitle(this);" onmouseout="removeBackgroundColorForTitle(this);">
                                    Добавить заёмщка ...
                                </th>
                            </tr>
                            <tr>
                                <th class="titleNotes">&nbsp;</th>
                            </tr>
                            <tr align="center">
                                <th class="titleNotes">Заёмщики</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                Connection connection = new ConnectionFactory().getConnection();
                                Statement statement = connection.createStatement();
                                String sqlQuery = "SELECT \"Title\",\"ID\" FROM \"Blacklist\" WHERE \"WMID\"='" + wmid + "' ORDER BY \"Title\" ASC;";
                                ResultSet results = statement.executeQuery(sqlQuery);
                                String title = null;
                                long id;
                                while (results.next())
                                {
                                    title = results.getString("Title");
                                    id = results.getLong("ID");
                                    out.println(
                                            "<tr class='titleNotes' href=\"#dialog\" name=\"modal\" onclick=\"setFormValues('" + title + "','" + id + "');\">"
                                            + "<td onmouseover=\"setBackgroundColor(this);\" onmouseout=\"removeBackgroundColor(this);\">"
                                            + title + "</td>"
                                            + "</tr>");

                                    //out.println(
                                    //       "<tr class='titleNotes'>"
                                    //        +"<a href=\"#dialog\" name=\"modal\" onclick=\"setFormValues('"+title+"','"+id+"');\">"
                                    //        +"<td onmouseover=\"setBackgroundColor(this);\" onmouseout=\"removeBackgroundColor(this);\">"
                                    //        +title+"</td></a>"
                                    //        +"</tr>");
                                }
                                connection.close();
                            %>
                        </tbody>
                    </table>

                    <!--Модальное окно-->
                    <div id="boxes">
                        <div id="dialog">

                            <div class="forNodeForm" align="right">
                                <form action="/WMD/servlets/deleteNote" method="post" name="deleteNoteForm" class="forNodeForm"
                                      onkeyup="checkTitle();" onmousemove="checkTitle();">
                                    <input type="hidden" name="idd" value=""/>
                                    <input type="hidden" name="tablename" value="Blacklist"/>
                                    <table class="forNodeForm">
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
                            </div>
                            <form action="/WMD/servlets/writeNote" method="post" name="addNoteForm" class="forNodeForm">
                                <input type="hidden" name="idw" value=""/>
                                <input type="hidden" id="tableName" name="tablename" value="Blacklist"/>
                                <table class="forNodeForm">
                                    <tr>
                                        <td colspan="2" align="center"><strong>Заёмщик:</strong></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <input size="90" type="text" id="title" name="title" value="" oninput="checkTitle();" onpropertychange="checkTitle();" />
                                            <span class="error" id="titleIndicator">*</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center"><strong>Комментарий:</strong></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <textarea cols="70" rows="12" name="body"></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td align="left">
                                            <a href="#" id="closeModal"><input type="button" id="closeModalButton" name="closeModalButton" value="Закрыть"/></a>
                                        </td>
                                        <td align="right"><input type="submit" name="send" value="Записать" disabled /></td>
                                    </tr>
                                </table>
                            </form>

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
                </center>
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
