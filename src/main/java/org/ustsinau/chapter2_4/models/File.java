package org.ustsinau.chapter2_4.models;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

@Entity
@Table(name = "files", schema = "schema_Rest_Flyway_Swagger_2_4")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Expose
    private Integer id;

    @Column(name = "name")
    private String fileName;
    @Expose
    @Column(name = "filePath")
    private  String filePath;

    public File() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
