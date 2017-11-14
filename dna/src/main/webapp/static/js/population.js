$(function (){
    var groupVal1="111利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal2="222利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal3="333利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal4="444利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal5="555利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal6="666利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal7="777利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal8="888利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal9="999利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal10="100利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";

    $("#groups li").click(function (e){
        var val = $(this).find("p").text();
        console.log(val);
        $("#popTips").show();
        $(".tipTopCnt").text(val);
        switch (val){
            case "Group 1":
                $(".groupCnt").text(groupVal1);
                break;
            case "Group 2":
                $(".groupCnt").text(groupVal2);
                break;
            case "Group 3":
                $(".groupCnt").text(groupVal3);
                break;
            case "Group 4":
                $(".groupCnt").text(groupVal4);
                break;
            case "Group 5":
                $(".groupCnt").text(groupVal5);
                break;
            case "Group 6":
                $(".groupCnt").text(groupVal6);
                break;
            case "Group 7":
                $(".groupCnt").text(groupVal7);
                break;
            case "Group 8":
                $(".groupCnt").text(groupVal8);
                break;
            case "Group 9":
                $(".groupCnt").text(groupVal9);
                break;
            case "Group 10":
                $(".groupCnt").text(groupVal10);
                break;

        }
    })
    $(".closeBtn img").click(function (){
        $("#popTips").hide();
    })
    // 弹框移动
   var x1,y1,x2,y2,offLeft,offTop,isClick = 0;

    $("#popTips").mousedown(function (e){
        // 点击时的坐标位置
        x1 = e.pageX;
        y1 = e.pageY;
        // 点击时相对于屏幕左边和上边的距离
        offLeft = parseInt($(this).css("left"));
        offTop = parseInt($(this).css("top"));
        isClick = 1;
    }).mousemove(function (e){
        if(isClick == 1){
            x2 = e.pageX;
            y2 = e.pageY;
            var xx = x2 - x1 + offLeft;
            var yy = y2 - y1 + offTop;
            $("#popTips").css("left",xx+"px");
            $("#popTips").css("top",yy+"px");
        }
    }).mouseup(function (){
        isClick = 0;
    });

    //选中状态代码封装
    function checkStatus(bool){
        var lists = $("#selectedDetails li");
        if(bool){
            for(var i=0;i<lists.length;i++){
                var $input = $(lists[i]).find("input");
                if(!$input.is(":checked")){
                    $input.get(0).checked = true;
                }
            }
        }else {
            for(var i=0;i<lists.length;i++){
                var $input = $(lists[i]).find("input");
                if($input.is(":checked")){
                    $input.removeAttr("checked");
                }
            }
        }
    }
    // 全选
    $("#SelectAllBox").click(function (){
        var status = $(this).prop("checked");
        console.log(status);
        checkStatus(status);
    })
    // 清空所有选中的
    $(".selectedAll").click(function  (){
        var lists = $("#selectedDetails li");
        var status = $("#SelectAllBox").prop("checked");
        if(status){
            $("#SelectAllBox").removeAttr("checked");
        }
        for(var i=0;i<lists.length;i++){
            var $input = $(lists[i]).find("input");
            if($input.is(":checked")){
                $input.removeAttr("checked");
            }
        }

    })
    $(".packUp").click(function (){
        $(".selecting").hide();
        $("#operate").hide();
    })
    $('#tableSet').click(function (){
        $(".selecting").show();
        $("#operate").show();
    })

    // 确定按钮（过滤条件）
    $("#operate .sure").click(function (){
        var lists = $("#selectedDetails li");
        for(var i=0;i<lists.length;i++){
            var $input = $(lists[i]).find("input");
            if($input.is(":checked")){
             var classVal = $input.attr("name");
             var newClassVal = "." + classVal + "T";
             $("#tableShow thead").find(newClassVal).hide();
             $("#tableShow tbody").find(newClassVal).hide();
            }else {
                var classVal = $input.attr("name");
                var newClassVal = "." + classVal + "T";
                if($("#tableShow thead").find(newClassVal).is(":hidden")){
                    $("#tableShow thead").find(newClassVal).show();
                    $("#tableShow tbody").find(newClassVal).show();
                }
            }
        }
    })
    window.onload = function (){
        $(".btnConfirmInfo").trigger("click");
    }


    // 获取表格数据
    $(".btnConfirmInfo").click(function (){
        var data={
            cultivar:$(".cultivarI").val(),  // 品种名
            // population:$(".populationI").val(), // 群体
            species:$(".speciesI").val(),// 物种
            locality:$(".localityI").val(), // 位置
            sampleName:$(".sampleNameI").val(), // 样品名
            // weightPer100seeds:$(".weightPer100seedsI").val(),//百粒重
                "weightPer100seeds.operation":$(".weightPer100seedsI").parent().find("option:selected").text().trim() == ">"?"gt":$(".weightPer100seedsI").parent().find("option:selected").text().trim()=="="?"eq":$(".weightPer100seedsI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "weightPer100seeds.value":$(".weightPer100seedsI").val(),
            // protein:$(".proteinI").val(), //蛋白质含量
                "protein.operation":$(".proteinI").parent().find("option:selected").text().trim() == ">"?"gt":$(".proteinI").parent().find("option:selected").text().trim()=="="?"eq":$(".proteinI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "protein.value":$(".proteinI").val(),
            // 含油量
                "oil.operation":$(".oilI").parent().find("option:selected").text().trim() == ">"?"gt":$(".oilI").parent().find("option:selected").text().trim()=="="?"eq":$(".oilI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "oil.value":$(".oilI").val(),
            maturityDate:$(".maturityDateI").val(), // 熟期
            // height:$(".heightI").val(),//株高
            "height.operation":$(".heightI").parent().find("option:selected").text().trim() == ">"?"gt":$(".heightI").parent().find("option:selected").text().trim()=="="?"eq":$(".heightI").parent().find("option:selected").text().trim()=="<"?"lt":"",
            "height.value":$(".heightI").val(),
            seedCoatColor:$(".seedCoatColorI").val(),//种皮色
            hilumColor:$(".hilumColorI").val(),//种脐色
            cotyledonColor:$(".cotyledonColorI").val(), //子叶色
            flowerColor:$(".flowerColorI").val(),//花色
            podColor:$(".podColorI").val(),//荚色
            pubescenceColor:$(".pubescenceColorI").val(),//茸毛色
            // yield:$(".yieldI").val(),// 产量
            "yield.operation":$(".yieldI").parent().find("option:selected").text().trim() == ">"?"gt":$(".yieldI").parent().find("option:selected").text().trim()=="="?"eq":$(".yieldI").parent().find("option:selected").text().trim()=="<"?"lt":"",
            "yield.value":$(".yieldI").val(),
            // upperLeafletLength:$(".upperLeaf
            // letLengthI").val(), //顶端小叶长度
                "upperLeafletLength.operation":$(".upperLeafletLengthI").parent().find("option:selected").text().trim() == ">"?"gt":$(".upperLeafletLengthI").parent().find("option:selected").text().trim()=="="?"eq":$(".upperLeafletLengthI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "upperLeafletLength.value":$(".upperLeafletLengthI").val(),
            // linoleic:$(".linoleicI").val(), //亚油酸
                "linoleic.operation":$(".linoleicI").parent().find("option:selected").text().trim() == ">"?"gt":$(".linoleicI").parent().find("option:selected").text().trim()=="="?"eq":$(".linoleicI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "linoleicvalue":$(".linoleicI").val(),
            // linolenic:$(".linolenicI").val(), //亚麻酸
                "linolenic.operation":$(".linolenicI").parent().find("option:selected").text().trim() == ">"?"gt":$(".linolenicI").parent().find("option:selected").text().trim()=="="?"eq":$(".linolenicI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "linolenic.value":$(".linolenicI").val(),
            // oleic:$(".oleicI").val(), //油酸
                "oleic.operation":$(".oleicI").parent().find("option:selected").text().trim() == ">"?"gt":$(".oleicI").parent().find("option:selected").text().trim()=="="?"eq":$(".oleicI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "oleic.value":$(".oleicI").val(),
            // palmitic:$(".palmiticI").val(),  //软脂酸
                "palmitic.operation":$(".palmiticI").parent().find("option:selected").text().trim() == ">"?"gt":$(".palmiticI").parent().find("option:selected").text().trim()=="="?"eq":$(".palmiticI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "palmitic.value":$(".palmiticI").val(),
            // stearic:$(".stearicI").val(), //硬脂酸
            "stearic.operation":$(".stearicI").parent().find("option:selected").text().trim() == ">"?"gt":$(".stearicI").parent().find("option:selected").text().trim()=="="?"eq":$(".stearicI").parent().find("option:selected").text().trim()=="<"?"lt":"",
            "stearic.value":$(".stearicI").val()
        };
        $.ajax({
            type:"GET",
            url:CTXROOT + "/dna/condition",
            data:data,
            success:function (result){
                var $tbody = $("#tableShow table tbody");
                for (var i=0;i<result.length;i++){
                    var tr = "<tr><td class='param cultivarT'>" + result[i].cultivar +
                        "</td><td class='param speciesT'>" + result[i].species +
                        "</td><td class='param localityT'>" + result[i].locality +
                        "</td><td class='param sampleNameT'>" + result[i].sampleName +
                        "</td><td class='param weightPer100seedsT'>" + result[i].weightPer100seeds +
                        "</td><td class='param proteinT'>" + result[i].protein +
                        "</td><td class='param maturityDateT'>" + result[i].maturityDate +
                        "</td><td class='param heightT'>" + result[i].height +
                        "</td><td class='param seedCoatColorT'>" + result[i].seedCoatColor +
                        "</td><td class='param hilumColorT'>" + result[i].hilumColor +
                        "</td><td class='param cotyledonColorT'>" + result[i].cotyledonColor +
                        "</td><td class='param flowerColorT'>" + result[i].flowerColor +
                        "</td><td class='param podColorT'>" + result[i].podColor +
                        "</td><td class='param pubescenceColorT'>" + result[i].pubescenceColor +
                        "</td><td class='param yieldT'>" + result[i].yield +
                        "</td><td class='param upperLeafletLengthT'>" + result[i].upperLeafletLength +
                        "</td><td class='param linoleicT'>" + result[i].linoleic +
                        "</td><td class='param linolenicT'>" + result[i].linolenic +
                        "</td><td class='param oleicT'>" + result[i].oleic +
                        "</td><td class='param palmiticT'>" + result[i].palmitic +
                        "</td><td class='param stearicT'>" + result[i].stearic +"</td></tr>"
                    $tbody.append(tr);
                }
            },
            error:function (error){
                console.log(error);
            }
        })
    })

    //表格筛选框显示隐藏
    $("#tableShow thead th").mouseover(function (){
        $(this).find(".inputComponent").show();
    }).mouseleave(function (){
        $(this).find(".inputComponent").hide();
    })

    // 筛选取消按钮 样式
    $("#tableShow .inputComponent .btnCancel").click(function (){
        $(this).parent().parent().find("input").val("");
        $(this).parent().parent().hide();
    })

    // 分页
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
    //ajax 请求
    function getData(data){
        $.ajax({
            type:"GET",
            url:CTXROOT + "/dna/condition",
            data:data,
            success:function (result) {
                console.log(result);
                var $tbody = $("#tableShow table tbody");
                count = result.data.total;
                if(count <40){
                    $("#page").css({"padding-left":"186px"});
                }else {
                    $("#page").css({"padding-left":"10px"});
                };
                if(count == 0){
                    $("#paging").hide();
                    $("#errorImg").show();
                    $("#containerAdmin").css("height","754px");
                }else{
                    totalDatas = result.data.list;
                    nums = Math.ceil(count / page.pageSize);
                    //舍弃小数之后的取整
                    intNums = parseInt(count / page.pageSize);
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
})
