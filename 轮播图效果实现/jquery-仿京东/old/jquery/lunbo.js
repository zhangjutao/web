var ArrImg=["img/1.jpg","img/2.jpg","img/3.jpg","img/4.jpg","img/5.jpg"];
var num=ArrImg.length;
for(var i=0;i<ArrImg.length;i++){  
	$("<li/>").css("background","url("+ArrImg[i]+")").appendTo($("#banner"));  
	$("<li/>").html(i+1).appendTo($("#round"));  
}
//设置ul宽度  
$("#banner").css("width",$("#banner li:eq(1)").width()*(ArrImg.length)+"px");  
//操作轮播图  
// 1.轮播最大的left值  
var Liw=$("#banner li").width();  
var bannerW=$("#banner").width();  
var index=0;
//图片变化初始化  
function move(index){  
	$("#banner").css("left",-Liw*index );
	$("#round li").css("background","#000");
	$("#round li").eq(index).css("background","red");
}
//图片变化  
var time1=setInterval(changeImg,1500);
function changeImg(){
	move(index)
	if(index==4){
		index=-1;  
	}
	index++;
}

//点击下标  
$("#round li").on('mouseenter',function(){
	index=$(this).index();
	clearInterval(time1);
	move(index)})
$("#round li").on('mouseout',function(){
	time1=setInterval(changeImg,1500);
	})  
//划过出现按钮  
$("#wrap").on('mouseenter',function(){
	$("#right a").add("#left a").css("display","block");
	}).on('mouseleave',function(){
		$("#right a").add("#left a").css("display","none");
		})
//点击按钮  
$("#left a").add($("#right a")).hover(function(){
	clearInterval(time1);
	},function (){
		time1=setInterval(changeImg,1500);
		});

$("#right a").on('click',function(){
	clearInterval(time1);
	index=((++index)%5);
	move(index)
	});

$("#left a").on('click',function(){
	clearInterval(time1);
	index=((5+--index)%5);
	move(index)
	});
console.log(0%5);