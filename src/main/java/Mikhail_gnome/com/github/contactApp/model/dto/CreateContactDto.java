package Mikhail_gnome.com.github.contactApp.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateContactDto {
        @NotEmpty(message = "Имя не может быть пустым")
        private String firstName;
        @NotEmpty(message = "Фамилия не может быть пустой")
        private String lastName;
        @Pattern(regexp = "\\+7\\(\\d{3}\\)\\d{3} \\d{2} \\d{2}", message = "Неверный формат телефона")
        private String telephone;
        @Email(message = "Некорректный email")
        private String email;
}
