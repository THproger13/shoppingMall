package model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GoodsDAO {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //ctrl + shift + o
	//상품정보 수정
	public void update(GoodsVO vo) {
		try {
			String sql="update goods set title=?,price=?,maker=?,image=?, content=? where gid=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setInt(2, vo.getPrice());
			ps.setString(3, vo.getMaker());
			ps.setString(4, vo.getImage());
			ps.setString(5, vo.getContent());
			ps.setString(6, vo.getGid());
			ps.execute();
		}catch(Exception e) {
			System.out.println("상품수정:" + e.toString());
		}
	}
	
	//상품정보 읽기
	public GoodsVO read(String gid) {
		GoodsVO vo=new GoodsVO();
		try {
			String sql="select * from goods where gid=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, gid);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				vo.setGid(rs.getString("gid"));
				vo.setTitle(rs.getString("title"));
				vo.setImage(rs.getString("image"));
				vo.setPrice(rs.getInt("price"));
				vo.setMaker(rs.getString("maker"));
				vo.setRegDate(sdf.format(rs.getTimestamp("regDate")));
				vo.setContent(rs.getString("content"));
			}
		}catch(Exception e) {
			System.out.println("상품정보:" + e.toString());
		}
		return vo;
	}
	
	//상품삭제
	public void delete(String gid) {
		try {
			String sql="delete from goods where gid=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, gid);
			ps.execute();
		}catch(Exception e) {
			System.out.println("상품삭제:" + e.toString());
		}
	}
	
	//상품검색수
	public int total(String query) {
		int total=0;
		try {
			String sql="select count(*) cnt from goods where title like ?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, "%" + query + "%");
			ResultSet rs=ps.executeQuery();
			if(rs.next()) total=rs.getInt("cnt");
		}catch(Exception e) {
			System.out.println("검색수:" + e.toString());
		}
		return total;
	}
	
	//상품목록
	public ArrayList<GoodsVO> list(String query, int page, String uid){
		ArrayList<GoodsVO> array=new ArrayList<GoodsVO>();
		try {
			String sql="select *, (select count(*) from favorite where uid=? and gid=g.gid) ucnt";
			sql+=" from view_goods g where title like ? order by regDate desc limit ?,6";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, uid);
			ps.setString(2, "%" + query + "%");
			ps.setInt(3, (page-1)*6);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				GoodsVO vo=new GoodsVO();
				vo.setGid(rs.getString("gid"));
				vo.setTitle(rs.getString("title"));
				vo.setImage(rs.getString("image"));
				vo.setPrice(rs.getInt("price"));
				vo.setMaker(rs.getString("maker"));
				vo.setRegDate(sdf.format(rs.getTimestamp("regDate")));
				vo.setFcnt(rs.getInt("fcnt"));
				vo.setRcnt(rs.getInt("rcnt"));
				vo.setUcnt(rs.getInt("ucnt"));
				array.add(vo);
			}
		}catch(Exception e) {
			System.out.println("상품목록:" + e.toString());
		}
		return array;
	}
	
	//특정회원의 좋아요 상품목록
	public ArrayList<GoodsVO> list(String uid){
		ArrayList<GoodsVO> array=new ArrayList<GoodsVO>();
		try {
			String sql="select g.*, f.uid from goods g, favorite f where g.gid=f.gid and f.uid=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, uid);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				GoodsVO vo=new GoodsVO();
				vo.setGid(rs.getString("gid"));
				vo.setTitle(rs.getString("title"));
				vo.setImage(rs.getString("image"));
				vo.setPrice(rs.getInt("price"));
				vo.setMaker(rs.getString("maker"));
				vo.setRegDate(sdf.format(rs.getTimestamp("regDate")));
				array.add(vo);
			}
		}catch(Exception e) {
			System.out.println("특정회원의 좋아요 상품목록:" + e.toString());
		}
		return array;
	}
	
	//상품등록
	public void insert(GoodsVO vo) {
		try {
			String sql="insert into goods(gid,title,price,maker,image) values(?,?,?,?,?)";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, vo.getGid());
			ps.setString(2, vo.getTitle());
			ps.setInt(3, vo.getPrice());
			ps.setString(4, vo.getMaker());
			ps.setString(5, vo.getImage());
			ps.execute();
		}catch(Exception e) {
			System.out.println("상품등록:" + e.toString());
		}
	}
}
