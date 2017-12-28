$.fn.ProvinceCity = function (GP, GT, GC) {
    var _self = this;
    //定义3个默认值
    _self.data("province", ["请选择", "请选择"]);
    _self.data("city1", ["请选择", "请选择"]);
    //插入3个空的下拉框
    _self.append("<select></select>");
    _self.append("<select></select>");
    _self.append("<ul class='qtlList'></ul>");
    //分别获取3个下拉框
    var $sel1 = _self.find("select").eq(0);
    var $sel2 = _self.find("select").eq(1);
    var $sel3 = _self.find("ul");
    //默认一级下拉
    if (_self.data("province")) {
        $sel1.append("<option value='" + _self.data("province")[1] + "'>" + _self.data("province")[0] + "</option>")
    }
    $.each(GP, function (index, data) {
        $sel1.append("<option value='" + data + "'>" + data + "</option>")
    });
    //默认的1级基因名下拉
    if (_self.data("city1")) {
        $sel2.append("<option value='" + _self.data("city1")[1] + "'>" + _self.data("city1")[0] + "</option>")
    }
    //默认的2级基因名下拉
    if (_self.data("city2")) {
        $sel3.append("<li value='" + _self.data("city2")[1] + "'>" + _self.data("city2")[0] + "</li>")
    }
    //一级联动 控制
    var index1 = "";
    $sel1.change(function () {
        $('.qtlList li').remove();
        //清空其它2个下拉框
        $sel2[0].options.length = 0;
        //$sel3[0].options.length = 0;
        $('.qtlList').find("li").length = 0;
        //$("ul li").text().length = 0;
        index1 = this.selectedIndex;
        if (index1 == 0) {	//当选择的为 “请选择” 时
            if (_self.data("city1")) {
                $sel2.append("<option value='" + _self.data("city1")[1] + "'>" + _self.data("city1")[0] + "</option>")
            }
            if (_self.data("city2")) {
                $sel3.append("<li value='" + _self.data("city2")[1] + "'>" + _self.data("city2")[0] + "</li>")
            }
        } else {
            $.each(GT[index1 - 1], function (index, data) {
                $sel2.append("<option value='" + data + "'>" + data + "</option>")
            });
            $.each(GC[index1 - 1][0], function (index, data) {
                for (var i = 0; i < data.length; i++) {
                    // $sel3.append("<li value='" + data[i] + "'>" + data[i] + "</li>")
                    $sel3.append("<li>" + "<label for='" + data[i] + "'><span id ='" + data[i] + "' data-value='" + data[i] + "'></span>" + data[i] + "</label></li>");
                }
                
            })
        }
    }).change();
    //1级基因名联动 控制
    var index2 = "";
    $sel2.change(function () {
        $('.qtlList li').remove();
        //$sel3[0].options.length=0;
        $(".qtlList li").text().length = 0;
        index2 = this.selectedIndex;
        $.each(GC[index1 - 1][index2], function (index, data) {
            for (var i = 0; i < data.length; i++) {
                $sel3.append("<li>" + "<label for='" + data[i] + "'><span id ='" + data[i] + "' data-value='" + data[i] + "'></span>" + data[i] + "</label></li>");
            }
        })
    });
    return _self;
};