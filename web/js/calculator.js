function addElement(number)
{
    if(document.getElementById("checkbox"+number).checked)
    {
        var nextNumber=number;
        nextNumber++;
        document.getElementsByName("number")[0].value=nextNumber;
        $("#checkbox2").unbind("click");
        $("#calculatorTable").append(
            '<tr class="wrapFormElement extra">'
            +'<td class="toLeft">'
            +'Сумма'
            +'</td>'
            +'<td class="toRight">'
            +'<input type="text" name="inFactSumm'+number+'" value="" class="summ" onfocus="setBackgroundColor(this);" onblur="removeBackgroundColor(this);"/>'
            +'</td>'
            +'</tr>'
            +'<tr class="wrapFormElement extra">'
            +'<td class="toLeft">'
            +'Дата (гмд)'
            +'</td>'
            +'<td class="toRight">'
            +'<input readonly type="text" name="inFactDate'+number+'" value="" class="date" onfocus="setBackgroundColor(this);" onblur="removeBackgroundColor(this);"/>'
            +'</td>'
            +'</tr>'
            +'<tr class="extra">'
            +'<td colspan="2">'
            +'<hr />'
            +'</td>'
            +'</tr>'
            +'<tr class="extra">'
            +'<td colspan="2" class="toLeft">'
            +'<input type="checkbox" id="checkbox'+nextNumber+'" onclick="addElement(number.value);"/>&nbsp;Возврат '+nextNumber
            +'</td>'
            +'</tr>'
            );
                
        setCalendar('input.date');
        setInputType('input.summ');
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

function setInputType(el)
{
    $(el).keypress( function(e)
    {
        if($.browser.msie)
        {
            return isNum(e.keyCode);
        }
        else
        {
            return (e.keyCode) ? true : isNum(e.charCode);
        }
    });
    function isNum(cCode)
    {
        return /^[0-9]+|\.$/.test(String.fromCharCode(cCode));
    }
}

$(document).ready(function()
{
    setCalendar('input.date');
    setInputType('input.summ');
    
    $("#checkbox2").click(function(){
        addElement(2);
    });
});

function checkAvailabilityInput(exSumm,inSumm,exDate,inDate)
{
    if(((/^\d+(\.\d+){0,1}$/).test(exSumm))
        &&((/^\d+(\.\d+){0,1}$/).test(inSumm))
        &&((/^[0-9]{4}\.[0-9]{2}\.[0-9]{2}$/).test(exDate))
        &&((/^[0-9]{4}\.[0-9]{2}\.[0-9]{2}$/).test(inDate)))
        {
        document.resultForm.elements['resultButton'].disabled=false;
    }
    else
    {
        document.resultForm.elements['resultButton'].disabled=true;
    }
    checkValidexSumm(exSumm);
    checkValidinSumm(inSumm);
    checkValidexDate(exDate);
    checkValidinDate(inDate);
}

function checkValidexSumm(exSumm)
{
    if((/^\d+(\.\d+){0,1}$/).test(exSumm))
    {
        document.getElementById('exSummValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('exSummValidIndicator').style.color="#FF0000";
    }
}
function checkValidinSumm(inSumm)
{
    if((/^\d+(\.\d+){0,1}$/).test(inSumm))
    {
        document.getElementById('inSummValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('inSummValidIndicator').style.color="#FF0000";
    }
}

function checkValidexDate(exDate)
{
    if((/^[0-9]{4}\.[0-9]{2}\.[0-9]{2}$/).test(exDate))
    {
        document.getElementById('exDateValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('exDateValidIndicator').style.color="#FF0000";
    }
}
function checkValidinDate(inDate)
{
    if((/^[0-9]{4}\.[0-9]{2}\.[0-9]{2}$/).test(inDate))
    {
        document.getElementById('inDateValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('inDateValidIndicator').style.color="#FF0000";
    }
}

function resetCalculator()
{
    $("#checkbox2").unbind("click");
    $(".extra").remove();
    $("#checkbox2").click(function(){
        addElement(2);
    });
    document.getElementById('exSummValidIndicator').style.color="#FF0000";
    document.getElementById('inSummValidIndicator').style.color="#FF0000";
    document.getElementById('exDateValidIndicator').style.color="#FF0000";
    document.getElementById('inDateValidIndicator').style.color="#FF0000";
    document.resultForm.elements['resultButton'].disabled=true;
    document.resultForm.elements['result'].value="";
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

function makeCalculation()
{
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
        var typeCalculation=$('input[name="typeCalculation"]:checked').val();
        var exSumm=document.calculatorForm.elements['exSumm'].value;
        var inSumm=document.calculatorForm.elements['inSumm'].value;
        var exDate=document.calculatorForm.elements['exDate'].value;
        var inDate=document.calculatorForm.elements['inDate'].value;
        
        var params="typeCalculation="+encodeURIComponent(typeCalculation)
        +"&"+"exSumm="+encodeURIComponent(exSumm)
        +"&"+"inSumm="+encodeURIComponent(inSumm)
        +"&"+"exDate="+encodeURIComponent(exDate)
        +"&"+"inDate="+encodeURIComponent(inDate);
    
        var number=document.calculatorForm.elements['number'].value;
        var inFactNumber=0;
        for(var i=1;i<number;i++)
        {
            var checkboxNumber="checkbox"+i;
            if(document.getElementById(checkboxNumber).checked)
            {
                var tempSumm=document.calculatorForm.elements['inFactSumm'+i].value;
                var tempDate=document.calculatorForm.elements['inFactDate'+i].value;
                
                inFactNumber++;
                params=params
                +"&"+"inFactSumm"+inFactNumber+"="+encodeURIComponent(tempSumm)
                +"&"+"inFactDate"+inFactNumber+"="+encodeURIComponent(tempDate);
            }
        }
        params=params+"&"+"inFactNumber="+encodeURIComponent(inFactNumber);
        
        var getRequest="http://localhost:8084/WMD/calculatorAjax";
        xhr.open("POST",getRequest,true);
        xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        xhr.onreadystatechange=function()
        {
            handleResponse(xhr);
        };
        xhr.send(params);
    }
}

function handleResponse(xhr)
{
    if(xhr.readyState==4&&xhr.status==200)
    {
        document.getElementsByName("result")[0].value=xhr.responseText;
    }
}
//Ajax
