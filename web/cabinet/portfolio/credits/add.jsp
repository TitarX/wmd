<%-- 
    Document   : add
    Created on : Jul 5, 2011, 4:06:48 PM
    Author     : TitarX
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! private String wmid = "", comment = "";%>
<%
    if (session.getAttribute("access") != null)
    {
        wmid = session.getAttribute("access").toString();
    }
    else
    {
        wmid = "";
    }

    if (request.getParameter("comment") != null)
    {
        comment = request.getParameter("comment");
    }
    else
    {
        comment = "Комментарий";
    }

    session.setAttribute("referer", "/cabinet/portfolio/credits/add.jsp");
    if (session.getAttribute("add") != null)
    {
        session.removeAttribute("add");
    }
%>
<!DOCTYPE html>
<html>
    <head>

        <noscript>
        <meta http-equiv="refresh" content="0; url=/WMD/noscript.jsp"/>
        </noscript>

        <meta name="autor" content="Dmitriy Ignatenko" />
        <meta name="keywords" content="добавить кредит" />
        <meta name="description" content="Добавить кредит" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="Pragma" content="no-cache"/>
        <link rel="icon" type="image/x-icon" href="/WMD/images/favicon.ico"/>
        <link rel="shortcut icon" type="image/x-icon" href="/WMD/images/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/panels.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/menu.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/credits/add.css"/>
        <link rel="stylesheet" type="text/css" href="/WMD/css/jquery/ui/excitebike/jquery-ui-1.8.14.custom.css"/>
        <script type="text/javascript" src="/WMD/js/jquery/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="/WMD/js/jquery/ui/excitebike/datepicker.js"></script>
        <script type="text/javascript" src="/WMD/js/jquery/ui/excitebike/datepicker-ru.js"></script>
        <script type="text/javascript" src="/WMD/js/main.js"></script>
        <script type="text/javascript" src="/WMD/js/panels.js"></script>
        <script type="text/javascript" src="/WMD/js/credits/add.js"></script>
        <title>Добавить кредит<%= " " + wmid%></title>
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
                    <div>
                        |<a href="/WMD/cabinet/portfolio/credits/add.jsp"><span class="thispage">Добавить кредит</span></a>|
                        |<a href="/WMD/cabinet/portfolio/credits/yielded.jsp">Выданные</a>|
                        |<a href="/WMD/cabinet/portfolio/credits/returned.jsp">Возвращённые</a>|
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
                    <p><span class='warning'>Отмеченные поля обязательны для заполнения</span></p>
                    <p><span class='warning'>Номер кредита в портфеле должен быть уникальным</span></p>
                    <%
                        if (request.getAttribute("addCreditInfo") != null)
                        {
                            out.println("<p>" + request.getAttribute("addCreditInfo").toString() + "</p>");
                        }
                    %>
                    <form name="addCreditForm" action="/WMD/servlets/addCredit" method="post"
                          onkeyup="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                          onmousemove="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);">
                        <table class="grayTableWrapper">
                            <tr>
                                <th colspan="2" class="wrapHeader">
                                    Добавить кредит
                                </th>
                            </tr>
                            <tr>
                                <td>
                                    <table>
                                        <tr class="wrapFormElement">
                                            <td>
                                                Заёмщик
                                            </td>
                                            <td>
                                                <input title="Идентификатор заёмщика"
                                                       class="text" type="text" name="borrower" value='${param["borrower"]}'
                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onfocus="setBackgroundColor(this,this.parentNode.parentNode);"
                                                       onblur="removeBackgroundColor(this,this.parentNode.parentNode);"/>
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
                                                <input title="Уникальный номер кредита"
                                                       class="text" type="text" name="ncredit" value='${param["ncredit"]}'
                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onfocus="setBackgroundColor(this,this.parentNode.parentNode);"
                                                       onblur="removeBackgroundColor(this,this.parentNode.parentNode);"/>
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
                                                       class="text" type="text" name="nrequistion" value='${param["nrequistion"]}'
                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onfocus="setBackgroundColor(this,this.parentNode.parentNode);"
                                                       onblur="removeBackgroundColor(this,this.parentNode.parentNode);"/>
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
                                                       class="summ" type="text" name="scredit" value='${param["scredit"]}'
                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onfocus="setBackgroundColor(this,this.parentNode.parentNode);"
                                                       onblur="removeBackgroundColor(this,this.parentNode.parentNode);"/>
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
                                                       id="sreturnId" class="summ" type="text" name="sreturn" value='${param["sreturn"]}'
                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onfocus="setBackgroundColor(this,this.parentNode.parentNode);"
                                                       onblur="removeBackgroundColor(this,this.parentNode.parentNode);"/>
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
                                                       readonly class="date" type="text" name="begin" value='${param["begin"]}'
                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onfocus="setBackgroundColor(this,this.parentNode.parentNode);"
                                                       onblur="removeBackgroundColor(this,this.parentNode.parentNode);"/>
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
                                                       readonly id="endId" class="date" type="text" name="end" value='${param["end"]}'
                                                       oninput="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onpropertychange="checkAvailabilityInput(borrower.value,ncredit.value,nrequistion.value,scredit.value,sreturn.value,begin.value,end.value);"
                                                       onfocus="setBackgroundColor(this,this.parentNode.parentNode);"
                                                       onblur="removeBackgroundColor(this,this.parentNode.parentNode);"/>
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
                                                <input readonly title="Дата полного погашения кредита" id="factId" class="date" type="text" name="fact" value='${param["fact"]}'
                                                       onfocus="setBackgroundColor(this,this.parentNode.parentNode);"
                                                       onblur="removeBackgroundColor(this,this.parentNode.parentNode);"/>
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
                                                <input title="Сколько возвращено на текущий момент" id="returnId" class="summ" type="text" name="return" value='${param["return"]}'
                                                       onfocus="setBackgroundColor(this,this.parentNode.parentNode);"
                                                       onblur="removeBackgroundColor(this,this.parentNode.parentNode);"/>
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
                                                       class="summ" type="text" name="compensation" value='${param["compensation"]}'
                                                       onfocus="setBackgroundColor(this,this.parentNode.parentNode);"
                                                       onblur="removeBackgroundColor(this,this.parentNode.parentNode);"/>
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
                                                <input 
                                                    <%
                                                        if (request.getParameter("importantly") != null)
                                                        {
                                                            out.println("checked");
                                                        }
                                                    %>
                                                    title="Пометить как важный" type="checkbox" name="importantly" value=""/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <textarea name="comment" cols="50" rows="20"
                                              onfocus="setBackgroundColor(this);"
                                              onblur="removeBackgroundColor(this);"
                                              ><%= comment%></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="toRight">
                                    <input type="reset" name="reset" value="Сброс"/>
                                    <input disabled type="submit" name="send" value="Добавить"/>
                                </td>
                            </tr>
                        </table>
                    </form>
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
