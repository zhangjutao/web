<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<style>
    #popu-paginate .total-page-count {
        display: inline-block!important;
        height: 28px;
    }
    .label-txt {
        cursor: pointer;
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        width: 140px;
    }
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
                    <option value="Chr01" data-max="56831624">Chr01</option>
                    <option value="Chr02" data-max="48577505">Chr02</option>
                    <option value="Chr03" data-max="45779781">Chr03</option>
                    <option value="Chr04" data-max="52389146">Chr04</option>
                    <option value="Chr05" data-max="42234498">Chr05</option>
                    <option value="Chr06" data-max="51416486">Chr06</option>
                    <option value="Chr07" data-max="44630646">Chr07</option>
                    <option value="Chr08" data-max="47837940">Chr08</option>
                    <option value="Chr09" data-max="50189764">Chr09</option>
                    <option value="Chr10" data-max="51566898">Chr10</option>
                    <option value="Chr11" data-max="34766867">Chr11</option>
                    <option value="Chr12" data-max="40091314">Chr12</option>
                    <option value="Chr13" data-max="45874162">Chr13</option>
                    <option value="Chr14" data-max="49042192">Chr14</option>
                    <option value="Chr15" data-max="51756343">Chr15</option>
                    <option value="Chr16" data-max="37887014">Chr16</option>
                    <option value="Chr17" data-max="41641366">Chr17</option>
                    <option value="Chr18" data-max="58018742">Chr18</option>
                    <option value="Chr19" data-max="50746916">Chr19</option>
                    <option value="Chr20" data-max="47904181">Chr20</option>
                </select>
            </div>
            <div class="item-bd-list"><span>Start Position</span><input onkeyup="this.value=this.value.replace(/\D/g,'')" type="number" min="0" placeholder="请输入数值" class="js-start-position" value="0"></div>
            <div class="item-bd-list"><span>End Position</span><input onkeyup="this.value=this.value.replace(/\D/g,'')" type="number" min="0" placeholder="请输入数值" class="js-end-position"></div>
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
            <div class="gene-search-list">
                <%--<label><span></span>GlYMA17G35620_CLE22</label>--%>
                <%--<label><span></span>GlYMA17G35620_CLE22</label>--%>
                <%--<label><span></span>GlYMA17G35620_CLE22</label>--%>
                <%--<label><span></span>GlYMA17G35620_CLE22</label>--%>
                <%--<label><span></span>GlYMA17G35620_CLE22</label>--%>
                <%--<label><span></span>GlYMA17G35620_CLE22</label>--%>
                <%--<label><span></span>GlYMA17G35620_CLE22</label>--%>
                <%--<label><span></span>GlYMA17G35620_CLE22</label>--%>
            </div>
        </div>
    </div>
    <div class="select-item selcet-set-length">
        <div class="select-item-hd">Set length</div>
        <div class="select-item-bd">
            <div class="item-bd-list"><span>Upstream: </span><input onkeyup="this.value=this.value.replace(/\D/g,'')" type="number" min="0" class="js-up-stream" placeholder="bp"></div>
            <div class="item-bd-list"><span>Downstream: </span><input onkeyup="this.value=this.value.replace(/\D/g,'')" type="number" min="0" class="js-down-stream" placeholder="bp"></div>
        </div>
    </div>
    <div class="select-item select-populations">
        <div class="select-item-hd">
            <span>Select Populations</span>
            <div class="custom-groups">
                <span class="custom-groups-btn">自定义群体</span>
                <div class="cover"></div>
                <div class="custom-groups-content">
                    <div class="sample">
                        <label><b>样本></b></label>
                        <div class="sample-text">
                            <%--<span>Glycine soja1<i class="js-colse-text">X</i></span>--%>
                            <%--<span>Glycine soja2<i class="js-colse-text">X</i></span>--%>
                            <%--<span>Glycine soja3<i class="js-colse-text">X</i></span>--%>
                            <%--<span>Glycine soja4<i class="js-colse-text">X</i></span>--%>
                            <%--<span>Glycine soja5<i class="js-colse-text">X</i></span>--%>
                        </div>
                        <div class="colse-sample">
                            <button type="button" class="btn-fill sample-empty">清空</button><span>X</span></div>
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
                            <%--<button class="multiselect"><i>+</i>多选</button>--%>
                        </div>
                        <div class="category-position" data-name="locality">
                            <label><b class="category-title">位置:</b><span>China</span><span>United States</span><span>Japan</span><span>Korea</span><span>Brazil</span><span>Other</span></label>
                            <%--<button class="multiselect"><i>+</i>多选</button>--%>
                        </div>
                        <div class="category-grain-weight" data-name="weightPer100seeds">
                            <label class="category-content"><b class="category-title">百粒重(g):</b><span>0-10</span><span>10-20</span><span>20-30</span><span>30-40</span>
                                <%--<span class="js-custom-add"></span>--%>
                            </label>
                            <div class="grain-weight-section">
                                <div class="input-range"><input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')" class="js-category-start" style="ime-mode:disabled;" onpaste="return false;"> - <input type="number" onkeyup="this.value=this.value.replace(/\D/g,'')" min="0" class="js-category-end" style="ime-mode:disabled;" onpaste="return false;"></div>
                                <button type="button" data-name="weightPer100seeds" class="btn js-customize-sample">确定</button>
                            </div>
                        </div>
                        <div class="category-oil-content" data-name="oil">
                            <label class="category-content"><b class="category-title">含油量(%):</b><span>0-10</span><span>10-15</span><span>15-20</span><span>20-25</span></label>
                            <div class="oil-content-section">
                                <div class="input-range"><input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"> - <input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"></div>
                                <button type="button" data-name="oil" class="btn js-customize-sample">确定</button>
                            </div>
                        </div>
                        <div class="category-protein " data-name="protein">
                            <label><b class="category-title">蛋白质含量(%):</b><span>30-40</span><span>40-50</span><span>50-60</span></label>
                            <div class="oil-content-section">
                                <div class="input-range"><input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"> - <input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"></div>
                                <button type="button" data-name="protein" class="btn js-customize-sample">确定</button>
                            </div>
                        </div>
                        <div class="flowering-data " data-name="floweringDate">
                            <label><b  class="category-title">开花日期(月):</b><span>6</span><span>7</span><span>8</span><span>9</span></label>
                        </div>
                        <div class="mature-data " data-name="maturityDate">
                            <label><b  class="category-title">成熟日期(月):</b><span>8</span><span>9</span><span>10</span><span>11</span></label>
                        </div>
                        <div class="plant-height" data-name="height">
                            <label  class="category-content"><b  class="category-title">株高(cm):</b><span>20-60</span><span>60-100</span><span>100-140</span><span>140-180</span></label>
                            <div class="oil-content-section">
                                <div class="input-range"><input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"> - <input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"></div>
                                <button type="button" data-name="height" class="btn js-customize-sample">确定</button>
                            </div>
                        </div>
                        <div class="grain-color " data-name="seedCoatColor">
                            <label>
                                <b  class="category-title">粒色(种皮色):</b>
                                <p>
                                <span>Bl - Black</span><span>Striped</span><span>Y - Yellow</span><span>Ggn - Grayish green</span>
                                <span>Gn - Green</span><span>Rbr - Reddish brown</span><span>Br - Brown</span><span>Lgn - Light green</span>
                                <span>Ib - Imperfect black</span><span>Gnbr - Greenish brown</span><span>dull yellow with black hila</span><span>dull yellow with imperfect black hila</span>
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
                                <span>Brbl - Brown w/black</span><span>Rbr - Reddish brown</span><span>Lbl - Light black</span><span>Ib - Imperfect black</span>
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
                                <div class="input-range"><input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"> - <input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"></div>
                                <button type="button" data-name="yield" class="btn js-customize-sample">确定</button>
                            </div>
                        </div>
                        <div class="apical-leaflet-length category-more" data-name="upperLeafletLength">
                            <label><b  class="category-title">顶端小叶长度(mm):</b><span>20-30</span><span>30-40</span><span>40-50</span></label>
                            <div class="oil-content-section">
                                <div class="input-range"><input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"> - <input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"></div>
                                <button type="button" data-name="upperLeafletLength" class="btn js-customize-sample">确定</button>
                            </div>
                        </div>
                        <div class="fatty-acid-content category-more" data-name="linoleic">
                            <label><b  class="category-title">亚油酸(%):</b><span>40-45</span><span>45-50</span><span>50-55</span><span>55-60</span></label>
                            <div class="oil-content-section">
                                <div class="input-range"><input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"> - <input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"></div>
                                <button type="button" data-name="linoleic" class="btn js-customize-sample">确定</button>
                            </div>
                        </div>
                        <div class="fatty-acid-content category-more" data-name="linolenic">
                            <label><b  class="category-title">亚麻酸(%):</b><span>0-10</span><span>10-15</span><span>15-20</span><span>20-25</span></label>
                            <div class="oil-content-section">
                                <div class="input-range"><input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"> - <input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"></div>
                                <button type="button" data-name="linolenic" class="btn js-customize-sample">确定</button>
                            </div>
                        </div>
                        <div class="fatty-acid-content category-more" data-name="oleic">
                            <label><b  class="category-title">油酸(%):</b><span>5-15</span><span>15-25</span><span>25-35</span></label>
                            <div class="oil-content-section">
                                <div class="input-range"><input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"> - <input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"></div>
                                <button type="button" data-name="oleic" class="btn js-customize-sample">确定</button>
                            </div>
                        </div>
                        <div class="fatty-acid-content category-more" data-name="palmitic">
                            <label><b  class="category-title">软脂酸(%):</b><span>9-15</span><span>15-21</span></label>
                            <div class="oil-content-section">
                                <div class="input-range"><input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"> - <input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"></div>
                                <button type="button" data-name="palmitic" class="btn js-customize-sample">确定</button>
                            </div>
                        </div>
                        <div class="fatty-acid-content category-more" data-name="stearic">
                            <label><b  class="category-title">硬脂酸(%):</b><span>2-3</span><span>3-6</span></label>
                            <div class="oil-content-section">
                                <div class="input-range"><input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"> - <input type="number" min="0" onkeyup="this.value=this.value.replace(/\D/g,'')"></div>
                                <button type="button" data-name="stearic" class="btn js-customize-sample">确定</button>
                            </div>
                        </div>
                    </div>
                    <div class="retract"><p>更多选项(开花日期、成熟日期、株高等)<img src="${ctxStatic}/images/more_unfold.png"></p></div>
                </div>
            </div>
        </div>
        <div class="select-item-bd">
            <%--<dl id="select-populations-checkbox">--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">1</span></label></dd>--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">2</span></label></dd>--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">3</span></label></dd>--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">4</span></label></dd>--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">5</span></label></dd>--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">6</span></label></dd>--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">7</span></label></dd>--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">8</span></label></dd>--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">9</span></label></dd>--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">10</span></label></dd>--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">11</span></label></dd>--%>
                <%--<dd><label for=""><span id="" data-value="" class="label-checkbox"></span><span class="label-txt">12</span></label></dd>--%>
            <%--</dl>--%>
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
<div class="tab-detail">
    <div class="tab-detail-thead">
        <p><span>1</span>号群体属性信息
            <a href="javascript:void(0)">X</a></p>
    </div>
    <div class="table-item popu-checkbox" style="display:none; box-shadow: none;">
        <div class="checkbox-item">
            <div class="che-list ">
                <span class="tab-title">表格内容:</span>
                <dl class="table_header_setting js-table-header-setting-popu">
                    <dd><label for="species" class="checkbox-ac"><span id="species" data-value="species"></span>物种</label></dd>
                    <dd><label for="locality" class="checkbox-ac"><span id="locality" data-value="locality"></span>位置</label></dd>
                    <dd><label for="sampleName" class="checkbox-ac"><span id="sampleName" data-value="sampleName"></span>样品名</label></dd>
                    <dd><label for="cultivar" class="checkbox-ac"><span id="cultivar" data-value="cultivar"></span>品种名</label></dd>
                    <dd><label for="weightPer100seeds" class="checkbox-ac"><span id="weightPer100seeds" data-value="weightPer100seeds"></span>百粒重(g)</label></dd>
                    <dd><label for="oil" class="checkbox-ac"><span id="oil" data-value="oil"></span>含油量(%)</label></dd>
                    <dd><label for="protein" class="checkbox-ac"><span id="protein" data-value="protein"></span>蛋白质含量(%)</label></dd>
                    <dd><label for="floweringDate" class="checkbox-ac"><span id="floweringDate" data-value="floweringDate"></span>开花日期</label></dd><span></span>
                    <dd><label for="maturityDate" class="checkbox-ac"><span id="maturityDate" data-value="maturityDate"></span>成熟日期</label></dd><span></span>
                    <dd><label for="height" class="checkbox-ac"><span id="height" data-value="height"></span>株高</label></dd><span></span>
                    <dd><label for="seedCoatColor" class="checkbox-ac"><span id="seedCoatColor" data-value="seedCoatColor"></span>种皮色</label></dd><span></span>
                    <dd><label for="hilumColor" class="checkbox-ac"><span id="hilumColor" data-value="hilumColor"></span>种脐色</label></dd><span></span>
                    <dd><label for="cotyledonColor" class="checkbox-ac"><span id="cotyledonColor" data-value="cotyledonColor"></span>子叶色</label></dd><span></span>
                    <dd><label for="flowerColor" class="checkbox-ac"><span id="flowerColor" data-value="flowerColor"></span>花色</label></dd><span></span>
                    <dd><label for="podColor" class="checkbox-ac"><span id="podColor" data-value="podColor"></span>荚色</label></dd><span></span>
                    <dd><label for="pubescenceColor" class="checkbox-ac"><span id="pubescenceColor" data-value="pubescenceColor"></span>茸毛色</label></dd><span></span>
                    <dd><label for="yield" class="checkbox-ac"><span id="yield" data-value="yield"></span>产量</label></dd><span></span>
                    <dd><label for="upperLeafletLength" class="checkbox-ac"><span id="upperLeafletLength" data-value="upperLeafletLength"></span>顶端小叶长度</label></dd><span></span>
                    <dd><label for="linoleic" class="checkbox-ac"><span id="linoleic" data-value="linoleic"></span>亚油酸</label></dd><span></span>
                    <dd><label for="linolenic" class="checkbox-ac"><span id="linolenic" data-value="linolenic"></span>亚麻酸</label></dd><span></span>
                    <dd><label for="oleic" class="checkbox-ac"><span id="oleic" data-value="oleic"></span>油酸</label></dd><span></span>
                    <dd><label for="palmitic" class="checkbox-ac"><span id="palmitic" data-value="palmitic"></span>软脂酸</label></dd><span></span>
                    <dd><label for="stearic" class="checkbox-ac"><span id="stearic" data-value="stearic"></span>硬脂酸</label></dd><span></span>
                </dl>
            </div>
        </div>
        <div class="export-data">
            <p class="btn-export-set">
                <button type="button" class="btn btn-export js-export-popu"><img src="${ctxStatic}/images/export.png">导出数据</button>
            </p>
        </div>
        <div class="choose-default">
            <div class="btn-default">
                <label><span class="js-choose-all "></span>全选</label>
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
            <button type="button" class="btn btn-export js-export-popu"><img src="${ctxStatic}/images/export.png">导出数据</button>
            <button type="button" class="btn popu-set-up"><img src="${ctxStatic}/images/set.png">表格设置</button>
        </p>
    </div>
    <div class="tab-detail-tbody">
        <table class="popu-table">
            <thead>
                <tr>
                    <td class="species" rowspan="2">物种</td>
                    <td class="locality" rowspan="2">位置</td>
                    <td class="sampleName" rowspan="2">样品名</td>
                    <td class="cultivar" rowspan="2">品种名</td>
                    <td class="weightPer100seeds" rowspan="2">百粒重(g)</td>
                    <td class="oil" rowspan="2">含油量(%)</td>
                    <td class="protein" rowspan="2">蛋白质含量(%)</td>
                    <td class="floweringDate" rowspan="2">开花日期(月日)</td>
                    <td class="maturityDate" rowspan="2">成熟日期(月日)</td>
                    <td class="height" rowspan="2">株高(cm)</td>
                    <td class="seedCoatColor" rowspan="2">种皮色</td>
                    <td class="hilumColor" rowspan="2">种脐色</td>
                    <td class="cotyledonColor"  rowspan="2">子叶色</td>
                    <td class="flowerColor" rowspan="2">花色</td>
                    <td class="podColor" rowspan="2">荚色</td>
                    <td class="pubescenceColor" rowspan="2">茸毛色</td>
                    <td class="yield" rowspan="2">产量(t/ha)</td>
                    <td class="upperLeafletLength" rowspan="2">顶端小叶长度(mm)</td>
                    <td colspan="5" >脂肪酸(%)</td>
                </tr>
                <tr>
                    <td class="linoleic">亚油酸</td>
                    <td class="linolenic">亚麻酸</td>
                    <td class="oleic">油酸</td>
                    <td class="palmitic">软脂酸</td>
                    <td class="stearic">硬脂酸</td>
                </tr>
            </thead>
            <tbody>
                <%--<tr>--%>
                    <%--<td>13</td><td>数值</td><td>13</td><td>数值</td><td>13</td><td>数值</td><td>13</td><td>数值</td>--%>
                    <%--<td>13</td><td>数值</td><td>13</td><td>数值</td><td>13</td><td>数值</td><td>数值</td><td>13</td>--%>
                    <%--<td>13</td><td>数值</td><td>13</td><td>数值</td><td>13</td><td>数值</td><td>数值</td>--%>
                <%--</tr>--%>
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
    </form>
