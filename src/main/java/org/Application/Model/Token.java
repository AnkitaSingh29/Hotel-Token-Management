package org.Application.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Application.TokenStatus;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    private Integer tokenID;
    private LocalDateTime expiryDate;
    private  String registrationID;
    private TokenStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Customer customer;


    @Override
    public String toString() {
        return "Token{" +
                "customer=" + customer +
                ", tokenID=" + tokenID +
                ", expiryDate=" + expiryDate +
                ", registrationID='" + registrationID + '\'' +
                ", status=" + status +
                '}';
    }
}
