<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <script >
        var msg = "[[${param.msg}]]";
        var url = "[[${param.url}]]";
        alert(msg);
        if(msg == "비밀번호 변경 성공"){
        document.location.href=url;
        }else{
            history.back();
    }
    </script>
</body>
</html>