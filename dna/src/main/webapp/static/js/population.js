$(function (){
    // 定义全局 population 信息
    var popuSelectedVal=""
    var groupVal1="含89份材料，多为中国长江流域区域的大豆栽培品种和地方品种，蛋白含量40-50%，含油量15-20%，株高集中在40-160cm，百粒重7-20g，种皮黄，花色紫和白，成熟期IV/V/VI/VII组。";
    var groupVal2="含137份材料，多为中国东北部黑龙江地区的栽培品种，少量的内蒙古和吉林地区品种及美国品种，蛋白含量35-45%，含油量29-23%，株高70-100cm，百粒重15-25g，种皮黄，花色紫和白，成熟期II/III/IV组。";
    var groupVal3="含71份材料，多为中国及少数朝、韩地区的野生品种，蛋白含量45-55%，含油量7-15%，株高50-150cm，百粒重1-2g，种皮黑，紫色花朵，成熟期范围广泛I-X组。";
    var groupVal4="含72份材料，多为中国黄河流域及极少数日韩地区的地方品种，蛋白含量38-50%，含油量15-25%，株高100-200cm，百粒重9-16g，种皮多为黑，少量黄色，紫色花朵为主，部分白色，成熟期III/IV/V组为主。";
    var groupVal5="含115份材料，多为中国东部地区的地方品种及巴西、美国地区的栽培品种，蛋白含量40-50%，含油量15-20%，株高60-130cm，百粒重10-20g，种皮黄，花色紫和白，成熟期范围广泛I-VI组。";
    var groupVal6="含102份材料，多为中国黑龙江及吉林地区的栽培品种，蛋白含量35-45%，含油量20-25%，株高70-110cm，百粒重15-25g，种皮黄，花朵以紫色为主，白色次之，成熟期范围广泛I-V组。";
    var groupVal7="含59份材料，多为美国、加拿大地区的栽培品种，蛋白含量39-45%，含油量15-25%，株高80-150cm，百粒重15-25g，种皮黄，紫色花朵，成熟期0-III为主。";
    var groupVal8="含102份材料，多为加拿大及少数巴西、美国地区的栽培品种，蛋白含量40-50%，含油量15-25%，株高80-120cm，百粒重10-20g，种皮黄，紫色花朵，成熟期0-IV为主。";
    var groupVal9="含138份材料，主要为中韩地区的半野生品种和美洲地区的栽培品种，蛋白含量40-50%，含油量15-25%，株高50-120cm，百粒重10-20g，种皮黄，花朵紫和白，成熟期组以III/IV为主。";
    var groupVal10="含80份材料，主要为中、韩、美地区的地方和栽培品种，蛋白含量40-50%，含油量7-15%，株高50-100cm，百粒重10-25g，种皮多黄，少棕黑色，紫色和白色花朵，成熟期组以I-V为主。";
    var layerIndex;
    $("#groups li").click(function (e){
        var val = $(this).find("p").text();
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

        layerIndex = layer.open({
            title:"",
            type: 1,
            content:$("#popTips"),
            area: '600px',
            shadeClose:true,
            scrollbar:false,
            move: '.tipTopCnt',
            tips: 2,
            closeBtn: 0

        });

    })
    // 默认表格内容全选

    $(".closeBtn img").click(function (){
        // $("#popTips").hide();
        layer.close(layerIndex);
    })
    // 弹框移动
   // var x1,y1,x2,y2,offLeft,offTop,isClick = 0;
   //
   //  $("#popTips").mousedown(function (e){
   //      // 点击时的坐标位置
   //      x1 = e.pageX;
   //      y1 = e.pageY;
   //      // 点击时相对于屏幕左边和上边的距离
   //      offLeft = parseInt($(this).css("left"));
   //      offTop = parseInt($(this).css("top"));
   //      isClick = 1;
   //  }).mousemove(function (e){
   //      if(isClick == 1){
   //          x2 = e.pageX;
   //          y2 = e.pageY;
   //          var xx = x2 - x1 + offLeft;
   //          var yy = y2 - y1 + offTop;
   //          $("#popTips").css("left",xx+"px");
   //          $("#popTips").css("top",yy+"px");
   //      }
   //  }).mouseup(function (){
   //      isClick = 0;
   //  });

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
        $("#tableSet").show();
        $("#tableSet").css("margin-right","10px");
        // $("#tableSet").removeClass("pageUpMargin");
    })
    $('#tableSet').click(function (){
        $(".selecting").show();
        $("#operate").show();
        $(this).hide();
        // $("#exportData").addClass("pageUpMargin")
        // $("#exportData").css("margin-right","20px")
    })

    // 确定按钮（过滤条件）
    $("#operate .sure").click(function (){
        var lists = $("#selectedDetails li");
        for(var i=0;i<lists.length;i++){
            var $input = $(lists[i]).find("input");
            if(!$input.is(":checked")){
             var classVal = $input.attr("name");
             var newClassVal = "." + classVal + "T";
             $("#tableShow thead").find(newClassVal).hide();
             $("#tableShow tbody").find(newClassVal).hide();
            }
            else {
                var classVal = $input.attr("name");
                var newClassVal = "." + classVal + "T";
                if($("#tableShow thead").find(newClassVal).is(":hidden")){
                    $("#tableShow thead").find(newClassVal).show();
                    $("#tableShow tbody").find(newClassVal).show();
                }
            }
        }
    })
        // 点击群体信息进入页面初始化开始获取table数据
        var initData = getParamas()
        getData(initData,1);
        // $("#page .two").addClass("pageColor");

    // 获取当前参数 封装
    function getParamas (){
        var datas={
            runNo:$(".runNoI").val(),  // 测序样品编号
            scientificName:$(".scientificNameI").val(),// 物种名称
            sampleId:$(".sampleIdI").val(), // 编号
            strainName:$(".strainNameI").val(), // 菌株名称
            locality:$(".localityI").val(), // 地理位置
            preservationLocation:$(".preservationLocationI").val(),//保藏地点
            type:$(".typeI").val(),//类型
            environment:$(".environmentI").val(),//培养环境
            materials:$(".materialsI").val(), //材料
            treat:$(".treatI").val(),//处理
            definitionTime:$(".timeI").val(),//采集时间
            taxonomy:$(".taxonomyI").val(),//分类地位
            myceliaPhenotype:$(".myceliaPhenotypeI").val(),//菌丝形态
            myceliaDiameter:$(".myceliaDiameterI").val(),//菌丝直径
            myceliaColor:$(".myceliaColorI").val(),//菌丝颜色
            sporesColor:$(".sporesColorI").val(),//孢子颜色
            sporesShape:$(".sporesShapeI").val(),//孢子形态
            clampConnection:$(".clampConnectionI").val(),//锁状联合
            pileusPhenotype:$(".pileusPhenotypeI").val(),//菌盖形态
            pileusColor:$(".pileusColorI").val(),//菌盖颜色
            stipePhenotype:$(".stipePhenotypeI").val(),//菌柄形态
            stipeColor:$(".stipeColorI").val(),//菌柄颜色
            fruitbodyColor:$(".fruitbodyColorI").val(),//子实体颜色
            fruitbodyType:$(".fruitbodyTypeI").val(),//子实体形态
            illumination:$(".illuminationI").val(),//光照
            collarium:$(".collariumI").val(),//菌环
            volva:$(".volvaI").val(),//菌托
            velum:$(".velumI").val(),//菌幕
            sclerotium:$(".sclerotiumI").val(),//菌核
            strainMedium:$(".strainMediumI").val(),//菌种培养基
            mainSubstrate:$(".mainSubstrateI").val(),//主要栽培基质
            afterRipeningStage:$(".afterRipeningStageI").val(),//后熟期
            primordialStimulationFruitbody:$(".primordialStimulationFruitbodyI").val(),//原基刺激&子实体
            reproductiveMode:$(".reproductiveModeI").val(),//生殖方式
            lifestyle:$(".lifestyleI").val(),//生活方式
            preservation:$(".preservationI").val(),//保藏方法
            domestication:$(".domesticationI").val(),//驯化
            nuclearPhase:$(".nuclearPhaseI").val(),//核相
            matingType:$(".matingTypeI").val(),//交配型
            isPage:1  // 是否分页
        };
        return datas;
    };

    // 获取表格数据
    $(".btnConfirmInfo").click(function (){
        $(".lay-per-page-count-select option:nth-child(1)").prop("selected", 'selected');
        var data = getParamas();
        getData(data,page.pageNum);
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

    // 获取焦点添加样式：
    $("#sysPopulations").on("focus", ".laypage_skip", function() {
        $(this).addClass("isFocus");
    });
    $("#sysPopulations").on("blur", ".laypage_skip", function() {
        $(this).removeClass("isFocus");
    });

    // 注册 enter 事件的元素
    $(document).keyup(function (event) {
        var _page_skip = $('#sysPopulations .laypage_skip');
        if (_page_skip.hasClass("isFocus")) {
            if (event.keyCode == 13) {
                var pageSizeNum = Number($('#per-page-count .lay-per-page-count-select').val());
                var total= $("#total-page-count span").text();
                var mathCeil=  Math.ceil(total/pageSizeNum);

                var selectedNum = $('#sysPopulations .laypage_skip').val();
                page.pageNum = selectedNum;
                paramData.pageNum = page.pageNum;
                var selectedDatas = getParamas();
                selectedDatas.pageNum = paramData.pageNum;
                selectedDatas.pageSize =pageSizeNum;

                if (selectedNum>mathCeil) {
                    selectedDatas.pageNum = 1;
                    getData(selectedDatas,1);
                }else{
                    // page.curr = selectedNum;
                    getData(selectedDatas,selectedDatas.pageNum);
                }
            }
        }
    });



    var curr = 1;
    var currPageNumber = 1;
    //ajax 请求
    function getData(data,curr){
        $.ajax({
            type:"GET",
            url:CTXROOT + "/dna/condition",
            data:data,
            success:function (result) {
                count = result.data.total;
                if(count == 0){
                    $("#tableShow table tbody tr").remove();
                    $("#errorImg").show();
                    $("#containerAdmin").css("height","754px");
                    $(".ga-ctrl-footer").hide();
                    $("#tableShow table tbody").html("<div class='zwsj' style='padding: 20px 10px;'>暂无数据</div>")
                }else{
                    $('.zwsj').remove();
                    if($("#per-page-count").is(":hidden")){
                        $(".ga-ctrl-footer").show();
                    }
                    totalDatas = result.data.list;
                    $("#tableShow table tbody tr").remove();
                    nums = Math.ceil(count / page.pageSize);
                    //舍弃小数之后的取整
                    intNums = parseInt(count / page.pageSize);
                    for (var i=0;i<totalDatas.length;i++){
                        var runNoT = totalDatas[i].runNo==null?"":totalDatas[i].runNo;  // 测序样品编号
                        var scientificNameT = totalDatas[i].scientificName==null?"":totalDatas[i].scientificName;  // 物种名称
                        var sampleIdT = totalDatas[i].sampleId==null?"":totalDatas[i].sampleId;  // 编号
                        var strainNameT = totalDatas[i].strainName==null?"":totalDatas[i].strainName; // 菌株名称
                        var localityT = totalDatas[i].locality==null?"":totalDatas[i].locality;  //地理位置
                        var preservationLocationT = totalDatas[i].preservationLocation==null?"":totalDatas[i].preservationLocation;  //保藏地点
                        var typeT = totalDatas[i].type==null?"":totalDatas[i].type;  //类型
                        var environmentT = totalDatas[i].environment==null?"":totalDatas[i].environment;  //培养环境
                        var materialsT = totalDatas[i].materials==null?"":totalDatas[i].materials;  //材料
                        var treatT = totalDatas[i].treat==null?"":totalDatas[i].treat  //处理
                        var timeT = totalDatas[i].definitionTime==null?"":totalDatas[i].definitionTime;  //采集时间
                        var taxonomyT  = totalDatas[i].taxonomy==null?"":totalDatas[i].taxonomy;  //分类地位
                        var myceliaPhenotypeT = totalDatas[i].myceliaPhenotype==null?"":totalDatas[i].myceliaPhenotype;  //菌丝形态
                        var myceliaDiameterT = totalDatas[i].myceliaDiameter==null?"":totalDatas[i].myceliaDiameter;  //菌丝直径
                        var myceliaColorT = totalDatas[i].myceliaColor==null?"":totalDatas[i].myceliaColor;  //菌丝颜色
                        var sporesColorT = totalDatas[i].sporesColor ==null?"":totalDatas[i].sporesColor;  //孢子颜色
                        var sporesShapeT = totalDatas[i].sporesShape==null?"":totalDatas[i].sporesShape;  //孢子形态
                        var clampConnectionT = totalDatas[i].clampConnection==null?"":totalDatas[i].clampConnection;   //锁状联合
                        var pileusPhenotypeT = totalDatas[i].pileusPhenotype==null?"":totalDatas[i].pileusPhenotype;  //菌盖形态
                        var pileusColorT = totalDatas[i].pileusColor==null?"":totalDatas[i].pileusColor;  //菌盖颜色
                        var stipePhenotypeT = totalDatas[i].stipePhenotype==null?"":totalDatas[i].stipePhenotype;  //菌柄形态
                        var stipeColorT = totalDatas[i].stipeColor==null?"":totalDatas[i].stipeColor;  //菌柄颜色
                        var fruitbodyColorT = totalDatas[i].fruitbodyColor==null?"":totalDatas[i].fruitbodyColor;  //子实体颜色
                        var fruitbodyTypeT = totalDatas[i].fruitbodyType==null?"":totalDatas[i].fruitbodyType;  //子实体形态
                        var illuminationT = totalDatas[i].illumination==null?"":totalDatas[i].illumination;  //光照
                        var collariumT = totalDatas[i].collarium==null?"":totalDatas[i].collarium;  //菌环
                        var volvaT = totalDatas[i].volva==null?"":totalDatas[i].volva;  //菌托
                        var velumT = totalDatas[i].velum==null?"":totalDatas[i].velum;  //菌幕
                        var sclerotiumT = totalDatas[i].sclerotium==null?"":totalDatas[i].sclerotium;  //菌核
                        var strainMediumT = totalDatas[i].strainMedium==null?"":totalDatas[i].strainMedium;  //菌种培养基
                        var mainSubstrateT = totalDatas[i].mainSubstrate==null?"":totalDatas[i].mainSubstrate;  //主要栽培基质
                        var afterRipeningStageT = totalDatas[i].afterRipeningStage==null?"":totalDatas[i].afterRipeningStage;  //后熟期
                        var primordialStimulationFruitbodyT = totalDatas[i].primordialStimulationFruitbody==null?"":totalDatas[i].primordialStimulationFruitbody;  //原基刺激&子实体
                        var reproductiveModeT = totalDatas[i].reproductiveMode==null?"":totalDatas[i].reproductiveMode;  //生殖方式
                        var lifestyleT = totalDatas[i].lifestyle==null?"":totalDatas[i].lifestyle;  //生活方式
                        var preservationT = totalDatas[i].preservation==null?"":totalDatas[i].preservation;  //保藏方法
                        var domesticationT = totalDatas[i].domestication==null?"":totalDatas[i].domestication;  //驯化
                        var nuclearPhaseT = totalDatas[i].nuclearPhase==null?"":totalDatas[i].nuclearPhase;  //核相
                        var matingTypeT = totalDatas[i].matingType==null?"":totalDatas[i].matingType;  //交配型

                        var tr = "<tr>" +
                            "<td class='paramTag runNoT'>" + runNoT+
                            "</td><td class='paramTag scientificNameT'>" + scientificNameT+
                            "</td><td class='paramTag sampleIdT'>" + sampleIdT+
                            "</td><td class='paramTag strainNameT'>" + strainNameT +
                            "</td><td class='paramTag localityT'>" + localityT +
                            "</td><td class='paramTag preservationLocationT'>" + preservationLocationT +
                            "</td><td class='paramTag typeT'>" + typeT +
                            "</td><td class='paramTag environmentT'>" + environmentT +
                            "</td><td class='paramTag materialsT'>" + materialsT +
                            "</td><td class='paramTag treatT'>" + treatT +
                            "</td><td class='paramTag timeT'>" + timeT +
                            "</td><td class='paramTag taxonomyT'>" + taxonomyT +
                            "</td><td class='paramTag myceliaPhenotypeT'>" + myceliaPhenotypeT +
                            "</td><td class='paramTag myceliaDiameterT'>" +myceliaDiameterT +
                            "</td><td class='paramTag myceliaColorT'>" + myceliaColorT +
                            "</td><td class='paramTag sporesColorT'>" + sporesColorT +
                            "</td><td class='paramTag sporesShapeT'>" + sporesShapeT +
                            "</td><td class='paramTag clampConnectionT'>" + clampConnectionT +
                            "</td><td class='paramTag pileusPhenotypeT'>" + pileusPhenotypeT +
                            "</td><td class='paramTag pileusColorT'>" + pileusColorT +
                            "</td><td class='paramTag stipePhenotypeT'>" + stipePhenotypeT +
                            "</td><td class='paramTag stipeColorT'>" + stipeColorT +
                            "</td><td class='paramTag fruitbodyColorT'>" + fruitbodyColorT +"</td>" +
                            "<td class='paramTag fruitbodyTypeT'>" + fruitbodyTypeT +"</td>"+
                            "<td class='paramTag illuminationT'>" + illuminationT +"</td>"+
                            "<td class='paramTag collariumT'>" + collariumT +"</td>"+
                            "<td class='paramTag volvaT'>" + volvaT +"</td>"+
                            "<td class='paramTag velumT'>" + velumT +"</td>"+
                            "<td class='paramTag sclerotiumT'>" + sclerotiumT +"</td>"+
                            "<td class='paramTag strainMediumT'>" + strainMediumT +"</td>"+
                            "<td class='paramTag mainSubstrateT'>" + mainSubstrateT +"</td>"+
                            "<td class='paramTag afterRipeningStageT'>" + afterRipeningStageT +"</td>"+
                            "<td class='paramTag primordialStimulationFruitbodyT'>" + primordialStimulationFruitbodyT +"</td>"+
                            "<td class='paramTag reproductiveModeT'>" + reproductiveModeT +"</td>"+
                            "<td class='paramTag lifestyleT'>" + lifestyleT +"</td>"+
                            "<td class='paramTag domesticationT'>" + domesticationT +"</td>"+
                            "<td class='paramTag nuclearPhaseT'>" + nuclearPhaseT +"</td>"+
                            "<td class='paramTag preservationT'>" + preservationT +"</td>"+
                            "<td class='paramTag matingTypeT'>" + matingTypeT +"</td>"+
                            "</tr>"
                        var $tbody = $("#tableShow table tbody");
                        $tbody.append(tr);
                    }
                }
                // 分页
                laypage({
                    cont: $('#sysPopulations .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(result.data.total /  page.pageSize), //通过后台拿到的总页数
                    curr: curr || 1, //当前页
                    skin: '#5c8de5',
                    skip: true,
                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                    last: Math.ceil(result.data.total /  page.pageSize), //将尾页显示为总页数。若不显示，设置false即可
                    prev: '<',
                    next: '>',
                    groups: 3, //连续显示分页数
                    jump: function (obj, first) { //触发分页后的回调
                        if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                            var pageSizeNum = Number($('#per-page-count .lay-per-page-count-select').val());
                            var tmp = getParamas();
                            tmp.pageNum = obj.curr;
                            currPageNumber = obj.curr;
                            tmp.pageSize = pageSizeNum;
                            getData(tmp,obj.curr);
                        }
                    }
                });
                $("#total-page-count span").html(result.data.total);
            },
            error:function (error){
                console.log(error);
            }
        })
    }

    // pageSize 事件
    $("body").on("change",".lay-per-page-count-select", function() {
        var curr = Number($(".laypage_curr").text());
        var pageSize = Number($(this).val());
        var total= Number($("#total-page-count span").text());
        var mathCeil=  Math.ceil(total/curr);
        page.pageSize = $(this).val();
        var data = getParamas();
        if(pageSize>mathCeil){
                data.pageSize = pageSize;
                data.pageNum = 1;
                getData(data,1);
        }else{
            data.pageSize = pageSize;
            data.pageNum =curr;
            getData(data,data.pageNum);

        }
    });

    // 表格导出
    $("#exportData").click(function (){
        var unSelectes = $("#selectedDetails ul input");
        var unSelectedLists="";
        for(var i=0;i<unSelectes.length;i++){
            if($(unSelectes[i]).is(":checked")){
                var unSelecteNames = $(unSelectes[i]).attr("name");
                unSelectedLists+=unSelecteNames + ",";
            }
        }
        var exportCondition=getParamas();
        // modify by Crabime
        // 修复tomcat8无法识别的JSON格式问题
        $.ajax({
            type:"GET",
            url:CTXROOT + "/export",
            data:{
                  "titles":unSelectedLists,
                  "condition":JSON.stringify(exportCondition)
                },
            dataType: "json",
            contentType: "application/json",
            success:function (result){
               window.location.href = result;
            },
            error:function (error){
                console.log(error);
            }
        })
    })
    // 新增group 表
    $(".popMoveOnNewAdd").mouseover(function (){
        $(".popNamesNewAdd").show();
    }).mouseleave(function (){
        $(".popNamesNewAdd").hide()
    })
    // 点击群体时触发请求，跟后端协调字段名成是否正确
    $(".popNamesNewAdd li").click(function (){
        $(".lay-per-page-count-select option:nth-child(1)").prop("selected", 'selected');
        $(".popNamesNewAdd").hide();
        popuSelectedVal = $(this).text();
        var data = getParamas();
        getData(data);
    })
})
