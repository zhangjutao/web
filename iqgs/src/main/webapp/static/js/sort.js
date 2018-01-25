
// $.ajax({
//     type: 'GET',
//     url: 'json/selectArr.js',
//     dataType: 'json',
//     success: function (jsonStr) {
//
var jsonStr=[
    {
        "id": "59898cfc1d78c746c0df80cf",
        "name": "pod_All",
        "chinese": "豆荚",
        "children": [
            {
                "name": "pod",
                "chinese": "",
                "children": [

                ]
            }
        ]
    },
    {
        "id": "59898cfc1d78c746c0df80d0",
        "name": "seed_All",
        "chinese": "种子 ",
        "children": [
            {
                "name": "seed",
                "chinese": "",
                "children": [

                ]
            },
            {
                "name": "seed coat",
                "chinese": "",
                "children": [
                    {
                        "name": "seed coat endothelium",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "seed coat epidermis",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "seed coat hilum",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "seed coat inner integument",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "seed coat outer integument",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "seed coat parenchyma compartment",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "seed coat hourglass",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "seed coat parenchyma",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "seed coat palisade",
                        "chinese": "",
                        "children": [

                        ]
                    }
                ]
            },
            {
                "name": "embryo",
                "chinese": "",
                "children": [
                    {
                        "name": "embryo proper",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "suspensor",
                        "chinese": "",
                        "children": [

                        ]
                    }
                ]
            },
            {
                "name": "cotyledon",
                "chinese": "",
                "children": [
                    {
                        "name": "cotyledon abaxial parenchyma",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "cotyledon adaxial epidermis",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "cotyledon adaxial parenchyma",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "cotyledon vasculature",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "cotyledon abaxial epidermis",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "germinated cotyledon",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "immature cotyledon",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "embryonic cotyledon",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "embryo proper cotyledon",
                        "chinese": "",
                        "children": [

                        ]
                    }
                ]
            },
            {
                "name": "axis",
                "chinese": "",
                "children": [
                    {
                        "name": "embryo proper axis ",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "embryonic axis",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "axis epidermis",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "axis plumules",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "axis vasculature",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "axis parenchyma",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "axis root tip",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "axis shoot apical meristem",
                        "chinese": "",
                        "children": [

                        ]
                    },
                    {
                        "name": "axis stele",
                        "chinese": "",
                        "children": [

                        ]
                    }
                ]
            },
            {
                "name": "endosperm",
                "chinese": "",
                "children": [

                ]
            }
        ]
    }
]
        var arr_geneList = [""];
        var arr_geneName = ["请选择"];
        for (var i = 0; i < jsonStr.length; i++) {
            //组织select
            list = [jsonStr[i].chinese];
            arr_geneName.push(list)
            var arr_geneListName = [];
            for (var j = 0; j < jsonStr[i].children.length; j++) {
                geneList = jsonStr[i].children[j].name
                arr_geneListName.push(geneList);

            }

            arr_geneList.push(arr_geneListName);
        }
        //初始化组织
        init(arr_geneName);

        //选择单个组织触发
        $("#geneName").change(function () {
            changeSelect(this.selectedIndex, arr_geneList);
            // 设置组织
            $("#geneList li").off("click").on("click",function (){
                if($(this).hasClass("checked")) {
                    $(this).removeClass("checked");
                    var _thisText=$(this).text();
                    // console.log(_thisText)
                    var selTexts = $(".sortZzText").find("span");
                    for (var i=0;i<selTexts.length;i++){
                        var selTextsName = $(selTexts[i]).text().substring(0,$(selTexts[i]).text().length-1);
                        // console.log(selTextsName)
                        if(_thisText==selTextsName){
                            // console.log(selTexts[i])
                            $(selTexts[i]).remove();
                        }
                    }
                }else {
                    $(this).addClass("checked");
                        var sortzzTextConter=$(this).text();
                        var str="<span class='sortZzText_conter'>"+sortzzTextConter+"<i class='sortZzGb'>X</i></span>";
                        $(".sortZzText").append(str);
                }

                // 选中的点击删除
                $(".sortZzGb").on("click",function (){
                    $(this).parent().remove();

                    var _thisdelText=$(this).parent().text().substring(0,$(this).parent().text().length-1);
                    // console.log(_thisText)
                    var sortInputs = $("#geneList").find("li");
                    for (var j=0;j<sortInputs.length;j++){
                        var tdParent = $(sortInputs[j]).find("label").text();
                        if(_thisdelText==tdParent){
                            $(sortInputs[j]).removeClass("checked");
                        }
                    }
                })

            });
            //调用重置保存状态
            sortSaveStatus();
        })


// 重置保存状态
function sortSaveStatus(){
    var sortInputs = $("#geneList").find("li");
    var selTexts = $(".sortZzText").find("span");
    for (var i=0;i<selTexts.length;i++){
        // var selTextsName = $(selTexts[i]).text().substring(3,$(selTexts[i]).text().length-1);
        var selTextsName = $(selTexts[i]).text().substring(0,$(selTexts[i]).text().length-1);
        // console.log(selTextsName)
        for (var j=0;j<sortInputs.length;j++){
            var tdParent = $(sortInputs[j]).find("label").text();
            if(tdParent==selTextsName){
                $(sortInputs[j]).addClass("checked");
            }
        }
    }
}


//网页加载完成，初始化菜单
function init(arr_geneName) {

    //首先获取对象
    var geneName = document.form1.geneName;
    var geneList = document.form1.geneList;
    console.log(geneName)
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
        // console.log(industry)
    }


}


