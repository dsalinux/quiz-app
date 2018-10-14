package br.com.professordanilo.quizapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Fight implements Serializable {

    private static final long serialVersionUID = 1225367777881476419L;
    
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="player1_id")
    private Player player1;
    @ManyToOne
    @JoinColumn(name="player2_id")
    private Player player2;
    @ManyToOne
    @JoinColumn(name="questionSorted_id")
    private Question questionSorted;
    @ManyToOne
    @JoinColumn(name="winnerPlayer_id")
    private Player winnnerPlayer;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeTheFight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Question getQuestionSorted() {
        return questionSorted;
    }

    public void setQuestionSorted(Question questionSorted) {
        this.questionSorted = questionSorted;
    }

    public Player getWinnnerPlayer() {
        return winnnerPlayer;
    }

    public void setWinnnerPlayer(Player winnnerPlayer) {
        this.winnnerPlayer = winnnerPlayer;
    }

    public Date getTimeTheFight() {
        return timeTheFight;
    }

    public void setTimeTheFight(Date timeTheFight) {
        this.timeTheFight = timeTheFight;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Fight other = (Fight) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
