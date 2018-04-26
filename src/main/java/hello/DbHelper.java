package hello;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import hello.model.RegisterUser;
import hello.model.SearchResults;
import hello.model.SendResponse;
import hello.util.StatVar;

public class DbHelper {
	static Connection conn = null;
	PreparedStatement stmt = null;

	/*
	 * private String JDBC_DRIVER = StatVar.JDBC_DRIVER; private String DB_URL =
	 * StatVar.DB_URL;
	 * 
	 * private String USER = StatVar.USER; private String PASS = StatVar.PASS;
	 */

	/*@Value("${spring.datasource.url}")
	private String dbUrl;

	@Autowired
	private DataSource dataSource;

	@Bean
	public DataSource dataSource() throws SQLException {
		if (dbUrl == null || dbUrl.isEmpty()) {
			return new HikariDataSource();
		} else {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		}
	}*/

	private static Connection getConnection() throws URISyntaxException, SQLException {
		String dbUrl = System.getenv("JDBC_DATABASE_URL");
		return DriverManager.getConnection(dbUrl);
	}

	void dbconnect() throws Exception {
		/*
		 * Class.forName(JDBC_DRIVER); System.out.println("Connecting to database...");
		 * conn = DriverManager.getConnection(DB_URL, USER, PASS);
		 */
		/*
		 * try (Connection connection = dataSource.getConnection()) {
		 * System.out.println("Connecting to database..."); } catch (Exception e) {
		 * System.out.println("Error connecting to database..."); }
		 */
		System.out.println("Connecting to database...");
		conn = getConnection();
	}

	void checkConn() throws Exception {
		dbconnect();

		String sql = "SELECT * FROM bond_users";
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		JSONArray jsonArray = new JSONArray();
		while (rs.next()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", rs.getString("name"));
			// jsonObj.put("isregistered", rs.getInt("isregistered"));

			jsonArray.put(jsonObj);

		}

		System.out.println(jsonArray.toString());

		stmt.close();
		conn.close();
	}

	void createTable() throws Exception {

		/*
		 * dbconnect(); String sql; String sql2; String sql3;
		 * 
		 * sql="drop table bond_valid_emails"; //sql =
		 * "CREATE TABLE bond_users(id NUMBER(10) NOT NULL, name VARCHAR2 (50), email VARCHAR2 (50), password_hash VARCHAR2 (255), mobile NUMBER(10), location VARCHAR2 (25), category VARCHAR2 (25), user_type VARCHAR2 (20), imei VARCHAR2 (20) NULL, token VARCHAR2 (250)  NULL,created_at timestamp(6),PRIMARY KEY (id))"
		 * ; sql2 =
		 * "CREATE TABLE bond_valid_emails (email_id VARCHAR(50) NOT NULL, isregistered NUMBER(1,0) DEFAULT 0  NOT NULL,PRIMARY KEY(email_id))"
		 * ; //sql3 =
		 * "CREATE TABLE bond_notification (title VARCHAR (150) NOT NULL, description VARCHAR(500) NOT NULL, created_by VARCHAR(100) NOT NULL,time TIMESTAMP(6))"
		 * ;
		 * 
		 * stmt = conn.prepareStatement(sql); stmt.executeQuery(sql);
		 * 
		 * stmt = conn.prepareStatement(sql2); stmt.executeQuery(sql2);
		 * 
		 * //stmt = conn.prepareStatement(sql3); //stmt.executeQuery(sql3);
		 * 
		 * stmt.close(); conn.close();
		 */

		//
		// dbconnect();
		// //sql="CREATE TABLE resources_videos(name VARCHAR(20) NOT NULL,url
		// VARCHAR(250) NOT NULL,description VARCHAR(500) NOT NULL,category VARCHAR(15)
		// NOT NULL)";
		//
		String sql = "ALTER TABLE resources MODIFY name char(100) ";
		// String sql1="Truncate Table resources";
		// String sql="ALTER TABLE resources ADD res_type varchar2(50)";
		//
		System.out.println(sql);
		stmt = conn.prepareStatement(sql);
		stmt.executeQuery();
		stmt.close();
		conn.close();

	}

