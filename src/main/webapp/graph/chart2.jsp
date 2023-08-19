<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<style>
	text {
		font-family: 'GmarketSansMedium';
	}
</style>
<div class="row">
	<div class="col p-5">
		 <div id="chart" style="width:100%; height: 500px"></div>
	</div>
</div>
<script>
	function chart(rows){
		google.charts.load('current', {packages: ['corechart', 'bar']});
		google.charts.setOnLoadCallback(drawBarColors);

		function drawBarColors() {
		      var data = google.visualization.arrayToDataTable(rows);
		      var options = {
		        title: '사용자별 좋아요,댓글, 상품구매수',
		        chartArea: {width: '70%'},
		        colors: ['red', 'yellow', 'green'],
		        hAxis: {
		          title: '횟수',
		          minValue: 0
		        },
		        vAxis: {
		          title: '이름'
		        }
		      };
		      var chart = new google.visualization.BarChart(document.getElementById('chart'));
		      chart.draw(data, options);
		    }
	}
	
	getProcedure2();
	function getProcedure2(){
		$.ajax({
			type:"get",
			url:"/graph/procedure2.json",
			dataType:"json",
			success:function(data){
				let rows=[];
				rows.push(["회원명", "좋아요수", "댓글수", "구매수"])
				data.forEach(item=>{
					const row=[item.uname, item.fcnt, item.rcnt, item.pcnt];
					rows.push(row);
				});
				chart(rows);
			}
		});
	}
</script>