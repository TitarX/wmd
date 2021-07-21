function setFields(t)
{
    if(t==1)
    {
        $(".dateFields").remove();
        $("#calculationsTable").append(
            '<tr class="dateFields">'
            +'<td>'
            +'<input type="hidden" name="from" id="from" value="2000.01.01"/>'
            +'</td>'
            +'</tr>'
            +'<tr class="dateFields">'
            +'<td>'
            +'<input type="hidden" name="to" id="to" value="0"/>'
            +'</td>'
            +'</tr>'
            );
    }
    if(t==2)
    {
        $(".dateFields").remove();
        $("#calculationsTable").append(
            '<tr class="dateFields">'
            +'<td>&nbsp;'
            +'</td>'
            +'</tr>'
            +'<tr class="dateFields">'
            +'<td>'
            +'<input type="hidden" name="from" id="from" value="2000.01.01"/>'
            +'</td>'
            +'</tr>'
            +'<tr class="dateFields">'
            +'<td>'
            +'До&nbsp;<input readonly class="date" type="text" name="to" id="to" value="" onfocus="setBackgroundColor(this);" onblur="removeBackgroundColor(this);"/>'
            +'</td>'
            +'</tr>'
            );
        setCalendar('input.date');
    }
    if(t==3)
    {
        $(".dateFields").remove();
        $("#calculationsTable").append(
            '<tr class="dateFields">'
            +'<td>&nbsp;'
            +'</td>'
            +'</tr>'
            +'<tr class="dateFields">'
            +'<td>'
            +'От&nbsp;<input readonly class="date" type="text" name="from" id="from" value="" onfocus="setBackgroundColor(this);" onblur="removeBackgroundColor(this);"/>'
            +'</td>'
            +'</tr>'
            +'<tr class="dateFields">'
            +'<td>'
            +'До&nbsp;<input readonly class="date" type="text" name="to" id="to" value="" onfocus="setBackgroundColor(this);" onblur="removeBackgroundColor(this);"/>'
            +'</td>'
            +'</tr>'
            );
        setCalendar('input.date');
    }
}

function setCalendar(el)
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

function calculate()
{
    var from=$('#from').val();
    var to=$('#to').val();
    
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
        if((from!="")&&(to!=""))
        {
            var params="from="+encodeURIComponent(from)+"&"+"to="+encodeURIComponent(to);
            var getRequest="http://localhost:8084/WMD/servlets/calculateTotalAjax";
            xhr.open("POST",getRequest,true);
            xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            xhr.onreadystatechange=function(){
                handleResponse(xhr);
            };
            xhr.send(params);
        }
    }
}

function handleResponse(xhr)
{
    if(xhr.readyState==4&&xhr.status==200)
    {
        var parseResponse=xhr.responseXML;
        
        $('#issued').empty();
        $('#toReturn').empty();
        $('#returned').empty();
        $('#delayed').empty();
        $('#commissionWM').empty();
        $('#commissionCM').empty();
        $('#refund').empty();
        $('#profit').empty();
        
        $('#issued').append(parseResponse.getElementsByTagName("issued")[0].firstChild.nodeValue);
        $('#toReturn').append(parseResponse.getElementsByTagName("toReturn")[0].firstChild.nodeValue);
        $('#returned').append(parseResponse.getElementsByTagName("returned")[0].firstChild.nodeValue);
        $('#delayed').append(parseResponse.getElementsByTagName("delayed")[0].firstChild.nodeValue);
        $('#commissionWM').append(parseResponse.getElementsByTagName("commissionWM")[0].firstChild.nodeValue);
        $('#commissionCM').append(parseResponse.getElementsByTagName("commissionCM")[0].firstChild.nodeValue);
        $('#refund').append(parseResponse.getElementsByTagName("refund")[0].firstChild.nodeValue);
        $('#profit').append(parseResponse.getElementsByTagName("profit")[0].firstChild.nodeValue);
    }
}
//Ajax
