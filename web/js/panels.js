$(document).ready(function()
    {
        $("#top").hide();
        $("#top-lable").click(function()
        {
            $("#top").slideToggle("slow");
            
            if($("div").is("#menu-show"))
            {
                $("#menu-show").remove();
                $('<div id="menu-hide"><image id="menu-hide-image" title="Закрыть" src="/WMD/images/menu/up.png"/><image id="menu-image" title="Меню" src="/WMD/images/menu/menu.png" alt="Меню"/></div>').appendTo("#top-lable");
            }
            else
            {
                if($("div").is("#menu-hide"))
                {
                    $("#menu-hide").remove();
                    $('<div id="menu-show"><image id="menu-show-image" title="Открыть" src="/WMD/images/menu/down.png"/><image id="menu-image" title="Меню" src="/WMD/images/menu/menu.png" alt="Меню"/></div>').appendTo("#top-lable");
                }
            }
            
            return false;
        });
        
        $("#bottom").hide();
        $("#bottom-lable").click(function()
        {
            $("#bottom").slideToggle("slow");
            
            if($("div").is("#panel-show"))
            {
                $("#panel-show").remove();
                $('<div id="panel-hide"><image id="panel-hide-image" title="Закрыть" src="/WMD/images/menu/down.png"/></div>').appendTo("#bottom-lable");
            }
            else
            {
                if($("div").is("#panel-hide"))
                {
                    $("#panel-hide").remove();
                    $('<div id="panel-show"><image id="panel-show-image" title="Открыть" src="/WMD/images/menu/up.png"/></div>').appendTo("#bottom-lable");
                }
            }
            
            return false;
        });
        
    //
    });
