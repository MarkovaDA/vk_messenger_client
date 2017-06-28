<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Личный кабинет</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="<c:url value='/static_resources/css/simple_sidebar.css'/>" rel="stylesheet"></link>        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Статистика отправки сообщений по критерию</title>
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="sidebar.jsp"/>
            <div id="page-content-wrapper">              
                <div class="container-fluid" style="position:relative!important;">
                    <div class="row">
                        <div class="col-lg-2">
                            <!-- кнопка динамического открытия меню-->
                            <button class = "btn btn-default" id='btn_open_menu'>
                                <span class="glyphicon glyphicon-th-large"></span>     
                            </button>
                        </div>        
                        <!-- имя пользователя-->
                        <div class="col-lg-offset-7 col-lg-3">                            
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <span class="glyphicon glyphicon-home"></span>
                                    Пользователь:<br><b>${login}</b>
                                </div>
                            </div>
                        </div>                        
                        <div class="col-lg-12" id="all_criteria">   
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Статистика по выбранному критерию
                                </div>
                                <div class="panel-body" >                                            
                                    <table class="table">
                                           <thead>
                                               <tr class="active">                                                
                                                    <th>Отправитель</th>
                                                    <th>Получатель</th>
                                                    <th>Статус отправки</th>
                                                    <th>Дата и время</th>
                                               </tr>
                                           </thead>
                                           <tbody>
                                               <!--настроить дату и время, а также проверку на успех или не успех-->
                                                <c:forEach items="${listInfo}" var="info">
                                                    <tr class="active">
                                                        <td>${info.senderVkId}</td>
                                                        <td>${info.receiverVkId}</td>
                                                        <td>${info.errorMsg}</td>
                                                        <td>${info.humanDate}</td>
                                                    </tr>
                                                </c:forEach>
                                           </tbody>                                       
                                    </table>             
                                </div>                                
                            </div>
                             
                        </div>                 
                        <input type="hidden" value="${uid}" id="txt_uid">
                     </div>
                                </div>
                                </div>
                                </div>
                                <script>
                                    $(document).ready(function () {
                                        $('.visible_tr').show();
                                        //код меню слева
                                        $('#btn_open_menu').hide();
                                        $("#btn_open_menu").click(function (e) {
                                            e.preventDefault();
                                            $("#wrapper").toggleClass("toggled");
                                            $('#btn_open_menu').fadeOut(200);
                                        });
                                        $('.closebtn').click(function () {
                                            //console.log("sidebar-wrapper click");
                                            //меню закрывается, отобразить кнопочку open
                                            $('#btn_open_menu').fadeIn(200);
                                            $("#wrapper").toggleClass("toggled");
                                        });
                                    });
                                </script>
                                </body>
                                </html>
