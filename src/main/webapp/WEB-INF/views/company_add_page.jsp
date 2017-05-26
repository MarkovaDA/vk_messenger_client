<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-lg-12" id="add_company">   
    <div class="panel panel-default">
        <div class="panel-heading">
            Добавить компанию
        </div>
        <div class="panel-body">
            <input type="text" class="form-control" placeholder="название компании" id="company_title">
            <br>
            <!--поле с кнопкой glyphicon glyphicon-refresh-->
            <div class="input-group">
                <input type="text" class="form-control" placeholder="код компании" id="company_code">
                <span class="input-group-btn">                  
                  <button class="btn btn-default" type="button" id="generate">
                      <span class="glyphicon glyphicon-refresh"></span>
                      генерировать
                  </button>
                </span>
            </div>
            <br>
            <button class="btn btn-default" type="button" id="btn_add_company">добавить</button>
        </div> 
    </div>
</div>
