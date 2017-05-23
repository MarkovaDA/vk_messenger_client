package su.vistar.client.model;



public class AdresatCriteria {
    private String criteriaString;//сам критерий
    private String  message; //сообщение
    private Integer message_id; //идентификатор старого сообщения

    @Override
    public String toString() {
      return criteriaString;
    }
    public String getCriteriaString() {
        return criteriaString;
    }     
    public Integer getMessage_id() {
        return message_id;
    }
    public String getMessage() {
        return message;
    }
       
}
