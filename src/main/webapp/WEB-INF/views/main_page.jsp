﻿<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Personal tools</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link href="<c:url value='static_resources/css/bootstrap.min.css' />" rel="stylesheet"></link>
        <link href="<c:url value='static_resources/css/simple_sidebar.css' />" rel="stylesheet"></link>
        <link href="<c:url value='static_resources/css/style.css' />" rel="stylesheet"></link>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">       
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
        <script type="text/javascript" src="<c:url value='static_resources/js/criteria.script.js' />"></script>
        <script type="text/javascript" src="<c:url value='static_resources/js/message_dropdown.js' />"></script>
        <script type="text/javascript" src="<c:url value='static_resources/js/criteria.generate.js' />"></script>
    </head>
    <body>
        <div id="wrapper">
           <jsp:include page="sidebar.jsp"/>
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
                           <div class="panel panel-default">
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
                                    <jsp:include page="optional_select.jsp"/>                                                                     
                                    <br><hr><br>
                                    <table>
                                        <tr id="q">
                                            <td><span class="label label-primary">Шаблон имени:</span></td>
                                            <td>
                                                 <input type="text" class="form-control for_reading" placeholder="pattern">
                                            </td>
                                        </tr>
                                      
                                        <tr id="country">
                                            <td><span class="label label-primary">Страна:</span></td>
                                            <td>        
                                                <select id="select_country" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <c:forEach items="${countries}" var="coutry"> 
                                                        <option value="${coutry.id}">${coutry.title}</option>
                                                    </c:forEach>                                       
                                                </select>
                                            </td>
                                        </tr>
                                        <tr id="city">
                                            <td><span class="label label-primary">Город проживания:</span></td>                    
                                            <td>
                                                <select id="select_city" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <option value="">выберите город</option>
                                                    <c:forEach items="${cities}" var="city"> 
                                                        <option value="${city.id}">${city.title}</option>
                                                    </c:forEach>                                       
                                                </select>
                                            </td>
                                        </tr>
                                        <tr id="hometown">
                                            <td><span class="label label-primary">Родной город:</span></td>
                                            <td>        
                                                <input type="text" class="form-control for_reading" placeholder="hometown">
                                            </td>
                                        </tr>                                       
                                         <tr id="university_country">
                                            <td><span class="label label-primary">Страна окончания ВУЗа:</span></td>                    
                                            <td>
                                                <select id="select_сountry_univ" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <c:forEach items="${countries}" var="coutry"> 
                                                        <option value="${coutry.id}">${coutry.title}</option>
                                                    </c:forEach>                                         
                                                </select>
                                            </td>
                                        </tr>                                       
                                        <tr id="university">
                                            <!-- индентификатор ВУЗА-->
                                            <td><span class="label label-primary">Университет:</span>                                                
                                            </td>
                                            <td>
                                                <select id="select_univ" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                </select>                                                
                                            </td>
                                        </tr>
                                        <tr id="university_faculty">
                                            <td><span class="label label-primary">Факультет:</span></td>
                                            <td>
                                                <select id="select_fac" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                </select>      
                                            </td>
                                        </tr>
                                        <tr id="university_chair">
                                            <td><span class="label label-primary">Кафедра:</span></td>
                                            <td>
                                                <select id="select_chair" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                </select>      
                                            </td>
                                        </tr>
                                        <!--пол-->
                                        <tr id="sex">
                                            <td><span class="label label-primary">Пол:</span></td>
                                            <td>
                                                <select id="select_sex" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <option value="1">женский</option>
                                                    <option value="2">мужской</option>
                                                    <option value="0">любой</option>
                                                    <option value="4">женат</option>
                                                    <option value="5">все сложно</option>
                                                    <option value="6">в активном поиске</option>
                                                    <option value="7">влюблен</option>
                                                </select>      
                                            </td>
                                        </tr>
                                        <!--статус-->
                                        <tr id="status">
                                            <td><span class="label label-primary">Семейное положение:</span></td>
                                            <td>
                                                <select id="select_sex" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <option value="1">не женат</option>
                                                    <option value="2">встречается</option>
                                                    <option value="3">помолвлен</option>
                                                </select>      
                                            </td>
                                        </tr>
                                        <!--год рождения-->
                                        <tr id="birth_year">
                                            <td><span class="label label-primary">Год рождения:</span></td>
                                            <td><input type="text" class="form-control for_reading" placeholder="birth_year"></td>
                                        </tr>
                                        <!--месяц рождения-->
                                        <tr id="birth_month">
                                            <td><span class="label label-primary">Месяц рождения:</span></td>
                                            <td><input type="text" class="form-control for_reading" placeholder="birth_month"></td>
                                        </tr>
                                         <!--число рождения-->
                                        <tr id="birth_day">
                                            <td><span class="label label-primary">Число рождения:</span></td>
                                            <td><input type="text" class="form-control for_reading" placeholder="birth_day"></td>
                                        </tr>
                                        <tr>                                          
                                            <input type="hidden" value="${accessToken}" id="token_field">
                                        </tr>
                                        <tr id="university_year" style="display: none;">                   
                                            <td><span class="label label-primary">Год выпуска:</span></td>
                                            <td>
                                                <input class="form-control for_reading" style="width:100px;" type="number" id="year_field" min="1970" max="2030" step="1" ></input>
                                            </td>    
                                        </tr>
                                        <tr id="age_from">
                                            <td><span class="label label-primary">Возраст (нижняя граница)</span></td>
                                            <td>
                                                <input  class="form-control for_reading" type="number" id="age_from_field" value="40" min="10" max="50" step="1"></input>
                                            </td>
                                        </tr>
                                        <tr id="age_to">
                                            <td><span class="label label-primary">Возраст (верхняя граница)</span></td>
                                            <td>
                                                <input class="form-control for_reading" type="number" id="age_to_field" value="40" min="10" max="50" step="1"></input>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><span class="label label-primary">Профессия</span></td>
                                            <td>
                                                <div class="form-group">
                                                    <textarea  class="form-control for_reading" id="job_field"></textarea>
                                                </div>                                               
                                            </td>                                       
                                        </tr>
                                        <!-- сообщение-->
                                        <tr class="visible_tr">
                                            <td><span class="label label-primary">Cooбщение:</span></td>
                                            <td>
                                                 <!--список позволяющий выбрать сообщения из имеющихся-->
                                                <div class="dropdown">
                                                    <!--предусмотерть паттерн для внедрения щаблона-->
                                                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                                     Выбрать сообщение из имеющихся
                                                    <span class="caret"></span>
                                                    </button>
                                                    <ul id="message_list" class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                                    </ul>
                                                </div>
                                                <div class="form-group" style="margin-top: 20px !important">
                                                    <textarea class="form-control" id="message_field" cols="50" rows="5" placeholder="новое сообщение"></textarea>
                                                </div>
                                                <input type="hidden" id="selected_mes_id">                                                
                                            </td>                              
                                        </tr>
                                        <!-- кнопка-->
                                        <tr class="visible_tr">
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
                $('.selectpicker').selectpicker({
                    style: 'btn-info',
                    size: 10
                });
        });
           
        </script>
        <script type="text/javascript" src='<c:url value="static_resources/js/bootstrap.min.js"/>'></script>
    </body>
</html>
