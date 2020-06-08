package AP;
//아직 고려하지 않은 내용들

//- 오늘 날짜 강조 할것인가.

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JOptionPane;

//AP_DAO : 저장하기, 불러오기, 계산하기. 
//저장하기 >> 1건의 내역 저장(등록 수정 삭제)
//불러오기 >> 1건의 내역 불러오기(인수는 일), 월별 내역 불러오기(인수는 연, 월)
//계산하기 >> 하루총지출 / 수입계산
//월별총지출/ 수입계산  
public class DAO {
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR); // 올해
	int month = cal.get(Calendar.MONTH) + 1; // 이번 달
	int startDay, lastDay; // 달력의 매월 시작 날짜, 마지막 날짜
	int today = cal.get(Calendar.DATE); // 이번 달의 오늘 날짜

	public static ArrayList<VO> select(String writeDate) {
		System.out.println(writeDate);
		ArrayList<VO> list = null;
		try {
			Connection conn = DBUtil.getMySqlConnection();
			String sql = "select * from apDB where writeDate = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, writeDate);
			ResultSet rs = pstmt.executeQuery();
//			ResultSet 객체에서 얻어온 테이블의 글 목록을 ArrayList에 저장한다.
			list = new ArrayList<VO>();
			while (rs.next()) {
				VO vo = new VO();
				vo.setWriteDate(rs.getString("writeDate"));
				vo.setType(rs.getString("type"));
				vo.setItem(rs.getString("item"));
				vo.setMoney(rs.getInt("money"));
				vo.setMemo(rs.getString("memo"));
				list.add(vo);
			}
			DBUtil.close(conn);
			DBUtil.close(pstmt);
			DBUtil.close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			;
		}
		return list;
	}

	public static void insert(VO vo) {
//    금액, 메모가 입력되었는지 확인
		boolean flag = true;
		if (vo.getMoney() == 0) {
			JOptionPane.showMessageDialog(null, "금액이 입력되지 않았습니다.");
			flag = false;
		}
//	금액이 모두 입력되었으면 테이블에 저장한다.
		if (flag) {
			try {
				Connection conn = DBUtil.getMySqlConnection();
				String sql = "insert into apDB (writedate, type, item, money, memo) values (?, ?, ?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getWriteDate());
				pstmt.setString(2, vo.getType());
				pstmt.setString(3, vo.getItem());
				pstmt.setInt(4, vo.getMoney());
				pstmt.setString(5, vo.getMemo());
				pstmt.executeUpdate();
				DBUtil.close(conn);
				DBUtil.close(pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void update(VO vo, String[] p_field) {

		try {
			Connection conn = DBUtil.getMySqlConnection();
			String sql = "update apDB set type = ?, item = ?, money = ?, memo = ? "
					+ "where type = ? and item =? and money = ? and memo = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getType());
			pstmt.setString(2, vo.getItem());
			pstmt.setInt(3, vo.getMoney());
			pstmt.setString(4, vo.getMemo());
			pstmt.setString(5, p_field[1]);
			pstmt.setString(6, p_field[2]);
			pstmt.setInt(7, Integer.parseInt(p_field[3]));
			pstmt.setString(8, p_field[4]);
			pstmt.executeUpdate();
			DBUtil.close(conn);
			DBUtil.close(pstmt);
			// executeUpdate() 메소는 sql 명령이 실행되고 난 후 정상적으로 실행된 sql 명령의 개수를 리턴한다.
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void delete(VO vo) {

		try {
			Connection conn = DBUtil.getMySqlConnection();
			String sql = "delete from apDB where writeDate = ? and type = ? and item =? and money = ? and memo = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getWriteDate());
			pstmt.setString(2, vo.getType());
			pstmt.setString(3, vo.getItem());
			pstmt.setInt(4, vo.getMoney());
			pstmt.setString(5, vo.getMemo());
			pstmt.executeUpdate();
			DBUtil.close(conn);
			DBUtil.close(pstmt);
			// executeUpdate() 메소는 sql 명령이 실행되고 난 후 정상적으로 실행된 sql 명령의 개수를 리턴한다.
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int[] typeSum(String writeDate) {
		String[] type = {"지출", "수입"};
		int[] typeSum = new int[type.length];
		
		try {
			for (int i = 0; i < type.length; i++) {
				Connection conn = DBUtil.getMySqlConnection();
				String sql = "select sum(money) from apDB where writeDate = ? and type = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, writeDate);
				pstmt.setString(2, type[i]);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					typeSum[i] = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return typeSum;
	}

	public static int[] itemSum(String yearMonth, String[] itemCombo) {
		int[] itemSum = new int[itemCombo.length];
		try {
			for (int i = 0; i < itemCombo.length; i++) {
				Connection conn = DBUtil.getMySqlConnection();
				String sql = "select sum(money) from apDB where writeDate like ? and item = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, yearMonth+"%");
				pstmt.setString(2, itemCombo[i]);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					itemSum[i] = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemSum;
	}

	public static void monthSum() {

	}

//	1번째 페이지에서 필요한 기능들 

//	-켈린더 파트
//	1. 처음 프로그램 실행 시 today를 받아 해당 달을 텍스트 필드에 출력하기 위한 코드
//	2. 사용자가 원하는 날짜(년,월)을 입력 받고 해당 달을 출력
//===========================
//	- 해당 월 수입 지출 파트
//	1. 지금까지 입력한 데이터를 수식하여 계산
//	=========================

	// DB 연결, 테이블 컬럼까지는 미리 SQL에 만들어두기

//	2,3 번째 페이지에서 필요한 기능들

//	-  내역 파트
//	* 금액이 기입이 되지 않았다면 "기입이 되지 않았습니다" 팝업 메세지 출력 
//	1. DB 내용을 불러와서 줄대로 출력하는 코드
//	- 기입 내용 파트 (뷰에서 클릭된 값을 받아서 그 값을 가지고 기능구현)
//	1. 뷰단에서 클릭하면 지출 / 수입 구분하여 기억
//	2. 항목 콤보박스에서 선택된 내용 기억 
//	3. 금액 및 메모에 기입된 내용 기억
//	- 버튼 파트 
//	1. 추가를 누르면 SQL 명령어 INSERT 코드 실행 (위 내용 모두 DB로 정렬/구분하여 저장)
//	2. 수정을 누르면 SQL 명령어 UPDATE 코드 실행 (새로 기입된 정보로 저장)
//	3. 삭제를 누르면 SQL 명령어 DELETE 코드 실행 (MySQL 데이터 삭제)	

}
