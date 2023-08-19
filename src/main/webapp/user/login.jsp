<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row my-5 justify-content-center">
	<div class="col-md-8 col-lg-6 my-5">
		<h1 class="text-center mb-5">로그인</h1>
		<form name="frm" class="card p-3">
			<input name="uid" class="form-control mb-3" placeholder="아이디" value="blue">
			<input name="upass" type="password" class="form-control mb-3" placeholder="비밀번호" value="pass">
			<button class="btn btn-primary w-100">로그인</button>
			<div class="row mt-3">
				<div class="col">
  					<input name="is_check" class="form-check-input" type="checkbox">
  					<span>로그인 상태 저장</span>
				</div>
				<div class="col text-end">
					<a href="/user/insert">회원가입</a>
				</div>
			</div>
		</form>
	</div>
</div>
<script>
	const target="${target}";
	$(frm).on("submit", function(e){
		e.preventDefault();
		const uid=$(frm.uid).val();
		const upass=$(frm.upass).val();
		const is_check=$(frm.is_check).is(":checked");
		if(uid=="" || upass==""){
			alert("아이디와 비밀번호를 입력하세요!");
		}else{
			$.ajax({
				type:"post",
				url:"/user/login",
				data:{uid:uid, upass:upass, is_check},
				success:function(data){
					if(data==0) {
						alert("아이디가 존재하지 않습니다!");
						$(frm.uid).focus();
					}else if(data==2){
						alert("비밀번호가 일치하지 않습니다!");
						$(frm.upass).focus();
					}else{
						alert("로그인에 성공하였습니다!");
						if(target==""){
							location.href="/";
						}else{
							location.href=target;
						}
					}
				}
			});
		}
	});
</script>








