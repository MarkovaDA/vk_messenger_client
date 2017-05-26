<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-lg-12" id="all_criteria">   
    <div class="panel panel-default">
        <div class="panel-heading">
            Мои кампании
        </div>
        <div class="panel-body">
            <select  id="select_company" class="selectpicker" data-show-subtext="true" data-live-search="true" style="width: 200px">
                <option value=''>выберите кампанию</option>
                <c:forEach items="${companies}" var="company"> 
                    <option value="${company.code}">${company.title}</option>
                </c:forEach>                                       
            </select>
            <br><br>
            <input type="text" class="form-control" placeholder="код компании" id="txt_company_code">
            <br>
            <button class = "btn btn-default">обновить код</button>
        </div> 
    </div>
</div>
