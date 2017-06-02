$(document).ready(function(){
    $('#select_company').on('changed.bs.select', 
        function(event, clickedIndex, newValue, oldValue){
             //выбранная компания
            var company_code = $(this).find('option').eq(clickedIndex).val();
            var company_title = $(this).find('option').eq(clickedIndex).text();
            $('#txt_company_code').val(company_code);
            $('.company_title').empty();
            $('.company_title').append(company_title);
            //разблокирование блока критериев
            $('.toggle_visible').fadeIn(100);
        });
    //генерируем код
    $('#generate').click(function(){
        var code = randomInteger(10000,1000000);
        $('#company_code').val(code);
    });
    //добавление новой компании
    $('#btn_add_company').click(function(){
        var company = new Object();
        company.title = $('#company_title').val();
        company.code = $('#company_code').val();      
        //проверять на пустоту и выводить сообщение об ошибке
         /*headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
         },*/
        /*$.ajax({           
            //ответ интерпретируется как ошибка
            contentType : "application/json",
            type: 'POST',
            url: 'add_company',
            data: JSON.stringify(company),
            success: function(data) {
                console.log("SUCCESS: ", data);
            },
            error: function(xhr, ajaxOptions, thrownError) {
                console.log(thrownError, ajaxOptions, xhr.status);
            },
            done: function(e) {
                console.log("DONE");
            },          
            dataType: 'json'
        });*/
        //тестирвоание работы апи
    });
});

function randomInteger(min, max) {
    var rand = min - 0.5 + Math.random() * (max - min + 1)
    rand = Math.round(rand);
    return rand;
}


