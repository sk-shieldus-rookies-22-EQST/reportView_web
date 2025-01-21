package com.skrookies.dahaezlge.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//import finProj_counrty.countryDo;

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
		String sql = "select * from membership";
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
		
		//sql
		//3. SQL문 완성
		String sql = "insert into membership values(?,?,?,?,?,?,?,?,'white')";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,rdo.getUserid());
			pstmt.setString(2,rdo.getUserpw());
			pstmt.setString(3,rdo.getUsername());
			pstmt.setString(4,rdo.getUseremail());
			pstmt.setString(5,rdo.getUsertel());
			pstmt.setString(6,rdo.getUsergrade());
			
			//4. SQL 실행
			pstmt.executeUpdate();
			System.out.println("완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		disConnect( );
	}
	
	//전체 사용자 검색
	public ArrayList<findResultDo> getAllUsers() {
		connect();
		
		ArrayList<findResultDo> aList = new ArrayList<>();
		
		//3. SQL문 완성
		String sql = "select * from membership";
		try {
			pstmt = conn.prepareStatement(sql);

			//4. SQL문 실행(전송)
			rs = pstmt.executeQuery();
			int i = 1;
			while(rs.next()) {
				findResultDo rdo = new findResultDo();
				rdo.setUserid(rs.getString(1));
				rdo.setUserpw(rs.getString(2));
				rdo.setUsername(rs.getString(3));
				rdo.setUsercountry(rs.getString(4));
				rdo.setUserbirth(rs.getString(5));
				rdo.setUseremail(rs.getString(6));
				rdo.setUsertel(rs.getString(7));
				rdo.setUseragree(rs.getString(8));
				
				aList.add(rdo);
				i++;
			}
			//System.out.println("getAllRegister() 처리 완료!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		disConnect();
		return aList;
		
	}
	
	//내 정보 검색
		public findResultDo getmyInfo(String a) {
			connect();
			
			findResultDo rdo = new findResultDo();
			
			//3. SQL문 완성
			String sql = "select * from membership where userid=?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, a);

				//4. SQL문 실행(전송)
				rs = pstmt.executeQuery();
				while(rs.next()) {
					rdo.setUserid(rs.getString(1));
					rdo.setUserpw(rs.getString(2));
					rdo.setUsername(rs.getString(3));
					rdo.setUsercountry(rs.getString(4));
					rdo.setUserbirth(rs.getString(5));
					rdo.setUseremail(rs.getString(6));
					rdo.setUsertel(rs.getString(7));
					rdo.setUseragree(rs.getString(8));
					rdo.setUsergrade(rs.getString(9));
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
					String sql = "update membership set userpw=?, username=?, usercountry=?, userbirth=?, useremail=?, usertel=?, useragree=? where userid=?";
					if(rdo.getUseragree() == null ) {
						agree = "0";
					}
					try {
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, rdo.getUserpw());
						pstmt.setString(2, rdo.getUsername());
						pstmt.setString(3, rdo.getUsercountry().toUpperCase());
						pstmt.setString(4, rdo.getUserbirth());
						pstmt.setString(5, rdo.getUseremail());
						pstmt.setString(6, rdo.getUsertel());
						pstmt.setString(7, agree);
						pstmt.setString(8, id);

						//4. SQL문 실행(전송)
						pstmt.executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					disConnect();
					return rdo;
					
				}
				
			
	
	//내 예약 검색
	public ArrayList<findResultDo> findMyReserves(String a) {
		connect();
		//sql 처리
		ArrayList<findResultDo> aList = new ArrayList<>();
		//3. SQL문 완성
		String sql = "select * from reservation where userid = ? order by reserveno";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,a);

			//4. SQL문 실행(전송)
			rs = pstmt.executeQuery();
			int i = 1;
			while(rs.next()) {
				findResultDo rdo = new findResultDo();
				rdo.setReserveno(rs.getString(1));
				rdo.setUserid(rs.getString(2));
				rdo.setReservedate(rs.getString(3));
				rdo.setGoflightdate(rs.getString(4));
				rdo.setGoflightairline(rs.getString(5));
				rdo.setGoflightno(rs.getString(6));
				rdo.setBackflightdate(rs.getString(7));
				rdo.setBackflightairline(rs.getString(8));
				rdo.setBackflightno(rs.getString(9));
				
				aList.add(rdo);
				i++;
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
		
		disConnect();
		return aList;
		
	}
	
	//출발지(국가) 검색
	public String findCountry(String a) {
		String str = "";
		connect();
		
		//SQL
		findResultDo rdo = new findResultDo();
		String sql = "select country from flight, airport where flightfrom = ? and flightfrom = airportcode";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				str = rs.getString(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		disConnect();
		return str;
	}
}
