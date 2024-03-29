package fund.data.assets.dto;

import fund.data.assets.utils.enums.RussianSexEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для обслуживания создания владельца активов с гражданством РФ.
 * Обслуживаемая сущность - {@link fund.data.assets.model.asset.user.RussianAssetsOwner}.
 * Сервис сущности - {@link fund.data.assets.service.impl.RussianAssetsOwnerServiceImpl}.
 * @version 0.0.1-alpha
 * @author MarkDementev a.k.a JavaMarkDem
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewRussianAssetsOwnerDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((19|20)\\d\\d)")
    private String birthDate;

    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank
    private String patronymic;

    @NotNull
    private RussianSexEnum sex;

    @NotNull
    @Pattern(regexp = "9[0-9]{9}")
    private String mobilePhoneNumber;

    @NotNull
    @Pattern(regexp = "[0-9]{2}\\s?[0-9]{2}")
    private String passportSeries;

    @NotNull
    @Pattern(regexp = "[0-9]{6}")
    private String passportNumber;

    @NotBlank
    private String placeOfBirth;

    @NotBlank
    private String placeOfPassportGiven;

    @NotBlank
    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((19|20)\\d\\d)")
    private String issueDate;

    @NotNull
    @Pattern(regexp = "[0-9]{3}-[0-9]{3}")
    private String issuerOrganisationCode;
}
