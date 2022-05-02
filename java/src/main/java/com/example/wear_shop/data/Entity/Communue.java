package com.example.wear_shop.data.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.FilterDef;

import javax.persistence.*;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "commune")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Communue  implements Serializable {
    private static final long serialVersionUID = 6174016284429444079L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commune_id")
    private Long communeId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "commune_name")
    private String communeName;
    @Column(name = "is_delete")
    private Long isDelete = 0L;
    @Column(name = "commune_code", nullable = false, unique = true)
    private String communeCode;

    //status
    @Column(name = "status")
    private Long status = 1L;

    @JoinColumn(name = "district_id", referencedColumnName = "district_id")
    @ManyToOne(optional = false)
    private District district;

}
