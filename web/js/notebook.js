function checkTitle()
{
    var title=$.trim($("#title").val());
    
    if(title=="")
    {
        document.addNoteForm.elements["send"].disabled=true;
        document.getElementById("titleIndicator").style.color="#FF0000";
    }
    else
    {
        document.addNoteForm.elements["send"].disabled=false;
        document.getElementById("titleIndicator").style.color="#00FF00";
    }
}

function setBackgroundColorForTitle(el)
{
    $(el).css('background-color','lemonchiffon');
    $(el).css('border','1px solid #E56F03');
    $(el).css('color','#E56F03');
}

function removeBackgroundColorForTitle(el)
{
    $(el).css('background-color','transparent');
    $(el).css('border','1px solid #0000FF');
    $(el).css('color','#0000FF');
}

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
        
        checkTitle();
    });
	
    $('#closeModal').click(function (e) {
        e.preventDefault();
        $('#mask,#dialog').hide();
        document.getElementsByName("body")[0].value="";
    });
	
    $('#mask').click(function () {
        $(this).hide();
        $('#dialog').hide();
    });
    //Модальное окно
    
    $("#notes").dataTable({
        "oLanguage": {
            "sUrl": "http://localhost:8084/WMD/css/jquery/tables/ru_RU.txt"
        },
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "aLengthMenu": [
        [10,20,50,100,-1],
        [10,20,50,100,"Все"]]
    });
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

function getBody(id)
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
        var params="id="+encodeURIComponent(id)+"&tablename="+$("#tableName").val();
        var getRequest="http://localhost:8084/WMD/servlets/getterBodyNoteAjax";
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
        document.getElementsByName("body")[0].value=xhr.responseText;
    }
}
//Ajax

function setFormValues(title,id)
{
    if(title)
    {
        document.getElementsByName("title")[0].value=title;
    }
    else
    {
        document.getElementsByName("title")[0].value="";
    }
    
    if(id)
    {
        document.getElementsByName("idw")[0].value=id;
        document.getElementsByName("idd")[0].value=id;
        getBody(id);
    }
    else
    {
        document.getElementsByName("idw")[0].value="";
        document.getElementsByName("idd")[0].value="";
        document.getElementsByName("body")[0].value="";
    }
}
