$('documnet').ready(function(){
 upLoadPicture();
});


function upLoadPicture() { 
    var $lists = $('.content');
    $.ajax({
        url: '/UpLoadWeb/LoadPicServlet',
        type: 'POST',
        datatype: 'json',
        data: {},
        beforeSend: function() {
          $lists.empty();
            $(".content").append("<a href='http://www.baidu.com'>图片数据正在加载中......</h4>");
        },
        success: function(data) {  //接收回传回来的数据
            $lists.empty();
            var list = data.list;
            console.log(list);    
            //每一行显示
            var lineCount=3;
            var count=1;
            var $list;
            var $ul;
            var $div;
            list.forEach(function(item, index, array) {
              if((count-1)%3==0){
                  $list = $('<div class="pic-content"></div>').appendTo($lists);
                  $ul = $('<ul></ul>').appendTo($list);
                  $div = $('<div style="clear:both"></div>').appendTo($list);
              }
              var $li = $('<li></li>').appendTo($ul);
              var $a = $('<a></a>').appendTo($li);
              var $img = $('<img>').attr('src', item.picPath).appendTo($a);
              var $p1 = $('<p class="title"></p>').html(item.title).appendTo($li);
              var $p2 = $('<p class="info"></p>').html(item.time).appendTo($li);
               count++;
                });
        },
        complete: function() { 
        	$('.pic-content a img').jqthumb({
        		width: 350,
        		height: 250,
        		after: function(imgObj){
        			imgObj.css('opacity', 0).animate({opacity: 1}, 2000);
        		}
        	});
          console.log("加载成功！");
        },
        error: function() {
          alert("出错啦！");
            console.log("数据加载失败");
        }

    });
}
