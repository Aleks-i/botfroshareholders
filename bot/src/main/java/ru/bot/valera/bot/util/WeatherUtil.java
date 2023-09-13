package ru.bot.valera.bot.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class WeatherUtil {

    public static final Map<String, String> CITIES_NAMES = new HashMap<>();

    public static final String MSC_LAT = "55.7504461";
    public static final String MSC_LON = "37.6174943";

    public static final String PITERBURG_LAT = "59.938732";
    public static final String PITERBURG_LON = "30.316229";

    public static final String NOVOSIB_LAT = "55.0282171";
    public static final String NOVOSIB_LON = "82.9234509";

    public static final String EKB_LAT = "56.839104";
    public static final String EKB_LON = "60.60825";

    public static final String KAZAN_LAT = "55.7823547";
    public static final String KAZAN_LON = "49.1242266";

    public static final String NIZHNY_NOVGOROD_LAT = "56.3264816";
    public static final String NIZHNY_NOVGOROD_LON = "44.0051395";

    public static final String KRASNOYARSK_LAT = "56.0090968";
    public static final String KRASNOYARSK_LON = "92.8725147";

    public static final String CHELYABINSK_LAT = "55.1598408";
    public static final String CHELYABINSK_LON = "61.4025547";

    public static final String SAMARA_LAT = "53.198627";
    public static final String SAMARA_LON = "50.113987";

    public static final String UFA_LAT = "54.7261409";
    public static final String UFA_LON = "55.947499";

    public static final String ROSTOV_ON_DON_LAT = "47.2213858";
    public static final String ROSTOV_ON_DON_LON = "39.7114196";

    public static final String KRASNODAR_LAT = "45.0352718";
    public static final String KRASNODAR_LON = "38.9764814";

    public static final String OMSK_LAT = "54.991375";
    public static final String OMSK_LON = "73.371529";

    public static final String VORONEZH_LAT = "51.6605982";
    public static final String VORONEZH_LON = "39.2005858";

    public static final String PERMIAN_LAT = "58.014965";
    public static final String PERMIAN_LON = "56.246723";

    public static final String VOLGOGRAD_LAT = "48.7081906";
    public static final String VOLGOGRAD_LON = "44.5153353";

    public static final String SARATOV_LAT = "51.530018";
    public static final String SARATOV_LON = "46.034683";

    public static final String TYUMEN_LAT = "57.153534";
    public static final String TYUMEN_LON = "65.542274";

    public static final String SURG_LAT = "61.254032";
    public static final String SURG_LON = "73.3964";


    public static final String SOCHI_LAT = "43.5854823";
    public static final String SOCHI_LON = "39.723109";

    public static final List<String> coordsList = List.of(MSC_LAT + " " + MSC_LON,
            PITERBURG_LAT + " " + PITERBURG_LON,
            NOVOSIB_LAT + " " + NOVOSIB_LON,
            EKB_LAT + " " + EKB_LON,
            KAZAN_LAT + " " + KAZAN_LON,
            NIZHNY_NOVGOROD_LAT + " " + NIZHNY_NOVGOROD_LON,
            KRASNOYARSK_LAT + " " + KRASNOYARSK_LON,
            CHELYABINSK_LAT + " " + CHELYABINSK_LON,
            SAMARA_LAT + " " + SAMARA_LON,
            UFA_LAT + " " + UFA_LON,
            ROSTOV_ON_DON_LAT + " " + ROSTOV_ON_DON_LON,
            KRASNODAR_LAT + " " + KRASNODAR_LON,
            OMSK_LAT + " " + OMSK_LON,
            VORONEZH_LAT + " " + VORONEZH_LON,
            PERMIAN_LAT + " " + PERMIAN_LON,
            VOLGOGRAD_LAT + " " + VOLGOGRAD_LON,
            SARATOV_LAT + " " + SARATOV_LON,
            TYUMEN_LAT + " " + TYUMEN_LON,
            SURG_LAT + " " + SURG_LON,
            SOCHI_LAT + " " + SOCHI_LON);
}
