package com.hibernate_example.models;

import javax.persistence.*;

@Entity
@Table(name="ability")
public class Ability {

    @Id
    @Column(name="ability_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "name", length = 128)
    private String name;

    @Column(name="damage")
    private int damage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_effect", length = 8)
    private StatusEffect statusEffect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public StatusEffect getStatusEffect() {
        return statusEffect;
    }

    public void setStatusEffect(StatusEffect statusEffect) {
        this.statusEffect = statusEffect;
    }

    enum StatusEffect {
        NONE,
        BURN,
        POISON,
        PARALYZE
    }
}
