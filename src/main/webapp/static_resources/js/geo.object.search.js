/* 
 * поиск населенного пункта по названию
 */
$(document).ready(function(){
    $('#geo_searched').bind("change", function() {        
        getSearchedGeo($(this).val());
    });
    $('#ch_city').change(function() {   
        if (this.checked) {
            //показать
            $('#dropdown_geo_search').fadeIn(100);
        } else {
            $('#dropdown_geo_search').fadeOut(100);
        }
    });
});

function getSearchedGeo(pattern){
    $.ajax({
        'contentType' : "application/json",
        'type': 'GET',
        'url': 'api/city/search?q=' + pattern + "&country_id=1",
        success:function(data){
            var item;
            var cloned_block = $('#div_pattern .geo_pattern');
            $('#searched_list').empty();
            for(var index in data){
                item = data[index];
                console.log(item);
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
                    $('#geo_searched').val($(this).find('.city').text());
                    $('#select_city').val($(this).find('.city').text());
                    criteria["city"] = $(this).attr('object_id');
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


