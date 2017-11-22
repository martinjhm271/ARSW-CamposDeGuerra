/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.cache.impl;

import edu.eci.arsw.camposdeguerra.model.Room;
import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.cache.CamposDeGuerraRoomPersistence;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;


public class RedisCamposDeGuerraRoomPersistence implements CamposDeGuerraRoomPersistence {

    private final ConcurrentHashMap<Integer, Room> rooms = new ConcurrentHashMap<>();

    public RedisCamposDeGuerraRoomPersistence() {
        for (int i = 0; i < 20; i++) {
            rooms.putIfAbsent(i, new Room(i));
        }
    }


    
    @Override
    public Integer getRoomFree() throws CamposDeGuerraNotFoundException {
        Integer r = 0;
        for (Room room : rooms.values()) {
            if (!room.isFull()) {
                r = room.getId();
                break;
            }
        }
        return r;
    }

    @Override
    public void addUserToRoom(Usuario us, Integer room) throws CamposDeGuerraPersistenceException {
        if (rooms.containsKey(room)) {
            boolean ans = rooms.get(room).addCompetidor(us);
            if(!ans){
                throw  new CamposDeGuerraPersistenceException("La Room ingresada se encuentre llena, Por favor intente con otra");
            }
        } else {
            throw  new CamposDeGuerraPersistenceException("La Room ingresada no existe!");
        }
    }

    @Override
    public Set<Usuario> getAllUsuariosFromRoom(Integer room) throws CamposDeGuerraNotFoundException {
        HashSet<Usuario> ans = new HashSet<>();
        if (rooms.containsKey(room)) {
            ans=rooms.get(room).getAllCompetitors();
        } else {
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
        return ans;
    }

    @Override
    public void deleteUsuarioFromRoom(Usuario us, Integer room) throws CamposDeGuerraNotFoundException {
        if (rooms.containsKey(room)) {
            boolean ans=rooms.get(room).deleteUser(us);
            if(!ans){
                throw  new CamposDeGuerraNotFoundException("El usuario no existe en la room indicada.");
            }
        } else {
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void deleteAllUsuariosFromRoom(Integer room) throws CamposDeGuerraNotFoundException {
        if (rooms.containsKey(room)) {
            rooms.get(room).clear();
        } else {
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public Set<Usuario> getAllUsuariosFromTeamARoom(Integer room) throws CamposDeGuerraNotFoundException {
        HashSet<Usuario> ans = new HashSet<>();
        if (rooms.containsKey(room)) {
            ans=rooms.get(room).getAllCompetitorsTeamA();
        } else {
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
        return ans;
    }

    @Override
    public Set<Usuario> getAllUsuariosFromTeamBRoom(Integer room) throws CamposDeGuerraNotFoundException {
        HashSet<Usuario> ans = new HashSet<>();
        if (rooms.containsKey(room)) {
            ans=rooms.get(room).getAllCompetitorsTeamB();
        } else {
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
        return ans;
    }

    @Override
    public String getTeamOfMyRoom(String user, Integer room) throws CamposDeGuerraNotFoundException {
        String ans="";
        if(rooms.containsKey(room)){
            ans=rooms.get(room).TeamOfUser(user);
        }
        else{
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
        if(ans.equals("Ninguno")){
            throw  new CamposDeGuerraNotFoundException("El usuario ingresado no existe en esta sala!");
        }
        return ans;
    }

    @Override
    public void setFlagARoom(String user, Integer room) throws CamposDeGuerraNotFoundException {
        boolean ans;
        if(rooms.containsKey(room)){
            ans=rooms.get(room).tomarBanderaA(user);
            if(!ans){
                throw  new CamposDeGuerraNotFoundException("La Bandera ya fue tomada o el usuario no existe en la sala!");
            }
        }
        else{
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void setFlagBRoom(String user, Integer room) throws CamposDeGuerraNotFoundException {
        boolean ans;
        if(rooms.containsKey(room)){
            ans=rooms.get(room).tomarBanderaB(user);
            if(!ans){
                throw  new CamposDeGuerraNotFoundException("La Bandera ya fue tomada o el usuario no existe en la sala!");
            }
        }
        else{
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void puntuarA(String user,Integer room) throws CamposDeGuerraNotFoundException {
        boolean ans;
        if(rooms.containsKey(room)){
            ans=rooms.get(room).puntuarA(user);
            if(!ans){
                throw  new CamposDeGuerraNotFoundException("La Bandera de su equipo fue tomada por el enemigo o el usuario no existe en la sala!");
            }
        }
        else{
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void puntuarB(String user,Integer room) throws CamposDeGuerraNotFoundException {
        boolean ans;
        if(rooms.containsKey(room)){
            ans=rooms.get(room).puntuarB(user);
            if(!ans){
                throw  new CamposDeGuerraNotFoundException("La Bandera de su equipo fue tomada por el enemigo o el usuario no existe en la sala!");
            }
        }
        else{
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void soltarBanderaB(String user,Integer room) throws CamposDeGuerraNotFoundException {
        boolean ans;
        if(rooms.containsKey(room)){
            ans=rooms.get(room).soltarBanderaB(user);
            if(!ans){
                throw  new CamposDeGuerraNotFoundException("Usted no posee la bandera B!");
            }
        }
        else{
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void soltarBanderaA(String user,Integer room) throws CamposDeGuerraNotFoundException {
        boolean ans;
        if(rooms.containsKey(room)){
            ans=rooms.get(room).soltarBanderaA(user);
            if(!ans){
                throw  new CamposDeGuerraNotFoundException("Usted no posee la bandera A!");
            }
        }
        else{
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public List<Integer> obtenerScorer(Integer room) throws CamposDeGuerraNotFoundException {
        List<Integer> ans =new ArrayList<>();
        if(rooms.containsKey(room)){
            ans=rooms.get(room).obtenerScorer();
        }
        else{
            throw  new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
        return ans;
    }

    @Override
    public void addRoom(Room room) throws CamposDeGuerraPersistenceException {
        
    }

    @Override
    public List<Room> getAllRooms() throws CamposDeGuerraNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}