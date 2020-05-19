package altayo.springfw.recipeapp.controllers;

import altayo.springfw.recipeapp.commands.RecipeCommand;
import altayo.springfw.recipeapp.exceptions.NotFoundException;
import altayo.springfw.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public String showRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }

    @GetMapping("/create")
    public String createRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/create";
    }

    @GetMapping("/{id}/edit")
    public String editRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(new Long(id)));
        return "recipe/create"; // Redundant to add a separate edit view.
    }

    @PostMapping("/store")
    public String storeRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error("Validation Error: " + objectError);
            });
            return "recipe/create";
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:" + savedCommand.getId();
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        recipeService.deleteById(new Long(id));

        return "redirect:/";
    }

    @GetMapping("/{id}/edit/image")
    public String editImageRecipe(@PathVariable String id, Model model) {
        RecipeCommand foundCommand = recipeService.findCommandById(new Long(id));
        model.addAttribute("recipe", foundCommand);
        return "recipe/editImage";
    }

    @PostMapping("/{id}/store/image")
    public String storeImageRecipe(@PathVariable String id, @RequestParam("imageFile") MultipartFile file) {
        recipeService.saveImageFile(new Long(id), file);
        return "redirect:/recipe/" + id;
    }

    @GetMapping("/{id}/recipeImage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));

        if (recipeCommand.getImage() != null) {
            byte[] byteArray = new byte[recipeCommand.getImage().length];
            int i = 0;

            for (Byte wrappedByte : recipeCommand.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handlerNotFound(Exception exception) {
        log.error("Handling -> Not Found Exception: " + exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception.getMessage());
        return modelAndView;
    }
}