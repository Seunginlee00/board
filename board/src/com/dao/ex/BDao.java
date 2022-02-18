package com.dao.ex;

import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.naming.Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.dto.ex.BDto;
import java.sql.Timestamp;

public class BDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		private DataSource dataSource;
		//Context : 구분 문맥
		// 
		public BDao() {
			try {
				Context context = new InitialContext(); // 구문을 해석하는 역할
				dataSource = (DataSource)context.lookup("java:comp/env/jdbc_mariadb"); // lookup : 바라보다 
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		
	
	public ArrayList<BDto> list(){
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BDto dto = null;
		try {
			conn = dataSource.getConnection();
			String query = "select * from mvc_board order by bGroup desc, bStep asc";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int b_id = rs.getInt("bId");
				String b_name = rs.getNString("bName");
				String b_title = rs.getNString("bTitle");
				String b_content = rs.getNString("bContent");
				Timestamp b_date = rs.getTimestamp("bDate");
				int b_hit = rs.getInt("bHit");
				int b_group = rs.getInt("bGroup");
				int b_step = rs.getInt("bStep");
				int b_indent = rs.getInt("bIndent");
				dto = new BDto(b_id, b_name, b_title, b_content, b_date, b_hit, b_group, b_step, b_indent);
				dtos.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dtos;
	}

	
	public BDto contentView(String strID) {
		upHit(strID);
		
		BDto dto = null;
		try {
			conn = dataSource.getConnection();
			
			String query = "select * from mvc_board where bId=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strID));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int b_id = rs.getInt("bId");
				String b_name = rs.getNString("bName");
				String b_title = rs.getNString("bTitle");
				String b_content = rs.getNString("bContent");
				Timestamp b_date = rs.getTimestamp("bDate");
				int b_hit = rs.getInt("bHit");
				int b_group = rs.getInt("bGroup");
				int b_step = rs.getInt("bStep");
				int b_indent = rs.getInt("bIndent");
				dto = new BDto(b_id, b_name, b_title, b_content, b_date, b_hit, b_group, b_step, b_indent);
						
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
				try {
					if (rs != null) rs.close();
					if (pstmt != null) pstmt.close();
					if (conn != null) conn.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}	
		
		}
		return dto;
	}
	
	private void upHit(String bId) {
		try {
			conn = dataSource.getConnection();
			String query = "update mvc_board set bHit = bHit + 1 where bId=?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, Integer.parseInt(bId));
			int rn = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
				try {
					if (pstmt != null) pstmt.close();
					if (conn != null) conn.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}	
		
		}
	}
	
	public void modify(String bId, String bName, String bTitle, String bContent) {
		try {
			conn = dataSource.getConnection();
			
			String query = "update mvc_board set bName=?, bTile=?, bContent=? where bId=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
			int rn= pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
				try {
					if (pstmt != null) pstmt.close();
					if (conn != null) conn.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}	
		
		}
	}
	
	public void delete(String bId) {
		try {
			conn = dataSource.getConnection();
			String query = "delete from mvc_board where bId=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,Integer.parseInt(bId));
			int rn = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
				try {
					if (pstmt != null) pstmt.close();
					if (conn != null) conn.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}	
			}
		}
	public void reply(String bId, String bName, String bTitle, String bContent,String bGroup, String bStep, String bIndent) {
		replyShape(bGroup,bStep);
		
		try {
			conn = dataSource.getConnection();
			String query = "insert into mvc_board (bId,bName,bTitle,bContent,bGroup,bStep,bIndent) values (mac_board_seq.nextval,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,bName);
			pstmt.setString(2,bTitle);
			pstmt.setString(3,bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(4, Integer.parseInt(bStep) +  1);
			pstmt.setInt(4, Integer.parseInt(bIndent) + 1);
			int rn = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
				try {
					if (pstmt != null) pstmt.close();
					if (conn != null) conn.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}	
			}
		}
	private void replyShape(String strGroup, String strStep) {
		try {
			conn = dataSource.getConnection();
			String query = "update mvc_board set bStep=bStep+1 where bGrup=? and bStep> ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, Integer.parseInt(strGroup));
			pstmt.setInt(1, Integer.parseInt(strStep));
			
			int rn = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
				try {
					if (pstmt != null) pstmt.close();
					if (conn != null) conn.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}	
			}
	}
}
