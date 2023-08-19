package model;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import java.sql.*;

public class GraphDAO {
	//년도별 월별 매출현황
	public ArrayList<JSONObject> procedure1(String yymm){
		ArrayList<JSONObject> array=new ArrayList<JSONObject>();
		try {
			String sql="call procedure1(?)";
			CallableStatement cs=Database.CON.prepareCall(sql);
			cs.setString(1, yymm);
			cs.execute();
			ResultSet rs=cs.getResultSet();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("id", rs.getInt("id"));
				obj.put("yymm", rs.getString("puryymm"));
				obj.put("sum", rs.getInt("pursum"));
				array.add(obj);
			}
		}catch(Exception e) {
			System.out.println("년도별 월별 매출현황:" + e.toString());
		}
		return array;
		
	}
	
	//사용자별 좋아요, 댓글, 구매횟수
	public ArrayList<JSONObject> procedure2(){
		ArrayList<JSONObject> array=new ArrayList<JSONObject>();
		try {
			String sql="call procedure2()";
			CallableStatement cs=Database.CON.prepareCall(sql);
			cs.execute();
			ResultSet rs=cs.getResultSet();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("uid", rs.getString("uid"));
				obj.put("uname", rs.getString("uname"));
				obj.put("fcnt", rs.getInt("fcnt"));
				obj.put("rcnt", rs.getInt("rcnt"));
				obj.put("pcnt", rs.getInt("pcnt"));
				array.add(obj);
			}
		}catch(Exception e) {
			System.out.println("사용자별 좋아요, 댓글, 구매횟수:" + e.toString());
		}
		return array;
		
	}
	
	//제조사별 상품수
	public ArrayList<JSONObject> procedure3(){
		ArrayList<JSONObject> array=new ArrayList<JSONObject>();
		try {
			String sql="call procedure3()";
			CallableStatement cs=Database.CON.prepareCall(sql);
			cs.execute();
			ResultSet rs=cs.getResultSet();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("maker", rs.getString("maker"));
				obj.put("count", rs.getInt("count(*)"));
				array.add(obj);
			}
		}catch(Exception e) {
			System.out.println("제조사별상품수:" + e.toString());
		}
		return array;
		
	}
}
