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
	form input{
		 autocomplete="off"
	}
	#button-block{
		position:relative;
		left:300px;
		padding-top:10px;
	}
	
	
	#lodgerPop{	
	
	}
	
	#addPopForm input{
		width：50px;
	}
	#editPopForm input{
		width：50px;
	}
	
	#companyTable{
		padding-left:50px;
	}
	
  </style>
  <script type="text/javascript">
  	function reset(){
  		location.reload();
  	}
  </script>
</head>
<body>
	<!-- 登记入住  begin -->
	<div class="layui-row" id="lodgerPop">
	    <div class="layui-col-md12">
	        <form style="margin-top:10px;" class="layui-form" lay-filter="logInFilter" id="logderForm">
	        	<!-- 第一行 begin -->
	            <div class="layui-form-item">
	            	<div class="layui-inline">
	                <label class="layui-form-label" style="font-size:16px;">宾客姓名</label>
	                <div class="layui-input-inline" style="width:200px;font-size:15px;">
	                    <input id="logderName"  type="text" name="lodgerName"  class="layui-input" >
	                </div>
	                </div>
	                
	                <div class="layui-inline">
	                <label class="layui-form-label" style="font-size:16px;">证件类型</label>
	                <div class="layui-input-inline" style="width:200px;font-size:15px;">
	                    <select id="creType" name="creType" lay-filter="creType" lay-verify="required">
        					<option value="身份证">身份证</option>
        					<option value="港澳通行证">港澳通行证</option>
        					<option value="护照">护照</option>
      						</select>
	                </div>
	                </div>
	                
	                <div class="layui-inline">
	                <label class="layui-form-label" style="font-size:16px;">证件号码</label>
	                <div class="layui-input-inline" style="width:200px;font-size:15px;">
	                    <input id="creNo"  type="text" name="creNo"  class="layui-input">
	                </div>
	                </div>
	            </div>
	            <!-- 第一行 end -->
	            
	            <!-- 第二行 begin -->
	            <div class="layui-form-item">
	            	<div class="layui-inline">
	                <label class="layui-form-label" style="font-size:16px;">手机号码</label>
	                <div class="layui-input-inline" style="width:200px;font-size:15px;">
	                    <input id="phoneNumber"  type="text" name="phoneNumber"  class="layui-input" >
	                </div>
	                </div>
	                
	                <div class="layui-inline">
	                <label class="layui-form-label" style="font-size:16px;">入住日期</label>
	                <div class="layui-input-inline" style="width:200px;font-size:15px;">
	                   <input id="inTime"  type="text" name="inTime"  class="layui-input">
	                </div>
	                </div>
	                
	                <div class="layui-inline">
	                <label class="layui-form-label" style="font-size:16px;">离店日期</label>
	                <div class="layui-input-inline" style="width:200px;font-size:15px;">
	                    <input id="outTime"  type="text" name="outTime"  class="layui-input">
	                </div>
	                </div>
	            </div>
	            <!-- 第二行 end -->
	            
	            <!-- 第三行 begin -->
	            <div class="layui-form-item">
	            	<div class="layui-inline">
	                <label class="layui-form-label" style="font-size:16px;">房间号</label>
	                <div  class="layui-input-inline" style="width:200px;font-size:15px;">
	                     <input id="roomSelect"  type="text" name="roomSelect"  class="layui-input"  placeholder="点击选择房间" >
	                </div>
	                </div>
	                
	                <div class="layui-inline">
	                <label class="layui-form-label" style="font-size:16px;">收受押金</label>
	                <div class="layui-input-inline" style="width:200px;font-size:15px;">
	                   <input id="desposit"  type="text" name="desposit"  class="layui-input" placeholder="单位:￥">
	                </div>
	                </div>
	                 <div class="layui-inline">
	                <label class="layui-form-label" style="font-size:16px;">宾客性别</label>
	                <div id="mainsex" class="layui-input-inline" style="width:200px;font-size:15px;">
	                   <input id="boy" type="radio" name="sex" value="男"  lay-filter="sex" title="男">
      				<input id="girl" type="radio" name="sex" value="女"  lay-filter="sex" title="女">
	                </div>
	                </div>
	            </div>
	            <!-- 第三行 end -->
	            
	            <!-- 第四行 begin -->
	            <div class="layui-form-item">
	            	<div class="layui-inline">
	                <label class="layui-form-label" style="font-size:16px;">备注</label>
	                <div class="layui-input-inline" style="width:538px;font-size:15px;">
	                     <textarea id="note" name="note" placeholder="" class="layui-textarea"></textarea>
	                </div>
	                </div>
	            </div>
	            <!-- 第四行 end -->
	            
	             <!-- 第五行 begin -->
	            <div class="layui-form-item">
	            	<div class="layui-inline">
	                <label class="layui-form-label" style="font-size:16px;">同行宾客</label>
	                <div class="layui-input-inline" style="width:800px;font-size:15px;">
	                    <table class="layui-hide" id="demo" lay-filter="add"></table>
	                    <button id="add" type="button" class="layui-btn layui-btn-normal">添加</button>
	                </div>
	                </div>
	            </div>
	            <!-- 第五行 end -->
	            
	            
	            <div class="layui-form-item" id="button-block">
	                <div class="layui-input-block">
	                    <button id="in_confrim" type="button" class="layui-btn layui-btn-normal">确认入住</button>
	                    <button id="reset" class="layui-btn layui-btn-warn" onclick='reset()'>重置信息</button>
	                </div>
	            </div>
	        </form>
	    </div>
	</div>
	<!-- 添加房间信息 end -->
	
	<!-- 添加宾客弹窗 begin -->
	<div class="layui-row" id="addPop" style="display:none;">
	    <div class="layui-col-md12">
	        <form style="margin-top:10px;" class="layui-form" lay-filter="addFilter" id="addPopForm">
	           <div class="layui-form-item">
	                <label class="layui-form-label">宾客名称</label>
	                <div class="layui-input-inline" style="width:150px;"> 
	                    <input id="cpName" type="text" name="cpName"  class="layui-input">
	                </div>
	            </div>
	            
	            <div class="layui-form-item">
	                <label class="layui-form-label">证件类型</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <select id="cerType" name="creType" lay-filter="creType" lay-verify="required">
        					<option value="身份证">身份证</option>
        					<option value="港澳通行证">港澳通行证</option>
        					<option value="护照">护照</option>
      						</select>
	                </div>
	            </div>
	            
	            <div class="layui-form-item">
	                <label class="layui-form-label">证件号码</label>
	                	   <div class="layui-input-inline" style="width:150px;">
	                    <input id="cerNo" type="text" name="cerNo"  class="layui-input">
	                </div>
	            </div>
	            
	            <div class="layui-form-item">
	                <label class="layui-form-label">宾客性别</label>
	                	   <div id="sex" class="layui-input-inline" style="width:150px;">
	                    <input id="boy" type="radio" name="sex" value="男"  lay-filter="sex" title="男">
      				<input id="girl" type="radio" name="sex" value="女"  lay-filter="sex" title="女">
	                </div>
	            </div>
	      
	            <div class="layui-form-item" id="edit-button-block">
	                <div class="layui-input-block">
	                    <button id="add_confrim" type="button" class="layui-btn layui-btn-normal">保存</button>
	                    <button id="cancel" type="button" class="layui-btn layui-btn-normal">取消</button>
	                </div>
	            </div>
	        </form>
	    </div>
	</div>
	<!-- 添加宾客弹窗 end -->
	
	<!-- 编辑宾客弹窗 begin -->
	<div class="layui-row" id="editPop" style="display:none;">
	    <div class="layui-col-md12">
	        <form style="margin-top:10px;" class="layui-form" lay-filter="editFilter" id="editPopForm">
	           <div class="layui-form-item">
	                <label class="layui-form-label">宾客名称</label>
	                <div class="layui-input-inline" style="width:150px;"> 
	                    <input id="edit_cpName" type="text" name="cpName"  class="layui-input">
	                </div>
	            </div>
	            
	            <div class="layui-form-item">
	                <label class="layui-form-label">证件类型</label>
	                <div class="layui-input-inline" style="width:150px;">
	                    <select id="edit_cerType" name="creType" lay-filter="creType" lay-verify="required">
        					<option value="身份证">身份证</option>
        					<option value="港澳通行证">港澳通行证</option>
        					<option value="护照">护照</option>
      						</select>
	                </div>
	            </div>
	            
	            <div class="layui-form-item">
	                <label class="layui-form-label">证件号码</label>
	                	   <div class="layui-input-inline" style="width:150px;">
	                    <input id="edit_cerNo" type="text" name="cerNo"  class="layui-input">
	                </div>
	            </div>
	            
	            <div class="layui-form-item">
	                <label class="layui-form-label">宾客性别</label>
	                	   <div id="edit_sex" class="layui-input-inline" style="width:150px;">
	                    <input id="boy" type="radio" name="sex" value="男"  lay-filter="sex" title="男">
      				<input id="girl" type="radio" name="sex" value="女"  lay-filter="sex" title="女">
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
	<!-- 编辑宾客弹窗 end -->
	
	<!-- 选择房间弹窗 begin -->
	<div class="layui-row" id="roomPop" style="display:none;">
	    <div class="layui-col-md12">
	    <form style="margin-top:10px;margin-left:200px;"  class="layui-form">
	    	<div class="layui-form-item">
	                <label class="layui-form-label">房间类型</label>
	                 <div class="layui-input-inline" style="width:auto;padding-left:15px;">
	                	<select id="roomType" name="roomType" lay-filter="roomType" lay-verify="required">
	                		<option value="*">全部类型</option>
      						</select>
      				</div>
	            </div>
	        </form>
	    </div>
	    <div class="layui-col-md12">
	    <div style="margin:0px 15px;">
	     <table  class="layui-hide" id="roomTable" lay-filter="select"></table>
	     </div>
	    </div>
	    <div style="position:relative;left:225px;" class="layui-inline">
	        <button id="room_confrim" type="button" class="layui-btn layui-btn-normal">确认</button>
	        <button id="cancel" type="button" class="layui-btn layui-btn-normal">取消</button>
	     </div>
	</div>
	<!-- 选择房间弹窗 end -->

 
