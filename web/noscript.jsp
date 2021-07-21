<%-- 
    Document   : noscript
    Created on : Jun 27, 2011, 7:30:24 PM
    Author     : TitarX
--%>

<%@page import="ru.webcentrum.wmd_data.DataGetter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! private String urlidLink = "";%>
<%
    urlidLink = new DataGetter().getData("/data/urlid/link/text()");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="autor" content="Dmitriy Ignatenko" />
        <meta name="keywords" content="" />
        <meta name="description" content="Включите JavaScript" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="Pragma" content="no-cache"/>
        <link rel="icon" type="image/x-icon" href="/WMD/images/favicon.ico"/>
        <link rel="shortcut icon" type="image/x-icon" href="/WMD/images/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/panels.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/menu.css"/>
        <script type="text/javascript" src="/WMD/js/jquery/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="/WMD/js/main.js"></script>
        <script type="text/javascript" src="/WMD/js/panels.js"></script>
        <title>Включите JavaScript</title>
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
                    <%
                        if (session.getAttribute("access") == null)
                        {
                            out.println("<ul class=\"empties-list-logout\">");
                            out.println("<li></li>");
                            out.println("</ul>");

                            out.println("<ul id=\"cabinet-menu-list\" class=\"menu-list\">");
                            out.println("<li class=\"header-menu-list\">Кабинет</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href='" + urlidLink + "'>Вход</a>");
                            out.println("</li>");
                            out.println("</ul>");

                            out.println("<ul id=\"portfolio-menu-list\" class=\"menu-list\">");
                            out.println("<li class=\"header-menu-list\">Портфель</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href=\"/WMD/calculator.jsp\">Калькулятор компенсации</a>");
                            out.println("</li>");
                            out.println("</ul>");

                            out.println("<ul id=\"contacts-menu-list\" class=\"menu-list\">");
                            out.println("<li class=\"header-menu-list\">Контакты</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href=\"/WMD/feedback.jsp\">Обратная связь</a>");
                            out.println("</li>");
                            out.println("</ul>");
                        }
                        else
                        {
                            out.println("<ul class=\"empties-list-login\">");
                            out.println("<li></li>");
                            out.println("</ul>");
                            out.println("<ul id=\"cabinet-menu-list\" class=\"menu-list\">");
                            out.println("<li class=\"header-menu-list\">Кабинет</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href=\"/WMD/cabinet/profile.jsp\">Профиль</a>");
                            out.println("</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href=\"/WMD/exit\">Выход</a>");
                            out.println("</li>");
                            out.println("</ul>");

                            out.println("<ul id=\"portfolio-menu-list\" class=\"menu-list\">");
                            out.println("<li class=\"header-menu-list\">Портфель</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href=\"/WMD/cabinet/portfolio/credits/all.jsp\">Кредиты</a>");
                            out.println("</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href=\"/WMD/cabinet/portfolio/calculations.jsp\">Расчёты</a>");
                            out.println("</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href=\"/WMD/calculator.jsp\">Калькулятор компенсации</a>");
                            out.println("</li>");
                            out.println("</ul>");

                            out.println("<ul id=\"portfolio-menu-list\" class=\"menu-list\">");
                            out.println("<li class=\"header-menu-list\">Записи</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href=\"/WMD/cabinet/records/notes.jsp\">Записная книжка</a>");
                            out.println("</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href=\"/WMD/cabinet/records/blacklist.jsp\">Чёрный список</a>");
                            out.println("</li>");
                            out.println("</ul>");

                            out.println("<ul id=\"contacts-menu-list\" class=\"menu-list\">");
                            out.println("<li class=\"header-menu-list\">Контакты</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href=\"/WMD/cabinet/contacts/sendemail.jsp\">Отправить email</a>");
                            out.println("</li>");
                            out.println("<li class=\"item\">");
                            out.println("<a href=\"/WMD/feedback.jsp\">Обратная связь</a>");
                            out.println("</li>");
                            out.println("</ul>");
                        }
                    %>
                </div>
            </div>
            <div id="content">
                <div id="filler"></div>
                <p class="error">
                    Для работы с сервисом необходимо разрешить в вашем браузере использование JavaScript
                </p>
                <p>
                    <a href="http://www.google.ru/support/bin/answer.py?answer=23852" target="_blank">
                        Как включить JavaScript?
                    </a>
                </p>
                <p>
                    <a href="/WMD/index.jsp">
                        На главную
                    </a>
                </p>
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
