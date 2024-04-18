package com.example.demo.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="idProject")
  private Integer idProject ;
    private String Title;
    private  String Description ;
    private  String Prototype ;
    private  String Image ;
  private boolean liked;
  private boolean disliked;
  private int likes;
  private int dislikes;

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  @Column(name = "image_path")
  private String imagePath;
  @Column(name = "image_name")
  private String imageName;

  @Enumerated(EnumType.STRING)
    private  EtatProject etatProject;

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public int getDislikes() {
    return dislikes;
  }

  public void setDislikes(int dislikes) {
    this.dislikes = dislikes;
  }

  @Temporal(TemporalType.DATE)
  @JsonProperty("dateDebut")
  private Date Date_debut;

  @Temporal(TemporalType.DATE)
  @JsonProperty("dateFinPrevu")
  private Date Datefin_prevu;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
  private List<Comment> comments;
}
