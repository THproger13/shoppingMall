<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="row my-5">
	<div class="col">
		<h1 class="text-center mb-5">상품목록</h1>
		<table class="table">
			<c:forEach items="${array}" var="vo" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td><a href="/goods/read?gid=${vo.gid}">${vo.gid}</a></td>
					<td><img src="${vo.image}" width="50px"></td>
					<td>${vo.title}</td>
					<td><fmt:formatNumber value="${vo.price}" pattern="#,###원"/></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>