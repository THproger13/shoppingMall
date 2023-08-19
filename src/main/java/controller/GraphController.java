package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GraphDAO;

@WebServlet(value={"/graph/procedure1.json", "/graph/chart1", "/graph/procedure2.json", "/graph/chart2",
		"/graph/procedure3.json", "/graph/chart3"})
public class GraphController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    GraphDAO dao=new GraphDAO(); 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		RequestDispatcher dis=request.getRequestDispatcher("/home.jsp");
		switch(request.getServletPath()) {
		case "/graph/procedure1.json":
			out.println(dao.procedure1("2023"));
			break;
		case "/graph/chart1":
			request.setAttribute("pageName", "/graph/chart1.jsp");
			dis.forward(request, response);
			break;
		case "/graph/procedure2.json":
			out.println(dao.procedure2());
			break;
		case "/graph/chart2":
			request.setAttribute("pageName", "/graph/chart2.jsp");
			dis.forward(request, response);
			break;
		case "/graph/procedure3.json":
			out.println(dao.procedure3());
			break;
		case "/graph/chart3":
			request.setAttribute("pageName", "/graph/chart3.jsp");
			dis.forward(request, response);
			break;
		}
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
