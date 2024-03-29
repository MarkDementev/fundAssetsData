package fund.data.assets.service.impl;

import fund.data.assets.dto.common.PercentFloatValueDTO;
import fund.data.assets.dto.TurnoverCommissionValueDTO;
import fund.data.assets.model.financial_entities.Account;
import fund.data.assets.model.financial_entities.TurnoverCommissionValue;
import fund.data.assets.repository.AccountRepository;
import fund.data.assets.repository.TurnoverCommissionValueRepository;
import fund.data.assets.service.TurnoverCommissionValueService;
import fund.data.assets.utils.enums.CommissionSystem;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация сервиса для обслуживания размера комиссии с оборота для типа актива на счёте.
 * Обслуживаемая сущность - {@link fund.data.assets.model.financial_entities.TurnoverCommissionValue}.
 * @version 0.0.1-alpha
 * @author MarkDementev a.k.a JavaMarkDem
 */
@Service
@RequiredArgsConstructor
public class TurnoverCommissionValueServiceImpl implements TurnoverCommissionValueService {
    private final AccountRepository accountRepository;
    private final TurnoverCommissionValueRepository turnoverCommissionValueRepository;

    @Override
    public TurnoverCommissionValue getTurnoverCommissionValue(Long id) {
        return turnoverCommissionValueRepository.findById(id).orElseThrow();
    }

    @Override
    public List<TurnoverCommissionValue> getTurnoverCommissionValues() {
        return turnoverCommissionValueRepository.findAll();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public TurnoverCommissionValue createTurnoverCommissionValue(
            TurnoverCommissionValueDTO turnoverCommissionValueDTO) {
        TurnoverCommissionValue newTurnoverCommissionValue = new TurnoverCommissionValue();
        Account account = accountRepository.findById(turnoverCommissionValueDTO.getAccountID()).orElseThrow();

        newTurnoverCommissionValue.setAccount(account);
        newTurnoverCommissionValue.setCommissionSystem(CommissionSystem.TURNOVER);
        newTurnoverCommissionValue.setAssetTypeName(turnoverCommissionValueDTO.getAssetTypeName());
        newTurnoverCommissionValue.setCommissionPercentValue(turnoverCommissionValueDTO.getCommissionPercentValue());

        return turnoverCommissionValueRepository.save(newTurnoverCommissionValue);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = {Exception.class})
    public TurnoverCommissionValue updateTurnoverCommissionValue(Long id, PercentFloatValueDTO percentFloatValueDTO) {
        TurnoverCommissionValue turnoverCommissionValueToUpdate = turnoverCommissionValueRepository.findById(id)
                .orElseThrow();

        turnoverCommissionValueToUpdate.setCommissionPercentValue(percentFloatValueDTO.getPercentValue());

        return turnoverCommissionValueRepository.save(turnoverCommissionValueToUpdate);
    }

    @Override
    public void deleteTurnoverCommissionValue(Long id) {
        turnoverCommissionValueRepository.deleteById(id);
    }
}
