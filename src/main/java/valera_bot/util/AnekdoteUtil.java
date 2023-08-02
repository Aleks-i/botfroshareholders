package valera_bot.util;

public class AnekdoteUtil {
    private static final int QUANTITY_ANEKDOTES_DB = 130263;

    private static final Integer DIFFERENT_CAT_1 = 48494;
    private static final Integer APHORISMS_CAT_2 = 37195;
    private static final Integer QUOTES_CAT_3 = 14426;
    private static final Integer FAMILY_CAT_4 = 6461;
    private static final Integer ARMY_CAT_5 = 2485;
    private static final Integer INTIMATE_CAT_6 = 2412;
    private static final Integer STUDENTS_CAT_7 = 2248;
    private static final Integer MEDICAL_CAT_8 = 2160;
    private static final Integer ABOUT_MEN_CAT_9 = 2084;
    private static final Integer FOLK_CAT_10 = 1823;
/*            11 - Наркоманы - 1191
            12 - Новые Русские - 1173
            13 - Вовочка - 1001
            14 - Компьютеры - 738
            15 - Спорт - 674
            16 - Советские - 650
            17 - Иностранцы - 642
            18 - Дорожные - 599
            19 - Животные - 585
            20 - Черный юмор - 578
            21 - Сказочные - 529
            22 - Про евреев - 487
            23 - Криминал - 419
            24 - Поручик Ржевский - 288
            25 - Про женщин - 284
            26 - Штирлиц - 223
            27 - WOW - 214
            28 - Киногерои - 174
            29 - Алкоголики - 155
            30 - Чукча - 100
            31 - Реклама - 98
            32 - Бородатые - 93
            33 - Про детей - 57
            34 - Про программистов - 52
            35 - Про Путина - 52
            36 - Милиция - 39
            37 - Судебные - 34
            38 - Про сисадмина - 30
            39 - Политика - 30
            40 - Друзья - 28
            41 - Про Билла Гейтса - 22
            42 - Про тещу - 21
            43 - Про деньги - 21
            44 - Шоу-бизнес - 15
            45 - Школьные - 11*/

    public static int getRandomAnekdoteId() {
        return ((int) ((Math.random() * QUANTITY_ANEKDOTES_DB)));
    }
}
