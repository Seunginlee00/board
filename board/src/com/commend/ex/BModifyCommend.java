package com.commend.ex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.ex.BDao;

public class BModifyCommend implements BCommend {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTile = request.getParameter("bTile");
		String bContent = request.getParameter("bContent");
		
		BDao dao = new BDao();
		dao.modify(bId,bName,bTile,bContent);
	}

}
