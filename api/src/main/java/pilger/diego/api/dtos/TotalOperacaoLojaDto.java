package pilger.diego.api.dtos;


import pilger.diego.api.models.TipoTransacao;

import java.math.BigDecimal;

public class TotalOperacaoLojaDto {
    private String tipo;
    private BigDecimal total;

    public static TotalOperacaoLojaDto getObject(Object[] row) {
        TotalOperacaoLojaDto totalOperacaoLojaDto = new TotalOperacaoLojaDto();
        totalOperacaoLojaDto.setTipo(TipoTransacao.fromCodigo(row[0] + "").name());
        totalOperacaoLojaDto.setTotal(new BigDecimal(row[1] + ""));
        return totalOperacaoLojaDto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
