function isEmptyValue(selector) {
    var value = $(selector).val();
    if (value !== null && typeof value !== 'undefined')
        return !(value.length > 0);
    else
        return true;
}
$(document).ready(function () {
    $("#select_city").click(function () {
        console.log("city_id=" + $(this).val());
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
                }
        );
    });
    $('#select_univ').click(function () {
        console.log("university_id=" + $(this).val()); //выводим значение университета(?) не работает
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
                }
        );
    });
    $('#btn_add').click(function () {
        var criteria = new Object();
        criteria.university = !isEmptyValue('#select_univ') ? $('#select_univ').val() : null;
        criteria.university_faculty = !isEmptyValue('#select_fac') ? $('#select_fac').val() : null;
        criteria.university_year = !isEmptyValue('#year_field') ? $('#year_field').val() : null;
        criteria.age_from = !isEmptyValue('#age_from_field') ? $('#age_from_field').val() : null;
        criteria.age_to = !isEmptyValue('#age_to_field') ? $('#age_to_field').val() : null;
        criteria.position = !isEmptyValue('#job_field') ? $('#job_field').val() : null;
        criteria.message = $('#message_field').val();
        console.log(criteria);
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'POST',
            'url': 'save_criteria',
            'data': JSON.stringify(criteria),
            'dataType': 'json',
            'success': function (data) {
                console.log(data);
            }
        });
    });
});


