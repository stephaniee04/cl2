package pe.edu.cibertec.cl2_stephanie_eusebio_garro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.dto.FilmCreateDto;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.dto.FilmDetailDto;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.dto.FilmDto;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.service.MaintenanceService;

import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {
    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/start")
    public String start(Model model) {

        List<FilmDto> films = maintenanceService.findAllFilms();
        model.addAttribute("films", films);
        return "maintenance";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findFilmById(id);
        model.addAttribute("film", filmDetailDto);
        return "maintenance_detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findFilmById(id);
        model.addAttribute("film", filmDetailDto);
        return "maintenance_edit";
    }

    @PostMapping("/edit-confirm")
    public String editConfirm(@ModelAttribute FilmDetailDto filmDetailDto, Model model) {
        maintenanceService.updateFilm(filmDetailDto);
        return "redirect:/maintenance/start";
    }

    //
    @PostMapping("/remove/{id}")
    public String removeFilm(@PathVariable Integer id, Model model) {
        boolean isDeleted = maintenanceService.deleteFilm(id);

        if (isDeleted) {
            return "redirect:/maintenance/start";
        } else {
            model.addAttribute("errorMessage", "Error al eliminar la película.");
            return "maintenance";
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("filmCreateDto", new FilmCreateDto(
                "",
                "",
                0,
                1,
                0,
                0.0,
                0,
                0.0,
                "",
                ""
        ));
        return "maintenance_create";
    }

    @PostMapping("/create-confirm")
    public String createConfirm(@ModelAttribute FilmCreateDto filmCreateDto, Model model) {
        boolean isCreated = maintenanceService.createFilm(filmCreateDto);

        if (isCreated) {
            return "redirect:/maintenance/start";
        } else {
            model.addAttribute("errorMessage", "Error al crear la película.");
            return "maintenance_create";
        }
    }
}