	public SendResponse insert(String email) throws Exception {

		dbconnect();
		String sql;

		sql = "Insert into bond_valid_emails (email_id,isregistered) values (?,?)";

		stmt = conn.prepareStatement(sql);
		stmt.setString(1, email.toLowerCase());
		stmt.setInt(2, 0);
		stmt.executeUpdate();

		/*
		 * String sql2 = "Select * from bond_valid_emails"; stmt =
		 * conn.prepareStatement(sql2); ResultSet rs = stmt.executeQuery(sql2);
		 * 
		 * JSONArray jsonArray = new JSONArray(); while (rs.next()) { JSONObject jsonObj
		 * = new JSONObject(); jsonObj.put("email", rs.getString("email_id"));
		 * jsonObj.put("isregistered", rs.getInt("isregistered"));
		 * 
		 * jsonArray.put(jsonObj);
		 * 
		 * }
		 */

		// System.out.println(jsonArray.toString());

		stmt.close();
		conn.close();

		return new SendResponse("Email Added!", false);

	}

	public SendResponse delete(String email) throws Exception {
		dbconnect();
		String sql, sql2;

		sql = "delete from bond_valid_emails WHERE email_id = '" + email.toLowerCase() + "'";
		stmt = conn.prepareStatement(sql);
		stmt.executeUpdate();
		System.out.println(sql);

		sql2 = "delete from bond_users WHERE email = '" + email.toLowerCase() + "'";
		stmt = conn.prepareStatement(sql2);
		stmt.executeUpdate();
		System.out.println(sql2);

		// sql3 = "Select * from bond_users";
		/*
		 * stmt = conn.prepareStatement(sql3); ResultSet rs = stmt.executeQuery(sql3);
		 * 
		 * JSONArray jsonArray = new JSONArray(); while (rs.next()) { JSONObject jsonObj
		 * = new JSONObject(); jsonObj.put("email", rs.getString("email"));
		 * jsonArray.put(jsonObj);
		 * 
		 * }
		 * 
		 * System.out.println(jsonArray.toString());
		 */

		stmt.close();
		conn.close();

		return new SendResponse("User Deleted", false);

	}

	public SendResponse deleteResource(String url) throws Exception {
		dbconnect();
		String sql;

		sql = "delete from resources WHERE url = '" + url + "'";
		stmt = conn.prepareStatement(sql);
		stmt.executeUpdate();
		System.out.println(sql);
		stmt.close();
		conn.close();

		return new SendResponse("Resources Deleted", false);

	}

	public SendResponse insert_resources(String name, String url, String description, String category, String res_type)
			throws Exception {

		dbconnect();
		String sql;

		sql = "Insert into resources (name,url,description,category,res_type) values (?,?,?,?,?)";

		stmt = conn.prepareStatement(sql);
		stmt.setString(1, name);
		stmt.setString(2, url);
		stmt.setString(3, description);
		stmt.setString(4, category.toLowerCase());
		stmt.setString(5, res_type);
		stmt.executeUpdate();
		/*
		 * String sql2 = "Select * from resources"; stmt = conn.prepareStatement(sql2);
		 * ResultSet rs = stmt.executeQuery(sql2); System.out.println(sql2); JSONArray
		 * jsonArray = new JSONArray(); while (rs.next()) { JSONObject jsonObj = new
		 * JSONObject(); jsonObj.put("name", rs.getString("name")); jsonObj.put("url",
		 * rs.getString("url")); jsonObj.put("description",
		 * rs.getString("description")); jsonObj.put("category",
		 * rs.getString("category")); jsonObj.put("res_type", rs.getString("res_type"));
		 * 
		 * jsonArray.put(jsonObj);
		 * 
		 * }
		 */

		// System.out.println(jsonArray.toString());

		stmt.close();
		conn.close();

		return new SendResponse("Resource Added", false);

		// return jsonArray;

	}

	public RegisterUser getUser(String email) throws Exception {
		dbconnect();
		String sql = "Select * from bond_users WHERE email = '" + email.toLowerCase() + "' ";
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		RegisterUser userDetails = null;
		while (rs.next()) {

			userDetails = new RegisterUser();
			userDetails.setCategory(rs.getString("category"));
			userDetails.setEmail(rs.getString("email"));
			userDetails.setLocation(rs.getString("location"));
			userDetails.setMobile(rs.getString("mobile"));
			userDetails.setName(rs.getString("name"));
			userDetails.setUserType(rs.getString("user_type"));

		}

		stmt.close();
		conn.close();
		rs.close();
		return userDetails;
	}

