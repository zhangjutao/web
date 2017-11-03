$(function () {
    var nums;
    var totalDatas;
    var intNums;
    var count;
    //每页展示的数量
    var pageSize = 10;
    window.onload = function () {
        var param = {
            pageNum:1,
            pageSize:10
        }
        getData(param);
    };
    //ajax 请求
    function getData(data){
        $.ajax({
            type:"GET",
            url: ctxRoot + "/manager/users",
            data:data,
            success:function (result) {
                    console.log(result);
                    count = result.data.total;
                    totalDatas = result.data.list;
                    nums = Math.ceil(count / pageSize);
                    //舍弃小数之后的取整
                    intNums = parseInt(count / pageSize);
                    $("#tblbody table>tr").remove();
                    for (var i=0;i<totalDatas.length;i++){
                    var status = totalDatas[i].enabled==1?"已审核":"待审核";
                    console.log(status);
                    var str=" <tr><td>"+totalDatas[i].username+"</td><td>"+totalDatas[i].email+"</td><td>" +status+"</td><td><p class=\'btnAudited btnCommon\'>"+status+"</p></td></tr>";
                    var $tbl = $("#tblbody table");
                    $tbl.append(str);
                    pageStyle(nums,intNums);
                    $("#totals").text(count);
                }
            },
            error:function (error){
                console.log(error);
            }
        })
    }
    // 样式调整方法
    function pageStyle(nums,intNums){
                if (nums > 4) {
                    $(".first").hide().next().text(1).addClass("pageColor").next().hide();
                    $(".four").text(2).next().text(3).next().text(4);
                    $(".eight").text(nums);
                    $(".seven").show();
                    $(".last").show();
                };
                if (intNums == 0) {
                    styleChange();
                    $(".two").text(1);
                    $(".four").hide();
                    $(".five").hide();
                    $(".six").hide();
                }
                switch (nums) {
                    case 1:
                        styleChange();
                        $(".two").text(1);
                        $(".four").hide();
                        $(".five").hide();
                        $(".six").hide();
                        break;
                    case 2:
                        styleChange();
                        $(".two").text(1);
                        $(".four").text(2);
                        $(".five").hide();
                        $(".six").hide();
                        break;
                    case 3:
                        styleChange();
                        $(".two").text(1);
                        $(".four").text(2);
                        $(".five").text(3);
                        $(".six").hide();
                        break;
                    case 4:
                        styleChange();
                        $(".two").text(1);
                        $(".four").text(2);
                        $(".five").text(3);
                        $(".six").text(4);
                        break;
                }
    }
    // 显示隐藏样式封装
    function styleChange() {
        $(".three").hide();
        $(".first").hide();
        $(".seven").hide();
        $(".eight").hide();
        $(".last").hide();
    };
    //每个页码的点击事件
    $("#page p").click(function (e) {
        //样式
        if (nums > 4) {
            $(".first").show();
            $(".three").show();
            $(".eight").text(nums);
        }
        var $p = $(e.target);
        var data = {
            pageNum:parseInt($p.text()),
            pageSize:pageSize
        }
        getData(data);
        $p.addClass("pageColor");
        var plists = $p.siblings();
        for (var i = 0; i < plists.length; i++) {
            if ($(plists[i]).hasClass("pageColor")) {
                $(plists[i]).removeClass("pageColor");
            }
        }
    });
    // "<" 点击事件
    $(".first").click(function () {
        var content6 = Number($(".six").text());
        var content4 = Number($(".four").text());
        var content5 = Number($(".five").text());
        if (content6 < nums) {
            $(".last").show();
            $(".seven").show();
        }
        if (content4 <= 2) {
            $(".first").hide().next().next().hide();
        } else {
            $(".six").text(content6 - 1);
            $(".four").text(content4 - 1);
            $(".five").text(content5 - 1);
        }
    })
    // ">" 点击事件
    $(".last").click(function () {
        var content6 = Number($(".six").text());
        var content4 = Number($(".four").text());
        var content5 = Number($(".five").text());
        var content2 = Number($(".two").text());
        if (content2 == 1) {
            $(".first").show();
            $(".three").show();
        }

        if (content6 >= nums - 1) {
            $(".seven").hide();
            $(this).hide();
        } else {
            $(".six").text(content6 + 1);
            $(".four").text(content4 + 1);
            $(".five").text(content5 + 1);
        }
    })
    //     $("tr").mouseover(function (e) {
    //     var $tr = $(e.target).parent();
    //     console.log($tr)
    //     var trs = $tr.siblings();
    //     if (!$tr.hasClass("trColor")) {
    //         $tr.addClass("trColor");
    //     }
    //     for (var i = 0; i < trs.length; i++) {
    //         if ($(trs[i]).hasClass("trColor")) {
    //             $(trs[i]).removeClass("trColor");
    //         }
    //
    //     }
    // })
    $(".btnCommon").click(function (e) {
        var $p = $(e.target);
        // console.log(p);
        if (!$p.hasClass("btnAudited")) {
            $p.addClass("btnAudited");
            $p.text("已通过");
        }
    })
})
