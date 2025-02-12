package taskmanagementsystem.model.user;

public enum RoleEnum {
    ROLE_USER("ЮЗЕР"),
    ROLE_MODERATOR( "МОДЕРАТОР"),
    ROLE_ADMIN("АДМИН");
    final String role;
    RoleEnum(String role) {
        this.role = role;
    }
}
