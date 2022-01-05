package pilger.diego.api.utils;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FilePropertiesUtils {
    public static final int LINE_SIZE = 80;

    public static final DateTimeFormatter DTF_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter DTF_TIME = DateTimeFormatter.ofPattern("HHmmss");
    public static final ZoneOffset UTC_MINUS_3 = ZoneOffset.ofHours(-3);
    public static final BigDecimal HUNDRED = new BigDecimal("100");
}
