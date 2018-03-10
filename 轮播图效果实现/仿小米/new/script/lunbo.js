var oBody = document.getElementsByTagName("body")[0];
var aBanner = document.getElementById("banner").getElementsByTagName("li");
var aPoint = document.getElementById("tab").getElementsByTagName("li");
var oNext = document.getElementsByClassName("next")[0];
var Oprev = document.getElementsByClassName("prev")[0];
var Oon = document.getElementsByClassName("on")[0];
//初始化让第一张图片显示，和第一个原点显示
aBanner[0].style.opacity = "1";
aPoint[0].className = "on";

var num = 0;
for(var i = 0;i < aPoint.length;i++){
	aPoint[i].index = i;
	//点击小圆点图片相对应的进行切换
	aPoint[i].onclick = function(){
		for(var j = 0 ;j < aPoint.length; j++){
			num = this.index;
			aPoint[j].className = "";
			aBanner[j].style.opacity = "0";
			}
		aPoint[num].className = "on";
		aBanner[num].style.opacity = "1";
	};
	
	//按下图片切换到后一张
	oNext.onclick = function(){
		for(var j = 0 ;j < aPoint.length; j++){
			if(aPoint[j].className == "on"){
				aPoint[j].className = "";
				aBanner[j].style.opacity = "0";
				j++;
				num++;
				if(j > 4){
					j = 0;
				}
				aPoint[j].className = "on";
				aBanner[j].style.opacity = "1";
			}
		}
	}

//按下图片切换到前一张
Oprev.onclick = function(){
	for(var j = 0 ;j < aPoint.length; j++){
      if(aPoint[j].className == "on"){
          aPoint[j].className = "";
          aBanner[j].style.opacity = "0";
          j--;
          num--;
          if(j < 0){
          j = 4;
      }
          aPoint[j].className = "on";
  aBanner[j].style.opacity = "1";

  }
}
}  
}

function Time(){/*设置定时器运行的函数*/
num++;
if(num < 5){
    for(var j = 0 ;j < aPoint.length; j++){
    aPoint[j].className = "";
    aBanner[j].style.opacity = "0";
}
aPoint[num].className = "on";
aBanner[num].style.opacity = "1";
}else {
    num = -1;
}         
}
clearInterval(timer);
var timer = setInterval("Time()",2000);/*调用定时器*/

oBody.onmouseover = function(){/*鼠标引入，清除定时器，轮播图停止*/
    clearInterval(timer);
};
oBody.onmouseout = function(){/*鼠标移出，重新调用定时器，轮播图开始*/
    clearInterval(timer);
     timer = setInterval("Time()",2000);
};