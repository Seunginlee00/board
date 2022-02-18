package com.servlet.ex;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commend.ex.BCommend;
import com.commend.ex.BContentCommend;
import com.commend.ex.BListCommend;
import com.commend.ex.BModifyCommend;
import com.commend.ex.BReplyCommend;
import com.commend.ex.BdeleteCommend;

/**
 * Servlet implementation class BFrontCon
 */
@WebServlet("*.do")
public class BFrontCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BFrontCon() {
       // 디폴트 생성자 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request,response);
	}
	
	protected void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String viewPage  = null;
		BCommend commend = null;
		
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length()); // do 윗부분을 짤라냄
		
		if(com.equals("/writeView.do")) {
			viewPage = "writeView.jsp";
		}else if(com.equals("/write.do")) {
			///commend = new BwriteCommend(); // 생성해야함
			commend.execute(request,response);
			viewPage = "list.do";
		}else if(com.equals("/list.do")) {
			commend = new BListCommend(); 
			commend.execute(request,response);
			viewPage = "list.jsp";
		}else if(com.equals("/contentView.do")) {
			commend = new BContentCommend(); // 생성해야함
			commend.execute(request,response);
			viewPage = "contentView.jsp";
		}else if(com.equals("/modify.do")) {
			commend = new BModifyCommend(); // 생성해야함
			commend.execute(request,response);
			viewPage = "lit.do";
		}else if(com.equals("/delete.do")) {
			commend = new BdeleteCommend(); // 생성해야함
			commend.execute(request,response);
			viewPage = "list.do";
		}else if(com.equals("/replyView.do")) {
			//commend = new BReplyViewCommend(); // 생성해야함
			commend.execute(request,response);
			viewPage = "replyView.jsp";
		}else if(com.equals("/reply.do")) {
			commend = new BReplyCommend(); // 생성해야함
			commend.execute(request,response);
			viewPage = "list.do";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}

}
