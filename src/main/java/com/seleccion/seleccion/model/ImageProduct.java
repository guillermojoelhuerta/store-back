package com.seleccion.seleccion.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="imagen_producto")
public class ImageProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="id_imagen_producto")
    private Long idImageProduct;

    @Column(name = "id_usuario")
    private int idUsuario;

    @Column(name = "nombre_imagen")
    private String name;

    @Column(name = "id_producto")
    private Long idProduct;

    /*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", insertable=false, updatable=false)
    private Product product;
    */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_producto", insertable=false, updatable=false)
    private Product product;

    public Long getIdImageProduct() {
        return idImageProduct;
    }

    public void setIdImageProduct(Long idImageProduct) {
        this.idImageProduct = idImageProduct;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public String toString() {
        return "ImageProduct{" +
                "idImageProduct=" + idImageProduct +
                ", idUsuario=" + idUsuario +
                ", name='" + name + '\'' +
                ", idProduct=" + idProduct +
                ", product=" + product +
                '}';
    }
}
