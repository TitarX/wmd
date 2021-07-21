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
        return /^[^ \t\n\r]$/.test(String.fromCharCode(cCode));
    }
}

$(document).ready(function()
{
    setInputTextType('input.text');
});