	public void registerUser(String name, String email, String pass, String mob, String location, String category,
			String userType) throws Exception {

		dbconnect();

		String password = Encryptor.encrypt(StatVar.key, StatVar.iv, pass);

		String sql;
		sql = "Insert into bond_users (name,email,password_hash,mobile,location,category,user_type) values (?,?,?,?,?,?,?)";

		stmt = conn.prepareStatement(sql);
		// stmt.setInt(1, id);
		stmt.setString(1, name);
		stmt.setString(2, email.toLowerCase());
		stmt.setString(3, password);
		stmt.setString(4, mob);
		stmt.setString(5, location);
		stmt.setString(6, category.toLowerCase());
		stmt.setString(7, userType);

		stmt.executeUpdate();

		stmt.close();

		System.out.println(userType + "====    DONE+++++++");

		String sql1 = "UPDATE bond_valid_emails SET isregistered= 1 WHERE email_id = ?";
		stmt = conn.prepareStatement(sql1);
		stmt.setString(1, email);
		stmt.executeUpdate();

		stmt.close();

		conn.close();

	}

	public boolean login(String email, String pass, String imei, String token) throws Exception {
		dbconnect();

		String sql = "SELECT password_hash FROM bond_users WHERE email = '" + email.toLowerCase() + "' ";
		stmt = conn.prepareStatement(sql);
		// stmt.setString(1, email);
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery();

		System.out.println(rs.next());
		if (rs.getRow() > 0) {
			System.out.println("got 1 row");
			System.out.println(rs.getString("password_hash"));
			boolean status = Encryptor.checkPass(pass, rs.getString("password_hash"));
			if (status) {
				String sql1 = "UPDATE bond_users SET imei= ?, token= ? WHERE email = ?";
				stmt = conn.prepareStatement(sql1);
				stmt.setString(1, imei);
				stmt.setString(2, token);
				stmt.setString(3, email.toLowerCase());
				stmt.executeUpdate();

			}
			rs.close();
			stmt.close();
			conn.close();
			// System.out.println("pass wrong");
			return status;

		} else {

			System.out.println("got 0 row");
			rs.close();
			stmt.close();
			conn.close();

			return false;
		}

	}

	public boolean adminlogin(String email, String pass) throws Exception {
		dbconnect();

		String sql = "SELECT password_hash FROM bond_users WHERE email = '" + email + "' and user_type = 'admin' ";
		stmt = conn.prepareStatement(sql);
		// stmt.setString(1, email);
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery();

		System.out.println(rs.next());

		if (rs.getRow() > 0) {
			System.out.println("got 1 row");
			boolean status = Encryptor.checkPass(pass, rs.getString("password_hash"));
			if (status) {
				System.out.println("success");

			}
			rs.close();
			stmt.close();
			conn.close();
			// System.out.println("pass wrong");
			return status;

		} else {

			System.out.println("got 0 row");
			rs.close();
			stmt.close();
			conn.close();

			return false;
		}

	}

	public JSONObject isEmailValid(String email) throws Exception {
		dbconnect();

		String sql = "SELECT * FROM bond_valid_emails WHERE email_id= '" + email.toLowerCase() + "' ";
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		System.out.println(sql);

		JSONObject jsonObj = new JSONObject();
		System.out.println(rs.next());
		if (rs.getRow() > 0) {

			System.out.println("got 1 row");
			// System.out.println(rs.getString("email_id"));

			jsonObj.put("email", rs.getString("email_id"));
			jsonObj.put("isRegistered", rs.getBoolean("isregistered"));

			stmt.close();
			conn.close();
			rs.close();
			return jsonObj;
		} else {
			stmt.close();
			conn.close();
			rs.close();
			return null;
		}

	}

	public ArrayList<SearchResults> searchContacts(String character) throws Exception {

		dbconnect();

		ArrayList<SearchResults> list = new ArrayList<>();
		String sql = "SELECT * FROM bond_users  WHERE name LIKE '%" + character + "%' ORDER BY name ASC ";
		stmt = conn.prepareStatement(sql);
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {

			SearchResults obj = new SearchResults();

			obj.setCategory(rs.getString("category"));
			obj.setEmail(rs.getString("email"));
			obj.setName(rs.getString("name"));
			obj.setLocation(rs.getString("location"));
			obj.setMobile(rs.getString("mobile"));
			obj.setUserType(rs.getString("user_type"));

			list.add(obj);

		}
		return list;
	}

	public void addNotiToDB(String title, String desc, String createdBy) throws Exception {

		dbconnect();
		String sql = "Insert into bond_notification (title,description,created_by) values (?,?,?)";

		stmt = conn.prepareStatement(sql);
		stmt.setString(1, title);
		stmt.setString(2, desc);
		stmt.setString(3, createdBy);

		stmt.executeUpdate();

		stmt.close();
		conn.close();

	}

