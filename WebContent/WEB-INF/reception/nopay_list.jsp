<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, user-scalable=0, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
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
	div label{
		font-size:14px;
	}
	#detailForm input{
		border:none;
		font-size:14px;
	}
	#editPopForm input{
		width：50px;
	}
	#payView input{
		border:none;
	}
	 #pay-button-block{
		position:relative;
		right:20px;
	}
  </style>
</head>
<body>


	<!-- 表格 begin -->
	<table class="layui-hide" id="demo" lay-filter="test"></table>
	<!-- 表格 end -->
	
	<!-- 查看详情 begin -->
	<div class="layui-row" id="viewDetailPop" style="display:none;">
	    <div class="layui-col-md12">
	    <form style="margin-top:10px;" class="layui-form" lay-filter="detailForm" id="detailForm">
	    <h3 style="margin:0px;padding:0px;text-align:center;margin-right:35px;font-weight:bold;color:#009688;font-style:italic;">Yellow Sun Down Hotel</h3>
	    <hr style="margin-right:40px;background:#009688;">
	    	 <div class="layui-form-item">
	    	 <div class="layui-inline">
	                <label class="layui-form-label">信息编号:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="lodginginfoId" type="text" name="lodginginfoId"  class="layui-input">
	                </div>
	            </div>
	             <div class="layui-inline">
	                <label class="layui-form-label">宾客姓名:</label>
	                <div class="layui-input-inline">
	                    <input readonly="readonly" id="lodgername" type="text" name="lodgername"  class="layui-input">
	                </div>
	            </div>
	    	</div>
	    	
	    	<div class="layui-form-item">
	    	 <div class="layui-inline">
	                <label class="layui-form-label">证件类型:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="cretype" type="text" name="cretype"  class="layui-input">
	                </div>
	            </div>
	             <div class="layui-inline">
	                <label class="layui-form-label">证件号码:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="creno" type="text" name="creno"  class="layui-input">
	                </div>
	            </div>
	    	</div>
	    	
	    	<div class="layui-form-item">
	    	 <div class="layui-inline">
	                <label class="layui-form-label">联系方式:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="phone" type="text" name="phone"  class="layui-input">
	                </div>
	            </div>
	             <div class="layui-inline">
	                <label class="layui-form-label">性别:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="sex" type="text" name="sex"  class="layui-input">
	                </div>
	            </div>
	    	</div>
	    	
	    		<div class="layui-form-item">
	    		<div class="layui-inline">
	                <label class="layui-form-label">备注:</label>
	                <div class="layui-input-inline">
	                     <input  readonly="readonly" id="note" type="text" name="note"  class="layui-input">
	                </div>
	                </div>
	                <div class="layui-inline">
	                <label class="layui-form-label">押金:</label>
	                <div class="layui-input-inline">
	                     <input  readonly="readonly" id="deposit" type="text" name="deposit"  class="layui-input">
	                </div>
	                </div>
	    	</div>
	    	<hr style="margin-right:40px;background:#009688;">
	    	<div class="layui-form-item">
	    	 <div class="layui-inline">
	                <label class="layui-form-label">房间号码:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="roomno" type="text" name="roomno"  class="layui-input">
	                </div>
	            </div>
	             <div class="layui-inline">
	                <label class="layui-form-label">房间类型:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="roomtype" type="text" name="roomtype"  class="layui-input">
	                </div>
	            </div>
	    	</div>
	    	
	    	<div class="layui-form-item">
	    	 <div class="layui-inline">
	                <label class="layui-form-label">房间价格:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="roomprice" type="text" name="roomprice"  class="layui-input">
	                </div>
	            </div>
	             <div class="layui-inline">
	                <label class="layui-form-label">入住类型:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="intype" type="text" name="intype"  class="layui-input">
	                </div>
	            </div>
	    	</div>
	    	
	    	<div class="layui-form-item">
	    	 <div class="layui-inline">
	                <label class="layui-form-label">房费组成:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="paydetail" type="text" name="paydetail"  class="layui-input">
	                </div>
	            </div>
	    	</div>
	    	<hr style="margin-right:40px;background:#009688;">
	    	
	    	<div class="layui-form-item">
	    		<div class="layui-inline">
	                <label class="layui-form-label">同行宾客:</label>
	                <div class="layui-input-inline" style="width:600px;">
	                </div>
	                </div>
	                <div class="layui-inline">
	                <div class="layui-input-inline" style="width:600px; margin-left:32px;">
	                <table class="layui-hide" id="cptable" lay-filter="cptable"></table>
	                </div>
	                </div>
	    	</div>
	    	<hr style="margin-right:40px;background:#009688;">
	    	<div class="layui-form-item">
	    	 <div class="layui-inline" style="position:relative;left:480px;">
	                <label class="layui-form-label" style="paddin-right:0px;font-size:17px;">总价(￥):</label>
	                <div class="layui-input-inline" style="padding-left:0px;width:60px;">
	                    <input style="font-size:18px;width:50px;padding-left:0px;" id="price" type="text" name="price"  class="layui-input">
	                </div>
	            </div>
	    	</div>
	    	
	    	 <div class="layui-form-item" id="detail-button-block">
	                <div class="layui-input-block">
	                    <button style="position:relative;left:400px;" id="detail_confrim" type="button" class="layui-btn layui-btn-default">确认</button>
	                     <button style="position:relative;left:400px;" id="toPay_button" type="button" class="layui-btn layui-btn-danger">结账</button>
	                </div>
	            </div>
	    </form>
	    </div>
	</div>
	<!-- 查看详情 end -->
	
	<!-- 续房弹窗 begin -->
	<div class="layui-row" id="continueView" style="display: none;">
		<div class="layui-col-md12">
			<form style="margin-top: 10px;" class="layui-form"
				lay-filter="coninueForm" id="continueForm">
				<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label" style="font-size: 16px;">日期选择</label>
					<div class="layui-input-inline"style="width: 200px; font-size: 15px;">
						<input id="continueTime" type="text" name="continueTime" class="layui-input">
						<p style="color:red;">注：续费只能以天为单位</p>	
					</div>
				</div>
				</div>
				
				 <div class="layui-form-item" id="continue-button-block">
	                <div class="layui-input-block">
	                    <button id="continue_confrim" type="button" class="layui-btn layui-btn-default">确认</button>
	                    <button id="cancel" type="button" class="layui-btn layui-btn-default">取消</button>
	                </div>
	            </div>
			</form>
		</div>
	</div>
	<!-- 续房弹窗 end -->
	
		<!-- 结账弹窗 begin -->
	<div class="layui-row" id="payView" style="display: none;">
		<div class="layui-col-md12">
		 <h3 style="margin:0px;padding:0px;text-align:center;font-weight:bold;color:#009688;font-style:italic;">Yellow Sun Down Hotel</h3>
			<hr style="margin:0 40px;background:#009688;">
			<form style="margin-top: 10px;margin-left:20px;" class="layui-form"
				lay-filter="payForm" id="payForm">
				<div class="layui-form-item">
					<label class="layui-form-label" style="font-size: 16px;">宾客姓名:</label>
					<div class="layui-input-inline"style="width: 200px; font-size: 15px;">
						<input id="payName" type="text" name="payName" class="layui-input">	
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="font-size: 16px;">房间号码:</label>
					<div class="layui-input-inline"style="width: 200px; font-size: 15px;">
						<input id="payRoomNo" type="text" name="payRoomNo" class="layui-input">	
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="font-size: 16px;">入住时间:</label>
					<div class="layui-input-inline"style="width: 200px; font-size: 15px;">
						<input id="payInTime" type="text" name="payInTime" class="layui-input">	
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="font-size: 16px;">预计离店:</label>
					<div class="layui-input-inline"style="width: 200px; font-size: 15px;">
						<input id="payOutTime" type="text" name="payOutTime" class="layui-input">	
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="font-size: 16px;">实际离店:</label>
					<div class="layui-input-inline"style="width: 200px; font-size: 15px;">
						<input id="realOutTime" type="text" name="realOutTime" class="layui-input">	
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label" style="font-size: 16px;">费用组成:</label>
					<div class="layui-input-inline"style="width: 200px; font-size: 15px;">
						<input id="details" type="text" name="details" class="layui-input">	
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label" style="font-size: 16px;">应付:</label>
					<div class="layui-input-inline"style="width: 200px; font-size: 15px;">
						<input id="money" type="text" name="money" class="layui-input">	
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="font-size: 16px;">实付:</label>
					<div class="layui-input-inline"style="width: 200px; font-size: 15px;">
						<input style="border:1px solid #009688;width:60px;" id="realpay" type="text" name="realpay" class="layui-input">	
					</div>
				</div>
				
				 <div class="layui-form-item" id="pay-button-block">
	                <div class="layui-input-block">
	                    <button id="pay_confrim" type="button" class="layui-btn layui-btn-default">确认</button>
	                    <button id="cancel" type="button" class="layui-btn layui-btn-default">取消</button>
	                </div>
	            </div>
			</form>
		</div>
	</div>
	<!-- 结账弹窗 end -->

 
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-primary layui-btn-ms" lay-event="detail">详情</a>
  <a class="layui-btn layui-btn-ms" lay-event="edit">续住</a>
  <a class="layui-btn layui-btn-danger layui-btn-ms" lay-event="del">结账</a>
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
    ,url: '	${ctxPath}/Reception/jsonNoPayList' //数据接口
    ,title: '未结账表'
    ,page: true //开启分页
    ,totalRow: false //开启合计行
    ,limit:10
    ,limits:[5,10]
    ,cols: [[ //表头
    	{title: '序号',templet: '#xuhao',align:'center',unresize:true,type:'numbers'}
      ,{field: 'lodgerName', title: '宾客',align:'center',width:200,unresize:true}
      ,{field: 'roomNo', title: '入住房间',align:'center',width:200,unresize:true}
      ,{field: 'inTime', title: '入住时间',align:'center',unresize:true}
      ,{field: 'outTime', title: '预计离店',align:'center',unresize:true}
      ,{field: 'days', title: '入住天数',align:'center',width:100,unresize:true}
      ,{field: 'desposit', title: '已收押金',align:'center',width:100,unresize:true}
      ,{title:'操作', align:'center',unresize:true, toolbar: '#barDemo',style:'margin-top:20px;'}
    ]]
  	,done:function(res, curr, count){
      tableList=res.data;
  }

 });
  
