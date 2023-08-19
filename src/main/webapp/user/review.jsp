<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row my-5">
	<div class="col">
		<h1 class="text-center mb-5">리뷰목록</h1>
		<table class="table">
			<c:forEach items="${array}" var="vo" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td width="10%"><a href="/goods/read?gid=${vo.gid}">${vo.gid}</a></td>
					<td width="70%"><div class="ellipsis">${vo.content}</div></td>
					<td width="20%">${vo.revDate}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>