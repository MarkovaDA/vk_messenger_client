
package su.vistar.client.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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
    
    @Insert("insert into vk_messenger_v2.country (id,title) values (#{id}, #{title})")
    public void insertCountry(@Param("id")Integer id, @Param("title")String title);
    
    @Insert("insert into vk_messenger_v2.city (id,title,area,region,country_id) values (#{id}, #{title},#{area}, #{region},#{country_id})")
    public void insertCity(@Param("id")Integer id, @Param("title")String title, @Param("area")String area, @Param("region")String region, @Param("country_id")Integer countryId);
    
    @Select("select distinct id from vk_messenger_v2.country where id > 3")
    public List<Integer> getCountriesIds();
}
