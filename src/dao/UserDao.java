package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.UserBean;

public class UserDao {
	public UserDao() {
	}

	public static UserBean certificate​(String id, String password) {
		UserBean user = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();
			String sql = "SELECT * FROM user WHERE id = ? AND password = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new UserBean(rs.getString("id"), rs.getString("password"), rs.getString("name"),
						rs.getString("address"), rs.getString("adminFlg"));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return user;
	}


	public static List<UserBean> searchAllUsers() {
		List<UserBean> list = new ArrayList<UserBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();
			String sql = "select * from user";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String adminFlg = rs.getString("adminFlg");
				UserBean rb = new UserBean(id, password, name, address, adminFlg);
				list.add(rb);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return list;
	}

	public static List<UserBean> findByUser(String id) {
		List<UserBean> list = new ArrayList<UserBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();
			String sql = "select * from user WHERE id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				String password = rs.getString("password");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String adminFlg = rs.getString("adminFlg");
				UserBean rb = new UserBean(id, password, name, address, adminFlg);
				list.add(rb);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return list;
	}

	public static boolean addUser​(UserBean userbean) {
		String id = userbean.getId();
		String password = userbean.getPassword();
		String name = userbean.getName();
		String address = userbean.getAddress();
		String adminFlg = userbean.getAdminFlg();
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();
			String sql = "INSERT INTO user (id, password, name, address, adminFlg) VALUES (?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, password);
			ps.setString(3, name);
			ps.setString(4, address);
			ps.setString(5, adminFlg);
			int ret = ps.executeUpdate();
			if (ret != 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public static boolean updateUser(String newId, String newPassword, String newName, String newAddress, UserBean user) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();
			String sql = "UPDATE user SET id = ?, password = ?, name = ?, address = ? WHERE id = ? AND password = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, newId);
			ps.setString(2, newPassword);
			ps.setString(3, newName);
			ps.setString(4, newAddress);
			ps.setString(5, user.getId());
			ps.setString(6, user.getPassword());

			int updateRows = ps.executeUpdate();
			return updateRows > 0; // 更新が成功したかどうかを返す
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static boolean deleteUser​(UserBean userbean) {
		String id = userbean.getId();
		String password = userbean.getPassword();
		//		String name = userbean.getName();
		//		String address = userbean.getAddress();
		//		String adminFlg = userbean.getAdminFlg();
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();

			String sql = "DELETE FROM user WHERE id=? AND password=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, password);
			int ret = ps.executeUpdate();

			if (ret != 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	//表示名の変更
	public static boolean RegistrationDisplayName​​(String displayname, String id) {
//		UserBean user = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();

			String sql = "UPDATE user SET name = ? WHERE id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, displayname);
			ps.setString(2, id);

			ps.executeUpdate();
			//rs = ps.executeQuery();

	        } catch (Exception e) {
	            System.err.println(e.getMessage());
	            return false;
	        } finally {
	            try {
	                if (rs != null) {
	                    rs.close();
	                }
	                if (ps != null) {
	                    ps.close();
	                }
	                if (con != null) {
	                    con.close();
	                }
	            } catch (Exception e) {
	                System.err.println(e.getMessage());
	            }
	        }
	        return true;
	}

	//パスワードの変更
		public static boolean RegistrationChangePassword​​(String changePassword,String id) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				Context context = new InitialContext();
				DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
				con = ds.getConnection();

				String sql = "UPDATE user SET password = ? WHERE id = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, changePassword);
				ps.setString(2, id);

				ps.executeUpdate();
				//rs = ps.executeQuery();

		        } catch (Exception e) {
		            System.err.println(e.getMessage());
		            return false;
		        } finally {
		            try {
		                if (rs != null) {
		                    rs.close();
		                }
		                if (ps != null) {
		                    ps.close();
		                }
		                if (con != null) {
		                    con.close();
		                }
		            } catch (Exception e) {
		                System.err.println(e.getMessage());
		            }
		        }
		        return true;
		}


}