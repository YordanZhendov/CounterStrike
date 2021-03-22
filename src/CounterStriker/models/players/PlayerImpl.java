package CounterStriker.models.players;

import CounterStriker.models.guns.Gun;

import static CounterStriker.common.ExceptionMessages.*;

public abstract class PlayerImpl implements Player {
    private String username;
    private int health;
    private int armor;
    private boolean isAlive;
    private Gun gun;

    protected PlayerImpl(String username, int health, int armor, Gun gun) {
        this.setUsername(username);
        this.setHealth(health);
        this.setArmor(armor);
        this.setGun(gun);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new NullPointerException(INVALID_PLAYER_NAME);
        }
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException(INVALID_PLAYER_HEALTH);
        }
    }

    @Override
    public int getArmor() {
        return this.armor;
    }

    public void setArmor(int armor) {
        if (armor < 0) {
            throw new IllegalArgumentException(INVALID_PLAYER_ARMOR);
        }
    }

    @Override
    public boolean isAlive() {
        return this.isAlive;
    }

    public boolean setAlive(boolean alive) {
        return this.health > 0;
    }

    @Override
    public Gun getGun() {
        return this.gun;
    }

    public void setGun(Gun gun) {
        if (gun == null) {
            throw new NullPointerException(INVALID_GUN);
        }
    }

    @Override
    public void takeDamage(int points) {
        int restArmor = this.armor - points;
        if (restArmor <= 0) {
            int restHealth = this.health - Math.abs(restArmor);
            if (restHealth <= 0) {
                setAlive(false);
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();

        stringBuilder.append(this.username);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Health: ").append(this.health);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Armor: ").append(this.armor);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Gun: ").append(this.gun.getName());
        stringBuilder.append(System.lineSeparator());

        return stringBuilder.toString().trim();
    }
//    "{player type}: {player username}
//            --Health: {player health}
//--Armor: {player armor}
//--Gun: {player gun name}"

}
