$(function () {
    var nums;
    var totalDatas;
    var intNums;
    //每页展示的数量
    var pageNums = 10;
    window.onload = function () {
        $.ajax({
            type: "GET",
            url: "/manager/users",
            success: function (result) {
                //获取数组列表
                totalDatas = result;
                //向上取整
                nums = Math.ceil(result.length / pageNums);
                //舍弃小数之后的取整
                intNums = parseInt(result.length / pageNums);

                console.log(intNums);
                console.log("总长度为：" +totalDatas.length);
                console.log("除以pageNums之后的整数部分为：" + nums);

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
                $("#totals").text(totalDatas.length);
            },
            error: function (error) {
                console.log("error")
            }
        })
    };
    // 显示隐藏样式封装
    function styleChange() {
        console.log(1234);
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
        alert($p.text())
        $p.addClass("pageColor");
        var plists = $p.siblings();
        for (var i = 0; i < plists.length; i++) {
            if ($(plists[i]).hasClass("pageColor")) {
                $(plists[i]).removeClass("pageColor");
            }
        }
        //获取对应页面数据
        var currentNum = (Number($p.text())) * pageNums - 1;
        var showDatas = totalDatas.slice(currentNum, currentNum + pageNums);
        console.log(currentNum);
        console.log(showDatas);
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
    $("#tblbody tr").mouseover(function (e) {
        var $tr = $(e.target).parent();
        var trs = $tr.siblings();
        if (!$tr.hasClass("trColor")) {
            $tr.addClass("trColor");
        }
        for (var i = 0; i < trs.length; i++) {
            if ($(trs[i]).hasClass("trColor")) {
                $(trs[i]).removeClass("trColor");
            }

        }
    })
    $(".btnCommon").click(function (e) {
        var $p = $(e.target);
        // console.log(p);
        if (!$p.hasClass("btnAudited")) {
            $p.addClass("btnAudited");
            $p.text("已通过");
        }
    })
})
