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
	public List<Item> loadItems() {
		List<Item> items = new ArrayList<>();
		try {
			Connection connection = dataSource.getConnection();
			String query = "select i.id as id,i.name,i.price,i.total_price,id.id as item_details_id,id.item_id,id.describtion,id.issue_date,id.expiry_data from hr.item i LEFT join hr.item_details id on i.ID =id.ITEM_ID";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				ItemDetails itemDetails = new ItemDetails(resultSet.getInt("item_details_id"),
						resultSet.getString("describtion"), resultSet.getString("issue_date"),
						resultSet.getString("expiry_data"), resultSet.getInt("item_id"));
				Item item = new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("price"),
						resultSet.getDouble("total_price"), itemDetails);
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
	public boolean addItem(Item item) throws Exception {
		try {
			Connection connection = dataSource.getConnection();
			String query = "insert into hr.item values(?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, item.getId());
			statement.setString(2, item.getName());
			statement.setDouble(3, item.getPrice());
			statement.setDouble(4, item.getTotalPrice());
			return statement.execute();
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
			return statement.execute();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public boolean updateItem(Item item) throws Exception {
		try {
			Connection connection = dataSource.getConnection();
			if (item.getItemDetails()!=null&&item.getItemDetails().getDescription() != null) {
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
			String query = "insert into hr.ITEM_DETAILS values(?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, itemDetails.getId());
			statement.setString(2, itemDetails.getDescription());
			statement.setDate(3, Date.valueOf(itemDetails.getIssueDate()));
			statement.setDate(4, Date.valueOf(itemDetails.getExpireDate()));
			statement.setInt(5, itemDetails.getItemId());
			return statement.execute();
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
			return statement.execute();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
