<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.uid {
		font-size:0.8rem;
		text-align:center;
	}
	.bi-suit-heart-fill{
		color: red;
	}

	.bi {
		font-size:0.8rem;
	}
	
	.rcnt, .fcnt, .ucnt {
		font-size:0.5rem;
	}
</style>
<div class="row my-5">
	<div class="col">
		<h1 class="text-center mb-5">회원목록</h1>
		<div class="row">
			<form class="col-6" name="frm">
				<div class="input-group">
					<select class="form-select" name="key">
						<option value="uid">아이디</option>
						<option value="uname" selected>회원이름</option>
						<option value="address1">회원주소</option>
						<option value="phone">회원전화</option>
					</select>&nbsp;
					<input class="form-control" placeholder="검색어" name="query">
					<input type="submit" value="검색" class="btn btn-primary">
				</div>
			</form>
		</div>
		<hr>
		<div id="div_user" class="row"></div>
		<div id="pagination" class="pagination justify-content-center mt-3"></div>
	</div>
</div>
<!-- 회원목록 템플릿 -->
<script id="temp_user" type="x-handlebars-template">
	{{#each .}}
	<div class="col-6 col-md-4 col-lg-2">
		<div class="card mb-3">
			<div class="card-body p-3 text-center">
			<a href="/user/read?uid={{uid}}"><img src="{{photo photo}}" width="50%"></a>
			</div>
			<div class="uid">{{uname}} ({{uid}})</div>
			<div class="card-footer text-end">
				<i id="heart" class="bi bi-suit-heart-fill"></i>
				<span class="fcnt">{{fcnt}}</span>
				<i class="bi bi-chat-left-text ms-2"></i>
				<span class="rcnt">{{rcnt}}</span>
				<i class="bi bi-cart-check ms-2"></i>
				<span class="ucnt">{{ucnt}}</span>
			</div>
		</div>
	</div>
	{{/each}}
</script>
<script>
	Handlebars.registerHelper("photo", function(photo){
		if(!photo){
			return "http://via.placeholder.com/100x100";
		}else{
			return photo;
		}
	});
</script>
<script>
	let page=1;
	let query=$(frm.query).val();
	let key=$(frm.key).val();
	
	$(frm).on("submit", function(e){
		e.preventDefault();
		query=$(frm.query).val();
		key=$(frm.key).val();
		page=1;
		getTotal();
	});
	
	function getList(){
		$.ajax({
			type:"get",
			url:"/user/list.json",
			data:{key:key,query:query, page:page},
			dataType:"json",
			success:function(data){
				const temp=Handlebars.compile($("#temp_user").html());
				$("#div_user").html(temp(data));
			}
		});
	}
	
	getTotal();
	function getTotal(){
		$.ajax({
			type:"get",
			url:"/user/total",
			data:{query:query, key:key},
			success:function(data){
				if(data==0){
					$("#div_user").html("<h3 class='text-center my-5'>검색회원이 존재하지 않습니다.</h3>");
				}else{
					const totalPages=Math.ceil(data/6);
					$("#pagination").twbsPagination("changeTotalPages", totalPages, page);
				}
				if(data > 6){
					$("#pagination").show();
				}else{
					$("#pagination").hide();
				}
			}
		});
	}
	
	$('#pagination').twbsPagination({
	    totalPages:10,	// 총 페이지 번호 수
	    visiblePages: 5,	// 하단에서 한번에 보여지는 페이지 번호 수
	    startPage : 1, // 시작시 표시되는 현재 페이지
	    initiateStartPageClick: false,	// 플러그인이 시작시 페이지 버튼 클릭 여부 (default : true)
	    first : '<<',	// 페이지네이션 버튼중 처음으로 돌아가는 버튼에 쓰여 있는 텍스트
	    prev : '<',	// 이전 페이지 버튼에 쓰여있는 텍스트
	    next : '>',	// 다음 페이지 버튼에 쓰여있는 텍스트
	    last : '>>',	// 페이지네이션 버튼중 마지막으로 가는 버튼에 쓰여있는 텍스트
	    onPageClick: function (event, curPage) {
	    	page=curPage;
	    	getList();
	    }
	});
</script>























