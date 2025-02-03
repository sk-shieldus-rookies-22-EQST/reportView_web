<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

	<link rel="icon" type="image/png" href="images/favicon.png">

	<title>BOOKIES</title>

<style>
    /* Chrome, Safari, Edge에서 숫자 화살표 숨기기 */
    input[type="number"]::-webkit-outer-spin-button,
    input[type="number"]::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

    /* Firefox에서 숫자 화살표 숨기기 */
    input[type="number"] {
        -moz-appearance: textfield;
    }
</style>

</head>
<body>
<div class="container">
		<%@ include file="banner.jsp" %>
<div class="container">
	<div class="container" style="max-width: 500px;margin-bottom:100px;border-radius: 5px;padding: 50px 20px;">
		<p class="text-start fs-1 fw-bold" style="display: flex;justify-content: center; margin-bottom:50px;margin-top:16px">충전하기</p>
        <%
            Integer point = (Integer) session.getAttribute("point");
        %>
		<form action="/pointChargeProc" method="post">
			<div class="mb-3" style="margin-bottom : 50px!important;display: flex;justify-content: space-between;">
			  <div class="form-label fw-bold fs-4">보유 포인트</div>
			  <div> <%= point %> </div>
			</div>

			<div class="mb-3" style="margin-bottom : 50px!important;display: flex;justify-content: space-between;">
			  <!-- for="charge_point"는 input의 id와 일치해야 함 -->
			  <label for="charge_point" class="form-label fw-bold fs-4">충전 포인트</label>
			  <input type="number" class="form-control" id="charge_point" aria-describedby="emailHelp" name="charge_point" style="width:200px;">
			</div>

			<div class="mb-3" style="margin-bottom : 50px!important;display: flex;justify-content: space-between;">
			  <div class="form-label fw-bold fs-4">충전 후 포인트</div>
			  <div id="updated_point"> <%= point %> </div>
			</div>

			<div class="d-grid gap-2 col-6 mx-auto">
			  <% String previousPage = request.getHeader("referer"); %>
			  <input type="hidden" id="referer" name="referer" value="<%= previousPage %>" />
			  <button class="btn btn-primary" type="submit">충전하기</button>
			  <button class="btn btn-outline-primary" type="button" onclick="history.back()">취소하기</button>
			</div>
		</form>

        <script>
            // 현재 보유 포인트
            let currentPoint = <%= point %>;

            // 충전 포인트 입력 필드
            const chargePointInput = document.getElementById('charge_point');

            // 충전 후 포인트를 표시할 div
            const updatedPointDiv = document.getElementById('updated_point');

            // 숫자만 입력받도록 처리하는 함수 (e, +, - 등 방지)
            chargePointInput.addEventListener('keydown', function(event) {
                const invalidKeys = ['e', 'E', '+', '-', '.']; // 숫자 외의 문자 입력 방지

                // 숫자나 백스페이스, 화살표 키만 허용
                if (invalidKeys.includes(event.key)) {
                    event.preventDefault();
                }
            });

            // 충전 포인트 값이 변경될 때마다 실행되는 함수
            chargePointInput.addEventListener('input', function() {
                // 입력된 값 가져오기
                let chargePoint = parseFloat(chargePointInput.value) || 0;  // 숫자가 아닌 값은 0으로 처리

                // 새로운 포인트 계산 (보유 포인트 + 충전 포인트)
                let updatedPoint = currentPoint + chargePoint;

                // 결과를 화면에 업데이트
                updatedPointDiv.textContent = updatedPoint;
            });
        </script>

</div></div></div>

</body>
</html>
