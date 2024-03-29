package fund.data.assets.model.asset.user;

import fund.data.assets.model.asset.relationship.AssetRelationship;
import fund.data.assets.utils.converter.StringCryptoConverter;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность - абстрактная заготовка собственника активов без специфичных для каждой страны
 * паспортных данных для его идентификации. Имя и фамилия указаны у всех, но не отчество и т.п. элементы.
 * @since 0.0.1-alpha
 * @author MarkDementev a.k.a JavaMarkDem
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@Getter
@Setter
public abstract class AssetsOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((19|20)\\d\\d)")
    private LocalDate birthDate;

    /**
     * Для написания regexp использован стандарт RFC5322 - https://www.rfc-editor.org/info/rfc5322
     * Почты может и не быть, потому не ставлю ограничение в виде @NotBlank.
     */
    //TODO Проверь - возможно, регексп надо оставить только в ДТО, т.к. здесь идёт шифрование
    @Column(unique = true)
    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Convert(converter = StringCryptoConverter.class)
    private String email;

    /**
     * Обращаясь к AssetsOwner, с вероятностью в 95% нам важны не его иные параметры, а активы на балансе. Информацию
     * о них можно получить через обращение к сущности AssetRelationship. Потому fetch - это сразу FetchType.EAGER,
     * а не FetchType.LAZY.
     */
    @NotNull
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "assetsOwner")
    private List<AssetRelationship> assetRelationships;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    /**
     * У кого-то может не быть электронной почты, но это не препятствие для взаимодействия с фондом. Всё равно
     * можно создать свой аккаунт в системе фонда. Email приходит через DTO, где он может быть null.
     */
    public AssetsOwner(String name, String surname, LocalDate birthDate, String email) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.email = email;
        this.assetRelationships = new ArrayList<>();
    }
}
