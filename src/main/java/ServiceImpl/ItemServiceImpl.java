package ServiceImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import Services.ItemService;
import models.Item;
import models.ItemDetails;

public class ItemServiceImpl implements ItemService {
	private DataSource dataSource;

	public ItemServiceImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Item> loadItems(int userId) {
		List<Item> items = new ArrayList<>();
		try {
			Connection connection = dataSource.getConnection();
			String query = "SELECT i.id,i.name,i.price,i.total_price,id.id as item_details_id ,id.item_id,id.describtion,id.issue_date,id.expiry_data from hr.USERS_ITEMS ui JOIN hr.item i ON ui.ID =? AND ui.id=i.user_id LEFT join hr.item_details id  ON i.id=id.id";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ItemDetails itemDetails = new ItemDetails(resultSet.getInt("item_details_id"),
						resultSet.getString("describtion"), resultSet.getString("issue_date"),
						resultSet.getString("expiry_data"), resultSet.getInt("item_id"));
				Item item = new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("price"),
						resultSet.getDouble("total_price"), itemDetails);
				System.out.println("item "+item);
				items.add(item);
			}
		} catch (Exception e) {
			System.out.println("error : " + e);
		}
		return items;
	}

	@Override
	public Item loaditem(int id) throws Exception {
		try {
			Connection connection = dataSource.getConnection();
			String query = "select i.id as id,i.name,i.price,i.total_price,id.id as item_details_id,id.item_id,id.describtion,id.issue_date,id.expiry_data from hr.item i LEFT join hr.item_details id ON id.ITEM_ID =i.ID WHERE i.ID = ? ";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				ItemDetails itemDetails = new ItemDetails(resultSet.getInt("item_details_id"),
						resultSet.getString("describtion"), resultSet.getString("issue_date"),
						resultSet.getString("expiry_data"), resultSet.getInt("id"));
				Item item = new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("price"),
						resultSet.getDouble("total_price"), itemDetails);
				return item;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return null;
	}

	@Override
	public boolean addItem(Item item, int userId) throws Exception {
		try {
			Connection connection = dataSource.getConnection();
			String query = "insert into hr.item (name,price,total_price,user_id) values(?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, item.getName());
			statement.setDouble(2, item.getPrice());
			statement.setDouble(3, item.getTotalPrice());
			statement.setDouble(4, userId);
			int result = statement.executeUpdate();
			return result > 0;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public boolean deleteItem(int id) throws Exception {
		try {
			Connection connection = dataSource.getConnection();
			String query = "delete from hr.item where id = ? ";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			int result = statement.executeUpdate();
			return result > 0;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public boolean updateItem(Item item) throws Exception {
		try {
			Connection connection = dataSource.getConnection();
			if (item.getItemDetails() != null && item.getItemDetails().getDescription() != null) {
				String query = "UPDATE hr.ITEM_DETAILS SET DESCRIBTION=?, ISSUE_DATE =?, EXPIRY_DATA=? WHERE ITEM_DETAILS.ITEM_ID = ? ";
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setString(1, item.getItemDetails().getDescription());
				statement.setDate(2, Date.valueOf(item.getItemDetails().getIssueDate()));
				statement.setDate(3, Date.valueOf(item.getItemDetails().getExpireDate()));
				statement.setInt(4, item.getId());
				int result = statement.executeUpdate();
				if (result <= 0) {
					return false;
				}
			}
			String query = "update hr.item set name = ? ,price = ? ,total_price = ? where id = ? ";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, item.getName());
			statement.setDouble(2, item.getPrice());
			statement.setDouble(3, item.getTotalPrice());
			statement.setInt(4, item.getId());
			int result = statement.executeUpdate();
			if (result > 0) {
				return true;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return false;
	}

	@Override
	public boolean addDetailsItem(ItemDetails itemDetails) throws Exception {
		try {
			Connection connection = dataSource.getConnection();
			String query = "insert into hr.ITEM_DETAILS (describtion,issue_date,expiry_data,item_id) values(?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, itemDetails.getDescription());
			statement.setDate(2, Date.valueOf(itemDetails.getIssueDate()));
			statement.setDate(3, Date.valueOf(itemDetails.getExpireDate()));
			statement.setInt(4, itemDetails.getItemId());
			int result = statement.executeUpdate();
			return result > 0;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public boolean deleteDetailsItem(int itemId) throws Exception {
		try {
			Connection connection = dataSource.getConnection();
			String query = "DELETE FROM hr.item_details WHERE item_id = ? ";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, itemId);
			int result = statement.executeUpdate();
			return result > 0;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
