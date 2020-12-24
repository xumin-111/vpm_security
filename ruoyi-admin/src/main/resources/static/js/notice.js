var time;
var delayTime;
$(function(){
    // 增加浮动DIV
    $('body').append("<div id='notice' onselectstart='return false'><span class='notice_title'>&nbsp;</span><span class='cbtn'>[关闭]</span><div class='notice_content'></div></div>");

    // 更改样式
    $('#notice').css({right:"0",bottom:"0",cursor:"default",position:"fixed","background-color":"#CFDEF4",color:"#1F336B","z-index":"999",border:"1px #1F336B solid",margin:"2px",padding:"10px","font-weight":"bold","line-height":"25px",display:"none"});
    $('#notice .cbtn').css({color:"#FF0000",cursor:"pointer","padding-right":"5px",float:"right",position:"relative"});
    $('#notice .notice_content').css({margin:"3px","font-weight":"normal",border:"1px #B9C9EF solid","line-height":"20px","margin-bottom":"10px",padding:"10px"});

    /* 绑定事件*/
    $('#notice').hover(
        function(){
            $(this).stop(true,true).slideDown();
            clearTimeout(time);
        },
        function(){
            time = setTimeout('_notice()',delayTime);
        }
    );

    //绑定关闭事件
    $('.cbtn').bind('click',function(){
        $('#notice').slideUp('fast');
        clearTimeout(time);
    });
});
$.extend({
    lay:function(_width,_height){
        $('#notice').css({width:_width,height:_height});
    },
    show:function(_title,_msg,_time){
        delayTime = _time;
        $('.notice_title').html(_title);
        $('.notice_content').html(_msg);
        $('#notice').slideDown(2000);
        time = setTimeout('_notice()',delayTime);
    },
});
function _notice(){
    $('#notice').slideUp(2000);
}