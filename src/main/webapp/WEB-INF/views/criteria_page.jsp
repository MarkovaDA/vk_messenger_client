<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-lg-12" id="all_criteria">   
    <div class="panel panel-default">
        <div class="panel-heading">
            Все критерии компании <b class="company_title"></b>
        </div>
        <div class="panel-body separate_criteria" criteria_id="-1">
            <div class="input-group">
                <input type="text" class="form-control" value="название критерия">
                <span class="input-group-btn">
                  <button class="btn btn-default" type="button">удалить</button>
                </span>
            </div>
        </div> 
    </div>
</div>
