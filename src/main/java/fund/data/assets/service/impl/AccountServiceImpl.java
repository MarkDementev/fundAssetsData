package fund.data.assets.service.impl;

import fund.data.assets.dto.AccountDTO;
import fund.data.assets.model.financial_entities.Account;
import fund.data.assets.repository.AccountRepository;
import fund.data.assets.service.AccountService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация сервиса для обслуживания банковских счетов.
 * Обслуживаемая сущность - {@link fund.data.assets.model.financial_entities.Account}.
 * @version 0.0.1-alpha
 * @author MarkDementev a.k.a JavaMarkDem
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account createAccount(AccountDTO accountDTO) {
        Account newAccount = new Account();

        getFromDTOThenSetAll(newAccount, accountDTO);

        return accountRepository.save(newAccount);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = {Exception.class})
    public Account updateAccount(Long id, AccountDTO accountDTO) {
        final Account accountToUpdate = accountRepository.findById(id).orElseThrow();

        getFromDTOThenSetAll(accountToUpdate, accountDTO);

        return accountRepository.save(accountToUpdate);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    private void getFromDTOThenSetAll(Account accountToWorkWith, AccountDTO accountDTO) {
        accountToWorkWith.setOrganisationWhereAccountOpened(accountDTO.getOrganisationWhereAccountOpened());
        accountToWorkWith.setAccountNumber(accountDTO.getAccountNumber());
        accountToWorkWith.setAccountOpeningDate(accountDTO.getAccountOpeningDate());
    }
}
