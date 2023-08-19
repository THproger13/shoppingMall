<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<div class="row">
	<div class="col p-5">
		 <div id="chart" style="width:100%; height: 500px" class="card"></div>
	</div>
</div>
<script>
	function chart(rows) {
		google.charts.load('current', {'packages':['corechart']});
		google.charts.setOnLoadCallback(drawChart);
		
		function drawChart() {
	        var data = google.visualization.arrayToDataTable(rows);
	        var options = {
		          title: '2023년 월별 매출현황',
		          //curveType: 'function',
		          //legend: { position: 'bottom' },
	        };
	        var chart = new google.visualization.LineChart(document.getElementById('chart'));
	        chart.draw(data, options);
	      }
	}
	
	getProcedure1();
	function getProcedure1(){
		$.ajax({
			type:"get",
			url:"/graph/procedure1.json",
			dataType:"json",
			success:function(data){
				let rows=[];
				rows.push(["월", "매출액"])
				data.forEach(item=>{
					const row=[item.yymm, item.sum];
					rows.push(row);
				});
				chart(rows);
			}
		});
	}
</script>