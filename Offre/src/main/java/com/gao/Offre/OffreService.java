package com.gao.Offre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OffreService {
	 private final OffreRepository repository;
	    public Offre saveOffre(Offre offre)
	    {
	        return repository.save(offre);
	    }
	    public void deleteOffre(Offre offre)
	    {
	        repository.delete(offre);
	    }
	  
	    public void deleteOffreById(UUID id)
	    {
	        repository.deleteById(id);  }
	    public Offre updateOffre(Offre offre) {
	    	Offre OffreUpdated = repository.save(offre);
	        return OffreUpdated;
	        }
	    public List<Offre> findAllOffres(){
	        return repository.findAll();
	    }
}
