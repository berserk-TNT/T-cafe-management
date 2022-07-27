package hmt.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String USERNAME_REGEX = "^[\\w\\d\\.\\-\\_]{6,30}$";
    public static final String PASSWORD_REGEX = "^[\\w\\d]{6,30}$";
    public static final String LETTER_WITHOUT_NUMBER_REGEX = "^([A-Z]+[a-z]*[ ]?)+$";
    public static final String PHONE_REGEX = "^((0?)||84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";
    public static final String EMAIL_REGEX = "^(([\\w\\d._]*)+(@[\\w]{3,})+(.[\\w]{2,3})||([\\w\\d._]*)+(@[\\w]{3,})+(.[\\w]{2,3})+(.[\\w]{2}))$";
    public static final String PATTERN_FORMAT = "dd-MM-yyyy";
//    public static final String DATE_REGEX = "^[0-9]{4}-([0-9]|0[0-9]|1[0-2])-([0-9]|[0-2][0-9]|3[0-1])$";
    public static final String NUMBER_REGEX = "\\d+";
//    public static final String NAME_REGEX = "^[\\w ]*$";

    public static String displayVND(BigDecimal price) {
        String patternVND = ",###₫";
        DecimalFormat decimalFormat = new DecimalFormat(patternVND);
        return decimalFormat.format(price);
    }

    public static String instantToString(Instant instant) {
        return instantToString(instant, null);
    }
    public static String instantToString(Instant instant, String patternFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternFormat != null ? patternFormat : PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public static boolean isUsernameRegex(String username) {
        return Pattern.compile(USERNAME_REGEX).matcher(username).matches();
    }

    public static boolean isPasswordRegex(String password) {
        return Pattern.compile(PASSWORD_REGEX).matcher(password).matches();
    }

    public static boolean isNameRegex(String name) {
        return Pattern.compile(LETTER_WITHOUT_NUMBER_REGEX).matcher(name).matches();
    }

    public static boolean isPhoneRegex(String phone) {
        return Pattern.compile(PHONE_REGEX).matcher(phone).matches();
    }

    public static boolean isEmailRegex(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    public static boolean isNumber(String attribute) {
        return Pattern.compile(NUMBER_REGEX).matcher(attribute).matches();
    }
}
