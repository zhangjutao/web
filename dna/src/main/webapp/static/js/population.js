$(function (){
    var groupVal1="111利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal2="222利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal3="333利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal4="444利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal5="555利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal6="666利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal7="777利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal8="888利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal9="999利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";
    var groupVal10="100利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群";

    $("#groups li").click(function (e){
        var val = $(this).find("p").text();
        console.log(val);
        $("#popTips").show();
        $(".tipTopCnt").text(val);
        switch (val){
            case "Group 1":
                $(".groupCnt").text(groupVal1);
                break;
            case "Group 2":
                $(".groupCnt").text(groupVal2);
                break;
            case "Group 3":
                $(".groupCnt").text(groupVal3);
                break;
            case "Group 4":
                $(".groupCnt").text(groupVal4);
                break;
            case "Group 5":
                $(".groupCnt").text(groupVal5);
                break;
            case "Group 6":
                $(".groupCnt").text(groupVal6);
                break;
            case "Group 7":
                $(".groupCnt").text(groupVal7);
                break;
            case "Group 8":
                $(".groupCnt").text(groupVal8);
                break;
            case "Group 9":
                $(".groupCnt").text(groupVal9);
                break;
            case "Group 10":
                $(".groupCnt").text(groupVal10);
                break;

        }
    })
    $(".closeBtn img").click(function (){
        $("#popTips").hide();
    })
    // 弹框移动
    var posStart;
    var posEnd;
    var isMove = true;
    $(".tipTop").click(function (e){
       posStart = e.pageX;
        posEnd = e.pageY;
        // console.log(posStart);
        // console.log(posEnd);
    }).mousemove(function (e){
    })
    // 全选
    $("#SelectAllBox").click(function (){
        var status = $(this).prop("checked");
        console.log(status);
        var lists = $("#selectedDetails li");
        console.log(lists)
           if(status){
               for(var i=0;i<lists.length;i++){
                   // $(lists[i]).
               }
           }

    })
})
{   key:num
    value:89，


}