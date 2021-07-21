function checkAvailabilityInput(emailTo,emailFrom)
{
    if(((/^[_0-9a-zA-Zа-яА-Я][-._0-9a-zA-Zа-яА-Я]{0,29}[_0-9a-zA-Zа-яА-Я]@([0-9a-zA-Zа-яА-Я][-0-9a-zA-Zа-яА-Я]*[0-9a-zA-Zа-яА-Я]\.)+[a-zA-Zа-яА-Я]{2,8}$/).test(emailTo))
        &&((/^[_0-9a-zA-Zа-яА-Я][-._0-9a-zA-Zа-яА-Я]{0,29}[_0-9a-zA-Zа-яА-Я]@([0-9a-zA-Zа-яА-Я][-0-9a-zA-Zа-яА-Я]*[0-9a-zA-Zа-яА-Я]\.)+[a-zA-Zа-яА-Я]{2,8}$/).test(emailFrom)))
    {
        document.sendEmail.elements['send'].disabled=false;
    }
    else
    {
        document.sendEmail.elements['send'].disabled=true;
    }
    checkValidEmailTo(emailTo);
    checkValidEmailFrom(emailFrom);
}

function checkValidEmailTo(emailTo)
{
    if((/^[_0-9a-zA-Zа-яА-Я][-._0-9a-zA-Zа-яА-Я]{0,29}[_0-9a-zA-Zа-яА-Я]@([0-9a-zA-Zа-яА-Я][-0-9a-zA-Zа-яА-Я]*[0-9a-zA-Zа-яА-Я]\.)+[a-zA-Zа-яА-Я]{2,8}$/).test(emailTo))
    {
        document.getElementById('emailToValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('emailToValidIndicator').style.color="#FF0000";
    }
}

function checkValidEmailFrom(emailFrom)
{
    if((/^[_0-9a-zA-Zа-яА-Я][-._0-9a-zA-Zа-яА-Я]{0,29}[_0-9a-zA-Zа-яА-Я]@([0-9a-zA-Zа-яА-Я][-0-9a-zA-Zа-яА-Я]*[0-9a-zA-Zа-яА-Я]\.)+[a-zA-Zа-яА-Я]{2,8}$/).test(emailFrom))
    {
        document.getElementById('emailFromValidIndicator').style.color="#00FF00";
    }
    else
    {
        document.getElementById('emailFromValidIndicator').style.color="#FF0000";
    }
}

function resetEditProfile()
{
    document.getElementById('emailToValidIndicator').style.color="#FF0000";
    document.getElementById('emailFromValidIndicator').style.color="#FF0000";
}

function setInputTextType(el)
{
    $(el).keypress( function(e)
    {
        if($.browser.msie)
        {
            return isNum(e.keyCode)
        }
        else
        {
            return (e.keyCode) ? true : isNum(e.charCode)
        }
    });
    function isNum(cCode)
    {
        return /^[^\s]$/.test(String.fromCharCode(cCode));
    }
}

$(document).ready(function()
{
    setInputTextType('input.text');
});
