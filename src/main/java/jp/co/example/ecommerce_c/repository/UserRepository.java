package jp.co.example.ecommerce_c.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_c.domain.User;

/**
 * userテーブルを操作するリポジトリ.
 * 
 * @author yuyayokoyama
 *
 */

@Repository
public class UserRepository {
	/**
	 * userオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {

		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));

		return user;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	/**
	 * メールアドレスとパスワードから利用者情報を取得します.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return 利用者情報 存在しない場合はnullを返します
	 */
	public User findByMailAddressAndPassword(String email, String password) {
		String sql = "SELECT id,name, email, password, zipcode, address, telephone FROM users where email=:email and password=:password";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password",password);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	

	/**
	 * 新規ユーザーをテーブルに挿入する
	 * 
	 * @param 登録するユーザー情報
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("INSERT INTO users(name,email,password,zipcode,address,telephone)");
		insertSql.append(" VALUES(:name,:email,:password,:zipcode,:address,:telephone);");
		template.update(insertSql.toString(), param);
	}
	
	/**
	 * メールアドレスからユーザー情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報 存在しない場合はnullを返します
	 */
	public User findByMailAddress(String email) {
		String sql = "select id,name,email,password,zipcode,address,telephone from users where email=:email;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

}
