/**
 * Created by Administrator on 2017/7/6 0006.
 */
$("#js-pop-genes").click(function(e){
    e.preventDefault();
    $("#mid").show();
    $(".tab-detail").show();
})
$(".tab-detail-thead a").click(function(){
    $("#mid").hide();
    $(".tab-detail").hide();
    $(".links-pop").hide();
})

$(".btn-toggle").click(function(){
    $(".checkbox-list").hide();
    $(".btn-group").hide();
    $(".set-up").show();
})
$(".abstract").click(function(e){
    e.preventDefault();
    $("#mid").show();//遮挡层
    $(".links-pop").show();//弹出框
    //$(".links-text tbody").html("")//弹出页面清空
    var abs_html=$(this).attr("data-src");
    //获取REFERENCE INFORMATION 内容 添加到弹出框
    var item=$(".reference-information-tab table tbody ").html()
    console.log(item)
    //获取Abstract内容 并添加到弹出框
    var abs=""
    abs +="<tr class='abs' >"
    abs +="     <td>Abstract</td>"
    abs +="     <td>"
    abs +="         <p>"+abs_html+"</p>"
    abs +="     </td>"
    abs +="</tr>"
    $(".links-text tbody").html(item+abs);
    //弹出框 标题冒号添加
    $(".links-text tbody tr td:even").append(":")
})
$(".js-pop-head").find("a").click(function(e){
    e.preventDefault();
    $("#mid").hide();
    $(".js-pop").hide();
})