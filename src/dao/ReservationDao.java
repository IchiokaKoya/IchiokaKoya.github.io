package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.ReservationBean;

public class ReservationDao {

	ReservationDao() {
	}

	public static List<ReservationBean> findByDate​(String date) {

		List<ReservationBean> list = new ArrayList<ReservationBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();
			String sql = "select * from reservation WHERE date = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, date);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String roomId = rs.getString("roomId");
				String useDate = rs.getString("date");
				String start = rs.getString("start").substring(0, 5);
				String end = rs.getString("end").substring(0, 5);
				String userId = rs.getString("userId");
				ReservationBean rb = new ReservationBean(id, roomId, useDate, start, end, userId);
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

	public static ReservationBean[] userReservationInformationSearch(String userId) {

		List<ReservationBean> list = new ArrayList<ReservationBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();
			String sql = "select * from reservation WHERE userId = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String roomId = rs.getString("roomId");
				String useDate = rs.getString("date");
				String start = rs.getString("start").substring(0, 5);
				String end = rs.getString("end").substring(0, 5);
				String userId2 = rs.getString("userId");
				ReservationBean rb = new ReservationBean(id, roomId, useDate, start, end, userId2);
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
		ReservationBean[] reservationArray = new ReservationBean[list.size()];
        list.toArray(reservationArray);
		return reservationArray;
	}

	public static ReservationBean[] roomReservationInformationSearch(String roomId) {

		List<ReservationBean> list = new ArrayList<ReservationBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();
			String sql = "select * from reservation WHERE roomId = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, roomId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String roomId2 = rs.getString("roomId");
				String useDate = rs.getString("date");
				String start = rs.getString("start").substring(0, 5);
				String end = rs.getString("end").substring(0, 5);
				String userId = rs.getString("userId");
				ReservationBean rb = new ReservationBean(id, roomId2, useDate, start, end, userId);
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
		ReservationBean[] reservationArray = new ReservationBean[list.size()];
        list.toArray(reservationArray);
		return reservationArray;
	}


	public static boolean insert​(ReservationBean reservation) {
		String roomId = reservation.getRoomId();
		String useDate = reservation.getDate();
		String start = reservation.getStart();
		String end = reservation.getEnd();
		String userId = reservation.getUserId();
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();
			String sql = "INSERT INTO reservation (roomId, date, start, end, userId) VALUES (?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, roomId);
			ps.setString(2, useDate);
			ps.setString(3, start);
			ps.setString(4, end);
			ps.setString(5, userId);
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

	public static boolean delete​(ReservationBean reservation) {
		//		int id = reservation.getId();
		String roomId = reservation.getRoomId();
		String useDate = reservation.getDate();
		String start = reservation.getStart();
		String end = reservation.getEnd();
		String userId = reservation.getUserId();
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(RoomDao.RESOUACE);
			con = ds.getConnection();

			String sql = "DELETE FROM reservation WHERE roomId=? AND date=? AND start=? AND end=? AND userId=?";
			ps = con.prepareStatement(sql);
			//			ps.setInt(1, id);
			ps.setString(1, roomId);
			ps.setString(2, useDate);
			ps.setString(3, start);
			ps.setString(4, end);
			ps.setString(5, userId);

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
