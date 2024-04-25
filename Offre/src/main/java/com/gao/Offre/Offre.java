package com.gao.Offre;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offre {
	
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(columnDefinition = "BINARY(16)")
	    private UUID id;

private String nom;
private String secteuractivite;

}
