package pilger.diego.api.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum TipoTransacao {
    DEBITO("1", "E"),
    BOLETO("2", "S"),
    FINANCIAMENTO("3", "S"),
    CREDITO("4", "E"),
    RECEBIMENTO_EMPRESTIMO("5", "E"),
    VENDAS("6", "E"),
    RECEBIMENTO_TED("7", "E"),
    RECEBIMENTO_DOC("8", "E"),
    ALUGUEL("9", "S"),
    DESCONHECIDO("0", "E");

    private String codigo;
    private String natureza;

    private static Map<String, TipoTransacao> reverseMap = new HashMap<>();

    static {
        Arrays.stream(TipoTransacao.values()).forEach(i -> reverseMap.put(i.getCodigo(), i));
    }

    TipoTransacao(String codigo, String natureza) {
        this.codigo = codigo;
        this.natureza = natureza;
    }

    public static TipoTransacao fromCodigo(String codigo) {
        TipoTransacao v = reverseMap.get(codigo);
        return v == null ? DESCONHECIDO : v;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNatureza() {
        return natureza;
    }
}
