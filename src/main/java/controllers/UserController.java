package controllers;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import ServiceImpl.ItemServiceImpl;
import Services.ItemService;
import models.Item;
import models.ItemDetails;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "database_system")
	private DataSource dataSource;
	private ItemService itemService;

	public UserController() {
		super();
	}

	@Override
	public void init() throws ServletException {// initialization method
		super.init();
		this.itemService = new ItemServiceImpl(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			action = "load-items";
		}
		switch (action) {
		case "load-items":
			loadItems(request, response);
			break;
		case "load-item":
			loadItem(request, response);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			doGet(request, response);
		}
		switch (action) {
		case "add-item":
			addItem(request, response);
			break;
		case "add-details":
			addDetailsItem(request, response);
			break;
		case "delete-details":
			deleteDetailsItem(request, response);
			break;
		case "delete-item":
			deleteItem(request, response);
			break;
		case "update-item":
			updateItem(request, response);
			break;
		default:
			response.sendRedirect("items.jsp");
			break;
		}
	}

	private void deleteDetailsItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			int itemId = Integer.parseInt(request.getParameter("item-id"));
			itemService.deleteDetailsItem(itemId);
			loadItems(request, response);
		} catch (Exception e) {
			loadItems(request, response);
		}
	}

	private void addDetailsItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String desc = request.getParameter("description");
			String issue_date = request.getParameter("issue_date");
			String expire_date = request.getParameter("expire_date");
			int itemId = Integer.parseInt(request.getParameter("item-id"));
			ItemDetails itemDetails = new ItemDetails(id, desc, issue_date, expire_date, itemId);
			itemService.addDetailsItem(itemDetails);
			loadItems(request, response);
		} catch (Exception e) {
			request.setAttribute("error",e.toString());
			request.getRequestDispatcher("/add_details_item.jsp").forward(request, response);
		}
	}

	private void updateItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			ItemDetails itemDetails=null;
			int id = Integer.parseInt(request.getParameter("id"));
			String desc=request.getParameter("description");
			if(desc!=null) {
				int itemId = Integer.parseInt(request.getParameter("item-id"));
				String issue_date = request.getParameter("issue_date");
				String expire_date = request.getParameter("expire_date");
				itemDetails=new ItemDetails(itemId,desc,issue_date,expire_date,id);
			}
			String name = request.getParameter("name");
			double price = Double.parseDouble(request.getParameter("price"));
			double totalPrice = Double.parseDouble(request.getParameter("total_price"));
			Item item = new Item(id, name, price, totalPrice, itemDetails);
			itemService.updateItem(item);
			loadItems(request, response);
		} catch (Exception e) {
			request.setAttribute("error",e.toString());
			request.getRequestDispatcher("/update_item.jsp").forward(request, response);
		}
	}

	private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			int itemId = Integer.parseInt(request.getParameter("id"));
			itemService.deleteDetailsItem(itemId);
			itemService.deleteItem(itemId);
			loadItems(request, response);
		} catch (Exception e) {
			loadItems(request, response);
		}
	}

	private void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			Item item = new Item(Integer.parseInt(request.getParameter("id")), request.getParameter("name"),
					Double.parseDouble(request.getParameter("price")),
					Double.parseDouble(request.getParameter("total_price")), null);
			itemService.addItem(item);
			loadItems(request, response);
		} catch (Exception e) {
			request.setAttribute("error",e.toString());
			request.getRequestDispatcher("/add_item.jsp").forward(request, response);
		}
	}

	private void loadItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Item item = itemService.loaditem(id);
			request.setAttribute("item", item);
			request.getRequestDispatcher("/update_item.jsp").forward(request, response);
		} catch (Exception e) {
			loadItems(request, response);
		}

	}

	private void loadItems(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			List<Item> items = itemService.loadItems();
			request.setAttribute("items", items);
			request.getRequestDispatcher("/items.jsp").forward(request, response);
		} catch (Exception e) {
			loadItems(request, response);
		}
	}

//	public String convertDateToString(String date) {
//		try {
//			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//			Date dateAfterConvertToDate = inputFormat.parse(date);
//			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String outPutFormat = outputFormat.format(dateAfterConvertToDate);
//			System.out.println("date after formated : " + outPutFormat);
//			return outPutFormat;
//		} catch (Exception e) {
//			return "2000-01-01 00:00:00";
//		}
//	}
}
