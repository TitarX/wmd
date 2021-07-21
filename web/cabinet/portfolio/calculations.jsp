<%-- 
    Document   : calculations
    Created on : Jun 20, 2011, 12:51:55 PM
    Author     : TitarX
--%>

<%@page import="java.text.NumberFormat"%>
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
        <meta name="keywords" content="расчёты" />
        <meta name="description" content="Расчёты" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="Pragma" content="no-cache"/>
        <link rel="icon" type="image/x-icon" href="/WMD/images/favicon.ico"/>
        <link rel="shortcut icon" type="image/x-icon" href="/WMD/images/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/panels.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/menu.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/calculations.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/jquery/ui/excitebike/jquery-ui-1.8.14.custom.css"/>
        <script type="text/javascript" src="/WMD/js/jquery/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="/WMD/js/jquery/ui/excitebike/datepicker.js"></script>
        <script type="text/javascript" src="/WMD/js/jquery/ui/excitebike/datepicker-ru.js"></script>
        <script type="text/javascript" src="/WMD/js/main.js"></script>
        <script type="text/javascript" src="/WMD/js/panels.js"></script>
        <script type="text/javascript" src="/WMD/js/calculations.js"></script>
        <title>Расчёты<%= " " + wmid%></title>
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
                <div id="filler"></div>
                <center>
                    <table class="grayTableWrapper">
                        <tr>
                            <th class="wrapHeader" colspan="3">
                                В обороте
                            </th>
                        </tr>
                        <%
                            Connection connection = new ConnectionFactory().getConnection();
                            Statement statement = connection.createStatement();
                            String sqlQuery = "SELECT \"Creditsumm\",\"Returnsumm\",\"Return\" FROM \"Credits\" WHERE \"WMID\"='" + wmid + "' AND \"Status\"='1';";
                            ResultSet results = statement.executeQuery(sqlQuery);

                            NumberFormat format = NumberFormat.getInstance();
                            format.setMaximumFractionDigits(2);

                            double c = 0;
                            double r = 0;
                            double pr = 0;
                            while (results.next())
                            {
                                c += results.getDouble("Creditsumm");
                                r += results.getDouble("Returnsumm");
                                pr += results.getDouble("Return");
                            }
                            out.println("<tr><td class='result'>Без просроченных (выдано / к возврату): " + format.format(c) + " / " + format.format(r) + "</td>"
                                    + "<td></td>"
                                    + "<td class='result'>,из них возвращено: " + format.format(pr) + "</td></tr>");

                            sqlQuery = "SELECT \"Creditsumm\",\"Returnsumm\",\"Return\" FROM \"Credits\" WHERE \"WMID\"='" + wmid + "' AND (\"Status\"='1' OR \"Status\"='3');";
                            results = statement.executeQuery(sqlQuery);
                            c = 0;
                            r = 0;
                            pr = 0;
                            while (results.next())
                            {
                                c += results.getDouble("Creditsumm");
                                r += results.getDouble("Returnsumm");
                                pr += results.getDouble("Return");
                            }
                            out.println("<tr><td class='result'>С просроченными (выдано / к возврату): " + format.format(c) + " / " + format.format(r) + "</td>"
                                    + "<td></td>"
                                    + "<td class='result'>,из них возвращено: " + format.format(pr) + "</td></tr>");

                            connection.close();
                        %>
                    </table>
                    <p>&nbsp;</p>
                    <table>
                        <tr>
                            <td valign="top">
                                <table class="whiteTableWrapper">
                                    <tr>
                                        <th class="wrapHeader">
                                            Период расчёта
                                        </th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <form action="#" method="post" name="calculationsForm">
                                                <table id="calculationsTable">
                                                    <tr>
                                                        <td align="left">
                                                            <input checked type="radio" name="total" value="1" onclick="setFields(this.value)"/>&nbsp;За весь период
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td align="left">
                                                            <input type="radio" name="total" value="2" onclick="setFields(this.value)"/>&nbsp;До даты
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td align="left">
                                                            <input type="radio" name="total" value="3" onclick="setFields(this.value)"/>&nbsp;Выбрать период
                                                        </td>
                                                    </tr>
                                                    <tr class="dateFields">
                                                        <td>
                                                            <input type="hidden" name="from" id="from" value="2000.01.01"/>
                                                        </td>
                                                    </tr>
                                                    <tr class="dateFields">
                                                        <td>
                                                            <input type="hidden" name="to" id="to" value="0"/>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </form>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <form action="#" method="post" name="calculationsButtonForm">
                                                <table>
                                                    <tr>
                                                        <td>
                                                            <input type="button" name="show" value="Расчитать" onclick="calculate();"/>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </form>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td>
                                <table class="grayTableWrapper toLeft">
                                    <tr>
                                        <th class="wrapHeader" colspan="2">
                                            Результат расчёта
                                        </th>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">
                                            <small>
                                                (Участвуют возвращённые кредиты с фактической датой завершения попадающей в указанный период расчёта.
                                                <br/>И просроченные кредиты с датой завершения попадающей в указанный период расчёта)
                                            </small>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td class="result">Выдано:&nbsp;</td>
                                        <td class="result" id="issued"></td>
                                    </tr>
                                    <tr>
                                        <td class="result">К возврату:&nbsp;</td>
                                        <td class="result" id="toReturn"></td>
                                    </tr>
                                    <tr>
                                        <td class="result">Возвращено:&nbsp;</td>
                                        <td class="result" id="returned"></td>
                                    </tr>
                                    <tr>
                                        <td class="result">Задержано:&nbsp;</td>
                                        <td class="result" id="delayed"></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td class="result">Комиссия Webmoney:&nbsp;</td>
                                        <td class="result" id="commissionWM"></td>
                                    </tr>
                                    <tr>
                                        <td class="result">Комиссия кредитной биржи:&nbsp;</td>
                                        <td class="result" id="commissionCM"></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td class="result">Компенсация за задержку:&nbsp;</td>
                                        <td class="result" id="refund"></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td class="result">Прибыль:&nbsp;</td>
                                        <td class="result" id="profit"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </center>
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
