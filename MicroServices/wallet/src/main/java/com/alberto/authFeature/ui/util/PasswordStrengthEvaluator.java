package com.alberto.authFeature.ui.util;

public final class PasswordStrengthEvaluator {

    private PasswordStrengthEvaluator() {}

    private static final String UPPERCASE_PATTERN = ".*[A-Z].*";
    private static final String DIGIT_PATTERN     = ".*[0-9].*";
    private static final String SPECIAL_PATTERN   = ".*[^A-Za-z0-9].*";

    public static Strength evaluate(String password) {

        if (password == null || password.isBlank()) {
            return Strength.WEAK;
        }

        int score = 0;

        if (password.length() >= 8)  score++;
        if (password.length() >= 12) score++;
        if (password.matches(UPPERCASE_PATTERN)) score++;
        if (password.matches(DIGIT_PATTERN))     score++;
        if (password.matches(SPECIAL_PATTERN))   score++;

        if (score >= 4) return Strength.STRONG;
        if (score >= 2) return Strength.MODERATE;
        return Strength.WEAK;
    }

    public enum Strength {

        WEAK    ("Weak — add uppercase letters, numbers, or symbols"),
        MODERATE("Moderate — try adding more variety"),
        STRONG  ("Strong ✓");

        private final String label;

        Strength(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }
    }
}