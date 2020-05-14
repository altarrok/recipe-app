package altayo.springfw.recipeapp.services;

import altayo.springfw.recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> findAllCommand();
}
