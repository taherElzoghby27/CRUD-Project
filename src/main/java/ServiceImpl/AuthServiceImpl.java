package ServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import Services.AuthService;
import models.User;

public class AuthServiceImpl implements AuthService {
	private DataSource dataSource;

	public AuthServiceImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public boolean signUp(User user) throws Exception {
		try {
			Connection connection = dataSource.getConnection();
			String query = "INSERT INTO hr.USERS_ITEMS (name,email,password) VALUES (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			int result = statement.executeUpdate();
			if (result > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public boolean signIn(User user) throws Exception {
		try {
			Connection connection = dataSource.getConnection();
			String query = "SELECT * FROM hr.users_items WHERE email LIKE ? AND password LIKE ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public User getUser(String email) throws Exception {
		try {
			Connection connection = dataSource.getConnection();
			String query = "SELECT * FROM hr.users_items WHERE email LIKE ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				User user = new User(result.getInt("id"), result.getString("name"), result.getString("email"));
				return user;
			}
			return null;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
