// 预先执行信息
getMerInfo();
// 商家账号
var merPassword;
// LayUI JavaScript代码区域
layui.use('element', function () {
  var element = layui.element;
});
$(document).ready(function () {
  layui.use(['form'], function(){
    var form = layui.form;
    
    //监听修改信息
    form.on('submit(upForm)', function(data){
      // 执行添加函数
      upMerInfo();
      return false;
    });
    // 监听密码修改
    form.on('submit(formDemo)', function(data){
      // 执行修改密码
      ifPassword();
      return false;
    });
  });
});
// 获取商家信息
function getMerInfo () {
  $.ajax({
    url: "http://127.0.0.1:8081/admin-api/getMerInfo",
    type: "GET",
    success(res) {
      // 获取数据后传给模板
      onMerInfo(res);
      merAccount(res.data.merAccount);
      merPassword = res.data.merPassword;
    },
    error() {
      console.log("网络出错，请刷新重试");
    }
  })
}
// 渲染商家信息模板数据
function onMerInfo (res) {
  layui.use('laytpl', function () {
    var laytpl = layui.laytpl;
    var myDatas = {
      //数据
      "title": "编辑套餐规格",
      "merInfo": res.data,
    };
    var getTpl = merData.innerHTML, view = $("#merInfo");
    laytpl(getTpl).render(myDatas, function (result) {
      //清空元素内部的html代码
      view.empty();
      //重新添加
      view.append(result);
    });
  })
};
// 渲染商家信息模板数据
function merAccount (merAccount) {
  layui.use('laytpl', function () {
    var laytpl = layui.laytpl;
    var myDatas = {
      //数据
      "title": "编辑套餐规格",
      "merAccount": merAccount,
    };
    var getTpl = accountData.innerHTML, view = $("#merAccount");
    laytpl(getTpl).render(myDatas, function (result) {
      //清空元素内部的html代码
      view.empty();
      //重新添加
      view.append(result);
    });
  })
};
// 写一个修改信息提交的函数
function upMerInfo () {
  $.ajax({
    url: "http://127.0.0.1:8081/admin-api/upMerInfo",
    type: "POST",
    data: {
      "merAddress": $('input[name="merAddress"]').val(),
      "email": $('input[name="email"]').val(),
      "merPhone": $('input[name="merPhone"]').val(),
      "merInfo": $('input[name="merInfo"]').val()
    },
    success(res) {
      // 修改成功
      getMerInfo();
    },
    error() {
      console.log("网络出错，请刷新重试");
    }
  })
};
// 写一个修改密码的函数
function upMerLoginInfo () {
  $.ajax({
    url: "http://127.0.0.1:8081/admin-api/upLoginInfo",
    type: "POST",
    data: {
      "merAccount": $('#account').val(),
      "merPassword": $("#newPassword").val()
    },
    success(res) {
      // 修改成功
      $('#account').val('');
      $("#newPassword").val('');
      $("#conPassword").val('');
      $("#password").val('');
      // 密码修改后 回到login
      const temp = "exit";
      window.top.location.replace("login.html?sessionKey=" + temp);
    },
    error() {
      alert("网络出错");
      $("#newPassword").val('');
      $("#conPassword").val('');
      $("#password").val('');
      console.log("网络出错，请刷新重试");
    }
  })
};
//判断新密码与确认密码是否一致
function ifPassword() {
  layui.use(['layer'], function(){
    var layer = layui.layer;
    var password = $("#password").val();//原密码
    var newPassword = $("#newPassword").val();//新密码
    var conPassword = $("#conPassword").val();//确认新密码
    if (newPassword == conPassword) {
      if (password === merPassword) {
        // 执行函数 修改密码
        upMerLoginInfo();
      } else {
        layer.msg("密码不正确");
      }
      return true;
    } else {
      layer.msg("确认密码与新密码填写不一致");
      return false;
    }
  })
};