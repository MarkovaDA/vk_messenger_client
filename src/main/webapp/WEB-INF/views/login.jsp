<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Аутентификация</title>
        <link href="<c:url value='static_resources/css/bootstrap.min.css' />" rel="stylesheet"></link>
        <link href="<c:url value='static_resources/css/style.css' />" rel="stylesheet"></link>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script type="text/javascript" src='<c:url value="static_resources/js/bootstrap.min.js"/>'></script>
    </head>
    <body>
        <c:url var="loginUrl" value="/login"/>
        <div class="row" >
            <div class="col-xs-4 col-xs-offset-4" 
                 style="margin-top:100px;text-align: center;">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <span class="glyphicon glyphicon-lock"></span>
                        Аутентификация
                    </div>
                    <div class="panel-body">
                        <form action="${loginUrl}" method="post">
                            <c:if test="${param.error != null}">
                                <div class="ui label">
                                    <p>Invalid username and password.</p>
                                </div>
                            </c:if>
                            <c:if test="${param.logout != null}">
                                <div class="ui label">
                                    <p>You have been logged out successfully.</p>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <input class="form-control" type="text" id="username" name="login" placeholder="логин">
                            </div>
                            <div class="form-group">
                                <input class="form-control" type="password"  id="password" name="password" placeholder="пароль" required>
                            </div>
                            <button type="submit"  class="btn btn-default">Войти</button>
                        </form>
                    </div>
                </div>     
            </div>
        </div>                   
    </body>
</html>