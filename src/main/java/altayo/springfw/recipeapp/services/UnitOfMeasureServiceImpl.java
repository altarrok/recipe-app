package altayo.springfw.recipeapp.services;

import altayo.springfw.recipeapp.commands.UnitOfMeasureCommand;
import altayo.springfw.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import altayo.springfw.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> findAllCommand() {
        Set<UnitOfMeasureCommand> uomCommandSet = new HashSet<>();
        unitOfMeasureRepository.findAll().forEach(unitOfMeasure -> {
            uomCommandSet.add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure));
        });
        return uomCommandSet;
    }
}
