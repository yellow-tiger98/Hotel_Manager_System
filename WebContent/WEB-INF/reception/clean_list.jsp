<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <title>房态设置</title>
  <link rel="stylesheet" href="${ctxPath}/layui/css/layui.css" media="all">
  <style>
    body{
    margin: 10px;
    text-align:center;
    }
    .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
    .layui-table-cell{
    height:45px;
    line-height: 45px;
	}
	.layui-table { color: #252525;}


	#edit-button-block{
		position:relative;
		right:5px;
		padding-top:10px;
	}
	
	#main{
		display:inline-block;
	}


  </style>
</head>
<body>
	
	<div id="main">
	<div class="demoTable" style="text-align:left;">
  <div class="layui-inline">
    <input id="search_info" class="layui-input" name="search_info" autocomplete="off" placeholder="请输入房间编号">
  </div>
  <button id="search" class="layui-btn" data-type="reload">搜索</button>
  <button id="all" class="layui-btn" >全部</button>
</div>
		<!-- 表格 begin -->
		<table class="layui-hide" id="demo" lay-filter="test"></table>
		<!-- 表格 end -->
	</div>
	

 

<script src="${ctxPath}/js/jquery-1.8.3.min.js"></script>
<script src="${ctxPath}/layui/layui.js"></script>
<script type="text/html" id="xuhao">
    {{d.LAY_TABLE_INDEX+1}}
</script>


<!-- layui 模块加载和各种事件处理 -->
<script>
var popForm;
var data;
layui.config({
  version: '1554901098009' //为了更新 js 缓存，可忽略
});
 
layui.use(
	['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider','form'], 
	function(){
	  var laydate = layui.laydate //日期
	  ,laypage = layui.laypage //分页
	  ,layer = layui.layer //弹层
	  ,table = layui.table //表格
	  ,carousel = layui.carousel //轮播
	  ,upload = layui.upload //上传
	  ,element = layui.element //元素操作
	  ,slider = layui.slider //滑块
	  popForm = layui.form;
	  popForm.render();
	  
	    
  //监听Tab切换
  element.on('tab(demo)', function(data){
    layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
      tips: 1
    });
  });
  
 
  //执行一个 table 实例
  table.render({
	  id:"test"
    ,elem: '#demo'
    ,height:'full'
    ,width:1327
    ,url: '${ctxPath}/Reception/jsonCleanList' //数据接口
    ,title: '用户表'
    ,page: true //开启分页
    ,totalRow: false //开启合计行
    ,limit:10
    ,limits:[5,10]
    ,cols: [[ //表头
    	{title: '序号',templet: '#xuhao',align:'center',unresize:true, width:220,type:'numbers'}
      ,{field: 'roomid', title: '房间编号',align:'center',unresize:true,width:220}
      ,{field: 'floor', title: '房间楼层',align:'center',unresize:true, width:220}
      ,{field: 'note', title: '备注',align:'center',unresize:true, width:220}
      ,{field: 'name', title: '清洁人员',align:'center',unresize:true, width:220}
      ,{field: 'time', title: '清洁时间',align:'center',unresize:true, width:220}
    ]]
  	,done:function(res, curr, count){
      tableList=res.data;
  }

 });
  
  $('#search').on('click',function(){
	  var search_info = $('#search_info').val();
	  console.log(search_info);
	    table.reload('test', {
	    	 url: '${ctxPath}/Reception/jsonCleanListBySearch'
	    	,method:'post'
	        ,page: {
	          curr: 1 //重新从第 1 页开始
	        }
	        ,where: {
	            roomid: search_info
	        }
	      });
  });
  
  $('#all').on('click',function(){
	 location.reload(); 
  });
  
  //分页
  laypage.render({
    elem: 'pageDemo' //分页容器的id
    ,count: 8        //总页数
    ,skin: '#1E9FFF' //自定义选中色值
    //,skip: true    //开启跳页
    ,jump: function(obj, first){
      if(!first){
        layer.msg('第'+ obj.curr +'页', {offset: 'b'});
        alert( "TTTTTTTTT" );
      }
    }
  });
  
  
  
});
</script>
</body>
</html>        