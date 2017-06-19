$(document).ready(function(){
    var company_code, company_title;
    $('#select_company').on('changed.bs.select', 
        function(event, clickedIndex, newValue, oldValue){
             //выбранная компания
            company_code = $(this).find('option').eq(clickedIndex).val();
            company_title = $(this).find('option').eq(clickedIndex).text();
            $('#txt_company_code').val(company_code);
            $('.company_title').empty();
            $('.company_title').append(company_title);
            //разблокирование блока критериев
            $('.toggle_visible').fadeIn(100);
            $('#st_company_title').empty();
            $('#st_company_title').append(company_title);
            fullStatistics(company_code);  
            //getAllCriteria(company_code);
        });
    //генерируем код
    $('#generate').click(function(){
        var code = randomInteger(10000,1000000);
        $('#company_code').val(code);
    });
    //обновление кода кампании
    $('#btn_update_company').click(function(){
        var company = new Object();
        company.title = company_title;
        company.code =  $('#txt_company_code').val();//сюда новое значение нужно        
        if (!Number.isInteger(parseInt(company.code))){
            showMessage('alert-danger', "обновленный код не является целочисленным");
            return;
        }
        
        $.ajax({          
            contentType : 'application/json',
            type: 'POST',
            url: 'update_company',
            data: JSON.stringify(company),
            dataType: 'json',
            success: function(data) {
                showMessage('alert-info', data);
            },  
            error: function(xhr, ajaxOptions, thrownError) {
                console.log(xhr,ajaxOptions,thrownError);
                showMessage('alert-danger', xhr.responseJSON);
            }
        });
        
    });
    //добавление новой компании
    $('#btn_add_company').click(function(){
        var company = new Object();
        company.title = $('#company_title').val();
        company.code = $('#company_code').val();
        if (!Number.isInteger(parseInt(company.code))){
            showMessage('alert-danger', "сгенерированный код не является целочисленным");
            return;
        }     
        $.ajax({
            /*headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }*/        
            contentType : "application/json",
            type: 'POST',
            url: 'add_company',
            data: JSON.stringify(company),
            dataType: 'json',
            success: function(data) {
                showMessage('alert-info', data);
            },  
            error: function(xhr, ajaxOptions, thrownError) {
                showMessage('alert-danger', xhr.responseJSON);
            }                   
        });
    });
});
function deleteAllClass(){
    //alert-info, alert-success, alert-warning, alert-danger
    $('.message_block .alert')
           .removeClass('alert-info')
           .removeClass('alert-success')
           .removeClass('alert-warning')
           .removeClass('alert-danger'); 
}
/*
 * Отображение всплывающего сообщения со статусом выполнения
 * type: alert-info
 *       alert-success
 *       alert-warning
 *       alert-danger
 */
function showMessage(type, text) {
    $('.message_block .alert').addClass(type);
        $('.message_block .alert').text(text);
        $('.message_block').fadeIn(100);
        //прокрутка к всплывающему сообщению
        scrollUp();//вот эта функция вызывает сложности
        var timerId = setTimeout(function()
        {   
            deleteAllClass();
            $('.message_block').fadeOut(100);
            clearTimeout(timerId);
        },            
        2000);
    
}
/*генерирование рандомного кода*/
function randomInteger(min, max) {
    var rand = min - 0.5 + Math.random() * (max - min + 1)
    rand = Math.round(rand);
    return rand;
}


