
package su.vistar.client.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.client.dto.CriteriaDTO;
import su.vistar.client.model.Criteria;

@Transactional("mainManager")
public interface CriteriaMapper {
    
    @Select("select * from vk_messenger_v2.criteria where id=107")
    public CriteriaDTO getCriteria();
    
    public Criteria getAll();
    
    @Delete("delete from vk_messenger_v2.criteria where id=#{id}")
    public void deleteCriteriaById(@Param("id")Integer id);
}
