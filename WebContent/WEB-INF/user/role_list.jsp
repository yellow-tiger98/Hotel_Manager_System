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
		right:50px;
		padding-top:10px;
	}
	#touxiang{
		margin-top:10px;
		margin-left:10px;
	}
	#viewPopForm input{
		border:none;
		disabled:true;
	}
	#editPopForm input{
		width：50px;
	}
  </style>
</head>
<body>

<table class="layui-hide" id="demo" lay-filter="test"></table>

<!-- 查看弹窗 begin -->
	<div class="layui-row" id="popViewTest" style="display:none;">
	    <div class="layui-col-md12">
	     <div class="layui-form-item" id="touxiang">
	                <div class="layui-input-inline" style="margin-left:145px;">
	                    <a><img alt="" src="${ctxPath}/image/role_touxiang.png"></a>
	                </div>
	            </div>
	        <form style="margin-top:10px;" class="layui-form" lay-filter="formTestFilter" id="viewPopForm">
	            <div class="layui-form-item">
	                <label class="layui-form-label">角色id：</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <input id="roleId"  type="text" name="roleid"  class="layui-input" disabled=true>
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
	                    <input id="desc" type="text" name="desc" class="layui-input">
	                </div>
	            </div>
	      
	            <div class="layui-form-item" id="button-block">
	                <div class="layui-input-block">
	                    <button id="confrim" type="button" class="layui-btn layui-btn-normal">确认</button>
	                    <button id="edit" type="button" class="layui-btn layui-btn-normal">编辑</button>
	                </div>
	            </div>
	        </form>
	    </div>
	</div>
	<!-- 查看弹窗 end -->
	
	<!-- 编辑弹窗 begin -->
	<div class="layui-row" id="editPop" style="display:none;">
	    <div class="layui-col-md12">
	     <div class="layui-form-item" id="touxiang">
	                <div class="layui-input-inline" style="margin-left:145px;">
	                    <a><img alt="" src="${ctxPath}/image/touxiang.png"></a>
	                </div>
	            </div>
	        <form style="margin-top:10px;" class="layui-form" lay-filter="formTestFilter" id="editPopForm">
	            <div class="layui-form-item">
	                <label class="layui-form-label">角色id：</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <input id="roleId"  type="text" name="roleid"  class="layui-input" disabled=true>
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">角色姓名：</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <input id="roleName" type="text" name="rolename" class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">角色描述：</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <input id="desc" type="text" name="desc" class="layui-input">
	                </div>
	            </div>
	      
	            <div class="layui-form-item" id="button-block">
	                <div class="layui-input-block">
	                    <button id="save_confrim" type="button" class="layui-btn layui-btn-normal">保存</button>
	                    <button id="cancel" type="button" class="layui-btn layui-btn-normal">取消</button>
	                </div>
	            </div>
	        </form>
	    </div>
	</div>
	<!-- 编辑弹窗 end -->
 
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-primary layui-btn-ms" lay-event="detail">查看</a>
  <a class="layui-btn layui-btn-ms" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-ms" lay-event="del">删除</a>
</script>

