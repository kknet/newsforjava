<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
 <meta name="description" content="">
    <meta name="viewport" content="width=device-width">
 <link href="http://fonts.useso.com/css?family=Open+Sans:400,300,600,700,800" rel="stylesheet">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/normalize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/templatemo-misc.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/templatemo-style.css">
    <script src="http://code.jquery.com/jquery-1.9.0.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/imagesloaded.pkgd.min.js"></script>
<title>${obj.title}</title>
</head>
<body  id="body" style="padding:20px;background-color:${background}">
    <header class="site-header">
        <div class="main-header">
            <div class="container">
                <div class="row">
                   <div id="title" style="margin-top: 0px;color:${color}">${obj.title}</div>
                   <div id="source" style="color:${color}">
                       ${obj.source}
                       
                       <a style="margin-left: 10px;" href="javascript:void(0)" onclick="openSource('${obj.source_url}')">View Source</a>
                   </div>
                   <div id="publish" style="color:${color}">${obj.published_at}</div>
                </div> 
            </div> 
        </div> 
    </header> 

    <div class="content-section">
        <div class="container">
            <div id="news_content" class="row" style="color:${color};line-height: 25px;">
                ${obj.content}
            </div> 
        </div> 
    </div> 
</body>


    <script>
        var Sfont = [14,12,8];
        var Mfont = [16,14,10];
        var Lfont = [18,16,12];
        
        $(".image").html("<img onclick='changeDefaultImg(this);'/>");
        var is_nopic = "${nopic}";
        var defaultimg = "${pageContext.request.contextPath}/static/img/default.png";
        var trueimg = "${obj.related_images}";
        var ImgList = trueimg.split(",");
        var browsWidth = document.documentElement.clientWidth;
        var defaultFont = "${font}";
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
            $("#news_content").css("line-height",(sizeArr[1]/2+5)+"px");
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