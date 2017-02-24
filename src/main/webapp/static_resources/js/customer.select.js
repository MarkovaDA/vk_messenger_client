 $(document).ready(function(){
    $('.select_items').hide();
    $('.select_header button').click(function(){
        //$('.select_items').slideDown(300);
        $(this).parent().parent().find('.select_items').slideDown(300);
    });
    $('.select_header input').focus(function(){
        //$('.select_items').slideDown(300); 
        $(this).parent().parent().find('.select_items').slideDown(300);
    });
    
    $('.select_items div').click(function()
    {
        $(this).parent().slideUp(100);
        $(this).parent().parent().find('input').val($(this).text());
    });
    
    $('.select_items').mouseleave(function(){
        $(this).fadeOut(100);
    });
    
    $('.select_header input').keyup(function(){
        var value = $(this).val();
        $('.select_items').children().each(function(index, elem){
           var current = $(this).text();
           if (current.indexOf(value) >= 0) $(this).show(); else $(this).hide();
    });
});
});


