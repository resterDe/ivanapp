// 定义接口
const inter_url = "http://127.0.0.1:8081/message-api/getPhotoLists"
//初始化执行，查询全部数据
getPhotoList(inter_url);
// 定义一个初始化函数 获取套餐全部信息
function getPhotoList (inter_url) {
  layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function() {
    var laydate = layui.laydate //日期
    ,laypage = layui.laypage //分页
    ,layer = layui.layer //弹层
    ,table = layui.table //表格
    ,carousel = layui.carousel //轮播
    ,upload = layui.upload //上传
    ,element = layui.element; //元素操作 等等...

    // 创建表格
    table.render({
      elem: '#demo',
      url: inter_url,//数据接口
      method: "GET",
      title: '店主套餐',
      parseData: function (res) {
        console.log(res)
        return {
          "code": res.status, //解析接口状态
          "msg": "摄影套餐信息", //解析提示文本
          "count": 7, //解析数据长度 这里不分页，默认为7
          "data": res.data //解析数据列表
        }
      },
      cols: [
        [ //表头
           {
            field: 'photoName',
            title: '套餐名称',
            align: 'center',
            width: 180,
            sort: true,
            fixed: 'left',
        }, {
            field: 'serviceInfo',
            title: '基本服务',
            align: 'center',
            width: 300
        }, {
            field: 'photoType',
            title: '套餐类型',
            align: 'center',
            width: 180,
            sort: true,
        }, {
          field: 'minPrice',
          title: '起步价格￥',
          align: 'center',
          width: 150,
          sort: true,
        }, {
          fixed: 'right',
          width: 252,
          align: 'center',
          toolbar: '#barArray'
        }, {
            fixed: 'pid',
            hide: true,
        }
      ]
    ],
    });
    // 监听 行工具事件
    table.on('tool(test)', function (obj) {
      //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
      var data = obj.data,//获得当前行数据
          layEvent = obj.event; //获得 lay-event 对应的值
          // 设置请求连接， 以及封装参数oid
          const url = "http://127.0.0.1:8081/message-api/getSpeInfo"
      switch (layEvent) {
        // 未拍 设置为已拍
        case 'detail':
          console.log(data.pid);
          // 打开弹出窗口
          querySpeInfo(url,data.pid);
          break;
        case 'edit':
          console.log(data.pid);
          // 设置请求连接， 以及封装参数oid
          openUp(data.pid);
          break;
      }
    })
  });
}
// 查询规格信息
function querySpeInfo (nurl,pId) {
  $.ajax({
    url: nurl,
    type: "GET",
    data: {
        "pId": pId
    },
    success(res) {
      console.log("查询成功：" + res);
      //将数据传入layui模板
      onQuery(res);
    },
    error() {
      console.log("网络出错");
    }
  })
};
// 查看规格数据模板
function onQuery(res) {
  layui.use('laytpl', function () {
      var laytpl = layui.laytpl;
      var myDatas = {
          //数据
          "speInfoList": res.data,
          "title": "套餐规格信息"
      };
      var getTpl = myData.innerHTML, view = $("#query-main");
      laytpl(getTpl).render(myDatas, function (result) {
          //清空元素内部的html代码
          view.empty();
          //重新添加
          view.append(result);
          //弹窗
          tanchu();
      });
  })
};
// 写一个建立弹出窗口的函数
function tanchu () {
  layer.open({
    type: 1,
    title: "规格信息",
    closeBtn: false,
    anim: 3,
    area: ['500px'],
    shadeClose: true,
    content: $("#query-main"),
    success: function (layero, index) {
      console.log("窗口弹出");
    }
  })
};
// 写一个弹出编辑框的函数
function openUp (pid) {
  layer.open({
    type: 2,
    title: "编辑套餐",
    closeBtn: false,
    anim: 1,
    closeBtn: 1,
    fixed: false,
    area: ['1050px','580px'],
    shadeClose: false,
    content: ["upPhoto.html?pId=" + pid,"none"],
    success: function (layero, index) {
      console.log("窗口弹出");
    },
  })
};
