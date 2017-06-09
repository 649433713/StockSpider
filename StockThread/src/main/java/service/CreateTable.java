package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PO.StockCurrentData;
import dao.DaoImpl;
import net.sf.json.JSONArray;

@Service
public class CreateTable {

	
	@Autowired
	private DaoImpl daoImpl;
	private String hs_a_url = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?num=100&node=hs_a&page=";
	private String hs_b_url = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?num=100&node=hs_b";
	private String shfxjs_url = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?num=100&node=shfxjs";
	
	private String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36 OPR/45.0.2552.812";
	
	public void create() {
		Document document = null;
		ArrayList<StockCurrentData> result = new ArrayList<>();
		try {
			for (int i = 1; i <33 ; i++) {
				document = Jsoup.connect(hs_a_url+i).header("User-Agent",userAgent).timeout(5000).get();
				String jsonstr = document.body().text();
				JSONArray jsonArray = JSONArray.fromObject(jsonstr);
				Collection<StockCurrentData> temp = JSONArray.toCollection(jsonArray, StockCurrentData.class);
				result.addAll(temp);
			}
			//updateByJDBC(result);
			//updateByHibernate(result);
			document = Jsoup.connect(hs_b_url).header("User-Agent",userAgent).timeout(5000).get();
			String jsonstr = document.body().text();
			JSONArray jsonArray = JSONArray.fromObject(jsonstr);
			Collection<StockCurrentData> temp = JSONArray.toCollection(jsonArray, StockCurrentData.class);
			result.addAll(temp);
			document = Jsoup.connect(shfxjs_url).header("User-Agent",userAgent).timeout(5000).get();
			jsonstr = document.body().text();
			jsonArray = JSONArray.fromObject(jsonstr);
			temp = JSONArray.toCollection(jsonArray, StockCurrentData.class);
			result.addAll(temp);
			
			daoImpl.creatByJDBC(result);
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			create();
		}
	}
}
