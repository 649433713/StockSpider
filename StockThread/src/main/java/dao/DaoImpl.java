package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import PO.StockCurrentData;

/**
 * @author 凡
 * 数据层
 */
@Component
//@Scope("prototype")
public class DaoImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static List<StockCurrentData> result;
	
	@Transactional("JDBC")
	public void updateByJDBC() {
		List<StockCurrentData> temp = DaoImpl.result;
		String updateSql = "UPDATE `stock_current_data` set `changepercent`=?,"
				+ "`trade`=?,`open`=?,`high`=?,`low`=?,`settlement`=?,`volume`=?,"
				+ "`turnoverratio`=?,`amount`=?,`per`=?,`pb`=?,`mktcap`=?,`nmc`=?"
				+ " where code =?";
		jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {

				ps.setDouble(1, temp.get(i).getChangepercent());
				ps.setDouble(2, temp.get(i).getTrade());
				ps.setDouble(3, temp.get(i).getOpen());
				ps.setDouble(4, temp.get(i).getHigh());
				ps.setDouble(5, temp.get(i).getLow());
				ps.setDouble(6, temp.get(i).getSettlement());
				ps.setLong(7, temp.get(i).getVolume());
				ps.setDouble(8, temp.get(i).getTurnoverratio());
				ps.setLong(9, temp.get(i).getAmount());
				ps.setDouble(10, temp.get(i).getPer());
				ps.setDouble(11, temp.get(i).getPb());
				ps.setDouble(12, temp.get(i).getMktcap());
				ps.setDouble(13, temp.get(i).getNmc());
				ps.setString(14, temp.get(i).getCode());
				
			}
			
			@Override
			public int getBatchSize() {
				return temp.size();
			}
		});
		
		DaoImpl.result.clear();
		System.out.println(new Date() + "=========finish===========");
	}

}
