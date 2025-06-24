package app.bank.repository;

import app.bank.user.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Integer> {
}
