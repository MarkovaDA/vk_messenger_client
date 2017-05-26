﻿<%-- 
    Document   : optional_select
    Created on : May 21, 2017, 3:49:54 PM
    Author     : darya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <p>Набор параметров критерия</p> 
<select class="selectpicker" id="criteria_type" multiple data-live-search="true">
    <!-- шаблон имени-->                                       
    <option value="q">шаблон имени</option>
    <!--для поиска по стране указывается код страны-->
    <option value="country">страна</option>
    <option value="city">город</option>
    <option value="hometown">родной город</option>
    <!--идентификатор страны, в которой пользователь закончил вуз-->
    <option value="university_country">страна окончания ВУЗа</option>
    <!--идентификатор университета-->
    <option value="university">ВУЗ</option>
    <!--год окончания вуза-->
    <option value="university_year">год окончания ВУЗа</option>
    <option value="university_faculty">факультет</option>
    <option value="university_chair">кафедра университета</option>
    <option value="sex">пол</option>
    <option value="status">семейное положение</option>
    <option value="age_from">возраст(нижняя граница)</option>
    <option value="age_to">возраст(верхняя граница)</option>
    <option value="birth_day">день рождения</option>
    <option value="birth_month">месяц рождения</option>
    <option value="birth_year">год рождения</option>
    <option value="school_country">страна окончания школы</option>
    <option value="school_city">город окончания школы</option>
    <option value="school_class">номер класса школы</option>
    <option value="school">школа</option>
    <option value="school_year">год окончания школы</option>
    <option value="religion">религиозные взгляды</option>
    <option >interests</option>
    <option >company</option>
    <option >position</option>
    <option>group_id</option>
    <option>from_list</option>
</select>