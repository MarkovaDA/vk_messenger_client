

$(document).ready(function(){
    var selectedFaculty, selectedCourse, selectedGroup;
    
    $('#select_faculties .select_items div').click(function(){

        var fac_id = parseInt($(this).attr("fac_id"));
        selectedFaculty = fac_id;//выбранный факультет
        $('#select_courses .select_items').empty();
        //подгрузка курсов теперь
        $.get("api/get_courses?fac_id="+fac_id, function(data){
            $('#selected_courses .select_items').empty();
            //id, number
            for(var i=0; i < data.length; i++){
                var course = data[i];
                var option = '<div course_id='+course.id + '>' + course.number + '</div>';
                $('#select_courses .select_items').append(option);
                
            }
            //привязать событие выборки
            $('#select_courses .select_items div').click(function()
            {
                $(this).parent().slideUp(100);
                $(this).parent().parent().find('input').val($(this).text());
                selectedCourse = $(this).attr("course_id");
                $.get("api/get_groups?course_id="+parseInt($(this).attr("course_id")), function(data){
                    $('#selected_groups .select_items').empty();
                    for(var j in data){
                        var group = data[j];
                        var option = '<div group_id=' + group.id + '>' + group.number + '</div>';
                        $('#selected_groups .select_items').append(option);                        
                    }
                    $('#selected_groups .select_items div').click(function(){
                            $(this).parent().slideUp(100);
                            var inputField = $(this).parent().parent().find('input');
                            inputField.attr('group_value_id',$(this).attr('group_id'));
                            selectedGroup = $(this).attr('group_id');
                            inputField.val($(this).text());
                        }
                    );
                });
            });
        });
    });
    var filterArray = new Array(); //массив фильтров
    //отправка запроса на сервер
    $('#add_filter_btn').click(function(){
        var filterObject = new Object();      
        //шаблоны фильтров для отображения
        filterObject["faculty"] = parseInt(selectedFaculty);
        filterObject["course"] = parseInt(selectedCourse);
        filterObject["group"] = parseInt(selectedGroup);
        filterObject["only_captain"] = $('#only_captain').is(":checked");
        filterArray.push(filterObject);
        console.log(filterArray);
        var facultyText = ($('#faculty_value').val() !== "") ?  $('#faculty_value').val() : "";
        var courseText = ($('#course_value').val() !== "") ? ", курс:" + $('#course_value').val() : "";   
        var groupText =  ($('#groupe_id').val() !== "") ? ", группа:" + $('#groupe_id').val() : "";     
        var filterText = facultyText + courseText + groupText;
        
        if (filterText.length > 0){
            var pattern = "<div style=\"margin-top:5px;\"><div class=\"ui horizontal label\">" + filterText + "<i class=\"delete icon\"></i></div></div>";
            $('#filters').append(pattern);
            //привязываем событие удаления фильтра
            var length = $('#filters').find('.delete').length;
            
            $('#filters').find('.delete').eq(length-1).click(function(){
               var index =  parseInt($(this).parent().parent().index());
               filterArray.splice(index,1);
               $(this).parent().parent().remove();
            });
        }
        $('#send_filters').click(function(){
            var obj = new Object(); //объект, состоящий 
                obj.message = $('#text_message').val();
                obj.add_signature = $('#add_signature').is(":checked"); //добавлять подпись
                obj.filters = filterArray;
            
            console.log(obj);
            
            $.ajax({
                    headers: { 
                    'Accept': 'application/json',
                    'Content-Type': 'application/json' 
                    },
                    'type': 'POST',
                    'url': 'api/send_info',
                    'data': JSON.stringify(obj),
                    'dataType': 'json',
                    'success': function(data){
                        console.log(data);
                    }
            });
        });       
    });
    //удаление фильтра
});

