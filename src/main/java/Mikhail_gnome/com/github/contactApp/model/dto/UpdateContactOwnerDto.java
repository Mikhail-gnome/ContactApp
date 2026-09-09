package Mikhail_gnome.com.github.contactApp.model.dto;

import Mikhail_gnome.com.github.contactApp.model.enums.AppRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateContactOwnerDto {

    @NotBlank(message = "ID обязателен")
    private String id;

    @Size(min = 6, max = 20, message = "Имя пользователя должно быть от 6 до 20 символов")
    private String username;

    @NotNull(message = "Значение null недопустимо")
    private String description;

    @NotBlank(message = "Поле не может быть пустым")
    @Email(message = "Некорректный Email")
    private String email;

    @Size(min = 2, max = 50, message = "Это поле должно содержать от 2 до  50 символов")
    private String fullName;

}
