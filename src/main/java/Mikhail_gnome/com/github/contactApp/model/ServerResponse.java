package Mikhail_gnome.com.github.contactApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ServerResponse <T> {

    private boolean isSuccess;
    private HttpStatus statusCode;
    @JsonProperty("errors")
    private ArrayList<String> errorMessages;
    private T result;

    public ServerResponse() {
        this.isSuccess = true;
        this.errorMessages = new ArrayList<>();
    }
    @JsonProperty("isSuccess")
    public boolean isSuccess() { // добавлен гет-метод для поля isSuccess так как @Getter + @JsonProperty("isSuccess") вызывали конфликт и поля дублировались
        return isSuccess;
    }
}
