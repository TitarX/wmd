$(document).ready(function() {

    //Модальное окно
    function maskSize()
    {
        var maskH = $('#content').height();
        var maskW = $('#content').width();
        $('#mask').css({
            'width':maskW,
            'height':maskH
        });
    }
    
    function dialogPosition()
    {
        var conH = $('#content').height();
        var conW = $('#content').width();
        $('#dialog').css('top',  conH/3-$('#dialog').height()/3);
        $('#dialog').css('left', conW/2-$('#dialog').width()/2);
    }
    
    function boxesPosition()
    {
        var scrollTop=$('#content').scrollTop();
        $('#boxes').css({
            'top':scrollTop
        });
    }
    
    $('tr[name=modal]').click(function(e) {
        e.preventDefault();
	
        maskSize();
        $('#mask').fadeIn(1000);
        $('#mask').fadeTo("slow",0.8);
	
        dialogPosition();	
        $('#dialog').fadeIn(2000); 
        
        boxesPosition();
        $('#content').scroll(function(){
            boxesPosition();
        });
        
        $(window).resize(function(){
            maskSize();
            dialogPosition();
            boxesPosition();
        });
    });
	
    $('#closeModal').click(function (e) {
        e.preventDefault();
        $('#mask,#dialog').hide();
    });
	
    $('#mask').click(function () {
        $(this).hide();
        $('#dialog').hide();
    });
    //Модальное окно
    
    $("#credits").dataTable({
        "oLanguage": {
            "sUrl": "http://localhost:8084/WMD/css/jquery/tables/ru_RU.txt"
        },
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "aLengthMenu": [
        [10,20,50,100,-1],
        [10,20,50,100,"Все"]]
    });
    
    setPeriodCalendar('input.datePeriod');
});
    
//Confirm окно
function confirm(message, callback) {
    $('#confirm').modal({
        closeHTML: "<a href='#' title='Close' class='modal-close'>x</a>",
        position: ["20%", ],
        overlayId: 'confirm-overlay',
        containerId: 'confirm-container',
        onShow: function (dialog) {
            $('.message', dialog.data[0]).append(message);

            $('.yes', dialog.data[0]).click(function () {
                if ($.isFunction(callback)) {
                    callback.apply();
                }
            //$('#closeModal').click();
            });
        }
    });
}
//Confirm окно
    
function deleteNote()
{
    //$('#closeModal').click();
    confirm("Удалить запись?",function(){
        $("#deleteNoteButton").click();
    });
}

//Ajax
function createXHR()
{
    try{
        return new XMLHttpRequest();
    }catch(e){}
    try{
        return new ActiveXObject("Msxml2.XMLHTTP.6.0");
    }catch(e){}
    try{
        return new ActiveXObject("Msxml2.XMLHTTP.3.0");
    }catch(e){}
    try{
        return new ActiveXObject("Msxml2.XMLHTTP");
    }catch(e){}
    try{
        return new ActiveXObject("Microsoft.XMLHTTP");
    }catch(e){}
    alert("XMLHttpRequest not supported");
    return null;
}

function setFormValues(id)
{
    document.getElementsByName("idd")[0].value=id;
    document.getElementsByName("ida")[0].value=id;
    
    var xhr=createXHR();

    /*
    //Отправка параметров в запросе GET
    if(xhr)
    {
        var params="name="+document.getElementsByName("name")[0].value;
        var getRequest="AjaxServlet?"+params;
        xhr.open("GET",getRequest,true);
        xhr.onreadystatechange=function(){handleResponse(xhr);};
        xhr.send(null);
    }
    */

    //Отправка параметров в запросе POST
    if(xhr)
    {
        var params="id="+encodeURIComponent(id);
        var getRequest="http://localhost:8084/WMD/servlets/getterValueCreditAjax";
        xhr.open("POST",getRequest,true);
        xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        xhr.onreadystatechange=function(){
            handleResponse(xhr);
        };
        xhr.send(params);
    }
}

