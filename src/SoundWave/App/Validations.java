package SoundWave.App;

import javax.swing.*;

public class Validations {
    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isEmailValid(JTextField emailTxt) {
        String email = emailTxt.getText();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean PasswordsMatch(JTextField passwordTxt,JTextField confirmPasswordTxt) {
        String password = passwordTxt.getText();
        String confirmPassword = confirmPasswordTxt.getText();
        return password.equals(confirmPassword);
    }

}
