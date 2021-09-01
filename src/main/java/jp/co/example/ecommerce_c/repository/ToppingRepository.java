package jp.co.example.ecommerce_c.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_c.domain.Topping;

/**
 * toppingsテーブルからトッピング情報を取得するリポジトリ.
 *
 * @author fukushima
 *
 */
@Repository
public class ToppingRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Topping> TOPPING_ROW_MAPPER
		= (rs, i) -> {
			Topping topping = new Topping();
			topping.setId(rs.getInt("id"));
			topping.setName(rs.getString("name"));
			topping.setPriceM(rs.getInt("price_m"));
			topping.setPriceL(rs.getInt("price_l"));
			return topping;
		};

	/**
	 * トッピング一覧情報をID順で取得します.
	 *
	 * @return 全トッピングリスト
	 */
	public List<Topping> findAll() {
		String sql = "SELECT id, name, price_m, price_l FROM toppings ORDER BY id;";
		List<Topping> toppingList = template.query(sql, TOPPING_ROW_MAPPER);
		return toppingList;
	}
}
