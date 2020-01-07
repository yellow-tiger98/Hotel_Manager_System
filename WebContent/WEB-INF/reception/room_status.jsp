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
    body{margin: 10px;}
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


  </style>
</head>
<body>


	<!-- 表格 begin -->
	<table class="layui-hide" id="demo" lay-filter="test"></table>
	<!-- 表格 end -->
	
	<!-- 修改房态弹窗 begin -->
	<div class="layui-row" id="popViewTest" style="display:none;">
	    <div class="layui-col-md12">
	        <form style="margin-top:10px;" class="layui-form" lay-filter="formTestFilter" id="viewPopForm">
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
	                    <button id="edit_confrim" type="button" class="layui-btn layui-btn-warm">保存</button>
	                    <button id="cancel" type="button" class="layui-btn layui-btn-danger">取消</button>
	                </div>
	            </div>
	        </form>
	    </div>
	</div>
	<!-- 修改房态弹窗 end -->
	
	<!-- 维修信息填写弹窗 begin -->
	<div class="layui-row" id="popMaintain" style="display:none;">
	    <div class="layui-col-md12">
	        <form style="margin-top:10px;" class="layui-form" lay-filter="maintainPop" id="maintainPop">
	            <div class="layui-form-item">
	                <label class="layui-form-label">房间编号：</label>
	                <div class="layui-input-inline">
	                     <input disabled="disabled" type="text" name="roomid"  class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">房间楼层：</label>
	                <div class="layui-input-inline">
	                     <input disabled="disabled" type="text" name="floor"  class="layui-input">
	                </div>
	            </div>
	             <div class="layui-form-item">
	                <label class="layui-form-label">维修信息：</label>
	                <div class="layui-input-inline">
	                     <textarea id="info" type="text" name="info"  class="layui-input"></textarea>
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">维修人员：</label>
	                <div class="layui-input-inline">
	                     <select id="operator" name="operator" lay-filter="operator" lay-verify="required">
	                     
	                     </select>
	                </div>
	            </div>
	        <div class="layui-form-item" id="add-button-block">
	                <div class="layui-input-block">
	                    <button id="add" type="button" class="layui-btn layui-btn-normal">添加</button>
	                    <button id="add_cancel" type="button" class="layui-btn layui-btn-normal">取消</button>
	                </div>
	            </div>
	        </form>
	    </div>
	</div>
	<!-- 维修信息填写弹窗 end -->
	
	<!-- 清洁信息填写弹窗 begin -->
	<div class="layui-row" id="popClean" style="display:none;">
	    <div class="layui-col-md12">
	        <form style="margin-top:10px;" class="layui-form" lay-filter="cleanPop" id="cleanPop">
	            <div class="layui-form-item">
	                <label class="layui-form-label">房间编号：</label>
	                <div class="layui-input-inline">
	                     <input disabled="disabled" type="text" name="roomid"  class="layui-input">
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">房间楼层：</label>
	                <div class="layui-input-inline">
	                     <input disabled="disabled" type="text" name="floor"  class="layui-input">
	                </div>
	            </div>
	             <div class="layui-form-item">
	                <label class="layui-form-label">备注：</label>
	                <div class="layui-input-inline">
	                     <textarea id="note" type="text" name="note"  class="layui-input"></textarea>
	                </div>
	            </div>
	            <div class="layui-form-item">
	                <label class="layui-form-label">清洁人员：</label>
	                <div class="layui-input-inline">
	                     <select id="cleaner" name="cleaner" lay-filter="cleaner" lay-verify="required">
	                     
	                     </select>
	                </div>
	            </div>
	        <div class="layui-form-item" id="add-button-block">
	                <div class="layui-input-block">
	                    <button id="add_clean" type="button" class="layui-btn layui-btn-normal">添加</button>
	                    <button id="cancel_clean" type="button" class="layui-btn layui-btn-normal">取消</button>
	                </div>
	            </div>
	        </form>
	    </div>
	</div>
	<!-- 清洁信息填写弹窗 end -->

 
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-ms" lay-event="detail">修改房态</a>
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
    ,url: '${ctxPath}/Room/jsonRoomList' //数据接口
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
            title:"修改房态",
            area: ['20%','20%'],
            content:$("#popViewTest")
        });
    	setFormValue(data);
    } 
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
  
  
  slider.render({
    elem: '#sliderDemo'
    ,input: true //输入框
  });
  
});

//修改房态弹窗赋值
function setFormValue(data){
	popForm.val("formTestFilter", {
		"status":data.status
		});
		popForm.render(null,'formTestFilter');
	}

