/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.professordanilo.quizapp.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Event {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer id;
    private String name;
    private byte[] logo;
    private Integer stopwatch;
    @Enumerated(EnumType.STRING)
    private TypeCompetidor typeCompetidor;
//    private List<Player> players;

    public enum TypeCompetidor {
        GROUP("Em Grupo"),
        SINGLE("Individual");

        private String description;

        TypeCompetidor(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Integer getStopwatch() {
        return stopwatch;
    }

    public void setStopwatch(Integer stopwatch) {
        this.stopwatch = stopwatch;
    }

    public TypeCompetidor getTypeCompetidor() {
        return typeCompetidor;
    }

    public void setTypeCompetidor(TypeCompetidor typeCompetidor) {
        this.typeCompetidor = typeCompetidor;
    }

//    public List<Player> getPlayers() {
//        return players;
//    }
//
//    public void setPlayers(List<Player> players) {
//        this.players = players;
//    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
