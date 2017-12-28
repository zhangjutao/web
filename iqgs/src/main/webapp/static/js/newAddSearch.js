//基因表达量一级联动
function geneExpressionData(jsonStr) {
    var arr_geneList = [""];
    var arr_geneName = ["请选择"];
    for (var i = 0; i < jsonStr.length; i++) {
        //基因表达量select
        list = [jsonStr[i].chinese];
        arr_geneName.push(list)
        var arr_geneListName = [];
        for (var j = 0; j < jsonStr[i].children.length; j++) {
            geneList = jsonStr[i].children[j].name
            arr_geneListName.push(geneList);
        }

        arr_geneList.push(arr_geneListName);
    }
    //初始化基因表达量
    init(arr_geneName);

    //选择单个基因表达量触发
    $("#geneName").change(function () {
        changeSelect(this.selectedIndex, arr_geneList)
    })
}
//网页加载完成，初始化菜单
function init(arr_geneName) {
    //首先获取对象
    var geneName = document.form1.geneName;
    var geneList = document.form1.geneList;
    //指定基因表达量中<option>标记的个数
    geneName.length = arr_geneName.length;

    //循环将数组中的数据写入<option>标记中
    for(var i=0;i<arr_geneName.length;i++){
        geneName.options[i].text = arr_geneName[i];
        geneName.options[i].value = arr_geneName[i];
    }

    //修改基因表达量列表的默认选择项
    var index = 0;
    geneName.selectedIndex = index;
}

function changeSelect(index, arr_geneList) {
    $('.select_con li').remove();
    //选择对象
    var geneList = document.form1.geneList;
    //修改基因表达量列表的选择项
    geneName.selectedIndex = index;

    //循环将数组中的数据写入<option>标记中
    var geneNameVal= $("#geneName ").val();
    for (var j = 0; j < arr_geneList[index].length; j++) {
      $(".select_con").append("<li>" + "<label for='" + arr_geneList[index][j] + "'><span id ='" + arr_geneList[index][j] + "' data-value='" + arr_geneList[index][j] + "'></span>" + arr_geneList[index][j] + "</label></li>");
        // $('.geneExpression_select').append('<span>'+geneNameVal+'</span><b>" + jybdl + "</b>')
    }
}

// 每个基因表达量列表的点击选中事件
var geneExpression = {};
geneExpression.selectedQtl = [];
$(".form_search .fuzzySearch").on("click","li",function (){
    var list =  $(".form_search .fuzzySearch li");
    if($(this).hasClass("checked")) {
        $(this).removeClass("checked");
        for(var i=0;i<geneExpression.selectedQtl.length;i++){
            if(geneExpression.selectedQtl[i] == $(this).find("span").attr("id")){
                geneExpression.selectedQtl.splice(i,1);
                console.log(geneExpression.selectedQtl)
                $('.geneExpression_select').html((geneExpression.selectedQtl+ ",").replace(/[,]$/,";"));
            }
        }
    }
    else {
        $(this).addClass("checked")
                geneExpression.selectedQtl.push($(this).find("span").attr("id"));
                console.log(geneExpression.selectedQtl)
                $('.geneExpression_select').html((geneExpression.selectedQtl+ ",").replace(/[,]$/,";"))
                if($('.geneExpression_select').text().length!==0){
                    var geneNameVal= $("#geneName ").val();
                    $('.geneExpression_name').text("基因表达量:"+geneNameVal);
                    $('.geneExpression_del').on('click',function(){
                        $('.geneExpression_select,.geneExpression_name').text("");
                        geneExpression.selectedQtl = [];
                        $('#geneList').find("li").removeClass("checked");
                console.log(geneExpression.selectedQtl)
            })
        }
    }


});

/////***************************************//////
//SNP数据处理
function searchSnpData(jsonStr) {
    for(var i=0;i<jsonStr.length;i++){
        $('.snpSearch_ul').append("<li>" + "<label for='" + jsonStr[i] + "'><span id ='" + jsonStr[i] + "' data-value='" + jsonStr[i] + "'></span>" + jsonStr[i] + "</label></li>");
    }
}

