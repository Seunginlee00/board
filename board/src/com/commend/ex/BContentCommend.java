package com.commend.ex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.ex.BDao;
import com.dto.ex.BDto;

public class BContentCommend implements BCommend {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String bId = request.getParameter("bId");
		System.out.println("글번호: "+ request.getParameter("bId"));
		
		BDao dao = new BDao();
		BDto dto = dao.contentView(bId);
		
		request.setAttribute("contentView", dto);

	}

}
