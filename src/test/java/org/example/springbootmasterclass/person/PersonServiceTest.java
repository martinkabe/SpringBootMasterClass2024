package org.example.springbootmasterclass.person;

import org.example.springbootmasterclass.SortingOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private FakePersonRepository fakePersonRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
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