function handleResponse(xhr)
{
    if(xhr.readyState==4&&xhr.status==200)
    {
        var parseResponse=xhr.responseXML;
        document.getElementsByName("borrower")[0].value=parseResponse.getElementsByTagName("borrower")[0].firstChild.nodeValue;
        document.getElementsByName("ncredit")[0].value=parseResponse.getElementsByTagName("ncredit")[0].firstChild.nodeValue;
        document.getElementsByName("nrequistion")[0].value=parseResponse.getElementsByTagName("nrequest")[0].firstChild.nodeValue;
        document.getElementsByName("scredit")[0].value=parseResponse.getElementsByTagName("creditsumm")[0].firstChild.nodeValue;
        document.getElementsByName("sreturn")[0].value=parseResponse.getElementsByTagName("returnsumm")[0].firstChild.nodeValue;
        document.getElementsByName("begin")[0].value=parseResponse.getElementsByTagName("begindate")[0].firstChild.nodeValue;
        document.getElementsByName("end")[0].value=parseResponse.getElementsByTagName("enddate")[0].firstChild.nodeValue;
        
        var factdate=parseResponse.getElementsByTagName("factdate")[0].firstChild.nodeValue;
        var returN=parseResponse.getElementsByTagName("returN")[0].firstChild.nodeValue;
        var compensation=parseResponse.getElementsByTagName("refund")[0].firstChild.nodeValue;
        var comment=parseResponse.getElementsByTagName("comment")[0].firstChild.nodeValue;
        if(factdate==0)
        {
            document.getElementsByName("fact")[0].value="";
        }
        else
        {
            document.getElementsByName("fact")[0].value=factdate;
        }
        if(returN==0)
        {
            document.getElementsByName("return")[0].value="";
        }
        else
        {
            document.getElementsByName("return")[0].value=returN;
        }
        if(compensation==0)
        {
            document.getElementsByName("compensation")[0].value="";
        }
        else
        {
            document.getElementsByName("compensation")[0].value=compensation;
        }
        if(comment==0)
        {
            document.getElementsByName("comment")[0].value="";
        }
        else
        {
            document.getElementsByName("comment")[0].value=comment;
        }
        
        var importantly=parseResponse.getElementsByTagName("importantly")[0].firstChild.nodeValue;
        if(importantly==0)
        {
            $("input[name=importantly]").removeAttr("checked");
        }
        else
        {
            $("input[name=importantly]").attr("checked","checked");
        }
    }
}
//Ajax

function setFields(t)
{
    if(t==1)
    {
        $(".dateFields").remove();
        $("#periodTable").append(
            '<tr class="dateFields">'
            +'<td  colspan="2">'
            +'<input id="from" type="hidden" name="from" value="2000.01.01"/>'
            +'</td>'
            +'</tr>'
            +'<tr class="dateFields">'
            +'<td colspan="2">'
            +'<input id="to" type="hidden" name="to" value="0"/>'
            +'</td>'
            +'</tr>'
            );
    }
    if(t==2)
    {
        $(".dateFields").remove();
        $("#periodTable").append(
            '<tr class="dateFields">'
            +'<td colspan="2">'
            +'<input id="from" type="hidden" name="from" value="2000.01.01"/>'
            +'</td>'
            +'</tr>'
            +'<tr class="dateFields">'
            +'<td class="toRight">'
            +'До:&nbsp;'
            +'</td>'
            +'<td class="toLeft">'
            +'<input id="to" readonly class="datePeriod" type="text" name="to" value=""'
            +'onfocus="setBackgroundColor(this);" onblur="removeBackgroundColor(this);"'
            +'oninput="copyFields();" onpropertychange="copyFields();"/>'
            +'</td>'
            +'</tr>'
            );
        setPeriodCalendar('input.datePeriod');
    }
    if(t==3)
    {
        $(".dateFields").remove();
        $("#periodTable").append(
            '<tr class="dateFields">'
            +'<td class="toRight">'
            +'От:&nbsp;'
            +'</td>'
            +'<td class="toLeft">'
            +'<input id="from" readonly class="datePeriod" type="text" name="from" value=""'
            +'onfocus="setBackgroundColor(this);" onblur="removeBackgroundColor(this);"'
            +'oninput="copyFields();" onpropertychange="copyFields();"/>'
            +'</td>'
            +'</tr>'
            +'<tr class="dateFields">'
            +'<td class="toRight">'
            +'До:&nbsp;'
            +'</td>'
            +'<td class="toLeft">'
            +'<input id="to" readonly class="datePeriod" type="text" name="to" value=""'
            +'onfocus="setBackgroundColor(this);" onblur="removeBackgroundColor(this);"'
            +'oninput="copyFields();" onpropertychange="copyFields();"/>'
            +'</td>'
            +'</tr>'
            );
        setPeriodCalendar('input.datePeriod');
    }
    document.exportForm.elements['typePeriod'].value=t;
}

function setPeriodCalendar(el)
{
    $.datepicker.setDefaults($.extend($.datepicker.regional["ru"]));
    
    $(el).datepicker
    ({
        maxDate: '0d',
        dateFormat: 'yy.mm.dd',
        numberOfMonths: 2,
        stepMonths: 2
    });
}

function copyFields()
{
    //    document.exportForm.elements['from'].value=document.periodForm.elements['from'].value;
    //    document.exportForm.elements['to'].value=document.periodForm.elements['to'].value;

    //    document.getElementById("filler").innerHTML=$("#to").val();
    
    document.exportForm.elements['from'].value=$("#from").val();
    document.exportForm.elements['to'].value=$("#to").val();
}
