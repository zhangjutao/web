var webSocket=null;
if('webSocket' in window){
    console.log("webSocket in wondow");
    webSocket=new WebSocket('ws://127.0.0.1:8083/iqgs/webSocket');
}else if('MozWebSocket' in window){
    console.log("MozWebSocket in wondow");
    webSocket=new MozWebSocket("ws://127.0.0.1:8083/iqgs/webSocket");
}else {
    alert('浏览器不支持webSocket！!')
}
webSocket.onopen=function (event) {
    console.log("与服务端建立webSocket连接")
}
webSocket.onclose=function (event) {
    console.log("关闭webSocket连接")
}
webSocket.onmessage =function (event) {
    debugger
    //todo 将过期刷新的token存入sessionStorage中
    alert("webSocket接到token："+event.data);
    localStorage.setItem("access_token",event.data);
    console.log("收到服务端消息："+event.data);
}
webSocket.onerror=function () {
    console.log("webSocket通信发生错误");
}
webSocket.onbefoerunload=function () {
    webSocket.close();
}