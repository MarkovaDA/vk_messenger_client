package su.vistar.client.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import su.vistar.client.model.User;
import su.vistar.client.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.client.dto.CompanyDTO;
import su.vistar.client.dto.CriteriaDTO;
import su.vistar.client.model.Company;

@Transactional("mainManager")
public interface DBMapper {
    
    @Select("SELECT * from vk_messenger_v2.users WHERE login=#{login}")
    User findUserByLogin(@Param("login")String login);
    
    @Select("SELECT * from vk_messenger_v2.company WHERE user_id=#{user_id}")
    List<Company> findCompany(@Param("user_id")Integer userId);
    
    @Insert("INSERT into vk_messenger_v2.company (title,code,user_id) values(#{title},#{code},#{user_id})")
    Integer addCompany(Company company);
    
    @Update("UPDATE vk_messenger_v2.company set code=#{code} where title=#{title} and user_id=#{user_id}")
    Integer updateCompany(Company company);
    
    @Select("SELECT type FROM vk_messenger_v2.users left join vk_messenger_v2.user_profile " +
    "ON vk_messenger_v2.user_profile.user_id = vk_messenger_v2.users.id " +
    "LEFT join vk_messenger_v2.profile " +
    "ON vk_messenger_v2.profile.id = vk_messenger_v2.user_profile.profile_id where login=#{login}")
    String roleByLogin(@Param("login")String login);
    
    @Update("UPDATE vk_messenger_v2.users SET access_token=#{access_token},last_date=#{last_date} WHERE login=#{login} and password=#{password}")
    void updateAccessToken(User user);
    
    @Insert("INSERT into vk_messenger_v2.criteria (vk_messenger_v2.criteria.condition, offset, company_id) values (#{condition},#{offset},#{company_id})")
    void saveCriteria(@Param("condition")String condition, @Param("offset")int offset, @Param("company_id")int companyId);
    
    @Insert("INSERT into vk_messenger_v2.messages (text) values (#{text})")
    void saveMessage(@Param("text")String message);
    
    @Insert("INSERT into vk_messenger_v2.criteria_message (criteria_id, message_id) values (#{criteria_id}, #{message_id})")
    void saveMessageCriteria(@Param("criteria_id")Integer criteriaId, @Param("message_id")Integer messageId);
    
    @Select("SELECT id, text FROM vk_messenger_v2.messages")
    List<Message> getAllMessage();
    
    @Select("SELECT id,text FROM vk_messenger_v2.messages WHERE id=#{mesId}")
    Message getMessageById(@Param("mesId")Integer mesId);
      
    @Select("SELECT LAST_INSERT_ID()")
    Integer lastInsertedId();
    
    @Select("SELECT * from vk_messenger_v2.company where code=#{code}")
    Company getCompanyByCode(@Param("code")Long code);
    
    @Insert("INSERT into vk_messenger_v2.sendors(vk_uid, company_id, message_count) values (#{vk_uid},#{company_id},#{message_count})")
    Integer subscribe(@Param("vk_uid")Long vkUid, @Param("company_id")Integer companyId,  @Param("message_count")Integer message_count);   
 
    @Delete("DELETE from vk_messenger_v2.sendors where vk_uid=#{vk_uid} and company_id=#{company_id}")
    Integer unscribeFromCompany(@Param("vk_uid")Long vkUid, @Param("company_id")int companyId);
    
    @Delete("DELETE from vk_messenger_v2.sendors where vk_uid=#{vk_uid}")
    Integer unscribeFromAll(@Param("vk_uid")Long vkUid);
    
    @Select("SELECT id from vk_messenger_v2.sendors where vk_uid=#{vk_uid} and company_id=#{company_id}")
    Object tryUnigueSubscribe(@Param("vk_uid")Long vkUid, @Param("company_id")int companyId);

    @Select("SELECT distinct title, code, fio FROM vk_messenger_v2.company join vk_messenger_v2.sendors ON "
            + "vk_messenger_v2.company.id = vk_messenger_v2.sendors.company_id "
            + "left join vk_messenger_v2.users on vk_messenger_v2.company.user_id = vk_messenger_v2.users.id "
            + "where vk_uid=#{vk_uid}")
    List<CompanyDTO> getCompanies(@Param("vk_uid")String vkUid);
    
    @Select("SELECT company.id,code, title, fio FROM vk_messenger_v2.company join " +
            "vk_messenger_v2.users on users.id=company.user_id where code=#{code}")
    CompanyDTO getCompanyInfo(Long code);
    
    @Select("SELECT * from vk_messenger_v2.criteria where company_id=#{company_id} and considered=0")
    List<CriteriaDTO> getCriteriesByCompanyId(@Param("company_id")Integer companyId);
    
    @Select("SELECT * FROM vk_messenger_v2.messages join vk_messenger_v2.criteria_message on id=message_id "
            + "where criteria_id=#{criteria_id}")
    Message getMessageByCriteriaId(@Param("criteria_id")Integer criteriaId);
    
    @Update("UPDATE vk_messenger_v2.criteria set offset=#{offset} where id=#{criteria_id}")
    void updateOffset(@Param("criteria_id")Integer criteriaId, @Param("offset")Integer offset);
    
    @Select("SELECT sum(message_count) from vk_messenger_v2.sendors where vk_uid=#{vk_uid} group by vk_uid")
    Integer countOfSubscribesForUser(@Param("vk_uid")Long vkUid);
    
    @Select("SELECT message_count from vk_messenger_v2.sendors where vk_uid=#{vk_uid} and company_id=#{company_id}")
    Integer getCountMessagesByCompanyId(@Param("vk_uid")String vkUid,@Param("company_id")Integer companyId);
}