</div>

<script>
    $(function(){

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
            skin: '#5c8de5',
            skip: true,
            first: 1, //将首页显示为数字1,。若不显示，设置false即可
            last: 1, //将尾页显示为总页数。若不显示，设置false即可
            prev: '<',
            next: '>',
            groups: 3
        });

        /* 侧边栏收起和展开 */
        $(".icon-right").click(function() {
            if($(".snp-content").hasClass("js-nav-ac")){
                $(".snp-content").removeClass("js-nav-ac");
            }else{
                $(".snp-content").addClass("js-nav-ac");
            }
        });
        /* search for SNPs or INDELs in Region or Gene SELECT LOGIC */
        function select(){
            var  _val = $(".selcet select option:selected").attr("data-value");
            if(_val=="gene"){
                $(".selcet-gene-function").show();
                $(".selcet-set-length").show();
                $(".select-chorosome").hide();
            }else if(_val=="region"){
                $(".selcet-gene-function").hide();
                $(".selcet-set-length").hide();
                $(".select-chorosome").show();
            }
        }
        $(".selcet select").change(function(){
            select();
        });
        select();

        $(".popu-set-up").click(function () {
            $(".popu-checkbox").show();
            $(this).parents(".export-data").hide();
        })

        /* 自定义群体-展开 */
        $(".custom-groups-btn").click( function(){
            $(".custom-groups-content").show();
            $(".cover").show();
        });

        /* 自定义群体-关闭 */
        $(".colse-sample span").click(function(){
            $(".custom-groups-content").hide();
            $(".cover").hide();
        });

        /* 自定义群体-"更多"展现 */
        $(".retract p").click(function(){
            var _dis=$(".hilum-color").css("display");
            if(_dis=="block"){
                $(".category-more").hide();
                $(".retract p").html("更多选项(开花日期、成熟日期、株高等)<img src='${ctxStatic}/images/more_unfold.png'>")
            }else {
                $(".category-more").show();
                $(".retract p").html("收起<img src='${ctxStatic}/images/less.png'>")
            }
        });

        var populations = []; // 存储自定义群体信息
        var defineDefault = [{"name":"物种Glycine soja","id": 001, "condition":{"species":"Glycine soja"}},{"name":"物种Glycine gracilis","id": 002, "condition":{"species":"Glycine gracilis"}},{"name":"物种Landrace", "id": 003, "condition":{"species":"Landrace"}},{"name":"位置China","id": 004, "condition":{"locality":"China"}},{"name":"位置United States","id":005, "condition":{"locality":"United States"}},{"name":"位置Japan","id": 006, "condition":{"locality":"Japan"}}];
        var defaultPopulations2 = defineDefault.concat();

        // 从cookie中读取群体信息并渲染
        function initPopulations() {
            if(getCookie("populations")) {
                populations = JSON.parse(getCookie("populations"));
                console.log(populations);
                var str = "";
                $.each(populations, function(idx, popu) {
                    str +="<div class='js-ad-dd'>"
                    str +="    <label class='species-add' title='" + popu.name + "' data-index='"+ popu.id +"'>"
                    str +="         <span></span><div class='label-txt'>"+ popu.name + "</div>";
                    str +="    </label>"
                    str +="    <i class='js-del-dd' data-index='" + popu.id + "'>X</i>"
                    str +="</div>"
                });
                $(".js-cursom-add").empty().append(str);
            }
        }
        initPopulations();

        function renderDefaultPopulations() {
            var str = "";
            $.each(defaultPopulations2, function(idx, popu) {
                str +="<div class='js-ad-dd'>"
                str +="    <label class='species-add' title='" + popu.name + "' data-index='"+ popu.id +"'>"
                str +="         <span></span><div class='label-txt'>"+ popu.name + "</div>";
                str +="    </label>"
                str +="    <i class='js-del-dd' data-index='" + popu.id + "'>X</i>"
                str +="</div>"
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

        function getUnitValue(obj) {
            var key = Object.keys(obj)[0];
            if(obj[key].indexOf("-") > -1) {
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
                        unit = "月";
                        break;
                    default :
                        unit = "";
                        break;
                }
                return obj[key] + unit;
            }
        }

        function replaceUnvalideChar(str) {
            console.log(str.replace(/[\%,\/]/g,"_"));
            return str.replace(/[\%,\/]/g,"_");
        }

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
            var defaultLen = $(".js-cursom-add2").find(".js-ad-dd").length;
            if(populations.length + defaultLen < 10){
                var arr = [];
                for(var i in popuSamples) {
                    var obj = {};
                    obj[i] = popuSamples[i];
                    arr.push(getKeyName(i) + getUnitValue(obj));
                }
                var o = {"name":arr.join(","), "id": new Date().getTime(), "condition":getStandardPopulation(popuSamples)};
//                initPopulations();
                appendPopulation(o);

                $(".sample-empty").trigger("click");
            } else {
                alert("最多可添加10个群体");
            }

        });

        // 向自定义群体添加
        function appendPopulation(obj) {
            var str = "";
            str +="<div class='js-ad-dd'>"
            str +="    <label class='species-add' title='" + obj.name + "' data-index='"+ obj.id +"'>"
            str +="         <span></span><div class='label-txt'>"+ obj.name + "</div>";
            str +="    </label>"
            str +="    <i class='js-del-dd' data-index='" + obj.id + "'>X</i>"
            str +="</div>"
            $(".js-cursom-add").append(str);
            populations.push(obj);
            setCookie('populations', JSON.stringify(populations));
        }

        // 向自定义群体删除
        function deletePopulation(id) {
            $.each(populations, function(idx,ele) {
                if(ele.id == id) {
                    populations.splice(idx, 1);
                    return false;
                }
            });
            setCookie('populations', JSON.stringify(populations));
        }

        function deleteDefaultPopulation(id) {
            $.each(defaultPopulations2, function(idx,ele) {
                if(ele.id == id) {
                    defaultPopulations2.splice(idx, 1);
                    return false;
                }
            });
        }

        // 勾选的群体数组
        var selectedPopulations = [];
        /* 选择自定义群体 */
        $("body").on("click",".js-ad-dd label span",function(){
            if($(this).parent().hasClass("cur")){
                $(this).parent().removeClass("cur");
            } else {
                $(this).parent().addClass("cur");
            }
            getSelectedPopulations();

            console.log(selectedPopulations);
//            if(selectedPopulations.length > 0) {
//                $(".js-default-add").find("label").removeClass("cur");
//            } else {
//                $(".js-default-add").find("label").addClass("cur");
//            }
        });

        // 根据群体生成表格内容设置，和表头
        window.renderTableHead = function () {
            var str = '', str2 = '';
            var commonStr = '<li data-value="all">ALL</li>'+
                    '<li data-type="type" data-value="downstream">Downstream</li>'+
                    '<li data-type="type" data-value="exonic;splicing">Exonic;Splicing</li>'+
                    '<li data-type="effect" data-value="nonsynonymous SNV">Exonic_nonsynonymous SNV</li>'+
                    '<li data-type="effect" data-value="stopgain">Exonic_stopgain</li>'+
                    '<li data-type="effect" data-value="stoploss">Exonic_stoploss</li>' +
                    '<li data-type="effect" data-value="synonymous SNV">Exonic_synonymous SNV</li>' +
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
                    '<li data-type="effect" data-value="frameshift deletion">Exonic_frameshift deletion</li>'+
                    '<li data-type="effect" data-value="frameshift insertion">Exonic_frameshift insertion</li>'+
                    '<li data-type="effect" data-value="frameshift insertion">Exonic_nonframeshift deletion</li>'+
                    '<li data-type="effect" data-value="nonframeshift insertion">Exonic_nonframeshift insertion</li>'+
                    '<li data-type="effect" data-value="stopgain">Exonic_stopgain</li>'+
                    '<li data-type="effect" data-value="stoploss">Exonic_stoploss</li>'+
                    '<li data-type="type" data-value="intergenic">Intergenic</li>'+
                    '<li data-type="type" data-value="intronic">Intronic</li>'+
                    '<li data-type="type" data-value="splicing">Splicing</li>'+
                    '<li data-type="type" data-value="upstream">Upstream</li>'+
                    '<li data-type="type" data-value="upstream;downstream">Upstream;Downstream</li>'+
                    '<li data-type="type" data-value="UTR3">3&acute;UTR</li>'+
                    '<li data-type="type" data-value="UTR5">5&acute;UTR</li>'+
                    '<li data-type="type" data-value="UTR5;UTR3">5&acute;UTR;3&acute;UTR</li>';

            var headStr = '<td class="t_snpid">SNP ID</td>' +
                        '<td class="param t_consequenceType">Consequence Type'+
                        '<img src="${ctxStatic}/images/down.png">'+
                        '<input type="hidden" class="js-consequence-type"> <div class="input-component "> <ul class="consequence-type ">'+ commonStr +
                        '</ul> </div></td>'+
                        '<td class="param t_snpchromosome">Chromosome</td>'+
                        '<td class="param t_position">Position</td>'+
                        '<td class="param t_snpreference">Reference</td>'+
                        '<td class="param t_majorAllele">Major Allele</td>'+
                        '<td class="param t_minorAllele">Minor Allele</td>'+
                        '<td class="param t_fmajorAllele"><select class="f-ma"><option value="major">Frequency of Major Allele</option>' +
                        '<option value="minor">Frequency of Minor Allele</option></select></td>';
            var headStr2 = '<td class="t_indels">INDEL ID</td>' +
                        '<td class="param t_iconsequenceType">Consequence Type'+
                        '<img src="${ctxStatic}/images/down.png">'+
                        '<input type="hidden" class="js-consequence-type"> <div class="input-component "> <ul class="consequence-type ">'+ commonStr2 +
                        '</ul> </div></td>'+
                        '<td class="param t_indelchromosome">Chromosome</td>'+
                        '<td class="param t_iposition">Position</td>'+
                        '<td class="param t_indelreference">Reference</td>'+
                        '<td class="param t_imajorAllele">Major Allele</td>'+
                        '<td class="param t_iminorAllele">Minor Allele</td>'+
                        '<td class="param t_ifmajorAllele"><select class="f-ma"><option value="major">Frequency of Major Allele</option>' +
                        '<option value="minor">Frequency of Minor Allele</option></select></td>';
            if(selectedPopulations.length > 0) {
                var resultPopulations = selectedPopulations;
            } else {
                var resultPopulations = defaultPopulations;
            }
            $.each(resultPopulations, function(idx, item) {
                str += '<dd><label title="'+item.name+'" data-col-name="fmajorAllelein'+ replaceUnvalideChar(item.name).split(",").join("_") +'" for="fmajorAllelein'+ replaceUnvalideChar(item.name).split(",").join("_").replace(/\s/g,"") +'" class="checkbox-ac">'+
                        '<span id="fmajorAllelein'+ replaceUnvalideChar(item.name).split(",").join("_").replace(/\s/g,"") +'" data-value="fmajorAllelein'+ item.name +'"></span>Frequency of Major Allele in '+ item.name.substr(0, 20) +'...</label></dd>'

                headStr += '<td title="'+ item.name +'" class="param t_fmajorAllelein'+ replaceUnvalideChar(item.name).split(",").join("_").replace(/\s/g,"") +'">' +
                            '<select class="f-ma">' +
                            '<option value="major">Frequency of Major Allele in '+ item.name.substr(0, 20) +'...</option>' +
                            '<option value="minor">Frequency of Minor Allele in '+ item.name.substr(0, 20) +'...</option>' +
                            '</select>' +'</td>'
                headStr2 += '<td title="'+ item.name +'" class="param t_fmajorAllelein'+ replaceUnvalideChar(item.name).split(",").join("_").replace(/\s/g,"") +'">' +
                            '<select class="f-ma">' +
                            '<option value="major">Frequency of Major Allele in '+ item.name.substr(0, 20) +'...</option>' +
                            '<option value="minor">Frequency of Minor Allele in '+ item.name.substr(0, 20) +'...</option>' +
                            '</select>' +'</td>'
            });
            $(".js-table-header-setting-snp > span").empty().append(str);
            $(".js-table-header-setting-indel > span").empty().append(str);
            $(".js-snp-table thead tr").empty().append(headStr);
            $(".js-indel-table thead tr").empty().append(headStr2);
            TableHeaderSettingSnp();
            TableHeaderSettingIndel();
        }

        /* 删除手动添加的自定义群体 */
        $("body").on("click",".js-del-dd",function(){
            $(this).parent().remove();
            var id = $(this).attr("data-index");

//            initPopulations();
            deletePopulation(id);
            getSelectedPopulations();
        });
        $(".js-cursom-add2").on("click",".js-del-dd",function(){
            $(this).parent().remove();
            getSelectedPopulations();
        });

        var currPopu = "";
        /* 显示群体信息、弹框 */
        $(".js-cursom-add").on("click",".label-txt",function(){
            var label=$(this).parent().find("label");
            if(label.hasClass("cur")){
                label.addClass("cur");
            }else{
                label.removeClass("cur");
            }
            console.log($(this).text());
            $(".tab-detail").show();
            $("#mid").show();
            $(".tab-detail-thead p span").text($(this).text());

            var id = $(this).parent("label").attr("data-index");
            currPopu = selectPopulation(id)[0];
            console.log("current:", currPopu);
            getPopuTable(1);

        });
        $(".js-cursom-add2").on("click",".label-txt",function(){
            var label=$(this).parent().find("label");
            if(label.hasClass("cur")){
                label.addClass("cur");
            }else{
                label.removeClass("cur");
            }
            console.log($(this).text());
            $(".tab-detail").show();
            $("#mid").show();
            $(".tab-detail-thead p span").text($(this).text());

            var id = $(this).parent("label").attr("data-index");
            currPopu = selectDefaulPopulation(id)[0];
            console.log("current:", currPopu);
            getPopuTable(1);

        });
