package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.GoodsDAO;
import model.GoodsVO;
import model.NaverAPI;


@WebServlet(value={"/goods/search","/goods/search.json","/goods/append","/goods/list.json",
		"/goods/total", "/goods/list", "/goods/delete", "/goods/insert", "/goods/update",
		"/goods/read", "/goods/ckupload"})
public class GoodsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    GoodsDAO gdao=new GoodsDAO(); 
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		RequestDispatcher dis=request.getRequestDispatcher("/home.jsp");
		
		switch(request.getServletPath()) {
		case "/goods/search":
			request.setAttribute("pageName", "/goods/search.jsp");
			dis.forward(request, response);
			break;
		case "/goods/search.json": //실행: /goods/search.json?page=1&query=한빛미디어
			int page=Integer.parseInt(request.getParameter("page"));
			String query=request.getParameter("query");
			String result=NaverAPI.search(page, query);
			out.println(result);
			break;
		case "/goods/list.json": //실행: /goods/list.json?page=1&query= 
			page=Integer.parseInt(request.getParameter("page"));
			query=request.getParameter("query");
			String uid=request.getParameter("uid");
			Gson gson=new Gson();
			out.print(gson.toJson(gdao.list(query, page, uid)));
			break;
		case "/goods/total":
			query=request.getParameter("query");
			out.print(gdao.total(query));
			break;
		case "/goods/list":
			request.setAttribute("pageName", "/goods/list.jsp");
			dis.forward(request, response);
			break;
		case "/goods/insert":
			request.setAttribute("pageName", "/goods/insert.jsp");
			dis.forward(request, response);
			break;
		case "/goods/update":
			request.setAttribute("vo", gdao.read(request.getParameter("gid")));
			request.setAttribute("pageName", "/goods/update.jsp");
			dis.forward(request, response);
			break;
		case "/goods/read":
			request.setAttribute("vo", gdao.read(request.getParameter("gid")));
			request.setAttribute("pageName", "/goods/read.jsp");
			dis.forward(request, response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path="/upload/goods/";
		File dirPath=new File("c:" + path);
		if(!dirPath.exists()) dirPath.mkdir();
		
		switch(request.getServletPath()) {
		case "/goods/append":
			try {
				//이미지저장
				UUID uuid=UUID.randomUUID();
				String gid=uuid.toString().substring(0,8);
				URL url=new URL(request.getParameter("image"));
				InputStream is=url.openStream();
				String fileName = gid + ".jpg";
				FileOutputStream fos=new FileOutputStream("c:/" + path + fileName);
				int data=0;
				while((data=is.read()) != -1) {
					fos.write(data);
				}
				//데이터저장
				GoodsVO vo=new GoodsVO();
				vo.setGid(gid);
				vo.setTitle(request.getParameter("title"));
				vo.setMaker(request.getParameter("maker"));
				vo.setPrice(Integer.parseInt(request.getParameter("lprice")));
				vo.setImage(path + fileName);
				//System.out.println(vo.toString());
				gdao.insert(vo);
			}catch(Exception e) {
				System.out.println("상품이미지저장:" + e.toString());
			}
			break;
		case "/goods/delete":
			try {
				String gid=request.getParameter("gid");
				String image=request.getParameter("image");
				File file=new File("c:/" + image);
				file.delete(); //이미지파일삭제
				gdao.delete(gid); //데이터삭제
			}catch(Exception e) {
				System.out.println("상품삭제:" + e.toString());
			}
			break;
		case "/goods/insert":
			MultipartRequest multi=new MultipartRequest(
					request, "c:"+path, 1024*1024*10,"UTF-8", new DefaultFileRenamePolicy());
			String image=path + multi.getFilesystemName("image");
			
			GoodsVO vo=new GoodsVO();
			UUID uuid=UUID.randomUUID();
			String gid=uuid.toString().substring(0,8);
			vo.setGid(gid);
			vo.setImage(image);
			vo.setTitle(multi.getParameter("title"));
			vo.setMaker(multi.getParameter("maker"));
			vo.setPrice(Integer.parseInt(multi.getParameter("price")));
			//System.out.println(vo.toString());
			gdao.insert(vo);
			response.sendRedirect("/goods/list");
			break;
		case "/goods/update":
			multi=new MultipartRequest(
					request, "c:"+path, 1024*1024*10,"UTF-8", new DefaultFileRenamePolicy());
			image=multi.getFilesystemName("image")==null?
					multi.getParameter("oldImage") : path + multi.getFilesystemName("image");
			vo=new GoodsVO();
			vo.setGid(multi.getParameter("gid"));
			vo.setImage(image);
			vo.setTitle(multi.getParameter("title"));
			vo.setMaker(multi.getParameter("maker"));
			vo.setPrice(Integer.parseInt(multi.getParameter("price")));
			vo.setContent(multi.getParameter("content"));
			System.out.println(vo.toString());
			gdao.update(vo);
			response.sendRedirect("/goods/list");
			break;
		case "/goods/ckupload":
			try {
				gid=request.getParameter("gid");
				String pathCK ="/upload/goods/ck/" + gid + "/";
				File dirPathCK = new File("c:" + pathCK);
				if(!dirPathCK.exists()) dirPathCK.mkdir();
				
	            multi = new MultipartRequest(request,"c:" + pathCK, 1024 * 1024 * 10,"UTF-8",new DefaultFileRenamePolicy());
	            String fileName=multi.getFilesystemName("upload");
	            String fileUrl=pathCK + fileName;
	
	            JSONObject json = new JSONObject();
	            json.put("uploaded", 1);
	            json.put("fileName", fileName);
	            json.put("url", fileUrl);
	            PrintWriter out=response.getWriter();
	            out.println(json);
			}catch(Exception e) {
				System.out.println("ck 업로드:" + e.toString());
			}
			break;
		}
	}

}






