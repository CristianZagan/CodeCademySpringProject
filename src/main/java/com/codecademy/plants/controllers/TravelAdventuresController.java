package com.codecademy.plants.controllers;

import com.codecademy.plants.entities.Adventure;
import com.codecademy.plants.repositories.AdventureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/traveladventures")
public class TravelAdventuresController {

    private final AdventureRepository adventureRepository;

    public TravelAdventuresController(AdventureRepository adventureRepo) {
        this.adventureRepository = adventureRepo;
    }

    // Add controller methods below:
    @GetMapping()
    public Iterable<Adventure> getAllAdventures() {
        return this.adventureRepository.findAll();
    }

    @GetMapping("/bycountry/{country}")
    public List<Adventure> getByCountry(@PathVariable String country) {
        return this.adventureRepository.findByCountry(country);
    }

    @GetMapping("/bystate")
    public List<Adventure> getByState(@RequestParam("state") String state) {
        return this.adventureRepository.findByState(state);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Adventure createAdventure(@RequestBody Adventure adventure) {
        Optional<Adventure> existingAdventure = adventureRepository.findById(adventure.getId());
        if (existingAdventure.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Adventure with ID " + adventure.getId() + " already exists");
        }
        return adventureRepository.save(adventure);
    }

    @PutMapping("/{id}")
    public Adventure updateAdventure(@PathVariable Integer id, @RequestBody Adventure adventureToUpdate) {
        Optional<Adventure> adventureOptional = this.adventureRepository.findById(id);
        if (!adventureOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aventure Id not found with ID " + id);
        }
        Adventure existingAdventure = adventureOptional.get();
        existingAdventure.setBlogCompleted(adventureToUpdate.getBlogCompleted());
        return adventureRepository.save(existingAdventure);
    }

    @DeleteMapping("/{id}")
    public void deleteAdventure(@PathVariable Integer id) {
        Optional<Adventure> adventureOptional = this.adventureRepository.findById(id);
        if (adventureOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aventure Id not found");
        } else {
            adventureRepository.deleteById(id);
        }
    }
}





