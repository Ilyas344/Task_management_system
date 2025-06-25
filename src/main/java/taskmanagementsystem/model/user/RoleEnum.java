package taskmanagementsystem.model.user;

public enum RoleEnum {
    ROLE_USER("Пользователь"),
    ROLE_ADMIN("Администратор");
    final String role;
    RoleEnum(String role) {
        this.role = role;
    }

    public String getLocalizedRole() {
        return this.role;
    }
}
