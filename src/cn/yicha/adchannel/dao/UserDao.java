package cn.yicha.adchannel.dao;

import java.util.List;

import cn.yicha.adchannel.model.User;
/**
 * 
 * @author zhangcc
 *
 */
public class UserDao {

	private static final String TABLE_NAME = "user"; 

	private static final UserDao instance = null;

	private UserDao() {
	}

	public static UserDao getInstance() {
		if (instance == null) {
			return new UserDao();
		}
		return instance;
	}
	
	/**
	 * 根据用户名，密码查找用户
	 * @param userName
	 * @param password
	 * @return
	 */
	public User loginValidate(String userName, String password){
		return User.dao.findFirst("select * from " + TABLE_NAME + " where name=? and pwd=?", userName, password);
	}
	
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<User> getAllUsers(){
		return User.dao.find("select * from user order by role desc");
	}
	
	/**
	 * 根据id查找用户
	 * @param id
	 * @return
	 */
	public User getUserById(int id) {
		return User.dao.findById(id);
	}
	
	/**
	 * 根据id删除用户
	 * @param id
	 * @return
	 */
	public boolean delUser(int id) {
		return User.dao.deleteById(id);
	}
}
