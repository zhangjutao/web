/**
 * Created by admin on 2017/10/12.
 */
$(function(){
    $(".content-sidebar li").each(function(i){
        $(this).click(function(){
            $(this).addClass("sidebar-ac").siblings().removeClass("sidebar-ac");
            $(".explains > div").eq(i).show().siblings().hide();
        })
    })
})