package paths;

public abstract class path {
    public String name;
    public String description;
    public String weapon_type;

    public String getWeaponType() {
        return this.weapon_type;
    }
}
