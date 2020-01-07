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
	#view-button-block{
		position:relative;
		right:50px;
		padding-top:10px;
	}
	#edit-button-block{
		position:relative;
		right:5px;
		padding-top:10px;
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


	<!-- 表格 begin -->
	<table class="layui-hide" id="demo" lay-filter="test"></table>
	<!-- 表格 end -->
	
	<!-- 查看弹窗 begin -->
	<div class="layui-row" id="popViewTest" style="display:none;">
	    <div class="layui-col-md12">
	        <form style="margin-top:10px;" class="layui-form" lay-filter="formTestFilter" id="viewPopForm">
	            <div class="layui-form-item">
	                <label class="layui-form-label">房间编号：</label>
	                <div class="layui-input-inline">
	                    <input style="border:none;" type="text" name="roomid"  class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">房间类型：</label>
	                <div class="layui-input-inline">
	                     <input type="text" name="roomtype" class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">房间楼层：</label>
	                <div class="layui-input-inline">
	                    <input type="text" name="roomfloor" class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">房间状态：</label>
	                <div class="layui-input-inline">
	                    <input type="text" name="status" class="layui-input">
	                </div>
	            </div>
	      
	            <div class="layui-form-item" id="view-button-block">
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
	        <form style="margin-top:10px;" class="layui-form" lay-filter="formTestFilter" id="editPopForm">
	           <div class="layui-form-item">
	                <label class="layui-form-label">房间编号：</label>
	                <div class="layui-input-inline">
	                    <input id="roomid" style="border:none;" type="text" name="roomid"  class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">房间类型：</label>
	                <div class="layui-input-inline">
	                    <select id="roomtype" name="roomtype" lay-filter="roomtype" lay-verify="required">
      						</select>
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">房间楼层：</label>
	                	<div  class="layui-input-block" style="width:150px;">
      						<select id="roomfloor" name="rolefloor" lay-filter="roomfloor" lay-verify="required">
        					<option value="1">1</option>
        					<option value="2">2</option>
        					<option value="3">3</option>
        					<option value="4">4</option>
        					<option value="5">5</option>
        					<option value="6">6</option>
      						</select>
    					</div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">房间状态：</label>
	                <div class="layui-input-inline">
	                <select id="status" name="status" lay-filter="status" lay-verify="required">
        					<option value="空闲">空闲</option>
        					<option value="入住中">入住中</option>
        					<option value="清洁中">清洁中</option>
        					<option value="维修中">维修中</option>
        					<option value="暂不开放">暂不开放</option>
      						</select>
	                </div>
	            </div>
	      
	            <div class="layui-form-item" id="edit-button-block">
	                <div class="layui-input-block">
	                    <button id="edit_confrim" type="button" class="layui-btn layui-btn-normal">保存</button>
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
    elem: '#demo'
    ,height:'full'
    ,url: '	${ctxPath}/Room/jsonRoomList' //数据接口
    ,title: '用户表'
    ,page: true //开启分页
    ,totalRow: false //开启合计行
    ,limit:10
    ,limits:[5,10]
    ,cols: [[ //表头
    	{title: '序号',templet: '#xuhao',align:'center',unresize:true, width:220,type:'numbers'}
      ,{field: 'id', title: '房间编号',align:'center',unresize:true,width:220}
      ,{field: 'typeName', title: '房间类型',align:'center',unresize:true, width:220}
      ,{field: 'floor', title: '房间楼层',align:'center',unresize:true, width:220}
      ,{field: 'status', title: '房间状态',align:'center',unresize:true, width:220}
      ,{title:'操作', align:'center',unresize:true, toolbar: '#barDemo',style:'margin-top:20px;'}
    ]]
  	,done:function(res, curr, count){
      tableList=res.data;
  }

 });


  
  //监听行工具事件
  table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
     data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'detail'){
    	console.log(data);
    	layer.open({
        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type:1,
            title:"查看房间信息",
            area: ['21%','40%'],
            content:$("#popViewTest")
        });
    	var type = 'view';
    	setFormValue(data,type);
      
    } else if(layEvent === 'del'){
    		layer.confirm('确定要删除吗？', function(index){
    	        obj.del(); //删除对应行（tr）的DOM结构
    	        layer.close(index);
    	        //拿取员工id
    	        var _roomid = data.id;
    	        console.log("删除的房间id："+_roomid);
    	            $.ajax({
    	        		url:"${ctxPath}/Room/delRoom",
    	        		type:"post",
    	        		data:{id:_roomid},
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
    		
    } else if(layEvent === 'edit'){
    	console.log(data);
    	layer.open({
        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type:1,
            title:"编辑房间信息",
            area: ['25%','40%'],
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
		  "roomid":data.id 
		 ,"roomtype":data.typeName
		 ,"roomfloor":data.floor
		 ,"status":data.status
		});
		popForm.render(null,'formTestFilter'
	);
		} else {
			popForm.val("formTestFilter", {
				  "roomid":data.id 
					 ,"roomtype":data.typeId
					 ,"roomfloor":data.floor
					 ,"status":data.status
			});
			popForm.render(null, 'formTestFilter');
		}

	}

	//查看弹窗确认按钮点击事件
	$(document).on('click', "#confrim", function() {
		layer.closeAll();
	});

	//查看弹窗编辑按钮点击事件
	$(document).on('click', "#edit", function() {
		layer.open({
			//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
			type : 1,
			title : "编辑房间信息",
			area : [ '25%', '40%' ],
			content : $("#editPop")
		});
		setFormValue(data);
	});

	$(document).on('click', "#edit_confrim", function() {
		var _roomid = $("#roomid").val();
		var _roomtype = $("#roomtype").val();
		var _roomfloor = $('#roomfloor').val();
		var _status = $('#status').val();
		console.log(_roomid);
		console.log(_roomtype);
		console.log(_roomfloor);
		console.log(_status);
			layer.confirm('确定要保存修改吗？', function(index) {
				layer.close(index);
				console.log("编辑的房间id：" + _roomid);
				$.ajax({
					url : "${ctxPath}/Room/viewEditRoom",
					type : "post",
					data : {
						id : _roomid,
						typeId:_roomtype,
						floor:_roomfloor,
						status:_status,
					},
					dataType : "json",
					success : function(json) {
						console.log(json);
						if (json['result'] == 'no') {
							layer.msg(json['message']);
						} else {
							
							layer.msg("修改成功！");
							setTimeout(function(){
								location.reload();
							}, 1000);
						}
					}
				});
			});


	});

	//编辑信息取消按钮点击事件
	$(document).on('click', "#cancel", function() {
		layer.closeAll();
	});
	
	$(getOption);
	function getOption(){
		var $rtSelect = $("div #roomtype");
		$.ajax({
			url:"${ctxPath}/Room/getSelectOp",
			type:"post",
			data:{},
			dataType:"json",
			success:function(json){
					var list = json.list;
					list.forEach(function(item,index,array){
						console.log(item);
						var $option = $('<option></option>').attr('value',item.id).html(item.name).appendTo($rtSelect);
					});
			}
		});
	}
</script>
</body>
</html>        