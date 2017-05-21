$(document).ready(function () {
    console.log("included");
    $('#criteria_type').on('changed.bs.select', function(event, clickedIndex, newValue, oldValue){
        //event, clickedIndex, newValue, oldValue.
        var prop = $(this).children().eq(clickedIndex).attr('value');
            console.log(prop);
        if (String(newValue)==='true'){
            $('#' + prop).fadeIn(100);
        }
        if (String(newValue)==='false'){
            $('#' + prop).fadeOut(100);
        }
        /*for(var index in options){
            option_name = options[index];          
            if (Boolean(newValue) === true){
                console.log('new value is true');
            }
            else if (Boolean(newValue) === false){
                $('#' + option_name).fadeOut(100);
            }
        }*/
    });
});


