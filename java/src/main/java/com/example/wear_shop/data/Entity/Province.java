package com.example.wear_shop.data.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.domain.Auditable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "province")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Province  implements Serializable {
    private static final long serialVersionUID = 6174016284429444079L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "province_co", nullable = false, unique = true)
    private String provinceCode;

    @Column(name = "province_na")
    private String provinceName;

    @Column(name = "is_delete")
    private Long isDelete = 0L;

    //status
    @Column(name = "status")
    private Long status = 1L;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "province")
    private List<District> districts;


}