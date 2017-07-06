<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="div_pattern" style="display:none;">
    <li class="geo_pattern" object_id="-1" style="display:none; cursor:pointer;">
            <div style="padding: 2px 5px; border-bottom:1px solid #cccccc;">
                <b class="city"></span></b><br>
                <span class="area"></span><br>
                <span class="region"></span><br>                
            </div>
    </li>
</div>
<div class="dropdown input-group" id="dropdown_geo_search" style="display:none;">   
    <div class="input-group dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
        <input id="geo_searched" type="text" class="form-control" placeholder="введите название...">
        <span class="input-group-btn" id="span_search">
              <button class="btn btn-default" type="button">
                <span class="glyphicon glyphicon-search"></span>
              </button>
        </span>
    </div>
    <ul class="dropdown-menu" id="searched_list" style="width:auto !important; height:200px; overflow-y:scroll !important;" aria-labelledby="geo_searched">
    </ul>
</div>
<script>
    $(document).ready(function(){         
        function fullDropdown(){
            getSearchedGeo($('#geo_searched').val());//прогрузка данных по шаблону
            var dropdown = $(this).parent().parent().find('.dropdown-menu');
            var length = dropdown.children().length;
            if (length === 0){
                dropdown.hide();
            }
            else if (length > 0) {
                dropdown.height('200');
                dropdown.show();
                dropdown.find('li').bind('click', function(){
                    dropdown.hide();
                });
            }
        }
        $('#geo_searched').change(function(){
            fullDropdown();
        });
        $('#geo_searched').click(function(){
            fullDropdown();
        });
    });
</script>

