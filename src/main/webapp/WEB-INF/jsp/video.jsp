<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if lt IE 7]><html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]><html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]><html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
<!-- 
Kool Store Template
#/preview/templatemo_428_kool_store
-->
    <meta charset="utf-8">
    <title>${obj.content}</title>

    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <link href="http://fonts.useso.com/css?family=Open+Sans:400,300,600,700,800" rel="stylesheet">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/normalize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/templatemo-misc.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/templatemo-style.css">
	<title>${obj.content}</title>
<script src="http://code.jquery.com/jquery-1.9.0.js"></script>
</head>
<body style="background-color: ${background};color:${color}">
    <div>
        <div class="container">
            <div id="news_content" class="row" style="position: relative;padding-bottom: 56.25%; /* 16:9 */padding-top: 25px;height: 0;">
                <iframe id="youtube_video" frameborder="no" scrolling="no" style="position: absolute;top: 0;left: 0;width: 100%;height: 100%;" src="http://www.youtube.com/embed/${obj.source}?enablejsapi=1&widgetid=1&showinfo=0&fs=0" frameborder="1" width="100%">
                </iframe>
            </div> 
        </div> 
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
                
                
        $(document).ready(function(){ 
            changeFont(defaultFont);
            var el = document.createElement('script');
            el.onerror = errorFunction;
            el.src = 'https://www.youtube.com/iframe_api?'+ new Date().getTime();
            document.body.appendChild(el);
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