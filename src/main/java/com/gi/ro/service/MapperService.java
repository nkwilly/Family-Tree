package com.gi.ro.service;

import com.gi.ro.entity.Female;
import com.gi.ro.entity.Male;
import com.gi.ro.entity.Person;
import com.gi.ro.entity.Union;
import com.gi.ro.service.dto.PersonCreateDTO;
import com.gi.ro.service.dto.UnionCreateDTO;

public interface MapperService {
    UnionCreateDTO toUnionCreateDTO(Union union);

    Union toUnion(UnionCreateDTO unionCreateDTO);

    Male toMale(PersonCreateDTO dto);

    Female toFemale(PersonCreateDTO dto);

    PersonCreateDTO toPersonCreateDTO(Person person);

    PersonCreateDTO toPersonCreateDTO(Male male);

    PersonCreateDTO toPersonCreateDTO(Female female);
}
