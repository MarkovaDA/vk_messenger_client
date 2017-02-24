package su.vistar.client.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AdresatCriteria {
    private Integer university;
    private Integer university_faculty;
    private Integer university_year;
    private Integer age_from;
    private Integer age_to;
    private String position; //должность
    private String message; //сообщение

    public Integer getUniversity() {
        return university;
    }

    public void setUniversity(Integer university) {
        this.university = university;
    }

    public Integer getUniversity_faculty() {
        return university_faculty;
    }

    public void setUniversity_faculty(Integer university_faculty) {
        this.university_faculty = university_faculty;
    }

    public Integer getUniversity_year() {
        return university_year;
    }

    public void setUniversity_year(Integer university_year) {
        this.university_year = university_year;
    }

    public Integer getAge_from() {
        return age_from;
    }

    public void setAge_from(Integer age_from) {
        this.age_from = age_from;
    }

    public Integer getAge_to() {
        return age_to;
    }

    public void setAge_to(Integer age_to) {
        this.age_to = age_to;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        Map<String,String> params = new HashMap<>();
        if (university != null)
            params.put("university", Integer.toString(university));
        if (university_faculty != null)
            params.put("university_faculty", Integer.toString(university_faculty));
        if (university_year != null)
            params.put("university_year", Integer.toString(university_year));
        if (age_from != null)
            params.put("age_from", Integer.toString(age_from));
        if (age_to != null)
            params.put("age_to", Integer.toString(age_to));
        if (position != null)
            params.put("position", position);
        String criteria = "";
        Iterator iterator = params.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            criteria+=pair.getKey() + "=" + pair.getValue();
            
            if (iterator.hasNext())
                criteria+="&";
            
        }
        return criteria;
        //return  "university=" + university + "&university_faculty=" + university_faculty + "&university_year=" + university_year + "&age_from=" + age_from + "&age_to=" + age_to + "&position=" + position;
    }
    
    
}
