<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><kprop:prop key="title"/> </title>
    <link rel="stylesheet" href="${ctx }/public/css/login2_style.css" />
    <script src="${ctx }/public/libraries/BJUI/js/jquery-1.7.2.min.js"></script>
    <script src="${ctx }/public/libraries/BJUI/js/jquery.cookie.js"></script>
    <script src="${ctx }/public/libraries/BJUI/js/sha256.js"></script>
    <style type="text/css">
        .msg-wrap{
            min-height:10px;
        }
        input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px #e8ecf0 inset;
        }
    </style>
</head>
<body>
<div class="login_bg">
    <img src="${ctx }/public/images/login/bg.png" id="login_bgImg">
    <div class="ls_logo"><img src="${ctx }/public/images/login/ls_logo.png"></div>
    <div class="login_wrap">
        <form action="${ctx}/secure/dologin" method="post" id="loginForm" >
            <div class="login_left">
                <h2><kprop:prop key="title"/> </h2>
                <div class="login_line login_top">
                    <div class="login_img">
                        <img src="${ctx }/public/images/login/user_2.png">
                    </div>
                    <input type="text"  id="username" name="username" placeholder="请输入用户名" style="background-color:#e8ecf0">
                </div>
                <div class="login_line">
                    <div class="login_img">
                        <img src="${ctx }/public/images/login/pwd_2.png">
                    </div>
                    <input type="password" onfocus="this.type='password'" name="password" id="password"  placeholder="请输入密码">
                </div>
                <div class="login_yz">
                    <div class="login_img"><img src="${ctx }/public/images/login/yz_2.png"></div>
                    <input style="width:160px" id="j_captcha" type="text" name="captcha" placeholder="验证码" maxlength="4">
                </div>
                <span class="login_yzm">
            	<img id="captcha_img" alt="验证码" src="${ctx}/secure/captcha" />
        	</span>
                <div class="login_btnLogin" onclick="submit()">登录</div>
                <div class="login_btnReset">重置</div>
            </div>
        </form>
        <div class="login_right">
            <div class="login_rightModel ">
                <span></span>S5000平板驱动
            </div>
            <div class="login_rightModel ">
                <span></span>三星平板电脑驱动
            </div>
            <div class="login_rightModel ">
                <span></span>安监系统浏览器
            </div>
            <div class="login_rightModel ">
                <span></span>下载操作手册说明
            </div>
            <div class="login_rightModel ">
                <span></span>安监系统APK下载
            </div>
            <div class="login_rightModel ">
                <span></span>下载安监客户端
            </div>
            <div class="login_rightModel ">
                <span></span>常见问题处理方法
            </div>
        </div>
    </div>
    <div class="compangCopy"  >版权所有:国网供电公司&nbsp;技术支持：四川XXXX信息技术有限公司</div>
</div>
</body>
<script>


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

        $('#login_bgImg').css('height', function () {
            return $(window).height()
        });
        $('.login_right').css('height', function () {
            return parseInt($('.login_left').css('height')) + 10 + 'px'
        })
        $('.login_wrap').css('margin-top', function () {
//            console.log(parseInt($('.login_wrap').css('height')) / 2)
            return -parseInt($('.login_wrap').css('height')) / 2 + 'px'
        })
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

        var password = SHA256_hash($("#password").val());
        $("#password").val(password);

        $("#loginForm").submit();

    }


    function genTimestamp(){
        var time = new Date();
        return time.getTime();
    }
</script>
</html>