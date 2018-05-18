package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

}
