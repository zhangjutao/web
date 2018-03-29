<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<style>
    #popu-paginate .total-page-count {
        display: inline-block !important;
        height: 28px;
        top: 0px;
    }
    .label-txt {
        cursor: pointer;
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        width: 140px;
    }

    .paramTag img {
        width: 15px;
        vertical-align: middle;
    }

    .inputComponent {
        margin-top: 6px;
        display: none;
        position: absolute;
        /*border: 1px solid #5c8ce6;*/
        border: 1px solid #0f9145;
        z-index: 1;
        padding: 15px 10px;
        width: 114px;
        background-color: #fff;
        margin-left: -16px;
    }

    .inputStyle {
        width: 90px;
    }

    .inputComponent .selectOperate {
        width: 30px;
    }

    .inputComponent p a {
        padding: 2px 8px;
        display: inline-block;
        /*border: 1px solid #5c8ce6;*/
        border: 1px solid #0f9145;
        border-radius: 3px;
    }

    .btnCancel {
        margin-right: 5px;
        /*color: #5c8ce6;*/
        color: #0f9145;
    }

    a {
        color: #000;
        text-decoration: none;
    }

    .inputComponent p {
        margin-top: 10px;
    }

    .btnConfirmInfo {
        /*background: #5c8ce6;*/
        background: #0f9145;
        color: #fff;
    }

    .js-cursom-add2 .label-txt {
        vertical-align: bottom;
    }

    .js-ad-dd {
        padding: 3px 8px;
    }

    body .tab-detail {
        left: 50%;
        margin-left: -450px;
        border: none;
    }

    #tableBody .zwsj {
        padding: 20px 10px;
        text-align: left;
    }

    #tableBody2 .zwsj {
        padding: 20px 10px;
        text-align: left;
    }

    .gene-search-list label {
        display: flex !important;
    }

    .setRadio {
        position: relative;
        top: 10px;
        width: 13px;
        height: 13px;
    }

    .setLength {
        cursor: pointer;
        /*display: inline-block;*/
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        width: 220px;
        padding-left: 3px;
    }

    .total-page-count {
        position: relative;
    }
    .label-txt {
        vertical-align: bottom;
    }
   #tagsPagination #total-page-count{position: relative;top:0px;}
</style>


<div class="nav_ac">
    <div class="icon-right"><img src="${ctxStatic}/images/Category.png"></div>
