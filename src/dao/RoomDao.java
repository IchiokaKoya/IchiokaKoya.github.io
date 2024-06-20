package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.RoomBean;

public class RoomDao {
	static final String RESOUACE = "java:comp/env/jdbc/meetingrooma";

	public static RoomBean[] findAll() {
		List<RoomBean> list = new ArrayList<RoomBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup(RESOUACE);
			con = ds.getConnection();
			String sql = "SELECT * FROM room";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				String roomId = rs.getString("id");
				String roomName = rs.getString("name");
				RoomBean room = new RoomBean(roomId, roomName);
				list.add(room);
			}

			}catch(Exception e) {
				System.err.println(e.getMessage());
				return null;
			}finally {
				try {
					if(rs != null) {
						rs.close();
					}
					if(ps != null) {
						ps.close();
					}
					if(con != null) {
						con.close();
					}
				}catch(Exception e) {
					System.err.println(e.getMessage());
				}
		}
        RoomBean[] roomArray = new RoomBean[list.size()];
        list.toArray(roomArray);
        return roomArray;
	}


	public static RoomBean findByRoomId(String id) {
	    RoomBean room = null;
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        Context context = new InitialContext();
	        DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
	        con = ds.getConnection();
	        String sql = "SELECT * FROM room WHERE id=?";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            String name = rs.getString("name");
	            room = new RoomBean(id, name);
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
	    return room;
	}
	public static boolean addRoom​(RoomBean roombean) {
		String id = roombean.getId();
		String name = roombean.getName();
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();
			String sql = "INSERT INTO room (id, name) VALUES (?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
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

	public static boolean deleteRoom​(RoomBean roombean) {
		String id = roombean.getId();
		String name = roombean.getName();
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();

			String sql = "DELETE FROM room WHERE id=? AND name=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
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

}
