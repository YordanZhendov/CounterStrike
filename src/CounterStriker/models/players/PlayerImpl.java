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
        return this.username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new NullPointerException(INVALID_PLAYER_NAME);
        }
        this.username=username;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException(INVALID_PLAYER_HEALTH);
        }

        this.health=health;
    }

    @Override
    public int getArmor() {
        return this.armor;
    }

    public void setArmor(int armor) {
        if (armor < 0) {
            throw new IllegalArgumentException(INVALID_PLAYER_ARMOR);
        }

        this.armor=armor;
    }

    @Override
    public boolean isAlive() {
        return this.health>0;
    }

    @Override
    public Gun getGun() {
        return this.gun;
    }

    public void setGun(Gun gun) {
        if (gun == null) {
            throw new NullPointerException(INVALID_GUN);
        }
        this.gun=gun;
    }

    @Override
    public void takeDamage(int points) {

        int restArmor = this.armor - points;
        if (restArmor < 0) {
            this.setArmor(0);

            int restHealth = this.getHealth() - Math.abs(restArmor);
            if(restHealth>0){
                this.setHealth(restHealth);
                this.isAlive=true;
            }else {
                this.setHealth(0);
                this.isAlive=false;
            }

        }else {
            this.setArmor(restArmor);
        }


    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();

        stringBuilder.append(String.format("%s: %s",this.getClass().getSimpleName(),this.getUsername()));
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(String.format("--Health: %d",this.getHealth()));
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(String.format("--Armor: %d",this.getArmor()));
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(String.format("--Gun: %s",this.gun.getName()));
        stringBuilder.append(System.lineSeparator());

        return stringBuilder.toString().trim();
    }
//    "{player type}: {player username}
//            --Health: {player health}
//--Armor: {player armor}
//--Gun: {player gun name}"

}
