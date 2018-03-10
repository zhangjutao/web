window.onload = function(){
	var list = document.getElementById("list");
	var prev = document.getElementById("prev");
	var next = document.getElementById("next");
	var container = document.getElementById("container");
	var buttons = document.getElementById("buttons").getElementsByTagName("li");
	var index = 0;
	
	//滑动效果
	function animate(offset){
		//获取的是style.left，是相对于左边获取距离，所以第一张图后style.left都为负值
		//且style.left获取的是字符串，需要用parseInt()取整值化为数字。
		var newLeft = parseInt(list.style.left) + offset;
		if (newLeft > 0){
			list.style.left = -2000 + 'px';
		}else if (newLeft < -2000){
			list.style.left = 0 + 'px';
		}else{
			list.style.left = newLeft + 'px';
		}
	}

	//点击往上的箭头
	prev.onclick = function(){
		index -= 1;
		if (index < 0){
			index = 4;
		}
		buttonsShow();
		animate(500);
	}
	
	//点击往下的箭头
	next.onclick = function(){
		index += 1;
		if (index > 4){
			index = 0;
		}
		buttonsShow();
		animate(-500);
	}
	

	//定时器
	var timer ;
	function play(){
		timer = setInterval(function(){next.onclick()},2000);
	}
	play();

	
	
	

	
	//暂停
	function stop(){
		clearInterval(timer);
	}
	
	//鼠标移入移出时调用定时器和暂停器
	container.onmouseover = stop;
	container.onmouseout = play;

	//设置小圆点的显示效果
	function buttonsShow(){
		for (var i = 0; i < buttons.length; i++){
			if (buttons[i].className == 'on'){
				buttons[i].className = '';
			}
			//buttons[index].style.backgroundColor = "balck";
		}
		
		//数组从0开始
		buttons[index].className = "on";
		//buttons[index].style.backgroundColor = "red";
	}
	
	
	//点击小圆点
	for (var i = 0; i < buttons.length ; i++){
		/* i始终输出5
		buttons[i].onclick = function(){
			//console.log(i);
			
			// 偏移量获取：这里获得鼠标移动到小圆点的位置，用this把index绑定到对象buttons[i]上，去谷歌this的用法
			// 由于这里的index是自定义属性，需要用到getAttribute()这个DOM2级方法，去获取自定义index的属性
			//console.log(this.getAttribute("index"));
			var clickIndex = parseInt(this.getAttribute('index'));
			var offset = 500 * (index - clickIndex);
			animate(offset);
			index = clickIndex;
			buttonsShow();
			}
		*/
		
		/*闭包*/
		(function(i){
			buttons[i].onclick = function(){
			console.log(i);
			
			// 偏移量获取：这里获得鼠标移动到小圆点的位置，用this把index绑定到对象buttons[i]上，去谷歌this的用法
			// 由于这里的index是自定义属性，需要用到getAttribute()这个DOM2级方法，去获取自定义index的属性
			//console.log(this.getAttribute("index"));
			var clickIndex = parseInt(this.getAttribute('index'));
			var offset = 500 * (index - clickIndex);
			animate(offset);
			index = clickIndex;
			buttonsShow();
			}
		})(i);
	}


}