package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import model.AttachDAO;
import model.AttachVO;

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024, //1MB
	maxFileSize = 1024 * 1024 * 10,
	maxRequestSize = 1024*1024 * 15)

@WebServlet(value= {"/attach/insert", "/attach/list.json", "/attach/delete"})
public class AttachController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AttachDAO dao=new AttachDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		switch(request.getServletPath()) {
		case "/attach/list.json":
			String gid=request.getParameter("gid");
			Gson gson=new Gson();
			out.println(gson.toJson(dao.list(gid)));
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		switch(request.getServletPath()) {
		case "/attach/insert":
			String gid=request.getParameter("gid");
			String path="/upload/goods/attach/" + gid + "/";
			File dirPath = new File("c:/" + path);
	        if(!dirPath.exists()) dirPath.mkdir();
	        
	        Collection<Part> parts = request.getParts();
	        for(Part part:parts) {
	        	if(!part.getName().equals("files")) continue;
	        	String name=part.getSubmittedFileName();
	        	String exe=name.substring(name.lastIndexOf("."));
	        	UUID uuid = UUID.randomUUID();
	        	String fileName = uuid.toString().substring(0,8) + exe;
	        	
	        	System.out.println(fileName);
	        	try {
	        		//파일업로드
	        		InputStream is=part.getInputStream();
	        		FileOutputStream fos=new FileOutputStream("c:/" + path + fileName);
	        		int data=0;
	        		while((data=is.read())!=-1) {
	        			fos.write(data);
	        		}
	        		is.close();
	        		fos.close();
	        		
	        		//데이터저장
	        		AttachVO vo=new AttachVO();
	        		vo.setGid(gid);
	        		vo.setImage(path + fileName);
	        		dao.insert(vo);
	        	}catch(Exception e) {
	        		System.out.println("파일업로드:" + e.toString());
	        	}
	        }
	        break;
		case "/attach/delete":
			AttachVO vo=new AttachVO();
			vo.setAid(Integer.parseInt(request.getParameter("aid")));
			vo.setGid(request.getParameter("gid"));
			vo.setImage(request.getParameter("image"));
			
			//이미지파일삭제
			File file=new File("c:" + vo.getImage());
			if(file.exists()) file.delete();
			
			//데이터삭제
			dao.delete(vo.getAid());
			break;
		}
	}
}
