$(function (){
    $("#addTags>span").click(function (){
        if(!$(this).hasClass("tagColor")){
            $(this).addClass("tagColor");
            var sibElement = $(this).siblings()[0];
            if($(sibElement).has("tagColor")){
                $(sibElement).removeClass("tagColor");
            }
        }
        if($(this).hasClass("popCnt1")){
            $("#tagKind").hide();
            $(".sample-category.popCnt1").show();
            $(".retract.popCnt1").show();
            $('.resetBtn').hide();
            $(".savePoP").show();
            $(".saveKind").hide();
        }else{
            $("#tagKind").show();
            $(".sample-category.popCnt1").hide();
            $(".retract.popCnt1").hide();
            $('.resetBtn').show();
            $(".savePoP").hide()
            $(".saveKind").show();
        }
    })
    $(".popMoveOn").mouseover(function (){
        $(".popNames").show();
    }).mouseleave(function (){
        $(".popNames").hide();
    })
    $(".popNames li").click(function (e){
        var currentGroup = $(this).text();
        var data = getParamas();
        data.group = currentGroup;
        getData(data,data.pageNum,resetSaveStatus);
        $(".popNames").hide();
    })

    // 重置保存状态代码封装
    function resetSaveStatus(){
        var tdInputs = $("#tagTBody").find("input");
        var selInputs = $(".sample-text").find("span");
        for (var i=0;i<selInputs.length;i++){
            var selInputsName = $(selInputs[i]).text().substring(3,$(selInputs[i]).text().length-1);
            for (var j=0;j<tdInputs.length;j++){
                var tdParent = $(tdInputs[j]).parent().next().text();
                if(tdParent==selInputsName){
                    $(tdInputs[j]).prop("checked",true);
                }
            }
        }
    }
    // 重置按钮点击事件
    $(".resetBtn").click(function (){
        var data = getParamas();
            // 默认回到第一页，
        data.pageNum = 1;
        //modified by zjt 2018-3-27
        /*var pageSizeP = $("#page").find("p")
        liVal = "";
        for(var i=0;i<pageSizeP.length;i++){
            if($(pageSizeP[i]).hasClass("pageColor")){
                $(pageSizeP[i]).removeClass("pageColor")
            }
        };
        $(pageSizeP[0]).addClass("pageColor");
        getData(data,curr,resetSaveStatus);*/
        //每页展示数目还原为10
        $(".ga-ctrl-footer .select_default_page").val('10');
        page.pageSize = 10;
        paramData.pageSize = page.pageSize;
        paramData.pageNum = data.pageNum;
        getData(data,data.pageNum,resetSaveStatus);
        //modified by zjt 2018-3-27

    })
   // localstorage 存储选择的品种
    if(window.localStorage){
       var storage = window.localStorage;
        var initKindVal = JSON.parse(storage.getItem("kind"));
        var kindStorage = {};
        if(!initKindVal){
            kindStorage.name = [];
        }else{
           kindStorage.name = initKindVal.name;
        }

    }else{
        //alert('This browser does NOT support localStorage');
        layer.open({
            type: 0,
            title: "温馨提示:",
            content: "This browser does NOT support localStorage",
            shadeClose: true,
        });
    }

    // 选择品种中的保存群体
    $(".saveKind").click(function (){
        // 先判断保存群体/品种的数量
        var sampleTexts=$(".sample-text").text();
        if(sampleTexts.length==0){
            //alert("请选择样品!")
            layer.open({
                type: 0,
                title: "温馨提示:",
                content: "请选择样品!",
                shadeClose: true,
            });
            return;
        }
        var numbs =$(".js-cursom-add").find(".js-ad-dd").length;
        if(numbs>3){
            //alert("最多可添加10个群体")
            layer.open({
                type: 0,
                title: "温馨提示:",
                content: "最多可添加10个群体",
                shadeClose: true,
            });
        }else {
            var selKinds = $(".sample-text").find("span");
            var selContent='';
            var spanIds = [];
            for (var i=0;i<selKinds.length;i++){
                selContent += $(selKinds[i]).text().substring(0,$(selKinds[i]).text().length-1) + ",";
                spanIds.push(parseInt($(selKinds[i]).attr("id")));
            }
            var selContents = selContent.substring(0,selContent.length-1);
            // var arr = selContents.split(",");
            // var arrStr = "";
            // for(var j=0;j<arr.length;j++){
            //     arrStr+=arr[j].substring(6) + ",";
            // };

            // 当前保存群体的顺序
            var popLength = $(".js-cursom-add>div.js-ad-dd").length;
            // var newArrStr = arrStr.substring(0,arrStr.length-1);
            var newArrStr = spanIds.join(",");
            var ki = {name:selContents,
                     id:popLength +1+6,
                     condition:{
                            idList:newArrStr
                     }
            };
            kindStorage.name.push(ki);
            var div = "<div class='js-ad-dd'><label class='species-add' data-index=" + ki.id + ">" + "<span></span><div class='label-txt'>" + selContents + "</div></label><i class='js-del-dd'>X</i></div>"
            $(".js-cursom-add").append(div);
            storage.setItem("kind",JSON.stringify(kindStorage));
            $(".sample-text").empty();
            var inputSeList = $("#tagKind table tbody tr input");
            for (var i=0;i<inputSeList.length;i++){
                if($(inputSeList[i]).is(":checked")){
                    $(inputSeList[i]).attr("checked",false);
                }
            }
        }
    })
    // 表格中每个复选框的点击事件
    $("#tagTBody").on("click","input",function (e){
        var currentStatus = $(this).prop("checked");
        var selectedName =  $(this).parent().next().text();
        var id = $(this).parent().next().attr("data-id");
        if (!selectedName){
            selectedName = $(this).parent().siblings().filter(".sampleNameT").text();
            if(currentStatus){
                var putName = "<span>测序样品编号" + selectedName + "<i class='deleteSelected'>X</i></span>";
                $("#sampleText").append(putName);
            }else {
                var checkNames = $(".sample-text").find("span");
                for (var i=0;i<checkNames.length;i++){
                    var checkName = $(checkNames[i]).text().substring(3);
                    if(selectedName == checkName){
                        $(checkNames[i]).remove();
                    }
                }
            }
        }else {
            if(currentStatus){
                var putName = "<span id='"+id +"'>测序样品编号" + selectedName + "<i class='deleteSelected'>X</i></span>";
                $("#sampleText").append(putName);
            }else {
                var checkNames = $(".sample-text").find("span");
                for (var i=0;i<checkNames.length;i++){
                    var checkName = $(checkNames[i]).text().substring(6,$(checkNames[i]).text().length-1);
                    if(selectedName == checkName){
                        $(checkNames[i]).remove();
                    }
                }
            }
        };
        // 清楚非品种之外的群体￥
        // var wrapList =$(".sample-text").find("span");
        //   for (var i=0;i<wrapList.length;i++){
        //       if($(wrapList[i]).text().substring(0,3) !="品种名"){
        //           $(wrapList[i]).remove();
        //       }
        //   }
    })

    // 选中的品种点击X
    $(".sample-text").on("click",".deleteSelected",function (){
        var deleteName = $(this).parent().text().substring(3,$(this).parent().text().length-1);
        $(this).parent().remove();
        var selectedInputs = $("#tagTBody").find("input:checked");
        for(var i=0;i<selectedInputs.length;i++){
            if($(selectedInputs[i]).parent().next().text() == deleteName){
                $(selectedInputs[i]).removeAttr("checked");
            }
        }
    })

    // 点击清空 清空列表和表格中的所有数据
    $(".sample-empty").click(function (){
        $(".sample-text").empty();
        var selectedInputs = $("#tagTBody").find("input:checked");
        for(var i=0;i<selectedInputs.length;i++){
            $(selectedInputs[i]).removeAttr("checked");
        }
    })
    // 获取数据--》请求参数
    function getParamas (){
        var datas = {
            runNo:$(".runNo").val(),  // 测序样品编号
            scientificName:$(".scientificName").val(),// 物种名称
            sampleId:$(".sampleId").val(), // 编号
            strainName:$(".strainName").val(), // 菌株名称
            locality:$(".locality").val(), // 地理位置
            preservationLocation:$(".preservationLocation").val(),//保藏地点
            type:$(".type").val(),//类型
            environment:$(".environment").val(),//培养环境
            materials:$(".materials").val(), //材料
            treat:$(".treat").val(),//处理
            definitionTime:$(".time").val(),//采集时间
            taxonomy:$(".taxonomy").val(),//分类地位
            myceliaPhenotype:$(".myceliaPhenotype").val(),//菌丝形态
            myceliaDiameter:$(".myceliaDiameter").val(),//菌丝直径
            myceliaColor:$(".myceliaColor").val(),//菌丝颜色
            sporesColor:$(".sporesColor").val(),//孢子颜色
            sporesShape:$(".sporesShape").val(),//孢子形态
            clampConnection:$(".clampConnection").val(),//锁状联合
            pileusPhenotype:$(".pileusPhenotype").val(),//菌盖形态
            pileusColor:$(".pileusColor").val(),//菌盖颜色
            stipePhenotype:$(".stipePhenotype").val(),//菌柄形态
            stipeColor:$(".stipeColor").val(),//菌柄颜色
            fruitbodyColor:$(".fruitbodyColor").val(),//子实体颜色
            fruitbodyType:$(".fruitbodyType").val(),//子实体形态
            illumination:$(".illumination").val(),//光照
            collarium:$(".collarium").val(),//菌环
            volva:$(".volva").val(),//菌托
            velum:$(".velum").val(),//菌幕
            sclerotium:$(".sclerotium").val(),//菌核
            strainMedium:$(".strainMedium").val(),//菌种培养基
            mainSubstrate:$(".mainSubstrate").val(),//主要栽培基质
            afterRipeningStage:$(".afterRipeningStage").val(),//后熟期
            primordialStimulationFruitbody:$(".primordialStimulationFruitbody").val(),//原基刺激&子实体
            reproductiveMode:$(".reproductiveMode").val(),//生殖方式
            lifestyle:$(".lifestyle").val(),//生活方式
            preservation:$(".preservation").val(),//保藏方法
            domestication:$(".domestication").val(),//驯化
            nuclearPhase:$(".nuclearPhase").val(),//核相
            matingType:$(".matingType").val(),//交配型
            group:"",
            pageSize:paramData.pageSize,
            pageNum:paramData.pageNum,
            isPage:1

        };
        return datas;
    }
    // 获取表格数据
    $("#tagKind .btnConfirmInfo").click(function (){
        var selectedDatas = getParamas();
        selectedDatas.pageNum = paramData.pageNum;
        selectedDatas.pageSize = paramData.pageSize;
        getData(selectedDatas,paramData.pageNum,resetSaveStatus);
    })
    $("#addTags span.popCnt1").click(function (){
        if($("#hiddenP").is(":hidden")){
            $("#hiddenP").show();
        };
        var inputs = $(".sample-screening-btn input");
        for(var i=0;i<inputs.length;i++){
            if(!$(input[i]).is(":hidden")){
                $(input[i]).hide();
            }
        }
    })
    // 选中品种按钮点击获取数据
    $("#kindSelect").click(function (){
        if(!$("#hiddenP").is(":hidden")){
            $("#hiddenP").hide();
        }
        // 每次点击品种都会清空所有的input 框里的值
        var inputValues = $("#tagKind table thead input");
        $.each(inputValues,function (i,item){
            $(item).val("");
        })
        var data = getParamas();
        //modified by zjt 2018-3-27
        data.pageNum = 1;                 //回到第一页
        paramData.pageNum = data.pageNum;
        data.pageSize = 10;               //每页条数重置为10条每页
        page.pageSize = 10;
        paramData.pageSize = data.pageSize;
        $('.ga-ctrl-footer .select_default_page').val('10');
        //modified by zjt 2018-3-27
       getData(data,paramData.pageNum,resetSaveStatus);
    });
    //表格筛选框显示隐藏
    $("#tagKind thead th").mouseover(function (){
        $(this).find(".inputComponent").show();
    }).mouseleave(function (){
        $(this).find(".inputComponent").hide();
    })
    // 筛选取消按钮 样式
    $("#tagKind .inputComponent .btnCancel").click(function (){
        $(this).parent().parent().find("input").val("");
        $(this).parent().parent().hide();
    })
    // // pageSize 选择事件
    //modified by zjt 2018-3-27
    /* modified by zjt 2018-3-27
    $("#per-page-count select").change(function (e){
        var currentSelected = $(this).find("option:selected").text();
        page.pageSize = currentSelected;
        paramData.pageSize = page.pageSize;
    });*/
    $("#tagsPagination #per-page-count .select_item_page li").click(function (e){
        var currentSelected = $(this).text();
        page.pageSize = currentSelected;
        paramData.pageSize = page.pageSize;
    });
    //modified by zjt 2018-3-27

    // 获取焦点添加样式：
    $("#tagsPagination").on("focus", ".laypage_skip", function() {
        $(this).addClass("isFocus");
    });
    $("#tagsPagination").on("blur", ".laypage_skip", function() {
        $(this).removeClass("isFocus");
    });
    $("#tagsPagination").on("keydown",".laypage_skip",function (e){
        var _page_skip = $('#tagsPagination .laypage_skip');
            if(e && e.keyCode==13){ // enter 键
                if( _page_skip.hasClass("isFocus") ) {
                    if(_page_skip.val() * 1 > Math.ceil(count/ paramData.pageSize)) {
                        //return alert("输入页码不能大于总页数");
                        layer.open({
                            type: 0,
                            title: "温馨提示:",
                            content: "输入页码不能大于总页数",
                            shadeClose: true,
                        });
                    }
                    var selectedNum = $('#tagsPagination .laypage_skip').val();
                    page.pageNum = selectedNum;
                    paramData.pageNum = page.pageNum;
                    var selectedDatas = getParamas();
                    selectedDatas.pageNum = paramData.pageNum;
                    selectedDatas.pageSize = paramData.pageSize;
                    getData(selectedDatas,selectedDatas.pageNum,resetSaveStatus);
                }
            }
    });
    // pageSize 事件
    //midified by zjt 2018-3-27
    /*$("#tagsPagination select").change(function (e){
        var val = $(this).val();
        var data = getParamas();
        data.pageSize = val;
        data.pageNum =  paramData.pageSize;
        getData(data,data.pageNum);

    });*/
    $("#tagsPagination #per-page-count .select_item_page li").click(function (e){
        var val = $(this).text();
        var data = getParamas();
        data.pageSize = val;
        paramData.pageSize = data.pageSize;
        data.pageNum =  1;
        data.pageNum = paramData.pageNum;
        getData(data,data.pageNum,resetSaveStatus);
    });
    //midified by zjt 2018-3-27
    // 分页
    var nums;
    var totalDatas;
    var intNums;
    var count;
    var curr = 1;
    var page = {
        pageNum:1,
        pageSize:10
    }
    //每页展示的数量
    var paramData = {
        pageNum:page.pageNum,
        pageSize:page.pageSize
    };
    // // pageSize 选择事件
    $("#tagsPagination select").change(function (e){
        var currentSelected = $(this).find("option:selected").text();
        page.pageSize = currentSelected;
        paramData.pageSize = page.pageSize;
    });
    //ajax 请求
    function getData(data,curr,fn){
        $.ajax({
            type:"GET",
            url:CTXROOT + "/dna/condition",
            data:data,
            success:function (result) {
                count = result.data.total;
                if(count == 0){
                    $("#errorImg").show();
                    $("#containerAdmin").css("height","754px");
                    $("#tagTBody").empty();
                }else{
                    totalDatas = result.data.list;
                    $("#tagKind table tbody tr").remove();
                    nums = Math.ceil(count / page.pageSize);
                    //舍弃小数之后的取整
                    intNums = parseInt(count / page.pageSize);
                    for (var i=0;i<totalDatas.length;i++){
                        var idTV = totalDatas[i].id==null?"":totalDatas[i].id;  // 测序样品编号
                        var runNoTV = totalDatas[i].runNo==null?"":totalDatas[i].runNo;  // 测序样品编号
                        var scientificNameTV = totalDatas[i].scientificName==null?"":totalDatas[i].scientificName;  // 物种名称
                        var sampleIdTV = totalDatas[i].sampleId==null?"":totalDatas[i].sampleId;  // 编号
                        var strainNameTV = totalDatas[i].strainName==null?"":totalDatas[i].strainName; // 菌株名称
                        var localityTV = totalDatas[i].locality==null?"":totalDatas[i].locality;  //地理位置
                        var preservationLocationTV = totalDatas[i].preservationLocation==null?"":totalDatas[i].preservationLocation;  //保藏地点
                        var typeTV = totalDatas[i].type==null?"":totalDatas[i].type;  //类型
                        var environmentTV = totalDatas[i].environment==null?"":totalDatas[i].environment;  //培养环境
                        var materialsTV = totalDatas[i].materials==null?"":totalDatas[i].materials;  //材料
                        var treatTV = totalDatas[i].treat==null?"":totalDatas[i].treat  //处理
                        var timeTV = totalDatas[i].definitionTime==null?"":totalDatas[i].definitionTime;  //采集时间
                        var taxonomyTV  = totalDatas[i].taxonomy==null?"":totalDatas[i].taxonomy;  //分类地位
                        var myceliaPhenotypeTV = totalDatas[i].myceliaPhenotype==null?"":totalDatas[i].myceliaPhenotype;  //菌丝形态
                        var myceliaDiameterTV = totalDatas[i].myceliaDiameter==null?"":totalDatas[i].myceliaDiameter;  //菌丝直径
                        var myceliaColorTV = totalDatas[i].myceliaColor==null?"":totalDatas[i].myceliaColor;  //菌丝颜色
                        var sporesColorTV = totalDatas[i].sporesColor ==null?"":totalDatas[i].sporesColor;  //孢子颜色
                        var sporesShapeTV = totalDatas[i].sporesShape==null?"":totalDatas[i].sporesShape;  //孢子形态
                        var clampConnectionTV = totalDatas[i].clampConnection==null?"":totalDatas[i].clampConnection;   //锁状联合
                        var pileusPhenotypeTV = totalDatas[i].pileusPhenotype==null?"":totalDatas[i].pileusPhenotype;  //菌盖形态
                        var pileusColorTV = totalDatas[i].pileusColor==null?"":totalDatas[i].pileusColor;  //菌盖颜色
                        var stipePhenotypeTV = totalDatas[i].stipePhenotype==null?"":totalDatas[i].stipePhenotype;  //菌柄形态
                        var stipeColorTV = totalDatas[i].stipeColor==null?"":totalDatas[i].stipeColor;  //菌柄颜色
                        var fruitbodyColorTV = totalDatas[i].fruitbodyColor==null?"":totalDatas[i].fruitbodyColor;  //子实体颜色
                        var fruitbodyTypeTV = totalDatas[i].fruitbodyType==null?"":totalDatas[i].fruitbodyType;  //子实体形态
                        var illuminationTV = totalDatas[i].illumination==null?"":totalDatas[i].illumination;  //光照
                        var collariumTV = totalDatas[i].collarium==null?"":totalDatas[i].collarium;  //菌环
                        var volvaTV = totalDatas[i].volva==null?"":totalDatas[i].volva;  //菌托
                        var velumTV = totalDatas[i].velum==null?"":totalDatas[i].velum;  //菌幕
                        var sclerotiumTV = totalDatas[i].sclerotium==null?"":totalDatas[i].sclerotium;  //菌核
                        var strainMediumTV = totalDatas[i].strainMedium==null?"":totalDatas[i].strainMedium;  //菌种培养基
                        var mainSubstrateTV = totalDatas[i].mainSubstrate==null?"":totalDatas[i].mainSubstrate;  //主要栽培基质
                        var afterRipeningStageTV = totalDatas[i].afterRipeningStage==null?"":totalDatas[i].afterRipeningStage;  //后熟期
                        var primordialStimulationFruitbodyTV = totalDatas[i].primordialStimulationFruitbody==null?"":totalDatas[i].primordialStimulationFruitbody;  //原基刺激&子实体
                        var reproductiveModeTV = totalDatas[i].reproductiveMode==null?"":totalDatas[i].reproductiveMode;  //生殖方式
                        var lifestyleTV = totalDatas[i].lifestyle==null?"":totalDatas[i].lifestyle;  //生活方式
                        var preservationTV = totalDatas[i].preservation==null?"":totalDatas[i].preservation;  //保藏方法
                        var domesticationTV = totalDatas[i].domestication==null?"":totalDatas[i].domestication;  //驯化
                        var nuclearPhaseTV = totalDatas[i].nuclearPhase==null?"":totalDatas[i].nuclearPhase;  //核相
                        var matingTypeTV = totalDatas[i].matingType==null?"":totalDatas[i].matingType;  //交配型
                        var tr = "<tr>" +
                            "<td class='paramTag'><input type='checkbox'/></td>" +
                            // "<td class='paramTag runNoTV'>" + idTV+
                            "</td><td class='paramTag runNoTV' data-id='" +idTV +"'>" + runNoTV+
                            "</td><td class='paramTag scientificNameTV'>" + scientificNameTV+
                            "</td><td class='paramTag sampleIdTV'>" + sampleIdTV+
                            "</td><td class='paramTag strainNameTV'>" + strainNameTV +
                            "</td><td class='paramTag localityTV'>" + localityTV +
                            "</td><td class='paramTag preservationLocationTV'>" + preservationLocationTV +
                            "</td><td class='paramTag typeTV'>" + typeTV +
                            "</td><td class='paramTag environmentTV'>" + environmentTV +
                            "</td><td class='paramTag materialsTV'>" + materialsTV +
                            "</td><td class='paramTag treatTV'>" + treatTV +
                            "</td><td class='paramTag timeTV'>" + timeTV +
                            "</td><td class='paramTag taxonomyTV'>" + taxonomyTV +
                            "</td><td class='paramTag myceliaPhenotypeTV'>" + myceliaPhenotypeTV +
                            "</td><td class='paramTag myceliaDiameterTV'>" +myceliaDiameterTV +
                            "</td><td class='paramTag myceliaColorTV'>" + myceliaColorTV +
                            "</td><td class='paramTag sporesColorTV'>" + sporesColorTV +
                            "</td><td class='paramTag sporesShapeTV'>" + sporesShapeTV +
                            "</td><td class='paramTag clampConnectionTV'>" + clampConnectionTV +
                            "</td><td class='paramTag pileusPhenotypeTV'>" + pileusPhenotypeTV +
                            "</td><td class='paramTag pileusColorTV'>" + pileusColorTV +
                            "</td><td class='paramTag stipePhenotypeTV'>" + stipePhenotypeTV +
                            "</td><td class='paramTag stipeColorTV'>" + stipeColorTV +
                            "</td><td class='paramTag fruitbodyColorTV'>" + fruitbodyColorTV +"</td>" +
                            "<td class='paramTag fruitbodyTypeTV'>" + fruitbodyTypeTV +"</td>"+
                            "<td class='paramTag illuminationTV'>" + illuminationTV +"</td>"+
                            "<td class='paramTag collariumTV'>" + collariumTV +"</td>"+
                            "<td class='paramTag volvaTV'>" + volvaTV +"</td>"+
                            "<td class='paramTag velumTV'>" + velumTV +"</td>"+
                            "<td class='paramTag sclerotiumTV'>" + sclerotiumTV +"</td>"+
                            "<td class='paramTag strainMediumTV'>" + strainMediumTV +"</td>"+
                            "<td class='paramTag mainSubstrateTV'>" + mainSubstrateTV +"</td>"+
                            "<td class='paramTag afterRipeningStageTV'>" + afterRipeningStageTV +"</td>"+
                            "<td class='paramTag primordialStimulationFruitbodyTV'>" + primordialStimulationFruitbodyTV +"</td>"+
                            "<td class='paramTag reproductiveModeTV'>" + reproductiveModeTV +"</td>"+
                            "<td class='paramTag lifestyleTV'>" + lifestyleTV +"</td>"+
                            "<td class='paramTag domesticationTV'>" + domesticationTV +"</td>"+
                            "<td class='paramTag nuclearPhaseTV'>" + nuclearPhaseTV +"</td>"+
                            "<td class='paramTag preservationTV'>" + preservationTV +"</td>"+
                            "<td class='paramTag matingTypeTV'>" + matingTypeTV +"</td>"+
                            "</tr>"
                        var $tbody = $("#tagKind table tbody");
                        $tbody.append(tr);
                    }

                };
                fn && fn();
                // 分页
                laypage({
                    cont: $('#tagsPagination .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(result.data.total /  page.pageSize), //通过后台拿到的总页数
                    curr: curr || 1, //当前页
                    /*skin: '#5c8de5',*/
                    skin: '#0f9145',
                    skip: true,
                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                    last: Math.ceil(result.data.total /  page.pageSize), //将尾页显示为总页数。若不显示，设置false即可
                    prev: '<',
                    next: '>',
                    groups: 3, //连续显示分页数
                    jump: function (obj, first) { //触发分页后的回调
                        if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                            var tmp = getParamas();
                            tmp.group = liVal;
                            tmp.pageNum = obj.curr;
                            tmp.pageSize = paramData.pageSize;
                            getData(tmp,obj.curr,resetSaveStatus);
                        }
                    }
                });
                // $("#total-page-count span").html(result.data.total);
                $("#tagsPagination #total-page-count span").html(result.data.total);
            },
            error:function (error){
                console.log(error);
            }
        })
    }
    // 点击时选择的group;
    var liVal;
    // 每个group的点击事件
    $(".popNames li").click(function (){
        liVal = $(this).text();
        var data = getParamas();
        data.group = liVal;
        getData(data,curr,resetSaveStatus);
    })
    if(initKindVal){
        var initKindVals = initKindVal.name;
        for (var i=0;i<initKindVals.length;i++){
            var div = "<div class='js-ad-dd'><label class='species-add' data-index=" + initKindVals[i].id + ">" + "<span></span><div class='label-txt'>" + initKindVals[i].name + "</div></label><i class='js-del-dd'>X</i></div>"
            $(".js-cursom-add").append(div);
        }
    }


})