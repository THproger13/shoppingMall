<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row my-5">
	<div class="col">
		<h1 class="text-center mb-5">구매목록</h1>
		<table class="table">
			<c:forEach items="${array}" var="vo" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td><a href="/purchase/read?pid=${vo.pid}">${vo.pid}</a></td>
					<td>${vo.recName}</td>
					<td>${vo.recAddress1} ${vo.recAddress2}</td>
					<td>${vo.recPhone}</td>
					<td>${vo.purDate}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
