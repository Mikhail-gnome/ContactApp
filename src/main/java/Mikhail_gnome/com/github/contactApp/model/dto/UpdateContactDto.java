package Mikhail_gnome.com.github.contactApp.model.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContactDto {

        @NotNull(message = "ID обязателен")
        private Integer id;

        @NotBlank(message = "Имя не может быть пустым")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2-х до 50-ти символов")
        @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]{2,}$", message = "Имя должно содержать только буквы")
        private String firstName;

        @NotBlank(message = "Фамилия не может быть пустой")
        @Size(min = 2, max = 50, message = "Фамилия должна быть от 2-х до 50-ти символов")
        @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]{2,}$", message = "Фамилия должна содержать только буквы")
        private String lastName;

        @NotBlank(message = "Телефон обязателен")
        @Pattern(regexp = "\\+7\\(\\d{3}\\)\\d{3} \\d{2} \\d{2}", message = "Неверный формат телефона")
        private String telephone;

        @NotBlank(message = "email обязателен")
        @Email(message = "Некорректный email")
        private String email;
    }
