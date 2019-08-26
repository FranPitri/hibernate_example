package com.hibernate_example.models;

import com.hibernate_example.models.Ability;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="pokemon")
public class Pokemon {

    @Id
    @Column(name="pokemon_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "name", length = 64, unique = true)
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="pokemon_ability",
            joinColumns={@JoinColumn(name="pokemon_id")},
            inverseJoinColumns={@JoinColumn(name="ability_id")})
    private Set<Ability> abilities = new HashSet<Ability>();

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

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<Ability> abilities) {
        this.abilities = abilities;
    }
}
