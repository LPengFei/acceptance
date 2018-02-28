<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
		<meta http-equiv="x-ua-compatible" content="ie=edge">
		<!-- bootstrap css -->
		<link rel="stylesheet" href="${ctx }/public/login/css/bootstrap.min.css" />
		<!-- 时间选择 css -->
		<link rel="stylesheet" href="${ctx }/public/login/css/bootstrap-datepicker.min.css" />
		<!-- 自定义 css -->
		<link rel="stylesheet" href="${ctx }/public/login/css/css.css" />
		<!-- jquery js -->
		<script type="text/javascript" src="${ctx }/public/login/js/jquery.min.js"></script>
		<!-- 时间选择 js -->
		<script type="text/javascript" src="${ctx }/public/login/js/bootstrap-datepicker.min.js"></script>
		<script type="text/javascript" src="${ctx }/public/login/js/bootstrap-datepicker.zh-CN.min.js"></script>
		<!-- bootstrap js -->
		<script type="text/javascript" src="${ctx }/public/login/js/bootstrap.min.js"></script>
		<!-- 自定义 js -->
		<script type="text/javascript" src="${ctx }/public/login/js/js.js"></script>
		<title>首页</title>


		<style type="text/css" >
			.copy_right{
				font-size: 12px;
			}
		</style>
	</head>

	<body style="background: #4AAAA4;">
		<!-- wrap -->
		<div class="wrap login_bg">
			<div class="login_container">
<!-- 				<div class="login_logo" style="padding-top: 100px;"><img src="assets/image/logo.png"></div> -->
				<div class="login_logo" style="padding-top: 100px;"></div>
				<div class="system_name"><kprop:prop key="title"/></div>
				<div class="login_form">
					<form action="${ctx}/secure/dologin" method="post" onsubmit="return form_login();">
						<div class="login_user"><span></span><input type="text" name="username" placeholder="请输入用户名"></div>
						<div class="login_password"><span></span><input type="password" name="password" placeholder="请输入密码"></div>
						<div class="login_yzm"><span></span>
							<input type="text" name="captcha" placeholder="请输入验证码" data-rule="required">
							<img src="${ctx}/secure/captcha" id="randomCode_img" alt="验证码">
						</div>
						<div class="login_button_group" style="overflow: auto;">
							<input type="reset" value="重置" />
							<input type="submit" value="登录" />
						</div>
						<c:if test="${error != null}">
							<p style="color: red;">${error}<p>
						</c:if>
					</form>
<!-- 					<div style="width: 100px;height: 20px;padding-top: 60px;margin-left: -70px;"> -->
<!-- 						<span> -->
<!-- 							<a href="http://ver.cnksi.com.cn/upload/versiozn/SafeTools1.3_2016-08-09_leshan.apk">手机端app下载</a> -->
<!-- 						</span> -->
<!-- 					</div> -->
				</div>
 				<div class="copy_right">Copyright &copy; 2016 - 2017</div>
			</div>
		</div>
		<!-- /wrap -->
		<script type="text/javascript">
			$(function() {
				$(".login_container").css("margin-top", ($(window).height() - 800) / 2);
			})

			// 登录校验
			function form_login() {
				var username = $("input[name='username']").val();
				var password = $("input[name='password']").val();
				var captcha = $("input[name='captcha']").val();
				if(username == null || username.length == 0){
                    $("input[name='username']").css('border', '1px #ff0000 solid');
                    $("input[name='username']").focus();
                    return false;
				}
				if(password == null || password.length == 0){
                    $("input[name='password']").css('border', '1px #ff0000 solid');
                    $("input[name='username']").css('border', 'none');
                    $("input[name='password']").focus();
                    return false;
				}
				if(captcha == null || captcha.length == 0){
                    $("input[name='captcha']").css('border', '1px #ff0000 solid');
                    $("input[name='username']").css('border', 'none');
                    $("input[name='password']").css('border', 'none');
                    $("input[name='captcha']").focus();
                    return false;
				}
            }
		</script>
	</body>

</html>