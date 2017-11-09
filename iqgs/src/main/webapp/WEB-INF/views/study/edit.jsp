<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/WEB-INF/views/include/dashboard/header.jsp" %>

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
                                <div class="widget-title  am-cf">mRNA_study</div>
                            </div>
                            <div class="widget-body  am-fr">

                                <form class="am-form tpl-form-border-form tpl-form-border-br" modelAttribute="study1" method="post" action="${ctxroot}/study/saveedit">
                                    <div class="am-form-group" style="display:none">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">id</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="id" name="id" value="${study.id}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">sraStudy</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="sraStudy" name="sraStudy" value="${study.sraStudy}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">study</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="study" name="study" value='${study.study}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">sampleName</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="sampleName" name="sampleName" value="${study.sampleName}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">isExpression</span></label>
                                        <div class="am-u-sm-9">
                                            <c:set  var="isExpression" value="${study.isExpression}" scope="request"/>
                                            <select data-am-selected="{searchBox: 1}" style="display: none;" id="isExpression" name="isExpression">
                                                <option value="1" <c:if test="${1==isExpression}">selected</c:if>>expression</option>
                                                <option value="0" <c:if test="${0==isExpression}">selected</c:if>>comparison</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">sampleRun</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="sampleRun" name="sampleRun" value="${study.sampleRun}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">tissue</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="tissue" name="tissue" value="${study.tissue}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">tissueForClassification</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="tissueForClassification" name="tissueForClassification" value="${study.tissueForClassification}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">preservation</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="preservation" name="preservation" value="${study.preservation}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">treat</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="treat" name="treat" value='${study.treat}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">stage</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="stage" name="stage" value='${study.stage}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">geneType</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="geneType" name="geneType" value='${study.geneType}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">phenoType</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="phenoType" name="phenoType" value='${study.phenoType}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">environment</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="environment" name="environment" value='${study.environment}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">geoLoc</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="geoLoc" name="geoLoc" value='${study.geoLoc}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">ecoType</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="ecoType" name="ecoType" value="${study.ecoType}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">collectionDate</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="collectionDate" name="collectionDate" value="${study.collectionDate}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">coordinates</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="coordinates" name="coordinates" value="${study.coordinates}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">ccultivar</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="ccultivar" name="ccultivar" value='${study.ccultivar}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">scientificName</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="scientificName" name="scientificName" value="${study.scientificName}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">pedigree</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="pedigree" name="pedigree" value="${study.pedigree}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">reference</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="reference" name="reference" value='${study.reference}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">institution</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="institution" name="institution" value='${study.institution}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">submissionTime</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="submissionTime" name="submissionTime" value="${study.submissionTime}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">instrument</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="instrument" name="instrument" value="${study.instrument}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">libraryStrategy</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="libraryStrategy" name="libraryStrategy" value="${study.libraryStrategy}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">librarySource</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="librarySource" name="librarySource" value="${study.librarySource}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">libraryLayout</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="libraryLayout" name="libraryLayout" value="${study.libraryLayout}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">insertSize</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="insertSize" name="insertSize" value="${study.insertSize}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">readLength</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="readLength" name="readLength" value="${study.readLength}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">spots</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="spots" name="spots" value="${study.spots}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">experiment</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="experiment" name="experiment" value="${study.experiment}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">links</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="links" name="links" value="${study.links}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <div class="am-u-sm-9 am-u-sm-push-3">
                                            <button type="submit" class="am-btn am-btn-primary tpl-btn-bg-color-success ">提交</button>
                                            <%@ include file="/WEB-INF/views/include/backbutton.jsp" %>
                                        </div>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
    <script src="${ctxStatic}/dashboard/js/amazeui.datatables.min.js"></script>
    <script src="${ctxStatic}/dashboard/js/dataTables.responsive.min.js"></script>
<script>
    $(function(){
        $('#example-r').DataTable();
    })
</script>

</body>

</html>