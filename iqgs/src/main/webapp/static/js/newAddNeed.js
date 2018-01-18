/*2017 - 12 新增业务需求 add by jarry*/
// $(function (){


    // ajax 请求的代码封装
    function SendAjaxRequest(method, url,data) {
        if (window.Promise) {//检查浏览器是否支持Promise
            var promise = new Promise(function (resolve, reject) {
                $.ajax({
                    method: method,
                    url: url,
                    data:data,
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    success: function (result) {
                        resolve(result)
                    },
                    error: function (error) {
                        reject(error)
                    }
                });
            });
            return promise;
        } else {
            alert("sorry,你的浏览器不支持Promise 对象")
        };
    };
    // qtl 搜索 -- 》获取数据 -- >search
    $("#QtlBtnName").click(function (){
        var qtlSearchVal = $("#qtlName").val().trim();
        var data ={
            qtlName:qtlSearchVal
        };
        var promise = SendAjaxRequest("GET",ctxROOT + "/advance-search/query-by-qtl-name",data);
        promise.then(
            function (result){
                if(result.length==0){
                    if($("#qtlErrorTip").is(":hidden")){
                        $("#qtlErrorTip").show();
                        $("#qtlName").addClass("inputErrorColor")
                    };
                    if(!$("#qtlAdd .sureBtn").is(":hidden")){
                        $("#qtlAdd .sureBtn").hide();
                    }
                }else {
                    if( $("#qtlName").hasClass("inputErrorColor")){
                        $("#qtlName").removeClass("inputErrorColor")
                    };
                    if($("#qtlAdd .fuzzySearch").is(":hidden")){
                        $("#qtlAdd .fuzzySearch").show();
                    };
                    if(!$("#qtlErrorTip").is(":hidden")){
                        $("#qtlErrorTip").hide();
                    };
                    if($("#qtlAdd .sureBtn").is(":hidden")){
                        $("#qtlAdd .sureBtn").show();
                    }
                    // 动态生成qtlname列表
                    var list = result;
                    var $ul = $("#qtlAdd .fuzzySearch ul");
                    $ul.empty();
                    for (var i = 0; i < list.length; i++) {
                        var name = list[i].qtlName;
                        var id = list[i].associatedGenesId;
                        // 判断：如果qltName的长度大于36的话，就单独一行显示
                        if(name.length>36){
                            var li = "<li class='onLine'>" + "<label for='" + name + "'><span id ='" + name + "' data-value='" + id + "'></span>" + name + "</label></li>";
                        }else {
                            var li = "<li>" + "<label for='" + name + "'><span id ='" + name + "' data-value='" + id + "'></span>" + name + "</label></li>";
                        }
                        $ul.append(li);
                    }
                }
            },
            function (error){
                console.log(error);
            }
        )
    });
    // 每个qtlname列表的点击选中事件
    var globleObject = {};
     globleObject.selectedQtl = [];
     globleObject.selectedQtlNames = [];
    $("#qtlAdd .fuzzySearch").on("click","li",function (){
        var list =  $("#qtlAdd .fuzzySearch li");
        if($(this).hasClass("checked")) {
            $(this).removeClass("checked");
            for(var i=0;i<globleObject.selectedQtl.length;i++){
                if(globleObject.selectedQtl[i] == $(this).find("span").attr("data-value")){
                    globleObject.selectedQtl.splice(i,1);
                    globleObject.selectedQtlNames.splice(i,1);
                }
            }
        }
        else {
            $(this).addClass("checked")
            globleObject.selectedQtl.push($(this).find("span").attr("data-value"));
            globleObject.selectedQtlNames.push($(this).find("span").attr("id"));
        }
    });
    // sessionStorage
    if(window.sessionStorage){
        var storage = window.sessionStorage;
    }else{
        alert('This browser does NOT support localStorage');
    };
    // 根据选择的qtl 搜索 -- > sureBtn
    $("#qtlAdd .sureBtn").click(function (){
        flag = 0;
        storage.setItem("qtlSearchNames", JSON.stringify(globleObject.selectedQtlNames));
        var num = globleObject.selectedQtl.length;
        var qtlNameArr = globleObject.selectedQtl;
        if(num>5) {
            alert("最多只能选择 5 个");
            return;
        } else {
            var qtlName="";
            $.each(qtlNameArr,function (i,item) {
                qtlName += item + "*";
            });
            var qtlNames = qtlName.substring(0,qtlName.length-1);
            window.location = DOMAIN + "/search/list?keyword=" + encodeURI(qtlNames)+ "&searchType=4" ;
        }
    })
    // qtl 示例搜索
    $("#qtlAdd .qtlExample").click(function (){
        var qtlNames = $(this).text();
        var qtlNameArr = [];
        qtlNameArr.push(qtlNames)
        storage.setItem("qtlSearchNames",JSON.stringify(qtlNameArr));
        window.location = DOMAIN + "/search/list?keyword=" +6340+ "&searchType=4" ;
    });

    // geneID 示例搜索
    $("#GeneIdName .geneExampleId,#GeneIdName .geneExampleName").click(function (){
        var qtlId = "Glyma.01G004900";
        window.location = DOMAIN + "/search/list?keyword=" +encodeURI(qtlId)+ "&searchType=1" ;
    });
    // name 示例搜索
    $("#GeneFunction .geneExampleName").click(function (){
        var qtlId = "ALF4";
        window.location = DOMAIN + "/search/list?keyword=" +encodeURI(qtlId)+ "&searchType=2" ;
    });
    // function 示例搜索
    $("#GeneFunction .funcExample").click(function (){
        var func = "cycloartenol synthase";
        window.location = DOMAIN + "/search/list?keyword=" + encodeURI(func) + "&searchType=2";
    });

    // 高级搜索
    $("#advancedSearch p").click(function(){
        getExpreData();
        getSnpData();
        getIndelData();
        getQtlData();
        // toggle img
        // if($(this).find("img").attr("src").indexOf("downtou") !=-1){
        //     $(this).find("img").attr("src",ctxStatic + "/images/uptou.png");
        //     $("#advanceSelect").show();
        // }else {
        //     $(this).find("img").attr("src",ctxStatic + "/images/downtou.png");
        //     $("#advanceSelect").hide();
        // };
        toggleShow();
    });
    // 高级搜索页面显示/隐藏切换
    function toggleShow(){
        var $btn = $("#advancedSearch p");
        if($btn.find("img").attr("src").indexOf("downtou") !=-1){
            $btn.find("img").attr("src",ctxStatic + "/images/uptou.png");
            $btn.addClass("flag")
            $("#advanceSelect").show();
        }else {
            $btn.find("img").attr("src",ctxStatic + "/images/downtou.png");
            $btn.removeClass("flag")
            $("#advanceSelect").hide();
        };
    }
    // 获取基因表达量的组织以及小组织
    function getExpreData (){
        var promise = SendAjaxRequest("GET",  window.ctxROOT + "/advance-search/query-all-organic");
        promise.then(
            function (result){
              // globalObj.expreDatas = result.data;  // 基因表达量的所有数据
              globalObj.expreDatas = result;  // 基因表达量的所有数据
                // var arr = result.data;
                var arr = result;
                var $ul =  $("#expreKinds .expreList ul");
                // 基因表达量 下拉框数据渲染
                $ul.empty();
                for (var i=0;i<arr.length;i++){
                    var li = "<li data-id='" +arr[i].id+ "'>" + arr[i].chinese + "</li>";
                   $ul.append(li);
                }
            },
            function (error){
                console.log(error);
            }
        )
    };
    // 获取snp 数据
    function getSnpData(){
        var promise = SendAjaxRequest("POST",window.ctxROOT + "/advance-search/query-snp");
        promise.then(
            function (result){
                if(result.length != 0){
                    globalObj.snpDatas = result;  // 基因表达量的所有数据
                    var obj = {
                        name:"snp",
                        data:result
                    }
                    snpRender(obj);
                }else {
                    console.log("无数据/系统异常");
                }
            },
            function (error){
                console.log(error);
            }
        )
    };
    // 获取indel 数据
    function getIndelData(){
        var promise = SendAjaxRequest("POST",window.ctxROOT + "/advance-search/query-indel");
        promise.then(
            function (result){
                if(result.length != 0){
                    globalObj.indelDatas = result;
                    var obj = {
                        name:"indel",
                        data:result
                    }// 基因表达量的所有数据
                    indelRender(obj);
                }else {
                    console.log("无数据/系统异常");
                }
            },
            function (error){
                console.log(error);
            }
        )
    };
    // 获取qtl 数据
    function getQtlData(){
        var promise = SendAjaxRequest("GET", window.ctxROOT + "/advance-search/fetch-qtl-smarty");
        promise.then(
            function (result){
                if(result.length !=0){
                    globalObj.qtlDatas = result;  // 基因表达量的所有数据
                }else {
                    console.log("无数据/系统异常");
                }
            },
            function (error){
                console.log(error);
            }
        )
    };
    // 基因表达量 选择
    $("#advanceSelect .selectedKinds ul").on("click","li",function (){
        var list = $("#advanceSelect .selectedKinds ul li");
        if($(this).hasClass("checked")) {
            $(this).removeClass("checked");
        }
        else {
            $(this).addClass("checked")
        }
    });
    // SNP 每个li 点击事件
    $("#advanceSelect .geneSnp ul").on("click","li",function (){
        var list = $("#advanceSelect .geneSnp ul li");
        var val = $(this).find("label").text().trim();
        var id = $(this).find("span").attr("id");
        var showList = $("#expreDetail span.snpSigle");
        if($(this).hasClass("checked")) {
            $(this).removeClass("checked");
            for(var i=0;i< globalObj.SleSnpDatas.length;i++){
                if(globalObj.SleSnpDatas[i] == val){
                    globalObj.SleSnpDatas.splice(i,1);
                };
            };
            if(showList.length == 1){
                $(showList[0]).next().remove();
                $(showList[0]).prev().remove();
                $(showList[0]).remove();
            }else {
                for(var i=0;i<showList.length;i++){
                    if($(showList[i]).attr("class").split(" ")[2] == id){
                        $(showList[i]).next().remove();
                        $(showList[i]).remove();
                    }
                }
            }
        }
        else {
            $(this).addClass("checked");
            globalObj.SleSnpDatas.push(val);
            // 把选择的放在上面
            // 如果已经有一个snp了，则直接在后面添加
            if(showList.length ==0){
                var span = " <span class='expreSigle' style='padding-right:0px;'>SNP:</span><span class='expreSigle snpSigle " +id +  "'>" + val + "</span><span class='deleteIconP deleteIconPsnp'>X</span>";
                $("#expreDetail").append(span);
            }else {
                var snps =  $("#expreDetail span.deleteIconPsnp")[$("#expreDetail span.deleteIconPsnp").length-1];
               $(snps).after(" <span class='expreSigle snpSigle " +id +  "'>" + val + "</span><span class='deleteIconP deleteIconPsnp'>X</span>")
            }
        };
    });
    // indel 每个li的点击事件
    $("#advanceSelect .geneIndel ul").on("click","li",function (){
        var list = $("#advanceSelect .geneIndel ul li");
        var val = $(this).find("label").text().trim();
        var id = $(this).find("span").attr("id");
        var showList = $("#expreDetail span.indelSigle");
        if($(this).hasClass("checked")) {
            $(this).removeClass("checked");
            var val = $(this).find("label").text().trim();
            for(var i=0;i< globalObj.SleIndelDatas.length;i++){
                if(globalObj.SleIndelDatas[i] == val){
                    globalObj.SleIndelDatas.splice(i,1);
                };
            };
            if(showList.length == 1){
                $(showList[0]).next().remove();
                $(showList[0]).prev().remove();
                $(showList[0]).remove();
            }else {
                for(var i=0;i<showList.length;i++){
                    if($(showList[i]).attr("class").split(" ")[2] == id){
                        $(showList[i]).next().remove();
                        $(showList[i]).remove();
                    }
                }
            }
        }else {
            $(this).addClass("checked");
            var val = $(this).find("label").text().trim();
            globalObj.SleIndelDatas.push(val);
            // 把选择的放在上面
            // 如果已经有一个snp了，则直接在后面添加
            if(showList.length ==0){
                var span = " <span class='expreSigle' style='padding-right:0px;'>INDEL:</span><span class='expreSigle indelSigle " +id +  "'>" + val + "</span><span class='deleteIconP deleteIconPindel'>X</span>";
                $("#expreDetail").append(span);
            }else {
                var snps =  $("#expreDetail span.deleteIconPindel")[$("#expreDetail span.deleteIconPindel").length-1];
                $(snps).after(" <span class='expreSigle indelSigle " +id +  "'>" + val + "</span><span class='deleteIconP deleteIconPindel'>X</span>")
            }
        };
    });

    // QTL  品种选择
    $("#advanceSelect .geneQtl .qtlSelectList ul").on("click","li",function (){
        var list = $("#advanceSelect .geneQtl .qtlList ul li");
        var box1 = $("#qtlBox1").val().trim();
        var currVal = $(this).find("label").text().trim();
        var currKn = $(this).find("span").attr("id").trim();
        var currKns = $(this).attr("data-id").trim();
        var currNm = $(this).attr("data-name").trim();
        var list = $("#expreDetail span.qtlSigle");
        if($(this).hasClass("checked")) {
            $(this).removeClass("checked");
            if(list.length == 1){  //  只有一个元素时
                $(list[0]).next().remove();
                $(list[0]).remove();
                $("#expreDetail span.qtlSigleTitle").remove();
            }else {
                for(var i=0;i<list.length;i++){
                    if($(list[i]).attr("class").split(" ")[2] == currKns && $(list[i]).text().indexOf("QTL")==-1){
                        $(list[i]).next().remove();
                        $(list[i]).remove();
                    }else if ($(list[i]).attr("class").split(" ")[2] == currKns && $(list[i]).text().indexOf("QTL")!=-1){
                        $(list[i]).next().remove();
                        $(list[i]).removeClass(currKns).removeClass("qtlSigle").addClass("qtlSigleTitle").text("QTL:");
                    }
                }
            };

        }
        else {
            $(this).addClass("checked");
            //选中的放在上面
            // 判断是否有本品种的已经显示在上面
            var list = $("#expreDetail span.qtlSigle");
            if(list.length != 0){
                var flag = 0;
                for(var k=0;k<list.length;k++){
                    var showQtl =  $(list[k]).text().indexOf("QTL")!=-1?$(list[k]).text().split(":")[1]:$(list[k]).text();
                    var box1Length = box1.length;
                    var newShowQtl = showQtl.indexOf(box1)!=-1?showQtl.substring(box1Length):showQtl;

                    if(newShowQtl == currVal){
                        flag = 1;
                    }
                };
                if(flag == 0){
                    qtlShowDatas(list,box1,currKns,currVal,list.length,currNm);
                }
            }else {
                // 删除qtl头
                    $("#expreDetail span.qtlSigleTitle").remove();
                var span = " <span class='expreSigle qtlSigle " +currKns +  "'data-name='" +currNm + "'data-value='"+currVal+"'>QTL:" +box1 + currVal+  "</span><span class='deleteIconP deleteIconPqtl'>X</span>";
                $("#expreDetail").append(span);
            }
        };
        // 获取qtl 相关参数
        getQtlParams();
    });

        // 筛选代码封装
    // input  框点击
    function cntClick(parent,list){
        var imgs = $("#" + parent).find("img");
        $("#" + parent).find("." + list).show();
        $("#" + parent).find("input").addClass("borderWt");
        if(!$(imgs[0]).is(":hidden")){
            $(imgs[0]).hide();
        };
        if($(imgs[1]).is(":hidden")){
            $(imgs[1]).show();
        };
    };
    // input 下拉框列表 li 点击
    function liClick(parent,list,that){
        var imgs = $("#" + parent).find("img");
        that && $("#" + parent).find("input").val($(that).text().trim());
        $("#" + parent).find("." + list).hide();
        $("#" + parent).find("input").removeClass("borderWt")
        if($(imgs[0]).is(":hidden")){
            $(imgs[0]).show();
        };
        if(!$(imgs[1]).is(":hidden")){
            $(imgs[1]).hide();
        };
    };
    // 基因表达量 筛选
    $("#expreKinds .inputBox").click(function (){
        if($(this).find("input").hasClass("borderWt")){
            $(this).find("input").removeClass("borderWt");
            $("#expreKinds .expreList").hide();
            var imgs =$(this).find("img");
            if($(imgs[0]).is(":hidden")){
                $(imgs[0]).show();
            };
            if(!$(imgs[1]).is(":hidden")){
                $(imgs[1]).hide();
            };
        }else {
            cntClick("expreKinds","expreList");
        }
    });
    // 基因表达量 大组织和小组织的选择
    $("#expreKinds ul").on("click","li",function (e){
        var that = this;
            // 只能选择一次
        if(!$(this).hasClass("testClass")){
            // 先清空
            $("#selectedKinds ul").empty();
            $(this).addClass("testClass");
            // var currVal = $(this).text().trim();
            var currId = $(this).attr("data-id");
            liClick("expreKinds","expreList",that);
            var list =  globalObj.expreDatas;
            // 生成对应的小组织
            for(var i=0;i<list.length;i++){
                // if(currVal == list[i].name){
                if(currId == list[i].id){
                    var $ul = $(".selectedKinds ul");
                    // var arr = list[i].detail;
                    var arr = list[i].children;
                    if(arr.length!=0){
                        // var currExpreName = arr[0];
                        if($("#selectedKinds").is(":hidden")){
                            $("#selectedKinds").show();
                        };
                        for(var k=0;k<arr.length;k++){
                            var li = " <li class='checked'>\n" +
                                "                            <label for='" + currId + k+ "'>\n" +
                                "                            <span id ='" + currId + k+ "'></span>\n" +
                                arr[k].name+
                                "                            </label>\n" +
                                "                        </li>";
                            $ul.append(li);
                        }
                    }
                }
            }
        };
        liClick("expreKinds","expreList",that);
    });

    // qtl 下拉框  第一个
    $("#qtlKinds .inputBox").click(function (){
        if($(this).find("input").hasClass("borderWt")){
            $(this).find("input").removeClass("borderWt");
            $(".geneQtl .qtlList").hide();
            var imgs =$(this).find("img");
            if($(imgs[0]).is(":hidden")){
                $(imgs[0]).show();
            };
            if(!$(imgs[1]).is(":hidden")){
                $(imgs[1]).hide();
            };
        }else {
            cntClick("qtlKinds","qtlList");
        };
        // 向一级输入框填充数据
        var list = globalObj.qtlDatas;
        var $ul = $("#qtlKinds .qtlList ul");
        $ul.empty();
        for(var i=0;i<list.length;i++){
            var li = "<li id='qtl" +list[i].traitCategoryId+ "'data-name='" +list[i].qtlDesc +  "'>" + list[i].qtlDesc +"</li>";
            $ul.append(li);
        };
    });
    // qtl 第一个下拉框点击时需要向第二个下拉框填充数据
    $("#qtlKinds ul").on("click","li",function (){
        var that = this;
        liClick("qtlKinds","qtlList",that);
        // 向第二个input框填入数据
        var $ul = $("#qtlKinds2 .qtlList ul");
        $ul.empty();
        $("#qtlBox2").val("请选择");
        var list = globalObj.qtlDatas;
        var box2list ;
        var qtlN;
        for (var i=0;i<list.length;i++){
            if(parseInt($(this).attr("id").substring(3)) == list[i].traitCategoryId){
                box2list = list[i].traitLists;
                // qtlN = list[i].qtlName;
                qtlN = list[i].qtlDesc;
            }
        };
        for(var k=0;k<box2list.length;k++){
            var li = "<li id='qtlt" +box2list[k].traitListId + "'data-name='" + qtlN +"'>" + box2list[k].traitName + "</li>";
            // $(li).data("qtlt"+box2list[k].qtlId,box2list[k].qtls);

            $ul.append(li);
            $("#qtlt" + box2list[k].traitListId).data("qtlt"+box2list[k].traitListId,box2list[k].qtls);
            $("#qtlt" + box2list[k].traitListId).data("qtlName",qtlN);
           // var data = $("#qtlt" + box2list[k].qtlId).data("qtlt"+box2list[k].qtlId);
        }
    });
    // qtl 下拉框 第二个  -- 样式
    $("#qtlKinds2 .inputBox").click(function (){
        if($(this).find("input").hasClass("borderWt")){
            $(this).find("input").removeClass("borderWt");
            $("#qtlKinds2 .qtlList").hide();
            var imgs =$(this).find("img");
            if($(imgs[0]).is(":hidden")){
                $(imgs[0]).show();
            };
            if(!$(imgs[1]).is(":hidden")){
                $(imgs[1]).hide();
            };
        }else {
            cntClick("qtlKinds2","qtlList");
        };

    });
    // qtl  二级下拉框的点击事件
    $("#qtlKinds2 ul").on("click","li",function (){
        if($(".geneQtl .qtlSelectList").is(":hidden")){
            $(".geneQtl .qtlSelectList").show();
        };
        var that = this;
        liClick("qtlKinds2","qtlList",that);
        // 展示所有品种
        var $ul = $(".geneQtl .qtlSelectList ul");
        $ul.empty();
        var getName = $(this).attr("id");
        var datas =$(this).data(getName);
        var qtlName = $(this).data("qtlName");
       // 展示详细品展信息
        for(var m=0;m<datas.length;m++){
            var li = " <li data-name='" +qtlName+ "'data-id='qtlt" + datas[m].associatedGenesId+"'>\n" +
                "                            <label for='" +getName + m+ "'>\n" +
                "                                <span id='"+getName + m+"'></span>\n" +
                datas[m].qtlName+
                "                            </label>\n" +
                "                        </li>";
            $ul.append(li);
        };
        // 状态保存
        var qtlList = $("#expreDetail span.qtlSigle");
        var qtlShowList = $(".geneQtl .qtlSelectList ul li");
        for(var i=0;i<qtlList.length;i++){
            var id = $(qtlList[i]).attr("class").split(" ")[2];
            for (var k=0;k<qtlShowList.length;k++){
                // if(id == $(qtlShowList[k]).find("span").attr("id")){
                if(id == $(qtlShowList[k]).attr("data-id")){
                    $(qtlShowList[k]).addClass("checked");
                }
            }
        }
    });
    var flagStart = 0;
    var flagEnd = 0;
    // 基因表达量 input 输入框的失焦事件
    var expreEnd;
    var expreStart;
    $("#expreStart").on("blur change",function (){
        expreStart = $("#expreStart").val();
        if(!expreStart){
            if(!$("#expreStart").hasClass("borderColorInput")){
                $("#expreStart").addClass("borderColorInput");
                if(!$(".expreConfirm").hasClass("backColorBtn")){
                    $(".expreConfirm").addClass("backColorBtn");
                }
            }else {
                if(flagStart == 0){
                    $(".expreConfirm").addClass("backColorBtn");
                }
            }
            return ;
        }else if(expreStart){
            flagStart = 1;
            if($("#expreStart").hasClass("borderColorInput")){
                $("#expreStart").removeClass("borderColorInput");
            };
            if(flagStart == 1 && flagEnd == 1){
                $(".expreConfirm").removeClass("backColorBtn");
            }
        }
    });
    // FPKM值为必填项，如果没填，就弹框提示并框框变红
    $("#expreEnd").on("blur change",function (){
        expreEnd = $("#expreEnd").val();
        if(!expreEnd){
            if(!$("#expreEnd").hasClass("borderColorInput")){
                $("#expreEnd").addClass("borderColorInput");
                if(!$(".expreConfirm").hasClass("backColorBtn")){
                    $(".expreConfirm").addClass("backColorBtn");
                }
            }else if(flagEnd == 0){
                $(".expreConfirm").addClass("backColorBtn");
            }
            return ;
        }else if(expreEnd){
            flagEnd = 1;
            if($("#expreEnd").hasClass("borderColorInput")){
                $("#expreEnd").removeClass("borderColorInput");
            };
            if(flagStart == 1 && flagEnd == 1){
                $(".expreConfirm").removeClass("backColorBtn");
            }
        }
    });
    // 基因表达量 --》 确定
    $("#selectedKinds .expreConfirm").click(function (){
            // 当前列表置灰
        var beginVal =  Number($("#expreStart").val());
        var endVal =  Number($("#expreEnd").val());
        if(flagStart!=1 || flagEnd!=1 || beginVal>endVal){
            if(!$("#expreStart").hasClass("borderColorInput")){
                $("#expreStart").addClass("borderColorInput");
            };
            if(!$("#expreEnd").hasClass("borderColorInput")){
                $("#expreEnd").addClass("borderColorInput");
            };
            $(".expreConfirm").addClass("backColorBtn");
            return;
        }
        var name = $("#expreKinds div.inputBox input").val().trim();
       var list = $("#expreKinds .expreList ul li");
       $.each(list,function (i,item){
           // if($(item).hasClass("testClass") && $(item).text().trim() == name){
           //     $(item).addClass("noClick");
           // }
           if($(item).text().trim() == name){
               $(item).addClass("noClick").addClass("testClass");
           }
       });
       liClick("expreKinds","expreList");
        for(var i=0;i<globalObj.SleExpreDatas.length;i++){
            if(globalObj.SleExpreDatas[i].name == name){
                return;
            }
        };

       // 保存相关信息
        var kindObj ={
            name:"",
            selected:[],
            FPKM:""
        };
        var currTotal = $("#selectedKinds ul.lists").find("li");
        $.each(currTotal,function (i,item){
            if($(item).hasClass("checked")){
               var val = $(item).find("label").text().trim();
               kindObj.selected.push(val);
            };
        });
        var fpkmStart = $("#expreStart").val();
        var fpkmEnd = $("#expreEnd").val();
        var fpkm = fpkmStart + "-" + fpkmEnd;
        kindObj.FPKM = fpkm;
        kindObj.name =name;
        var secondKind = kindObj.selected;
        var secondStr = "";
        for(var i=0;i<secondKind.length;i++){
            secondStr +=secondKind[i] + ","
        };
        var secondStrs = secondStr.substring(0,secondStr.length-1);
        // 当前的品种
        var currkn = kindObj.selected[0];
        // // 选中的放在上面
        var span = " <span class='expreSigle expreSigle1 " +currkn +  "'>基因表达量:" + kindObj.name + kindObj.FPKM + "," + secondStrs + "</span><span class='deleteIconP deleteIconPexp'>X</span>";
        $("#expreDetail").append(span);
        globalObj.SleExpreDatas.push(kindObj);
    });
    // 去除所有的基因表达量 -->
    var tip;
    $("#expreDetail").on("mouseover",".expreSigle1",function (){
        // 所有的基因表达量
        var list = globalObj.SleExpreDatas;
        var currEle = $(this).attr("class").split(" ")[2];
        var currName = $(this).text().split(":")[1].substring(0,1);
        if(!list.length){
            return ;
        };
        var $div = $("<div id='exprePopTip'></div>");
        for(var i=0;i<list.length;i++){
            if(list[i].name.substring(0,1) == currName){
                var secondStr = "";
                var arr = list[i].selected;
                for(var k=0;k<arr.length;k++){
                    secondStr +=arr[k] + ","
                };
                var secondStrs = secondStr.substring(0,secondStr.length-1);
                var $span = $("<span>基因表达量:" + list[i].name + list[i].FPKM + "," +secondStrs + "</span>");
                $div.append($span);
            };
        };
       tip = layer.tips($div.get(0).outerHTML, '.' + currEle, {
           tips: [1, '#F5F5F5'],
           area: ['auto', 'auto'],
            guide: 1,
            time: 60000000
        });
       $(".layui-layer-content").parent().attr("width","900px!important");
    }).on("mouseleave",function (){
        layer.close(tip);
    });

    // 基因表达量X 事件
    $(document).on("click","span.deleteIconPexp",function (){
        // 取消置灰
        var  data = $(this).prev().text().trim();
        var kind = data.split(":")[1].substring(0,1);
        var list = $("#expreKinds .expreList").find("li");
        for(var i=0;i<list.length;i++){
            if($(list[i]).text().trim().substring(0,1) == kind){
                $(list[i]).removeClass("noClick").removeClass("testClass");
            }
        };
        var arr = globalObj.SleExpreDatas;
        if(arr.length !=0){
            for(var i=0;i<arr.length;i++){
                if(arr[i].name.substring(0,1) == kind){
                   arr.splice(i,1);
                }
            };
        };
        //  删除当前基因表达量
        $(this).prev().remove();
        $(this).remove();
        if(!$.contains($("#exprePopTip").get(0),$("#exprePopTip").find("span").get(0))){
            layer.close(tip);
        }
    });
     // snp x 事件
    $(document).on("click","span.deleteIconPsnp",function (){
            var id = $(this).prev().attr("class").split(" ")[2].trim();
            var list = $(".geneSnp .snpList ul li");
            for(var i=0;i<list.length;i++){
                if($(list[i]).find("span").attr("id") == id){
                    $(list[i]).removeClass("checked");
                }
            };
            var snpNum = $("#expreDetail .snpSigle");
            if(snpNum.length == 1){
                $(this).prev().prev().remove();
                $(this).prev().remove();
                $(this).remove();
            }else {
                $(this).prev().remove();
                $(this).remove();
            }
    });
    // indel x 事件
    $(document).on("click","span.deleteIconPindel",function (){
        var id = $(this).prev().attr("class").split(" ")[2].trim();
        var list = $(".geneIndel .indelList ul li");
        for(var i=0;i<list.length;i++){
            if($(list[i]).find("span").attr("id") == id){
                $(list[i]).removeClass("checked");
            }
        };
        var indelNum = $("#expreDetail .indelSigle");
        if(indelNum.length == 1){
            $(this).prev().prev().remove();
            $(this).prev().remove();
            $(this).remove();
        }else {
            $(this).prev().remove();
            $(this).remove();
        }
    });
    // qtl x 事件
    $(document).on("click","span.deleteIconPqtl",function (){
        var id = $(this).prev().attr("class").split(" ")[2].trim();
        var txt = $(this).prev().text().trim();
        var list = $(".geneQtl .qtlSelectList ul li");
        // ============
        var box1Size = $("#qtlBox1").val().trim();
        //=========
        for(var i=0;i<list.length;i++){
            // if($(list[i]).find("span").attr("id") == id){
            if($(list[i]).attr("data-id") == id){
                $(list[i]).removeClass("checked");
            }
        };
        var qtlNum = $("#expreDetail .qtlSigle");
        if(txt.indexOf('QTL')!=-1 && qtlNum.length > 1){
            // $(this).prev().removeClass(id).removeClass("qtlSigle").addClass("qtlSigleTitle").text("QTL:");
                var nextTxt = $(this).next().text();
                $(this).next().text("QTL:" + box1Size + nextTxt);
                $(this).prev().remove()
            $(this).remove();
        }else if(qtlNum.length == 1){
            // $(this).prev().prev().remove();
            $(this).prev().remove();
            $(this).remove();
        }
        else {
            $(this).prev().remove();
            $(this).remove();
        };
    });
    // snp 数据渲染
    function snpRender(data){
        var name = data.name;
        var list = data.data;
        var $ul= $(".geneSnp .snpList ul");
        $ul.empty();
        for(var i=0;i<list.length;i++){
            var $li = " <li>\n" +
                "                            <label for=" + name + i +">\n" +
                "                                <span id =" + name + i +"></span>\n" +
                list[i] +
                "                            </label>\n" +
                "                        </li>";
            $ul.append($li);
        };
    };
    // indel 数据渲染
    function indelRender(data){
        var name = data.name;
        var list = data.data;
        var $ul= $(".geneIndel .indelList ul");
        $ul.empty();
        for(var i=0;i<list.length;i++){
            var $li = " <li>\n" +
                "                            <label for=" + name + i +">\n" +
                "                                <span id =" + name + i +"></span>\n" +
                list[i] +
                "                            </label>\n" +
                "                        </li>";
            $ul.append($li);
        };
    };
    // 递归函数
    function qtlShowDatas (arr,box1,currKns,currVal,i,name){
        if ($(arr[i-1]).text().trim().substring(0,3) == "QTL" && $(arr[i-1]).text().trim().split(":")[1].substring(0, 2) == box1.substring(0, 2)) {
            var span = " <span class='expreSigle qtlSigle " + currKns + "'data-name='"+name+"'data-value='" +currVal+"'>" + currVal + "</span><span class='deleteIconP deleteIconPqtl'>X</span>";
            $(arr[i-1]).next().after(span);
        } else {
            i--;
            if(i==0){
                var span = " <span class='expreSigle qtlSigle " +currKns +  "'data-name='"+name+"'data-value='"+currVal +"'>QTL:" +box1 + currVal+  "</span><span class='deleteIconP deleteIconPqtl'>X</span>";
                $("#expreDetail").append(span);
                return ;
            }else {

                qtlShowDatas(arr,box1,currKns,currVal,i,name);
            }
        }
    };
    // 存放qtl 相关信息
    function getQtlParams(){
        var list = $("#expreDetail span.qtlSigle");

        var arr = [];
        for(var i=0;i<list.length;i++){
            var obj = {};
            obj.qtlName = $(list[i]).attr("data-name");
            obj.value = $(list[i]).attr("data-value");
            arr.push(obj);
        }
        var newArr = [];
        deleteSame(arr,newArr);
    };
    // 递归函数
    function deleteSame(arr,newArr){
        var i=arr.length-1;
        if(i>0){
            if(arr[i].qtlName == arr[i-1].qtlName){
                arr[i-1].value += "," +arr[i].value ;
                arr.splice(i,1);
            }else {
                newArr.push(arr[i]);
                arr.splice(i,1);
            };
            deleteSame(arr,newArr);
        };
        globalObj.qtlParams = arr.concat(newArr);
    };

    // 清空上面的显示框
    $("#advanceSelect .showSelected .showClear").click(function (){
       //既然不让用闭包，那就简单粗暴一些吧
        $("#expreDetail").empty();
        // 1.清空基因表达量的相关数据
        var expreList = $("#expreKinds ul li");
        // 1.1 清空下拉框列表
        for(var i=0;i<expreList.length;i++){
            if($(expreList[i]).hasClass("noClick")){
                $(expreList[i]).removeClass("testClass noClick");
            }
        };
        // 1.2 清空保存的数据
        globalObj.SleExpreDatas = [];
        // 2.1  清空snp 所有选中的数据
        var snpList = $(".geneSnp .snpList ul li");
        for(var i=0;i<snpList.length;i++){
            if($(snpList[i]).hasClass("checked")){
                $(snpList[i]).removeClass("checked");
            }
        };
        // 2.2 清空所有保存的数据
        globalObj.SleSnpDatas = [];
        // 3.1 清空indel 所有选中的数据
        var  indelList = $(".geneIndel .indelList ul li");
        for(var i=0;i<indelList.length;i++){
            if($(indelList[i]).hasClass("checked")){
                $(indelList[i]).removeClass("checked");
            }
        };
        // 3.2 清空所有保存的数据
        globalObj.SleIndelDatas = [];
        // 4.1清空所有选中的qtl 数据
        var qtlList = $(".geneQtl .qtlSelectList ul li");
        for(var i=0;i<qtlList.length;i++){
            if($(qtlList[i]).hasClass("checked")){
                $(qtlList[i]).removeClass("checked");
            }
        };
        // 4.2 清空所有所存的数据
        if(dataParam){
            dataParam.firstHierarchyQtlId = [].concat.apply(nums);
            dataParam.qtlId = [];
        };
    });
    // 定义首字符大写的原型方法
    function firstUpperCase(str) {
        return str.toLowerCase().replace(/\b[a-z]/g,function(s){return s.toUpperCase();});
    }

    // 去掉空格的方法封装
    function deleteSpace (str){
        if(str.indexOf(" ") == -1){
            return str;
        }else {
            var arr = str.split(" ");
            var newStr = arr[0];
            for (var i=1;i<arr.length;i++){
                if(arr[i].indexOf("(")==-1){
                    newStr +=firstUpperCase(arr[i]);
                }
            };
            return newStr;
        }
    };

    // 高级搜索
    $("#iqgsSearch p").click(function (){
        flag = 1;
        var dataParam = getParams();
        advanceSearchFn(dataParam);
    });





// })