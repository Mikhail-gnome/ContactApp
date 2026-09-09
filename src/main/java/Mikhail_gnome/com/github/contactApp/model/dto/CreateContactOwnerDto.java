package Mikhail_gnome.com.github.contactApp.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactOwnerDto {
    @NotBlank(message = "Имя пользователя обязательно")
    @Size(min = 6, max = 20, message = "Имя пользователя должно быть от 6 до 20 символов")
    private String username;


    @NotNull(message = "Значение null недопустимо")
    private String description;

    @NotBlank(message = "Поле не может быть пустым")
    @Email(message = "Некорректный Email")
    private String email;

    @Size(min = 2, max = 50, message = "Это поле должно содержать от 2 до  50 символов")
    private String fullName;

    @NotBlank(message = "Пароль не может быть пустым")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z0-9]{8,}$",
            message = "пароль должен содержать минимум 8 символов, включая заглавные, строчные буквы и цифры")
    private String password;
}