<script src="${ctxPath}/js/jquery-1.8.3.min.js"></script>
<script src="${ctxPath}/layui/layui.js"></script>
<script>
var data;
var popForm;
layui.config({
  version: '1554901098010' //为了更新 js 缓存，可忽略
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
    elem: '#demo'
    ,height:'full'
    ,width:1000
    ,url: '	${ctxPath}/Admin/jsonRoleList' //数据接口
    ,title: '角色表'
    ,page: true //开启分页
    ,totalRow: false //开启合计行
    ,limit:10
    ,limits:[5,10]
    ,cols: [[ //表头
      	{field: 'roleid', title: '角色id',align:'center',unresize:true,width:220}
      ,{field: 'roleName', title: '角色姓名',align:'center',unresize:true, width:220}
      ,{field: 'desc', title: '角色描述',align:'center',unresize:true, width:220}
      ,{title:'操作', align:'center',unresize:true, toolbar: '#barDemo',style:'margin-top:20px;'}
    ]]
  	,done:function(res, curr, count){
      tableList=res.data;
  }

 });

  
  
  //监听行工具事件 [查看、编辑、删除]
  table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'detail'){  //[1]查看功能
    	console.log(data);
    	layer.open({
        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type:1,
            title:"查看角色信息",
            area: ['21%','40%'],
            content:$("#popViewTest")
        });
    	var type = 'view';
    	setFormValue(data,type);
    } else if(layEvent === 'del'){  //[2]删除功能
        var _roleId = data.roleid;
    	if(_roleId == "*"){
    		layer.msg("此角色不能删除！");
    	}else{
    		layer.confirm('确定要删除此角色吗？', function(index){
    	        obj.del(); //删除对应行（tr）的DOM结构
    	        layer.close(index);
    	        console.log("删除的角色id："+_roleId);
    	            $.ajax({
    	        		url:"${ctxPath}/Admin/deleteRole",
    	        		type:"post",
    	        		data:{roleid:_roleId},
    	        		dataType:"json",
    	        		success:function(json){
    	        			console.log(json);
    	        			if(json['result']=='ok'){
    	        				layer.msg("删除成功！");
    	        			}else{
    	        				layer.msg("删除失败！");
    	        			}
    	        		}	
    	        	});	
    	        
    	      });
    	
    	}
    } else if(layEvent === 'edit'){ //[3]编辑功能
    	console.log(data);
    	layer.open({
        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type:1,
            title:"编辑员角色信息",
            area: ['21%','40%'],
            content:$("#editPop")
        });
    	var type = 'edit';
    	setFormValue(data,type);
    }
  });
  
  
  //执行一个轮播实例
  carousel.render({
    elem: '#test1'
    ,width: '100%' //设置容器宽度
    ,height: 200
    ,arrow: 'none' //不显示箭头
    ,anim: 'fade' //切换动画方式
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
  
  //上传
  upload.render({
    elem: '#uploadDemo'
    ,url: '' //上传接口
    ,done: function(res){
      console.log(res)
    }
  });
  
  slider.render({
    elem: '#sliderDemo'
    ,input: true //输入框
  });
  
  
  //底部信息
  var footerTpl = lay('#footer')[0].innerHTML;
  lay('#footer').html(layui.laytpl(footerTpl).render({}))
  .removeClass('layui-hide');
});

function setFormValue(data,type){
	if(type=='view'){
	popForm.val("formTestFilter", {
		  "roleid":data.roleid 
		 ,"rolename":data.roleName
		 ,"desc":data.desc
		});
		popForm.render(null,'formTestFilter');
	}else{
		popForm.val("formTestFilter", {
			   "roleid":data.roleid 
			  ,"rolename":data.roleName
			  ,"desc":data.desc
			});
			popForm.render(null,'formTestFilter');
	}
		
	}

//查看弹窗确认按钮点击事件
$(document).on('click',"#confrim",function(){
layer.closeAll();
});

//查看弹窗编辑按钮点击事件
$(document).on('click',"#edit",function(){
layer.open({
	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
    type:1,
    title:"编辑角色信息",
    area: ['21%','40%'],
    content:$("#editPop")
});
setFormValue(data);
});

$(document).on('click',"#save_confrim",function(){
 var _id = $("#roleId").val();
 var _rolename = $("#roleName").val();
 var _desc = $("#desc").val();
 console.log(_id);
 console.log(_rolename);
 console.log(_desc);
if(_id=="*"){
	layer.msg("管理员角色信息不可修改！");
}else if(_rolename==''){
	layer.msg("角色名称信息不能为空!");
}else{
	layer.confirm('确定要保存修改吗？', function(index){
        layer.close(index);
        console.log("编辑的用户id："+_id);
        $.ajax({
    		url:"${ctxPath}/Admin/editRole",
    		type:"post",
    		data:{id:_id,roleName:_rolename,desc:_desc},
    		dataType:"json",
    		success:function(json){
    			console.log(json);
    			if(json['result']=='no'){
    				layer.msg(json['message']);
    			}else{
    				location.reload(); 
    				layer.msg("修改成功！");
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