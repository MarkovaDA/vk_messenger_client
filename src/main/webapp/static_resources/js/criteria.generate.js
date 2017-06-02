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
            $('#' + prop).fadeIn(100);           
        }
        if (String(newValue)==='false'){
            delete criteria[prop];
            $('#' + prop).find('select').removeClass('readable');
            $('#' + prop).find('input').removeClass('readable');
            $('#' + prop).fadeOut(100);          
        }     
    });
    //считываем с селектов параметры в объект criteria
    $('.selectpicker').on('changed.bs.select', function(event, clickedIndex, newValue, oldValue){
        if ($(this).hasClass('.readable')){
            var selectedValue = $(this).find('option').eq(clickedIndex).val();
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
        var propertyName, propertyValue;
        $('.readable').each(function(){
            propertyName = $(this).attr('property');
            propertyValue = $(this).val();           
            if (typeof propertyName !== 'undefined' && !isEmptyValue(propertyValue)){
                criteria[propertyName] = propertyValue;            
            }            
        });
        var criteriaString = "";
        for(var key in criteria){
            if (!isEmptyValue(key) && !isEmptyValue(criteria[key]))
                criteriaString += key + "=" + criteria[key] + "&";
        }
        console.log(criteriaString);
        //criteriaString = criteriaString.substring(0, criteriaString.length-2);
        var serverObject = new Object();
        serverObject.criteriaString = criteriaString;
        serverObject.message = $('#message_field').val();        
        serverObject.message_id = $('#selected_mes_id').val();
        var company_code = $('#txt_company_code').val();
        //отправка критерия на сервер
        /*$.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'POST',
            'url': 'save_criteria/'+ company_code,
            'data': JSON.stringify(serverObject),
            'dataType': 'json',
            success: function(data){
                console.log(data);
            },
            error:function(jqXHR, textStatus, errorThrown){
                console.log(jqXHR + textStatus+errorThrown);
            }
        });*/
        //updating message performance
        getAllMessagesToList();
    });   
});


