/* 
 * поиск населенного пункта по названию
 */
$(document).ready(function(){
   
    $("input[type='checkbox']").change(function() {
        if (this.checked) {
            var panel = $('#dropdown_geo_search').detach();
            $(this).parent().append(panel);
            $('#dropdown_geo_search').fadeIn(100);
            //подгрузить факультеты            
        } else {
            console.log("показано");
            $('#dropdown_geo_search').fadeOut(100);
        }
    });
});

function getSearchedGeo(pattern){
    var searchUrl = "api/city/search";
    var paramObject = new Object();
    paramObject.country_id=1;
    paramObject.q = pattern;
    console.log(JSON.stringify(paramObject));
    //передадим это и посмотрим, что будет
    $.ajax({
        'data': JSON.stringify(paramObject),
        'contentType' : "application/json",
        'type': 'POST',
        'url': searchUrl,
        success:function(data) {
            var item;
            var cloned_block = $('#div_pattern .geo_pattern');
            $('#searched_list').empty();
            console.log('объекты, удовлетворяющие условиям поиска', data);
            for(var index in data){
                item = data[index];
                cloned_block = cloned_block.clone();
                cloned_block.find('.city').empty().append(item["title"]);
                if (item["area"] === null)
                    item["area"]="---";
                if (item["region"] === null)
                    item["region"]="---";
                cloned_block.find('.area').empty().append(item["area"]);
                cloned_block.find('.region').empty().append(item["region"]);
                cloned_block.attr('object_id', item["id"]);
                cloned_block.bind('click', function(){
                    //с формируемым объектом какая-то лажа
                    $('#geo_searched').val($(this).find('.city').text());
                    $('#select_city').val($(this).find('.city').text());
                    criteria["city"] = $(this).attr('object_id');
                    //подгрузка факультетов
                    get_objects('#select_univ','get_universities?', 'city_id=', criteria["city"]);
                    //подгрузка школ
                    get_objects('#select_school','get_schools_bycity?', 'city_id=',criteria["city"]);
                });
                $('#searched_list').append(cloned_block);
            }
            $('#searched_list .geo_pattern').show();
        },
        error:function(data){
            console.log(data);
        }
    });
}


