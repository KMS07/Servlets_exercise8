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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;

@WebServlet("/query")
public class Q10_3 extends HttpServlet{
	static Scanner sc = new Scanner(System.in);
	private static final long serialVersionUID = 1L;

    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher reqd = request.getRequestDispatcher("condquery.html");
		reqd.include(request, response);
		String docType = "<!DOCTYPE HTML>\n";
		String title="Result";
		out.println(docType + "<HTML>\n" + "<HEAD><TITLE>" +title+ "</TITLE>"+"<STYLE>\n"
		+"TH{background-color: red; BORDER: 2px solid black;}\n"+"</STYLE>\n"+"</HEAD>\n"
				+ "<BODY>\n" + "<H1 ALIGN=CENTER>" + title + "</H1>\n");
		
		int age = Integer.parseInt(request.getParameter("age"));
		String query="";
		String url = "jdbc:mysql://localhost:3306/company?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String usr = "root";
		String password = "password";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try(Connection con = DriverManager.getConnection(url,usr,password);){
			LocalDate d = LocalDate.parse(request.getParameter("date"));
			LocalDate current = LocalDate.now();
			String mdate;
			if(current.getDayOfMonth()<=10) {
				mdate = (current.getYear()-age)+"-"+"0"+current.getMonthValue()+"-"+"0"+current.getDayOfMonth();
			}
			else {
				mdate = (current.getYear()-age)+"-"+"0"+current.getMonthValue()+"-"+current.getDayOfMonth();
			}
			LocalDate updateDate = LocalDate.parse(mdate);
			query = "select * from employee where emp_join>'"+d+"' and dob<'"+updateDate+"';";
			Statement s = con.createStatement();
			ResultSet r = s.executeQuery(query);
			
			out.println("<TABLE STYLE ='BORDER: 1PX SOLID BLACK; '>\n"+"<TR>\n"
						+"<TH> Employee id </TH>\n"
						+"<TH> Employee name </TH>\n"
						+"<TH> Job </TH>\n"
						+"<TH> Joining Date </TH>\n"
						+"<TH> DOB </TH>\n"
						+"<TH> Salary </TH>\n"
						+"<TH> Department id </TH>\n"+"</TR>\n");
			while (r.next()) {
               String l= "<TR>\n"+"<TD> "+r.getInt(1)+" </TD>\n"
            		   	 +"<TD> "+r.getString(2)+" </TD>\n"
            		   	 +"<TD> "+r.getString(3)+" </TD>\n"
            		   	 +"<TD> "+r.getDate(4)+" </TD>\n"
            		   	 +"<TD> "+r.getDate(5)+" </TD>\n"
            		   	 +"<TD> "+r.getDouble(6)+" </TD>\n"
            		   	 +"<TD> "+r.getInt(7)+" </TD>\n"+"</TR>\n";
				out.println(l+"");
               
			}
			out.println("</TABLE>"+"</BODY>"+"</HTML>");
            con.close();
			
		}catch(SQLException e) {
			Logger lgr = Logger.getLogger(Q10_3.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
		}
		
		
		
    }
    
	
	
	
		
}
