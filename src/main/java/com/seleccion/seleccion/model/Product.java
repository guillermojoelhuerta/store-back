package com.seleccion.seleccion.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.List;

@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="producto")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="id_producto")
    private Long idProduct;
    @NotBlank(message = "El nombre is requerido")
    @Column(name = "nombre")
    private String name;
    @Column(name = "precio")
    @NotNull(message = "El precio es requerido")
    private BigDecimal price;
    @Column(name = "cantidad")
    @NotNull(message = "La cantidad es requerida")
    private int amount;
    @Column(name = "descripcion")
    @NotBlank(message = "La descripción es requerida")
    private String description;
    @Column(name = "activo")
    private boolean active;
    @Column(name = "id_usuario")
    @NotNull(message = "El id_usuario es requerido")
    private int idUsuario;
    @OneToOne(mappedBy="product", fetch = FetchType.EAGER)
    private ImageProduct imageProduct;

    @JoinTable(
            name = "rel_producto_categorias",
            joinColumns = @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", nullable = false)
    )
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ImageProduct getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(ImageProduct imageProduct) {
        this.imageProduct = imageProduct;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", idUsuario=" + idUsuario +
                ", imageProduct=" + imageProduct +
                ", categories=" + categories +
                '}';
    }
}
