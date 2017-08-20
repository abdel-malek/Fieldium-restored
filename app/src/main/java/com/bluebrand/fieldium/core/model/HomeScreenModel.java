package com.bluebrand.fieldium.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 1/22/2017.
 */
public class HomeScreenModel  implements Serializable{

    private List<Game> games;
    private List<City> cities;
    private List<Booking> lastBookings;
    private List<Booking> upcomingBookings;
    private List<Offer> offeres;
    private List<Voucher> vouchers;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Booking> getLastBookings() {
        return lastBookings;
    }

    public void setLastBookings(List<Booking> lastBookings) {
        this.lastBookings = lastBookings;
    }

    public List<Booking> getUpcomingBookings() {
        return upcomingBookings;
    }

    public void setUpcomingBookings(List<Booking> upcomingBookings) {
        this.upcomingBookings = upcomingBookings;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public List<Offer> getOfferes() {
        return offeres;
    }

    public void setOfferes(List<Offer> offeres) {
        this.offeres = offeres;
    }
}
