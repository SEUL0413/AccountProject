package AP;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
//	mysql에 연결하는 메소드
//	프로젝트 > Build Path > Configure Build Path
//	> Java Build Path > Libraries > Add External JARs 
//	> mysql-connector-java-5.1.27-bin.jar 추가
	
	public static Connection getMySqlConnection() {
		Connection c = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/ap?useUnicode=true&characterEncoding=UTF-8";
			String user = "root";
			String password = "0000";
			c = DriverManager.getConnection(url, user, password);
			System.out.println("연결 성공 : " + c);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스가 없거나 읽어올 수 없습니다.");
		} catch (SQLException e) {
			System.out.println("데이터베이스 접속 정보가 올바르지 않습니다.");
		}
		return c;
	}
	
	public static void close(Connection c) {
		if(c != null) {try {c.close();} catch (SQLException e) {e.printStackTrace();}}
	}
	public static void close(Statement s) {
		if(s != null) {try {s.close();} catch (SQLException e) {e.printStackTrace();}}
	}
	public static void close(PreparedStatement p) {
		if(p != null) {try {p.close();} catch (SQLException e) {e.printStackTrace();}}
	}
	public static void close(ResultSet r) {
		if(r != null) {try {r.close();} catch (SQLException e) {e.printStackTrace();}}
	}
}
