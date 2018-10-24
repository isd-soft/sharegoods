package com.sharegoods.inth3rship.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    public User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @NotNull
    public Item item;

    public Rating() {
    }

    public Rating(Double rating, User user, Item item) {
        this.rating = rating;
        this.user = user;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}