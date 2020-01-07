$('document').ready(function() {
	var floor = 1;
	var count = 0;
	getRealRs(floor);	
	getFloor();
	
	//首层点击事件
	$('#lead').on("click",".index a",function(){
			var $span = $('.floor .cur');
			floor = 1;
			$span.html(1);
			getRealRs(1);
	});
	
	
	//上一页点击事件
	$('#lead').on("click",".pre a",function(){
		console.log(floor);
		if(floor>1){
			var $span = $('.floor .cur');
			floor = floor-1;
			$span.html(floor);
			getRealRs(floor);
		}
	});
	
	//下一页点击事件
	$('#lead').on("click",".next a",function(){
		if(floor<count){
			var $span = $('.floor .cur');
			floor = floor+1;
			$span.html(floor);
			getRealRs(floor);
		}
	});
	
	//尾层点击事件
	$('#lead').on("click",".last a",function(){
			var $span = $('.floor .cur');
			floor = count;
			$span.html(count);
			getRealRs(count);
	});
	
	//加载每一页的房间状态
	function getRealRs(_floor) {
		var $container = $('#container');
		var $box = $('<div></div>')
		$.ajax({
			url : "${ctxPath}/Reception/jsonRealRsList",
			type : 'post',
			data : {
				floor : _floor
			},
			dataType : "json",
			success : function(json) {
				$container.empty();
				var list = json.list;
				console.log(list);
				var i = 0;
				list.forEach(function(item, index, array){
						if (item.ldstatus === '入住中') {
							addDiv($box, item.ldname+"(入住中)", item, $container,"box user u"+i);
						} else if (item.status == '空闲') {
							addDiv($box, item.status, item, $container,"box empty e"+i);
						} else if (item.status == '清洁中') {
							addDiv($box, item.status, item, $container,"box clean c"+i);
						} else if (item.status == '维修中') {
							addDiv($box, item.status, item, $container,"box mtain m"+i);
						}
					i++;
				});
				if(list==''){
					var $h3 = $('<h3 style="text-align:center;color:red;margin-right:30px;">此楼层暂时没有设置房间</h3>').appendTo($container);
				}
				var $div = $('<div><div>').attr('style',"clear:both;").appendTo($container);
			}
		});
	}

	//添加房间状态样式
	function addDiv(content, name, item, main,cl) {
		var $list1 = content.attr('class',cl).clone().appendTo(main);
		var $list2 = $('<div class="bx_top"></div>').html(item.roomid + '房')
				.appendTo($list1);
		var $list3 = $('<div class="bx_bar"></div>').html(item.roomtype).appendTo(
				$list1);
		var $list4 = $('<div class="info"></div>').appendTo($list1);
		var $list6 = $('<div class="name"></div>').html(name).appendTo($list4);
	}
	
	//获取分页条
	function getFloor(){
		var $container = $('#lead');
		$.ajax({
			url : "${ctxPath}/Reception/jsonFloorList",
			type : 'post',
			data : {},
			dataType : "json",
			success : function(json) {
				$container.empty();
				var list = json.floor_list;
				console.log(list);
				var $index = $('<div class="op index"></div>').appendTo($container);
				var $index_a = $('<a onclick="">首层</a>').appendTo($index);
				var $pre = $('<div class="op pre"></div>').appendTo($container);
				var $pre_a = $('<a onclick="">上一层</a>').appendTo($pre);
				list.forEach(function(item, index, array){
					count++;
				});
				var $cur = $('<div class="op floor"></div>').appendTo($container);
				var $cur_span = $('<span class="cur">'+floor+'</span><span class="count">/'+count+'</span>').appendTo($cur);
				var $next = $('<div class="op next"></div>').appendTo($container);
				var $next_a = $('<a onclick="">下一层</a>').appendTo($next);
				var $last = $('<div class="op last"></div>').appendTo($container);
				var $last_a = $('<a onclick="">尾层</a>').appendTo($last);
			}
		});
	}
});



