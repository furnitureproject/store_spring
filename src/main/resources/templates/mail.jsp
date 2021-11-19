<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <div>
        <form th:action="@{http://127.0.0.1:8080/ROOT/email/pwchange(email=${email})}" method="post">
            <span style="width: 120px; display: inline-block;" >새 비밀번호  </span><input  type="password" name="userPw" placeholder="새 비밀번호" />
            <input style="width: 50px;" type="submit" value="수정"/>
        </form>
    </div>

    
</body>

</html>