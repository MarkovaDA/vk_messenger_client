
package su.vistar.client.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="информация отсуствует")  // 404
public class DataNotFoundException extends RuntimeException {
    
}
