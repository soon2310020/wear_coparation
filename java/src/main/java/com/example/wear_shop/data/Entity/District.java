package com.example.wear_shop.data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "district")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class District implements Serializable {
    private static final long serialVersionUID = 6174016284429444079L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "district_na")
    private String districtName;

    @Column(name = "district_co", nullable = false, unique = true)
    private String districtCode;
    @Column(name = "is_delete")
    private Long isDelete = 0L;

    //status
    @Column(name = "status")
    private Long status = 1L;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district")
    private List<Communue> communues;

    @JoinColumn(name = "province_id", referencedColumnName = "province_id")
    @ManyToOne(optional = false)
    private Province province;

}
