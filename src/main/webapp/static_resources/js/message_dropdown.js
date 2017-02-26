$(document).ready(function(){
    $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': 'api/get_messages',
            'success': function (messages) {
                for(var mesIndex in messages){
                    var li = "<li mesid=" + messages[mesIndex].id + 
                            "><a href='#'>" + (messages[mesIndex].text).substring(0,100) + "...</a></li>";
                    $('#message_list').append(li);                  
                }
                $('#message_list li').each(function(index, value){
                    $(this).bind("click", function(){
                        var mesIndex = parseInt($(this).attr('mesid'));
                        getMessageById(mesIndex);
                    });
                });  
            }
        });
});
function getMessageById(mesId){
    $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': 'api/get_message?mes_id='+mesId,
            'success': function (message) {
               console.log("полученное сообщение:" + message);
               $('#message_field').text(message.text);
            }
    });
}


