package com.task.delivery.entity;

import com.task.delivery.dto.BeerDto;

import javax.persistence.*;

@Entity
@Table(name = "beers")
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brewery_id", foreignKey = @ForeignKey(name = "beer_brewery"))
    private Brewery brewery;

    @Column(columnDefinition = "DOUBLE")
    private double abv;

    @Column(columnDefinition = "DOUBLE")
    private double ibu;

    @Column(columnDefinition = "DOUBLE")
    private double srm;

    @Column(columnDefinition = "TEXT")
    private String filepath;

    @Column(name = "descript", columnDefinition = "TEXT")
    private String description;

    @Column(name = "last_mod", columnDefinition = "TEXT")
    private String lastMod;

    private int upc;

    @Column(name = "add_user")
    private int addUser;


    public BeerDto toDto() {
        return new BeerDto(name);
    }
}
