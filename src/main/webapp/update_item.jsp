<!DOCTYPE html>
<%@page import="models.Item"%>
<html>
<head>
<meta charset="UTF-8">
<title>Update Item</title>
<link rel="stylesheet" href="styles/snackbar.css"/>
<link rel="stylesheet" href="styles/add_item.css"/>
</head>
<body>
	<div class="dashboard">
		<form action="UserController" method="post">
		<%
		Item item =(Item)request.getAttribute("item");
		%>
			<h1>Update Item</h1>
			<input class="input-text" type="text" id="name" name="name" value="<%=item.getName() %>" placeholder="Enter your order name" required><br> 
			<input class="input-text" type="text" id="price" name="price" value="<%=item.getPrice() %>" placeholder="Enter your order price" required><br> 
			<input class="input-text" type="text" id="total_price" name="total_price" value="<%=item.getTotalPrice() %>" placeholder="Enter your total price" required><br> 
			<%if(item.getItemDetails().getDescription()!=null){ %>
			<input class="input-text" type="text" id="description" name="description" value="<%=item.getItemDetails().getDescription() %>" placeholder="Enter order description" required><br> 
			<input class="input-text" type="date" id="issue_date" name="issue_date" value="<%=item.getItemDetails().getIssueDate().split(" ")[0]%>" placeholder="Issue Date" required><br> 
			<input class="input-text" type="date" id="expire_date" name="expire_date" value="<%=item.getItemDetails().getExpireDate().split(" ")[0]%>" placeholder="Expire Date" required><br>
			<input type="hidden" id="item-id" name="item-id" value="<%=item.getItemDetails().getId()%>">
			<%}%>
			<input type="hidden" id="id" name="id" value="<%=item.getId()%>">
			<input type="hidden" id="action" name="action" value="update-item">
			<input class="add" type="submit" value="Update">
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