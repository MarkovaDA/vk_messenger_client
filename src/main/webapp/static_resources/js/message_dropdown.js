
function getAllMessagesToList(company_code){
    $('#message_list').empty();
    $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': 'api/get_messages?code='+company_code,
            'success': function (messages) {
                for(var mesIndex in messages){
                    var li = "<li mesid=" + messages[mesIndex].id + 
                            "><a href='#'>" + (messages[mesIndex].text).substring(0,100) + "...</a></li>";
                    $('#message_list').append(li);                  
                }
                //для каждого пункта сообщения выполняется подписка
                $('#message_list li').each(function(index, value){
                    $(this).bind("click", function(){
                        var mesIndex = parseInt($(this).attr('mesid'));
                        //при клике на листе выполняется отображение сообщения
                        getMessageById(mesIndex);
                    });
                });  
            }
        });   
}
function getMessageById(mesId){
    $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': 'api/get_message?mes_id='+mesId,
            'success': function (message) {
               //console.log(message.text);
               $('#message_field').val(message.text);
               $('#selected_mes_id').val(message.id);
            }
    });
}


