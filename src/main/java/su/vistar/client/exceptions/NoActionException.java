
package su.vistar.client.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.ACCEPTED, reason="операция не была выполнена")
public class NoActionException extends RuntimeException{
    
}
