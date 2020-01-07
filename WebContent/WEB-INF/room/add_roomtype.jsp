<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <title>layui在线调试</title>
  <link rel="stylesheet" href="${ctxPath}/layui/css/layui.css" media="all">
  <style>
    body{margin: 10px; }
    .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
    .layui-table-cell{
    height:45px;
    line-height: 45px;
	}
	.layui-table { color: #252525;}
	form{
		padding-left:50px;
	}
	#button-block{
		position:relative;
		right:25px;
		padding-top:10px;
	}
	#touxiang{
		margin-top:10px;
		margin-left:30px;
	}
	
	#editPop{
	margin-left:500px;
	width:400px;
	padding-left:15px;
	
	}
	#editPopForm input{
		width：50px;
	}
  </style>
</head>
<body>
	<!-- 添加房间信息  begin -->
	<div class="layui-row" id="editPop">
	    <div class="layui-col-md12">
	        <form style="margin-top:10px;" class="layui-form" lay-filter="formTestFilter" id="editPopForm">
	            <div class="layui-form-item">
	                <label class="layui-form-label">类型编号:</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <input id="rtId"  type="text" name="rtid"  class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label" id="_account">类型名称:</label>
	                <div  class="layui-input-inline" style="width:150px;">
      						<input id="rtName"  type="text" name="rtname"  class="layui-input">
    					</div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">价格(天):</label>
	             	<div  class="layui-input-inline" style="width:150px;">
      					<input id="dayPrice"  type="text" name="dayprice"  class="layui-input" placeholder="单位:元">
    					</div>
	            </div>
	             <div class="layui-form-item">
	                <label class="layui-form-label">价格(小时):</label>
	             	<div  class="layui-input-inline" style="width:150px;">
      					<input id="hourPrice"  type="text" name="hourprice"  class="layui-input" placeholder="单位:元(可以为空)">
    					</div>
	            </div>
	             <div class="layui-form-item">
	                <label class="layui-form-label">预定价格:</label>
	             	<div  class="layui-input-inline" style="width:150px;">
      					<input id="residence"  type="text" name="residence"  class="layui-input" placeholder="单位:元">
    					</div>
	            </div>
	            <div class="layui-form-item" id="button-block">
	                <div class="layui-input-block">
	                    <button id="edit_confrim" type="button" class="layui-btn layui-btn-normal">保存</button>
	                    <button id="cancel" type="reset" class="layui-btn layui-btn-warn">重置</button>
	                </div>
	            </div>
	        </form>
	    </div>
	</div>
	<!-- 添加房间信息 end -->

 
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-primary layui-btn-ms" lay-event="detail">查看</a>
  <a class="layui-btn layui-btn-ms" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-ms" lay-event="del">删除</a>
</script>

<script src="${ctxPath}/js/jquery-1.8.3.min.js"></script>
<script src="${ctxPath}/layui/layui.js"></script>


<!-- layui 模块加载和各种事件处理 -->
<script>
var popForm;
var data;
layui.config({
  version: '1554901098009' //为了更新 js 缓存，可忽略
});
 
layui.use(
	[  'layer','table', 'element','form'], 
	function(){
	  var laydate = layui.laydate //日期
	  ,layer = layui.layer //弹层
	  ,table = layui.table //表格
	  ,element = layui.element //元素操作
	  popForm = layui.form;
	  popForm.render();
	  
	    
  //监听Tab切换
  element.on('tab(demo)', function(data){
    layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
      tips: 1
    });
  });
	
});
  

$(document).on('click',"#edit_confrim",function(){
	 var _rtid = $("#rtId").val();
	 var _rtname = $("#rtName").val();
	 var _dayprice = $('#dayPrice').val();
	 var _hourprice = $('#hourPrice').val();
	 var _ren = $('#residence').val();
	 console.log(_rtid);
	 console.log(_rtname);
	 console.log(_dayprice);
	 console.log(_hourprice);
	 console.log(_ren);

 	if(_rtid==''||_rtname==''||_dayprice==''||_ren==""){
    	layer.msg("请继续完善信息！");
    }else{
    	layer.confirm('确定要添加吗？', function(index){
            layer.close(index);
            console.log("新增的的房间类型id："+_rtid);
            $.ajax({
        		url:"${ctxPath}/RoomType/addRoomType",
        		type:"post",
        		data:{id:_rtid,typeName:_rtname,dayPrice:_dayprice,hourPrice:_hourprice,residence:_ren},
        		dataType:"json",
        		success:function(json){
        			console.log(json);
        			if(json['result']=='no'){
        				layer.msg(json['message']);
        			}else{
        				layer.msg("添加房间成功！");
        			}
        		}	
        	});
          });

    }
    
});

</script>
</body>
</html>        