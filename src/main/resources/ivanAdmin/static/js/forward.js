// 定义接口
const inter_url = "http://127.0.0.1:8081/admin-api/getOrderAllInfo"
//初始化执行，查询全部数据
getTableList(inter_url);
//依据联系方式搜索
//监听按钮,会员查询界面
$(document).ready(function () {
  // 绑定联系方式输入搜索
  layui.use(['form'], function() {
    var form = layui.form;

    form.on('submit(formDemo)', function(data){
      // 设置参数 及 请求接口
      const inter_url = "http://127.0.0.1:8081/admin-api/getOrderInfoByPhone?makePhone=" + data.field.keyword;
      layer.msg("搜索中...");
      getTableList(inter_url);
      return false;
    });
  });
  // 绑定全部按钮
  $("#all").click(function () {
    const inter_url = "http://127.0.0.1:8081/admin-api/getOrderAllInfo";
    layer.msg("查询全部");
    getTableList(inter_url);
  });
  // 绑定未拍摄按钮
  $("#wps").click(function () {
    const inter_url = "http://127.0.0.1:8081/admin-api/getOrderNoPhoto";
    layer.msg("查询未拍摄");
    getTableList(inter_url);
  });
  // 绑定已取片按钮
  $("#yqp").click(function () {
    const inter_url = "http://127.0.0.1:8081/admin-api/getOrderIsFetch";
    layer.msg("查询已取消");
    getTableList(inter_url);
  });
  // 绑定未取片按钮
  $("#wqp").click(function () {
    const inter_url = "http://127.0.0.1:8081/admin-api/getOrderNoFetch";
    layer.msg("查询未取消");
    getTableList(inter_url);
  });
  // 绑定已取消按钮
  $("#yqx").click(function () {
    const inter_url = "http://127.0.0.1:8081/admin-api/getNoOrderInfo";
    layer.msg("查询已取消");
    getTableList(inter_url);
  });
  // 绑定评价按钮
  $("#pj").click(function () {
    const inter_url = "http://127.0.0.1:8081/admin-api/getOrderEvaluate";
    layer.msg("查询有评价");
    getTableList(inter_url);
  });
});
// 定义更改状态的函数
function upState (upUrl, id) {
  layui.use(['layer'], function () {
    var layer = layui.layer;
    $.ajax({
      url: upUrl,
      type: "POST",
      data: {
        "oId": id
      },
      success (data) {
        console.log(data);
        layer.msg('执行成功');
        getTableList(inter_url);
      },
      error (e) {
        console.log(e)
      }
    })
  });
}
// 定义一个初始化函数 获取表格数据
function getTableList (inter_url) {
  // 执行先清空输入框内容
  $("#keyword").val("")
  layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function() {
    var laydate = layui.laydate //日期
    ,laypage = layui.laypage //分页
    ,layer = layui.layer //弹层
    ,table = layui.table //表格
    ,carousel = layui.carousel //轮播
    ,upload = layui.upload //上传
    ,element = layui.element; //元素操作 等等...

    // 每页面显示条数
    var limitcount = 10;
    // 当前页
    var curnum = 1;
    // 创建表格
    table.render({
      elem: '#demo',
      height: 480,
      url: inter_url,//数据接口
      method: "GET",
      title: '预约信息',
      page: true, //开启分页
      limit: 10,
      limits: [10, 20, 30],
      toolbar: true,//开启工具栏，此处显示默认图标，可以自定义模板，详见文档
      parseData: function (res) {
        return {
          "code": res.status, //解析接口状态
          "msg": res.msg, //解析提示文本
          "count": res.data.count, //解析数据长度
          "data": res.data.data //解析数据列表
        }
      },
      cols: [
        [ //表头
           {
            field: 'makeName',
            title: '预约姓名',
            width: 105,
            sort: true,
            fixed: 'left',
        }, {
            field: 'makePhone',
            title: '联系方式',
            width: 120
        }, {
            field: 'makeSex',
            title: '性别',
            width: 80,
            sort: true,
        }, {
            field: 'photoName',
            title: '预约套餐',
            width: 105,
            sort: true
        }, {
            field: 'speName',
            title: '套餐规格',
            width: 120,
            sort: true,
        }, {
            field: 'payPrice',
            title: '价格',
            width: 80
        }, {
            field: 'makeDate',
            title: '预约时间',
            width: 160
        }, {
            field: 'photoState',
            title: '摄影状态',
            width: 105,
            sort: true,
            templet: '#tpl_demo1'
        }, {
            field: 'tfetch',
            title: '取片状态',
            width: 105,
            sort: true,
            templet: '#tpl_demo2'
        }, {
          field: 'postState',
          title: '取片方式',
          width: 105,
          sort: true,
          templet: '#tpl_demo3'
        }, {
          field: 'evaluate',
          title: '评价',
          width: 160
        }, {
            fixed: 'right',
            width: 200,
            align: 'center',
            toolbar: '#barArray'
        }, {
            fixed: 'oId',
            hide: true,
        }
      ]
    ],
    done: function (res, curr, count) {
      // 开启分页
      laypage.render({
        elem: '#demo', //分页容器的id
        count: count, //总条数
        curr: curr,
        limit: limitcount, //当前页显示数据
        skin: '#1E9FFF', //自定义选中色值
        jump: function (obj, first) {
          if (!first) {
              curnum = obj.curr;
              limitcount = obj.limit;
              layer.msg('第' + obj.curr + '页', {
                  offset: 'b'
              });
          }
        }
      })
    }
    });
    // 监听 行工具事件
    table.on('tool(test)', function (obj) {
      //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
      var data = obj.data,//获得当前行数据
          layEvent = obj.event; //获得 lay-event 对应的值
      console.log(data.oid);
      switch (layEvent) {
        // 未拍 设置为已拍
        case 'editPhotoState':
          console.log("设置为已拍")
          layer.confirm('确认已拍摄', function (index) {
            // 设置请求连接， 以及封装参数oid
            const upUrl = "http://127.0.0.1:8081/admin-api/upPhotoState"
            upState(upUrl,data.oid);
            layer.close(index);
          });
          break;
        case 'editTfetch':
          console.log("设置为已取片")
          layer.confirm('确认已取片', function (index) {
            // 设置请求连接， 以及封装参数oid
            const upUrl = "http://127.0.0.1:8081/admin-api/upTfetchState"
            upState(upUrl,data.oid)
            layer.close(index);
          });
          break;
        case 'del':
          console.log("删除")
          layer.confirm('真的要删除吗？', function (index) {
            // 设置请求连接， 以及封装参数oid
            const upUrl = "http://127.0.0.1:8081/admin-api/delOrderById"
            upState(upUrl,data.oid);
            layer.close(index);
          });
          break;    
      }
    })
  });
}