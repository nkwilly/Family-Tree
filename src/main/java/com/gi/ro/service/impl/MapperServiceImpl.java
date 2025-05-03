package com.gi.ro.service.impl;

import com.gi.ro.entity.Female;
import com.gi.ro.entity.Male;
import com.gi.ro.entity.Person;
import com.gi.ro.entity.Union;
import com.gi.ro.repository.FemaleRepository;
import com.gi.ro.repository.MaleRepository;
import com.gi.ro.repository.UnionRepository;
import com.gi.ro.service.MapperService;
import com.gi.ro.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MapperServiceImpl implements MapperService {

    private final MaleRepository maleRepository;
    private final FemaleRepository femaleRepository;
    private final UnionRepository unionRepository;

    @Override
    public UnionCreateDTO toUnionCreateDTO(Union union) {
        if (union == null) return null;

        return UnionCreateDTO.builder()
                .husbandId(union.getHusband() != null ? union.getHusband().getId() : null)
                .wifeId(union.getWife() != null ? union.getWife().getId() : null)
                .date(union.getDate())
                .place(union.getPlace())
                .church(union.isChurch())
                .build();
    }

    @Override
    public UnionDTO toUnionDTO(Union union) {
        if (union == null) return null;

        return UnionDTO.builder()
                .id(union.getId())
                .husbandId(union.getHusband().getId())
                .wifeId(union.getWife().getId())
                .date(union.getDate())
                .place(union.getPlace())
                .church(union.isChurch())
                .build();
    }

    @Override
    public Union toUnion(UnionCreateDTO unionCreateDTO) {
        if (unionCreateDTO == null) return null;

        Union union = new Union();
        if (unionCreateDTO.getHusbandId() != null) {
            union.setHusband(maleRepository.findById(unionCreateDTO.getHusbandId())
                    .orElseThrow(() -> new IllegalArgumentException("Mari non trouvé avec l'ID: " + unionCreateDTO.getHusbandId())));
        }
        if (unionCreateDTO.getWifeId() != null) {
            union.setWife(femaleRepository.findById(unionCreateDTO.getWifeId())
                    .orElseThrow(() -> new IllegalArgumentException("Femme non trouvée avec l'ID: " + unionCreateDTO.getWifeId())));
        }
        union.setDate(unionCreateDTO.getDate());
        union.setPlace(unionCreateDTO.getPlace());
        union.setChurch(unionCreateDTO.getChurch() != null && unionCreateDTO.getChurch());

        return union;
    }

    @Override
    public Union toUnion(UnionDTO dto) {
        if (dto == null || dto.getId() == null) return null;
        Union union = unionRepository.findUnionById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Union non trouvé avec l'ID: " + dto.getId()));
        Optional<Male> husband = dto.getHusbandId() != null ? maleRepository.findById(dto.getHusbandId()) : Optional.empty();
        Optional<Female> wife = dto.getWifeId() != null ? femaleRepository.findById(dto.getWifeId()) : Optional.empty();
        union.setDate(dto.getDate());
        union.setPlace(dto.getPlace());
        union.setChurch(dto.isChurch());
        husband.ifPresent(union::setHusband);
        wife.ifPresent(union::setWife);
        return union;
    }

    @Override
    public Male toMale(PersonCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        Male male = new Male();
        copyPersonFields(dto, male);
        return male;
    }

    @Override
    public Female toFemale(PersonCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        Female female = new Female();
        copyPersonFields(dto, female);
        return female;
    }

    @Override
    public PersonCreateDTO toPersonCreateDTO(Person person) {
        if (person == null) {
            return null;
        }

        return PersonCreateDTO.builder()
                .name(person.getName())
                .email(person.getEmail())
                .phone(person.getPhone())
                .country(person.getCountry())
                .city(person.getCity())
                .born(person.getBorn())
                .death(person.getDeath())
                .photoUrl(person.getPhotoUrl())
                .unionId(person.getUnionId())
                .build();
    }

    @Override
    public PersonCreateDTO toPersonCreateDTO(Male male) {
        return toPersonCreateDTO((Person) male);
    }

    @Override
    public PersonCreateDTO toPersonCreateDTO(Female female) {
        return toPersonCreateDTO((Person) female);
    }

    @Override
    public PersonDTO toPersonDTO(Male male) {
        if (male == null)
            return null;
        PersonCreateDTO dto = this.toPersonCreateDTO(male);
       PersonDTO newPerson = new PersonDTO();
       this.copyPersonCreateInPersonDTO(dto, newPerson);
       newPerson.setSex(0);
        return newPerson;
    }

    @Override
    public PersonDTO toPersonDTO(Female female) {
        if (female == null)
            return null;

        PersonCreateDTO dto = this.toPersonCreateDTO(female);
        PersonDTO newPerson = new PersonDTO();
        this.copyPersonCreateInPersonDTO(dto, newPerson);
        newPerson.setSex(1);
        return newPerson;
    }

    @Override
    public Male personDTOToMale(PersonDTO personDTO) {
        if (personDTO == null)
            return null;
        Male male = new Male();
        this.copyPersonFields(personDTO, male);
        return male;
    }

    @Override
    public Female personDTOToFemale(PersonDTO personDTO) {
        if (personDTO == null)
            return null;
        Female female = new Female();
        this.copyPersonFields(personDTO, female);
        return female;
    }

    @Override
    public Male personUpdateDTOToMale(PersonUpdateDTO dto) {
        if (dto == null || dto.getId() == null) return null;
        Male male = new Male();
        male.setId(dto.getId());
        this.copyFieldsWithNullCheck(dto, male);
        return male;
    }

    @Override
    public Female personUpdateDTOToFemale(PersonUpdateDTO dto) {
        if (dto == null || dto.getId() == null) return null;
        Female female = new Female();
        female.setId(dto.getId());
        this.copyFieldsWithNullCheck(dto, female);
        return female;
    }

    @Override
    public PersonUpdateDTO maleToPersonUpdateDTO(Person person) {
        return null;
    }

    @Override
    public PersonUpdateDTO femaleToPersonUpdateDTO(Person person) {
        return null;
    }

    @Override
    public PersonWithIdDTO toPersonWithIdDTO(Male male) {
        if (male == null) return null;

        PersonDTO dto = this.toPersonDTO(male);
        PersonWithIdDTO newPerson = new PersonWithIdDTO();
        copyPersonCreateInPersonDTO(dto, newPerson);
        newPerson.setId(male.getId());
        newPerson.setSex(0);
        return newPerson;
    }

    @Override
    public PersonWithIdDTO toPersonWithIdDTO(Female female) {
        if (female == null) return null;
        PersonDTO dto = this.toPersonDTO(female);
        PersonWithIdDTO newPerson = new PersonWithIdDTO();
        copyPersonCreateInPersonDTO(dto, newPerson);
        newPerson.setId(female.getId());
        newPerson.setSex(1);
        return newPerson;
    }

    private void copyPersonFields(PersonCreateDTO dto, Person person) {
        person.setName(dto.getName());
        person.setEmail(dto.getEmail());
        person.setPhone(dto.getPhone());
        person.setCountry(dto.getCountry());
        person.setCity(dto.getCity());
        person.setBorn(dto.getBorn());
        person.setDeath(dto.getDeath());
        person.setPhotoUrl(dto.getPhotoUrl());
        person.setUnionId(dto.getUnionId());
    }

    private void copyFieldsWithNullCheck(PersonUpdateDTO dto, Person female) {
        if (dto.getName() != null) female.setName(dto.getName());
        if (dto.getEmail() != null) female.setEmail(dto.getEmail());
        if (dto.getPhone() != null) female.setPhone(dto.getPhone());
        if (dto.getCountry() != null) female.setCountry(dto.getCountry());
        if (dto.getCity() != null) female.setCity(dto.getCity());
        if (dto.getBorn() != null) female.setBorn(dto.getBorn());
        if (dto.getDeath() != null) female.setDeath(dto.getDeath());
        if (dto.getPhotoUrl() != null) female.setPhotoUrl(dto.getPhotoUrl());
        if (dto.getUnionId() != null) female.setUnionId(dto.getUnionId());
    }

    private void copyPersonCreateInPersonDTO(PersonCreateDTO dto, PersonDTO newPerson) {
        newPerson.setName(dto.getName());
        newPerson.setPhone(dto.getPhone());
        newPerson.setEmail(dto.getEmail());
        newPerson.setCountry(dto.getCountry());
        newPerson.setCity(dto.getCity());
        newPerson.setBorn(dto.getBorn());
        newPerson.setDeath(dto.getDeath());
        newPerson.setPhotoUrl(dto.getPhotoUrl());
        newPerson.setUnionId(dto.getUnionId());
    }
}