	public JSONArray getNotification() throws Exception {
		dbconnect();
		String sql = "Select * from bond_notification";

		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		JSONArray jsonArray = new JSONArray();
		while (rs.next()) {

			JSONObject obj = new JSONObject();
			obj.put("title", rs.getString("title"));
			obj.put("description", rs.getString("description"));
			obj.put("created_by", rs.getString("created_by"));
			obj.put("time", rs.getString("time"));

			jsonArray.put(obj);
		}

		/*
		 * if (!rs.next()) { JSONObject obj = new JSONObject(); obj.put("error", true);
		 * obj.put("message", "No notification"); jsonArray.put(obj); }
		 */

		stmt.close();
		conn.close();
		return jsonArray;
	}

	public String getToken(String category) throws Exception {

		dbconnect();
		String sql = "Select * from bond_users where category= '" + category.toLowerCase() + "'";
		System.out.println(sql);
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		JSONArray jArray = new JSONArray();

		while (rs.next()) {
			JSONObject jsonObj = new JSONObject();
			System.out.println("NAME--->>>" + rs.getString("name"));
			System.out.println("TOKEN--->>>" + rs.getString("token"));

			jsonObj.put("token", rs.getString("token"));
			jArray.put(jsonObj);

		}

		stmt.close();
		conn.close();
		return jArray.toString();

	}

	public boolean updateCategory(String email, String category) throws Exception {

		dbconnect();
		String sql = "UPDATE bond_users SET category= '" + category.toLowerCase() + "' WHERE email = '"
				+ email.toLowerCase() + "'";
		stmt = conn.prepareStatement(sql);

		// stmt.setString(1, email);
		// stmt.setString(2, category);
		int count = stmt.executeUpdate();
		stmt.close();
		conn.close();
		// System.out.println(sql + "---------->>" + count + "-----" + email + "-----" +
		// category);

		return (count > 0) ? true : false;

	}

	public boolean updateUsertype(String email, String usertype, String category) throws Exception {
		dbconnect();
		String sql = "UPDATE bond_users SET user_type= '" + usertype + "' WHERE email = '" + email.toLowerCase() + "'";
		stmt = conn.prepareStatement(sql);
		int count = stmt.executeUpdate();

		String sql2 = "UPDATE bond_users SET category= '" + category + "' WHERE email = '" + email.toLowerCase() + "'";
		stmt = conn.prepareStatement(sql2);
		int count1 = stmt.executeUpdate();
		stmt.close();
		conn.close();

		return (count > 0 && count1 > 0) ? true : false;
	}

	public JSONArray categoryDetails(String category, String usertype) throws Exception {

		dbconnect();
		String sql = "select * from bond_users where user_type='" + usertype + "' AND  category='"
				+ category.toLowerCase() + "' ";
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		JSONArray jsonArray = new JSONArray();
		while (rs.next()) {

			JSONObject obj = new JSONObject();
			obj.put("category", rs.getString("category"));
			obj.put("name", rs.getString("name"));
			obj.put("mobile", rs.getString("mobile"));
			obj.put("email", rs.getString("email"));

			jsonArray.put(obj);
		}

		stmt.close();
		conn.close();
		return jsonArray;

	}

	public JSONArray getAllUsers() throws Exception {

		dbconnect();
		String sql = "select * from bond_users ORDER BY name ASC ";
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		JSONArray jsonArray = new JSONArray();
		while (rs.next()) {

			JSONObject obj = new JSONObject();
			obj.put("category", rs.getString("category"));
			obj.put("name", rs.getString("name"));
			obj.put("mobile", rs.getString("mobile"));
			obj.put("email", rs.getString("email"));
			obj.put("location", rs.getString("location"));
			obj.put("user_type", rs.getString("user_type"));

			jsonArray.put(obj);
		}

		stmt.close();
		conn.close();
		return jsonArray;

	}

	public JSONArray getResources(String category, String res_type) throws Exception {

		dbconnect();
		String sql = "select * from resources where category='" + category.toLowerCase() + "' AND res_type='" + res_type
				+ "'";
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		JSONArray jsonArray = new JSONArray();
		while (rs.next()) {

			JSONObject obj = new JSONObject();
			obj.put("name", rs.getString("name"));
			obj.put("url", rs.getString("url"));
			obj.put("description", rs.getString("description"));
			obj.put("res_type", rs.getString("res_type"));

			jsonArray.put(obj);

		}
		return jsonArray;

	}

}
