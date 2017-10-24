<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/WEB-INF/views/include/dashboard/header.jsp" %>

<style>
    /*body,html{color: #5a5a5a;font-family:"microsoft yahei";font-size:14px;}*/
    .cover,.custom-groups-content{display: block}
    .cover{width: 27px;position: absolute;top: 0px;left: 100px;background-color: #fff;height: 30px;z-index: 10;border-bottom: 1px solid #5c8ce6;border-top: 1px solid #5c8ce6;display: ;}
    .custom-groups-content{background-color: #fff;}
    .sample{;line-height: 30px;padding: 0 10px;}
    .sample label{float: left}
    .sample-text span{float: left; background-color: #f5f8ff;padding: 0 3px;height: 20px;margin-right: 5px;margin-bottom: 3px;line-height: 20px;border: 1px solid #e6e6e6}
    .sample-text span i{padding-left: 5px;color: #bdbdbd}
    .sample-text span i:hover{cursor: pointer}
    .sample-empty{height: 20px;border: 1px solid #5c8ce6; color: #5c8ce6;background-color: #fff;float: left;margin-top: 5px}
    .sample-screening{background-color: #f5f5f5;height: 34px;padding: 0 10px;    line-height: 34px;}
    .sample-screening-title{float: left}
    .sample-screening-btn{float: right}
    .sample-screening-btn button{background-color: #5c8ce6;color: #fff; border: none; padding: 0 15px; height: 24px;border-radius: 3px;}
    .sample-text{float: left;width: 590px;margin-top: 4px}
    .sample-category label p {display: inline;}
    .colse-sample{float: right;width: 80px;}
    .colse-sample span{font-size: 24px;display: block;color: #bdbdbd;float: right;padding-left: 5px;cursor: pointer}
    .sample:after,.sample-screening:after{content:"";display: block;clear: both}
    .sample-category{padding-left: 10px;padding-right: 10px}
    .sample-category >div{/*padding: 5px 0;height: 30px;line-height: 35px;*/padding: 4px 0;;line-height: 30px;}
    .category-group label span,.category-position span,.category-grain-weight span,.category-oil-content span,.category-protein span,
    .flowering-data span,.mature-data span,.plant-height span,.grain-color span,.hilum-color span,.cotyledon-colour span,.flower-colour span,
    .pod-color span,.hair-color span,.yield span,.apical-leaflet-length span,.fatty-acid-content span{padding: 0px 10px;margin-left: 5px;display: inline-block}
    .category-more{display: block}
    .category-title{padding-right: 15px;font-weight: normal;float: left;}
    .oil-content-section{float: right}
    .category-grain-weight .grain-weight-section{float: right}
    .category-grain-weight span.category-title{padding-right: 20px}
    .category-grain-weight button,.oil-content-section button{float: right;width: 45px;height: 24px;background-color: #5c8ce6;color: #fff;border: 1px solid #5c8ce6;margin-top: 3px;margin-left: 6px}
    .oil-content-section label input,.grain-weight-section label input{width: 35px;height: 22px;border: 1px solid #c9c9c9;padding: 0 3px;}
    .retract{text-align: center;    height: 35px;    line-height: 35px;    margin-top: 10px;}
    .retract p{cursor: pointer}
    .js-span-ac{background-color: #eff3fa;color: #5c8ce6;}

    input {width: 50px!important; display: inline-block!important;}
</style>
<script>
    var CTXROOT = "${ctxroot}";
</script>

<body data-type="widgets" class="theme-white">
    <div class="am-g tpl-g">
        <!-- 头部 -->
        <%@ include file="/WEB-INF/views/include/dashboard/inside-header.jsp" %>

        <!-- 侧边导航栏 -->
        <div class="left-sidebar">

            <%@ include file="/WEB-INF/views/include/dashboard/nav.jsp" %>

        </div>


        <!-- 内容区域 -->
        <div class="tpl-content-wrapper">
            <div class="row-content am-cf">
                <div class="row">
                    <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                        <div class="widget am-cf">
                            <div class="widget-head am-cf">
                                <div class="widget-title  am-cf">DNA_默认群体设置</div>
                            </div>
                            <div class="widget-body  am-fr">

                                <div class="custom-groups-content">
                                    <div class="sample">
                                        <label><b>样本></b></label>
                                        <div class="sample-text">
                                        </div>
                                        <div class="colse-sample">
                                            <button type="button" class="btn-fill sample-empty">清空</button></div>
                                    </div>
                                    <div class="sample-screening">
                                        <div class="sample-screening-title">样本筛选(共<span class="js-total-samples">0</span>个sample)</div>
                                        <div class="sample-screening-btn">
                                            <button type="button" class="btn">保存群体</button>
                                        </div>
                                    </div>
                                    <div class="sample-category">
                                        <div class="category-group" data-name="species">
                                            <label><b class="category-title">物种:</b><span>Glycine soja</span><span>Glycine gracilis</span><span>Landrace</span><span>Improved cultivar</span><span>Mutant cultivar</span></label>
                                        </div>
                                        <div class="category-position" data-name="locality">
                                            <label><b class="category-title">位置:</b><span>China</span><span>United States</span><span>Japan</span><span>Korea</span><span>Brazil</span><span>Other</span></label>
                                        </div>
                                        <div class="category-grain-weight" data-name="weightPer100seeds">
                                            <label class="category-content"><b class="category-title">百粒重(g):</b><span>0-10</span><span>10-20</span><span>20-30</span><span>30-40</span>
                                            </label>
                                            <div class="grain-weight-section">
                                                <label><input type="text" class="js-category-start" style="ime-mode:disabled;" onpaste="return false;"> g -<input type="text" class="js-category-end" style="ime-mode:disabled;" onpaste="return false;">g</label>
                                                <button type="button" data-name="weightPer100seeds" class="btn js-customize-sample">确定</button>
                                            </div>
                                        </div>
                                        <div class="category-oil-content" data-name="oil">
                                            <label class="category-content"><b class="category-title">含油量(%):</b><span>0-10</span><span>10-15</span><span>15-20</span><span>20-25</span></label>
                                            <div class="oil-content-section">
                                                <label><input type="text">% - <input type="text">%</label>
                                                <button type="button" data-name="oil" class="btn js-customize-sample">确定</button>
                                            </div>
                                        </div>
                                        <div class="category-protein category-more" data-name="protein">
                                            <label><b class="category-title">蛋白质含量(%):</b><span>30-40</span><span>40-50</span><span>50-60</span></label>
                                            <div class="oil-content-section">
                                                <label><input type="text">% - <input type="text">%</label>
                                                <button type="button" data-name="protein" class="btn js-customize-sample">确定</button>
                                            </div>
                                        </div>
                                        <div class="flowering-data category-more" data-name="floweringDate">
                                            <label><b  class="category-title">开花日期(月):</b><span>6</span><span>7</span><span>8</span><span>9</span></label>
                                        </div>
                                        <div class="mature-data category-more" data-name="maturityDate">
                                            <label><b  class="category-title">成熟日期(月):</b><span>8</span><span>9</span><span>10</span><span>11</span></label>
                                        </div>
                                        <div class="plant-height category-more" data-name="height">
                                            <label  class="category-content"><b  class="category-title">株高(cm):</b><span>20-60</span><span>60-100</span><span>100-140</span><span>140-180</span></label>
                                            <div class="oil-content-section">
                                                <label><input type="text">cm - <input type="text">cm</label>
                                                <button type="button" data-name="height" class="btn js-customize-sample">确定</button>
                                            </div>
                                        </div>
                                        <div class="grain-color category-more" data-name="seedCoatColor">
                                            <label>
                                                <b  class="category-title">粒色(种皮色):</b>
                                                <p>
                                                    <span>Bl - Black</span><span>Striped</span><span>Y - Yellow</span><span>Ggn - Grayish green</span>
                                                    <span>Gn - Green</span><span>Rbr - Reddish brown</span><span>Br - Brown</span><span>Lgn - Light green</span>
                                                    <span>Ib - Imperfect black</span><span>Ggbr - Greenish brown</span><span>dull yellow with black hila</span><span>dull yellow with imperfect black hila</span>
                                                    <span>Wye - White yellow</span><span>Lye - Light yellow</span>
                                                </p>
                                            </label>
                                        </div>
                                        <div class="hilum-color category-more" data-name="hilumColor">
                                            <label>
                                                <b  class="category-title">种脐色:</b>
                                                <p>
                                                    <span>Bl - Black</span><span>Br - Brown</span><span>Dbr - Dark Brown</span><span>Lbf - Light buff</span>
                                                    <span>Bf - Buff</span><span>Gn - Green</span><span>Y - Yellow</span><span>Dib - Dark imperfect black</span>
                                                    <span>Brbl - Brown w/black</span><span>Rbr - Reddish brown</span><span>Lbl- Light black</span><span>Ib - Imperfect black</span>
                                                    <span>G - Gray</span><span>Tn - Tan</span><span>H - Hazel</span><span>Gnbr - Greenish-brown</span>
                                                    <span>Dbf - Dark buff</span><span>W - White</span><span>Lye - Light Yellow</span><span>C - Colorless</span>
                                                </p>
                                            </label>
                                        </div>
                                        <div class="cotyledon-colour category-more" data-name="cotyledonColor">
                                            <label><b  class="category-title">子叶色:</b><span>Gn - Green</span><span>Y - Yellow</span></label>
                                        </div>
                                        <div class="flower-colour category-more" data-name="flowerColor">
                                            <label><b  class="category-title">花色:</b><span>Lp - Light purple</span><span>P - Purple</span><span>W - White</span><span>Dp - Dark purple</span></label>
                                        </div>
                                        <div class="pod-color category-more" data-name="podColor">
                                            <label><b  class="category-title">荚色:</b><span>Bl - Black</span><span>Br - Brown</span><span>Dbr - Dark brown</span><span>Tn - Tan</span><span>Lbr - Light brown</span></label>
                                        </div>
                                        <div class="hair-color category-more" data-name="pubescenceColor">
                                            <label><b  class="category-title">茸毛色:</b><span>Br - Brown</span><span>T - Tawny</span><span>Lt - Light tawny</span><span>G - Gray</span><span>Ng - Near gray</span></label>
                                        </div>
                                        <div class="yield category-more" data-name="yield">
                                            <label><b  class="category-title">产量(t/ha):</b><span>0-1</span><span>1-2</span><span>2-3</span><span>3-4</span><span>4-5</span></label>
                                            <div class="oil-content-section">
                                                <label><input type="text">t/ha - <input type="text">t/ha</label>
                                                <button type="button" data-name="yield" class="btn js-customize-sample">确定</button>
                                            </div>
                                        </div>
                                        <div class="apical-leaflet-length category-more" data-name="upperLeafletLength">
                                            <label><b  class="category-title">顶端小叶长度(mm):</b><span>20-30</span><span>30-40</span><span>40-50</span></label>
                                            <div class="oil-content-section">
                                                <label><input type="text">mm - <input type="text">mm</label>
                                                <button type="button" data-name="upperLeafletLength" class="btn js-customize-sample">确定</button>
                                            </div>
                                        </div>
                                        <div class="fatty-acid-content category-more" data-name="linoleic">
                                            <label><b  class="category-title">亚油酸(%):</b><span>40-45</span><span>45-50</span><span>50-55</span><span>55-60</span></label>
                                            <div class="oil-content-section">
                                                <label><input type="text">% - <input type="text">%</label>
                                                <button type="button" data-name="linoleic" class="btn js-customize-sample">确定</button>
                                            </div>
                                        </div>
                                        <div class="fatty-acid-content category-more" data-name="linolenic">
                                            <label><b  class="category-title">亚麻酸(%):</b><span>0-10</span><span>10-15</span><span>15-20</span><span>20-25</span></label>
                                            <div class="oil-content-section">
                                                <label><input type="text">% - <input type="text">%</label>
                                                <button type="button" data-name="linolenic" class="btn js-customize-sample">确定</button>
                                            </div>
                                        </div>
                                        <div class="fatty-acid-content category-more" data-name="oleic">
                                            <label><b  class="category-title">油酸(%):</b><span>5-15</span><span>15-25</span><span>25-35</span></label>
                                            <div class="oil-content-section">
                                                <label><input type="text">% - <input type="text">%</label>
                                                <button type="button" data-name="oleic" class="btn js-customize-sample">确定</button>
                                            </div>
                                        </div>
                                        <div class="fatty-acid-content category-more" data-name="palmitic">
                                            <label><b  class="category-title">软脂酸(%):</b><span>9-15</span><span>15-21</span></label>
                                            <div class="oil-content-section">
                                                <label><input type="text">% - <input type="text">%</label>
                                                <button type="button" data-name="palmitic" class="btn js-customize-sample">确定</button>
                                            </div>
                                        </div>
                                        <div class="fatty-acid-content category-more" data-name="stearic">
                                            <label><b  class="category-title">硬脂酸(%):</b><span>2-3</span><span>3-6</span></label>
                                            <div class="oil-content-section">
                                                <label><input type="text">% - <input type="text">%</label>
                                                <button type="button" data-name="stearic" class="btn js-customize-sample">确定</button>
                                            </div>
                                        </div>
                                    </div>

                                </div>

                                <form id="dnaForm" style="display: none;" class="am-form tpl-form-border-form tpl-form-border-br" modelAttribute="dnaGroups" method="post" action="${ctxroot}/dnagroups/saveadd">
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">name</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="name" class="name" name="name" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">condition</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="condition" class="condition" name="condition" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>


                                    <div class="am-form-group">
                                        <div class="am-u-sm-9 am-u-sm-push-3">
                                            <button type="submit" class="am-btn am-btn-primary tpl-btn-bg-color-success dna-btn ">提交</button>

                                        </div>
                                    </div>
                                </form>

                                <%@ include file="/WEB-INF/views/include/backbutton.jsp" %>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
    <%--<script src="${ctxStatic}/dashboard/js/amazeui.datatables.min.js"></script>--%>
    <%--<script src="${ctxStatic}/dashboard/js/dataTables.responsive.min.js"></script>--%>
<script>
    $(function(){

        var popuSamples = {}; // 存储选中的样本数据

        /* 自定义样本选中 */
        $("body").on("click", ".sample-category > div span", function(){
            $(this).parent().find("span").removeClass("js-span-ac");
            $(this).addClass("js-span-ac");

            var sampleName = $(this).parents("label").parent().attr("data-name"),
                    sampleValue = $(this).text();
            popuSamples[sampleName] = sampleValue;
            renderSampleText();

            var arr = [];
            for(var i in popuSamples) {
                arr.push(getKeyName(i) + popuSamples[i]);
            }
            var obj = {"name":arr.join(","), "condition":getStandardPopulation(popuSamples)};
            $.ajax({
                url: CTXROOT + "/dna/queryByGroup",
                data: {group: JSON.stringify(obj), pageNo: 1 || 1, pageSize: 10},
                type: "POST",
                dataType: "json",
                success: function(res) {
                    $(".js-total-samples").html(res.total);
                }
            });
        });

        // 生成样本文字
        function renderSampleText() {
            var str = '';
            for(var name in popuSamples) {
                str += '<span data-name="'+ name +'">'+ getKeyName(name) + popuSamples[name] +'<i class="js-colse-text">X</i></span>'
            }
            $(".sample-text").empty().append(str);
        }

        /* 样本清空 */
        $(".sample-empty").click(function(){
            var samples = $(".sample-text").find("span");
            $.each(samples, function(idx, item) {
                $(item).find(".js-colse-text").trigger("click");
            });
            $(".js-total-samples").html(0);
        });

        /* 删除单个样本条件 */
        $(".sample-text").on("click", ".js-colse-text", function(){
            $(this).parent().remove();
            var name = $(this).parent().attr("data-name");
            delete(popuSamples[name]);

            $(".sample-category").find("div[data-name='"+name+"']").find("span").removeClass("js-span-ac");
        });

        /* 自定义 样本 */
        $(".js-customize-sample").click(function() {
            var name = $(this).attr("data-name");
            var _prev = $(this).prev();
            var val1 = _prev.find("input").first().val();
            var val2 = _prev.find("input").last().val();

            $(".sample-category").find("div[data-name='"+name+"']").find("label").first().append("<span>"+ val1 + "-" + val2 +"</span>")
        });

        // 生成后台需要的单个群体数据结构
        function getStandardPopulation(obj) {
            // 定义有范围的样本属性
            var rangeCondition = ['weightPer100seeds', 'oil', 'protein', 'height', 'yield', 'upperLeafletLength', 'linoleic', 'linolenic', 'oleic', 'palmitic', 'stearic'];
            var condition = {};
            for(key in obj) {
                if($.inArray(key, rangeCondition) > -1) {
                    condition[key] = {};
                    condition[key]["min"] = obj[key].split("-")[0];
                    condition[key]["max"] = obj[key].split("-")[1];
                } else {
                    condition[key] = obj[key];
                }
            }
            return condition;
        }

        /* 保存群体 */
        $(".sample-screening-btn button").click(function(){
            console.log(popuSamples);
            var arr = [];
            for(var i in popuSamples) {
                arr.push(getKeyName(i) + popuSamples[i]);
            }
            var obj = {"name":arr.join(","), "condition":getStandardPopulation(popuSamples)};
            console.log(obj);
            $("#dnaForm").find(".name").val(obj.name);
            $("#dnaForm").find(".condition").val(JSON.stringify(obj.condition));
            $(".dna-btn").trigger("click");

            $(".sample-empty").trigger("click");

        });

        // 获取样本中文名
        function getKeyName (key) {
            switch (key) {
                case 'species':
                    return "物种";
                case 'locality':
                    return "位置";
                case 'weightPer100seeds':
                    return "百粒重";
                case 'oil':
                    return "含油量";
                case 'protein':
                    return "蛋白质";
                case 'floweringDate':
                    return "开花日期";
                case 'maturityDate':
                    return "成熟日期";
                case 'height':
                    return "株高";
                case 'seedCoatColor':
                    return "粒色";
                case 'hilumColor':
                    return "种脐色";
                case 'cotyledonColor':
                    return "子叶色";
                case 'flowerColor':
                    return "花色";
                case 'podColor':
                    return "荚色";
                case 'pubescenceColor':
                    return "茸毛色";
                case 'yield':
                    return "产量";
                case 'upperLeafletLength':
                    return "顶端小叶长度";
                case 'linoleic':
                    return "亚油酸";
                case 'linolenic':
                    return "亚麻酸";
                case 'oleic':
                    return "油酸";
                case 'palmitic':
                    return "软脂酸";
                case 'stearic':
                    return "硬脂酸";
                default :
                    return key;
            }
        }
    })
</script>

</body>

</html>