package bean;

import java.io.Serializable;

public class RoomBean implements Serializable {
	//フィールド--------------------------------
	private String id;
	private String name;
	private static final long serialVersionUID = 1L;

	//コンストラクタ----------------------------
	public RoomBean() {
	}

	public RoomBean(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	//getter---------------------------------
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	//toString-------------------------------
	@Override
	public String toString() {
		UserBean user = new UserBean();
		return "利用者ID:" + id + ",パスワード:" + user.getPassword() + ",氏名:" + name + ",住所:" + user.getAddress();
	}

}
