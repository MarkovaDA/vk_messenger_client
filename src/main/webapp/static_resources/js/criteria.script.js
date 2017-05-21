function isEmptyValue(selector) {
    var value = $(selector).val();
    if (value !== null && typeof value !== 'undefined')
        return !(value.length > 0);
    else
        return true;
}//ОБОБЩИТЬ СТРУКТУРУ ЗАПРОСОВ ОДНИМ МЕТОДОМ
$(document).ready(function () {
    $('.selectpicker').on('change', function(){
        var id = $(this).attr('id');//идентификатор селекта  
        switch(id){
            case'select_city': 
                $('#select_univ').empty();
                $.get("api/get_universities?token=" + $('#token_field').val() + "&city_id=" + $(this).val(),
                        function (data) {
                            var university;
                            for (var i = 0; i < data.length; i++) {
                                university = data[i];
                                $('#select_univ').append($('<option>', {
                                    value: university.id,
                                    text: university.title
                                }));                                
                            }
                            $('#select_univ').selectpicker('refresh');
                        }
                );
                break;
            case 'select_univ':
                $('#select_fac').empty();
                $.get("api/get_faculties?token=" + $('#token_field').val() + "&univ_id=" + $(this).val(),
                        function (data) {
                            var faculty; 
                            for (var i = 0; i < data.length; i++) {
                                faculty = data[i];                              
                                $('#select_fac').append($('<option>', {
                                    value: faculty.id,
                                    text: faculty.title
                                }));
                            }
                            $('#select_fac').selectpicker('refresh');
                        }
                );
                break;
            case 'select_fac':
                $('#select_chair').empty();
                $.get("api/get_chairs?token=" + $('#token_field').val() + "&chair_id=" + $(this).val(),
                        function (data) {
                            var chair; 
                            for (var i = 0; i < data.length; i++) {
                                chair = data[i];                              
                                $('#select_chair').append($('<option>', {
                                    value: chair.id,
                                    text: chair.title
                                }));
                            }
                            $('#select_chair').selectpicker('refresh');
                        }
                );
                break;
             default:
                 
        }
    });
    //добавление и отправка критерия на сервер
    $('#btn_add').click(function () {
        var criteria = new Object();
        criteria.city = !isEmptyValue('#select_city') ? $('#select_city').val() : null;
        criteria.university = !isEmptyValue('#select_univ') ? $('#select_univ').val() : null;
        criteria.university_faculty = !isEmptyValue('#select_fac') ? $('#select_fac').val() : null;
        criteria.university_year = !isEmptyValue('#year_field') ? $('#year_field').val() : null;
        criteria.age_from = !isEmptyValue('#age_from_field') ? $('#age_from_field').val() : null;
        criteria.age_to = !isEmptyValue('#age_to_field') ? $('#age_to_field').val() : null;
        criteria.position = !isEmptyValue('#job_field') ? $('#job_field').val() : null;
        criteria.message = $('#message_field').val();
        criteria.message_id = $('#selected_mes_id').val();
        console.log(criteria);
        /*$.ajax({
             headers: {
                 'Accept': 'application/json',
                 'Content-Type': 'application/json'
             },
             'type': 'POST',
             'url': 'save_criteria',
             'data': JSON.stringify(criteria),
             'dataType': 'json'         
        });*/
        //updating message performance
        getAllMessagesToList();
    });
});


