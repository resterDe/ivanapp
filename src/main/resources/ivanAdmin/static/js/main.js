$(document).ready(function (){
  // 截取sessionKey
  var url = location.search;
  var theRequest = new Object();  
  if (url.indexOf("?") != -1) {  
    var str = url.substr(1);  
    strs = str.split("&");  
    for(var i = 0; i < strs.length; i ++) {  
      theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);  
    }
  }
  var sessionKey = theRequest.sessionKey;
  if (typeof sessionKey == "undefined" || sessionKey == "exit") {
    layui.use(['layer'], function () {
      var layer = layui.layer;
      layer.msg("未登录，请先登录再使用");
      alert("未登录，请先登录再使用");
      window.location.href = "login.html";
    })
  }
  // 设置默认值
  $('#ifr').attr('src', './forward.html');
  $('#athis').attr('class', 'layui-this');
  $('#bthis').attr('class', '');
});
// 点击我的套餐 更改页面
function myPhoto () {
    $('#ifr').attr('src', './myPhoto.html');
    $('#athis').attr('class', ''); // 执行我的预约
    $('#bthis').attr('class', 'layui-this');  // 指向我的套餐
}
// 点击的我的预约 刷新执行main
function myOrder () {
    $('#ifr').attr('src', './forward.html');
    $('#bthis').attr('class', ''); // 执行我的预约
    $('#athis').attr('class', 'layui-this');  // 指向我的套餐
}
// 点击我的基本资料
function myInfo () {
    $('#ifr').attr('src', './merInfo.html');
    $('#athis').attr('class', ''); // 执行我的预约
    $('#bthis').attr('class', '');  // 指向我的套餐
}
// 写一个退出登录的
function exitLogin () {
  $.ajax({
    url: "http://127.0.0.1:8081/admin-api/exitLogin",
    type: "GET",
    success(res) {
      var temp;
      // 退出成功
      sessionKey = temp;
      console.log(sessionKey);
      window.location.href = "login.html";
    },
    error() {
      console.log("网络出错，请刷新重试");
    }
  });
}