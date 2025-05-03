package com.gi.ro.service;

import com.gi.ro.entity.Female;
import com.gi.ro.entity.Male;
import com.gi.ro.entity.Person;
import com.gi.ro.entity.Union;
import com.gi.ro.service.dto.*;

public interface MapperService {
    UnionCreateDTO toUnionCreateDTO(Union union);

    UnionDTO toUnionDTO(Union union);

    Union toUnion(UnionCreateDTO unionCreateDTO);

    Union toUnion(UnionDTO unionDTO);

    Male toMale(PersonCreateDTO dto);

    Female toFemale(PersonCreateDTO dto);

    PersonCreateDTO toPersonCreateDTO(Person person);

    PersonCreateDTO toPersonCreateDTO(Male male);

    PersonCreateDTO toPersonCreateDTO(Female female);

    PersonDTO toPersonDTO(Male male);

    PersonDTO toPersonDTO(Female female);

    Male personDTOToMale(PersonDTO personDTO);

    Female personDTOToFemale(PersonDTO personDTO);

    Male personUpdateDTOToMale(PersonUpdateDTO personUpdateDTO);

    Female personUpdateDTOToFemale(PersonUpdateDTO personUpdateDTO);

    PersonUpdateDTO maleToPersonUpdateDTO(Person person);

    PersonUpdateDTO femaleToPersonUpdateDTO(Person person);

    PersonWithIdDTO toPersonWithIdDTO(Male male);

    PersonWithIdDTO toPersonWithIdDTO(Female female);
}