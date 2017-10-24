<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>SNP list</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/mRNA.css">
    <link rel="stylesheet" href="${ctxStatic}/css/DNA.css">
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">
    <link href="https://cdn.bootcss.com/normalize/7.0.0/normalize.min.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
</head>
<body>
<header>
    <div class="container">
        <div class="logo">
            <a href="http://www.gooalgene.com" target="_blank" class="qtl-logo-pic"><img src="${ctxStatic}/images/logo.png"></a>
            <a href="dna-index.jsp" class="qtl-data">SNP INDEL Database</a>
        </div>
        <div class="login-out"></div>
    </div>
</header>
<!--header-->
<div class="container snp-content">
    <div class="nav_ac">
        <div class="icon-right"><img src="images/Category.png"></div>
    </div>
    <div class="aside box-shadow">
        <div class="item-header">
            <div class="icon-left"><img src="images/bookmarks.png">SNP/INDEL</div>
            <div class="icon-right"><img src="images/Category.png"></div>
        </div>
        <div class="selcet">
            <select>
                <option class="region" data-value="region">search for SNPs/INDELs in Region</option>
                <option data-value="gene">search for SNPs/INDELs in Gene</option>
            </select>
        </div>
        <div class="select-item select-chorosome">
            <div class="select-item-hd">select Chorosome</div>
            <div class="select-item-bd">
                <div class="item-bd-list"><span>Chorosome</span><select><option>请选择</option></select></div>
                <div class="item-bd-list"><span>Start Posittion</span><input type="text" placeholder="请输入数值"></div>
                <div class="item-bd-list"><span>End Posittion</span><input  placeholder="请输入数值"></div>
            </div>
        </div>
        <div class="select-item selcet-gene-function">
            <div class="select-item-hd">Search for SNPs/INDELs with Gene and Function</div>
            <div calss="select-item-bd">
                <div class="gene-search">
                    <label>
                        <input type="text" placeholder="请输入关键词"/>
                        <span><img src="images/search.png">搜索</span>
                    </label>
                </div>
                <div class="gene-search-list">
                    <label><span></span>GlYMA17G35620_CLE22</label>
                    <label><span></span>GlYMA17G35620_CLE22</label>
                    <label><span></span>GlYMA17G35620_CLE22</label>
                    <label><span></span>GlYMA17G35620_CLE22</label>
                    <label><span></span>GlYMA17G35620_CLE22</label>
                    <label><span></span>GlYMA17G35620_CLE22</label>
                    <label><span></span>GlYMA17G35620_CLE22</label>
                    <label><span></span>GlYMA17G35620_CLE22</label>
                </div>
            </div>
        </div>
        <div class="select-item selcet-set-length">
            <div class="select-item-hd">Set length</div>
            <div class="select-item-bd">
                <div class="item-bd-list"><span>Upstream: </span><input type="text" placeholder="bp"></div>
                <div class="item-bd-list"><span>Upstream: </span><input type="text" placeholder="bp"></div>
            </div>
        </div>
        <div class="select-item select-populations">
            <div class="select-item-hd">
                <span>select Populations</span>
                <div class="custom-groups">
                    <span class="custom-groups-btn btn-fill">自定义群体</span>
                    <div class="cover"></div>
                    <div class="custom-groups-content">
                        <div class="sample">
                            <label><b>样本></b></label>
                            <div class="sample-text">
                                <span>Glycine soja1<i class="js-colse-text">X</i></span>
                                <span>Glycine soja2<i class="js-colse-text">X</i></span>
                                <span>Glycine soja3<i class="js-colse-text">X</i></span>
                                <span>Glycine soja4<i class="js-colse-text">X</i></span>
                                <span>Glycine soja5<i class="js-colse-text">X</i></span>
                            </div>
                            <div class="colse-sample">
                                <button class="btn-fill sample-empty">清空</button><span>X</span></div>
                        </div>
                        <div class="sample-screening">
                            <div class="sample-screening-title">样本筛选(共<span>xxxx</span>个sample)</div>
                            <div class="sample-screening-btn">
                                <button class="btn">保存群体</button>
                            </div>
                        </div>
                        <div class="sample-category">
                            <div class="category-group">
                                <label><b class="category-title">群体:</b><span>Glycine soja</span><span>Glycine gracilis</span><span>Landrace</span><span>Improved  Cultivar</span><span>Mutant cultivar</span></label>
                                <!--<button class="multiselect"><i>+</i>多选</button>-->
                            </div>
                            <div class="category-position">
                                <label><b class="category-title">位置:</b><span>China</span><span>United States</span><span>Japan</span><span>korea</span><span>Brazil</span></label>
                                <!--<button class="multiselect"><i>+</i>多选</button>-->
                            </div>
                            <div class="category-grain-weight">
                                <label><b class="category-title">百粒重:</b><span>0g-5g</span><span>5g-10</span><span>10g-15g</span><span>15g-20g</span></label>
                                <div class="grain-weight-section">
                                    <label><input> g ~ <input>g</label>
                                    <button class="btn">确定</button>
                                </div>
                            </div>
                            <div class="category-oil-content">
                                <label><b class="category-title">含油量:</b><span>0%~2%</span><span>2%~5%</span><span>5%~7%</span><span>7%~10%</span></label>
                                <div class="oil-content-section">
                                    <label><input> %~ <input>%</label>
                                    <button class="btn">确定</button>
                                </div>
                            </div>
                            <div class="category-protein category-more">
                                <label><b  class="category-title">蛋白质:</b><span>1</span><span>2</span><span>3</span><span>4</span><span>5</span><span>6</span></label>
                            </div>
                            <div class="category-protein category-more">
                                <label><b class="category-title">蛋白质:</b><span>0%~2%</span><span>2%~5%</span><span>5%~7%</span><span>7%~10%</span></label>
                            </div>
                            <div class="flowering-data category-more">
                                <label><b  class="category-title">开花日期:</b><span>6月</span><span>7月</span><span>8月</span><span>9月</span></label>
                            </div>
                            <div class="mature-data category-more">
                                <label><b  class="category-title">成熟日期:</b><span>8月</span><span>9月</span><span>10月</span><span>11月</span></label>
                            </div>
                            <div class="plant-height category-more">
                                <label><b  class="category-title">株高(cm):</b><span>8月</span><span>9月</span><span>10月</span><span>11月</span></label>
                            </div>
                            <div class="grain-color category-more">
                                <label><b  class="category-title">粒色(种皮色):</b><span>Bl-Black</span><span>Br-Brown</span><span>Ggn-Grayish green</span><span>Gn-Green</span></label>
                            </div>
                            <div class="hilum-color category-more">
                                <label><b  class="category-title">种脐色:</b><span>Bf-Buff</span><span>Bl-Black</span><span>Br-Brown</span></label>
                            </div>
                            <div class="cotyledon-colour category-more">
                                <label><b  class="category-title">子叶色:</b><span>Gn-Green</span><span>Gn-Green</span></label>
                            </div>
                            <div class="flower-colour category-more">
                                <label><b  class="category-title">花色:</b><span>Lp - Light purple</span><span>P - Purple</span><span>W - White</span></label>
                            </div>
                            <div class="pod-color category-more">
                                <label><b  class="category-title">荚色:</b><span>Bl - Black</span><span>Br - Brown</span><span>Dbr - Dark brown</span><span>Tn - Tan</span></label>
                            </div>
                            <div class="hair-color category-more">
                                <label><b  class="category-title">茸毛色:</b><span>Br - Brown</span><span>T - Tawny</span><span>Lt - Light tawny</span><span>G - Gray</span><span>Ng - Near</span><span>gray</span></label>
                            </div>
                            <div class="yield category-more">
                                <label><b  class="category-title">产量(t/ha):</b><span>0-1</span><span>1-2</span><span>2-3</span><span>3-4</span><span>4-5</span></label>
                            </div>
                            <div class="apical-leaflet-length category-more">
                                <label><b  class="category-title">顶端小叶长度(mm):</b><span>20-30</span><span>30-40</span><span>40-50</span></label>
                            </div>
                            <div class="fatty-acid-content category-more">
                                <label><b  class="category-title">亚油酸:</b><span>20-30</span><span>30-40</span><span>40-50</span></label>
                            </div>
                            <div class="fatty-acid-content category-more">
                                <label><b  class="category-title">亚麻酸:</b><span>20-30</span><span>30-40</span><span>40-50</span></label>
                            </div>
                            <div class="fatty-acid-content category-more">
                                <label><b  class="category-title">油酸:</b><span>20-30</span><span>30-40</span><span>40-50</span></label>
                            </div>
                            <div class="fatty-acid-content category-more">
                                <label><b  class="category-title">软脂酸:</b><span>20-30</span><span>30-40</span><span>40-50</span></label>
                            </div>
                            <div class="fatty-acid-content category-more">
                                <label><b  class="category-title">硬脂酸:</b><span>20-30</span><span>30-40</span><span>40-50</span></label>
                            </div>
                        </div>
                        <div class="retract"><p>收起<img src="images/less.png"></p></div>
                    </div>
                </div>
            </div>
            <div class="select-item-bd">
                <dl id="select-populations-checkbox">
                    <dd><label for=""><span id="" data-value=""></span>1</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>2</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>3</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>4</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>5</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>6</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>7</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>8</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>9</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>10</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>11</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>12</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>13</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>14</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>15</label></dd>
                    <dd><label for=""><span id="" data-value=""></span>16</label></dd>
                </dl>
            </div>
        </div>
        <div class="confirmed-btn">
            <button class="btn">确定</button>
        </div>
    </div>
    <div class="contant ">
        <div class="table-item box-shadow snp-checkbox" style="display: none">
            <div class="checkbox-item">
                <div class="che-list">
                    <span class="tab-title">表格内容:</span>
                    <dl id="table_header_setting">
                        <dd><label for="sampleName" class="checkbox-ac"><span id="sampleName" data-value="sampleName"></span>Sample name</label></dd>
                        <dd><label for="study" class="checkbox-ac"><span id="study" data-value="study"></span>Study</label></dd>
                        <dd><label for="reference" class="checkbox-ac"><span id="reference" data-value="reference"></span>Reference</label></dd>
                        <dd><label for="tissue" class="checkbox-ac"><span id="tissue" data-value="tissue"></span>Tissue</label></dd>
                        <dd><label for="preservation" class="checkbox-ac"><span id="preservation" data-value="preservation"></span>Preservation</label></dd>
                        <dd><label for="phenoType" class="checkbox-ac"><span id="phenoType" data-value="phenoType"></span>Phenotype</label></dd>
                        <dd><label for="environment" class="checkbox-ac"><span id="environment" data-value="environment"></span>Environment</label></dd>
                        <dd><label for="cultivar" class="checkbox-ac"><span id="cultivar" data-value="cultivar"></span>Cultivar</label></dd>
                        <dd><label for="spots" class="checkbox-ac"><span id="spots" data-value="spots"></span>Spots</label></dd>
                        <dd><label for="sampleRun"><span id="sampleRun" data-value="sampleRun"></span>Run</label></dd>
                        <dd><label for="sraStudy"><span id="sraStudy" data-value="sraStudy"></span>SRAStudy</label></dd>
                        <dd><label for="experiment"><span id="experiment" data-value="experiment"></span>Experiment</label></dd>
                    </dl>
                </div>
            </div>
            <div class="export-data">
                <p class="btn-export-set">
                    <button class="btn btn-export">导出数据</button>
                </p>
            </div>
            <div class="choose-default">
                <div class="btn-default">
                    <label><span class="js-choose-all" style="background-image: url(images/contrast.png)"></span>全选</label>
                    <label class="js-btn-default"><span></span>默认</label>
                </div>
                <div class="btn-group" style="display: block;">
                    <button class="btn-fill btn-confirm">确认</button>
                    <button class="btn-chooseAll" id="clear-all">清空</button>
                    <button class="btn-toggle">收起<img src="images/down.png"></button>
                </div>
            </div>
        </div>
        <div class="box-shadow resulting">
            <div class="item-header">
                <div class="icon-left">
                    <img src="images/result.png">结果
                    <span>搜索条件:1号染色体、范围xxxbp-xxxbp</span>
                    <i>></i>
                </div>
                <div class="icon-right">
                    <p>共<span>12345</span>条结果</p>
                </div>
            </div>
            <div class="tab-item">
                <ul class="item">
                    <li class="item-ac">SNPs</li>
                    <li class="">INDELs</li>
                </ul>
                <div class="tab">
                    <div class="tab-txt tab-txt-ac">
                        <div class="export-data">
                            <p class="btn-export-set">
                                <button class="btn btn-export">导出数据</button>
                                <button class="btn set-up" >表格设置</button>
                            </p>
                        </div>
                        <div class="genes-tab tab-txt-snps" style="height: auto;">
                            <table>
                                <thead>
                                    <tr>
                                        <td class="t_snpid">SNP ID</td>
                                        <td class="param t_reference">Consequence Type<img src="images/down.png">
                                            <div class="input-component ">
                                                <input type="text" placeholder="请输入" class="js-reference">
                                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                            </div>
                                        </td>
                                        <td class="param t_tissue">Chromosome<img src="images/down.png">
                                            <div class="input-component ">
                                                <input type="text" placeholder="请输入" class="js-tissue">
                                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                            </div>
                                        </td>
                                        <td class="param t_preservation">Position<img src="images/down.png">
                                            <div class="input-component ">
                                                <input type="text" placeholder="请输入" class="js-preservation">
                                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                            </div>
                                        </td>
                                        <td class="param t_phenoType">Reference<img src="images/down.png">
                                            <div class="input-component ">
                                                <input type="text" placeholder="请输入" class="js-pheno-type">
                                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                            </div>
                                        </td>
                                        <td class="param t_environment">Major Allele<img src="images/down.png">
                                            <div class="input-component ">
                                                <input type="text" placeholder="请输入" class="js-environment">
                                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                            </div>
                                        </td>
                                        <td class="param t_cultivar">Minor Allele<img src="images/down.png">
                                            <div class="input-component ">
                                                <input type="text" placeholder="请输入" class="js-cultivar">
                                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                            </div>
                                        </td>
                                        <td class="param t_spots">
                                            <select class="f-ma">
                                                <option>Frequency of Major Allele</option>
                                                <option>Frequency of Minor Allele</option>
                                            </select>
                                            <!--Frequency of Major Allele-->
                                            <img src="images/down.png">
                                            <!--<div class="input-component ">-->
                                                <!--<input type="text" placeholder="请输入" class="js-spots">-->
                                                <!--<p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>-->
                                            <!--</div>-->
                                        </td>
                                        <td class="param t_sampleRun" style="display: table-cell;">
                                            <select class="f-ma-in">
                                                <option>Frequency of Major Allele in</option>
                                                <option>Frequency of Minor Allele in</option>
                                            </select>
                                            <!--Frequency of Major Allele in-->
                                            <img src="images/down.png">
                                            <!--<div class="input-component " >-->
                                                <!--<input type="text" placeholder="请输入" class="js-sample-run">-->
                                                <!--<p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>-->
                                            <!--</div>-->
                                        </td>
                                    </tr>
                                </thead>
                                <tbody id="tableBody">
                                    <tr>
                                        <td class="t_snpid">sf1111111</td>
                                        <td class="t_reference"><p class="js-tipes-show">"Okamoto S, Suzuki T, Kawaguchi M, et al. A comprehensive strategy for identifying long‐distance mobile peptides in xylem sap[J]. Plant Journal, 2015, 84(3):611-620."</p></td>
                                        <td class="t_tissue"><p>stem internode</p></td>
                                        <td class="t_environment"><p class="js-tipes-show"></p></td>
                                        <td class="t_cultivar"><p>Enrei</p></td>
                                        <td class="t_spots"><p>12346873</p></td>
                                        <td class="t_sampleRun" style="display: table-cell;"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRR029571</a></p></td>
                                        <td class="t_sraStudy"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRP002726</a></p></td>
                                        <td class="t_experiment"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRX026629</a></p></td>
                                    </tr>
                                    <tr>
                                        <td class="t_snpid">sf1111111</td>
                                        <td class="t_reference"><p class="js-tipes-show">"Okamoto S, Suzuki T, Kawaguchi M, et al. A comprehensive strategy for identifying long‐distance mobile peptides in xylem sap[J]. Plant Journal, 2015, 84(3):611-620."</p></td>
                                        <td class="t_tissue"><p>stem internode</p></td>
                                        <td class="t_environment"><p class="js-tipes-show"></p></td>
                                        <td class="t_cultivar"><p>Enrei</p></td>
                                        <td class="t_spots"><p>12346873</p></td>
                                        <td class="t_sampleRun" style="display: table-cell;"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRR029571</a></p></td>
                                        <td class="t_sraStudy"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRP002726</a></p></td>
                                        <td class="t_experiment"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRX026629</a></p></td>
                                    </tr>
                                    <tr>
                                        <td class="t_snpid">sf1111111</td>
                                        <td class="t_reference"><p class="js-tipes-show">"Okamoto S, Suzuki T, Kawaguchi M, et al. A comprehensive strategy for identifying long‐distance mobile peptides in xylem sap[J]. Plant Journal, 2015, 84(3):611-620."</p></td>
                                        <td class="t_tissue"><p>stem internode</p></td>
                                        <td class="t_environment"><p class="js-tipes-show"></p></td>
                                        <td class="t_cultivar"><p>Enrei</p></td>
                                        <td class="t_spots"><p>12346873</p></td>
                                        <td class="t_sampleRun" style="display: table-cell;"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRR029571</a></p></td>
                                        <td class="t_sraStudy"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRP002726</a></p></td>
                                        <td class="t_experiment"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRX026629</a></p></td>
                                    </tr>
                                    <tr>
                                        <td class="t_snpid">sf1111111</td>
                                        <td class="t_reference"><p class="js-tipes-show">"Okamoto S, Suzuki T, Kawaguchi M, et al. A comprehensive strategy for identifying long‐distance mobile peptides in xylem sap[J]. Plant Journal, 2015, 84(3):611-620."</p></td>
                                        <td class="t_tissue"><p>stem internode</p></td>
                                        <td class="t_environment"><p class="js-tipes-show"></p></td>
                                        <td class="t_cultivar"><p>Enrei</p></td>
                                        <td class="t_spots"><p>12346873</p></td>
                                        <td class="t_sampleRun" style="display: table-cell;"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRR029571</a></p></td>
                                        <td class="t_sraStudy"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRP002726</a></p></td>
                                        <td class="t_experiment"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRX026629</a></p></td>
                                    </tr>
                                    <tr>
                                        <td class="t_snpid">sf1111111</td>
                                        <td class="t_reference"><p class="js-tipes-show">"Okamoto S, Suzuki T, Kawaguchi M, et al. A comprehensive strategy for identifying long‐distance mobile peptides in xylem sap[J]. Plant Journal, 2015, 84(3):611-620."</p></td>
                                        <td class="t_tissue"><p>stem internode</p></td>
                                        <td class="t_environment"><p class="js-tipes-show"></p></td>
                                        <td class="t_cultivar"><p>Enrei</p></td>
                                        <td class="t_spots"><p>12346873</p></td>
                                        <td class="t_sampleRun" style="display: table-cell;"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRR029571</a></p></td>
                                        <td class="t_sraStudy"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRP002726</a></p></td>
                                        <td class="t_experiment"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRX026629</a></p></td>
                                    </tr>
                                    <tr>
                                        <td class="t_snpid">sf1111111</td>
                                        <td class="t_reference"><p class="js-tipes-show">"Okamoto S, Suzuki T, Kawaguchi M, et al. A comprehensive strategy for identifying long‐distance mobile peptides in xylem sap[J]. Plant Journal, 2015, 84(3):611-620."</p></td>
                                        <td class="t_tissue"><p>stem internode</p></td>
                                        <td class="t_environment"><p class="js-tipes-show"></p></td>
                                        <td class="t_cultivar"><p>Enrei</p></td>
                                        <td class="t_spots"><p>12346873</p></td>
                                        <td class="t_sampleRun" style="display: table-cell;"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRR029571</a></p></td>
                                        <td class="t_sraStudy"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRP002726</a></p></td>
                                        <td class="t_experiment"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRX026629</a></p></td>
                                    </tr>
                                    <tr>
                                        <td class="t_snpid">sf1111111</td>
                                        <td class="t_reference"><p class="js-tipes-show">"Okamoto S, Suzuki T, Kawaguchi M, et al. A comprehensive strategy for identifying long‐distance mobile peptides in xylem sap[J]. Plant Journal, 2015, 84(3):611-620."</p></td>
                                        <td class="t_tissue"><p>stem internode</p></td>
                                        <td class="t_environment"><p class="js-tipes-show"></p></td>
                                        <td class="t_cultivar"><p>Enrei</p></td>
                                        <td class="t_spots"><p>12346873</p></td>
                                        <td class="t_sampleRun" style="display: table-cell;"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRR029571</a></p></td>
                                        <td class="t_sraStudy"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRP002726</a></p></td>
                                        <td class="t_experiment"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRX026629</a></p></td>
                                    </tr>
                                    <tr>
                                        <td class="t_snpid">sf1111111</td>
                                        <td class="t_reference"><p class="js-tipes-show">"Okamoto S, Suzuki T, Kawaguchi M, et al. A comprehensive strategy for identifying long‐distance mobile peptides in xylem sap[J]. Plant Journal, 2015, 84(3):611-620."</p></td>
                                        <td class="t_tissue"><p>stem internode</p></td>
                                        <td class="t_environment"><p class="js-tipes-show"></p></td>
                                        <td class="t_cultivar"><p>Enrei</p></td>
                                        <td class="t_spots"><p>12346873</p></td>
                                        <td class="t_sampleRun" style="display: table-cell;"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRR029571</a></p></td>
                                        <td class="t_sraStudy"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRP002726</a></p></td>
                                        <td class="t_experiment"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRX026629</a></p></td>
                                    </tr>
                                    <tr>
                                        <td class="t_snpid">sf1111111</td>
                                        <td class="t_reference"><p class="js-tipes-show">"Okamoto S, Suzuki T, Kawaguchi M, et al. A comprehensive strategy for identifying long‐distance mobile peptides in xylem sap[J]. Plant Journal, 2015, 84(3):611-620."</p></td>
                                        <td class="t_tissue"><p>stem internode</p></td>
                                        <td class="t_environment"><p class="js-tipes-show"></p></td>
                                        <td class="t_cultivar"><p>Enrei</p></td>
                                        <td class="t_spots"><p>12346873</p></td>
                                        <td class="t_sampleRun" style="display: table-cell;"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRR029571</a></p></td>
                                        <td class="t_sraStudy"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRP002726</a></p></td>
                                        <td class="t_experiment"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRX026629</a></p></td>
                                    </tr>
                                    <tr>
                                        <td class="t_snpid">sf1111111</td>
                                        <td class="t_reference"><p class="js-tipes-show">"Okamoto S, Suzuki T, Kawaguchi M, et al. A comprehensive strategy for identifying long‐distance mobile peptides in xylem sap[J]. Plant Journal, 2015, 84(3):611-620."</p></td>
                                        <td class="t_tissue"><p>stem internode</p></td>
                                        <td class="t_environment"><p class="js-tipes-show"></p></td>
                                        <td class="t_cultivar"><p>Enrei</p></td>
                                        <td class="t_spots"><p>12346873</p></td>
                                        <td class="t_sampleRun" style="display: table-cell;"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRR029571</a></p></td>
                                        <td class="t_sraStudy"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRP002726</a></p></td>
                                        <td class="t_experiment"><p><a target="_blank" href="https://www.ncbi.nlm.nih.gov/bioproject/?term=PRJDB3474">DRX026629</a></p></td>
                                    </tr>
                                </tbody>
                            </table>
                            <form id="exportForm" action="" method="get">
                                <input id="search1" class="search-type" name="type" type="hidden">
                                <input id="search2" name="keywords" type="hidden">
                                <input id="search3" name="condition" type="hidden">
                                <input id="search4" name="choices" type="hidden">
                            </form>
                        </div>
                        <div class="checkbox-item-tab">
                            <div class="ga-ctrl-footer">
                                <div id="pagination"><div name="laypage1.3" class="laypage_main laypageskin_molv" id="laypage_0"><span class="laypage_curr" style="background-color:#5c8de5">1</span><a href="javascript:;" data-page="2">2</a><a href="javascript:;" data-page="3">3</a><span>…</span><a href="javascript:;" class="laypage_last" title="尾页" data-page="51">51</a><a href="javascript:;" class="laypage_next" data-page="2">&gt;</a></div></div>
                                <div id="per-page-count" class="per-page-count lay-per-page-count">
                                    <span>展示数量</span>
                                    <select name="" class="lay-per-page-count-select">
                                        <option value="10">10</option>
                                        <option value="20">20</option>
                                        <option value="30">30</option>
                                        <option value="50">50</option>
                                    </select>
                                    <span>条/页</span>
                                </div>
                                <!--<div id="total-page-count">总条数 <span>510</span></div>-->
                            </div>
                        </div>
                    </div>
                    <div class="tab-txt">
                        <div class="tab-txt-indels genes-tab">
                            <table>
                                <thead>
                                    <tr>
                                        <td class="param">INDEL ID</td>
                                        <td class="param">Consequence Type
                                            <img src="images/down.png">
                                            <input type="hidden" class="js-consequence-type">
                                            <ul class="consequence-type input-component ">
                                                <li>ALL</li>
                                                <li>SNPs that introduce stop codons</li>
                                                <li>Non-Synonymous SNP</li>
                                                <li>UTR_3_PRIME</li>
                                                <li>INTRON</li>
                                                <li>Synonymous</li>
                                                <li>UTR_5_PRIME</li>
                                                <li>INTERGENIC</li>
                                            </ul>
                                        </td>
                                        <td class="param">Chromosome</td>
                                        <td class="param">Start position</td>
                                        <td class="param">End position</td>
                                        <td class="param">REF</td>
                                        <td class="param">ALT</td>
                                        <td class="param">Number of REF</td>
                                        <td class="param">Number of ALT</td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>sd2222</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                    </tr>
                                    <tr>
                                        <td>sd2222</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                    </tr>
                                    <tr>
                                        <td>sd2222</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                    </tr>
                                    <tr>
                                        <td>sd2222</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                    </tr>
                                    <tr>
                                        <td>sd2222</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                    </tr>
                                    <tr>
                                        <td>sd2222</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                    </tr>
                                    <tr>
                                        <td>sd2222</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                    </tr>
                                    <tr>
                                        <td>sd2222</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                    </tr>
                                    <tr>
                                        <td>sd2222</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                        <td>数值</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="checkbox-item-tab">
                            <div class="ga-ctrl-footer">
                                <div id="pagination"><div name="laypage1.3" class="laypage_main laypageskin_molv" id="laypage_0"><span class="laypage_curr" style="background-color:#5c8de5">1</span><a href="javascript:;" data-page="2">2</a><a href="javascript:;" data-page="3">3</a><span>…</span><a href="javascript:;" class="laypage_last" title="尾页" data-page="51">51</a><a href="javascript:;" class="laypage_next" data-page="2">&gt;</a></div></div>
                                <div id="per-page-count" class="per-page-count lay-per-page-count">
                                    <span>展示数量</span>
                                    <select name="" class="lay-per-page-count-select">
                                        <option value="10">10</option>
                                        <option value="20">20</option>
                                        <option value="30">30</option>
                                        <option value="50">50</option>
                                    </select>
                                    <span>条/页</span>
                                </div>
                                <!--<div id="total-page-count">总条数 <span>510</span></div>-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->
