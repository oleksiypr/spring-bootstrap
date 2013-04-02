$(document).ready(function() {
	$("#editUser").click(function() {
		var json ={
			'id': $("#userId").val(),
			'name': $("#userName").val(),
			'address': $("#userAddress").val()
		};
		
		$.ajax({
			type : "POST",
			url : "../rest/user/update",
			dataType: "text",
			contentType: 'application/json; charset=utf-8',			
			data: JSON.stringify(json), 
				
			success : function(result) {
				$("#editResult").html("User saved!");
			},
			
			error:  function(xhr, str){
			    alert('An error arised: ' + str);
			    $("#editResult").html("<span style = 'color:#ff0000'>Failed!</span>");
			}
		});
	});
});