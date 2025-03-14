<!DOCTYPE html>
<%@page import="models.Item"%>
<html>
<head>
<meta charset="UTF-8">
<title>Update Item</title>
<style>
body {
    background-color: #F2F5F6;
    font-family: 'Roboto', sans-serif;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0;
    padding: 4% 10%;
}

.dashboard {
    background-color: #FFFFFF;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
    width: 100%;
    max-width: 400px;
}

h1 {
    color: #073B4C;
    font-size: 20px;
    margin-bottom: 15px;
}

form {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.input-text {
    width: 100%;
    max-width: 300px;
    padding: 10px 12px;
    font-size: 10px;
    border: 1px solid #D1D9E0;
    border-radius: 8px;
    outline: none;
    transition: 0.3s;
    box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.05);
    margin-bottom: 3px;
}

.input-text:focus {
    border-color: #073B4C;
    box-shadow: 2px 2px 12px rgba(7, 59, 76, 0.2);
}

.add, .add-item-button {
    border: none;
    padding: 8px 12px;
    font-size: 11px;
    font-weight: bold;
    border-radius: 5px;
    cursor: pointer;
    width: 80%;
    max-width: 200px;
    transition: 0.3s ease-in-out;
    margin-top: 10px;
}

.add {
    background-color: #073B4C;
    color: white;
}

.add:hover {
    background-color: #055064;
    transform: scale(1.05);
}

.add-item-button {
    background-color: #FFD166;
    color: #073B4C;
}

.add-item-button:hover {
    background-color: #FFC34D;
    transform: scale(1.05);
}

/* Responsive Design */
@media (max-width: 500px) {
    .dashboard {
        padding: 20px;
    }

    .input-text {
        font-size: 12px;
        padding: 10px;
    }

    .add, .add-item-button {
        font-size: 13px;
        padding: 8px 12px;
    }
}
</style>
<link rel="stylesheet" href="styles/snackbar.css"/>
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