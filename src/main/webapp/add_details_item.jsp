<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Details For Item</title>
<link rel="stylesheet" href="styles/snackbar.css"/>
<link rel="stylesheet" href="styles/add_item.css"/>
</head>
<body>
	<div class="dashboard">
		<form action="UserController"  method="post">
			<h1>Add Details</h1>
			<input class="input-text" type="text" id="description" name="description" placeholder="Enter description" required>
				<br> 
		    <input class="input-text" type="date" id="issue_date" name="issue_date" placeholder="Enter issue date" required>
				<br> 
			<input class="input-text" type="date" id="expire_date" name="expire_date" placeholder="Enter expire date" required>
				<br> 
			<input type="hidden" id="action" name="action" value="add-details">
			<input type="hidden" id="item-id" name="item-id" value="<%=request.getParameter("item-id") %>">
			<input class="add" type="submit" value="Add">
		</form>
		<a href="UserController">
			<button class="add-item-button">Back to Items</button>
		</a>
	</div>
	<%-- Check if an error message exists --%>
<%
String errorMessage = (String) request.getAttribute("error");
if (errorMessage != null) {
%>
    <div id="snackbar" class="snackbar"><%= errorMessage %></div>
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