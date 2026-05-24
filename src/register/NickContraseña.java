/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package register;

/**
 *
 * @author fabio
 */
public class NickContraseña{
   private static NickContraseña guardar;
    
    private String nickname;
    private String contraseña;

    private NickContraseña() {
    }
    
    public static NickContraseña getGuardar() {
        if (guardar == null) {
            guardar = new NickContraseña();
        }
        return guardar;
    }


    public String getNickname() { 
        return nickname; }
    public void setNickname(String nickname) { 
        this.nickname = nickname; }

    public String getContraseña() { 
        return contraseña; }
    public void setContraseña(String contraseña) { 
        this.contraseña = contraseña; }
    
}

