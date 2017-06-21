
package su.vistar.client.mapper;

import org.springframework.transaction.annotation.Transactional;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import su.vistar.client.model.User;

@Transactional("mainManager")
public interface AuthUserMapper {
    
    @Select("SELECT * FROM vk_messenger_v2.users where uid=#{uid}")
    public User getUserByUid(Long uid);
    
    @Insert("INSERT into vk_messenger_v2.users (login,password,status,last_date,fio,access_token,expires_in,uid) "
            + "values (#{login},#{password},#{status},#{last_date},#{fio},#{access_token},#{expires_in},#{uid})")
    public void addNewUser(User user);
    
    @Select("UPDATE vk_messenger_v2.users SET status = #{status} where uid=#{uid}")
    public String updateUserStatus(@Param("status")String status, @Param("uid")Long uid);
}
