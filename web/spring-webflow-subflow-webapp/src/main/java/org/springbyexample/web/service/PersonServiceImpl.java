/*
 * Copyright 2007-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springbyexample.web.service;

import java.util.Collection;

import org.springbyexample.web.orm.entity.Address;
import org.springbyexample.web.orm.entity.Person;
import org.springbyexample.web.orm.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Person service implementation.
 * 
 * @author David Winterfeldt
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    
    @Autowired
    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person findById(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public Collection<Person> find() {
        return repository.findAll();
    }

    @Override
    public Person save(Person person) {
        return repository.saveAndFlush(person);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    @Transactional
    public Person saveAddress(Integer id, Address address) {
        Person person = findById(id);

        if (person.getAddresses().contains(address)) {
            person.getAddresses().remove(address);
        }

        person.getAddresses().add(address);        

        return save(person);
    }

    @Override
    @Transactional
    public Person deleteAddress(Integer id, Integer addressId) {
        Person person = findById(id);
        
        Address address = new Address();
        address.setPk(addressId);        

        if (person.getAddresses().contains(address)) {
            person.getAddresses().remove(address);
        }

        return save(person);
    }
    
}
