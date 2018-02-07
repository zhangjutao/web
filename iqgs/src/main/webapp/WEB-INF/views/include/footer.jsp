<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<footer>
    <div class="container">

        <dl class="">
            <dt>产品链接</dt>
            <dd><a target="_blank" href="http://report.gooalgene.com">可交互分析报告</a></dd>
            <dd><a target="_blank" href="http://dms.gooalgene.com:81">数据管理系统</a></dd>
            <dd><a target="_blank" href="http://www.gooalgene.com/pages/dataBase.html">基因数据库</a></dd>
        </dl>
        <dl>
            <dt>热门资源</dt>
            <dd><a target="_blank" href="http://www.ncbi.nlm.nih.gov">NCBI</a></dd>
            <dd><a target="_blank" href="http://phytozome.jgi.doe.gov">Phytozome</a></dd>
            <dd><a target="_blank" href="http://asia.ensembl.org">Ensembl</a></dd>
            <dd><a target="_blank" href="http://www.soybase.org/">Soybase</a></dd>
            <dd><a target="_blank" href="http://www.1000genomes.org">1000 Genomes</a></dd>
        </dl>
        <dl>
            <dt>关于我们</dt>
            <dd><a target="_blank" href="http://www.gooalgene.com/pages/company.html">关于古奥基因</a></dd>
            <dd><a target="_blank" href="http://www.gooalgene.com/pages/joinus.html">招贤纳士</a></dd>
            <dd style="padding-top: 15px; font-size:26px;">400-863-0058</dd>
        </dl>
        <dl>
            <dt>关注古奥基因</dt>
            <dd class="qr-code">
                <img src="${ctxStatic}/images/qr-code.jpg">
            </dd>
        </dl>

        <p>&copy 版权所有 古奥基因 GOOALGENE 2016 鄂ICP备16015451号-1</p>
    </div>
</footer>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?51d5509a2adcfd85d877cd1edafc0471";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>