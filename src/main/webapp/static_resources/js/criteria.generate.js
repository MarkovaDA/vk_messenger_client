$(document).ready(function () {
    $('#criteria_type').on('changed.bs.select', function(event, clickedIndex, newValue, oldValue){
        var prop = $(this).children().eq(clickedIndex).attr('value');
        if (String(newValue)==='true'){
            $('#' + prop).fadeIn(100);
        }
        if (String(newValue)==='false'){
            $('#' + prop).fadeOut(100);
        }     
    });


    $('#btn_add').click(function () {
        var propertyName, propertyValue;       
        $('table tr').each(function(index,elem){
            propertyName = $(this).attr('id');
                if (typeof propertyName !== 'undefined'){
                    propertyValue = $(this).find('.for_reading').length;
                    console.log(propertyValue);
                }
        });
        /*var criteria = new Object();
        criteria.city = !isEmptyValue('#select_city') ? $('#select_city').val() : null;
        criteria.university = !isEmptyValue('#select_univ') ? $('#select_univ').val() : null;
        criteria.university_faculty = !isEmptyValue('#select_fac') ? $('#select_fac').val() : null;
        criteria.university_year = !isEmptyValue('#year_field') ? $('#year_field').val() : null;
        criteria.age_from = !isEmptyValue('#age_from_field') ? $('#age_from_field').val() : null;
        criteria.age_to = !isEmptyValue('#age_to_field') ? $('#age_to_field').val() : null;
        criteria.position = !isEmptyValue('#job_field') ? $('#job_field').val() : null;
        criteria.message = $('#message_field').val();
        criteria.message_id = $('#selected_mes_id').val();
        $.ajax({
             headers: {
                 'Accept': 'application/json',
                 'Content-Type': 'application/json'
             },
             'type': 'POST',
             'url': 'save_criteria',
             'data': JSON.stringify(criteria),
             'dataType': 'json'         
        });
        //updating message performance
        getAllMessagesToList();*/
    });   
});


