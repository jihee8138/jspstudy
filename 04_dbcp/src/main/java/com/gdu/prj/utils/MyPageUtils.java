package com.gdu.prj.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data


public class MyPageUtils {
	
	private int total; 			// 전체 게시글 개수 (DB에서 구한다.)
	private int display;	  // 한 페이지에 표시할 게시글 개수 (요청 파라미터로 받는다.)
	private int page; 			// 현재 페이지 번호 (요청 파라미터로 받는다.)
	private int begin;			// 한 페이지에 표시할 게시글의 시작 번호 (계산한다.)
	private int end;				// 한 페이지에 표시할 게시글의 종료 번호 (계산한다.)
	
	private int totalPage;  				// 전체 페이지 개수
	private int pagePerBlock = 10;  // 한 블록에 표시할 페이지 링크의 개수			(계산한다.)
	private int beginPage;					// 한 블록에 표시할 페이지 링크의 시작 번호 (계산한다.)
	private int endPage;						// 한 블록에 표시할 페이지 링크의 종료 번호 (계산한다.)
	
	
	public void setPaging(int total, int display, int page) {
		
		
		this.total = total;
		this.display = display;
		this.page = page;
		
		// 게시글이 가지고 있는 보드 넘버를 페이징 처리할 때 쓰면 안 되는 이유
		// 중간 데이터가 지워지면 번호는 남아있는데 가지고 있는 데이터의 개수가 안 맞을 수 있다.
		// 번호가 절대적인 게시글의 위치 정보가 아니기 때문에
		// 회원도 회원정보로 페이징 처리하면 안 된다 회원이 중간에 탈퇴할 수도 있기 때문에
		// sql rownum으로 페이징 처리하기
		
		begin = (page - 1) * display + 1;
		end  = begin + display - 1;
		
		// total / display 만 하면 정수로 계산되기 때문에 
		// 1001개의 게시물의 경우 페이지가 51개 나와야하는데 50개밖에 나오지 않는다. 
		totalPage = (int)Math.ceil((double)total / display);
		beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1;
		endPage = beginPage + pagePerBlock - 1;
		endPage = Math.min(totalPage, endPage);
	}
	
	public String getPaging(String requestURI, String sort, int display) {
		
		StringBuilder builder = new StringBuilder();
		
		// <
		// "<" 이 웹화면으로 전달되므로 엔티티코드로 바꿔준다.
		if(beginPage == 1)  {  // beginPage 가 1페이지가 나와있으면
			builder.append("<div class=\"dont-clck\">&lt;</div>");
		} else {
			builder.append("<div><a href=\"" + requestURI + "?page=" + (beginPage - 1) + "&sort=" + sort + " &display=" + display + "\">&lt;</a></div>");
		}
		
		// 1 2 3 4 5 6 7 8 9 10
		for(int p = beginPage; p <= endPage; p++) {
			if(p == page) {
				builder.append("<div><a class=\"current-page\" href=\"" + requestURI + "?page=" + p + "&sort= " + sort + "&display=" + display + "\">" + p + "</a></div>");
			} else {
				builder.append("<div><a href=\"" + requestURI + "?page=" + p + "&sort=" + sort + "&display=" + display + "\">" + p + "</a></div>");
			}
		}
		
		// >
		if(endPage == totalPage) {  // endPage가 총 페이지와 같다면
			builder.append("<div class=\"dont-clck\">&gt;</div>");			
		} else {
			builder.append("<div><a href=\"" + requestURI + "?page=" + (endPage + 1) + "&sort=" + sort + "&display=" + display + "\">&gt;</a></div>");
		}
		
		return builder.toString();
	}
	
}
