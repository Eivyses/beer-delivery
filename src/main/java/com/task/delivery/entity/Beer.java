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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
    }

    public double getAbv() {
        return abv;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    public double getIbu() {
        return ibu;
    }

    public void setIbu(double ibu) {
        this.ibu = ibu;
    }

    public double getSrm() {
        return srm;
    }

    public void setSrm(double srm) {
        this.srm = srm;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastMod() {
        return lastMod;
    }

    public void setLastMod(String lastMod) {
        this.lastMod = lastMod;
    }

    public int getUpc() {
        return upc;
    }

    public void setUpc(int upc) {
        this.upc = upc;
    }

    public int getAddUser() {
        return addUser;
    }

    public void setAddUser(int addUser) {
        this.addUser = addUser;
    }
}
