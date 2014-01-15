/**
	 * 在列表中增加单击  鼠标移入移除样式修改
	 */
$(function(){
		$("table.table-striped tbody tr:odd").addClass("table-color-odd");
		$("table.table-striped tbody tr").click(function() {
			var v = $(this).hasClass("table-color-click");
			$("table.table-striped tbody tr").removeClass("table-color-click");
			if(!v){
				 $(this).addClass("table-color-click");
			}
		});
		
		$("table.table-striped tbody tr:last-child td").css("border-bottom","1px solid #dddddd");
	});