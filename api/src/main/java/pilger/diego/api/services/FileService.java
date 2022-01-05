package pilger.diego.api.services;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pilger.diego.api.exceptions.DataHoraInvalidaException;
import pilger.diego.api.exceptions.FormatoInvalidoException;
import pilger.diego.api.exceptions.ValorInvalidoException;
import pilger.diego.api.models.Loja;
import pilger.diego.api.models.TipoTransacao;
import pilger.diego.api.models.Transacao;
import pilger.diego.api.utils.FileParseResultEnum;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.substring;
import static pilger.diego.api.utils.FilePropertiesUtils.*;

@Service
public class FileService {

    private static Logger logger = LoggerFactory.getLogger(FileService.class);

    @Value("${pilger.diego.api.bach-size}")
    private Integer batchSize;

    @Autowired
    private AtomicPersistFileService atomicPersistFileService;

    public FileParseResultEnum parseAndSave(final byte[] fileContentBytes) {
        try {
            final LineIterator lineIterator = IOUtils.lineIterator(new ByteArrayInputStream(fileContentBytes),
                    StandardCharsets.UTF_8);

            List<Transacao> transacoes = new ArrayList<>(batchSize);
            while (lineIterator.hasNext()) {
                final String line = lineIterator.next();
                if (line.trim().length() == 0) continue;

                transacoes.add(handleLine(line));

                if (transacoes.size() == batchSize) {
                    atomicPersistFileService.persist(transacoes);
                    transacoes = new ArrayList<>(batchSize);
                }
            }

            if (!transacoes.isEmpty()) {
                atomicPersistFileService.persist(transacoes);
            }
        } catch (FormatoInvalidoException e) {
            return FileParseResultEnum.FORMATO_INVALIDO;
        } catch (DataHoraInvalidaException e) {
            return FileParseResultEnum.DATA_HORA_INVALIDA;
        } catch (ValorInvalidoException e) {
            return FileParseResultEnum.VALOR_INVALIDO;
        } catch (Exception e) {
            logger.error("Erro ao tentar parsear arquivo", e);
            return FileParseResultEnum.ERRO_GENERICO;
        }
        return FileParseResultEnum.OK;
    }

    private Transacao handleLine(final String line) throws DataHoraInvalidaException, ValorInvalidoException, Exception {
        if (line.length() != LINE_SIZE) throw new FormatoInvalidoException();

        final Transacao transacao = new Transacao();
        transacao.setTipo(TipoTransacao.fromCodigo(substring(line, 0, 1)));
        transacao.setDataHora(getDataHora(line));
        transacao.setValor(getValor(line, transacao.getTipo().getNatureza()));
        transacao.setCpfBeneficiario(substring(line, 19, 30));
        transacao.setCartao(substring(line, 30, 42));
        transacao.setLoja(getLoja(line));

        return transacao;
    }

    private OffsetDateTime getDataHora(final String line) throws DataHoraInvalidaException {
        try {
            final TemporalAccessor dateParsed = DTF_DATE.parse(substring(line, 1, 9));
            final TemporalAccessor timeParsed = DTF_TIME.parse(substring(line, 42, 48));
            return OffsetDateTime.of(LocalDate.from(dateParsed), LocalTime.from(timeParsed), UTC_MINUS_3);
        } catch (Exception e) {
            throw new DataHoraInvalidaException();
        }
    }

    private BigDecimal getValor(String line, String natureza) throws ValorInvalidoException {
        try {
            BigDecimal valor = new BigDecimal(substring(line, 9, 19));
            if ("S".equalsIgnoreCase(natureza)) {
                valor = valor.negate();
            }
            return valor.divide(HUNDRED, 2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new ValorInvalidoException();
        }
    }

    private Loja getLoja(String line) {
        final String nome = substring(line, 62, 81);
        final String representante = substring(line, 48, 62);
        final Loja loja = new Loja();
        loja.setNome(nome.trim().toUpperCase());
        loja.setRepresentante(representante.trim().toUpperCase());
        return loja;
    }

}
