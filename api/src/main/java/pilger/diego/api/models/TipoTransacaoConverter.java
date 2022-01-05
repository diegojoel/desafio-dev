package pilger.diego.api.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoTransacaoConverter implements AttributeConverter<TipoTransacao, String> {
    @Override
    public String convertToDatabaseColumn(TipoTransacao input) {
        if (input == null) {
            return null;
        }
        return input.getCodigo();
    }

    @Override
    public TipoTransacao convertToEntityAttribute(String codigo) {
        if (codigo == null) {
            return TipoTransacao.DESCONHECIDO;
        }

        return TipoTransacao.fromCodigo(codigo);
    }
}
