package org.ustsinau.chapter2_4.models;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "files", schema = "schema_Rest_Flyway_Swagger_2_4")
@Schema(description = "File entity")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Expose
    @Schema(description = "The database generated file ID")
    private Integer id;
    @Expose
    @Column(name = "name")
    @Schema(description = "The file name")
    private String fileName;
    @Expose
    @Column(name = "filePath")
    @Schema(description = "The file path")
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
