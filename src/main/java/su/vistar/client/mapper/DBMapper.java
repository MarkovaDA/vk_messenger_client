
package su.vistar.client.mapper;

import su.vistar.client.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

@Transactional("mainManager")
public interface DBMapper {
    
    @Select("select * from vk_messenger_v2.users where login=#{login}")
    User findUserByLogin(@Param("login")String login);
    
    @Select("select type FROM vk_messenger_v2.users left join vk_messenger_v2.user_profile " +
    "on vk_messenger_v2.user_profile.user_id = vk_messenger_v2.users.id " +
    "left join vk_messenger_v2.profile " +
    "on vk_messenger_v2.profile.id = vk_messenger_v2.user_profile.profile_id where login=#{login}")
    String roleByLogin(@Param("login")String login);
    
    @Update("update vk_messenger_v2.users set access_token=#{access_token},last_date=#{last_date} where login=#{login} and password=#{password}")
    void updateAccessToken(User user);
    
    @Insert("insert into vk_messenger_v2.criteria (vk_messenger_v2.criteria.condition, offset, user_id) values (#{condition},#{offset},#{user_id})")
    void saveCriteria(@Param("condition")String condition, @Param("offset")int offset, @Param("user_id")int userId);
    
    @Insert("insert into vk_messenger_v2.messages (criteria_id, text) values (#{criteria_id},#{text})")
    void saveMessageForCriteria(@Param("criteria_id")int criteriaId, @Param("text")String message);
    
    @Select("select LAST_INSERT_ID()")
    Integer lastInsertedCriteriaId();
    //переделать запрос на только с объектом, а объект обновлять из кода
}
