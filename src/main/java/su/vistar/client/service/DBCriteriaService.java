
package su.vistar.client.service;

import java.nio.charset.Charset;
import static java.nio.charset.Charset.defaultCharset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.vistar.client.mapper.DBMapper;
import su.vistar.client.model.AdresatCriteria;
import su.vistar.client.model.User;

@Service
public class DBCriteriaService {
    @Autowired
    DBMapper dbMapper;
    
    @Autowired
    AuthService authService;
    
    private void saveCriteria(String criteria, int offset, int userId){
        Charset charset = defaultCharset();
        dbMapper.saveCriteria(criteria,0, userId);
    }
    
    private void saveMessageForCriteria(String message){
        dbMapper.saveMessageForCriteria(dbMapper.lastInsertedCriteriaId(), message);
    }
    
    public void saveCriteria(AdresatCriteria criteria){
        User currentUser = authService.getCurrentUser(); 
        saveCriteria(criteria.toString(),0, currentUser.getId());
        saveMessageForCriteria(criteria.getMessage());
    }
}
