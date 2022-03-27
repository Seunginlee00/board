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
				dataSource  = (DataSource)context.lookup("java:/comp/env/jdbc/Oracle11g");


				//dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g"); // lookup : 바라보다 
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
	                int bId = rs.getInt("bId");
	                String bName = rs.getString("bName");
	                String bTitle = rs.getString("bTitle");
	                String bContent = rs.getString("bContent");
	                Timestamp bDate = rs.getTimestamp("bDate");
	                int bHit = rs.getInt("bHit");
	                int bGroup = rs.getInt("bGroup");    // 게시글과 답글을 묶는 그룹
	                int bStep = rs.getInt("bStep");        // 게시글을 기준으로 몇번째 밑에 있는 답글을 달지의 단계
	                int bIndent = rs.getInt("bIndent");    // 얼마만큼 안쪽으로 들어가서 글을 시작할지의 수
	                
	                dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
	            }
			
		}catch (NumberFormatException e) {
			e.printStackTrace();
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
			String query = "update mvc_board set bHit = bHit + 1 where bId= ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			int rn = pstmt.executeUpdate();
		}catch (NumberFormatException e) {
			e.printStackTrace();
			
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
			
			String query = "update mvc_board set bName=?, bTitle=?, bContent=? where bId=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
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
    public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent) {
        // TODO Auto-generated method stub
        
        // 계속 답글을 달려고 할 때 step 과 indent가 더해지면 안되니 조건을 거는 함수필요
        replyShape(bGroup, bStep);
        
  
        
        try {
            conn = dataSource.getConnection();
            String query = "insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);
            
            //pstmt.setInt(1, Integer.parseInt(bId) + 1); 
            pstmt.setString(1, bName);
            pstmt.setString(2, bTitle);
            pstmt.setString(3, bContent);
            pstmt.setInt(4, Integer.parseInt(bGroup));    // 게시글과 같은 그룹이라서 같음
            pstmt.setInt(5, Integer.parseInt(bStep) + 1);    // 게시글 기준 몇번째 밑에 있는 답글인지
            pstmt.setInt(6, Integer.parseInt(bIndent) + 1);    // 얼만큼 안쪽으로 들어가서 글을 시작할지
            int result = pstmt.executeUpdate();
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            try {
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch (Exception e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
        }
    }


	private void replyShape(String strGroup, String strStep) {
		try {
			conn = dataSource.getConnection();
			String query = "update mvc_board set bStep=bStep+1 where bGroup=? and bStep> ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, Integer.parseInt(strGroup));
			pstmt.setInt(2, Integer.parseInt(strStep));
			
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


	public void write(String bName, String bTitle, String bContent) { //글 작성 메서드
		// TODO Auto-generated method stub
		
		try {
			conn = dataSource.getConnection();
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0 )";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			int rn = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}

	public BDto reply_view(String str_bId) {
        // TODO Auto-generated method stub
        BDto dto = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = dataSource.getConnection();
            String query = "select * from mvc_board where bId=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(str_bId));
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int bId = rs.getInt("bId");
                String bName = rs.getString("bName");
                String bTitle = rs.getString("bTitle");
                String bContent = rs.getString("bContent");
                Timestamp bDate = rs.getTimestamp("bDate");
                int bHit = rs.getInt("bHit");
                int bGroup = rs.getInt("bGroup");    // 게시글과 답글을 묶는 그룹
                int bStep = rs.getInt("bStep");        // 게시글을 기준으로 몇번째 밑에 있는 답글을 달지의 단계
                int bIndent = rs.getInt("bIndent");    // 얼마만큼 안쪽으로 들어가서 글을 시작할지의 수
                
                dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch (Exception e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
        }
        
        return dto;
    }


}
