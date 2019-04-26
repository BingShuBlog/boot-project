package com.nbkj.bootjpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Address
 * 
 * @author: BingShu
 * @date:2019年4月3日18:01:51
 */
@Entity
@Table(name = "t_address")
@Data
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

}