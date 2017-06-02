<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Личный кабинет</title>
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
        <script type="text/javascript" src="<c:url value='static_resources/js/company.script.js' />"></script>
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
                                   <span class="glyphicon glyphicon-home"></span>
                                    Пользователь:<b>${login}</b>
                               </div>
                           </div>
                        </div>
                        <jsp:include page="сompany_page.jsp"/>        
                        <div class="col-lg-12 toggle_visible" id="add_criteria" style="display: none;">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Cоздать новый критерий в <b class="company_title"></b>
                                </div>
                                <div class="panel-body"> 
                                    <jsp:include page="optional_select.jsp"/>                                                                     
                                    <br><hr><br>
                                    <table>
                                        <!--паттерн имени-->
                                        <tr id="q">
                                            <td><span class="label label-primary">Шаблон имени:</span></td>
                                            <td>
                                                 <input type="text" property="q" class="form-control for_reading" placeholder="pattern">
                                            </td>
                                        </tr> 
                                        <!--страна-->
                                        <tr id="country">
                                            <td><span class="label label-primary">Страна:</span></td>
                                            <td>        
                                                <select property="country" id="select_country" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <option value=''>выберите страну</option>
                                                    <c:forEach items="${countries}" var="coutry"> 
                                                        <option value="${coutry.id}">${coutry.title}</option>
                                                    </c:forEach>                                       
                                                </select>
                                            </td>
                                        </tr>
                                        <!--город-->
                                        <tr id="city">
                                            <td><span class="label label-primary">Город проживания:</span></td>                    
                                            <td>
                                                <select property="city" id="select_city" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <option value="">выберите город</option>
                                                    <c:forEach items="${cities}" var="city"> 
                                                        <option value="${city.id}">${city.title}</option>
                                                    </c:forEach>                                       
                                                </select>
                                            </td>
                                        </tr>
                                        <!--родной город-->
                                        <tr id="hometown">
                                            <td><span class="label label-primary">Родной город:</span></td>
                                            <td>        
                                                <input type="text" property="hometown" class="form-control for_reading" placeholder="hometown">
                                            </td>
                                        </tr>                                       
                                        <!--страна окончания вуза-->
                                        <tr id="university_country">
                                            <td><span class="label label-primary">Страна окончания ВУЗа:</span></td>                    
                                            <td>
                                                <select property="university_country" id="select_сountry_univ" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <option value="">выберите страну</option>
                                                    <c:forEach items="${countries}" var="coutry"> 
                                                        <option value="${coutry.id}">${coutry.title}</option>
                                                    </c:forEach>                                         
                                                </select>
                                            </td>
                                        </tr>                                       
                                        <!--университет-->
                                        <tr id="university">
                                            <!-- индентификатор ВУЗА-->
                                            <td><span class="label label-primary">Университет:</span>                                                
                                            </td>
                                            <td>
                                                <select property="university" id="select_univ" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                </select>                                                
                                            </td>
                                        </tr>
                                        <!--факультет-->
                                        <tr id="university_faculty">
                                            <td><span class="label label-primary">Факультет:</span></td>
                                            <td>
                                                <select property="university_faculty" id="select_fac" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                </select>      
                                            </td>
                                        </tr>
                                        <!--кафедра-->
                                        <tr id="university_chair">
                                            <td><span class="label label-primary">Кафедра:</span></td>
                                            <td>
                                                <select property="university_chair" id="select_chair" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                </select>      
                                            </td>
                                        </tr>
                                        <!--пол-->
                                        <tr id="sex">
                                            <td><span class="label label-primary">Пол:</span></td>
                                            <td>
                                                <select property="sex" id="select_sex" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <option value=''>выберите пол</option>
                                                    <option value="1">женский</option>
                                                    <option value="2">мужской</option>
                                                    <option value="0">любой</option>                                                
                                                </select>      
                                            </td>
                                        </tr>
                                        <!--статус-->
                                        <tr id="status">
                                            <td><span class="label label-primary">Семейное положение:</span></td>
                                            <td>
                                                <select property="status" id="select_sex" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <option value=''>выберите статус</option>
                                                    <option value="1">не женат</option>
                                                    <option value="2">встречается</option>
                                                    <option value="3">помолвлен</option>
                                                    <option value="4">женат</option>
                                                    <option value="5">все сложно</option>
                                                    <option value="6">в активном поиске</option>
                                                    <option value="7">влюблен</option>
                                                </select>      
                                            </td>
                                        </tr>
                                        <!--год рождения-->
                                        <tr id="birth_year">
                                            <td><span class="label label-primary">Год рождения:</span></td>
                                            <td><input property="birth_year" type="text" class="form-control for_reading" placeholder="birth_year"></td>
                                        </tr>
                                        <!--месяц рождения-->
                                        <tr id="birth_month">
                                            <td><span class="label label-primary">Месяц рождения:</span></td>
                                            <td><input type="text" property="birth_month" class="form-control for_reading" placeholder="birth_month"></td>
                                        </tr>
                                         <!--число рождения-->
                                        <tr id="birth_day">
                                            <td><span class="label label-primary">Число рождения:</span></td>
                                            <td><input type="text" property="birth_day" class="form-control for_reading" placeholder="birth_day"></td>
                                        </tr>
                                        <!--страна окончания школы-->
                                        <tr id="school_country">
                                            <td><span class="label label-primary">Страна окончания школы:</span></td>
                                            <td>                               
                                                <select property="school_country" id="select_school_country" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <option value=''>выберите страну</option>
                                                    <c:forEach items="${countries}" var="coutry"> 
                                                        <option value="${coutry.id}">${coutry.title}</option>
                                                    </c:forEach>                                       
                                                </select>
                                            </td>
                                        </tr>
                                        <!--город окончания школы-->
                                        <tr id="school_city">
                                            <td><span class="label label-primary">Город окончания школы:</span></td>
                                            <td>                               
                                                <select property="school_city" id="select_school_city" class="selectpicker for_reading" data-show-subtext="true" data-live-search="true" style="width: 200px">
                                                    <option value=''>выберите город</option>                                                                      
                                                </select>
                                            </td>
                                        </tr>
                                        <!--номер класса школы-->
                                        <tr id="school_class">
                                           <td><span class="label label-primary">Номер класса школы:</span></td>
                                            <td>
                                                <input property="school_class" class="form-control for_reading" style="width:100px;" type="number" min="1" max="11"></input>
                                            </td> 
                                        </tr>
                                        <!--год окончания школы-->
                                        <tr id="school_year">
                                            <td><span class="label label-primary">Год окончания школы:</span></td>
                                            <td>
                                                <input property="school_year" class="form-control for_reading" style="width:100px;"></input>
                                            </td> 
                                        </tr>
                                        <!--идентификатор законченной школы-->
                                        <tr id="school">
                                            
                                        </tr>
                                        <!--религиозные взгляды, строка-->
                                        <tr id="religion">
                                            <td><span class="label label-primary">Религиозные взгляды:</span></td>
                                            <td>
                                                <input property="religion" class="form-control for_reading"></input>
                                            </td> 
                                        </tr>
                                        <tr>                                          
                                            <input type="hidden" value="${accessToken}" id="token_field">
                                        </tr>
                                        <!--год выпуска из ВУЗА-->
                                        <tr id="university_year" style="display: none;">                   
                                            <td><span class="label label-primary">Год выпуска:</span></td>
                                            <td>
                                                <input property="university_year" class="form-control for_reading" style="width:100px;" type="number" id="year_field" min="1970" max="2030" step="1" ></input>
                                            </td>    
                                        </tr>
                                        <!--возраст от -->
                                        <tr id="age_from">
                                            <td><span class="label label-primary">Возраст (нижняя граница)</span></td>
                                            <td>
                                                <input property="age_from" class="form-control for_reading" type="number" id="age_from_field"  min="10" max="50" step="1"></input>
                                            </td>
                                        </tr>
                                        <!--возраст до-->
                                        <tr id="age_to">
                                            <td><span class="label label-primary">Возраст (верхняя граница)</span></td>
                                            <td>
                                                <input property="age_to" class="form-control for_reading" type="number" id="age_to_field" min="10" max="50" step="1"></input>
                                            </td>
                                        </tr>
                                        <!--профессия-->
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
                        <jsp:include page="criteria_page.jsp"/> 
                        <jsp:include page="company_add_page.jsp"/> 
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
