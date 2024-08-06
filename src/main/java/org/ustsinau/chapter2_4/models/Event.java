package org.ustsinau.chapter2_4.models;



import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "events", schema = "schema_Rest_Flyway_Swagger_2_4")
@Schema(description = "Event entity")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Expose
    @Schema(description = "The database generated event ID")
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Expose
    @Schema(description = "The user associated with the event")
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    @Expose
    @Schema(description = "The file associated with the event")
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
