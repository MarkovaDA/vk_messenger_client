<%-- 
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
    <option>sex</option>
    <option>status</option>
    <option>age_from</option>
    <option>age_to</option>
    <option >birth_day</option>
    <option >birth_month</option>
    <option>birth_year</option>
    <option >school_country</option>
    <option >school_city</option>
    <option >school_class</option>
    <option >school</option>
    <option >school_year</option>
    <option >religion</option>
    <option >interests</option>
    <option >company</option>
    <option >position</option>
    <option>group_id</option>
    <option>from_list</option>
</select>