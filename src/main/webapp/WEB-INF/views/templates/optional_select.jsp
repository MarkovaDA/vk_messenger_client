<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
 
<p>Набор параметров критерия</p> 
<select class="selectpicker" id="criteria_type" multiple data-live-search="true">
    <!-- шаблон имени-->                                       
    <option value="q">шаблон имени</option>
    <!--для поиска по стране указывается код страны-->
    <option value="country">страна проживания</option>
    <option value="city">город проживания</option>
    <option value="hometown">родной город</option>
    <!--идентификатор страны, в которой пользователь закончил вуз-->
    <option value="university_country">страна окончания ВУЗа</option>
    <!--идентификатор университета-->
    <option value="university">ВУЗ</option>
    <!--год окончания вуза-->
    <option value="university_year">год окончания ВУЗа</option>
    <option value="university_faculty">факультет университета</option>
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
    <option value="interests">интересы</option>
    <option value="company">место работы</option>
    <option value="position">должность</option>
    <option value="group_id">идентификатор группы, среди которой будем искать</option>
    <option value="from_list">разделы,среди которых нужно осуществить поиск</option>
</select>
<br><br>
<p>Наборы зависимых данных</p>
<a dependency_chain="1" class="breadcrumbs">Cтрана проживания</a><a class="right_bracket">></a>
<a dependency_chain="1" class="breadcrumbs">Город проживания</a><a class="right_bracket">></a>
<a dependency_chain="1" class="breadcrumbs">ВУЗ</a><a class="right_bracket">></a>
<a dependency_chain="1" class="breadcrumbs">Факультет</a><a class="right_bracket">></a>
<a dependency_chain="1" class="breadcrumbs">Кафедра</a>
<br>
<a dependency_chain="2" class="breadcrumbs">Страна окончания школы</a><a class="right_bracket">></a>
<a dependency_chain="2" class="breadcrumbs">Город окончания школы</a><a class="right_bracket">></a>
<a dependency_chain="2" class="breadcrumbs">Школа</a>

<script>
    $(document).ready(function(){     
        var dependency_arr = [
            {value:"country", chain:1},
            {value:"city", chain:1}, 
            {value:"university", chain:1}, 
            {value:"university_faculty", chain:1},
            {value:"university_chair", chain:1},             
            {value:"school_country", chain:2},
            {value:"school_city",chain:2},
            {value:"school", chain:2}
        ];
        $('.breadcrumbs').click(function(){
            var breadcrumbs_index = $('.breadcrumbs').index($(this));            
            var dependency_chain = parseInt($(this).attr('dependency_chain'));
    
            for(var i=0; i < dependency_arr.length; i++) {
                var current_id = '#' + dependency_arr[i].value;
                var current_chain = dependency_arr[i].chain;
                if (current_chain === dependency_chain && i <= breadcrumbs_index) 
                    $(current_id).show();                
                else 
                    $(current_id).hide();                
            }
        });
    });
</script>