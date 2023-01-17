package dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Account2;
import util.GenerateHashedPw;
import util.GenerateSalt;

public class Account2DAO {
	private static Connection getConnection() throws URISyntaxException, SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    URI dbUri = new URI(System.getenv("DATABASE_URL"));

	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

	    return DriverManager.getConnection(dbUrl, username, password);
	}
	public static List<Account2>selectAllAccount2(){
		List<Account2>result = new ArrayList<>();
		
		String sql = "SELECT * FROM account2";
		
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					int age = rs.getInt("age");
					String gender = rs.getString("gender");
					int tel = rs.getInt("tel");
					String mail = rs.getString("mail");
					String salt = rs.getString("salt");
					String password = rs.getString("password");
//					String hashedPassword = rs.getString("hashedpassword");
					System.out.println("mail");
					Account2 fun = new Account2(id,name,age,gender,tel,mail,salt,password,null);
					
					result.add(fun);
				}
			}
			}catch(SQLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
		}
		return result;
	}
	
	public static int registerAccount2(Account2 account2) {
		String sql = "INSERT INTO account2 VALUES(default, ?, ?, ?, ?, ?, ?, ?)";
		int result = 0;
		
		// ランダムなソルトの取得(今回は32桁で実装)
		String salt = GenerateSalt.getSalt(32);
		
		// 取得したソルトを使って平文PWをハッシュ
		String hashedPw = GenerateHashedPw.getSafetyPassword(account2.getPassword(), salt);
		System.out.println("登録時のソルト:" + salt);
		System.out.println("登録時のハッシュＰＷ:" +  hashedPw);
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, account2.getName());
			pstmt.setInt(2, account2.getAge());
			pstmt.setString(3,account2.getGender());
			pstmt.setInt(4,account2.getTel());
			pstmt.setString(5, account2.getMail());
			pstmt.setString(6, salt);
			pstmt.setString(7, hashedPw);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			System.out.println(result + "件更新しました。");
		}
		return result;
	}
	
	public static int daleteAccount2(String mail) {
		String sql = "DELETE FROM account2 WHERE mail = ?";
		
		int result = 0;
		
		try (
				Connection con = getConnection();	// DB接続
				PreparedStatement pstmt = con.prepareStatement(sql);			// 構文解析
				){
			pstmt.setString(1, mail);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			System.out.println(result + "件更新しました。");
		}
		return result;
	}
	// メールアドレスを元にソルトを取得
		public static String getSalt(String mail) {
			String sql = "SELECT salt FROM account2 WHERE mail = ?";
			
			try (
					Connection con = getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);
					){
				pstmt.setString(1, mail);

				try (ResultSet rs = pstmt.executeQuery()){
					
					if(rs.next()) {
						String salt = rs.getString("salt");
						return salt;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		// ログイン処理
		public static Account2 login(String mail, String hashedPw) {
			String sql = "SELECT * FROM account2 WHERE mail = ? AND password = ?";
			
			try (
					Connection con = getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);
					){
				pstmt.setString(1, mail);
				pstmt.setString(2, hashedPw);

				try (ResultSet rs = pstmt.executeQuery()){
					
					if(rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						int age = rs.getInt("age");
						String gender = rs.getString("gender");
						int tel = rs.getInt("tel");
						String salt = rs.getString("salt");
						
						return new Account2(id, name,age,gender,tel, mail, salt, null, null);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return null;
		}

}
