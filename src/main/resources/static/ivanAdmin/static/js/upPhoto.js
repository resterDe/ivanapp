layui.use(['element', 'layer'], function(){
  var element = layui.element;
  var layer = layui.layer;
  
  //监听折叠
  element.on('collapse(test)', function(data){
    layer.msg('展开状态：'+ data.show);
  });
});
// 获取传过来的pid
var url = location.search;
var theRequest = new Object();  
if (url.indexOf("?") != -1) {  
  var str = url.substr(1);  
  strs = str.split("&");  
  for(var i = 0; i < strs.length; i ++) {  
    theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);  
  }
}
const pId = theRequest.pId;
const speUrl = "http://127.0.0.1:8081/message-api/getSpeInfo";
const allUrl = "http://127.0.0.1:8081/message-api/getPhotoAllInfo";
upSpeInfo(speUrl,pId);
getPhotoAllInfo(allUrl,pId);
// 查询规格信息 并返回
function upSpeInfo (speUrl,pId) {
  $.ajax({
    url: speUrl,
    type: "GET",
    data: {
        "pId": pId
    },
    success(res) {
      console.log(res);
      onUpData(res);
    },
    error() {
      console.log("网络出错");
    }
  });
};
// 编辑数据模板
function onUpData(res) {
  layui.use('laytpl', function () {
      var laytpl = layui.laytpl;
      var myDatas = {
          //数据
          "title": "编辑套餐规格",
          "speInfoList": res.data,
      };
      var getTpl = speData.innerHTML, view = $("#spePhoto");
      laytpl(getTpl).render(myDatas, function (result) {
          //清空元素内部的html代码
          view.empty();
          //重新添加
          view.append(result);
      });
  })
};
// 新增一个规格 add节点
function addSpeDom () {
  console.log("点击新增")
  layui.use(['form','layer','laydate','upload'], function(){
    var newInput = '<tr id="addTr"><td>' +
      '<input type="text" id="speName" name="speName" placeholder="请输入规格名称" class="layui-input">' +
      '</td><td>' +
      '<input type="text" id="price" name="price" placeholder="请输入价格" class="layui-input">'+
      '</td><td>' +
      '<button class="layui-btn layui-btn-xs" onclick="saveInfo()">提交</button>'+
      '<button class="layui-btn layui-btn-xs" onclick="delDom()">删除</button>'+
      '</td></tr>';
    $('#tbody').append(newInput);
  });
};
// 删除节点
function delDom () {
  console.log('删除节点')
  $('#addTr').remove();
};
// 提交
function saveInfo () {
  console.log('提交')
  $.ajax({
    url: "http://127.0.0.1:8081/message-api/addSpe",
    type: "POST",
    data: {
      "pId": pId,
      "speName": $("#speName").val(),
      "price": $("#price").val()
    },
    success(res) {
      console.log(res);
      // 提交成功后
      upSpeInfo(speUrl,pId);
    },
    error() {
      console.log("网络出错");
    }
  });
};
// 删除一个规格
function delSpe (speId) {
  console.log(speId + '删除')
  $.ajax({
    url: "http://127.0.0.1:8081/message-api/delSpe",
    type: "POST",
    data: {
        "speId": speId
    },
    success(res) {
      console.log(res);
      // 删除成功后
      upSpeInfo(speUrl,pId);
    },
    error() {
      console.log("网络出错");
    }
  });
};
// 调用封面信息 跟 展示图信息以及其他信息
function getPhotoAllInfo (allUrl,pId) {
  $.ajax({
    url: allUrl,
    type: "GET",
    data: {
        "pId": pId
    },
    success(res) {
      console.log(res);
      onMoreData(res);
    },
    error() {
      console.log("网络出错");
    }
  });
};
// 封面 展示图 等数据模板
function onMoreData(res) {
  layui.use('laytpl', function () {
      var laytpl = layui.laytpl;
      var myDatas = {
          //数据
          "title": res.data.photoInfo.photoName,
          "coverImg": res.data.photoInfo.coverImg,
          "showList": res.data.showList,
          "photoInfo": res.data.photoInfo
      };
      var getTpl2 = titleData.innerHTML, view2 = $("#title");
      laytpl(getTpl2).render(myDatas, function (result) {
          //清空元素内部的html代码
          view2.empty();
          //重新添加
          view2.append(result);
      });
      var getTpl0 = coverData.innerHTML, view0 = $("#coverImg");
      laytpl(getTpl0).render(myDatas, function (result) {
          //清空元素内部的html代码
          view0.empty();
          //重新添加
          view0.append(result);
      });
      var getTpl1 = showData.innerHTML, view1 = $("#showList");
      laytpl(getTpl1).render(myDatas, function (result) {
          //清空元素内部的html代码
          view1.empty();
          //重新添加
          view1.append(result);
      });
      var getTpl3 = infoData.innerHTML, view3 = $("#allInfo");
      laytpl(getTpl3).render(myDatas, function (result) {
          //清空元素内部的html代码
          view3.empty();
          //重新添加
          view3.append(result);
      });
  })
};
// 上传封面
layui.use('upload', function(){
  var $ = layui.jquery
  ,upload = layui.upload;
  
  //普通图片上传
  var uploadInst = upload.render({
    elem: '#test1'
    ,url: 'http://127.0.0.1:8081/message-api/upCoverImg'
    ,data: {
      "pId": pId
    }
    ,accept: "file"
    ,field: "file"
    ,method: "POST" //改成您自己的上传接口
    ,before: function(obj){
      //预读本地文件示例，不支持ie8
      obj.preview(function(index, file, result){
        $('#demo1').attr('src', result); //图片链接（base64）
      });
    }
    ,done: function(res){
      //如果上传失败
      if(res.code > 0){
        return layer.msg('上传失败');
      }
      //上传成功
    }
    ,error: function () {
      //演示失败状态，并实现重传
      var demoText = $('#demoText');
      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
      demoText.find('.demo-reload').on('click', function(){
        uploadInst.upload();
      });
    }
  });

  //多图片上传 layui的多文件上传是通过多次调用上传实现的
  upload.render({
    elem: '#test2'
    ,url: 'http://127.0.0.1:8081/message-api/saveShowImg' //改成您自己的上传接口
    ,data: {
      "pId": pId
    }
    ,multiple: true
    ,before: function(obj){
      //预读本地文件示例，不支持ie8
      $('img').remove();
      obj.preview(function(index, file, result){
        $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" style="height:100px;width:150px">')
      });
    }
    ,done: function(res){
      //上传完毕
    }
  });
});
// 监听基础信息提交
$(document).ready(function () {
  layui.use(['form'], function(){
    var form = layui.form;
    
    //监听提交
    form.on('submit(saveForm)', function(data){
      // 执行添加函数
      upSerInfo();
      return false;
    });
  });
});
// 修改基础信息函数
function upSerInfo () {
  layui.use(['layer'], function() {
    var layer = layui.layer //弹层
    /*layer弹出一个示例*/
    $.ajax({
      url: "http://127.0.0.1:8081/message-api/upSerInfo",
      type: "POST",
      data: {
        "modelling": $('input[name="modelling"]').val(),
        "shoot": $('input[name="shoot"]').val(),
        "anaphase": $('input[name="anaphase"]').val(),
        "plot": $('input[name="plot"]').val(),
        "afterSale": $('input[name="afterSale"]').val(),
        "explain": $('input[name="explain"]').val(),
        "pId": pId
      },
      success(res) {
        console.log(res);
        getPhotoAllInfo (allUrl,pId);
        layer.msg('修改成功');
      },
      error() {
        console.log("网络出错");
      }
    });
  });
}