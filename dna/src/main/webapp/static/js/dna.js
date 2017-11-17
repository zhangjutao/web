$(function () {
    var CurrentTab = "SNP";

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
            skin: '#5c8de5',
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
            skin: '#5c8de5',
            skip: true,
            first: 1, //将首页显示为数字1,。若不显示，设置false即可
            last: 1, //将尾页显示为总页数。若不显示，设置false即可
            prev: '<',
            next: '>',
            groups: 3
        });
    }
    initPaginate();

    // 筛选面板 确认
    $(".js-panel-btn").click(function() {
        $(".page-tables").show();
        $(".page-circle").hide();
        var obj = getPanelParams();
        if(typeof obj == "object") {
            CTypeSnp = "all";
            CTypeIndel = "all";
            requestForSnpData(1, obj.url, obj.params);
            requestForIndelData(1, obj.url, obj.params);
            renderSearchText();
            renderTableHead();
        }
    });

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
        var params, url;
        if(panelType == "gene") {
            params = GetPanelParams.getGeneParams();
            url = CTXROOT + "/dna/searchSNPinGene";
        } else { // region
            params = GetPanelParams.getRegionParams();
            url = CTXROOT + "/dna/searchSNPinRegion";
        }
        if(typeof params == "object"){
            return {
                "params" :  params,
                "url" : url
            }
        }
        return false;
    }

    // 配置默认每页显示条数
    var pageSizeSNP = 10;
    $("#snp-paginate .lay-per-page-count-select").val(pageSizeSNP);

    // 配置默认每页显示条数
    var pageSizeINDEL = 10;
    $("#indel-paginate .lay-per-page-count-select").val(pageSizeINDEL);

    // 修改每页显示条数
    $(".js-snp-tab").on("change", ".lay-per-page-count-select", function() {
        pageSizeSNP = $(this).val();
        var obj = getPanelParams();
        requestForSnpData(1, obj.url, obj.params);
    });

    $(".js-indel-tab").on("change", ".lay-per-page-count-select", function() {
        pageSizeINDEL = $(this).val();
        var obj = getPanelParams();
        requestForIndelData(1, obj.url, obj.params);
    });

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
        var _page_skip = $('#snp-paginate .laypage_skip');
        var _page_skip2 = $('#indel-paginate .laypage_skip');
        if(e && e.keyCode==13){ // enter 键
            if( _page_skip.hasClass("isFocus") ) {
                if(_page_skip.val() * 1 > Math.ceil(($(".total-page-count-snp").text()*1)/pageSizeSNP)) {
                    return alert("输入页码不能大于总页数");
                }
                var obj = getPanelParams();
                requestForSnpData(_page_skip.val() * 1, obj.url, obj.params);
            }
            if(_page_skip2.hasClass("isFocus")) {
                if(_page_skip2.val() * 1 > Math.ceil(($(".total-page-count-indel").text() *1)/pageSizeINDEL)) {
                    return alert("输入页码不能大于总页数");
                }
                var obj = getPanelParams();
                requestForIndelData(_page_skip2.val() * 1, obj.url, obj.params);
            }
        }
    }

    var SNPData = [];
    var Major_Or_Minor_SNP = "major";
    // 请求数据并分页 -- SNP
    function requestForSnpData(curr, url, params) {
        params['pageNo'] = curr || 1;
        params['pageSize'] = pageSizeSNP;
        params['type'] = 'SNP';
        params['ctype'] = CTypeSnp;

        loadMask ("#mask-test");
        $.ajax({
            url: url,
            data: params,
            type: "POST",
            dataType: "json",
            success: function(res) {
                console.log(res);
                drawGeneConstructor(res);
                maskClose("#mask-test");
                SNPData = res.data;
                if(res.data.length > 0) {
                    renderSNPTable(SNPData, Major_Or_Minor_SNP);
                } else {
                    //alert("无数据");
                    $(".js-snp-table>tbody").empty().append("<span>无数据</span>");
                }

                laypage({
                    cont: $('#snp-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(res.total / pageSizeSNP), //通过后台拿到的总页数
                    curr: curr || 1, //当前页
                    skin: '#5c8de5',
                    skip: true,
                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                    last: Math.ceil(res.total / pageSizeSNP), //将尾页显示为总页数。若不显示，设置false即可
                    prev: '<',
                    next: '>',
                    groups: 3, //连续显示分页数
                    jump: function (obj, first) { //触发分页后的回调
                        if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                            var tmp = getPanelParams();
                            requestForSnpData(obj.curr, tmp.url, tmp.params);
                        }
                    }
                });
                $(".total-page-count-snp").html(res.total);

                /*鼠标悬浮在SNP ID上显示SNP信息*/
                $(".js-snp-table tbody .t_snpid").hover(function () {
                    var self = this;
                    var snpid = $(this).attr("data-id");
                    var va = $(this).attr("data-var");
                    var gene = $(this).attr("data-gene");
                    var effect = $(this).attr("data-effect");
                    var content = "";
                        content += "<div class='snpid-td'>"
                        content += "<p><span>SNP ID:</span>" + snpid + "</p>",
                        content += "<p><span>Var:</span>" + va + "</p>",
                        content += "<p><span>Gene:</span>" + gene + "</p>",
                        content += "<p><span>Effect/Location:</span>" + effect + "</p>",
                        content += "</div>"
                    $.pt({
                        target: self,
                        position: 'b',
                        autoClose: false,
                        content: content
                    });
                    //$(".pt").css("left", $(".pt").position().left);
                },function () {
                    $(".pt").remove();
                });
            }
        });
    }

    var INDELData = [];
    var Major_Or_Minor_INDEL = "major";
    // 请求数据并分页 -- INDEL
    function requestForIndelData(curr, url, params) {
        params['pageNo'] = curr || 1;
        params['pageSize'] = pageSizeINDEL;
        params['type'] = 'INDEL';
        params['ctype'] = CTypeIndel;

        loadMask ("#mask-test2");

        $.ajax({
            url: url,
            data: params,
            type: "POST",
            dataType: "json",
            success: function(res) {

                console.log(res)
                maskClose("#mask-test2");
                INDELData = res.data;
                if(res.data.length > 0) {
                    renderINDELTable(INDELData, Major_Or_Minor_INDEL);
                } else {
                    //alert("无数据");
                    $(".js-indel-table>tbody").empty().append("<span>无数据</span>");
                }

                laypage({
                    cont: $('#indel-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(res.total / pageSizeINDEL), //通过后台拿到的总页数
                    curr: curr || 1, //当前页
                    skin: '#5c8de5',
                    skip: true,
                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                    last: Math.ceil(res.total / pageSizeINDEL), //将尾页显示为总页数。若不显示，设置false即可
                    prev: '<',
                    next: '>',
                    groups: 3, //连续显示分页数
                    jump: function (obj, first) { //触发分页后的回调
                        if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                            var tmp = getPanelParams();
                            requestForIndelData(obj.curr, tmp.url, tmp.params);
                        }
                    }
                });
                $(".total-page-count-indel").html(res.total);

                /*鼠标悬浮在INDEL ID上显示INDEL信息*/
                $(".js-indel-table tbody .t_indels").hover(function () {
                    var self = this;
                    var indelid = $(this).attr("data-id");
                    var va = $(this).attr("data-var");
                    var gene = $(this).attr("data-gene");
                    var effect = $(this).attr("data-effect");
                    var content = "";
                        content += "<div class='snpid-td'>"
                        content += "<p><span>INDEL ID:</span>" + indelid + "</p>",
                        content += "<p><span>Var:</span>" + va + "</p>",
                        content += "<p><span>Gene:</span>" + gene + "</p>",
                        content += "<p><span>Effect/Location:</span>" + effect + "</p>",
                        content += "</div>"
                    $.pt({
                        target: self,
                        position: 'b',
                        autoClose: false,
                        content: content
                    });
                    //$(".pt").css("left", $(".pt").position().left);
                },function () {
                    $(".pt").remove();
                });
            }
        });
    }

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
        //console.log(str.replace(/[\%,\/]/g,"_"));
        return str.replace(/[\%,\/]/g,"_");
    }

    // 生成SNPs表格
    function renderSNPTable(data) {
        var str = '';
        $.each(data, function(idx, item) {
            console.log(item);
            str += '<tr>'
            str += '    <td class="t_snpid" data-id="'+ item.id +'" data-var="'+ item.ref + '->' + item.alt +'" data-gene="'+ item.gene +'" data-effect="'+ item.effect +'">'+ item.id +'</td>'
            str += '    <td class="t_consequenceType"><p class="js-tipes-show">'+ formatConseType(item.consequencetype) + '</p></td>'
            str += '    <td class="t_snpchromosome"><p>'+ item.chr +'</p></td>'
            str += '<td class="t_position"><p class="js-tipes-show">'+ item.pos +'</p></td>'
            str += '<td class="t_snpreference"><p>'+ formatRef(item.ref) +'</p></td>'
            str += '<td class="t_majorAllele"><p>'+ formatRef(item.majorallen) +'</p></td>'
            str += '<td class="t_minorAllele"><p>'+ formatRef(item.minorallen) +'</p></td>'

            str += '<td class="t_fmajorAllele"><p>'+ formatPercent(item[Major_Or_Minor_SNP]) +'</p></td>'
            var freq = item.freq.concat() ;
            freq.reverse();
            $.each(freq, function(i, e) {
                str += '<td class="t_fmajorAllelein'+ replaceUnvalideChar(e.name).split(",").join("_").replace(/\s/g,"") +'"><p>' + formatPercent(e[Major_Or_Minor_SNP]) + '</p></td>'
            });

            str += '</tr>'
        });
        $(".js-snp-table>tbody").empty().append(str);
        TableHeaderSettingSnp();
    }

    // 生成INDELs表格
    function renderINDELTable(data) {
        var str = '';
        $.each(data, function(idx, item) {
            str += '<tr id="' +item.id + '">'
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
            console.log($(this).text());
        }
        //(function(){
        //    console.log($(this).text());
        //})
    }
    // 表头设置
    window.TableHeaderSettingSnp = function () {
        var headers = $(".js-table-header-setting-snp").find("label");
        //var hideHeaders = [];
        $.each(headers, function(idx, item) {
            if(!$(item).hasClass("checkbox-ac")) {
                //hideHeaders.push($(item).attr("for"));
                $(".js-snp-table").find(".t_"+$(item).attr("for")).hide();
            } else {
                $(".js-snp-table").find(".t_"+$(item).attr("for")).show();
            }
        });
    }
    window.TableHeaderSettingIndel = function () {
        var headers = $(".js-table-header-setting-indel").find("label");
        //var hideHeaders = [];
        $.each(headers, function(idx, item) {
            if(!$(item).hasClass("checkbox-ac")) {
                //hideHeaders.push($(item).attr("for"));
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
        $(".js-snp-table .consequence-type li").removeClass("active");
        $(this).addClass("active");
        var type = $(".js-snp-table .consequence-type").find(".active").attr("data-type");
        if(type == "effect") {
            CTypeSnp = "_" + $(".js-snp-table .consequence-type").find(".active").attr("data-value");
        } else if(type == "type") {
            CTypeSnp = $(".js-snp-table .consequence-type").find(".active").attr("data-value") + "_";
        } else {
            CTypeSnp = 'all';
        }
        var obj = getPanelParams();
        requestForSnpData(1, obj.url, obj.params);
    });

    var CTypeIndel = 'all';
    $(".js-indel-table").on("click", ".consequence-type li", function() {
        $(".js-indel-table .consequence-type li").removeClass("active");
        $(this).addClass("active");
        var type = $(".js-indel-table .consequence-type").find(".active").attr("data-type");
        if(type == "effect") {
            CTypeIndel = "_" + $(".js-indel-table .consequence-type").find(".active").attr("data-value");
        } else if(type == "type") {
            CTypeIndel = $(".js-indel-table .consequence-type").find(".active").attr("data-value") + "_";
        } else {
            CTypeIndel = 'all';
        }
        var obj = getPanelParams();
        requestForIndelData(1, obj.url, obj.params);
    });

    /*show-hidden-tab*/
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
    //$(".che-list label").click(function () {
    //    $(".js-btn-default").removeClass("btn-default-ac")
    //    $(".table_header_setting").removeClass("js-r-ac")
    //    if ($(this).hasClass("checkbox-ac")) {
    //        $(this).removeClass("checkbox-ac");
    //    } else {
    //        $(this).addClass("checkbox-ac");
    //    }
    //})
    /*全选*/
    $(".js-choose-all").click(function () {
        if($(this).hasClass("js-choose-all-ac")){
            $(this).removeClass("js-choose-all-ac")
        }else{
            $(this).addClass("js-choose-all-ac")
            var _labels = $(this).parents(".choose-default").siblings(".checkbox-item").find(".table_header_setting label");
            $.each(_labels, function (index, item) {
                $(this).addClass("checkbox-ac");
            });
        }
        $(".js-default").removeClass("js-default-ac");
        $(".table_header_setting").removeClass("js-r-ac");
        //$(this).css({"background-image": "url(${ctxStatic}/images/contrast-ac.png)"})

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
        } else {
            CurrentTab = "INDEL";
            $(".page-num-tab-indel").show();
            $(".page-num-tab-snp").hide();
        }
        $(".table-item").hide();
        $(".tab .export-data").show();
        $(this).addClass("item-ac").siblings().removeClass("item-ac ");
        $(".tab > div").eq($(this).index()).show().siblings().hide();
    });

    /* 导出 */
    $(".js-export").click(function() {
        var panelType = GetPanelParams.getPanelType();
        console.log(panelType);
        if(panelType == "gene") {
            var _form = $("#exportGeneForm");
            var params = GetPanelParams.getGeneParams();
            _form.find(".gene").val(params.gene);
            _form.find(".upstream").val(params.upstream);
            _form.find(".downstream").val(params.downstream);
            _form.find(".type").val(CurrentTab);
            if(CurrentTab == "SNP") {
                _form.find(".ctype").val(CTypeSnp);
                var _labels = $(".js-table-header-setting-snp").find("label");
            } else {
                _form.find(".ctype").val(CTypeIndel);
                var _labels = $(".js-table-header-setting-indel").find("label");
            }
            var choiceArr = [];
            $.each(_labels, function(idx, item) {
                if($(item).hasClass("checkbox-ac")) {
                    choiceArr.push($(item).attr("data-col-name"));
                }
            });
            _form.find(".choices").val(choiceArr.join(","));
            _form.find(".group").val(params.group);
            _form.submit();
        } else {
            var _form = $("#exportRegionForm");
            var params = GetPanelParams.getRegionParams();
            _form.find(".chromosome").val(params.chromosome);
            _form.find(".start").val(params.start);
            _form.find(".end").val(params.end);
            _form.find(".type").val(CurrentTab);
            if(CurrentTab=="SNP") {
                _form.find(".ctype").val(CTypeSnp);
                var _labels = $(".js-table-header-setting-snp").find("label");
            } else {
                _form.find(".ctype").val(CTypeIndel);
                var _labels = $(".js-table-header-setting-indel").find("label");
            }
            var choiceArr = [];
            $.each(_labels, function(idx, item) {
                if($(item).hasClass("checkbox-ac")) {
                    choiceArr.push($(item).attr("data-col-name"));
                }
            });
            _form.find(".choices").val(choiceArr.join(","));
            _form.find(".group").val(params.group);
            _form.submit();
        }/**/
    });
    //判断当前用户是否是管理员
            $.ajax({
                type:"GET",
                url:ctxRoot+ "/manager/user",
                success:function(result){
                    console.log(result);
                    var roles = result.data.authorities;
                    for (var i=0;i<roles.length;i++){
                        if(roles[i].name == "ROLE_ADMIN"){
                            $("#adminUser").show();
                        }
                    }
                }
            })


    // 基因结构图
    function drawGeneConstructor(result){
        console.log(result);
        // 参考值
        var referenceVal = result.bps;
        var startPos = parseInt(result.conditions.split(",")[1])-2000<0?1:parseInt(result.conditions.split(",")[1])-2000;
        var endPos =parseInt(result.conditions.split(",")[2])+2000>referenceVal?referenceVal:parseInt(result.conditions.split(",")[2]);
        var geneLength = endPos - startPos;
        console.log(startPos);
        console.log(endPos);
        console.log(geneLength);
       d3.select("#constructorPanel").selectAll("svg").remove();
       // 创建一个svg 元素
        var svgTotal = $("#constructorPanel").width();
        if(geneLength >svgTotal*10){
            var svg = d3.select("#constructorPanel").append("svg").attr("width",geneLength/10 + "px").attr("height","220px");
            var acrossLineData = [[20,220],[geneLength/10,220]];
            var topLineData = [[20,1],[geneLength/10,1]];
            var centerLineData = [[20,90],[geneLength/10,90]]
        }else {
            var svg = d3.select("#constructorPanel").append("svg").attr("width",svgTotal + "px").attr("height","220px");
            var acrossLineData = [[20,220],[svgTotal,220]];
            var topLineData = [[20,1],[svgTotal,1]];
            var centerLineData = [[20,90],[svgTotal,90]]
        }
        // 创建一个直线生成器
        var line = d3.line()
            .x(function (d){return d[0]})
            .y(function (d){return d[1]})
        // 起始竖线
        var verticalLineData = [[20,0],[20,220]];
        // 起始横线
        // var acrossLineData = [[20,220],[geneLength/10,220]];
        // 顶部横线
        // var topLineData = [[20,1],[geneLength/10,1]];
        // 中间分割（竖）线
        // var centerLineData = [[20,90],[geneLength/10,90]]
        // 画方向箭头
        var dirArrows  =  [[60,60],[40,72],[60,84],[60,72]];
        var intervalLineData = [];
        var svgLength = $("#constructorPanel svg").width();
        console.log(svgLength);
        if (svgLength >885){
            var intervalNums = geneLength/100;
        }else {
            var intervalNums = svgLength/10;
        }
        // console.log(intervalNums);
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
            }
        // 利用defined 把一条路径切割成一段一段的多条路径
            var line2 = line.defined(function(d, i, index) {
                //   在返回值为false的位置进行切割，并且当前数据不再计入到路径中
                return d[0] > 0 && d[1] > 0;
            })(intervalLineData);
            // 利用直线生成器生成相应的直线
            svg.append("path").attr("stroke","#000000").attr("stroke-width","3").attr("d",line(verticalLineData));
            svg.append("path").attr("stroke","#6E6E6E").attr("stroke-width","3").attr("d",line(acrossLineData));
            svg.append("path").attr("stroke","#E1E1E1").attr("stroke-width","2").attr("d",line(topLineData));
            svg.append("path").attr("stroke","#666666").attr("stroke-width","2").attr("d",line(centerLineData));
            svg.append("path").attr("stroke","#000").attr('stroke-width', '2').attr("fill","#000").attr("d",line(dirArrows)).attr("transform","translate(0,18)");
            svg.append("path").attr("stroke","#E1E1E1").attr("stroke-width","2").attr("d",line2);
            //   画方向箭头
            // 画基因结构图
            var topY = 70;   // 基因结构图距离上边距离
            var rectHeight = 20;   // 基因结构图高度
            var leftMargin = 60;
            var snpWidth = 5;
            var g = svg.append("g").attr("transform","translate(" +leftMargin + ",10)");
            var g1 = svg.append("g").attr("transform","translate(" +leftMargin + ",10)");
            var geneConstructs = result.dnaGenStructures;
            var snpLocalPoints = result.data;
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
            for (var i=0;i<geneConstructs.length;i++){
                var feature = geneConstructs[i].feature;
                var colorVal = chromoColor(feature);
                g.append("rect").attr("x",(endPos-geneConstructs[i].start)/10).attr("y",topY).attr("width",(geneConstructs[i].end - geneConstructs[i].start)/10).attr("height",rectHeight).attr("fill",colorVal);
            }
            // 画snp 位点
            for (var i=0;i<snpLocalPoints.length;i++){
                if(i < snpLocalPoints.length - 1){
                    if((snpLocalPoints[i+1].pos - snpLocalPoints[i].pos)/10 >10){
                        var a = g1.append("a").attr("href","#" +snpLocalPoints[i].id);
                        a.append("rect").attr("x",(endPos - snpLocalPoints[i].pos)/10).attr("y",topY + 50).attr("width",snpWidth).attr("height",snpWidth).attr("fill",snpColor);
                    }else {
                        var a = g1.append("a").attr("href","#" +snpLocalPoints[i].id);
                        a.append("rect").attr("x",(endPos - snpLocalPoints[i].pos)/10).attr("y",topY + 40).attr("width",snpWidth).attr("height",snpWidth).attr("fill",snpColor);
                    }
                }
            }
            // 画一条线
            // g.append("line").attr("x1","20").attr("y1","80").attr("x2",geneLength).attr("y2","80").attr("stroke-width","2").attr("stroke","#666666");

        $("g a rect").click(function (){
            alert(33)
        })

        }

    // 定义滚轮缩放
    // var count = 1;
    // $("#constructorPanel").on("mousewheel DOMMouseScroll","svg",function (e) {
    //
    //     var delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) ||  // chrome & ie
    //         (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1));              // firefox
    //
    //
    //     if (delta > 0) {
    //         // 向上滚
    //         count++;
    //         $(this).css("transform", "scale(" + count * 0.2 + ")");
    //         console.log("wheelup");
    //
    //     } else if (delta < 0) {
    //         // 向下滚
    //         count--;
    //         if (count > 0) {
    //             $(this).css("transform", "scale(" + count * 0.2 + ")");
    //             console.log("wheeldown");
    //         }else if(count<=0){
    //             count = 1
    //         }
    //
    //     }
    // });
})
