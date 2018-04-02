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
        // 每次点击重置都会清空所有的input 框和select框里的值 modified by zjt 2018-3-23
        var inputValues = $("#tagKind table thead input");
        $.each(inputValues,function (i,item){
            $(item).val("");
        })
        //同时清空条件筛选框的值 modified by zjt 2018-3-23
        var selectValues = $("#tagKind table thead select");
        $.each(selectValues,function (i,item){
            $(item).val("");
        })
        //将pageSize改为10 modified by zjt 2018-3-23
        $("#per-page-count select").val('10');
        page.pageSize = 10;
        paramData.pageSize = page.pageSize;
        var data = getParamas();
            data.pageNum = 1;
            // 默认回到第一页，
        var pageSizeP = $("#page").find("p")
        liVal = "";
        for(var i=0;i<pageSizeP.length;i++){
            if($(pageSizeP[i]).hasClass("pageColor")){
                $(pageSizeP[i]).removeClass("pageColor")
            }
        };
        $(pageSizeP[0]).addClass("pageColor");
        getData(data,curr,resetSaveStatus);

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
        alert('This browser does NOT support localStorage');
    }

    // 选择品种中的保存群体
    $(".saveKind").click(function (){
        // 先判断保存群体/品种的数量
        var sampleTexts=$(".sample-text").text();
        if(sampleTexts.length==0){
            alert("请选择样品!")
            return;
        }
        var numbs =$(".js-cursom-add").find(".js-ad-dd").length;
        if(numbs>3){
            alert("最多可添加10个群体")
        }else {
            var selKinds = $(".sample-text").find("span");
            var selContent='';
            for (var i=0;i<selKinds.length;i++){
                selContent += $(selKinds[i]).text().substring(0,$(selKinds[i]).text().length-1) + ",";
            }
            var selContents = selContent.substring(0,selContent.length-1);

            // kindStorage.name.push(selContent);
            var arr = selContents.split(",");
            var arrStr = "";
            for(var j=0;j<arr.length;j++){
                arrStr+=arr[j].substring(3) + ",";
            };

            var newArrStr = arrStr.substring(0,arrStr.length-1);
            var ki = {name:selContents,
                     id:new Date().getTime(),
                     condition:{
                            cultivar:newArrStr
                     }
            };
            kindStorage.name.push(ki);
            var div = "<div class='js-ad-dd'><label class='species-add' data-index=" + ki.id + ">" + "<span></span><div class='label-txt'>" + selContents + "</div></label><i class='js-del-dd'>X</i></div>"
            $(".js-cursom-add").append(div);
            storage.setItem("kind",JSON.stringify(kindStorage));
            // setCookie("kind",JSON.stringify(kindStorage))
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
        // $(".sample-text").empty();
        var currentStatus = $(this).prop("checked");
        var selectedName =  $(this).parent().next().text();
        if (!selectedName){
            selectedName = $(this).parent().siblings().filter(".sampleNameT").text();
            if(currentStatus){
                var putName = "<span>样品名" + selectedName + "<i class='deleteSelected'>X</i></span>";
                $(".sample-text").append(putName);
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
                var putName = "<span>品种名" + selectedName + "<i class='deleteSelected'>X</i></span>";
                $(".sample-text").append(putName);
            }else {
                var checkNames = $(".sample-text").find("span");
                for (var i=0;i<checkNames.length;i++){
                    var checkName = $(checkNames[i]).text().substring(3,$(checkNames[i]).text().length-1);
                    if(selectedName == checkName){
                        $(checkNames[i]).remove();
                    }
                }
            }
        };
        // 清楚非品种之外的群体￥
        var wrapList =$(".sample-text").find("span");
          for (var i=0;i<wrapList.length;i++){
              if($(wrapList[i]).text().substring(0,3) !="品种名"){
                  $(wrapList[i]).remove();
              }
          }
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
            cultivar:$(".cultivarI").val(),  // 品种名
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
            "linoleic.value":$(".linoleicI").val(),
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
            "stearic.value":$(".stearicI").val(),
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
    // 选中品种按钮点击获取数据
    $("#kindSelect").click(function (){
        // 每次点击品种都会清空所有的input 框里的值
        var inputValues = $("#tagKind table thead input");
        $.each(inputValues,function (i,item){
            $(item).val("");
        })
        //同时清空条件筛选框的值 modified by zjt 2018-3-23
        var selectValues = $("#tagKind table thead select");
        $.each(selectValues,function (i,item){
            $(item).val("");
        })
        //同时清空条件筛选框的值 modified by zjt 2018-3-23
        $("#per-page-count select").val('10');
        page.pageSize = 10;
        paramData.pageSize = page.pageSize;
        var data = getParamas();
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
        $(this).parent().parent().find("input").val(""); //清空input框的值
        $(this).parent().parent().find("select").val(""); //清空select框的值
        //重新获取表格的值 modified by zjt 2018-3-22
        var data = getParamas();
        getData(data,paramData.pageNum,resetSaveStatus);
        //重新获取表格的值 modified by zjt 2018-3-22
        $(this).parent().parent().hide();
    })
    // // pageSize 选择事件
    $("#per-page-count select").change(function (e){
        var currentSelected = $(this).find("option:selected").text(); //选择的新的页码值
        page.pageSize = currentSelected;
        paramData.pageSize = page.pageSize;
    });


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
                        return alert("输入页码不能大于总页数");
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
    $("#tagsPagination select").change(function (e){
        /*var val = $(this).val();
        var data = getParamas();
        data.pageSize = val;
        data.pageNum =  paramData.pageSize;
        getData(data,data.pageNum);*/
        //modified by zjt
        var currentSelected = $(this).find("option:selected").text();//获取当前展示条数
        page.pageSize = currentSelected;
        paramData.pageSize = page.pageSize;
        page.pageNum = 1;
        paramData.pageNum = page.pageNum;
        var data = getParamas();
        data.pageNum =  paramData.pageNum;
        getData(data,data.pageNum);
        //modified by zjt
    })
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
    //$("#tagsPagination select").change(function (e){
    //    var currentSelected = $(this).find("option:selected").text();
    //  page.pageSize = currentSelected;
    //  paramData.pageSize = page.pageSize;
    //});
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
                        var cultivarTV = totalDatas[i].cultivar==null?"":totalDatas[i].cultivar;  // 品种名
                        var groupTV = totalDatas[i].group==null?"":totalDatas[i].group;  // 群体
                        var speciesTV = totalDatas[i].species==null?"":totalDatas[i].species;  // 物种
                        var localityTV = totalDatas[i].locality==null?"":totalDatas[i].locality; // 位置
                        var sampleNameTV = totalDatas[i].sampleName==null?"":totalDatas[i].sampleName;  //样品名
                        var weightPer100seedsTV = totalDatas[i].weightPer100seeds==null?"":totalDatas[i].weightPer100seeds;  //百粒重
                        var proteinTV = totalDatas[i].protein==null?"":totalDatas[i].protein;  //蛋白质含量
                        var oilTV = totalDatas[i].oil==null?"":totalDatas[i].oil;  //含油量
                        var maturityDateTV = totalDatas[i].maturityDate==null?"":totalDatas[i].maturityDate;  //熟期
                        var heightTV = totalDatas[i].height==null?"":totalDatas[i].height  //株高
                        var seedCoatColorTV = totalDatas[i].seedCoatColor==null?"":totalDatas[i].seedCoatColor;  //种皮色
                        var hilumColorTV  = totalDatas[i].hilumColor==null?"":totalDatas[i].hilumColor;  //种脐色
                        var cotyledonColorTV = totalDatas[i].cotyledonColor==null?"":totalDatas[i].cotyledonColor;  //子叶色
                        var flowerColorTV = totalDatas[i].flowerColor==null?"":totalDatas[i].flowerColor;  //花色
                        var podColorTV = totalDatas[i].podColor==null?"":totalDatas[i].podColor;  //荚色
                        var pubescenceColorTV = totalDatas[i].pubescenceColor ==null?"":totalDatas[i].pubescenceColor;  //茸毛色
                        var yieldTV = totalDatas[i].yield==null?"":totalDatas[i].yield;  //茸毛色
                        var upperLeafletLengthTV = totalDatas[i].upperLeafletLength==null?"":totalDatas[i].upperLeafletLength;   //顶端小叶长度
                        var linoleicTV = totalDatas[i].linoleic==null?"":totalDatas[i].linoleic;  //亚油酸
                        var linolenicTV = totalDatas[i].linolenic==null?"":totalDatas[i].linolenic;  //亚麻酸
                        var oleicTV = totalDatas[i].oleic==null?"":totalDatas[i].oleic;  //油酸
                        var palmiticTV = totalDatas[i].palmitic==null?"":totalDatas[i].palmitic;  //软脂酸
                        var stearicTV = totalDatas[i].stearic==null?"":totalDatas[i].stearic;  //硬脂酸

                        var tr = "<tr><td class='paramTag'><input type='checkbox'/></td><td class='paramTag cultivarT'>" + cultivarTV+
                            "</td><td class='paramTag groupT'>" + groupTV+
                            "</td><td class='paramTag speciesT'>" + speciesTV+
                            "</td><td class='paramTag localityT'>" + localityTV +
                            "</td><td class='paramTag sampleNameT'>" + sampleNameTV +
                            "</td><td class='paramTag weightPer100seedsT'>" + weightPer100seedsTV +
                            "</td><td class='paramTag proteinT'>" + proteinTV +
                            "</td><td class='paramTag oilT'>" + oilTV +
                            "</td><td class='paramTag maturityDateT'>" + maturityDateTV +
                            "</td><td class='paramTag heightT'>" + heightTV +
                            "</td><td class='paramTag seedCoatColorT'>" + seedCoatColorTV +
                            "</td><td class='paramTag hilumColorT'>" + hilumColorTV +
                            "</td><td class='paramTag cotyledonColorT'>" + cotyledonColorTV +
                            "</td><td class='paramTag flowerColorT'>" +flowerColorTV +
                            "</td><td class='paramTag podColorT'>" + podColorTV +
                            "</td><td class='paramTag pubescenceColorT'>" + pubescenceColorTV +
                            "</td><td class='paramTag yieldT'>" + yieldTV +
                            "</td><td class='paramTag upperLeafletLengthT'>" + upperLeafletLengthTV +
                            "</td><td class='paramTag linoleicT'>" + linoleicTV +
                            "</td><td class='paramTag linolenicT'>" + linolenicTV +
                            "</td><td class='paramTag oleicT'>" + oleicTV +
                            "</td><td class='paramTag palmiticT'>" + palmiticTV +
                            "</td><td class='paramTag stearicT'>" + stearicTV +"</td></tr>"
                        var $tbody = $("#tagKind table tbody");
                        $tbody.append(tr);
                    }

                };
                fn && fn();
                // 分页
                laypage({
                    cont: $('#tagsPagination .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    /*pages: Math.ceil(result.data.total /  page.pageSize), //通过后台拿到的总页数*/
                    pages: parseInt(result.data.total /  page.pageSize) + 1, //通过后台拿到的总页数
                    curr: curr || 1, //当前页
                    skin: '#5c8de5',
                    skip: true,
                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                    /*last: Math.ceil(result.data.total /  page.pageSize), //将尾页显示为总页数。若不显示，设置false即可*/
                    last: parseInt(result.data.total /  page.pageSize) + 1, //将尾页显示为总页数。若不显示，设置false即可
                    prev: '<',
                    next: '>',
                    groups: 2, //连续显示分页数
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
                $(".js-total-samples").html(result.data.total);
                //modified by zjt 2018-3-22
                $("#total-page-count span").html(result.data.total);
                //modified by zjt 2018-3-22
            },
            error:function (error){
                if(error.status==901){window.location.href=CTXROOT + "/login"};
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

    $("#allSelected").click(function (){
        if($(this).hasClass("whiteOk")){
            $(this).removeClass("whiteOk").addClass("rightOk");
        }else {
           $(this).removeClass("rightOk").addClass("whiteOk");
        }
    });

    $("#clear-all").click(function(){
        if($("#allSelected").hasClass("rightOk")){
            $("#allSelected").removeClass("rightOk").addClass("whiteOk");
        }
    });


})