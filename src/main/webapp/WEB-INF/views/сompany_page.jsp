<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-lg-12">   
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
            <div class="input-group">
                <input type="text" class="form-control" placeholder="код компании" id="txt_company_code">
                <span class="input-group-btn">
                  <button class="btn btn-default" id="btn_update_company" type="button">обновить код</button>
                </span>
            </div>
            <br>
            <button class="btn btn-default" id="btn_delete_company" type="button">удалить выбранную кампанию</button>
        </div> 
    </div>
</div>
