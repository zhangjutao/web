<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="focus" type="java.lang.String" required="true" description="" %>
<%@ attribute name="genId" type="java.lang.String" required="true" description="" %>
<c:set var="id" value="${java.util.UUID.randomUUID()}"/>

<ul id="nav_${id}" class="content-sidebar">
    <li id="nav_1"><a class="basic " href="${ctxroot}/iqgs/detail/basic?gen_id=${genId}"><i></i>基本信息</a></li>
    <li id="nav_2"><a class="sequence " href="${ctxroot}/iqgs/detail/sequence?gen_id=${genId}"><i></i>参考序列</a></li>
    <li id="nav_3"><a class="gene-structure" href="${ctxroot}/iqgs/detail/structure?gen_id=${genId}"><i></i>基因结构</a></li>
    <li id="nav_4"><a class="gene-annotation" href="${ctxroot}/iqgs/detail/anno?gen_id=${genId}"><i></i>基因注释</a></li>
    <li id="nav_5"><a class="gene-family" href="${ctxroot}/iqgs/detail/family?gen_id=${genId}"><i></i>基因家族</a></li>
    <li id="nav_6"><a class="homologous-gene" href="${ctxroot}/iqgs/detail/origin?gen_id=${genId}"><i></i>同源基因</a></li>
    <li id="nav_7"><a class="expression-data" href="${ctxroot}/iqgs/detail/expression?gen_id=${genId}"><i></i>表达数据</a></li>
    <li id="nav_8"><a class="qtl-data" href="${ctxroot}/iqgs/detail/qtl?gen_id=${genId}"><i></i>QTL数据</a></li>
    <li id="nav_9"><a class="variation-data" href="${ctxroot}/iqgs/detail/variation?gen_id=${genId}"><i></i>变异数据</a></li>
    <li id="nav_10"><a class="regulatory-network" href="${ctxroot}/iqgs/detail/regulatory?gen_id=${genId}"><i></i>调控网络</a></li>
    <li id="nav_11"><a class="coexpression-network" href="${ctxroot}/iqgs/detail/coexpression?gen_id=${genId}"><i></i>共表达网络</a></li>
</ul>

<script>

    $(function(){
            $("#nav_${focus}").addClass("sidebar-ac");
    })

</script>
