<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	 .bi-suit-heart,  .bi-suit-heart-fill{
		color: red;
	}
	.bi {
		font-size:1.2rem;
		cursor: pointer;
	}

	#rcnt, #fcnt {
		font-size:0.7rem;
	}
</style>
<div class="row my-5  justify-content-center">
	<div class="col-md-10 col-lg-8">
		<h1 class="text-center mb-5">상품정보</h1>
		<div class="card p-3">
			<div class="row">
				<div class="col-lg-4 text-center">
					<img src="${vo.image}" width="100%">
				</div>
				<div class="col">
					<hr>
					<div>상품코드: ${vo.gid}</div>
					<div>상품이름: ${vo.title}</div>
					<hr>
					<div class="my-2">상품가격: <fmt:formatNumber value="${vo.price}" pattern="#,###원"/></div>
					<div class="my-2">제조사: ${vo.maker}</div>
					<div class="my-2">상품등록일: ${vo.regDate}</div>
					<hr>
					<div class="row">
						<div class="col-6">
							<button class="btn btn-primary" id="btn-cart">장바구니</button>
						</div>
						<div class="col-6 text-end" id="count">
							<i id="heart" class="bi bi-suit-heart ms-3"></i>
							<span id="fcnt"></span>
							<i class="bi bi-chat-left-text ms-3" id="btn-tab-review"></i>
							<span id="rcnt"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="my-5">
			<ul class="nav nav-tabs" id="myTab" role="tablist">
			  <li class="nav-item" role="presentation">
			    <button class="nav-link active" id="detail-tab" data-bs-toggle="tab" data-bs-target="#detail-tab-pane" type="button" role="tab" aria-controls="deatil-tab-pane" aria-selected="true">
			    	상세설명
			    </button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="review-tab" data-bs-toggle="tab" data-bs-target="#review-tab-pane" type="button" role="tab" aria-controls="review-tab-pane" aria-selected="false">
			    	고객리뷰
			    </button>
			  </li>
			  <li class="nav-item" role="presentation">
			    <button class="nav-link" id="attach-tab" data-bs-toggle="tab" data-bs-target="#attach-tab-pane" type="button" role="tab" aria-controls="attach-tab-pane" aria-selected="false">
			    	첨부이미지
			    </button>
			  </li>
			</ul>
			<div class="tab-content pt-5" id="myTabContent">
				<!-- 상세설명 -->
				<div class="tab-pane fade show active" id="detail-tab-pane" role="tabpanel" aria-labelledby="detail-tab" tabindex="0">
					<div>${vo.content}</div>
				</div>	
				<!-- 상세설명 -->
				<div class="tab-pane fade" id="review-tab-pane" role="tabpanel" aria-labelledby="review-tab" tabindex="1">
					<jsp:include page="review.jsp"/>
				</div>
				<!-- 첨부이미지 -->
				<div class="tab-pane fade" id="attach-tab-pane" role="tabpanel" aria-labelledby="attach-tab" tabindex="2">
					<div id="div_attached" class="p-3 my-3"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 첨부한 파일목록 템플릿 -->
<script id="temp_attached" type="x-handlebars-template">
    <div class="row">
        {{#each .}}
            <div class="col-6 col-md-3 col-lg-2 mb-2">
				<div class="card p-2">
					<img src="{{image}}" width="100%">
				</div>
			</div>
        {{/each}}
    </div>
</script>

<script>
	getFavorite();
	function getFavorite(){
		$.ajax({
			type:"get",
			url:"/favorite/read",
			data:{gid, uid},
			dataType:"json",
			success:function(data){
				console.log(data);
				$("#fcnt").html(data.fcnt);
				if(data.ucnt==1){
					$("#heart").addClass("bi-suit-heart-fill").removeClass("bi-suit-heart");
				}else{
					$("#heart").addClass("bi-suit-heart").removeClass("bi-suit-heart-fill");
				}
			}
		});
	}
	
	//빈하트를 클릭한 경우
	$("#count").on("click", ".bi-suit-heart" ,function(){
		if(uid=="") {
			location.href="/user/login?target=/goods/read?gid=" + gid;
		}else{
			if(confirm("좋아요!를 추가하실래요")){
				$.ajax({
					type:"get",
					url:"/favorite/insert",
					data:{gid, uid},
					success:function(){
						 getFavorite();
					}
				});
			}
		}		
	});
	
	//채운하트를 클릭한 경우
	$("#count").on("click", ".bi-suit-heart-fill", function(){
		if(confirm("좋아요!를 삭제하실래요")){
			$.ajax({
				type:"get",
				url:"/favorite/delete",
				data:{gid, uid},
				success:function(){
					 getFavorite();
				}
			});
		}		
	});
	
	$("#btn-cart").on("click", function(){
		$.ajax({
			type:"get",
			url:"/cart/insert",
			data: {gid:gid},
			success:function(){
				if(confirm("계속 쇼핑하실래요?")) {
					location.href="/";
				}else{
					location.href="/cart/list";
				}
			}
		});
	});
	
	getAttach();
	function getAttach(){
		$.ajax({
			type:"get",
			url:"/attach/list.json",
			data:{"gid": gid},
			dataType:"json",
			success:function(data){
				const temp=Handlebars.compile($("#temp_attached").html());
				$("#div_attached").html(temp(data));
				if(data==0) {
					$("#div_attached").html("첨부한 이미지");
				}
			}
		});
	}
	
	$("#btn-tab-review").on("click", function(){
		$("#review-tab").click();
	});
</script>








