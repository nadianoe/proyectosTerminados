package app.challenge;

import app.challenge.entidades.*;
import app.challenge.repositorios.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class ChallengeController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @RequestMapping(path = "/idAlumno", method = RequestMethod.GET)
    public ResponseEntity<Object> obtenerIdAlumno(@RequestParam long dni) {

        Alumno alumno = alumnoRepository.findByDni(dni);
        HashMap<String, Object> informacion = new HashMap<>();
        informacion.put("idAlumno",alumno.getId());
        return new ResponseEntity<>(informacion,HttpStatus.OK);

    }

    @RequestMapping(path = "/admin/infoProfesores", method = RequestMethod.GET)
    public ResponseEntity<Object> infoAdminProfesores(Authentication auth) {

        HashMap<String, Object> informacion = new HashMap<>();
        List<HashMap<String, Object>> materias = new ArrayList<>();

        if (auth.isAuthenticated()) {

            HashMap<String,Object> infoProfesores = new HashMap<>();
            List<HashMap<String, Object>> profesores = new ArrayList<>();

            for (Profesor profesor : profesorRepository.findAll()) {
                HashMap<String,Object> infoProfesor = new HashMap<>();
                infoProfesor.put("id",profesor.getId());
                infoProfesor.put("nombre",profesor.getNombre());
                infoProfesor.put("apellido",profesor.getApellido());
                infoProfesor.put("dni",profesor.getDni());
                infoProfesor.put("esActivo",profesor.getEsActivo());

                profesores.add(infoProfesor);
            }

            informacion.put("profesores",profesores);
            return new ResponseEntity<>(informacion,HttpStatus.OK);

        } else {

            informacion.put("mensaje","no está autorizado a ver la informacion");
            return new ResponseEntity<>(informacion,HttpStatus.UNAUTHORIZED);
        }


    }


    @RequestMapping(path = "/admin/infoMaterias", method = RequestMethod.GET)
    public ResponseEntity<Object> infoAdminMaterias(Authentication auth) {

        HashMap<String, Object> informacion = new HashMap<>();
        List<HashMap<String, Object>> materias = new ArrayList<>();

        if (auth.isAuthenticated()) {

            for (Materia materia : materiaRepository.findAll()) {

                HashMap<String, Object> infoDeMateria = new HashMap<>();
                infoDeMateria.put("id",materia.getId());
                infoDeMateria.put("nombre", materia.getNombre());
                infoDeMateria.put("horarioInicio",materia.getHorarioInicio());
                infoDeMateria.put("horarioFinal",materia.getHorarioFinal());

                HashMap<String,Object> infoProfesor = new HashMap();
                Profesor profesor = materia.getProfesor();
                infoProfesor.put("id",profesor.getId());
                infoProfesor.put("nombre",profesor.getNombre());
                infoProfesor.put("apellido",profesor.getApellido());
                infoProfesor.put("dni",profesor.getDni());
                infoProfesor.put("esActivo",profesor.getEsActivo());

                infoDeMateria.put("profesor",infoProfesor);
                infoDeMateria.put("cupo", materia.getCupoMaximo());
                materias.add(infoDeMateria);
            }

            informacion.put("materias", materias);
            return new ResponseEntity<>(informacion,HttpStatus.OK);

        } else {

            informacion.put("mensaje","no está autorizado a ver la informacion");
            return new ResponseEntity<>(informacion,HttpStatus.UNAUTHORIZED);
        }

    }

    @RequestMapping(path = "/alumno/infoMaterias", method = RequestMethod.GET)
    public ResponseEntity<Object> obtenerInfoDeMaterias() {

        HashMap<String, Object> informacion = new HashMap<>();
        List<HashMap<String, Object>> materias = new ArrayList<>();

        for (Materia materia : materiaRepository.findAll()) {

            HashMap<String, Object> infoDeMateria = new HashMap<>();
            infoDeMateria.put("id",materia.getId());
            infoDeMateria.put("nombre", materia.getNombre());
            infoDeMateria.put("horarioInicio",materia.getHorarioInicio());
            infoDeMateria.put("horarioFinal",materia.getHorarioFinal());

            Profesor profesor = materia.getProfesor();
            infoDeMateria.put("nombreProfesor",profesor.getNombre());
            infoDeMateria.put("apellidoProfesor",profesor.getApellido());

            infoDeMateria.put("cupo", materia.getCupoMaximo());
            materias.add(infoDeMateria);
        }

        informacion.put("materias", materias);

        return new ResponseEntity<>(informacion,HttpStatus.OK);

    }

    @RequestMapping(path = "/alumno/{idAlumno}/inscribirse", method = RequestMethod.POST)
    public ResponseEntity<Object> inscribirseAMateria(@PathVariable long idAlumno,
                                                      @RequestParam long idMateria) {

        HashMap<String,Object> infoResponse = new HashMap<>();

        Materia materia = materiaRepository.findById(idMateria);
        Alumno alumno = alumnoRepository.findById(idAlumno);

        alumno.agregarMateriaACursar(materia);
        materia.agregarAlumno(alumno);

        infoResponse.put("mensaje","Inscripción realizada");

        return new ResponseEntity<>(infoResponse,HttpStatus.OK);

    }

    @RequestMapping(path = "/admin/updateMateria", method = RequestMethod.POST)
    public ResponseEntity<Object> updateMateria(@RequestBody Materia materiaActualizada,
                                                Authentication auth) {

        HashMap<String, Object> infoResponse = new HashMap<>();

        if (chaqueoQueEsAdmin(auth)) {

            long id = materiaActualizada.getId();
            materiaRepository.deleteById(id);
            materiaRepository.save(materiaActualizada);
            infoResponse.put("mensaje","se actualizo materia");
            return new ResponseEntity<>(infoResponse,HttpStatus.OK);

        } else {

            infoResponse.put("mensaje","No pudo realizarse la inscripción");
            return new ResponseEntity<>(infoResponse,HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(path = "/admin/eliminarMateria", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminarMateria(@RequestParam long idMateria,
                                                  Authentication auth) {

        HashMap<String, Object> infoResponse = new HashMap<>();

        if (chaqueoQueEsAdmin(auth)) {
            materiaRepository.deleteById(idMateria);
            infoResponse.put("mensaje","se ha elimado materia");
            return new ResponseEntity<>(infoResponse,HttpStatus.OK);

        } else {

            infoResponse.put("mensaje","No pudo elimnarse la materia");
            return new ResponseEntity<>(infoResponse,HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(path = "/admin/agregarMateria", method = RequestMethod.PUT)
    public ResponseEntity<Object> agregarMateria(@RequestBody Materia materia,
                                                  Authentication auth) {

        HashMap<String, Object> infoResponse = new HashMap<>();

        if (chaqueoQueEsAdmin(auth)) {
            materiaRepository.save(materia);
            infoResponse.put("mensaje","se ha agregado nueva materia");
            return new ResponseEntity<>(infoResponse,HttpStatus.OK);

        } else {

            infoResponse.put("mensaje","No pudo agregarse materia");
            return new ResponseEntity<>(infoResponse,HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(path = "/admin/updateProfesor", method = RequestMethod.POST)
    public ResponseEntity<Object> updateProfesor(@RequestBody Profesor profesorActualizado,
                                                Authentication auth) {

        HashMap<String, Object> infoResponse = new HashMap<>();

        if (chaqueoQueEsAdmin(auth)) {

            long id = profesorActualizado.getId();
            profesorRepository.deleteById(id);
            profesorRepository.save(profesorActualizado);
            infoResponse.put("mensaje","se actualizo profesor");
            return new ResponseEntity<>(infoResponse,HttpStatus.OK);

        } else {

            infoResponse.put("mensaje","No pudo realizarse la inscripción");
            return new ResponseEntity<>(infoResponse,HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(path = "/admin/eliminarProfesor", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminarProfesor(@RequestParam long idProfesor,
                                                  Authentication auth) {

        HashMap<String, Object> infoResponse = new HashMap<>();

        if (chaqueoQueEsAdmin(auth)) {
            materiaRepository.deleteById(idProfesor);
            infoResponse.put("mensaje","se ha elimado profesor");
            return new ResponseEntity<>(infoResponse,HttpStatus.OK);

        } else {

            infoResponse.put("mensaje","No pudo elimnarse el profesor");
            return new ResponseEntity<>(infoResponse,HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(path = "/admin/agregarProfesor", method = RequestMethod.PUT)
    public ResponseEntity<Object> agregarProfesor(@RequestBody Profesor profesor,
                                                 Authentication auth) {

        HashMap<String, Object> infoResponse = new HashMap<>();

        if (chaqueoQueEsAdmin(auth)) {
            profesorRepository.save(profesor);
            infoResponse.put("mensaje","se ha agregado nuevo profesor");
            return new ResponseEntity<>(infoResponse,HttpStatus.OK);

        } else {

            infoResponse.put("mensaje","No pudo agregarse profesor");
            return new ResponseEntity<>(infoResponse,HttpStatus.UNAUTHORIZED);
        }
    }


    private boolean chaqueoQueEsAdmin(Authentication auth){
        SimpleGrantedAuthority tipoDeAuth = new SimpleGrantedAuthority("ADMIN");
        boolean esAdmin = auth.getAuthorities().contains(tipoDeAuth);
        return esAdmin;
    }

}
