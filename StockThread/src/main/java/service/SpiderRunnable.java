package service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import PO.StockCurrentData;
import dao.DaoImpl;
import net.sf.json.JSONArray;
/**
 * @author 凡
 * 解析界面
 */
@Service
@Scope("prototype")
public class SpiderRunnable extends TimerTask{

	@Autowired
	private DaoImpl daoImpl;
	private String url ;
	private String userAgent ;
	private int i;

	public SpiderRunnable(int i) {
		this.i = i;
		switch (i) {
		case 33:
			url = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?num=100&node=hs_b";
			break;
		case 34:
			url = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?num=100&node=shfxjs";
			break;
		default:
			url = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?num=100&node=hs_a&page="+i;
			break;
		}
			userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36 OPR/45.0.2552.812";

	}
	@Override
	public void run() {
		Calendar calendar = Calendar.getInstance();
		Calendar start = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, 9);
		start.set(Calendar.MINUTE,15);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 15);
		end.set(Calendar.MINUTE,0);
		
		if (calendar.before(start)||calendar.after(end)) {
			//return;
		}
		System.out.println(new Date()+"===========thread "+ i +" start===========");

		Document document = null;
	
		try {
			document = Jsoup.connect(url).header("User-Agent",userAgent).timeout(5000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			run();
		}

		if (document==null||document.body()==null) {
			run();
		}
		
		String jsonstr = document.body().text();
		JSONArray jsonArray = JSONArray.fromObject(jsonstr);
		Collection<StockCurrentData> temp = JSONArray.toCollection(jsonArray, StockCurrentData.class);
		if (DaoImpl.result==null) {
			DaoImpl.result = new ArrayList<>();
		}
		DaoImpl.result.addAll(temp);
		if (DaoImpl.result.size()>3300) {
			daoImpl.updateByJDBC();
		}
	
		//daoImpl.updateByJDBC((List<StockCurrentData>)temp);
	
	}

		
}
