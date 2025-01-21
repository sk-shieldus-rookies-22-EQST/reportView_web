package com.skrookies.dahaezlge.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class findResultDao {  // DB 정보 수정 필요
	String id = "root";
	String password = "111111";
	String url = "jdbc:mysql://localhost:3306/jspdb_3315?characterEncoding=utf-8";
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public void connect() {
		//1. 드라이버 로딩
		try {
			Class.forName("com.mysql.jdbc.Driver");
	
		//2. DB 연결
			conn = DriverManager.getConnection(url,id,password);
			//System.out.println("MySQL 연결 성공 !!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disConnect() {
		if(conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//조건에 맞는 비행기 검색
//	public ArrayList<findResultDo> getFlights(String a, String b, String c, String e) {
//		connect();
//		//sql 처리
//		ArrayList<findResultDo> aList = new ArrayList<>();
//		//3. SQL문 완성
//		String sql = "select * from flight, airline where flightfrom='" + a + "' and flightto='" + b + "' and flightdate='" + c + "' and flightairline = airlinecode order by " + e ;
//
//		try {
//			pstmt = conn.prepareStatement(sql);
//
//			//4. SQL문 실행(전송)
//			rs = pstmt.executeQuery();
//			int i = 1;
//			while(rs.next()) {
//				findResultDo rdo = new findResultDo();
//				rdo.setFlightno(rs.getString(1));
//				rdo.setFlightairline(rs.getString(2));
//				rdo.setFlightfrom(rs.getString(3));
//				rdo.setFlightto(rs.getString(4));
//				rdo.setFlightdate(rs.getString(5));
//				rdo.setTakeofftime(rs.getString(6));
//				rdo.setLandingtime(rs.getString(7));
//				rdo.setAirline(rs.getString(9));
//
//				aList.add(rdo);
//				i++;
//			}
//		} catch (Exception exp) {
//			exp.printStackTrace();
//		}
//
//
//		disConnect();
//		return aList;
//
//	}
	
	//선택한 비행기 정보 확인
//	public findResultDo selectedflight(String a, String b) {
//		connect();
//
//		//SQL
//		findResultDo rdo = new findResultDo();
//		String sql = "select * from flight, airline where flightno =? and flightdate = ? and flightairline = airlinecode";
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, a);
//			pstmt.setString(2, b);
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				rdo.setFlightno(rs.getString(1));
//				rdo.setFlightairline(rs.getString(2));
//				rdo.setFlightfrom(rs.getString(3));
//				rdo.setFlightto(rs.getString(4));
//				rdo.setFlightdate(rs.getString(5));
//				rdo.setTakeofftime(rs.getString(6));
//				rdo.setLandingtime(rs.getString(7));
//				rdo.setAirline(rs.getString(9));
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		disConnect();
//		return rdo;
//	}
	
	//예약 데이터 삽입
//	public void insertreservation(String a, String b, String c, String d, String e, String f, String g, String h) {
//		connect();
//
//		String sql = "insert into reservation values(null,?,?,?,?,?,?,?,?)";
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1,a);
//			pstmt.setString(2,b);
//			pstmt.setString(3,c);
//			pstmt.setString(4,d);
//			pstmt.setString(5,e);
//			pstmt.setString(6,f);
//			pstmt.setString(7,g);
//			pstmt.setString(8,h);
//
//			//4. SQL 실행
//			pstmt.executeUpdate();
//
//			System.out.println("insertreservation() 처리완료!");
//
//		} catch (Exception ex) {
//			//
//			ex.printStackTrace();
//		}
//
//		disConnect();
//	}
	
	//예약 데이터 삭제
//		public void deleteReservation(int a) {
//			connect();
//
//			String sql = "delete from reservation where reserveno = ?";
//
//			try {
//				pstmt = conn.prepareStatement(sql);
//				pstmt.setInt(1,a);
//
//				//4. SQL 실행
//				pstmt.executeUpdate();
//
//				System.out.println("deletereservation() 처리완료!");
//
//			} catch (Exception ex) {
//				//
//				ex.printStackTrace();
//			}
//
//			disConnect();
//		}
	
	
	//사용자 중복확인
	public boolean findAtom(int i, String str) {
		connect();
		String sql = "select * from users";
		try {
			pstmt = conn.prepareStatement(sql);

			//4. SQL문 실행(전송)
			rs = pstmt.executeQuery();
			int a = 1;
			while(rs.next()) {
				findResultDo rdo = new findResultDo();
				if(rs.getString(i).equals(str)) return true;
				a++;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//사용자테이블 삽입
	public void insertRegister(findResultDo rdo) {
		System.out.println("insertRegister() --> ");
		connect();
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		//sql
		//3. SQL문 완성
		String sql = "insert into users values(?,?,?,?,?,"+formatedNow+")";


		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,rdo.getUser_id());
			pstmt.setString(2,rdo.getUser_pw());
			pstmt.setString(3,rdo.getUser_phone());
			pstmt.setString(4,rdo.getUser_email());
			pstmt.setString(5,rdo.getUser_level());
			
			//4. SQL 실행
			pstmt.executeUpdate();
			System.out.println("완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		disConnect( );
	}
	
	//전체 사용자 검색
//	public ArrayList<findResultDo> getAllUsers() {
//		connect();
//
//		ArrayList<findResultDo> aList = new ArrayList<>();
//
//		//3. SQL문 완성
//		String sql = "select * from users";
//		try {
//			pstmt = conn.prepareStatement(sql);
//
//			//4. SQL문 실행(전송)
//			rs = pstmt.executeQuery();
//			int i = 1;
//			while(rs.next()) {
//				findResultDo rdo = new findResultDo();
//				rdo.setUser_id(rs.getString(1));
//				rdo.setUser_pw(rs.getString(2));
//				rdo.setUser_email(rs.getString(6));
//				rdo.setUser_phone(rs.getString(7));
//				rdo.setUser_level(rs.getString(8));
//
//				aList.add(rdo);
//				i++;
//			}
//			//System.out.println("getAllRegister() 처리 완료!!");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//		disConnect();
//		return aList;
//
//	}
	
	//내 정보 검색
		public findResultDo getmyInfo(String user_id) {
			connect();
			
			findResultDo rdo = new findResultDo();
			
			//3. SQL문 완성
			String sql = "select * from users where user_id=?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user_id);

				//4. SQL문 실행(전송)
				rs = pstmt.executeQuery();
				while(rs.next()) {
					rdo.setUser_id(rs.getString(1));
					rdo.setUser_pw(rs.getString(2));
					rdo.setUser_phone(rs.getString(3));
					rdo.setUser_email(rs.getString(4));
					rdo.setUser_created_at(rs.getString(5));

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			disConnect();
			return rdo;
			
		}
		
		//내 정보 수정
				public findResultDo modifymyInfo(String id, findResultDo rdo) {
					connect();
					String agree ="1";
					//3. SQL문 완성
					String sql = "update users set user_pw=?, user_phone=? user_email=? where userid=?";
					try {
						pstmt = conn.prepareStatement(sql);

						pstmt.setString(1,rdo.getUser_pw());
						pstmt.setString(2,rdo.getUser_email());
						pstmt.setString(3,rdo.getUser_phone());
						pstmt.setString(4,rdo.getUser_id());

						//4. SQL문 실행(전송)
						pstmt.executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					disConnect();
					return rdo;
					
				}
				
			
	
	//구매내역 검색
	public ArrayList<findResultDo> findMyPUrchase(String user_id) {
		connect();
		//sql 처리
		ArrayList<findResultDo> aList = new ArrayList<>();
		//3. SQL문 완성
		String sql = "select * from purchase where purchase_user_id = ? order by purchase_date";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);

			//4. SQL문 실행(전송)
			rs = pstmt.executeQuery();
			int i = 1;
			while(rs.next()) {
				findResultDo rdo = new findResultDo();
				rdo.setPurchase_cart_id(rs.getString(1));
				rdo.setUser_pw(rs.getString(2));
				rdo.setUser_email(rs.getString(6));
				rdo.setUser_phone(rs.getString(7));
				rdo.setUser_level(rs.getString(8));
				
				aList.add(rdo);
				i++;
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
		
		disConnect();
		return aList;
		
	}
}
