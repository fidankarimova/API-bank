package app.bank.repository;

import app.bank.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Integer> {
    List<Statement> findBySenderOrReceiver(String sender, String receiver);
}
