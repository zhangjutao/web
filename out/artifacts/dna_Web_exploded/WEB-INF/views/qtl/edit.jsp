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
                                <div class="widget-title  am-cf">qtl</div>


                            </div>
                            <div class="widget-body  am-fr">

                                <form class="am-form tpl-form-border-form tpl-form-border-br" modelAttribute="qtl" method="post" action="${ctxroot}/qtl/saveedit">
                                    <div class="am-form-group" style="display:none">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">id</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="id" name="id" value="${qtl.id}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>

                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">qtl_name</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="qtlName" name="qtlName" value="${qtl.qtlName}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>

                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">trait</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="trait" name="trait" value="${qtl.trait}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">type</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="type" name="type" value="${qtl.type}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>


                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">chrlg_id</span></label>
                                        <div class="am-u-sm-9">
                                            <c:set  var="chrlgId" value="${qtl.chrlgId}" scope="request"/>

                                            <select data-am-selected="{searchBox: 1}" style="display: none;" id="chrlgId" name="chrlgId">
                                                <c:forEach var="item" items="${list}">
                                                    <option value="${item.id}" <c:if test="${item.id==chrlgId}">selected</c:if>>
                                                            ${item.chr}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">marker1</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="marker1" name="marker1" value="${qtl.marker1}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">marker2</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="marker2" name="marker2" value="${qtl.marker2}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <%--<div class="am-form-group">--%>
                                        <%--<label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">associatedGenes_id</span></label>--%>
                                        <%--<div class="am-u-sm-9">--%>
                                            <%--<c:set  var="associatedGenesId" value="${qtl.associatedGenesId}" scope="request"/>--%>
                                            <%--<select data-am-selected="{searchBox: 1}" style="display: none;" id="associatedGenesId" name="associatedGenesId">--%>
                                                <%--<c:forEach var="item" items="${gens}">--%>
                                                    <%--<option value="${item.id}" <c:if test="${item.id==associatedGenesId}">selected</c:if>>--%>
                                                            <%--${item.id}--%>
                                                    <%--</option>--%>
                                                <%--</c:forEach>--%>
                                            <%--</select>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="am-form-group">--%>
                                        <%--<label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">version</span></label>--%>
                                        <%--<div class="am-u-sm-9">--%>
                                            <%--<input type="text" id="version" name="version" value="${qtl.version}" class="am-form-field tpl-form-no-bg" placeholder="" >--%>
                                            <%--<small></small>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">method</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="method" name="method" value="${qtl.method}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">genome_start</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="genomeStart" name="genomeStart" value="${qtl.genomeStart}" class="am-form-field tpl-form-no-bg" placeholder="输入数字" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">genome_end</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="genomeEnd" name="genomeEnd" value="${qtl.genomeEnd}" class="am-form-field tpl-form-no-bg" placeholder="输入数字" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">lod</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="lod" name="lod" value="${qtl.lod}" class="am-form-field tpl-form-no-bg" placeholder="输入数字" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">parent1</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="parent1" name="parent1" value="${qtl.parent1}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">parent2</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="parent2" name="parent2" value="${qtl.parent2}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">ref</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="ref" name="ref" value="${qtl.ref}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">author</span></label>
                                        <div class="am-u-sm-9">
                                            <textarea type="text" id="author" name="author" class="am-form-field tpl-form-no-bg" placeholder="" >${qtl.author}</textarea>
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