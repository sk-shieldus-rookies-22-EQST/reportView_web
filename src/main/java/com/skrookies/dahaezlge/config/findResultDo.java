package com.skrookies.dahaezlge.config;

public class findResultDo {
	// users
	private String user_id;
	private String user_pw;
	private String user_email;
	private String user_phone;
	private String user_level;
	private String user_created_at;

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_level(String user_level) {
		this.user_level = user_level;
	}
	public String getUser_level() {
		return user_level;
	}
	public void setUser_created_at(String user_created_at) {
		this.user_created_at = user_created_at;
	}
	public String getUser_created_at() {
		return user_created_at;
	}

	//user_key
	private String key_id;
	private String key_user_id;
	private String key_created_at;

	public void set_Key_id(String key_id) {
		this.key_id = key_id;
	}
	public String get_Key_id() {
		return key_id;
	}
	public void set_Key_user_id(String key_user_id) {
		this.key_user_id = key_user_id;
	}
	public String get_Key_user_id() { return key_user_id; }
	public void set_Key_created_at(String key_created_at) {
		this.key_created_at = key_created_at;
	}
	public String get_Key_created_at() {
		return key_created_at;
	}

	//user_point
	private String point_user_id;
	private String point;

	public void set_Point_user_id(String point_user_id) {this.point_user_id = point_user_id;}
	public String get_Point_user_id() {
		return point_user_id;
	}
	public void set_Point(String key_user_id) {
		this.point = point;
	}
	public String get_Point() { return point; }


	//book
	private String book_id;
	private String book_title;
	private String book_auth;
	private String book_path;
	private String book_summary;
	private String book_reg_date;
	private String book_img_path;

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getBook_id() {
		return book_id;
	}
	public void setBook_title(String book_title) {
		this.book_title = book_title;
	}
	public String getBook_title() { return book_title; }
	public void setBook_auth(String book_auth) {
		this.book_auth = book_auth;
	}
	public String get_Book_auth() {
		return book_auth;
	}
	public void set_Book_path(String book_path) { this.book_path = book_path;}
	public String get_Book_path() {
		return book_path;
	}
	public String get_Book_summary() {	return book_summary;	}
	public void set_Book_summary(String book_summary) {
		this.book_summary = book_summary;
	}
	public String get_Book_reg_date() {	return book_reg_date;	}
	public void set_Book_reg_date(String book_reg_date) {
		this.book_reg_date = book_reg_date;
	}
	public String get_Book_img_path() {	return book_img_path;	}
	public void set_Book_img_path(String book_img_path) {
		this.book_img_path = book_img_path;
	}

	//qna
	private String qna_id;
	private String qna_title;
	private String qna_body;
	private String qna_user_id;
	private String qna_created_at;

	public String get_Qna_id() {
		return qna_id;
	}
	public void set_Qna_id(String qna_id) {
		this.qna_id = qna_id;
	}
	public String get_Qna_title() {
		return qna_title;
	}
	public void set_Qna_title(String qna_title) {
		this.qna_title = qna_title;
	}
	public String get_Qna_body() {
		return qna_body;
	}
	public void set_Qna_body(String qna_body) {
		this.qna_body = qna_body;
	}
	public String get_Qna_user_id() {
		return qna_user_id;
	}
	public void set_Qna_user_id(String qna_user_id) {
		this.qna_user_id = qna_user_id;
	}
	public String get_Qna_created_at() {
		return qna_created_at;
	}
	public void set_Qna_created_at(String qna_created_at) {
		this.qna_created_at = qna_created_at;
	}

	//qna_re
	private String qna_re_id;
	private String qna_re_user_id;
	private String qna_re_body;
	private String qna_re_created_at;

	public String get_Qna_re_id() {
		return qna_re_id;
	}
	public void set_Qna_re_id(String qna_re_id) {
		this.qna_re_id = qna_re_id;
	}
	public String get_Qna_re_user_id() {
		return qna_re_user_id;
	}
	public void set_Qna_re_user_id(String qna_re_user_id) {	this.qna_re_user_id = qna_re_user_id; }
	public String get_Qna_re_body() {
		return qna_re_body;
	}
	public void set_Qna_re_body(String qna_re_body) {
		this.qna_re_body = qna_re_body;
	}
	public String get_Qna_re_created_at() {
		return qna_re_created_at;
	}
	public void set_Qna_re_created_at(String qna_re_created_at) {
		this.qna_re_created_at = qna_re_created_at;
	}


}
