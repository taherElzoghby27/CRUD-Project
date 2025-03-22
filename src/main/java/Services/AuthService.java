package Services;

import models.User;

public interface AuthService {
	public boolean signUp(User user) throws Exception;

	public boolean signIn(User user) throws Exception;

	public User getUser(String email) throws Exception;
}
