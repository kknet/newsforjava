<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
 	<meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
    <title>${obj.content}</title>
    <style type="text/css">
    html {
        font-size: 62.5%;
    }
    
    html,
    body {
        width: 100%;
        height: 100%;
        margin: 0px;
        padding: 0px;
    }
    
    .header {
        margin: 1rem 1rem 0.6rem;
        font-size: 2.2rem;
        color: #333;
        font-weight: bold;
    }
    
    .video {
        width: 100%;
        position: relative;
        margin-bottom: 42px;
    }
    
    #my-video {
        width: 100%;
    }
    
    .bottom-news {
        height: 42px;
        width: 100%;
        position: fixed;
        bottom: 0px;
        background: #3c3c3c;
    }
    
    .bottom-news .left {
        float: left;
    }
    
    .bottom-news .left img {
        float: left;
        margin: 6px 12px;
        height: 30px
    }
    
    .bottom-news .left .con {
        float: left;
        padding: 4px 0;
    }
    
    .bottom-news .left .con span {
        color: #fff;
        font-size: 1.6rem;
        display: block;
    }
    
    .bottom-news .left .con span:last-child {
        font-size: 1.2rem;
    }
    
    .bottom-news .right.btn {
        margin: 6px 14px 6px 0px;
        height: 30px;
        width: 68px;
        border-radius: 5px;
        font-size: 1.6rem;
        color: #df3031;
        background: #fff;
        float: right;
        text-align: center;
        line-height: 30px;
        text-decoration: none;
    }
    </style>
    <script src="http://code.jquery.com/jquery-1.9.0.js"></script>
</head>

<body style="background-color: ${background};color:${color}">
    <div class="header">${obj.content}</div>
    <div id="news_content" class="video" style="padding-bottom: 56.25%; /* 16:9 */padding-top: 25px;height: 0;">
         <iframe id="youtube_video" frameborder="no" scrolling="no" style="position: absolute;top: 0;left: 0;width: 100%;height: 100%;" src="http://www.youtube.com/embed/${obj.source}?enablejsapi=1&widgetid=1&showinfo=0&fs=0" frameborder="1" width="100%">
         </iframe>
    </div>
    <div class="bottom-news">
        <div class="left">
            <img src="${pageContext.request.contextPath}/static/img/newslogo.png">
            <div class="con">
                <span>Hi News</span>
                <span>news for you fun for you</span>
            </div>
        </div>
        <a id="openApp" href="javascript:void(0)" class="right btn"> Open it </a>
        <iframe id="down_iframe" style="display:none;"></iframe>
    </div>
</body>

