package com.gi.ro.service.impl;

import com.gi.ro.entity.Female;
import com.gi.ro.entity.Male;
import com.gi.ro.entity.Person;
import com.gi.ro.entity.Union;
import com.gi.ro.repository.FemaleRepository;
import com.gi.ro.repository.MaleRepository;
import com.gi.ro.service.MapperService;
import com.gi.ro.service.dto.PersonCreateDTO;
import com.gi.ro.service.dto.UnionCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperServiceImpl implements MapperService {

    private final MaleRepository maleRepository;
    private final FemaleRepository femaleRepository;

    @Autowired
    public MapperServiceImpl(MaleRepository maleRepository, FemaleRepository femaleRepository) {
        this.maleRepository = maleRepository;
        this.femaleRepository = femaleRepository;
    }

    @Override
    public UnionCreateDTO toUnionCreateDTO(Union union) {
        if (union == null) {
            return null;
        }

        return UnionCreateDTO.builder()
                .husbandId(union.getHusband() != null ? union.getHusband().getId() : null)
                .wifeId(union.getWife() != null ? union.getWife().getId() : null)
                .date(union.getDate())
                .place(union.getPlace())
                .church(union.isChurch())
                .build();
    }

    @Override
    public Union toUnion(UnionCreateDTO unionCreateDTO) {
        if (unionCreateDTO == null) {
            return null;
        }

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
}