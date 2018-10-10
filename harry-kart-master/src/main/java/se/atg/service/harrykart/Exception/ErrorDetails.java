package se.atg.service.harrykart.Exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String error;
    private HttpStatus status;
    
    public ErrorDetails(Date timestamp, 
            String error,HttpStatus status) {
    	super();
        this.error = error;
        this.status= status;
        this.timestamp= timestamp;
    }
    
    
    
    public Date getTimestamp() {
        return timestamp;
      }

      public String getMessage() {
        return message;
      }

      public String getErrors() {
        return error;
      }
      
      public HttpStatus getStatus() {
    	  return status;
      }

}
