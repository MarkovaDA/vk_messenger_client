
package su.vistar.client.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.client.dto.CriteriaDTO;


@Transactional("mainManager")
public interface CriteriaMapper {
    
    @Select("select * from vk_messenger_v2.criteria where id=107")
    public CriteriaDTO getCriteria();
   
    @Delete("delete from vk_messenger_v2.criteria where id=#{id}")
    public void deleteCriteriaById(@Param("id")Integer id);
    
    @Delete("delete from vk_messenger_v2.criteria_message where criteria_id=#{id}")
    public void deleteCriteriaReference(@Param("id")Integer id);
}
