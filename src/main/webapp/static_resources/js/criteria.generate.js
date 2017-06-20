function isEmptyValue(value) {
    if (value !== null && typeof value !== 'undefined')
        return !(value.length > 0);
    else
        return true;
}
$(document).ready(function () {
    var criteria = new Object();
    //селект, который отвечает за опциональное отображение критериев
    $('#criteria_type').on('changed.bs.select', function(event, clickedIndex, newValue, oldValue){
        var prop = $(this).children().eq(clickedIndex).attr('value');
        if (String(newValue)==='true'){
            $('#' + prop).find('select').addClass('readable');
            $('#' + prop).find('input').addClass('readable');
            $('#' + prop).find('textarea').addClass('readable');
            $('#' + prop).fadeIn(100);           
        }
        if (String(newValue)==='false'){
            delete criteria[prop];
            $('#' + prop).find('select').removeClass('readable');
            $('#' + prop).find('input').removeClass('readable');
            $('#' + prop).find('textarea').removeClass('readable');
            $('#' + prop).fadeOut(100);          
        }     
    });
    //считываем с селектов параметры в объект criteria - кажется, этот кусок кода вообще не работает
    $('.selectpicker').on('changed.bs.select', function(event, clickedIndex, newValue, oldValue){
        if ($(this).hasClass('readable')) 
        {   console.log("событие");
            var selectedValue = $(this).find('option').eq(clickedIndex).val();
            //var selectedText = $(this).find('option').eq(clickedIndex).text();
            var propertyName = $(this).attr('property');
            if (!isEmptyValue(selectedValue))
                criteria[propertyName] = selectedValue;
        }
    });
    $('#message_field').keypress(function(){
        criteria.message_id = "";
    });
    //считываем значение остальных, текстовых полей
    $('#btn_add').click(function () {
        var propertyName, propertyValue, propertyText;
        var criteriaName = "";
        //значение считывается только с селекта (?)
        $('.readable').each(function(){
            propertyName = $(this).attr('property');
            propertyValue = $(this).val();               
            if (/*typeof propertyName !== 'undefined'*/ !isEmptyValue(propertyName) && !isEmptyValue(propertyValue)){
                criteria[propertyName] = propertyValue; 
                //это для селектов только пропахивает
                propertyText = $(this).find("option:selected").text();
                if (isEmptyValue(propertyText)){
                    propertyText = propertyValue;
                }
                criteriaName += propertyName + "="+ propertyText + ";";          
            }            
        });
        var criteriaUserName = $('#criteria_title').val();//пользовательское имя критерия
        if (isEmptyValue(criteriaUserName)) {
            //если пользователь для критерия не указал имя, то вставляем пользвательское
            $('#criteria_title').val(criteriaName);
        }
        var criteriaString = "";
        for(var key in criteria){
            if (!isEmptyValue(key) && !isEmptyValue(criteria[key]))
                criteriaString += key + "=" + criteria[key] + "&";
        }
        //console.log(criteriaString);
        //проверка содержимого критерия
        if (isEmptyValue(criteriaString)){
            showMessage('alert-danger', "критерий не указан");
            return;
        }
        //проверка содержимого сообщения
        if (isEmptyValue($('#message_field').val())){
            showMessage('alert-danger', "сообщение не выбрано/не указано");
            return;
        }
        var serverObject = new Object();
        serverObject.criteriaString = criteriaString;
        serverObject.message = $('#message_field').val();        
        serverObject.message_id = $('#selected_mes_id').val();
        serverObject.criteriaName = $('#criteria_title').val();
        
        var company_code = $('#txt_company_code').val();
        //отправка критерия на сервер
        $.ajax({
            'contentType' : "application/json",
            'type': 'POST',
            'url': 'save_criteria/'+ company_code,
            'data': JSON.stringify(serverObject),
            success: function(data){            
                showMessage('alert-info',data);
                getAllCriteria(company_code);
            },
            error:function(jqXHR, textStatus, errorThrown){
                showMessage('alert-danger', xhr.responseJSON);
            }
        });
        getAllMessagesToList();
    });   
});


