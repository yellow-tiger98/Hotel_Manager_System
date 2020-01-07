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
    body{margin: 10px;}
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
	                    <a><img alt="" src="${ctxPath}/image/touxiang.png"></a>
	                </div>
	            </div>
	        <form style="margin-top:10px;" class="layui-form" lay-filter="formTestFilter" id="editPopForm">
	            <div class="layui-form-item">
	                <label class="layui-form-label">员工编号：</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <input id="userId"  type="text" name="id"  class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label" id="_account">员工账号：</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <input id="account" type="text" name="account" class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">员工密码：</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <input id="pass" type="text" name="password" class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">用户名称：</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <input id="username" type="text" name="username" class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
    				<label class="layui-form-label">用户性别：</label>
    				<div id="sex" class="layui-input-block" style="width:150px;">
      				<input id="boy" type="radio" name="sex" value="男"  lay-filter="sex" title="男">
      				<input id="girl" type="radio" name="sex" value="女"  lay-filter="sex" title="女">
    				</div>
  				</div>
	            <div class="layui-form-item">
    				<label class="layui-form-label">用户级别：</label>
    					<div  class="layui-input-block" style="width:150px;">
      						<select id="role_name" name="roleName" lay-filter="role" lay-verify="required">
        					<option value="0">酒店经理</option>
        					<option value="1">酒店前台</option>
      						</select>
    					</div>
  				</div>
	            <div class="layui-form-item" id="button-block">
	                <div class="layui-input-block">
	                    <button id="edit_confrim" type="button" class="layui-btn layui-btn-normal">保存</button>
	                    <button id="cancel" type="button" class="layui-btn layui-btn-normal">取消</button>
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
	 var _id = $("#userId").val();
	 var _account = $("#account").val();
	 var _password = $('#pass').val();
	 var _username = $('#username').val();
	 var _sex = $('#sex input[name="sex"]:checked ').val();
	var _role = $("#role_name").find("option:selected").text();
    var _roleid;
	 console.log(_id);
	 console.log(_account);
	 console.log(_password);
	 console.log(_username);
	 console.log(_sex);
	 console.log(_role);
 	if(_role=='酒店经理'){
    	_roleid = '0';
    }else if(_role=='酒店前台'){
    	_roleid = '1';
    }
	if(_id==''||_account==''||_password==''||_username==''||_sex==null){
    	layer.msg("请继续完善信息！");
    }else{
    	layer.confirm('确定要保存修改吗？', function(index){
            layer.close(index);
            console.log("编辑的用户id："+_id);
            $.ajax({
        		url:"${ctxPath}/Admin/addUser",
        		type:"post",
        		data:{id:_id,account:_account,password:_password,username:_username,sex:_sex,roleName:_role,roleid:_roleid},
        		dataType:"json",
        		success:function(json){
        			console.log(json);
        			if(json['result']=='no'){
        				layer.msg(json['message']);
        			}else{
        				layer.msg("添加用户成功！");
        			}
        		}	
        	});
          });

    }
    
});

//编辑信息取消按钮点击事件
$(document).on('click',"#cancel",function(){
    layer.closeAll();
});
</script>
</body>
</html>        