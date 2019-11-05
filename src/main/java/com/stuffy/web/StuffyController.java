package com.stuffy.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stuffy.business.Stuffy;
import com.stuffy.db.StuffyRepository;

@CrossOrigin			// for security

@RestController			// exposes this class on the web
						// will only accept "/stuffies/"
						// since "rest", automatically returns data in JSON format
@RequestMapping("/stuffies")

public class StuffyController {

//	@GetMapping("/")
//	public Stuffy createAStuffy() {
//		Stuffy s = new Stuffy(1, "Whale", "Blue", "XL", 2);
//		return s;
//	}
	
	@Autowired
	private StuffyRepository stuffyRepo;
	
	// list - return all stuffies
	@GetMapping("/")
	public JsonResponse listStuffies() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(stuffyRepo.findAll());
		} catch  (Exception e ){
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	// demo use of a Path Variable
	// return one stuff for a given ID
	@GetMapping("/{id}")
	public JsonResponse getStuffy(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(stuffyRepo.findById(id)); 
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	// demo of Request Parameters  
	// to execute this, use a request similar to this:
	// http://localhost:8080/stuffies?id=10&type=Fish&color=pink&size=small&limbs=0
		
//	@GetMapping("")
//	public Stuffy createAStuffy(@RequestParam int id, @RequestParam String type, 
//			@RequestParam String color, @RequestParam String size, @RequestParam int limbs) {
//		Stuffy s = new Stuffy(id, type, color, size, limbs);
//		return s;
//	}
	
	// add - adds a new Stuffy
	@PostMapping("/")
	public JsonResponse addStuffy(@RequestBody Stuffy s) {
		// add a new stuffy
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(stuffyRepo.save(s));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);		
		}
		return jr;
	}
	
	// update - update a new Stuffy
	@PutMapping("/")
	public JsonResponse updateStuffy(@RequestBody Stuffy s) {
		// update a stuffy
		JsonResponse jr = null;
		try {
			if (stuffyRepo.existsById(s.getId())) {
				jr = JsonResponse.getInstance(stuffyRepo.save(s));
			} else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error updating Stuffy. "+
						"id: " + s.getId() + " doesn't exist");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);		
		}
		return jr;
	}

	// delete - delete a new Stuffy
	@DeleteMapping("/{id}")
	public JsonResponse deleteStuffy(@PathVariable int id) {
		// delete a stuffy
		JsonResponse jr = null;
		try {
			if (stuffyRepo.existsById(id)) {
				stuffyRepo.deleteById(id);
			} else {
				jr = JsonResponse.getInstance("Error deleting Stuffy. "  +
						"id: " + id + " doesn't exist");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		
		return jr;
	}
}
