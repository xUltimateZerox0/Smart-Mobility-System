package com.smartmobility.model;

import com.smartmobility.model.enums.RuoloAttore;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "attore")
public abstract class Attore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RuoloAttore ruolo;

    protected Attore() { /* required by JPA */ }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RuoloAttore getRuolo() {
        return ruolo;
    }

    public void setRuolo(RuoloAttore ruolo) {
        this.ruolo = ruolo;
    }
}
