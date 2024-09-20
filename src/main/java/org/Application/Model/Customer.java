package org.Application.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {


    @Id
    private Integer id;
    private String name;
    private String address;



}
