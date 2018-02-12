$(function () {
    // $(".total-page-count").hide();
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

    // 初始化页面
    function initPage() {
        // $(".page-tables").show();
        // $(".page-circle").hide();
        var obj = getPanelParams();
        if(typeof obj == "object") {
            CTypeSnp = "all";
            CTypeIndel = "all";
            requestForSnpData(1,10,obj.url, obj.params);
            requestForIndelData(1,10, obj.url, obj.params);
            renderSearchText();
            renderTableHead();
        }
    }
    initPage();

    // 生成搜索描述文字
    function renderSearchText() {
        // var panelType = GetPanelParams.getPanelType();
        // if(panelType == "region") {
        //     var params = GetPanelParams.getRegionParams();
        //     var desc = '染色体 ' + params.chromosome + ", 范围 " + params.start + "bp-" + params.end + "bp";
        // } else {
        //     var params = GetPanelParams.getGeneParams();
        //     if(!params.downstream && !params.upstream) {
        //         var desc = '基因 ' + params.gene ;
        //     } else if(!params.upstream && params.downstream) {
        //         var desc = '基因 ' + params.gene + ", 范围 Downstream " + params.downstream + "bp";
        //     } else if(!params.downstream && params.upstream) {
        //         var desc = '基因 ' + params.gene + ", 范围 Upstream " + params.upstream + "bp";
        //     } else {
        //         var desc = '基因 ' + params.gene + ", 范围 Upstream " + params.upstream + "bp -Downstream " + params.downstream + "bp";
        //     }
        //
        // }
        // $(".js-search-desc").html(desc);
    }

    // 获取面板上的参数
    function getPanelParams() {
        var params, url;
        params = {"gene": iGENE };
        url = CTXROOT + "/iqgs/searchDNAinGene";
        if(typeof params == "object"){
            return {
                "params" :  params,
                "url" : url
            }
        }
        return false;
    }

    /*
    * 原逻辑：根据群体生成表格内容设置，和表头
    * 修改：去除群体信息
    */
    function renderTableHead () {
        var str = '', str2 = '';
        var commonStr = '<li data-value="all">ALL</li>'+
            '<li data-type="type" data-value="downstream">Downstream</li>'+
            '<li data-type="type" data-value="exonic;splicing">Exonic;Splicing</li>'+
            '<li data-type="effect" data-value="exonic_nonsynonymous SNV">Exonic_nonsynonymous SNV</li>'+
            '<li data-type="effect" data-value="exonic_stopgain">Exonic_stopgain</li>'+
            '<li data-type="effect" data-value="exonic_stoploss">Exonic_stoploss</li>' +
            '<li data-type="effect" data-value="exonic_synonymous SNV">Exonic_synonymous SNV</li>' +
            '<li data-type="type" data-value="intergenic">Intergenic</li>'+
            '<li data-type="type" data-value="intronic">Intronic</li>'+
            '<li data-type="type" data-value="splicing">Splicing</li>'+
            '<li data-type="type" data-value="upstream">Upstream</li>'+
            '<li data-type="type" data-value="upstream;downstream">Upstream;Downstream</li>'+
            '<li data-type="type" data-value="UTR3">3&acute;UTR</li>'+
            '<li data-type="type" data-value="UTR5">5&acute;UTR</li>' +
            '<li data-type="type" data-value="UTR5;UTR3">5&acute;UTR;3&acute;UTR</li>';
        var commonStr2 = '<li data-value="all">ALL</li>'+
            '<li data-type="type" data-value="downstream">Downstream</li>'+
            '<li data-type="type" data-value="exonic;splicing">Exonic;Splicing</li>'+
            '<li data-type="effect" data-value="exonic_frameshift deletion">Exonic_frameshift deletion</li>'+
            '<li data-type="effect" data-value="exonic_frameshift insertion">Exonic_frameshift insertion</li>'+
            '<li data-type="effect" data-value="exonic_frameshift insertion">Exonic_nonframeshift deletion</li>'+
            '<li data-type="effect" data-value="exonic_nonframeshift insertion">Exonic_nonframeshift insertion</li>'+
            '<li data-type="effect" data-value="exonic_stopgain">Exonic_stopgain</li>'+
            '<li data-type="effect" data-value="exonic_stoploss">Exonic_stoploss</li>'+
            '<li data-type="type" data-value="intergenic">Intergenic</li>'+
            '<li data-type="type" data-value="intronic">Intronic</li>'+
            '<li data-type="type" data-value="splicing">Splicing</li>'+
            '<li data-type="type" data-value="upstream">Upstream</li>'+
            '<li data-type="type" data-value="upstream;downstream">Upstream;Downstream</li>'+
            '<li data-type="type" data-value="UTR3">3&acute;UTR</li>'+
            '<li data-type="type" data-value="UTR5">5&acute;UTR</li>'+
            '<li data-type="type" data-value="UTR5;UTR3">5&acute;UTR;3&acute;UTR</li>';

        var headStr = '<td class="t_snpid">SNP ID</td>' +
            '<td class="param t_consequenceType">Consequence Type' +
            ' <img src="/iqgs/static/images/down.png">\n' +
            '<input type="hidden" class="js-consequence-type">\n' +
            ' <div class="input-component ">\n' +
            '<ul class="consequence-type ">\n' +
            commonStr  +
            '</ul>\n' +
            '</div>' +
            '</td>'+
            '<td class="param t_snpchromosome">Chromosome</td>'+
            '<td class="param t_position">Position</td>'+
            '<td class="param t_snpreference">Reference</td>'+
            '<td class="param t_majorAllele">Major Allele</td>'+
            '<td class="param t_minorAllele">Minor Allele</td>'+
            '<td class="param t_fmajorAllele"><select class="f-ma"><option value="major">Frequence of Major Allele</option>' +
            '<option value="minor">Frequence of Minor Allele</option></select></td>';
        var headStr2 = '<td class="t_indels">INDEL ID</td>' +
            '<td class="param t_iconsequenceType">Consequence Type' +
            ' <img src="/iqgs/static/images/down.png">\n' +
            '<input type="hidden" class="js-consequence-type">\n' +
            ' <div class="input-component ">\n' +
            '<ul class="consequence-type ">\n' +
            commonStr2 +
            '</ul>\n' +
            '</div>' +
            '</td>'+
            '<td class="param t_indelchromosome">Chromosome</td>'+
            '<td class="param t_iposition">Position</td>'+
            '<td class="param t_indelreference">Reference</td>'+
            '<td class="param t_imajorAllele">Major Allele</td>'+
            '<td class="param t_iminorAllele">Minor Allele</td>'+
            '<td class="param t_ifmajorAllele"><select class="f-ma"><option value="major">Frequence of Major Allele</option>' +
            '<option value="minor">Frequence of Minor Allele</option></select></td>';

        $(".js-table-header-setting-snp > span").empty().append(str);
        $(".js-table-header-setting-indel > span").empty().append(str);
        $(".js-snp-table thead tr").empty().append(headStr);
        $(".js-indel-table thead tr").empty().append(headStr2);
        // TableHeaderSettingSnp();
        // TableHeaderSettingIndel();
    }

    // 配置默认每页显示条数
    var curr=1;
    var pageSizeSNP = 10;
    $("#snp-paginate .lay-per-page-count-select").val(pageSizeSNP);

    // 配置默认每页显示条数
    var pageSizeINDEL = 10;
    $("#indel-paginate .lay-per-page-count-select").val(pageSizeINDEL);

    // 修改每页显示条数
    $(".js-snp-tab").on("change", ".lay-per-page-count-select", function() {
        var obj = getPanelParams();
        var currNum=Number($(".js-snp-tab .laypage_curr").text());
        var pageSizeSNPS = Number($(this).val());
        var total= Number($(".js-snp-tab #total-page-count span").text());
        var mathCeil=  Math.ceil(total/currNum);

        if(pageSizeSNPS>mathCeil){
            requestForSnpData(1,pageSizeSNPS, obj.url, obj.params);
        }else{
            requestForSnpData(currNum,pageSizeSNPS, obj.url, obj.params);
        }
    });

    $(".js-indel-tab").on("change", ".lay-per-page-count-select", function() {
        var obj = getPanelParams();
        var currIndelNum=Number($("#indel-paginate .laypage_curr").text());
        var pageSizeINDEL = Number($(this).val());
        var total= $("#indel-paginate #total-page-count span").text();
        var mathCeil=  Math.ceil(total/currIndelNum);

        if(pageSizeINDEL>mathCeil){
            requestForIndelData(1,pageSizeINDEL, obj.url, obj.params);
        }else{
            requestForIndelData(currIndelNum,pageSizeINDEL, obj.url, obj.params);
        }
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
        // var pageSizeSNPS = Number($(".js-snp-tab .lay-per-page-count-select").val());


        if(e && e.keyCode==13){ // enter 键
            if( _page_skip.hasClass("isFocus") ) {
                var pageSizeSNPS = Number($(".js-snp-tab .lay-per-page-count-select").val());

                var obj = getPanelParams();
                var total= Number($(".js-snp-tab #total-page-count span").text());
                var currNum=Number($("#snp-paginate .laypage_skip").val());
                var mathCeil=  Math.ceil(total/pageSizeSNPS);
                if(currNum>mathCeil){
                    requestForSnpData(1,pageSizeSNPS, obj.url, obj.params);
                }else{
                    requestForSnpData(_page_skip.val() * 1,pageSizeSNPS, obj.url, obj.params);
                }
            }
            if(_page_skip2.hasClass("isFocus")) {
                var pageSizeINDEL = Number($(".js-indel-tab .lay-per-page-count-select").val());
                var obj = getPanelParams();
                if(_page_skip2.val() * 1 > Math.ceil(($(".total-page-count-indel").text() *1)/pageSizeINDEL)) {
                    var curr= 1;
                    requestForIndelData(1,pageSizeINDEL, obj.url, obj.params);
                    // return alert("输入页码不能大于总页数");
                }else{
                    requestForIndelData(_page_skip2.val() * 1,pageSizeINDEL, obj.url, obj.params);
                }
            }
        }
    }

    var SNPData = [];
    var Major_Or_Minor_SNP = "major";
    // 请求数据并分页 -- SNP
    function requestForSnpData(curr,pageSizeSNP, url, params) {
        // console.log(pageSizeSNP)
        params['pageNo'] = curr || 1;
        params['pageSize'] = pageSizeSNP||10;
        params['type'] = 'SNP';
        params['ctype'] = CTypeSnp;

        // loadMask ("#mask-test");

        $.ajax({
            url: url,
            data: params,
            type: "POST",
            dataType: "json",
            success: function(res) {
                // maskClose("#mask-test");
                SNPData = res.data;
                if(res.data.length > 0) {
                    renderSNPTable(SNPData, Major_Or_Minor_SNP);
                } else {
                    //alert("无数据");
                    $(".js-snp-table>tbody").empty().append("<div class='zwsj'><p>暂无数据</p></div>");
                }

                laypage({
                    cont: $('#snp-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(res.total / pageSizeSNP), //通过后台拿到的总页数
                    curr: curr || 1, //当前页
                    skin: '#5c8de5',
                    // skip: true,
                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                    last: Math.ceil(res.total / pageSizeSNP), //将尾页显示为总页数。若不显示，设置false即可
                    prev: '<',
                    next: '>',
                    skip: true,
                    groups: 3, //连续显示分页数
                    jump: function (obj, first) { //触发分页后的回调
                        if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                            var tmp = getPanelParams();
                            requestForSnpData(obj.curr,pageSizeSNP, tmp.url, tmp.params);
                        }
                    }
                });
                // $(".total-page-count-snp").html(res.total);
                $(".js-snp-tab #total-page-count span").html(res.total);
                $(".total-page-count-snp").html(res.total);

                /*鼠标悬浮在SNP ID上显示SNP信息*/
                //$(".js-snp-table tbody .t_snpid").hover(function () {
                //    var self = this;
                //    var snpid = $(this).attr("data-id");
                //    var va = $(this).attr("data-var");
                //    var gene = $(this).attr("data-gene");
                //    var effect = $(this).attr("data-effect");
                //    var content = "";
                //        content += "<div class='snpid-td'>"
                //        content += "<p><span>SNP ID:</span>" + snpid + "</p>",
                //        content += "<p><span>Var:</span>" + va + "</p>",
                //        content += "<p><span>Gene:</span>" + gene + "</p>",
                //        content += "<p><span>Effect/Location:</span>" + effect + "</p>",
                //        content += "</div>"
                //    $.pt({
                //        target: self,
                //        position: 'b',
                //        autoClose: false,
                //        content: content
                //    });
                //    //$(".pt").css("left", $(".pt").position().left);
                //},function () {
                //    $(".pt").remove();
                //});
            }
        });
    }

    var INDELData = [];
    var Major_Or_Minor_INDEL = "major";
    // 请求数据并分页 -- INDEL
    function requestForIndelData(curr,pageSizeINDEL, url, params) {
        params['pageNo'] = curr || 1;
        params['pageSize'] = pageSizeINDEL;
        params['type'] = 'INDEL';
        params['ctype'] = CTypeIndel;

        // loadMask ("#mask-test2");

        $.ajax({
            url: url,
            data: params,
            type: "POST",
            dataType: "json",
            success: function(res) {
                // maskClose("#mask-test2");
                INDELData = res.data;
                if(res.data.length > 0) {
                    renderINDELTable(INDELData, Major_Or_Minor_INDEL);
                } else {
                    //alert("无数据");
                    $(".js-indel-table>tbody").empty().append("<div class='zwsj'><p>暂无数据</p></div>");
                }

                laypage({
                    cont: $('#indel-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(res.total / pageSizeINDEL), //通过后台拿到的总页数
                    curr: curr || 1, //当前页
                    skin: '#5c8de5',
                    // skip: true,
                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                    last: Math.ceil(res.total / pageSizeINDEL), //将尾页显示为总页数。若不显示，设置false即可
                    prev: '<',
                    next: '>',
                    skip: true,
                    groups: 3, //连续显示分页数
                    jump: function (obj, first) { //触发分页后的回调
                        if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                            var pageSizeINDEL = Number($("#indel-paginate #per-page-count .lay-per-page-count-select").val());
                            var tmp = getPanelParams();
                            requestForIndelData(obj.curr,pageSizeINDEL, tmp.url, tmp.params);
                        }
                    }
                });
                $("#indel-paginate #total-page-count span").html(res.total);
                $(".total-page-count-indel").html(res.total);

                /*鼠标悬浮在INDEL ID上显示INDEL信息*/
                //$(".js-indel-table tbody .t_indels").hover(function () {
                //    var self = this;
                //    var indelid = $(this).attr("data-id");
                //    var va = $(this).attr("data-var");
                //    var gene = $(this).attr("data-gene");
                //    var effect = $(this).attr("data-effect");
                //    var content = "";
                //        content += "<div class='snpid-td'>"
                //        content += "<p><span>INDEL ID:</span>" + indelid + "</p>",
                //        content += "<p><span>Var:</span>" + va + "</p>",
                //        content += "<p><span>Gene:</span>" + gene + "</p>",
                //        content += "<p><span>Effect/Location:</span>" + effect + "</p>",
                //        content += "</div>"
                //    $.pt({
                //        target: self,
                //        position: 'b',
                //        autoClose: false,
                //        content: content
                //    });
                //    //$(".pt").css("left", $(".pt").position().left);
                //},function () {
                //    $(".pt").remove();
                //});
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
            str += '<tr>'
            str += '    <td class="t_snpid" data-id="'+ item.id +'" data-var="'+ item.ref + '->' + item.alt +'" data-gene="'+ item.gene +'" data-effect="'+ item.effect +'">'+ item.id +'</td>'
            str += '    <td class="t_consequenceType"><p >'+ formatConseType(item.consequencetype) + '</p></td>'
            str += '    <td class="t_snpchromosome"><p>'+ item.chr +'</p></td>'
            str += '<td class="t_position"><p>'+ item.pos +'</p></td>'
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
            str += '<tr>'
            str += '    <td class="t_indels" data-id="'+ item.id +'" data-var="'+ item.ref + '->' + item.alt +'" data-gene="'+ item.gene +'" data-effect="'+ item.effect +'">'+ item.id +'</td>'
            str += '    <td class="t_iconsequenceType"><p>'+ formatConseType(item.consequencetype) + '</p></td>'
            str += '    <td class="t_indelchromosome"><p>'+ item.chr +'</p></td>'
            str += '<td class="t_iposition"><p>'+ item.pos +'</p></td>'
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
            CTypeSnp = $(".js-snp-table .consequence-type").find(".active").attr("data-value");
        } else {
            CTypeSnp = 'all';
        }
        var obj = getPanelParams();
        requestForSnpData(1,10, obj.url, obj.params);
    });

    var CTypeIndel = 'all';
    $(".js-indel-table").on("click", ".consequence-type li", function() {
        $(".js-indel-table .consequence-type li").removeClass("active");
        $(this).addClass("active");
        var type = $(".js-indel-table .consequence-type").find(".active").attr("data-type");
        if(type == "effect") {
            CTypeIndel = "_" + $(".js-indel-table .consequence-type").find(".active").attr("data-value");
        } else if(type == "type") {
            CTypeIndel = $(".js-indel-table .consequence-type").find(".active").attr("data-value");
        } else {
            CTypeIndel = 'all';
        }
        var obj = getPanelParams();
        requestForIndelData(1,10, obj.url, obj.params);
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
        }
    });

})