</div>
<div class="aside box-shadow">
    <div class="item-header">
        <div class="icon-left"><img src="${ctxStatic}/images/bookmarks.png">SNP/INDEL</div>
        <div class="icon-right"><img src="${ctxStatic}/images/Category.png"></div>
    </div>
    <div class="selcet">
        <select>
            <option class="region" data-value="region">search for SNPs/INDELs in Region</option>
            <option data-value="gene">search for SNPs/INDELs in Gene</option>
        </select>
    </div>
    <div class="select-item select-chorosome">
        <div class="select-item-hd">Select Chromosome</div>
        <div class="select-item-bd">
            <div class="item-bd-list">
                <span>Chromosome</span>
                <select class="js-chorosome">
                    <%--<option value="Chr01" data-max="164327">chr316</option>--%>
                </select>
            </div>
            <div class="item-bd-list"><span>Start Position</span><input
                    onkeyup="this.value=this.value.replace(/\D/g,'')" type="number" min="0" placeholder="请输入数值"
                    class="js-start-position" value="0"></div>
            <div class="item-bd-list"><span>End Position</span><input onkeyup="this.value=this.value.replace(/\D/g,'')"
                                                                      type="number" min="0" placeholder="<=164327"
                                                                      class="js-end-position"></div>
        </div>
    </div>
    <div class="select-item selcet-gene-function">
        <div class="select-item-hd">Search for SNPs/INDELs with Gene and Function</div>
        <div calss="select-item-bd">
            <div class="gene-search">
                <label>
                    <input class="js-search-gene-text" type="text" placeholder="请输入关键词"/>
                    <span class="js-search-gene-btn"><img src="${ctxStatic}/images/search.png">搜索</span>
                </label>
            </div>
            <div class="errorBoxShow">
                没有找到您要查询的内容，请尝试其他搜索词
            </div>
            <div class="gene-search-list">
            </div>
        </div>
    </div>
    <div class="select-item selcet-set-length">
        <div class="select-item-hd">Set length</div>
        <div class="select-item-bd">
            <div class="item-bd-list"><span>Upstream: </span><input onkeyup="this.value=this.value.replace(/\D/g,'')"
                                                                    type="number" min="0" class="js-up-stream"
                                                                    placeholder="bp"></div>
            <div class="item-bd-list"><span>Downstream: </span><input onkeyup="this.value=this.value.replace(/\D/g,'')"
                                                                      type="number" min="0" class="js-down-stream"
                                                                      placeholder="bp"></div>
        </div>
    </div>
    <div class="select-item select-populations">
        <div class="select-item-hd">
            <span>Select Populations</span>
            <div class="custom-groups">
                <span class="custom-groups-btn">选择群体/品种</span>
                <div class="cover"></div>
                <div class="custom-groups-content" style="overflow: hidden;">
                    <div class="sample">
                        <label><b>样本></b></label>
                        <div class="sample-text" id="sampleText"></div>
                        <div class="colse-sample">
                            <button type="button" class="btn-fill sample-empty">清空</button>
                            <span>X</span></div>
                    </div>
                    <%--// 新增选择品种 begin --%>

                    <div id="addTags">
                        <span class="tagColor popCnt1" style="margin-left:10px;">选择群体</span>
                        <span class="kindCnt1" id="kindSelect">选择品种</span>
                        <%--<span style="background:#000;width:20px;height:20px;opacity:0.3;"></span>--%>
                    </div>
                    <%--// 新增选择品种 end --%>
                    <div class="sample-screening">
                        <div class="sample-screening-title">样本筛选(共<span class="js-total-samples">0</span>个sample)</div>
                        <div class="sample-screening-btn">
                            <input type="button" class="btn resetBtn" value="重置"/>
                            <%--<button type="button" class="btn" style="background:#ccc;">保存群体</button>--%>
                                <p id="hiddenP" style="background:#ccc;width:90px;height:30px;line-height:30px;text-align:center;border-radius: 3px;cursor: not-allowed;    margin-top: 2px;">保存群体</p>
                            <input type="button" class="btn saveKind" value="保存群体"/>
                        </div>
                    </div>
                    <div class="sample-category popCnt1">
                        <div class="category-group" data-name="species">
                            <label><b
                                    class="category-title">物种:</b><span>Pleurotus tuoliensis</span></label>
                            <%--<button class="multiselect"><i>+</i>多选</button>--%>
                        </div>
                        <div class="category-position" data-name="locality">
                            <label><b class="category-title">位置:</b><span>Toli,China;</span><span>Yumin,China;</span><span>Tacheng,China</span><span>Beijing,China</span><span>Shihezi,China</span><span>Qinghe,China</span><span>Fuyun,China</span><span>Xinjiang,China</span></label>
                            <%--<button class="multiselect"><i>+</i>多选</button>--%>
                        </div>
                        <div class="category-grain-weight" data-name="weightPer100seeds">
                            <label class="category-content"><b class="category-title">类型:</b><span>cultivated</span><span>wild</span>
                                <%--<span class="js-custom-add"></span>--%>
                            </label>
                            <%--<div class="grain-weight-section">--%>
                                <%--<div class="input-range">--%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"--%>
                                           <%--class="js-category-start" style="ime-mode:disabled;" onpaste="return false;">--%>
                                    <%--- <input type="number" onkeyup="this.value=this.value.replace(/\D/g,'')" min="0"--%>
                                             <%--class="js-category-end" style="ime-mode:disabled;" onpaste="return false;">--%>
                                <%--</div>--%>
                                <%--<button type="button" data-name="weightPer100seeds" class="btn js-customize-sample">确定--%>
                                <%--</button>--%>
                            <%--</div>--%>
                        </div>
                        <div class="category-oil-content" data-name="oil">
                            <label class="category-content"><b class="category-title">材料:</b><span>mycelium</span></label>
                            <%--<div class="oil-content-section">--%>
                                <%--<div class="input-range"><input type="number" min="0"--%>
                                                                <%--onkeyup="this.value=this.value.replace(/\D/g,'')"> ---%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                                <%--</div>--%>
                                <%--<button type="button" data-name="oil" class="btn js-customize-sample">确定</button>--%>
                            <%--</div>--%>
                        </div>
                        <div class="category-protein " data-name="protein">
                            <label><b class="category-title">分类地位:</b><span>Basidiomycetes</span><span>wood-rottingfungi</span></label>
                            <%--<div class="oil-content-section">--%>
                                <%--<div class="input-range"><input type="number" min="0"--%>
                                                                <%--onkeyup="this.value=this.value.replace(/\D/g,'')"> ---%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                                <%--</div>--%>
                                <%--<button type="button" data-name="protein" class="btn js-customize-sample">确定</button>--%>
                            <%--</div>--%>
                        </div>
                        <%--<div class="flowering-data " data-name="floweringDate">--%>
                        <%--<label><b  class="category-title">开花日期(月):</b><span>I</span><span>II</span><span>III</span></label>--%>
                        <%--</div>--%>
                        <div class="mature-data " data-name="maturityDate">
                            <label><b class="category-title">菌丝形态 :</b><span>stolonmycelium</span></label>
                        </div>
                        <div class="mature-data " data-name="maturityDate">
                            <label><b class="category-title">菌丝颜色 :</b><span>white</span></label>
                        </div>
                        <div class="mature-data " data-name="maturityDate">
                            <label><b class="category-title">孢子颜色 :</b><span>white</span></label>
                        </div>
                        <div class="mature-data " data-name="maturityDate">
                            <label><b class="category-title">孢子形态 :</b><span>Oval</span></label>
                        </div>
                        <div class="mature-data " data-name="maturityDate">
                            <label><b class="category-title">子实体颜色 :</b><span>white</span></label>
                        </div>
                        <div class="mature-data " data-name="maturityDate">
                            <label><b class="category-title">子实体形态 :</b><span>cluster</span></label>
                        </div>
                        <div class="mature-data " data-name="maturityDate">
                            <label><b class="category-title">菌盖颜色 :</b><span>Palmlike</span></label>
                        </div>
                        <div class="mature-data " data-name="maturityDate">
                            <label><b class="category-title">菌柄形态 :</b><span>solid</span></label>
                        </div>
                        <div class="mature-data " data-name="maturityDate">
                            <label><b class="category-title">菌柄颜色 :</b><span>white</span></label>
                        </div>


                        <%--<div class="plant-height" data-name="height">--%>
                            <%--<label class="category-content">--%>
                                <%--<b class="category-title">株高(cm):</b>--%>
                                <%--<span>20-60</span>--%>
                                <%--<span>60-100</span>--%>
                                <%--<span>100-140</span>--%>
                                <%--<span>140-180</span>--%>
                            <%--</label>--%>
                            <%--<div class="oil-content-section">--%>
                                <%--<div class="input-range">--%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"> ---%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                                <%--</div>--%>
                                <%--<button type="button" data-name="height" class="btn js-customize-sample">确定</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="grain-color " data-name="seedCoatColor">--%>
                            <%--<label>--%>
                                <%--<b class="category-title">粒色(种皮色):</b>--%>
                                <%--<p>--%>
                                    <%--<span>Bl - Black</span><span>Striped</span><span>Y - Yellow</span><span>Ggn - Grayish green</span>--%>
                                    <%--<span>Gn - Green</span><span>Rbr - Reddish brown</span><span>Br - Brown</span><span>Lgn - Light green</span>--%>
                                    <%--<span>Ib - Imperfect black</span><span>Gnbr - Greenish brown</span><span>dull yellow with black hila</span><span>dull yellow with imperfect black hila</span>--%>
                                    <%--<span>Wye - White yellow</span><span>Lye - Light yellow</span>--%>
                                <%--</p>--%>
                            <%--</label>--%>
                        <%--</div>--%>
                        <%--<div class="hilum-color category-more" data-name="hilumColor">--%>
                            <%--<label>--%>
                                <%--<b class="category-title">种脐色:</b>--%>
                                <%--<p>--%>
                                    <%--<span>Bl - Black</span><span>Br - Brown</span><span>Dbr - Dark Brown</span><span>Lbf - Light buff</span>--%>
                                    <%--<span>Bf - Buff</span><span>Gn - Green</span><span>Y - Yellow</span><span>Dib - Dark imperfect black</span>--%>
                                    <%--<span>Brbl - Brown w/black</span><span>Rbr - Reddish brown</span><span>Lbl - Light black</span><span>Ib - Imperfect black</span>--%>
                                    <%--<span>G - Gray</span><span>Tn - Tan</span><span>H - Hazel</span><span>Gnbr - Greenish-brown</span>--%>
                                    <%--<span>Dbf - Dark buff</span><span>W - White</span><span>Lye - Light Yellow</span><span>C - Colorless</span>--%>
                                <%--</p>--%>
                            <%--</label>--%>
                        <%--</div>--%>
                        <%--<div class="cotyledon-colour category-more" data-name="cotyledonColor">--%>
                            <%--<label>--%>
                                <%--<b class="category-title">子叶色:</b>--%>
                                <%--<span>Gn - Green</span>--%>
                                <%--<span>Y - Yellow</span>--%>
                            <%--</label>--%>
                        <%--</div>--%>
                        <%--<div class="flower-colour category-more" data-name="flowerColor">--%>
                            <%--<label>--%>
                                <%--<b class="category-title">花色:</b>--%>
                                <%--<span>Lp - Light purple</span>--%>
                                <%--<span>P - Purple</span>--%>
                                <%--<span>W - White</span>--%>
                                <%--<span>Dp - Dark purple</span>--%>
                            <%--</label>--%>
                        <%--</div>--%>
                        <%--<div class="pod-color category-more" data-name="podColor">--%>
                            <%--<label><b class="category-title">荚色:</b><span>Bl - Black</span><span>Br - Brown</span><span>Dbr - Dark brown</span><span>Tn - Tan</span><span>Lbr - Light brown</span></label>--%>
                        <%--</div>--%>
                        <%--<div class="hair-color category-more" data-name="pubescenceColor">--%>
                            <%--<label><b class="category-title">茸毛色:</b><span>Br - Brown</span><span>T - Tawny</span><span>Lt - Light tawny</span><span>G - Gray</span><span>Ng - Near gray</span></label>--%>
                        <%--</div>--%>
                        <%--<div class="yield category-more" data-name="yield">--%>
                            <%--<label>--%>
                                <%--<b class="category-title">产量(Mg/ha):</b>--%>
                                <%--<span>0-1</span>--%>
                                <%--<span>1-2</span>--%>
                                <%--<span>2-3</span><span>3-4</span><span>4-5</span></label>--%>
                            <%--<div class="oil-content-section">--%>
                                <%--<div class="input-range"><input type="number" min="0"--%>
                                                                <%--onkeyup="this.value=this.value.replace(/\D/g,'')"> ---%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                                <%--</div>--%>
                                <%--<button type="button" data-name="yield" class="btn js-customize-sample">确定</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="apical-leaflet-length category-more" data-name="upperLeafletLength">--%>
                            <%--<label><b class="category-title">顶端小叶长度(mm):</b><span>20-30</span><span>30-40</span><span>40-50</span></label>--%>
                            <%--<div class="oil-content-section">--%>
                                <%--<div class="input-range"><input type="number" min="0"--%>
                                                                <%--onkeyup="this.value=this.value.replace(/\D/g,'')"> ---%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                                <%--</div>--%>
                                <%--<button type="button" data-name="upperLeafletLength" class="btn js-customize-sample">--%>
                                    <%--确定--%>
                                <%--</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="fatty-acid-content category-more" data-name="linoleic">--%>
                            <%--<label><b--%>
                                    <%--class="category-title">亚油酸(%):</b><span>40-45</span><span>45-50</span><span>50-55</span><span>55-60</span></label>--%>
                            <%--<div class="oil-content-section">--%>
                                <%--<div class="input-range"><input type="number" min="0"--%>
                                                                <%--onkeyup="this.value=this.value.replace(/\D/g,'')"> ---%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                                <%--</div>--%>
                                <%--<button type="button" data-name="linoleic" class="btn js-customize-sample">确定</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="fatty-acid-content category-more" data-name="linolenic">--%>
                            <%--<label><b--%>
                                    <%--class="category-title">亚麻酸(%):</b><span>0-10</span><span>10-15</span><span>15-20</span><span>20-25</span></label>--%>
                            <%--<div class="oil-content-section">--%>
                                <%--<div class="input-range"><input type="number" min="0"--%>
                                                                <%--onkeyup="this.value=this.value.replace(/\D/g,'')"> ---%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                                <%--</div>--%>
                                <%--<button type="button" data-name="linolenic" class="btn js-customize-sample">确定</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="fatty-acid-content category-more" data-name="oleic">--%>
                            <%--<label><b--%>
                                    <%--class="category-title">油酸(%):</b><span>5-15</span><span>15-25</span><span>25-35</span></label>--%>
                            <%--<div class="oil-content-section">--%>
                                <%--<div class="input-range"><input type="number" min="0"--%>
                                                                <%--onkeyup="this.value=this.value.replace(/\D/g,'')"> ---%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                                <%--</div>--%>
                                <%--<button type="button" data-name="oleic" class="btn js-customize-sample">确定</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="fatty-acid-content category-more" data-name="palmitic">--%>
                            <%--<label><b class="category-title">软脂酸(%):</b><span>9-15</span><span>15-21</span></label>--%>
                            <%--<div class="oil-content-section">--%>
                                <%--<div class="input-range"><input type="number" min="0"--%>
                                                                <%--onkeyup="this.value=this.value.replace(/\D/g,'')"> ---%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                                <%--</div>--%>
                                <%--<button type="button" data-name="palmitic" class="btn js-customize-sample">确定</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="fatty-acid-content category-more" data-name="stearic">--%>
                            <%--<label><b class="category-title">硬脂酸(%):</b><span>2-3</span><span>3-6</span></label>--%>
                            <%--<div class="oil-content-section">--%>
                                <%--<div class="input-range"><input type="number" min="0"--%>
                                                                <%--onkeyup="this.value=this.value.replace(/\D/g,'')"> ---%>
                                    <%--<input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                                <%--</div>--%>
                                <%--<button type="button" data-name="stearic" class="btn js-customize-sample">确定</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </div>
                    <%--<div class="retract popCnt1"><p>更多选项(种脐色、花色、产量等)<img src="${ctxStatic}/images/more_unfold.png"></p>--%>
                    <%--</div>--%>
                    <div id="tagKind" class="kindCnt1">
                        <div style="overflow-x: scroll;height: 419px;">
                            <table style="overflow-x: scroll;" cellpadding="0" cellspacing="0" style="height:268px;">
                                <thead style="overflow-x: scroll;width:730px;">
                                <tr>
                                    <th class="paramTag" style="width:42px;"></th>
                                    <%--<th class="paramTag" style="width:42px;">ID</th>--%>
                                    <th class="paramTag">测序样品编号
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="runNo inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">物种名称
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="scientificName inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">编号
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="sampleId inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">菌株名称
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="strainName inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">地理位置
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="locality inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">保藏地点
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入"
                                                   class="preservationLocation inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">类型
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="type inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">培养环境
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="environment inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">材料
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="materials inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">处理
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="treat inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <%--<th class="paramTag popMoveOn" style="position:relative;">时间--%>
                                    <%--<img src="/dna/static/images/arrow-drop-down.png" alt="logo"--%>
                                    <%--style="width: 15px;vertical-align: middle;">--%>
                                    <%--<div class="popNames">--%>
                                    <%--<ul>--%>
                                    <%--<li>Q1</li>--%>
                                    <%--<li>Q2</li>--%>
                                    <%--<li>Q3</li>--%>
                                    <%--<li>Q4</li>--%>
                                    <%--<li>Q5</li>--%>
                                    <%--<li>Q6</li>--%>
                                    <%--<li>Q7</li>--%>
                                    <%--<li>Q8</li>--%>
                                    <%--<li>Q9</li>--%>
                                    <%--<li>Q10</li>--%>
                                    <%--</ul>--%>
                                    <%--</div>--%>
                                    <%--</th>--%>
                                    <th class="paramTag">采集时间
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="time inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">分类地位
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="taxonomy inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">菌丝形态
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="myceliaPhenotype inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">菌丝直径
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="myceliaDiameter inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">菌丝颜色
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="myceliaColor inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">孢子颜色
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="sporesColor inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">孢子形态
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="sporesShape inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">锁状联合
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="clampConnection inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">菌盖形态
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="pileusPhenotype inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">菌盖颜色
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="pileusColor inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">菌柄形态
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="stipePhenotype inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">菌柄颜色
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="stipeColor inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">子实体颜色
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="fruitbodyColor inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">子实体形态
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="fruitbodyType inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">光照
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="illumination inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">菌环
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="collarium inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">菌托
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="volva inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">菌幕
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="velum inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">菌核
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="sclerotium inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">菌种培养基
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="strainMedium inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">主要栽培基质
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="mainSubstrate inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">后熟期
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="afterRipeningStage inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">原基刺激&子实体
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入"
                                                   class="primordialStimulationFruitbody inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                    <th class="paramTag">生殖方式
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="reproductiveMode inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">生活方式
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="lifestyle inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">保藏方法
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="preservation inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">驯化
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="domestication inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">核相
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="nuclearPhase inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>

                                    <th class="paramTag">交配型
                                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                                        <div class="inputComponent">
                                            <input type="text" placeholder="请输入" class="matingType inputStyle">
                                            <p>
                                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                            </p>
                                        </div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody style="overflow-x: scroll;width:730px;" id="tagTBody">
                                </tbody>
                            </table>
                        </div>
                        <%--laypage 分页 插件  begin--%>
                        <div class="checkbox-item-tab" id="tagsPagination">
                            <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div class="select-item-bd">
            <div class="js-default-add"></div>
            <div class="js-cursom-add2"></div>
            <div class="js-cursom-add"></div>
        </div>
    </div>
    <div class="confirmed-btn">
        <!-- 该事件封到dna-index.jsp页面dna.js中 -->
        <button type="button" class="btn js-panel-btn">确定</button>
    </div>
