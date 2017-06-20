
package su.vistar.client.mapper;

import org.springframework.transaction.annotation.Transactional;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import su.vistar.client.model.User;

@Transactional("mainManager")
public interface AuthUserMapper { 
    public User getUserByUid(Long uid);
    public void addNewUser(User user);
    //обновить status
}
