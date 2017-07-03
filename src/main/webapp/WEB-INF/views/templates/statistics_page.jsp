<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-lg-12" id="statistics">   
    <div class="panel panel-default">
        <div class="panel-heading">
            Статистика по кампании <b id="st_company_title"></b>
        </div>
        <div class="panel-body">
            <select id="st_select_company" class="selectpicker" data-show-subtext="true" data-live-search="true" style="width: 200px">
                <option value=''>выберите кампанию</option>
                <c:forEach items="${companies}" var="company"> 
                    <option value="${company.code}">${company.title}</option>
                </c:forEach>                                       
            </select><br><br>
            <li class="list-group-item separate_li" style="display: none;" criteria_id="-1">
                    <span class="criteria_title">Критерий 1</span>
                    <button class="btn btn-default btn_criteria_report">Отправлено сообщ.
                        <span class="badge">14</span>
                    </button>
            </li>
            <ul class="list-group">
                
            </ul>
        </div> 
    </div>
</div>

