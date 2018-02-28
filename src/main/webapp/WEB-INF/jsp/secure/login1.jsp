<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><kprop:prop key="title"/> </title>
    <link rel="stylesheet" href="${ctx }/public/css/login1_style.css" />
    <script src="${ctx }/public/libraries/BJUI/js/jquery-1.7.2.min.js"></script>
    <script src="${ctx }/public/libraries/BJUI/js/jquery.cookie.js"></script>
    <script src="${ctx }/public/libraries/BJUI/js/sha256.js"></script>
    <style type="text/css">
        .msg-wrap{
            min-height:10px;
        }
        input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px white inset;
        }
    </style>
</head>
<body>
<div class="login-bg">
    <img src="${ctx }/public/images/login/bg_1_.png" id="bg_img">
    <form action="${ctx}/secure/dologin" method="post" id="loginForm" >
        <div class="login-wrap">
            <div class="login-title">用户登录<span>×</span></div>
            <div class="login-line lineTop divUsername">
                <div class="userImg"><img src="${ctx }/public/images/login/user1.png"></div>
                <input type="text" id="username" name="username" placeholder="请输入用户名"  />
            </div>
            <div class="login-line divPassword" >
                <div class="userImg"><img src="${ctx }/public/images/login/pwd1.png"></div>
                <input type="text" onfocus="this.type='password'" name="password" id="password" placeholder="请输入密码"  />
            </div>
            <div class="login-yz ">
                <div class="userImg"><img src="${ctx }/public/images/login/yz1.png"></div>
                <input id="j_captcha" type="text" name="captcha" placeholder="验证码" maxlength="4"/>
            </div>
            <span class="login-yzm" style="width:97px; margin-left: 15px;" >
            <img id="captcha_img" alt="验证码" src="${ctx}/secure/captcha" />
        </span>
            <div class="login-check"><input type="checkbox" name="remember" id="remember"><label>记住用户名</label>
                <a href="#">忘记密码？</a>
            </div>
            <c:if test="${!empty(error) }">
                <div  style="color:red;margin:5px 20px; text-align: left;font-size:12px;">错误提示： ${error } </div>
            </c:if>
            <div class="login-btn" onclick="submit()">登录</div>
        </div>
    </form>
</div>
</body>
<script type="text/javascript">
    var COOKIE_NAME = 'sys__username';

    $(function() {

        if ($.cookie(COOKIE_NAME)){
            $("#username").val($.cookie(COOKIE_NAME));
            $("#remember").attr('checked', true);
            $("#username").focus();
        }

        $('#bg_img').css('height', function () {
            return $(window).height()
        });
        $('.login-wrap').css('margin-top', function () {
            return -parseInt($('.login-wrap').css('height')) / 2 - 20 + 'px'
        });

        $("#captcha_img").click(function(){
            $("#captcha_img").attr("src", "${ctx}/secure/captcha?t="+genTimestamp());
        });


    });

    function submit(){

        var issubmit = true;

        if ($.trim($("#username").val()).length == 0) {
            $(".divUsername").css('border', '1px #ff0000 solid');
            $("#username").focus();
            issubmit=false;
            return;
        }

        if ($.trim($("#password").val()).length == 0) {
            $(".divPassword").css('border', '1px #ff0000 solid');
            $("#password").focus();
            issubmit=false;
            return;
        }

        if ($.trim($("#j_captcha").val()).length == 0) {
            $(".login-yz").css('border', '1px #ff0000 solid');
            $("#j_captcha").focus();
            issubmit=false;
        }

        if(!issubmit){
            return;
        }

        var $remember = $("#remember");
        if ($remember.attr('checked')) {
            $.cookie(COOKIE_NAME, $("#username").val(), { path: '/', expires: 15 });
        } else {
            $.cookie(COOKIE_NAME, null, { path: '/' });  //删除cookie
        }

//        var password = SHA256_hash($("#password").val());
//        $("#password").val(password);

        $("#loginForm").submit();

    }


    function genTimestamp(){
        var time = new Date();
        return time.getTime();
    }
</script>
</html>