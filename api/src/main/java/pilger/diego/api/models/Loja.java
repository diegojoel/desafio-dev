package pilger.diego.api.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(indexes = {@Index(columnList = "nome")})
public class Loja implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 19)
    private String nome;

    @Column(nullable = false, length = 14)
    private String representante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }
}
