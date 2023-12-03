package Service;

public class ValidationService {
    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean validatePassword(String password) {
        int minLength = 8;
        boolean hasLetter = false;
        boolean hasDigit = false;

        if (password.length() < minLength) {
            return false;
        }

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }

            if(hasLetter && hasDigit) {
                return true;
            }
        }

        return false;
    }

    public static boolean validateFullName(String fullName) {
        return fullName != null && !fullName.trim().isEmpty() && fullName.matches("[a-zA-Z\\s]+");
    }

    public static boolean validateRegistration(String email, String password, String fullName) {
        return validateEmail(email) && validatePassword(password) && validateFullName(fullName);
    }
}

