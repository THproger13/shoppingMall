package model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserDAO {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//회원비밀번호 변경
	public void update(String uid, String upass) {
		try {
			String sql="update users set upass=? where uid=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, upass);
			ps.setString(2, uid);;
			ps.execute();
		}catch(Exception e) {
			System.out.println("회원비밀번호변경:" + e.toString());
		}
	}
	
	//회원목록
	public ArrayList<UserVO> list(String key, String query, int page){
		ArrayList<UserVO> array=new ArrayList<UserVO>();
		try {
			String sql="select *,";
			sql+="(select count(*) from purchase where uid=u.uid) ucnt,";
			sql+="(select count(*) from reviews where uid=u.uid) rcnt,";
			sql+="(select count(*) from favorite where uid=u.uid) fcnt ";
			sql+="from users u where " + key + " like ? order by regDate desc limit ?,6";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, "%" + query + "%");
			ps.setInt(2, (page-1)*6);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				UserVO vo=new UserVO();
				vo.setUid(rs.getString("uid"));
				vo.setUpass(rs.getString("upass"));
				vo.setUname(rs.getString("uname"));
				vo.setPhone(rs.getString("phone"));
				vo.setAddress1(rs.getString("address1"));
				vo.setAddress2(rs.getString("address2"));
				vo.setRegDate(sdf.format(rs.getTimestamp("regDate")));
				vo.setPhoto(rs.getString("photo"));
				vo.setUcnt(rs.getInt("ucnt"));
				vo.setFcnt(rs.getInt("fcnt"));
				vo.setRcnt(rs.getInt("rcnt"));
				array.add(vo);
			}
		}catch(Exception e) {
			System.out.println("회원목록:" + e.toString());
		}
		return array;
	}
	
	//검색수
	public int total(String key, String query) {
		int total=0;
		try {
			String sql="select count(*) from users where " + key + " like ?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, "%" + query + "%");
			ResultSet rs=ps.executeQuery();
			if(rs.next()) total=rs.getInt("count(*)");
		}catch(Exception e) {
			System.out.println("회원 검색수:" + e.toString());
		}
		return total;
	}
	
	//회원수정
	public void update(UserVO vo) {
		try {
			String sql="update users set uname=?,phone=?,address1=?,address2=?,photo=? where uid=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(6, vo.getUid());
			ps.setString(1, vo.getUname());
			ps.setString(2, vo.getPhone());
			ps.setString(3, vo.getAddress1());
			ps.setString(4, vo.getAddress2());
			ps.setString(5, vo.getPhoto());
			ps.execute();
		}catch(Exception e) {
			System.out.println("회원수정:" + e.toString());
		}
	}
		
	//회원등록
	public void insert(UserVO vo) {
		try {
			String sql="insert into users(uid,upass,uname,phone,address1,address2,photo) values(?,?,?,?,?,?,?)";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, vo.getUid());
			ps.setString(2, vo.getUpass());
			ps.setString(3, vo.getUname());
			ps.setString(4, vo.getPhone());
			ps.setString(5, vo.getAddress1());
			ps.setString(6, vo.getAddress2());
			ps.setString(7, vo.getPhoto());
			ps.execute();
		}catch(Exception e) {
			System.out.println("회원등록:" + e.toString());
		}
	}
	//회원정보 읽기
	public UserVO read(String uid) {
		UserVO vo=new UserVO();
		try {
			String sql="select *,";
			sql+="(select count(*) from purchase where uid=u.uid) ucnt,";
			sql+="(select count(*) from reviews where uid=u.uid) rcnt,";
			sql+="(select count(*) from favorite where uid=u.uid) fcnt ";
			sql+="from users u where uid=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, uid);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				vo.setUid(rs.getString("uid"));
				vo.setUpass(rs.getString("upass"));
				vo.setUname(rs.getString("uname"));
				vo.setPhone(rs.getString("phone"));
				vo.setAddress1(rs.getString("address1"));
				vo.setAddress2(rs.getString("address2"));
				vo.setRegDate(sdf.format(rs.getTimestamp("regDate")));
				vo.setPhoto(rs.getString("photo"));
				vo.setRole(rs.getInt("role"));
				vo.setUcnt(rs.getInt("ucnt"));
				vo.setFcnt(rs.getInt("fcnt"));
				vo.setRcnt(rs.getInt("rcnt"));
			}
		}catch(Exception e) {
			System.out.println("회원정보읽기:" + e.toString());
		}
		return vo;
	}
}