//添加维修信息弹窗赋值
function setRepairFormValue(data,type){
	popForm.val("maintainPop", {
		"roomid":data.id
		,"floor":data.floor
		});
		popForm.render(null,'maintainPop');
	}
	
//添加清洁信息弹窗赋值
function setCleanFormValue(data,type){
	popForm.val("cleanPop", {
		"roomid":data.id
		,"floor":data.floor
		});
		popForm.render(null,'cleanPop');
	}

	//房态更改弹窗确认按钮点击事件
	$(document).on('click', "#edit_confrim", function() {
		var _id = data.id;
		var _status = $('#status').val();
		console.log(_status);
		if(_status=='维修中'){
			layer.open({
	            type:1,
	            title:"添加维修信息",
	            area: ['20%','39%'],
	            content:$("#popMaintain")
	        });
			setRepairFormValue(data);
		}else if(_status=='清洁中'){
			layer.open({
	            type:1,
	            title:"添加清洁信息",
	            area: ['20%','39%'],
	            content:$("#popClean")
	        });
			setCleanFormValue(data);
		}else{
			layer.confirm('确定要保存修改吗？', function(index) {
				layer.close(index);
				changeRoomStatus(_id,_status);
			});	
		}


	});
	
	//维修状态信息添加弹窗的添加按钮点击事件
	$(document).on('click', "#add", function() {
		var _id = data.id;
		var _floor = data.floor;
		var _operator = $('#operator').val();
		console.log(_operator);
			layer.confirm('确定要添加吗？', function(index) {
				layer.close(index);
				addRepairInfo(_id,_floor,_operator);
			});	
	});
	
	//清洁状态信息添加弹窗的添加按钮点击事件
	$(document).on('click', "#add_clean", function() {
		var _id = data.id;
		var _floor = data.floor;
		var _cleanerId = $('#cleaner').val();
		var _cleanerName = $('#cleaner option:selected').text();
		console.log(_cleanerName);
			layer.confirm('确定要添加吗？', function(index) {
				layer.close(index);
				addCleanInfo(_id,_floor,_cleanerId,_cleanerName);
			});	
	});

	//编辑信息取消按钮点击事件
	$(document).on('click', "#cancel", function() {
		layer.closeAll();
	});
	
	//添加维修信息取消按钮点击事件
	$(document).on('click', "#add_cancel", function() {
		layer.closeAll();
	});
	
	//添加清洁信息取消按钮点击事件
	$(document).on('click', "#cancel_clean", function() {
		layer.closeAll();
	});
	
	//改变房间状态事件
	function changeRoomStatus(_id,_status){
		$.ajax({
			url : "${ctxPath}/Reception/changeRoomStatus",
			type : "post",
			data : {
				id : _id,
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
	}
	
	//添加维修信息事件
	function addRepairInfo(_id,_floor,_operator){
		var _status = $('#status').val();
		var _info = $('#info').val();
		$.ajax({
			url : "${ctxPath}/Reception/addRepairInfo",
			type : "post",
			data : {
				roomId: _id,
				floor:_floor,
				operator:_operator,
				info:_info
			},
			dataType : "json",
			success : function(json) {
				console.log(json);
				if (json['result'] == 'no') {
					layer.msg(json['message']);
				} else {
					changeRoomStatus(_id,_status)
				}
			}
		});
	}
	
	//添加清洁信息事件
	function addCleanInfo(_id,_floor,_cleanerId,_cleanerName){
		var _status = $('#status').val();
		var _note = $('#note').val();
		$.ajax({
			url : "${ctxPath}/Reception/addCleanInfo",
			type : "post",
			data : {
				roomid: _id,
				floor:_floor,
				note:_note,
				name:_cleanerName,
				id:_cleanerId
			},
			dataType : "json",
			success : function(json) {
				console.log(json);
				if (json['result'] == 'no') {
					layer.msg(json['message']);
				} else {
					changeRoomStatus(_id,_status)
				}
			}
		});
	}
	
	//获取维修人员列表
	$(getOption);
	function getOption(){
		var $rtSelect = $("div #operator");
		$.ajax({
			url:"${ctxPath}/Reception/getRepairOp",
			type:"post",
			data:{},
			dataType:"json",
			success:function(json){
					var list = json.list;
					list.forEach(function(item,index,array){
						console.log(item);
						var $option = $('<option></option>').attr('value',item.name).html(item.name).appendTo($rtSelect);
					});
			}
		});
	}
	
	//获取清洁人员列表
	$(getCleanerOption);
	function getCleanerOption(){
		var $rtSelect = $("div #cleaner");
		$.ajax({
			url:"${ctxPath}/Reception/getCleanerOp",
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