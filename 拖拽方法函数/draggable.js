function drag(element, parentnum, xdeviation, ydeviation){
    //element:节点 parentnum:回溯的父节点层数（当前为0，父节点为1，祖父节点为2...）
    // xdeviation：回溯节点的起始的left偏移量 ydeviation：回溯节点的起始的top偏移量
    var parentnum = parentnum||0;
    var xdeviation = xdeviation||0;
    var ydeviation = ydeviation||0;
    var isDrag = false;

    function down(){
        var ev = window.event;
        var that = this;
        for(var i = 0; i < parseInt(parentnum); i++){
            that = that.parentNode;
        }
        x = ev.clientX - that.offsetLeft ;
        y =  ev.clientY - that.offsetTop;
        this.style.cursor = "move";
        isDrag = true;
    }

    function move(){
        if (isDrag){
            var ev = window.event;
            var that =  this;
            for(var i = 0; i < parseInt(parentnum); i++){
                that = that.parentNode;
            }
            that.style.left = (ev.clientX -x + xdeviation) + "px";
            that.style.top = (ev.clientY - y + ydeviation) + "px";
            if (parseInt(that.style.left) < xdeviation){
                that.style.left = xdeviation + "px";
            }
            if (parseInt(that.style.top) < ydeviation){
                that.style.top = ydeviation + "px";
            }
            if (parseInt(that.style.left) > (document.documentElement.clientWidth - that.offsetWidth + xdeviation)){
                that.style.left = document.documentElement.clientWidth - that.offsetWidth + xdeviation + "px";
            }
            if (parseInt(that.style.top) > document.documentElement.clientHeight - that.offsetHeight + ydeviation) {
                that.style.top = document.documentElement.clientHeight - that.offsetHeight + ydeviation + "px";
            }
        }
    }
    function up(){
        isDrag = false;
        this.style.cursor = "pointer";
    }
    element.on("mousedown", down);
    element.on("mousemove", move);
    element.on("mouseup", up)
}

drag($('p'), 2, 450, 0);