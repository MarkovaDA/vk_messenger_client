package su.vistar.client.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="представлена неактуальная информация по запросу")
public class IrrelevantInformationException extends RuntimeException{
    
}
