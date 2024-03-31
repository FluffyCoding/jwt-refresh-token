package com.unity.jwtrefresh.entities;

import jakarta.persistence.*;

@Entity
public class AppPages {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String pageName;

    @ManyToOne
    @JoinColumn(name = "parent_module_id")
    private AppModule appModule;

}
