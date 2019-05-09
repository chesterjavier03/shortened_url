package com.project.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by chesterjavier.
 */
@Table(name = "url")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Url {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "original_url", length = 100)
  private String originalUrl;

  @Column(name = "shortened_url", length = 100)
  private String shortenedUrl;

  public Url(String originalUrl, String shortenedUrl) {
    this.originalUrl = originalUrl;
    this.shortenedUrl = shortenedUrl;
  }
}
