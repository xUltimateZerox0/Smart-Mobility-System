package com.smartmobility.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pa")
@PrimaryKeyJoinColumn(name = "id")
public class PA extends Attore {

    public PA() { /* required by JPA */ }
}