<script>
        var Sfont = [1.0,0.8,0.4];
        var Mfont = [1.2,1.0,0.6];
        var Lfont = [1.4,1.2,0.8];
        var XLfont = [1.6,1.4,1.0];
        var defaultFont = "${font}";
        var language = "${language}";
        var errorHit = language=="india"?"लोड करने में विफल":"Fail to load"
        var toUser = "${touser}";
        var fromapp = "${from}";
        $("#openApp").click(function(){
            var openStr = "intent://newsvideo.com#Intent;scheme=Lewa;action=android.intent.action.VIEW;S.newsid=${obj.news_id};S.language=" + language + ";S.type=youtube_video;S.youtubeid=${obj.source};end";
            var gpStr = "${pageContext.request.contextPath}/static/LbNews_india_2C.apk";
            if(fromapp == "xscreen"){
            	gpStr = "${pageContext.request.contextPath}/static/LbNews_india_2B.apk";
            }
            /*if(toUser == "2b"){
            	if(language == "indonesian"){
            		gpStr = "${pageContext.request.contextPath}/static/LbNews_Lt_2B.apk";
            	}else{
            		gpStr = "${pageContext.request.contextPath}/static/LbNews_india_2B.apk";
            	}
            }else{
            	if(language == "indonesian"){
            		gpStr = "${pageContext.request.contextPath}/static/LbNews_Lt_2C.apk";
            	}else{
            		gpStr = "https://play.google.com/store/apps/details?id=com.lb.news";
            	}
            }*/
            setTimeout(function () { window.location = gpStr; }, 3000);
            window.location = openStr;
        });
        $(document).ready(function(){ 
            changeFont(defaultFont);
            var el = document.createElement('script');
            el.onerror = errorFunction;
            el.src = 'https://www.youtube.com/iframe_api?'+ new Date().getTime();
            document.body.appendChild(el);
            
            if(fromapp == "xscreen"){
            	var downUrl = "${pageContext.request.contextPath}/static/LbNews_india_2B.apk";
            	document.getElementById('down_iframe').src = downUrl;
            }
            
        });
        
        function errorFunction(){
            $("#youtube_video").remove();
            var html = '<div style="background-color:black;font-family: STHeiti;color:white;text-align: center;position: absolute;top: 0;left: 0;width: 100%;height: 100%;">';
            html+='<div id="errorHit" style="margin-top:100px;">'+errorHit+'</div>';
            html+='</div>';
            
            $("#news_content").html(html);
        }
        
        
      //打开夜间模式
        function callJsFuc_SetNightMode(){
            $("body").css("background-color","#444444"); 
            $("#news_content").css("color","#ffffff");
            $("#errorHit").css("color","#ffffff");
        }
        
        //打开白天模式
        function callJsFuc_SetDayMode(){
            $("body").css("background-color","#ffffff"); 
            $("#news_content").css("color","#000000");
            $("#errorHit").css("color","#000000");
        }
        //修改字体大小
        function callJsFuc_SetFontSize(fontSize){
            changeFont(fontSize);
        }
        
        function changeFont(sizetype){
            var sizeArr;
            switch(sizetype){
                case "s":
                    sizeArr = Sfont;
                    break;
                case "m":
                    sizeArr = Mfont;
                    break;
                case "l":
                    sizeArr = Lfont;
                    break;
                case "xl":
                    sizeArr = XLfont;
                    break;
                default:
                    sizeArr = Mfont;
                    break;
            }
            $("#news_title").css("line-height","1.6rem");
            $("#news_title").css("font-size",sizeArr[0]+"rem");
        }
        
        
        function callJsFuc_LoadTimeOut(mode){
            var html = "<div onclick='freshpage()' style='text-align: center;margin-top:20px;'>";
            if(mode == "night"){
                html += "<div><img src='${pageContext.request.contextPath}/static/img/loadfailnight.png' style='width:206px;height:206px;'/></div>";
            }else{
                html += "<div><img src='${pageContext.request.contextPath}/static/img/loadfailday.png' style='width:206px;height:206px;'/></div>";
            }
            
            if(mode == "night"){
                html += "<div style='margin-top:20px;font-size:14px;color:#FFF'><span>click from the new fresh</span></div>";
            }else{
                html += "<div style='margin-top:20px;font-size:14px;color:#000'><span>click from the new fresh</span></div>";
            }
            html += "</div>";
            $("#body").html(html);
        }
        
        function freshpage(){
            location.reload();
        };
        
        var player;
        function onYouTubeIframeAPIReady() {
           player = new YT.Player('youtube_video');
        }
      
        function callJsFuc_pauseVideo(){
            player.pauseVideo();
        }
        
        function callJsFuc_playVideo(){
            player.playVideo();
        }
        
        
        
        //打开夜间模式
        function callJsFuc_SetNightMode(){
            $("body").css("background-color","#444444"); 
            $("#news_content").css("color","#ffffff");
            $("#errorHit").css("color","#ffffff");
        }
        
        //打开白天模式
        function callJsFuc_SetDayMode(){
            $("body").css("background-color","#ffffff"); 
            $("#news_content").css("color","#000000");
            $("#errorHit").css("color","#000000");
        }
        //修改字体大小
        function callJsFuc_SetFontSize(fontSize){
            changeFont(fontSize);
        }
        
        function changeFont(sizetype){
            var sizeArr;
            switch(sizetype){
                case "s":
                    sizeArr = Sfont;
                    break;
                case "m":
                    sizeArr = Mfont;
                    break;
                case "l":
                    sizeArr = Lfont;
                    break;
                case "xl":
                    sizeArr = XLfont;
                    break;
                default:
                    sizeArr = Mfont;
                    break;
            }
            $("#news_title").css("line-height","1.6rem");
            $("#news_title").css("font-size",sizeArr[0]+"rem");
        }
        
        
        function callJsFuc_LoadTimeOut(mode){
            var html = "<div onclick='freshpage()' style='text-align: center;margin-top:20px;'>";
            if(mode == "night"){
                html += "<div><img src='${pageContext.request.contextPath}/static/img/loadfailnight.png' style='width:206px;height:206px;'/></div>";
            }else{
                html += "<div><img src='${pageContext.request.contextPath}/static/img/loadfailday.png' style='width:206px;height:206px;'/></div>";
            }
            
            if(mode == "night"){
                html += "<div style='margin-top:20px;font-size:14px;color:#FFF'><span>click from the new fresh</span></div>";
            }else{
                html += "<div style='margin-top:20px;font-size:14px;color:#000'><span>click from the new fresh</span></div>";
            }
            html += "</div>";
            $("#body").html(html);
        }
        
        function freshpage(){
            location.reload();
        };
    </script>

</html>
