package botforshareholders.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyUtil {

    public static String formatterBigDecimal(String string) {
        DecimalFormat df = new DecimalFormat(
                "#,##0.00",
                new DecimalFormatSymbols(new Locale("pt", "BR")));
        BigDecimal value = new BigDecimal(string);
        return df.format(value.floatValue());
    }
}
