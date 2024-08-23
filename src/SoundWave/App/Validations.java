package SoundWave.App;

import javax.swing.*;

public class Validations {
    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean PasswordsMatch(JTextField passwordTxt,JTextField confirmPasswordTxt) {
        String password = passwordTxt.getText();
        String confirmPassword = confirmPasswordTxt.getText();
        return password.equals(confirmPassword);
    }

}
