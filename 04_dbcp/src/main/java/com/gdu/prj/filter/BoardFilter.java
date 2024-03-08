package com.gdu.prj.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class BoardFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// HttpServletRequest  /  HttpServletResponse
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		// 요청 UTD-8 인코딩
		req.setCharacterEncoding("UTF-8");
		
		// 요청 방식 확인 + 요청 주소 확인
		System.out.print(String.format("%-4s", req.getMethod()));
		System.out.print(" | ");
		System.out.println(req.getRequestURI());
		
		// 요청 파라미터 확인
		Map<String, String[]> params = req.getParameterMap();
		// Map 에 for 문 돌리기
		// key 와 value 의 조합 : entry (파라미터 이름과 파라미터 값)
		for(Map.Entry<String, String[]> param : params.entrySet()) {
			System.out.print(String.format("%7s", " "));
			System.out.print(String.format("%-10s", param.getKey()) + " : ");
			System.out.println(Arrays.toString(param.getValue()));
		}
		
		
		chain.doFilter(request, response);
		
	}


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
