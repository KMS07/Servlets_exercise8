import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;

@WebServlet("/empserver")
public class Q10_2 extends HttpServlet{
	static Scanner sc = new Scanner(System.in);
	private static final long serialVersionUID = 1L;
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher reqd = request.getRequestDispatcher("emp.html");
		reqd.include(request, response);
		String docType = "<!DOCTYPE HTML>\n";
		String title="";
		
		int deptId = Integer.parseInt(request.getParameter("deptId"));
		int empId = Integer.parseInt(request.getParameter("eid"));
		double sal = Double.parseDouble(request.getParameter("sal"));
		if( addEmp(empId,request.getParameter("ename"),request.getParameter("job"),
						request.getParameter("bdate"),request.getParameter("jdate"),sal,deptId)) {
			title="Records Inserted Successfully";
		}else {
			title = "Cannot insert tuples";
		}
		
		out.println(docType + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n"
				+ "<BODY>\n" + "<H1 ALIGN=CENTER>" + title + "</H1>\n"+"</BODY></HTML>");
		
    }
    
	public static boolean addEmp(int Empid,String name,String JobTitle,String bday,String jday,double sal,int deptId) {
		String url = "jdbc:mysql://localhost:3306/company?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String usr = "root";
		String password = "password";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try(Connection con = DriverManager.getConnection(url,usr,password);){
					String template = "INSERT INTO employee values(?,?,?,?,?,?,?)";
					try {
						PreparedStatement  ins = con.prepareStatement(template);
						ins.setInt(1,Empid);
						ins.setString(2,name);
						ins.setString(3,JobTitle);
						ins.setDate(4, java.sql.Date.valueOf(bday));
						ins.setDate(5, java.sql.Date.valueOf(jday));
						ins.setDouble(6,sal);
						ins.setInt(7,deptId);
						ins.executeUpdate();
						con.close();
					}
					catch(SQLException e1){
						return false;
					}
					return true;
		}catch(SQLException e) {
			Logger lgr = Logger.getLogger(Q10_2.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
            return false;
	}

}
	
	
	
}
