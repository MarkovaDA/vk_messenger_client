<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Заявка на использование системы OnClick messenger</title>
         <link href="<c:url value='static_resources/css/bootstrap.min.css' />" rel="stylesheet"></link>
    </head>
    <body>
        <br>
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Добро пожаловать, ${login}
                </div>
                <div class="panel-body">
                    <p>${message}</p>
                    <hr>
                    <p>По вопросам подтверждения заявок обращаться по адресу <b>gennady.ponomarev@vistar.su</b></p>
                </div>
            </div>
        </div>       
    </body>
</html>
