package pilger.diego.api.dtos;

import java.math.BigDecimal;

public class LojaSaldoDto {

    private Long id;
    private String loja;
    private BigDecimal saldo;

    public static LojaSaldoDto getObject(Object[] row) {
        LojaSaldoDto lojaSaldoDto = new LojaSaldoDto();
        lojaSaldoDto.setId(Long.parseLong(row[0] + ""));
        lojaSaldoDto.setLoja(row[1] + "");
        try {
            lojaSaldoDto.setSaldo(new BigDecimal(row[2] + ""));
        } catch (Exception e) {
            lojaSaldoDto.setSaldo(BigDecimal.ZERO);
        }
        return lojaSaldoDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
