package ma.projet.classes;

import java.util.Date;

public class Commande {
    private int id;
    private Date date;

    public Commande() {
    }

    public Commande(Date date) {
        this.date = date;
    }

    public Commande(int id, Date date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}

