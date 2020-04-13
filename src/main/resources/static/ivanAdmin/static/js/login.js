var url = location.search;
var theRequest = new Object();  
if (url.indexOf("?") != -1) {  
  var str = url.substr(1);  
  strs = str.split("&");  
  for(var i = 0; i < strs.length; i ++) {  
    theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);  
  }
}
// 定义sessionKey
var sessionKey = theRequest.sessionKey;
//预定义
$(document).ready(function () {
  //Demo
  layui.use('form', function () {
    var form = layui.form;
    //监听提交
    form.on('submit(formDemo)', function (data) {
      //文字弹窗，获取文本弹窗
      login();
      return false;
    });
    // 重置
    $("#reset").click(function () {
      $("#serialNumber").val("");
      $("#newPassword").val("");
    });
  });
});
function login () {
  $.ajax({
    url: "http://127.0.0.1:8081/admin-api/merLogin",
    type: "POST",
    data: {
      "merAccount": $("#serialNumber").val(),
      "merPassword": $("#newPassword").val()
    },
    success(res) {
      console.log("登录成功");
      console.log(res);
      //进入主页面
      if (res.status === 0) {
        sessionKey = res.data;
        // 登录成功
        window.location.href = "main.html?sessionKey=" + sessionKey;
      } else {
        $("#serialNumber").val("");
        $("#newPassword").val("");
      }
    },
    error() {
      console.log("网络出错");
      alert("网络出错");
      $("#serialNumber").val("");
      $("#newPassword").val("");
    }
  })
};