<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="col">
		<img src="/image/header01.png" width="100%" id="img_header">
		<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
			<div class="container-fluid">
				<a class="navbar-brand" href="/">쇼핑몰</a>
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav me-auto mb-2 mb-lg-0">
						<c:if test="${user.role==1}">
							<li class="nav-item">
								<a class="nav-link active" aria-current="page" href="/goods/search">상품검색</a>
							</li>
							<li class="nav-item">
								<a class="nav-link active" aria-current="page" href="/goods/list">상품목록</a>
							</li>
							<li class="nav-item">
								<a class="nav-link active" aria-current="page" href="/user/list">회원목록</a>
							</li>
							<li class="nav-item">
								<a class="nav-link active" aria-current="page" href="/purchase/list">구매목록</a>
							</li>
							<li class="nav-item dropdown">
								<a class="nav-link dropdown-toggle active" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
						            그래프
						        </a>
						        <ul class="dropdown-menu">
						            <li><a class="dropdown-item" href="/graph/chart1">월별 매출현황</a></li>
						            <li><a class="dropdown-item" href="/graph/chart2">사용자별 댓글,좋아요,구매수</a></li>
						            <li><a class="dropdown-item" href="/graph/chart3">제조사별상품수</a></li>
						        </ul>
							</li>
						</c:if>
						<c:if test="${user.role==2 || user==null}">
							<li class="nav-item">
								<a class="nav-link active" aria-current="page" href="/cart/list">장바구니</a>
							</li>
						</c:if>
					</ul>
					<ul class="navbar-nav  mb-2 mb-lg-0">
						<c:if test="${user==null}">
							<li class="nav-item">
								<a class="nav-link active" aria-current="page" href="/user/login">로그인</a>
							</li>
						</c:if>
						<c:if test="${user!=null}">
							<li class="nav-item">
								<a class="nav-link active" aria-current="page" href="/user/read?uid=${user.uid}">${user.uname}님</a>
							</li>
							<li class="nav-item">
								<a class="nav-link active" aria-current="page" href="/user/logout">로그아웃</a>
							</li>
						</c:if>
					</ul>		
				</div>
			</div>
		</nav>
	</div>
</div>