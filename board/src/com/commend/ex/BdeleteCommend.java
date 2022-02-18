package com.commend.ex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.ex.BDao;

public class BdeleteCommend implements BCommend {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String  bId = request.getParameter("bId");
		BDao dao = new BDao();
		dao.delete(bId);
	}

}
