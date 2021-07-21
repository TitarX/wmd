function checkAvailabilityInput(borrower,ncredit,nreqistion,scredit,sreturn,begin,end)
{
    if(((/^[^\s]+$/).test(borrower))&&((/^[^\s]+$/).test(ncredit))&&((/^[^\s]+$/).test(nreqistion))&&((/^\d+(\.\d+){0,1}$/).test(scredit))
        &&((/^\d+(\.\d+){0,1}$/).test(sreturn))&&((/^[0-9]{4}\.[0-9]{2}\.[0-9]{2}$/).test(begin))&&((/^[0-9]{4}\.[0-9]{2}\.[0-9]{2}$/).test(end)))
        {
        document.addCreditForm.elements['send'].disabled=false;
    }
    else
    {
        document.addCreditForm.elements['send'].disabled=true;
    }
    
//    var date=new Date();
//    var currentdate=date.getYear()+"."+(date.getMonth()+1)+"."+date.getDate();
    
    if(($('#factId').val()!="")&&($('#returnId').val()!=$('#sreturnId').val()))
    {
        document.addCreditForm.elements['return'].value=$('#sreturnId').val();
    }
//    if((($('#returnId').val())!="")&&(($('#returnId').val())==($('#sreturnId').val()))&&($('#factId').val()==""))
//    {
//        document.addCreditForm.elements['fact'].value=currentdate;
//    }
    
    checkBorrowerInput(borrower);
    checkNcreditInput(ncredit);
    checkNrequistionInput(nreqistion);
    checkScreditInput(scredit);
    checkSreturnInput(sreturn);
    checkBeginInput(begin);
    checkEndInput(end);
}

function checkBorrowerInput(borrower)
{
    if((/^[^\s]+$/).test(borrower))
    {
        document.getElementById('borrowerValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('borrowerValidIndicator').style.color="#FF0000";
    }
}
function checkNcreditInput(ncredit)
{
    if((/^[^\s]+$/).test(ncredit))
    {
        document.getElementById('ncreditValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('ncreditValidIndicator').style.color="#FF0000";
    }
}
function checkNrequistionInput(nreqistion)
{
    if((/^[^\s]+$/).test(nreqistion))
    {
        document.getElementById('nrequistionValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('nrequistionValidIndicator').style.color="#FF0000";
    }
}
function checkScreditInput(scredit)
{
    if((/^\d+(\.\d+){0,1}$/).test(scredit))
    {
        document.getElementById('screditValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('screditValidIndicator').style.color="#FF0000";
    }
}
function checkSreturnInput(sreturn)
{
    if((/^\d+(\.\d+){0,1}$/).test(sreturn))
    {
        document.getElementById('sreturnValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('sreturnValidIndicator').style.color="#FF0000";
    }
}
function checkBeginInput(begin)
{
    if((/^[0-9]{4}\.[0-9]{2}\.[0-9]{2}$/).test(begin))
    {
        document.getElementById('beginValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('beginValidIndicator').style.color="#FF0000";
    }
}
function checkEndInput(end)
{
    if((/^[0-9]{4}\.[0-9]{2}\.[0-9]{2}$/).test(end))
    {
        document.getElementById('endValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('endValidIndicator').style.color="#FF0000";
    }
}

function resetEditProfile()
{
    document.addCreditForm.elements['send'].disabled=true;
    document.getElementById('borrowerValidIndicator').style.color="#FF0000";
    document.getElementById('ncreditValidIndicator').style.color="#FF0000";
    document.getElementById('nrequistionValidIndicator').style.color="#FF0000";
    document.getElementById('screditValidIndicator').style.color="#FF0000";
    document.getElementById('sreturnValidIndicator').style.color="#FF0000";
    document.getElementById('beginValidIndicator').style.color="#FF0000";
    document.getElementById('endValidIndicator').style.color="#FF0000";
}

$(document).ready(function()
{
//    $('#commentId').each(function(){
//        $(this).data('default',$(this).val())
//        .addClass('inactive')
//        .focus(function(){
//            $(this).removeClass('inactive');
//            if($(this).val()==$(this).data('default')||''){
//                $(this).val('');
//            }
//        })
//        .blur(function(){
//            var default_val=$(this).data('default');
//            if($(this).val()==''){
//                $(this).addClass('inactive');
//                $(this).val($(this).data('default'));
//            }
//        });
//    });
    
    setCalendar('input.date');
    setInputMoneyType('input.summ');
    setInputTextType('input.text');
});

function setCalendar(el)
{
    $.datepicker.setDefaults($.extend($.datepicker.regional["ru"]));
    
    $(el).datepicker
    ({
        dateFormat: 'yy.mm.dd',
        numberOfMonths: 2,
        stepMonths: 2
    });
}

function setInputMoneyType(el)
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

function setInputTextType(el)
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
        return /^[^\s]$/.test(String.fromCharCode(cCode));
    }
}
