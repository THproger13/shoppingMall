<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	span{
		width: 150px;
		justify-content: center;
	}
	#image {
		border-radius:40%;
		border: 3px solid gray;
	}
	
	.rcnt, .fcnt, .ucnt {
		font-size: 0.5rem;
	}
	
	.bi-suit-heart-fill {
		color: red;
	}
	
	.bi {
		font-size:1.2rem;
	}
</style>
<div class="row my-5 justify-content-center">
	<div class="col-md-10 col-lg-8">
		<h1 class="text-center mb-5">회원정보</h1>
		<div class="card p-3">
			<div>
				<c:if test="${vo.photo==null || vo.photo==''}">
					<img src="http://via.placeholder.com/100x100" width="80px" id="image">
				</c:if>
				<c:if test="${vo.photo!=null}">
					<img src="${vo.photo}" width="80px" id="image">
				</c:if>
				<span class="w-100 ms-3"> ${vo.uname } ${vo.uid }</span>
			</div>
			<hr>
			<div class="input-group mb-3">
				<span class="input-group-text">전화번호</span>
				<input name="phone" class="form-control" value="${vo.phone}" disabled>
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">주소1</span>
				<input name="address1" class="form-control" value="${vo.address1}" disabled>
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">주소2</span>
				<input name="address2" class="form-control" value="${vo.address2}" disabled>
			</div>
			<c:if test="${user.uid == vo.uid }">
				<div class="text-center my-3">
					<a href="/user/update?uid=${vo.uid}" class="btn btn-primary px-5">정보수정</a>
				</div>
			</c:if>
			<hr>
			<div class="text-end">
				<a href="/user/favorite?uid=${vo.uid }"><i id="heart" class="bi bi-suit-heart-fill"></i></a>
				<span class="fcnt">${vo.fcnt}</span>
				<a href="/user/review?uid=${vo.uid}"><i class="bi bi-chat-left-text ms-3"></i></a>
				<span class="rcnt">${vo.rcnt}</span>
				<a href="/user/purchase?uid=${vo.uid}"><i class="bi bi-cart-check ms-3"></i></a>
				<span class="ucnt">${vo.ucnt}</span>
			</div>
		</div>	
	</div>
</div>