<!--jquery-1.11.0-->
<script src="js/jquery-1.11.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!--tooltips-->
<script src="js/jquery.pure.tooltips.js"></script>
<script>
    $(function(){
        /*侧边栏收起*/
        $(".icon-right").click(function(){
            if($(".snp-content").hasClass("js-nav-ac")){
                $(".snp-content").removeClass("js-nav-ac")
            }else{
                $(".snp-content").addClass("js-nav-ac")
            }

        })
        /*样本清空*/
        $(".sample-empty").click(function(){
            $(".sample-text").empty();
        })
        $(".js-colse-text").click(function(){
            $(this).parent().remove();
        })
        $(".colse-sample span").click(function(){
            $(".custom-groups-content").hide();
            $(".cover").hide();
        })
        /*自定义群体*/
        $(".custom-groups-btn").click( function(){
            $(".custom-groups-content").show();
            $(".cover").show();
        })
        /*search slecet*/
        function select(){
            var  _val=$(".selcet select option:selected").attr("data-value")
            if(_val=="gene"){
                $(".selcet-gene-function").show();
                $(".selcet-set-length").show();
                $(".select-chorosome").hide();
            }else if(_val=="region"){
                $(".selcet-gene-function").hide();
                $(".selcet-set-length").hide();
                $(".select-chorosome").show();
            }
            console.log(_val)
        }
        select()
        $(".selcet select").change(function(){
            select();
        })
        /*收起*/
        function showmore(){
            var _dis=$(".category-protein").css("display");
            if(_dis=="block"){
                $(".category-more").hide();
                $(".retract p").html("更多选项(xxxxx等)<img src='images/more_unfold.png'>")
            }else {
                $(".category-more").show();
                $(".retract p").html("收起<img src='images/less.png'>")
            }
        }
        $(".retract p").click(function(){
            showmore()
        })
        /*show-hidden-tab*/
        function showtab(){
            var colArr = [];
            $("#table_header_setting span").each(function(){
                if(!$(this).parent().hasClass("checkbox-ac")){
                    $(".t_"+$(this).attr("data-value")).hide()
                    var colName = $(this).attr("data-value");
                    colArr.push(colName);
                }else{
                    $(".t_"+$(this).attr("data-value")).show()
                }
            })
        }
        /*切换*/
        $(".che-list label").click(function(){
            $(".js-btn-default").removeClass("btn-default-ac")
            $("#table_header_setting").removeClass("js-r-ac")
            if($(this).hasClass("checkbox-ac")){
                $(this).removeClass("checkbox-ac");
                $(".js-choose-all").css({"background-image":"url(images/contrast.png)"})
            }else{
                $(this).addClass("checkbox-ac");
            }
        })
        /*全选*/
        $(".js-choose-all").click(function(){
            $(".js-btn-default").removeClass("btn-default-ac");
            $("#table_header_setting").removeClass("js-r-ac");
            $(this).css({"background-image":"url(images/contrast-ac.png)"})
            $("#table_header_setting dd label").each(function(index){
                $(this).addClass("checkbox-ac");
            })
        })
        /*确认*/
        $(".btn-confirm").click(function(){
            showtab()
        })
        /*收起*/
        $(".btn-toggle").click(function(){
            $(".table-item").hide();
            $(".tab-txt .export-data").show();
        })
        /*表格设置*/
        $(".set-up").click(function(){
            $(".table-item").show();
            $(".tab-txt .export-data").hide();
        })
        /* 默认勾选*/
        $(".js-btn-default").click(function(){
            $("#table_header_setting label").addClass("checkbox-ac")
            $(".js-choose-all").css({"background-image":"url(images/contrast.png)"})
            if($(this).hasClass("btn-default-ac")){
                $(this).removeClass("btn-default-ac")
                $("#table_header_setting").removeClass("js-r-ac")
                console.log($("#experiment").parent().html())
            }else{
                $(this).addClass("btn-default-ac");
                $("#table_header_setting").addClass("js-r-ac")
                $("#sampleRun").parent().removeClass("checkbox-ac")
                $("#sraStudy").parent().removeClass("checkbox-ac")
                $("#experiment").parent().removeClass("checkbox-ac")
            }
        })
        /*SNP INDEL 切换*/
        $(".item li").click(function(){
            $(this).addClass("item-ac").siblings().removeClass("item-ac ");
            $(".tab > div").eq($(this).index()).show().siblings().hide();
        });
        /* selec populations*/
        $("#select-populations-checkbox dd label").click(function(){
            $(this).hasClass("cur")?$(this).removeClass("cur"): $(this).addClass("cur");
        })
        /*显示Samples（样本数）*/
        $(".sample-category div span").hover(
            function(){
                if($(this).text()!==""){
                    var _text=$(this).text()
                    var self = this;
                    var content = "";
                    content += "<div>"+_text+"</div>";
                    $.pt({
                        target: self,
                        position: 't',
                        align: 'l',
                        autoClose: false,
                        content: content
                    });
                    $(".pt").css("left", $(".pt").position().left);
                }else{
                    $(".pt").remove();
                }
            },
            function(){
                $(".pt").remove();
            }
        )
        /*鼠标悬浮在SNP ID上显示SNP信息*/
        $(".t_snpid").hover(
            function(){
                if($(this).text()!==""){
                    var _text=$(this).text()
                    var self = this;
                    var content = "";
                    content += "<div class='snpid-td'>"
                    content += "<p><span>SNP ID:</span>"+_text+"</p>",
                    content += "<p><span>Var:</span>"+"Var"+"</p>",
                    content += "<p><span>Gene:</span>"+"Gene"+"</p>",
                    content += "<p><span>Effect/Location:</span>"+"Effect/Location"+"</p>",
                    content += "</div>"
                    $.pt({
                        target: self,
                        position: 't',
                        align: 'l',
                        autoClose: false,
                        content: content
                    });
                    $(".pt").css("left", $(".pt").position().left);
                }else{
                    $(".pt").remove();
                }
            },
            function(){
                $(".pt").remove();
            }
        );
        $(".f-ma").change(function(){
            if($(this).val()=="Frequency of Major Allele"){
                $(".f-ma-in").val("Frequency of Major Allele in");
            }else if($(this).val()=="Frequency of Minor Allele"){
                $(".f-ma-in").val("Frequency of Minor Allele in");
            }
        })
    })
</script>
</body>
</html>