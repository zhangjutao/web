
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


// 初始化性状
function initXZ(arr_geneName) {

    //首先获取对象
    var geneName = document.forms.sortSelect;
    var geneList = document.forms.geneList;
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

    //指定基因名中<option>标记的个数
    //geneList.length = arr_geneList[index].length;

    //循环将数组中的数据写入<option>标记中
    for (var j = 0; j < arr_geneList[index].length; j++) {
        var industry = arr_geneList[index];
        $(".select_con").append("<li value="+ arr_geneList[index][j] + "><label><span></span>" + arr_geneList[index][j] + "</label></li>");
    }


}


