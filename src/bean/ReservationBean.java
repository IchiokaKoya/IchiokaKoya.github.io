package bean;

import java.io.Serializable;

public class ReservationBean implements Serializable{

	//フィールド
	private static final long serialVersionUID = 1L;
	private int id;
	private String roomId;
	private String date;
	private String start;
	private String end;
	private String userId;
//	private int reservationId;

	//コンストラクタ
	public ReservationBean() {}

	public ReservationBean(int id, String roomId, String date, String start, String end, String userId){
		this.id = id;
		this.roomId = roomId;
		this.date = date;
		this.start = start;
		this.end = end;
		this.userId = userId;
	}

	public ReservationBean(String roomId, String date, String start, String end, String userId) {
		this.roomId = roomId;
		this.date = date;
		this.start = start;
		this.end = end;
		this.userId = userId;

	}

	//メソッド
	public int getId() {
		return id;
	}



	public String getRoomId() {
		return roomId;
	}
	public String getDate() {
		return date;
	}
	public String getStart() {
		return start;
	}
	public String getEnd() {
		return end;
	}
	public String getUserId() {
		return userId;
	}


	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ReservationBean [id=" + id + ", roomId=" + roomId + ", date=" + date + ", start=" + start + ", end="
				+ end + ", userId=" + userId + "]";
	}





}


