//скрипт, ответственный за отображение всех критериев
$(document).ready(function(){
   
   $('#select_company').on('hide.bs.select', function(){
        getAllCriteria($('#txt_company_code').val());
        /*$('#all_criteria .panel-body .separate_criteria').remove();
        var cloned_block = $('.separate_criteria');
        var criteria_count = randomInteger(2,10);//имитирует число критериев
        console.log(criteria_count);
        for (var i=0; i < criteria_count; i++) {
            cloned_block = cloned_block.clone();
            cloned_block.find('input').val(i);
            $('#all_criteria .panel-body').append(cloned_block);
        }    
        $('.panel-body .separate_criteria').show();*/
   });
});
function getAllCriteria(company_code){
    
    console.log('get all criteria');
    //отображение всех критериев
    $.ajax({
        'contentType' : "application/json",
        'type': 'GET',
        'url': 'company/criteria/'+ company_code,
        success: function(data){
            console.log(data);
            $('#all_criteria .panel-body .separate_criteria').remove();
            var cloned_block = $('.separate_criteria');
            for(index in data){
                var item = data[index];
                console.log(item["title"]);
                cloned_block = cloned_block.clone();
                cloned_block.find('input').val(item["title"]);
                $('#all_criteria .panel-body').append(cloned_block);                
            }
            $('.panel-body .separate_criteria').show();
        },
        error:function(jqXHR, textStatus, errorThrown){
            
            console.log(jqXHR, textStatus, errorThrown);
            //showMessage('alert-danger', xhr.responseJSON);
        }
    });
}


