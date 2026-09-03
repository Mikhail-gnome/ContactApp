package Mikhail_gnome.com.github.contactApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateContactDto {
        private String firstName;
        private String lastName;
        private String telephone;
        private String email;
}
