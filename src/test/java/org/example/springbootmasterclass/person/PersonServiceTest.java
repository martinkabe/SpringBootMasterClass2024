package org.example.springbootmasterclass.person;

import org.example.springbootmasterclass.SortingOrder;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PersonServiceTest {

    @MockitoBean
    private PersonRepository personRepository;

    @MockitoBean
    private FakePersonRepository fakePersonRepository;

    @Autowired
    private PersonService personService;

    @Test
    void canGetAllPeople() {
        // given
        SortingOrder sort = SortingOrder.ASC;
        // when
        personService.getPeople(sort);
        // then
        ArgumentCaptor<Sort> sortCaptor =
                ArgumentCaptor.forClass(Sort.class);

        verify(personRepository).findAll(sortCaptor.capture());
        assertThat(sortCaptor.getValue())
                .isEqualTo(Sort.by(
                        Sort.Direction.valueOf(sort.name()),
                        "id"
                ));
    }
}