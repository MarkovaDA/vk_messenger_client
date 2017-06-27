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
            getMessages(company_code);
            getAllMessagesToList(company_code);//прогружаем кол-во сообщений в список
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
            url: 'update_company?uid='+$('#txt_uid').val(),
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
        console.log("добавление кампании", company);
        $.ajax({
            /*headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },*/       
            contentType : 'application/json',
            type: 'POST',
            url: 'add_company?uid='+$('#txt_uid').val(),
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
    $('#btn_delete_company').click(function(){
        $("#deleteModal").modal('show');
    });
    $('#btn_really_delete').click(function(){
        $.post("api/company/delete/"+company_code, function(data){
            showMessage('alert-success', 'кампания удалена успешно');
        });
        $("#deleteModal").modal('hide');
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
function getMessages(company_code){   
    //отображаем все сообщения по критерию, внутри объявление и добавление и тра-та-та
     $.ajax({
          'contentType' : "application/json",
          'type': 'GET',
          'url': 'api/messages/company/'+ company_code,
          success: function(data){
            $('.message_container .message_pattern').remove();
            $('.message_container').empty();
            if (data.length === 0){
               $('.message_container').append('<p>у данной кампании сообщения отсутствуют</p>');  
            }
            var cloned_block = $('.message_pattern');
            for(index in data){
                var item = data[index];
                cloned_block = cloned_block.clone();
                cloned_block.find('.message_text').text(item["text"]);
                cloned_block.attr('message_id', item["id"]);
                cloned_block.find('.btn_delete_message').bind("click", function(){
                    var id = $(this).parent().attr('message_id'); 
                    $(this).parent().fadeOut(100);
                    $.post("api/messages/delete/"+id, function(){});
                });
                $('.message_container').append(cloned_block);
            }
            $('.btn_save_message').bind("click", function(){
                    var id = $(this).parent().attr('message_id');
                    var text = $(this).parent().find('.message_text').val();
                    console.log(text);
                    $.post("api/messages/update/"+id, 
                        {title: text}, function() {
                        showMessage('alert-success', 'сообщение было обновлено!');
                    });               
            });
            $('.message_container .message_pattern').show();
          },
          error:function(jqXHR, textStatus, errorThrown){
             console.log(jqXHR, textStatus, errorThrown);
          }
      });
}
/*генерирование рандомного кода*/
function randomInteger(min, max) {
    var rand = min - 0.5 + Math.random() * (max - min + 1)
    rand = Math.round(rand);
    return rand;
}




