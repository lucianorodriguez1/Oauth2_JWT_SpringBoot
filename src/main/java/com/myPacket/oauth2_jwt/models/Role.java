package com.myPacket.oauth2_jwt.models;

import com.myPacket.oauth2_jwt.models.enums.ERole;
import jakarta.persistence.*;

@Entity
@Table(name= "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //Esta linea la pongo porque es un string.
    @Enumerated(EnumType.STRING)
    private ERole rol;

    public Role() {
    }

    public Role(long id, ERole rol) {
        this.id = id;
        this.rol = rol;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ERole getRol() {
        return rol;
    }

    public void setRol(ERole rol) {
        this.rol = rol;
    }
}
