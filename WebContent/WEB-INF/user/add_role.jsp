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
	<!-- 添加用户信息  begin -->
	<div class="layui-row" id="editPop">
	    <div class="layui-col-md12">
	     <div class="layui-form-item" id="touxiang">
	                <div class="layui-input-inline" style="margin-left:145px;">
	                    <a><img alt="" src="${ctxPath}/image/role_touxiang.png"></a>
	                </div>
	            </div>
	        <form style="margin-top:10px;" class="layui-form" lay-filter="formTestFilter" id="editPopForm">
	            <div class="layui-form-item">
	                <label class="layui-form-label">角色id：</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <input id="roleId"  type="text" name="roleid"  class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label" id="_account">角色姓名：</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <input id="roleName" type="text" name="rolename" class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">角色描述：</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <textarea id="desc" type="text" name="desc" class="layui-textarea"></textarea>
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
	<!-- 添加用户信息 end -->

 
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
  
  //用于动态刷新select中被选中数据
  popForm.on('select', function(data){
    $("#role_name").val(data.value);
  });
  
 popForm.on("radio(sex)", function (data) {
      var sex = data.value;
       if (this.value == '男') { 
           	$('#sex').val(this.value);
       } else if (this.value == '女') { 
           $('#sex').val(this.value);
       } 
   });
 
});



$(document).on('click',"#edit_confrim",function(){
	 var _roleid = $("#roleId").val();
	 var _rolename = $("#roleName").val();
	 var _desc = $('#desc').val();

	 console.log(_roleid);
	 console.log(_rolename);
	 console.log(_desc);
	if(_roleid==''||_rolename==''||_desc==''){
    	layer.msg("请继续完善信息！");
    }else{
    	layer.confirm('确定要添加吗？', function(index){
            layer.close(index);
            console.log("新增的的角色id："+_roleid);
            $.ajax({
        		url:"${ctxPath}/Admin/addRole",
        		type:"post",
        		data:{roleid:_roleid,roleName:_rolename,descript:_desc},
        		dataType:"json",
        		success:function(json){
        			console.log(json);
        			if(json['result']=='no'){
        				layer.msg(json['message']);
        			}else{
        				layer.msg("添加角色成功！");
        			}
        		}	
        	});
          });

    }
    
});

</script>
</body>
</html>        