//        $(".js-default-add").on("click",".label-txt",function(){
//            var label=$(this).parent().find("label");
//            if(label.hasClass("cur")){
//                label.addClass("cur");
//            }else{
//                label.removeClass("cur");
//            }
//            console.log($(this).text());
//            $(".tab-detail").show();
//            $("#mid").show();
//            $(".tab-detail-thead p span").text($(this).text());
//
//            var idx = $(this).parent("label").attr("data-index");
//            currPopu = defaultPopulations.slice(idx * 1, idx * 1 + 1)[0];
//            console.log("current:", currPopu);
//            getPopuTable(1);
//
//        });

        var pageSizePopu = 10;
        function getPopuTable(curr) {
            $.ajax({
                url: CTXROOT + "/dna/queryByGroup",
                data: {group: JSON.stringify(currPopu), pageNo: curr || 1, pageSize: pageSizePopu},
                type: "POST",
                dataType: "json",
                success: function(res) {
                    renderPopuTable(res.data);
                    laypage({
                        cont: $('#popu-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(res.total / pageSizePopu), //通过后台拿到的总页数
                        curr: curr || 1, //当前页
                        skin: '#5c8de5',
                        skip: true,
                        first: 1, //将首页显示为数字1,。若不显示，设置false即可
                        last: Math.ceil(res.total / pageSizePopu), //将尾页显示为总页数。若不显示，设置false即可
                        prev: '<',
                        next: '>',
                        groups: 3, //连续显示分页数
                        jump: function (obj, first) { //触发分页后的回调
                            if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
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
            $.each(data, function(idx, item) {
                str += '<tr>'
                str += '<td class="species">'+ item.species +'</td><td class="locality">'+ item.locality +'</td><td class="sampleName">'+ item.sampleName +'</td>'
                str += '<td class="cultivar">'+ item.cultivar +'</td><td class="weightPer100seeds">'+ item.weightPer100seeds +'</td><td class="oil">'+ item.oil +'</td>'
                str += '<td class="protein">'+ item.protein +'</td><td class="floweringDate">'+ item.floweringDate +'</td>'
                str += '<td class="maturityDate">'+ item.maturityDate +'</td><td class="height">'+item.height+'</td><td class="seedCoatColor">'+item.seedCoatColor+'</td>'
                str += '<td class="hilumColor">'+item.hilumColor+'</td><td class="cotyledonColor">'+item.cotyledonColor+'</td><td class="flowerColor">'+item.flowerColor+'</td>'
                str += '<td class="podColor">'+item.podColor+'</td><td class="pubescenceColor">'+item.pubescenceColor+'</td>'
                str += '<td class="yield">'+item.yield+'</td><td class="upperLeafletLength">'+item.upperLeafletLength+'</td>'
                str += '<td class="linoleic">'+item.linoleic+'</td><td class="linolenic">'+item.linolenic+'</td><td class="oleic">'+item.oleic+'</td>'
                str += '<td class="palmitic">'+item.palmitic+'</td><td class="stearic">'+item.stearic+'</td>'
                str += '</tr>'
            });
            $(".popu-table > tbody").empty().append(str);
        }

        $(".js-popu-setting-btn").click(function(){
            var _labels = $(".js-table-header-setting-popu").find("label");
            $.each(_labels, function(idx, item) {
                var cls = "." + $(item).attr("for");
                if(!$(item).hasClass("checkbox-ac")) {
                    $(".popu-table").find(cls).hide();
                } else {
                    $(".popu-table").find(cls).show();
                }
            });
        });


        /* 关闭群体信息、弹框 */
        $(".tab-detail-thead p a").click(function(){
            $(".tab-detail").hide();
            $("#mid").hide();
        });

        /* 群体信息弹框可拖动 */
        $( ".tab-detail" ).draggable({ containment: "body"});

        var popuSamples = {}; // 存储选中的样本数据

        /* 自定义样本选中 */
        $("body").on("click", ".sample-category > div span", function(){
            $(this).parent().find("span").removeClass("js-span-ac");
            $(this).addClass("js-span-ac");

            var sampleName = $(this).parents("label").parent().attr("data-name"),
            sampleValue = $(this).text();
            popuSamples[sampleName] = sampleValue;
            renderSampleText();

            renderSampleCount();
        });

        function renderSampleCount() {
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
        }
        renderSampleCount();

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
            renderSampleCount();
//            $(".js-total-samples").html(0);
        });

        /* 删除单个样本条件 */
        $(".sample-text").on("click", ".js-colse-text", function(){
            $(this).parent().remove();
            var name = $(this).parent().attr("data-name");
            delete(popuSamples[name]);

            renderSampleCount();

            $(".sample-category").find("div[data-name='"+name+"']").find("span").removeClass("js-span-ac");
        });

        /* 自定义 样本 */
        $(".js-customize-sample").click(function() {
            // 百分比类型数组
            var percetageArray = ["oil", "protein", "linoleic", "linolenic", "oleic", "palmitic", "stearic"];
            var name = $(this).attr("data-name");
            var _prev = $(this).prev();
            var val1 = _prev.find("input").first().val() * 1;
            var val2 = _prev.find("input").last().val() * 1;
            var str = val1 + "-" + val2;
            if(val1 != "" && val2 != "") {
                if($.inArray($(this).attr("data-name"), percetageArray) > -1) {
                    if((val1 < 0 || val2 < 0) || val1 > val2 || (val1 > 100 || val2 > 100)) {
                        alert("添加的区间数据不合理,如果是百分比则要小于100");
                    } else {
                        var spans = $(this).parent().siblings("label").find("span:not('.hidden')");
                        var arr = [];
                        $.each(spans, function (idx, ele) {
                            arr.push($(ele).text());
                        });
                        if ($.inArray(str, arr) > -1) {
                            alert("添加区间重复");
                        } else {
                            var ss = $("<span class='hidden' style='display: none;'>" + str + "</span>");
                            $(".sample-category").find("div[data-name='" + name + "']").find("label").first().append(ss);
                            ss.trigger("click");
                        }
                    }
                } else {
                    if ((val1 < 0 || val2 < 0) || val1 > val2) {
                        alert("添加的区间数据不合理");
                    } else {
                        var spans = $(this).parent().siblings("label").find("span:not('.hidden')");
                        var arr = [];
                        $.each(spans, function (idx, ele) {
                            arr.push($(ele).text());
                        });
                        if ($.inArray(str, arr) > -1) {
                            alert("添加区间重复");
                        } else {
                            var ss = $("<span class='hidden' style='display: none;'>" + str + "</span>");
                            $(".sample-category").find("div[data-name='" + name + "']").find("label").first().append(ss);
                            ss.trigger("click");
                        }
                    }
                }
            } else {
                alert("输入不能为空");
            }
        });


        /* 显示Samples详细文本 */
        $(".sample-category div span").hover(function () {
            if ($(this).text() !== "") {
                var self = this;
                var content = "";
                var obj = {};
                var sampleName = $(this).parents("label").parent().attr("data-name"),
                    sampleValue = $(this).text();
                obj[sampleName] = sampleValue;

                var arr = [];
                for(var i in obj) {
                    arr.push(getKeyName(i) + popuSamples[i]);
                }
                var obj = {"name":arr.join(","), "condition":getStandardPopulation(obj)};
                $.ajax({
                    url: CTXROOT + "/dna/queryByGroup",
                    data: {group: JSON.stringify(obj), pageNo: 1 || 1, pageSize: 10},
                    type: "POST",
                    dataType: "json",
                    success: function(res) {
                        content += "<div>" + res.total + "</div>";
                        $.pt({
                            target: self,
                            position: 't',
                            align: 'l',
                            autoClose: false,
                            content: content
                        });
                        $(".pt").css("left", $(".pt").position().left);
                    }
                });

            } else {
                $(".pt").remove();
            }
        },function () {
            $(".pt").remove();
        });

        /* 选择搜索基因 */
        $("body").on("click",".gene-search-list label", function() {
            $(".gene-search-list label").removeClass("checkbox-ac");
//            var span = $(this).find("span");
            var val=$('input:radio[name="sex"]:checked');
            if($(this).hasClass("checkbox-ac")){
                $(this).removeClass("checkbox-ac");
            } else {
                $(this).addClass("checkbox-ac");
                GeneObj.gene = $(this).text().split("_")[0];
                console.log(GeneObj.gene)
            }
        });

        $(".js-search-gene-btn").click(function() {
            var gene = $(".js-search-gene-text").val();
            $.ajax({
                url: "${ctxroot}/dna/queryByGene",
                data: {gene: gene, pageSize: 100, pageNo: 1},
                type: "GET",
                dataType: "json",
                timeout: 10000,
                success: function(res) {
                    console.log(res)
                    if(res.data.length > 0) {
                        var len = res.data.length;
                        var str = '';
                        for(var i = 0; i < len; i++) {
                            str += '<label><input type="radio" name="radio" style="vertical-align: middle">'+ res.data[i].gene +'_'+ res.data[i].geneName +'<i style="display:none">_'+ res.data[i].geneFunction+'</i></label>'
                        }
                        $(".gene-search-list").empty().append(str);
                    } else {
                        $(".gene-search-list").empty();
                    }
                    $(document).on("mouseover mouseout",".gene-search-list label",function(e){
                        var e= e||event;
                        if(e.type == "mouseover"){
                            var _text=$(this).text()
                            var self = this;
                            var content = "<p>"+_text+"</p>";
                            $.pt({
                                target: self,
                                position: 't',
                                align: 'l',
                                autoClose: false,
                                content: content
                            });
                            $(".pt").css("left", $(".pt").position().left);
                        }else if(e.type == "mouseout"){
                            $(".pt").remove();
                        }

                    })
                },
                error: function(err){
                    window.location.href = '${ctxroot}/login'
                }
            });
        });

        /* 导出 */
        $(".js-export-popu").click(function() {
            var _labels = $(".popu-checkbox").find(".js-table-header-setting-popu").find("label");
            var choiceArr = [];
            $.each(_labels, function(idx, item) {
                if($(item).hasClass("checkbox-ac")) {
                    choiceArr.push($(item).attr("for"));
                }
            });
            $("#exportForm").find(".group").val(JSON.stringify(currPopu));
            $("#exportForm").find(".choices").val(choiceArr.join(","));
            $("#exportForm").submit();

        });

        function linkEndPosition() {
            var chromosomeMax = $(".js-chorosome option:selected").attr("data-max");
            $(".js-end-position").attr("placeholder", "<="+chromosomeMax);
        }
        linkEndPosition();
        $(".js-chorosome").change(function() {
           linkEndPosition();
        });

        /* 定义 Region & Gene 搜索条件 */
        var RegionObj = {
            "chromosome": "",
            "start": "",
            "end": ""
        }, GeneObj = {
            "gene": "",
            "upstream": "",
            "downstream": ""
        };

        // 根据ID获取选中的population
        function selectPopulation(id) {
            var o = [];
            $.each(populations, function(idx, ele) {
                if(ele.id == id) {
                    o.push(ele);
                }
            });
            return o;
        }
        function selectDefaulPopulation(id) {
            var o = [];
            $.each(defaultPopulations2, function(idx, ele) {
                if(ele.id == id) {
                    o.push(ele);
                }
            });
            return o;
        }

        function getSelectedPopulations() {
            selectedPopulations = [];
            $.each($(".js-cursom-add").find(".js-ad-dd"), function(idx, element) {
                if($(element).find("label").hasClass("cur")) {
                    var id = $(element).find("label").attr("data-index");
//                    var selectedItem = populations.slice(idx*1, idx*1+1);
                    var selectedItem = selectPopulation(id);
                    // console.log("selected", selectedItem);
                    selectedPopulations.push(selectedItem[0]);
                }
            });
            $.each($(".js-cursom-add2").find(".js-ad-dd"), function(idx, element) {
                if($(element).find("label").hasClass("cur")) {
                    var id = $(element).find("label").attr("data-index");
                    var selectedItem = selectDefaulPopulation(id);
                    // console.log("selected", selectedItem);
                    selectedPopulations.push(selectedItem[0]);
                }
            });
        }

        window.GetPanelParams = {
            getPanelType: function() {
                return $(".selcet select option:selected").attr("data-value");
            },
            getRegionParams : function() {
                RegionObj.chromosome = $(".js-chorosome").val();
                RegionObj.start = $(".js-start-position").val();
                RegionObj.end = $(".js-end-position").val();
                var chromosomeMax = $(".js-chorosome option:selected").attr("data-max");
                if(selectedPopulations.length > 0) {
                    RegionObj["group"] = JSON.stringify(selectedPopulations);
                } else {
//                    RegionObj["group"] = JSON.stringify(defaultPopulations);
                    RegionObj["group"] = JSON.stringify([]);
                }
                if(RegionObj.start == "" || RegionObj.end == "") {
                    return alert("输入不能为空");
                }
                if(!isNaN(RegionObj.start*1) && !isNaN(RegionObj.end*1)) {
                    if(RegionObj.start*1 < 0 || RegionObj.end*1 < 0) {
                        return alert("输入数字应大于0");
                    }
                    if(RegionObj.start*1 > chromosomeMax*1 || RegionObj.end*1 > chromosomeMax*1) {
                        return alert("输入值超过该基因最大值");
                    }
                    if(RegionObj.start*1 > RegionObj.end*1) {
                        return alert("StartPosition应小于EndPosition");
                    }
                    if(RegionObj.end*1 - RegionObj.start*1 > 100000) {
                        return alert("区间应小于100 kb");
                    }
                    return RegionObj;
                } else {
                    alert("请输入数字");
                }

            },
            getGeneParams: function() {
                GeneObj.upstream = $(".js-up-stream").val();
                GeneObj.downstream = $(".js-down-stream").val();
                if(selectedPopulations.length > 0) {
                    GeneObj["group"] = JSON.stringify(selectedPopulations);
                } else {
//                    GeneObj["group"] = JSON.stringify(defaultPopulations);
                    GeneObj["group"] = JSON.stringify([]);
                }
                if(GeneObj.gene == "") {
                    return alert("请选择一个基因");
                }
                if(GeneObj.upstream == "") {
                    delete GeneObj["upstream"];
                } else {
                    if(!isNaN(GeneObj.upstream * 1)) {
                        if (GeneObj.upstream * 1 > 200000) {
                            return alert("输入范围值要小于20kb");
                        } else if (GeneObj.upstream * 1 < 0) {
                            return alert("输入数字应大于0");
                        }
                    } else {
                        return alert("请输入数字");
                    }
                }
                if(GeneObj.downstream == "") {
                    delete GeneObj["downstream"];
                } else {
                    if(!isNaN(GeneObj.downstream * 1)) {
                        if (GeneObj.downstream * 1 > 200000) {
                            return alert("输入范围值要小于20kb");
                        } else if (GeneObj.downstream * 1 < 0) {
                            return alert("输入数字应大于0");
                        }
                    } else {
                        return alert("请输入数字");
                    }
                }
//                if(GeneObj.upstream*1 < GeneObj.downstream*1) {
//                    return alert("输入范围值不合理");
//                }
                return GeneObj;
            }
        }
    })
</script>