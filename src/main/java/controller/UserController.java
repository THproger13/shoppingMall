package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.*;


@WebServlet(value= {"/user/login", "/user/logout", "/user/read", "/user/insert", "/user/update","/user/total",
			"/user/list.json", "/user/list", "/user/purchase", "/user/change", "/user/review", "/user/favorite"})
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    UserDAO dao=new UserDAO();
    PurchaseDAO pdao=new PurchaseDAO();
    ReviewDAO rdao=new ReviewDAO();
    GoodsDAO gdao=new GoodsDAO();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		RequestDispatcher dis=request.getRequestDispatcher("/home.jsp");
		switch(request.getServletPath()){
		case "/user/login":
			String target=request.getParameter("target")==null ? "":request.getParameter("target");
			session.setAttribute("target", target);
			request.setAttribute("pageName", "/user/login.jsp");
			dis.forward(request, response);
			break;
		case "/user/logout":
			session.removeAttribute("user");
			session.removeAttribute("target");
			Cookie cookie = new Cookie("uid","");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			response.sendRedirect("/");
			break;
		case "/user/read":
			request.setAttribute("vo", dao.read(request.getParameter("uid")));
			request.setAttribute("pageName", "/user/read.jsp");
			dis.forward(request, response);
			break;
		case "/user/insert":
			request.setAttribute("pageName","/user/insert.jsp");
			dis.forward(request, response);
			break;
		case "/user/list.json": //실행 /user/list.json?page=1&key=uid&query=
			String key=request.getParameter("key");
			String query=request.getParameter("query");
			int page=Integer.parseInt(request.getParameter("page"));
			ArrayList<UserVO> array=dao.list(key, query, page);
			Gson gson=new Gson();
			out.print(gson.toJson(array));
			break;
		case "/user/total":
			key=request.getParameter("key");
			query=request.getParameter("query");
			out.println(dao.total(key, query));
			break;
		case "/user/list":
			request.setAttribute("pageName", "/user/list.jsp");
			dis.forward(request, response);
			break;
		case "/user/purchase":
			request.setAttribute("array", pdao.list(request.getParameter("uid")));
			request.setAttribute("pageName", "/user/purchase.jsp");
			dis.forward(request, response);
			break;
		case "/user/update":
			request.setAttribute("vo", dao.read(request.getParameter("uid")));
			request.setAttribute("pageName", "/user/update.jsp");
			dis.forward(request, response);
			break;
		case  "/user/review":
			request.setAttribute("array", rdao.userList(request.getParameter("uid")));
			request.setAttribute("pageName", "/user/review.jsp");
			dis.forward(request, response);
			break;
		case "/user/favorite":
			request.setAttribute("array", gdao.list(request.getParameter("uid")));
			request.setAttribute("pageName", "/user/favorite.jsp");
			dis.forward(request, response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		
		//폴더생성
		String path="/upload/photo/";
		File mdPath=new File("c:" + path);
		if(!mdPath.exists()) mdPath.mkdir();
		
		switch(request.getServletPath()) {
		case "/user/login":
			String uid=request.getParameter("uid");
			String upass=request.getParameter("upass");
			boolean is_check=Boolean.parseBoolean(request.getParameter("is_check"));
			UserVO user=dao.read(uid);
			
			int result=0; //아이디가 없는경우
			if(user.getUid()!=null) {
				if(user.getUpass().equals(upass)) {
					result=1; //성공
					session.setAttribute("user", user);
					if(is_check) {
						 Cookie cookie = new Cookie("uid", user.getUid());
						 cookie.setPath("/");
						 cookie.setMaxAge(60*60*24*7);
						 response.addCookie(cookie);
					}
				}else {
					result=2; //비밀번호 불일치
				}
			}
			out.println(result);
			break;
		case  "/user/insert":
			//사진저장
			MultipartRequest multi=new MultipartRequest(
					request, "c:"+path, 1024*1024*10,"UTF-8", new DefaultFileRenamePolicy());
			String photo = multi.getFilesystemName("photo")==null ? "":
					path + multi.getFilesystemName("photo");
			
			//데이터저장
			UserVO vo=new UserVO();
			vo.setUid(multi.getParameter("uid"));
			vo.setUpass(multi.getParameter("upass"));
			vo.setUname(multi.getParameter("uname"));
			vo.setPhone(multi.getParameter("phone"));
			vo.setAddress1(multi.getParameter("address1"));
			vo.setAddress2(multi.getParameter("address2"));
			vo.setPhoto(photo);
			dao.insert(vo);
			response.sendRedirect("/user/login");
			break;
		case "/user/update":
			//사진저장
			multi=new MultipartRequest(
					request, "c:"+path, 1024*1024*10,"UTF-8", new DefaultFileRenamePolicy());
			photo = multi.getFilesystemName("photo")==null ? multi.getParameter("old_photo"):
					path + multi.getFilesystemName("photo");
			vo=new UserVO();
			vo.setUid(multi.getParameter("uid"));
			vo.setUname(multi.getParameter("uname"));
			vo.setPhone(multi.getParameter("phone"));
			vo.setAddress1(multi.getParameter("address1"));
			vo.setAddress2(multi.getParameter("address2"));
			vo.setPhoto(photo);
			dao.update(vo);
			response.sendRedirect("/user/read?uid=" + vo.getUid());
			break;
		case "/user/change":
			uid=request.getParameter("uid");
			upass=request.getParameter("upass");
			dao.update(uid, upass);
			break;
		}
		
	}
}






