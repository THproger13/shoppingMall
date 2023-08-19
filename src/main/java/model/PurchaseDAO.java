package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class PurchaseDAO {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//구매상태변경
	public void update(String pid, int status) {
		try {
			String sql="update purchase set status=? where pid=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setString(2, pid);
			ps.execute();
		}catch(Exception e) {
			System.out.println("구매상태변경:" + e.toString());
		}
	}
	
	//구매한 상품목록
	public ArrayList<OrderVO> orderList(String pid){
		ArrayList<OrderVO> array=new ArrayList<OrderVO>();
		try {
			String sql="select * from view_orders where pid=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, pid);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				OrderVO vo=new OrderVO();
				vo.setPid(rs.getString("pid"));
				vo.setGid(rs.getString("gid"));
				vo.setPrice(rs.getInt("price"));
				vo.setQnt(rs.getInt("qnt"));
				vo.setTitle(rs.getString("title"));
				vo.setImage(rs.getString("image"));
				array.add(vo);
			}
		}catch(Exception e) {
			System.out.println("구매한 상품목록:" + e.toString());
		}
		return array;
	}
	
	//구매정보읽기
	public PurchaseVO read(String pid) {
		PurchaseVO vo=new PurchaseVO();
		try {
			String sql="select * from view_purchase where pid=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, pid);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				vo.setPid(rs.getString("pid"));
				vo.setUid(rs.getString("uid"));
				vo.setUname(rs.getString("uname"));
				vo.setRecName(rs.getString("recName"));
				vo.setRecAddress1(rs.getString("recAddress1"));
				vo.setRecAddress2(rs.getString("recAddress2"));
				vo.setRecPhone(rs.getString("recPhone"));
				vo.setPurDate(sdf.format(rs.getTimestamp("purDate")));
				vo.setStatus(rs.getInt("status"));
				vo.setSum(rs.getInt("sum"));
			}
		}catch(Exception e) {
			System.out.println("구매정보:" + e.toString());
		}
		return vo;
	}
	
	//검색수
	public int total(String key, String query) {
		int total=0;
		try {
			String sql="select count(*) from view_purchase where " + key + " like ?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, "%" + query + "%");
			ResultSet rs=ps.executeQuery();
			if(rs.next()) total=rs.getInt("count(*)");
		}catch(Exception e) {
			System.out.println("구매검색수:" + e.toString());
		}
		return total;
	}
	
	//구매목록
	public ArrayList<PurchaseVO> list(String key, String query, int page){
		ArrayList<PurchaseVO> array=new ArrayList<PurchaseVO>();
		try {
			String sql="select * from view_purchase where " + key + " like ? order by purdate desc limit ?, 5";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, "%" + query + "%");
			ps.setInt(2, (page-1)* 5);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				PurchaseVO vo=new PurchaseVO();
				vo.setPid(rs.getString("pid"));
				vo.setUid(rs.getString("uid"));
				vo.setRecName(rs.getString("recName"));
				vo.setRecAddress1(rs.getString("recAddress1"));
				vo.setRecAddress2(rs.getString("recAddress2"));
				vo.setRecPhone(rs.getString("recPhone"));
				vo.setPurDate(sdf.format(rs.getTimestamp("purDate")));
				vo.setStatus(rs.getInt("status"));
				vo.setSum(rs.getInt("sum"));
				array.add(vo);
			}
		}catch(Exception e) {
			System.out.println("구매목록:" + e.toString());
		}
		return array;
	}
	
	//특정 회원의 구매목록
	public ArrayList<PurchaseVO> list(String uid){
		ArrayList<PurchaseVO> array=new ArrayList<PurchaseVO>();
		try {
			String sql="select * from view_purchase where uid=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, uid);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				PurchaseVO vo=new PurchaseVO();
				vo.setPid(rs.getString("pid"));
				vo.setUid(rs.getString("uid"));
				vo.setRecName(rs.getString("recName"));
				vo.setRecAddress1(rs.getString("recAddress1"));
				vo.setRecAddress2(rs.getString("recAddress2"));
				vo.setRecPhone(rs.getString("recPhone"));
				vo.setPurDate(sdf.format(rs.getTimestamp("purDate")));
				vo.setStatus(rs.getInt("status"));
				vo.setSum(rs.getInt("sum"));
				array.add(vo);
			}
		}catch(Exception e) {
			System.out.println("특정 회원의 구매목록:" + e.toString());
		}
		return array;
	}
		
	//주문상품등록
	public void insert(OrderVO vo) {
		try {
			String sql="insert into orders(pid,gid,price,qnt) values(?,?,?,?)";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, vo.getPid());
			ps.setString(2, vo.getGid());
			ps.setInt(3, vo.getPrice());
			ps.setInt(4, vo.getQnt());
			ps.execute();
		}catch(Exception e) {
			System.out.println("주문상품등록:" + e.toString());
		}
	}
	
	//구매(주문)등록
	public void insert(PurchaseVO vo) {
		try {
			String sql="insert into purchase(pid,uid,recName,recAddress1,recAddress2,recPhone,sum) values(?,?,?,?,?,?,?)";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, vo.getPid());
			ps.setString(2, vo.getUid());
			ps.setString(3, vo.getRecName());
			ps.setString(4, vo.getRecAddress1());
			ps.setString(5, vo.getRecAddress2());
			ps.setString(6, vo.getRecPhone());
			ps.setInt(7, vo.getSum());
			ps.execute();
		}catch(Exception e) {
			System.out.println("구매등록:" + e.toString());
		}
	}
}
