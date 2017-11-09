<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!-- 菜单 -->
<ul class="sidebar-nav am-nav">
    <li class="sidebar-nav-heading">QTL<span class="sidebar-nav-heading-info"> 管理</span></li>
    <li id="traitcategory" class="sidebar-nav-link">
        <a href="${ctxroot}/traitcategory/list?cname=traitcategory">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> qtl类别
        </a>
    </li>
    <li id="traitlist" class="sidebar-nav-link">
        <a href="${ctxroot}/traitlist/list?cname=traitlist">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> qtl类别对应的trait
        </a>
    </li>
    <li id="soybean" class="sidebar-nav-link">
        <a href="${ctxroot}/soybean/list?cname=soybean">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> 大豆图片信息
        </a>
    </li>
    <li id="chrlg" class="sidebar-nav-link">
        <a href="${ctxroot}/chrlg/list?cname=chrlg">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> chrlg
        </a>
    </li>
    <li id="qtl" class="sidebar-nav-link">
        <a href="${ctxroot}/qtl/list?cname=qtl">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> qtl
        </a>
    </li>
    <li id="qtlref" class="sidebar-nav-link">
        <a href="${ctxroot}/qtlref/list?cname=qtlref">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> qtlref
        </a>
    </li>

    <li id="advanceinfo" class="sidebar-nav-link">
        <a href="${ctxroot}/advanceinfo/list?cname=advanceinfo">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> advanceinfo
        </a>
    </li>
    <li id="associatedgenes" class="sidebar-nav-link">
        <a href="${ctxroot}/associatedgenes/list?cname=associatedgenes">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> associatedgenes
        </a>
    </li>
    <li id="marker" class="sidebar-nav-link">
        <a href="${ctxroot}/marker/list?cname=marker">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> marker
        </a>
    </li>
    <li id="markerposition" class="sidebar-nav-link">
        <a href="${ctxroot}/markerposition/list?cname=markerposition">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> marker_position
        </a>
    </li>

    <li id="indexexplains" class="sidebar-nav-link">
        <a href="${ctxroot}/indexexplains/list?cname=indexexplains">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> 首页数据库概况设置
        </a>
    </li>
    <li class="sidebar-nav-heading">mRNA<span class="sidebar-nav-heading-info"> 管理</span></li>
    <li id="mrnaclassify" class="sidebar-nav-link">
        <a href="${ctxroot}/mrnaclassify/list?cname=mrnaclassify">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> mRNA分类管理
        </a>
    </li>
    <li id="mrnagens" class="sidebar-nav-link">
        <a href="${ctxroot}/mrnagens/list?cname=mrnagens">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> mRNA_genes
        </a>
    </li>
    <li id="study" class="sidebar-nav-link">
        <a href="${ctxroot}/study/list?cname=study">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> mRNA_study
        </a>
    </li>
    <li id="firstgens" class="sidebar-nav-link">
        <a href="${ctxroot}/firstgens/list?cname=firstgens">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> mRNA首页基因设置
        </a>
    </li>
    <li id="fpkm" class="sidebar-nav-link">
        <a href="${ctxroot}/fpkm/list?cname=fpkm">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> 大表数据fpkm导入
        </a>
    </li>
    <li id="expression" class="sidebar-nav-link">
        <a href="${ctxroot}/expression/list?cname=expression">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> 表达基因导入
        </a>
    </li>
    <li id="comparision" class="sidebar-nav-link">
        <a href="${ctxroot}/comparision/list?cname=comparision">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> 差异基因导入
        </a>
    </li>
    <li class="sidebar-nav-heading">DNA<span class="sidebar-nav-heading-info"> 管理</span></li>
    <li id="dnagroups" class="sidebar-nav-link">
        <a href="${ctxroot}/dnagroups/list?cname=dnagroups">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> DNA_默认群体设置
        </a>
    </li>
    <li id="dnagens" class="sidebar-nav-link">
        <a href="${ctxroot}/dnagens/list?cname=dnagens">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> DNA_genes
        </a>
    </li>
    <li id="dnarun" class="sidebar-nav-link">
        <a href="${ctxroot}/dnarun/list?cname=dnarun">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> DNA_samples
        </a>
    </li>
    <li class="sidebar-nav-heading">系统<span class="sidebar-nav-heading-info"> 管理</span></li>
    <li id="usercheck" class="sidebar-nav-link">
        <a href="${ctxroot}/d/user/check">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> 注册用户审核
        </a>
    </li>
    <li id="userpwdreset" class="sidebar-nav-link">
        <a href="${ctxroot}/d/user/pwd">
            <i class="am-icon-calendar sidebar-nav-link-logo"></i> 用户密码重置
        </a>
    </li>
</ul>
<script>

    $(function () {
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return unescape(r[2]);
            return null;
        }

        console.log((GetQueryString("cname")));
        var t = GetQueryString("cname");
        switch (t) {
            case "traitcategory":
                $("#traitcategory").addClass("am-active");
                break
            case "traitlist":
                $("#traitlist").addClass("am-active");
                break
            case "soybean":
                $("#soybean").addClass("am-active");
                break
            case "chrlg":
                $("#chrlg").addClass("am-active");
                break
            case "qtl":
                $("#qtl").addClass("am-active");
                break
            case "qtlref":
                $("#qtlref").addClass("am-active");
                break
            case "advanceinfo":
                $("#advanceinfo").addClass("am-active");
                break
            case "associatedgenes":
                $("#associatedgenes").addClass("am-active");
                break
            case "marker":
                $("#marker").addClass("am-active");
                break
            case "markerposition":
                $("#markerposition").addClass("am-active");
                break
            case "mrnagens":
                $("#mrnagens").addClass("am-active");
                break
            case "study":
                $("#study").addClass("am-active");
                break
            case "firstgens":
                $("#firstgens").addClass("am-active");
                break
            case "fpkm":
                $("#fpkm").addClass("am-active");
                break
            case "expression":
                $("#expression").addClass("am-active");
                break
            case "comparision":
                $("#comparision").addClass("am-active");
                break
            case "mrnaclassify":
                $("#mrnaclassify").addClass("am-active");
                break;
            case "indexexplains":
                $("#indexexplains").addClass("am-active");
                break;
            case "dnagroups":
                $("#dnagroups").addClass("am-active");
                break;
            case "dnagens":
                $("#dnagens").addClass("am-active");
                break;
            case "dnarun":
                $("#dnarun").addClass("am-active");
                break;
        }
    })


</script>