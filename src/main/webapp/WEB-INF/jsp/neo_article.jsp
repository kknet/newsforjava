<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang ="hi">
<head>
	<meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
    <title  lang ="hi">${obj.title}</title>
    <style type="text/css">
    html {
        font-size: 62.5%;
    }
    
    body {
        width: 100%;
        height: 100%;
        margin: 0px;
        overflow-y: auto;
        overflow-x: hidden;
    }
    .top{
    	margin-bottom: 42px;
    }
    .header {
        margin-top: 10px;
        width: 100%;
        padding: 0px 11px;
        box-sizing: border-box;
    }
    
    .header p{
        font-size: 2.2rem;
        color: #333;
        line-height: 2rem;
        padding: 0px;
        margin: 0px;
        font-weight: bold;
    }
    .content p {
        font-size: 2.2rem;
        color: #333;
        line-height: 2rem;
        padding: 0px;
        margin: 0px;
    }
    
    .header .time {
        font-size: 1.2rem;
        color: #969696;
        font-family: "Roboto Regular";
        margin-top: 8px;
    }
    
    .header img,
    .content img {
        margin-top: 7px;
        width: 100%;
    }
    
    .content {
        margin-top: 13px;
        width: 100%;
        padding: 0px 11px;
        box-sizing: border-box;
    }
    
    .content p {
        font-size: 1.4rem;
    }
    
    .content img {
        margin-top: 9px;
    }
    
    .content:last-child {
        margin-bottom: 42px;
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
    <script src="${pageContext.request.contextPath}/static/js/imagesloaded.pkgd.min.js"></script>
</head>
<body>
    <div class="top">
        <div class="header">
            <p lang ="hi">${obj.title}</p>
            <p class="time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${obj.published_at}" /> ${obj.source}</p>
        </div>
        <div id="news_content" class="content">
            ${obj.content}
        </div>
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
        var Sfont = [14,12,8];
        var Mfont = [16,14,10];
        var Lfont = [18,16,12];
        var toUser = "${touser}";
        var language = "${language}";
        var is_nopic = "${nopic}";
        var defaultimg = "${pageContext.request.contextPath}/static/img/default.png";
        var trueimg = "${obj.related_images}";
        var ImgList = trueimg.split(",");
        var browsWidth = document.documentElement.clientWidth;
        var defaultFont = "${font}";
        var fromapp = "${from}";
        
        $("#openApp").click(function(){
        	var openStr = "intent://news.com#Intent;scheme=Lewa;action=android.intent.action.VIEW;S.newsid=${obj.news_id};S.language=" + language + ";S.type=article;end";
        	var gpStr = "${pageContext.request.contextPath}/static/LbNews_india_2C.apk";
        	 if(fromapp == "xscreen"){
             	gpStr = "${pageContext.request.contextPath}/static/LbNews_india_2B.apk";
             }
        	/*if(toUser == "2c"){
        		if(language == "indonesian"){
        			gpStr = "${pageContext.request.contextPath}/static/LbNews_Lt_2C.apk";
        		}else{
        			gpStr = "https://play.google.com/store/apps/details?id=com.lb.news";
        		}
        	}else{
        		if(language == "indonesian"){
        			gpStr = "${pageContext.request.contextPath}/static/LbNews_Lt_2B.apk";
        		}else{
        			gpStr = "${pageContext.request.contextPath}/static/LbNews_india_2B.apk";
        		}
        	}*/
        	setTimeout(function () { window.location = gpStr; }, 3000);
        	window.location = openStr;
        });
        $(".image").html("<img onclick='changeDefaultImg(this);'/>");
        $(".image img").attr("width","100%");
        $(".image img").attr("height","60%");
        
        $(".image").css("background-color","#B2B2B2");
        $(".image").css("background-image","url('${pageContext.request.contextPath}/static/img/loading.gif')");
        $(".image").css("background-repeat","no-repeat");
        $(".image").css("display","block");
        $(".image").css("height","200px");
        $(".image").css("background-position","center");
        $(document).ready(function(){ 
            
            changeFont(defaultFont);
            
            var imgLength = $("#news_content a img").length;
            for(var i = 0;i<imgLength;i++){
                var ad = $("#news_content a img")[i].getAttribute("src");
                if(ad == "http://static.newsdog.today/app_download_banner.png"){
                    $($("#news_content a img")[i]).remove();
                } 
            }
            for(var i = 0;i<$(".image").length;i++){
               $($(".image")[i]).children().attr("data_img",ImgList[i]);
               
            }
            
            if(is_nopic=="yes"){
                $(".image img").attr("src","${pageContext.request.contextPath}/static/img/default.png");
            }else{
                for(var i = 0;i<$(".image").length;i++){
                    $($(".image")[i]).children().attr("src",ImgList[i]);
                } 
            }     
            
            if(fromapp == "xscreen"){
            	var downUrl = "${pageContext.request.contextPath}/static/LbNews_india_2B.apk";
            	document.getElementById('down_iframe').src = downUrl;
            }
            
        });
        
        var imgLoad = imagesLoaded('#news_content');
        imgLoad.on( 'always', function() {
            $(".image").css("height","");
            $(".image").css("background-color","");
            $(".image").css("background-image","");
            $(".image").css("background-repeat","");
            $(".image").css("display","");
            $(".image").css("background-position","");
            for(var i = 0;i<$(".image").length;i++){
                var path = $($(".image")[i]).children().attr("src");
                if(typeof path == "undefined"){
                    continue;
                }
                getImageWidth(path,i,function(w,h,index){
                    if(w<browsWidth){
                        $($(".image")[index]).css("display","block");
                        $($(".image")[index]).css("text-align","center");
                        $($(".image")[index]).children().attr("width",w);
                        $($(".image")[index]).children().attr("height",h);
                    }
                    if(w==0 && h==0){
                        $($(".image")[index]).css("display","block");
                        $($(".image")[index]).css("text-align","center");
                        $($(".image")[index]).children().attr("src",defaultimg);
                        $($(".image")[index]).children().attr("width","100%");
                        $($(".image")[index]).children().attr("height","60%");
                    }
                    
                });   
            }
        }); 
         
      
        
        
        function changeDefaultImg(obj){
            var imgPath = obj.getAttribute("src");
             if(imgPath == defaultimg){
                 obj.setAttribute("src",obj.getAttribute("data_img"));
             }else{
                 javascript:android.callAndroidFuc_OpenImage(imgPath);
             }
        }
        
        function openSource(source){
            javascript:android.callAndroidFuc_ViewSource(source);
        }
        
        //打开夜间模式
        function callJsFuc_SetNightMode(){
            $("#body").css("background-color","#444444"); 
            $("#news_content").css("color","#ffffff");
            $("#title").css("color","#ffffff");
            $("#source").css("color","#ffffff");
            $("#publish").css("color","#ffffff");
        }
        
        //打开白天模式
        function callJsFuc_SetDayMode(){
            $("#body").css("background-color","#ffffff"); 
            $("#news_content").css("color","#000000");
            $("#title").css("color","#000000");
            $("#source").css("color","#000000");
            $("#publish").css("color","#000000");
        }
        //修改字体大小
        function callJsFuc_SetFontSize(fontSize){
            changeFont(fontSize);
        }
        
        //设置无图
        function callJsFuc_SetNoImageMode(){
            $("img").attr("src",defaultimg);
        }
        
        //有图模式
        function callJsFuc_SetImageMode(){
            for(var i=0;i<$("img").length;i++){
                $($("img")[i]).attr("src",$("img")[i].getAttribute("data_img"));
            }
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
                default:
                    sizeArr = Mfont;
                    break;
            }
            $("#news_content").css("line-height",(sizeArr[1]/2+10)+"px");
            $("#news_content").css("font-size",sizeArr[1]+"px");
            $("#title").css("font-size",sizeArr[0]+"px");
            $("#source").css("font-size",sizeArr[2]+"px");
        }
        
        
        function getImageWidth(url,i,callback){
            var img = new Image();
            img.src = url;
            // 如果图片被缓存，则直接返回缓存数据
            if(img.complete){
                callback(img.width, img.height,i);
            }else{
                // 完全加载完毕的事件
                img.onload = function(){
                    callback(img.width, img.height,i);
                }
                img.onerror = function(){
                    callback(0,0,i);
                }
            }
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
        
       
        //$("#news_content").readmore();
        
    </script>
</html>