<script type="text/html" id="addBarDemo">
  <a class="layui-btn layui-btn-ms" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-ms" lay-event="del">删除</a>
</script>

<script type="text/html" id="selectBarDemo">
	<a lay-event="select"><input type="radio" name="select"></a>
</script>
	

<script src="${ctxPath}/js/jquery-1.8.3.min.js"></script>
<script src="${ctxPath}/layui/layui.js"></script>

<!-- layui 模块加载和各种事件处理 -->
<script>
//layui表单对象
var popForm;
//表格当前行数据
var data;
//房间编号
var roomid;
layui.config({
  version: '1554901098009' //为了更新 js 缓存，可忽略
});
 
layui.use(
	[  'layer','table', 'element','form','laydate','laypage'], 
	function(){
	  var laydate = layui.laydate //日期
	  ,layer = layui.layer //弹层
	  ,table = layui.table //表格
	  ,element = layui.element //元素操作
	  ,laydate = layui.laydate
	  ,laypage = layui.laypage
	  popForm = layui.form;
	  popForm.render();
	  
	    
  //监听Tab切换
  element.on('tab(demo)', function(data){
    layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
      tips: 1
    });
  });
  
//日期时间选择器
  laydate.render({
    elem: '#inTime'
    ,type: 'datetime'
    ,value: new Date(	)
  });
  
