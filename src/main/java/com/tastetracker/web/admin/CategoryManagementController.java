package com.tastetracker.web.admin;

import com.tastetracker.domain.category.CategoryService;
import com.tastetracker.domain.category.dto.CategoryDto;
import com.tastetracker.domain.category.dto.NewCategorySaveDto;
import com.tastetracker.exception.CategoryAlreadyExsistsException;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class CategoryManagementController
{
    private final CategoryService categoryService;

    @GetMapping("/admin/add-category")
    public String addCategoryForm( Model model )
    {
        NewCategorySaveDto category = new NewCategorySaveDto();
        model.addAttribute( "category", category );
        return "admin/category-add-form";
    }

    @PostMapping("/admin/add-category" )
    public String addNewCategory( NewCategorySaveDto category, RedirectAttributes redirectAttributes )
    {
        CategoryDto categoryDto = categoryService.addNewCategory( category );
        redirectAttributes.addFlashAttribute(
            AdministrationPanelController.NOTIFICATION_ATTRIBUTE,
            "Kategoria %s została zapisana".formatted( categoryDto.name() )
        );

        return "redirect:/admin";
    }

    @ExceptionHandler( {CategoryAlreadyExsistsException.class} )
    public String handleCategoryAlreadyExsistsException(  CategoryAlreadyExsistsException ex, RedirectAttributes redirectAttributes )
    {
        redirectAttributes.addFlashAttribute( AdministrationPanelController.NOTIFICATION_ATTRIBUTE, ex.getMessage() );
        return "redirect:/admin";
    }
}
