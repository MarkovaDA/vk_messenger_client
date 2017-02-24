<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Personal tools</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link href="<c:url value='static_resources/css/bootstrap.min.css' />" rel="stylesheet"></link>
        <link href="<c:url value='static_resources/css/simple_sidebar.css' />" rel="stylesheet"></link>
        <link href="<c:url value='static_resources/css/style.css' />" rel="stylesheet"></link>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script type="text/javascript" src="<c:url value='static_resources/js/criteria.script.js' />"></script>
    </head>
    <body>
        <div id="wrapper">
            <div id="sidebar-wrapper">              
                <ul class="sidebar-nav">
                    <a class="closebtn">&times;</a>
                    <li class="sidebar-brand">
                        <a href="#">
                            VK Messenger
                        </a>
                    </li>
                    <li> 
                        <a href="#add_criteria">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                            Добавить критерий
                        </a>
                    </li>
                    <li>  
                        <a href="#all_criteria">
                            <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
                            Все критерии
                        </a>
                    </li>
                </ul>
            </div>
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    
                    <div class="row">
                        <div class="col-lg-2">
                             <!-- кнопка динамического открытия меню-->
                             <button class = "btn btn-default" id='btn_open_menu'>
                                <span class="glyphicon glyphicon-th-large"></span>     
                             </button>
                        </div>
                        <div class="col-lg-offset-7 col-lg-3">
                            <!-- имя пользователя-->
                           <div class="panel panel-default" style="border-left:3px solid grey; border-radius:2px;">
                               <div class="panel-body">
                                   Welcome, ${login}
                               </div>
                           </div>
                        </div>
                        <div class="col-lg-12" id="add_criteria">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Cоздать новый критерий
                                </div>
                                <div class="panel-body">
                                    <table>
                                        <tr>
                                            <td><span class="label label-default">Город:</span></td>                    
                                            <td>
                                                <select id="select_city" style="width: 200px">
                                                    <c:forEach items="${cities}" var="city"> 
                                                        <option value="${city.id}">${city.title}</option>
                                                    </c:forEach>                                       
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><span class="label label-default">Университет:</span></td>
                                            <td>
                                                <select id="select_univ" style="width: 200px">
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><span class="label label-default">Факультет:</span></td>
                                            <td>
                                                <select id="select_fac" style="width: 200px">
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                        <input type="hidden" value="${accessToken}" id="token_field">
                                        </tr>
                                        <tr>                   
                                            <td><span class="label label-default">Год выпуска:</span></td>
                                            <td><input type="number" id="year_field" min="1980" max="2030" step="1" ></input></td>
                                        </tr>
                                        <tr>
                                            <td><span class="label label-default">Возраст (от ... до)</span></td>
                                            <td>
                                                <input type="number" id="age_from_field" value="18" min="10" max="50" step="1"></input>
                                                <input type="number" id="age_to_field" value="40" min="10" max="50" step="1"></input>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><span class="label label-default">Профессия</span></td>
                                            <td>
                                                <div class="form-group">
                                                    <textarea  class="form-control" id="job_field"></textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><span class="label label-default">Cooбщение</span></td>
                                            <td>
                                                <div class="form-group">
                                                    <textarea class="form-control" id="message_field" cols="50" rows="5"></textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td>
                                                <button class = "btn btn-default" id="btn_add">    
                                                    добавить
                                                </button>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-12" id="all_criteria">   
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Все критерии

                                </div>
                                <div class="panel-body">
                                </div> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $(document).ready(function () {
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
        <script type="text/javascript" src='<c:url value="static_resources/js/bootstrap.min.js"/>'></script>
    </body>
</html>
