//скрипт, ответственный за отображение всех критериев
$(document).ready(function(){  
   $('#select_company').on('hide.bs.select', function(){
        //выбираем кампанию -> отображаем критерий
        getAllCriteria($('#txt_company_code').val());
   });
});
//получение списка критериев
function getAllCriteria(company_code){    
    //отображение всех критериев кампании
    $.ajax({
        'contentType' : "application/json",
        'type': 'GET',
        'url': 'company/criteria/'+ company_code,
        success: function(data){
            if (data.length === 0) {
                $('#all_criteria .panel-body').empty();
                $('#all_criteria .panel-body').append("<p>критерии отсутсвуют</p>");
            };
            $('#all_criteria .panel-body .separate_criteria').remove();
            $('#all_criteria .panel-body').empty();
            var cloned_block = $('.separate_criteria');
            for(index in data){
                var item = data[index];
                cloned_block = cloned_block.clone();
                cloned_block.find('input').val(item["title"]);
                cloned_block.find('.input-group').attr('criteria_id', item["id"]);
                $('#all_criteria .panel-body').append(cloned_block); 
                cloned_block.find('.btn_delete_criteria').click(function(){
                    var criteriaId = parseInt($(this).parent().parent().attr('criteria_id'));
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
//удаление критерия
function deleteCriteriaById(id){
    $.ajax({
        'contentType' : "application/json",
        'type': 'POST',
        'url': 'company/criteria/'+ id + '/delete',
        success:function(data){
            $('.separate_criteria').find('.input-group').each(function(index){             
                if (parseInt($(this).attr('criteria_id')) === id){                    
                    $(this).parent().fadeOut(100);
                    var serachExpr = '.separate_li[criteria_id=\"' + id + "\"]";
                    $(serachExpr).hide();
                    //в блоке статистики сокрытие соответствующего модуля
                }
            });
            showMessage('alert-info', data);
        },
        error:function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR,textStatus,errorThrown);
        }
    });
}


