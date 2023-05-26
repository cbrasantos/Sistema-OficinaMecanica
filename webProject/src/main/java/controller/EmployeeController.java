package controller;

import model.dao.EmployeeDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Person;

@WebServlet("/EmployeeController")
public class EmployeeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/index.jsp";
    private EmployeeDAO dao;

    public EmployeeController() {
        super();
        dao = new EmployeeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] person = new String[5];
        person[0] = (request.getParameter("name"));
        person[1] = (request.getParameter("cpf"));
        person[2] = (request.getParameter("tel"));
        person[3] = (request.getParameter("address"));
        person[4] = (request.getParameter("email"));
        
        String[] employee = new String[4];
        employee[0] = (request.getParameter("position"));
        employee[1] = (request.getParameter("salary"));
        employee[2] = (request.getParameter("workHours"));
        employee[3] = (request.getParameter("contractDate"));
        
        if(dao.insert(person, employee)) {
            System.out.println("SUCESSO!");
        }
    }
}