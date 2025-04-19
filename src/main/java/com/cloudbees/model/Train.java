package com.cloudbees.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Train")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long trainNumber;
    private String trainName;
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Section> sections = new ArrayList<>();
    private String source;
    private String destination;

    public long getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(long trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
