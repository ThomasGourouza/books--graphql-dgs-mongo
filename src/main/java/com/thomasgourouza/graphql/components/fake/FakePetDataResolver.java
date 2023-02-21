package com.thomasgourouza.graphql.components.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.thomasgourouza.graphql.generated.types.Cat;
import com.thomasgourouza.graphql.generated.types.Dog;
import com.thomasgourouza.graphql.generated.types.Pet;
import com.thomasgourouza.graphql.generated.types.PetFilter;
import com.thomasgourouza.graphql.generated.types.PetFoodType;

import io.micrometer.common.util.StringUtils;

@DgsComponent
public class FakePetDataResolver {

    @DgsData(parentType = "Query", field = "pets")
    public List<Pet> getPets(@InputArgument(name = "petFilter") Optional<PetFilter> filter) {
        List<Pet> pets = new ArrayList<>();
        Cat cat = new Cat();
        cat.setName("Lumba");
        cat.setBreed("Chat sauvage");
        cat.setFood(PetFoodType.CARNIVORE);
        cat.setRegistry("Gris");
        Dog dog = new Dog();
        dog.setName("Doggy");
        dog.setBreed("Berger Suisse");
        dog.setFood(PetFoodType.CARNIVORE);
        dog.setSize("Grand");
        dog.setCoatLength("Long");
        pets.add(cat);
        pets.add(dog);

        if (filter.isEmpty()) {
            return pets;
        }

        return pets.stream().filter(
            pet -> this.matchFilter(filter.get(), pet)
        ).collect(Collectors.toList());
    }

    private boolean matchFilter(PetFilter petFilter, Pet pet) {
        if (StringUtils.isBlank(petFilter.getPetType())) {
            return true;
        }

        if (petFilter.getPetType().equalsIgnoreCase(Dog.class.getSimpleName())) {
            return pet instanceof Dog;
        } else if (petFilter.getPetType().equalsIgnoreCase(Cat.class.getSimpleName())) {
            return pet instanceof Cat;
        } else {
            return false;
        }
    }

}