</div>
<div id="mid"></div>
<div id="tab-detail" class="tab-detail ui-widget-content">
    <div class="tab-detail-thead">
        <p style="position:relative;" id="tabDetailTitle">
            <span style="display:inline-block;text-align:center;max-width:600px;overflow:hidden;text-overflow: ellipsis; white-space: nowrap;">1</span>
            <i style="position:relative;top:-15px;color:#fff;">号群体/品种属性信息</i>

            <a href="javascript:void(0)">X</a>
        </p>
    </div>
    <div class="table-item popu-checkbox" style="display:none; box-shadow: none;">
        <div class="checkbox-item">
            <div class="che-list ">
                <span class="tab-title">表格内容:</span>
                <dl class="table_header_setting js-table-header-setting-popu">
                    <dd><label for="runNo" class="checkbox-ac"><span id="runNo" data-value="runNo"></span>测序样品编号</label>
                    </dd>
                    <dd><label for="scientificName" class="checkbox-ac"><span id="scientificName"
                                                                              data-value="scientificName"></span>物种名称</label>
                    </dd>
                    <dd><label for="sampleId" class="checkbox-ac"><span id="sampleId"
                                                                        data-value="sampleId"></span>编号</label></dd>
                    <dd><label for="strainName" class="checkbox-ac"><span id="strainName"
                                                                          data-value="strainName"></span>菌株名称</label>
                    </dd>
                    <dd><label for="locality" class="checkbox-ac"><span id="locality" data-value="locality"></span>地理位置</label>
                    </dd>
                    <dd><label for="preservationLocation" class="checkbox-ac"><span id="preservationLocation"
                                                                                    data-value="preservationLocation"></span>保藏地点</label>
                    </dd>
                    <dd><label for="type" class="checkbox-ac"><span id="type" data-value="type"></span>类型</label></dd>
                    <%--<dd><label for="floweringDate" class="checkbox-ac"><span id="floweringDate" data-value="floweringDate"></span>开花日期</label></dd><span></span>--%>
                    <dd><label for="environment" class="checkbox-ac"><span id="environment"
                                                                           data-value="environment"></span>培养环境</label>
                    </dd>
                    <dd><label for="materials" class="checkbox-ac"><span id="materials" data-value="materials"></span>材料</label>
                    </dd>
                    <dd><label for="treat" class="checkbox-ac"><span id="treat" data-value="treat"></span>处理</label>
                    </dd>
                    <dd><label for="time" class="checkbox-ac"><span id="time" data-value="time"></span>采集时间</label></dd>
                    <dd><label for="taxonomy" class="checkbox-ac"><span id="taxonomy" data-value="taxonomy"></span>分类地位</label>
                    </dd>
                    <dd><label for="myceliaPhenotype" class="checkbox-ac"><span id="myceliaPhenotype"
                                                                                data-value="myceliaPhenotype"></span>菌丝形态</label>
                    </dd>
                    <dd><label for="myceliaDiameter" class="checkbox-ac"><span id="myceliaDiameter"
                                                                               data-value="myceliaDiameter"></span>菌丝直径</label>
                    </dd>
                    <dd><label for="myceliaColor" class="checkbox-ac"><span id="myceliaColor"
                                                                            data-value="myceliaColor"></span>菌丝颜色</label>
                    </dd>
                    <dd><label for="sporesColor" class="checkbox-ac"><span id="sporesColor"
                                                                           data-value="sporesColor"></span>孢子颜色</label>
                    </dd>
                    <dd><label for="sporesShape" class="checkbox-ac"><span id="sporesShape"
                                                                           data-value="sporesShape"></span>孢子形态</label>
                    </dd>
                    <dd><label for="clampConnection" class="checkbox-ac"><span id="clampConnection"
                                                                               data-value="clampConnection"></span>锁状联合</label>
                    </dd>
                    <dd><label for="pileusPhenotype" class="checkbox-ac"><span id="pileusPhenotype"
                                                                               data-value="pileusPhenotype"></span>菌盖形态</label>
                    </dd>
                    <dd><label for="pileusColor" class="checkbox-ac"><span id="pileusColor"
                                                                           data-value="pileusColor"></span>菌盖颜色</label>
                    </dd>
                    <dd><label for="stipePhenotype" class="checkbox-ac"><span id="stipePhenotype"
                                                                              data-value="stipePhenotype"></span>菌柄形态</label>
                    </dd>
                    <dd><label for="stipeColor" class="checkbox-ac"><span id="stipeColor"
                                                                          data-value="stipeColor"></span>菌柄颜色</label>
                    </dd>
                    <dd><label for="fruitbodyColor" class="checkbox-ac"><span id="fruitbodyColor"
                                                                              data-value="fruitbodyColor"></span>子实体颜色</label>
                    </dd>
                    <dd><label for="fruitbodyType" class="checkbox-ac"><span id="fruitbodyType"
                                                                             data-value="fruitbodyType"></span>子实体形态</label>
                    </dd>
                    <dd><label for="illumination" class="checkbox-ac"><span id="illumination"
                                                                            data-value="illumination"></span>光照</label>
                    </dd>
                    <dd><label for="collarium" class="checkbox-ac"><span id="collarium" data-value="collarium"></span>菌环</label>
                    </dd>
                    <dd><label for="volva" class="checkbox-ac"><span id="volva" data-value="volva"></span>菌托</label>
                    </dd>
                    <dd><label for="velum" class="checkbox-ac"><span id="velum" data-value="velum"></span>菌幕</label>
                    </dd>
                    <dd><label for="sclerotium" class="checkbox-ac"><span id="sclerotium"
                                                                          data-value="sclerotium"></span>菌核</label></dd>
                    <dd><label for="strainMedium" class="checkbox-ac"><span id="strainMedium"
                                                                            data-value="strainMedium"></span>菌种培养基</label>
                    </dd>
                    <dd><label for="mainSubstrate" class="checkbox-ac"><span id="mainSubstrate"
                                                                             data-value="mainSubstrate"></span>主要栽培基质</label>
                    </dd>
                    <dd><label for="afterRipeningStage" class="checkbox-ac"><span id="afterRipeningStage"
                                                                                  data-value="afterRipeningStage"></span>后熟期</label>
                    </dd>
                    <dd><label for="primordialStimulationFruitbody" class="checkbox-ac"><span
                            id="primordialStimulationFruitbody" data-value="primordialStimulationFruitbody"></span>原基刺激&子实体</label>
                    </dd>
                    <dd><label for="reproductiveMode" class="checkbox-ac"><span id="reproductiveMode"
                                                                                data-value="reproductiveMode"></span>生殖方式</label>
                    </dd>
                    <dd><label for="lifestyle" class="checkbox-ac"><span id="lifestyle" data-value="lifestyle"></span>生活方式</label>
                    </dd>
                    <dd><label for="preservation" class="checkbox-ac"><span id="preservation"
                                                                            data-value="preservation"></span>保藏方法</label>
                    </dd>
                    <dd><label for="domestication" class="checkbox-ac"><span id="domestication"
                                                                             data-value="domestication"></span>驯化</label>
                    </dd>
                    <dd><label for="nuclearPhase" class="checkbox-ac"><span id="nuclearPhase"
                                                                            data-value="nuclearPhase"></span>核相</label>
                    </dd>
                    <dd><label for="matingType" class="checkbox-ac"><span id="matingType"
                                                                          data-value="matingType"></span>交配型</label>
                    </dd>
                </dl>
            </div>
        </div>
        <div class="export-data">
            <p class="btn-export-set">
                <button type="button" class="btn btn-export js-export-popu"><img src="${ctxStatic}/images/export.png">导出数据
                </button>
            </p>
        </div>
        <div class="choose-default">
            <div class="btn-default">
                <label><span class="js-choose-all testClass whiteOk" id="allSelected"></span>全选</label>
                <%--<label class="js-btn-default btn-default-ac"><span></span>默认</label>--%>
            </div>
            <div class="btn-group" style="display: block;">
                <button type="button" class="btn-fill btn-confirm js-popu-setting-btn">确认</button>
                <button type="button" class="btn-chooseAll js-clear-btn" id="clear-all">清空</button>
                <button type="button" class="btn-toggle">收起<img src="${ctxStatic}/images/down.png"></button>
            </div>
        </div>
    </div>
    <div class="export-data">
        <p class="btn-export-set">
            <button type="button" class="btn popu-set-up"><img src="${ctxStatic}/images/set.png">表格设置</button>
            <button type="button" class="btn btn-export js-export-popu"><img src="${ctxStatic}/images/export.png">导出数据
            </button>
        </p>
    </div>
    <div class="tab-detail-tbody" style="max-height: 500px; overflow-y: auto;">
        <table class="popu-table">
            <thead>
            <tr>
                <td class="runNo">测序样品编号</td>
                <td class="scientificName">物种名称</td>
                <td class="sampleId">编号</td>
                <td class="strainName">菌株名称</td>
                <td class="locality">地理位置</td>
                <td class="preservationLocation">保藏地点</td>
                <td class="type">类型</td>
                <td class="environment">培养环境</td>
                <td class="materials">材料</td>
                <td class="treat">处理</td>
                <td class="time">采集时间</td>
                <td class="taxonomy">分类地位</td>
                <td class="myceliaPhenotype">菌丝形态</td>
                <td class="myceliaDiameter">菌丝直径</td>
                <td class="myceliaColor">菌丝颜色</td>
                <td class="sporesColor">孢子颜色</td>
                <td class="sporesShape">孢子形态</td>
                <td class="clampConnection">锁状联合</td>
                <td class="pileusPhenotype">菌盖形态</td>
                <td class="pileusColor">菌盖颜色</td>
                <td class="stipePhenotype">菌柄形态</td>
                <td class="stipeColor">菌柄颜色</td>
                <td class="fruitbodyColor">子实体颜色</td>
                <td class="fruitbodyType">子实体形态</td>
                <td class="illumination">光照</td>
                <td class="collarium">菌环</td>
                <td class="volva">菌托</td>
                <td class="velum">菌幕</td>
                <td class="sclerotium">菌核</td>
                <td class="strainMedium">菌种培养基</td>
                <td class="mainSubstrate">主要栽培基质</td>
                <td class="afterRipeningStage">后熟期</td>
                <td class="primordialStimulationFruitbody">原基刺激&子实体</td>
                <td class="reproductiveMode">生殖方式</td>
                <td class="lifestyle">生活方式</td>
                <td class="preservation">保藏方法</td>
                <td class="domestication">驯化</td>
                <td class="nuclearPhase">核相</td>
                <td class="matingType">交配型</td>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div class="checkbox-item-tab" id="popu-paginate">
        <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
    </div>

    <form id="exportForm" action="${ctxroot}/dna/dataExport" method="get">
        <input class="model" name="model" type="hidden" value="SAMPLES"/>
        <input class="choices" name="choices" type="hidden" value=""/>
        <input class="group" name="group" type="hidden" value=""/>
        <input class="total" name="total" type="hidden" value=""/>
        <input class="flag" name="flag" type="hidden" value=""/>
        <input class="kindFlag" name="cultivar" type="hidden" value=""/>
    </form>
