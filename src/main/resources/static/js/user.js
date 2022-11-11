let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
	},
	
	save: function(){
		//alert("user의 save함수 호출됨");
		let data = {
			username: $("#username").val(),
			email: $("#email").val(),
			passowrd: $("#password").val()
		}
		
		//console.log(data);
		
		$.ajax.done().fail(); // ajax 통신을 통해 데이터를 json으로 변경해 insert 요청
	}
}

index.init();