$(document).ready(function() {
	
	$("#uploadFileForm").submit(function(evt) {
		evt.preventDefault();
		
		var formData = new FormData($(this)[0]);
		
		$.ajax({
			url : '/api/upload/csv/single',
			type : 'POST',
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			enctype : 'multipart/form-data',
			processData : false,
			success : function(response) {
				$("#response").empty();
				if(response.errStatus !== "error"){
					var displayInfo = response.messages[0].filename + " : " + response.messages[0].message + "<br>"; 
					
					$("#response").append(displayInfo);
					// add some css
					$("#response").css("display", "block");
					$("#response").css("background-color", "#e6e6ff");
					$("#response").css("border", "solid 1px black");
					$("#response").css("border-radius", "3px");
					$("#response").css("margin", "10px");
					$("#response").css("padding", "10px");
				}else{
					$("#response").css("display", "none");
					var error = response.error.errDesc;
					alert(error);
				}
			},
			error: function(e){
				alert("Fail! " + e);
			}
		});
		
		return false;
	});
	
})