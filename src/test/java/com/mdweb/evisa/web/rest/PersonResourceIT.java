package com.mdweb.evisa.web.rest;

import static com.mdweb.evisa.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mdweb.evisa.IntegrationTest;
import com.mdweb.evisa.domain.Person;
import com.mdweb.evisa.domain.Request;
import com.mdweb.evisa.repository.PersonRepository;
import com.mdweb.evisa.service.PersonService;
import com.mdweb.evisa.service.dto.PersonDTO;
import com.mdweb.evisa.service.mapper.PersonMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link PersonResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PersonResourceIT {

    private static final String DEFAULT_DIPLOMATICMISSION = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOMATICMISSION = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_REQUESTPARTY = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTPARTY = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTITY = "AAAAAAAAAA";
    private static final String UPDATED_IDENTITY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BIRTHDAY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTHDAY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PASSPORTTYPE = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORTTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORTNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORTNUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EXPIRATIONDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRATIONDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_VISATYPE = "AAAAAAAAAA";
    private static final String UPDATED_VISATYPE = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_OTHERREASON = "AAAAAAAAAA";
    private static final String UPDATED_OTHERREASON = "BBBBBBBBBB";

    private static final String DEFAULT_GUEST = "AAAAAAAAAA";
    private static final String UPDATED_GUEST = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ADMINISTATION = "AAAAAAAAAA";
    private static final String UPDATED_ADMINISTATION = "BBBBBBBBBB";

    private static final String DEFAULT_AUTOMATICCHECK = "AAAAAAAAAA";
    private static final String UPDATED_AUTOMATICCHECK = "BBBBBBBBBB";

    private static final String DEFAULT_MANUALCHECK = "AAAAAAAAAA";
    private static final String UPDATED_MANUALCHECK = "BBBBBBBBBB";

    private static final String DEFAULT_ENTRIES = "AAAAAAAAAA";
    private static final String UPDATED_ENTRIES = "BBBBBBBBBB";

    private static final String DEFAULT_EXITS = "AAAAAAAAAA";
    private static final String UPDATED_EXITS = "BBBBBBBBBB";

    private static final String DEFAULT_LASTMOVE = "AAAAAAAAAA";
    private static final String UPDATED_LASTMOVE = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PersonRepository personRepository;

    @Mock
    private PersonRepository personRepositoryMock;

    @Autowired
    private PersonMapper personMapper;

    @Mock
    private PersonService personServiceMock;

    @Autowired
    private MockMvc restPersonMockMvc;

    private Person person;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity() {
        Person person = new Person()
            .diplomaticmission(DEFAULT_DIPLOMATICMISSION)
            .nationality(DEFAULT_NATIONALITY)
            .requestparty(DEFAULT_REQUESTPARTY)
            .identity(DEFAULT_IDENTITY)
            .birthday(DEFAULT_BIRTHDAY)
            .passporttype(DEFAULT_PASSPORTTYPE)
            .passportnumber(DEFAULT_PASSPORTNUMBER)
            .expirationdate(DEFAULT_EXPIRATIONDATE)
            .visatype(DEFAULT_VISATYPE)
            .reason(DEFAULT_REASON)
            .otherreason(DEFAULT_OTHERREASON)
            .guest(DEFAULT_GUEST)
            .adress(DEFAULT_ADRESS)
            .administation(DEFAULT_ADMINISTATION)
            .automaticcheck(DEFAULT_AUTOMATICCHECK)
            .manualcheck(DEFAULT_MANUALCHECK)
            .entries(DEFAULT_ENTRIES)
            .exits(DEFAULT_EXITS)
            .lastmove(DEFAULT_LASTMOVE)
            .summary(DEFAULT_SUMMARY);
        // Add required entity
        Request request;
        request = RequestResourceIT.createEntity();
        request.setId("fixed-id-for-tests");
        person.getRequests().add(request);
        return person;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createUpdatedEntity() {
        Person person = new Person()
            .diplomaticmission(UPDATED_DIPLOMATICMISSION)
            .nationality(UPDATED_NATIONALITY)
            .requestparty(UPDATED_REQUESTPARTY)
            .identity(UPDATED_IDENTITY)
            .birthday(UPDATED_BIRTHDAY)
            .passporttype(UPDATED_PASSPORTTYPE)
            .passportnumber(UPDATED_PASSPORTNUMBER)
            .expirationdate(UPDATED_EXPIRATIONDATE)
            .visatype(UPDATED_VISATYPE)
            .reason(UPDATED_REASON)
            .otherreason(UPDATED_OTHERREASON)
            .guest(UPDATED_GUEST)
            .adress(UPDATED_ADRESS)
            .administation(UPDATED_ADMINISTATION)
            .automaticcheck(UPDATED_AUTOMATICCHECK)
            .manualcheck(UPDATED_MANUALCHECK)
            .entries(UPDATED_ENTRIES)
            .exits(UPDATED_EXITS)
            .lastmove(UPDATED_LASTMOVE)
            .summary(UPDATED_SUMMARY);
        // Add required entity
        Request request;
        request = RequestResourceIT.createUpdatedEntity();
        request.setId("fixed-id-for-tests");
        person.getRequests().add(request);
        return person;
    }

    @BeforeEach
    public void initTest() {
        personRepository.deleteAll();
        person = createEntity();
    }

    @Test
    void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();
        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getDiplomaticmission()).isEqualTo(DEFAULT_DIPLOMATICMISSION);
        assertThat(testPerson.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testPerson.getRequestparty()).isEqualTo(DEFAULT_REQUESTPARTY);
        assertThat(testPerson.getIdentity()).isEqualTo(DEFAULT_IDENTITY);
        assertThat(testPerson.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testPerson.getPassporttype()).isEqualTo(DEFAULT_PASSPORTTYPE);
        assertThat(testPerson.getPassportnumber()).isEqualTo(DEFAULT_PASSPORTNUMBER);
        assertThat(testPerson.getExpirationdate()).isEqualTo(DEFAULT_EXPIRATIONDATE);
        assertThat(testPerson.getVisatype()).isEqualTo(DEFAULT_VISATYPE);
        assertThat(testPerson.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testPerson.getOtherreason()).isEqualTo(DEFAULT_OTHERREASON);
        assertThat(testPerson.getGuest()).isEqualTo(DEFAULT_GUEST);
        assertThat(testPerson.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testPerson.getAdministation()).isEqualTo(DEFAULT_ADMINISTATION);
        assertThat(testPerson.getAutomaticcheck()).isEqualTo(DEFAULT_AUTOMATICCHECK);
        assertThat(testPerson.getManualcheck()).isEqualTo(DEFAULT_MANUALCHECK);
        assertThat(testPerson.getEntries()).isEqualTo(DEFAULT_ENTRIES);
        assertThat(testPerson.getExits()).isEqualTo(DEFAULT_EXITS);
        assertThat(testPerson.getLastmove()).isEqualTo(DEFAULT_LASTMOVE);
        assertThat(testPerson.getSummary()).isEqualTo(DEFAULT_SUMMARY);
    }

    @Test
    void createPersonWithExistingId() throws Exception {
        // Create the Person with an existing ID
        person.setId("existing_id");
        PersonDTO personDTO = personMapper.toDto(person);

        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDiplomaticmissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setDiplomaticmission(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setNationality(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRequestpartyIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setRequestparty(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIdentityIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setIdentity(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkBirthdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setBirthday(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPassporttypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setPassporttype(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPassportnumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setPassportnumber(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkExpirationdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setExpirationdate(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkVisatypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setVisatype(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setReason(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkOtherreasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setOtherreason(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkGuestIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setGuest(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAdressIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setAdress(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAdministationIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setAdministation(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAutomaticcheckIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setAutomaticcheck(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkManualcheckIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setManualcheck(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEntriesIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setEntries(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkExitsIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setExits(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLastmoveIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setLastmove(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSummaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setSummary(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.save(person);

        // Get all the personList
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId())))
            .andExpect(jsonPath("$.[*].diplomaticmission").value(hasItem(DEFAULT_DIPLOMATICMISSION)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].requestparty").value(hasItem(DEFAULT_REQUESTPARTY)))
            .andExpect(jsonPath("$.[*].identity").value(hasItem(DEFAULT_IDENTITY)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(sameInstant(DEFAULT_BIRTHDAY))))
            .andExpect(jsonPath("$.[*].passporttype").value(hasItem(DEFAULT_PASSPORTTYPE)))
            .andExpect(jsonPath("$.[*].passportnumber").value(hasItem(DEFAULT_PASSPORTNUMBER)))
            .andExpect(jsonPath("$.[*].expirationdate").value(hasItem(sameInstant(DEFAULT_EXPIRATIONDATE))))
            .andExpect(jsonPath("$.[*].visatype").value(hasItem(DEFAULT_VISATYPE)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].otherreason").value(hasItem(DEFAULT_OTHERREASON)))
            .andExpect(jsonPath("$.[*].guest").value(hasItem(DEFAULT_GUEST)))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)))
            .andExpect(jsonPath("$.[*].administation").value(hasItem(DEFAULT_ADMINISTATION)))
            .andExpect(jsonPath("$.[*].automaticcheck").value(hasItem(DEFAULT_AUTOMATICCHECK)))
            .andExpect(jsonPath("$.[*].manualcheck").value(hasItem(DEFAULT_MANUALCHECK)))
            .andExpect(jsonPath("$.[*].entries").value(hasItem(DEFAULT_ENTRIES)))
            .andExpect(jsonPath("$.[*].exits").value(hasItem(DEFAULT_EXITS)))
            .andExpect(jsonPath("$.[*].lastmove").value(hasItem(DEFAULT_LASTMOVE)))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPeopleWithEagerRelationshipsIsEnabled() throws Exception {
        when(personServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(personServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPeopleWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(personServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(personRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getPerson() throws Exception {
        // Initialize the database
        personRepository.save(person);

        // Get the person
        restPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId()))
            .andExpect(jsonPath("$.diplomaticmission").value(DEFAULT_DIPLOMATICMISSION))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.requestparty").value(DEFAULT_REQUESTPARTY))
            .andExpect(jsonPath("$.identity").value(DEFAULT_IDENTITY))
            .andExpect(jsonPath("$.birthday").value(sameInstant(DEFAULT_BIRTHDAY)))
            .andExpect(jsonPath("$.passporttype").value(DEFAULT_PASSPORTTYPE))
            .andExpect(jsonPath("$.passportnumber").value(DEFAULT_PASSPORTNUMBER))
            .andExpect(jsonPath("$.expirationdate").value(sameInstant(DEFAULT_EXPIRATIONDATE)))
            .andExpect(jsonPath("$.visatype").value(DEFAULT_VISATYPE))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.otherreason").value(DEFAULT_OTHERREASON))
            .andExpect(jsonPath("$.guest").value(DEFAULT_GUEST))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS))
            .andExpect(jsonPath("$.administation").value(DEFAULT_ADMINISTATION))
            .andExpect(jsonPath("$.automaticcheck").value(DEFAULT_AUTOMATICCHECK))
            .andExpect(jsonPath("$.manualcheck").value(DEFAULT_MANUALCHECK))
            .andExpect(jsonPath("$.entries").value(DEFAULT_ENTRIES))
            .andExpect(jsonPath("$.exits").value(DEFAULT_EXITS))
            .andExpect(jsonPath("$.lastmove").value(DEFAULT_LASTMOVE))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY));
    }

    @Test
    void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPerson() throws Exception {
        // Initialize the database
        personRepository.save(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findById(person.getId()).get();
        updatedPerson
            .diplomaticmission(UPDATED_DIPLOMATICMISSION)
            .nationality(UPDATED_NATIONALITY)
            .requestparty(UPDATED_REQUESTPARTY)
            .identity(UPDATED_IDENTITY)
            .birthday(UPDATED_BIRTHDAY)
            .passporttype(UPDATED_PASSPORTTYPE)
            .passportnumber(UPDATED_PASSPORTNUMBER)
            .expirationdate(UPDATED_EXPIRATIONDATE)
            .visatype(UPDATED_VISATYPE)
            .reason(UPDATED_REASON)
            .otherreason(UPDATED_OTHERREASON)
            .guest(UPDATED_GUEST)
            .adress(UPDATED_ADRESS)
            .administation(UPDATED_ADMINISTATION)
            .automaticcheck(UPDATED_AUTOMATICCHECK)
            .manualcheck(UPDATED_MANUALCHECK)
            .entries(UPDATED_ENTRIES)
            .exits(UPDATED_EXITS)
            .lastmove(UPDATED_LASTMOVE)
            .summary(UPDATED_SUMMARY);
        PersonDTO personDTO = personMapper.toDto(updatedPerson);

        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getDiplomaticmission()).isEqualTo(UPDATED_DIPLOMATICMISSION);
        assertThat(testPerson.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testPerson.getRequestparty()).isEqualTo(UPDATED_REQUESTPARTY);
        assertThat(testPerson.getIdentity()).isEqualTo(UPDATED_IDENTITY);
        assertThat(testPerson.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testPerson.getPassporttype()).isEqualTo(UPDATED_PASSPORTTYPE);
        assertThat(testPerson.getPassportnumber()).isEqualTo(UPDATED_PASSPORTNUMBER);
        assertThat(testPerson.getExpirationdate()).isEqualTo(UPDATED_EXPIRATIONDATE);
        assertThat(testPerson.getVisatype()).isEqualTo(UPDATED_VISATYPE);
        assertThat(testPerson.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPerson.getOtherreason()).isEqualTo(UPDATED_OTHERREASON);
        assertThat(testPerson.getGuest()).isEqualTo(UPDATED_GUEST);
        assertThat(testPerson.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testPerson.getAdministation()).isEqualTo(UPDATED_ADMINISTATION);
        assertThat(testPerson.getAutomaticcheck()).isEqualTo(UPDATED_AUTOMATICCHECK);
        assertThat(testPerson.getManualcheck()).isEqualTo(UPDATED_MANUALCHECK);
        assertThat(testPerson.getEntries()).isEqualTo(UPDATED_ENTRIES);
        assertThat(testPerson.getExits()).isEqualTo(UPDATED_EXITS);
        assertThat(testPerson.getLastmove()).isEqualTo(UPDATED_LASTMOVE);
        assertThat(testPerson.getSummary()).isEqualTo(UPDATED_SUMMARY);
    }

    @Test
    void putNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(UUID.randomUUID().toString());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(UUID.randomUUID().toString());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(UUID.randomUUID().toString());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.save(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .requestparty(UPDATED_REQUESTPARTY)
            .expirationdate(UPDATED_EXPIRATIONDATE)
            .visatype(UPDATED_VISATYPE)
            .reason(UPDATED_REASON)
            .otherreason(UPDATED_OTHERREASON)
            .guest(UPDATED_GUEST)
            .adress(UPDATED_ADRESS)
            .entries(UPDATED_ENTRIES)
            .lastmove(UPDATED_LASTMOVE)
            .summary(UPDATED_SUMMARY);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getDiplomaticmission()).isEqualTo(DEFAULT_DIPLOMATICMISSION);
        assertThat(testPerson.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testPerson.getRequestparty()).isEqualTo(UPDATED_REQUESTPARTY);
        assertThat(testPerson.getIdentity()).isEqualTo(DEFAULT_IDENTITY);
        assertThat(testPerson.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testPerson.getPassporttype()).isEqualTo(DEFAULT_PASSPORTTYPE);
        assertThat(testPerson.getPassportnumber()).isEqualTo(DEFAULT_PASSPORTNUMBER);
        assertThat(testPerson.getExpirationdate()).isEqualTo(UPDATED_EXPIRATIONDATE);
        assertThat(testPerson.getVisatype()).isEqualTo(UPDATED_VISATYPE);
        assertThat(testPerson.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPerson.getOtherreason()).isEqualTo(UPDATED_OTHERREASON);
        assertThat(testPerson.getGuest()).isEqualTo(UPDATED_GUEST);
        assertThat(testPerson.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testPerson.getAdministation()).isEqualTo(DEFAULT_ADMINISTATION);
        assertThat(testPerson.getAutomaticcheck()).isEqualTo(DEFAULT_AUTOMATICCHECK);
        assertThat(testPerson.getManualcheck()).isEqualTo(DEFAULT_MANUALCHECK);
        assertThat(testPerson.getEntries()).isEqualTo(UPDATED_ENTRIES);
        assertThat(testPerson.getExits()).isEqualTo(DEFAULT_EXITS);
        assertThat(testPerson.getLastmove()).isEqualTo(UPDATED_LASTMOVE);
        assertThat(testPerson.getSummary()).isEqualTo(UPDATED_SUMMARY);
    }

    @Test
    void fullUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.save(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .diplomaticmission(UPDATED_DIPLOMATICMISSION)
            .nationality(UPDATED_NATIONALITY)
            .requestparty(UPDATED_REQUESTPARTY)
            .identity(UPDATED_IDENTITY)
            .birthday(UPDATED_BIRTHDAY)
            .passporttype(UPDATED_PASSPORTTYPE)
            .passportnumber(UPDATED_PASSPORTNUMBER)
            .expirationdate(UPDATED_EXPIRATIONDATE)
            .visatype(UPDATED_VISATYPE)
            .reason(UPDATED_REASON)
            .otherreason(UPDATED_OTHERREASON)
            .guest(UPDATED_GUEST)
            .adress(UPDATED_ADRESS)
            .administation(UPDATED_ADMINISTATION)
            .automaticcheck(UPDATED_AUTOMATICCHECK)
            .manualcheck(UPDATED_MANUALCHECK)
            .entries(UPDATED_ENTRIES)
            .exits(UPDATED_EXITS)
            .lastmove(UPDATED_LASTMOVE)
            .summary(UPDATED_SUMMARY);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getDiplomaticmission()).isEqualTo(UPDATED_DIPLOMATICMISSION);
        assertThat(testPerson.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testPerson.getRequestparty()).isEqualTo(UPDATED_REQUESTPARTY);
        assertThat(testPerson.getIdentity()).isEqualTo(UPDATED_IDENTITY);
        assertThat(testPerson.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testPerson.getPassporttype()).isEqualTo(UPDATED_PASSPORTTYPE);
        assertThat(testPerson.getPassportnumber()).isEqualTo(UPDATED_PASSPORTNUMBER);
        assertThat(testPerson.getExpirationdate()).isEqualTo(UPDATED_EXPIRATIONDATE);
        assertThat(testPerson.getVisatype()).isEqualTo(UPDATED_VISATYPE);
        assertThat(testPerson.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPerson.getOtherreason()).isEqualTo(UPDATED_OTHERREASON);
        assertThat(testPerson.getGuest()).isEqualTo(UPDATED_GUEST);
        assertThat(testPerson.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testPerson.getAdministation()).isEqualTo(UPDATED_ADMINISTATION);
        assertThat(testPerson.getAutomaticcheck()).isEqualTo(UPDATED_AUTOMATICCHECK);
        assertThat(testPerson.getManualcheck()).isEqualTo(UPDATED_MANUALCHECK);
        assertThat(testPerson.getEntries()).isEqualTo(UPDATED_ENTRIES);
        assertThat(testPerson.getExits()).isEqualTo(UPDATED_EXITS);
        assertThat(testPerson.getLastmove()).isEqualTo(UPDATED_LASTMOVE);
        assertThat(testPerson.getSummary()).isEqualTo(UPDATED_SUMMARY);
    }

    @Test
    void patchNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(UUID.randomUUID().toString());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(UUID.randomUUID().toString());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(UUID.randomUUID().toString());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePerson() throws Exception {
        // Initialize the database
        personRepository.save(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Delete the person
        restPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, person.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
