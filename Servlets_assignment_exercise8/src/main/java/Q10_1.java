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
import java.time.LocalDate;
import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@jakarta.servlet.annotation.WebServlet("/deptserver")
public class Q10_1 extends HttpServlet{
	static Scanner sc = new Scanner(System.in);
	private static final long serialVersionUID = 1L;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher reqd = request.getRequestDispatcher("dept.html");
		reqd.include(request, response);
		String docType = "<!DOCTYPE HTML>\n";
		String title="";
		
		int deptId = Integer.parseInt(request.getParameter("deptid"));
		if( addDept(deptId ,request.getParameter("dname"),request.getParameter("dloc"))) {
				title="Records Inserted Successfully";
			}else {
				title = "Cannot insert tuples";
			}
		
		out.println(docType + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n"
				+ "<BODY>\n" + "<H1 ALIGN=CENTER>" + title + "</H1>\n"+"</BODY></HTML>");
		
    }
    
	
	
	public static boolean addDept(int depId, String dname, String dloc){
		String url = "jdbc:mysql://localhost:3306/company?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String usr = "root";
		String password = "password";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try(Connection con = DriverManager.getConnection(url,usr,password);){
				String template = "INSERT INTO department values(?,?,?)";
				PreparedStatement  ins = con.prepareStatement(template);
				ins.setInt(1,depId);
				ins.setString(2,dname);
				ins.setString(3, dloc);
				ins.executeUpdate();
				con.close();
				return true ;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
		
}
