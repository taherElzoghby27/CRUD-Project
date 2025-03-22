<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Item</title>
<link rel="stylesheet" href="styles/snackbar.css"/>
<link rel="stylesheet" href="styles/add_item.css"/>
</head>
<body>
	<div class="dashboard">
		<form action="UserController"  method="post">
			<h1>Add Item</h1>
			<input class="input-text" type="text" id="name" name="name" placeholder="Enter your order name" required>
				<br> 
		    <input class="input-text" type="text" id="price" name="price" placeholder="Enter your order price" required>
				<br> 
			<input class="input-text" type="text" id="total_price" name="total_price" placeholder="Enter your order total price" required>
				<br> 
			<input type="hidden" id="action" name="action" value="add-item">
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