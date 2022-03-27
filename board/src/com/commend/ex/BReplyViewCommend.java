package com.commend.ex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.ex.BDao;
import com.dto.ex.BDto;

public class BReplyViewCommend implements BCommend {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		 String bId = request.getParameter("bId");
	        BDao dao = new BDao();
	        BDto dto = dao.reply_view(bId);
	        
	        request.setAttribute("reply_view", dto);

	}

}
