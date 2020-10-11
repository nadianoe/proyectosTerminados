package app.challenge;

import app.challenge.entidades.Alumno;
import app.challenge.DniNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface ServiceLogin {

    UserDetails loadUserByDni(long dni) throws DniNotFoundException;

}
