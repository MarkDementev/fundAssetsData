package fund.data.assets.model.financial_entities;

import fund.data.assets.model.owner.AssetsOwner;
import fund.data.assets.utils.enums.AssetCurrency;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * Размер денежных средств определённого собственника на счёте в определённой валюте.
 * @version 0.0.1-alpha
 * @author MarkDementev a.k.a JavaMarkDem
 */
@Entity
@Table(name = "free_owners_cash_on_accounts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"account_id", "asset_currency", "assets_owner_id"})})
@NoArgsConstructor
@Getter
@Setter
public class Cash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AssetCurrency assetCurrency;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assets_owner_id", nullable = false)
    private AssetsOwner assetsOwner;

    @NotNull
    @PositiveOrZero
    private Float amount;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Cash(Account account, AssetCurrency assetCurrency, AssetsOwner assetsOwner, Float amount) {
        this.account = account;
        this.assetCurrency = assetCurrency;
        this.assetsOwner = assetsOwner;
        this.amount = amount;
    }
}
