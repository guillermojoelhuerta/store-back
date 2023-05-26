package com.seleccion.seleccion.model;

import com.seleccion.seleccion.validation.LettersOnly;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name="categorias")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id_categoria")
    private Long idCategory;

    @NotBlank(message = "El nombre es requerido")
    @Column(name = "nombre")
    @LettersOnly(message = "El campo nombre solo debe contener letras")
    private String name;

    @NotBlank(message = "La descripción es requerida")
    @Column(name = "descripcion")
    private String description;

    @Column(name = "activo")
    private boolean active;

    @ManyToMany(mappedBy = "categories", cascade = { CascadeType.MERGE })
    private List<Product> products;

    public Long getIdCategory() {
        return idCategory;
    }
    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory=" + idCategory +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                '}';
    }
}
