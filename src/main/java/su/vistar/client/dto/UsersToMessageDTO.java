
package su.vistar.client.dto;

import java.util.List;


public class UsersToMessageDTO {
    private String message;
    private List<VKUserDTO> users;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<VKUserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<VKUserDTO> users) {
        this.users = users;
    }

    public UsersToMessageDTO(String message, List<VKUserDTO> users) {
        this.message = message;
        this.users = users;
    }
    
    
}
