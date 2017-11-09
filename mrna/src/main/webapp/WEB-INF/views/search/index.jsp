<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/include/header-res.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header-bar.jsp" %>
    <section class="container">
        <div class="banner">
            <div class="plant-pic">
                <%@ include file="/WEB-INF/views/include/soybean.jsp" %>
            </div>
            <div class="search">
                <select>
                    <option>All</option>
                    <option>Trait</option>
                    <option>QTL Name</option>
                    <option>marker</option>
                    <option>parent</option>
                    <option>reference</option>
                </select>
                <label>
                    <input id="search-input" type="text" name="search" placeholder="输入您要查找的关键字">
                    <span class="clear-input" style="display: none"><img src="${ctxStatic}/images/clear-search.png"></span>
                    <button id="search-btn" ><img src="${ctxStatic}/images/search.png">搜索</button>
                </label>
            </div>
        </div>
        <div class="contant">
            <aside>
                <div class="item-header">
                    <div class="icon-left"><img src="${ctxStatic}/images/bookmarks.png">性状<i class="">TRAITS</i></div>
                </div>
                <%@ include file="/WEB-INF/views/include/nav.jsp" %>
            </aside>
            <article>
                <div class="item-header">
                    <div class="icon-left"><img src="${ctxStatic}/images/Linkage-group.png">遗传图谱</div>
                </div>
                <div class="Chromosome-item">
                    <p class="Chromosome-title">点击下图任意连锁群进入遗传图谱</p>
                    <div class="Chromosome-linkage-group">
                        <div class="linkage-group-item">
                            <label>Linkage Group</label>
                            <div class="item">
                                <table>
                                    <tr><td>D1a</td><td>D1b</td><td>N</td><td>C1</td><td>A1</td><td>C2</td><td>M</td><td>A2</td><td>K</td><td>O</td></tr>
                                    <tr>
                                        <td><a href="${ctxroot}/gene?chr=${D1a}&version=${version}&markerlg=D1a(1)"><img src="${ctxStatic}/images/01.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${D1b}&version=${version}&markerlg=D1b(2)"><img style="margin-top: -15px" src="${ctxStatic}/images/02.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${N}&version=${version}&markerlg=N(3)"><img src="${ctxStatic}/images/03.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${C1}&version=${version}&markerlg=C1(4)"><img src="${ctxStatic}/images/04.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${A1}&version=${version}&markerlg=A1(5)"><img src="${ctxStatic}/images/05.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${C2}&version=${version}&markerlg=C2(6)"><img src="${ctxStatic}/images/06.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${M}&version=${version}&markerlg=M(7)"><img src="${ctxStatic}/images/07.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${A2}&version=${version}&markerlg=A2(8)"><img src="${ctxStatic}/images/08.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${K}&version=${version}&markerlg=K(9)"><img src="${ctxStatic}/images/09.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${O}&version=${version}&markerlg=O(10)"><img src="${ctxStatic}/images/10.png"></a></td>
                                    </tr>
                                    <tr><td>${D1a}</td><td>${D1b}</td><td>${N}</td><td>${C1}</td><td>${A1}</td><td>${C2}</td><td>${M}</td><td>${A2}</td><td>${K}</td><td>${O}</td></tr>
                                </table>
                            </div>
                        </div>
                        <div class="linkage-group-item">
                            <label>Linkage Group</label>
                            <div class="item">
                                <table>
                                    <tr><td>B1</td><td>H</td><td>F</td><td>B2</td><td>E</td><td>J</td><td>D2</td><td>G</td><td>L</td><td>I</td></tr>
                                    <tr>
                                        <td><a href="${ctxroot}/gene?chr=${B1}&version=${version}&markerlg=B1(11)"><img src="${ctxStatic}/images/11.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${H}&version=${version}&markerlg=H(12)"><img style="margin-top: -15px" src="${ctxStatic}/images/12.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${F}&version=${version}&markerlg=F(13)"><img src="${ctxStatic}/images/13.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${B2}&version=${version}&markerlg=B2(14)"><img src="${ctxStatic}/images/14.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${E}&version=${version}&markerlg=E(15)"><img src="${ctxStatic}/images/15.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${J}&version=${version}&markerlg=J(16)"><img src="${ctxStatic}/images/16.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${D2}&version=${version}&markerlg=D2(17)"><img src="${ctxStatic}/images/17.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${G}&version=${version}&markerlg=G(18)"><img src="${ctxStatic}/images/18.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${L}&version=${version}&markerlg=L(19)"><img src="${ctxStatic}/images/19.png"></a></td>
                                        <td><a href="${ctxroot}/gene?chr=${I}&version=${version}&markerlg=I(20)"><img src="${ctxStatic}/images/20.png"></a></td>
                                    </tr>
                                    <tr><td>${B1}</td><td>${H}</td><td>${F}</td><td>${B2}</td><td>${E}</td><td>${J}</td><td>${D2}</td><td>${G}</td><td>${L}</td><td>${I}</td></tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </article>
        </div>

    </section>
    <div class="container explain">
        <div class="explain-title">
            <img src="${ctxStatic}/images/explain.png">数据库概况
        </div>
        <div class="explain-text">
            ${qtlDetail}
            <%--本数据库提供了大豆相关的4100条QTL数据，涉及220个相关数量性状，每条QTL数据包含该QTL在染色体上的遗传距离信息和数据源文献等信息。所有数据均来源于相关文献及soybase等数据库网站。同时创建了一套新的物理图谱与遗传图谱的可视化工具，利用该工具快速定位QTL区间，并给出区间内的基因列表。--%>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>
    $(".clear-input").click(function(){
        $("#search-input").val("");
        return false
    })
    $("#search-btn").click(function(){
        var s_option=$(".search select").val();
        var i_input=$.trim($("#search-input").val());
        window.location.href = "${ctxroot}/search/list?version=Glycine_max.V1.0.23.dna.genome&type="+s_option+"&keywords="+i_input;
    })
    $("#search-input").bind({
        focus:function(){
            $(".clear-input").css({"display":"inline-block"});
        },
        blur:function(){
            if($(this).val()==""){
                $(".clear-input").css({"display":"none"});
            }else{
                $(".clear-input").css({"display":"inline-block"});
            }
        }
    });
    $("#search-input").on("focus", function() {
       $(this).addClass("isFocus");
    });
    $("#search-input").on("blur", function() {
        $(this).removeClass("isFocus");
    });
    $(document).keyup(function(event){
        var _searchDom = $("#search-input");
        var e=e||event
        var keycode = e.which;
        if(keycode==13){
            if(_searchDom.hasClass("isFocus")) {
                $("#search-btn").trigger("click");
            }
        }
    });
//    $("#limb").mouseover(function(){
//        $(".limb-mouseover").show();
//    })
</script>
</body>
</html>