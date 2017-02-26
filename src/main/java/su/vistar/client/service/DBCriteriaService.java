
package su.vistar.client.service;

import java.nio.charset.Charset;
import static java.nio.charset.Charset.defaultCharset;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.vistar.client.mapper.DBMapper;
import su.vistar.client.model.AdresatCriteria;
import su.vistar.client.model.Message;
import su.vistar.client.model.User;
import static java.nio.charset.Charset.defaultCharset;

@Service
public class DBCriteriaService {
    @Autowired
    DBMapper dbMapper;
    
    @Autowired
    AuthService authService;
    
    private void saveCriteria(String criteria, int offset, int userId){
        dbMapper.saveCriteria(criteria,0, userId);
    }
    
    private void saveNewMessageForCriteria(String message){
        dbMapper.saveMessageForCriteria(dbMapper.lastInsertedCriteriaId(), message);
    }
    
    private void saveOldMessageForCriteria(){
    }
    
    public void saveCriteria(AdresatCriteria criteria){
        User currentUser = authService.getCurrentUser(); 
        saveCriteria(criteria.toString(),0, currentUser.getId());
        saveNewMessageForCriteria(criteria.getMessage());
    }
    
    public List<Message> getMessages(){
        return dbMapper.getAllMessage();
    }
    
    public Message getMessageById(Integer mesId){
        return dbMapper.getMessageById(mesId);
    }
    
}
