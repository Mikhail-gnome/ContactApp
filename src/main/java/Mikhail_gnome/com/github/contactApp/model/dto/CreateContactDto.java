package Mikhail_gnome.com.github.contactApp.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateContactDto {
        @NotEmpty(message = "Имя не может быть пустым")
        private String firstName;
        @NotEmpty(message = "Имя не может быть пустым")
        private String lastName;
        private String telephone;
        @Email(message = "Некорректный email")
        private String email;
}
