$(document).ready(function(){
    $('#select_company').on('changed.bs.select', 
        function(event, clickedIndex, newValue, oldValue){
             //выбранная компания
            var company_code = $(this).find('option').eq(clickedIndex).val();
            var company_title = $(this).find('option').eq(clickedIndex).text();
            console.log(company_title);
            $('#txt_company_code').val(company_code);
            $('.company_title').empty();
            $('.company_title').append(company_title);
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
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: 'POST',
            url: 'add_company',
            data: JSON.stringify(company),        
            success: function(data){
                console.log("успех");
                console.log(data);
            },           
            dataType: 'json'
        });
    });
});

function randomInteger(min, max) {
    var rand = min - 0.5 + Math.random() * (max - min + 1)
    rand = Math.round(rand);
    return rand;
}


