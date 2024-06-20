package bean;

import java.io.Serializable;

public class UserBean implements Serializable {
		//フィールドの作成
		private static final long serialVersionUID = 1L;
		private String id;
		private String password;
		private String name;
		private String address;
		private String adminFlg;

		//コンストラクタの作成
		public UserBean() {}

		public UserBean(String id,String password,String name,String address) {
			this.id = id;
			this.password = password;
			this.name = name;
			this.address = address;
		}

		public UserBean(String id, String password, String name, String address, String adminFlg) {
			super();
			this.id = id;
			this.password = password;
			this.name = name;
			this.address = address;
			this.adminFlg = adminFlg;
		}

		//メソッド
		public String getId() {
			return id;
		}

		public String getPassword() {
			return password;
		}

		public String getName() {
			return name;
		}

		public String getAddress() {
			return address;
		}

		public String getAdminFlg() {
			return adminFlg;
		}

		@Override
		public String toString() {
			return "Userbean [id=" + id + ", password=" + password + ", name=" + name + ", address=" + address + "]";
		}





}
