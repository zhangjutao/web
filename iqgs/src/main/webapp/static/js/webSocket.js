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

var webSocketDna=null;
if('webSocket' in window){
    console.log("dna webSocket in wondow");
    webSocketDna=new WebSocket('ws://127.0.0.1:8080/dna/webSocket');
}else if('MozWebSocket' in window){
    console.log("dna MozWebSocket in wondow");
    webSocketDna=new MozWebSocket("ws://127.0.0.1:8080/dna/webSocket");
}else {
    alert('浏览器不支持webSocket！!')
}
webSocketDna.onopen=function (event) {
    console.log("与dna服务端建立webSocket连接")
}
webSocketDna.onclose=function (event) {
    console.log("关闭dna webSocket连接")
}
webSocketDna.onmessage =function (event) {
    //todo 将过期刷新的token存入sessionStorage中
    alert("dna webSocket接到token："+event.data);
    localStorage.setItem("access_token",event.data);
    console.log("收到dna服务端消息："+event.data);
}
webSocketDna.onerror=function () {
    console.log("dna webSocket通信发生错误");
}
webSocketDna.onbefoerunload=function () {
    webSocketDna.close();
}


var webSocketMrna=null;
if('webSocket' in window){
    console.log("mrna webSocket in wondow");
    webSocketMrna=new WebSocket('ws://127.0.0.1:8082/mrna/webSocket');
}else if('MozWebSocket' in window){
    console.log("mrna MozWebSocket in wondow");
    webSocketMrna=new MozWebSocket("ws://127.0.0.1:8082/mrna/webSocket");
}else {
    alert('浏览器不支持webSocket！!')
}
webSocketMrna.onopen=function (event) {
    console.log("与mrna服务端建立webSocket连接")
}
webSocketMrna.onclose=function (event) {
    console.log("关闭mrna webSocket连接")
}
webSocketMrna.onmessage =function (event) {
    //todo 将过期刷新的token存入sessionStorage中
    alert("mrna webSocket接到token："+event.data);
    localStorage.setItem("access_token",event.data);
    console.log("收到mrna服务端消息："+event.data);
}
webSocketMrna.onerror=function () {
    console.log("mrna webSocket通信发生错误");
}
webSocketMrna.onbefoerunload=function () {
    webSocketMrna.close();
}



var webSocketQtl=null;
if('webSocket' in window){
    console.log("qtl webSocket in wondow");
    webSocketQtl=new WebSocket('ws://127.0.0.1:8081/qtl/webSocket');
}else if('MozWebSocket' in window){
    console.log("qtl MozWebSocket in wondow");
    webSocketQtl=new MozWebSocket("ws://127.0.0.1:8081/qtl/webSocket");
}else {
    alert('浏览器不支持webSocket！!')
}
webSocketQtl.onopen=function (event) {
    console.log("与qtl服务端建立webSocket连接")
}
webSocketQtl.onclose=function (event) {
    console.log("关闭qtl webSocket连接")
}
webSocketQtl.onmessage =function (event) {
    //todo 将过期刷新的token存入sessionStorage中
    alert("qtl webSocket接到token："+event.data);
    localStorage.setItem("access_token",event.data);
    console.log("收到qtl服务端消息："+event.data);
}
webSocketQtl.onerror=function () {
    console.log("qtl webSocket通信发生错误");
}
webSocketQtl.onbefoerunload=function () {
    webSocketQtl.close();
}