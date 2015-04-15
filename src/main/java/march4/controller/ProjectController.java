package march4.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import march4.dao.ProjectDao;
import march4.model.Project;
import march4.util.Json;

/*
 * 보통 api를 관리할 때 버전은 수시로 변경됩니다.
 * 그래서 api다음에 버전정보를 기록하는 것이 일반적입니다.
 * 
 * ex) /api/v1/project ...
 */
@WebServlet("/api/project")
public class ProjectController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String id = req.getParameter("projectId");
		if(id == null) return;
		
		PrintWriter writer = resp.getWriter();
		Json.builder.toJson(ProjectDao.getProjectById(Integer.parseInt(id)),writer);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Project newProject = Json.builder.fromJson(req.getReader(), Project.class);
		ProjectDao.addProject(newProject);
		doGet(req,resp);
	}
}
