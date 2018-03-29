$(function () {
    var CurrentTab = "SNP";
   // $("#constructorPanel2").hide();
// 获取蘑菇染色体数据
    function getChrs(){
        $.ajax({
            type:"get",
            url:"/dna/dna/fetch-all-chromosome",
            contentType:"application/json",
            dataType:"json",
                success:function (res){
                console.log(res);
                for(var i=0;i<res.length;i++){
                    var str ="<option value='" +res[i].chromosome+ "' data-max='" + res[i].length+ "'>" + res[i].chromosome + "</option>"
                    $(".js-chorosome").append(str);
                }
            },
            error:function (err){
                console.log(err);
            }
        })
    }
    getChrs();
    function loadMask (el) {
        $(el).css({"position": "relative"});
        var _mask = $('<div class="ga-mask"><div>数据加载中...</div></div>');
        $(el).append(_mask);
    }

    function maskClose(el) {
        $(el).find(".ga-mask").remove();
    }

    function initPaginate() {
        laypage({
            cont: $('#snp-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            pages: 0, //通过后台拿到的总页数
            curr: 1, //当前页
            /*skin: '#5c8de5',*/
            skin: '#0f9145',
            skip: true,
            first: 1, //将首页显示为数字1,。若不显示，设置false即可
            last: 1, //将尾页显示为总页数。若不显示，设置false即可
            prev: '<',
            next: '>',
            groups: 3
        });
        laypage({
            cont: $('#indel-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            pages: 0, //通过后台拿到的总页数
            curr: 1, //当前页
            /*skin: '#5c8de5',*/
            skin: '#0f9145',
            skip: true,
            first: 1, //将首页显示为数字1,。若不显示，设置false即可
            last: 1, //将尾页显示为总页数。若不显示，设置false即可
            prev: '<',
            next: '>',
            groups: 3
        });
    }
    initPaginate();
    // 点击品种名，获取品种信息；
    function kindValParam (){
        var divs = $(".js-cursom-add").find("div.js-ad-dd");
        var paramK = {};
            paramK.infos = [];
        for (var i=0;i<divs.length;i++){
            if($(divs[i]).find("label").hasClass("cur")){
                var realK = $(divs[i]).find("div.label-txt").text().substring(0,6);
                // 用于存放每次点击品种/样品信息的对象
                var paramk1 = {};
                if(realK == "测序样品编号"){
                    paramk1.name = $(divs[i]).find("div.label-txt").text().substring(0);
                    paramk1.id =  Number($(divs[i]).find(".species-add").attr("data-index"));
                    if((paramk1.name).indexOf(",") ==-1){
                        paramk1.condition = {
                            cultivar:paramk1.name.substring(6)
                        }
                    }else {
                        var conds =paramk1.name.split(",");// 存放 condition 品种信息
                        var condName = [];
                        for (var i=0;i<conds.length;i++){
                            condName.push(conds[i].substring(6,conds[i].length));

                        };
                        paramk1.condition = {
                            cultivar:condName.join(",")
                        };
                    }
                    paramK.infos.push(paramk1)
                }else if(realK == "样品名"){
                    paramk1.name = $(divs[i]).find("div.label-txt").text().substring(0);
                    paramk1.id =  Number($(divs[i]).find(".species-add").attr("data-index"));
                    if((paramk1.name).indexOf(",") ==-1){
                        paramk1.condition = {
                            cultivar:"?" + paramk1.name.substring(3)
                        }
                    }else {
                        var conds =paramk1.name.split(",");// 存放 condition 品种信息
                        var condName = [];
                        for (var i=0;i<conds.length;i++){
                            condName.push(conds[i].substring(3,conds[i].length));
                        };
                        paramk1.condition = {
                            cultivar:condName.join(",")
                        };
                    }
                    paramK.infos.push(paramk1)
                }
            }
        };
        return paramK;
    }
    // 定义全局对象存放snp 位点点击需要的相关数据 -- 》 按范围查找
    var snpPintDatas={
        start:"",
        end:"",
        url:"",
        ctype:"all"
    }
    var snpGroup = {
        group:[]
    };
    // 定义全局对象存放snp 位点点击需要的相关数据 -- 》 按基因查找
    var snpPintDatasGene={
        upstream:"",
        downstream:"",
        url:"",
        ctype:"all"
    }
    var globelType;
    var globelGeneId;
    var globelTotalSnps = {
        snp:[],
        indel:[]
    };
    var filterEvent=0;

    // 获取所有snp数据
    function getQueryForTable(paramas,type,curr,fn){
        $.ajax({
            type:"post",
            data:JSON.stringify(paramas),
            url:"/dna/dna/queryForTable",
            contentType:"application/json;charset=UTF-8",
            dataType:"json",
            success:function(res){
                if(type=="SNP"){
                    renderSNPTable(res,curr,fn);
                }else{
                    renderINDELTable(res,curr)
                }



            },
            error:function (err){
                console.log(err);
            }
        })
    }

    var globFlag = 0;
    var isGeneFirst = 0;

    // 获取画图需要的基因结构和snp位点数据
    function getQueryForChat(params,parentCont,gid,type) {

        $.ajax({
            type:"post",
            data:JSON.stringify(params),
            url:"/dna/dna/fetch-point",
            contentType:"application/json;charset=UTF-8",
            dataType:"json",
            success:function(res){
                if(type=="SNP"){
                    globelTotalSnps.snp = res.snpList;
                }else {
                    globelTotalSnps.indel = res.snpList;
                };

                if (!res.geneInsideRegion && globFlag==0){
                    $("#GlyIds ul").empty();
                    $("#GlyIds").hide();
                }else {
                    isPop = 1;
                    isGeneFirst = 1;
                    filterEvent = res.geneInsideRegion;

                    if(filterEvent){
                            if ($("#GlyIds").is(":hidden")) {
                                $("#GlyIds").show();
                            }
                            var GlyList = res.geneInsideRegion;
                            var $ul = $("#GlyIds ul");
                            $ul.find("li").remove();
                            for (var i = 0; i < GlyList.length; i++) {
                                var $li = $("<li>" + GlyList[i] + "</li>");
                                $ul.append($li);
                            };

                            if (!$("#GlyIds li:first").hasClass("GlyColor")) {
                                var Glylis = $("#GlyIds li");
                                for (var i = 0; i < Glylis.length; i++) {
                                    if ($(Glylis[i]).hasClass("GlyColor")) {
                                        $(Glylis[i]).removeClass("GlyColor");
                                    }
                                }
                                $("#GlyIds li:first").addClass("GlyColor");
                            };
                    }
                };
                // gene 搜索添加gene
                var currSearchType = GetPanelParams.getPanelType();
                if(currSearchType == "gene"){
                    if(!$("#GlyIds").is(":hidden")){
                        $("#GlyIds ul").empty();
                        var li = "<li style='background:#0F9145;color:#fff;'>" +gloableSelectedGene +  "</li>";
                        $("#GlyIds ul").append(li);
                        // $("#GlyIds").hide();
                    }else {
                        $("#GlyIds").show();
                        $("#GlyIds ul").empty();
                        var li = "<li style='background:#0F9145;color:#fff;'>" +gloableSelectedGene +  "</li>";
                        $("#GlyIds ul").append(li);
                    }
                }
                    drawGeneConstructor(res,parentCont,gid,type);
                svgPanZoom("#"+ parentCont + " svg", {
                    zoomEnabled: true,
                    controlIconsEnabled: true
                });
            },
            error:function (err){
                console.log(err);
            }
        })
    }
    // 筛选面板 确认
    var gloableSelectedGene ;
    $(".js-panel-btn").click(function() {

        globFlag = 0;
        pageNumber = 1;
        pageSize = 10;
        gloableSnpSelectedNum = 10;
        $("#snp-paginate .lay-per-page-count-select").val(10);
        if(!$(".custom-groups-content").is(":hidden")){
            $(".custom-groups-content").hide();
        };
        if(!$(".cover").is(":hidden")){
            $(".cover").hide();
        }
        $(".tab-item li:first").trigger("click");
        var obj = getPanelParams();
        if(!obj){
            return;
        }
       var getKindSNames =  kindValParam();

        var totalGroups = JSON.parse(obj.params.group)
       // 去掉null 值
        for (var i=0;i<totalGroups.length;i++){
           if (!totalGroups[i]){
               totalGroups.splice(i,1);
           }
       };
       obj.params.group = totalGroups;
        console.log(obj);
        obj.params.type="SNP";
        obj.params.ctype="all";
        obj.params.pageNo = pageNumber;
        obj.params.pageSize = pageSize;
        if(obj.params.index){
            delete obj.params.index;
        }
        var currSearchType = GetPanelParams.getPanelType();
        gloableSelectedGene = obj.params.gene;
        if(currSearchType == "gene"){
            if(!$("#GlyIds").is(":hidden")){
                $("#GlyIds ul").empty();
                $("#GlyIds").hide();
            }
        }else {
            if($("#GlyIds").is(":hidden")){
                $("#GlyIds").show();
                $("#GlyIds ul").empty();
            }else{
                $("#GlyIds ul").empty();
            }
        }
        getQueryForTable(obj.params,"SNP");
        getQueryForChat(obj.params,"constructorPanel","snpid","SNP");
        renderTableHead();
        // 获取表格数据--ajax
         $(".page-tables").show();
          $(".page-circle").hide();
    });

    // 根据基因查询所有的snp位点信息
    // 删除当前显示的table tr
    function deleteTableColorSnp(){
        var snpTableList =$(".js-snp-table #tableBody tr");
        for(var i=0;i<snpTableList.length;i++){
            if($(snpTableList[i]).hasClass("tabTrColor")){
                $(snpTableList[i]).removeClass("tabTrColor");
            }
        }
    };
    function deleteTableColorIndel(){
        var indelTableList =$(".js-indel-table #tableBody2 tr");
        for(var i=0;i<indelTableList.length;i++){
            if($(indelTableList[i]).hasClass("tabTrColor")){
                $(indelTableList[i]).removeClass("tabTrColor");
            }
        }
    };
    // in region 每个基因ID的点击事件
    $("#GlyIds ul").on("click","li",function (e){
        if(CurrentTab == "SNP"){
            deleteTableColorSnp();
        }else {
            deleteTableColorIndel();
        }
        if(isGeneFirst != 1){
            var version = getUrlParam("version");
            var geneName =$(this).text();
            $(".js-gene-head-name").html(geneName);
            $(".js-gene-head-name").html(geneName);
            $("#geneIframe").attr("src", ctxRoot + "/dnagens/geneInfo?geneName=" + geneName + "&version=" + version);
            e.preventDefault();
            $(".genesInfo").show();

            var genesInfoIndex;
            genesInfoIndex =  layer.open({
                title:"",
                type: 1,
                content: $(".genesInfo"),
                area: ['980px','628px'],
                shadeClose:true,
                scrollbar:false,
                move: '.genesInfo-head',
                closeBtn: 0,
                offset:['135px', '320px'],
                moveOut: false
            });
            $(".genesInfo-head>a").click(function (){
                /* 关闭信息信息、弹框 */
                layer.close(genesInfoIndex);
            });
        }


        if(!$(this).hasClass("GlyColor")){
            var Glylis = $("#GlyIds li");
            for (var i=0;i<Glylis.length;i++){
                if($(Glylis[i]).hasClass("GlyColor")){
                    $(Glylis[i]).removeClass("GlyColor");
                }
            }
            $(this).addClass("GlyColor");
        };
        // 列表重新获取数据
        var clickVal = $(this).text();
        globelGeneId = clickVal;
        var obj = getPanelParams();
                obj.params.gene = clickVal;
                obj.params.group = JSON.parse(obj.params.group);
                globFlag = 1;
        //判断当前是根据snp查询还是根据indel查询
        var currType = $(".tab-item li.item-ac").text();
        if(currType.indexOf("SNPs")!=-1){
            obj.params.type = "SNP";
            getQueryForChat(obj.params,"constructorPanel","snpid","SNP");
        }else{
            obj.params.type = "INDEL";
            getQueryForChat(obj.params,"constructorPanel2","indelid","INDEL");
        }
    })
    // 生成搜索描述文字
    function renderSearchText() {
        var panelType = GetPanelParams.getPanelType();
        if(panelType == "region") {
            var params = GetPanelParams.getRegionParams();
            var desc = '染色体 ' + params.chromosome + ", 范围 " + params.start + "bp-" + params.end + "bp";
        } else {
            var params = GetPanelParams.getGeneParams();
            if(!params.downstream && !params.upstream) {
                var desc = '基因 ' + params.gene ;
            } else if(!params.upstream && params.downstream) {
                var desc = '基因 ' + params.gene + ", 范围 Downstream " + params.downstream + "bp";
            } else if(!params.downstream && params.upstream) {
                var desc = '基因 ' + params.gene + ", 范围 Upstream " + params.upstream + "bp";
            } else {
                var desc = '基因 ' + params.gene + ", 范围 Upstream " + params.upstream + "bp -Downstream " + params.downstream + "bp";
            }
        }
        $(".js-search-desc").html(desc);
    }
    // 获取面板上的参数
    function getPanelParams() {
        var panelType = GetPanelParams.getPanelType();
        var params;
        if(panelType == "gene") {
            params = GetPanelParams.getGeneParams();
        } else { // region
            params = GetPanelParams.getRegionParams();
        }
        if(typeof params == "object"){
            return {
                "params" :  params
            }
        }
        return false;
    }
    // 配置默认每页显示条数
    // 新接口 定义分页初始值
    var pageNumber = 1;
    var pageSize = 10;
    var snpOffset,indelOffset;

    var pageSizeSNP = 10;
    // 配置默认每页显示条数
    var pageSizeINDEL = 10;
    //去除snp 图中的选中位点
    function deleteSelectedSnp (){
        if(CurrentTab == "SNP"){
            var snppoints = $("#snpid").find("a");
            for(var i=0;i<snppoints.length;i++){
                if($(snppoints[i]).find("rect").attr("fill") == "#ff0000" && $(snppoints[i]).find("rect").attr("data-status")=="save1"){
                    var id = $(snppoints[i]).attr("href");
                    d3.select("#snpid").select("a[href='" +id+ "']").select("rect").attr("fill","#6b69d6");
                }else if($(snppoints[i]).find("rect").attr("fill") == "#ff0000" && $(snppoints[i]).find("rect").attr("data-status")=="snp1"){
                    var id = $(snppoints[i]).attr("href");
                    d3.select("#snpid").select("a[href='" +id+ "']").select("rect").attr("fill","#02ccb1");
                }
            };
        }else {
            var snppoints = $("#indelid").find("a");
            for(var i=0;i<snppoints.length;i++){
                if($(snppoints[i]).find("rect").attr("fill") == "#ff0000"&&$(snppoints[i]).find("rect").attr("data-status")=="save1"){
                    var id = $(snppoints[i]).attr("href");
                    d3.select("#indelid").select("a[href='" +id+ "']").select("rect").attr("fill","#6b69d6");
                }else if($(snppoints[i]).find("rect").attr("fill") == "#ff0000"&&$(snppoints[i]).find("rect").attr("data-status")=="indel1"){
                    var id = $(snppoints[i]).attr("href");
                    d3.select("#indelid").select("a[href='" +id+ "']").select("rect").attr("fill","#0ccdf1");
                }else if($(snppoints[i]).find("rect").attr("fill") == "#ff0000"&&$(snppoints[i]).find("rect").attr("data-status")=="indel2"){
                    var id = $(snppoints[i]).attr("href");
                    d3.select("#indelid").select("a[href='" +id+ "']").select("rect").attr("fill","#df39e0");
                }
            };
        };

    };

    $("#snp-paginate .select_item_page li").click(function(){
        pageSizeSNP = Number($(this).text());
        $("#snp-paginate .select_default_page").val(pageSizeSNP);
        var obj = getPanelParams();
        deleteSelectedSnp();
        obj.params.pageNo = pageNumber;
        obj.params.pageSize =  pageSizeSNP;
        obj.params.group = JSON.parse(obj.params.group);
        var panelType = GetPanelParams.getPanelType();
        if(panelType == "region" && obj.params.gene){
            delete obj.params.gene;
        }
        obj.params.type = "SNP";
        getQueryForTable(obj.params,"SNP",1);
    });
    $("#indel-paginate .select_item_page li").click(function(){
        pageSizeINDEL = Number($(this).text());
        $("#indel-paginate .select_default_page").val(pageSizeINDEL);
        var obj1 = getPanelParams();
        deleteSelectedSnp();

        obj1.params.pageNo = pageNumber;
        obj1.params.pageSize = pageSizeINDEL;
        obj1.params.group = JSON.parse(obj1.params.group);
        var panelType = GetPanelParams.getPanelType();
        if(panelType == "region" && obj1.params.gene){
            delete obj1.params.gene;
        }
        obj1.params.type = "INDEL";
        getQueryForTable(obj1.params,"INDEL",1);
    });
    //modified by zjt 2018-3-28


    // 分页跳转
    $(".js-snp-tab").on("focus", "#snp-paginate .laypage_skip", function() {
        $(this).addClass("isFocus");
    });
    $(".js-snp-tab").on("blur", "#snp-paginate .laypage_skip", function() {
        $(this).removeClass("isFocus");
    });
    $(".js-indel-tab").on("focus", "#indel-paginate .laypage_skip", function() {
        $(this).addClass("isFocus");
    });
    $(".js-indel-tab").on("blur", "#indel-paginate .laypage_skip", function() {
        $(this).removeClass("isFocus");
    });
    // 注册 enter 事件的元素
    document.onkeydown = function(e) {

        deleteSelectedSnp();
        var _page_skip = $('#snp-paginate .laypage_skip');
        var _page_skip2 = $('#indel-paginate .laypage_skip');
        if(e && e.keyCode==13){ // enter 键
            if( _page_skip.hasClass("isFocus") ) {
                var obj = getPanelParams();
                obj.params.group = JSON.parse(obj.params.group);
                var currNum1 = Number(_page_skip.val());
                    pageNumber = Number(_page_skip.val());
                var total1= Number($("#snp-paginate .total-page-count").text());
                var mathCeil=  Math.ceil(total1/pageSize);
                if(currNum1>mathCeil){
                    obj.params.pageNo = mathCeil
                    obj.params.pageSize = pageSize;
                    var panelType = GetPanelParams.getPanelType();
                    if(panelType == "region" && obj.params.gene){
                        delete obj.params.gene;
                    }
                    getQueryForTable(obj.params,"SNP",mathCeil);
                }else{
                    obj.params.pageNo = pageNumber = Number(_page_skip.val());
                    obj.params.pageSize = pageSize;
                    var panelType = GetPanelParams.getPanelType();
                    if(panelType == "region" && obj.params.gene){
                        delete obj.params.gene;
                    }
                    getQueryForTable(obj.params,"SNP",pageNumber);
                }
            }
            if(_page_skip2.hasClass("isFocus")) {
                var obj = getPanelParams();
                obj.params.group = JSON.parse(obj.params.group);
                var currNum2 = Number(_page_skip2.val());
                pageNumber = Number(_page_skip2.val());
                var total2= Number($("#indel-paginate .total-page-count").text());
                var mathCei2=  Math.ceil(total2/pageSize);
                if(currNum2>mathCei2){
                    obj.params.pageNo = mathCeil
                    obj.params.pageSize = pageSize;
                    var panelType = GetPanelParams.getPanelType();
                    if(panelType == "region" && obj.params.gene){
                        delete obj.params.gene;
                    }
                    getQueryForTable(obj.params,"INDEL",mathCeil);
                }else{
                    obj.params.pageNo = pageNumber;
                    obj.params.pageSize = pageSize;
                    var panelType = GetPanelParams.getPanelType();
                    if(panelType == "region" && obj.params.gene){
                        delete obj.params.gene;
                    }
                    getQueryForTable(obj.params,"INDEL",pageNumber);
                }
            }
        }
    }

    var SNPData = [];
    var currPageNumb=1;
    var Major_Or_Minor_SNP = "major";
    var INDELData = [];
    var indelDatas;
    var Major_Or_Minor_INDEL = "major";
    /* 格式化碱基 */
    function formatRef(str) {
        var len = str.length;
        if(len <= 15) {
            return "<div title='"+ str +"'>"+ str +"</div>";
        } else {
            var sub_str1 = str.substr(0, 15);
            var sub_str2 = str.substring(15, len);
            if(sub_str2.length <= 15) {
                return "<div title='"+ str + "'><div>" + sub_str1 + "</div>" + "<div>" + sub_str2 + "</div></div>"
            }
            return "<div title='"+ str + "'><div>" + sub_str1 + "</div>" + "<div>" + sub_str2.substr(0, 14) + " ...</div></div>"
        }
    }

    function formatPercent(n) {
        if(n * 1 == 1) {
            return "100%";
        } else if (n * 1 == 0) {
            return "0%";
        } else {
            return (n * 100).toFixed(2) + "%";
        }
    }

    function replaceStr(str){ // 正则法
        str = str.toLowerCase();
        var reg = /\b(\w)|\s(\w)/g; //  \b判断边界\s判断空格
        return str.replace(reg,function(m){
            return m.toUpperCase()
        });
    }

    function formatConseType(str) {
        if(str == "UTR5;UTR3") {
            return "5'UTR;3'UTR"
        } else if (str.indexOf(";") > -1) {
            var firstChar = replaceStr(str.split(";")[0]);
            var lastChar = replaceStr(str.split(";")[1]);
            return firstChar + ";" + lastChar;
        } else if (str == "UTR3") {
            return "3'UTR"
        } else if (str == "UTR5") {
            return "5'UTR"
        } else {
            if(replaceStr(str).indexOf("Snv") > -1) {
                return replaceStr(str).replace("Snv", "SNV");
            }
            return replaceStr(str);
        }
    }

    function replaceUnvalideChar(str) {
        return str.replace(/[\%,\/]/g,"_");
    }

    // snp tabl高亮显示
    function snpTableColor(){
        var trsList = $(".js-snp-table #tableBody tr");
        $(trsList[snpOffset]).addClass("tabTrColor");
    }
    function indelTableColor(){
        var trsList = $(".js-indel-table #tableBody2 tr");
        $(trsList[indelOffset]).addClass("tabTrColor");
    }

    // 生成SNPs表格
    function renderSNPTable(res,curr,fn) {
        // 无数据展示
        if ((res.data.length == 0)) {
            $("#tableBody").empty();
            $("#snp-paginate .ga-ctrl-footer").hide();
            $("#tableErrorShow").show();
            return;
        };

        if($("#snp-paginate .ga-ctrl-footer").is(":hidden")){
            $(".ga-ctrl-footer").show();
        }
        if(!$("#tableErrorShow").is(":hidden")){
            $("#tableErrorShow").hide();
        }
        var str = '';
        $.each(res.data, function (idx, item) {
            var ref = item.ref;
            var alt = item.alt;
            var rr = ref + ref;
            var aa = alt + alt;
            var ra = ref + alt;
            var RefAndRefPercent = parseFloat(item.geneType.RefAndRefPercent.toFixed(4));
            var totalAltAndAltPercent = parseFloat(item.geneType.totalAltAndAltPercent.toFixed(4));
            var totalRefAndAltPercent = parseFloat(item.geneType.totalRefAndAltPercent.toFixed(4));
            str += '<tr id="' + item.id + '" data-index="' + item.index+'" >'
            str += '    <td class="t_snpid" data-id="' + item.id + '" data-var="' + item.ref + '->' + item.alt + '" data-gene="' + item.gene + '" data-effect="' + item.effect + '">' + item.id + '</td>'
            str += '    <td class="t_consequenceType"><p class="js-tipes-show">' + formatConseType(item.consequencetype) + '</p></td>'
            str += '    <td class="t_snpchromosome"><p>' + item.chr + '</p></td>'
            str += '<td class="t_position"><p class="js-tipes-show">' + item.pos + '</p></td>'
            str += '<td class="t_snpreference"><p>' + formatRef(item.ref) + '</p></td>'
            str += '<td class="t_majorAllele"><p>' + formatRef(item.majorallen) + '</p></td>'
            str += '<td class="t_minorAllele"><p>' + formatRef(item.minorallen) + '</p></td>'

            str += '<td class="t_fmajorAllele"><p>' + formatPercent(item[Major_Or_Minor_SNP]) + '</p></td>'
            // var freq = item.freq.concat();
            var freq = item.freq;
            str += '<td class="t_genoType"><div><p>' + rr + " " + (RefAndRefPercent * 100).toFixed(2) + "%" + '</p><p style="width:' + RefAndRefPercent * 100 + 'px;"></p></div><div><p>' + aa + " " + (totalAltAndAltPercent * 100).toFixed(2) + "%" + '</p><p style="width:' + totalAltAndAltPercent * 100 + 'px;"></p></div><div><p>' + ra + " " + (totalRefAndAltPercent * 100).toFixed(2) + "%" + '</p><p style="width:' + totalRefAndAltPercent * 100 + 'px;"></p></div></td>'


            freq.reverse();
            $.each(freq, function (i, e) {
                str += '<td class="t_fmajorAllelein' + replaceUnvalideChar(e.name).split(",").join("_").replace(/\s/g, "") + '"><p>' + formatPercent(e[Major_Or_Minor_SNP]) + '</p></td>'
            });
            str += '</tr>';
            $("tr").data(item.id, item.geneType);
        });
            $(".js-snp-table>tbody").empty().append(str);
        laypage({
            cont: $('#snp-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            pages: Math.ceil(res.total / pageSizeSNP), //通过后台拿到的总页数
            curr: curr || 1, //当前页
            /*skin: '#5c8de5',*/
            skin: '#0f9145',
            skip: true,
            first: 1, //将首页显示为数字1,。若不显示，设置false即可
            last: Math.ceil(res.total / pageSizeSNP), //将尾页显示为总页数。若不显示，设置false即可
            prev: '<',
            next: '>',
            groups: 3, //连续显示分页数
            jump: function (obj, first) { //触发分页后的回调
                if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                    deleteSelectedSnp();
                    var tmp = getPanelParams();
                    var panelType = GetPanelParams.getPanelType();
                    if(panelType == "region" && tmp.params.gene){
                        delete tmp.params.gene;
                    }
                    tmp.params.group = JSON.parse(tmp.params.group);
                    tmp.params.pageNo = obj.curr;
                    tmp.params.pageSize = pageSizeSNP;
                    getQueryForTable(tmp.params,"SNP",obj.curr);
                }
            }
        });
        $("#snp-paginate .total-page-count span").html(res.total);
        gloableSnpSelectedNum = $("#snp-paginate .select_default_page").val();

        fn&&fn();
        var trsList = $(".js-snp-table #tableBody tr");
        for(var i=0;i<trsList.length;i++){
            if($(trsList[i]).hasClass("tabTrColor")){
                var tdGenotypes  = $(trsList[i]).find("td.t_genoType").find("div");
                for(var k=0;k<tdGenotypes.length;k++){
                    if(!$(tdGenotypes[k]).find("p:first").hasClass("addTrColor")){
                        $(tdGenotypes[k]).find("p:first").addClass("addTrColor");
                    }
                }
            }

        }
        TableHeaderSettingSnp();
    }

    // 生成INDELs表格
    function renderINDELTable(res,curr,fn) {
        if ((res.data.length == 0)) {
            $("#tableBody2").empty();
            $("#indel-paginate .ga-ctrl-footer").hide();
            $("#tableErrorShow2").show();
            return;
        };

        if($("#indel-paginate .ga-ctrl-footer").is(":hidden")){
            $("#indel-paginate .ga-ctrl-footer").show();
        }
        if(!$("#tableErrorShow2").is(":hidden")){
            $("#tableErrorShow2").hide();
        }
        var str = '';
        $.each(res.data, function(idx, item) {
            str += '<tr id="' +item.id + '" data-index="' + item.index+'" >'
            str += '    <td class="t_indels" data-id="'+ item.id +'" data-var="'+ item.ref + '->' + item.alt +'" data-gene="'+ item.gene +'" data-effect="'+ item.effect +'">'+ item.id +'</td>'
            str += '    <td class="t_iconsequenceType"><p class="js-tipes-show">'+ formatConseType(item.consequencetype) + '</p></td>'
            str += '    <td class="t_indelchromosome"><p>'+ item.chr +'</p></td>'
            str += '<td class="t_iposition"><p class="js-tipes-show">'+ item.pos +'</p></td>'
            str += '<td class="t_indelreference"><p>'+ formatRef(item.ref) +'</p></td>'
            str += '<td class="t_imajorAllele"><p>'+ formatRef(item.majorallen) +'</p></td>'
            str += '<td class="t_iminorAllele"><p>'+ formatRef(item.minorallen) +'</p></td>'

            str += '<td class="t_ifmajorAllele"><p>'+ formatPercent(item[Major_Or_Minor_INDEL]) +'</p></td>'
            var freq = item.freq.concat() ;
            freq.reverse();
            $.each(freq, function(i, e) {
                str += '<td class="t_fmajorAllelein'+ replaceUnvalideChar(e.name).split(",").join("_").replace(/\s/g,"") +'"><p>' + formatPercent(e[Major_Or_Minor_INDEL]) + '</p></td>'
            });
            str += '</tr>'
        });

        $(".js-indel-table>tbody").empty().append(str);
        laypage({
            cont: $('#indel-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            pages: Math.ceil(res.total / pageSizeINDEL), //通过后台拿到的总页数
            curr: curr || 1, //当前页
            /*skin: '#5c8de5',*/
            skin: '#0f9145',
            skip: true,
            first: 1, //将首页显示为数字1,。若不显示，设置false即可
            last: Math.ceil(res.total / pageSizeINDEL), //将尾页显示为总页数。若不显示，设置false即可
            prev: '<',
            next: '>',
            groups: 3, //连续显示分页数
            jump: function (obj, first) { //触发分页后的回调
                if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                    deleteSelectedSnp();
                    var tmp = getPanelParams();
                    var panelType = GetPanelParams.getPanelType();
                    if(panelType == "region" && tmp.params.gene){
                        delete tmp.params.gene;
                    }
                    tmp.params.group = JSON.parse(tmp.params.group);
                    tmp.params.pageNo = obj.curr;
                    tmp.params.pageSize = pageSizeINDEL;
                    getQueryForTable(tmp.params,"INDEL",obj.curr);
                }
            }
        });
        $("#indel-paginate .total-page-count span").html(res.total);
        fn&&fn();
        TableHeaderSettingIndel();
    }

    // 表头的 major 和 minor 切换
    $(".js-snp-table").on("change", ".f-ma", function () {
        Major_Or_Minor_SNP = $(this).val();
        $(".js-snp-table .f-ma").val(Major_Or_Minor_SNP);
        renderSNPTable(SNPData, Major_Or_Minor_SNP);
    });

    $(".js-indel-table").on("change", ".f-ma", function () {
        Major_Or_Minor_INDEL = $(this).val();
        $(".js-indel-table .f-ma").val(Major_Or_Minor_INDEL);
        renderINDELTable(INDELData, Major_Or_Minor_INDEL);
    });
    var tab=$(".js-table-header-setting-indel").find("label");
    for(var i=0;i<tab.length;i++){
        tab[i].onclick=function(){
        }
    }
    // 表头设置
    window.TableHeaderSettingSnp = function () {
        var headers = $(".js-table-header-setting-snp").find("label");
        $.each(headers, function(idx, item) {
            if(!$(item).hasClass("checkbox-ac")) {
                $(".js-snp-table").find(".t_"+$(item).attr("for")).hide();
            } else {
                $(".js-snp-table").find(".t_"+$(item).attr("for")).show();
            }
        });
    }
    window.TableHeaderSettingIndel = function () {
        var headers = $(".js-table-header-setting-indel").find("label");
        $.each(headers, function(idx, item) {
            if(!$(item).hasClass("checkbox-ac")) {
                $(".js-indel-table").find(".t_"+$(item).attr("for")).hide();
            } else {
                $(".js-indel-table").find(".t_"+$(item).attr("for")).show();
            }
        });
    }

    $(".js-snp-setting-btn").click(function() {
        TableHeaderSettingSnp();
    });

    $(".js-indel-setting-btn").click(function() {
        TableHeaderSettingIndel();
    });

    // consequence type 交互
    var CTypeSnp = 'all';
    $(".js-snp-table").on("click", ".consequence-type li", function() {


        $("#snp-paginate .lay-per-page-count-select option:first").prop("selected", 'selected');
        // 每次进行筛选都要snp 位点上的信息都去掉
        var snppoints = $("#snpid").find("a");
        for(var i=0;i<snppoints.length;i++){
            if($(snppoints[i]).find("rect").attr("fill") == "#ff0000"){
                var id = $(snppoints[i]).attr("href");
                d3.select("#snpid").select("a[href='" +id+ "']").select("rect").attr("fill","#6b69d6");
            }
        };
        snpPintDatas.ctype = $(this).text();
        $(".js-snp-table .consequence-type li").removeClass("active");
        $(this).addClass("active");

        var obj = getPanelParams();
        obj.params.group= JSON.parse(obj.params.group);
        obj.params.ctype = $(this).text();
        var panelType = GetPanelParams.getPanelType();
        if(panelType == "region" && obj.params.gene){
            delete obj.params.gene;
        }
        getQueryForTable(obj.params,"SNP",pageNumber);
    });

    var CTypeIndel = 'all';
    $(".js-indel-table").on("click", ".consequence-type li", function() {
        $("#indel-paginate .lay-per-page-count-select option:first").prop("selected", 'selected');
        // 每次进行筛选都要snp 位点上的信息都去掉
        var snppoints = $("#indelid").find("a");
        for(var i=0;i<snppoints.length;i++){
            if($(snppoints[i]).find("rect").attr("fill") == "#ff0000"){
                var id = $(snppoints[i]).attr("href");
                d3.select("#indelid").select("a[href='" +id+ "']").select("rect").attr("fill","#6b69d6");
            }
        };
        snpPintDatas.ctype = $(this).text();
        $(".js-indel-table .consequence-type li").removeClass("active");
        $(this).addClass("active");
        var obj = getPanelParams();
        obj.params.group= JSON.parse(obj.params.group);
        obj.params.ctype = $(this).text();
        getQueryForTable(obj.params,"INDEL",pageNumber);
    });

    function showtab() {
        var colArr = [];
        $(".table_header_setting span").each(function () {
            if (!$(this).parent().hasClass("checkbox-ac")) {
                $(".t_" + $(this).attr("data-value")).hide()
                var colName = $(this).attr("data-value");
                colArr.push(colName);
            } else {
                $(".t_" + $(this).attr("data-value")).show()
            }
        })
    }

    /*切换*/
    $(document).on("click",".che-list label",function(){
        $(".js-btn-default").removeClass("btn-default-ac")
        $(".table_header_setting").removeClass("js-r-ac")
        $(".js-choose-all").removeClass("js-choose-all-ac")
        if ($(this).hasClass("checkbox-ac")) {
            $(this).removeClass("checkbox-ac");
        } else {
            $(this).addClass("checkbox-ac");
        }
    })
    /*清空*/
    $("#indels-clear-all").click(function(){
        $("#table_header_setting").removeClass("js-r-ac");
        $(".js-choose-all").removeClass("js-choose-all-ac");
        $(".js-btn-default").removeClass("btn-default-ac");
        $(".che-list span").each(function(){
            $("#table_header_setting dd label").removeClass("checkbox-ac");
        })
    })

    /*全选*/
    $(".js-choose-all").click(function () {
        if($(this).hasClass("js-choose-all-ac")){
            $(this).removeClass("js-choose-all-ac");
            var _labels = $(this).parents(".choose-default").siblings(".checkbox-item").find(".table_header_setting label");
            $.each(_labels, function (index, item) {
                if($(this).hasClass("checkbox-ac")){
                    $(this).removeClass("checkbox-ac");
                }
            });


        }else{
            $(this).addClass("js-choose-all-ac")

            var _labels = $(this).parents(".choose-default").siblings(".checkbox-item").find(".table_header_setting label");
            $.each(_labels, function (index, item) {
                $(this).addClass("checkbox-ac");
            });
        }
        $(".js-default").removeClass("js-default-ac");
        $(".table_header_setting").removeClass("js-r-ac");

    })
    /* 默认勾选*/
    $(".js-default").click(function () {
        $(".table_header_setting label").addClass("checkbox-ac")
        $(".js-choose-all").removeClass("js-choose-all-ac")
        if ($(this).hasClass("js-default-ac")) {
            $(this).removeClass("js-default-ac")
            $(".table_header_setting").removeClass("js-r-ac")
        } else {
            $(this).addClass("js-default-ac");
            $(".table_header_setting").addClass("js-r-ac")
        }
    })
    /*收起*/
    $(".btn-toggle").click(function () {
        $(this).parents(".table-item").hide();
        $(this).parents(".table-item").siblings(".export-data").show();
        $(".tab-txt .export-data").show();
    });
    /*snp表格设置*/
    $(".snp-set-up").click(function () {
        $(".snp-checkbox").show();
        $(".tab-txt .export-data").hide();
    })
    /*indels表格设置*/
    $(".indels-set-up").click(function () {
        $(".indels-checkbox").show();
        $(".tab-txt .export-data").hide();
    })

    $(".js-clear-btn").click(function() {
        if($("#allSelected").hasClass("rightOk")){
            $("#allSelected").removeClass("rightOk").addClass("whiteOk");
        };

        var _labels = $(this).parents(".choose-default").siblings(".checkbox-item").find(".table_header_setting label");
        $(".js-choose-all").removeClass("js-choose-all-ac");
        $(".js-default").removeClass("js-default-ac");
        $.each(_labels, function (index, item) {
            $(this).removeClass("checkbox-ac");
        });
    });
    /*SNP INDEL 切换*/
    $(".item li").click(function () {
        if($(this).text().indexOf('SNP') > -1) {
            CurrentTab = "SNP";
            $(".page-num-tab-indel").hide();
            $(".page-num-tab-snp").show();
            if($(".snpTipE").is(":hidden")){
                $(".snpTipE").show();
            };

            if(!$(".indelTipE").is(":hidden")){
                $(".indelTipE").hide();
            };
        } else {
            if(!$(".snpTipE").is(":hidden")){
                $(".snpTipE").hide();
            };
            if($(".indelTipE").is(":hidden")){
                $(".indelTipE").show();
            };
            CurrentTab = "INDEL";
            $(".page-num-tab-snp").hide();
            $(".page-num-tab-indel").show();
        }
        $(".table-item").hide();
        $(".tab .export-data").show();
        $(this).addClass("item-ac").siblings().removeClass("item-ac ");
        $(".tab > div").eq($(this).index()).show().siblings().hide();
        if(CurrentTab == "SNP"){
            if( $("#constructorPanel").hasClass("hiddeCurr")){
                $("#constructorPanel").removeClass("hiddeCurr");
            }
            $("#constructorPanel2").addClass("hiddeCurr");
        }else if(CurrentTab == "INDEL"){
            if( $("#constructorPanel2").hasClass("hiddeCurr")){
                $("#constructorPanel2").removeClass("hiddeCurr")
                $("#constructorPanel").addClass("hiddeCurr");
            }
        }
    });
    // 定义全局查询总数
    var totalSnp;
    var totalIndel;
    /* 导出 */
    $(".js-export").click(function() {
        var fma = $(".t_fmajorAllele").find("option:selected").text();
        var panelType = GetPanelParams.getPanelType();
        if(panelType == "gene") {
            var _form = $("#exportGeneForm");
            var params = GetPanelParams.getGeneParams();
            // params.total = total;
            _form.find(".gene").val(params.gene);
            _form.find(".upstream").val(params.upstream);
            _form.find(".downstream").val(params.downstream);
            _form.find(".type").val(CurrentTab);
            // _form.find(".total").val(params.total);
            if(CurrentTab == "SNP") {
                _form.find(".total").val(totalSnp);
                _form.find(".ctype").val(CTypeSnp);
                var _labels = $(".js-table-header-setting-snp").find("label");
            } else {
                _form.find(".total").val(totalIndel);
                _form.find(".ctype").val(CTypeIndel);
                var _labels = $(".js-table-header-setting-indel").find("label");
            }
            var choiceArr = [];
            $.each(_labels, function(idx, item) {
                if($(item).hasClass("checkbox-ac")) {
                    choiceArr.push($(item).attr("data-col-name"));
                }
            });
            for (var i=0;i<choiceArr.length;i++){
                if(choiceArr[i] == "frequencyOfMajorAllele"){
                    var newFma = fma.split(" ");
                    var newFmas = "";
                    for (var j=0;j<newFma.length;j++){
                        newFmas+=newFma[j];
                    };
                    choiceArr[i] = newFmas;
                }
            }
            _form.find(".choices").val(choiceArr.join(","));
            _form.find(".group").val(snpPintDatasGene.group);
            _form.submit();
        } else {
            var _form = $("#exportRegionForm");
            if(isPop == 1){
                _form.find(".model").val("GENE");
                _form.find(".gene").val($("#GlyIds .GlyColor").text());
            }else {
                _form.find(".model").val("REGION");
            }
            var params = GetPanelParams.getRegionParams();
            _form.find(".chromosome").val(params.chromosome);
            _form.find(".start").val(params.start);
            _form.find(".end").val(params.end);
            _form.find(".type").val(CurrentTab);
            if(CurrentTab=="SNP") {
                _form.find(".total").val(totalSnp);
                _form.find(".ctype").val(CTypeSnp);
                var _labels = $(".js-table-header-setting-snp").find("label");
            } else {
                _form.find(".total").val(totalIndel);
                _form.find(".ctype").val(CTypeIndel);
                var _labels = $(".js-table-header-setting-indel").find("label");
            }
            var choiceArr = [];
            $.each(_labels, function(idx, item) {
                if($(item).hasClass("checkbox-ac")) {
                    choiceArr.push($(item).attr("data-col-name"));
                }
            });
            for (var i=0;i<choiceArr.length;i++){
                if(choiceArr[i] == "frequencyOfMajorAllele"){
                    var newFma = fma.split(" ");
                    var newFmas = "";
                    for (var j=0;j<newFma.length;j++){
                        newFmas+=newFma[j];
                    };
                    choiceArr[i] = newFmas;
                }
            }
            _form.find(".choices").val(choiceArr.join(","));
            _form.find(".group").val(snpGroup.group);
            _form.submit();
        }
    });

    // 点击每个snp位点重新获取数据
    function jumpPage (paramas,type,fn){

        $.ajax({
            type:"post",
            data:JSON.stringify(paramas),
            url:"/dna/dna/jump-page",
            contentType:"application/json;charset=UTF-8",
            dataType:"json",
            success:function(res){
                if(type=="SNP"){
                    snpOffset = res.offset;
                    renderSNPTable(res,res.pageNo,fn);
                }else{
                    indelOffset = res.offset;
                    renderINDELTable(res,res.pageNo,fn);
                }
            },
            error:function (err){
                console.log(err);
            }
        })
    }
    // 基因结构图
    function drawGeneConstructor(result,id,gsnpid,type){

        // 参考值
        var ttdistance;
        if(!result.structureList){
            var direction = -1;
        }else {
            var direction = result.structureList[0].strand;
        }
        var startPos = parseInt(result.upstream);
        var endPos =parseInt(result.downstream);
        var geneLength = endPos - startPos;
       d3.select("#" + id).selectAll("svg").remove();
       // 创建一个svg 元素
        var svgTotal = $("#" + id).width();
        var totalLength;
        if(geneLength >svgTotal*10){
            var svg = d3.select("#" +id).append("svg").attr("width",parseInt(geneLength/10) + "px").attr("height","250px");

            var acrossLineData = [[20,220],[Math.ceil(geneLength/10)+100,220]];
            var topLineData = [[20,1],[Math.ceil(geneLength/10)+100,1]];
            var centerLineData = [[20,90],[Math.ceil(geneLength/10)+100,90]]
            totalLength = Math.ceil(geneLength/10)+100;
        }else {
            var svg = d3.select("#" + id).append("svg").attr("width",svgTotal + "px").attr("height","250px");
            var acrossLineData = [[20,220],[svgTotal+133,220]];
            var topLineData = [[20,1],[svgTotal+133,1]];
            var centerLineData = [[20,90],[svgTotal+133,90]]
            totalLength = svgTotal+133;

        }
        // 创建一个直线生成器
        var line = d3.line()
            .x(function (d){return d[0]})
            .y(function (d){return d[1]})
        // 起始竖线
        var verticalLineData = [[20,0],[20,220]];
        // 画方向箭头 向左
        var dirArrowsLeft  =  [[60,60],[40,72],[60,84],[50,72]];
        // 画方向箭头 向右
        var dirArrowsRight = [[totalLength-30,60],[totalLength-10,72],[totalLength-29,84],[totalLength-20,72]];
        var intervalLineData = [];
        var svgLength = $("#" + id).find("svg").width();
        // to do
        if (svgLength >885){
            var intervalNums = Math.ceil(svgLength/100);
            // 每份的长度
            ttdistance = parseInt(svgLength/intervalNums);

            // ttdistance =100;
        }else {
            // 如果svg 长度小于容器长度，则默认分为10份
            // var intervalNums = Math.floor(svgLength/100);
            var intervalNums = 10;
            // 每份的长度
            // ttdistance = svgLength/intervalNums;
            ttdistance = parseInt(geneLength/100);
        }
        for (var i=0;i<intervalNums;i++){
            var intervalElement1 = [];
            var intervalElement2 = [];
            var faultElement = [];
                intervalElement1[0] = parseInt(i*100 + 20);
                // y轴的值不能设置为0 *****
                intervalElement1[1] =1;
            intervalElement2[0] =  parseInt(i*100 + 20);
            intervalElement2[1] = 219;
            faultElement[0] = -1;
            faultElement[1] = -1;
            intervalLineData.push(intervalElement1);
            intervalLineData.push(intervalElement2);
            intervalLineData.push(faultElement);
                if(svgLength>885){
                    svg.append("text").text(parseInt(startPos+ i*ttdistance*10)).attr("fontSize","30px").attr("x",intervalElement1[0]).attr("y",245).attr("text-anchor","middle");
                }
                else if(svgLength<=885&&geneLength>100){
                    svg.append("text").text(parseInt(startPos+ i*ttdistance*10)).attr("fontSize","30px").attr("x",intervalElement1[0]).attr("y",245).attr("text-anchor","middle");
                }else{
                    svg.append("text").text(parseInt(startPos+ i*ttdistance*10)).attr("fontSize","30px").attr("x",intervalElement1[0]).attr("y",245).attr("text-anchor","middle");
                }
        }
        // 利用defined 把一条路径切割成一段一段的多条路径
            var line2 = line.defined(function(d, i, index) {
                //   在返回值为false的位置进行切割，并且当前数据不再计入到路径中
                return d[0] > 0 && d[1] > 0;
            })(intervalLineData);
            // 利用直线生成器生成相应的直线
            svg.append("path").attr("stroke","#6E6E6E").attr("stroke-width","3").attr("d",line(acrossLineData));
            svg.append("path").attr("stroke","#E1E1E1").attr("stroke-width","2").attr("d",line(topLineData));
            svg.append("path").attr("stroke","#666666").attr("stroke-width","2").attr("d",line(centerLineData)).attr("id","centerLine");

            svg.append("path").attr("stroke","#E1E1E1").attr("stroke-width","2").attr("d",line2);
            svg.append("path").attr("stroke","#ff0000").attr("stroke-width","3").attr("d",line(verticalLineData));


            // 画基因结构图
            var topY = 70;   // 基因结构图距离上边距离
            var rectHeight = 20;   // 基因结构图高度
            var leftMargin = 60;
            var snpWidth = 5;
            // var g = svg.append("g").attr("transform","translate(" +leftMargin + ",10)");
            var g = svg.append("g").attr("transform","translate(20,10)");
            var g1 = svg.append("g").attr("transform","translate(20,30)").attr("id",gsnpid);  //?问题点
            var geneConstructs = result.structureList;
            var snpLocalPoints = result.snpList;
            var snpColor = "#6b69d6";
            // 根据染色体不同绘制不同的颜色
            function chromoColor (str){
                if(str == "three_prime_UTR"){
                    return "#ffb902";
                }else if(str == "CDS"){
                    return "#0099bb";
                }else if(str == "five_prime_UTR"){
                    return "#f76919";
                }
            }
            // 基因结构
                if(geneConstructs){
                    for (var i=0;i<geneConstructs.length;i++){
                        var feature = geneConstructs[i].feature;
                        var colorVal = chromoColor(feature);
                        if(geneLength<8850){
                            var scale = geneLength/885;
                            g.append("rect").attr("x",(geneConstructs[i].start-startPos)/scale).attr("y",topY).attr("width",(geneConstructs[i].end - geneConstructs[i].start)/scale).attr("height",rectHeight).attr("fill",colorVal);
                        }else {
                            g.append("rect").attr("x",(geneConstructs[i].start-startPos)/10).attr("y",topY).attr("width",(geneConstructs[i].end - geneConstructs[i].start)/10).attr("height",rectHeight).attr("fill",colorVal);
                        }
                    };
                }
        // 方向箭头
        if(direction == "-"){
            svg.append("path").attr("stroke","#B3B3B3").attr('stroke-width', '2').attr("fill","#B3B3B3").attr("d",line(dirArrowsLeft)).attr("transform","translate(-10,18)").attr("id","arrows");
        }else if(direction == "+"){
            svg.append("path").attr("stroke","#B3B3B3").attr('stroke-width', '2').attr("fill","#B3B3B3").attr("d",line(dirArrowsRight)).attr("transform","translate(0,18)").attr("id","arrows");
        }

            // 画snp 位点
                    var newArr = [];
                    if(geneLength<8850){
                            var scale = geneLength/885;
                            for(var i=0;i<snpLocalPoints.length;i++){
                                var obj = {x:0,y:0,id:""};
                                    obj.x = (snpLocalPoints[i].pos - startPos)/scale;
                                obj.y = 90;
                                obj.index = snpLocalPoints[i].index;
                                obj.consequencetypeColor = snpLocalPoints[i].consequencetypeColor;
                                newArr.push(obj);
                            }
                    }else {
                        for(var i=0;i<snpLocalPoints.length;i++){
                            var obj = {x:0,y:0,id:""};
                                obj.x = (snpLocalPoints[i].pos - startPos)/10;
                            obj.y = 90;
                            // obj.id = snpLocalPoints[i].id;
                            obj.index = snpLocalPoints[i].index;
                            obj.consequencetypeColor = snpLocalPoints[i].consequencetypeColor;

                            newArr.push(obj);
                        }
                    }
                    var globalY = 10;
                    function loop(arr) {
                        if(arr.length<=0) return;
                        var temp = [];
                        for (var j = 0; j < arr.length; j++) {
                            for (var k = 0; k < arr.length; k++) {
                                if (k === j) continue;
                                if (Math.abs(arr[j].x - arr[k].x) < 15 ) {
                                    arr[j].y +=globalY;
                                    temp.push(arr[j]);
                                    arr.splice(j,1);
                                    break;
                                    j--;
                                }
                            };
                        }
                        for (var m=0;m<arr.length;m++){
                            var a = g1.append("a").attr("href","#" +arr[m].index);
                            // var a = g1.append("a");
                            if(arr[m].consequencetypeColor == 1){
                                a.append("rect").attr("x",arr[m].x).attr("y",arr[m].y).attr("width",snpWidth).attr("height",snpWidth).attr("fill","#02ccb1").attr("data-index",arr[m].index).attr("data-status","snp1");
                              continue;
                            }else if(arr[m].consequencetypeColor == 2){
                                a.append("rect").attr("x",arr[m].x).attr("y",arr[m].y).attr("width",snpWidth).attr("height",snpWidth).attr("fill","#0ccdf1").attr("data-index",arr[m].index).attr("data-status","indel1");;
                                continue;
                            }else if (arr[m].consequencetypeColor == 3){
                                a.append("rect").attr("x",arr[m].x).attr("y",arr[m].y).attr("width",snpWidth).attr("height",snpWidth).attr("fill","#df39e0").attr("data-index",arr[m].index).attr("data-status","indel2");
                                continue;
                            };
                              a.append("rect").attr("x",arr[m].x).attr("y",arr[m].y).attr("width",snpWidth).attr("height",snpWidth).attr("fill",snpColor).attr("data-index",arr[m].index).attr("data-status","save1");;
                        }
                        loop(temp)
                    }
                    loop(newArr);

        //每个snp位点的点击事件
            $("#" + gsnpid + " a rect").click(function (e){
                var obj = getPanelParams();
                // pageSize = 10;
                var index = Number($(e.target).attr("data-index"));
                obj.params.group = JSON.parse(obj.params.group);
                obj.params.index = index;
                obj.params.pageSize = pageSize;
                delete obj.params.pageNo;
                var panelType = GetPanelParams.getPanelType();
                    if(panelType == "region" && obj.params.gene){
                        delete obj.params.gene;
                    }
                if(type == "SNP"){

                    jumpPage(obj.params,type,snpTableColor);
                }else {
                    jumpPage(obj.params,type,indelTableColor);

                }


                var idIndex = $(this).parent().attr("href").substring(1);
                var snps = result.snpList;
                if(type=="SNP"){
                    var list = $("#snpid").find("a");
                    for (var i=0;i<snps.length;i++){
                        if(snps[i].index == idIndex){
                            d3.select("#snpid").select("a[href='#" +idIndex+ "']").select("rect").attr("fill","#ff0000");
                        }else if(snps[i].consequencetypeColor ==1){
                            d3.select("#snpid").select("a[href='#" +snps[i].index+ "']").select("rect").attr("fill"," #02ccb1");
                        }else {
                            d3.select("#snpid").select("a[href='#" +snps[i].index+ "']").select("rect").attr("fill","#6b69d6");
                        }
                    };
                }else if(type="INDEL"){
                    for (var i=0;i<snps.length;i++){
                        if(snps[i].index == idIndex){
                            d3.select("#indelid").select("a[href='#" +idIndex+ "']").select("rect").attr("fill","#ff0000");
                        }
                        else if(snps[i].consequencetypeColor ==2){
                            d3.select("#indelid").select("a[href='#" +snps[i].index+ "']").select("rect").attr("fill","#0ccdf1");
                        }  else if(snps[i].consequencetypeColor ==3){
                            d3.select("#indelid").select("a[href='#" +snps[i].index+ "']").select("rect").attr("fill","#df39e0");
                        }  else {
                            d3.select("#indelid").select("a[href='#" +snps[i].index+ "']").select("rect").attr("fill","#6b69d6");
                        }
                    };
                }
        })
    }
    // table 表格中的tr 点击跳转
    $("#tableBody").on("click","tr>td.t_snpid,tr>td.t_genoType",function (e){
           var id = $(this).parent().attr("id");
           var chr = $(this).parent().find("td.t_snpchromosome").text();
           var reference = $(this).parent().find("td.t_snpreference").text();
           var minorAllele = $(this).parent().find("td.t_minorAllele").find("div").text();
           var consquence = $(this).parent().find("td.t_consequenceType").find("p").text();
           var position = $(this).parent().find("td.t_position").find("p").text();
           var majorAllele = $(this).parent().find("td.t_majorAllele").find("div").text();
           var frequence = $(this).parent().find("td.t_fmajorAllele").find("p").text();
           var clickType = "snp";
          window.open(ctxRoot + "/dna/snp/info?snpId=" + id);
    });
    // 去除所有的其他的tr(非当前选中的)的颜色行
    function deleteTrsColor(){
        var trs = $("#tableBody>tr");
        for(var i=0;i<trs.length;i++){
            var tdGenotypes  = $(trs[i]).find("td.t_genoType").find("div");
            for(var k=0;k<tdGenotypes.length;k++){
                if($(tdGenotypes[k]).find("p:first").hasClass("addTrColor")){
                    $(tdGenotypes[k]).find("p:first").removeClass("addTrColor");
                }
            }
        }
    };
    // 根据表格去锚点图上的snp 位点  --snp
    $("#tableBody").on("click","tr>td:not('.t_snpid'),tr>td:not('.t_genoType')",function (e){
        deleteSelectedSnp();
        var id = Number($(this).parent().attr("data-index"));
        var idAttr = $(this).parent().attr("id");
        var snps = globelTotalSnps.snp;
        var list = $("#snpid").find("a");
        for (var i=0;i<snps.length;i++){
            if(snps[i].index == id){
                d3.select("#snpid").select("a[href='#" +id+ "']").select("rect").attr("fill","#ff0000");
            }else if(snps[i].consequencetypeColor ==1){
                d3.select("#snpid").select("a[href='#" +snps[i].id+ "']").select("rect").attr("fill"," #02ccb1");
            }else {
                d3.select("#snpid").select("a[href='#" +snps[i].id+ "']").select("rect").attr("fill","#6b69d6");
            }
        };
        deleteTrsColor();
        // 让当前高亮显示，同时其他行的高亮都消失
        var trs = $("#tableBody").find("tr");
        for(var i=0;i<trs.length;i++){
            if($(trs[i]).hasClass("tabTrColor")){
                $(trs[i]).removeClass("tabTrColor");

                if( i%2 == 0){
                    $(trs[i]).find("td:last-child>div>p:first-child").css("background","#fff");
                }else{
                    $(trs[i]).find("td:last-child>div>p:first-child").css("background","#F5F8FF");
                }
            }
        };
        $(this).parent().addClass("tabTrColor");
        var pps = $("#" + idAttr).find("td.t_genoType div");
        for (var i=0;i<pps.length;i++){
            // $(pps[i]).find("p:first").css("background","#0F9145");
            $(pps[i]).find("p:first").addClass("addTrColor");
        }

    });
    // 根据表格去锚点图上的snp 位点 -- indel
    $("#tableBody2").on("click","tr>td:not('.t_indels')",function (e){
        deleteSelectedSnp();
        var id = Number($(this).parent().attr("data-index"));
        var snps = globelTotalSnps.indel;
        var list = $("#indelid").find("a");
        for (var i=0;i<snps.length;i++){
            if(snps[i].index == id){
                d3.select("#indelid").select("a[href='#" +id+ "']").select("rect").attr("fill","#ff0000");
            }
            else if(snps[i].consequencetypeColor ==2){
                d3.select("#indelid").select("a[href='#" +snps[i].id+ "']").select("rect").attr("fill","#0ccdf1");
            }  else if(snps[i].consequencetypeColor ==3){
                d3.select("#indelid").select("a[href='#" +snps[i].id+ "']").select("rect").attr("fill","#df39e0");
            }  else {
                d3.select("#indelid").select("a[href='#" +snps[i].id+ "']").select("rect").attr("fill","#6b69d6");
            }
        };
        // 让当前高亮显示，同时其他行的高亮都消失
        var trs = $("#tableBody2").find("tr");
        for(var i=0;i<trs.length;i++){
            if($(trs[i]).hasClass("tabTrColor")){
                $(trs[i]).removeClass("tabTrColor");

                if( i%2 == 0){
                    $(trs[i]).find("td:last-child>div>p:first-child").css("background","#fff");
                }else{
                    $(trs[i]).find("td:last-child>div>p:first-child").css("background","#F5F8FF");
                }
            }
        };
        $(this).parent().addClass("tabTrColor");
        var pps = $("#" + id).find("td.t_genoType div");
        for (var i=0;i<pps.length;i++){
            $(pps[i]).find("p:first").css("background","#5D8CE6");
        }
    });

    $("#tableBody2").on("click","tr>td.t_indels",function (e){
        var id = $(this).parent().attr("id");
        var chr = $(this).parent().find("td.t_snpchromosome").text();
        var reference = $(this).parent().find("td.t_snpreference").text();
        var minorAllele = $(this).parent().find("td.t_minorAllele").find("div").text();
        var consquence = $(this).parent().find("td.t_consequenceType").find("p").text();
        var position = $(this).parent().find("td.t_position").find("p").text();
        var majorAllele = $(this).parent().find("td.t_majorAllele").find("div").text();
        var frequence = $(this).parent().find("td.t_fmajorAllele").find("p").text();
        var clickType = "ind";
        window.open(ctxRoot + "/dna/snp/info?snpId="+id);
    });

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    };
    // table thead 每次点击的时候select 的时候清空snp 红色小点
        $("#mask-test table thead").on("change","td.t_fmajorAllele",function (){
            deleteSelectedSnp();
        });
    $("#mask-test2 table thead").on("change","td.t_ifmajorAllele",function (){
        deleteSelectedSnp();
    })
    var gloableSnpSelectedNum = 10;
        // SNP 按钮的点击事件

        $(".tab-item .geneSnps").click(function(){
            $("#snp-paginate .select_default_page").val(Number(gloableSnpSelectedNum));

        })
    // add by jarry at 3-23
    $(".tab-item .geneIndels").click(function (){
        pageNumber = 1;
        pageSizeINDEL = 10;
        $("#indel-paginate .lay-per-page-count-select").val(10)  ;
        var obj = getPanelParams();
        obj.params.type="INDEL";
        obj.params.group = JSON.parse(obj.params.group);
        obj.params.ctype = "all";
        globFlag = 1;
        obj.params.pageSize = pageSizeINDEL;
        var panelType = GetPanelParams.getPanelType();
        if(panelType == "region" && obj.params.gene){
            delete obj.params.gene;
        }
        if(obj.params.index){
            delete obj.params.index;
        }
        getQueryForChat(obj.params,"constructorPanel2","indelid","IDNEL");
        getQueryForTable(obj.params,"INDEL",1);
    })

})
