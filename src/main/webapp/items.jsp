<%@page import="models.Item"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Items Page</title>
<style>
/* General Page Styling */
body {
	background-color: #F2F5F6;
	padding: 4% 10%;
	font-family: 'Roboto', sans-serif;
	margin: 0;
}

.dashboard {
	background-color: #FFFFFF;
	border-radius: 10px;
	padding: 20px;
	box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
	max-width: 1000px;
	margin: auto;
}

h1 {
	text-align: center;
	color: #073B4C;
	font-size: 20px;
	margin-bottom: 20px;
}

table {
	width: 100%;
	font-size: 8px;
	border-collapse: collapse;
	margin-top: 8px;
	border-radius: 10px;
	overflow: hidden;
}

th, td {
	padding: 10px;
	text-align: center;
	border-bottom: 1px solid #ddd;
}

th {
	background-color: #073B4C;
	color: white;
	font-weight: bold;
	text-transform: uppercase;
}

td {
	background-color: #F9FAFB;
	color: #073B4C;
}

tr:hover td {
	background-color: #E0E7EA;
	transition: background-color 0.3s ease-in-out;
}

.action {
	display: flex;
	gap: 8px;
	justify-content: center;
	align-items: center;
}

/* Buttons Styling */
.add-item-button, .update-item-button, input[type="submit"] {
	background-color: #FFD166;
	border: none;
	padding: 6px 12px;
	color: #073B4C;
	border-radius: 5px;
	cursor: pointer;
	font-size: 11px;
	font-weight: bold;
	transition: all 0.3s ease;
}

/* Add Details Button - Green */
.add-details-button {
	background-color: #28A745; /* Green */
	color: white;
	border: none;
	padding: 6px 12px;
	border-radius: 5px;
	cursor: pointer;
	font-size: 11px;
	font-weight: bold;
	transition: all 0.3s ease;
}

/* Delete Details Button - Red */
.delete-details-button {
	background-color: #DC3545; /* Red */
	color: white;
	border: none;
	padding: 6px 12px;
	border-radius: 5px;
	cursor: pointer;
	font-size: 11px;
	font-weight: bold;
	transition: all 0.3s ease;
}

.add-item-button:hover, .add-details-button:hover,
	.delete-details-button:hover, .update-item-button:hover {
	opacity: 0.9;
	transform: scale(1.07);
}

form {
	display: inline-block;
}

button, input[type="submit"] {
	display: inline-block;
}

/* Responsive Design */
@media ( max-width : 768px) {
	body {
		padding: 2% 5%;
	}
	.dashboard {
		padding: 15px;
	}
	table {
		font-size: 10px;
	}
	th, td {
		padding: 8px;
	}
	.add-item-button, input[type="submit"] {
		font-size: 10px;
		padding: 6px 10px;
	}
	.action {
		flex-direction: column;
		gap: 5px;
	}
}
</style>
</head>
<body>
	<div class="dashboard">
		<h1>Items</h1>
		<table>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Price</th>
				<th>Total Price</th>
				<th>Description</th>
				<th>Issue Date</th>
				<th>Expire Date</th>
				<th>Action</th>
			</tr>

			<%
			List<Item> items = (List<Item>) session.getAttribute("items");
			if (items != null) {
				for (Item item : items) {
			%>
			<tr>
				<td><%=item.getId()%></td>
				<td><%=item.getName()%></td>
				<td><%=item.getPrice()%></td>
				<td><%=item.getTotalPrice()%></td>
				<td><%=item.getItemDetails() == null || item.getItemDetails().getDescription() == null ? "No Description"
		: item.getItemDetails().getDescription()%></td>
				<td><%=item.getItemDetails() == null || item.getItemDetails().getIssueDate() == null ? "No Issue Date"
		: item.getItemDetails().getIssueDate()%></td>
				<td><%=item.getItemDetails() == null || item.getItemDetails().getExpireDate() == null ? "No Expire Date"
		: item.getItemDetails().getExpireDate()%></td>
				<td>
					<div class="action">
						<form action="UserController">
							<input type="hidden" id="action" name="action" value="load-item">
							<input type="hidden" id="id" name="id" value="<%=item.getId()%>">
							<%
							if (item.getItemDetails() != null && item.getItemDetails().getDescription() != null) {
							%>
							<input type="hidden" id="item-id" name="item-id"
								value="<%=item.getItemDetails().getId()%>">
							<%
							}
							%>
							<input class="add-item-button" type="submit" name="update"
								value="update">
						</form>
						<form action="UserController" method="post">
							<input type="hidden" id="action" name="action"
								value="delete-item"> <input type="hidden" id="id"
								name="id" value="<%=item.getId()%>"> <input
								class="add-item-button" type="submit" name="delete"
								value="delete">
						</form>
						<%
						if (item.getItemDetails().getDescription() == null) {
						%>
						<form action="add_details_item.jsp" method="post">
							<input type="hidden" id="item-id" name="item-id"
								value="<%=item.getId()%>"> <input
								class="add-details-button" type="submit" name="add Details"
								value="add Details">
						</form>
						<%
						} else {
						%>
						<form action="UserController" method="post">
							<input type="hidden" id="action" name="action"
								value="delete-details"> <input type="hidden"
								id="item-id" name="item-id" value="<%=item.getId()%>"> <input
								class="delete-details-button" type="submit"
								name="delete Details" value="delete Details">
						</form>
						<%
						}
						%>
					</div>
				</td>
			</tr>

			<%
			}
			} else {
			%>
			<tr>
				<td colspan="5">No items available</td>
			</tr>
			<%
			}
			%>
		</table>
		<a href="add_item.jsp">
			<button class="add-item-button" style="margin-top: 20px;">
				Add Item</button>
		</a>
	</div>
</body>
</html>