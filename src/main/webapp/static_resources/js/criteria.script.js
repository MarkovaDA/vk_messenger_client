
$(document).ready(function () {
    $('.selectpicker').on('change', function(){
        var id = $(this).attr('id');//идентификатор селекта  
        switch(id){
            case 'select_city': 
                $('#select_univ').empty();
                $.get("api/get_universities?token=" + $('#token_field').val() + "&city_id=" + $(this).val(),
                        function (data) {
                            var university;
                            $('#select_univ').append($('<option>', {
                                    value: '',
                                    text: 'выберите университет'
                                }));
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
                            $('#select_fac').append($('<option>', {
                                    value: -1,
                                    text: 'выберите факультет'
                            }))
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
                $.get("api/get_chairs?token=" + $('#token_field').val() + "&faculty_id=" + $(this).val(),
                        function (data) {
                            var chair; 
                            $('#select_chair').append($('<option>', {
                                    value: -1,
                                    text: 'выберите кафедру'
                            }));
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
    
    function get_objects(select_id,api_str,param_id){
        $(select_id).empty();
        $.get("api/"+api_str+"?token=" + $('#token_field').val() + param_id + $(this).val(),
                function (data) {
                    var item; 
                    for (var i = 0; i < data.length; i++) {
                        item = data[i];                              
                        $(select_id).append($('<option>', {
                            value: item.id,
                            text: item.title
                        }));
                    }
                    $(select_id).selectpicker('refresh');
                }
        );
    }            
});


