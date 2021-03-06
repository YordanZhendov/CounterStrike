package CounterStriker.core;

import CounterStriker.core.interfaces.Controller;
import CounterStriker.models.field.Field;
import CounterStriker.models.field.FieldImpl;
import CounterStriker.models.guns.Gun;
import CounterStriker.models.guns.Pistol;
import CounterStriker.models.guns.Rifle;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.Terrorist;
import CounterStriker.repositories.GunRepository;
import CounterStriker.repositories.PlayerRepository;
import CounterStriker.repositories.Repository;

import java.util.Collection;

import java.util.stream.Collectors;

import static CounterStriker.common.ExceptionMessages.*;
import static CounterStriker.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private Repository<Gun> guns;
    private Repository<Player> players;
    private Field field;

    public ControllerImpl() {
        this.guns = new GunRepository<>();
        this.players = new PlayerRepository<>();
        this.field = new FieldImpl();
    }

    @Override
    public String addGun(String type, String name, int bulletsCount) {
        Gun gun;
        if ("Pistol".equals(type)) {
            gun = new Pistol(name, bulletsCount);
        } else if ("Rifle".equals(type)) {
            gun = new Rifle(name, bulletsCount);
        } else {
            throw new IllegalArgumentException(INVALID_GUN_TYPE);
        }

        this.guns.add(gun);

        return String.format(SUCCESSFULLY_ADDED_GUN, name);

    }

    @Override
    public String addPlayer(String type, String username, int health, int armor, String gunName) {
        Gun gun = this.guns.findByName(gunName);
        Player player;

        if (gun == null) {
            throw new NullPointerException(GUN_CANNOT_BE_FOUND);
        }

        if ("Terrorist".equals(type)) {
            player = new Terrorist(username, health, armor, gun);
        } else if ("CounterTerrorist".equals(type)) {
            player = new CounterTerrorist(username, health, armor, gun);
        } else {
            throw new IllegalArgumentException(INVALID_PLAYER_TYPE);
        }

        this.players.add(player);
        return String.format(SUCCESSFULLY_ADDED_PLAYER, username);
    }

    @Override
    public String startGame() {
        Collection<Player> playersFilter = this.players.getModels().stream().filter(Player::isAlive).collect(Collectors.toList());
        return this.field.start(playersFilter);
    }

    @Override
    public String report() {

        StringBuilder stringBuilder=new StringBuilder();
        this.players.getModels().stream().sorted((p1, p2) -> {
            int result = p1.getClass().getSimpleName().compareTo(p2.getClass().getSimpleName());

            if (result == 0) {
                result = Integer.compare(p1.getHealth(), p2.getHealth());

            }

            if (result == 0) {
                result =p1.getUsername().compareTo(p2.getUsername());
            }
            return result;
        }).forEach(player -> stringBuilder.append(player.toString()).append(System.lineSeparator()));



        return stringBuilder.toString().trim();
    }
}
