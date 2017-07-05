//отображение статистики по  критериям в рамках выбранной кампании
$(document).ready(function(){
    var st_company_code, st_company_title;
    $('#st_select_company').on('changed.bs.select', 
        function(event, clickedIndex, newValue, oldValue){
            st_company_code = $(this).find('option').eq(clickedIndex).val();
            st_company_title = $(this).find('option').eq(clickedIndex).text();
            $('#st_company_title').empty();
            $('#st_company_title').append(st_company_title);
            fullStatistics(st_company_code);
        }
    );
});
//отображение статистики
function fullStatistics(company_code){
    $.ajax({
          'contentType' : "application/json",
          'type': 'GET',
          'url': 'company/criteria/'+ company_code,
          success: function(data){                    
            $('.list-group .separate_li').remove();
            $('.list-group').empty();
            var cloned_block = $('.separate_li');
            for(index in data){
              var item = data[index];
              cloned_block = cloned_block.clone();
              cloned_block.find('.criteria_title').text(item["title"]);
              cloned_block.find('.badge').text(item["offset"]);
              cloned_block.attr('criteria_id', item["id"]);
              $('.list-group').append(cloned_block); 
            }
            $('.list-group .separate_li').show();
            $('.btn_criteria_report').click(function(){
                var url = "statistics/" + $(this).parent().attr("criteria_id");
                window.open(url);
            });
          },
          error:function(jqXHR, textStatus, errorThrown){
             console.log(jqXHR, textStatus, errorThrown);
          }
      });
    }