// 每个Snp列表的点击选中事件
var globleObjectSnp = {};
globleObjectSnp.selectedQtl = [];
$(".snpSearch .snpSearch_ul").on("click","li",function (){
    var list =  $(".snpSearch .snpSearch_ul li");
    if($(this).hasClass("checked")) {
        $(this).removeClass("checked");
        for(var i=0;i<globleObjectSnp.selectedQtl.length;i++){
            if(globleObjectSnp.selectedQtl[i] == $(this).find("span").attr("id")){
                globleObjectSnp.selectedQtl.splice(i,1);
                console.log(globleObjectSnp.selectedQtl)
                $('.snp_select').html((globleObjectSnp.selectedQtl+ ",").replace(/[,]$/,""));
            }
        }
    }
    else {
        $(this).addClass("checked")
        globleObjectSnp.selectedQtl.push($(this).find("span").attr("id"));
        console.log(globleObjectSnp.selectedQtl)
        $('.snp_select').html((globleObjectSnp.selectedQtl+ ",").replace(/[,]$/,""))

        if($('.snp_select').text().length!==0){
            // var geneNameVal= $("#geneName ").val();
            // $('.snp_name').text("基因表达量:"+geneNameVal);
            $('.snp_del').on('click',function(){
                $('.snp_select,.geneExpression_name').text("");
                globleObjectSnp.selectedQtl = [];
                $('.snpSearch_ul').find("li").removeClass("checked");
                console.log(globleObjectSnp.selectedQtl)
            })
        }

    }
});

/////***************************************//////
//INDEL数据处理
function searchIndelData(jsonStr) {
    for(var i=0;i<jsonStr.length;i++){
        $('.indelSearch_ul').append("<li>" + "<label for='" + jsonStr[i] + "'><span id ='" + jsonStr[i] + "' data-value='" + jsonStr[i] + "'></span>" + jsonStr[i] + "</label></li>");
    }
}
// 每个indel列表的点击选中事件
var globleObjectIndel = {};
globleObjectIndel.selectedQtl = [];
$(".indelSearch .indelSearch_ul").on("click","li",function (){
    var list =  $(".indelSearch .indelSearch_ul li");
    if($(this).hasClass("checked")) {
        $(this).removeClass("checked");
        for(var i=0;i<globleObjectIndel.selectedQtl.length;i++){
            if(globleObjectIndel.selectedQtl[i] == $(this).find("span").attr("id")){
                globleObjectIndel.selectedQtl.splice(i,1);
                $('.indel_select').html((globleObjectIndel.selectedQtl+ ",").replace(/[,]$/,""));
            }
        }
    }
    else {
        $(this).addClass("checked")
        globleObjectIndel.selectedQtl.push($(this).find("span").attr("id"));
        console.log(globleObjectIndel.selectedQtl)
        $('.indel_select').html((globleObjectIndel.selectedQtl+ ",").replace(/[,]$/,""))

        if($('.indel_select').text().length!==0){
            $('.indel_del').on('click',function(){
                $('.indel_select,.geneExpression_name').text("");
                globleObjectIndel.selectedQtl = [];
                $('.indelSearch_ul').find("li").removeClass("checked");
            })
        }
    }
});

/////***************************************//////
//QTL两级联动
function searchQtlData(jsonStr) {
    var GT = [];
    var GP = [];
    var GC = [];
    for (var i = 0; i < jsonStr.length; i++) {
        //基因表达量select
        list = jsonStr[i].qtlDesc;
        GP.push(list)
        var arr_qtlName = [];
        var qtlAll = [];
        for (var j = 0; j < jsonStr[i].traitLists.length; j++) {
            geneList = jsonStr[i].traitLists[j].traitName;
            arr_qtlName.push(geneList);
            var arr_qtlNameList = [];
            var qtls = jsonStr[i].traitLists[j].qtls;
            for (var m = 0; m < qtls.length; m++) {
                qtlNameList = qtls[m].qtlName;
                arr_qtlNameList.push([qtlNameList])
            }
            qtlAll.push([arr_qtlNameList]);
        }
        GT.push(arr_qtlName);
        GC.push(qtlAll);
    }
    //调用QTL二级联动方法
    $("#province").ProvinceCity(GP, GT, GC);

    // 每个QTL列表的点击选中事件
    var globleObjectQTL = {};
    globleObjectQTL.selectedQtl = [];
    $("#province .qtlList").on("click","li",function (){
        var list =  $(".fuzzySearch .qtlList li");
        if($(this).hasClass("checked")) {
            $(this).removeClass("checked");
            for(var i=0;i<globleObjectQTL.selectedQtl.length;i++){
                if(globleObjectQTL.selectedQtl[i] == $(this).find("span").attr("id")){
                    globleObjectQTL.selectedQtl.splice(i,1);
                    console.log(globleObjectQTL.selectedQtl)
                }
            }
        }
        else {
            $(this).addClass("checked")
            globleObjectQTL.selectedQtl.push($(this).find("span").attr("id"));
            console.log(globleObjectQTL.selectedQtl)
        }
    });
}

