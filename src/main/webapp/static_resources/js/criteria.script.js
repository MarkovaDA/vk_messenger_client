
$(document).ready(function () {
    $('.selectpicker').on('change', function(){
        var id = $(this).attr('id');//идентификатор селекта  
        switch(id)
        {
            case 'select_country':
                $('#select_city').empty();
                get_objects('#select_city','get_cities_bycountry?', 'country_id=', $(this).val());
            case 'select_city': 
                $('#select_univ').empty();
                get_objects('#select_univ','get_universities?', 'city_id=', $(this).val());
                 break;
            case 'select_univ':
                $('#select_fac').empty();
                get_objects('#select_fac','get_faculties?', 'univ_id=', $(this).val());               
                break;
            case 'select_fac':
                $('#select_chair').empty();
                get_objects('#select_chair','get_chairs?', 'faculty_id=', $(this).val());
                break;
            case 'select_school_country':
                $('#select_school_city').empty();              
                get_objects('#select_school_city','get_cities_bycountry?', 'country_id=', $(this).val());
                break;
            case 'select_school_city':
                $('#select_school').empty();
                //подгрузка зависящих зависимостей
                get_objects('#select_school','get_schools_bycity?', 'city_id=', $(this).val());
                break;
            default:
        }
    });
             
});
  //обобщенная функция получения данных об объектах
function get_objects(select_id,api_str,param_id, value) {
    $(select_id).empty();
    var url = "api/"+api_str + param_id + value;     
    $.get(url, function (data) {
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


