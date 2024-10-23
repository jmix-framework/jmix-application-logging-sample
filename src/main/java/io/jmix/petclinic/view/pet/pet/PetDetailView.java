package io.jmix.petclinic.view.pet.pet;

import io.jmix.core.EntityStates;
import io.jmix.core.SaveContext;
import io.jmix.petclinic.entity.pet.Pet;

import io.jmix.petclinic.service.PetService;
import io.jmix.petclinic.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Route(value = "pets/:id", layout = MainView.class)
@ViewController("petclinic_Pet.detail")
@ViewDescriptor("pet-detail-view.xml")
@EditedEntityContainer("petDc")
@DialogMode
public class PetDetailView extends StandardDetailView<Pet> {
    @Autowired
    private PetService petService;
    @Autowired
    private EntityStates entityStates;

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> saveDelegate(final SaveContext saveContext) {

        if (entityStates.isNew(getEditedEntity())) {
            Optional<Pet> savedPet = petService.savePet(getEditedEntity());

            if (savedPet.isPresent()) {
                return Collections.singleton(savedPet.get());
            }
            else {
                return Collections.emptySet();
            }
        }
        else {
            Optional<Pet> savedPet = petService.updatePet(getEditedEntity());

            if (savedPet.isPresent()) {
                return Collections.singleton(savedPet.get());
            }
            else {
                return Collections.emptySet();
            }
        }
    }
}