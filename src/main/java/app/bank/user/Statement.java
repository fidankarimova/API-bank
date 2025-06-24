package app.bank.user;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;

@Entity
@Table(name = "statements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    private String type;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

}
