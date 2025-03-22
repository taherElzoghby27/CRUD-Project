<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="styles/auth.css" />
<link rel="stylesheet" href="styles/snackbar.css"/>
</head>

<body>
	<div class="login_class">
		<h2>Login to your account</h2>
		<form action="AuthController" method="post">
			<label for="email">Email</label> <input type="email" name="email"
				id="email" placeholder="Enter your email" required> <br>
			<br> <label for="password">Password</label> <input
				type="password" name="password" id="email"
				placeholder="Enter your password" required> <br> <br>
			<input type="hidden" name="action" id="action" value="sign-in">
			<button type="submit">Login</button>
		</form>
		<div style="text-align: center;">
			<p class="dont_have_account">
				Don't have an account ? <a href="register.jsp" class="signup">Sign
					up</a>
			</p>
		</div>
	</div>
	<%-- Check if an error message exists --%>
	<%
	String errorMessage = (String) session.getAttribute("error");
	if (errorMessage != null) {
	%>
	<div id="snackbar" class="snackbar"><%=errorMessage%></div>
	<script>
        document.getElementById("snackbar").style.display = "block";
        document.getElementById("snackbar").style.opacity = "1";
        setTimeout(() => {
            document.getElementById("snackbar").style.opacity = "0";
        }, 3000); // Hide after 3 seconds
    </script>
	<%
	}
	%>
</body>
</html>