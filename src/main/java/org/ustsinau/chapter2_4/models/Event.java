package org.ustsinau.chapter2_4.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

@Entity
@Table(name = "events", schema = "schema_Rest_Flyway_Swagger_2_4")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Expose
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Expose
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    @Expose
    private File file;

    public Event() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user=" + user +
                ", file=" + file +
                '}';
    }
}
