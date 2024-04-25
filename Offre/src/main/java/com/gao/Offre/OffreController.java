package com.gao.Offre;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/offres")
public class OffreController {
	 @Autowired
	    private OffreService offreService;
	 
	    @RequestMapping(path="all",method =RequestMethod.GET)
	    public List<Offre> getAllOffres() {
	        return offreService.findAllOffres();
	    }
	    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERADMIN')")
	    @RequestMapping(value="/addoffre",method = RequestMethod.POST)
	    //@RequestMapping(method = RequestMethod.POST)
	    public Offre createOffre(@RequestBody Offre offre) {
	        return offreService.saveOffre(offre);
	}
	    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERADMIN')")
	    @RequestMapping(value="/deleteoffre/{id}",method = RequestMethod.DELETE)
	    public void deletePatient(@PathVariable("id") UUID id) {
	    	offreService.deleteOffreById(id);
	    }
	    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERADMIN')")
	    @RequestMapping(value="/updateoffre",method = RequestMethod.PUT)
	    //@PutMapping
	    public Offre updateOffre(@RequestBody Offre offre) {
	        return offreService.updateOffre(offre);

	    
	    }
}

