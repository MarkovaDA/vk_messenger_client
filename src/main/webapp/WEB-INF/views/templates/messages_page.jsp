<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-lg-12" id="all_messages">   
    <div class="panel panel-default">
        <div class="panel-heading">
            Cообщения  кампании <b class="company_title"></b>
        </div>
        <div class="panel-body">
            <div class="message_pattern" message_id="-1" style="display:none;">
                <textarea class="message_text form-control" rows="10"></textarea><br>
                <button type="button" class="btn btn-default btn_save_message">сохранить</button>
                <button type="button" class="btn btn-default btn_delete_message">удалить</button>
                <hr>
            </div>           
            <div class="message_container">                
            </div>             
        </div> 
    </div>
</div>
