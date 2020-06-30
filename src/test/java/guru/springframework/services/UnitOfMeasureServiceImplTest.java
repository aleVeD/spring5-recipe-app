package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {
  @Mock
  UnitOfMeasureRepository unitOfMeasureRepository;

  UnitOfMeasureService unitOfMeasureService;
  UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,unitOfMeasureToUnitOfMeasureCommand);
  }

  @Test
  void listAllUoms() {
    Set<UnitOfMeasure> units = new HashSet<>();
    UnitOfMeasure u1 = new UnitOfMeasure();
    u1.setId(1L);
    units.add(u1);
    UnitOfMeasure u2 = new UnitOfMeasure();
    u2.setId(2L);
    units.add(u2);
    when(unitOfMeasureRepository.findAll()).thenReturn(units);
    Set<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUoms();
    assertEquals(2, commands.size());
    verify(unitOfMeasureRepository, times(1)).findAll();
  }
}