//日期时间选择器
  laydate.render({
    elem: '#continueTime'
    ,type: 'datetime'
  });
  
  //监听行工具事件
  table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
     data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'detail'){ //查看详情
    	console.log(data);
    	layer.open({
        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type:1,
            title:"查看详情",
            area: ['45%','90%'],
            content:$("#viewDetailPop")
        });
    	getDetailValue(data);
      
    } else if(layEvent === 'del'){ //结账
    	var _realPrice = $("#realpay");
    	_realPrice.val("");
    	layer.open({
        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type:1,
            title:"账单详情",
            area: ['25%','65%'],
            content:$("#payView")
        });
    	getPayDetail(data);
    		
    } else if(layEvent === 'edit'){  //续费
    	console.log(data);
    	layer.open({
        	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
            type:1,
            title:"续住",
            area: ['25%','25%'],
            content:$("#continueView")
        });
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
  
  //查看详情获取对应值方法
  function getDetailValue(data){
	  //房间类型
	  var _roomtype;
	  //房间价格
	  var _roomprice;
	  //入住类型
	  var _inStyle;
	  //总价
	  var _price;
	  //费用组成
	  var _paydetail;
	  $.ajax({
		 url:"${ctxPath}/Reception/getRoomDetail",
		 type:"post",
		 data:{roomId:data.roomNo,days:data.days,leaveDate:data.outTime},
		 dataType:'json',
		 success:function(json){
			 if(json['result']!='no'){
				 _roomtype=json['roomType'];
				 _roomprice=json['roomPrice'];
				 _inStyle=json['inStyle'];
				 _price = json['totalPrice'];
				 _paydetail = json['detail'];
				  popForm.val("detailForm",{
						 "lodginginfoId":data.lodgingInfoId
						 ,"lodgername":data.lodgerName
						 ,"cretype":data.certificate
						 ,"creno":data.certificateNo
						 ,"phone":data.phone
						 ,"sex":data.sex
						 ,"cretype":data.certificate
						 ,"roomno":data.roomNo
						 ,"roomtype":_roomtype
						 ,"roomprice":_roomprice
						 ,"roomtype":_roomtype
						 ,"note":data.note
						 ,"intype":_inStyle
						 ,"deposit":data.desposit
						 ,"price":_price
						 ,"paydetail":_paydetail
					});
					popForm.render(null,'detailForm');
					getCpTable(data.lodgingInfoId);
			 }else{
				 layer.msg("房间相关信息加载失败")
			 }
		 }
	  });

		
	}
  
  //获取账单详情弹窗页面值
  function getPayDetail(data){
	var _money;
	var _dateNow;
	var _detail;
	  $.ajax({
		 url:"${ctxPath}/Reception/getRoomDetail",
		 type:"post",
		 data:{roomId:data.roomNo,days:data.days,leaveDate:data.outTime},
		 dataType:'json',
		 success:function(json){
			 _money=json['totalPrice'];
			 _dateNow=json['relLeaveTime'];
			 _detail=json['detail'];
			dateNow=json['dateNow'];	 
			 if(json['result']!='no'){
				 _roomtype=json['roomType'];
				 _roomprice=json['roomPrice'];
				 _inStyle=json['inStyle'];
				 _price = json['totalPrice'];
				  popForm.val("payForm",{
						 "payName":data.lodgerName
						 ,"payRoomNo":data.roomNo
						 ,"payInTime":data.inTime
						 ,"payOutTime":data.outTime
						 ,"realOutTime":_dateNow
						 ,"details":_detail
						 ,"money":_money
					});
					popForm.render(null,'payForm');
					getCpTable(data.lodgingInfoId);
			 }else{
				 layer.msg("房间相关信息加载失败")
			 }
		 }
	  });

		
	}
  
  function getCpTable(id){
		console.log(id);
		$.ajax({
			url:'${ctxPath}/Company/updateInfoId',
			type:'post',
			data:{lodgingInfoId:id},
			dataType:'json',
			success:function(json){
				if(json['result']=='ok'){
					 table.render({
					 	    elem: '#cptable'
					 	    ,height:'full'
					 	    ,url: '	${ctxPath}/Company/jsonCpList' //数据接口
					 	    ,title: '未结账表'
					 	    ,page: false //开启分页
					 	    ,totalRow: false //开启合计行
					 	    ,limit:10
					 	    ,limits:[5,10]
					 	    ,cols: [[ //表头
					 	    	{title: '序号',templet: '#xuhao',align:'center',unresize:true,type:'numbers'}
					 	      ,{field: 'lodgerName', title: '宾客姓名',align:'center',unresize:true}
					 	      ,{field: 'sex', title: '宾客性别',align:'center',unresize:true}
					 	     ,{field: 'certificate', title: '证件类型',align:'center',unresize:true}
					 	    ,{field: 'certificateNo', title: '证件号码',align:'center',unresize:true}
					 	    ]]
					 	  	,done:function(res, curr, count){
					 	      tableList=res.data;
					 	  }

					 	 });		
				}else{
					layer.msg('获取同行宾客信息失败！');
				}
			}
		});
	}
  
	//查看详情结账按钮点击事件
	$(document).on('click', "#toPay_button", function() {
	  	layer.open({
      	//layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
          type:1,
          title:"账单详情",
          area: ['25%','65%'],
          content:$("#payView")
      });
  	getPayDetail(data);
	});
  
});


	//查看弹窗确认按钮点击事件
	$(document).on('click', "#detail_confrim", function() {
		layer.closeAll();
	});
	

	//结算确认按钮点击事件
	$(document).on('click', "#pay_confrim", function() {
		var _roomId = $("#payRoomNo").val();
		var _realPrice = $("#realpay").val();
		if(_realPrice==""){
			layer.msg("请填入实付金额！");
		}else{
			layer.confirm('确定要结算吗？', function(index) {
				layer.close(index);
				$.ajax({
					url : "${ctxPath}/Reception/payForRoom",
					type : "post",
					data : {
						lodgingInfoId:data.lodgingInfoId,
						roomId:_roomId,
						rePrice:_realPrice,
					},
					dataType : "json",
					success : function(json) {
						console.log(json);
						if (json['result'] == 'no') {
							layer.msg(json['message']);
						} else {
							layer.msg("结算成功！");
							setTimeout(function(){
								location.reload();
							}, 1000);
						}
					}
				});
			});
			
		}
	});
	
	//续费确认按钮点击事件
	$(document).on('click', "#continue_confrim", function() {
		var _continueTime = $("#continueTime").val();
		var _continueDate = _continueTime.split(' ');
		var _beforeDate = data.outTime.split(' ');
		console.log(_continueTime);
		if(_continueTime==''){
			layer.msg('请选择入住日期！');
		}else if(_continueDate[0]<=_beforeDate[0]){
			layer.msg('续住日期应大于当前离店日期！');
		}
		else{
			layer.confirm('确定要续住吗？', function(index) {
				layer.close(index);
				$.ajax({
					url : "${ctxPath}/Reception/continueRoom",
					type : "post",
					data : {
						lodgingInfoId:data.lodgingInfoId,
						roomNo:data.roomNo,
						outTime:_continueTime,
						inTime:data.inTime
					},
					dataType : "json",
					success : function(json) {
						console.log(json);
						if (json['result'] == 'no') {
							layer.msg(json['message']);
						} else {
							
							layer.msg("续住成功！");
							setTimeout(function(){
								location.reload();
							}, 1000);
						}
					}
				});
			});
		}
	});

	//取消按钮点击事件
	$(document).on('click', "#cancel", function() {
		layer.closeAll();
	});
	
</script>
</body>
</html>        