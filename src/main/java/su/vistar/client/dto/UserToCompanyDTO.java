package su.vistar.client.dto;


public class UserToCompanyDTO {
    private Integer id;
    private String title;
    private Long code;
    private String fio;
    private Integer message_count;

    public Integer getMessage_count() {
        return message_count;
    }

    public void setMessage_count(Integer message_count) {
        this.message_count = message_count;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