//日期时间选择器
  laydate.render({
    elem: '#outTime'
    ,type: 'datetime'
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
    
  //监听房间选择弹窗下拉菜单选择
  popForm.on('select(roomType)',function(data){
	 var _typeid = data.value;
	 if(_typeid=='*'){
		 var path = "${ctxPath}/Reception/jsonRoomList"
		getRoomList(path);
	 }else{ 
	 $.ajax({
		url:"${ctxPath}/Reception/refreshRoomType", 
		type:'post',
		data:{typeId:_typeid},
		dataType:"json",
		success:function(json){
			if(json.result=="ok"){
				var path = "${ctxPath}/Reception/jsonNewRoomList"
				getRoomList(path);
			}else{
				layer.msg("获取指定房间类型失败")
			}
		}
		 
	 });
	 }
  });
  
	
  //房间选择表格工具条点击事件
  table.on('tool(select)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
     data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
	if(layEvent==='select'){
		roomid = data.id;
	}
  });
  
  //选择房间弹窗确认按钮点击事件
  $(document).on('click',"#room_confrim",function(){
	 var roomSelect = $('#roomSelect');
	 roomSelect.val(roomid);
	 layer.closeAll();
  });
  
  //所有弹窗取消按钮点击事件
  $(document).on('click','#cancel',function(){
	layer.closeAll(); 
  });
  
  
  
  //房间号码input框点击事件
  $(document).on('click','#roomSelect',function(){
	  layer.open({
          type:1,
          title:"选择房间",
          area: ['40%','65%'],
          content:$("#roomPop")
      });
	  var path = "${ctxPath}/Reception/jsonRoomList";
	  getRoomList(path);
  });
  
  
  //------------------------------添加宾客相关所有点击事件 begin------------------------------------------
  //添加同行宾客按钮点击事件
  $(document).on('click',"#add",function(){
		layer.open({
			type : 1,
			title : "添加同行宾客",
			area : [ '25%', '40%' ],
			content : $("#addPop")
		});
		setFormValue(data,"add")
	});
  
  //添加宾客弹窗确认按钮点击事件
  $(document).on('click',"#add_confrim",function(){
		 var _cpname= $("#cpName").val();
		 var _certype = $("#cerType").val();
		 var _cerno = $('#cerNo').val();
		 var _cpsex = $('#sex input[name="sex"]:checked ').val();
		 console.log(_cpname);
		 console.log(_certype);
		 console.log(_cerno);
		 console.log(_cpsex);
		 
		if(_cpname==''||_cerno==''||_cpsex==null){
	   	layer.msg("请继续完善信息！");
	   }else{
	   	layer.confirm('确定要添加吗？', function(index){
	           layer.close(index);
	           console.log("新增的的宾客姓名："+_cpname);
	           $.ajax({
	       		url:"${ctxPath}/Company/addCompany",
	       		type:"post",
	       		data:{lodgerName:_cpname,certificate:_certype,certificateNo:_cerno,sex:_cpsex},
	       		dataType:"json",
	       		success:function(json){
	       			console.log(json);
	       			if(json['result']=='no'){
	       				layer.msg(json['message']);
	       			}else{
	       			 	getCompanyList();
	       				layer.msg("添加成功！");
	       				setTimeout(function(){
							layer.closeAll();
						}, 1000);
	       			}
	       			}	
	       		});
	         });

	   }
	   
	});
  
  
	//同行宾客表格工具条点击事件
  table.on('tool(add)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
     data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
 	 if(layEvent === 'del'){
    		layer.confirm('确定要删除吗？', function(index){
    	        obj.del(); //删除对应行（tr）的DOM结构
    	        layer.close(index);
    	        //拿取员工id
    	        var _cpid = data.cpId;
    	        console.log("删除的宾客id："+_cpid);
    	            $.ajax({
    	        		url:"${ctxPath}/Company/deleteCompany",
    	        		type:"post",
    	        		data:{cpId:_cpid},
    	        		dataType:"json",
    	        		success:function(json){
    	        			console.log(json);
    	        			if(json['result']=='ok'){
    	        				getCompanyList();
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
            type:1,
            title:"编辑宾客信息",
            area: ['25%','40%'],
            content:$("#editPop")
        });
    	setFormValue(data);
    }
  });
	
  //编辑宾客弹窗确认按钮点击事件
  $(document).on('click',"#edit_confrim",function(){
		 var _cpname= $("#edit_cpName").val();
		 var _certype = $("#edit_cerType").val();
		 var _cerno = $('#edit_cerNo').val();
		 var _cpsex = $('#edit_sex input[name="sex"]:checked ').val();
		 var _cpid = data.cpId;
		 console.log(_cpid);
		 console.log(_cpname);
		 console.log(_certype);
		 console.log(_cerno);
		 console.log(_cpsex);
		 
		if(_cpname==''||_cerno==''||_cpsex==null){
	   	layer.msg("请继续完善信息！");
	   }else{
	   	layer.confirm('确定要修改吗？', function(index){
	           layer.close(index);
	           console.log("修改的宾客姓名："+_cpname);
	           $.ajax({
	       		url:"${ctxPath}/Company/updateCompany",
	       		type:"post",
	       		data:{lodgerName:_cpname,certificate:_certype,certificateNo:_cerno,sex:_cpsex,cpId:_cpid},
	       		dataType:"json",
	       		success:function(json){
	       			console.log(json);
	       			if(json['result']=='no'){
	       				layer.msg(json['message']);
	       			}else{
	       			 	getCompanyList();
	       				layer.msg("添加成功！");
	       				setTimeout(function(){
							layer.closeAll();
						}, 1000);
	       			}
	       			}	
	       		});
	         });

	   }
	   
	});
  //------------------------------添加宾客相关所有点击事件 end------------------------------------------

  //确认入住点击事件
  $(document).on('click','#in_confrim',function(){
	  entryLog();
  });
  //获取房间信息通用方法
  function getRoomList(path){
		 table.render({
			    elem: '#roomTable'
			    ,height:'full'
			    ,url: path //数据接口
			    ,title: '房间选择表'
			    ,page: true //开启分页
			    ,totalRow: false //开启合计行
			    ,limit:5
			    ,limits:[5]
			    ,cols: [[ //表头
			    	{title: '序号',templet: '#xuhao',align:'center',unresize:true, width:100,type:'numbers'}
			      ,{field: 'id', title: '房间编号',align:'center',unresize:true}
			      ,{field: 'typeName', title: '房间类型',align:'center',unresize:true}
			      ,{title:'操作', align:'center',unresize:true,toolbar:'#selectBarDemo',style:'margin-top:20px;'}
			    ]]
			  	,done:function(res, curr, count){
			      tableList=res.data;
			  }
			 });
	  
  }
  
  //获取宾客信息通用方法
  function getCompanyList(){
	  table.render({
			    elem: '#demo'
			    ,height:'full'
			    ,url: '	${ctxPath}/Company/jsonCompanyList' //数据接口
			    ,title: '同行宾客表'
			    ,page: false //开启分页
			    ,totalRow: false //开启合计行
			    ,limit:10
			    ,limits:[5,10]
			    ,cols: [[ //表头
			    	{title: '序号',templet: '#xuhao',align:'center',unresize:true, width:100,type:'numbers'}
			      ,{field: 'lodgerName', title: '宾客姓名',align:'center',unresize:true,width:100}
			      ,{field: 'certificate', title: '证件类型',align:'center',unresize:true, width:100}
			      ,{field: 'certificateNo', title: '证件号码',align:'center',unresize:true, width:140}
			      ,{field: 'sex', title: '宾客性别',align:'center',unresize:true, width:150}
			      ,{title:'操作', align:'center',unresize:true, toolbar: '#addBarDemo',style:'margin-top:20px;'}
			    ]]
			  	,done:function(res, curr, count){
			      tableList=res.data;
			  }
			 });
  }
  
  //确认入住响应事件
  function entryLog(){
	  var _mainName = $('#logderName').val();
	  var _creType = $('#creType').val();
	  var _creNo = $('#creNo').val();
	  var _phone = $('#phoneNumber').val();
	  var _inTime = $('#inTime').val();
	  var _outTime = $('#outTime').val();
	  var _roomSelect = $('#roomSelect').val();
	  var _desposit = $('#desposit').val();
	  var _sex = $('#mainsex input[name="sex"]:checked ').val();
	  var _note = $('#note').val();
	  console.log(_mainName);
	  console.log(_creType);
	  console.log(_creNo);
	  console.log(_phone);
	  console.log(_inTime);
	  console.log(_outTime);
	  console.log(_roomSelect);
	  console.log(_desposit);
	  console.log(_sex);
	  console.log(_note);
	  var inDate = _inTime.split(' ');
		var outDate = _outTime.split(' ');
	  if(_mainName==""||_creType==""||_creNo==""||_phone==""||
			  _inTime==""||_outTime==""||_roomSelect==""||_desposit==""||_sex==null){
		  layer.msg("请完善入住信息！");
	  }else if(outDate<=inDate){
		  layer.msg('离店时间应大于当前入住时间！');
	  }
	  else{
		  layer.confirm('确定入住吗？', function(index){
	           layer.close(index);
	           console.log("入住的登记人姓名："+_mainName);
	           $.ajax({
	       		url:"${ctxPath}/Reception/addMainRegister",
	       		type:"post",
	       		data:{lodgerName:_mainName,certificate:_creType,
	       			certificateNo:_creNo,phone:_phone,inTime:_inTime,
	       			outTime:_outTime,roomNo:_roomSelect,desposit:_desposit,
	       			sex:_sex,note:_note},
	       		dataType:"json",
	       		success:function(json){
	       			console.log(json);
	       			if(json['result']=='no'){
	       				layer.msg(json['message']);
	       			}else{
	       				var _infoId = json['infoId'];
	       			 	$.ajax({
	       			 		url:"${ctxPath}/Reception/addCpRegister",
	       			 		type:'post',
	       			 		data:{lodgingInfoId:_infoId},
	       			 		dataType:'json',
	       			 		success:function(json){
	       			 			if(json['result']=='no'){
	       			 			layer.msg(json['message']);
	       			 			}else if(json['result']=='null'){
	       			 				layer.msg("入住成功");
	       			 			}else{
	       			 				layer.msg("入住成功");
	       			 			}
	       			 		}
	       			 	});
	       			 setTimeout(function(){
							location.reload();
						}, 1000);
	       			}
	       			}	
	       		});
	         });
	  }


	  
  }
  
});
//------------------------------------layui.user() end--------------------------------------------------

//宾客信息编辑赋值input框方法
function setFormValue(data,type){
	if(type=="add"){
		popForm.val("addFilter", {
			 "cpName":"" 
			,"cerType":""
			,"cerNo":""
			,"sex":""
			});
			popForm.render(null, 'addFilter');	
	}else{
		popForm.val("editFilter", {
			 "cpName":data.lodgerName 
			,"cerType":data.certificate
			,"cerNo":data.certificateNo
			,"sex":data.sex
			});
			popForm.render(null, 'editFilter');	
	}	
	}

//初始化执行获取房间类型下拉菜单的选项值
$(getOption);
function getOption(){
	var $rtSelect = $("div #roomType");
	$.ajax({
		url:"${ctxPath}/Reception/getRoomOp",
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