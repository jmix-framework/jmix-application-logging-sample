package io.jmix.petclinic.service;

import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.pet.Pet;
import org.springframework.stereotype.Component;
import java.util.Optional;

// tag::logging-imports[]
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// end::logging-imports[]

// tag::logging-imports-mdc[]
import org.slf4j.MDC;

// end::logging-imports-mdc[]

// tag::pet-service-logger[]
@Component("petclinic_PetService")
public class PetService {

    private static final Logger log = LoggerFactory.getLogger(PetService.class); // <1>

    // end::pet-service-logger[]

    private final DataManager dataManager;

    public PetService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // tag::pet-service-logging[]
    public Optional<Pet> savePet(Pet pet) {
        try {
            Pet savedPet = dataManager.save(pet);
            log.info("Pet {} was saved correctly", pet); // <2>
            return Optional.of(savedPet);
        } catch (Exception e) {
            log.error("Pet {} could not be saved", pet, e); // <3>
            return Optional.empty();
        }
    }
    // end::pet-service-logging[]


    // tag::pet-service-logging-mdc[]
    public Optional<Pet> updatePet(Pet pet) {
        MDC.put("petId", pet.getIdentificationNumber()); // <1>

        log.info("Updating Pet"); // <2>

        try {
            Pet updatedPet = dataManager.save(pet);

            log.info("Pet Update successfully"); // <3>

            return Optional.of(updatedPet);
        } catch (Exception e) {
            log.error("Failed to update Pet", e); // <4>
            return Optional.empty();
        } finally {
            // Ensure MDC is cleared to avoid leaking context into unrelated log entries
            MDC.remove("petId"); // <5>
        }
    }
    // end::pet-service-logging-mdc[]
}