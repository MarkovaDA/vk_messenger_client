<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-lg-12" id="all_criteria">   
    <div class="panel panel-default">
        
        <div class="panel-heading">
            Все критерии компании <b class="company_title"></b>
            <div class="separate_criteria" style="display:none;" criteria_id="-1">
                <div class="input-group" criteria_id="-1">
                    <input type="text" class="form-control" value="название критерия">
                    <span class="input-group-btn">
                        <button class="btn btn-default btn_delete_criteria" type="button">удалить</button>
                    </span>
                </div>
                <br>
            </div>
        </div>
        <div class="panel-body" >          
           <!--здесь будут выводиться критерии-->
        </div> 
    </div>
</div>
