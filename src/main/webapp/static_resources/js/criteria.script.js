
$(document).ready(function () {
    $('.selectpicker').on('change', function(){
        var id = $(this).attr('id');//идентификатор селекта  
        switch(id){
            case 'select_city': 
                $('#select_univ').empty();
                $.get("api/get_universities?" + "city_id=" + $(this).val(),
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
                $.get("api/get_faculties?"  + "univ_id=" + $(this).val(),
                        function (data) {
                            var faculty; 
                            $('#select_fac').append($('<option>', {
                                    value: '',
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
                $.get("api/get_chairs?" + "faculty_id=" + $(this).val(),
                        function (data) {
                            var chair; 
                            $('#select_chair').append($('<option>', {
                                    value: '',
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
            case 'select_school_country':
                $('#select_school_city').empty();
                $.get("api/get_cities_bycountry?" + "country_id=" + $(this).val(),
                        function (data) {
                            console.log(data);
                            var city; 
                            $('#select_school_city').append($('<option>', {
                                    value: '',
                                    text: 'выберите город'
                            }));
                            for (var i = 0; i < data.length; i++) {
                                city = data[i];                              
                                $('#select_school_city').append($('<option>', {
                                    value: city.id,
                                    text: city.title
                                }));
                            }
                            $('#select_school_city').selectpicker('refresh');
                        }
                );
                break;
             default:
        }
    });
    
    //функция, обобщающая все запросы
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


