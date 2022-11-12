let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{ //function 사용하지 않은이유는 ()=>{}를 사용해 this를 바인딩하려고
			this.save();
		});
		
	},
	
	save: function(){
		//alert("user의 save함수 호출됨");
		let data = {
			username: $("#username").val(),
			email: $("#email").val(),
			password: $("#password").val()
		}
		
		//console.log(data);
		
		//ajax호출시 디폴트가 비동기 호출
		//ajax 통신을 통해 데이터를 json으로 변경해 insert 요청
		//ajax가 통신 성공시 json을 리턴하면 서버가 자동으로 자바 오브젝트로 변경해줌 (디폴트로!)
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), //http body
			contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지
			dataType: "json" //응답이왔을때 데이터 타입, 기본적으로 String, 생긴모양이 json이면 js json 오브젝트로 변경
			//회원가입수행요청
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			//console.log(resp);
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	

}

index.init();