</div>

<script src="${ctxStatic}/js/laypage/laypage.js"></script>
<script>
    //为弹窗出来的ga-ctrl-footer添加hover触发事件 modified by zjt 2018-3-27
    $(document).ready(function(){
        var    $sel_page = $("#tab-detail #popu-paginate #per-page-count #select_page"),
            $sel_default_page = $("#tab-detail #popu-paginate #per-page-count .select_default_page"),
            $sel_item_page = $("#tab-detail #popu-paginate #per-page-count .select_item_page"),
            $sel_item_li_page = $("#tab-detail #popu-paginate #per-page-count .select_item_page li");
        $sel_default_page.val($("#tab-detail #popu-paginate #per-page-count .select_item_page li:first").text());
        $sel_page.hover(function(){
            $sel_item_page.show();
            console.log('hahaha');
            $sel_default_page.addClass("rotate");
            $sel_item_li_page.hover(function(){
                $index_page = $sel_item_li_page.index(this);
                $sel_item_li_page.eq($index_page).addClass("hover");
            },function(){
                $sel_item_li_page.removeClass("hover");
            })
        }, function(){
            $sel_item_page.hide();
            $sel_default_page.removeClass("rotate");
        });
        $sel_item_li_page.click(function(){
            $sel_default_page.val($(this).text());
            $sel_item_page.hide();
        });
    });
    //为弹窗出来的ga-ctrl-footer添加hover触发事件 modified by zjt 2018-3-27

    $(function () {
        if (window.localStorage) {
            var storage = window.localStorage;
        } else {
//            alert('This browser does NOT support localStorage');
            layer.open({
                type: 0,
                title: "温馨提示:",
                content: "This browser does NOT support localStorage",
                shadeClose: true,
            });

        }

        function setCookie(name, value) {
            var Days = 30;
            var exp = new Date();
            exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
            document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
        }

        function getCookie(name) {
            var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
            if (arr = document.cookie.match(reg))
                return unescape(arr[2]);
            else
                return null;
        }

        laypage({
            cont: $('#popu-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
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

        /* 侧边栏收起和展开 */
        $(".icon-right").click(function () {
            if ($(".snp-content").hasClass("js-nav-ac")) {
                $(".snp-content").removeClass("js-nav-ac");
            } else {
                $(".snp-content").addClass("js-nav-ac");
            }
        });

        /* search for SNPs or INDELs in Region or Gene SELECT LOGIC */
        function select() {
            var _val = $(".selcet select option:selected").attr("data-value");
            if (_val == "gene") {
                $(".selcet-gene-function").show();
                $(".selcet-set-length").show();
                $(".select-chorosome").hide();
            } else if (_val == "region") {
                $(".selcet-gene-function").hide();
                $(".selcet-set-length").hide();
                $(".select-chorosome").show();
            }
        }

        $(".selcet select").change(function () {
            select();
        });
        select();

        $(".popu-set-up").click(function () {
            $(".popu-checkbox").show();
            $(this).parents(".export-data").hide();
        })

        /* 自定义群体-展开 */
        $(".custom-groups-btn").click(function () {
            $(".custom-groups-content").show();
            $(".cover").show();
        });

        /* 自定义群体-关闭 */
        $(".colse-sample span").click(function () {
            $(".custom-groups-content").hide();
            $(".cover").hide();
//            $(".sample-text").empty();
        });

        /* 自定义群体-"更多"展现 */
        <%--$(".retract p").click(function () {--%>
            <%--var _dis = $(".hilum-color").css("display");--%>
            <%--if (_dis == "block") {--%>
                <%--$(".category-more").hide();--%>
                <%--$(".retract p").html("更多选项(开花日期、成熟日期、株高等)<img src='${ctxStatic}/images/more_unfold.png'>")--%>
            <%--} else {--%>
                <%--$(".category-more").show();--%>
                <%--$(".retract p").html("收起<img src='${ctxStatic}/images/less.png'>")--%>
            <%--}--%>
        <%--});--%>

        var populations = []; // 存储自定义群体信息
        var defineDefault = [{
            "name": "物种名称Pleurotus tuoliensis",
            "id": 001,
            "condition": {"scientificName": "Pleurotus tuoliensis"}
        }, {
            "name": "菌株名称Zhongnongduanqi1hao",
            "id": 002,
            "condition": {"strainName": "Zhongnongduanqi1hao"}
        }, {
            "name": "地理位置Beijing,China",
            "id": 003, "condition": {"locality": "Beijing,China"}
        }, {
            "name": "保藏地点Jilin agricultural university",
            "id": 004,
            "condition": {"environment": "Jilin agricultural university"}
        }, {
            "name": "培养环境indoorcultivation",
            "id": 005,
            "condition": {"environment": "indoorcultivation"}
        }, {
            "name": "材料mycelium",
            "id": 006,
            "condition": {"materials": "mycelium"}
        }];
        var defaultPopulations2 = defineDefault.concat();

        // 从cookie中读取群体信息并渲染
        function initPopulations() {
            if (getCookie("populations")) {
                populations = JSON.parse(getCookie("populations"));
                var str = "";
                $.each(populations, function (idx, popu) {
                    str += "<div class='js-ad-dd'>"
                    str += "    <label class='species-add' title='" + popu.name + "' data-index='" + popu.id + "'>"
                    str += "         <span></span><div class='label-txt'>" + popu.name + "</div>";
                    str += "    </label>"
                    str += "    <i class='js-del-dd' data-index='" + popu.id + "'>X</i>"
                    str += "</div>"
                });
                $(".js-cursom-add").empty().append(str);
            }
        }

        initPopulations();

        function renderDefaultPopulations() {
            var str = "";
            $.each(defaultPopulations2, function (idx, popu) {
                str += "<div class='js-ad-dd'>"
                str += "    <label class='species-add' title='" + popu.name + "' data-index='" + popu.id + "'>"
                str += "         <span></span><div class='label-txt'>" + popu.name + "</div>";
                str += "    </label>"
                str += "    <i class='js-del-dd' data-index='" + popu.id + "'>X</i>"
                str += "</div>"
            });
            $(".js-cursom-add2").empty().append(str);
        }

        renderDefaultPopulations();

        var defaultPopulations = [];
        // 获取默认的群体
//        function getDefaultPopulations() {
//            $.ajax({
//                url: CTXROOT + "/dna/defaultGroup",
//                type: "GET",
//                dataType: "json",
//                success: function(data) {
//                    defaultPopulations = data;
//                    if(data.length > 0) {
//                        var str = "";
//                        $.each(data, function(idx, popu) {
//                            str +="<div class='js-ad-dd'>"
//                            str +="    <label class='cur' title='" + popu.name + "' data-index='"+ idx +"'>"
//                            str +="         <span></span><div class='label-txt'>"+ popu.name.substr(0, 20) + "...</div>";
//                            str +="    </label>"
//                            str +="</div>"
//                        });
//                        $(".js-default-add").empty().append(str);
//                    }
//                }
//            });
//        }
//        getDefaultPopulations();

        // 获取样本中文名
        function getKeyName(key) {
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
                    return "熟期组";
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

        function getUnitValue(obj) {
            var key = Object.keys(obj)[0];
            if (obj[key].indexOf("-") > -1) {
                var vs = obj[key].split("-");
                var unit = "";
                switch (key) {
                    case 'weightPer100seeds':
                        unit = "g";
                        break;
                    case 'oil':
                        unit = "%";
                        break;
                    case 'protein':
                        unit = "%";
                        break;
                    case 'height':
                        unit = "cm";
                        break;
                    case 'yield':
                        unit = "t/ha";
                        break;
                    case 'upperLeafletLength':
                        unit = "mm";
                        break;
                    case 'linoleic':
                        unit = "%";
                        break;
                    case 'linolenic':
                        unit = "%";
                        break;
                    case 'oleic':
                        unit = "%";
                        break;
                    case 'palmitic':
                        unit = "%";
                        break;
                    case 'stearic':
                        unit = "%";
                        break;
                    default :
                        unit = "";
                        break;
                }
                return vs[0] + unit + "-" + vs[1] + unit;
            } else {
                switch (key) {
                    case 'floweringDate':
                        unit = "月";
                        break;
                    case 'maturityDate':
                        unit = "";
                        break;
                    default :
                        unit = "";
                        break;
                }
                return obj[key] + unit;
            }
        }

        function replaceUnvalideChar(str) {
            return str.replace(/[\%,\/]/g, "_");
        }

        // 生成后台需要的单个群体数据结构
        function getStandardPopulation(obj) {
            // 定义有范围的样本属性
            var rangeCondition = ['weightPer100seeds', 'oil', 'protein', 'height', 'yield', 'upperLeafletLength', 'linoleic', 'linolenic', 'oleic', 'palmitic', 'stearic'];
            var condition = {};
            for (key in obj) {
                if ($.inArray(key, rangeCondition) > -1) {
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

//        $(".sample-screening-btn button").click(function () {
//            //修改输入框border-color modified by zjt 2018-3-14
//            $(".custom-groups-content input[type=number]").each(function () {
//                $(this).css('border-color', '');
//            });
//            //修改输入框border-color modified by zjt 2018-3-14
//
//            var sampleTexts = $(".sample-text").text();
//            if (sampleTexts.length == 0) {
////                alert("请选择群体!")
//                layer.open({
//                    type: 0,
//                    title: "温馨提示:",
//                    content: "请选择群体",
//                    shadeClose: true,
//                });
//
//                return;
//            }
//            var defaultLen = $(".js-cursom-add2").find(".js-ad-dd").length;
//            if (populations.length + defaultLen < 10) {
//                var arr = [];
//                // popuSamples 存储保存的样本数据
//                for (var i in popuSamples) {
//                    var obj = {};
//                    obj[i] = popuSamples[i];
//                    arr.push(getKeyName(i) + getUnitValue(obj));
//                }
//                // 当前保存群体的顺序
//                var popLength = $(".js-cursom-add>div.js-ad-dd").length;
//
//                var o = {
//                    "name": arr.join(","),
////                    "id": new Date().getTime(),
//                    "id": popLength + 1+ 6,
//                    "condition": getStandardPopulation(popuSamples)
//                };
////                initPopulations();
//                appendPopulation(o);
//
//                $(".sample-empty").trigger("click");
//            } else {
////                alert("最多可添加10个群体");
//                layer.open({
//                    type: 0,
//                    title: "温馨提示:",
//                    content: "最多可添加10个群体",
//                    shadeClose: true,
//                });
//            }
//
//        });

        // 向自定义群体添加
        function appendPopulation(obj) {
            var str = "";
            str += "<div class='js-ad-dd'>"
            str += "    <label class='species-add' title='" + obj.name + "' data-index='" + obj.id + "'>"
            str += "         <span></span><div class='label-txt'>" + obj.name + "</div>";
            str += "    </label>"
            str += "    <i class='js-del-dd' data-index='" + obj.id + "'>X</i>"
            str += "</div>"
            $(".js-cursom-add").append(str);
            populations.push(obj);
            setCookie('populations', JSON.stringify(populations));
        }

        // 向自定义群体删除
        function deletePopulation(id) {
            $.each(populations, function (idx, ele) {
                if (ele.id == id) {
                    populations.splice(idx, 1);
                    return false;
                }
            });
            setCookie('populations', JSON.stringify(populations));
        }

        function deleteDefaultPopulation(id) {
            $.each(defaultPopulations2, function (idx, ele) {
                if (ele.id == id) {
                    defaultPopulations2.splice(idx, 1);
                    return false;
                }
            });
        }

        // 勾选的群体数组
        var selectedPopulations = [];
        /* 选择自定义群体 */
        $("body").on("click", ".js-ad-dd label span", function () {
            if ($(this).parent().hasClass("cur")) {
                $(this).parent().removeClass("cur");
            } else {
                $(this).parent().addClass("cur");
            }
            getSelectedPopulations();
        });

        // 根据群体生成表格内容设置，和表头
        window.renderTableHead = function () {
            var str = '', str2 = '';
            var commonStr = '<li data-value="all">ALL</li>' +
                '<li data-type="type" data-value="downstream">Downstream</li>' +
                '<li data-type="type" data-value="exonic;splicing">Exonic;Splicing</li>' +
                '<li data-type="effect" data-value="nonsynonymous SNV">Exonic_nonsynonymous SNV</li>' +
                '<li data-type="effect" data-value="stopgain">Exonic_stopgain</li>' +
                '<li data-type="effect" data-value="stoploss">Exonic_stoploss</li>' +
                '<li data-type="effect" data-value="synonymous SNV">Exonic_synonymous SNV</li>' +
                '<li data-type="type" data-value="intergenic">Intergenic</li>' +
                '<li data-type="type" data-value="intronic">Intronic</li>' +
                '<li data-type="type" data-value="splicing">Splicing</li>' +
                '<li data-type="type" data-value="upstream">Upstream</li>' +
                '<li data-type="type" data-value="upstream;downstream">Upstream;Downstream</li>' +
                '<li data-type="type" data-value="UTR3">3&acute;UTR</li>' +
                '<li data-type="type" data-value="UTR5">5&acute;UTR</li>' +
                '<li data-type="type" data-value="UTR5;UTR3">5&acute;UTR;3&acute;UTR</li>';
            var commonStr2 = '<li data-value="all">ALL</li>' +
                '<li data-type="type" data-value="downstream">Downstream</li>' +
                '<li data-type="type" data-value="exonic;splicing">Exonic;Splicing</li>' +
                '<li data-type="effect" data-value="frameshift deletion">Exonic_frameshift deletion</li>' +
                '<li data-type="effect" data-value="frameshift insertion">Exonic_frameshift insertion</li>' +
                '<li data-type="effect" data-value="frameshift insertion">Exonic_nonframeshift deletion</li>' +
                '<li data-type="effect" data-value="nonframeshift insertion">Exonic_nonframeshift insertion</li>' +
                '<li data-type="effect" data-value="stopgain">Exonic_stopgain</li>' +
                '<li data-type="effect" data-value="stoploss">Exonic_stoploss</li>' +
                '<li data-type="type" data-value="intergenic">Intergenic</li>' +
                '<li data-type="type" data-value="intronic">Intronic</li>' +
                '<li data-type="type" data-value="splicing">Splicing</li>' +
                '<li data-type="type" data-value="upstream">Upstream</li>' +
                '<li data-type="type" data-value="upstream;downstream">Upstream;Downstream</li>' +
                '<li data-type="type" data-value="UTR3">3&acute;UTR</li>' +
                '<li data-type="type" data-value="UTR5">5&acute;UTR</li>' +
                '<li data-type="type" data-value="UTR5;UTR3">5&acute;UTR;3&acute;UTR</li>';

            var headStr = '<td class="t_snpid">SNP ID</td>' +
                '<td class="param t_consequenceType">Consequence Type' +
                '<img src="${ctxStatic}/images/down.png">' +
                '<input type="hidden" class="js-consequence-type"> <div class="input-component "> <ul class="consequence-type ">' + commonStr +
                '</ul> </div></td>' +
                '<td class="param t_snpchromosome">Chromosome</td>' +
                '<td class="param t_position">Position</td>' +
                '<td class="param t_snpreference">Reference</td>' +
                '<td class="param t_majorAllele">Major Allele</td>' +
                '<td class="param t_minorAllele">Minor Allele</td>' +
                '<td class="param t_fmajorAllele"><select class="f-ma"><option value="major">Frequency of Major Allele</option>' +
                '<option value="minor">Frequency of Minor Allele</option></select></td>' +
                '<td class="param t_genoType">Genotype</td>';
            var headStr2 = '<td class="t_indels">INDEL ID</td>' +
                '<td class="param t_iconsequenceType">Consequence Type' +
                '<img src="${ctxStatic}/images/down.png">' +
                '<input type="hidden" class="js-consequence-type"> <div class="input-component "> <ul class="consequence-type ">' + commonStr2 +
                '</ul> </div></td>' +
                '<td class="param t_indelchromosome">Chromosome</td>' +
                '<td class="param t_iposition">Position</td>' +
                '<td class="param t_indelreference">Reference</td>' +
                '<td class="param t_imajorAllele">Major Allele</td>' +
                '<td class="param t_iminorAllele">Minor Allele</td>' +
                '<td class="param t_ifmajorAllele"><select class="f-ma"><option value="major">Frequency of Major Allele</option>' +
                '<option value="minor">Frequency of Minor Allele</option></select></td>';
            if (selectedPopulations.length > 0) {
                var resultPopulations = selectedPopulations;
            } else {
                var resultPopulations = defaultPopulations;
            }
            $.each(resultPopulations, function (idx, item) {
                str += '<dd><label title="' + item.name + '" data-col-name="fmajorAllelein' + replaceUnvalideChar(item.name).split(",").join("_") + '" for="fmajorAllelein' + replaceUnvalideChar(item.name).split(",").join("_").replace(/\s/g, "") + '" class="checkbox-ac">' +
                    '<span id="fmajorAllelein' + replaceUnvalideChar(item.name).split(",").join("_").replace(/\s/g, "") + '" data-value="fmajorAllelein' + item.name + '"></span>Frequency of Major Allele in ' + item.name.substr(0, 20) + '...</label></dd>'

                headStr += '<td title="' + item.name + '" class="param t_fmajorAllelein' + replaceUnvalideChar(item.name).split(",").join("_").replace(/\s/g, "") + '">' +
                    '<select class="f-ma">' +
                    '<option value="major">Frequency of Major Allele in ' + item.name.substr(0, 20) + '...</option>' +
                    '<option value="minor">Frequency of Minor Allele in ' + item.name.substr(0, 20) + '...</option>' +
                    '</select>' + '</td>'
                headStr2 += '<td title="' + item.name + '" class="param t_fmajorAllelein' + replaceUnvalideChar(item.name).split(",").join("_").replace(/\s/g, "") + '">' +
                    '<select class="f-ma">' +
                    '<option value="major">Frequency of Major Allele in ' + item.name.substr(0, 20) + '...</option>' +
                    '<option value="minor">Frequency of Minor Allele in ' + item.name.substr(0, 20) + '...</option>' +
                    '</select>' + '</td>'
            });
            $(".js-table-header-setting-snp > span").empty().append(str);
            $(".js-table-header-setting-indel > span").empty().append(str);
            $(".js-snp-table thead tr").empty().append(headStr);
            $(".js-indel-table thead tr").empty().append(headStr2);
            TableHeaderSettingSnp();
            TableHeaderSettingIndel();
        }

        // 封装手动删除品种名/localStorage 中的值
        function deleteLocalKind(kindId, self) {
            // 判断删除的是不是品种
            var currPval = $(self).prev().find("div").text().substring(0, 3);
            if (currPval == "品种名") {
                var kindStorage = JSON.parse(storage.getItem("kind"));
                var sdKinds = kindStorage.name;
                for (var i = 0; i < sdKinds.length; i++) {
                    if (sdKinds[i].id == kindId) {
                        sdKinds.splice(i, 1);
                    }
                }
                storage.removeItem("kind");
                storage.setItem("kind", JSON.stringify(kindStorage));
            }
        };
        /* 删除手动添加的自定义群体 */
        $("body").on("click", ".js-del-dd", function () {
            var self = this;
            $(this).parent().remove();
            var id = $(this).attr("data-index");
            deletePopulation(id);
            getSelectedPopulations();
            // 手动删除品种名时，需要删除localStorage中的值
            var kindId = $(this).prev().attr("data-index");
            deleteLocalKind(kindId, self);
        });
        $(".js-cursom-add2").on("click", ".js-del-dd", function () {
            $(this).parent().remove();
            getSelectedPopulations();
        });
        var kindNames = [];
        var currPopu = "";
        var popId;
        var currVal = "";

        /* 显示群体信息、弹框 */
        $(".js-cursom-add").on("click", ".label-txt", function () {
            currVal = $(this).text().split(",")[0].substring(0, 3);
            var currKindList = $(this).text().split(",");
            kindNames = [];
            for (var i = 0; i < currKindList.length; i++) {
                var name = currKindList[i].substring(3, currKindList[i].length);
                kindNames.push(name);
            }
            var label = $(this).parent().find("label");
            if (label.hasClass("cur")) {
                label.addClass("cur");
            } else {
                label.removeClass("cur");
            }
            $(".tab-detail").show();
            $(".tab-detail-thead p span").text($(this).text());

            popId = $(this).parent("label").attr("data-index");
            if (currVal == "品种名") {
                currPopu = selectKindVal(popId)[0];
                var data = {
                    names: kindNames.join(",")
                };
                currFlag = "cultivar";
                getKindInfos(1);
            } else {
                currFlag = "group"
                currPopu = selectPopulation(popId)[0];
                getPopuTable(1);
            }
            // 弹框所有表头都显示
            var trs = $(".popu-table thead").find("tr");
            for (var i = 0; i < trs.length; i++) {
                var trChildrens = $(trs[i]).find("td");
                for (var j = 0; j < trChildrens.length; j++) {
                    if ($(trChildrens[j]).is(":hidden")) {
                        $(trChildrens[j]).show();
                    }
                }
            }

            //             修改拖拽样式
            index1 = layer.open({
                title: "",
                type: 1,
                content: $("#tab-detail"),
                area: ['860px', '175px'],
                shadeClose: true,
                scrollbar: false,
                move: '#tabDetailTitle',
                closeBtn: 0,
                offset: ['135px', '390px']
            });
            $("#popu-paginate .select_default_page").val(10);

        });
        var currFlag;
        var index1, index2;
        var choiceArr = [];
        function getAllChoice(){
            var _labels = $(".popu-checkbox").find(".js-table-header-setting-popu").find("label");
//            var choiceArr = [];
            $.each(_labels, function (idx, item) {
                if ($(item).hasClass("checkbox-ac")) {
                    choiceArr.push($(item).attr("for"));
                }
            });
        }
        getAllChoice();
        // 选则品种 之后 详情页
        function getKindInfos(curr) {
            $.ajax({
                type: 'GET',
                url: CTXROOT + "/dna/getByCultivar",
                data: {
                    names: kindNames.join(","),
                    pageNum: curr || 1,
                    pageSize: pageSizePopu
                },
                contentType: "application/json",
                dataType: "json",
                success: function (result) {
                    popCount = result.total;
                    renderPopuTable(result.data.list);
                    laypage({
                        cont: $('#popu-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(result.data.total / pageSizePopu), //通过后台拿到的总页数
                        curr: curr || 1, //当前页
                        /*skin: '#5c8de5',*/
                        skin: '#0f9145',
                        skip: true,
                        first: 1, //将首页显示为数字1,。若不显示，设置false即可
                        last: Math.ceil(result.data.total / pageSizePopu), //将尾页显示为总页数。若不显示，设置false即可
                        prev: '<',
                        next: '>',
                        groups: 3, //连续显示分页数
                        jump: function (obj, first) { //触发分页后的回调
                            if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                getKindInfos(obj.curr);
                            }
                        }
                    });
                    $("#popu-paginate .total-page-count > span").html(result.data.total);
                },
                error: function (error) {
                    console.log(error);
                }
            })
        };

        $(".js-cursom-add2").on("click", ".label-txt", function () {
            currFlag = "group";
            var label = $(this).parent().find("label");
            if (label.hasClass("cur")) {
                label.addClass("cur");
            } else {
                label.removeClass("cur");
            }
            $(".tab-detail").show();
//            $("#mid").show();
            $(".tab-detail-thead p span").text($(this).text());

            popId = $(this).parent("label").attr("data-index");
            currPopu = selectDefaulPopulation(popId)[0];
            getPopuTable(1);
            // 弹框所有表头都显示
            var trs = $(".popu-table thead").find("tr");
            for (var i = 0; i < trs.length; i++) {
                var trChildrens = $(trs[i]).find("td");
                for (var j = 0; j < trChildrens.length; j++) {
                    if ($(trChildrens[j]).is(":hidden")) {
                        $(trChildrens[j]).show();
                    }
                }
            }

//             修改拖拽样式
            index2 = layer.open({
                title: "",
                type: 1,
                content: $("#tab-detail"),
                area: ['860px', '175px'],
                shadeClose: true,
                scrollbar: false,
                move: '#tabDetailTitle',
                closeBtn: 0,
                offset: ['135px', '390px']

            });
            $("#popu-paginate .select_default_page").val(10);

        });
        var popPageNum = 1;
        var popCount;
        // 获取焦点添加样式：
        $("#popu-paginate").on("focus", ".laypage_skip", function () {
            $(this).addClass("isFocus");
        });
        $("#popu-paginate").on("blur", ".laypage_skip", function () {
            $(this).removeClass("isFocus");
        });
        //modified by zjt 2018-3-27
        /*$("#popu-paginate #per-page-count").on("change", ".lay-per-page-count-select", function () {
            var curr = Number($(".laypage_curr").text());
            var pageSize = Number($(this).val());
            var total = Number($("#popu-paginate #total-page-count span").text());
            var mathCeil = Math.ceil(total / curr);
            pageSizePopu = $(this).val();
            if (pageSize > mathCeil) {
                var pageSizeNum = $(this).val();
                getPopuTable(1, pageSizeNum)
            } else {
                var pageSizeNum = $(this).val();
                getPopuTable(curr, pageSizeNum)
            }
        });*/
        $("#popu-paginate #per-page-count .select_item_page li").click(function (){
            var currentSelected = $(this).text();
            var pageSizeNum = currentSelected;
            pageSizePopu = currentSelected;
            //paramData.pageSize = pageSizePopu;
            getPopuTable(1, pageSizeNum)
        });
        //modified by zjt 2018-3-27

        // 注册 enter 事件的元素
        $(document).keyup(function (event) {
            var _page_skip = $('#popu-paginate .laypage_skip');
            if (_page_skip.hasClass("isFocus")) {
                if (event.keyCode == 13) {
                    var _page_skip = $('#popu-paginate .laypage_skip');
                    var currNum = Number(_page_skip.val());
                    var pageSizeNum = Number($('#popu-paginate #per-page-count .lay-per-page-count-select').val());
                    var total = Number($("#popu-paginate #total-page-count span").text());
                    var mathCeil = Math.ceil(total / pageSizeNum);
                    if (currNum > mathCeil) {
                        getPopuTable(1, pageSizeNum)
                    } else {
                        getPopuTable(currNum, pageSizeNum)
                    }
                }
            }
        });

        var pageSizePopu = 10;

        function getPopuTable(curr) {
            var currData=[];
            currData.push(currPopu)
            var datas={group: JSON.stringify(currPopu), pageNo: curr || 1, pageSize: pageSizePopu};
            $.ajax({
                url: CTXROOT + "/dna/queryByGroup",
                data: datas,
                type: "POST",
                dataType: "json",
                success: function (res) {
                    popCount = res.total;
                    renderPopuTable(res.data);
                    laypage({
                        cont: $('#popu-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(res.total / pageSizePopu), //通过后台拿到的总页数
                        curr: curr || 1, //当前页
                        /*skin: '#5c8de5',*/
                        skin: '#0f9145',
                        skip: true,
                        first: 1, //将首页显示为数字1,。若不显示，设置false即可
                        last: Math.ceil(res.total / pageSizePopu), //将尾页显示为总页数。若不显示，设置false即可
                        prev: '<',
                        next: '>',
                        groups: 3, //连续显示分页数
                        jump: function (obj, first) { //触发分页后的回调
                            if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                var pageSizeNum = Number($('#per-page-count .lay-per-page-count-select').val());
                                console.log(pageSizeNum)
                                getPopuTable(obj.curr, currPopu);
                            }
                        }
                    });
                    $("#popu-paginate .total-page-count > span").html(res.total);

                }
            });
        }

        function renderPopuTable(data) {
            $(".js-table-header-setting-popu").find("label").addClass("checkbox-ac");
            var str = '';
            $.each(data, function (idx, item) {
                str += '<tr>'
                str += '<td class="runNo">' + item.runNo + '</td>'
                str += '<td class="scientificName">' + item.scientificName + '</td>'
                str += '<td class="sampleId">' + item.sampleId + '</td>'
                str += '<td class="strainName">' + item.strainName + '</td>'
                str += '<td class="locality">' + item.locality + '</td>'
                str += '<td class="preservationLocation">' + item.preservationLocation + '</td>'
                str += '<td class="type">' + item.type + '</td>'
                str += '<td class="environment">' + item.environment + '</td>'
                str += '<td class="materials">' + item.materials + '</td>'
                str += '<td class="treat">' + item.treat + '</td>'
                str += '<td class="time">' + item.definitionTime + '</td>'
                str += '<td class="taxonomy">' + item.taxonomy + '</td>'
                str += '<td class="myceliaPhenotype">' + item.myceliaPhenotype + '</td>'
                str += '<td class="myceliaDiameter">' + item.myceliaDiameter + '</td>'
                str += '<td class="myceliaColor">' + item.myceliaColor + '</td>'
                str += '<td class="sporesColor">' + item.sporesColor + '</td>'
                str += '<td class="sporesShape">' + item.sporesShape + '</td>'
                str += '<td class="clampConnection">' + item.clampConnection + '</td>'
                str += '<td class="pileusPhenotype">' + item.pileusPhenotype + '</td>'
                str += '<td class="pileusColor">' + item.pileusColor + '</td>'
                str += '<td class="stipePhenotype">' + item.stipePhenotype + '</td>'
                str += '<td class="stipeColor">' + item.stipeColor + '</td>'
                str += '<td class="fruitbodyColor">' + item.fruitbodyColor + '</td>'
                str += '<td class="fruitbodyType">' + item.fruitbodyType + '</td>'
                str += '<td class="illumination">' + item.illumination + '</td>'
                str += '<td class="collarium">' + item.collarium + '</td>'
                str += '<td class="volva">' + item.volva + '</td>'
                str += '<td class="velum">' + item.velum + '</td>'
                str += '<td class="sclerotium">' + item.sclerotium + '</td>'
                str += '<td class="strainMedium">' + item.strainMedium + '</td>'
                str += '<td class="mainSubstrate">' + item.mainSubstrate + '</td>'
                str += '<td class="afterRipeningStage">' + item.afterRipeningStage + '</td>'
                str += '<td class="primordialStimulationFruitbody">' + item.primordialStimulationFruitbody + '</td>'
                str += '<td class="reproductiveMode">' + item.reproductiveMode + '</td>'
                str += '<td class="lifestyle">' + item.lifestyle + '</td>'
                str += '<td class="preservation">' + item.preservation + '</td>'
                str += '<td class="domestication">' + item.domestication + '</td>'
                str += '<td class="nuclearPhase">' + item.nuclearPhase + '</td>'
                str += '<td class="matingType">' + item.matingType + '</td>'
                str += '</tr>'
            });
            $(".popu-table > tbody").empty().append(str);
        }

        // 弹框 筛选确认按钮

        $(".js-popu-setting-btn").click(function () {
            // add by jarry at 3-27
            choiceArr.length = 0;
            var _labels = $(".popu-checkbox").find(".js-table-header-setting-popu").find("label");
//            var choiceArr = [];
            $.each(_labels, function (idx, item) {
                if ($(item).hasClass("checkbox-ac")) {
                    choiceArr.push($(item).attr("for"));
                }
            });
            var _labels = $(".js-table-header-setting-popu").find("label");
            $.each(_labels, function (idx, item) {
                var cls = "." + $(item).attr("for");
                if (!$(item).hasClass("checkbox-ac")) {
                    $(".popu-table").find(cls).hide();
                } else {
                    $(".popu-table").find(cls).show();
                }
            });
            // 判断脂肪酸是否显示
            var linoleic1, linolenic1, oleic1, palmitic1, stearic1;
            var linoleic2 = $(".js-table-header-setting-popu").find("label[for='linoleic']").attr("class");
            var linolenic2 = $(".js-table-header-setting-popu").find("label[for='linolenic']").attr("class");
            var oleic2 = $(".js-table-header-setting-popu").find("label[for='oleic']").attr("class");
            var palmitic2 = $(".js-table-header-setting-popu").find("label[for='palmitic']").attr("class");
            var stearic2 = $(".js-table-header-setting-popu").find("label[for='stearic']").attr("class");
            if (!linoleic2 && !linolenic2 && !oleic2 && !palmitic2 && !stearic2) {
                $(".popu-table thead").find("td[colspan='5']").hide();
            } else {
                if ($(".popu-table thead").find("td[colspan='5']").is(":hidden")) {
                    $(".popu-table thead").find("td[colspan='5']").show();
                }
            }
        });

        /* 关闭群体信息、弹框 */
        $(".tab-detail-thead p a").click(function () {
//            $(".tab-detail").hide();
//            $("#mid").hide();
            layer.close(index1);
            layer.close(index2);
            //重置页面展示条数 modified by zjt
            pageSizePopu = 10;
            //重置页面展示条数 modified by zjt
        });

        var popuSamples = {}; // 存储选中的样本数据

        /* 自定义样本选中 */
//        $("body").on("click", ".sample-category > div span", function () {
//
//            $(this).parent().find("span").removeClass("js-span-ac");
//            $(this).addClass("js-span-ac");
//
//            var sampleName = $(this).parents("label").parent().attr("data-name"),
//                sampleValue = $(this).text();
//            popuSamples[sampleName] = sampleValue;
//            renderSampleText();
//
//            renderSampleCount();
//        });

        function renderSampleCount() {
            var arr = [];
            for (var i in popuSamples) {
                arr.push(getKeyName(i) + popuSamples[i]);
            }
            var obj = {"name": arr.join(","), "condition": getStandardPopulation(popuSamples)};
            var currData=[];
            currData.push(obj)
           var datas= {group: JSON.stringify(obj), pageNo: 1 || 1, pageSize: 10};
            $.ajax({
                url: CTXROOT + "/dna/queryByGroup",
                data: datas,
                type: "POST",
                dataType: "json",
                success: function (res) {
                    $(".js-total-samples").html(res.total);
                }
            });
        }

        renderSampleCount();

        // 生成样本文字
        function renderSampleText() {
            var str = '';
            for (var name in popuSamples) {
                str += '<span data-name="' + name + '">' + getKeyName(name) + popuSamples[name] + '<i class="js-colse-text">X</i></span>'
            }
            $(".sample-text").empty().append(str);
        }

        /* 样本清空 */
        $(".sample-empty").click(function () {
            //修改输入框border-color modified by zjt 2018-3-14
            $(".custom-groups-content input[type=number]").each(function () {
                $(this).css('border-color', '');
            });
            //修改输入框border-color modified by zjt 2018-3-14
            var samples = $(".sample-text").find("span");
            $.each(samples, function (idx, item) {
                $(item).find(".js-colse-text").trigger("click");
            });
            renderSampleCount();
        });

        /* 删除单个样本条件 */
        $(".sample-text").on("click", ".js-colse-text", function () {
            $(this).parent().remove();
            var name = $(this).parent().attr("data-name");
            delete(popuSamples[name]);

            renderSampleCount();

            $(".sample-category").find("div[data-name='" + name + "']").find("span").removeClass("js-span-ac");
        });

        /* 自定义 样本 */
        $(".js-customize-sample").click(function () {
            // 百分比类型数组
            var percetageArray = ["oil", "protein", "linoleic", "linolenic", "oleic", "palmitic", "stearic"];
            var name = $(this).attr("data-name");
            var _prev = $(this).prev();
            var val1 = _prev.find("input").first()[0].value;
            var val2 = _prev.find("input").last()[0].value;
            var str = val1 + "-" + val2;
            //恢复输入框border-colormodified by zjt 2018-3-14
            $(".custom-groups-content input[type=number]").each(function () {
                $(this).css('border-color', '');
            });
            //恢复输入框border-colormodified by zjt 2018-3-14

            //输入框颜色
            var input1 = _prev.find("input").first()[0];
            var input2 = _prev.find("input").last()[0];
            if ((val1 != "" && val2 != "") || (val1 == "0" || val2 == "0") && (val1 != "" || val2 != "")) {
                if ($.inArray($(this).attr("data-name"), percetageArray) > -1) {
                    if ((val1 < 0 || val2 < 0) || val1 > val2 || (val1 > 100 || val2 > 100)) {
                        layer.open({
                            type: 0,
                            title: "温馨提示:",
                            content: "添加的区间数据不合理,如果是百分比则要小于100",
                            shadeClose: true,
                        });
                        input1.style.borderColor = "red";
                        input2.style.borderColor = "red";
                    } else {
                        var spans = $(this).parent().siblings("label").find("span:not('.hidden')");
                        var arr = [];
                        $.each(spans, function (idx, ele) {
                            arr.push($(ele).text());
                        });
                        var ss = $("<span class='hidden' style='display: none;'>" + str + "</span>");
                        $(".sample-category").find("div[data-name='" + name + "']").find("label").first().append(ss);
                        ss.trigger("click");
                        input1.style.borderColor = "";
                        input2.style.borderColor = "";
                    }
                } else {
                    if ((val1 < 0 || val2 < 0) || val1 > val2) {
                        layer.open({
                            type: 0,
                            title: "温馨提示:",
                            content: "添加的区间数据不合理",
                            shadeClose: true,
                        });
                        input1.style.borderColor = "red";
                        input2.style.borderColor = "red";
                    } else {
                        var spans = $(this).parent().siblings("label").find("span:not('.hidden')");
                        var arr = [];
                        $.each(spans, function (idx, ele) {
                            arr.push($(ele).text());
                        });
                        var ss = $("<span class='hidden' style='display: none;'>" + str + "</span>");
                        $(".sample-category").find("div[data-name='" + name + "']").find("label").first().append(ss);
                        ss.trigger("click");
                        input1.style.borderColor = "";
                        input2.style.borderColor = "";
                    }
                }
            } else {
                if (val1 == "" && val2 != "") {
                    input1.style.borderColor = "red";
                    input2.style.borderColor = "";
                } else if (val1 != "" && val2 == "") {
                    input1.style.borderColor = "";
                    input2.style.borderColor = "red";
                } else {
                    input1.style.borderColor = "red";
                    input2.style.borderColor = "red";
                }
                layer.open({
                    type: 0,
                    title: "温馨提示:",
                    content: "输入不能为空",
                    shadeClose: true,
                });
            }
        });

        /* 输入框获取焦点则将border-color恢复原样 */
        //modified by zjt 2018-3-13
        //先将页面中所有的input输入框borderColor恢复
        $(".custom-groups-content input[type=number]").focus(function () {
            $(".custom-groups-content input[type=number]").each(function () {
                $(this).css('border-color', '');
            });
        });
        //modified by zjt 2018-3-13

//        /* 显示Samples详细文本 */
//        $(".sample-category div span").hover(function () {
//            if ($(this).text() !== "") {
//                var self = this;
//                var content = "";
//                var obj = {};
//                var sampleName = $(this).parents("label").parent().attr("data-name"),
//                    sampleValue = $(this).text();
//                obj[sampleName] = sampleValue;
//
//                var arr = [];
//                for (var i in obj) {
//                    arr.push(getKeyName(i) + popuSamples[i]);
//                }
//                // 只传单个
//                var obj = {"name": arr.join(","), "condition": getStandardPopulation(obj)};
//                $.ajax({
//                    url: CTXROOT + "/dna/queryByGroup",
//                    data: {group: JSON.stringify(obj), pageNo: 1 || 1, pageSize: 10},
//                    type: "POST",
//                    dataType: "json",
//                    success: function (res) {
//                        content += "<div>" + res.total + "</div>";
//                        $.pt({
//                            target: self,
//                            position: 't',
//                            align: 'l',
//                            autoClose: false,
//                            content: content
//                        });
//                        $(".pt").css("left", $(".pt").position().left);
//                    }
//                });
//
//            } else {
//                $(".pt").remove();
//            }
//        }, function () {
//            $(".pt").remove();
//        });

        /* 选择搜索基因 */
        $("body").on("click", ".gene-search-list label", function () {
            $(".gene-search-list label").removeClass("checkbox-ac");
            var val = $('input:radio[name="sex"]:checked');
            if ($(this).hasClass("checkbox-ac")) {
                $(this).removeClass("checkbox-ac");
            } else {
                $(this).addClass("checkbox-ac");
                GeneObj.gene = $(this).text();
            }
        });

        $(".js-search-gene-btn").click(function () {
            var gene = $(".js-search-gene-text").val();
            $.ajax({
                url: "${ctxroot}/dna/queryByGene",
                data: {gene: gene, pageSize: 100, pageNo: 1},
                type: "GET",
                dataType: "json",
                timeout: 10000,
                success: function (res) {
                    if (res.data.length > 0) {
                        if ($(".gene-search input").hasClass("inputError")) {
                            $(".gene-search input").removeClass("inputError");
                        }
                        ;
                        if (!$(".errorBoxShow").is(":hidden")) {
                            $(".errorBoxShow").hide();
                        }
                        var len = res.data.length;
                        var str = '';
                        for (var i = 0; i < len; i++) {
                            str += '<label><input type="radio" name="radio" class="setRadio"><div class="setLength">' + res.data[i].geneId+'</div><i style="display:none"></i></label>'
                        }
                        $(".gene-search-list").empty().append(str);
                    } else {
                        $(".gene-search-list").empty();
                        $(".gene-search input").addClass("inputError");
                        $(".errorBoxShow").show();
                    }
                    $(document).on("mouseover mouseout", ".gene-search-list label", function (e) {
                        var e = e || event;
                        if (e.type == "mouseover") {
                            var _text = $(this).text()
                            var self = this;
                            var content = "<p>" + _text + "</p>";
                            $.pt({
                                target: self,
                                position: 't',
                                align: 'l',
                                autoClose: false,
                                content: content
                            });
                            $(".pt").css("left", $(".pt").position().left);
                        } else if (e.type == "mouseout") {
                            $(".pt").remove();
                        }

                    })
                },
                error: function (err) {
                    window.location.href = '${ctxroot}/login'
                }
            });
        });

        $("#allSelected").click(function(){
            if($(this).hasClass("whiteOk")){
                $(this).removeClass("whiteOk").addClass("rightOk");
//                $("js-clear-btn").trigger("click");
            }else {
                $(this).addClass("whiteOk").removeClass("rightOk");
            }
        })

        /* 导出 */
        $(".js-export-popu").click(function () {
//            var _labels = $(".popu-checkbox").find(".js-table-header-setting-popu").find("label");
//            var choiceArr = [];
//            $.each(_labels, function (idx, item) {
//                if ($(item).hasClass("checkbox-ac")) {
//                    var title = $(item).attr("for");
//                    if(title=='time'){
//                        title='definitionTime';
//                    }
//                    choiceArr.push(title);
//                }
//            });
            console.log(choiceArr)
            $("#exportForm").find(".group").val(JSON.stringify(currPopu));
            $("#exportForm").find(".choices").val(choiceArr.join(","));
            $("#exportForm").find(".flag").val(currFlag);
            $("#exportForm").find(".kindFlag").val(kindNames.join(","));
            $("#exportForm").submit();

        });

        function linkEndPosition() {
            var chromosomeMax = $(".js-chorosome option:selected").attr("data-max");
            $(".js-end-position").attr("placeholder", "<=" + chromosomeMax);
        }

        $(".js-chorosome").change(function () {
            linkEndPosition();
        });

        /* 定义 Region & Gene 搜索条件 */
        var RegionObj = {
            "chromosome": "",
            "start": "",
            "end": ""
        }, GeneObj = {
            "gene": ""
        };

//        根据id获取选中的品种名
        function selectKindVal(id) {
            var o = [];
            var kindStor = JSON.parse(storage.getItem("kind"));
            for (var i = 0; i < kindStor.name.length; i++) {
                if (kindStor.name[i].id == id) {
                    o.push(kindStor.name[i]);
                }
            }
            ;
            return o;
        }

        // 根据ID获取选中的population
        function selectPopulation(id) {
            var o = [];

            $.each(populations, function (idx, ele) {
                if (ele.id == id) {
                    o.push(ele);
                }
            });
            return o;
        }

        function selectDefaulPopulation(id) {
            var o = [];
            $.each(defaultPopulations2, function (idx, ele) {
                if (ele.id == id) {
                    o.push(ele);
                }
            });
            return o;
        }

        function getSelectedPopulations() {
            selectedPopulations = [];
            $.each($(".js-cursom-add").find(".js-ad-dd"), function (idx, element) {
                if ($(element).find("label").hasClass("cur")) {
                    var id = $(element).find("label").attr("data-index");
                    if ($(element).find("label").find("div").text().substring(0, 6) == "测序样品编号") {
                        var selectedItem = selectKindVal(id);
                    } else {
                        var selectedItem = selectPopulation(id);
                    }
                    selectedPopulations.push(selectedItem[0]);
                }
            });
            $.each($(".js-cursom-add2").find(".js-ad-dd"), function (idx, element) {
                if ($(element).find("label").hasClass("cur")) {
                    var id = $(element).find("label").attr("data-index");
                    var selectedItem = selectDefaulPopulation(id);
                    selectedPopulations.push(selectedItem[0]);
                }
            });
        }

        window.GetPanelParams = {
            getPanelType: function () {
                return $(".selcet select option:selected").attr("data-value");
            },
            getRegionParams: function () {
                RegionObj.chromosome = $(".js-chorosome").val();
                RegionObj.start = $(".js-start-position").val();
                RegionObj.end = $(".js-end-position").val();
                var chromosomeMax = $(".js-chorosome option:selected").attr("data-max");
                if (selectedPopulations.length > 0) {
                    RegionObj["group"] = JSON.stringify(selectedPopulations);
                } else {
//                    RegionObj["group"] = JSON.stringify(defaultPopulations);
                    RegionObj["group"] = JSON.stringify([]);
                }
                if (RegionObj.start == "" || RegionObj.end == "") {
                    layer.open({
                        type: 0,
                        title: "温馨提示:",
                        content: "输入不能为空",
                        shadeClose: true,
                    });
                    return;
                }
                if (!isNaN(RegionObj.start * 1) && !isNaN(RegionObj.end * 1)) {
                    if (RegionObj.start * 1 < 0 || RegionObj.end * 1 < 0) {
                        layer.open({
                            type: 0,
                            title: "温馨提示:",
                            content: "输入数字应大于0",
                            shadeClose: true,
                        });
                        return;
                    }
                    if (RegionObj.start * 1 > chromosomeMax * 1 || RegionObj.end * 1 > chromosomeMax * 1) {
                        layer.open({
                            type: 0,
                            title: "温馨提示:",
                            content: "输入值超过该基因最大值",
                            shadeClose: true,
                        });
                        return;
                    }
                    if (RegionObj.start * 1 > RegionObj.end * 1) {
                        layer.open({
                            type: 0,
                            title: "温馨提示:",
                            content: "StartPosition应小于EndPosition",
                            shadeClose: true,
                        });
                        return;

                    }
//                    if(RegionObj.end*1 - RegionObj.start*1 > 100000) {
//                        return alert("区间应小于100 kb");
//                    }
                    return RegionObj;
                } else {
                    layer.open({
                        type: 0,
                        title: "温馨提示:",
                        content: "请输入数字",
                        shadeClose: true,
                    });
                }
            },
            getGeneParams: function () {
                GeneObj.start = $(".js-up-stream").val();
                GeneObj.end = $(".js-down-stream").val();
                if (selectedPopulations.length > 0) {
                    GeneObj["group"] = JSON.stringify(selectedPopulations);
                } else {
                    GeneObj["group"] = JSON.stringify(defaultPopulations);
//                    GeneObj["group"] = JSON.stringify([]);
                }
//
                    if (!isNaN(GeneObj.start * 1)) {
                        if (GeneObj.start * 1 > 20000) {
                            layer.open({
                                type: 0,
                                title: "温馨提示:",
                                content: "输入范围值要小于20kb",
                                shadeClose: true,
                            });
                            return;
                        } else if (GeneObj.start * 1 < 0) {
                            layer.open({
                                type: 0,
                                title: "温馨提示:",
                                content: "输入数字应大于0",
                                shadeClose: true,
                            });
                            return;
                        }
                    } else {
                        layer.open({
                            type: 0,
                            title: "温馨提示:",
                            content: "请输入数字",
                            shadeClose: true,
                        });
                        return;
                    }
                    if (!isNaN(GeneObj.end * 1)) {
                        if (GeneObj.end * 1 > 20000) {
                            layer.open({
                                type: 0,
                                title: "温馨提示:",
                                content: "输入范围值要小于20kb",
                                shadeClose: true,
                            });
                            return;
                        } else if (GeneObj.end * 1 < 0) {
                            layer.open({
                                type: 0,
                                title: "温馨提示:",
                                content: "输入数字应大于0",
                                shadeClose: true,
                            });
                            return;
                        }
                    } else {
                        layer.open({
                            type: 0,
                            title: "温馨提示:",
                            content: "请输入数字",
                            shadeClose: true,
                        });
                        return;
                    }
//                if(GeneObj.upstream*1 < GeneObj.downstream*1) {
//                    return alert("输入范围值不合理");
//                }
                return GeneObj;
            }
        }


    })
</script>