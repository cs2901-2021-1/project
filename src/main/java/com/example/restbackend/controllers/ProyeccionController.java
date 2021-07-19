package com.example.restbackend.controllers;

import com.example.restbackend.model.proyeccion.Proyeccion;
import com.example.restbackend.service.ProyeccionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/proyeccion")
public class ProyeccionController {
    private final ProyeccionService proyeccionService;

    public ProyeccionController(ProyeccionService proyeccionService) {
        this.proyeccionService = proyeccionService;
    }

    @GetMapping(path = "/proyeccionGeneral")
    public List<Proyeccion> getProyecciones(){
        return proyeccionService.getProyecciones();
    }

    @GetMapping(path = "/findByCurCod/{curCod}")
    public Proyeccion getProyeccionCurso(String cudCod){
        return proyeccionService.findOneByCurCod(cudCod);
    }

    @GetMapping(path = "/proyeccionCarrera/{comCarrera}")
    public Collection<Proyeccion> getProyeccionCarrera(String comCarrera){
        return proyeccionService.findProyeccionByNomCarr(comCarrera);
    }

    @GetMapping(path = "/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=proyeccion_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        var proyecciones = proyeccionService.getProyecciones();
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "Nombre Carrera", "Codigo Curso", "Nombre del curso", "Numero de alumnos"};
        String[] nameMapping = {"id", "nomCarrera", "codCurso", "nomCurso", "numAlumn"};
        csvWriter.writeHeader(csvHeader);

        for(var proyeccion: proyecciones){
            csvWriter.write(proyeccion, nameMapping);
        }
        csvWriter.close();
    }
}
