$(function () {
    var nums;
    var totalDatas;
    var intNums;
    var count;
    var page = {
        pageNum:1,
        pageSize:10
    }
    //每页展示的数量
    var paramData = {
        pageNum:page.pageNum,
        pageSize:page.pageSize
    };
    window.onload = function () {
        $(".two").addClass("pageColor");
        getData(paramData);
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
                if(count <40){
                    $("#page").css({"padding-left":"186px"});
                }else {
                    $("#page").css({"padding-left":"10px"});
                }
                if(count == 0){
                    $("#paging").hide();
                    $("#errorImg").show();
                    $("#containerAdmin").css("height","754px");
                }else{
                    totalDatas = result.data.list;
                    nums = Math.ceil(count / page.pageSize);
                    //舍弃小数之后的取整
                    intNums = parseInt(count / page.pageSize);
                    $("#tblbody table>tr").remove();
                    for (var i=0;i<totalDatas.length;i++){
                        var status = totalDatas[i].enabled==1?"1":"0";
                        var domains = totalDatas[i].domains?totalDatas[i].domains:"-";
                        var university =  totalDatas[i].university?totalDatas[i].university:"-";
                        if(status == 1){
                            var str=" <tr myid="+totalDatas[i].id+"><td><p class='notOverflow'>"+totalDatas[i].username+"</p></td><td><p class='notOverflow'>"+totalDatas[i].email+"</p></td><td><p class='notOverflow'>"+domains+"</p></td><td><p class='notOverflow'>"+university+"</p></td><td><p class='notOverflow'>已审核</p></td><td><p class='notOverflow'>"+totalDatas[i].enableDay+"</p></td><td><p class=\'btnAudited btnCommon\'>已审核</p></td></tr>";
                        }else{
                            var str=" <tr myid="+totalDatas[i].id+"><td><p class='notOverflow'>"+totalDatas[i].username+"</p></td><td><p class='notOverflow'>"+totalDatas[i].email+"</p></td><td><p class='notOverflow'>"+domains+"</p></td><td><p class='notOverflow'>"+university+"</p></td><td><p class='notOverflow'>待审核</p></td><td><p class='notOverflow'>"+totalDatas[i].enableDay+"</p></td><td><p class=\'btnAudit btnCommon\'>待审核</p></td></tr>";
                        }
                        console.log(status);
                        var $tbl = $("#tblbody table");
                        $tbl.append(str);
                        pageStyle(nums,intNums);
                        $("#totals").text(count);
                    }

                }
            },
            error:function (error){
                if(error.status==901){window.location.href=CTXROOT + "/login"};
            }
        })
    }
    // 样式调整方法
    function pageStyle(nums,intNums){
                if (nums > 4) {
                    // $(".first").hide().next().text(1).next().hide();
                    $(".first").next().text(1);
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
    $("#page>p").click(function (e) {
        //样式
        if (nums > 4) {
            $(".first").show();
            $(".three").show();
            $(".eight").text(nums);
        };
        var $p = $(e.target);

        page.pageNum = parseInt($p.text());
        paramData.pageNum = page.pageNum;
        getData(paramData);
        var plists = $p.siblings();
        for (var i = 0; i < plists.length; i++) {
            if ($(plists[i]).hasClass("pageColor")) {
                $(plists[i]).removeClass("pageColor");
            }
        }
        $p.addClass("pageColor");

    });
    // pageSize 选择事件
    $("#selectedNum").change(function (e){
        var currentSelected = $("#selectedNum option:selected").text();
        page.pageSize = currentSelected;
        paramData.pageSize = page.pageSize;
        getData(paramData)
        console.log(currentSelected);
    })
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
    // enter 键盘事件
    $("#inputNum").keydown(function(event){
        event=document.all?window.event:event;
        if((event.keyCode || event.which)==13){
            var selectedNum = $(this).val();
            page.pageNum = pageNum = selectedNum;
            paramData.pageNum = page.pageNum;
            getData(paramData);
        }
    });
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

        $("#tblbody table").on("mouseover",function (e) {
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

    $("#tblbody table").on("click",function (e) {
        var $p = $(e.target);
        var selfId = parseInt($p.parent().parent().attr("myid"));
        var data = {
                id:selfId
        };
        if ($p.hasClass("btnAudit")) {
            $p.removeClass("btnAudit").addClass("btnWait");
            $p.text("审核中");
            $.ajax({
                type:"POST",
                url:ctxRoot + "/manager/change/enable",
                data:data,
                success:function (result){
                    console.log(result);
                    // 0  为成功
                    if(result.code == 0){
                        $p.removeClass("btnWait").addClass("btnAudited").text("已审核");
                        $p.parent().prev().prev().text("已审核");
                        // -1 为系统异常
                    }else if(result.code == -1){
                        $p.parent().prev().prev().text("审核失败")
                        $p.text("待审核");
                        $p.removeClass("btnWait").addClass("btnAudit");
                        //  -2 审核没通过
                    }else if(result.code == -2){
                        $p.removeClass("btnWait").addClass("btnAudited").text("未通过");
                        $p.parent().prev().prev().text("未通过");
                        // -3 邮箱重复
                    }else if(result.code == -3){
                        $p.parent().prev().prev().text("邮箱重复").css("color","#ff0000");
                        $p.removeClass("btnWait").addClass("btnAudited").text("未通过");
                    }
                },
                error:function (error){

                }
            })

        }
    })
})
