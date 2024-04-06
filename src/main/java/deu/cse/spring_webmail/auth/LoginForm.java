package deu.cse.spring_webmail.auth;

public record LoginForm(
        String username,
        String password
) {
}
