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
	    	 <div class="layui-inline">
	                <label class="layui-form-label">应付:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="price" type="text" name="price"  class="layui-input">
	                </div>
	            </div>
	             <div class="layui-inline">
	                <label class="layui-form-label">实付:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="relpay" type="text" name="relpay"  class="layui-input">
	                </div>
	            </div>
	             <div class="layui-inline">
	                <label class="layui-form-label">结账日期:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="paytime" type="text" name="paytime"  class="layui-input">
	                </div>
	            </div>
	             <div class="layui-inline">
	                <label class="layui-form-label">操作员:</label>
	                <div class="layui-input-inline">
	                    <input  readonly="readonly" id="user" type="text" name="user"  class="layui-input">
	                </div>
	            </div>
	    	</div>
	    	<hr style="margin-right:40px;background:#009688;">
	    
	    	
	    	
	    	
	    	 <div class="layui-form-item" id="detail-button-block">
	                <div class="layui-input-block">
	                    <button style="position:relative;left:400px;" id="detail_confrim" type="button" class="layui-btn layui-btn-default">确认</button>
	                </div>
	            </div>
	    </form>
	    </div>
	</div>
	<!-- 查看详情 end -->
 
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-primary layui-btn-ms" lay-event="detail">详情</a>
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
	  
 
  //执行一个 table 实例
  table.render({
	  id:'testReload'
    ,elem: '#demo'
    ,height:'full'
    ,url: '	${ctxPath}/Reception/jsonHadPayList' //数据接口
    ,title: '未结账表'
    ,page: true //开启分页
    ,totalRow: false //开启合计行
    ,limit:10
    ,limits:[5,10]
    ,cols: [[ //表头
    	{title: '序号',templet: '#xuhao',align:'center',unresize:true,type:'numbers'}
      ,{field: 'lodgerName', title: '宾客',align:'center',width:200,unresize:true}
      ,{field: 'roomId', title: '入住房间',align:'center',width:200,unresize:true}
      ,{field: 'rePrice', title: '结账总额',align:'center',width:200,unresize:true}
      ,{field: 'entryDate', title: '入住时间',align:'center',unresize:true}
      ,{field: 'relLeaveDate', title: '结账时间',align:'center',unresize:true}
      ,{field: 'days', title: '入住天数',align:'center',width:100,unresize:true}
      ,{title:'操作', align:'center',unresize:true, toolbar: '#barDemo',style:'margin-top:20px;'}
    ]]
  	,done:function(res, curr, count){
      tableList=res.data;
  }

 });
  
/*   $('#search').click(function () {
	   var inputVal = $('#search_info').val();
	   console.log(inputVal);
	   table.reload('testReload', {
	    url: '${ctxPath}/Reception/jsonHadPayList'
	    // ,methods:"post"
	    ,request: {
	     pageName: 'page' //页码的参数名称，默认：page
	     ,limitName: 'limit' //每页数据量的参数名，默认：limit
	    }
	    ,where: {
	    	lodgerName : inputVal
	    }
	    ,page: {
	     curr: 1
	    }
	   });
	 }) */
  
  
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
    } 
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
		 data:{roomId:data.roomId,days:data.days,leaveDate:data.entryDate},
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
						 ,"cretype":data.cretificate
						 ,"creno":data.cretificateNo
						 ,"phone":data.phone
						 ,"sex":data.sex
						 ,"roomno":data.roomId
						 ,"roomtype":_roomtype
						 ,"roomprice":_roomprice
						 ,"roomtype":_roomtype
						 ,"note":data.note
						 ,"intype":_inStyle
						 ,"deposit":data.deposit
						 ,"price":"￥"+_price+"元"
						 ,"relpay":"￥"+data.rePrice+"元"
						 ,"paydetail":_paydetail
						 ,"paytime":data.relLeaveDate
						 ,"user":data.operaId
					});
					popForm.render(null,'detailForm');
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
  



	//查看弹窗确认按钮点击事件
	$(document).on('click', "#detail_confrim", function() {
		layer.closeAll();
	});
	
	});
	
</script>
</body>
</html>        