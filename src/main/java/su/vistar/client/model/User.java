package su.vistar.client.model;

import java.util.Date;


public class User {
    int id;
    Long uid;
    String login;
    String password;
    String status;
    String access_token;
    String fio;    
    Date last_date;
    Long expires_in;

    public Long getExpires_in() {
        return expires_in;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }   
    public Long getUid() {
        return uid;
    }
    public void setUid(Long uid) {
        this.uid = uid;
    }    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }    
    public Date getLast_date() {
        return last_date;
    }   
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }   
    public void setLast_date(Date last_date) {
        this.last_date = last_date;
    }
    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }   
}
