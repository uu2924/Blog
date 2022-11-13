<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/login" method="POST">
		<div class="form-group">
			<label for="username">Username address:</label> <input type="text" name="username"  class="form-control" placeholder="Enter username" id="username">
		</div>
		<div class="form-group">
			<label for="passowrd">Password:</label> <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
	</form>
	
</div>
<br />
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


