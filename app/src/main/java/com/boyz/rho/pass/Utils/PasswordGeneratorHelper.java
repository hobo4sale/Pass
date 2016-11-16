package com.boyz.rho.pass.Utils;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rho on 11/15/16.
 */

public class PasswordGeneratorHelper {

    private static final char[] UPPER_CHARACTERS = { 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private static final char[] LOWER_CHARACTERS = { 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
    private static final char[] NUMERIC_CHARACTERS = { '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9' };
    private static final char[] SPECIAL_CHARACTERS = { '~', '`', '!', '@', '#',
            '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '[', '{',
            ']', '}', '\\', '|', ';', ':', '\'', '"', ',', '<', '.', '>', '/',
            '?' };

    public PasswordGeneratorHelper() {

    }

    public char[] getPassword(boolean upper, boolean lower, boolean numeric, boolean special) {
        ArrayList<PasswordGenerator.PasswordCharacterSet> values =
                new ArrayList<>();
        if (upper) {
            values.add(new CustomSet(UPPER_CHARACTERS, 1));
        }

        if (lower) {
            values.add(new CustomSet(LOWER_CHARACTERS, 1));
        }

        if (numeric) {
            values.add(new CustomSet(NUMERIC_CHARACTERS, 1));
        }

        if (special) {
            values.add(new CustomSet(SPECIAL_CHARACTERS, 1));
        }

        PasswordGenerator generator = new PasswordGenerator(values, 8, 16);
        return generator.generatePassword();
    }

    private class CustomSet implements PasswordGenerator.PasswordCharacterSet {

        private final char[] chars;
        private final int min;

        private CustomSet(char[] chars, int min) {
            this.chars = chars;
            this.min = min;
        }

        @Override
        public char[] getCharacters() {
            return chars;
        }

        @Override
        public int getMinCharacters() {
            return min;
        }
    }


}
