package edu.umd.cs.daemondash.sqlite.model;

import java.util.List;

public class ContainerEntry implements Comparable<ContainerEntry> {

	private Long id;
	private Long tstamp;
	private User user;
	private Diner diner;
	private Double lat;
	private Double lon;
	private List<ContainerFood> foods;
	
	public ContainerEntry() {
	}
	
	public ContainerEntry(Long id, Long tstamp, User user, Diner diner,
			Double lat, Double lon, List<ContainerFood> foods) {
		this.id = id;
		this.tstamp = tstamp;
		this.user = user;
		this.diner = diner;
		this.lat = lat;
		this.lon = lon;
		this.foods = foods;
	}
	
	@Override
	public int compareTo(ContainerEntry another) {
		if (tstamp < another.tstamp) {
			return -1;
		} else if (tstamp > another.tstamp) {
			return 1;
		} else {
			return 0;
		}
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTstamp() {
		return tstamp;
	}
	public void setTstamp(Long tstamp) {
		this.tstamp = tstamp;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Diner getDiner() {
		return diner;
	}
	public void setDiner(Diner diner) {
		this.diner = diner;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public List<ContainerFood> getFoods() {
		return foods;
	}
	public void setFoods(List<ContainerFood> foods) {
		this.foods = foods;
	}
}
