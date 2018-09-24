package br.com.professordanilo.quizapp.entity;

import java.util.Objects;

/**
 *
 * @author danilo
 */
public class Question {

    private Integer id;
    private String question;
    private String falseAnswer1;
    private String falseAnswer2;
    private String falseAnswer3;
    private String falseAnswer4;
    private Integer trueAnswer;
    private String subject;
    private boolean selected;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFalseAnswer1() {
        return falseAnswer1;
    }

    public void setFalseAnswer1(String falseAnswer1) {
        this.falseAnswer1 = falseAnswer1;
    }

    public String getFalseAnswer2() {
        return falseAnswer2;
    }

    public void setFalseAnswer2(String falseAnswer2) {
        this.falseAnswer2 = falseAnswer2;
    }

    public String getFalseAnswer3() {
        return falseAnswer3;
    }

    public void setFalseAnswer3(String falseAnswer3) {
        this.falseAnswer3 = falseAnswer3;
    }

    public String getFalseAnswer4() {
        return falseAnswer4;
    }

    public void setFalseAnswer4(String falseAnswer4) {
        this.falseAnswer4 = falseAnswer4;
    }

    public Integer getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(Integer trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Question other = (Question) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

}
