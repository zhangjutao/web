<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>QTLName详情</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/index.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <style>
        .js-pop-head,.tab-detail-thead{cursor:move}
    </style>
</head>
<body>
    <qtl:qtl-header />
    <section class="container">
        <div class="description box-shadow">
            <p>qtl #${name}<span>版本号${version}</span></p>
        </div>
        <div class="information">
            <div class="information-problems box-shadow">
                <div class="basic-information ">
                    <p >BASIC INFORMATION</p>
                    <table>
                        <tr>
                            <td>QTL name</td>
                            <td>${basicInfo.qtlname}</td>
                        </tr>
                        <tr>
                            <td>type</td>
                            <td>${basicInfo.type}</td>
                        </tr>
                        <tr>
                            <td>trait</td>
                            <td>${basicInfo.trait}</td>
                        </tr>
                        <tr>
                            <td>marker1</td>
                            <td><a class="js-pop-marker1" href="javascript:;" data-src="${ctxroot}/query/marker?markerName=${basicInfo.marker1}">${basicInfo.marker1}</a></td>
                        </tr>
                        <tr>
                            <td>marker2</td>
                            <td><a class="js-pop-marker2" href="javascript:;" data-src="${ctxroot}/query/marker?markerName=${basicInfo.marker2}">${basicInfo.marker2}</a></td>
                        </tr>
                        <tr>
                            <td>genes</td>
                            <td><a class="js-pop-genes" href="javascript:void(0)">${basicInfo.genes.size}</a></td>
                        </tr>
                        <tr>
                            <td>LG</td>
                            <td><a href="${ctxroot}/gene?chr=${basicInfo.chr}&version=${version}&markerlg=${basicInfo.markerlg}&qtl=${basicInfo.qtlname}">${basicInfo.lg}</a></td>
                        </tr>
                        <tr>
                            <td>chr</td>
                            <td><a  href="${ctxroot}/gene?chr=${basicInfo.chr}&version=${version}&markerlg=${basicInfo.markerlg}&qtl=${basicInfo.qtlname}">${basicInfo.chr}</a></td>
                        </tr>
                        <tr>
                            <td>method</td>
                            <td class="method-txt"><span>${basicInfo.method}</span></td>
                        </tr>
                        <tr>
                            <td>LOD</td>
                            <td>${basicInfo.lod}</td>
                        </tr>
                        <tr>
                            <td>parent1</td>
                            <td>${basicInfo.parent1}</td>
                        </tr>
                        <tr>
                            <td>parent2</td>
                            <td>${basicInfo.parent2}</td>
                        </tr>
                        <tr>
                            <td>QTL span</td>
                            <td>
                                <span>${basicInfo.geneStart}-${basicInfo.geneEnd}(cM)</span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="link-information box-shadow">
                <div class="advanced-information">
                    <div class="information-title">
                        <p>ADVANCED INFORMATION</p>
                    </div>
                    <div class="information-detail">
                        <div class="Ontology">
                            <p>Controlled vocabulary terms associated with the QTL（PTO TO）:</p>
                            <p><span>Plant Trait Ontology</span><a target="_blank" href="${advanceInfo.plantTraitOntology.webUrl}">${advanceInfo.plantTraitOntology.TO}</a></p>
                            <p><span>Plant Ontology</span><a target="_blank"href="${advanceInfo.plantOntology.webUrl}">${advanceInfo.plantOntology.PO}</a></p>
                        </div>
                        <div class="related">
                            <p>Related QTL's:</p>
                            <c:forEach items="${advanceInfo.otherRelatedQtls.qtls}" var="item">
                                <span><a href="${ctxroot}/search/aboutus?name=${item}&version=${version}">${item}</a></span>
                            </c:forEach>
                        </div>
                        <div class="other">
                            <p>Other names for the QTL:</p>
                            <p>${advanceInfo.otherNamesQtl.names}</p>
                        </div>
                    </div>
                    <div class="information-title">
                        <p>REFERENCE INFORMATION</p>
                    </div>
                    <div class="reference-information-tab">
                        <table>
                            <tr>
                                <td>Title</td>
                                <td><p>${referenceInfo.title}</p></td>
                            </tr>
                            <tr>
                                <td>Authors</td>
                                <td><p>${referenceInfo.authors}</p></td>
                            </tr>

                            <tr>
                                <td>Source</td>
                                <td><p>${referenceInfo.source}</p></td>
                            </tr>
                            <tr class="js-links">
                                <td>Links</td>
                                <td>
                                    <p><a target="_blank" href="${referenceInfo.pubmed}">PubMed</a><a  class="abstract"  href="javascript" data-src="${referenceInfo.abs}">Abstract</a></p>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="reference-information"></div>
            </div>
        </div>
    </section>
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <div id="mid"></div>
    <div class="tab-detail">
        <div class="tab-detail-thead">
            <p class="tabDetailTheadTitle">qtl #${name}<span>(版本号)${version}</span>
            <a href="javascript:void(0)">X</a></p>
        </div>
        <div class="tab-detail-tbody">
            <div class="tab-category">Genes</div>
            <div class="tab-category-list">
                <c:forEach items="${basicInfo.genes.data}" var="item">
                    <span><a class="js-gene-info" data-gene-name="${item}" href="javascript:void(0);">${item}</a></span>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="links-pop">
        <div class="tab-detail-thead">
            <p>Links</span>
                <a href="javascript:void(0)">X</a>
            </p>
        </div>
        <div class="links-text" id="links-text">
            <table>
                <tbody>

                </tbody>
            </table>
            <%--<p>${referenceInfo.abs}</p>--%>
        </div>
    </div>
    <div class="js-pop">
        <div class="js-pop-head">
            <p><span>Marker</span>
                <a href="javascript:void(0)">X</a>
            </p>
        </div>
        <div class="js-pop-body">
            <table>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>
    <div class="genesInfo" style="display: none">
        <div class="genesInfo-head">
            <p>基因<span class="js-gene-head-name"></span>信息</p>
            <a href="#">x</a>
        </div>
        <iframe id="geneIframe" height="400" frameborder="no" border="0" marginwidth="0" marginheight="0" src=""></iframe>
    </div>
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>


    <script src="${ctxStatic}/js/layout.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        /*拖动弹框*/
