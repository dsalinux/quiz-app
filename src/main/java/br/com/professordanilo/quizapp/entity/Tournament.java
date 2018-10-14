package br.com.professordanilo.quizapp.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1233808275365301090L;
    
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer id;
    private String name;
    private Integer stopwatch = 5;
    @Enumerated(EnumType.STRING)
    private TypeCompetidor typeCompetidor = TypeCompetidor.GROUP;
    @OneToMany(mappedBy = "tournament")
    private List<Player> players;

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final Tournament other = (Tournament) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    

}
