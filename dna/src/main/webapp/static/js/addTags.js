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
        getData(data);
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
            data.pageNum = 1;
            // 默认回到第一页，
        var pageSizeP = $("#page").find("p")

        for(var i=0;i<pageSizeP.length;i++){
            if($(pageSizeP[i]).hasClass("pageColor")){
                $(pageSizeP[i]).removeClass("pageColor")
            }
        };
        $(pageSizeP[0]).addClass("pageColor");
        getData(data,resetSaveStatus);

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
            var ki = {name:selContents,id:new Date().getTime()};

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
        var currentStatus = $(this).prop("checked");
        var selectedName =  $(this).parent().next().text();
        if (!selectedName){
            selectedName = $(this).parent().siblings().filter(".sampleNameT").text();
        }
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
            group:"",
            pageSize:paramData.pageSize,
            pageNum:paramData.pageNum,
            isPage:1
        };
        return datas;
    }

    // 选中品种按钮点击获取数据
    $("#kindSelect").click(function (){
        var data = getParamas();
       getData(data);
    });

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
    function getData(data,fn){
        $.ajax({
            type:"GET",
            url:CTXROOT + "/dna/condition",
            data:data,
            success:function (result) {
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
                        var hilumColorTV  = totalDatas[i].hilumColor==null?"":totalDatas[i].hilumColor==null;  //种脐色
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
                    pageStyle(nums,intNums);
                    $("#totals").text(count);
                    fn && fn();
                }
            },
            error:function (error){
                console.log(error);
            }
        })
    }
    // 每个group的点击事件
    $(".popNames li").click(function (){
        var liVal = $(this).text();
        var data = getParamas();
        data.group = liVal;
        getData(data);
    })
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
        var selectedDatas = getParamas();
        selectedDatas.pageNum = paramData.pageNum;
        selectedDatas.pageSize = paramData.pageSize;
        getData(selectedDatas,resetSaveStatus);
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
        var selectedDatas = getParamas();
        selectedDatas.pageNum = paramData.pageNum;
        selectedDatas.pageSize = paramData.pageSize;
        getData(selectedDatas);
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
            var selectedDatas = getParamas();
            selectedDatas.pageNum = paramData.pageNum;
            selectedDatas.pageSize = paramData.pageSize;
            getData(selectedDatas);
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
    // 分页 end
    if(initKindVal){
        var initKindVals = initKindVal.name;
        for (var i=0;i<initKindVals.length;i++){
            var div = "<div class='js-ad-dd'><label class='species-add' data-index=" + initKindVals[i].id + ">" + "<span></span><div class='label-txt'>" + initKindVals[i].name + "</div></label><i class='js-del-dd'>X</i></div>"
            $(".js-cursom-add").append(div);
        }
    }


})