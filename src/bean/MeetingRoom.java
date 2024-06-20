package bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import dao.ReservationDao;
import dao.RoomDao;
import dao.UserDao;

public class MeetingRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	//フィールド
	private String date;
	private final int INTERVAL = 1;
	private static final String[] PERIOD = { "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00" };
	private RoomBean[] rooms;
	private UserBean user = null;

	//コンストラクタ
	public MeetingRoom() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.date = sdf.format(cal.getTime());
		this.rooms = RoomDao.findAll();
	}

	//	roomIdから会議室配列の添字を取得
	//	会議室配列に格納されている添字番号を出力する
	//	会議室配列に存在しない会議室IDが指定された場合は、例外を送出する
	private int roomIndex(String roomId) throws IndexOutOfBoundsException {
		for (int i = 0; i < rooms.length; i++) {
			if (roomId.equals(rooms[i].getId())) {
				return i;
			}
		}
		throw new IndexOutOfBoundsException("この会議室は存在しません: " + roomId);
	}

	//	利用開始時刻に対応する利用時間帯の添字を取得
	//	利用時間帯配列に存在しない利用時間が指定された場合は、例外を送出する
	private int startPeriod​(String start) throws IndexOutOfBoundsException {
		int index = Arrays.asList(PERIOD).indexOf(start);
		if (index != -1) {
			return index;
		} else {
			throw new IndexOutOfBoundsException(" 利用可能時間外です:" + start);
		}
	}

	//	利用時間帯の配列を取得
	//	フィールドから利用時間帯配列を出力する
	public static String[] getPeriod() {
		return PERIOD;
	}

	//	会議室予約システムで利用できるすべての会議室を返します。
	public RoomBean[] getRooms() {
		return rooms;
	}

	//	利用会議室取得
	//フィールドの会議室配列から該当する会議室IDを元に会議室情報を出力する
	public RoomBean getRoom​(String roomId) {
		try {
			int index = roomIndex(roomId);
			return rooms[index];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	//	会議室予約システムにログインしている利用者を返します。
	public UserBean getUser() {
		return user;
	}

	//	会議室予約システムの利用日を返します。
	public String getDate() {
		return date;
	}

	//  利用日更新
	//  会議室予約システムの利用日を設定します。
	public void setDate​(String date) {
		this.date = date;
	}

	//  認証
	//	会議室予約システムにログインします+フィールドのuserを初期化。
	public boolean login​(String id, String password) {
		UserBean user = UserDao.certificate​(id, password);
		if (user != null) {
			this.user = user;
			return true;
		} else {
			return false;
		}
	}

	//会議室予約システムの利用日における予約状況を返します。
	public ReservationBean[][] getReservations() {
		ReservationBean[][] rbList = new ReservationBean[rooms.length][PERIOD.length];
		List<ReservationBean> reservations = ReservationDao.findByDate​(date);
		if (reservations != null) {
			for (ReservationBean reservation : reservations) {
				int rIndex = roomIndex(reservation.getRoomId());
				int pIndex = startPeriod​(reservation.getStart().substring(0, 5));
				rbList[rIndex][pIndex] = reservation;
			}
		}
		return rbList;
	}

	//予約の生成
	public ReservationBean createReservation(String roomId, String start) {
		try {
			int pIndex = startPeriod​(start);
			String end;
			if (pIndex <= 6) {
				end = PERIOD[pIndex + INTERVAL];
			} else {
				end = "17:00";
			}
			return new ReservationBean(roomId, date, start, end, user.getId());
		} catch (IndexOutOfBoundsException e) {
			System.err.println("予約の生成に失敗しました: " + e.getMessage());
			return null;
		}
	}

	//予約登録
	public void reserve(ReservationBean reservation) throws Exception {
		LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
		// フォーマッタを作成
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
		// フォーマットした日時を取得
		String currentTime = now.format(formatter);
		String reserveTime = getDate() + reservation.getStart();
		if (reserveTime.compareTo(currentTime) > 0) {
			boolean success = ReservationDao.insert​(reservation);
			if (!success) {
				throw new Exception("予約に失敗しました。");
			}
		} else {
			throw new Exception("予約可能時間を過ぎています。");
		}
	}

	//予約キャンセル
	public void cancel(ReservationBean reservation) throws Exception {
		// 現在時刻を取得
		LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
		// フォーマッタを作成
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
		// フォーマットした日時を取得
		String currentTime = now.format(formatter);
		String reserveTime = getDate() + reservation.getStart();
		if (reserveTime.compareTo(currentTime) > 0) {
			boolean success = ReservationDao.delete​(reservation);
			if (!success) {
				throw new Exception("予約が既にキャンセルされています。");
			}
		} else {
			throw new Exception("キャンセル可能時間を過ぎています。");
		}
	}

	//新規利用者生成
	public UserBean newUser(String id, String password, String name, String address) throws Exception {
		UserBean user = new UserBean(id, password, name, address, "0");
		List<UserBean> userList = UserDao.findByUser(id);
		if (userList.size() == 0) {
			return user;
		} else {
			throw new Exception("利用者ＩＤを変更してください");
		}
	}

	//新規利用者生成(管理者権限付)
	public UserBean newUser(String id, String password, String name, String address, String adminFlg) throws Exception {
		UserBean user = new UserBean(id, password, name, address, adminFlg);
		List<UserBean> userList = UserDao.findByUser(id);
		if (userList.size() == 0) {
			return user;
		} else {
			throw new Exception("利用者ＩＤを変更してください");
		}
	}

	//新規利用者登録
	public void newUserRegistration(UserBean user) throws Exception {
		List<UserBean> userList = UserDao.findByUser(user.getId());
		if (userList.size() == 0) {
			UserDao.addUser​(user);
		} else {
			throw new Exception("既に登録されています。");
		}
	}

	//利用者情報修正
	public void userInformationModification(String newId, String newPassword, String newName, String newAddress)
			throws Exception {
		UserBean user = getUser();
		if (user != null) {
			UserDao.updateUser(newId, newPassword, newName, newAddress, user);
		} else {
			throw new Exception("利用者ＩＤ、またはパスワードを変更してください");
		}
	}

	//既存ユーザ―の検索
	public UserBean existingUsers(String id, String password) throws Exception {
		UserBean user = UserDao.certificate​(id, password);
		if (user != null) {
			return user;
		} else {
			throw new Exception("利用者ＩＤ、またはパスワードが間違っています。");
		}
	}

	//利用者登録解除
	public void userRelease(UserBean user) throws Exception {
		List<UserBean> existsUser = UserDao.findByUser(user.getId());
		ReservationBean[] reservations = ReservationDao.userReservationInformationSearch(user.getId());
		if (reservations.length > 0) {
			for (ReservationBean reservation : reservations) {
				ReservationDao.delete​(reservation);
			}
		}
		if (existsUser.size() == 0) {
			throw new Exception("既に解除されています。");
		}
		if (this.user.getId().equals(user.getId()) && user.getAdminFlg().equals("1")) {
			throw new Exception("管理者は自身の利用者解除はできません。");
		} else {
			UserDao.deleteUser​(user);
		}
	}

	//新規会議室作成
	public RoomBean newRoom(String id, String name) throws Exception {
		RoomBean room = new RoomBean(id, name);
		RoomBean existingRoom = RoomDao.findByRoomId(id);
		if (existingRoom == null) {
			return room;
		} else {
			throw new Exception("既に登録されているＩＤです。");
		}
	}

	//新規会議室登録
	public void newRoomRegistration(RoomBean room) throws Exception {
		RoomBean existingRoom = RoomDao.findByRoomId(room.getId());
		if (existingRoom == null) {
			RoomDao.addRoom​(room);
			this.rooms = RoomDao.findAll();
		} else {
			throw new Exception("既に登録されています。");
		}
	}

	//既存の会議室の検索
	public RoomBean existingRoom(String id) throws Exception {
		RoomBean room = RoomDao.findByRoomId(id);
		if (room != null) {
			return room;
		} else {
			throw new Exception("このＩＤに該当する会議室はありません。");
		}
	}

	//会議室削除
	public void existingRoomdelete(RoomBean room) throws Exception {
		RoomBean existingRoom = RoomDao.findByRoomId(room.getId());
		ReservationBean[] reservations = ReservationDao.roomReservationInformationSearch(room.getId());
		if (reservations.length > 0) {
			for (ReservationBean reservation : reservations) {
				ReservationDao.delete​(reservation);
			}
		}
		if (existingRoom != null) {
			RoomDao.deleteRoom​(room);
			this.rooms = RoomDao.findAll();
		} else {
			throw new Exception("既に削除されています。");
		}
	}

	//表示名の変更
	public boolean RegistrationDisplayName(String displayname, String id) {
		//Daoにつなげる
		boolean flag = UserDao.RegistrationDisplayName​​(displayname, id);
		if (flag) {
			return true;
		} else {
			return false;
		}
	}

	//パスワードの変更
	public boolean RegistrationChangePassword​​(String changePassword, String id) {
		//Daoにつなげる
		boolean flag = UserDao.RegistrationChangePassword​​(changePassword, id);
		if (flag) {
			return true;
		} else {
			return false;
		}
	}

	// HTMLエスケープ用のユーティリティメソッド
	public static String escapeHtml(String s) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&#x27;");
				break;
			case '/':
				sb.append("&#x2F;");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String shorteningAndEscape(String s, int maxLength) {
		// 入力文字列がnullの場合はnullを返す
		if (s == null) {
			return null;
		}

		// 入力文字列をHTMLエスケープする
		String escaped = escapeHtml(s);

		// エスケープされた文字列が指定した最大長を超える場合
		if (escaped.length() > maxLength) {
			// axLengthまで切り詰めて「...」を付加する
			return escaped.substring(0, maxLength) + "...";
		} else {
			// それ以外の場合はそのまま返す
			return escaped;

		}
	}
}