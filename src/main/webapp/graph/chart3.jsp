<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<div class="row">
	<div class="col p-5">
		 <div id="chart" style="width:100%; height: 500px" class="card"></div>
	</div>
</div>
<script>
	function chart(rows){
		google.charts.load('current', {'packages':['corechart']});
	    google.charts.setOnLoadCallback(drawChart);

	    function drawChart() {
	        var data = google.visualization.arrayToDataTable(rows);
	        var options = {
	          title: '사용자별 상품수'
	        };
	        var chart = new google.visualization.PieChart(document.getElementById('chart'));
	        chart.draw(data, options);
	      }
	}
	
	getProcedure2();
	function getProcedure2(){
		$.ajax({
			type:"get",
			url:"/graph/procedure3.json",
			dataType:"json",
			success:function(data){
				let rows=[];
				rows.push(["제조사", "상품수량"])
				data.forEach(item=>{
					const row=[item.maker, item.count];
					rows.push(row);
				});
				chart(rows);
			}
		});
	}
</script>