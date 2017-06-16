//скрипт, ответственный за отображение всех критериев
$(document).ready(function(){  
   $('#select_company').on('hide.bs.select', function(){
        getAllCriteria($('#txt_company_code').val());
   });
   /*$('.btn_delete_criteria').click(function(){
       console.log('будем удалять критерий');
   });*/
});
function getAllCriteria(company_code){
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
                cloned_block = cloned_block.clone();
                cloned_block.find('input').val(item["title"]);
                cloned_block.find('.input-group').attr('criteria_id', item["id"]);
                console.log(cloned_block.find('.input-group').attr('criteria_id'));
                $('#all_criteria .panel-body').append(cloned_block); 
                cloned_block.find('.btn_delete_criteria').click(function(){
                    var criteriaId = parseInt($(this).parent().parent().attr('criteria_id'));
                    console.log('удаление критерия', criteriaId);
                    deleteCriteriaById(criteriaId);
                });
            }
            $('.panel-body .separate_criteria').show();
        },
        error:function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR, textStatus, errorThrown);
            //showMessage('alert-danger', xhr.responseJSON);
        }
    });
}
function deleteCriteriaById(id){
    $.ajax({
        'contentType' : "application/json",
        'type': 'POST',
        'url': 'company/criteria/'+ id + '/delete',
        success:function(data){
            console.log(data);
        },
        error:function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR,textStatus,errorThrown);
        }
    });
}


