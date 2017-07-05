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
                  <button class="btn btn-default" id="btn_update_company" type="button">
                      <span class="glyphicon glyphicon-refresh"></span>
                      обновить код
                  </button>
                </span>
            </div>
            <br>
            <button class="btn btn-default" id="btn_delete_company" type="button">удалить выбранную кампанию</button>
        </div> 
    </div>
</div>
<!--спрятанное диалоговое окно для подтверждения запроса на удаление-->
<div id="deleteModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <!-- Заголовок модального окна -->
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">Подтвердите удаление</h4>
      </div>
      <!-- Основное содержимое модального окна -->
      <div class="modal-body">
        <p>Действительно удалить выбранную компанию?</p>
      </div>
      <!-- Футер модального окна -->
      <div class="modal-footer">        
        <button type="button" class="btn btn-default" data-dismiss="modal">отмена</button>
        <button type="button" class="btn btn-default" id="btn_really_delete">удалить</button>
      </div>
    </div>
  </div>
</div>
