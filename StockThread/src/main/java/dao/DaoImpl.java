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
		String selectSql = "select count(*) from information_schema.TABLES where TABLE_NAME = 'stock_current_data'";
		int num = jdbcTemplate.queryForObject(selectSql, int.class);
		if (num == 0) {
			String createSql = "CREATE TABLE `stock_current_data` (`code` varchar(45) NOT NULL DEFAULT '0',`name` varchar(45) DEFAULT NULL,"
					+ "`date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, `changepercent` double DEFAULT NULL COMMENT '涨跌幅',"
					+ "  `trade` double DEFAULT NULL COMMENT '现价',  `open` double DEFAULT NULL COMMENT '开盘价',"
					+ "  `high` double DEFAULT NULL,  `low` double DEFAULT NULL,  `settlement` double DEFAULT NULL COMMENT '昨日收盘价',"
					+ "  `volume` bigint(20) DEFAULT NULL,  `turnoverratio` double DEFAULT NULL COMMENT '换手率',  `amount` bigint(20) DEFAULT NULL COMMENT '成交量',"
					+ "  `per` double DEFAULT NULL COMMENT '市盈率',  `pb` double DEFAULT NULL COMMENT '市净率',"
					+ "  `mktcap` double DEFAULT NULL COMMENT '总市值',  `nmc` double DEFAULT NULL COMMENT '流通市值',  "
					+ "PRIMARY KEY (`code`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			jdbcTemplate.execute(createSql);
			String insertSql = "INSERT INTO `stock_current_data`(`code`,`name`,`date`,`changepercent`,`trade`,`open`,`high`,`low`,"
					+ "`settlement`,`volume`,`turnoverratio`,`amount`,`per`,`pb`,`mktcap`,`nmc`)VALUES"
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, temp.get(i).getCode());
					ps.setString(2, temp.get(i).getName());
					ps.setDate(3, new java.sql.Date(new Date().getTime()));
					ps.setDouble(4, temp.get(i).getChangepercent());
					ps.setDouble(5, temp.get(i).getTrade());
					ps.setDouble(6, temp.get(i).getOpen());
					ps.setDouble(7, temp.get(i).getHigh());
					ps.setDouble(8, temp.get(i).getLow());
					ps.setDouble(9, temp.get(i).getSettlement());
					ps.setLong(10, temp.get(i).getVolume());
					ps.setDouble(11, temp.get(i).getTurnoverratio());
					ps.setLong(12, temp.get(i).getAmount());
					ps.setDouble(13, temp.get(i).getPer());
					ps.setDouble(14, temp.get(i).getPb());
					ps.setDouble(15, temp.get(i).getMktcap());
					ps.setDouble(16, temp.get(i).getNmc());
					
				
				}
				
				@Override
				public int getBatchSize() {
					
					return temp.size();
				}
			});
			System.out.println(DaoImpl.result.size());
			DaoImpl.result.clear();
			
			System.out.println(new Date() + "===========finish===========");
		}else{
			String updateSql = "UPDATE `stock_current_data` set `changepercent`=?,"
					+ "`trade`=?,`open`=?,`high`=?,`low`=?,`settlement`=?,`volume`=?,"
					+ "`turnoverratio`=?,`amount`=?,`per`=?,`pb`=?,`mktcap`=?,`nmc`=?,"
					+ "`date`=? where code =?";
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
					ps.setDate(14, new java.sql.Date(new Date().getTime()));
					ps.setString(15, temp.get(i).getCode());
					
				}
				
				@Override
				public int getBatchSize() {
					return temp.size();
				}
			});
			System.out.println(DaoImpl.result.size());
			DaoImpl.result.clear();
			
			System.out.println(new Date() + "===========finish===========");
		}
		
	}

}