////        $(".js-pop").draggable({ containment: ".js-pop-head"});
//        $(".js-pop").draggable({ containment: "parent"});
//        $( ".links-pop" ).draggable({ containment: "parent"});
//        $( ".tab-detail" ).draggable({ containment: "parent"});
        function getUrlParam(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
        }
        var index1,index2;
        /*maker1  --maker2*/
        function pop(name,title){
            $(name).click(function(e){
                $(".js-pop-head span").html(title)
                e.preventDefault();
//                $("#mid").show();
                $(".js-pop").show();
//                $(".js-pop-body tbody").html();
                var url=$(this).attr("data-src");
                //定义拖拽
                index2 = layer.open({
                    title:"",
                    type: 1,
                    content:$(".js-pop"),
                    area: '800px',
                    shadeClose:true,
                    scrollbar:false,
                    move: '.js-pop-head',
                    tips: 2,
                    closeBtn: 0,
                    offset: ['250px', '350px']

                });
                $.ajax({
                    url:url,
                    type:"get",
                    dataType:"json",
                    success:function(data){
                        console.log(data);
                        var pop="";
                        pop+="<tr>"
                        pop+="  <td>Name</td>"
                        pop+="  <td>"+data.name+"</td>"
                        pop+="</tr>"
                        pop+="<tr>"
                        pop+="  <td>Type</td>"
                        pop+="  <td>"+data.type+"</td>"
                        pop+="</tr>"
                        pop+="<tr>"
                        pop+="  <td>LG(Chr)</td>"
                        pop+="  <td>"+data.lg+"</td>"
                        pop+="</tr>"
                        pop+="<tr>"
                        pop+="  <td>Position</td>"
                        pop+="  <td>"+data.position+"</td>"
                        pop+="</tr>"
                        pop+="<tr>"
                        pop+="  <td>Amplification Info</td>"
                        pop+="  <td>"+data.amplificationInfo+"</td>"
                        pop+="</tr>"
                        pop+="<tr>"
                        pop+="  <td>Providers</td>"
                        pop+="  <td>"+data.provider+"</td>"
                        pop+="</tr>"
                        pop+="<tr>"
                        pop+="  <td>References</td>"
                        pop+="  <td>"+data.refference+"</td>"
                        pop+="</tr>"
                        $(".js-pop-body tbody").html(pop)
                    }
                })

            })


        }
        pop(".js-pop-marker1","marker1")
        pop(".js-pop-marker2","marker2")

        $(".js-pop-genes").click(function(e){
            e.preventDefault();
//            $("#mid").show();
            $(".tab-detail").show();
           index1 = layer.open({
                title:"",
                type: 1,
                content:$(".tab-detail"),
                area: '800px',
                shadeClose:true,
                scrollbar:false,
                move: '.tabDetailTheadTitle',
                tips: 2,
                closeBtn: 0,
                offset: ['250px', '350px']

            });
        })
        $(".tab-category-list span").click(function(){
            $(".genesInfo").show()
        })
        $(".genesInfo a").click(function(e){
            e.preventDefault();
            $(".genesInfo").hide();
//            layer.close(index1);
//            layer.close(index2);


        })
        $(".js-pop-head a").click(function (){
            layer.close(index2);
        })
        $(".tabDetailTheadTitle a").click(function (){
            layer.close(index1);
        })


        $("body").on("click", ".js-gene-info", function(e) {
            var version = getUrlParam("version");
            var geneName = $(this).attr("data-gene-name");
            $(".js-gene-head-name").html(geneName);
            $("#geneIframe").attr("src", "${ctxroot}/geneInfo?geneName="+ geneName + "&version=" + version);
            e.preventDefault();
//        console.log($(this).html())
            $(".genesInfo").show();

        });
    </script>
</body>
</html>