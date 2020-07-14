<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="1">
			
			<div class="isolate">
				<div class="center narrow">
					<div class="main_container full_size container_16 clearfix">
						<div class="box">
							<div class="block">
								<a href="/" id="login_logo"><span>ZhetaPricing</span></a>
								<div class="section">
									<c:if test="${not empty param.login_error}">
										<div class="alert alert_red">
											<img width="24" height="24" src="assets/adminica/images/icons/small/white/alert_2.png">
											<fmt:message key="logInError" />
											<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
										</div>
									</c:if>
									<div class="alert alert_light">
										<img width="24" height="24" src="assets/adminica/images/icons/small/grey/locked.png">
										<fmt:message key="login.message" />
									</div>
								</div>
								<form name="login" id="loginform" action="<c:url value='j_spring_security_check'/>" method="POST" class="validate_form">
								<fieldset class="label_side top">
									<label for="username_field"><fmt:message key="user" /></label>
									<div>
										<input type="text" name='j_username' tabindex="0" class="required" id="username" value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' />
									</div>
								</fieldset>
								<fieldset class="label_side uniform">
									<label for="password_field"><fmt:message key="password" /><span><fmt:message key="rememberPassword" /></span></label>
									<div>
										<input type='password' name='j_password' id="password" class="required">
										<input type="checkbox" name="_spring_security_remember_me" id="remember_me">
									</div>
								</fieldset>
								<div class="button_bar clearfix">
									<button class="wide" type="submit">
										<img src="assets/adminica/images/icons/small/white/key_2.png">
										<span>Login</span>
									</button>
									<button type="reset" class="black send_right has_text img_icon">
										<img src="assets/adminica/images/icons/small/white/trashcan.png">
										<span><fmt:message key="cleanFields"/></span>
									</button>
								</div>
								</form>
							</div>
						</div>
					</div>
					<div align="center">
						<span style="padding-top: 10px; font-size: 10px;">Avant Pricing - <c:out value="${version}"></c:out> - <c:out value="${year}"></c:out></span>
					</div>
				</div>
			</div>

		</div> <!-- wrapper -->
	</div> <!-- #pjax -->		

	<div id="loading_overlay">
		<div class="loading_message round_bottom">
			<img src="assets/adminica/images/interface/loading.gif" alt="loading" />
		</div>
	</div>
</body>
</html>