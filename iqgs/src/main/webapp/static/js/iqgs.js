/**
 * Created by admin on 2017/10/11.
 */
$(function(){

    $("#btn_name").on('click', function(){
        var key = $("#key_name").val();
        if (key && !/^\s+$/.test(key)) {
            window.location = DOMAIN + "/search/list?keyword=" + encodeURI(key) + "&searchType=1";
        }
    });

    $("#btn_func").on('click', function(){
        var key = $("#key_func").val();
        if (key && !/^\s+$/.test(key)) {
            window.location = DOMAIN + "/search/list?keyword=" + encodeURI(key) + "&searchType=2";
        }
    });

    $("#btn_range").on('click', function(){
        var serial = $(".js-region").val();
        var rg_begin = $("#rg_begin").val();
        var rg_end = $("#rg_end").val();
        if (rg_begin && !/^\s+$/.test(rg_begin) && rg_end && !/^\s+$/.test(rg_end)) {
            window.location = DOMAIN + "/search/list?chr=" + encodeURI(serial) + "&begin="  + encodeURI(rg_begin) + "&end="  + encodeURI(rg_end) + "&searchType=3";
        }
    });
        // modify by jarry at 2017-12-18  qtl 确认搜索
    // $("#qtlAdd .sureBtn").on('click', function(){
    //     var qtlNameArr = globleObject.selectedQtl;
    //     var num = qtlNameArr.length;
    //     if(num>5){
    //         alert("最多只能选择 5 个");
    //         return;
    //     }else {
    //         var qtlName;
    //         $.each(qtlNameArr,function (i,item){
    //             qtlName += item + "&";
    //     });
    //    var qtlNames = qtlName.substring(0,qtlName.length-1);
    //         window.location = DOMAIN + "/search/list?keyword=" + qtlNames + "&searchType=4";
    //     }
    // })

    $("#myTabs li").each(function(i){
       $(this).click(function(){
           $(this).addClass("active").siblings().removeClass("active");
           var divs = $("#myTabContent>div");
           for(var k=0;k<divs.length;k++){
               if(k == i){
                   $(divs[k]).addClass("tab-pane-ac")
               }else {
                   $(divs[k]).removeClass("tab-pane-ac");
               }
           }
           // $("#myTabContent").find("div").eq(i).show().siblings().hide();
       })
    })


})


