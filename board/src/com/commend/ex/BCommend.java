package com.commend.ex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BCommend {
	void execute(HttpServletRequest request, HttpServletResponse response);
}
