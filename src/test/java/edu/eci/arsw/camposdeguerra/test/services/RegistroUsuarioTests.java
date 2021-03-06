package edu.eci.arsw.camposdeguerra.test.services;

import edu.eci.arsw.camposdeguerra.cache.impl.InMemoryCamposDeGuerraRoomPersistence;
import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import edu.eci.arsw.camposdeguerra.persistence.impl.InMemoryCamposDeGuerraUsuarioPersistence;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class RegistroUsuarioTests {
   
    

    @Test
    public void saveNewAndLoadTest() {

        InMemoryCamposDeGuerraUsuarioPersistence icgp = new InMemoryCamposDeGuerraUsuarioPersistence();
        Usuario u = new Usuario("test1", null, 99999, 100, "");
        try {
            icgp.saveUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            assertNotNull("Loading a previously stored user.", icgp.findById(u.getId()));
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void saveExistingUserTest() {

        InMemoryCamposDeGuerraUsuarioPersistence icgp = new InMemoryCamposDeGuerraUsuarioPersistence();
        Usuario u = new Usuario("test2", null, 99999, 100, "");
        try {
            icgp.saveUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            icgp.saveUsuario(u);
        } catch (CamposDeGuerraPersistenceException ex) {
            //Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
            assertEquals(ex.getMessage(), "El nombre de usuario ya se encuentra registrado,elija otro nombre");
        }

    }

    @Test
    public void saveAndGetUsuarioTest() {
        
        InMemoryCamposDeGuerraRoomPersistence icgp = new InMemoryCamposDeGuerraRoomPersistence();
        Usuario u = new Usuario("test3", null, 99999, 100, "");
        try {
            icgp.addUserToRoom(u,0);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Usuario> temp = new ArrayList<>() ;
        try {
            temp = new ArrayList<>(icgp.getAllUsuariosFromRoom(0));
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(temp.get(0).getId(), u.getId());
    }

    @Test
    public void saveAndUpdateAndGetUsuarioTest() {

        InMemoryCamposDeGuerraRoomPersistence icgp = new InMemoryCamposDeGuerraRoomPersistence();
        Usuario u = new Usuario("test4", null, 0, 100, "");
        try {
            icgp.addUserToRoom(u,0);
        } catch (CamposDeGuerraPersistenceException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Usuario> temp = new ArrayList<>() ;
        try {
            temp = new ArrayList<>(icgp.getAllUsuariosFromRoom(0));
        } catch (CamposDeGuerraNotFoundException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(0, temp.get(0).getPuntaje());
    }

}
